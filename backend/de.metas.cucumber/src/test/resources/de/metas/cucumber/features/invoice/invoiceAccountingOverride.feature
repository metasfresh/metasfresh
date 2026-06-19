@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@ghActions:run_on_executor5
Feature: Per-line GL account override on purchase invoices
# A per-line C_ElementValue_Override_ID on a purchase invoice line:
# - materializes C_Invoice_Acct rows on completion and posts to the override account;
# - must not leak into payment-allocation posting (which uses V_Liability_Acct).

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-15T08:00:00+02:00[Europe/Berlin]
    And documents are accounted immediately

    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | zeroTax    | taxCategory      | 0    | DE                       | DE                        |

    And metasfresh contains C_PaymentTerm
      | Identifier  |
      | paymentTerm |

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | purchasePriceList | pricingSystem      | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID    |
      | purchasePLV | purchasePriceList |

    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | purchasePLV            | product      | 100.00   | PCE      | taxCategory      |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | PO_PricingSystem_ID |
      | vendor     | N          | Y        | pricingSystem       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_location | vendor        | Y               | Y               |

    # A distinct override account; must differ from the product's default P_Expense_Acct
    And metasfresh contains C_ElementValues:
      | Identifier      |
      | overrideAccount |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID | IBAN                    |
      | org_EUR_account | EUR           | DE89370400440532013000  |


  # Scenario A: the per-line override materializes C_Invoice_Acct (P_Expense + P_InventoryClearing)
  # and the expense Fact_Acct leg posts to overrideAccount, not the product-default P_Expense_Acct.
  @Id:S30443_TC1
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  Scenario: Purchase invoice with per-line GL override posts to the override account
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | invoice    | vendor        | Eingangsrechnung        | 2022-06-15   | false   | EUR           | paymentTerm      |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID | C_ElementValue_Override_ID |
      | invoiceLine | invoice      | product      | 1 PCE       | zeroTax  | overrideAccount            |
    And the invoice identified by invoice is completed

    # (a) Materialized C_Invoice_Acct rows must exist for both expense concepts
    Then C_Invoice_Acct rows are found for invoice:
      | C_Invoice_ID | C_InvoiceLine_ID | AccountName                | C_ElementValue_ID |
      | invoice      | invoiceLine      | P_Expense_Acct             | overrideAccount   |
      | invoice      | invoiceLine      | P_InventoryClearing_Acct   | overrideAccount   |

    # (b) The expense leg posts to overrideAccount, not the product-default P_Expense_Acct
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID | Account_ID      |
      | V_Liability_Acct      |             | 100 EUR     | vendor        | invoice   |                 |
      | P_Expense_Acct        | 100 EUR     |             | vendor        | invoice   | overrideAccount |


  # Scenario B (regression guard): paying + allocating the override invoice must post the allocation
  # to V_Liability_Acct / B_PaymentSelect_Acct — the override must NOT leak into Doc_AllocationHdr posting.
  @Id:S30443_TC2
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  Scenario: Payment allocation for a per-line override invoice posts to V_Liability, not the override account
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | invoice    | vendor        | Eingangsrechnung        | 2022-06-15   | false   | EUR           | paymentTerm      |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID | C_ElementValue_Override_ID |
      | invoiceLine | invoice      | product      | 1 PCE       | zeroTax  | overrideAccount            |
    And the invoice identified by invoice is completed

    And metasfresh contains C_Payment
      | Identifier | C_BPartner_ID | PayAmt   | IsReceipt | C_BP_BankAccount_ID |
      | payment    | vendor        | 100 EUR  | false     | org_EUR_account     |
    And the payment identified by payment is completed

    And allocate payments to invoices
      | C_Invoice_ID | C_Payment_ID |
      | invoice      | payment      |

    And register C_AllocationHdr from C_Payment:
      | C_Payment_ID | C_AllocationHdr_ID |
      | payment      | alloc              |

    # Allocation posts to V_Liability_Acct / B_PaymentSelect_Acct. Fact_Acct matching is strict:
    # any unexpected P_Expense_Acct (override) row for this allocation would fail this step.
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID |
      | V_Liability_Acct      | 100 EUR     |             | vendor        | alloc     |
      | B_PaymentSelect_Acct  |             | 100 EUR     | vendor        | alloc     |
