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
  @Id:S0466_10
  @from:cucumber
  Scenario: Process Remittance-Advice
    # NOTE: this kind of allocation cannot be manually done by user (because REMADV code is doing it), but the purpose of this test
    # is to make sure, that in case of such allocation the open amounts and accounting is correct

    Given metasfresh contains C_Invoice:
      | Identifier         | DocumentNo       | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | customerInvoice    | F244410_S0466_10 | customer1     | Ausgangsrechnung        | 2022-05-11   | true    | EUR                 |
      | vendorInvoice      | 1034684_S0466_10 | vendor1       | Eingangsrechnung        | 2022-05-11   | false   | EUR                 |
      | customerCreditMemo | G104690_S0466_10 | customer1     | Gutschrift              | 2022-05-11   | true    | EUR                 |

    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID       | M_Product_ID      | QtyInvoiced | Price    | C_Tax_ID |
      | customerInvoice    | customerProduct   | 1 PCE       | 55278.05 | tax19%   |
      | vendorInvoice      | vendorProduct     | 1 PCE       | 1621.50  | tax19%   |
      | customerCreditMemo | creditMemoProduct | 1 PCE       | 77.60    | tax19%   |
    And the invoice identified by customerInvoice is completed
    And the invoice identified by vendorInvoice is completed
    And the invoice identified by customerCreditMemo is completed

    And metasfresh contains C_RemittanceAdvice
      | Identifier | Source_BP_BankAccount_ID   | Destination_BP_BankAccount_ID | DocumentNo | DateDoc    | RemittanceAmt | ServiceFeeAmount_Currency_ID | SendAt     |
      | ra1        | serviceProviderBankAccount | orgBankAccount                | S0466_100  | 2024-09-07 | 52583.57 EUR  | EUR                          | 2025-05-08 |
    And metasfresh contains C_RemittanceAdvice_Lines
      | Identifier | C_RemittanceAdvice_ID | LineIdentifier | InvoiceIdentifier_for | RemittanceAmt | PaymentDiscountAmt | ServiceFeeAmount |
      | ral10      | ra1                   | 10             | customerInvoice       | 53535.11      | 771.90             | 971.04           |
      | ral20      | ra1                   | 20             | vendorInvoice         | 1644.73       | 0                  | 23.23            |
      | ral30      | ra1                   | 30             | customerCreditMemo    | 77.62         | -1.09              | 1.11             |
    And the C_RemittanceAdvice identified by ra1 is completed
    And validate C_RemittanceAdvice
      | Identifier | RemittanceAmt |
      | ra1        | 51812.76 EUR  |

    When the C_RemittanceAdvice identified by ra1 is processed
    And load ServiceFeeInvoices from C_RemittanceAdvice_Lines
      | Identifier | Service_Fee_Invoice_ID |
      | ral10      | vendorServiceInvoice1  |
      | ral20      | vendorServiceInvoice2  |
      | ral30      | vendorServiceInvoice3  |
    And load Payments from C_RemittanceAdvices
      | Identifier | C_Payment_ID   |
      | ra1        | inboundPayment |

    Then validate created invoices
      | C_Invoice_ID          | C_BPartner_ID   | GrandTotal   | DocBaseType | IsPaid | OpenAmt |
      | customerInvoice       | customer1       | 55278.05 EUR | ARI         | true   | 0       |
      | vendorInvoice         | vendor1         | 1621.50 EUR  | API         | true   | 0       |
      | customerCreditMemo    | customer1       | 77.60 EUR    | ARC         | true   | 0       |
      | vendorServiceInvoice1 | serviceProvider | 971.04 EUR   | API         | true   | 0       |
      | vendorServiceInvoice2 | serviceProvider | 23.23 EUR    | API         | true   | 0       |
      | vendorServiceInvoice3 | serviceProvider | 1.11 EUR     | API         | true   | 0       |
    And validate payments
      | C_Payment_ID   | PayAmt   | IsAllocated |
      | inboundPayment | 51812.76 | true        |

    And validate C_AllocationLines
      | C_Invoice_ID          | C_Payment_ID   | Amount   | DiscountAmt | OverUnderAmt | C_AllocationHdr_ID |
    # customerInvoice
      | customerInvoice       | -              | 971.04   | 0           | 54307.01     | ral10_alloc1       |
      | vendorServiceInvoice1 | -              | -971.04  | 0           | 0            | ral10_alloc1       |
      | customerInvoice       | inboundPayment | 53535.11 | 771.90      | 0            | ral10_alloc2       |
    # vendorInvoice
      | vendorInvoice         | -              | 23.23    | 0           | -1644.73     | ral20_alloc1       |
      | vendorServiceInvoice2 | -              | -23.23   | 0           | 0            | ral20_alloc1       |
      | vendorInvoice         | inboundPayment | -1644.73 |             | 0            | ral20_alloc2       |
    # customerCreditMemo
      | customerCreditMemo    | -              | 1.11     | 0           | -78.71       | ral30_alloc1       |
      | vendorServiceInvoice3 | -              | -1.11    | 0           | 0            | ral30_alloc1       |
      | customerCreditMemo    | inboundPayment | -77.62   | -1.09       | 0            | ral30_alloc2       |

    And Fact_Acct records are matching
      | AccountConceptualName  | AmtSourceDr  | AmtSourceCr  | C_BPartner_ID   | C_Tax_ID | Record_ID             |
    # customerInvoice
      | C_Receivable_Acct      | 55278.05 EUR |              | customer1       | -        | customerInvoice       |
      | T_Due_Acct             |              | 8825.91 EUR  | customer1       | tax19%   | customerInvoice       |
      | *                      |              |              |                 |          | customerInvoice       |
      # ------------------------------------------------------------------------------------------
      | V_Liability_Acct       |              | 971.04 EUR   | serviceProvider | -        | vendorServiceInvoice1 |
      | *                      |              |              |                 |          | vendorServiceInvoice1 |
      # ------------------------------------------------------------------------------------------
      | C_Receivable_Acct      |              | 971.04 EUR   | customer1       | -        | ral10_alloc1          |
      | V_Liability_Acct       | 971.04 EUR   |              | serviceProvider | -        | ral10_alloc1          |
      # ------------------------------------------------------------------------------------------
      | C_Receivable_Acct      |              | 54307.01 EUR | customer1       | -        | ral10_alloc2          |
      | B_UnallocatedCash_Acct | 53535.11 EUR |              | serviceProvider | -        | ral10_alloc2          |
      | PayDiscount_Exp_Acct   | 771.90 EUR   |              | serviceProvider | tax19%   | ral10_alloc2          |
      # tax correction:
      | PayDiscount_Exp_Acct   |              | 123.24 EUR   | serviceProvider | tax19%   | ral10_alloc2          |
      | T_Due_Acct             | 123.24 EUR   |              | serviceProvider | tax19%   | ral10_alloc2          |
    # vendorInvoice
      | V_Liability_Acct       |              | 1621.5 EUR   | vendor1         | -        | vendorInvoice         |
      | T_Credit_Acct          | 258.89 EUR   |              | vendor1         | tax19%   | vendorInvoice         |
      | *                      |              |              |                 |          | vendorInvoice         |
      # ------------------------------------------------------------------------------------------
      | V_Liability_Acct       |              | 23.23 EUR    | serviceProvider | -        | vendorServiceInvoice2 |
      | *                      |              |              |                 |          | vendorServiceInvoice2 |
      # ------------------------------------------------------------------------------------------
      | V_Liability_Acct       |              | 23.23 EUR    | vendor1         | -        | ral20_alloc1          |
      | V_Liability_Acct       | 23.23 EUR    |              | serviceProvider | -        | ral20_alloc1          |
      # ----note: no PayDiscount_Rev_Acct because paymentDiscount=0 -------------------------------------------------
      | V_Liability_Acct       | 1644.73 EUR  |              | vendor1         | -        | ral20_alloc2          |
      | B_UnallocatedCash_Acct |              | 1644.73 EUR  | serviceProvider | -        | ral20_alloc2          |
      # tax correction: TODO: !!Verify i'Ts OK!!
