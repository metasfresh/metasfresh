@from:cucumber
Feature: create payments using payment API

  Background:

    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-12-21T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  @Id:S0249_100
  Scenario: Create INBOUND payment using the payment API
  - fully allocate invoice's open amount with one JsonInvoicePaymentCreateRequest#line (no discount, no write off)

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_02022023_1 | paymentAllocPricingSystem_02022023_1 | paymentAllocPricingSystem_02022023_1 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_02022023_1 | paymentAllocPricingSystem_02022023_1 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_02022023_1 | paymentAllocPriceList_02022023_1 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_02022023_1 | BPartnerTest_02022023_1 | BPartnerTest_02022023_1 | Y              | paymentAllocPricingSystem_02022023_1 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_02022023_1 | bpartner_02022023_1      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_02022023_1     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_02022023_1     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | inboundDocType          | ARR             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_02022023_1 | product_02022023_1 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_02022023_1 | paymentAllocPLV_02022023_1        | product_02022023_1      | 2.00     | PCE               | Normal                        |

#    GrandTotal = 23.8
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_1 | bpartner_02022023_1      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | invoiceExtId_02022023_1 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_1 | inv_02022023_1          | product_02022023_1      | 10          | PCE               |
    And the invoice identified by inv_02022023_1 is completed

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_02022023_1",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_02022023_1",
    "transactionDate": "2022-05-11",
    "type": "INBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_1",
            "amount": 23.8
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_02022023_1      | paymentExtId_02022023_1 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_02022023_1      | true                     | 0.00        | 23.8       | 0               | 0               | 2022-05-11  | bpartner_02022023_1          | bp_bank_account_02022023_1         | inboundDocType   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_02022023_1              | payment_02022023_1          | 23.8       | 0                | 0               | 0               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_02022023_1          | bpartner_02022023_1      | bpartner_location_02022023_1      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_200
  Scenario: Create INBOUND payment using the payment API
  - allocate a portion of the invoice's open amt with a first request
  - allocate remaining open amt with a second request (with discount and write off)

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_03022023_3 | paymentAllocPricingSystem_03022023_3 | paymentAllocPricingSystem_03022023_3 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_03022023_3 | paymentAllocPricingSystem_03022023_3 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_03022023_3 | paymentAllocPriceList_03022023_3 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_03022023_3 | BPartnerTest_03022023_3 | BPartnerTest_03022023_3 | Y              | paymentAllocPricingSystem_03022023_3 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_03022023_3 | bpartner_03022023_3      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_03022023_3     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_03022023_3     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | inboundDocType          | ARR             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_03022023_3 | product_03022023_3 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_03022023_3 | paymentAllocPLV_03022023_3        | product_03022023_3      | 2.00     | PCE               | Normal                        |

#    GrandTotal = 23.8
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_03022023_3 | bpartner_03022023_3      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | invoiceExtId_03022023_3 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_03022023_3 | inv_03022023_3          | product_03022023_3      | 10          | PCE               |
    And the invoice identified by inv_03022023_3 is completed

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_03022023_3",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_03022023_3",
    "transactionDate": "2022-05-11",
    "type": "INBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_03022023_3",
            "amount": 10
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_03022023_3      | paymentExtId_03022023_3 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_03022023_3      | true                     | 0.00        | 10         | 0               | 0               | 2022-05-11  | bpartner_03022023_3          | bp_bank_account_03022023_3         | inboundDocType   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_03022023_3              | payment_03022023_3          | 10         | 13.8             | 0               | 0               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_03022023_3          | bpartner_03022023_3      | bpartner_location_03022023_3      | 10 Tage 1 % | true      | CO        | false      |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_03022023_3",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_03022023_4",
    "transactionDate": "2022-05-11",
    "type": "INBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_03022023_3",
            "amount": 11.8,
            "discountAmt": 1,
            "writeOffAmt": 1
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_03022023_4      | paymentExtId_03022023_4 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_03022023_4      | true                     | 0.00        | 11.8       | 1               | 1               | 2022-05-11  | bpartner_03022023_3          | bp_bank_account_03022023_3         | inboundDocType   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_03022023_3              | payment_03022023_4          | 11.8       | 0                | 1               | 1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_03022023_3          | bpartner_03022023_3      | bpartner_location_03022023_3      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_300
  Scenario: Create INBOUND payment using the payment API
  - fully allocate invoice's open amount with one JsonInvoicePaymentCreateRequest#line (no discount, no write off)
  - use different currency and transaction date in the past

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_03022023_1 | paymentAllocPricingSystem_03022023_1 | paymentAllocPricingSystem_03022023_1 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_03022023_1 | paymentAllocPricingSystem_03022023_1 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_03022023_1 | paymentAllocPriceList_03022023_1 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_03022023_1 | BPartnerTest_03022023_1 | BPartnerTest_03022023_1 | Y              | paymentAllocPricingSystem_03022023_1 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_03022023_1 | bpartner_03022023_1      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_03022023_1     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_03022023_1     | CHF                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | inboundDocType          | ARR             |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

