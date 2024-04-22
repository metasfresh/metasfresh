@from:cucumber
@ghActions:run_on_executor5
Feature: Handling unit rest controller

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |

  Scenario: retrieve HU by id and update HU attributes EPs validation
    Given metasfresh contains M_Products:
      | Identifier | Value     | Name      |
      | huProduct  | huProduct | huProduct |

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
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | huProduct               | 10  | 2022-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | huProduct_inventory       | 2022-01-02   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 9        | PCE          |
    And the inventory identified by huProduct_inventory is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier |
      | createdCU          | packingVersionCU              | A        | locatorHauptlager           |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedCU           | createdCU          | huProduct               | 9   |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 9     | huProductTU                        | createdTU                 | newCreatedCU              |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | createdTU          | packingVersionTU              | huProductTU                            | A        | locatorHauptlager           |                            |
      | newCreatedCU       | packingVersionCU              |                                        | A        | locatorHauptlager           | createdTU                  |
      | createdCU          | packingVersionCU              |                                        | D        | locatorHauptlager           |                            |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedTU           | createdTU          | huProduct               | 9   |
      | storageNewCreatedCU        | newCreatedCU       | huProduct               | 9   |
      | storageCreatedCU           | createdCU          | huProduct               | 0   |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | createdLU          | packingVersionLU              |                                        | A        | locatorHauptlager           |                            |
      | createdTU          | packingVersionTU              | huProductTU                            | A        | locatorHauptlager           | createdLU                  |
      | newCreatedCU       | packingVersionCU              |                                        | A        | locatorHauptlager           | createdTU                  |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedLU           | createdLU          | huProduct               | 9   |
      | storageCreatedTU           | createdTU          | huProduct               | 9   |
      | storageNewCreatedCU        | newCreatedCU       | huProduct               | 9   |

    When store HU endpointPath /api/v2/material/handlingunits/byId/:createdLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs  | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdLU          | LU         | createdTU    | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | createdTU          | TU         | newCreatedCU | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | newCreatedCU       | CU         |              | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    When store HU endpointPath /api/v2/material/handlingunits/byId/:newCreatedCU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | newCreatedCU       | CU         |             | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |

    And store JsonHUAttributesRequest in context
      | M_HU_ID.Identifier | attributes.Lot-Nummer |
      | createdTU          | LotNumberTest         |

    And the metasfresh REST-API endpoint path '/api/v2/material/handlingunits' receives a 'PUT' request with the payload from context and responds with '200' status code

    And store HU endpointPath /api/v2/material/handlingunits/byId/:createdLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs  | attributes.Lot-Nummer | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | createdLU          | LU         | createdTU    | null                  | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | createdTU          | TU         | newCreatedCU | LotNumberTest         | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | newCreatedCU       | CU         |              | LotNumberTest         | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |


  Scenario: retrieve HU by id and update ClearanceStatus EPs validation
    Given metasfresh contains M_Products:
      | Identifier | Value     | Name      |
      | huProduct  | huProduct | huProduct |

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
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | huProduct               | 10  | 2022-01-01 |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | huProduct_inventory       | 2022-01-02   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 9        | PCE          |
    And the inventory identified by huProduct_inventory is completed

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier |
      | createdCU          | packingVersionCU              | A        | locatorHauptlager           |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedCU           | createdCU          | huProduct               | 9   |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 9     | huProductTU                        | createdTU                 | newCreatedCU              |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | createdTU          | packingVersionTU              | huProductTU                            | A        | locatorHauptlager           |                            |
      | newCreatedCU       | packingVersionCU              |                                        | A        | locatorHauptlager           | createdTU                  |
      | createdCU          | packingVersionCU              |                                        | D        | locatorHauptlager           |                            |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedTU           | createdTU          | huProduct               | 9   |
      | storageNewCreatedCU        | newCreatedCU       | huProduct               | 9   |
      | storageCreatedCU           | createdCU          | huProduct               | 0   |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | createdLU          | packingVersionLU              |                                        | A        | locatorHauptlager           |                            |
      | createdTU          | packingVersionTU              | huProductTU                            | A        | locatorHauptlager           | createdLU                  |
      | newCreatedCU       | packingVersionCU              |                                        | A        | locatorHauptlager           | createdTU                  |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageCreatedLU           | createdLU          | huProduct               | 9   |
      | storageCreatedTU           | createdTU          | huProduct               | 9   |
      | storageNewCreatedCU        | newCreatedCU       | huProduct               | 9   |

    And store JsonSetClearanceStatusRequest in context
      | M_HU_ID.Identifier | ClearanceStatus | OPT.ClearanceNote |
      | createdTU          | Cleared         | ClearedStatusNote |

    And the metasfresh REST-API endpoint path '/api/v2/material/handlingunits/clearance' receives a 'PUT' request with the payload from context and responds with '200' status code

    And store HU endpointPath /api/v2/material/handlingunits/byId/:createdLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs  | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus | OPT.ClearanceStatus.key | OPT.ClearanceNote |
      | createdLU          | LU         | createdTU    | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | null                    | null              |
      | createdTU          | TU         | newCreatedCU | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Cleared                 | ClearedStatusNote |
      | newCreatedCU       | CU         |              | huProduct            | huProduct             | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Cleared                 | ClearedStatusNote |


  Scenario: Set clearance status by QRCode on aggregated TU
    Given metasfresh contains M_Products:
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
      | supplierLocation_PO | supplierPO01 | supplier_PO              | true                | true                |
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
      | huLuTuConfig                          | processedLU        | receiptSchedule_PO              | N               | 1     | N               | 2     | N               | 9           | huItemPurchaseProduct              | huPackingLU                  |

    When create material receipt
      | M_InOut_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier |
      | inOut_PO              | processedLU        | receiptSchedule_PO              |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.HUStatus |
      | processedLU        | A            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | processedLU        | packingVersionLU              | A        | locatorHauptlager           |                            |
      | processedTU        | packingVersionCU              | A        | locatorHauptlager           | processedLU                |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | storageProcessedLU         | processedLU        | purchaseProduct         | 18  |
      | storageProcessedTU         | processedTU        | purchaseProduct         | 18  |

    And metasfresh contains M_HU_QRCode
      | M_HU_QRCode_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | M_HU_PI_ID.Identifier | M_HU_PI_Version_ID.Identifier |
      | qr2                       | processedTU        | purchaseProduct         | huPackingTU           | packingVersionCU              |
      | qr1                       | processedTU        | purchaseProduct         | huPackingTU           | packingVersionCU              |

    And store JsonSetClearanceStatusRequest in context
      | OPT.M_HU_QRCode_ID.Identifier | ClearanceStatus | OPT.ClearanceNote |
      | qr2                           | Cleared         | ClearedStatusNote |

    When the metasfresh REST-API endpoint path '/api/v2/material/handlingunits/clearance' receives a 'PUT' request with the payload from context and responds with '200' status code

    And load M_HU by QR code:
      | M_HU_QRCode_ID.Identifier | M_HU_ID.Identifier |
      | qr2                       | splitTU            |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier | OPT.M_HU_Parent.Identifier |
      | splitTU            | packingVersionTU              | huItemPurchaseProduct                  | A        | locatorHauptlager           |                            |
      | includedCU         | packingVersionCU              |                                        | A        | locatorHauptlager           | splitTU                    |

    And store HU endpointPath /api/v2/material/handlingunits/byId/:splitTU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus | OPT.ClearanceStatus.key | OPT.ClearanceNote |
      | splitTU            | TU         | includedCU  | purchaseProduct      | purchaseProduct       | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Cleared                 | ClearedStatusNote |
      | includedCU         | CU         |             | purchaseProduct      | purchaseProduct       | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        | Cleared                 | ClearedStatusNote |

    And store HU endpointPath /api/v2/material/handlingunits/byId/:processedLU in context

    And a 'GET' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate "retrieve hu" response:
      | M_HU_ID.Identifier | jsonHUType | includedHUs | products.productName | products.productValue | products.qty | products.uom | warehouseValue.Identifier | locatorValue.Identifier | numberOfAggregatedHUs | huStatus |
      | processedLU        | LU         | processedTU | purchaseProduct      | purchaseProduct       | 9            | PCE          | warehouseStd              | locatorHauptlager       | 0                     | A        |
      | processedTU        | TU         |             | purchaseProduct      | purchaseProduct       | 9            | PCE          | warehouseStd              | locatorHauptlager       | 1                     | A        |


  Scenario: Get a non-existing HU by QR Code
    Given metasfresh contains M_Products:
      | Identifier | Value                 | Name                 | REST.Context |
      | product    | testNewHUQRCode Value | testNewHUQRCode Name | M_Product_ID |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID | Value        | Name            | REST.Context |
      | TU         | My TU @Date@ | My TU Name - PI | M_HU_PI_ID   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                 | HU_UnitType | IsCurrent |
      | TU                            | TU                    | My TU Name - current | TU          | Y         |

    And put REST context variables
      | Name   | Value                                                                                                                                                                                                                                                                                                                                                  |
      | qrCode | HU#1#{\"id\":\"246d30ad0476a373263b777b41b2-09054\",\"packingInfo\":{\"huUnitType\":\"TU\",\"packingInstructionsId\":@M_HU_PI_ID@,\"caption\":\"My TU Name - current\"},\"product\":{\"id\":@M_Product_ID@,\"code\":\"testNewHUQRCode Value\",\"name\":\"testNewHUQRCode Name\"},\"attributes\":[{\"code\":\"Lot-Nummer\",\"displayName\":\"Lot-Nummer\",\"value\":\"aaa\"}]} |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/material/handlingunits/byQRCode' and fulfills with '200' status code
      """
      {
        "qrCode": "@qrCode@"
      }
      """
    Then the metasfresh REST-API responds with
      """
      {
          "result": {
              "id": null,
              "huStatus": null,
              "huStatusCaption": "-",
              "displayName": "My TU Name - current",
              "qrCode": {
                  "code": "@qrCode@",
                  "displayable": "09054"
              },
              "numberOfAggregatedHUs": 0,
              "products": [
                  {
                      "productValue": "testNewHUQRCode Value",
                      "productName": "testNewHUQRCode Name",
                      "qty": "0",
                      "uom": "PCE"
                  }
              ],
              "attributes": {
                  "Lot-Nummer": "aaa"
              },
              "attributes2": {
                  "list": [
                      {
                          "code": "Lot-Nummer",
                          "caption": "Lot-Nummer",
                          "value": "aaa"
                      }
                  ]
              },
              "clearanceStatus": null,
              "clearanceNote": null,
              "jsonHUType": "TU",
              "includedHUs": null
          }
      }
      """

