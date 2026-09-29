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

    And metasfresh contains M_Product_Category:
      | Identifier    | Name  | Value          |
      | goodsCategory | Ware  | WareS32353D14  |
      | pfandCategory | Pfand | PfandS32353D14 |

    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | goodsProduct    | goodsCategory                        | Y      | Y           |
      | pfandProduct    | pfandCategory                        | Y      | Y           |
      | discountProduct | goodsCategory                        | Y      | Y           |

    And metasfresh contains C_TaxCategory
      | Identifier          |
      | contractTaxCategory |
    And metasfresh contains C_Tax
      | Identifier  | C_TaxCategory_ID    | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | contractTax | contractTaxCategory | 19   | DE                       | DE                        |
    And metasfresh contains C_VAT_Codes:
      | Identifier   | C_Tax_ID    | IsSOTrx | AmountType |
      | sales19_T    | contractTax | Y       | T          |
      | sales19_N    | contractTax | Y       | N          |
      | purchase19_T | contractTax | N       | T          |
      | purchase19_N | contractTax | N       | N          |

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
      | pp_so_goods    | soPLV                             | goodsProduct            | 1000     | PCE               | contractTaxCategory |
      | pp_so_pfand    | soPLV                             | pfandProduct            | 200      | PCE               | contractTaxCategory |
      | pp_so_discount | soPLV                             | discountProduct         | 1        | PCE               | contractTaxCategory |
      | pp_po_goods    | poPLV                             | goodsProduct            | 1000     | PCE               | contractTaxCategory |
      | pp_po_pfand    | poPLV                             | pfandProduct            | 200      | PCE               | contractTaxCategory |
      | pp_po_discount | poPLV                             | discountProduct         | 1        | PCE               | contractTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |

    And metasfresh contains C_BPartners without locations:
      | Identifier        | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | customerBP        | Y          | N        | contractPS                    |
      | vendorSalemfrucht | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerBP_loc        | customerBP               | Y               | Y               |
      | vendorSalemfrucht_loc | vendorSalemfrucht        | Y               | Y               |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name              | OPT.IsAdditive |
      | soSchema   | Bonus Ware        | true           |
      | poSchema   | Bonus Salemfrucht | true           |
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
      | soTerm     | soConditions                        | customerBP                  | 2026-06-15 | 2026-12-31 | DR            | false         |
      | poTerm     | poConditions                        | vendorSalemfrucht           | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by soTerm is completed
    And the C_Flatrate_Term identified by poTerm is completed

    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |

  # ##############################################################################################
  # Real-world drop-ship case: SO completes -> dropship PO is auto-created and auto-completed; the
  # SO's own contract discount ("Bonus Ware") does NOT need a vendor and is NOT copied to the PO;
  # the PO's own contract ("Bonus Salemfrucht", matched on its own bill partner = the vendor)
  # computes an equal discount on the goods-only base (same prices on both sides). Both discount
  # lines are then invoiced with the discount product's tax and posted -- the SO's to the product's
  # revenue account, the PO's to the product's expense account.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32353_TC34
  Scenario: Drop-ship SO with its own contract exempts the discount line from the vendor check and the PO gets its own contract group
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | PreparationDate      | M_Warehouse_ID.Identifier |
      | orderTS1D  | true    | customerBP               | 2026-07-01  | 2026-06-30T22:00:00Z | dropshipWarehouse         |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_goods   | orderTS1D             | goodsProduct            | 1          | vendorSalemfrucht               |
      | ol_pfand   | orderTS1D             | pfandProduct            | 1          | vendorSalemfrucht               |

    And the order identified by orderTS1D is completed

    # SO side: the contract group ("Bonus Ware") is built on the goods-only base -- Pfand excluded
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soDiscount             | orderTS1D             | discountProduct         | 1          | true                        | 3                               | -30   | soTerm                            |
    And the order identified by orderTS1D has 3 order lines

    # The dropship PO is auto-created and auto-completed in the same transaction as the SO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poTS1D         | orderTS1D                | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID     |
      | poTS1D     | vendorSalemfrucht |

    # PO side: its OWN contract ("Bonus Salemfrucht") fires on the PO's own completion, matched on the
    # PO's own bill partner (the vendor). The base mirrors the SO's (same prices on poPL), so the amount
    # is equal (-30) -- but it is a DIFFERENT group, on a DIFFERENT term. Registers ol_poGoods/
    # ol_poDiscount for the invoicing steps below.
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_poGoods                | poTS1D                | goodsProduct            | 1          | false                       |                                 |       |                                   |
      | ol_poDiscount             | poTS1D                | discountProduct         | 1          | true                        | 3                               | -30   | poTerm                            |
    # exactly 3 lines (goods + pfand + discount) -- NOT 4: the SO's own "Bonus Ware" line was never copied here
    And the order identified by poTS1D has 3 order lines

    # ##########################################################################################
    # Invoice the SO's contract group ("Bonus Ware") -- the group must be invoiced as a whole
    # ##########################################################################################
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_goods       | ic_soGoods             |
      | ol_soDiscount  | ic_soDiscount          |

    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override |
      | ic_soGoods             | I                        |
      | ic_soDiscount          | I                        |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_soGoods                        |
      | ic_soDiscount                     |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invSO                   | ic_soDiscount                     |

    # the discount line's revenue leg posts with the discount product's own tax (contractTax) --
    # the natural negative sign is kept on the credit side (a negative sales line, not a flipped debit);
    # every other Fact_Acct row of this invoice (Receivable, the goods product's revenue leg, the
    # tax leg) is accepted without asserting its amount via the wildcard row
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID | Record_ID | M_Product_ID    | C_Tax_ID    | C_VAT_Code_ID |
      | P_Revenue_Acct        |             | -30 EUR     | customerBP    | invSO     | discountProduct | contractTax | sales19_N     |
      | *                     |             |             |               | invSO     |                 |             |               |

    # ##########################################################################################
    # Invoice the PO's contract group ("Bonus Salemfrucht") via the PO's own invoice candidates
    # ##########################################################################################
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_poGoods     | ic_poGoods             |
      | ol_poDiscount  | ic_poDiscount          |

    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override |
      | ic_poGoods             | I                        |
      | ic_poDiscount          | I                        |

    # IgnoreInvoiceSchedule=Y: the auto-created PO's purchase-side invoice schedule is evaluated
    # against the real wall-clock date, not the frozen simulated one, and would otherwise skip a
    # candidate whose effective billable-from date the schedule computes as "in the future"
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IgnoreInvoiceSchedule |
      | ic_poGoods                        | Y                         |
      | ic_poDiscount                     | Y                         |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invPO                   | ic_poDiscount                     |

    # the discount line's expense leg posts with the discount product's own tax (contractTax) --
    # the natural negative sign is kept on the debit side (a negative purchase line, not a flipped credit)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | C_BPartner_ID     | Record_ID | M_Product_ID    | C_Tax_ID    | C_VAT_Code_ID |
      | P_Expense_Acct        | -30 EUR     |             | vendorSalemfrucht | invPO     | discountProduct | contractTax | purchase19_N  |
      | *                     |             |             |                   | invPO     |                 |             |               |