#   Load conversion rates between CHF and EUR filtering them by both validFrom and validTo dates
#   When multiple rates are valid at the same time (i.e. matches a date), then they are ordered in a descending order based on validFrom and the rate with the most recent validFrom is used
    And load C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | C_Currency_ID.Identifier | C_Currency_ID_To.Identifier | ValidFrom  | ValidTo    |
      | currency_rate_1                 | chf                      | eur                         | 2015-05-21 | 2155-12-31 |
      | currency_rate_2                 | chf                      | eur                         | 2019-05-21 | 2056-12-31 |

    And validate C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | OPT.MultiplyRate | OPT.DivideRate |
      | currency_rate_1                 | 0.95672173557    | 1.045236       |
      | currency_rate_2                 | 0.884955752212   | 1.13           |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_03022023_1 | product_03022023_1 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_03022023_1 | paymentAllocPLV_03022023_1        | product_03022023_1      | 2.00     | PCE               | Normal                        |

#    GrandTotal = 23.8
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_03022023_1 | bpartner_03022023_1      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | invoiceExtId_03022023_1 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_03022023_1 | inv_03022023_1          | product_03022023_1      | 10          | PCE               |
    And the invoice identified by inv_03022023_1 is completed

#    Use "2016-05-21" as transactionDate, so that the rate with identifier "currency_rate_1" is used for conversion between CHF and EUR
#    "2016-05-21" is in the range [2015-05-21 - 2155-12-31] (currency_rate_1) and not in the range [2019-05-21 - 2056-12-31] (currency_rate_2)
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_03022023_1",
    "currencyCode": "CHF",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_03022023_1",
    "transactionDate": "2016-05-21",
    "type": "INBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_03022023_1",
            "amount": 24.88
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_03022023_1      | paymentExtId_03022023_1 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_03022023_1      | false                    | 24.88      | 0               | 0               | 2016-05-21  | bpartner_03022023_1          | bp_bank_account_03022023_1         | inboundDocType   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_03022023_1              | payment_03022023_1          | 23.8       | 0                | 0               | 0               |

#    Invoice is fully paid because rate with identifier "currency_rate_1" is used for conversion between CHF and EUR
#    The invoice's grandTotal is 23.8 EUR, the payment amount is 24.88 CHF and the C_Conversion_Rate.DivideRate is 1.045236 (24.88 CHF / 1.045236 = 23.8 EUR)
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_03022023_1          | bpartner_03022023_1      | bpartner_location_03022023_1      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_400
  Scenario: Create INBOUND payment using the payment API
  - fully allocate invoice's open amount with one JsonInvoicePaymentCreateRequest#line with discount (no write off)

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_01022023_1 | paymentAllocPricingSystem_01022023_1 | paymentAllocPricingSystem_01022023_1 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_01022023_1 | paymentAllocPricingSystem_01022023_1 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_01022023_1 | paymentAllocPriceList_01022023_1 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_01022023_1 | BPartnerTest_01022023_1 | BPartnerTest_01022023_1 | Y              | paymentAllocPricingSystem_01022023_1 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_01022023_1 | bpartner_01022023_1      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_01022023_1     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_01022023_1     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | inboundDocType          | ARR             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_01022023_1 | product_01022023_1 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_01022023_1 | paymentAllocPLV_01022023_1        | product_01022023_1      | 2.00     | PCE               | Normal                        |

