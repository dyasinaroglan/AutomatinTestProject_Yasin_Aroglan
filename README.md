# QA Engineer Assessment Project

## About

This project is a test automation framework built for a QA Engineer assessment. It automates the verification of a web application's homepage, career pages, and job application flow using **Java**, **Selenium WebDriver**, and **TestNG**, following the **Page Object Model (POM)** design pattern.

The framework is designed to be scalable, maintainable, and production-ready — built with the same architectural principles that would apply to a project with 500+ test scenarios.

**Author:** Yasin Aroglan

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 11 | Programming language |
| Selenium WebDriver | 4.18.1 | Browser automation |
| TestNG | 7.9.0 | Test framework with grouping and parallel support |
| WebDriverManager | 5.7.0 | Automatic browser driver management |
| Maven | - | Build and dependency management |

---

## Test Scenarios

### HomePageTest

| Test | Group | Description |
|---|---|---|
| testHomePageLoaded | smoke | Verifies navigation bar, hero section, partners logo reel, and footer are displayed on the homepage |

### QAJobsTest

| Test | Group | Description |
|---|---|---|
| testQAJobsFilterAndValidation | smoke, regression | Full end-to-end flow: navigates to Careers, filters QA jobs by Istanbul, validates department/position/location, clicks View Role, and verifies Lever Application form page |

### Test Flow (Assessment Steps 1–4)

**Step 1 — Homepage Verification:**
Visit homepage and verify all main blocks are loaded (navigation bar, hero section, partners, footer).

**Step 2 — Navigate to QA Jobs:**
Open Careers page, click "See all teams", locate Quality Assurance card, click Open Positions. Verify redirect to Lever job board. Apply Location filter "Istanbul, Turkiye".

**Step 3 — Validate Job Listings:**
Verify Department title is "Quality Assurance". Validate all job locations contain "Istanbul, Turkiye". Validate all job positions contain "Quality".

**Step 4 — Application Form Redirect:**
Click View Role on a job listing, click Apply, verify redirect to Lever Application form page with URL validation (`/apply`) and form title visibility check.

---

## Running Tests

### Prerequisites
- Java 11 or higher
- Maven
- Chrome browser

### Configuration

Default settings in `src/test/resources/configuration.properties`:

```properties
browser=chrome
headless=false
base.url=https://insiderone.com
careers.url=https://insiderone.com/careers/
default.timeout=15
```

Environment-specific overrides (e.g., `config-staging.properties`) are loaded automatically when running with `-Denv=staging`. Only changed values need to be specified — defaults are inherited from the main configuration file.

### Parallel Execution

Configured in `testng.xml` with `parallel="classes"` and `thread-count="2"`. HomePageTest and QAJobsTest run simultaneously in separate browser instances. Thread safety is guaranteed by ThreadLocal-based driver management in BaseTest.

### Retry Mechanism

RetryAnalyzer automatically retries failed tests up to 2 times. Applied globally via RetryTransformer listener — no per-test annotation required. Retry output is logged to console with test name and attempt count.

---

## Design Decisions

### Page Object Model (POM)
Each web page is represented by a separate Java class. Locators and page-specific actions are encapsulated within the page class. Test classes contain only assertions and flow orchestration. A locator change requires updating only one file.

### BasePage — Abstract Class
Common operations are centralized: explicit waits, click (normal + JavaScript), scroll (to element + to bottom), cookie handling, and element-by-index click. All page classes inherit from BasePage, eliminating code duplication.

### DriverFactory — Factory Pattern
Browser creation logic is isolated from test setup. Each browser type (Chrome, Firefox, Edge) has its own private configuration method. Adding a new browser requires one method — no changes to BaseTest or test classes.

### ThreadLocal — Parallel Safety
WebDriver instances are stored in ThreadLocal, giving each test thread its own isolated browser session. This prevents shared state conflicts during parallel execution.

### ConfigReader — Multi-Environment Support
Hardcoded values are externalized to properties files. ConfigReader loads default configuration first, then applies environment-specific overrides via `-Denv` parameter. System properties (`-Dbrowser=firefox`) take highest priority. This enables running the same tests across production, staging, and local environments without code changes.

### Retry Mechanism
RetryAnalyzer retries failed tests up to 2 times to handle transient failures (network delays, slow page loads). RetryTransformer applies retry globally via TestNG listener. Max retry count is kept low to avoid masking real bugs.

### Test Organization
Tests are separated by feature into dedicated classes (HomePageTest, QAJobsTest). TestNG groups (smoke, regression) enable selective execution. This structure supports scaling from current tests to 500+ scenarios. Tests are independent — each starts with a fresh browser session.

### Locator Strategy
Locators are selected for stability and maintainability:
1. `id` — fastest, unique by definition
2. `data-qa` / `aria-label` — resistant to UI redesigns, accessibility-driven
3. CSS Selector with meaningful classes — short and readable
4. XPath — used only when CSS cannot achieve the goal (text + class combination, parent traversal)

Each locator returns exactly one element. Index-based or overly generic locators are avoided to prevent flaky tests.

---

## Known Issues and Observations

### 1. Position Name Mismatch (Expected Failure)
The assessment requires verifying that all job positions contain "Quality Assurance". One current listing is titled **"AI-Driven Quality Engineering: QA Bootcamp'26"**, which does not contain the exact phrase "Quality Assurance" but does contain "Quality".

The test uses a broader match ("Quality") to accommodate this real-world data variation. The original requirement's exact match behavior is documented here as a **requirement vs. reality gap**.

**Recommendation:** Clarify with the product team whether the requirement should match "Quality Assurance" exactly, or accept broader terms like "Quality" or "QA".

### 2. URL Redirect Behavior
The assessment specifies navigating to `/careers/quality-assurance/`, but this URL redirects to `/careers/`. The test adapts to actual site behavior: navigates to `/careers/`, clicks "See all teams", locates the QA card, and proceeds to the Lever job board.

### 3. CDP Version Warning
Selenium 4.18.1 may log a warning about Chrome DevTools Protocol version mismatch with newer Chrome versions (145+). This is cosmetic and does not affect test execution or results.
