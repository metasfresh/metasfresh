@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00122
@ghActions:run_on_executor3
Feature: OLCand order creation uses the BP picking warehouse when no warehouse is in the payload
## F00122: OLCand Warehouse Advisor
##
## When a sales order candidate is POSTed with no warehouse in the payload
## and the sold-to business partner has a picking warehouse, the created
## order and its lines must use that BP picking warehouse, even when the
## OLCand processor has its own default warehouse.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122
  @Id:S30235_01
  Scenario: OLCand without warehouse → order inherits the BP picking warehouse
    # The sold-to BP has pickingWH as its picking warehouse; the OLCand processor keeps its
    # own default warehouse. No warehouse is sent in the payload, so the created order and
    # its lines must use pickingWH — the BP picking warehouse takes precedence over the
    # processor default.
    Given metasfresh contains M_Warehouse:
      | Identifier | Value     | Name      | IsPickingWarehouse |
      | pickingWH  | pickingWH | pickingWH | true               |

    # Give the standard OLCand import processor (repo-id 1000003) a default warehouse
    # (StdWarehouse, 540008) — this is what a candidate with no warehouse would otherwise
    # inherit. The @After hook in C_OLCandProcessor_StepDef restores the processor afterwards
    # so sibling scenarios on this executor are unaffected.
    And update C_OLCandProcessor:
      | C_OLCandProcessor_ID | M_Warehouse_ID |
      | 1000003              | 540008         |

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
      | Identifier | Name       | IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | M_Warehouse_ID |
      | bp_S30235  | bp_S30235  | Y          | N            | ps_S30235                     | bpLoc_S30235               | 3000000030235 | pickingWH      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier |
      | bpLoc_S30235 | 3000000030235 | bp_S30235                |

    # POST one candidate for bp_S30235 with no warehouse in the payload.
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

    # The order and its lines must use the BP's picking warehouse (pickingWH), not the
    # processor default warehouse.
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | poReference | processed | DocStatus | M_Warehouse_ID |
      | order_S30235          | bp_S30235                | bpLoc_S30235                      | 2021-04-16  | SOO         | EUR          | F            | S               | S30235_01   | true      | CO        | pickingWH      |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | M_Warehouse_ID |
      | orderLine_S30235_1        | order_S30235          | product_S30235          | 1          | 0            | 0           | 10    | 0        | EUR          | true      | pickingWH      |