#    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_01022023_1 | bpartner_01022023_1      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | invoiceExtId_01022023_1 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_01022023_1 | inv_01022023_1          | product_01022023_1      | 10          | PCE               |
    And the invoice identified by inv_01022023_1 is completed

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_01022023_1",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_01022023_1",
    "transactionDate": "2022-05-11",
    "type": "INBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_01022023_1",
            "amount": 23.56,
            "discountAmt": 0.24
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_01022023_1      | paymentExtId_01022023_1 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_01022023_1      | true                     | 0.00        | 23.56      | 0.24            | 0               | 2022-05-11  | bpartner_01022023_1          | bp_bank_account_01022023_1         | inboundDocType   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_01022023_1              | payment_01022023_1          | 23.56      | 0                | 0               | 0.24            |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_01022023_1          | bpartner_01022023_1      | bpartner_location_01022023_1      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_500
  Scenario: Create INBOUND payment using the payment API
  - fully allocate invoice's open amount with one JsonInvoicePaymentCreateRequest#line with discount and write off

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_02022023_3 | paymentAllocPricingSystem_02022023_3 | paymentAllocPricingSystem_02022023_3 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_02022023_3 | paymentAllocPricingSystem_02022023_3 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_02022023_3 | paymentAllocPriceList_02022023_3 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_02022023_3 | BPartnerTest_02022023_3 | BPartnerTest_02022023_3 | Y              | paymentAllocPricingSystem_02022023_3 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_02022023_3 | bpartner_02022023_3      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_02022023_3     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_02022023_3     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | inboundDocType          | ARR             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_02022023_3 | product_02022023_3 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_02022023_3 | paymentAllocPLV_02022023_3        | product_02022023_3      | 2.00     | PCE               | Normal                        |

#    GrandTotal = 23.8, Discount = 1, WriteOff = 1
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_3 | bpartner_02022023_3      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | invoiceExtId_02022023_3 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_3 | inv_02022023_3          | product_02022023_3      | 10          | PCE               |
    And the invoice identified by inv_02022023_3 is completed

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_02022023_3",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_02022023_3",
    "transactionDate": "2022-05-11",
    "type": "INBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_3",
            "amount": 21.8,
            "discountAmt": 1,
            "writeOffAmt": 1
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_02022023_3      | paymentExtId_02022023_3 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_02022023_3      | true                     | 0.00        | 21.8       | 1               | 1               | 2022-05-11  | bpartner_02022023_3          | bp_bank_account_02022023_3         | inboundDocType   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_02022023_3              | payment_02022023_3          | 21.8       | 0                | 1               | 1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_02022023_3          | bpartner_02022023_3      | bpartner_location_02022023_3      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_600
  Scenario: Create INBOUND payment using the payment API
  - same invoice on multiple lines
  - lines are summing up invoice's open amount (with discount and write off amt)

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_02022023_5 | paymentAllocPricingSystem_02022023_5 | paymentAllocPricingSystem_02022023_5 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_02022023_5 | paymentAllocPricingSystem_02022023_5 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_02022023_5 | paymentAllocPriceList_02022023_5 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_02022023_5 | BPartnerTest_02022023_5 | BPartnerTest_02022023_5 | Y              | paymentAllocPricingSystem_02022023_5 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_02022023_5 | bpartner_02022023_5      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_02022023_5     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_02022023_5     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | inboundDocType          | ARR             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_02022023_5 | product_02022023_5 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_02022023_5 | paymentAllocPLV_02022023_5        | product_02022023_5      | 2.00     | PCE               | Normal                        |

