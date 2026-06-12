@from:cucumber
@ghActions:run_on_executor2
@allure.label.epic:E2300_Attributes
@allure.label.feature:F67042_HU_receipt_date
@F67042
Feature: HU_DateReceived attribute population
## me03#29320: M_InOut.afterComplete (and the production / inventory paths) populate
## HU_DateReceived on the assigned HUs so reservation queries can match by receipt date.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And all periods are open
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | IsQualityReturnWarehouse |
      | warehouse      | Y                        |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
      | component  |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_purchase | ps                 | CH           | CHF           | false |
      | pl_sales    | ps                 | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_purchase | pl_purchase    |
      | plv_sales    | pl_sales       |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp_purchase | plv_purchase           | product      | 8.0      | PCE      |
      | pp_sales    | plv_sales              | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | partner    | Y        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | partnerLoc | partner       | CH           | Y               | Y               |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU         |
      | TU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | LU_Version         | LU         | LU          | Y         |
      | TU_Version         | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID |
      | huPiItemLU      | LU_Version         | 10  | HU       | TU                |
      | huPiItemTU      | TU_Version         |     | MI       |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | product_TU_10CU         | huPiItemTU      | product      | 10  | 2021-01-01 |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom        | product      | 2021-01-01 | bomVersions               |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | bomLine    | bom               | component    | 2021-01-01 | 10       |
    And the PP_Product_BOM identified by bom is completed
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

  @from:cucumber
  Scenario: HU_DateReceived populated with the receipt MovementDate on vendor receipt
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_PricingSystem_ID | M_Warehouse_ID |
      | po         | N       | partner       | 2025-04-01  | POO         | ps                 | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po_l1      | po         | product      | 10         |
    When the order identified by po is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs                   | po         | po_l1          | partner       | partnerLoc             | product      | 10         | warehouse      |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID |
      | hu      | rs                   | N               | 1     | N               | 1     | N               | 10          | product_TU_10CU         | LU            |
    And create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID |
      | hu      | rs                   | receipt    |
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueDate  |
      | hu      | HU_DateReceived      | 2025-04-01 |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

  @from:cucumber
  Scenario: HU_DateReceived populated with the inventory MovementDate when a new HU is created via inventory
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID |
      | inv            | warehouse      | 2025-04-15   | product      | 0 PCE   | 10 PCE   | product_TU_10CU         | LU            | huInv   |
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueDate  |
      | huInv   | HU_DateReceived      | 2025-04-15 |

  @from:cucumber
  Scenario: HU_DateReceived preserved when a later inventory adjusts an existing HU
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID |
      | invInitial     | warehouse      | 2025-04-15   | product      | 0 PCE   | 10 PCE   | product_TU_10CU         | LU            | huInv   |
    When metasfresh contains M_Inventories:
      | M_Inventory_ID | M_Warehouse_ID | MovementDate |
      | invRecount     | warehouse      | 2025-04-20   |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID | M_Product_ID | QtyBook | QtyCount | M_HU_ID |
      | invRecount     | product      | 10 PCE  | 20 PCE   | huInv   |
    And the inventory identified by invRecount is completed
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueDate  |
      | huInv   | HU_DateReceived      | 2025-04-15 |

  @from:cucumber
  Scenario: HU_DateReceived populated with the customer-return MovementDate
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID |
      | inv            | warehouse      | 2025-04-01   | product      | 0 PCE   | 10 PCE   | product_TU_10CU         | LU            | hu      |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so         | Y       | partner       | 2025-04-01  | ps                 | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_l1      | so         | product      | 10         |
    And the order identified by so is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | sched      | so_l1          | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | IsOnTheFlyPickToPackingInstructions |
      | sched                 | D            | true                | false       | true                                |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched                 | shipment   |
    And generate customer return from shipment
      | M_InOut_ID | CustomerReturn_ID |
      | shipment   | customerReturn    |
    And update M_InOut:
      | M_InOut_ID     | MovementDate |
      | customerReturn | 2025-04-15   |
    And the return inOut identified by customerReturn is completed
    And load HUs assigned to M_InOut
      | M_InOut_ID     | M_HU_ID  |
      | customerReturn | returnHu |
    Then M_HU_Attribute is validated
      | M_HU_ID  | M_Attribute_ID.Value | ValueDate  |
      | returnHu | HU_DateReceived      | 2025-04-15 |

  @from:cucumber
  Scenario: HU_DateReceived unchanged when an HU is sent back via vendor return
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_PricingSystem_ID | M_Warehouse_ID |
      | po         | N       | partner       | 2025-04-01  | POO         | ps                 | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po_l1      | po         | product      | 10         |
    And the order identified by po is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | rs                   | po         | po_l1          | partner       | partnerLoc             | product      | 10         | warehouse      |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_ID | M_ReceiptSchedule_ID | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID |
      | hu      | rs                   | N               | 1     | N               | 1     | N               | 10          | product_TU_10CU         | LU            |
    And create material receipt
      | M_HU_ID | M_ReceiptSchedule_ID | M_InOut_ID |
      | hu      | rs                   | receipt    |
    When generate vendor return from receipt
      | M_InOut_ID | VendorReturn_ID |
      | receipt    | vendorReturn    |
    And update M_InOut:
      | M_InOut_ID   | MovementDate |
      | vendorReturn | 2025-04-15   |
    And the return inOut identified by vendorReturn is completed
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueDate  |
      | hu      | HU_DateReceived      | 2025-04-01 |

  @from:cucumber
  Scenario: HU_DateReceived unchanged when an HU is shipped to a customer
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_Warehouse_ID | MovementDate | M_Product_ID | QtyBook | QtyCount | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | M_HU_ID |
      | inv            | warehouse      | 2025-04-01   | product      | 0 PCE   | 10 PCE   | product_TU_10CU         | LU            | hu      |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so         | Y       | partner       | 2025-04-01  | ps                 | warehouse      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_l1      | so         | product      | 10         |
    And the order identified by so is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | sched      | so_l1          | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | IsOnTheFlyPickToPackingInstructions |
      | sched                 | D            | false               | false       | true                                |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched                 | shipment   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | shipment   | 2025-04-15   |
    And the shipment identified by shipment is completed
    Then M_HU_Attribute is validated
      | M_HU_ID | M_Attribute_ID.Value | ValueDate  |
      | hu      | HU_DateReceived      | 2025-04-01 |

  @from:cucumber
  Scenario: HU_DateReceived populated with the production date when a HU is produced via PP_Order receipt
    Given metasfresh contains PP_Product_Plannings
      | Identifier         | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | productionPlanning | product      | bomVersions               | false        |
    When create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID | QtyEntered | S_Resource_ID | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrder                | MOP         | product      | 10         | testResource  | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | Y                |
    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID    | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID |
      | ppOrder     | producedTU | N               | 0     | N               | 1     | N               | 10          | product_TU_10CU         |
    Then M_HU_Attribute is validated
      | M_HU_ID    | M_Attribute_ID.Value | ValueDate  |
      | producedTU | HU_DateReceived      | 2025-04-01 |
