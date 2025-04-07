# Bug Report - Random Date Generator

## 1. Leap Year Handling Incorrect

- **Test:** `testLeapYear()`
- **Input:** Start = End = `2020-02-29`
- **Expected:** `2020-02-29`
- **Actual:** `2020-02-28`
- **Severity:** High
- **Impact:** Invalid date may be generated on valid leap year input
- **Suggestion:** Ensure exact match on leap year boundaries when start = end

---

## 2. End Date Before Start Not Validated

- **Test:** `testEndDateBeforeStart()`
- **Input:** Start = `2020-02-29`, End = `2019-02-02`
- **Expected:** Error or validation message
- **Actual:** Generator produces a date anyway
- **Severity:** High
- **Impact:** Misleads user with logically invalid input
- **Suggestion:** Add client-side and/or server-side validation to disallow end < start

