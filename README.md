# Tests

`Steps to run tests`

Configure Windows to capture audio: Go to Control Panel\Hardware and Sound\, click on sound icon. Now go to recording, Right click -> 
enable "disconnected devices". You will find "Stereo mix" icon. Make it as default device.

Configure Linux to capture audio: TODO

Configure Mac OS to capture audio: TODO

`1. Java 8, maven have to be installed`

`2. Run command "mvn install:install-file -Dfile="src/main/resources/musicg-1.4.2.0.jar" in project directory`

`3. Run command "mvn clean test site -P browser_chrome,grid_no" in project directory`

`Note: Allure report can be opened by link: /target/site/allure-maven-plugin/index.html`

![report](http://i.imgur.com/DUWp6ys.png)