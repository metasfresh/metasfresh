@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00122
@ghActions:run_on_executor3
Feature: OLCand order creation uses BP picking warehouse when no warehouse is in the payload
## F00122: OLCand Warehouse Advisor
##
## When a sales order candidate is POSTed with no warehouse in the payload,
## the order must be created with the business partner's picking warehouse —
## NOT the OLCand-processor default warehouse.
##
## Bug: before the fix, the processor-default warehouse always wins even when
## the BP has an explicit picking warehouse configured.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122
  @Id:S30235_01
  Scenario: OLCand without warehouse → order inherits BP picking warehouse, not processor default
    # Two warehouses: WH_PICK is the BP's picking warehouse; WH_DEFAULT is set as the
    # processor default warehouse.  The created order must use WH_PICK (the BP wins).
    # With the bug the order takes WH_DEFAULT → this scenario is RED before the fix.
    Given metasfresh contains M_Warehouse:
      | Identifier | Value      | Name       | OPT.IsPickingWarehouse |
      | pickingWH  | pickingWH  | pickingWH  | true                   |
      | defaultWH  | defaultWH  | defaultWH  | false                  |

    And update C_OLCandProcessor:
      | OPT.C_OLCandProcessor_ID | OPT.M_Warehouse_ID.Identifier |
      | 1000003                  | defaultWH                     |

    And metasfresh contains M_PricingSystems
      | Identifier | Name       | Value      | OPT.IsActive |
      | ps_S30235  | ps_S30235  | ps_S30235  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_S30235  | ps_S30235                     | DE                        | EUR                 | pl_S30235  | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | plv_S30235 | pl_S30235                 | plv_S30235 | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier     | Name           |
      | product_S30235 | product_S30235 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_S30235  | plv_S30235                        | product_S30235          | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name       | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | OPT.M_Warehouse_ID.Identifier |
      | bp_S30235  | bp_S30235  | Y              | N            | ps_S30235                     | bpLoc_S30235               | 3000000030235 | pickingWH                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier |
      | bpLoc_S30235 | 3000000030235 | bp_S30235                |

    # POST one candidate for bp_S30235 — NO warehouse in payload.
    # The OLCand-processor default warehouse is defaultWH; the BP's picking warehouse is pickingWH.
    # The fix must route the order to pickingWH (the BP wins over the processor default).
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S30235_01",
            "externalLineId": "S30235_01_L1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-3000000030235",
                "bpartnerLocationIdentifier": "gln-3000000030235"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_S30235",
            "qty": 1,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "S30235_01",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_S30235          |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError |
      | olCand_S30235          | bp_S30235                | bpLoc_S30235                      | product_S30235          | 1          | N       |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "S30235_01",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_S30235          |

    # Before the fix: C_Order.M_Warehouse_ID = defaultWH (processor default wins) → RED.
    # After the fix: C_Order.M_Warehouse_ID = pickingWH (BP picking warehouse wins) → GREEN.
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | poReference | processed | DocStatus | OPT.M_Warehouse_ID.Identifier |
      | order_S30235          | bp_S30235                | bpLoc_S30235                      | 2021-04-16  | SOO         | EUR          | F            | S               | S30235_01   | true      | CO        | pickingWH                     |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_Warehouse_ID.Identifier |
      | orderLine_S30235_1        | order_S30235          | product_S30235          | 1          | 0            | 0           | 10    | 0        | EUR          | true      | pickingWH                     |
