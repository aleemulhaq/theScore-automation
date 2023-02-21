# theScore QA Automation Challenge Feb 2023
This repository hosts the project solution to theScore's QA Automation (Mobile) challenge.

Author | Aleem ul Haq
--- | ---
Date Completed| Feb 21, 2023


## Challenge requirements
Write an automated test that finds a league, team or a player within theScore app.

Automate the following steps:
1. Open a team page
2. Verify the expected page opens correctly
3. Tap on the team's stats sub-tab of the page opened from step 1
4. Verify the correct tab opens and data is displayed as expected
5. Verify back navigation returns you to the previous page correctly

## Prerequisites for running this project
Please make you satisfy the following requirements before building the project

**Environment** requirements and dependencies:
- JVM
- Android sdk tools, Android home and platform tools path should be set
- min Node version >= 12

Project level dependencies like below will install through gradle automatically 
- Latest Appium 2.0 and its java client 8.3.0
- Gson parsing lib 2.10
- Junit Parameterized lib 5.6.0
- etc..

## Android device requirements:
Currently we are able to run the project against two kinds of devices:

#### Real device android phones
- Hardware android device connection to computer. MAKE SURE developer mode is enabled, and has settings accessible to webdriver
- If you have multiple hardware devices attached, adb will pick one.
- If you want to run tests on a specific hardware device, please make sure it is the only hardware Android device plugged in to the computer.

#### Android Emulators
- Only support Pixel_6_API_33 specifically at the moment. This is done on purpose so we always have an emulator back up option that boots up.
- Make sure you have Pixel_6_API_33 virtual device downloaded through Android sdk tools or Android studio device manager
##### Note about connected devices:
- The only virtual device that will run the tests is "Pixel_6_API_33", make sure the AVD name matches that
- If a hardware device is connected, it will always be prioritized for testing



## Setup & Running tests
To build the project and executes tests, you need to run the following command in the project path.
e.g:
- Set file path to access my project directory `cd.../../user/../../theScore-automation`,
- then run `gradlew clean test`
- After each successful test run, the sessions logs are saved in the `projDir/logs/` directory

## Test implementation
####### Launch app and go through onboarding to Favorites page
- Install theScore apk on an Android device and Launch app
- Navigate through **Onboarding** to get to the **Favorites** page

####### Get team profle data from theScore api
- Make a HTTP request to theScore api and grab the current team's profile api response. (Note, we are using JUNIT parameterized approach and will test several teams from different sports.)
- Parse the team profile api response and extract team full name, and team-stats from all possible seasons in the API Endpoint. (This is especially important because some soccer teams have multiple season stats and we scroll and verify through all that data)

####### Search for the team name using api data, navigate to team stats page
- We use the api response Full Team Name and search and open the team page
- Verify api data and UI elements and text match and are compatible
- Go to team stats page

####### Verify team stats page showing exactly the same data as API provides
- We first check if team stats tab is selected correctly
- Then verify all the data titles and values against the API
- Scroll team stats page, and verify all possible stats. Teams like soccer have multiple scrolls of Stats

####### Navigate back once, back to the Search view page
- When we navigate back once, we get to Search Page
- We verify search bar shows the cached text from our previous search result
- Navigate back again to Favorites page to support next parameterized run

####### Repeat steps for each parameter (Different league/sport's team)
- Once tests are concluded, we save test session logs to the `/logs` dir



## Rationale behind test approach
todo

## coverage assessment of feature
todo


## automation framework structure
page object model,
capabilities and variable driver (android + ios)
rigid tests, only need to update page models if ui changes or for ios
abstract classes
logger
screenshots of failure
multiple device? variable device? hardware device?


todo