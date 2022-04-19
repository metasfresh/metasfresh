@from:cucumber
@topic:orderCandidate
Feature: Enqueue order candidate in multiple workpackages for processing to order
  As a user
  I create multiple order candidates and when processing, multiple workpackages are enqueued for each order to be generated

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And enable sys config 'SKIP_WP_PROCESSOR_FOR_AUTOMATION'

  @from:cucumber
  @topic:orderCandidate
  Scenario: Process C_OLCand in batches
    Given metasfresh contains M_PricingSystems
      | Identifier               | Name                                 | Value                                | OPT.IsActive |
      | ps_scenario_14042022_fp5 | pricing_system_scenario_14042022_fp5 | pricing_system_scenario_14042022_fp5 | true         |
    And metasfresh contains M_PriceLists
      | Identifier                  | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                        | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_scenario_14042022_fp5_SO | ps_scenario_14042022_fp5      | DE                        | EUR                 | ps_scenario_14042022_fp5_SO | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier                   | M_PriceList_ID.Identifier   | Name          | ValidFrom  |
      | plv_scenario_14042022_fp5_SO | pl_scenario_14042022_fp5_SO | plv_olCand_SO | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier                       | Name                             |
      | product_14042022_fp5             | product_14042022_fp5             |
      | product_priceChange_14042022_fp5 | product_priceChange_14042022_fp5 |
    And metasfresh contains M_ProductPrices
      | Identifier             | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier          | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product             | plv_scenario_14042022_fp5_SO      | product_14042022_fp5             | 10.0     | PCE               | Normal                        |
      | pp_product_priceChange | plv_scenario_14042022_fp5_SO      | product_priceChange_14042022_fp5 | 20.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier      | Name                         | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier |
      | olCand_Customer | olCand_Customer_14042022_fp5 | Y              | N            | ps_scenario_14042022_fp5      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | GLN           | C_BPartner_ID.Identifier |
      | olCand_Customer_location | 1312321215555 | olCand_Customer          |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "14042022_fp5",
            "externalLineId": "14042022_fp5_0",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1312321215555",
                "bpartnerLocationIdentifier": "gln-1312321215555"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_14042022_fp5",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "14042022_fp5",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "14042022_fp5",
            "externalLineId": "14042022_fp5_1",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1312321215555",
                "bpartnerLocationIdentifier": "gln-1312321215555"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_priceChange_14042022_fp5",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "14042022_fp5",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "14042022_fp5",
            "externalLineId": "14042022_fp5_2",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1312321215555",
                "bpartnerLocationIdentifier": "gln-1312321215555"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_14042022_fp5",
            "qty": 1,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "14042022_fp5_new1",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "14042022_fp5",
            "externalLineId": "14042022_fp5_3",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1312321215555",
                "bpartnerLocationIdentifier": "gln-1312321215555"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_14042022_fp5",
            "qty": 3,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "14042022_fp5_new2",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier              |
      | olCand_1,olCand_2,olCand_3,olCand_4 |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier          | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference   | OPT.AD_InputDataSource_ID.Name | IsError | OPT.Processed | OPT.ExternalHeaderId | OPT.ExternalLineId | OPT.PriceActual |
      | olCand_1               | olCand_Customer          | olCand_Customer_location          | product_14042022_fp5             | 2          | F            | S               | 14042022_fp5      | Shopware                       | N       | N             | 14042022_fp5         | 14042022_fp5_0     | 10.00           |
      | olCand_2               | olCand_Customer          | olCand_Customer_location          | product_priceChange_14042022_fp5 | 2          | F            | S               | 14042022_fp5      | Shopware                       | N       | N             | 14042022_fp5         | 14042022_fp5_1     | 20.00           |
      | olCand_3               | olCand_Customer          | olCand_Customer_location          | product_14042022_fp5             | 1          | F            | S               | 14042022_fp5_new1 | Shopware                       | N       | N             | 14042022_fp5         | 14042022_fp5_2     | 10.00           |
      | olCand_4               | olCand_Customer          | olCand_Customer_location          | product_14042022_fp5             | 3          | F            | S               | 14042022_fp5_new2 | Shopware                       | N       | N             | 14042022_fp5         | 14042022_fp5_3     | 10.00           |

    And update M_ProductPrice:
      | Identifier             | IsActive |
      | pp_product_priceChange | false    |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "14042022_fp5",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | Order.Identifier        |
      | order_1,order_2,order_3 |
    And validate created order
      | Order.Identifier | externalId   | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference  | processed | docStatus |
      | order_1          | 14042022_fp5 | olCand_Customer          | olCand_Customer_location          | 2021-11-20  | SOO         | EUR          | F            | S               | 14042022_fp5 | true      | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | Order.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1_1             | order_1          | 2021-11-20  | product_14042022_fp5    | 0            | 2          | 0           | 10    | 0        | EUR          | true      |

    And validate created order
      | Order.Identifier | externalId   | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference       | processed | docStatus |
      | order_2          | 14042022_fp5 | olCand_Customer          | olCand_Customer_location          | 2021-11-20  | SOO         | EUR          | F            | S               | 14042022_fp5_new1 | true      | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | Order.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_2_1             | order_2          | 2021-11-20  | product_14042022_fp5    | 0            | 1          | 0           | 10    | 0        | EUR          | true      |

    And validate created order
      | Order.Identifier | externalId   | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference       | processed | docStatus |
      | order_3          | 14042022_fp5 | olCand_Customer          | olCand_Customer_location          | 2021-11-20  | SOO         | EUR          | F            | S               | 14042022_fp5_new2 | true      | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | Order.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_3_1             | order_3          | 2021-11-20  | product_14042022_fp5    | 0            | 3          | 0           | 10    | 0        | EUR          | true      |

    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier          | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference   | OPT.AD_InputDataSource_ID.Name | IsError | OPT.Processed | OPT.ExternalHeaderId | OPT.ExternalLineId | OPT.PriceActual | OPT.AD_Issue_ID.boolean |
      | olCand_1               | olCand_Customer          | olCand_Customer_location          | product_14042022_fp5             | 2          | F            | S               | 14042022_fp5      | Shopware                       | N       | Y             | 14042022_fp5         | 14042022_fp5_0     | 10.00           | N                       |
      | olCand_2               | olCand_Customer          | olCand_Customer_location          | product_priceChange_14042022_fp5 | 2          | F            | S               | 14042022_fp5      | Shopware                       | Y       | N             | 14042022_fp5         | 14042022_fp5_1     | 20.00           | Y                       |
      | olCand_3               | olCand_Customer          | olCand_Customer_location          | product_14042022_fp5             | 1          | F            | S               | 14042022_fp5_new1 | Shopware                       | N       | Y             | 14042022_fp5         | 14042022_fp5_2     | 10.00           | N                       |
      | olCand_4               | olCand_Customer          | olCand_Customer_location          | product_14042022_fp5             | 3          | F            | S               | 14042022_fp5_new2 | Shopware                       | N       | Y             | 14042022_fp5         | 14042022_fp5_3     | 10.00           | N                       |
