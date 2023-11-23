@from:cucumber
@ghActions:run_on_executor4
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
      | Identifier | Name                    |
      | p_1        | salesProduct_14062022_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_14062022_1 | d_pricing_system_value_14062022_1 | d_pricing_system_description_14062022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_14062022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_14062022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_14062022_1 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |
    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyOrdered |
      | ol_1                      | 20             |
    Then after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 20             | -1002                           |

  @from:cucumber
  Scenario: sync MD_Available_For_Sales when C_OrderLine's M_AttributeSetInstance_ID changes
    Given metasfresh contains M_Products:
      | Identifier | Name                        |
      | p_1        | d_salesProduct_16062022_2   |
      | p_2        | d_salesProduct_16062022_2_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_16062022_2 | d_pricing_system_value_16062022_2 | d_pricing_system_description_16062022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_16062022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_16062022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_16062022_2 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_2                     | 10         |
      | ol_3       | o_1                   | p_1                     | 10         |
    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
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
    Then after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | lineASI                         |
      | p_2                     | 0              | 10             | -1002                           |
      | p_1                     | 0              | 10             | -1002                           |

  @from:cucumber
  Scenario: sync MD_Available_For_Sales when MD_Stock is created and then move qtyToBeShipped in the future
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_14062022_3 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_14062022_3 | d_pricing_system_value_14062022_3 | d_pricing_system_description_14062022_3 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_14062022_3 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_14062022_3 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_14062022_3 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 10       | 0       |
    And the inventory identified by 11 is completed
    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 10             | 10             | -1002                           |
    And validate MD_AvailableForSales_Config
      | AD_Client_ID | AD_Org_ID | SalesOrderLookBehindHours | ShipmentDateLookAheadHours |
      | 1000000      | 0         | 8                         | 72                         |
    When update order
      | C_Order_ID.Identifier | OPT.PreparationDate |
      | o_1                   | 2022-04-17          |
    Then after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 10             | 0              | -1002                           |

  @from:cucumber
  Scenario: sync MD_Available_For_Sales when M_ShipmentSchedule's preparationDate_Override is changed
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_15062022_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_15062022_1 | d_pricing_system_value_15062022_1 | d_pricing_system_description_15062022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_15062022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15062022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15062022_1 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.PreparationDate_Override |
      | s_s_1                            | 2022-04-17                   |
    Then after not more than 60s, MD_Available_For_Sales table is empty for product: p_1

  @from:cucumber
  @Id:S0168.3_100
  @Id:S0168.3_400
  @Id:S0168.3_500
  Scenario: sync MD_Available_For_Sales when M_ShipmentSchedule is processed
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_15062022_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_15062022_2 | d_pricing_system_value_15062022_2 | d_pricing_system_description_15062022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_15062022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15062022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15062022_2 | N            | Y              | ps_1                          | D               |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_15062022_2 | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_15062022_2 | config_1                                | p_1                         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15062022_2"},"stock":-9} |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | s_1                   | CO            |
    And the shipment schedule identified by s_s_1 is processed after not more than 30 seconds

    Then after not more than 60s, MD_Available_For_Sales table is empty for product: p_1
    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                        |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15062022_2"},"stock":0} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  @Id:S0168.1_100
  Scenario: sync MD_Available_For_Sales and export stock when MD_Stock is created
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_15062022_3 |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_20062022_1 | d_pricing_system_value_20062022_1 | d_pricing_system_description_20062022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_20062022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_20062022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_20062022_2 | N            | Y              | ps_1                          | D               |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 50       | 0       |
    When the inventory identified by 11 is completed

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference      | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference | config_1                                | p_1                         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-11  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |

    Then after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 50             | 10             | -1002                           |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                              |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference"},"stock":36} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  @Id:S0168.1_200
  Scenario: Change Qty in SO and sync MD_Available_For_Sales with Shopware
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_14072022_2 |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_14072022_2 | d_pricing_system_value_14072022_2 | d_pricing_system_description_14072022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_14072022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_14072022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_14072022_2 | N            | Y              | ps_1                          | D               |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 50       | 0       |
    And the inventory identified by 11 is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 50             | 10             | -1002                           |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_14072022_2 | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_14072022_2 | config_1                                | p_1                         |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_14072022_2"},"stock":36} |

    When update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyOrdered |
      | ol_1                      | 20             |
    Then after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 50             | 20             | -1002                           |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_14072022_2"},"stock":27} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  @Id:S0168.1_300
  @Id:S0168.1_400
  Scenario: Create new SO and change qty in it, don't sync MD_Available_For_Sales with Shopware
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_14072022_1 |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_14072022_1 | d_pricing_system_value_14072022_1 | d_pricing_system_description_14072022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_14072022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_14072022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_20062022_2 | N            | Y              | ps_1                          | D               |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 400      | 0       |
    And the inventory identified by 11 is completed

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_14072022_1 | false                                  | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_14072022_1 | config_1                                | p_1                         |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-11  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 400            | 10             | -1002                           |

    And after not more than 60s, RabbitMQ MF_TO_ExternalSystem queue has no messages for criteria:
      | Command               | OPT.parameters.JsonAvailableForSales.ProductIdentifier |
      | Shopware6-exportStock | p_1                                                    |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyOrdered |
      | ol_1                      | 20             |

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 400            | 20             | -1002                           |

    Then after not more than 60s, RabbitMQ MF_TO_ExternalSystem queue has no messages for criteria:
      | Command               | OPT.parameters.JsonAvailableForSales.ProductIdentifier |
      | Shopware6-exportStock | p_1                                                    |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  @Id:S0168.2_100
  Scenario: New PO with material receipt and sync to Shopware
    Given load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_14072022_4 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_14072022_4 | pricing_system_value_14072022_4 | pricing_system_description_14072022_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_14072022_4 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_14072022_4 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_14072022_4 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789111 | endvendor_1              | Y                   | Y                   |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_14072022_4 | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_14072022_4 | config_1                                | p_1                         |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.DatePromised     |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    And the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_14072022_4      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_14072022_4      | N               | 1     | N               | 1     | N               | 100         | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_14072022_4      | inOut_14072022_4      |

    Then after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 100            | 0              | -1002                           |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_14072022_4"},"stock":90} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  @Id:S0168.2_200
  Scenario: Reverse material receipt and sync stock to Shopware
    Given load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_14072022_5 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_14072022_5 | pricing_system_value_14072022_5 | pricing_system_description_14072022_5 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_14072022_5 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_14072022_5 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_14072022_5 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789111 | endvendor_1              | Y                   | Y                   |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_14072022_5 | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_14072022_5 | config_1                                | p_1                         |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 50       | 0       |
    And the inventory identified by 11 is completed

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 50             | 0              | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_14072022_5"},"stock":45} |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.DatePromised     |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              | 2021-04-15T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    And the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_14072022_5      | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_14072022_5      | N               | 1     | N               | 1     | N               | 100         | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_14072022_5      | inOut_14072022_5      |

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 150            | 0              | -1002                           |

    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                          |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_14072022_5"},"stock":135} |

    And the material receipt identified by inOut_14072022_5 is reversed

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 50             | 0              | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_14072022_5"},"stock":45} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  @Id:S0168.3_300
  Scenario: Reverse shipment and sync stock to Shopware
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_15072022_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_15072022_1 | d_pricing_system_value_15072022_1 | d_pricing_system_description_15072022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_15072022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15072022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15072022_1 | N            | Y              | ps_1                          | D               |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_15072022_1 | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_15072022_1 | config_1                                | p_1                         |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 30       | 0       |
    And the inventory identified by 11 is completed

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 30             | 0              | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_1"},"stock":27} |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 30             | 10             | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_1"},"stock":18} |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | s_1                   | CO            |
    And the shipment schedule identified by s_s_1 is processed after not more than 30 seconds

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 20             | 0              | -1002                           |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_1"},"stock":18} |

    And the shipment identified by s_1 is reversed

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |

    # note: normally this is called by a scheduler
    And MD_Stock_Update_From_M_HUs process is called

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 30             | 10             | -1002                           |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_1"},"stock":18} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  @Id:S0168.3_200
  Scenario: Reactivate shipment and sync stock to Shopware
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_15072022_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_15072022_2 | d_pricing_system_value_15072022_2 | d_pricing_system_description_15072022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_15072022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15072022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15072022_2 | N            | Y              | ps_1                          | D               |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_15072022_2 | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_15072022_2 | config_1                                | p_1                         |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 30       | 0       |
    And the inventory identified by 11 is completed

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 30             | 0              | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_2"},"stock":27} |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 30             | 10             | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_2"},"stock":18} |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | s_1                   | CO            |
    And the shipment schedule identified by s_s_1 is processed after not more than 30 seconds

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 20             | 0              | -1002                           |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_2"},"stock":18} |

    And the shipment identified by s_1 is reactivated

    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | s_s_1                            |

    # note: normally this is called by a scheduler
    And MD_Stock_Update_From_M_HUs process is called

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 30             | 10             | -1002                           |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_2"},"stock":18} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  @Id:S0168.4_100
  Scenario: HU disposal and sync stock to Shopware
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_15072022_3 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_15072022_3 | d_pricing_system_value_15072022_3 | d_pricing_system_description_15072022_3 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_15072022_3 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15072022_3 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15072022_3 | N            | Y              | ps_1                          | D               |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_15072022_3 | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_15072022_3 | config_1                                | p_1                         |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 11                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 21                            | 11                        | p_1                     | PCE          | 100      | 0       |
    And the inventory identified by 11 is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | 21                            | createdCU_1        |

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 100            | 0              | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_3"},"stock":90} |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | 12                        | 540008         | 2021-07-11   | 1111           |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | 22                            | 12                        | p_1                     | PCE          | 10       | 0       |
    And the inventory identified by 12 is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | 22                            | createdCU_2        |

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 110            | 0              | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_3"},"stock":99} |

    And M_HU are disposed:
      | M_HU_ID.Identifier | MovementDate         |
      | createdCU_1        | 2021-04-11T21:00:00Z |

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 10             | 0              | -1002                           |

     # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                        |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_3"},"stock":9} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |

  @from:cucumber
  Scenario: Change ExternalSystem_Config_Shopware6.PercentageOfAvailableForSalesToSync and sync stock to Shopware
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_15072022_4 |

    And metasfresh contains M_PricingSystems
      | Identifier | Name                             | Value                             | OPT.Description                         | OPT.IsActive |
      | ps_1       | d_pricing_system_name_15072022_4 | d_pricing_system_value_15072022_4 | d_pricing_system_description_15072022_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | d_price_list_name_15072022_4 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15072022_4 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | Endcustomer_15072022_4 | N            | Y              | ps_1                          | D               |

    And add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue            | OPT.IsSyncAvailableForSalesToShopware6 | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | S6   | stockExportShopware_15072022_4 | true                                   | 10                                      |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference                 | OPT.ExternalSystem_Config_ID.Identifier | OPT.M_Product_ID.Identifier |
      | product_ref                       | Shopware6      | Product | stockProduct_reference_15072022_4 | config_1                                | p_1                         |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-11  | po_ref_mock     | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |

    And after not more than 60s, MD_Available_For_Sales table contains only the following records:
      | M_Product_ID.Identifier | QtyOnHandStock | QtyToBeShipped | StorageAttributesKey.Identifier |
      | p_1                     | 0              | 10             | -1002                           |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_4"},"stock":-9} |

    And update ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier | OPT.PercentageOfAvailableForSalesToSync |
      | config_1                            | 20                                      |

    # note: the productIdentifier's metasfreshId doesn't matter; the step only evaluates the externalReference and stock.
    And RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.JsonAvailableForSales                                                                                         |
      | config_1                            | {"productIdentifier": {"metasfreshId":999999,"externalReference":"stockProduct_reference_15072022_4"},"stock":-8} |

    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | config_1                            |