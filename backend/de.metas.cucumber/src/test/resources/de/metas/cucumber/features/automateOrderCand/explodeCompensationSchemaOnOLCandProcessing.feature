@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00122_Sales_Order_Candidate_to_Order
@ghActions:run_on_executor3
Feature: Explode a compensation-group-schema product into its component order lines when processing an order candidate to an order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-20T09:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122_Sales_Order_Candidate_to_Order
  @Id:S28977_TC1
  Scenario: OLCand for a compensation-group-schema product explodes into its component order lines
    Given metasfresh contains M_PricingSystems
      | Identifier | Name      |
      | ps_schema  | ps_schema |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_schema  | ps_schema                     | DE                        | EUR                 | pl_schema | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | plv_schema | pl_schema                 | plv_schema | 2026-07-01 |

    # the two sub-article products that make up the compensation group; created first so the schema's template lines can reference them
    And metasfresh contains M_Products:
      | Identifier  | Name        |
      | subProductA | subProductA |
      | subProductB | subProductB |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier      | Name            |
      | compGroupSchema | compGroupSchema |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier          | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | Qty | C_UOM_ID | SeqNo |
      | schemaTemplateLineA | compGroupSchema                          | subProductA             | 2   | PCE      | 10    |
      | schemaTemplateLineB | compGroupSchema                          | subProductB             | 3   | PCE      | 20    |

    # the compensation-group-schema product itself, linked to the schema
    And metasfresh contains M_Products:
      | Identifier    | Name          | OPT.C_CompensationGroup_Schema_ID.Identifier |
      | schemaProduct | schemaProduct | compGroupSchema                              |

    # all three products need a price: the schema product itself (to price the incoming OLCand) and both sub-articles (the exploded component lines must be priced)
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_schemaProduct | plv_schema                        | schemaProduct           | 15.00    | PCE               | Normal                        |
      | pp_subProductA   | plv_schema                        | subProductA             | 5.00     | PCE               | Normal                        |
      | pp_subProductB   | plv_schema                        | subProductB             | 3.00     | PCE               | Normal                        |

    # the C_BPartners step auto-creates the default location (with the given GLN, IsShipTo + IsBillToDefault) under the C_BPartner_Location_ID identifier
    And metasfresh contains C_BPartners:
      | Identifier | Name     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           |
      | customer   | customer | Y              | N            | ps_schema                     | customerLocation           | 4009900001234 |

    # a single OLCand for 2 units of the schema product
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "schemaExplosion",
            "externalLineId": "schemaExplosion_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4009900001234",
                "bpartnerLocationIdentifier": "gln-4009900001234"
            },
            "dateRequired": "2026-08-01",
            "dateOrdered": "2026-07-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-schemaProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "schemaExplosion",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "schemaExplosion",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId  | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | POReference     | processed | DocStatus |
      | order_1               | schemaExplosion | customer                 | customerLocation                  | 2026-07-20  | SOO         | EUR          | schemaExplosion | true      | CO        |

    # the ordered schema product must resolve to its schema's two component article lines (quantities scaled by the ordered quantity 2: 2×2=4 and 3×2=6),
    # matching what the sales-order window produces for the same product and quantity
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1_subProductA   | order_1               | 2026-07-20  | subProductA             | 0            | 4          | 0           | 5     | 0        | EUR          | true      |
      | orderLine_1_subProductB   | order_1               | 2026-07-20  | subProductB             | 0            | 6          | 0           | 3     | 0        | EUR          | true      |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122_Sales_Order_Candidate_to_Order
  @ghActions:run_on_executor3
  @Id:S28977_TC2
  Scenario: A shipment generated from a schema-exploded OLCand contains a line for each component product
    Given metasfresh contains M_PricingSystems
      | Identifier | Name      |
      | ps_schema  | ps_schema |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_schema  | ps_schema                     | DE                        | EUR                 | pl_schema | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | plv_schema | pl_schema                 | plv_schema | 2026-07-01 |

    And metasfresh contains M_Products:
      | Identifier  | Name        |
      | subProductA | subProductA |
      | subProductB | subProductB |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier      | Name            |
      | compGroupSchema | compGroupSchema |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier          | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | Qty | C_UOM_ID | SeqNo |
      | schemaTemplateLineA | compGroupSchema                          | subProductA             | 2   | PCE      | 10    |
      | schemaTemplateLineB | compGroupSchema                          | subProductB             | 3   | PCE      | 20    |

    And metasfresh contains M_Products:
      | Identifier    | Name          | OPT.C_CompensationGroup_Schema_ID.Identifier |
      | schemaProduct | schemaProduct | compGroupSchema                              |

    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_schemaProduct | plv_schema                        | schemaProduct           | 15.00    | PCE               | Normal                        |
      | pp_subProductA   | plv_schema                        | subProductA             | 5.00     | PCE               | Normal                        |
      | pp_subProductB   | plv_schema                        | subProductB             | 3.00     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | Name     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           |
      | customer   | customer | Y              | N            | ps_schema                     | customerLocation           | 4009900001234 |
    # explicit ship-to/bill-to location so the shipment-header C_BPartner_Location_ID assertion resolves the identifier reliably (registered in the step-def data)
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | customerLocation | 4009900001234 | customer                 | true         | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "schemaExplosion_ship",
            "externalLineId": "schemaExplosion_ship_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4009900001234",
                "bpartnerLocationIdentifier": "gln-4009900001234"
            },
            "dateRequired": "2026-08-01",
            "dateOrdered": "2026-07-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-schemaProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "schemaExplosion_ship",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_ship            |

    # process the compensation-group-schema candidate AND generate the shipment in one step (ship=true), exercising the widened
    # 1->N OLCand-to-order-line mapping all the way through shipment generation
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "schemaExplosion_ship",
    "externalSystemCode": "Shopware6",
    "ship": true,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier |
      | order_ship            | shipment_ship         |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | poreference          | processed | DocStatus |
      | shipment_ship         | customer                 | customerLocation                  | 2026-07-20  | schemaExplosion_ship | true      | CO        |

    # the shipment must carry ONE line per exploded component product, with the scaled quantities (2x2=4, 3x2=6)
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_subProductA  | shipment_ship         | subProductA             | 4           | true      |
      | shipmentLine_subProductB  | shipment_ship         | subProductB             | 6           | true      |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122_Sales_Order_Candidate_to_Order
  @ghActions:run_on_executor3
  @Id:S28977_TC3
  Scenario: A flatrate-linked OLCand threads its contract conditions onto the exploded component order lines
    Given metasfresh contains M_PricingSystems
      | Identifier | Name      |
      | ps_schema  | ps_schema |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_schema  | ps_schema                     | DE                        | EUR                 | pl_schema | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | plv_schema | pl_schema                 | plv_schema | 2026-07-01 |

    And metasfresh contains M_Products:
      | Identifier  | Name        |
      | subProductA | subProductA |
      | subProductB | subProductB |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier      | Name            |
      | compGroupSchema | compGroupSchema |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier          | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | Qty | C_UOM_ID | SeqNo |
      | schemaTemplateLineA | compGroupSchema                          | subProductA             | 2   | PCE      | 10    |
      | schemaTemplateLineB | compGroupSchema                          | subProductB             | 3   | PCE      | 20    |

    And metasfresh contains M_Products:
      | Identifier    | Name          | OPT.C_CompensationGroup_Schema_ID.Identifier |
      | schemaProduct | schemaProduct | compGroupSchema                              |

    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_schemaProduct | plv_schema                        | schemaProduct           | 15.00    | PCE               | Normal                        |
      | pp_subProductA   | plv_schema                        | subProductA             | 5.00     | PCE               | Normal                        |
      | pp_subProductB   | plv_schema                        | subProductB             | 3.00     | PCE               | Normal                        |

    # the contract conditions the incoming candidate carries; its (dynamically-allocated) id is exposed as the REST
    # context variable 'flatrateConditionsId' so the candidate payload below can reference it via @flatrateConditionsId@.
    # Type_Conditions=FlatFee (not Subscription): the exploded order lines must still carry C_Flatrate_Conditions_ID
    # (the behaviour under test), but a non-subscription type does not spawn a C_Flatrate_Term on order completion
    # (that path is gated on TYPE_CONDITIONS_Subscription in SubscriptionBL.isSubscription) and so does not drag in a
    # full subscription/transition contract setup that is orthogonal to the OLCand-explosion threading.
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier     | Name           | Type_Conditions | REST.Context.C_Flatrate_Conditions_ID |
      | schemaFlatrate | schemaFlatrate | FlatFee         | flatrateConditionsId                  |

    And metasfresh contains C_BPartners:
      | Identifier | Name     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           |
      | customer   | customer | Y              | N            | ps_schema                     | customerLocation           | 4009900001234 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "schemaExplosion_flatrate",
            "externalLineId": "schemaExplosion_flatrate_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4009900001234",
                "bpartnerLocationIdentifier": "gln-4009900001234"
            },
            "dateRequired": "2026-08-01",
            "dateOrdered": "2026-07-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-schemaProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "flatrateConditionsId": @flatrateConditionsId@,
            "poReference": "schemaExplosion_flatrate",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_flatrate        |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "schemaExplosion_flatrate",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_flatrate        |

    # both exploded component lines must carry the candidate's contract conditions (C2 threading)
    And validate the created order lines
      | C_OrderLine_ID.Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Flatrate_Conditions_ID.Identifier | processed |
      | orderLine_flatrate_subProductA | order_flatrate        | subProductA             | 4          | schemaFlatrate                          | true      |
      | orderLine_flatrate_subProductB | order_flatrate        | subProductB             | 6          | schemaFlatrate                          | true      |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122_Sales_Order_Candidate_to_Order
  @ghActions:run_on_executor3
  @Id:S28977_TC4
  Scenario: A compensation-group schema with a compensation line explodes into its regular component lines plus the compensation (discount) line
    Given metasfresh contains M_PricingSystems
      | Identifier | Name      |
      | ps_schema  | ps_schema |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_schema  | ps_schema                     | DE                        | EUR                 | pl_schema | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | plv_schema | pl_schema                 | plv_schema | 2026-07-01 |

    And metasfresh contains M_Products:
      | Identifier  | Name        |
      | subProductA | subProductA |
      | subProductB | subProductB |

    # a dedicated discount product used only as the schema's compensation line (no price list entry needed: the
    # compensation line is created with a manual price of 0)
    And metasfresh contains M_Products:
      | Identifier      | Name            |
      | discountProduct | discountProduct |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier      | Name            |
      | compGroupSchema | compGroupSchema |
    And metasfresh contains C_CompensationGroup_Schema_TemplateLine:
      | Identifier          | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | Qty | C_UOM_ID | SeqNo |
      | schemaTemplateLineA | compGroupSchema                          | subProductA             | 2   | PCE      | 10    |
      | schemaTemplateLineB | compGroupSchema                          | subProductB             | 3   | PCE      | 20    |
    # the schema's compensation line: an always-matching whole-order discount of 10%
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier     | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.SeqNo |
      | schemaDiscount | compGroupSchema                          | discountProduct         | 10                        | 30        |

    And metasfresh contains M_Products:
      | Identifier    | Name          | OPT.C_CompensationGroup_Schema_ID.Identifier |
      | schemaProduct | schemaProduct | compGroupSchema                              |

    # the compensation product is a real M_Product (M_Product_ID is mandatory on the schema line), so it must be on
    # the price list like any ordered product — order completion prices every line, including the compensation line.
    # Its PriceStd is irrelevant to the discount amount (the line is a percentage whole-order discount), hence 0.
    And metasfresh contains M_ProductPrices
      | Identifier         | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_schemaProduct   | plv_schema                        | schemaProduct           | 15.00    | PCE               | Normal                        |
      | pp_subProductA     | plv_schema                        | subProductA             | 5.00     | PCE               | Normal                        |
      | pp_subProductB     | plv_schema                        | subProductB             | 3.00     | PCE               | Normal                        |
      | pp_discountProduct | plv_schema                        | discountProduct         | 0.00     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | Name     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           |
      | customer   | customer | Y              | N            | ps_schema                     | customerLocation           | 4009900001234 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "schemaExplosion_comp",
            "externalLineId": "schemaExplosion_comp_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4009900001234",
                "bpartnerLocationIdentifier": "gln-4009900001234"
            },
            "dateRequired": "2026-08-01",
            "dateOrdered": "2026-07-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-schemaProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "schemaExplosion_comp",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_comp            |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "schemaExplosion_comp",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_comp            |

    # the order carries the 2 regular component lines (IsGroupCompensationLine=N) plus the compensation/discount line
    # the schema's 10% whole-order discount materialises as a compensation line (IsGroupCompensationLine=Y, QtyOrdered=1,
    # GroupCompensationPercentage=10 — the discount % carried from the schema line's CompleteOrderDiscount)
    And validate the created order lines
      | C_OrderLine_ID.Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | OPT.GroupCompensationPercentage | processed |
      | orderLine_comp_subProductA | order_comp            | subProductA             | 4          | false                       |                                 | true      |
      | orderLine_comp_subProductB | order_comp            | subProductB             | 6          | false                       |                                 | true      |
      | orderLine_comp_discount    | order_comp            | discountProduct         | 1          | true                        | 10                              | true      |
