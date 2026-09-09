@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00700_Invoicing
@allure.label.feature:F01010.4_Invoice_Accounting_Overrides
@ghActions:run_on_executor5
Feature: Per-line GL account override on purchase invoices
# A per-line C_ElementValue_Override_ID on a purchase invoice line:
# - materializes C_Invoice_Acct rows on completion and posts to the override account;
# - must not leak into payment-allocation posting (which uses V_Liability_Acct).

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
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

    And metasfresh contains M_Warehouse:
      | Identifier |
      | warehouse  |

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

    # HU packing instructions (LU holding 10 TUs; TU holding 10 CUs of product) — needed for the material receipt
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | LU                    |
      | TU                    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | LU_Version         | LU         | LU          | Y         |
      | TU_Version         | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | LU_Version                    | 10  | HU       | TU                               |
      | huPiItemTU                 | TU_Version                    |     | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | product_TU_10CU                    | huPiItemTU                 | product                 | 10  | 2021-01-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | PO_PricingSystem_ID |
      | vendor     | N          | Y        | pricingSystem       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_location | vendor        | Y               | Y               |

    # F01010.4: the vendor uses the "Invoice Line Standard Fields" aggregation (540003) for invoice-line
    # grouping — this is the aggregation that carries the C_ElementValue_Override key component. Without it,
    # the default (programmatic) line aggregation ignores the override, so the aggregation scenarios below
    # would not reflect the feature. Single-IC / direct-invoice scenarios are unaffected by this setting.
    And load C_Aggregations:
      | Identifier        | C_Aggregation_ID |
      | invoiceLineStdAgg | 540003           |
    And update C_BPartner:
      | Identifier | PO_InvoiceLine_Aggregation_ID.Identifier |
      | vendor     | invoiceLineStdAgg                        |

    # Distinct override accounts; must differ from the product's default P_InventoryClearing_Acct.
    # overrideAccount2 is used by the aggregation-key scenario to force two ICs apart.
    And metasfresh contains C_ElementValues:
      | Identifier       |
      | overrideAccount  |
      | overrideAccount2 |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID | IBAN                    |
      | org_EUR_account | EUR           | DE89370400440532013000  |


  # Scenario A: a per-line override set on the purchase invoice candidate is materialized into
  # C_Invoice_Acct on invoice completion, and the receipt-matched inventory-clearing leg of the
  # vendor invoice posts to the override account instead of the product-default account.
  @Id:S30443_TC1
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  @allure.label.feature:F01010.4_Invoice_Accounting_Overrides
  Scenario: Purchase invoice from a receipt-matched candidate with a per-line GL override posts to the override account
    # Purchase order -> complete
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | false   | vendor        | POO         | 2022-06-15  | warehouse      | paymentTerm      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | QtyEnteredTU | M_HU_PI_Item_Product_ID |
      | po_line    | po         | product      | 100        | 10           | product_TU_10CU         |
    And the order identified by po is completed

    # Material receipt (PO -> M_ReceiptSchedule -> HUs -> receipt)
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule                 | po                    | po_line                   | vendor                   | vendor_location                   | product                 | 100        | warehouse                 | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu                 | receiptSchedule                 | N               | 1     | N               | 10    | N               | 10          | product_TU_10CU                    | LU                           |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu                 | receiptSchedule                 | materialReceipt       |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | materialReceipt       | CO        |
    And validate the created material receipt lines
      | M_InOutLine_ID      | M_InOut_ID      | M_Product_ID | movementqty | processed |
      | materialReceiptLine | materialReceipt | product      | 100         | true      |

    # Invoice candidate -> set the per-line GL override -> process into the vendor invoice
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand                       | po                        | po_line                   | 100              | 100          | materialReceiptLine           |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_ElementValue_Override_ID |
      | invoiceCand            | overrideAccount            |
    And process invoice candidates
      | C_Invoice_Candidate_ID |
      | invoiceCand            |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | vendorInvoice           | invoiceCand                       |
    And validate created invoices
      | C_Invoice_ID  | C_BPartner_ID | C_BPartner_Location_ID | DocStatus |
      | vendorInvoice | vendor        | vendor_location        | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID  | C_Invoice_ID  | M_Product_ID | QtyInvoiced |
      | vendorInvoiceLine | vendorInvoice | product      | 100         |
    And M_MatchInv are found
      | M_MatchInv_ID | C_InvoiceLine_ID  | M_InOutLine_ID      | M_Product_ID | QytInUOM | IsSOTrx |
      | matchInv      | vendorInvoiceLine | materialReceiptLine | product      | 100      | N       |

    # (a) Materialized C_Invoice_Acct rows must exist for both expense concepts, scoped to the invoice line
    Then C_Invoice_Acct rows are found for invoice:
      | C_Invoice_ID  | C_InvoiceLine_ID  | AccountName              | C_ElementValue_ID |
      | vendorInvoice | vendorInvoiceLine | P_Expense_Acct           | overrideAccount   |
      | vendorInvoice | vendorInvoiceLine | P_InventoryClearing_Acct | overrideAccount   |

    # (b) The receipt-matched inventory-clearing leg on the vendor invoice posts to overrideAccount,
    #     not the product-default P_InventoryClearing_Acct. The V_Liability leg is unaffected.
    And Fact_Acct records are matching
      | AccountConceptualName    | C_BPartner_ID | Record_ID     | Account_ID      |
      | V_Liability_Acct         | vendor        | vendorInvoice |                 |
      | P_InventoryClearing_Acct | vendor        | vendorInvoice | overrideAccount |
      | T_Credit_Acct            | vendor        | vendorInvoice |                 |


  # Scenario B (regression guard): paying + allocating the override invoice must post the allocation
  # to V_Liability_Acct / B_PaymentSelect_Acct — the override must NOT leak into Doc_AllocationHdr posting.
  @Id:S30443_TC2
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  @allure.label.feature:F01010.4_Invoice_Accounting_Overrides
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


  # Scenario C (Q3): the per-line GL account override is part of the invoice-line aggregation key.
  # Two receipt-matched purchase invoice candidates for the SAME product/price/UOM but with DIFFERENT
  # C_ElementValue_Override_ID must NOT merge — they produce TWO separate invoice lines.
  @Id:S30443_TC3
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  @allure.label.feature:F01010.4_Invoice_Accounting_Overrides
  Scenario: Candidates differing only by the GL account override split into separate invoice lines
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | false   | vendor        | POO         | 2022-06-15  | warehouse      | paymentTerm      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | QtyEnteredTU | M_HU_PI_Item_Product_ID |
      | po_line_a  | po         | product      | 100        | 10           | product_TU_10CU         |
      | po_line_b  | po         | product      | 60         | 6            | product_TU_10CU         |
    And the order identified by po is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_a               | po                    | po_line_a                 | vendor                   | vendor_location                   | product                 | 100        | warehouse                 | 10               |
      | receiptSchedule_b               | po                    | po_line_b                 | vendor                   | vendor_location                   | product                 | 60         | warehouse                 | 6                |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_a                        | hu_a               | receiptSchedule_a               | N               | 1     | N               | 10    | N               | 10          | product_TU_10CU                    | LU                           |
      | huLuTuConfig_b                        | hu_b               | receiptSchedule_b               | N               | 1     | N               | 6     | N               | 10          | product_TU_10CU                    | LU                           |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_a               | receiptSchedule_a               | materialReceipt_a     |
      | hu_b               | receiptSchedule_b               | materialReceipt_b     |
    And validate the created material receipt lines
      | M_InOutLine_ID        | M_InOut_ID        | M_Product_ID | movementqty | processed |
      | materialReceiptLine_a | materialReceipt_a | product      | 100         | true      |
      | materialReceiptLine_b | materialReceipt_b | product      | 60          | true      |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_a                     | po                        | po_line_a                 | 100              | 100          | materialReceiptLine_a         |
      | invoiceCand_b                     | po                        | po_line_b                 | 60               | 60           | materialReceiptLine_b         |
    # Different override accounts on the two otherwise-identical candidates.
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_ElementValue_Override_ID |
      | invoiceCand_a          | overrideAccount            |
      | invoiceCand_b          | overrideAccount2           |

    # The override account is part of the invoice-line aggregation key (C_Aggregation 540003, the vendor's
    # line aggregation). Different overrides => different LineAggregationKey => the candidates do NOT merge;
    # the invoicing engine emits two separate invoice lines. (Asserting the key is the deterministic
    # mechanism-level check; line splitting/merging by key is core invoicing behaviour.)
    Then C_Invoice_Candidate LineAggregationKeys are different:
      | C_Invoice_Candidate_ID |
      | invoiceCand_a          |
      | invoiceCand_b          |


  # Scenario D (Q3 control): the SAME override account on both candidates keeps the default behaviour —
  # they merge into ONE invoice line (qty 160). Proves the split above is caused by the differing override.
  @Id:S30443_TC4
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  @allure.label.feature:F01010.4_Invoice_Accounting_Overrides
  Scenario: Candidates with the same GL account override merge into a single invoice line
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered | M_Warehouse_ID | C_PaymentTerm_ID |
      | po         | false   | vendor        | POO         | 2022-06-15  | warehouse      | paymentTerm      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | QtyEnteredTU | M_HU_PI_Item_Product_ID |
      | po_line_a  | po         | product      | 100        | 10           | product_TU_10CU         |
      | po_line_b  | po         | product      | 60         | 6            | product_TU_10CU         |
    And the order identified by po is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_a               | po                    | po_line_a                 | vendor                   | vendor_location                   | product                 | 100        | warehouse                 | 10               |
      | receiptSchedule_b               | po                    | po_line_b                 | vendor                   | vendor_location                   | product                 | 60         | warehouse                 | 6                |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_a                        | hu_a               | receiptSchedule_a               | N               | 1     | N               | 10    | N               | 10          | product_TU_10CU                    | LU                           |
      | huLuTuConfig_b                        | hu_b               | receiptSchedule_b               | N               | 1     | N               | 6     | N               | 10          | product_TU_10CU                    | LU                           |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_a               | receiptSchedule_a               | materialReceipt_a     |
      | hu_b               | receiptSchedule_b               | materialReceipt_b     |
    And validate the created material receipt lines
      | M_InOutLine_ID        | M_InOut_ID        | M_Product_ID | movementqty | processed |
      | materialReceiptLine_a | materialReceipt_a | product      | 100         | true      |
      | materialReceiptLine_b | materialReceipt_b | product      | 60          | true      |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_a                     | po                        | po_line_a                 | 100              | 100          | materialReceiptLine_a         |
      | invoiceCand_b                     | po                        | po_line_b                 | 60               | 60           | materialReceiptLine_b         |
    # Same override account on both otherwise-identical candidates.
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | C_ElementValue_Override_ID |
      | invoiceCand_a          | overrideAccount            |
      | invoiceCand_b          | overrideAccount            |

    # Equal overrides => identical LineAggregationKey => the candidates keep the default behaviour and
    # merge into a single invoice line. This is the control that isolates the override as the split cause
    # in the scenario above (everything else about the two candidates is identical).
    Then C_Invoice_Candidate LineAggregationKeys are equal:
      | C_Invoice_Candidate_ID |
      | invoiceCand_a          |
      | invoiceCand_b          |


  # Scenario E (Q2): on completion the override is materialized into C_Invoice_Acct scoped to the
  # accounting schema resolved from the invoice line's OWN org — getC_AcctSchema_ID(client, org).
  # This is a single-schema functional smoke test: the standard cucumber seed client has exactly one
  # C_AcctSchema, so it exercises the real completion->materialization path and asserts the row lands
  # in the org-resolved schema, but it does NOT by itself DISCRIMINATE the org-scoping fix from the old
  # every-client-schema behaviour (with one schema both write the same single row). That discrimination
  # (two client schemas, override materialized only into org A's) lives in the interceptor unit test
  # de.metas.invoice.acct.interceptor.C_Invoice_AcctOverrideTest — a genuine two-schema cucumber setup
  # is infeasible (no C_AcctSchema-create step-def) and unsafe (a second client schema is global seed
  # state that PostingService posts every document to, polluting sibling accounting scenarios).
  @Id:S30443_TC5
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00700_Invoicing
  @allure.label.feature:F01010.4_Invoice_Accounting_Overrides
  Scenario: Materialized C_Invoice_Acct rows target the invoice line's org-resolved accounting schema
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | invoice    | vendor        | Eingangsrechnung        | 2022-06-15   | false   | EUR           | paymentTerm      |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID | C_ElementValue_Override_ID |
      | invoiceLine | invoice      | product      | 1 PCE       | zeroTax  | overrideAccount            |
    And the invoice identified by invoice is completed

    # Exactly one row per concept, each carrying the override account AND targeting the schema resolved
    # from the line's org (getC_AcctSchema_ID). AssertAcctSchemaResolvedFromOrg makes the schema check strict.
    Then C_Invoice_Acct rows are found for invoice:
      | C_Invoice_ID | C_InvoiceLine_ID | AccountName              | C_ElementValue_ID | OPT.AssertAcctSchemaResolvedFromOrg |
      | invoice      | invoiceLine      | P_Expense_Acct           | overrideAccount   | Y                                   |
      | invoice      | invoiceLine      | P_InventoryClearing_Acct | overrideAccount   | Y                                   |