#    GrandTotal = 23.8, Discount = 1, WriteOff = 1
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_5 | bpartner_02022023_5      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | invoiceExtId_02022023_5 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_5 | inv_02022023_5          | product_02022023_5      | 10          | PCE               |
    And the invoice identified by inv_02022023_5 is completed

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_02022023_5",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_02022023_5",
    "transactionDate": "2022-05-11",
    "type": "INBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_5",
            "amount": 20.8
        },
        {
          "invoiceIdentifier": "ext-invoiceExtId_02022023_5",
            "amount": 1,
            "discountAmt": 1,
            "writeOffAmt": 1
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_02022023_5      | paymentExtId_02022023_5 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_02022023_5      | true                     | 0.00        | 21.8       | 1               | 1               | 2022-05-11  | bpartner_02022023_5          | bp_bank_account_02022023_5         | inboundDocType   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_02022023_5              | payment_02022023_5          | 21.8       | 0                | 1               | 1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_02022023_5          | bpartner_02022023_5      | bpartner_location_02022023_5      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_700
  Scenario: Create INBOUND payment using the payment API
  - 2 invoices
  - payment with two lines fully allocating both invoices' open amounts

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_02022023_7 | paymentAllocPricingSystem_02022023_7 | paymentAllocPricingSystem_02022023_7 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_02022023_7 | paymentAllocPricingSystem_02022023_7 | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_02022023_7 | paymentAllocPriceList_02022023_7 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_02022023_7 | BPartnerTest_02022023_7 | BPartnerTest_02022023_7 | Y              | paymentAllocPricingSystem_02022023_7 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_02022023_7 | bpartner_02022023_7      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_02022023_7     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_02022023_7     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | inboundDocType          | ARR             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_02022023_7 | product_02022023_7 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_02022023_7 | paymentAllocPLV_02022023_7        | product_02022023_7      | 2.00     | PCE               | Normal                        |

#    GrandTotal = 23.8
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_7 | bpartner_02022023_7      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | invoiceExtId_02022023_7 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_7 | inv_02022023_7          | product_02022023_7      | 10          | PCE               |
    And the invoice identified by inv_02022023_7 is completed

    #    GrandTotal = 19.04
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_8 | bpartner_02022023_7      | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 | invoiceExtId_02022023_8 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_8 | inv_02022023_8          | product_02022023_7      | 8           | PCE               |
    And the invoice identified by inv_02022023_8 is completed

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_02022023_7",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_02022023_7",
    "transactionDate": "2022-05-11",
    "type": "INBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_7",
            "amount": 23.8
        },
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_8",
            "amount": 19.04
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_02022023_7      | paymentExtId_02022023_7 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_02022023_7      | true                     | 0.00        | 42.84      | 0               | 0               | 2022-05-11  | bpartner_02022023_7          | bp_bank_account_02022023_7         | inboundDocType   |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_02022023_7              | payment_02022023_7          | 23.8       | 0                | 0               | 0               |
      | inv_02022023_8              | payment_02022023_7          | 19.04      | 0                | 0               | 0               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_02022023_7          | bpartner_02022023_7      | bpartner_location_02022023_7      | 10 Tage 1 % | true      | CO        | true       |
      | inv_02022023_8          | bpartner_02022023_7      | bpartner_location_02022023_7      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_800
  Scenario: Create OUTBOUND payment using the payment API
  - fully allocate invoice's open amount with one JsonInvoicePaymentCreateRequest#line (no discount, no write off)

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_02022023_2 | paymentAllocPricingSystem_02022023_2 | paymentAllocPricingSystem_02022023_2 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_02022023_2 | paymentAllocPricingSystem_02022023_2 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_02022023_2 | paymentAllocPriceList_02022023_2 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsVendor | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_02022023_2 | BPartnerTest_02022023_2 | BPartnerTest_02022023_2 | Y            | paymentAllocPricingSystem_02022023_2 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_02022023_2 | bpartner_02022023_2      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_02022023_2     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_02022023_2     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | outboundDocType         | APP             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_02022023_2 | product_02022023_2 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_02022023_2 | paymentAllocPLV_02022023_2        | product_02022023_2      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_2 | bpartner_02022023_2      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_02022023_2 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_2 | inv_02022023_2          | product_02022023_2      | 10          | PCE               |
    And the invoice identified by inv_02022023_2 is completed


    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_02022023_2",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_02022023_2",
    "transactionDate": "2022-05-11",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_2",
            "amount": 23.8
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_02022023_2      | paymentExtId_02022023_2 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_02022023_2      | true                     | 0.00        | 23.8       | 0               | 0               | 2022-05-11  | bpartner_02022023_2          | bp_bank_account_02022023_2         | outboundDocType  |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_02022023_2              | payment_02022023_2          | -23.8      | 0                | 0               | 0               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_02022023_2          | bpartner_02022023_2      | bpartner_location_02022023_2      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_900
  Scenario: Create OUTBOUND payment using the payment API
  - allocate a portion of the invoice's open amt with a first request
  - allocate remaining open amt with a second request (with discount and write off)

    Given metasfresh contains M_PricingSystems
      | Identifier                            | Name                                  | Value                                 |
      | paymentAllocPricingSystem_03022023_10 | paymentAllocPricingSystem_03022023_10 | paymentAllocPricingSystem_03022023_10 |

    And metasfresh contains M_PriceLists
      | Identifier                        | M_PricingSystem_ID.Identifier         | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_03022023_10 | paymentAllocPricingSystem_03022023_10 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                  | M_PriceList_ID.Identifier         | ValidFrom  |
      | paymentAllocPLV_03022023_10 | paymentAllocPriceList_03022023_10 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier           | Name                     | Value                    | OPT.IsVendor | M_PricingSystem_ID.Identifier         | OPT.C_PaymentTerm_ID |
      | bpartner_03022023_10 | BPartnerTest_03022023_10 | BPartnerTest_03022023_10 | Y            | paymentAllocPricingSystem_03022023_10 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                    | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_03022023_10 | bpartner_03022023_10     | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_03022023_10    | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_03022023_10    | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | outboundDocType         | APP             |

    And metasfresh contains M_Products:
      | Identifier          | Name                |
      | product_03022023_10 | product_03022023_10 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_03022023_10 | paymentAllocPLV_03022023_10       | product_03022023_10     | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8
    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId           |
      | inv_03022023_10 | bpartner_03022023_10     | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_03022023_10 |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_03022023_10 | inv_03022023_10         | product_03022023_10     | 10          | PCE               |
    And the invoice identified by inv_03022023_10 is completed


    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_03022023_10",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_03022023_10",
    "transactionDate": "2022-05-11",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_03022023_10",
            "amount": 10
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId           |
      | payment_03022023_10     | paymentExtId_03022023_10 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_03022023_10     | true                     | 0.00        | 10         | 0               | 0               | 2022-05-11  | bpartner_03022023_10         | bp_bank_account_03022023_10        | outboundDocType  |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_03022023_10             | payment_03022023_10         | -10        | -13.8            | 0               | 0               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_03022023_10         | bpartner_03022023_10     | bpartner_location_03022023_10     | 10 Tage 1 % | true      | CO        | false      |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_03022023_10",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_03022023_11",
    "transactionDate": "2022-05-11",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_03022023_10",
            "amount": 11.8,
            "discountAmt": 1,
            "writeOffAmt": 1
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId           |
      | payment_03022023_11     | paymentExtId_03022023_11 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_03022023_11     | true                     | 0.00        | 11.8       | 1               | 1               | 2022-05-11  | bpartner_03022023_10         | bp_bank_account_03022023_10        | outboundDocType  |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_03022023_10             | payment_03022023_11         | -11.8      | 0                | -1              | -1              |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_03022023_10         | bpartner_03022023_10     | bpartner_location_03022023_10     | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_1000
  Scenario: Create OUTBOUND payment using the payment API
  - fully allocate invoice's open amount with one JsonInvoicePaymentCreateRequest#line (no discount, no write off)
  - use different currency and transaction date in the past

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_03022023_2 | paymentAllocPricingSystem_03022023_2 | paymentAllocPricingSystem_03022023_2 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_03022023_2 | paymentAllocPricingSystem_03022023_2 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_03022023_2 | paymentAllocPriceList_03022023_2 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsVendor | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_03022023_2 | BPartnerTest_03022023_2 | BPartnerTest_03022023_2 | Y            | paymentAllocPricingSystem_03022023_2 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_03022023_2 | bpartner_03022023_2      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_03022023_2     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_03022023_2     | CHF                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | outboundDocType         | APP             |

    And load C_Currency:
      | C_Currency_ID.Identifier | OPT.C_Currency_ID |
      | eur                      | 102               |
      | chf                      | 318               |

