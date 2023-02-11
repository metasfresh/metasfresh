@from:cucumber
Feature:Create conversion rates via metasfresh api

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: Create conversion rate for existing currencies

    Given load C_Currency:
      | C_Currency_ID.Identifier | ISO_Code |
      | currencyFrom             | EUR      |
      | currencyTo_1             | USD      |
      | currencyTo_2             | CHF      |
    And load C_ConversionType:
      | C_ConversionType_ID.Identifier | Value |
      | conversionType                 | C     |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/currency/rates' and fulfills with '200' status code
    """
{
   "currencyCodeFrom": "EUR",
   "orgCode": "001",
   "requestItems":[
      {
        "currencyCodeTo": "USD",
        "currencyCodeToSet": true,
        "conversionType": "Company",
        "conversionTypeSet": true,
        "validFrom": "2023-02-20",
        "validFromSet": true,
        "validTo": "2023-05-20",
        "validToSet": true,
        "divideRate": "1.6",
        "divideRateSet": true
      },
       {
        "currencyCodeTo": "CHF",
        "currencyCodeToSet": true,
        "conversionType": "Company",
        "conversionTypeSet": true,
        "validFrom": "2023-05-15",
        "validFromSet": true,
        "validTo": "2023-07-20",
        "validToSet": true,
        "divideRate": "0.5",
        "divideRateSet": true
      }
   ]
}
   """

    Then load created C_Conversion_Rate items:
      | C_Conversion_Rate_ID.Identifier |
      | conversionRate_1                |
      | conversionRate_2                |

    And validate created C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | C_Currency_ID.Identifier | C_Currency_ID_To.Identifier | C_ConversionType_ID.Identifier | DivideRate | ValidFrom  |
      | conversionRate_1                | currencyFrom             | currencyTo_1                | conversionType                 | 1.6        | 2023-02-20 |
      | conversionRate_2                | currencyFrom             | currencyTo_2                | conversionType                 | 0.5        | 2023-05-15 |
