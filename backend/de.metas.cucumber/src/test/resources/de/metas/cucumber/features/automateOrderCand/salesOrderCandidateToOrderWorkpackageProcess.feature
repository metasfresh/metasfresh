@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00122
@topic:orderCandidate
@ghActions:run_on_executor3
Feature: Enqueue order candidate in multiple workpackages for processing to order
## F00101: Order Candidates
  As a user
  I create multiple order candidates and when processing, multiple workpackages are enqueued for each order to be generated

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # Isolation: start every scenario from the default preparation-date offset (one scenario below overrides it to -24h).
    And set sys config int value 0 for sys config de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset_Hours

  @from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00122
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
    And metasfresh contains AD_Users:
      | Identifier   | Name         |
      | salesRepUser | salesRepUser |
    And metasfresh contains C_BPartners:
      | Identifier      | Name                     | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | deliveryRule | C_Incoterms_Customer_ID.Value | IncotermLocation        | SalesRep_ID  |
      | olCand_Customer | olCand_Customer_14042022 | Y              | N            | ps_scenario_14042022          | olCand_Customer_location   | 1354423215434 | F            | EXW                           | partnerIncotermLocation | salesRepUser |
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
            "externalSystemCode": "Shopware6",
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
            "incotermsValue": "DAP",
            "incotermsLocation": "incotermLocation"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "14042022",
            "externalLineId": "14042022_1",
            "externalSystemCode": "Shopware6",
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
            "incotermsValue": "DAP",
            "incotermsLocation": "incotermLocation"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "14042022",
            "externalLineId": "14042022_2",
            "externalSystemCode": "Shopware6",
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
            "externalHeaderId": "14042022_new2",
            "externalLineId": "14042022_3",
            "externalSystemCode": "Shopware6",
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
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier      | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference | OPT.AD_InputDataSource_ID.Name | IsError | OPT.Processed | OPT.ExternalHeaderId | OPT.ExternalLineId | OPT.PriceActual | ExternalSystem.Value |
      | olCand_1               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 2          | F            | S               | 14042022        | Shopware                       | N       | N             | 14042022             | 14042022_0         | 10.00           | Shopware6            |
      | olCand_2               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 1          | F            | S               | 14042022        | Shopware                       | N       | N             | 14042022             | 14042022_1         | 10.00           | Shopware6            |
      | olCand_3               | olCand_Customer          | olCand_Customer_location          | product_priceChange_14042022 | 2          | F            | S               | 14042022_new1   | Shopware                       | N       | N             | 14042022             | 14042022_2         | 20.00           | Shopware6            |
      | olCand_4               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 3          | F            | S               | 14042022_new2   | Shopware                       | N       | N             | 14042022_new2        | 14042022_3         | 10.00           | Shopware6            |

    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | IsActive |
      | pp_product_priceChange       | false    |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "14042022",
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
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | poReference | processed | DocStatus | C_Incoterms_Customer_ID.Value | IncotermLocation | SalesRep_ID  |
      | order_1               | 14042022   | olCand_Customer          | olCand_Customer_location          | 2021-11-20  | SOO         | EUR          | F            | S               | 14042022    | true      | CO        | DAP                          | incotermLocation | salesRepUser |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1_1             | order_1               | 2021-11-20  | product_14042022        | 0            | 2          | 0           | 10    | 0        | EUR          | true      |
      | orderLine_1_2             | order_1               | 2021-11-20  | product_14042022        | 0            | 1          | 0           | 10    | 0        | EUR          | true      |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "14042022_new2",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_2               |

    And validate the created orders
      | C_Order_ID.Identifier | externalId    | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | poReference   | processed | DocStatus | SalesRep_ID  |
      | order_2               | 14042022_new2 | olCand_Customer          | olCand_Customer_location          | 2021-11-20  | SOO         | EUR          | F            | S               | 14042022_new2 | true      | CO        | salesRepUser |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_2_1             | order_2               | 2021-11-20  | product_14042022        | 0            | 3          | 0           | 10    | 0        | EUR          | true      |

    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier      | QtyEntered | DeliveryRule | DeliveryViaRule | OPT.POReference | OPT.AD_InputDataSource_ID.Name | IsError | OPT.Processed | OPT.ExternalHeaderId | OPT.ExternalLineId | OPT.PriceActual | OPT.AD_Issue_ID.Identifier | ExternalSystem.Value |
      | olCand_1               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 2          | F            | S               | 14042022        | Shopware                       | N       | Y             | 14042022             | 14042022_0         | 10.00           | null                       | Shopware6            |
      | olCand_2               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 1          | F            | S               | 14042022        | Shopware                       | N       | Y             | 14042022             | 14042022_1         | 10.00           | null                       | Shopware6            |
      | olCand_3               | olCand_Customer          | olCand_Customer_location          | product_priceChange_14042022 | 2          | F            | S               | 14042022_new1   | Shopware                       | Y       | N             | 14042022             | 14042022_2         | 20.00           | issue_olCand_3             | Shopware6            |
      | olCand_4               | olCand_Customer          | olCand_Customer_location          | product_14042022             | 3          | F            | S               | 14042022_new2   | Shopware                       | N       | Y             | 14042022_new2        | 14042022_3         | 10.00           | null                       | Shopware6            |
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

  @from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00122
  @topic:orderCandidate
  Scenario: Create OLCand with different currency than what the pricelist allows -> an error is thrown when trying to create an order from it
    Given metasfresh contains M_PricingSystems
      | Identifier           |
      | ps_scenario_11092025 |
    And metasfresh contains M_PriceLists
      | Identifier           | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_scenario_11092025 | ps_scenario_11092025          | DE                        | EUR                 | pl_scenario_11092025 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier | Name                  | ValidFrom  |
      | plv_scenario_11092025 | pl_scenario_11092025      | plv_scenario_11092025 | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier       | Name             |
      | product_11092025 | product_11092025 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | plv_scenario_11092025             | product_11092025        | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier               | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | C_BPartner_Location_ID.Identifier | GLN           |
      | olCand_Customer_11092025 | Y              | N            | ps_scenario_11092025          | olCand_Customer_location_11092025 | 1234543215432 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                        | C_BPartner_ID.Identifier | C_Country_ID | GLN           |
      | olCand_Customer_location_11092025 | olCand_Customer_11092025 | CH           | 1234543215432 |

    # we create 1 OLCand with externalHeaderId `11092025`
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '207' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "11092025",
            "externalLineId": "11092025_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1234543215432",
                "bpartnerLocationIdentifier": "gln-1234543215432"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_11092025",
            "qty": 2,
            "currencyCode": "CHF",
            "price" : 8,
            "discount": 0,
            "poReference": "11092025",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And validate C_OLCand is with error
      | C_OLCand_ID.Identifier | ErrorMsg                  |
      | olCand_1               | Preisliste nicht gefunden |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122
  Scenario: OLCands sharing externalHeaderId but with different DatePromised aggregate into ONE order
  - create 3 olcands with the same externalHeaderId, externalSystemCode and org but DIFFERING DatePromised (dateRequired)
  - process them
  - verify that exactly ONE C_Order with 3 order lines is created (DatePromised is not part of the order-split key)
    Given metasfresh contains M_PricingSystems
      | Identifier           | Name                             | Value                            | IsActive |
      | ps_scenario_22062026 | pricing_system_scenario_22062026 | pricing_system_scenario_22062026 | true     |
    And metasfresh contains M_PriceLists
      | Identifier           | M_PricingSystem_ID   | C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_scenario_22062026 | ps_scenario_22062026 | DE                    | EUR                 | pl_scenario_22062026 | true  | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID       | Name                  | ValidFrom  |
      | plv_scenario_22062026 | pl_scenario_22062026 | plv_scenario_22062026 | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier       | Name             |
      | product_22062026 | product_22062026 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID     | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | plv_scenario_22062026  | product_22062026 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier      | Name                     | IsCustomer | IsVendor | M_PricingSystem_ID   | C_BPartner_Location_ID   | GLN           | deliveryRule |
      | olCand_Customer | olCand_Customer_22062026 | Y          | N        | ps_scenario_22062026 | olCand_Customer_location | 9988776655443 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | GLN           | C_BPartner_ID   |
      | olCand_Customer_location | 9988776655443 | olCand_Customer |
    # Use a -24h preparation-date offset so each derived PreparationDate is one day before its delivery date,
    # proving the per-line preparation date is genuinely DERIVED on the OLCand -> order path (not copied from DatePromised).
    And set sys config int value -24 for sys config de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset_Hours

    # - Same externalHeaderId `22062026` / externalSystemCode / org / poReference / dateOrdered across all 3 OLCands.
    # - DIFFERENT dateRequired (-> C_OLCand.DatePromised) per OLCand.
    # - DatePromised is NOT part of the order-aggregation key -> all 3 aggregate into ONE C_Order (ExternalId=22062026), 3 lines.
    # - (Otherwise 3 separate orders would collide on the C_Order_ExternalHeader_ID unique index = ExternalSystem_ID, ExternalId, AD_Org_ID.)
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "22062026",
            "externalLineId": "22062026_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-9988776655443",
                "bpartnerLocationIdentifier": "gln-9988776655443"
            },
            "dateRequired": "2026-07-01",
            "dateOrdered": "2026-06-22",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_22062026",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "22062026",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "22062026",
            "externalLineId": "22062026_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-9988776655443",
                "bpartnerLocationIdentifier": "gln-9988776655443"
            },
            "dateRequired": "2026-07-08",
            "dateOrdered": "2026-06-22",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_22062026",
            "qty": 1,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "22062026",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "22062026",
            "externalLineId": "22062026_2",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-9988776655443",
                "bpartnerLocationIdentifier": "gln-9988776655443"
            },
            "dateRequired": "2026-07-15",
            "dateOrdered": "2026-06-22",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_22062026",
            "qty": 3,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "22062026",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier     |
      | olCand_1,olCand_2,olCand_3 |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "22062026",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |

    # The header DatePromised must equal the EARLIEST DatePromised among the lines (2026-07-01).
    And validate the created orders
      | C_Order_ID | externalId | DatePromised | DocStatus |
      | order_1    | 22062026   | 2026-07-01   | CO        |
    # Each line keeps the DatePromised (dateRequired) of its own source candidate (matched by QtyOrdered:
    # qty 2 -> 2026-07-01, qty 1 -> 2026-07-08, qty 3 -> 2026-07-15); its PreparationDate is derived from that
    # per-line delivery date minus the 24h offset (one day earlier).
    And validate the created order lines
      | C_OrderLine_ID | C_Order_ID | M_Product_ID     | QtyOrdered | DatePromised | PreparationDate |
      | orderLine_1    | order_1    | product_22062026 | 2          | 2026-07-01   | 2026-06-30      |
      | orderLine_2    | order_1    | product_22062026 | 1          | 2026-07-08   | 2026-07-07      |
      | orderLine_3    | order_1    | product_22062026 | 3          | 2026-07-15   | 2026-07-14      |
    # The per-line dates flow through to each line's shipment schedule: DeliveryDate = the line's own DatePromised,
    # and (no tour configured) the base PreparationDate = DeliveryDate minus the 24h offset (one day earlier).
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | schedule_1 | orderLine_1    | N             |
      | schedule_2 | orderLine_2    | N             |
      | schedule_3 | orderLine_3    | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | PreparationDate | DeliveryDate |
      | schedule_1            | 2026-06-30      | 2026-07-01   |
      | schedule_2            | 2026-07-07      | 2026-07-08   |
      | schedule_3            | 2026-07-14      | 2026-07-15   |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122
  Scenario: Reset the preparation-date offset sysconfig
    # Isolation: restore the default offset so sibling features on this executor are not affected.
    Given set sys config int value 0 for sys config de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset_Hours

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122
  Scenario: OLCands sharing externalHeaderId but with different bill partner surface the external-header uniqueness error
  - create 2 olcands with the same externalHeaderId, externalSystemCode, org and ship partner, but DIFFERENT bill partner
  - the differing bill partner is part of the order-aggregation key (forces a separate order) but NOT of the
    C_Order_ExternalHeader_ID unique index tuple (ExternalSystem_ID, ExternalId, AD_Org_ID)
  - both forced orders therefore carry the same C_Order.ExternalId=18062027 -> the second order save violates the
    C_Order_ExternalHeader_ID unique index -> processing fails. The candidate is NOT flagged IsError; the process call
    returns HTTP 400 with a JsonError body, stored on the request-audit's response (api_response_audit).
  - order creation runs in an async workpackage, so the 400 body carries the generic async-batch wrapper message
    ("WorkPackage completed with an exception"); the specific translated AD_Index_Table.ErrorMsg is recorded on the
    failed C_Queue_WorkPackage.errormsg / AD_Issue.
    Given metasfresh contains M_PricingSystems
      | Identifier           | Name                             | Value                            | IsActive |
      | ps_scenario_18062027 | pricing_system_scenario_18062027 | pricing_system_scenario_18062027 | true     |
    And metasfresh contains M_PriceLists
      | Identifier           | M_PricingSystem_ID   | C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision | IsActive |
      | pl_scenario_18062027 | ps_scenario_18062027 | DE                    | EUR                 | pl_scenario_18062027 | true  | false         | 2              | true     |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID       | Name                  | ValidFrom  |
      | plv_scenario_18062027 | pl_scenario_18062027 | plv_scenario_18062027 | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier       | Name             |
      | product_18062027 | product_18062027 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID     | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | plv_scenario_18062027  | product_18062027 | 10.0     | PCE               | Normal                        |
    # Two customers sharing the same pricing system: 'shipCustomer' is the common ship partner of both olcands,
    # while 'billCustomerA' and 'billCustomerB' are the two DIFFERENT bill partners that force the order split.
    And metasfresh contains C_BPartners:
      | Identifier    | IsCustomer | IsVendor | M_PricingSystem_ID   | C_BPartner_Location_ID | GLN           | deliveryRule |
      | shipCustomer  | Y          | N        | ps_scenario_18062027 | shipCustomer_location  | 1100000000011 | F            |
      | billCustomerA | Y          | N        | ps_scenario_18062027 | billCustomerA_location | 2200000000022 | F            |
      | billCustomerB | Y          | N        | ps_scenario_18062027 | billCustomerB_location | 3300000000033 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID |
      | shipCustomer_location  | 1100000000011 | shipCustomer  |
      | billCustomerA_location | 2200000000022 | billCustomerA |
      | billCustomerB_location | 3300000000033 | billCustomerB |

    # we create 2 OLCands with the same externalHeaderId `18062027`, same externalSystemCode, org and ship partner,
    # but DIFFERENT billBPartner. They aggregate into the same group, but the differing bill partner forces a
    # separate order for each -> both orders get C_Order.ExternalId=18062027 -> the unique index
    # C_Order_ExternalHeader_ID is violated on the second order's save.
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "18062027",
            "externalLineId": "18062027_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1100000000011",
                "bpartnerLocationIdentifier": "gln-1100000000011"
            },
            "billBPartner": {
                "bpartnerIdentifier": "gln-2200000000022",
                "bpartnerLocationIdentifier": "gln-2200000000022"
            },
            "dateRequired": "2027-07-01",
            "dateOrdered": "2027-06-18",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_18062027",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "18062027",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "18062027",
            "externalLineId": "18062027_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-1100000000011",
                "bpartnerLocationIdentifier": "gln-1100000000011"
            },
            "billBPartner": {
                "bpartnerIdentifier": "gln-3300000000033",
                "bpartnerLocationIdentifier": "gln-3300000000033"
            },
            "dateRequired": "2027-07-01",
            "dateOrdered": "2027-06-18",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-product_18062027",
            "qty": 1,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "18062027",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1,olCand_2      |

    # Processing fails: the second forced order violates the C_Order_ExternalHeader_ID unique index.
    # The DBUniqueConstraintException carries the translated AD_Index_Table.ErrorMsg; the workpackage fails,
    # so the process call returns HTTP 400.
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '400' status code
"""
{
    "externalHeaderId": "18062027",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    # The synchronous 400 response carries a JsonError body (the candidate itself is not flagged with IsError), stored
    # on the request-audit's response (api_response_audit). The PUT's own request-audit id is taken from its
    # X-Api-Request-Audit-ID response header (RESTUtil stores it into the test context), so the assertion below resolves
    # the EXACT request-audit of this PUT - we must NOT overwrite it with a "last audit record" guess.
    # Order creation runs in an async workpackage, so the body carries the generic async-batch wrapper message
    # (the specific translated AD_Index_Table.ErrorMsg lives on the failed C_Queue_WorkPackage.errormsg / AD_Issue).
    And after not more than 60s, there are added records in API_Response_Audit
      | HttpCode | Body                                    |
      | 400      | WorkPackage completed with an exception |