#   Load conversion rates between CHF and EUR filtering them by both validFrom and validTo dates
#   When multiple rates are valid at the same time (i.e. matches a date), then they are ordered in a descending order based on validFrom and the rate with the most recent validFrom is used
    And load C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | C_Currency_ID.Identifier | C_Currency_ID_To.Identifier | ValidFrom  | ValidTo    |
      | currency_rate_1                 | chf                      | eur                         | 2015-05-21 | 2155-12-31 |
      | currency_rate_2                 | chf                      | eur                         | 2019-05-21 | 2056-12-31 |

    And validate C_Conversion_Rate:
      | C_Conversion_Rate_ID.Identifier | OPT.MultiplyRate | OPT.DivideRate |
      | currency_rate_1                 | 0.95672173557    | 1.045236       |
      | currency_rate_2                 | 0.884955752212   | 1.13           |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_03022023_2 | product_03022023_2 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_03022023_2 | paymentAllocPLV_03022023_2        | product_03022023_2      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_03022023_2 | bpartner_03022023_2      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_03022023_2 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_03022023_2 | inv_03022023_2          | product_03022023_2      | 10          | PCE               |
    And the invoice identified by inv_03022023_2 is completed

#    Use "2016-05-21" as transactionDate, so that the rate with identifier "currency_rate_1" is used for conversion between CHF and EUR
#    "2016-05-21" is in the range [2015-05-21 - 2155-12-31] (currency_rate_1) and not in the range [2019-05-21 - 2056-12-31] (currency_rate_2)

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_03022023_2",
    "currencyCode": "CHF",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_03022023_2",
    "transactionDate": "2016-05-21",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_03022023_2",
            "amount": 24.88
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_03022023_2      | paymentExtId_03022023_2 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_03022023_2      | false                    | 24.88      | 0               | 0               | 2016-05-21  | bpartner_03022023_2          | bp_bank_account_03022023_2         | outboundDocType  |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_03022023_2              | payment_03022023_2          | -23.8      | 0                | 0               | 0               |

