@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Compensation-group contract take-over of the customer's periodic refund percentages
## F2070: Compensation Group Contract
## A drop-ship vendor's take-over record lists customer refund conditions; their percentages are added to the purchase order's take-over line.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name | Value                               |
      | goodsCategory | Ware | WareCompGroupContractRefundTakeOver |

    # drop-ship order setup shared by the scenarios: taxes, prices, document types, partners
    And temporarily set sys config boolean value false for sys config "AUTO_SHIP_AND_INVOICE"
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
    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |
    # the store is the order partner, the head office is the invoice partner and holds the contracts
    And metasfresh contains C_BPartners:
      | Identifier         | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier     | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship           | Y               | Y               |
    # the refund contracts' invoice schedules, as in the refund reference case
    And metasfresh contains C_InvoiceSchedules:
      | Identifier        | InvoiceDay | InvoiceDistance |
      | quarterlySchedule | 31         | 3               |
      | yearlySchedule    | 31         | 12              |

    # vendor-side setup shared by all scenarios
    And temporarily set sys config boolean value true for sys config "SKIP_WP_PROCESSOR_FOR_AUTOMATION"
    # categories unique per run: the discount product's accounts are copied from its category only at
    # product creation, so the override must be applied to a fresh category before the product exists
    And metasfresh contains M_Product_Categories:
      | Identifier       |
      | pfandCategory    |
      | discountCategory |
    And metasfresh contains C_ElementValues:
      | Identifier          | Value |
      | discountRevenueAcct | 4750  |
      | discountExpenseAcct | 5751  |
    And metasfresh contains M_Product_Category_Acct overrides:
      | M_Product_Category_ID | OPT.P_Revenue_Acct  | OPT.P_Expense_Acct  |
      | discountCategory      | discountRevenueAcct | discountExpenseAcct |

    And metasfresh contains M_Products:
      | Identifier          | Name          | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1             | Elstar 1      | goodsCategory                        | Y      | Y           |
      | elstar2             | Elstar 2      | goodsCategory                        | Y      | Y           |
      | gala                | Gala          | goodsCategory                        | Y      | Y           |
      | pfand1              | Pfand 1       | pfandCategory                        | Y      | Y           |
      | pfand2              | Pfand 2       | pfandCategory                        | Y      | Y           |
      | bonusWare           | Bonus Ware    | discountCategory                     | Y      | Y           |
      | bonusVendorDropship | Bonus Vendor  | discountCategory                     | Y      | Y           |
      | bonusRvWare         | Bonus RV Ware | discountCategory                     | Y      | Y           |

    # purchase price = sales price
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_elstar1     | soPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_so_elstar2     | soPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_so_gala        | soPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_so_pfand1      | soPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_so_pfand2      | soPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_so_bonusWare   | soPLV                             | bonusWare               | 1        | PCE               | discountTaxCategory |
      | pp_so_bonusVendor | soPLV                             | bonusVendorDropship     | 1        | PCE               | discountTaxCategory |
      | pp_so_bonusRvWare | soPLV                             | bonusRvWare             | 1        | PCE               | discountTaxCategory |
      | pp_po_elstar1     | poPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_po_elstar2     | poPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_po_gala        | poPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_po_pfand1      | poPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_po_pfand2      | poPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_po_bonusWare   | poPLV                             | bonusWare               | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusVendor | poPLV                             | bonusVendorDropship     | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusRvWare | poPLV                             | bonusRvWare             | 1        | PCE               | discountTaxCategory |

    # the drop-ship vendor's own 3% "Bonus Vendor" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier   | Name         | OPT.IsAdditive |
      | vendorSchema | Bonus Vendor | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier       | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorSchemaLine | vendorSchema                             | bonusVendorDropship     | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier     | Name            | C_CompensationGroup_Schema_ID.Identifier |
      | vendorSettings | Vendor settings | vendorSchema                             |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | vendorSettings                                     | docTypePurchaseOrder    |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier       | Name              | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | vendorConditions | Vendor conditions | CompensationGroup | zeroDurTrans                            | vendorSettings                                         |
    # open-ended vendor term: the drop-ship PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | vendorTerm | vendorConditions                    | vendorDropship              | 2026-06-15 | 2099-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by vendorTerm is completed

    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |


  # ##############################################################################################
  # Real-world shape (store orders, head office billed): the store orders, the head office is billed and holds
  # the on-invoice 3% "Bonus Ware" contract plus three periodic refund contracts, one per periodic bonus:
  # "RV Ware 1 % vierteljaehrlich", "RV Ware 0,125 % jaehrlich" (goods) and "RV Verpackung 0,3 % vierteljaehrlich" (packaging),
  # each with one untiered percentage setting on the goods category.
  # The drop-ship vendor has its own 3% "Ware" contract plus a take-over record on "Ware" listing "Bonus Ware"
  # and the two goods refund conditions (never the packaging one).
  # SO completes -> drop-ship PO: the vendor's own 3% line (-64.66) AND the take-over line 3 + 1 + 0.125 = 4.125% -> 4.13%
  # (2 decimals, half up) = -89.01 (4.13% of 2155.20 = 89.010), together 7.13% / -153.67 on 2155.20 goods; no discount on the 2 Pfand lines;
  # the purchase invoice carries both discount lines.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS1
  Scenario: Drop-ship PO takes over the head office's 3% contract bonus plus its listed 1% and 0.125% goods refund contracts as one 4.13% take-over line next to the vendor's own 3% line
    Given documents are accounted immediately

    # the packaging refund contract's bonus product (the other products are created in the Background)
    And metasfresh contains M_Products:
      | Identifier  | Name          | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusRvPack | Bonus RV Pack | discountCategory                     | Y      | Y           |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_bonusRvPack | soPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusRvPack | poPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's periodic bonuses: one refund contract per bonus, each with one untiered percentage setting on the goods category
    # (names unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                           | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS1         | Refund          |
      | rvWare0125 | RV Ware 0,125 % jährlich S32357_TS1            | Refund          |
      | rvPack03   | RV Verpackung 0,3 % vierteljährlich S32357_TS1 | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1    | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
      | cfgWare0125 | rvWare0125               | yearlySchedule       | 0.125         | goodsCategory         | bonusRvWare      |
      | cfgPack03   | rvPack03                 | quarterlySchedule    | 0.3           | goodsCategory         | bonusRvPack      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1    | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termWare0125 | rvWare0125                          | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termPack03   | rvPack03                            | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    And the C_Flatrate_Term identified by termWare0125 is completed
    And the C_Flatrate_Term identified by termPack03 is completed

    # VendorDropship takes over the head office's "Bonus Ware" and its two goods refund conditions on the goods category
    # (never the packaging refund condition); the take-over line uses the vendor's own discount product "Bonus Vendor"
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |
      | takeOver                                         | rvWare0125               |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, as on the sample invoice
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    # (the periodic refund bonuses are settled later by the refund contracts, not on the sales order)
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # drop-ship PO for VendorDropship
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID  |
      | poDropship | vendorDropship |

    # PO lines: the vendor's own line (no base category) and the take-over line (base category "Ware"), both with "Bonus Vendor";
    # nothing is discounted on the Pfand lines
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    # exactly 7 lines (3 goods + 2 Pfand + the vendor's own discount line + the take-over line)
    And the order identified by poDropship has 7 order lines

    # the vendor's own line stays 3% (-64.66); the take-over line carries 3% "Bonus Ware" + 1% + 0.125% of the listed goods refund contracts
    # = 4.125% -> 4.13% (2 decimals, half up) -> 4.13% of 2155.20 = 89.010 -> -89.01; together -153.67 (= 7.13%);
    # the packaging refund contract (0.3%) is not listed and not taken over
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                                                      |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                                                                      |
      | ol_poTakeOver            | 4.13                            | -89.01         | 3% Bonus Ware + 1% RV Ware 1 % vierteljährlich S32357_TS1 + 0.125% RV Ware 0,125 % jährlich S32357_TS1 |
    # PO total: goods + Pfand 2757.36 - 153.67 = 2603.69 net; tax 19% of 2757.36 = 523.90, 7% of -153.67 = -10.76 -> 3116.83
    And validate the created orders
      | C_Order_ID | GrandTotal |
      | poDropship | 3116.83    |

    # purchase invoice: carries both discount lines, each posted to the discount product's own expense account
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID           | C_Invoice_Candidate_ID   |
      | ol_poElstar1             | ic_poElstar1             |
      | ol_poElstar2             | ic_poElstar2             |
      | ol_poGala                | ic_poGala                |
      | ol_poPfand1              | ic_poPfand1              |
      | ol_poPfand2              | ic_poPfand2              |
      | ol_poBonusVendorDropship | ic_poBonusVendorDropship |
      | ol_poTakeOver            | ic_poTakeOver            |
    # the past DateToInvoice_Override clears the date gate: the auto-created PO's DateOrdered is the real wall clock
    And update invoice candidates
      | C_Invoice_Candidate_ID   | OPT.InvoiceRule_Override | OPT.DateToInvoice_Override |
      | ic_poElstar1             | I                        | 2026-07-01                 |
      | ic_poElstar2             | I                        | 2026-07-01                 |
      | ic_poGala                | I                        | 2026-07-01                 |
      | ic_poPfand1              | I                        | 2026-07-01                 |
      | ic_poPfand2              | I                        | 2026-07-01                 |
      | ic_poBonusVendorDropship | I                        | 2026-07-01                 |
      | ic_poTakeOver            | I                        | 2026-07-01                 |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_poElstar1                      |
      | ic_poElstar2                      |
      | ic_poGala                         |
      | ic_poPfand1                       |
      | ic_poPfand2                       |
      | ic_poBonusVendorDropship          |
      | ic_poTakeOver                     |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invPO                   | ic_poBonusVendorDropship          |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Account_ID          | C_BPartner_ID  | Record_ID | M_Product_ID        | C_Tax_ID    | C_VAT_Code_ID |
      | P_Expense_Acct        | -64.66 EUR  |             | discountExpenseAcct | vendorDropship | invPO     | bonusVendorDropship | discountTax | purchase7_N   |
      | P_Expense_Acct        | -89.01 EUR  |             | discountExpenseAcct | vendorDropship | invPO     | bonusVendorDropship | discountTax | purchase7_N   |
      | *                     |             |             |                     |                | invPO     |                     |             |               |


  # ##############################################################################################
  # As TS1, but the take-over record keeps 3 decimals: the take-over line's 3 + 1 + 0.125 = 4.125% is not rounded
  # -> 4.125% of 2155.20 = 88.902 -> -88.90; together with the vendor's own 3% line -153.56 on 2155.20 goods.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS1b
  Scenario: A take-over record with 3 decimals keeps the take-over line's summed 4.125% unrounded
    Given documents are accounted immediately

    # the packaging refund contract's bonus product (the other products are created in the Background)
    And metasfresh contains M_Products:
      | Identifier  | Name          | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusRvPack | Bonus RV Pack | discountCategory                     | Y      | Y           |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_bonusRvPack | soPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusRvPack | poPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's periodic bonuses: one refund contract per bonus, each with one untiered percentage setting on the goods category
    # (names unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                            | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS1b         | Refund          |
      | rvWare0125 | RV Ware 0,125 % jährlich S32357_TS1b            | Refund          |
      | rvPack03   | RV Verpackung 0,3 % vierteljährlich S32357_TS1b | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1    | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
      | cfgWare0125 | rvWare0125               | yearlySchedule       | 0.125         | goodsCategory         | bonusRvWare      |
      | cfgPack03   | rvPack03                 | quarterlySchedule    | 0.3           | goodsCategory         | bonusRvPack      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1    | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termWare0125 | rvWare0125                          | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termPack03   | rvPack03                            | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    And the C_Flatrate_Term identified by termWare0125 is completed
    And the C_Flatrate_Term identified by termPack03 is completed

    # VendorDropship takes over the head office's "Bonus Ware" and its two goods refund conditions on the goods category
    # (never the packaging refund condition); the take-over line uses the vendor's own discount product "Bonus Vendor"
    # the take-over record rounds its summed percentage to 3 decimals
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        | OPT.PercentagePrecision |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship | 3                       |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |
      | takeOver                                         | rvWare0125               |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, as on the sample invoice
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    # (the periodic refund bonuses are settled later by the refund contracts, not on the sales order)
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # drop-ship PO for VendorDropship
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID  |
      | poDropship | vendorDropship |

    # PO lines: the vendor's own line (no base category) and the take-over line (base category "Ware"), both with "Bonus Vendor";
    # nothing is discounted on the Pfand lines
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    # exactly 7 lines (3 goods + 2 Pfand + the vendor's own discount line + the take-over line)
    And the order identified by poDropship has 7 order lines

    # the vendor's own line stays 3% (-64.66); the take-over line carries 3% "Bonus Ware" + 1% + 0.125% of the listed goods refund contracts
    # = 4.125%, kept at 3 decimals -> 4.125% of 2155.20 = 88.902 -> -88.90; together -153.56
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                                                        |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                                                                        |
      | ol_poTakeOver            | 4.125                           | -88.90         | 3% Bonus Ware + 1% RV Ware 1 % vierteljährlich S32357_TS1b + 0.125% RV Ware 0,125 % jährlich S32357_TS1b |
    # PO total: goods + Pfand 2757.36 - 153.56 = 2603.80 net; tax 19% of 2757.36 = 523.90, 7% of -153.56 = -10.75 -> 3116.95
    And validate the created orders
      | C_Order_ID | GrandTotal |
      | poDropship | 3116.95    |


  # ##############################################################################################
  # AC7b: the refund percentages reach the drop-ship PO only while its sales order completes or is completed.
  # Refund-only customer: the head office has no "Bonus Ware" contract, only the two listed goods refund contracts (1% + 0.125%).
  # The drop-ship PO is left drafted (CompleteDropshipPO = N) and completed later:
  # - TS2_reactivatedSO_draftPO: the SO is reactivated before the PO is completed -> the PO takes over no refund %:
  #   only the vendor's own 3% line (-64.66), no take-over line.
  # - TS2_completedSO_draftPO: the SO stays completed -> take-over line 1 + 0.125 = 1.125% -> 1.13% (2 decimals, half up)
  #   = 1.13% of 2155.20 = 24.354 -> -24.35, next to the vendor's own 3% line (-64.66).
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_reactivatedSO_draftPO
  Scenario: A drop-ship PO completed while its sales order is reactivated takes over no refund percentage
    Given temporarily set sys config boolean value false for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the head office's two goods refund contracts (names unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                    | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2r | Refund          |
      | rvWare0125 | RV Ware 0,125 % jährlich S32357_TS2r    | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1    | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
      | cfgWare0125 | rvWare0125               | yearlySchedule       | 0.125         | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1    | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termWare0125 | rvWare0125                          | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    And the C_Flatrate_Term identified by termWare0125 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the two goods refund conditions
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |
      | takeOver                                         | rvWare0125               |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the drop-ship PO is left drafted (CompleteDropshipPO = N): no discount yet
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | DR            | true           |
    And the order identified by poDropship has 5 order lines

    # the SO is reactivated, then the drafted PO is completed: no refund % is taken over while the SO is not completed
    When the order identified by orderDropship is reactivated
    And the order identified by poDropship is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | 3                               | -64.66 | vendorTerm                        | null                                      |
    # exactly 6 lines: 3 goods + 2 Pfand + the vendor's own discount line; no take-over line
    And the order identified by poDropship has 6 order lines


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_completedSO_draftPO
  Scenario: A drop-ship PO completed later while its sales order is still completed takes over the refund percentages
    Given temporarily set sys config boolean value false for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the head office's two goods refund contracts (names unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                    | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2c | Refund          |
      | rvWare0125 | RV Ware 0,125 % jährlich S32357_TS2c    | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1    | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
      | cfgWare0125 | rvWare0125               | yearlySchedule       | 0.125         | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1    | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termWare0125 | rvWare0125                          | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    And the C_Flatrate_Term identified by termWare0125 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the two goods refund conditions
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |
      | takeOver                                         | rvWare0125               |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the drop-ship PO is left drafted (CompleteDropshipPO = N): no discount yet
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | DR            | true           |
    And the order identified by poDropship has 5 order lines

    # the SO stays completed; the drafted PO is completed later: the refund % are taken over
    When the order identified by poDropship is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # 1% + 0.125% = 1.125% -> 1.13% of 2155.20 = 24.354 -> -24.35
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                                          |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                                                          |
      | ol_poTakeOver            | 1.13                            | -24.35         | 1% RV Ware 1 % vierteljährlich S32357_TS2c + 0.125% RV Ware 0,125 % jährlich S32357_TS2c |


  # ##############################################################################################
  # AC7b, default path (CompleteDropshipPO = Y): the SO's completion completes the drop-ship PO, which takes over
  # the refund-only customer's 1% + 0.125% = 1.125% -> 1.13% (-24.35). Then the SO is reactivated, the PO is reactivated
  # and completed again while the SO is still reactivated -> the re-completed PO takes over no refund %:
  # only the vendor's own 3% line (-64.66), no take-over line.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_defaultPath
  Scenario: A drop-ship PO re-completed while its sales order is reactivated loses the taken-over refund percentages
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the head office's two goods refund contracts (names unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                               | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2_defaultPath | Refund          |
      | rvWare0125 | RV Ware 0,125 % jährlich S32357_TS2_defaultPath    | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1    | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
      | cfgWare0125 | rvWare0125               | yearlySchedule       | 0.125         | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1    | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termWare0125 | rvWare0125                          | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    And the C_Flatrate_Term identified by termWare0125 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the two goods refund conditions
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |
      | takeOver                                         | rvWare0125               |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion completes the drop-ship PO: the refund % are taken over
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # 1% + 0.125% = 1.125% -> 1.13% of 2155.20 = 24.354 -> -24.35
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                                                                |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                                                                                |
      | ol_poTakeOver            | 1.13                            | -24.35         | 1% RV Ware 1 % vierteljährlich S32357_TS2_defaultPath + 0.125% RV Ware 0,125 % jährlich S32357_TS2_defaultPath |

    # the SO is reactivated while its drop-ship PO is completed; then the PO is reactivated and completed again
    When the order identified by orderDropship is reactivated
    And the order identified by poDropship is reactivated
    And the order identified by poDropship is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1                         | poDropship            | elstar1                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poElstar2                         | poDropship            | elstar2                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poGala                            | poDropship            | gala                    | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand1                          | poDropship            | pfand1                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand2                          | poDropship            | pfand2                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poBonusVendorDropship_recompleted | poDropship            | bonusVendorDropship     | 1          | true                        | 3                               | -64.66 | vendorTerm                        | null                                      |
    # exactly 6 lines: 3 goods + 2 Pfand + the vendor's own discount line; no take-over line
    And the order identified by poDropship has 6 order lines


  # ##############################################################################################
  # AC3: only a valid refund term of the SO's invoice partner is taken over: DocStatus completed or closed, not voided,
  # SO date (2026-07-01) within StartDate..EndDate. Refund-only customer with one listed goods refund contract (1%):
  # a valid term -> take-over line 1% of 2155.20 = 21.552 -> -21.55 next to the vendor's own 3% line (-64.66);
  # an invalid term -> only the vendor's own 3% line, no take-over line.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_closed
  Scenario: A closed refund term of the invoice partner is taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the refund term is inserted already closed (DocStatus CL): no step closes a refund term; its empty ContractStatus
    # is irrelevant, the take-over only excludes ContractStatus Vo
    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                          | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2_closed | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | CL            | true          |

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # 1% of 2155.20 = 21.552 -> -21.55
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                  |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                  |
      | ol_poTakeOver            | 1                               | -21.55         | 1% RV Ware 1 % vierteljährlich S32357_TS2_closed |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_drafted
  Scenario: A drafted refund term of the invoice partner is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the refund term is left drafted (never completed)
    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                           | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2_drafted | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | 3                               | -64.66 | vendorTerm                        | null                                      |
    # exactly 6 lines: 3 goods + 2 Pfand + the vendor's own discount line; no take-over line
    And the order identified by poDropship has 6 order lines


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_voided
  Scenario: A refund term voided by its cancellation before its start date is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # cancelling a contract needs a status-change rule ("Gekuendigt") on its conditions' transition
    And metasfresh contains C_Contract_Change:
      | Identifier         | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | refundContractQuit | zeroDurTrans                        | ST     | Qu             | 0        | day          |

    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                          | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2_voided | Refund          | zeroDurTrans                            |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    # cancelled with a change date before its start date: the term becomes voided, its end date collapses to its start date
    And the C_Flatrate_Term identified by termWare1 is cancelled with change date 2026-06-01
    And the C_Flatrate_Term identified by termWare1 has the contract status and dates:
      | ContractStatus | EndDate    |
      | Vo             | 2026-07-01 |

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | 3                               | -64.66 | vendorTerm                        | null                                      |
    # exactly 6 lines: 3 goods + 2 Pfand + the vendor's own discount line; no take-over line
    And the order identified by poDropship has 6 order lines


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_cancelledBeforeSODate
  Scenario: A refund term cancelled with a change date before the SO date is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # cancelling a contract needs a status-change rule ("Gekuendigt") on its conditions' transition
    And metasfresh contains C_Contract_Change:
      | Identifier         | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | refundContractQuit | zeroDurTrans                        | ST     | Qu             | 0        | day          |

    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                         | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2_cancelledBeforeSODate | Refund          | zeroDurTrans                            |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-06-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    # cancelled as of 2026-06-20, before the SO date 2026-07-01: the term ends on 2026-06-20
    And the C_Flatrate_Term identified by termWare1 is cancelled with change date 2026-06-20
    And the C_Flatrate_Term identified by termWare1 has the contract status and dates:
      | ContractStatus | EndDate    |
      | Qu             | 2026-06-20 |

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | 3                               | -64.66 | vendorTerm                        | null                                      |
    # exactly 6 lines: 3 goods + 2 Pfand + the vendor's own discount line; no take-over line
    And the order identified by poDropship has 6 order lines


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_cancelledAfterSODate
  Scenario: A refund term cancelled with a change date after the SO date is still taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # cancelling a contract needs a status-change rule ("Gekuendigt") on its conditions' transition
    And metasfresh contains C_Contract_Change:
      | Identifier         | C_Flatrate_Transition_ID.Identifier | Action | ContractStatus | DeadLine | DeadLineUnit |
      | refundContractQuit | zeroDurTrans                        | ST     | Qu             | 0        | day          |

    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                        | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2_cancelledAfterSODate | Refund          | zeroDurTrans                            |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    # cancelled as of 2026-09-30, after the SO date 2026-07-01: the term still runs on the SO date
    And the C_Flatrate_Term identified by termWare1 is cancelled with change date 2026-09-30
    And the C_Flatrate_Term identified by termWare1 has the contract status and dates:
      | ContractStatus | EndDate    |
      | Qu             | 2026-09-30 |

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # 1% of 2155.20 = 21.552 -> -21.55
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                                |
      | ol_poTakeOver            | 1                               | -21.55         | 1% RV Ware 1 % vierteljährlich S32357_TS2_cancelledAfterSODate |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_soDateOutside
  Scenario: A refund term that starts after the SO date is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the refund term runs 2027-01-01..2027-12-31, the SO date 2026-07-01 lies outside
    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                 | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2_soDateOutside | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2027-01-01 | 2027-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | 3                               | -64.66 | vendorTerm                        | null                                      |
    # exactly 6 lines: 3 goods + 2 Pfand + the vendor's own discount line; no take-over line
    And the order identified by poDropship has 6 order lines


  # ##############################################################################################
  # TS2 (re-completion clause; complements AC7b): a re-completed SO takes over again. Refund-only customer (one listed goods refund contract, 1%):
  # the SO is completed, reactivated and completed again; the drop-ship PO completed inside the re-completion
  # carries the take-over line 1% (-21.55). The first PO is only registered to tell it apart; its lines and the
  # PO count are not asserted (existing behaviour, out of scope).
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS2_reCompletedSO
  Scenario: The drop-ship PO completed inside a re-completion of its sales order takes over the refund percentage
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                 | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS2_reCompletedSO | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's first completion creates and completes the first drop-ship PO; registered only to tell it apart below
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |

    # the SO is reactivated and completed again
    When the order identified by orderDropship is reactivated
    And the order identified by orderDropship is completed

    # the PO completed inside the SO's re-completion (the first PO is not a candidate)
    Then the order is created:
      | OPT.Identifier        | Link_Order_ID.Identifier | OPT.Exclude_C_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropshipRecompleted | orderDropship            | poDropship                        | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropshipRecompleted | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropshipRecompleted | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropshipRecompleted | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropshipRecompleted | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropshipRecompleted | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropshipRecompleted | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropshipRecompleted | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropshipRecompleted has 7 order lines
    # 1% of 2155.20 = 21.552 -> -21.55
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                         |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                         |
      | ol_poTakeOver            | 1                               | -21.55         | 1% RV Ware 1 % vierteljährlich S32357_TS2_reCompletedSO |


  # ##############################################################################################
  # AC4 (TS3): a listed refund condition counts only if it is untiered, percentage-based, for all products and without
  # packaging restriction: no product on the term, exactly one active setting, no product on it, RefundBase percentage,
  # MinQty 0, packaging filter off or on without packing options.
  # The head office has the on-invoice 3% "Bonus Ware" contract and one listed goods refund condition that does NOT qualify ->
  # the take-over line carries only the 3% "Bonus Ware" (-64.66), next to the vendor's own 3% line (-64.66).
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS3_tiered
  Scenario: A listed tiered refund condition (two settings, the second from 100 units) is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's goods refund contract is tiered: 1% from 0 units, 2% from 100 units
    # (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                          | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS3_tiered | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier    | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | MinQty |
      | cfgWare1      | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      | 0      |
      | cfgWare1Tier2 | rvWare1                  | quarterlySchedule    | 2             | goodsCategory         | bonusRvWare      | 100    |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the refund condition does not qualify: only the 3% "Bonus Ware" is taken over (-64.66)
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor |
      | ol_poTakeOver            | 3                               | -64.66         | 3% Bonus Ware   |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS3_singleSettingMinQty
  Scenario: A listed refund condition whose single setting starts at a minimum quantity is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's goods refund contract has one setting, from 10 units
    # (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                       | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS3_singleSettingMinQty | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | MinQty |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      | 10     |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the refund condition does not qualify: only the 3% "Bonus Ware" is taken over (-64.66)
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor |
      | ol_poTakeOver            | 3                               | -64.66         | 3% Bonus Ware   |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS3_amountBased
  Scenario: A listed amount-based refund condition is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's goods refund contract refunds an amount per unit (0.05 EUR), not a percentage
    # (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                    | Type_Conditions |
      | rvWare1    | RV Ware 0,05 EUR vierteljährlich S32357_TS3_amountBased | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundAmt | C_Currency.ISO_Code | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 0.05      | EUR                 | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the refund condition does not qualify: only the 3% "Bonus Ware" is taken over (-64.66)
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor |
      | ol_poTakeOver            | 3                               | -64.66         | 3% Bonus Ware   |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS3_productSpecificTerm
  Scenario: A listed refund condition whose term is for one product is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's refund contract is for the product "Elstar 1" only (product on the term)
    # (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                         | Type_Conditions |
      | rvWare1    | RV Elstar 1 % vierteljährlich S32357_TS3_productSpecificTerm | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | elstar1                     | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the refund condition does not qualify: only the 3% "Bonus Ware" is taken over (-64.66)
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor |
      | ol_poTakeOver            | 3                               | -64.66         | 3% Bonus Ware   |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS3_productSpecificSetting
  Scenario: A listed refund condition whose setting is for one product is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's refund contract's setting is for the product "Elstar 1" only (product on the setting)
    # (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                            | Type_Conditions |
      | rvWare1    | RV Elstar 1 % vierteljährlich S32357_TS3_productSpecificSetting | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | M_Product_ID |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      | elstar1      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the refund condition does not qualify: only the 3% "Bonus Ware" is taken over (-64.66)
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor |
      | ol_poTakeOver            | 3                               | -64.66         | 3% Bonus Ware   |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS3_packingFilterWithOption
  Scenario: A listed refund condition restricted to a listed packing option is not taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the crate (Pfandsteige) the goods can be delivered on (product name unique per run: one packing material per product)
    And metasfresh contains M_Products:
      | Identifier   | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | crateProduct | pfandCategory                        | Y      | Y           |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pmCrate                 | crateProduct |
    # the head office's goods refund contract only refunds goods delivered on the crate (packaging filter with one packing option)
    # (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                           | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS3_packingFilterWithOption | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsPackingOptionFiltered |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      | Y                       |
    And metasfresh contains C_Flatrate_RefundConfig_PackingOptions:
      | C_Flatrate_RefundConfig_ID | M_HU_PackingMaterial_ID |
      | cfgWare1                   | pmCrate                 |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the refund condition does not qualify: only the 3% "Bonus Ware" is taken over (-64.66)
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor |
      | ol_poTakeOver            | 3                               | -64.66         | 3% Bonus Ware   |


  # ##############################################################################################
  # AC1/AC4 (TS3b): settings admitted by the qualifying rule: a packaging filter without listed packing options
  # (the settlement treats it as "all packaging") and a setting deducted at payment (the listing decides).
  # The head office has the on-invoice 3% "Bonus Ware" contract and the listed 1% goods refund condition ->
  # the take-over line carries 3% + 1% = 4% (4% of 2155.20 = 86.208 -> -86.21), next to the vendor's own 3% line (-64.66).
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS3b_packingFilterWithoutOption
  Scenario: A listed refund condition with the packaging filter on but no packing option is taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's goods refund contract has the packaging filter on, but lists no packing option
    # (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                               | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS3b_packingFilterWithoutOption | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsPackingOptionFiltered |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      | Y                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # 3% "Bonus Ware" + 1% of the listed refund condition = 4% of 2155.20 = 86.208 -> -86.21
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                                       |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                                                       |
      | ol_poTakeOver            | 4                               | -86.21         | 3% Bonus Ware + 1% RV Ware 1 % vierteljährlich S32357_TS3b_packingFilterWithoutOption |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS3b_deductedAtPayment
  Scenario: A listed refund condition deducted at payment is taken over
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office deducts its goods refund at payment
    # (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                      | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS3b_deductedAtPayment | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID | IsDeductedAtPayment |
      | cfgWare1   | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      | Y                   |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1  | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # 3% "Bonus Ware" + 1% of the listed refund condition = 4% of 2155.20 = 86.208 -> -86.21
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                              |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                                              |
      | ol_poTakeOver            | 4                               | -86.21         | 3% Bonus Ware + 1% RV Ware 1 % vierteljährlich S32357_TS3b_deductedAtPayment |


  # ##############################################################################################
  # AC5 (TS4): a refund-only customer (no "Bonus Ware" contract, so no listed contract discount line on the SO) still gets the
  # take-over line, carrying only its listed refund percentage, next to the vendor's own 3% line (-64.66 on 2155.20 goods).
  # - TS4_quarterly3 (real-world shape: refund-only, quarterly 3 %): "RV Ware 3 % vierteljaehrlich" -> 3% (-64.66), together 6% / -129.32
  # - TS4_halfYearly1 (real-world shape: refund-only, half-yearly 1 %): "RV Ware 1 % halbjaehrlich", half-yearly schedule -> 1% (-21.55), together 4% / -86.21
  # - TS4_noVendorLine: a vendor whose contract has no line on "Ware" (only 1% on "Verpackung") -> on the goods only the
  #   take-over line 3% (-64.66); the vendor's 1% stays on the Verpackung line (-1.00)
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS4_quarterly3
  Scenario: A refund-only customer's listed 3% quarterly refund contract gets its own 3% take-over line next to the vendor's own 3% line
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                              | Type_Conditions |
      | rvWare     | RV Ware 3 % vierteljährlich S32357_TS4_quarterly3 | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare    | rvWare                   | quarterlySchedule    | 3             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare   | rvWare                              | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare                   |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the vendor's own 3% (-64.66) and the take-over line 3% of 2155.20 = 64.656 -> -64.66: together 6% / -129.32
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                      |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                      |
      | ol_poTakeOver            | 3                               | -64.66         | 3% RV Ware 3 % vierteljährlich S32357_TS4_quarterly3 |
    # PO total: goods + Pfand 2757.36 - 129.32 = 2628.04 net; tax 19% of 2757.36 = 523.90, 7% of -129.32 = -9.05 -> 3142.89
    And validate the created orders
      | C_Order_ID | GrandTotal |
      | poDropship | 3142.89    |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS4_halfYearly1
  Scenario: A refund-only customer's listed 1% half-yearly refund contract gets its own 1% take-over line next to the vendor's own 3% line
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the refund is invoiced half-yearly
    And metasfresh contains C_InvoiceSchedules:
      | Identifier         | InvoiceDay | InvoiceDistance |
      | halfYearlySchedule | 31         | 6               |
    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                            | Type_Conditions |
      | rvWare     | RV Ware 1 % halbjährlich S32357_TS4_halfYearly1 | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare    | rvWare                   | halfYearlySchedule   | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare   | rvWare                              | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare                   |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 5 lines
    And the order identified by orderDropship has 5 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the vendor's own 3% (-64.66) and the take-over line 1% of 2155.20 = 21.552 -> -21.55: together 4% / -86.21
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                    |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                    |
      | ol_poTakeOver            | 1                               | -21.55         | 1% RV Ware 1 % halbjährlich S32357_TS4_halfYearly1 |
    # PO total: goods + Pfand 2757.36 - 86.21 = 2671.15 net; tax 19% of 2757.36 = 523.90, 7% of -86.21 = -6.03 -> 3189.02
    And validate the created orders
      | C_Order_ID | GrandTotal |
      | poDropship | 3189.02    |


  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS4_noVendorLine
  Scenario: A refund-only customer's listed refund contract gets the take-over line also from a vendor without a line on the goods category
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # a Verpackung product and the discount product of the vendor's Verpackung bonus (the other products are created in the Background)
    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | verpackungCategory |
    And metasfresh contains M_Products:
      | Identifier            | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | verpackung            | verpackungCategory                   | Y      | Y           |
      | bonusVendorVerpackung | discountCategory                     | Y      | Y           |
    # purchase price = sales price
    And metasfresh contains M_ProductPrices
      | Identifier                  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_verpackung            | soPLV                             | verpackung              | 100.00   | PCE               | contractTaxCategory |
      | pp_so_bonusVendorVerpackung | soPLV                             | bonusVendorVerpackung   | 1        | PCE               | discountTaxCategory |
      | pp_po_verpackung            | poPLV                             | verpackung              | 100.00   | PCE               | contractTaxCategory |
      | pp_po_bonusVendorVerpackung | poPLV                             | bonusVendorVerpackung   | 1        | PCE               | discountTaxCategory |

    # the drop-ship vendor's own contract has 1% on "Verpackung" only, no line on "Ware"
    And metasfresh contains C_BPartners without locations:
      | Identifier   | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorNoWare | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorNoWare_loc | vendorNoWare             | Y               | Y               |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier         | Name         | OPT.IsAdditive |
      | vendorNoWareSchema | Bonus Vendor | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier             | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorNoWareSchemaLine | vendorNoWareSchema                       | bonusVendorVerpackung   | 1                         | verpackungCategory                   |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier           | Name                  | C_CompensationGroup_Schema_ID.Identifier |
      | vendorNoWareSettings | vendorNoWare settings | vendorNoWareSchema                       |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | vendorNoWareSettings                               | docTypePurchaseOrder    |
    # (no Name: a unique one is generated, so the Background's "Vendor conditions" are not upserted)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | vendorNoWareConditions | CompensationGroup | zeroDurTrans                            | vendorNoWareSettings                                   |
    # open-ended vendor term: the drop-ship PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier       | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | vendorNoWareTerm | vendorNoWareConditions              | vendorNoWare                | 2026-06-15 | 2099-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by vendorNoWareTerm is completed

    # refund-only customer: the head office has no "Bonus Ware" contract, only refund contracts
    # the head office's goods refund contract (name unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                                | Type_Conditions |
      | rvWare     | RV Ware 3 % vierteljährlich S32357_TS4_noVendorLine | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare    | rvWare                   | quarterlySchedule    | 3             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare   | rvWare                              | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare is completed

    # the vendor's take-over record on "Ware" lists "Bonus Ware" and the goods refund condition; its line uses "Bonus Vendor"
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorNoWareSettings                    | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare                   |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines + 1 Verpackung line (100.00), ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1    | orderDropship         | elstar1                 | 1          | vendorNoWare                    |
      | ol_elstar2    | orderDropship         | elstar2                 | 1          | vendorNoWare                    |
      | ol_gala       | orderDropship         | gala                    | 1          | vendorNoWare                    |
      | ol_pfand1     | orderDropship         | pfand1                  | 1          | vendorNoWare                    |
      | ol_pfand2     | orderDropship         | pfand2                  | 1          | vendorNoWare                    |
      | ol_verpackung | orderDropship         | verpackung              | 1          | vendorNoWare                    |
    And the order identified by orderDropship is completed
    # no contract on the head office's side: the SO keeps its 6 lines
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | poDropship | vendorNoWare  |
    # on the goods only the take-over line 3% of 2155.20 = 64.656 -> -64.66; the vendor's 1% on the Verpackung line = -1.00
    Then validate the created order lines
      | C_OrderLine_ID.Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1               | poDropship            | elstar1                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poElstar2               | poDropship            | elstar2                 | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poGala                  | poDropship            | gala                    | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand1                | poDropship            | pfand1                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poPfand2                | poDropship            | pfand2                  | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poVerpackung            | poDropship            | verpackung              | 1          | false                       |                                 |        |                                   |                                           |
      | ol_poBonusVendorVerpackung | poDropship            | bonusVendorVerpackung   | 1          | true                        | 1                               | -1.00  | vendorNoWareTerm                  | null                                      |
      | ol_poTakeOver              | poDropship            | bonusVendorDropship     | 1          | true                        | 3                               | -64.66 | vendorNoWareTerm                  | goodsCategory                             |
    # 6 regular lines + the vendor's Verpackung line + the take-over line
    And the order identified by poDropship has 8 order lines
    And validate C_OrderLine:
      | C_OrderLine_ID | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                        |
      | ol_poTakeOver  | 3                               | -64.66         | 3% RV Ware 3 % vierteljährlich S32357_TS4_noVendorLine |
    # PO total: 2857.36 - 65.66 = 2791.70 net; tax 19% of 2857.36 = 542.90, 7% of -65.66 = -4.60 -> 3330.00
    And validate the created orders
      | C_Order_ID | GrandTotal |
      | poDropship | 3330.00    |


  # ##############################################################################################
  # TS5a: reactivating and completing the drop-ship PO again recomputes the take-over line with the same refund percentages:
  # TS1's setup -> the vendor's own 3% line (-64.66) and the take-over line 4.13% (-89.01), before and after the re-completion.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS5a
  Scenario: Reactivating and completing the drop-ship PO again gives the same 3% vendor line and 4.13% take-over line
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the packaging refund contract's bonus product (the other products are created in the Background)
    And metasfresh contains M_Products:
      | Identifier  | Name          | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusRvPack | Bonus RV Pack | discountCategory                     | Y      | Y           |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_bonusRvPack | soPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusRvPack | poPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's periodic bonuses: one refund contract per bonus, each with one untiered percentage setting on the goods category
    # (names unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                            | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS5a         | Refund          |
      | rvWare0125 | RV Ware 0,125 % jährlich S32357_TS5a            | Refund          |
      | rvPack03   | RV Verpackung 0,3 % vierteljährlich S32357_TS5a | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1    | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
      | cfgWare0125 | rvWare0125               | yearlySchedule       | 0.125         | goodsCategory         | bonusRvWare      |
      | cfgPack03   | rvPack03                 | quarterlySchedule    | 0.3           | goodsCategory         | bonusRvPack      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1    | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termWare0125 | rvWare0125                          | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termPack03   | rvPack03                            | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    And the C_Flatrate_Term identified by termWare0125 is completed
    And the C_Flatrate_Term identified by termPack03 is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and the two goods refund conditions (never the packaging one)
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |
      | takeOver                                         | rvWare0125               |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, as on the sample invoice
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                  |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                  |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                  |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                  |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO: the vendor's own 3% (-64.66) and the take-over line 4.13% (-89.01)
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                                                          |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                                                                                          |
      | ol_poTakeOver            | 4.13                            | -89.01         | 3% Bonus Ware + 1% RV Ware 1 % vierteljährlich S32357_TS5a + 0.125% RV Ware 0,125 % jährlich S32357_TS5a |

    # the PO is reactivated and completed again (the SO stays completed): the discount lines are re-created with new ids,
    # so they are identified anew; same 3% and 4.13% lines
    When the order identified by poDropship is reactivated
    And the order identified by poDropship is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1                         | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2                         | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                            | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1                          | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2                          | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship_recompleted | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver_recompleted            | poDropship            | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    And validate C_OrderLine:
      | C_OrderLine_ID                       | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                                                          |
      | ol_poBonusVendorDropship_recompleted | 3                               | -64.66         | 3% Bonus Vendor                                                                                          |
      | ol_poTakeOver_recompleted            | 4.13                            | -89.01         | 3% Bonus Ware + 1% RV Ware 1 % vierteljährlich S32357_TS5a + 0.125% RV Ware 0,125 % jährlich S32357_TS5a |
    # PO total as in TS1: 2757.36 - 153.67 = 2603.69 net; tax 523.90 - 10.76 -> 3116.83
    And validate the created orders
      | C_Order_ID | GrandTotal |
      | poDropship | 3116.83    |


  # ##############################################################################################
  # TS1c, real-world shape: a vendor with a 3.6 % base: 3.6 % + 4.13 % = 7.73 % per goods line (-71.24 / -51.95 / -43.41),
  # -166.60 in total. TS1's customer setup, but the drop-ship vendor's own contract has 3.6% on "Ware" ->
  # the vendor's own line 3.6% of 2155.20 = 77.587 -> -77.59 and the take-over line 4.13% -> -89.01, together -166.60.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS1c
  Scenario: With a vendor's 3.6% base the drop-ship PO's two discount lines give 7.73% per goods line, -166.60 in total
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # the drop-ship vendor's own 3.6% "Bonus Vendor" contract on the goods category
    And metasfresh contains C_BPartners without locations:
      | Identifier   | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendor36Base | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendor36Base_loc | vendor36Base             | Y               | Y               |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier         | Name         | OPT.IsAdditive |
      | vendor36BaseSchema | Bonus Vendor | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier             | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendor36BaseSchemaLine | vendor36BaseSchema                       | bonusVendorDropship     | 3.6                       | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier           | Name                  | C_CompensationGroup_Schema_ID.Identifier |
      | vendor36BaseSettings | vendor36Base settings | vendor36BaseSchema                       |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | vendor36BaseSettings                               | docTypePurchaseOrder    |
    # (no Name: a unique one is generated, so the Background's "Vendor conditions" are not upserted)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | vendor36BaseConditions | CompensationGroup | zeroDurTrans                            | vendor36BaseSettings                                   |
    # open-ended vendor term: the drop-ship PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier       | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | vendor36BaseTerm | vendor36BaseConditions              | vendor36Base                | 2026-06-15 | 2099-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by vendor36BaseTerm is completed

    # the packaging refund contract's bonus product (the other products are created in the Background)
    And metasfresh contains M_Products:
      | Identifier  | Name          | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusRvPack | Bonus RV Pack | discountCategory                     | Y      | Y           |
    And metasfresh contains M_ProductPrices
      | Identifier        | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_bonusRvPack | soPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusRvPack | poPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |

    # the head office's on-invoice 3% "Bonus Ware" contract on the goods category
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name       | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed

    # the head office's periodic bonuses: one refund contract per bonus, each with one untiered percentage setting on the goods category
    # (names unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                            | Type_Conditions |
      | rvWare1    | RV Ware 1 % vierteljährlich S32357_TS1c         | Refund          |
      | rvWare0125 | RV Ware 0,125 % jährlich S32357_TS1c            | Refund          |
      | rvPack03   | RV Verpackung 0,3 % vierteljährlich S32357_TS1c | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier  | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWare1    | rvWare1                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
      | cfgWare0125 | rvWare0125               | yearlySchedule       | 0.125         | goodsCategory         | bonusRvWare      |
      | cfgPack03   | rvPack03                 | quarterlySchedule    | 0.3           | goodsCategory         | bonusRvPack      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWare1    | rvWare1                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termWare0125 | rvWare0125                          | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termPack03   | rvPack03                            | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWare1 is completed
    And the C_Flatrate_Term identified by termWare0125 is completed
    And the C_Flatrate_Term identified by termPack03 is completed

    # the vendor's take-over record on "Ware" lists "Bonus Ware" and the two goods refund conditions (never the packaging one)
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendor36BaseSettings                    | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWare1                  |
      | takeOver                                         | rvWare0125               |

    # SO: 3 goods lines (2155.20) + 2 Pfand lines, as on the sample invoice
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendor36Base                    |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendor36Base                    |
      | ol_gala    | orderDropship         | gala                    | 1          | vendor36Base                    |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendor36Base                    |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendor36Base                    |
    And the order identified by orderDropship is completed

    # SO side: only the head office's on-invoice 3% "Bonus Ware" on the goods: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                      |
    And the order identified by orderDropship has 6 order lines

    # the SO's completion creates and completes the drop-ship PO
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | poDropship | vendor36Base  |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poDropship            | bonusVendorDropship     | 1          | true                        | vendor36BaseTerm                  | null                                      |
      | ol_poTakeOver             | poDropship            | bonusVendorDropship     | 1          | true                        | vendor36BaseTerm                  | goodsCategory                             |
    And the order identified by poDropship has 7 order lines
    # the vendor's own 3.6% (-77.59) and the take-over line 3 + 1 + 0.125 = 4.125% -> 4.13% (-89.01): together -166.60 (7.73% per goods line)
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                                                                                          |
      | ol_poBonusVendorDropship | 3.6                             | -77.59         | 3.6% Bonus Vendor                                                                                        |
      | ol_poTakeOver            | 4.13                            | -89.01         | 3% Bonus Ware + 1% RV Ware 1 % vierteljährlich S32357_TS1c + 0.125% RV Ware 0,125 % jährlich S32357_TS1c |
    # PO total: goods + Pfand 2757.36 - 166.60 = 2590.76 net; tax 19% of 2757.36 = 523.90, 7% of -166.60 = -11.66 -> 3103.00
    And validate the created orders
      | C_Order_ID | GrandTotal |
      | poDropship | 3103.00    |


  # ##############################################################################################
  # AC7 (TS5): the take-over changes nothing in the refund settlement. Two refund-only customers with identical
  # sales orders (2155.20 goods + 2 Pfand lines) and one 1% quarterly goods refund contract each (identical settings);
  # customer A's refund condition is listed on the take-over record, customer B's is not:
  # A's drop-ship PO gets the take-over line 1% (-21.55), B's does not.
  # The sales orders are invoiced; each refund contract's refund invoice candidate is the same, before and after the invoice:
  # 1% of each goods line, rounded per line (9.216 -> 9.22, 6.72, 5.616 -> 5.62) = 21.56; the Pfand lines are outside the
  # goods-category base.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32357_TS5
  Scenario: The refund settlement of a customer whose refund condition is taken over equals that of a customer whose is not
    Given temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"

    # customer B: a second store with its own head office (customer A is the Background's store and head office)
    And metasfresh contains C_BPartners:
      | Identifier          | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOfficeB | Y              | contractPS                    |
      | customerStoreB      | Y              | contractPS                    |

    # refund-only customers: neither head office has a "Bonus Ware" contract, each has one goods refund contract with the same setting
    # (names unique per scenario: conditions are upserted by name)
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name                                            | Type_Conditions |
      | rvWareA    | RV Ware 1 % vierteljährlich S32357_TS5          | Refund          |
      | rvWareB    | RV Ware 1 % vierteljährlich S32357_TS5_unlisted | Refund          |
    And metasfresh contains C_Flatrate_RefundConfigs:
      | Identifier | C_Flatrate_Conditions_ID | C_InvoiceSchedule_ID | RefundPercent | M_Product_Category_ID | Bonus_Product_ID |
      | cfgWareA   | rvWareA                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
      | cfgWareB   | rvWareB                  | quarterlySchedule    | 1             | goodsCategory         | bonusRvWare      |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | termWareA  | rvWareA                             | customerHeadOffice          | 2026-07-01 | 2027-06-30 | DR            | false         |
      | termWareB  | rvWareB                             | customerHeadOfficeB         | 2026-07-01 | 2027-06-30 | DR            | false         |
    And the C_Flatrate_Term identified by termWareA is completed
    And the C_Flatrate_Term identified by termWareB is completed

    # VendorDropship's take-over record on "Ware" lists "Bonus Ware" and customer A's refund condition only
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID        |
      | takeOver   | vendorSettings                          | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
      | takeOver                                         | rvWareA                  |

    # the same SO for both customers: 3 goods lines (2155.20) + 2 Pfand lines, ordered on 2026-07-01
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderA     | true    | customerStore            | customerStore                         | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice              | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | olA_elstar1 | orderA                | elstar1                 | 1          | vendorDropship                  |
      | olA_elstar2 | orderA                | elstar2                 | 1          | vendorDropship                  |
      | olA_gala    | orderA                | gala                    | 1          | vendorDropship                  |
      | olA_pfand1  | orderA                | pfand1                  | 1          | vendorDropship                  |
      | olA_pfand2  | orderA                | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderA is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderB     | true    | customerStoreB           | customerStoreB                        | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOfficeB             | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | olB_elstar1 | orderB                | elstar1                 | 1          | vendorDropship                  |
      | olB_elstar2 | orderB                | elstar2                 | 1          | vendorDropship                  |
      | olB_gala    | orderB                | gala                    | 1          | vendorDropship                  |
      | olB_pfand1  | orderB                | pfand1                  | 1          | vendorDropship                  |
      | olB_pfand2  | orderB                | pfand2                  | 1          | vendorDropship                  |
    And the order identified by orderB is completed

    # A's drop-ship PO takes over A's listed 1% (1% of 2155.20 = 21.552 -> -21.55) next to the vendor's own 3% line
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poA            | orderA                   | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | ol_poElstar1              | poA                   | elstar1                 | 1          | false                       |                                   |                                           |
      | ol_poElstar2              | poA                   | elstar2                 | 1          | false                       |                                   |                                           |
      | ol_poGala                 | poA                   | gala                    | 1          | false                       |                                   |                                           |
      | ol_poPfand1               | poA                   | pfand1                  | 1          | false                       |                                   |                                           |
      | ol_poPfand2               | poA                   | pfand2                  | 1          | false                       |                                   |                                           |
      | ol_poBonusVendorDropship  | poA                   | bonusVendorDropship     | 1          | true                        | vendorTerm                        | null                                      |
      | ol_poTakeOver             | poA                   | bonusVendorDropship     | 1          | true                        | vendorTerm                        | goodsCategory                             |
    And the order identified by poA has 7 order lines
    And validate C_OrderLine:
      | C_OrderLine_ID           | OPT.GroupCompensationPercentage | OPT.LineNetAmt | OPT.Description                           |
      | ol_poBonusVendorDropship | 3                               | -64.66         | 3% Bonus Vendor                           |
      | ol_poTakeOver            | 1                               | -21.55         | 1% RV Ware 1 % vierteljährlich S32357_TS5 |

    # B's drop-ship PO has only the vendor's own 3% line: B's refund condition is not listed
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poB            | orderB                   | false   | POO         | CO            | true           |
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier | OPT.GroupCompensation_Product_Category_ID |
      | olB_poElstar1             | poB                   | elstar1                 | 1          | false                       |                                 |        |                                   |                                           |
      | olB_poElstar2             | poB                   | elstar2                 | 1          | false                       |                                 |        |                                   |                                           |
      | olB_poGala                | poB                   | gala                    | 1          | false                       |                                 |        |                                   |                                           |
      | olB_poPfand1              | poB                   | pfand1                  | 1          | false                       |                                 |        |                                   |                                           |
      | olB_poPfand2              | poB                   | pfand2                  | 1          | false                       |                                 |        |                                   |                                           |
      | olB_poBonusVendorDropship | poB                   | bonusVendorDropship     | 1          | true                        | 3                               | -64.66 | vendorTerm                        | null                                      |
    And the order identified by poB has 6 order lines

    # the sales orders' invoice candidates, invoiceable immediately
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | olA_elstar1    | icA_Elstar1            |
      | olA_elstar2    | icA_Elstar2            |
      | olA_gala       | icA_Gala               |
      | olA_pfand1     | icA_Pfand1             |
      | olA_pfand2     | icA_Pfand2             |
      | olB_elstar1    | icB_Elstar1            |
      | olB_elstar2    | icB_Elstar2            |
      | olB_gala       | icB_Gala               |
      | olB_pfand1     | icB_Pfand1             |
      | olB_pfand2     | icB_Pfand2             |
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override |
      | icA_Elstar1            | I                        |
      | icA_Elstar2            | I                        |
      | icA_Gala               | I                        |
      | icA_Pfand1             | I                        |
      | icA_Pfand2             | I                        |
      | icB_Elstar1            | I                        |
      | icB_Elstar2            | I                        |
      | icB_Gala               | I                        |
      | icB_Pfand1             | I                        |
      | icB_Pfand2             | I                        |

    # each refund contract refunds 1% of its customer's goods: the same for A (listed) and B (not listed).
    # Asserted on the refund amount (PriceActual) and the per-line assignments, which carry the settlement, not on the
    # refund candidate's NetAmtToInvoice: that can stay below PriceActual when the sales candidates are split across
    # invoice-candidate update runs, because the refund candidate is not re-flagged for recompute after a later assignment.
    # This is pre-existing refund-engine behaviour, reproduced with the take-over switched off.
    Then after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsSOTrx | PriceActual | M_Product_ID | Bill_BPartner_ID    |
      | refundA                | termWareA          | Y       | 21.56       | bonusRvWare  | customerHeadOffice  |
      | refundB                | termWareB          | Y       | 21.56       | bonusRvWare  | customerHeadOfficeB |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | refundA                     | icA_Elstar1                     | termWareA          | 9.22                |
      | refundA                     | icA_Elstar2                     | termWareA          | 6.72                |
      | refundA                     | icA_Gala                        | termWareA          | 5.62                |
      | refundB                     | icB_Elstar1                     | termWareB          | 9.22                |
      | refundB                     | icB_Elstar2                     | termWareB          | 6.72                |
      | refundB                     | icB_Gala                        | termWareB          | 5.62                |
    And the C_Invoice_Candidate identified by icA_Pfand1 has no C_Invoice_Candidate_Assignment
    And the C_Invoice_Candidate identified by icA_Pfand2 has no C_Invoice_Candidate_Assignment
    And the C_Invoice_Candidate identified by icB_Pfand1 has no C_Invoice_Candidate_Assignment
    And the C_Invoice_Candidate identified by icB_Pfand2 has no C_Invoice_Candidate_Assignment

    # each sales order is invoiced
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | icA_Elstar1                       |
      | icA_Elstar2                       |
      | icA_Gala                          |
      | icA_Pfand1                        |
      | icA_Pfand2                        |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | icB_Elstar1                       |
      | icB_Elstar2                       |
      | icB_Gala                          |
      | icB_Pfand1                        |
      | icB_Pfand2                        |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceA                | icA_Elstar1                       |
      | invoiceB                | icB_Elstar1                       |

    # after the invoices: both refund candidates are unchanged, and still the same for A and B
    And after not more than 60s, refund C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID | C_Flatrate_Term_ID | IsSOTrx | PriceActual | M_Product_ID | Bill_BPartner_ID    |
      | refundA                | termWareA          | Y       | 21.56       | bonusRvWare  | customerHeadOffice  |
      | refundB                | termWareB          | Y       | 21.56       | bonusRvWare  | customerHeadOfficeB |
    And after not more than 60s, C_Invoice_Candidate_Assignments are found:
      | C_Invoice_Candidate_Term_ID | C_Invoice_Candidate_Assigned_ID | C_Flatrate_Term_ID | AssignedMoneyAmount |
      | refundA                     | icA_Elstar1                     | termWareA          | 9.22                |
      | refundA                     | icA_Elstar2                     | termWareA          | 6.72                |
      | refundA                     | icA_Gala                        | termWareA          | 5.62                |
      | refundB                     | icB_Elstar1                     | termWareB          | 9.22                |
      | refundB                     | icB_Elstar2                     | termWareB          | 6.72                |
      | refundB                     | icB_Gala                        | termWareB          | 5.62                |
