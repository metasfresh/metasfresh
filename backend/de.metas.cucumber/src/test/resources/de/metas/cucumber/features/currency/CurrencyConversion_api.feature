@from:cucumber
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01105_Currency_Conversion
@ghActions:run_on_executor2
@Id:S31298
Feature: Currency-conversion REST API

  The provider-agnostic middleware keeps foreign-exchange rates current via three authenticated v2 endpoints:
  - PUT  /api/v2/currencyconversion/rates       — batch upsert of normalized conversion rates
  - GET  /api/v2/currencyconversion/currencies  — the active currencies to fetch rates for
  - GET  /api/v2/currencyconversion/newestRates — the newest stored rate per (from, to, type) combo

  Scenarios share one executor DB, so each uses a disjoint ValidFrom window (or a distinct conversion type)
  to stay isolated from the rate rows other scenarios create.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-06-01T08:00:00+01:00[Europe/Berlin]
    # The core seed ships these currencies inactive; a currency must be active before a rate for it can be upserted.
    And the following currencies are active:
      | ISO_Code |
      | EUR      |
      | CNY      |
      | JPY      |

  # AC1 batch upsert + per-record outcome, AC3 DivideRate derived, AC4 both directions
  Scenario: Batch upsert of daily EUR-CNY rates stores both directions with derived DivideRate
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      {
        "requestItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.60, "validFrom": "2026-06-01", "conversionTypeCode": "S" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.65, "validFrom": "2026-06-02", "conversionTypeCode": "S" }
        ]
      }
      """
    Then the metasfresh REST-API responds with
      """
      { "responseItems": [ { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "CREATED" },
                           { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "CREATED" } ] }
      """
    # forward rows, with DivideRate = 1/multiply (scale 12, HALF_UP)
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate | DivideRate     | ValidTo    |
      | EUR          | CNY        | S              | 2026-06-01 | 7.60         | 0.131578947368 | 2056-12-31 |
      | EUR          | CNY        | S              | 2026-06-02 | 7.65         | 0.130718954248 | 2056-12-31 |
    # auto-written reciprocal rows (reverse direction)
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate   | ValidTo    |
      | CNY          | EUR        | S              | 2026-06-01 | 0.131578947368 | 2056-12-31 |
      | CNY          | EUR        | S              | 2026-06-02 | 0.130718954248 | 2056-12-31 |

  # AC6 idempotent upsert
  Scenario: Re-posting a changed rate for the same key updates in place, no duplicate row
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      { "requestItems": [ { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.60, "validFrom": "2026-06-03", "conversionTypeCode": "S" } ] }
      """
    Then the metasfresh REST-API responds with
      """
      { "responseItems": [ { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "CREATED" } ] }
      """
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      { "requestItems": [ { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.70, "validFrom": "2026-06-03", "conversionTypeCode": "S" } ] }
      """
    Then the metasfresh REST-API responds with
      """
      { "responseItems": [ { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "UPDATED" } ] }
      """
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate | DivideRate     | ValidTo    |
      | EUR          | CNY        | S              | 2026-06-03 | 7.70         | 0.129870129870 | 2056-12-31 |

  # AC7 ValidTo open, no gap: each upserted rate is stored open (ValidTo = far-future sentinel), so a later
  # date with no own rate is covered by the most recent earlier rate. (The runtime resolution itself is
  # CurrencyBL's responsibility, out of scope for this endpoint feature; here we assert the endpoint stores
  # the rows open, which is the endpoint-observable half of the no-gap behaviour.)
  Scenario: Each upserted rate is stored open-ended so there is no gap between dates
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      {
        "requestItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.55, "validFrom": "2026-06-05", "conversionTypeCode": "S" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.72, "validFrom": "2026-06-08", "conversionTypeCode": "S" }
        ]
      }
      """
    Then the metasfresh REST-API responds with
      """
      { "responseItems": [ { "syncOutcome": "CREATED" }, { "syncOutcome": "CREATED" } ] }
      """
    # both rates are stored open (ValidTo = 2056-12-31), so 2026-06-05's rate covers the following days with no own rate
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate | ValidTo    |
      | EUR          | CNY        | S              | 2026-06-05 | 7.55         | 2056-12-31 |
      | EUR          | CNY        | S              | 2026-06-08 | 7.72         | 2056-12-31 |

  # AC2 unknown/inactive currency -> per-record error, AC11 friendly message
  Scenario: An unknown currency fails only that record; valid records still applied; no currency created
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      {
        "requestItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.60, "validFrom": "2026-06-09", "conversionTypeCode": "S" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "XXX", "multiplyRate": 1.10, "validFrom": "2026-06-09", "conversionTypeCode": "S" }
        ]
      }
      """
    Then the metasfresh REST-API responds with
      """
      {
        "responseItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "CREATED" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "XXX", "syncOutcome": "ERROR", "error": { "userFriendlyError": true } }
        ]
      }
      """
    # valid record applied ...
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate | ValidTo    |
      | EUR          | CNY        | S              | 2026-06-09 | 7.60         | 2056-12-31 |
    # ... and no currency row was auto-created for the unknown ISO
    And the following currencies do not exist:
      | ISO_Code |
      | XXX      |

  # AC5 conversion type default vs explicit; unknown code -> per-record error
  Scenario: Omitted conversion type uses the org default; explicit type is honored; unknown code errors
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      {
        "requestItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.60, "validFrom": "2026-06-10" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "JPY", "multiplyRate": 160.0, "validFrom": "2026-06-10", "conversionTypeCode": "P" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.60, "validFrom": "2026-06-10", "conversionTypeCode": "Z" }
        ]
      }
      """
    Then the metasfresh REST-API responds with
      """
      {
        "responseItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "CREATED" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "JPY", "syncOutcome": "CREATED" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "ERROR" }
        ]
      }
      """
    # omitted type -> org default (Spot / S)
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate | ValidTo    |
      | EUR          | CNY        | S              | 2026-06-10 | 7.60         | 2056-12-31 |
    # explicit type P honored on its own row
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate | ValidTo    |
      | EUR          | JPY        | P              | 2026-06-10 | 160.0        | 2056-12-31 |

  # AC9 interceptor invariants via the API, AC11 friendly message; no row written
  Scenario: Invariant-violating records each fail with a per-record error and write no row
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      {
        "requestItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "EUR", "multiplyRate": 1.00, "validFrom": "2026-06-11", "conversionTypeCode": "S" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 0,    "validFrom": "2026-06-11", "conversionTypeCode": "S" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.60, "validFrom": "2026-06-18", "validTo": "2026-06-11", "conversionTypeCode": "S" }
        ]
      }
      """
    Then the metasfresh REST-API responds with
      """
      {
        "responseItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "EUR", "syncOutcome": "ERROR", "error": { "userFriendlyError": true } },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "ERROR", "error": { "userFriendlyError": true } },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "syncOutcome": "ERROR", "error": { "userFriendlyError": true } }
        ]
      }
      """
    And no C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  |
      | EUR          | CNY        | S              | 2026-06-11 |
      | EUR          | CNY        | S              | 2026-06-18 |

  # AC10 auth: each of the three endpoints returns 401 without credentials
  Scenario: The three endpoints reject unauthenticated callers
    When a 'PUT' request without authentication is sent to metasfresh REST-API 'api/v2/currencyconversion/rates' expecting status '401'
    When a 'GET' request without authentication is sent to metasfresh REST-API 'api/v2/currencyconversion/currencies' expecting status '401'
    When a 'GET' request without authentication is sent to metasfresh REST-API 'api/v2/currencyconversion/newestRates' expecting status '401'

  # AC4 reverse-map: forward-only auto-creates the reciprocal; a supplied reverse is kept untouched
  Scenario: A forward-only rate auto-creates its reciprocal; a caller-supplied reverse is honored as-is
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      {
        "requestItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.60,  "validFrom": "2026-06-12", "conversionTypeCode": "S" },
          { "fromCurrencyCode": "CNY", "toCurrencyCode": "EUR", "multiplyRate": 0.140,  "validFrom": "2026-06-12", "conversionTypeCode": "S" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "JPY", "multiplyRate": 160.0,  "validFrom": "2026-06-12", "conversionTypeCode": "S" }
        ]
      }
      """
    Then the metasfresh REST-API responds with
      """
      { "responseItems": [ { "syncOutcome": "CREATED" }, { "syncOutcome": "CREATED" }, { "syncOutcome": "CREATED" } ] }
      """
    # caller supplied both EUR->CNY and CNY->EUR: the supplied reverse (0.140) is kept, NOT overwritten by 1/7.60
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate | ValidTo    |
      | CNY          | EUR        | S              | 2026-06-12 | 0.140        | 2056-12-31 |
    # EUR->JPY forward-only: the reciprocal JPY->EUR is auto-created as 1/160
    And this C_Conversion_Rate exists:
      | FromCurrency | ToCurrency | ConversionType | ValidFrom  | MultiplyRate   | ValidTo    |
      | JPY          | EUR        | S              | 2026-06-12 | 0.006250000000 | 2056-12-31 |

  # AC12 GET currencies = active set (code + name), excludes inactive
  Scenario: GET currencies returns the active currencies and excludes an inactive one
    Given the following currencies are inactive:
      | ISO_Code |
      | RUB      |
    When store REST endpointPath 'api/v2/currencyconversion/currencies'
    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then the currencies response contains 'CNY'
    And the currencies response does not contain 'RUB'

  # AC13 GET newestRates = newest per combo; optional filter narrows the result
  # Uses conversion type A (Average) so the (EUR,CNY,A) combo is unique to this scenario, isolated from
  # the S-type rows other scenarios create.
  Scenario: GET newestRates returns only the newest row per combo and honors a filter
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/currencyconversion/rates' and fulfills with '200' status code
      """
      {
        "requestItems": [
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.60, "validFrom": "2026-06-13", "conversionTypeCode": "A" },
          { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "multiplyRate": 7.70, "validFrom": "2026-06-14", "conversionTypeCode": "A" }
        ]
      }
      """
    Then the metasfresh REST-API responds with
      """
      { "responseItems": [ { "syncOutcome": "CREATED" }, { "syncOutcome": "CREATED" } ] }
      """
    # filter to EUR->CNY type A: only the newest (2026-06-14, 7.70) row is returned, not the 2026-06-13 one
    When store REST endpointPath 'api/v2/currencyconversion/newestRates?fromCurrencyCode=EUR&toCurrencyCode=CNY&conversionTypeCode=A'
    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code
    Then the metasfresh REST-API responds with
      """
      { "rates": [ { "fromCurrencyCode": "EUR", "toCurrencyCode": "CNY", "conversionTypeCode": "A", "validFrom": "2026-06-14", "multiplyRate": 7.7 } ] }
      """
    And the newestRates response has 1 rate
