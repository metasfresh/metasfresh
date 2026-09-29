@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract-triggered compensation group on a drop-ship sales order AND its auto-created purchase order
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    And documents are accounted immediately

    # unique per run (auto-generated Value/Name, never a fixed literal): a category row must never be
    # reused from an earlier run on a long-lived local stack -- the accounts overridden below are
    # copied onto a product ONLY at the moment the product is created (MProduct.insert_Accounting),
    # so a stale, already-overridden category from a previous run would let this pass without the
    # override below ever having run on THIS category.
    And metasfresh contains M_Product_Categories:
      | Identifier       |
      | goodsCategory    |
      | pfandCategory    |
      | discountCategory |

    # stand-ins for the customer's real chart-of-accounts 4750 (sales rebate) / 5750 (purchase rebate)
    # accounts -- the discount product's own accounts, distinct from the goods products' default ones.
    # 5751, not 5750: the seed chart of accounts already has an unrelated "5750 Quellensteuer"
    # (withholding tax) account, so 5750 would silently reuse it instead of getting its own stand-in.
    And metasfresh contains C_ElementValues:
      | Identifier          | Value |
      | discountRevenueAcct | 4750  |
      | discountExpenseAcct | 5751  |
    # applied BEFORE discountProduct is created below: a product's M_Product_Acct row is copied from
    # its category's M_Product_Category_Acct only once, at product-creation time -- overriding the
    # category afterwards has no effect on an already-created product.
    And metasfresh contains M_Product_Category_Acct overrides:
      | M_Product_Category_ID | OPT.P_Revenue_Acct  | OPT.P_Expense_Acct  |
      | discountCategory      | discountRevenueAcct | discountExpenseAcct |

    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1         | goodsCategory                        | Y      | Y           |
      | elstar2         | goodsCategory                        | Y      | Y           |
      | gala            | goodsCategory                        | Y      | Y           |
      | pfand1          | pfandCategory                        | Y      | Y           |
      | pfand2          | pfandCategory                        | Y      | Y           |
      | discountProduct | discountCategory                     | Y      | Y           |

    And metasfresh contains C_TaxCategory
      | Identifier          |
      | contractTaxCategory |
      | discountTaxCategory |
    And metasfresh contains C_Tax
      | Identifier  | C_TaxCategory_ID    | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | contractTax | contractTaxCategory | 19   | DE                       | DE                        |
      | discountTax | discountTaxCategory | 7    | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier   | C_Tax_ID    | IsSOTrx | AmountType |
      | sales19_T    | contractTax | Y       | T          |
      | sales19_N    | contractTax | Y       | N          |
      | purchase19_T | contractTax | N       | T          |
      | purchase19_N | contractTax | N       | N          |
      | sales7_T     | discountTax | Y       | T          |
      | sales7_N     | discountTax | Y       | N          |
      | purchase7_T  | discountTax | N       | T          |
      | purchase7_N  | discountTax | N       | N          |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name | SOTrx | IsTaxIncluded | PricePrecision |
      | soPL       | contractPS                    | DE                        | EUR                 | soPL | true  | false         | 2              |
      | poPL       | contractPS                    | DE                        | EUR                 | poPL | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name  | ValidFrom  |
      | soPLV      | soPL                      | soPLV | 2026-01-01 |
      | poPLV      | poPL                      | poPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_elstar1  | soPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_so_elstar2  | soPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_so_gala     | soPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_so_pfand1   | soPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_so_pfand2   | soPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_so_discount | soPLV                             | discountProduct         | 1        | PCE               | discountTaxCategory |
      | pp_po_elstar1  | poPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_po_elstar2  | poPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_po_gala     | poPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_po_pfand1   | poPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_po_pfand2   | poPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_po_discount | poPLV                             | discountProduct         | 1        | PCE               | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |

    # store / head-office split: the order's own partner is the store, but its bill location belongs
    # to the head office -- the contract is matched on the EFFECTIVE bill partner
    And metasfresh contains C_BPartners:
      | Identifier   | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | headOfficeBP | Y              | contractPS                    |
      | storeBP      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorBP   | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorBP_loc | vendorBP                 | Y               | Y               |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name            | OPT.IsAdditive |
      | soSchema   | Bonus Ware      | true           |
      | poSchema   | Bonus Lieferant | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier   | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | soSchemaLine | soSchema                                 | discountProduct         | 3                         | goodsCategory                        |
      | poSchemaLine | poSchema                                 | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name        | C_CompensationGroup_Schema_ID.Identifier |
      | soSettings | SO settings | soSchema                                 |
      | poSettings | PO settings | poSchema                                 |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | soSettings                                         | docTypeSalesOrder       |
      | poSettings                                         | docTypePurchaseOrder    |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier   | Name          | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | soConditions | SO conditions | CompensationGroup | zeroDurTrans                            | soSettings                                             |
      | poConditions | PO conditions | CompensationGroup | zeroDurTrans                            | poSettings                                             |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | soTerm     | soConditions                        | headOfficeBP                | 2026-06-15 | 2026-12-31 | DR            | false         |
      | poTerm     | poConditions                        | vendorBP                    | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by soTerm is completed
    And the C_Flatrate_Term identified by poTerm is completed

    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |

  # ##############################################################################################
  # Real-world drop-ship case (5 goods/Pfand lines, plus a purchase-side contract with the
  # vendor): SO completes -> dropship PO is auto-created and auto-completed; the SO's own contract
  # discount ("Bonus Ware") does NOT need a vendor and is NOT copied to the PO; the PO's own contract
  # ("Bonus Lieferant", matched on its own bill partner = the vendor) computes an equal discount on
  # the goods-only base (same prices on both sides). Both discount lines are then invoiced with the
  # discount product's OWN tax (a different rate than the goods) and posted to the discount product's
  # OWN accounts (stand-ins for 4750/5750) -- the SO's to the revenue account, the PO's to the expense
  # account.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC34
  Scenario: Drop-ship SO with its own contract exempts the discount line from the vendor check and the PO gets its own contract group
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderTS1D  | true    | storeBP                  | storeBP                               | 2026-07-01  | 2026-06-30T22:00:00Z | headOfficeBP                    | dropshipWarehouse         |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderTS1D             | elstar1                 | 1          | vendorBP                        |
      | ol_elstar2 | orderTS1D             | elstar2                 | 1          | vendorBP                        |
      | ol_gala    | orderTS1D             | gala                    | 1          | vendorBP                        |
      | ol_pfand1  | orderTS1D             | pfand1                  | 1          | vendorBP                        |
      | ol_pfand2  | orderTS1D             | pfand2                  | 1          | vendorBP                        |

    And the order identified by orderTS1D is completed

    # SO side: the contract group ("Bonus Ware") is built on the goods-only base -- Pfand excluded.
    # 3% of 921.60 + 672.00 + 561.60 = 64.656, rounded to -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soDiscount             | orderTS1D             | discountProduct         | 1          | true                        | 3                               | -64.66 | soTerm                            |
    And the order identified by orderTS1D has 6 order lines

    # The dropship PO is auto-created and auto-completed in the same transaction as the SO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poTS1D         | orderTS1D                | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | poTS1D     | vendorBP      |

    # PO side: its OWN contract ("Bonus Lieferant") fires on the PO's own completion, matched on the
    # PO's own bill partner (the vendor). The base mirrors the SO's (same prices on poPL), so the
    # amount is equal (-64.66) -- but it is a DIFFERENT group, on a DIFFERENT term. Registers every
    # PO line for the invoicing steps below.
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_poElstar1              | poTS1D                | elstar1                 | 1          | false                       |                                 |        |                                   |
      | ol_poElstar2              | poTS1D                | elstar2                 | 1          | false                       |                                 |        |                                   |
      | ol_poGala                 | poTS1D                | gala                    | 1          | false                       |                                 |        |                                   |
      | ol_poPfand1               | poTS1D                | pfand1                  | 1          | false                       |                                 |        |                                   |
      | ol_poPfand2               | poTS1D                | pfand2                  | 1          | false                       |                                 |        |                                   |
      | ol_poDiscount             | poTS1D                | discountProduct         | 1          | true                        | 3                               | -64.66 | poTerm                            |
    # exactly 6 lines (3 goods + 2 Pfand + discount) -- NOT 7: the SO's own "Bonus Ware" line was
    # never copied here
    And the order identified by poTS1D has 6 order lines

    # ##########################################################################################
    # Invoice the SO's contract group ("Bonus Ware") -- the whole order is invoiced together, as a
    # real accountant would
    # ##########################################################################################
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_elstar1     | ic_soElstar1           |
      | ol_elstar2     | ic_soElstar2           |
      | ol_gala        | ic_soGala              |
      | ol_pfand1      | ic_soPfand1            |
      | ol_pfand2      | ic_soPfand2            |
      | ol_soDiscount  | ic_soDiscount          |

    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override |
      | ic_soElstar1           | I                        |
      | ic_soElstar2           | I                        |
      | ic_soGala              | I                        |
      | ic_soPfand1            | I                        |
      | ic_soPfand2            | I                        |
      | ic_soDiscount          | I                        |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_soElstar1                      |
      | ic_soElstar2                      |
      | ic_soGala                         |
      | ic_soPfand1                       |
      | ic_soPfand2                       |
      | ic_soDiscount                     |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invSO                   | ic_soDiscount                     |

    # the discount line posts with its OWN tax (discountTax, 7% -- distinct from the goods' 19%) and
    # its OWN account (the 4750 stand-in, distinct from the goods products' default revenue account);
    # the natural negative sign is kept on the credit side (a negative sales line, not a flipped
    # debit); every other Fact_Acct row of this invoice (Receivable, the goods products' revenue
    # legs, the tax legs) is accepted without asserting its amount via the wildcard row
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID          | C_BPartner_ID | Record_ID | M_Product_ID    | C_Tax_ID    | C_VAT_Code_ID |
      | P_Revenue_Acct        |             | -64.66 EUR  | discountRevenueAcct | headOfficeBP  | invSO     | discountProduct | discountTax | sales7_N      |
      | *                     |             |             |                     |               | invSO     |                 |             |               |

    # ##########################################################################################
    # Invoice the PO's contract group ("Bonus Lieferant") via the PO's own invoice candidates
    # ##########################################################################################
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_poElstar1   | ic_poElstar1           |
      | ol_poElstar2   | ic_poElstar2           |
      | ol_poGala      | ic_poGala              |
      | ol_poPfand1    | ic_poPfand1            |
      | ol_poPfand2    | ic_poPfand2            |
      | ol_poDiscount  | ic_poDiscount          |

    # the past DateToInvoice_Override clears the date gate directly: the auto-created PO's DateOrdered
    # is stamped from the real wall clock (MOrder's defaulting constructor bypasses the Background's
    # simulated 2026-07-01 clock), so its InvoiceRule=Immediate candidates would otherwise compute a
    # DateToInvoice the schedule sees as "in the future" relative to the simulated today
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override | OPT.DateToInvoice_Override |
      | ic_poElstar1           | I                        | 2026-07-01                 |
      | ic_poElstar2           | I                        | 2026-07-01                 |
      | ic_poGala              | I                        | 2026-07-01                 |
      | ic_poPfand1            | I                        | 2026-07-01                 |
      | ic_poPfand2            | I                        | 2026-07-01                 |
      | ic_poDiscount          | I                        | 2026-07-01                 |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_poElstar1                      |
      | ic_poElstar2                      |
      | ic_poGala                         |
      | ic_poPfand1                       |
      | ic_poPfand2                       |
      | ic_poDiscount                     |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invPO                   | ic_poDiscount                     |

    # the discount line posts with its OWN tax (discountTax) and its OWN account (discountExpenseAcct,
    # the 5750 stand-in); the natural negative sign is kept on the debit side (a negative purchase
    # line, not a flipped credit)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID          | C_BPartner_ID | Record_ID | M_Product_ID    | C_Tax_ID    | C_VAT_Code_ID |
      | P_Expense_Acct        | -64.66 EUR  |             | discountExpenseAcct | vendorBP      | invPO     | discountProduct | discountTax | purchase7_N   |
      | *                     |             |             |                     |               | invPO     |                 |             |               |

  # ##############################################################################################
  # Regression for the exclusion filter's registration (MainValidator.registerFactories()): it must
  # stay SQL-translatable so it does NOT cache a stale "which groups have a contract" snapshot across
  # orders in the same JVM -- IC_Order_CreatePOFromSOsDAO holds this filter for the JVM's lifetime.
  # Self-contained: TWO independent drop-ship SOs within this one scenario -- order1/po1 "warms"
  # the filter first, then order2's own, brand-new contract group is created strictly AFTER that
  # filter use. If the filter cached order1's snapshot instead of re-querying, order2's own
  # discount line would wrongly slip through onto its own PO. No dependency on any other
  # scenario's run order.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC35
  Scenario: A second drop-ship SO's own contract group is still excluded after an earlier drop-ship PO already used the filter
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | order1     | true    | storeBP                  | storeBP                               | 2026-07-01  | 2026-06-30T22:00:00Z | headOfficeBP                    | dropshipWarehouse         |

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol1_elstar1 | order1                | elstar1                 | 1          | vendorBP                        |

    And the order identified by order1 is completed

    # order1's own "Bonus Ware" group (3% of 921.60 = -27.65) -- completing its PO below is what
    # "warms" the filter registration for the first time in this JVM
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol1_soDiscount            | order1                | discountProduct         | 1          | true                        | 3                               | -27.65 | soTerm                            |
    And the order identified by order1 has 2 order lines

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po1            | order1                   | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | po1        | vendorBP      |
    And the order identified by po1 has 2 order lines

    # a second, independent drop-ship SO, created and completed strictly AFTER po1 above already
    # used the filter once
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | order2     | true    | storeBP                  | storeBP                               | 2026-07-01  | 2026-06-30T22:00:00Z | headOfficeBP                    | dropshipWarehouse         |

    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol2_elstar2 | order2                | elstar2                 | 1          | vendorBP                        |

    And the order identified by order2 is completed

    # order2's own, brand-new "Bonus Ware" group (3% of 672.00 = -20.16)
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol2_soDiscount            | order2                | discountProduct         | 1          | true                        | 3                               | -20.16 | soTerm                            |
    And the order identified by order2 has 2 order lines

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | po2            | order2                   | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | po2        | vendorBP      |

    # exactly 2 lines (goods + order2's own discount) -- NOT 3: proves the filter re-evaluated
    # fresh for order2's own, brand-new contract group rather than reusing po1's cached snapshot
    And the order identified by po2 has 2 order lines
