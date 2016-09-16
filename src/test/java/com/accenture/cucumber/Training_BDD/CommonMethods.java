package com.accenture.cucumber.Training_BDD;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Fernanda on 9/14/2016.
 */
public class CommonMethods {
    /**
     * Starts a Selenium WebDriver object with the specified target browser,
     * chromedriver location and operating system.
     *
     * <p>Supported Browsers
     * <li><b>Mozilla Firefox: </b>(firefox, ff, mozilla firefox,
     *                            mozilla_firefox, mozilla.firefox)</li>
     * <li><b>Google Chrome: </b>(chrome, gc, google chrome,
     *                            google_chrome, google.chrome)</li>
     * <li><b>Internet Exlorer: </b>(ie, internet explorer,
     *                            internet_explorer, internet.explorer)</li>
     *
     * @param  targetBrowser Which browser should be used for running the
     *                       tests.
     * @param  chromedriverLocation chromedriver full path location.
     *                              Only necessary if running Chrome tests
     *                              and chromedriver is not in the root of
     *                              the project or system path.
     * @param  operatingSystem Which operating system will be running the
     *                         tests. Only necessary if running Chrome tests
     *                         on Linux.
     */
    public static WebDriver StartWebDriver(String targetBrowser,
                                           String chromedriverLocation,
                                           String operatingSystem) {

        WebDriver seleniumWebDriver = null;
        String chromedriver = "";

        try {

            //if browser type is Google Chrome
            if ((targetBrowser.equalsIgnoreCase("chrome")) ||
                    (targetBrowser.equalsIgnoreCase("gc")) ||
                    (targetBrowser.equalsIgnoreCase("google chrome")) ||
                    (targetBrowser.equalsIgnoreCase("google_chrome")) ||
                    (targetBrowser.equalsIgnoreCase("google.chrome"))
                    ) {
                try {
                    //checks operating system
                    if (operatingSystem.equalsIgnoreCase("windows")) {
                        //checks chromedriver location
                        if (chromedriverLocation == "") {
                            chromedriver = "chromedriver.exe";
                        } else {
                            chromedriver = chromedriverLocation + "\\chromedriver.exe";
                        }
                    } else if (operatingSystem.equalsIgnoreCase("linux")) {
                        //checks chromedriver location
                        if (chromedriverLocation == "") {
                            chromedriver = "chromedriver";
                        } else {
                            chromedriver = chromedriverLocation + "/chromedriver.exe";
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid " +
                                "operating system. Provided: " +
                                targetBrowser + " Expected : windows or " +
                                "linux");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("\nSorry, StartWebDriver only supports " +
                            "Microsoft Windows and Linux.\n");
                    e.printStackTrace();
                }

                //setup chrome properties
                System.setProperty("webdriver.chrome.driver", chromedriver);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("no-sandbox");
                options.addArguments("start-maximized");
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);

                //start chrome webdriver
                seleniumWebDriver = new ChromeDriver(capabilities);

                //if browser type is Microsoft Internet Explorer
            } else if ((targetBrowser.equalsIgnoreCase("ie")) ||
                    (targetBrowser.equalsIgnoreCase("internet explorer")) ||
                    (targetBrowser.equalsIgnoreCase("internet_explorer")) ||
                    (targetBrowser.equalsIgnoreCase("internet.explorer")) ||
                    (targetBrowser.equalsIgnoreCase("microsoft internet explorer")) ||
                    (targetBrowser.equalsIgnoreCase("microsoft_internet_explorer")) ||
                    (targetBrowser.equalsIgnoreCase("microsoft.internet.explorer"))
                    ) {
                //start Internet Explorer webdriver
                seleniumWebDriver = new InternetExplorerDriver();

                //if browser type is Mozilla Firefox
            } else if ((targetBrowser.equalsIgnoreCase("firefox")) ||
                    (targetBrowser.equalsIgnoreCase("ff")) ||
                    (targetBrowser.equalsIgnoreCase("mozilla firefox")) ||
                    (targetBrowser.equalsIgnoreCase("mozilla_firefox")) ||
                    (targetBrowser.equalsIgnoreCase("mozilla.firefox"))
                    ) {

                //start Firefox webdriver
                seleniumWebDriver = new FirefoxDriver();

                //invalid browser provided
            } else {
                throw new IllegalArgumentException("Invalid target browser. " +
                        "Provided: " + targetBrowser + " Expected : " +
                        "firefox, chrome or ie");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\nSorry, StartWebDriver only supports Mozilla " +
                    "Firefox, Google Chrome and Microsoft Internet Explorer\n.");
            e.printStackTrace();
        }

        seleniumWebDriver.manage().window().maximize();
        return seleniumWebDriver;
    }

    public static WebDriver StartWebDriver() {
        return StartWebDriver("ff", "", "windows");
    }

    public static WebDriver StartWebDriver(String targetBrowser) {
        return StartWebDriver(targetBrowser, "", "windows");
    }

    public static WebDriver StartWebDriver(String targetBrowser, String chromedriverLocation) {
        return StartWebDriver(targetBrowser, chromedriverLocation, "windows");
    }
}


