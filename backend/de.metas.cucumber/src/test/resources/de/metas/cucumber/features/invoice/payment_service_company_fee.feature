@from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F01200
@ghActions:run_on_executor5
Feature: service company fee at payment allocation
## A customer assigned to a service company (with FeePercentageOfGrandTotal) pays the invoice minus the service fee.
## There are two service companies: the customer belongs to the first one, i.e. NOT to the last one configured.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList    | pricingSystem      | DE           | EUR           | true  |
      | purchasePriceList | pricingSystem      | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID    |
      | salesPLV    | salesPriceList    |
      | purchasePLV | purchasePriceList |
    # IsTaxIncluded=Y, so that the invoice GrandTotal == line amount
    And update M_PriceLists:
      | Identifier        | IsTaxIncluded |
      | salesPriceList    | Y             |
      | purchasePriceList | Y             |

    And metasfresh contains C_BPartners without locations:
      | Identifier      | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1       | Y          | N        | pricingSystem      |
      | customer2       | Y          | N        | pricingSystem      |
      | serviceCompany1 | N          | Y        | pricingSystem      |
      | serviceCompany2 | N          | Y        | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | C_BPartner_ID   | IsShipToDefault | IsBillToDefault |
      | location_c1 | customer1       | Y               | Y               |
      | location_c2 | customer2       | Y               | Y               |
      | location_s1 | serviceCompany1 | Y               | Y               |
      | location_s2 | serviceCompany2 | Y               | Y               |
    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |

    And metasfresh contains M_Products:
      | Identifier     |
      | goodsProduct   |
      | serviceProduct |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID |
      | salesPLV               | goodsProduct   | 100.00   | PCE      |
      | purchasePLV            | serviceProduct | 0        | PCE      |

    And load C_DocType:
      | C_DocType_ID.Identifier | Name                         |
      | serviceInvoiceDocType   | Rechnung für Servicegebühren |

    And metasfresh contains InvoiceProcessingServiceCompany
      | Identifier | ServiceCompany_BPartner_ID | ServiceFee_Product_ID | ServiceInvoice_DocType_ID |
      | config1    | serviceCompany1            | serviceProduct        | serviceInvoiceDocType     |
      | config2    | serviceCompany2            | serviceProduct        | serviceInvoiceDocType     |
    And metasfresh contains InvoiceProcessingServiceCompany_BPartnerAssignment
      | InvoiceProcessingServiceCompany_ID | C_BPartner_ID | FeePercentageOfGrandTotal |
      | config1                            | customer1     | 2.6                       |
      | config2                            | customer2     | 1.5                       |

# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @allure.label.epic:E0220_Financial
  @allure.label.feature:F01200
  Scenario: customer of a non-last service company pays the invoice minus the service fee
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_1      | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_1     | inv_1        | goodsProduct | 1 PCE       |
    And the invoice identified by inv_1 is completed
    # invoice GrandTotal = 100.00; fee = 2.6% = 2.60; the service company pays on behalf of its customer: 100.00 - 2.60 = 97.40
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID   | PayAmt    | IsReceipt | C_BP_BankAccount_ID |
      | payment_1  | serviceCompany1 | 97.40 EUR | true      | org_EUR_account     |
    And the payment identified by payment_1 is completed

    When allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID | InvoiceProcessing.C_BPartner_ID | InvoiceProcessing.C_Invoice_ID |
      | inv_1        | payment_1    | serviceCompany1                 | serviceInvoice_1               |

    Then validate created invoices
      | C_Invoice_ID     | C_BPartner_ID   | GrandTotal | DocBaseType | IsPaid |
      | inv_1            | customer1       | 100.00 EUR | ARI         | true   |
      | serviceInvoice_1 | serviceCompany1 | 2.60 EUR   | API         | true   |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_1    | true        |
    And validate C_AllocationLines
      | C_Invoice_ID     | C_Payment_ID | Amount | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
      # the service fee: customer invoice is settled against the service invoice
      | inv_1            | -            | 2.60   | 0           | 97.40        | alloc_fee          |
      | serviceInvoice_1 | -            | -2.60  | 0           | 0            | alloc_fee          |
      # the payment settles the rest
      | inv_1            | payment_1    | 97.40  | 0           | 0            | alloc_payment      |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | C_BPartner_ID   | Record_ID     |
      | C_Receivable_Acct      |             | 2.60 EUR    | customer1       | alloc_fee     |
      | V_Liability_Acct       | 2.60 EUR    |             | serviceCompany1 | alloc_fee     |
      # ----------------------------------------------------------------------------------
      | B_UnallocatedCash_Acct | 97.40 EUR   |             | serviceCompany1 | alloc_payment |
      | C_Receivable_Acct      |             | 97.40 EUR   | customer1       | alloc_payment |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @allure.label.epic:E0220_Financial
  @allure.label.feature:F01200
  Scenario: a payment whose partner is not a service company is refused for an invoice with service fee
    Given metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | inv_2      | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invl_2     | inv_2        | goodsProduct | 1 PCE       |
    And the invoice identified by inv_2 is completed
    # the customer pays the full invoice itself, i.e. not via its service company
    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt     | IsReceipt | C_BP_BankAccount_ID |
      | payment_2  | customer1     | 100.00 EUR | true      | org_EUR_account     |
    And the payment identified by payment_2 is completed

    When allocate payments to invoices expecting error code SERVICE_FEE_PAYMENT_PARTNER_NO_CONFIG
      | C_Invoice_ID | C_Payment_ID |
      | inv_2        | payment_2    |

    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | GrandTotal | DocBaseType | IsPaid |
      | inv_2        | customer1     | 100.00 EUR | ARI         | false  |
    And validate payments
      | C_Payment_ID | IsAllocated |
      | payment_2    | false       |
