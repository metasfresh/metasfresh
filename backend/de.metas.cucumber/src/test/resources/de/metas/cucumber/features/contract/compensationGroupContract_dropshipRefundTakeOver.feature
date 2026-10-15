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


  # ##############################################################################################
  # Real-world case (Netto Maxhuette, sample invoice 186957): the store orders, the head office is billed and holds
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
    Given temporarily set sys config boolean value true for sys config "SKIP_WP_PROCESSOR_FOR_AUTOMATION"
    And documents are accounted immediately

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
      | bonusRvPack         | Bonus RV Pack | discountCategory                     | Y      | Y           |

    # purchase price = sales price
    And metasfresh contains M_ProductPrices
      | Identifier          | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_elstar1       | soPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_so_elstar2       | soPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_so_gala          | soPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_so_pfand1        | soPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_so_pfand2        | soPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_so_bonusWare     | soPLV                             | bonusWare               | 1        | PCE               | discountTaxCategory |
      | pp_so_bonusVendor   | soPLV                             | bonusVendorDropship     | 1        | PCE               | discountTaxCategory |
      | pp_so_bonusRvWare   | soPLV                             | bonusRvWare             | 1        | PCE               | discountTaxCategory |
      | pp_so_bonusRvPack   | soPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |
      | pp_po_elstar1       | poPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_po_elstar2       | poPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_po_gala          | poPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_po_pfand1        | poPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_po_pfand2        | poPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_po_bonusWare     | poPLV                             | bonusWare               | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusVendor   | poPLV                             | bonusVendorDropship     | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusRvWare   | poPLV                             | bonusRvWare             | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusRvPack   | poPLV                             | bonusRvPack             | 1        | PCE               | discountTaxCategory |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name         | OPT.IsAdditive |
      | customerSchema | Bonus Ware   | true           |
      | vendorSchema   | Bonus Vendor | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                           | bonusWare               | 3                         | goodsCategory                        |
      | vendorSchemaLine   | vendorSchema                             | bonusVendorDropship     | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier       | Name              | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                           |
      | vendorSettings   | Vendor settings   | vendorSchema                             |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                   | docTypeSalesOrder       |
      | vendorSettings                                     | docTypePurchaseOrder    |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name                | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                       |
      | vendorConditions   | Vendor conditions   | CompensationGroup | zeroDurTrans                            | vendorSettings                                         |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier   | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions                  | customerHeadOffice          | 2026-06-15 | 2026-12-31 | DR            | false         |
      | vendorTerm   | vendorConditions                    | vendorDropship              | 2026-06-15 | 2099-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

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

    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |

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