#    Invoice is fully paid because rate with identifier "currency_rate_1" is used for conversion between CHF and EUR
#    The invoice's grandTotal is 23.8 EUR, the payment amount is 24.88 CHF and the C_Conversion_Rate.DivideRate is 1.045236 (24.88 CHF / 1.045236 = 23.8 EUR)
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_03022023_2          | bpartner_03022023_2      | bpartner_location_03022023_2      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_1100
  Scenario: Create OUTBOUND payment using the payment API
  - fully allocate invoice's open amount with one JsonInvoicePaymentCreateRequest#line with discount (no write off)

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_01022023_2 | paymentAllocPricingSystem_01022023_2 | paymentAllocPricingSystem_01022023_2 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_01022023_2 | paymentAllocPricingSystem_01022023_2 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_01022023_2 | paymentAllocPriceList_01022023_2 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsVendor | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_01022023_2 | BPartnerTest_01022023_2 | BPartnerTest_01022023_2 | Y            | paymentAllocPricingSystem_01022023_2 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_01022023_2 | bpartner_01022023_2      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_01022023_2     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_01022023_2     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | outboundDocType         | APP             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_01022023_2 | product_01022023_2 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_01022023_2 | paymentAllocPLV_01022023_2        | product_01022023_2      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 0.24
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_01022023_2 | bpartner_01022023_2      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_01022023_2 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_01022023_2 | inv_01022023_2          | product_01022023_2      | 10          | PCE               |
    And the invoice identified by inv_01022023_2 is completed


    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_01022023_2",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_01022023_2",
    "transactionDate": "2022-05-11",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_01022023_2",
            "amount": 23.56,
            "discountAmt": 0.24
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_01022023_2      | paymentExtId_01022023_2 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_01022023_2      | true                     | 0.00        | 23.56      | 0.24            | 0               | 2022-05-11  | bpartner_01022023_2          | bp_bank_account_01022023_2         | outboundDocType  |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_01022023_2              | payment_01022023_2          | -23.56     | 0                | 0               | -0.24           |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_01022023_2          | bpartner_01022023_2      | bpartner_location_01022023_2      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_1200
  Scenario: Create OUTBOUND payment using the payment API
  - fully allocate invoice's open amount with one JsonInvoicePaymentCreateRequest#line with discount and write off

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_02022023_4 | paymentAllocPricingSystem_02022023_4 | paymentAllocPricingSystem_02022023_4 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_02022023_4 | paymentAllocPricingSystem_02022023_4 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_02022023_4 | paymentAllocPriceList_02022023_4 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsVendor | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_02022023_4 | BPartnerTest_02022023_4 | BPartnerTest_02022023_4 | Y            | paymentAllocPricingSystem_02022023_4 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_02022023_4 | bpartner_02022023_4      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_02022023_4     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_02022023_4     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | outboundDocType         | APP             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_02022023_4 | product_02022023_4 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_02022023_4 | paymentAllocPLV_02022023_4        | product_02022023_4      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 1, WriteOff = 1
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_4 | bpartner_02022023_4      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_02022023_4 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_4 | inv_02022023_4          | product_02022023_4      | 10          | PCE               |
    And the invoice identified by inv_02022023_4 is completed


    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_02022023_4",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_02022023_4",
    "transactionDate": "2022-05-11",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_4",
            "amount": 21.8,
            "discountAmt": 1,
            "writeOffAmt": 1
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_02022023_4      | paymentExtId_02022023_4 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_02022023_4      | true                     | 0.00        | 21.8       | 1               | 1               | 2022-05-11  | bpartner_02022023_4          | bp_bank_account_02022023_4         | outboundDocType  |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_02022023_4              | payment_02022023_4          | -21.8      | 0                | -1              | -1              |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_02022023_4          | bpartner_02022023_4      | bpartner_location_02022023_4      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_1300
  Scenario: Create OUTBOUND payment using the payment API
  - fully allocate invoice's open amount with multiple lines (with discount and write off)

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_02022023_6 | paymentAllocPricingSystem_02022023_6 | paymentAllocPricingSystem_02022023_6 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_02022023_6 | paymentAllocPricingSystem_02022023_6 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_02022023_6 | paymentAllocPriceList_02022023_6 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsVendor | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_02022023_6 | BPartnerTest_02022023_6 | BPartnerTest_02022023_6 | Y            | paymentAllocPricingSystem_02022023_6 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_02022023_6 | bpartner_02022023_6      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_02022023_6     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_02022023_6     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | outboundDocType         | APP             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_02022023_6 | product_02022023_6 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_02022023_6 | paymentAllocPLV_02022023_6        | product_02022023_6      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8, Discount = 1, WriteOff = 1
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_6 | bpartner_02022023_6      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_02022023_6 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_6 | inv_02022023_6          | product_02022023_6      | 10          | PCE               |
    And the invoice identified by inv_02022023_6 is completed


    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_02022023_6",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_02022023_6",
    "transactionDate": "2022-05-11",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_6",
            "amount": 20.8
        },
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_6",
            "amount": 1,
            "discountAmt": 1,
            "writeOffAmt": 1
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_02022023_6      | paymentExtId_02022023_6 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_02022023_6      | true                     | 0.00        | 21.8       | 1               | 1               | 2022-05-11  | bpartner_02022023_6          | bp_bank_account_02022023_6         | outboundDocType  |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_02022023_6              | payment_02022023_6          | -21.8      | 0                | -1              | -1              |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_02022023_6          | bpartner_02022023_6      | bpartner_location_02022023_6      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_1400
  Scenario: Create OUTBOUND payment using the payment API
  - 2 invoices
  - payment with two lines fully allocating both invoices' open amounts

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_02022023_9 | paymentAllocPricingSystem_02022023_9 | paymentAllocPricingSystem_02022023_9 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_02022023_9 | paymentAllocPricingSystem_02022023_9 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_02022023_9 | paymentAllocPriceList_02022023_9 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsVendor | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_02022023_9 | BPartnerTest_02022023_9 | BPartnerTest_02022023_9 | Y            | paymentAllocPricingSystem_02022023_9 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_02022023_9 | bpartner_02022023_9      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_02022023_9     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_02022023_9     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | outboundDocType         | APP             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_02022023_9 | product_02022023_9 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_02022023_9 | paymentAllocPLV_02022023_9        | product_02022023_9      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_02022023_9 | bpartner_02022023_9      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_02022023_9 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_9 | inv_02022023_9          | product_02022023_9      | 10          | PCE               |
    And the invoice identified by inv_02022023_9 is completed

    And metasfresh contains C_Invoice:
      | Identifier      | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId           |
      | inv_02022023_10 | bpartner_02022023_9      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_02022023_10 |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_02022023_10 | inv_02022023_10         | product_02022023_9      | 8           | PCE               |
    And the invoice identified by inv_02022023_10 is completed


    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '200' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_02022023_9",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_02022023_9",
    "transactionDate": "2022-05-11",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_9",
            "amount": 23.8
        },
        {
            "invoiceIdentifier": "ext-invoiceExtId_02022023_10",
            "amount": 19.04
        }
    ]
}
"""

    Then after not more than 30s, C_Payment is found
      | C_Payment_ID.Identifier | OPT.ExternalId          |
      | payment_02022023_9      | paymentExtId_02022023_9 |

    And validate payments
      | C_Payment_ID.Identifier | C_Payment_ID.IsAllocated | OPT.OpenAmt | OPT.PayAmt | OPT.DiscountAmt | OPT.WriteOffAmt | OPT.DateTrx | OPT.C_BPartner_ID.Identifier | OPT.C_BP_BankAccount_ID.Identifier | OPT.C_DocType_ID |
      | payment_02022023_9      | true                     | 0.00        | 42.84      | 0               | 0               | 2022-05-11  | bpartner_02022023_9          | bp_bank_account_02022023_9         | outboundDocType  |

    And validate C_AllocationLines
      | OPT.C_Invoice_ID.Identifier | OPT.C_Payment_ID.Identifier | OPT.Amount | OPT.OverUnderAmt | OPT.WriteOffAmt | OPT.DiscountAmt |
      | inv_02022023_9              | payment_02022023_9          | -23.8      | 0                | 0               | 0               |
      | inv_02022023_10             | payment_02022023_9          | -19.04     | 0                | 0               | 0               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.IsPaid |
      | inv_02022023_9          | bpartner_02022023_9      | bpartner_location_02022023_9      | 10 Tage 1 % | true      | CO        | true       |
      | inv_02022023_10         | bpartner_02022023_9      | bpartner_location_02022023_9      | 10 Tage 1 % | true      | CO        | true       |

  @from:cucumber
  @Id:S0249_1500
  Scenario: Create OUTBOUND payment using the payment API
  - allocate more than the invoice's open amount with multiple JsonInvoicePaymentCreateRequest#lines (no discount, no write off)
  - expect error

    Given metasfresh contains M_PricingSystems
      | Identifier                           | Name                                 | Value                                |
      | paymentAllocPricingSystem_03022023_5 | paymentAllocPricingSystem_03022023_5 | paymentAllocPricingSystem_03022023_5 |

    And metasfresh contains M_PriceLists
      | Identifier                       | M_PricingSystem_ID.Identifier        | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | paymentAllocPriceList_03022023_5 | paymentAllocPricingSystem_03022023_5 | DE                        | EUR                 | PriceListName1 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier        | ValidFrom  |
      | paymentAllocPLV_03022023_5 | paymentAllocPriceList_03022023_5 | 2022-05-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                    | Value                   | OPT.IsVendor | M_PricingSystem_ID.Identifier        | OPT.C_PaymentTerm_ID |
      | bpartner_03022023_5 | BPartnerTest_03022023_5 | BPartnerTest_03022023_5 | Y            | paymentAllocPricingSystem_03022023_5 | 1000009              |

    And metasfresh contains C_BPartner_Locations:
      | Identifier                   | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartner_location_03022023_5 | bpartner_03022023_5      | Y                   | Y                   |

    And load C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_BP_BankAccount_ID |
      | bp_bank_account_03022023_5     | 2000257                 |

    And update C_BP_BankAccount:
      | C_BP_BankAccount_ID.Identifier | OPT.C_Currency.ISO_Code |
      | bp_bank_account_03022023_5     | EUR                     |

    And load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType |
      | outboundDocType         | APP             |

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_03022023_5 | product_03022023_5 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_03022023_5 | paymentAllocPLV_03022023_5        | product_03022023_5      | 2.00     | PCE               | Normal                        |

    #    GrandTotal = 23.8
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.ExternalId          |
      | inv_03022023_5 | bpartner_03022023_5      | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 | invoiceExtId_03022023_5 |
    And metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_UOM_ID.X12DE355 |
      | invl_03022023_5 | inv_03022023_5          | product_03022023_5      | 10          | PCE               |
    And the invoice identified by inv_03022023_5 is completed

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/payment' and fulfills with '422' status code
    """
{
    "bpartnerIdentifier": "val-BPartnerTest_03022023_5",
    "currencyCode": "EUR",
    "orgCode": "001",
    "externalPaymentId": "paymentExtId_03022023_5",
    "transactionDate": "2022-05-11",
    "type": "OUTBOUND",
    "lines": [
        {
            "invoiceIdentifier": "ext-invoiceExtId_03022023_5",
            "amount": 10
        },
        {
            "invoiceIdentifier": "ext-invoiceExtId_03022023_5",
            "amount": 10,
            "writeOffAmt": 4
        }
    ]
}
"""

    Then validate payment api response error message
      | JsonErrorItem.message                                             |
      | Line's total amount cannot be greater than invoice's open amount! |