@from:cucumber
@ghActions:run_on_executor5
Feature: invoice payment allocation

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

    And metasfresh contains C_BPartners without locations:
      | Identifier      | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1       | Y          | N        | pricingSystem      |
      | vendor1         | N          | Y        | pricingSystem      |
      | serviceProvider | N          | Y        | pricingSystem      |

    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID   | IsShipToDefault | IsBillToDefault |
      | bpartner_location_1 | customer1       | Y               | Y               |
      | bpartner_location_2 | vendor1         | Y               | Y               |
      | bpartner_location_3 | serviceProvider | Y               | Y               |

    And metasfresh contains C_BP_BankAccount
      | Identifier                 | C_BPartner_ID   | C_Currency_ID |
      | serviceProviderBankAccount | serviceProvider | EUR           |

    And metasfresh contains organization bank accounts
      | Identifier     | C_Currency_ID |
      | orgBankAccount | EUR           |

    Given metasfresh contains M_Products:
      | Identifier           |
      | creditMemoProduct    |
      | customerProduct      |
      | vendorProduct        |
      | vendorServiceProduct |
    # IsTaxIncluded=Y, so that when we create invoices we have GrandTotal == LineNetAmount
    And update M_PriceLists:
      | Identifier        | IsTaxIncluded |
      | salesPriceList    | Y             |
      | purchasePriceList | Y             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID |
      | salesPLV               | creditMemoProduct    | 0        | PCE      |
      | salesPLV               | customerProduct      | 0        | PCE      |
      | purchasePLV            | vendorProduct        | 0        | PCE      |
      | purchasePLV            | vendorServiceProduct | 0        | PCE      |

    And load C_DocType:
      | C_DocType_ID.Identifier | Name                         |
      | vendorServiceDocType    | Rechnung für Servicegebühren |

    And metasfresh contains InvoiceProcessingServiceCompany
      | Identifier | ServiceCompany_BPartner_ID | ServiceFee_Product_ID | ServiceInvoice_DocType_ID |
      | config     | serviceProvider            | vendorServiceProduct  | vendorServiceDocType      |
    And metasfresh contains InvoiceProcessingServiceCompany_BPartnerAssignment
      | InvoiceProcessingServiceCompany_ID | C_BPartner_ID |
      | config                             | customer1     |
      | config                             | vendor1       |




# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  @Id:S0466_100
  @from:cucumber
  Scenario: Process Remittance-Advice
    # NOTE: this kind of allocation cannot be manually done by user (because REMADV code is doing it), but the purpose of this test
    # is to make sure, that in case of such allocation the open amounts and accounting is correct
    Given metasfresh contains C_Invoice:
      | Identifier         | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | customerCreditMemo | customer1     | Gutschrift              | 2022-05-11   | Spot                     | true    | EUR                 |
      | vendorInvoice      | vendor1       | Eingangsrechnung        | 2022-05-11   | Spot                     | false   | EUR                 |
      | customerInvoice    | customer1     | Ausgangsrechnung        | 2022-05-11   | Spot                     | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID       | M_Product_ID      | QtyInvoiced | Price  | C_Tax_ID |
      | customerCreditMemo | creditMemoProduct | 1 PCE       | 434.58 | tax19%   |
      | vendorInvoice      | vendorProduct     | 1 PCE       | 434.58 | tax19%   |
      | customerInvoice    | customerProduct   | 1 PCE       | 595.00 | tax19%   |
    And the invoice identified by customerCreditMemo is completed
    And the invoice identified by vendorInvoice is completed
    And the invoice identified by customerInvoice is completed

    And metasfresh contains C_RemittanceAdvice
      | Identifier | Source_BP_BankAccount_ID   | Destination_BP_BankAccount_ID | DocumentNo | DateDoc    | RemittanceAmt | ServiceFeeAmount_Currency_ID | SendAt     |
      | ra1        | serviceProviderBankAccount | orgBankAccount                | S0466_100  | 2025-05-07 | 1399.91 EUR   | EUR                          | 2025-05-08 |
    And metasfresh contains C_RemittanceAdvice_Lines
      | Identifier | C_RemittanceAdvice_ID | LineIdentifier | InvoiceIdentifier_for | RemittanceAmt | ServiceFeeAmount | PaymentDiscountAmt |
      | ral1       | ra1                   | 10             | customerCreditMemo    | 434.97        | 6.46             | -6.07              |
