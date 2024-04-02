@from:cucumber
@topic:orderCandidate
@ghActions:run_on_executor3
Feature: Enqueue order candidate in multiple workpackages for processing to order
  As a user
  I create multiple order candidates and when processing, multiple workpackages are enqueued for each order to be generated

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @topic:orderCandidate
  Scenario: Process C_OLCand in batches:
  - create 4 olcands - they would end of in 3 C_Orders
  - deactivate the productprice of one of the C_OLcand's products
  - verify that three C_Orders are still created
    Given metasfresh contains M_PricingSystems
      | Identifier           | Name                             | Value                            | OPT.IsActive |
      | ps_scenario_14042022 | pricing_system_scenario_14042022 | pricing_system_scenario_14042022 | true         |
    And metasfresh contains M_PriceLists
      | Identifier           | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_scenario_14042022 | ps_scenario_14042022          | DE                        | EUR                 | pl_scenario_14042022 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier | Name                  | ValidFrom  |
      | plv_scenario_14042022 | pl_scenario_14042022      | plv_scenario_14042022 | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier                   | Name                         |
      | product_14042022             | product_14042022             |
      | product_priceChange_14042022 | product_priceChange_14042022 |
    And metasfresh contains M_ProductPrices
      | Identifier             | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier      | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product             | plv_scenario_14042022             | product_14042022             | 10.0     | PCE               | Normal                        |
      | pp_product_priceChange | plv_scenario_14042022             | product_priceChange_14042022 | 20.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier      | Name                     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier |
      | olCand_Customer | olCand_Customer_14042022 | Y              | N            | ps_scenario_14042022          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | GLN           | C_BPartner_ID.Identifier |
      | olCand_Customer_location | 1354423215434 | olCand_Customer          |

    # we create 4 OLCands with externalHeaderId `14042022`
    # OLCands with externalLineId `14042022_0` and `14042022_1` should end up in the same order
    # OLCand with externalLineId `14042022_2` should end up in an individual order
    # OLCand with externalLineId `14042022_3` should end up in an individual order
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "14042022",
            "externalLineId": "14042022_0",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1354423215434",
                "bpartnerLocationIdentifier": "gln-1354423215434"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_14042022",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "14042022",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "14042022",
            "externalLineId": "14042022_1",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1354423215434",
                "bpartnerLocationIdentifier": "gln-1354423215434"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_14042022",
            "qty": 1,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "14042022",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "14042022",
            "externalLineId": "14042022_2",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1354423215434",
                "bpartnerLocationIdentifier": "gln-1354423215434"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_priceChange_14042022",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "14042022_new1",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "14042022",
            "externalLineId": "14042022_3",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1354423215434",
                "bpartnerLocationIdentifier": "gln-1354423215434"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_14042022",
            "qty": 3,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "14042022_new2",
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
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier      | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference | OPT.AD_InputDataSource_ID.Name | IsError | OPT.Processed | OPT.ExternalHeaderId | OPT.ExternalLineId | OPT.PriceActual |
      | olCand_1               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 2          | F            | S               | 14042022        | Shopware                       | N       | N             | 14042022             | 14042022_0         | 10.00           |
      | olCand_2               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 1          | F            | S               | 14042022        | Shopware                       | N       | N             | 14042022             | 14042022_1         | 10.00           |
      | olCand_3               | olCand_Customer          | olCand_Customer_location          | product_priceChange_14042022 | 2          | F            | S               | 14042022_new1   | Shopware                       | N       | N             | 14042022             | 14042022_2         | 20.00           |
      | olCand_4               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 3          | F            | S               | 14042022_new2   | Shopware                       | N       | N             | 14042022             | 14042022_3         | 10.00           |

    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.IsActive |
      | pp_product_priceChange       | false        |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "14042022",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1,order_2       |

    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus |
      | order_1               | 14042022   | olCand_Customer          | olCand_Customer_location          | 2021-11-20  | SOO         | EUR          | F            | S               | 14042022    | true      | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1_1             | order_1               | 2021-11-20      | product_14042022        | 0            | 2          | 0           | 10    | 0        | EUR          | true      |
      | orderLine_1_2             | order_1               | 2021-11-20      | product_14042022        | 0            | 1          | 0           | 10    | 0        | EUR          | true      |

    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference   | processed | docStatus |
      | order_2               | 14042022   | olCand_Customer          | olCand_Customer_location          | 2021-11-20  | SOO         | EUR          | F            | S               | 14042022_new2 | true      | CO        |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_2_1             | order_2               | 2021-11-20      | product_14042022        | 0            | 3          | 0           | 10    | 0        | EUR          | true      |

    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier      | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference | OPT.AD_InputDataSource_ID.Name | IsError | OPT.Processed | OPT.ExternalHeaderId | OPT.ExternalLineId | OPT.PriceActual | OPT.AD_Issue_ID.Identifier |
      | olCand_1               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 2          | F            | S               | 14042022        | Shopware                       | N       | Y             | 14042022             | 14042022_0         | 10.00           | null                       |
      | olCand_2               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 1          | F            | S               | 14042022        | Shopware                       | N       | Y             | 14042022             | 14042022_1         | 10.00           | null                       |
      | olCand_3               | olCand_Customer          | olCand_Customer_location          | product_priceChange_14042022 | 2          | F            | S               | 14042022_new1   | Shopware                       | Y       | N             | 14042022             | 14042022_2         | 20.00           | issue_olCand_3             |
      | olCand_4               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 3          | F            | S               | 14042022_new2   | Shopware                       | N       | Y             | 14042022             | 14042022_3         | 10.00           | null                       |
    And validate AD_Issue
      | AD_Issue_ID.Identifier | IssueSummary                         |
      | issue_olCand_3         | Produkt ist nicht auf der Preisliste |

    And locate last C_Queue_WorkPackage by enqueued element
      | C_Queue_WorkPackage_ID.Identifier | C_Queue_PackageProcessor_ID.InternalName | AD_Table_ID.TableName | Record_ID.Identifier |
      | wp_order_1                        | C_OLCandToOrderWorkpackageProcessor      | C_OLCand              | olCand_1             |
      | wp_order_2                        | C_OLCandToOrderWorkpackageProcessor      | C_OLCand              | olCand_4             |

    # we validate that olCand_2 and olCand_1 end up in the same work package
    And validate enqueued elements for C_Queue_WorkPackage
      | C_Queue_Element_ID.Identifier | C_Queue_WorkPackage_ID.Identifier | AD_Table_ID.TableName | Record_ID.Identifier |
      | queueElement_olCand_2         | wp_order_1                        | C_OLCand              | olCand_2             |