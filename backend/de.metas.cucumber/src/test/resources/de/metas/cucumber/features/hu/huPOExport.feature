@from:cucumber
@ignore
@ghActions:run_on_executor5
Feature: Handling unit export from purchase order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  Scenario: HU export from purchase order
    Given add external system parent-child pair
      | ExternalSystem_Config_ID.Identifier | Type | ExternalSystemValue | OPT.IsSyncHUsOnMaterialReceipt | OPT.IsSyncHUsOnProductionReceipt |
      | GRSConfig_PO                        | GRS  | testGRSSignum_PO    | Y                              | Y                                |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |

    And metasfresh contains M_Products:
      | Identifier      | Value           | Name            |
      | purchaseProduct | purchaseProduct | purchaseProduct |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU           | huPackingLU     |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 20  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | purchaseProduct         | 10  | 2022-01-01 |

    And metasfresh contains M_PricingSystems
      | Identifier | Name  | Value | OPT.IsActive |
      | ps_PO      | ps_PO | ps_PO | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_PO_name | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_PO     | pl_PO                     | plv_PO | 2022-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | purchaseProduct         | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name        | Value       | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | supplier_PO | supplier_PO | supplier_PO | Y            | N              | ps_PO                         | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN          | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | supplierLocation_PO | supplierPO01 | supplier_PO              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order_PO   | N       | supplier_PO              | 2022-01-05  | po_ref          | 1000012              | POO             | ps_PO                             |

    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO | order_PO              | purchaseProduct         | 18         |

    And the order identified by order_PO is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | order_PO              | orderLine_PO              | supplier_PO              | supplierLocation_PO               | purchaseProduct         | 18         | warehouseStd              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 2     | N               | 9           | huItemPurchaseProduct              | huPackingLU                  |

    And RabbitMQ MF_TO_ExternalSystem queue is purged

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | inOut_PO              |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.HUStatus |
      | processedTopHU     | A            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | processedTopHU     | packingVersionLU              | A        | locatorHauptlager           |                            |
      | processedTU        | packingVersionCU              | A        | locatorHauptlager           | processedTopHU             |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageProcessedTopHU      | processedTopHU     | purchaseProduct         | 18  |
      | storageProcessedTU         | processedTU        | purchaseProduct         | 18  |

    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | OPT.M_HU_ID.Identifier |
      | GRSConfig_PO                        | processedTopHU         |

    When store HU endpointPath /api/v2/material/handlingunits/byId/:processedTopHU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus | OPT.ClearanceStatus.key | OPT.ClearanceStatus.caption | OPT.ClearanceNote           |
      | processedTopHU     | LU         | processedTU | purchaseProduct      | purchaseProduct       | 18           | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Locked                  | Gesperrt                    | Erwartet Freigabe durch GRS |
      | processedTU        | TU         |             | purchaseProduct      | purchaseProduct       | 18           | PCE          | warehouseStd              | locatorHauptlager       | 2                     | A        | Locked                  | Gesperrt                    | Erwartet Freigabe durch GRS |
    And deactivate ExternalSystem_Config
      | ExternalSystem_Config_ID.Identifier |
      | GRSConfig_PO                        |