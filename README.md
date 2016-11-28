# ss.lv

Google chrome, Firefox, JDK 1.8 and the latest maven version have to be installed for test execution

Test execution command on Windows OS: Go to project directory and execute command "mvn clean test -P grid_no,browser_chrome"
You have to download appropriate driver for [Chrome](https://sites.google.com/a/chromium.org/chromedriver/) or [FF](https://github.com/mozilla/geckodriver/releases) browser if you want to run tests on Linux or Mac OS 

For report generation run 'mvn site' command and open /target/site/allure-maven-plugin/index.html