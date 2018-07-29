# Code Challenge - Upcoming Movies Mobile App

Upcoming Movies Mobile App is an app for listing the upcoming movies from ​The Movie Database (TMDb)​.

## Upcoming Movies Mobile App

<img src="https://i.imgur.com/t8a9lQS.jpg" height="620">
<img src="https://i.imgur.com/1WQ4cNR.jpg" height="620">

## Getting started

Requirements:

- Java

You need to have Java 8 installed. Run `Java -version` to check it. If Java not installed or it's version is older, please check [here](https://java.com/en/download/) to install.

- Android Studio

You need to have Android Studio 3.0 or newer installed. If it's not installed, please, check [here](https://developer.android.com/studio/index.html?hl=en) to install the newest version.

### Clone the project
```
git clone git@github.com:Maychell/upcoming_movies_mobile.git
```

### Import the project

Open Android Studio and then go through the following steps:

 - File
 - Open...
 - Select the path where you have just cloned from the repository and click `OK`

If needed, accept and download the Android SDK updates in Android Studio.

### Add environment variables to `gradle.properties` file

On the root path, create the `gradle.properties` file with the following variables - replace the `***` with your environment url and your api key:

```
tmdb_api_key=***
tmdb_api_url=***
```

## Running the project

There are two ways to run the project: into your Android smartphone or into the emulator:

- Running on Emulator:

 - Create an Android Virtual Device (AVD) following [this steps](https://developer.android.com/studio/run/managing-avds.html)
 - Start your AVD that you just created
 - Click Run <img src="https://developer.android.com/studio/images/buttons/toolbar-run.png" height="16" width="16">
 Select your AVD and click OK.

- Running on a Hardware Device

 - On the device, open the `Settings` app, select `Developer options`, and then enable `USB debugging`.
 > Note: If you do not see `Developer options`, follow the instructions to [enable developer options](https://developer.android.com/studio/debug/dev-options.html).
 - Connect your device to the computer through USB
 - Click Run <img src="https://developer.android.com/studio/images/buttons/toolbar-run.png" height="16" width="16">
 Select your hardware device and click OK.

## How do we organize the source code

The code is is organized in the following structure:

```
|-- manifests/
|-- java /
|---- com.archtouch.codechallenge /
|------ api /
|------ app /
|------ data /
|------ model /
|------ util /
|------ view /
|-- res /
|---- drawable /
|---- layout /
|---- mipmap /
|---- values /
```

- `manifests`: contains the AndroidManifest.xml file, that is the the main project file, where we can find all the project's configs;
- `java/com.archtouch.codechallenge/api`: holds the Tmdb api interfaces;
- `java/com.archtouch.codechallenge/app`: holds the general app's states and constants that are used on the project;
- `java/com.archtouch.codechallenge/data`: holds the cached data;
- `java/com.archtouch.codechallenge/model`: wraps the model object classes;
- `java/com.archtouch.codechallenge/util`: wraps all the util code related, such as validators methods, ui masks, image utils, network utils, etc.;
- `java/com.archtouch.codechallenge/view`: holds all the views organized by package. Each package contains a controller, a `dagger` folder which contains the DI files - when needed -, and a presenter. Since this project follows MVP structure, please, do NOT keep any logic in the controller files;
- `res/drawable`: holds all the non-icon images for the project and custom components (button, circle view, etc.);
- `res/layout`: wraps all the xml views of the project;
- `res/mipmap`: wraps the icons of the project;
- `res/values`: holds the custom styles and translations for the project;

## Unit testing

We are using [JUnit](http://junit.org/junit4/), [Mockito](http://site.mockito.org) and [PowerMockito](https://github.com/powermock/powermock/wiki) as our unit test library. Create a test file with the same name as the file to be tested with the suffix `Test.java`, and place it into `java/com.archtouch.codechallenge (test)` folder at the same level as your file to be tested. e.g:

```
|-- java /
|---- com.archtouch.codechallenge /
|------ view /
|-------- home /
|---------- HomePresenter.java
|------ util /
|------ model /
```

Running test:

On the Project toolbar, right click on the file (to run a single test file) or on the folder (to run all the tests in the folder) and then click `Run 'FilenameThatShouldBeTested'`.

## Instrumented testing

We are using [Espresso](https://developer.android.com/training/testing/espresso/index.html) as our instrumented test library. Create a test file with the same name as the file to be tested with the suffix `Test.java`, and place it into `java/com.archtouch.codechallenge (androidTest)` folder at the same level as your file to be tested. e.g:

```
|-- java /
|---- com.archtouch.codechallenge /
|------ view /
|-------- home /
|---------- HomeActivity.java
```

Running test:

On the Project toolbar, right click on the file (to run a single test file) or on the folder (to run all the tests in the folder) and then click `Run 'FilenameThatShouldBeTested'`.

## Style Guide

We're following the following style guides:
 - [Ribot guidelines](https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md)
 - [Java Code Style guidelines](https://source.android.com/source/code-style)
