# QA Engineer Assessment Project

## About

This project is a test automation framework built for a QA Engineer assessment. It automates the verification of a web application's homepage and career pages using **Java**, **Selenium WebDriver**, and **TestNG**, following the **Page Object Model (POM)** design pattern.

**Author:** Yasin Aroglan

---

## Tech Stack

- **Language:** Java 11
- **Test Framework:** TestNG 7.9.0
- **Browser Automation:** Selenium WebDriver 4.18.1
- **Driver Management:** WebDriverManager 5.7.0
- **Build Tool:** Maven
- **Design Pattern:** Page Object Model (POM)
- **Browser:** Chrome (configurable via properties)

---

---

## Test Scenarios

### Test 1: Homepage Verification
Visits the homepage and verifies that all main blocks are loaded:
- Navigation bar is displayed
- Logo is visible
- Hero section with title is present
- Partners logo reel is displayed
- Footer with links is loaded

### Test 2: QA Jobs Filter and Validation
Navigates to the Careers page and filters QA job listings:
- Opens the Careers page and clicks "See all teams"
- Locates the Quality Assurance card and clicks "Open Positions"
- Redirects to Lever job board
- Applies Location filter: "Istanbul, Turkiye"
- Verifies job listings are present after filtering
- Validates Department title is "Quality Assurance"
- Validates all job locations contain "Istanbul"
- Validates all job positions contain "Quality Assurance"

### Test 3: View Role Redirect
Clicks on a job listing and verifies redirection to the Lever Application form page.

---

## Known Issues and Observations

### Position Name Mismatch
The assessment requires verifying that all job positions contain "Quality Assurance". However, one of the current listings is titled **"AI-Driven Quality Engineering: QA Bootcamp'26"**, which does not contain the exact text "Quality Assurance".

This causes `testQAJobsFilterAndValidation` to fail on the position assertion as expected. This is documented as a **requirement vs. reality gap** — the test is written according to the specification, and the mismatch appears to be a data issue rather than a test defect.

**Recommendation:** Clarify with the product team whether the requirement should check for "Quality Assurance" exactly, or if a broader match (e.g., "Quality" or "QA") is acceptable.

### URL Redirect Behavior
The assessment specifies navigating to `/careers/quality-assurance/`, but this URL redirects to `/careers/`. The test adapts to the actual site behavior by navigating to `/careers/`, clicking "See all teams", finding the QA card, and proceeding to the Lever job board.

### CDP Version Warning
Selenium 4.18.1 may show a warning about Chrome DevTools Protocol version mismatch with newer Chrome versions. This does not affect test execution.

---

## Setup and Running

### Prerequisites
- Java 11 or higher
- Maven
- Chrome browser installed

### Installation
```bash
git clone https://github.com/YOUR_USERNAME/yasin_aroglan_case.git
cd yasin_aroglan_case
mvn clean install -DskipTests
```

### Running Tests
```bash
# Run all tests
mvn test

# Run with specific browser
mvn test -Dbrowser=firefox

# Run in headless mode
mvn test -Dheadless=true
```

### Configuration
All settings are managed in `src/test/resources/configuration.properties`:

```properties
browser=chrome
headless=false
base.url=https://insiderone.com
careers.url=https://insiderone.com/careers/
default.timeout=15
```

---

## Design Decisions

### Page Object Model
Each web page is represented by a separate Java class. Locators and page-specific actions are encapsulated within the page class, keeping test classes clean and focused on assertions. This approach ensures that a locator change requires updating only one file.

### BasePage Abstract Class
Common operations (wait, click, scroll, JavaScript execution) are centralized in BasePage. All page classes inherit from it, eliminating code duplication across 4+ page objects.

### DriverFactory with Factory Pattern
Browser creation logic is separated from test setup. Adding support for a new browser requires only a new method in DriverFactory — no changes to BaseTest or any test class.

### ThreadLocal for Parallel Execution
WebDriver instances are stored in ThreadLocal, ensuring each test thread has its own isolated browser. This enables safe parallel test execution without shared state conflicts.

### ConfigReader for Environment Management
Hardcoded values are eliminated. URLs, timeouts, and browser settings are externalized to a properties file. Different environments (prod, staging, local) can be targeted by changing configuration without modifying any Java code.

### Locator Strategy
Locators prioritize stability: `id` > `data-qa` / `aria-label` > CSS Selector > XPath. Dynamic or index-based locators are avoided. Each locator is designed to return exactly one element to prevent flaky tests.
