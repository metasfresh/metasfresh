@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00102
@ghActions:run_on_executor3
Feature: Compensation groups that were not created by a contract
## F00102: Compensation Group
  - user edits of a discount line or a goods line recompute the discount
  - reactivating and completing an order again keeps or recomputes the discount
  - a schema discount line restricted to a product category counts only that category's goods
  - invoicing the whole order carries the discount on the invoice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]

    # goodsSub's category is a sub-category of goods1's category; goods3 is in an unrelated category
    And metasfresh contains M_Product_Category:
      | Identifier  | Name              | Value                     | OPT.M_Product_Category_Parent_ID.Identifier |
      | catGoods    | Ware              | CompGroupRegrGoods        |                                             |
      | catGoodsSub | Ware Untergruppe  | CompGroupRegrGoodsSub     | catGoods                                    |
      | catOther    | Andere Ware       | CompGroupRegrOther        |                                             |

    # not stocked: shipments need no inventory
    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier | OPT.IsStocked |
      | goods1          | catGoods                             | false         |
      | goods2          | catGoodsSub                          | false         |
      | goods3          | catOther                             | false         |
      | discountProduct |                                      | false         |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | groupPS    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name    | SOTrx | IsTaxIncluded | PricePrecision |
      | groupPL    | groupPS                       | DE                        | EUR                 | groupPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name     | ValidFrom  |
      | groupPLV   | groupPL                   | groupPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_goods1   | groupPLV                          | goods1                  | 1000     | PCE               | Normal                        |
      | pp_goods2   | groupPLV                          | goods2                  | 500      | PCE               | Normal                        |
      | pp_goods3   | groupPLV                          | goods3                  | 200      | PCE               | Normal                        |
      | pp_discount | groupPLV                          | discountProduct         | 1        | PCE               | Normal                        |

    # invoice rule "after delivery": the order is invoiced once its goods are shipped
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | plainBP    | Y              | groupPS                       | D               |

    # a plain bundle: 3 % on all of the group's goods
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier    | Name       |
      | productSchema | Bundle 3 % |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_goods1  | productSchema                 | goods1       | 1   | PCE      | 10    |
      | tl_goods2  | productSchema                 | goods2       | 1   | PCE      | 20    |
      | tl_goods3  | productSchema                 | goods3       | 1   | PCE      | 30    |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier        | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount |
      | productSchemaLine | productSchema                            | discountProduct         | 3                         |

    # the same bundle, but its 3 % apply only to the goods of category "Ware" (incl. its sub-category)
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier     | Name             |
      | categorySchema | Bundle Ware 3 %  |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier    | C_CompensationGroup_Schema_ID | M_Product_ID | Qty | C_UOM_ID | SeqNo |
      | tl_cat_goods1 | categorySchema                | goods1       | 1   | PCE      | 10    |
      | tl_cat_goods2 | categorySchema                | goods2       | 1   | PCE      | 20    |
      | tl_cat_goods3 | categorySchema                | goods3       | 1   | PCE      | 30    |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier         | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | categorySchemaLine | categorySchema                           | discountProduct         | 3                         | catGoods                             |


  # ##############################################################################################
  # The user changes the percentage of the discount line of a group created on the order's lines
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC71
  Scenario: Changing the percentage of a hand-made group's discount line recomputes the discount
    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderManual | true    | plainBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderManual           | goods1                  | 1          |
      | ol_goods2  | orderManual           | goods2                  | 1          |
    And create compensation group from order lines:
      | C_OrderLine_ID      | M_Product_ID    | Name       | CompensationLine | OPT.GroupCompensationPercentage |
      | ol_goods1,ol_goods2 | discountProduct | Bundle 3 % | ol_discount      | 3                               |

    # 3 % of 1000 + 500
    Then validate C_OrderLine:
      | C_OrderLine_ID | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | 3                           | 1500                     | -45   | -45        |

    When the user changes the percentage of compensation order lines in the order line grid:
      | C_OrderLine_ID | GroupCompensationPercentage |
      | ol_discount    | 10                          |

    # 10 % of 1000 + 500
    Then validate C_OrderLine:
      | C_OrderLine_ID | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | 10                          | 1500                     | -150  | -150       |

    When the order identified by orderManual is completed
    Then validate C_OrderLine:
      | C_OrderLine_ID | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt | processed |
      | ol_discount    | 10                          | 1500                     | -150  | -150       | true      |
    And the order identified by orderManual has 3 order lines


  # ##############################################################################################
  # The user changes the percentage of the discount line of a group created from a product schema
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC72
  Scenario: Changing the percentage of a schema group's discount line recomputes the discount
    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderSchema | true    | plainBP                  | 2026-07-01  |
    And create compensation group from schema template:
      | C_Order_ID  | C_CompensationGroup_Schema_ID | Qty |
      | orderSchema | productSchema                 | 1   |

    # 3 % of 1000 + 500 + 200
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID  | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | orderSchema | discountProduct | 1          | true                    | 3                           | 1700                     | -51   | -51        |

    When the user changes the percentage of compensation order lines in the order line grid:
      | C_OrderLine_ID | GroupCompensationPercentage |
      | ol_discount    | 10                          |

    # 10 % of 1000 + 500 + 200
    Then validate C_OrderLine:
      | C_OrderLine_ID | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | 10                          | 1700                     | -170  | -170       |

    When the order identified by orderSchema is completed
    Then validate C_OrderLine:
      | C_OrderLine_ID | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt | processed |
      | ol_discount    | 10                          | 1700                     | -170  | -170       | true      |
    And the order identified by orderSchema has 4 order lines


  # ##############################################################################################
  # The user changes the quantity of a goods line of a group created on the order's lines
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC73
  Scenario: Changing the quantity of a goods line in a hand-made group recomputes the discount
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderQty   | true    | plainBP                  | 2026-07-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_goods1  | orderQty              | goods1                  | 1          |
      | ol_goods2  | orderQty              | goods2                  | 1          |
    And create compensation group from order lines:
      | C_OrderLine_ID      | M_Product_ID    | Name       | CompensationLine | OPT.GroupCompensationPercentage |
      | ol_goods1,ol_goods2 | discountProduct | Bundle 3 % | ol_discount      | 3                               |

    Then validate C_OrderLine:
      | C_OrderLine_ID | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | 1500                     | -45   | -45        |

    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.AsUIAction |
      | ol_goods1                 | 2              | Y              |

    # 3 % of 2 x 1000 + 500
    Then validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered | price | LineNetAmt |
      | ol_goods1      | 2          | 1000  | 2000       |
    And validate C_OrderLine:
      | C_OrderLine_ID | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | 3                           | 2500                     | -75   | -75        |

    When the order identified by orderQty is completed
    Then validate C_OrderLine:
      | C_OrderLine_ID | GroupCompensationBaseAmt | price | LineNetAmt | processed |
      | ol_discount    | 2500                     | -75   | -75        | true      |


  # ##############################################################################################
  # Reactivate and complete again an order with a group created from a product schema
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC74
  Scenario: Reactivating and completing again keeps a schema group's discount; a changed goods line is reflected
    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderReactivate | true    | plainBP                  | 2026-07-01  |
    And create compensation group from schema template:
      | C_Order_ID      | C_CompensationGroup_Schema_ID | Qty |
      | orderReactivate | productSchema                 | 1   |
    And the order identified by orderReactivate is completed

    # 3 % of 1000 + 500 + 200
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID      | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | orderReactivate | discountProduct | 1          | true                    | 1700                     | -51   | -51        |

    # reactivate and complete again without changes: the very same discount line stays as it is
    When the order identified by orderReactivate is reactivated
    And the order identified by orderReactivate is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID      | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt | processed |
      | ol_discount    | orderReactivate | discountProduct | 1          | true                    | 3                           | 1700                     | -51   | -51        | true      |
    And the order identified by orderReactivate has 4 order lines

    # reactivate, the user raises goods2 to 3 pieces, complete again: 3 % of 1000 + 3 x 500 + 200
    When the order identified by orderReactivate is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.AsUIAction |
      | schema_ol_2               | 3              | Y              |
    And the order identified by orderReactivate is completed
    Then validate C_OrderLine:
      | C_OrderLine_ID | QtyOrdered | LineNetAmt |
      | schema_ol_2    | 3          | 1500       |
    And validate the created order lines
      | C_OrderLine_ID     | C_Order_ID      | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt | processed |
      | ol_discountChanged | orderReactivate | discountProduct | 1          | true                    | 3                           | 2700                     | -81   | -81        | true      |
    And the order identified by orderReactivate has 4 order lines


  # ##############################################################################################
  # A schema discount line restricted to a product category, without any contract
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC75
  Scenario: A schema discount line restricted to a product category counts only that category's goods, incl. its sub-category
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderCategory | true    | plainBP                  | 2026-07-01  |
    And create compensation group from schema template:
      | C_Order_ID    | C_CompensationGroup_Schema_ID | Qty |
      | orderCategory | categorySchema                | 1   |

    # 3 % of goods1 (Ware, 1000) + goods2 (Ware's sub-category, 500); goods3 (other category, 200) is not counted
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID    | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationPercentage | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | orderCategory | discountProduct | 1          | true                    | 3                           | 1500                     | -45   | -45        |

    # more of the other category's goods: the discount does not change
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.AsUIAction |
      | schema_ol_3               | 5              | Y              |
    Then validate the created order lines
      | C_OrderLine_ID     | C_Order_ID    | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discountOther   | orderCategory | discountProduct | 1          | true                    | 1500                     | -45   | -45        |

    # more of the sub-category's goods: 3 % of 1000 + 2 x 500
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.AsUIAction |
      | schema_ol_2               | 2              | Y              |
    Then validate the created order lines
      | C_OrderLine_ID     | C_Order_ID    | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discountSub     | orderCategory | discountProduct | 1          | true                    | 2000                     | -60   | -60        |

    When the order identified by orderCategory is completed
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID    | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationBaseAmt | price | LineNetAmt | processed |
      | ol_discountSub | orderCategory | discountProduct | 1          | true                    | 2000                     | -60   | -60        | true      |
    And the order identified by orderCategory has 4 order lines


  # ##############################################################################################
  # The whole order is shipped and invoiced at once: the invoice carries the group's discount
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC76
  Scenario: Invoicing a whole order with a schema group puts the full discount on the one invoice
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderInvoice | true    | plainBP                  | 2026-07-01  |
    And create compensation group from schema template:
      | C_Order_ID   | C_CompensationGroup_Schema_ID | Qty |
      | orderInvoice | productSchema                 | 1   |
    And the order identified by orderInvoice is completed

    # 3 % of 1000 + 500 + 200
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID   | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | orderInvoice | discountProduct | 1          | true                    | 1700                     | -51   | -51        |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | schema_ol_1               | N             |
      | ss_goods2   | schema_ol_2               | N             |
      | ss_goods3   | schema_ol_3               | N             |
      | ss_discount | ol_discount               | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_goods2             |
      | ss_goods3             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | schema_ol_1    | ic_goods1              |
      | schema_ol_2    | ic_goods2              |
      | schema_ol_3    | ic_goods3              |
      | ol_discount    | ic_discount            |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 1               |
      | ic_goods3                         | 1               |
      | ic_discount                       | 1               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |
    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice1_goods1             | goods1                  | 1           | 1000       |
      | invoice1_goods2             | goods2                  | 1           | 500        |
      | invoice1_goods3             | goods3                  | 1           | 200        |
      | invoice1_discount           | discountProduct         | 1           | -51        |
    # the invoice's net total: the goods (1000 + 500 + 200) less the discount
    And validate created invoices
      | C_Invoice_ID | TotalLines |
      | invoice1     | 1649       |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 1           | 0            | -51            | true      | false   |
    And validate the created orders
      | C_Order_ID   | InvoiceStatus |
      | orderInvoice | CI            |


  # ##############################################################################################
  # The whole order is invoiced at once: a category-restricted discount is invoiced on its category's goods
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00102
  @Id:S32353_TC77
  Scenario: Invoicing a whole order with a category-restricted schema group puts the category's discount on the one invoice
    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | orderCatInvoice | true    | plainBP                  | 2026-07-01  |
    And create compensation group from schema template:
      | C_Order_ID      | C_CompensationGroup_Schema_ID | Qty |
      | orderCatInvoice | categorySchema                | 1   |
    And the order identified by orderCatInvoice is completed

    # 3 % of goods1 + goods2 (1000 + 500); goods3 is in another category
    Then validate the created order lines
      | C_OrderLine_ID | C_Order_ID      | M_Product_ID    | QtyOrdered | IsGroupCompensationLine | GroupCompensationBaseAmt | price | LineNetAmt |
      | ol_discount    | orderCatInvoice | discountProduct | 1          | true                    | 1500                     | -45   | -45        |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_goods1   | schema_ol_1               | N             |
      | ss_goods2   | schema_ol_2               | N             |
      | ss_goods3   | schema_ol_3               | N             |
      | ss_discount | ol_discount               | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_goods1             |
      | ss_goods2             |
      | ss_goods3             |
      | ss_discount           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | ss_goods1                        | shipment1             |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | schema_ol_1    | ic_goods1              |
      | schema_ol_2    | ic_goods2              |
      | schema_ol_3    | ic_goods3              |
      | ol_discount    | ic_discount            |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | ic_goods1                         | 1               |
      | ic_goods2                         | 1               |
      | ic_goods3                         | 1               |
      | ic_discount                       | 1               |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice1                | ic_goods1                         |
    And validate invoice lines for invoice1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice1_goods1             | goods1                  | 1           | 1000       |
      | invoice1_goods2             | goods2                  | 1           | 500        |
      | invoice1_goods3             | goods3                  | 1           | 200        |
      | invoice1_discount           | discountProduct         | 1           | -45        |
    # the invoice's net total: the goods (1000 + 500 + 200) less the discount
    And validate created invoices
      | C_Invoice_ID | TotalLines |
      | invoice1     | 1655       |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_goods1              |
      | ic_goods2              |
      | ic_goods3              |
      | ic_discount            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyInvoiced | QtyToInvoice | NetAmtInvoiced | Processed | IsError |
      | ic_discount                       | 1           | 0            | -45            | true      | false   |
    And validate the created orders
      | C_Order_ID      | InvoiceStatus |
      | orderCatInvoice | CI            |
