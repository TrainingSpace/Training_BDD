package com.accenture.cucumber.Training_BDD;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ALMUpdater {
	/**
	 * @author Alexander Bolte - Bolte Consulting 2015
	 *         <p>
	 *         AlmConnector is only a thin layer around the HP class RestConnector, which resides in the package infrastructure. AlmConnector provides only methods around logging into ALM.
	 *         </p>
	 *         <p>
	 *         HP designed the class RestConnector in a static way. Once initialized, the class holds an instance, which can be referenced by any other class using the method <code>RestConnector.getInstance()</code>.
	 *         </p>
	 *         <p>
	 *         Operations like reading from and writing to HP ALM have to be applied through the class RestConnector directly ignoring the AlmConnector.
	 *         </p>
	 *         <p>
	 *         This class applies for HP ALM 11.52. It has not been tested for newer versions like 12.0.
	 *         </p>
	 */
	public class AlmConnector {
		private RestConnector con;

		/**
		 * <p>
		 * Once you initialized the class RestConnector, you can use this constructor to create a new object from AlmConnector since the referenced class RestConnector is keeping the connection details.
		 * </p>
		 */
		public AlmConnector() {
			this.con = RestConnector.getInstance();
		}

		/**
		 * <p>
		 * Initializes / prepares a new connection to HP ALM using the provided details. A connection to ALM is realized using the class infrastructure.RestConnector.
		 * </p>
		 * <p>
		 * In order to open a connection prepared using this constructor you have to call the method <code>login</code> from this class and provide a user name as well as the corresponding password.
		 * </p>
		 * <p>
		 * Therefore connecting to ALM would look as follows.
		 * </p>
		 * <code>
		 * AlmConnector alm = new AlmConnector(new HashMap<String, String>(), Constants.HOST, Constants.DOMAIN, Constants.PROJECT); 
		 * <br/><br/> 
		 * alm.login("userName", "HP ALM Password");
		 * </code>
		 * 
		 * @param serverUrl
		 *            - a String providing an URL following the format <code>"https://{HOST}/qcbin"</code>
		 * @param domain
		 *            - a String providing the domain a user wants to log onto.
		 * @param project
		 *            - a String providing the name of a project a user wants to log into.
		 */
		public AlmConnector(final String serverUrl, final String domain, final String project) {
			this.con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);
		}

		/**
		 * Indicates if a user is already authenticated and returns an URL to authenticate against if the user is not authenticated yet. Having this said the returned URL is always as follows. https://{host}/qcbin/authentication-point/authenticate
		 * 
		 * @return null if a user is already authenticated.<br>
		 *         else an URL to authenticate against.
		 * @throws Exception
		 *             - an Exception occurs, if HTTP errors like 404 or 500 occur and the thrown Exception should reflect those errors.
		 */
		public String isAuthenticated() throws Exception {
			String isAuthenticateUrl = con.buildUrl("rest/is-authenticated");
			System.out.println("isAuthenticateUrl = " + isAuthenticateUrl);
			String ret;
			Response response = con.httpGet(isAuthenticateUrl, null, null);
			int responseCode = response.getStatusCode();
			/**
			 * If a user is already authenticated, the return value is set to null and the current connection is kept open.
			 */
			if (responseCode == HttpURLConnection.HTTP_OK) {
				ret = null;
			}
			/**
			 * If a user is not authenticated yet, return an URL at which he can authenticate himself via www-authenticate.
			 */
			else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
				ret = con.buildUrl("authentication-point/authenticate");
			}
			/**
			 * If an error occurred during login, the function throws an Exception.
			 */
			else {
				throw response.getFailure();
			}
			return ret;
		}

		/**
		 * <p>
		 * Attempts to log a user into an ALM project. If a user is already authenticated, no action is applied but true will be returned.
		 * </p>
		 * <p>
		 * Calling <code>login</code> after being already authenticated will not logout the currently logged in user. You specifically have to call <code>logout</code> before logging in with other user credentials.
		 * </p>
		 * <p>
		 * To check if a user is authenticated call method <code>isAuthenticated()</code>.
		 * </p>
		 * 
		 * @param username
		 *            - a String providing the name of a user in HP ALM.
		 * @param password
		 *            - the HP ALM password corresponding a provided user name.
		 * @return true if user is successfully authenticated else false.
		 * @throws Exception
		 */
		public boolean login(String username, String password) throws Exception {
			/**
			 * Get the current authentication status.
			 */
			String authenticationPoint = this.isAuthenticated();
			/**
			 * If the authenticationPoint is null, the user is already authenticated. In this case no login necessary.
			 */
			if (authenticationPoint != null) {
				return this.login(authenticationPoint, username, password);
			}
			return true;
		}

		/**
		 * Closes a session on a server and cleans session cookies on a client.
		 * 
		 * @return true if logout was successful.
		 * @throws Exception
		 */
		public boolean logout() throws Exception {
			/**
			 * note the get operation logs us out by setting authentication cookies to: LWSSO_COOKIE_KEY="" via server response header Set-Cookie
			 */
			Response response = con.httpGet(con.buildUrl("authentication-point/logout"), null, null);
			return (response.getStatusCode() == HttpURLConnection.HTTP_OK);
		}

		/**
		 * <p>
		 * Logging into HP ALM is standard HTTP login (basic authentication), where one must store the returned cookies for further use.
		 * <p>
		 * 
		 * @param loginUrl
		 *            - a String providing an URL to authenticate at.
		 * @param username
		 *            - an HP ALM user name.
		 * @param password
		 *            - an HP ALM user password corresponding username.
		 * @return true if login is successful, else false.
		 * @throws Exception
		 */
		private boolean login(String loginUrl, String username, String password) throws Exception {
/**
			 * create a string that looks like:
			 * "Basic ((username:password)<as bytes>)<64encoded>"
			 */
			byte[] credBytes = (username + ":" + password).getBytes();
			String credEncodedString = "Basic " + Base64Encoder.encode(credBytes);
			Map<String, String> map = new HashMap<String, String>();
			System.out.println("credEncodedString = " + credEncodedString);
			//map.put("Authorization", "cGVycmoxMTU6Um9ndWUxMTc=");
			map.put("Authorization", credEncodedString);
			Response response = con.httpGet(loginUrl, null, map);
			boolean ret = response.getStatusCode() == HttpURLConnection.HTTP_OK;
			System.out.println("login ret = " + ret);
			return ret;
		}
	}

	public static class Base64Encoder {
		private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
		private static int[] toInt = new int[128];
		static {
			for (int i = 0; i < ALPHABET.length; i++) {
				toInt[ALPHABET[i]] = i;
			}
		}

		/**
		 * Translates the specified byte array into Base64 string.
		 *
		 * @param buf
		 *            the byte array (not null)
		 * @return the translated Base64 string (not null)
		 */
		public static String encode(byte[] buf) {
			int size = buf.length;
			char[] ar = new char[((size + 2) / 3) * 4];
			int a = 0;
			int i = 0;
			while (i < size) {
				byte b0 = buf[i++];
				byte b1 = (i < size) ? buf[i++] : 0;
				byte b2 = (i < size) ? buf[i++] : 0;
				int mask = 0x3F;
				ar[a++] = ALPHABET[(b0 >> 2) & mask];
				ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
				ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
				ar[a++] = ALPHABET[b2 & mask];
			}
			switch (size % 3) {
			case 1:
				ar[--a] = '=';
			case 2:
				ar[--a] = '=';
			}
			return new String(ar);
		}
	}

	/**
	 * These constants are used throughout the code to set the server to work with. To execute this code, change these settings to fit those of your server.
	 */
	public static class Constants {
		public static final String HOST = "http://dtssalm-qa.disney.com/qcbin"; // Make sure that there is no slash at the end!
		public static final String USERNAME = System.getenv("almUser");
		public static final String PASSWORD = System.getenv("almPass");
		public static final String DOMAIN = "TRAINING";
		public static final String PROJECT = "TCoE_Sample_Data";

		//		public static final String HOST = "http://cmnckapv0099.swna.wdpr.disney.com:9090/qcbin";
		//		public static final String PORT = "9090";
		//		public static final String USERNAME = System.getenv("almUser");
		//		public static final String PASSWORD = System.getenv("almPass");
		//		public static final String DOMAIN = "PILOT";
		//		public static final String PROJECT = "GQE";
		/**
		 * Supports running tests correctly on both versioned and non-versioned projects.
		 * 
		 * @return true if entities of entityType support versioning
		 */
		public static boolean isVersioned(String entityType, final String domain, final String project) throws Exception {
			RestConnector con = RestConnector.getInstance();
			String descriptorUrl = con.buildUrl("rest/domains/" + domain + "/projects/" + project + "/customization/entities/" + entityType);
			String descriptorXml = con.httpGet(descriptorUrl, null, null).toString();
			/////EntityDescriptor descriptor = EntityMarshallingUtils.marshal(EntityDescriptor.class, descriptorXml);
			///boolean isVersioned = descriptor.getSupportsVC().getValue();
			boolean isVersioned = false;
			System.out.println("Fix this isVersioned");
			return isVersioned;
		}

		private Constants() {
		}
	}

	/**
	 * This is a naive implementation of an HTTP response. We use it to simplify matters in the examples. It is nothing more than a container of the response headers and the response body.
	 */
	public class Response {
		private Map<String, ? extends Iterable<String>> responseHeaders = null;
		private byte[] responseData = null;
		private Exception failure = null;
		private int statusCode = 0;

		public Response() {
		}

		public Response(Map<String, Iterable<String>> responseHeaders, byte[] responseData, Exception failure, int statusCode) {
			super();
			this.responseHeaders = responseHeaders;
			this.responseData = responseData;
			this.failure = failure;
			this.statusCode = statusCode;
		}

		/**
		 * @return the failure if the access to the requested URL failed, such as a 404 or 500. If no such failure occured this method returns null.
		 */
		public Exception getFailure() {
			return failure;
		}

		/**
		 * @return the responseData
		 */
		public byte[] getResponseData() {
			return responseData;
		}

		/**
		 * @return the responseHeaders
		 */
		public Map<String, ? extends Iterable<String>> getResponseHeaders() {
			return responseHeaders;
		}

		/**
		 * @return the statusCode
		 */
		public int getStatusCode() {
			return statusCode;
		}

		/**
		 * @param failure
		 *            the failure to set
		 */
		public void setFailure(Exception failure) {
			this.failure = failure;
		}

		/**
		 * @param responseData
		 *            the responseData to set
		 */
		public void setResponseData(byte[] responseData) {
			this.responseData = responseData;
		}

		/**
		 * @param responseHeaders
		 *            the responseHeaders to set
		 */
		public void setResponseHeaders(Map<String, ? extends Iterable<String>> responseHeaders) {
			this.responseHeaders = responseHeaders;
		}

		/**
		 * @param statusCode
		 *            the statusCode to set
		 */
		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		/**
		 * @see Object#toString() return the contents of the byte[] data as a string.
		 */
		@Override
		public String toString() {
			return new String(this.responseData);
		}
	}

	/**
	 * This class keeps the state of the connection for the examples. This class is a thus sharing state singleton. All examples get the instance in their default constructors - (cookies, server url). Some simple methods are implemented to get commonly used paths.
	 */
	public static class RestConnector {
		private static RestConnector instance = new RestConnector();

		public static RestConnector getInstance() {
			return instance;
		}
		protected Map<String, String> cookies;
		/**
		 * This is the URL to the ALM application. For example: http://myhost:8080/qcbin. Make sure that there is no slash at the end.
		 */
		protected String serverUrl;
		protected String domain;
		protected String project;

		private RestConnector() {
		}

		public String buildEntityCollectionUrl(String entityType) {
			System.out.println("start buildEntityCollectionUrl");
			System.out.println("buildEntityCollectionUrl domain = " + domain);
			System.out.println("buildEntityCollectionUrl project = " + project);
			return buildUrl("rest/domains/" + domain + "/projects/" + project + "/" + entityType.replaceAll(" ", "%20"));
		}

		/**
		 * @param path
		 *            on the server to use
		 * @return a url on the server for the path parameter
		 */
		public String buildUrl(String path) {
			return String.format("%1$s/%2$s", serverUrl, path);
		}

		/**
		 * @return the cookies
		 */
		public Map<String, String> getCookies() {
			return cookies;
		}

		public String getCookieString() {
			StringBuilder sb = new StringBuilder();
			if (!cookies.isEmpty()) {
				Set<Entry<String, String>> cookieEntries = cookies.entrySet();
				for (Entry<String, String> entry : cookieEntries) {
					sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
				}
			}
			String ret = sb.toString();
			return ret;
		}

		//added per KM01754222   
		public void getQCSession() {
			String qcsessionurl = this.buildUrl("rest/site-session");
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Content-Type", "application/xml");
			requestHeaders.put("Accept", "application/xml");
			try {
				Response resp = this.httpPost(qcsessionurl, null, requestHeaders);
				this.updateCookies(resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public Response httpDelete(String url, Map<String, String> headers) throws Exception {
			return doHttp("DELETE", url, null, null, headers, cookies);
		}

		public Response httpGet(String url, String queryString, Map<String, String> headers) throws Exception {
			return doHttp("GET", url, queryString, null, headers, cookies);
		}

		public Response httpPost(String url, byte[] data, Map<String, String> headers) throws Exception {
			System.out.println("httpPost = " + "POST");
			System.out.println("httpPost url = " + url);
			//System.out.println("httpPost data = " + data.toString());
			System.out.println("httpPost headers = " + headers.toString());
			System.out.println("httpPost cookies = " + cookies.toString());
			return doHttp("POST", url, null, data, headers, cookies);
		}

		public Response httpPut(String url, byte[] data, Map<String, String> headers) throws Exception {
			return doHttp("PUT", url, null, data, headers, cookies);
		}

		public RestConnector init(Map<String, String> cookies, String serverUrl, String domain, String project) {
			this.cookies = cookies;
			this.serverUrl = serverUrl;
			this.domain = domain;
			this.project = project;
			return this;
		}

		/**
		 * @param cookies
		 *            the cookies to set
		 */
		public void setCookies(Map<String, String> cookies) {
			this.cookies = cookies;
		}

		/**
		 * @param type
		 *            http operation: get post put delete
		 * @param url
		 *            to work on
		 * @param queryString
		 * @param data
		 *            to write, if a writable operation
		 * @param headers
		 *            to use in the request
		 * @param cookies
		 *            to use in the request and update from the response
		 * @return http response
		 * @throws Exception
		 */
		private Response doHttp(String type, String url, String queryString, byte[] data, Map<String, String> headers, Map<String, String> cookies) throws Exception {
			if ((queryString != null) && !queryString.isEmpty()) {
				url += "?" + queryString;
			}
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod(type);
			String cookieString = getCookieString();
			prepareHttpRequest(con, headers, data, cookieString);
			con.connect();
			Response ret = retrieveHtmlResponse(con);
			updateCookies(ret);
			return ret;
		}

		/**
		 * @param con
		 *            connection to set the headers and bytes in
		 * @param headers
		 *            to use in the request, such as content-type
		 * @param bytes
		 *            the actual data to post in the connection.
		 * @param cookieString
		 *            the cookies data from clientside, such as lwsso, qcsession, jsession etc.
		 * @throws java.io.IOException
		 */
		private void prepareHttpRequest(HttpURLConnection con, Map<String, String> headers, byte[] bytes, String cookieString) throws IOException {
			String contentType = null;
			//attach cookie information if such exists
			if ((cookieString != null) && !cookieString.isEmpty()) {
				con.setRequestProperty("Cookie", cookieString);
			}
			//send data from headers
			if (headers != null) {
				//Skip the content-type header - should only be sent
				//if you actually have any content to send. see below.
				contentType = headers.remove("Content-Type");
				Iterator<Entry<String, String>> headersIterator = headers.entrySet().iterator();
				while (headersIterator.hasNext()) {
					Entry<String, String> header = headersIterator.next();
					con.setRequestProperty(header.getKey(), header.getValue());
				}
			}
			// If there's data to attach to the request, it's handled here.
			// Note that if data exists, we take into account previously removed
			// content-type.
			if ((bytes != null) && (bytes.length > 0)) {
				con.setDoOutput(true);
				//warning: if you add content-type header then you MUST send
				// information or receive error.
				//so only do so if you're writing information...
				if (contentType != null) {
					con.setRequestProperty("Content-Type", contentType);
				}
				OutputStream out = con.getOutputStream();
				out.write(bytes);
				out.flush();
				out.close();
			}
		}

		/**
		 * @param con
		 *            that is already connected to its url with an http request, and that should contain a response for us to retrieve
		 * @return a response from the server to the previously submitted http request
		 * @throws Exception
		 */
		private Response retrieveHtmlResponse(HttpURLConnection con) throws Exception {
			Response ret = objALMUpdater.new Response();
			ret.setStatusCode(con.getResponseCode());
			ret.setResponseHeaders(con.getHeaderFields());
			InputStream inputStream;
			//select the source of the input bytes, first try 'regular' input
			try {
				inputStream = con.getInputStream();
			}
			/*
			 * If the connection to the server somehow failed, for example 404 or 500, con.getInputStream() will throw an exception, which we'll keep. We'll also store the body of the exception page, in the response data.
			 */
			catch (Exception e) {
				inputStream = con.getErrorStream();
				ret.setFailure(e);
			}
			// This actually takes the data from the previously set stream
			// (error or input) and stores it in a byte[] inside the response
			ByteArrayOutputStream container = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int read;
			while ((read = inputStream.read(buf, 0, 1024)) > 0) {
				container.write(buf, 0, read);
			}
			ret.setResponseData(container.toByteArray());
			return ret;
		}

		private void updateCookies(Response response) {
			Iterable<String> newCookies = response.getResponseHeaders().get("Set-Cookie");
			if (newCookies != null) {
				for (String cookie : newCookies) {
					int equalIndex = cookie.indexOf('=');
					int semicolonIndex = cookie.indexOf(';');
					String cookieKey = cookie.substring(0, equalIndex);
					String cookieValue = cookie.substring(equalIndex + 1, semicolonIndex);
					cookies.put(cookieKey, cookieValue);
				}
			}
		}
	}

	private class ExceptionParseXML extends Exception {
		private static final long serialVersionUID = 1L;

		private ExceptionParseXML(String message) {
			super(message);
		}
	}

	private class ExceptionTotalResultsGreaterThanOne extends Exception {
		private static final long serialVersionUID = 1L;

		private ExceptionTotalResultsGreaterThanOne(String message) {
			super(message);
		}
	}

	private class ExceptionTotalResultsZero extends Exception {
		private static final long serialVersionUID = 1L;

		private ExceptionTotalResultsZero(String message) {
			super(message);
		}
	}

	private class ExceptionValidateTestSetFolderPath extends Exception {
		private static final long serialVersionUID = 1L;

		private ExceptionValidateTestSetFolderPath(String message) {
			super(message);
		}
	}
	static ALMUpdater objALMUpdater = new ALMUpdater();
	AlmConnector alm = new AlmConnector();
	RestConnector conn = RestConnector.getInstance();

	public void almUpdateTestStatus(String strTestId, String strTestSetFolderPath, String strTestSetName, String strTestInstance, String strEnvironment, String strRunStatus, String strDuration, String strJenkinsBuildNumber) throws Exception {
		System.out.println("start almUpdateTestStatus");
		Map<String, String> requestHeaders = new HashMap<String, String>();
		Response response = null;
		String strCycleId = "";
		String strDataXML = "";
		String strRunID = "";
		String strRunsUrl = "";
		String strRunUrl = "";
		String strTestConfigID = "";
		String strTestcyclID = "";
		String strTestInstancesUrl = "";
		try {

			//1. Setup ALM connection. URL, credential and projects are defined in CONSTANTS object
			conn.init(new HashMap<String, String>(), Constants.HOST, Constants.DOMAIN, Constants.PROJECT);
			alm.login(Constants.USERNAME, Constants.PASSWORD);
			conn.getQCSession();

			//2. Identify test set ID based on the folder path and test set name
			strCycleId = getTestSetID(strTestSetFolderPath + strEnvironment, strTestSetName);
			if (strCycleId == "") {
				throw new ExceptionTotalResultsZero("The strCycleId returned no results");
			}

			//3. Create REST API url that points to the test instance to be updated
			strTestInstancesUrl = conn.buildEntityCollectionUrl("test-instances?query={test-id['" + strTestId + "'];test-instance['" + strTestInstance + "'];cycle-id['" + strCycleId + "']}");
			System.out.println("almUpdateTestStatus: strTestInstancesUrl = " + strTestInstancesUrl);

			//4. Call API to read current test instance properties in ALM
			requestHeaders.put("Accept", "application/xml");
			response = conn.httpGet(strTestInstancesUrl, null, requestHeaders);
			System.out.println("almUpdateTestStatus: response.toString() = ");
			System.out.println(formatXML(response.toString()));

			//5. Identify test instance ID and test config ID from the GET API results
			//////////Get
			confirmTotalResults(response.toString());
			strTestcyclID = getXmlFieldByName(response.toString(), "id");
			if (strTestcyclID == "") {
				throw new ExceptionTotalResultsZero("The strTestcyclID returned no results");
			}
			System.out.println("almUpdateTestStatus: strTestcyclID = " + strTestcyclID);
			strTestConfigID = getXmlFieldByName(response.toString(), "test-config-id");
			System.out.println("almUpdateTestStatus: strTestConfigID = " + strTestConfigID);

			//6. Create URL to update the ALM run
			strRunsUrl = conn.buildEntityCollectionUrl("runs");
			System.out.println("almUpdateTestStatus: strRunsUrl = " + strRunsUrl);

			//7. Create XML string that will create a run in ALM with Jenkins execution info
			response = null;
			requestHeaders.clear();
			//
			requestHeaders.put("Accept", "application/xml");
			requestHeaders.put("Content-Type", "application/xml");
			strDataXML += "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
			strDataXML += "<Entity Type=\"run\">";
			strDataXML += "<Fields>";
			strDataXML += "<Field Name=\"test-config-id\"><Value>" + strTestConfigID + "</Value></Field>";
			strDataXML += "<Field Name=\"cycle-id\"><Value>" + strCycleId + "</Value></Field>";
			strDataXML += "<Field Name=\"test-id\"><Value>" + strTestId + "</Value></Field>";
			strDataXML += "<Field Name=\"testcycl-id\"><Value>" + strTestcyclID + "</Value></Field>";
			////////////////    Run: 07-Apr-2017 11:41:51
			// Rename the run to match with the Jenkins build #
			strDataXML += "<Field Name=\"name\"><Value>TCoE ALM REST API update from Jenkins Build " + strJenkinsBuildNumber + "</Value></Field>";
			strDataXML += "<Field Name=\"owner\"><Value>" + Constants.USERNAME + "</Value></Field>";
			// First status when creating the run = NOT COMPLETED
			strDataXML += "<Field Name=\"status\"><Value>Not Completed</Value></Field>";
			strDataXML += "<Field Name=\"subtype-id\"><Value>hp.qc.run.MANUAL</Value></Field>";
			strDataXML += "<Field Name=\"duration\"><Value>" + strDuration + "</Value></Field>";
			strDataXML += "<Field Name=\"execution-date\"><Value>2017-04-01</Value></Field>";
			strDataXML += "<Field Name=\"execution-time\"><Value>15:10:07</Value></Field>";
			strDataXML += "</Fields>";
			strDataXML += "</Entity>";
			System.out.println("almUpdateTestStatus: strDataXML = ");
			System.out.println(formatXML(strDataXML));

			//8. Create run in ALM (POST)
			response = conn.httpPost(strRunsUrl, strDataXML.getBytes(), requestHeaders);
			//////////Post
			System.out.println("almUpdateTestStatus: response.toString() = ");
			System.out.println(formatXML(response.toString()));

			//9. Create XML string tht will update the newly created run with Jenkins execution status (
			strRunID = getXmlFieldByName(response.toString(), "id");
			if (strRunID == "") {
				throw new ExceptionTotalResultsZero("The response xml had no results: ");
			}
			strRunUrl = conn.buildEntityCollectionUrl("runs/" + strRunID);
			System.out.println("almUpdateTestStatus: strRunUrl = " + strRunUrl);
			strDataXML = "";
			response = null;
			requestHeaders.clear();
			requestHeaders.put("Accept", "application/xml");
			requestHeaders.put("Content-Type", "application/xml");
			strDataXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
			strDataXML += "<Entity Type=\"run\">";
			strDataXML += "<Fields>";
			strDataXML += "<Field Name=\"status\"><Value>" + strRunStatus + "</Value></Field>";
			strDataXML += "</Fields>";
			strDataXML += "</Entity>";
			System.out.println("almUpdateTestStatus: strDataRun = ");
			System.out.println(formatXML(strDataXML));

			//10. Update run in ALM with Jenkins execution status (PUT)
			response = conn.httpPut(strRunUrl, strDataXML.getBytes(), requestHeaders);
			//////////Put
			System.out.println("almUpdateTestStatus: response.toString() = ");
			System.out.println(formatXML(response.toString()));

		} catch (Exception e) {
			System.out.println("almUpdateTestStatus: Exception = " + e.toString());
		} finally {
			alm.logout();
		}
	}

	private void confirmTotalResults(String strXML) throws ExceptionParseXML, ExceptionTotalResultsZero, ExceptionTotalResultsGreaterThanOne {
		String strTotalResults = "";
		try {
			DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = newDocumentBuilder.parse(new ByteArrayInputStream(strXML.getBytes()));
			doc.getDocumentElement().normalize();
			System.out.println("getXmlFieldByName: Root element :" + doc.getDocumentElement().getNodeName());
			strTotalResults = doc.getDocumentElement().getAttribute("TotalResults");
			System.out.println("getXmlFieldByName: Root element TotalResults :" + strTotalResults);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionParseXML("getXmlFieldByName: The response xml had errors during parsing");
		}
		if (strTotalResults == "" || strTotalResults == "0") {
			throw new ExceptionTotalResultsZero("The response xml had no results: " + strTotalResults);
		}
		if (Integer.parseInt(strTotalResults) > 1) {
			throw new ExceptionTotalResultsGreaterThanOne("The response xml had results greater than 1: " + strTotalResults);
		}
	}

	private String formatXML(String input) {
		try {
			final InputSource src = new InputSource(new StringReader(input));
			final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
			final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			final LSSerializer writer = impl.createLSSerializer();
			writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			writer.getDomConfig().setParameter("xml-declaration", true);
			return writer.writeToString(document);
		} catch (Exception e) {
			e.printStackTrace();
			return input;
		}
	}

	private String getTestSetID(String strTestSetFolderPath, String strTestSetName) {
		Map<String, String> requestHeaders = new HashMap<String, String>();
		Response response;
		String strTestSetFolderID = "0";
		String strTestSetFolderPathEach = "";
		String strTestSetFoldersUrl = "";
		String strTestSetID = "0";
		String strTestSetUrl = "";
		String[] arrTestSetFolderPath = strTestSetFolderPath.split("\\\\");
		System.out.println("getTestSetID: strTestSetFolderPath = " + strTestSetFolderPath);
		System.out.println("getTestSetID: arrTestSetFolderPath.length = " + arrTestSetFolderPath.length);
		try {
			if (arrTestSetFolderPath.length == 1) {
				System.out.println("getTestSetID: arrTestSetFolderPath.length must be greater 0 include full path starting with Root\\");
				throw new ExceptionValidateTestSetFolderPath("getTestSetID: strTestSetFolderPath must contain \\\\ seperating folder path");
			}
			if (arrTestSetFolderPath[0].toString().equalsIgnoreCase("root") == false) {
				System.out.println("getTestSetID: arrTestSetFolderPath[0].toString() must start with Root\\\\");
				throw new ExceptionValidateTestSetFolderPath("getTestSetID: strTestSetFolderPath must start with Root\\\\");
			}
			requestHeaders.put("Accept", "application/xml");
			for (int intEach = 1; intEach < arrTestSetFolderPath.length; intEach++) {
				strTestSetFolderPathEach = arrTestSetFolderPath[intEach].toString();
				System.out.println("getTestSetID: strTestSetFolderPathEach = " + strTestSetFolderPathEach);
				strTestSetFoldersUrl = conn.buildEntityCollectionUrl("test-set-folders?query={name['" + strTestSetFolderPathEach + "'];parent-id['" + strTestSetFolderID + "']}");
				response = conn.httpGet(strTestSetFoldersUrl, null, requestHeaders);
				confirmTotalResults(response.toString());
				System.out.println("getTestSetID: response.toString() = ");
				System.out.println(formatXML(response.toString()));
				strTestSetFolderID = getXmlFieldByName(response.toString(), "id");
				System.out.println("getTestSetID: strTestSetFolderID = " + strTestSetFolderID);
			}
			response = null;
			strTestSetUrl = conn.buildEntityCollectionUrl("test-sets?query={name['" + strTestSetName + "'];parent-id['" + strTestSetFolderID + "']}");
			System.out.println("getTestSetID: strTestSetUrl = " + strTestSetUrl);
			response = conn.httpGet(strTestSetUrl, null, requestHeaders);
			System.out.println("getTestSetID: response.toString() = ");
			System.out.println(formatXML(response.toString()));
			confirmTotalResults(response.toString());
			strTestSetID = getXmlFieldByName(response.toString(), "id");
			System.out.println("getTestSetID: strTestSetID = " + strTestSetID);
		} catch (Exception e) {
			System.out.println("getTestSetID: Exception = " + e.toString());
		}
		return strTestSetID;
	}

	private String getXmlFieldByName(String strXML, String strNameToFind) throws ExceptionParseXML {
		String strReturnValue = "";
		try {
			DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = newDocumentBuilder.parse(new ByteArrayInputStream(strXML.getBytes()));
			doc.getDocumentElement().normalize();
			System.out.println("getXmlFieldByName: Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("Field");
			System.out.println("getXmlFieldByName:----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String strName = eElement.getAttribute("Name");
					if (eElement.getElementsByTagName("Value").getLength() > 0) {
						String strValue = eElement.getElementsByTagName("Value").item(0).getTextContent();
						if (strValue != null) {
							if (strName.equals(strNameToFind)) {
								strReturnValue = strValue;
								System.out.println("getXmlFieldByName: strName : strReturnValue " + strName + " : " + strReturnValue);
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("getXmlFieldByName: Exception = " + e.toString());
			throw new ExceptionParseXML("getXmlFieldByName: The response xml had errors during parsing");
		}
		return strReturnValue;
	}

	private void testAlmLogin() throws Exception {
		//AlmConnector alm = new AlmConnector();
		//RestConnector conn = RestConnector.getInstance();
		conn.init(new HashMap<String, String>(), Constants.HOST, Constants.DOMAIN, Constants.PROJECT);
		alm.login(Constants.USERNAME, Constants.PASSWORD);
		conn.getQCSession();
		//alm.logout();
	}

	public void testAttachment() throws Exception {
		// File.separator
		Path path = null;
		String octetStreamFileName = "";
		AttachmentsExample attach = new AttachmentsExample();
		final String runUrl = conn.buildEntityCollectionUrl("runs/3585875");
		System.out.println(runUrl);
		//		path = Paths.get("C:/temp/error.JPG");
		//		octetStreamFileName = "error.JPG";
		//		path = Paths.get("C:/temp/log_testCanadianGuestPositive.log");
		//		octetStreamFileName = "log_testCanadianGuestPositive.log";
		//		path = Paths.get("C:/temp/emailable-report.html");
		//		octetStreamFileName = "emailable-report.html";
		path = Paths.get("C:/temp/testng-results.xml");
		octetStreamFileName = "testng-results.xml";
		System.out.println(path.toString());
		byte[] data = Files.readAllBytes(path);
		System.out.println(data);
		String newOctetStreamAttachmentUrl = attach.attachWithOctetStream(runUrl, data, octetStreamFileName);
		System.out.println(newOctetStreamAttachmentUrl);
	}

	/**
	 * This example shows how to work with attachments. While the other examples show how to read/write/update/delete resources, usually entities, this example shows all of those operations for files.
	 */
	public class AttachmentsExample {
		/**
		 * @param entityUrl
		 *            the entity whose attachment we'd like to update
		 * @param bytes
		 *            the data to write instead of previously stored data
		 * @param attachmentFileName
		 *            the attachment we'd like to update file name on the server side.
		 * @return
		 */
		private String updateAttachmentData(String entityUrl, byte[] bytes, String attachmentFileName) throws Exception {
			Map<String, String> requestHeaders = new HashMap<String, String>();
			// This line makes the update be on data and not properties
			// such as description.
			requestHeaders.put("Content-Type", "application/octet-stream");
			requestHeaders.put("Accept", "application/xml");
			Response putResponse = con.httpPut(entityUrl + "/attachments/" + attachmentFileName, bytes, requestHeaders);
			if (putResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception(putResponse.toString());
			}
			byte[] ret = putResponse.getResponseData();
			return new String(ret);
		}

		/**
		 * @param entityUrl
		 *            url of entity whose attachment's description we want to update
		 * @param description
		 *            string to store as description
		 * @param attachmentFileName
		 *            the attachment file name on the server-side whose description we'd like to update
		 * @return
		 */
		private String updateAttachmentDescription(String entityUrl, String description, String attachmentFileName) throws Exception {
			Map<String, String> requestHeaders = new HashMap<String, String>();
			// This line makes the update be on properties such as description
			// and not on the files binary data
			requestHeaders.put("Content-Type", "application/xml");
			requestHeaders.put("Accept", "application/xml");
			Response putResponse = con.httpPut(entityUrl + "/attachments/" + attachmentFileName, ("<Entity Type=\"attachment\"><Fields><Field Name=\"description\"><Value>" + description + "</Value></Field></Fields></Entity>").getBytes(), requestHeaders);
			if (putResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception(putResponse.toString());
			}
			byte[] ret = putResponse.getResponseData();
			return new String(ret);
		}
		RestConnector con;

		public AttachmentsExample() {
			con = RestConnector.getInstance();
		}

		/**
		 * @param attachmentUrl
		 *            of attachment
		 * @return the xml of the metadata on the requested attachment
		 */
		private String readAttachmentDetails(String attachmentUrl) throws Exception {
			Map<String, String> requestHeaders = new HashMap<String, String>();
			/*
			 * A get operation that specifies via accept header that we must have an application/xml reply. An alt query parameter could also have been used.
			 */
			requestHeaders.put("Accept", "application/xml");
			Response readResponse = con.httpGet(attachmentUrl, null, requestHeaders);
			if (readResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception(readResponse.toString());
			}
			return readResponse.toString();
		}

		/**
		 * @param attachmentUrl
		 *            of attachment
		 * @return the contents of the file
		 */
		private byte[] readAttachmentData(String attachmentUrl) throws Exception {
			Map<String, String> requestHeaders = new HashMap<String, String>();
			/*
			 * A get operation that specifies via accept header that we must have an application/octet-stream reply. An alt query parameter could also have been used.
			 */
			requestHeaders.put("Accept", "application/octet-stream");
			Response readResponse = con.httpGet(attachmentUrl, null, requestHeaders);
			if (readResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception(readResponse.toString());
			}
			return readResponse.getResponseData();
		}

		/**
		 * @param entityUrl
		 *            of the entity whose attachments we want to get
		 * @return an xml with metadata on all attachmens of the entity
		 * @throws Exception
		 */
		private String readAttachments(String entityUrl) throws Exception {
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Accept", "application/xml");
			Response readResponse = con.httpGet(entityUrl + "/attachments", null, requestHeaders);
			if (readResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception(readResponse.toString());
			}
			return readResponse.toString();
		}

		/**
		 * @param entityUrl
		 *            url of entity to attach the file to
		 * @param fileData
		 *            content of file
		 * @param filename
		 *            to use on server side
		 * @return
		 */
		private String attachWithOctetStream(String entityUrl, byte[] fileData, String filename) throws Exception {
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Slug", filename);
			requestHeaders.put("Content-Type", "application/octet-stream");
			Response response = con.httpPost(entityUrl + "/attachments", fileData, requestHeaders);
			if (response.getStatusCode() != HttpURLConnection.HTTP_CREATED) {
				throw new Exception(response.toString());
			}
			return response.getResponseHeaders().get("Location").iterator().next();
		}

		/**
		 * @param entityUrl
		 *            url of entity to attach the file to
		 * @param fileData
		 *            content of file
		 * @param contentType
		 *            of the file - txt/html or xml, or octetstream etc..
		 * @param filename
		 *            to use on serverside
		 * @return
		 */
		private String attachWithMultipart(String entityUrl, byte[] fileData, String contentType, String filename, String description) throws Exception {
			/*
			 * headers: Content-Type: multipart/form-data; boundary=<boundary> //template for file mime part: --<boundary>\r\n Content-Disposition: form-data; name="<fieldName>"; filename="<filename>"\r\n Content-Type: <mime-type>\r\n \r\n <file-data>\r\n <boundary>-- //template for post parameter mime part, such as description and/or filename: --<boundary>\r\n Content-Disposition: form-data; name="<fieldName>"\r\n \r\n <value>\r\n <boundary>-- //end of parts: --<boundary>-- we need 3 parts: filename(template for parameter), description(template for parameter), and file data(template for file).
			 */
			// This can be pretty much any string.
			// It's used to mark the different mime parts
			String boundary = "exampleboundary";
			//template to use when sending field data (assuming none-binary data)
			String fieldTemplate = "--%1$s\r\n" + "Content-Disposition: form-data; name=\"%2$s\" \r\n\r\n" + "%3$s" + "\r\n";
			// Template to use when sending file data.
			// Binary data still needs to be suffixed.
			String fileDataPrefixTemplate = "--%1$s\r\n" + "Content-Disposition: form-data; name=\"%2$s\"; filename=\"%3$s\"\r\n" + "Content-Type: %4$s\r\n\r\n";
			String filenameData = String.format(fieldTemplate, boundary, "filename", filename);
			String descriptionData = String.format(fieldTemplate, boundary, "description", description);
			String fileDataSuffix = "\r\n--" + boundary + "--";
			String fileDataPrefix = String.format(fileDataPrefixTemplate, boundary, "file", filename, contentType);
			// Note the order - extremely important:
			// Filename and description before file data.
			// Name of file in file part and filename part value MUST MATCH.
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bytes.write(filenameData.getBytes());
			bytes.write(descriptionData.getBytes());
			bytes.write(fileDataPrefix.getBytes());
			bytes.write(fileData);
			bytes.write(fileDataSuffix.getBytes());
			bytes.close();
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Content-Type", "multipart/form-data; boundary=" + boundary);
			Response response = con.httpPost(entityUrl + "/attachments", bytes.toByteArray(), requestHeaders);
			if (response.getStatusCode() != HttpURLConnection.HTTP_CREATED) {
				throw new Exception(response.toString());
			}
			return response.getResponseHeaders().get("Location").iterator().next();
		}
	}
}

