@from:cucumber
@ghActions:run_on_executor5
Feature: Delivery rules with and without quantity in stock

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-08-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier | Name              | Value              | OPT.IsActive |
      | ps_1       | PricingSystemName | PricingSystemValue | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | PriceListName | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | plv_1      | pl_1                      | SalesOrder-PLV | 2022-08-01 |

  @from:cucumber
  @Id:S0159_A_10
  Scenario: C_BPartner.DeliveryRule = `Availability`, product is marked as `Stocked` but has no available stock
  _Given M_Product.IsStocked = true
  _And C_BPartner.DeliveryRule = Availability
  _And create order with C_OrderLine.QtyEntered = 1
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 0
  _And validate that M_InOut could not be generated

    And metasfresh contains M_Products:
      | Identifier          | Name                | IsStocked |
      | product_A_stocked_1 | Product_A_stocked_1 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product_A_stocked_1     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_A_stocked_1 | BPartner_A_stocked_1 | N            | Y              | ps_1                          | A                |
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_A_stocked_1 | true    | bpartner_A_stocked_1     | 2022-08-16  |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_A_stocked_1 | order_A_stocked_1     | product_A_stocked_1     | 1          |

    When the order identified by order_A_stocked_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_1 | orderLine_A_stocked_1     | N             | 0            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_1               | D            | true                | false       |

    Then validate no M_InOut found for C_Order identified by order_A_stocked_1


  @from:cucumber
  @Id:S0159_A_20
  Scenario: C_BPartner.DeliveryRule = `Availability`, product is not marked as `Stocked` and has no available stock
  _Given M_Product.IsStocked = false
  _And C_BPartner.DeliveryRule = Availability
  _And create order with C_OrderLine.QtyEntered = 1
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 1
  _When M_InOut is generated for shipmentSchedule
  _Then validate that hu in stock was not picked

    And metasfresh contains M_Products:
      | Identifier             | Name                   | IsStocked |
      | product_A_notStocked_1 | Product_A_notStocked_1 | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_1                             | product_A_notStocked_1  | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier              | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_A_notStocked_1 | BPartner_A_notStocked_1 | N            | Y              | ps_1                          | A                |
    And metasfresh contains C_Orders:
      | Identifier           | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_A_notStocked_1 | true    | bpartner_A_notStocked_1  | 2022-08-16  |
    And metasfresh contains C_OrderLines:
      | Identifier               | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_A_notStocked_1 | order_A_notStocked_1  | product_A_notStocked_1  | 1          |

    When the order identified by order_A_notStocked_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_2 | orderLine_A_notStocked_1  | N             | 1            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_2               | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_2               | inOut                 |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentSchedule_2               | 1         | true      | false                       | null                  |


  @from:cucumber
  @Id:S0159_A_30
  Scenario: C_BPartner.DeliveryRule = `Availability`, product is marked as `Stocked` and has available stock
  _Given M_Product.IsStocked = true
  _And C_BPartner.DeliveryRule = Availability
  _And one quantity in stock for product
  _And create order with C_OrderLine.QtyEntered = 1
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 1
  _When M_InOut is generated for shipmentSchedule
  _Then validate that hu in stock was picked

    And metasfresh contains M_Products:
      | Identifier          | Name                | IsStocked |
      | product_A_stocked_2 | Product_A_stocked_2 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_3       | plv_1                             | product_A_stocked_2     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | inventory_A_stocked_2     | 540008         | 2022-08-16   | 160822_1       |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLine_A_stocked_2     | inventory_A_stocked_2     | product_A_stocked_2     | PCE          | 1        | 0       |
    And the inventory identified by inventory_A_stocked_2 is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product_A_stocked_2     | 1         |
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine_A_stocked_2     | hu_A_stocked       |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_A_stocked_2 | BPartner_A_stocked_2 | N            | Y              | ps_1                          | A                |
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_A_stocked_2 | true    | bpartner_A_stocked_2     | 2022-08-16  |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_A_stocked_2 | order_A_stocked_2     | product_A_stocked_2     | 1          |
    And the order identified by order_A_stocked_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_3 | orderLine_A_stocked_2     | N             | 1            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_3               | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_3               | inOut                 |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentSchedule_3               | 1         | true      | true                        | hu_A_stocked          |


  @from:cucumber
  @Id:S0159_A_40
  Scenario: C_BPartner.DeliveryRule = `Availability`, product is not marked as `Stocked` but has available stock
  _Given M_Product.IsStocked = false
  _And C_BPartner.DeliveryRule = Availability
  _And one quantity in stock for product
  _And create order with C_OrderLine.QtyEntered = 1
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 1
  _When M_InOut is generated for shipmentSchedule
  _Then validate that hu in stock was not picked

    And metasfresh contains M_Products:
      | Identifier             | Name                   | IsStocked |
      | product_A_notStocked_2 | Product_A_notStocked_2 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_4       | plv_1                             | product_A_notStocked_2  | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | inventory_A_notStocked_2  | 540008         | 2022-08-16   | 160822_2       |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLine_A_notStocked_2  | inventory_A_notStocked_2  | product_A_notStocked_2  | PCE          | 1        | 0       |
    And the inventory identified by inventory_A_notStocked_2 is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product_A_notStocked_2  | 1         |
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine_A_notStocked_2  | hu_A_notStocked    |
    And metasfresh contains C_BPartners:
      | Identifier              | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_A_notStocked_2 | BPartner_A_notStocked_2 | N            | Y              | ps_1                          | A                |
    And update M_Product:
      | M_Product_ID.Identifier | IsStocked |
      | product_A_notStocked_2  | false     |
    And metasfresh contains C_Orders:
      | Identifier           | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_A_notStocked_2 | true    | bpartner_A_notStocked_2  | 2022-08-16  |
    And metasfresh contains C_OrderLines:
      | Identifier               | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_A_notStocked_2 | order_A_notStocked_2  | product_A_notStocked_2  | 1          |
    And the order identified by order_A_notStocked_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_4 | orderLine_A_notStocked_2  | N             | 1            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_4               | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_4               | inOut                 |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentSchedule_4               | 1         | true      | false                       | null                  |


  @from:cucumber
  @Id:S0159_A_50
  Scenario: C_BPartner.DeliveryRule = `Force`, product is not marked as `Stocked` and has no available stock
  _Given M_Product.IsStocked = false
  _And C_BPartner.DeliveryRule = Force
  _And create order with C_OrderLine.QtyEntered = 1
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 1
  _When M_InOut is generated for shipmentSchedule
  _Then validate that hu in stock was not picked

    And metasfresh contains M_Products:
      | Identifier             | Name                   | IsStocked |
      | product_F_notStocked_1 | Product_F_notStocked_1 | false     |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_5       | plv_1                             | product_F_notStocked_1  | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier              | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_F_notStocked_1 | BPartner_F_notStocked_1 | N            | Y              | ps_1                          | F                |
    And metasfresh contains C_Orders:
      | Identifier           | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_F_notStocked_1 | true    | bpartner_F_notStocked_1  | 2022-08-16  |
    And metasfresh contains C_OrderLines:
      | Identifier               | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_F_notStocked_1 | order_F_notStocked_1  | product_F_notStocked_1  | 1          |
    And the order identified by order_F_notStocked_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_5 | orderLine_F_notStocked_1  | N             | 1            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_5               | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_5               | inOut                 |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentSchedule_5               | 1         | true      | false                       | null                  |


  @from:cucumber
  @Id:S0159_A_60
  Scenario: C_BPartner.DeliveryRule = `Force`, product is marked as `Stocked` but has no available stock
  _Given M_Product.IsStocked = true
  _And C_BPartner.DeliveryRule = Force
  _And create order with C_OrderLine.QtyEntered = 1
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 1
  _When M_InOut is generated for shipmentSchedule
  _Then validate that hu in stock was not picked

    And metasfresh contains M_Products:
      | Identifier          | Name                | IsStocked |
      | product_F_stocked_1 | Product_F_stocked_1 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_6       | plv_1                             | product_F_stocked_1     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_F_stocked_1 | BPartner_F_stocked_1 | N            | Y              | ps_1                          | F                |
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_F_stocked_1 | true    | bpartner_F_stocked_1     | 2022-08-16  |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_F_stocked_1 | order_F_stocked_1     | product_F_stocked_1     | 1          |
    And the order identified by order_F_stocked_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_6 | orderLine_F_stocked_1     | N             | 1            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_6               | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_6               | inOut                 |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentSchedule_6               | 1         | true      | false                       | null                  |


  @from:cucumber
  @Id:S0159_A_70
  Scenario: C_BPartner.DeliveryRule = `Force`, product is not marked as `Stocked` but has available stock
  _Given M_Product.IsStocked = false
  _And C_BPartner.DeliveryRule = Force
  _And one quantity in stock for product
  _And create order with C_OrderLine.QtyEntered = 1
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 1
  _When M_InOut is generated for shipmentSchedule
  _Then validate that hu in stock was not picked

    And metasfresh contains M_Products:
      | Identifier             | Name                   | IsStocked |
      | product_F_notStocked_2 | Product_F_notStocked_2 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_7       | plv_1                             | product_F_notStocked_2  | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | inventory_F_notStocked_2  | 540008         | 2022-08-16   | 160822_3       |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLine_F_notStocked_2  | inventory_F_notStocked_2  | product_F_notStocked_2  | PCE          | 1        | 0       |
    And the inventory identified by inventory_F_notStocked_2 is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product_F_notStocked_2  | 1         |
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine_F_notStocked_2  | hu_F_notStocked    |
    And metasfresh contains C_BPartners:
      | Identifier              | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_F_notStocked_2 | BPartner_F_notStocked_2 | N            | Y              | ps_1                          | F                |
    And update M_Product:
      | M_Product_ID.Identifier | IsStocked |
      | product_F_notStocked_2  | false     |
    And metasfresh contains C_Orders:
      | Identifier           | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_F_notStocked_2 | true    | bpartner_F_notStocked_2  | 2022-08-16  |
    And metasfresh contains C_OrderLines:
      | Identifier               | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_F_notStocked_2 | order_F_notStocked_2  | product_F_notStocked_2  | 1          |
    And the order identified by order_F_notStocked_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_7 | orderLine_F_notStocked_2  | N             | 1            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_7               | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_7               | inOut                 |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentSchedule_7               | 1         | true      | false                       | null                  |


  @from:cucumber
  @Id:S0159_A_80
  Scenario: C_BPartner.DeliveryRule = `Force`, product is marked as `Stocked` and has available stock
  _Given M_Product.IsStocked = true
  _And C_BPartner.DeliveryRule = Force
  _And one quantity in stock for product
  _And create order with C_OrderLine.QtyEntered = 1
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 1
  _When M_InOut is generated for shipmentSchedule
  _Then validate that hu in stock was picked

    And metasfresh contains M_Products:
      | Identifier          | Name                | IsStocked |
      | product_F_stocked_2 | Product_F_stocked_2 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_8       | plv_1                             | product_F_stocked_2     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | inventory_F_stocked_2     | 540008         | 2022-08-16   | 160822_4       |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLine_F_stocked_2     | inventory_F_stocked_2     | product_F_stocked_2     | PCE          | 1        | 0       |
    And the inventory identified by inventory_F_stocked_2 is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product_F_stocked_2     | 1         |
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine_F_stocked_2     | hu_F_stocked       |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_F_stocked_2 | BPartner_F_stocked_2 | N            | Y              | ps_1                          | F                |
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_F_stocked_2 | true    | bpartner_F_stocked_2     | 2022-08-16  |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_F_stocked_2 | order_F_stocked_2     | product_F_stocked_2     | 1          |
    And the order identified by order_F_stocked_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_8 | orderLine_F_stocked_2     | N             | 1            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_8               | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_8               | inOut                 |
    And validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule
      | M_ShipmentSchedule_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentSchedule_8               | 1         | true      | true                        | hu_F_stocked          |


  @from:cucumber
  @Id:S0159_B_10
  @Id:S0223_200
  Scenario: C_BPartner.DeliveryRule = `Availability` - FIFO
  _Given M_Product.IsStocked = true
  _And C_BPartner.DeliveryRule = Availability
  _And quantity in stock for product is equal to 12 ( 2,5,5 - M_HUs created in this order for existing inventoryLines)
  _And create order with C_OrderLine.QtyEntered = 20
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 12
  _When M_InOut is generated for shipmentSchedule
  _Then validate all HUs in stock were picked in FIFO order

    And metasfresh contains M_Products:
      | Identifier     | Name           | IsStocked |
      | product_FIFO_1 | Product_FIFO_1 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_9       | plv_1                             | product_FIFO_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | inventory_FIFO1_1         | 540008         | 2022-08-17   | 160822_fifo_1  |
      | inventory_FIFO1_2         | 540008         | 2022-08-17   | 160822_fifo_2  |
      | inventory_FIFO1_3         | 540008         | 2022-08-17   | 160822_fifo_3  |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLine_FIFO1_1         | inventory_FIFO1_1         | product_FIFO_1          | PCE          | 2        | 0       |
      | inventoryLine_FIFO1_2         | inventory_FIFO1_2         | product_FIFO_1          | PCE          | 5        | 0       |
      | inventoryLine_FIFO1_3         | inventory_FIFO1_3         | product_FIFO_1          | PCE          | 5        | 0       |
    And the inventory identified by inventory_FIFO1_1 is completed
    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | product_FIFO_1          | 2022-08-17  |                              | 2                            | 2                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 2                              | 2                          | 0                                  |
    And the inventory identified by inventory_FIFO1_2 is completed
    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | product_FIFO_1          | 2022-08-17  |                              | 7                            | 7                  | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 7                              | 7                          | 0                                  |
    And the inventory identified by inventory_FIFO1_3 is completed
    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PurchaseOrder_AtDate |
      | cp_1       | product_FIFO_1          | 2022-08-17  |                              | 12                           | 12                 | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 12                             | 12                         | 0                                  |
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product_FIFO_1          | 12        |
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine_FIFO1_1         | hu_fifo_first      |
      | inventoryLine_FIFO1_2         | hu_fifo_second     |
      | inventoryLine_FIFO1_3         | hu_fifo_third      |
    And metasfresh contains C_BPartners:
      | Identifier      | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_FIFO_1 | BPartner_FIFO_1 | N            | Y              | ps_1                          | A                |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_FIFO_1 | true    | bpartner_FIFO_1          | 2022-08-17  |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_FIFO_1 | order_FIFO_1          | product_FIFO_1          | 20         |
    And the order identified by order_FIFO_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_FIFO_1 | orderLine_FIFO_1          | N             | 12           |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_FIFO_1          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_FIFO_1          | inOut_FIFO_1          |
    And M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule shipmentSchedule_FIFO_1 can be located in specified order
      | M_ShipmentSchedule_QtyPicked_ID.Identifier |
      | shipmentScheduleQtyPicked_1                |
      | shipmentScheduleQtyPicked_2                |
      | shipmentScheduleQtyPicked_3                |
    And validate M_ShipmentSchedule_QtyPicked by id
      | M_ShipmentSchedule_QtyPicked_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentScheduleQtyPicked_1                | 2         | true      | true                        | hu_fifo_first         |
      | shipmentScheduleQtyPicked_2                | 5         | true      | true                        | hu_fifo_second        |
      | shipmentScheduleQtyPicked_3                | 5         | true      | true                        | hu_fifo_third         |

  @flaky
  @from:cucumber
  @Id:S0159_B_20
  Scenario: C_BPartner.DeliveryRule = `Availability` - FIFO
  _Given M_Product.IsStocked = true
  _And C_BPartner.DeliveryRule = Availability
  _And quantity in stock for product is equal to 20 ( 5,10,5 - M_HUs created in this order for existing inventoryLines)
  _And create order with C_OrderLine.QtyEntered = 6
  _When order is completed
  _Then validate M_ShipmentSchedule.QtyToDeliver = 6
  _When M_InOut is generated for shipmentSchedule
  _Then validate HUs in stock were picked in FIFO order (first HU picked, second HU was the sourceHU for the actual picked HU)

    And metasfresh contains M_Products:
      | Identifier     | Name           | IsStocked |
      | product_FIFO_2 | Product_FIFO_2 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_10      | plv_1                             | product_FIFO_2          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate | OPT.DocumentNo |
      | inventory_FIFO2_1         | 540008         | 2022-08-17   | 170822_fifo_1  |
      | inventory_FIFO2_2         | 540008         | 2022-08-17   | 170822_fifo_2  |
      | inventory_FIFO2_3         | 540008         | 2022-08-17   | 170822_fifo_3  |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLine_FIFO2_1         | inventory_FIFO2_1         | product_FIFO_2          | PCE          | 5        | 0       |
      | inventoryLine_FIFO2_2         | inventory_FIFO2_2         | product_FIFO_2          | PCE          | 10       | 0       |
      | inventoryLine_FIFO2_3         | inventory_FIFO2_3         | product_FIFO_2          | PCE          | 5        | 0       |
    And the inventory identified by inventory_FIFO2_1 is completed
    And the inventory identified by inventory_FIFO2_2 is completed
    And the inventory identified by inventory_FIFO2_3 is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | product_FIFO_2          | 20        |
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inventoryLine_FIFO2_1         | hu_fifo_first_2    |
      | inventoryLine_FIFO2_2         | hu_fifo_second_2   |
      | inventoryLine_FIFO2_3         | hu_fifo_third_2    |
    And metasfresh contains C_BPartners:
      | Identifier      | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.DeliveryRule |
      | bpartner_FIFO_2 | BPartner_FIFO_2 | N            | Y              | ps_1                          | A                |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_FIFO_2 | true    | bpartner_FIFO_2          | 2022-08-17  |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_FIFO_2 | order_FIFO_2          | product_FIFO_2          | 6          |
    And the order identified by order_FIFO_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier              | C_OrderLine_ID.Identifier | IsToRecompute | QtyToDeliver |
      | shipmentSchedule_FIFO_2 | orderLine_FIFO_2          | N             | 6            |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_FIFO_2          | D            | true                | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_FIFO_2          | inOut_FIFO_2          |
    And M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule shipmentSchedule_FIFO_2 can be located in specified order
      | M_ShipmentSchedule_QtyPicked_ID.Identifier |
      | shipmentScheduleQtyPicked_1                |
      | shipmentScheduleQtyPicked_2                |
    And after not more than 60s, load newly created M_HU record based on SourceHU
      | M_HU_ID.Identifier | VHU_Source_ID.Identifier | Qty | HUTraceType    |
      | hu_fifo_picked     | hu_fifo_second_2         | 1   | TRANSFORM_LOAD |
    And validate M_ShipmentSchedule_QtyPicked by id
      | M_ShipmentSchedule_QtyPicked_ID.Identifier | QtyPicked | Processed | IsAnonymousHuPickedOnTheFly | OPT.VHU_ID.Identifier |
      | shipmentScheduleQtyPicked_1                | 5         | true      | true                        | hu_fifo_first_2       |
      | shipmentScheduleQtyPicked_2                | 1         | true      | true                        | hu_fifo_picked        |
