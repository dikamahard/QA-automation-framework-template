# Base Automation Template — Web & Mobile

Reusable automation framework template for starting new **Web (Selenium)** and **Mobile (Appium)** automation projects.

Use this repository as the base when initiating a new automation project, then modify the project-specific configurations as described below.

Created By : 

Rizky Mahardika - 12/08/2026

---

## File Structure

### `src/main/java/helper/`

Contains reusable helper utilities that are independent of specific test cases.

Examples:

* Excel utilities
* String utilities
* Date utilities
* Common reusable functions

---

### `src/main/java/pages/`

Contains Page Object classes for Web and Mobile pages.

```text
pages/
├── base/
└── ...
```

`pages/base/` contains reusable page components and methods shared across multiple pages.

---

### `src/test/java/appiumTest/`

Contains test classes for **Mobile Automation** using Appium.

---

### `src/test/java/seleniumTest/`

Contains test classes for **Web Automation** using Selenium.

---

### `src/test/java/flow/`

Contains reusable business or test flows used by test classes.

Examples:

```text
LoginFlow
RegistrationFlow
TransactionFlow
```

Use flows when multiple tests need to execute the same sequence of actions.

---

### `src/test/java/base/`

Contains base test classes responsible for the test lifecycle and test setup.

Responsibilities may include:

* Driver setup
* Driver teardown
* Mobile application configuration
* Mobile device configuration
* Test environment setup
* Test Case path
* Issue Log path

---

### `src/test/java/factory/`

Contains factory classes responsible for creating drivers.

Examples:

```text
AndroidDriverFactory
WebDriverFactory
```

Driver creation logic should be kept here instead of directly inside test classes.

---

### `src/test/java/helper/`

Contains test-specific helpers that should not be part of the reusable main framework.

Examples:

* TLS bypass
* Database access
* Test environment utilities

---

### `src/test/resources/`

Contains project test data resources.

Examples:

* Test data
* Excel sheet mapping
* JSON files
* Other test-specific configuration files

---

### `testcase/template/`

Contains the original Excel templates used by the automation framework.

```text
testcase/template/
├── TestCase.xlsx
└── IssueLog.xlsx
```

These files act as the source templates and should not be modified by test execution.

---

### `testcase/generated/`

Contains test results generated during automation execution.

The framework should generate or copy output files from the templates into this directory.

Example:

```text
testcase/generated/
├── TestCase_Result.xlsx
└── IssueLog_Result.xlsx
```

---

### `testNGSuites/`

Contains TestNG suite configuration.

```text
testNGSuites/
└── testng.xml
```

`testng.xml` defines which tests are executed locally or through the Jenkins pipeline.

---

### `.env`

Contains environment-specific and sensitive project configuration.

Examples:

```text
URL=
PORT=
DEVICE_UDID=
USERNAME=
PASSWORD=
```

> `.env` must not be committed to Git.

Make sure `.env` is included in `.gitignore`.

---

### `.gitignore`

Defines files and directories that should not be committed to Git.

Typical ignored files include:

```text
.env
target/
testcase/generated/
.idea/
*.log
```

Adjust according to the project requirements.

---

### `Jenkinsfile`

Contains the Jenkins CI/CD pipeline configuration.

Separate or adjust the pipeline according to whether the project uses:

* Web Automation
* Mobile Automation

---

### `pom.xml`

Maven project configuration.

Contains:

* Project information
* Artifact ID
* Dependencies
* Plugins
* Build configuration

---

# Project Setup

After creating a new automation project from this template, update the following configurations.

## 1. `Jenkinsfile`

Use the appropriate Jenkins pipeline configuration for **Web** or **Mobile** automation.

Update:

* Project-related data
* Email recipients
* Tag name
* Email subject
* Test name in the Test stage
* Test Case Excel filename
* Other project-specific pipeline variables

Make sure the Test stage references the correct TestNG suite.

---

## 2. `pom.xml`

Update the project artifact ID.

Example:

```xml
<artifactId>your-project-name</artifactId>
```

Only add additional dependencies when required by the project.

---

## 3. `testcase/template/`

Replace the default Excel templates with the project's:

* Test Case
* Issue Log

Keep the required Excel structure, sheet names, columns, and mapping used by the framework.

---

## 4. `BaseTest.java`

Configure project-specific test setup.

Depending on the project, update:

### Mobile

* Mobile device
* Mobile application
* Appium URL
* Appium port
* Test Case path
* Issue Log path

### Web

* Web URL
* Test Case path
* Issue Log path

Environment-specific values such as URL, port, UDID, username, and password should be retrieved from `.env` instead of being hardcoded.

---

## 5. `testNGSuites/testng.xml`

Define the tests that should be executed.

Example:

```xml
<suite name="Automation Suite">

    <test name="Sample Test">
        <classes>
            <class name="seleniumTest.{SampleTest}"/>
        </classes>
    </test>

</suite>
```

Update:

* Suite name
* Test name
* Test classes

The test name configured here must match the test name executed by the Jenkins Test stage.

---

# Setup Checklist

Before running a newly created project:

* [ ] Select the correct Jenkins configuration for Web or Mobile
* [ ] Update Jenkins project information
* [ ] Update Jenkins email recipients
* [ ] Update Jenkins tag name
* [ ] Update Jenkins email subject
* [ ] Update Jenkins Test stage
* [ ] Update Jenkins Excel filename
* [ ] Update `pom.xml` artifact ID
* [ ] Replace Test Case template
* [ ] Replace Issue Log template
* [ ] Configure `.env`
* [ ] Confirm `.env` is ignored by Git
* [ ] Configure `BaseTest.java`
* [ ] Configure mobile device and application if required
* [ ] Configure Web URL/browser if required
* [ ] Configure Test Case and Issue Log paths
* [ ] Configure `testng.xml`
* [ ] Run the TestNG suite locally
* [ ] Verify generated Excel results
* [ ] Verify Jenkins pipeline execution

---

# Recommended Project Flow

```text
Clone Base Automation Template
            ↓
Configure Project
            ↓
Update pom.xml
            ↓
Configure .env
            ↓
Replace Test Case & Issue Log Templates
            ↓
Configure BaseTest
            ↓
Configure TestNG Suite
            ↓
Run Locally
            ↓
Verify Generated Results
            ↓
Configure & Run Jenkins Pipeline
```

---

## Important

This repository is a **base framework**, not a project that should be copied without modification.

Keep reusable framework components generic and avoid introducing project-specific logic into shared utilities whenever possible.

Project-specific configuration should primarily stay in:

```text
.env
BaseTest.java
testNGSuites/
testcase/template/
Jenkinsfile
```

This keeps the framework reusable and reduces the amount of code that needs to be changed when starting a new automation project.