#      | ral2       | ra1                   | 20             | vendorInvoice         | 434.97        | 6.46             | -6.07              |
# already probably OK      | ral3       | ra1                   | 30             | customerInvoice       | 529.97        | 32.10            | 32.93              |
    And the C_RemittanceAdvice identified by ra1 is completed

    When the C_RemittanceAdvice identified by ra1 is processed

    And load ServiceFeeInvoices from C_RemittanceAdvice_Lines
      | Identifier | Service_Fee_Invoice_ID |
      | ral1       | vendorServiceInvoice1  |
      | ral2       | vendorServiceInvoice2  |
      | ral3       | vendorServiceInvoice3  |

    Then validate C_AllocationLines
      | C_Invoice_ID          | C_Payment_ID   | Amount  | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
      | customerCreditMemo    | -              | 6.46    | 0           | -441.04      | alloc1             |
      | vendorServiceInvoice1 | -              | -6.46   | 0           | 0            | alloc1             |
      # --------------------------------------------------------------------------------------------------
      | customerCreditMemo    | inboundPayment | -434.97 | -6.07       | 0            | alloc2             |

    And validate created invoices
      | C_Invoice_ID          | C_BPartner_ID   | GrandTotal | DocBaseType | IsPaid | IsPartiallyPaid | OpenAmt |
      | customerCreditMemo    | customer1       | 434.58 EUR | ARC         | true   | false           | 0       |
      | vendorServiceInvoice1 | serviceProvider | 6.46 EUR   | API         | true   | false           | 0       |
      | vendorServiceInvoice2 | serviceProvider | 6.46 EUR   | API         | true   | false           | 0       |
      | vendorServiceInvoice3 | serviceProvider | 32.10 EUR  | API         | true   | false           | 0       |

    And validate payments
      | C_Payment_ID   | IsAllocated |
      | inboundPayment | true        |
    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr | AmtSourceCr | C_BPartner_ID   | C_Tax_ID | Record_ID             |
      | C_Receivable_Acct      |             | 434.58 EUR  | customer1       | -        | customerCreditMemo    |
      | T_Due_Acct             | 69.39 EUR   |             | customer1       | tax19%   | customerCreditMemo    |
      | *                      |             |             |                 |          | customerCreditMemo    |
      # ------------------------------------------------------------------------------------------
      | V_Liability_Acct       |             | 6.46 EUR    | serviceProvider | -        | vendorServiceInvoice1 |
      | *                      |             |             |                 |          | vendorServiceInvoice1 |
      # ------------------------------------------------------------------------------------------
      | C_Receivable_Acct      |             | 6.46 EUR    | customer1       | -        | alloc1                |
      | V_Liability_Acct       | 6.46 EUR    |             | serviceProvider | -        | alloc1                |
      # ------------------------------------------------------------------------------------------
      | C_Receivable_Acct      | 441.04 EUR  |             | customer1       | -        | alloc2                |
      | B_UnallocatedCash_Acct |             | 434.97 EUR  | serviceProvider | -        | alloc2                |
      | PayDiscount_Rev_Acct   |             | 6.07 EUR    | serviceProvider | tax19%   | alloc2                |
      # tax correction:
      | PayDiscount_Rev_Acct   | 0.97 EUR    |             | customer1       | tax19%   | alloc2                |
      | T_Due_Acct             |             | 0.97 EUR    | customer1       | tax19%   | alloc2                |
      # ------------------------------------------------------------------------------------------
      | B_UnallocatedCash_Acct |             | -434.97 EUR | serviceProvider | -        | inboundPayment        |
      | B_InTransit_Acct       | -434.97 EUR |             | serviceProvider | -        | inboundPayment        |