#      | PayDiscount_Rev_Acct   | 999.99 EUR   |              | serviceProvider | tax19%   | ral20_alloc2          |
#      | T_Credit_Acct          |              | 999.99 EUR   | serviceProvider | tax19%   | ral20_alloc2          |
    # customerCreditMemo
      | C_Receivable_Acct      |              | 77.60 EUR    | customer1       | -        | customerCreditMemo    |
      | T_Due_Acct             | 12.39 EUR    |              | customer1       | tax19%   | customerCreditMemo    |
      | *                      |              |              |                 |          | customerCreditMemo    |
      # ------------------------------------------------------------------------------------------
      | V_Liability_Acct       |              | 1.11 EUR     | serviceProvider | -        | vendorServiceInvoice3 |
      | *                      |              |              |                 |          | vendorServiceInvoice3 |
      # ------------------------------------------------------------------------------------------
      | C_Receivable_Acct      |              | 1.11 EUR     | customer1       | -        | ral30_alloc1          |
      | V_Liability_Acct       | 1.11 EUR     |              | serviceProvider | -        | ral30_alloc1          |
      # ------------------------------------------------------------------------------------------
      | C_Receivable_Acct      | 78.71 EUR    |              | customer1       | -        | ral30_alloc2          |
      | B_UnallocatedCash_Acct |              | 77.62 EUR    | serviceProvider | -        | ral30_alloc2          |
      | PayDiscount_Rev_Acct   |              | 1.09 EUR     | serviceProvider | tax19%   | ral30_alloc2          |
      # tax correction:
      | PayDiscount_Rev_Acct   | 0.17 EUR     |              | serviceProvider | tax19%   | ral30_alloc2          |
      | T_Due_Acct             |              | 0.17 EUR     | serviceProvider | tax19%   | ral30_alloc2          |
    # Payment
      | B_UnallocatedCash_Acct |              | 51812.76 EUR | serviceProvider | -        | inboundPayment        |
      | B_InTransit_Acct       | 51812.76 EUR |              | serviceProvider | -        | inboundPayment        |
