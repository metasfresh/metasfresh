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

    Then creating C_CompensationGroup_ContractSettings_TakeOver is refused with error code DBUniqueConstraint:
      | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID       |
      | settings                                | goodsCategory         | ownDiscountProduct |

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
  # SO completes -> drop-ship PO: ONE "Bonus Salemfrucht" line of 3% own + 3% taken over = 6% of the goods
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
      | bonusSalemfrucht     | Bonus Salemfrucht | discountCategory                     | Y      | Y           |

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
      | pp_so_bonusSalem   | soPLV                             | bonusSalemfrucht        | 1        | PCE               | discountTaxCategory |
      | pp_po_elstar1      | poPLV                             | elstar1                 | 921.60   | PCE               | contractTaxCategory |
      | pp_po_elstar2      | poPLV                             | elstar2                 | 672.00   | PCE               | contractTaxCategory |
      | pp_po_gala         | poPLV                             | gala                    | 561.60   | PCE               | contractTaxCategory |
      | pp_po_pfand1       | poPLV                             | pfand1                  | 416.88   | PCE               | contractTaxCategory |
      | pp_po_pfand2       | poPLV                             | pfand2                  | 185.28   | PCE               | contractTaxCategory |
      | pp_po_bonusWare    | poPLV                             | bonusWare               | 1        | PCE               | discountTaxCategory |
      | pp_po_bonusSalem   | poPLV                             | bonusSalemfrucht        | 1        | PCE               | discountTaxCategory |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    # Netto: the store is the order partner, the head office is the invoice partner and holds the contract
    And metasfresh contains C_BPartners:
      | Identifier         | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | nettoHeadOffice    | Y              | contractPS                    |
      | nettoStore         | Y              | contractPS                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier |
      | salemfrucht | N          | Y        | contractPS                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | salemfrucht_loc  | salemfrucht              | Y               | Y               |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name              | OPT.IsAdditive |
      | nettoSchema | Bonus Ware        | true           |
      | salemSchema | Bonus Salemfrucht | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier      | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | nettoSchemaLine | nettoSchema                              | bonusWare               | 3                         | goodsCategory                        |
      | salemSchemaLine | salemSchema                              | bonusSalemfrucht        | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name           | C_CompensationGroup_Schema_ID.Identifier |
      | nettoSettings | Netto settings | nettoSchema                              |
      | salemSettings | Salem settings | salemSchema                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | nettoSettings                                      | docTypeSalesOrder       |
      | salemSettings                                      | docTypePurchaseOrder    |

    # Salemfrucht takes over the head office's "Bonus Ware" on the goods category into its own "Bonus Salemfrucht"
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
      | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID     |
      | takeOver   | salemSettings                           | goodsCategory         | bonusSalemfrucht |
    And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
      | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID |
      | takeOver                                         | bonusWare    |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name             | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | nettoConditions | Netto conditions | CompensationGroup | zeroDurTrans                            | nettoSettings                                          |
      | salemConditions | Salem conditions | CompensationGroup | zeroDurTrans                            | salemSettings                                          |
    # open-ended vendor term: the auto-created PO's DateOrdered is the real wall clock, not the simulated date
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | nettoTerm  | nettoConditions                     | nettoHeadOffice             | 2026-06-15 | 2026-12-31 | DR            | false         |
      | salemTerm  | salemConditions                     | salemfrucht                 | 2026-06-15 | 2099-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by nettoTerm is completed
    And the C_Flatrate_Term identified by salemTerm is completed

    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |

    # SO: 3 goods lines + 2 Pfand lines
    When metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | DateOrdered | PreparationDate      | OPT.Bill_Location_ID.Identifier | M_Warehouse_ID.Identifier |
      | orderDropship | true    | nettoStore               | nettoStore                            | 2026-07-01  | 2026-06-30T22:00:00Z | nettoHeadOffice                 | dropshipWarehouse         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_elstar1 | orderDropship         | elstar1                 | 1          | salemfrucht                     |
      | ol_elstar2 | orderDropship         | elstar2                 | 1          | salemfrucht                     |
      | ol_gala    | orderDropship         | gala                    | 1          | salemfrucht                     |
      | ol_pfand1  | orderDropship         | pfand1                  | 1          | salemfrucht                     |
      | ol_pfand2  | orderDropship         | pfand2                  | 1          | salemfrucht                     |
    And the order identified by orderDropship is completed

    # SO side: the head office's own 3% "Bonus Ware" on the goods only: 3% of 2155.20 = 64.656 -> -64.66
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soBonusWare            | orderDropship         | bonusWare               | 1          | true                        | 3                               | -64.66 | nettoTerm                         |
    And the order identified by orderDropship has 6 order lines

    # drop-ship PO for Salemfrucht
    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | poDropship | salemfrucht   |

    # PO side: ONE discount line "Bonus Salemfrucht" = 3% own + 3% taken over = 6% of 2155.20 = 129.312 -> -129.31
    # (the SO's "Bonus Ware" line is not copied; nothing is discounted on the Pfand lines)
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | price   | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_poElstar1              | poDropship            | elstar1                 | 1          | false                       |                                 |         |                                   |
      | ol_poElstar2              | poDropship            | elstar2                 | 1          | false                       |                                 |         |                                   |
      | ol_poGala                 | poDropship            | gala                    | 1          | false                       |                                 |         |                                   |
      | ol_poPfand1               | poDropship            | pfand1                  | 1          | false                       |                                 |         |                                   |
      | ol_poPfand2               | poDropship            | pfand2                  | 1          | false                       |                                 |         |                                   |
      | ol_poBonusSalemfrucht     | poDropship            | bonusSalemfrucht        | 1          | true                        | 6                               | -129.31 | salemTerm                         |
    # exactly 6 lines (3 goods + 2 Pfand + ONE merged discount line) -- no separate "Bonus Ware" line
    And the order identified by poDropship has 6 order lines
    And validate the take-over composition description of the order lines:
      | C_OrderLine_ID        | Description                            |
      | ol_poBonusSalemfrucht | 3% Bonus Salemfrucht + 3% Bonus Ware   |

    # purchase invoice: the discount posts to the discount product's own expense account
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID        | C_Invoice_Candidate_ID |
      | ol_poElstar1          | ic_poElstar1           |
      | ol_poElstar2          | ic_poElstar2           |
      | ol_poGala             | ic_poGala              |
      | ol_poPfand1           | ic_poPfand1            |
      | ol_poPfand2           | ic_poPfand2            |
      | ol_poBonusSalemfrucht | ic_poBonusSalemfrucht  |
    # the past DateToInvoice_Override clears the date gate: the auto-created PO's DateOrdered is the real wall clock
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.InvoiceRule_Override | OPT.DateToInvoice_Override |
      | ic_poElstar1           | I                        | 2026-07-01                 |
      | ic_poElstar2           | I                        | 2026-07-01                 |
      | ic_poGala              | I                        | 2026-07-01                 |
      | ic_poPfand1            | I                        | 2026-07-01                 |
      | ic_poPfand2            | I                        | 2026-07-01                 |
      | ic_poBonusSalemfrucht  | I                        | 2026-07-01                 |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_poElstar1                      |
      | ic_poElstar2                      |
      | ic_poGala                         |
      | ic_poPfand1                       |
      | ic_poPfand2                       |
      | ic_poBonusSalemfrucht             |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invPO                   | ic_poBonusSalemfrucht             |
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr  | AmtSourceCr | Account_ID          | C_BPartner_ID | Record_ID | M_Product_ID     | C_Tax_ID    | C_VAT_Code_ID |
      | P_Expense_Acct        | -129.31 EUR  |             | discountExpenseAcct | salemfrucht   | invPO     | bonusSalemfrucht | discountTax | purchase7_N   |
      | *                     |              |             |                     |               | invPO     |                  |             |               |
