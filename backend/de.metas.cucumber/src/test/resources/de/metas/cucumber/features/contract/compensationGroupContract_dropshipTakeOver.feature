@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Compensation-group contract take-over of the customer's discount lines
## F2070: Compensation Group Contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_Product_Category:
      | Identifier    | Name  | Value                  |
      | goodsCategory | Ware | WareS32355TakeOver     |
      | foodCategory  | Food  | FoodS32355TakeOver     |

    And metasfresh contains M_Products:
      | Identifier              | OPT.M_Product_Category_ID.Identifier |
      | ownDiscountProduct      | goodsCategory                        |
      | otherOwnDiscountProduct | foodCategory                         |
      | customerDiscountProduct | goodsCategory                        |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name           | OPT.IsAdditive |
      | schema     | Contract bonus | true           |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name     | C_CompensationGroup_Schema_ID.Identifier |
      | settings   | Settings | schema                                   |

  # ##############################################################################################
  # One take-over record per product category and settings.
  # A second take-over record for a category that already has one is refused.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS9a
  Scenario: A second take-over record for the same product category on one settings is refused
    Given metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID       |
      | takeOver1  | settings                                | goodsCategory         | ownDiscountProduct |

    Then creating C_CompensationGroup_ContractSettings_TakeOver is refused with error code DBUniqueConstraint and messages:
      | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID       | Message_de_DE                                                                                   | Message_en_US                                                                          |
      | settings                                | goodsCategory         | ownDiscountProduct | Für diese Produktkategorie gibt es in diesen Einstellungen bereits eine aktive Übernahme-Zeile. | There is already an active take-over line for this product category in these settings. |

  # ##############################################################################################
  # A customer discount product is listed on at most one take-over record per settings.
  # The same customer product on a second take-over record of the same settings is refused.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS9b
  Scenario: The same customer discount product on two take-over records of one settings is refused
    Given metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID            |
      | takeOver1  | settings                                | goodsCategory         | ownDiscountProduct      |
      | takeOver2  | settings                                | foodCategory          | otherOwnDiscountProduct |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID            |
      | takeOver1                                        | customerDiscountProduct |

    Then creating C_CompensationGroup_ContractSettings_TakeOver_Product is refused with error code ContractCompGroup_TakeOverProductUnique:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID            |
      | takeOver2                                        | customerDiscountProduct |


  # ##############################################################################################
  # Real-world case: the store orders, the head office is billed and holds a 3% "Bonus Ware" contract.
  # The drop-ship vendor has its own 3% "Ware" contract plus a take-over record on "Ware" listing "Bonus Ware".
  # SO completes -> drop-ship PO: ONE "Bonus Vendor" line of 3% own + 3% taken over = 6% of the goods
  # (3 goods lines = 2155.20 -> 129.31); no discount on the 2 Pfand lines; the line description names both parts;
  # the purchase invoice posts the discount to the discount product's own expense account.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS1
  Scenario: Drop-ship PO merges the vendor's own 3% with the 3% taken over from the head office's contract
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
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
      | Identifier           | Name              | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1              | Elstar 1          | goodsCategory                        | Y      | Y           |
      | elstar2              | Elstar 2          | goodsCategory                        | Y      | Y           |
      | gala                 | Gala              | goodsCategory                        | Y      | Y           |
      | pfand1               | Pfand 1           | pfandCategory                        | Y      | Y           |
      | pfand2               | Pfand 2           | pfandCategory                        | Y      | Y           |
      | bonusWare            | Bonus Ware        | discountCategory                     | Y      | Y           |
      | bonusVendorDropship     | Bonus Vendor | discountCategory                     | Y      | Y           |

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

    # purchase price = sales price
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
      | Identifier         | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID    |
      | pp_so_elstar1      | soPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_so_elstar2      | soPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_so_gala         | soPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_so_pfand1       | soPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_so_pfand2       | soPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_so_bonusWare    | soPLV                             | bonusWare               | 1        | PCE               | discountTaxCategory |
      | pp_so_bonusVendor   | soPLV                             | bonusVendorDropship        | 1        | PCE               | discountTaxCategory |
      | pp_po_elstar1      | poPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_po_elstar2      | poPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_po_gala         | poPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_po_pfand1       | poPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_po_pfand2       | poPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_po_bonusWare    | poPLV                             | bonusWare               | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusVendor   | poPLV                             | bonusVendorDropship        | 1        | PCE               | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # Customer: the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier         | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice    | Y              | contractPS                    |
      | customerStore         | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc  | vendorDropship              | Y               | Y               |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name              | OPT.IsAdditive |
      | customerSchema | Bonus Ware        | true           |
      | vendorSchema | Bonus Vendor | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier      | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerSchemaLine | customerSchema                              | bonusWare               | 3                         | goodsCategory                        |
      | vendorSchemaLine | vendorSchema                              | bonusVendorDropship        | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name           | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | Customer settings | customerSchema                              |
      | vendorSettings | Vendor settings | vendorSchema                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings                                      | docTypeSalesOrder       |
      | vendorSettings                                      | docTypePurchaseOrder    |

    # VendorDropship takes over the head office's "Bonus Ware" on the goods category into its own "Bonus Vendor"
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID     |
      | takeOver   | vendorSettings                           | goodsCategory         | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | Customer conditions | CompensationGroup | zeroDurTrans                            | customerSettings                                          |
      | vendorConditions | Vendor conditions | CompensationGroup | zeroDurTrans                            | vendorSettings                                          |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | customerTerm  | customerConditions                     | customerHeadOffice             | 2026-06-15 | 2026-12-31 | DR            | false         |
      | vendorTerm  | vendorConditions                     | vendorDropship                 | 2026-06-15 | 2099-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |

    # SO: 3 goods lines + 2 Pfand lines
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | vendorDropship                     |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | vendorDropship                     |
      | ol_gala    | orderDropship         | gala                    | 1          | vendorDropship                     |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | vendorDropship                     |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | vendorDropship                     |
    And the order identified by orderDropship is completed

    # SO side: the head office's own 3% "Bonus Ware" on the goods only: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | customerTerm                         |
    And the order identified by orderDropship has 6 order lines

    # drop-ship PO for VendorDropship
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | poDropship | vendorDropship   |

    # PO side: ONE discount line "Bonus Vendor" = 3% own + 3% taken over = 6% of 2155.20 = 129.312 -> -129.31
    # (the SO's "Bonus Ware" line is not copied; nothing is discounted on the Pfand lines)
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price   | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                 |         |                                   |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                 |         |                                   |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                 |         |                                   |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                 |         |                                   |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                 |         |                                   |
      | ol_poBonusVendorDropship     | poDropship            | bonusVendorDropship        | 1          | true                        | 6                               | -129.31 | vendorTerm                         |
    # exactly 6 lines (3 goods + 2 Pfand + ONE merged discount line) -- no separate "Bonus Ware" line
    And the order identified by poDropship has 6 order lines
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID        | Description                            |
      | ol_poBonusVendorDropship | 3% Bonus Vendor + 3% Bonus Ware   |

    # purchase invoice: the discount posts to the discount product's own expense account
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID        | C_Invoice_Candidate_ID |
      | ol_poElstar1          | ic_poElstar1           |
      | ol_poElstar2          | ic_poElstar2           |
      | ol_poGala             | ic_poGala              |
      | ol_poPfand1           | ic_poPfand1            |
      | ol_poPfand2           | ic_poPfand2            |
      | ol_poBonusVendorDropship | ic_poBonusVendorDropship  |
    # the past DateToInvoice_Override clears the date gate: the auto-created PO's DateOrdered is the real wall clock
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override | OPT.DateToInvoice_Override |
      | ic_poElstar1           | I                        | 2026-07-01                 |
      | ic_poElstar2           | I                        | 2026-07-01                 |
      | ic_poGala              | I                        | 2026-07-01                 |
      | ic_poPfand1            | I                        | 2026-07-01                 |
      | ic_poPfand2            | I                        | 2026-07-01                 |
      | ic_poBonusVendorDropship  | I                        | 2026-07-01                 |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_poElstar1                      |
      | ic_poElstar2                      |
      | ic_poGala                         |
      | ic_poPfand1                       |
      | ic_poPfand2                       |
      | ic_poBonusVendorDropship             |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invPO                   | ic_poBonusVendorDropship             |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr  | AmtSourceCr | Account_ID          | C_BPartner_ID | Record_ID | M_Product_ID     | C_Tax_ID    | C_VAT_Code_ID |
      | P_Expense_Acct        | -129.31 EUR  |             | discountExpenseAcct | vendorDropship   | invPO     | bonusVendorDropship | discountTax | purchase7_N   |
      | *                     |              |             |                     |               | invPO     |                  |             |               |


  # ##############################################################################################
  # Exclusion is by discount PRODUCT, not by category:
  # a packaging bonus ("Bonus Verpackung" 0.6%) and a goods-based bonus with its OWN discount product
  # ("Bonus Ware 7 Prozent" 0.6% on goods) that the take-over record does not list are not taken over.
  # Only the listed "Bonus Ware" (3%) is added to the vendor's own 3% -> one 6% line = -129.31.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS2
  Scenario: Packaging bonus and a goods bonus with an unlisted discount product are not taken over
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS2 | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS2 | discountCategory | Y | Y |
      | bonusVerpackung | Bonus Verpackung TS2 | discountCategory | Y | Y |
      | bonusWareSeven | Bonus Ware 7 Prozent TS2 | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVerpackung | soPLV | bonusVerpackung | 1 | PCE | discountTaxCategory |
      | pp_so_bonusWareSeven | soPLV | bonusWareSeven | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVerpackung | poPLV | bonusVerpackung | 1 | PCE | discountTaxCategory |
      | pp_po_bonusWareSeven | poPLV | bonusWareSeven | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
      | customerLineVerpackung | customerSchema | bonusVerpackung | 0.6 | verpackungCategory |
      | customerLineWareSeven | customerSchema | bonusWareSeven | 0.6 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    # SO: 3 goods + 2 Pfand + 1 packaging line
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
      | ol_elstar2 | orderDropship | elstar2 | 1 | vendorDropship |
      | ol_gala | orderDropship | gala | 1 | vendorDropship |
      | ol_pfand1 | orderDropship | pfand1 | 1 | vendorDropship |
      | ol_pfand2 | orderDropship | pfand2 | 1 | vendorDropship |
      | ol_verpackung | orderDropship | verpackung | 1 | vendorDropship |
    And the order identified by orderDropship is completed
    # SO side: 6 lines + the 3 customer discount lines (3% goods, 0.6% packaging, 0.6% goods with its own discount product)
    And the order identified by orderDropship has 9 order lines

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # only "Bonus Ware" is listed: 3% own + 3% taken over = 6% of the goods = 129.31; the 0.6% packaging bonus and the 0.6% goods bonus with its own discount product are NOT taken over
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poPfand1 | poDropship | pfand1 | 1 | false |  |  |
      | ol_poPfand2 | poDropship | pfand2 | 1 | false |  |  |
      | ol_poVerpackung | poDropship | verpackung | 1 | false |  |  |
      | ol_poBonusVendorDropship | poDropship | bonusVendorDropship | 1 | true | 6 | -129.31 |
    And the order identified by poDropship has 7 order lines


  # ##############################################################################################
  # Vendor's schema has 1% on "Verpackung" ONLY, no "Ware" line: the take-over record on "Ware" APPENDS an own line.
  # The own line keeps its "Ware" base on the purchase order AND after the invoice-candidate rebuild (the invoice):
  # -64.66 = 3% of the goods only; Pfand and the Verpackung line stay out of its base. Both lines reach the purchase invoice.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS3
  Scenario: Vendor without a line on the goods category gets an own take-over line that keeps its goods base
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS3 | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS3 | discountCategory | Y | Y |
      | bonusVendorVerpackung | Bonus Vendor Verpackung TS3 | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorVerpackung | soPLV | bonusVendorVerpackung | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorVerpackung | poPLV | bonusVendorVerpackung | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineVerpackung | vendorSchema | bonusVendorVerpackung | 1 | verpackungCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    # SO: 3 goods (Ware) + 2 Pfand (own category) + 1 Verpackung line of 100.00
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
      | ol_elstar2 | orderDropship | elstar2 | 1 | vendorDropship |
      | ol_gala | orderDropship | gala | 1 | vendorDropship |
      | ol_pfand1 | orderDropship | pfand1 | 1 | vendorDropship |
      | ol_pfand2 | orderDropship | pfand2 | 1 | vendorDropship |
      | ol_verpackung | orderDropship | verpackung | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # no schema line on "Ware": the take-over record APPENDS an own "Bonus Vendor" line of 3% on the goods = 64.66 (not 3% of everything);
    # the vendor's 1% on the Verpackung line = 1.00
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poPfand1 | poDropship | pfand1 | 1 | false |  |  |
      | ol_poPfand2 | poDropship | pfand2 | 1 | false |  |  |
      | ol_poVerpackung | poDropship | verpackung | 1 | false |  |  |
      | ol_poOwn | poDropship | bonusVendorDropship | 1 | true | 3 | -64.66 |
      | ol_poVendor | poDropship | bonusVendorVerpackung | 1 | true | 1 | -1.00 |
    # 6 regular lines + the appended own line + the vendor's line
    And the order identified by poDropship has 8 order lines
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID | Description |
      | ol_poOwn | 3% Bonus Ware TS3 |

    # purchase invoice
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_poElstar1 | ic_e1 |
      | ol_poElstar2 | ic_e2 |
      | ol_poGala | ic_g |
      | ol_poPfand1 | ic_p1 |
      | ol_poPfand2 | ic_p2 |
      | ol_poVerpackung | ic_v |
      | ol_poVendor | ic_vendor |
      | ol_poOwn | ic_own |
    # the past DateToInvoice_Override clears the date gate: the auto-created PO's DateOrdered is the real wall clock
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override | OPT.DateToInvoice_Override |
      | ic_e1 | I | 2026-07-01 |
      | ic_e2 | I | 2026-07-01 |
      | ic_g | I | 2026-07-01 |
      | ic_p1 | I | 2026-07-01 |
      | ic_p2 | I | 2026-07-01 |
      | ic_v | I | 2026-07-01 |
      | ic_vendor | I | 2026-07-01 |
      | ic_own | I | 2026-07-01 |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_e1 |
      | ic_e2 |
      | ic_g |
      | ic_p1 |
      | ic_p2 |
      | ic_v |
      | ic_vendor |
      | ic_own |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invPO | ic_own |
    # the discount lines keep their own base through the invoice-candidate rebuild: the amounts on the invoice are computed on that base
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtInvoiced |
      | ic_own | -64.66 |
      | ic_vendor | -1.00 |


  # ##############################################################################################
  # Variant: the vendor's 1% line has NO base (applies to the whole order) -> -28.57 (1% of 2857.36);
  # the own take-over line is neither compounded with it nor loses its goods base -> -64.66.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS3b
  Scenario: Vendor line without base: the own take-over line still computes on the goods only
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS3b | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS3b | discountCategory | Y | Y |
      | bonusVendorVerpackung | Bonus Vendor Verpackung TS3b | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorVerpackung | soPLV | bonusVendorVerpackung | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorVerpackung | poPLV | bonusVendorVerpackung | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount |
      | vendorLineAll | vendorSchema | bonusVendorVerpackung | 1 |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    # SO: 3 goods (Ware) + 2 Pfand (own category) + 1 Verpackung line of 100.00
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
      | ol_elstar2 | orderDropship | elstar2 | 1 | vendorDropship |
      | ol_gala | orderDropship | gala | 1 | vendorDropship |
      | ol_pfand1 | orderDropship | pfand1 | 1 | vendorDropship |
      | ol_pfand2 | orderDropship | pfand2 | 1 | vendorDropship |
      | ol_verpackung | orderDropship | verpackung | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # vendor's 1% has no base -> 1% of all 6 lines = 2857.36 -> 28.57; the own line is not compounded with it and keeps the goods base: 64.66
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poPfand1 | poDropship | pfand1 | 1 | false |  |  |
      | ol_poPfand2 | poDropship | pfand2 | 1 | false |  |  |
      | ol_poVerpackung | poDropship | verpackung | 1 | false |  |  |
      | ol_poOwn | poDropship | bonusVendorDropship | 1 | true | 3 | -64.66 |
      | ol_poVendor | poDropship | bonusVendorVerpackung | 1 | true | 1 | -28.57 |
    # 6 regular lines + the appended own line + the vendor's line
    And the order identified by poDropship has 8 order lines
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID | Description |
      | ol_poOwn | 3% Bonus Ware TS3b |

    # purchase invoice
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_poElstar1 | ic_e1 |
      | ol_poElstar2 | ic_e2 |
      | ol_poGala | ic_g |
      | ol_poPfand1 | ic_p1 |
      | ol_poPfand2 | ic_p2 |
      | ol_poVerpackung | ic_v |
      | ol_poVendor | ic_vendor |
      | ol_poOwn | ic_own |
    # the past DateToInvoice_Override clears the date gate: the auto-created PO's DateOrdered is the real wall clock
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override | OPT.DateToInvoice_Override |
      | ic_e1 | I | 2026-07-01 |
      | ic_e2 | I | 2026-07-01 |
      | ic_g | I | 2026-07-01 |
      | ic_p1 | I | 2026-07-01 |
      | ic_p2 | I | 2026-07-01 |
      | ic_v | I | 2026-07-01 |
      | ic_vendor | I | 2026-07-01 |
      | ic_own | I | 2026-07-01 |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_e1 |
      | ic_e2 |
      | ic_g |
      | ic_p1 |
      | ic_p2 |
      | ic_v |
      | ic_vendor |
      | ic_own |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invPO | ic_own |
    # the discount lines keep their own base through the invoice-candidate rebuild: the amounts on the invoice are computed on that base
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtInvoiced |
      | ic_own | -64.66 |
      | ic_vendor | -28.57 |


  # ##############################################################################################
  # Sub-variant: the vendor's schema is NOT additive -> the own line keeps its goods base,
  # both amounts stay the same as with an additive schema, on the purchase order and on the purchase invoice.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS3c
  Scenario: Vendor line without base on a non-additive vendor schema: the own line keeps its base, amounts unchanged
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS3c | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS3c | discountCategory | Y | Y |
      | bonusVendorVerpackung | Bonus Vendor Verpackung TS3c | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorVerpackung | soPLV | bonusVendorVerpackung | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorVerpackung | poPLV | bonusVendorVerpackung | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | false |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount |
      | vendorLineAll | vendorSchema | bonusVendorVerpackung | 1 |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    # SO: 3 goods (Ware) + 2 Pfand (own category) + 1 Verpackung line of 100.00
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
      | ol_elstar2 | orderDropship | elstar2 | 1 | vendorDropship |
      | ol_gala | orderDropship | gala | 1 | vendorDropship |
      | ol_pfand1 | orderDropship | pfand1 | 1 | vendorDropship |
      | ol_pfand2 | orderDropship | pfand2 | 1 | vendorDropship |
      | ol_verpackung | orderDropship | verpackung | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # non-additive vendor schema: still 1% of 2857.36 = 28.57 and the own line 64.66 on its own goods base (never compounded)
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poPfand1 | poDropship | pfand1 | 1 | false |  |  |
      | ol_poPfand2 | poDropship | pfand2 | 1 | false |  |  |
      | ol_poVerpackung | poDropship | verpackung | 1 | false |  |  |
      | ol_poOwn | poDropship | bonusVendorDropship | 1 | true | 3 | -64.66 |
      | ol_poVendor | poDropship | bonusVendorVerpackung | 1 | true | 1 | -28.57 |
    # 6 regular lines + the appended own line + the vendor's line
    And the order identified by poDropship has 8 order lines
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID | Description |
      | ol_poOwn | 3% Bonus Ware TS3c |

    # purchase invoice
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_poElstar1 | ic_e1 |
      | ol_poElstar2 | ic_e2 |
      | ol_poGala | ic_g |
      | ol_poPfand1 | ic_p1 |
      | ol_poPfand2 | ic_p2 |
      | ol_poVerpackung | ic_v |
      | ol_poVendor | ic_vendor |
      | ol_poOwn | ic_own |
    # the past DateToInvoice_Override clears the date gate: the auto-created PO's DateOrdered is the real wall clock
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override | OPT.DateToInvoice_Override |
      | ic_e1 | I | 2026-07-01 |
      | ic_e2 | I | 2026-07-01 |
      | ic_g | I | 2026-07-01 |
      | ic_p1 | I | 2026-07-01 |
      | ic_p2 | I | 2026-07-01 |
      | ic_v | I | 2026-07-01 |
      | ic_vendor | I | 2026-07-01 |
      | ic_own | I | 2026-07-01 |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_e1 |
      | ic_e2 |
      | ic_g |
      | ic_p1 |
      | ic_p2 |
      | ic_v |
      | ic_vendor |
      | ic_own |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invPO | ic_own |
    # the discount lines keep their own base through the invoice-candidate rebuild: the amounts on the invoice are computed on that base
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtInvoiced |
      | ic_own | -64.66 |
      | ic_vendor | -28.57 |


  # ##############################################################################################
  # Nothing to take over (customer has no contract): the purchase order carries the vendor's own 3% only, no additional line.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS4a
  Scenario: Customer without a contract: the drop-ship PO carries the vendor's percentage only
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS4a | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS4a | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by vendorTerm is completed

    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # vendor's own 3% of 921.60 = 27.648 -> 27.65 only; with a take-over it would be 6% = 55.30
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poBonusVendorDropship | poDropship | bonusVendorDropship | 1 | true | 3 | -27.65 |
    And the order identified by poDropship has 2 order lines


  # ##############################################################################################
  # Nothing to take over: a mediated purchase order has the mediated document subtype, which the vendor's settings do not list.
  # No vendor contract applies, so the purchase order has no discount line at all.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS4b
  Scenario: Mediated PO whose document type the vendor's settings do not list gets no discount line
    Given temporarily set sys config boolean value false for sys config "SKIP_WP_PROCESSOR_FOR_AUTOMATION"
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS4b | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS4b | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorDropship              | elstar1                 |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | customerHeadOffice                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          |
    And the order identified by orderDropship is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_elstar1 | ol_elstar1                | N             |
    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendorDropship              | orderDropship         | Mediated |
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | DocSubType | OPT.DocStatus |
      | poDropship     | orderDropship            | false   | POO         | MED        | DR            |
    And the order identified by poDropship is completed
    # no vendor contract applies: the purchase order has no discount line at all
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
    And the order identified by poDropship has 1 order lines


  # ##############################################################################################
  # Nothing to take over: a manually created purchase order has no Link_Order_ID. Vendor's own 3% only, no additional line.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS4c
  Scenario: Manually created PO without a linked sales order carries the vendor's percentage only
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS4c | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS4c | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    # manually created purchase order: no linked sales order
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | poManual   | false   | vendorDropship              | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_poElstar1 | poManual              | elstar1                 | 1          |
    And the order identified by poManual is completed

    # vendor's own 3% of 921.60 = 27.648 -> 27.65; nothing is taken over
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poManual | elstar1 | 1 | false |  |  |
      | ol_poBonusVendorDropship | poManual | bonusVendorDropship | 1 | true | 3 | -27.65 |
    And the order identified by poManual has 2 order lines


  # ##############################################################################################
  # Nothing to take over: a purchase order created by the standard "create purchase order from sales orders"
  # is linked to the sales order but is no drop-ship purchase order (IsDropShip = N). Vendor's own 3% only.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS4d
  Scenario: Standard create-PO-from-SOs for one sales order (not drop-ship) carries the vendor's percentage only
    Given temporarily set sys config boolean value false for sys config "SKIP_WP_PROCESSOR_FOR_AUTOMATION"
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS4d | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS4d | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorDropship              | elstar1                 |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | OPT.Bill_Location_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | customerHeadOffice                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          |
    And the order identified by orderDropship is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_elstar1 | ol_elstar1                | N             |
    When generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | vendorDropship              | orderDropship         | Standard |
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | DR            | false          |
    And the order identified by poDropship is completed
    # vendor's own 3% of 921.60 = 27.648 -> 27.65 only; with a take-over it would be 6% = 55.30
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poBonusVendorDropship | poDropship | bonusVendorDropship | 1 | true | 3 | -27.65 |
    And the order identified by poDropship has 2 order lines


  # ##############################################################################################
  # Nothing to take over: the vendor has no compensation-group contract at all, so the purchase order has no discount line.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS4e
  Scenario: Vendor without a contract of the new type: the drop-ship PO has no discount line
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS4e | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS4e | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed

    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # no vendor contract applies: the purchase order has no discount line at all
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
    And the order identified by poDropship has 1 order lines


  # ##############################################################################################
  # Nothing to take over: the vendor's settings have no take-over record. Vendor's own 3% only, no additional line.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS4f
  Scenario: Vendor settings without a take-over record: the drop-ship PO carries the vendor's percentage only
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS4f | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS4f | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # vendor's own 3% of 921.60 = 27.648 -> 27.65 only; with a take-over it would be 6% = 55.30
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poBonusVendorDropship | poDropship | bonusVendorDropship | 1 | true | 3 | -27.65 |
    And the order identified by poDropship has 2 order lines


  # ##############################################################################################
  # Not taken over: a product-bundle group, a manual discount and a fixed-amount contract line on the sales order
  # (all use LISTED discount products). Only the contract's 3% is taken over;
  # the bundle's goods on the purchase order still receive it: 6% of 4 goods lines (2255.20) = -135.31.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS5
  Scenario: Bundle group, manual discount and fixed-amount contract line are not taken over; bundle goods still receive the percentage
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased | GroupCompensationAmtType |
      | bonusWare | Bonus Ware TS5 | discountCategory | Y | Y | P |
      | bonusVendorDropship | Bonus Vendor TS5 | discountCategory | Y | Y | P |
      | bonusFixed | Bonus Fixed Amount TS5 | discountCategory | Y | Y | Q |
      | bonusManual | Bonus Manual TS5 | discountCategory | Y | Y | P |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_so_bonusFixed | soPLV | bonusFixed | 1 | PCE | discountTaxCategory |
      | pp_so_bonusManual | soPLV | bonusManual | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_bonusFixed | poPLV | bonusFixed | 1 | PCE | discountTaxCategory |
      | pp_po_bonusManual | poPLV | bonusManual | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
      | customerLineFixed | customerSchema | bonusFixed | 0 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
      | takeOver | bonusFixed |
      | takeOver | bonusManual |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    # SO: 4 goods lines; the first two form a product-bundle group, the third has a manual discount, only the fourth is covered by the contract
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
      | ol_elstar2 | orderDropship | elstar2 | 1 | vendorDropship |
      | ol_gala | orderDropship | gala | 1 | vendorDropship |
      | ol_braeburn | orderDropship | braeburn | 1 | vendorDropship |
    # the manual groups use a LISTED discount product with high percentages: taking them over would show in the amount
    And create compensation group from order lines:
      | C_OrderLine_ID        | M_Product_ID | Name            | CompensationLine  | OPT.GroupCompensationPercentage | OPT.C_BPartner_Vendor_ID |
      | ol_elstar1,ol_elstar2 | bonusManual  | Product bundle  | ol_bundleDiscount | 10                              | vendorDropship              |
      | ol_gala               | bonusManual  | Manual discount | ol_manualDiscount | 5                               | vendorDropship              |
    And the order identified by orderDropship is completed
    # the contract covers the one remaining goods line: 3% of 100.00 = 3.00; its fixed-amount contract line (listed, but a fixed amount) carries no percentage
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -3.00 |

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # only the contract's 3% is taken over: 3% own + 3% = 6% of ALL 4 goods lines (incl. the bundle's goods) = 2255.20 -> 135.312 -> 135.31
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poBraeburn | poDropship | braeburn | 1 | false |  |  |
      | ol_poBonusVendorDropship | poDropship | bonusVendorDropship | 1 | true | 6 | -135.31 |
    # the description names only the customer discount products that were actually taken over, not every listed one
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID | Description |
      | ol_poBonusVendorDropship | 3% Bonus Vendor TS5 + 3% Bonus Ware TS5 |


  # ##############################################################################################
  # The customer's schema is NOT additive (3% + 1% compound on the sales order).
  # The take-over sums the NOMINAL percentages: 4% taken over, 3% + 4% = 7% on the purchase order = -150.86.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS6
  Scenario: A compounding customer schema is taken over with the sum of its nominal percentages
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware A TS6 | discountCategory | Y | Y |
      | bonusWareB | Bonus Ware B TS6 | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS6 | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusWareB | soPLV | bonusWareB | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusWareB | poPLV | bonusWareB | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | false |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineA | customerSchema | bonusWare | 3 | goodsCategory |
      | customerLineB | customerSchema | bonusWareB | 1 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
      | takeOver | bonusWareB |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
      | ol_elstar2 | orderDropship | elstar2 | 1 | vendorDropship |
      | ol_gala | orderDropship | gala | 1 | vendorDropship |
      | ol_pfand1 | orderDropship | pfand1 | 1 | vendorDropship |
      | ol_pfand2 | orderDropship | pfand2 | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    # the customer's compounding 3% + 1% are summed NOMINALLY to 4%: 3% own + 4% = 7% of 2155.20 = 150.864 -> 150.86
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poPfand1 | poDropship | pfand1 | 1 | false |  |  |
      | ol_poPfand2 | poDropship | pfand2 | 1 | false |  |  |
      | ol_poBonusVendorDropship | poDropship | bonusVendorDropship | 1 | true | 7 | -150.86 |
    And the order identified by poDropship has 6 order lines
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID | Description |
      | ol_poBonusVendorDropship | 3% Bonus Vendor TS6 + 4% Bonus Ware A TS6, Bonus Ware B TS6 |


  # ##############################################################################################
  # Reactivating and completing the drop-ship purchase order again re-computes the take-over:
  # same 6% (-129.31), still ONE discount line, same composition.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS7a
  Scenario: Reactivating and completing the drop-ship PO again gives the same single 6% line
    Given set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS7a | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS7a | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
      | ol_elstar2 | orderDropship | elstar2 | 1 | vendorDropship |
      | ol_gala | orderDropship | gala | 1 | vendorDropship |
      | ol_pfand1 | orderDropship | pfand1 | 1 | vendorDropship |
      | ol_pfand2 | orderDropship | pfand2 | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | CO | true |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poPfand1 | poDropship | pfand1 | 1 | false |  |  |
      | ol_poPfand2 | poDropship | pfand2 | 1 | false |  |  |
      | ol_poBonusVendorDropship | poDropship | bonusVendorDropship | 1 | true | 6 | -129.31 |
    # reactivating and completing the purchase order again re-computes the take-over from the sales order: same 6%, still ONE discount line
    And the order identified by poDropship is reactivated
    And the order identified by poDropship is completed
    # the discount line is re-created by the completion: it gets a new id, so it is identified anew
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poPfand1 | poDropship | pfand1 | 1 | false |  |  |
      | ol_poPfand2 | poDropship | pfand2 | 1 | false |  |  |
      | ol_poBonusVendorDropship2 | poDropship | bonusVendorDropship | 1 | true | 6 | -129.31 |
    And the order identified by poDropship has 6 order lines
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID | Description |
      | ol_poBonusVendorDropship2 | 3% Bonus Vendor TS7a + 3% Bonus Ware TS7a |


  # ##############################################################################################
  # A drop-ship purchase order left drafted (CompleteDropshipPO = N) and completed later
  # gets the same single 6% line (-129.31) as when it is completed together with the sales order.
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  @Id:S32355_TS7b
  Scenario: A drop-ship PO left drafted and completed later gets the same single 6% line
    Given temporarily set sys config boolean value false for sys config "de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO"
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Product_Categories:
      | Identifier         |
      | pfandCategory      |
      | verpackungCategory |
      | discountCategory   |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | elstar1    | goodsCategory                        | Y      | Y           |
      | elstar2    | goodsCategory                        | Y      | Y           |
      | gala       | goodsCategory                        | Y      | Y           |
      | braeburn   | goodsCategory                        | Y      | Y           |
      | pfand1     | pfandCategory                        | Y      | Y           |
      | pfand2     | pfandCategory                        | Y      | Y           |
      | verpackung | verpackungCategory                   | Y      | Y           |
    And metasfresh contains M_Products:
      | Identifier | Name | OPT.M_Product_Category_ID.Identifier | IsSold | IsPurchased |
      | bonusWare | Bonus Ware TS7b | discountCategory | Y | Y |
      | bonusVendorDropship | Bonus Vendor TS7b | discountCategory | Y | Y |

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

    # purchase price = sales price
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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_so_elstar1 | soPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_so_elstar2 | soPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_so_gala | soPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_so_braeburn | soPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_so_pfand1 | soPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_so_pfand2 | soPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_so_verpackung | soPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_so_bonusWare | soPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_so_bonusVendorDropship | soPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |
      | pp_po_elstar1 | poPLV | elstar1 | 921.60 | PCE | contractTaxCategory |
      | pp_po_elstar2 | poPLV | elstar2 | 672.00 | PCE | contractTaxCategory |
      | pp_po_gala | poPLV | gala | 561.60 | PCE | contractTaxCategory |
      | pp_po_braeburn | poPLV | braeburn | 100.00 | PCE | contractTaxCategory |
      | pp_po_pfand1 | poPLV | pfand1 | 416.88 | PCE | contractTaxCategory |
      | pp_po_pfand2 | poPLV | pfand2 | 185.28 | PCE | contractTaxCategory |
      | pp_po_verpackung | poPLV | verpackung | 100.00 | PCE | contractTaxCategory |
      | pp_po_bonusWare | poPLV | bonusWare | 1 | PCE | discountTaxCategory |
      | pp_po_bonusVendorDropship | poPLV | bonusVendorDropship | 1 | PCE | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerHeadOffice | Y              | contractPS                    |
      | customerStore      | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | vendorDropship | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorDropship_loc | vendorDropship              | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name | OPT.IsAdditive |
      | customerSchema | Bonus Ware | true |
      | vendorSchema | Bonus Vendor | true |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | customerLineWare | customerSchema | bonusWare | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | vendorLineWare | vendorSchema | bonusVendorDropship | 3 | goodsCategory |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name | C_CompensationGroup_Schema_ID.Identifier |
      | customerSettings | customerSettings | customerSchema |
      | vendorSettings | vendorSettings | vendorSchema |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | customerSettings | docTypeSalesOrder |
      | vendorSettings | docTypePurchaseOrder |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID |
      | takeOver | vendorSettings | goodsCategory | bonusVendorDropship |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver | bonusWare |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name | Type_Conditions | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | customerConditions | customer conditions | CompensationGroup | zeroDurTrans | customerSettings |
      | vendorConditions | vendor conditions | CompensationGroup | zeroDurTrans | vendorSettings |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate | EndDate | OPT.DocStatus | OPT.Processed |
      | customerTerm | customerConditions | customerHeadOffice | 2026-06-15 | 2026-12-31 | DR | false |
      | vendorTerm | vendorConditions | vendorDropship | 2026-06-15 | 2099-12-31 | DR | false |
    And the C_Flatrate_Term identified by customerTerm is completed
    And the C_Flatrate_Term identified by vendorTerm is completed

    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerStore               | customerStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | customerHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship | elstar1 | 1 | vendorDropship |
      | ol_elstar2 | orderDropship | elstar2 | 1 | vendorDropship |
      | ol_gala | orderDropship | gala | 1 | vendorDropship |
      | ol_pfand1 | orderDropship | pfand1 | 1 | vendorDropship |
      | ol_pfand2 | orderDropship | pfand2 | 1 | vendorDropship |
    And the order identified by orderDropship is completed

    # the drop-ship PO is left drafted: no discount yet
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship | orderDropship | false | POO | DR | true |
    And the order identified by poDropship has 5 order lines
    # completed later: same result as when it is completed together with the sales order
    And the order identified by poDropship is completed
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price |
      | ol_poElstar1 | poDropship | elstar1 | 1 | false |  |  |
      | ol_poElstar2 | poDropship | elstar2 | 1 | false |  |  |
      | ol_poGala | poDropship | gala | 1 | false |  |  |
      | ol_poPfand1 | poDropship | pfand1 | 1 | false |  |  |
      | ol_poPfand2 | poDropship | pfand2 | 1 | false |  |  |
      | ol_poBonusVendorDropship | poDropship | bonusVendorDropship | 1 | true | 6 | -129.31 |
    And the order identified by poDropship has 6 order lines
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID | Description |
      | ol_poBonusVendorDropship | 3% Bonus Vendor TS7b + 3% Bonus Ware TS7b |
