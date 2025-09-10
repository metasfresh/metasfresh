@from:cucumber
@myfeature
Feature: available for sales

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh initially has no MD_Available_For_Sales data

  @from:cucumber
  Scenario: sync MD_Available_For_Sales when C_OrderLine's qtyOrdered changes
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                             | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference | C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | po_ref_mock | 1000012          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 10         |
    And after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyOrdered |
      | ol_1                      | 20             |
    Then after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 20             | -1002                           |

  @from:cucumber
  Scenario: sync MD_Available_For_Sales when C_OrderLine's M_AttributeSetInstance_ID changes
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                             | p_1          | 10.0     | PCE      | Normal           |
      | plv_1                             | p_2          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference | C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | po_ref_mock | 1000012          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
      | ol_3       | o_1                   | p_1                     | 10         |
    And after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 20             | -1002                           |
      | p_2                     | 0              | 10             | -1002                           |

    And metasfresh contains M_AttributeSetInstance with identifier "lineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1                      | lineASI                                  |
    Then after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | lineASI                         |
      | p_2                     | 0              | 10             | -1002                           |
      | p_1                     | 0              | 10             | -1002                           |

  @from:cucumber
  Scenario: sync MD_Available_For_Sales when MD_Stock is created and then move qtyToBeShipped in the future
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                             | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference | C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | po_ref_mock | 1000012          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 10         |
    And after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 10       | 0       |
    And the inventory identified by 11 is completed
    And after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 10             | 10             | -1002                           |
    And validate MD_AvailableForSales_Config
      | AD_Client_ID | AD_Org_ID | SalesOrderLookBehindHours | ShipmentDateLookAheadHours |
      | 1000000      | 0         | 8                         | 72                         |
    When update order
      | C_Order_ID.Identifier | OPT.PreparationDate |
      | o_1                   | 2022-04-17          |
    Then after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 10             | 0              | -1002                           |

  @from:cucumber
  Scenario: sync MD_Available_For_Sales when M_ShipmentSchedule's preparationDate_Override is changed
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                             | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference | C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | po_ref_mock | 1000012          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.PreparationDate_Override |
      | s_s_1                            | 2022-04-17                   |
    Then after not more than 30s, MD_Available_For_Sales table is empty for product: p_1

  @from:cucumber
  Scenario: sync MD_Available_For_Sales when M_ShipmentSchedule is processed
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                             | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference | C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | po_ref_mock | 1000012          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | s_1                   | CO            |
    And the shipment schedule identified by s_s_1 is processed after not more than 30 seconds
    Then after not more than 30s, MD_Available_For_Sales table is empty for product: p_1

  @from:cucumber
  Scenario: sync MD_Available_For_Sales
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                             | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference | C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | po_ref_mock | 1000012          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 8          |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 10       | 0       |
    When the inventory identified by 11 is completed

    Then after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 10             | 8              | -1002                           |

  @from:cucumber
  Scenario: sync MD_Available_For_Sales and export it via API
    Given metasfresh contains M_Products:
      | Identifier | REST.Context |
      | p_1        | productId_1  |
      | p_2        | productId_2  |
    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference            | ExternalReferenceType.Code | RecordId.Identifier |
      | GRSSignum           | availableForSales_09102025_1 | Product                    | p_1                 |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                             | p_1          | 10.0     | PCE      | Normal           |
      | plv_1                             | p_2          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_1 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference | C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1 | 2021-04-17  | po_ref_mock | 1000012          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 8          |
      | ol_2       | o_1        | p_2          | 10         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 10       | 0       |
    And the inventory identified by 11 is completed

    And after not more than 30s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 10             | 8              | -1002                           |
      | p_2                     | 0              | 10             | -1002                           |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Available_For_Sales_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "ExternalSystem",
      "value": "GRSSignum"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
[
    {
    "ProductExternalReference": "availableForSales_09102025_1",
    "Product_ID": @productId_1@,
    "QtyOnHandStock": 10,
    "QtyToBeShipped": 8,
    "StorageAttributesKey": "-1002",
    "ExternalSystem": "GRSSignum"
  }
]
    """
    
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/Available_For_Sales_JSON/invoke' and fulfills with '200' status code
    """
    """
    And the metasfresh REST-API responds with
    """
[
    {
    "ProductExternalReference": "availableForSales_10102025_1",
    "Product_ID": @productId_1@,
    "QtyOnHandStock": 10,
    "QtyToBeShipped": 8,
    "StorageAttributesKey": "-1002",
    "ExternalSystem": "GRSSignum"
    },
    {
    "ProductExternalReference": "",
    "Product_ID": @productId_2@,
    "QtyOnHandStock": 0,
    "QtyToBeShipped": 10,
    "StorageAttributesKey": "-1002",
    "ExternalSystem": ""
    }
]
    """