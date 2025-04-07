# Test Report - Random Date Generator

This document summarizes test cases executed against the [Random Date Generator](https://codebeautify.org/generate-random-date) using Selenium and JUnit.

##  Test Environment

| Component        | Value                 |
|------------------|-----------------------|
| Language         | Java 21               |
| Framework        | JUnit 5               |
| Automation Tool  | Selenium WebDriver 4.19.1 |
| Browser          | ChromeDriver (Headless v134) |
| OS               | Linux/Windows/Mac     |

---

## Test Cases

### 1. testValidDateRange()
- **Input:** start = `2020-01-01`, end = `2099-12-31`
- **Expected:** Generated date is within range
- **Status:**  Pass

---

### 2. testLeapYear()
- **Input:** start = end = `2020-02-29`
- **Expected:** Output is exactly `2020-02-29`
- **Actual:** Output was `2020-02-28`
- **Status:**  Fail

---

### 3. testEndDateBeforeStart()
- **Input:** start = `2020-02-29`, end = `2019-02-02`
- **Expected:** Error/validation message, no date generated
- **Actual:** Date still generated
- **Status:**  Fail

