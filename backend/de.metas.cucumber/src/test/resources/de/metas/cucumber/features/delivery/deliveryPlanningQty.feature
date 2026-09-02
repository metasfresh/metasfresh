@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29050_Delivery_Planning
@ghActions:run_on_executor5
Feature: Delivery planning quantities

  Splitting a delivery planning divides its planned figures across the resulting plannings; each
  share sums back to the figure the split started from.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically

    Given metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_SO | pricingSystem      | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID |
      | priceListVersion_SO | priceList_SO   |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_SO    | product      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | customerLocation | 1234568110599 | customer                 | true                | true                |
    And contains M_Shippers
      | Identifier  | OPT.IsCreateDeliveryPlanning |
      | shipper_DHL | true                         |

  @Id:S31789_TC_Q4_Outgoing
  Scenario: Creating a delivery planning from an order line seeds the planned discharge quantity from the planned load

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtySeed | true  | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtySeed | orderQtySeed           | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQtySeed is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID  | C_OrderLine_ID   |
      | deliveryPlanningQtySeed | orderLineQtySeed |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID  | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningQtySeed | 10         | 10           | Outgoing            | 10                    | 10                       |

  @Id:S31789_TC_Q4_Incoming
  Scenario: Creating an incoming delivery planning from a purchase order line seeds the planned discharge quantity from the planned load

    Given metasfresh contains M_PricingSystems
      | Identifier         | OPT.IsActive |
      | pricingSystemQtyPO | true         |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_QtyPO | pricingSystemQtyPO             | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier             | M_PriceList_ID.Identifier |
      | priceListVersion_QtyPO | priceList_QtyPO           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_QtyPO            | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQtyPO     | Y        | N          | pricingSystemQtyPO             |
      | warehouseBPQtyPO |          |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier           | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQtyPO    | 1234564396491 | vendorQtyPO              | true                | true                |
      | warehouseLocationQtyPO | 1203522892491 | warehouseBPQtyPO         | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQtyPO               | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value             | Name              | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQtyPO             | warehouseValueQtyPO | warehouseNameQtyPO | warehouseBPQtyPO             | warehouseLocationQtyPO                 |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value          | M_Warehouse_ID.Identifier |
      | locatorQtyPO              | locatorValueQtyPO | warehouseQtyPO             |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQtyPO   | false   | vendorQtyPO               | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQtyPO                    | warehouseQtyPO                 | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyPO | orderQtyPO             | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQtyPO is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQtyPO  | orderLineQtyPO |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningQtyPO  | 10         | 10           | Incoming            | 10                    | 10                       |

  @Id:S31789_TC_Q3_Split
  Scenario: Splitting an unallocated delivery planning divides both the loaded and discharge planned quantities

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQty   | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQty | orderQty               | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQty is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQty_1  | orderLineQty   |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningQty_1  | 10         | 10           | Outgoing            | 10                    | 10                       |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningQty_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                      | C_OrderLine_ID |
      | deliveryPlanningQty_1,deliveryPlanningQty_2 | orderLineQty   |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningQty_1  | 10         | 10           | Outgoing            | 5                     | 5                        |
      | deliveryPlanningQty_2  | 10         | 10           | Outgoing            | 5                     | 5                        |

  @Id:S31789_TC_Q3_Remainder
  Scenario: Splitting an unallocated delivery planning with an uneven divisor puts the remainder on the original for both quantities

    Given metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyRem | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyRem | orderQtyRem            | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQtyRem is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID  |
      | deliveryPlanningRem_1  | orderLineQtyRem |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningRem_1  | 10         | 10           | Outgoing            | 10                    | 10                       |

    When generate 2 additional M_Delivery_Planning records for: deliveryPlanningRem_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                            | C_OrderLine_ID  |
      | deliveryPlanningRem_1,deliveryPlanningRem_2,deliveryPlanningRem_3 | orderLineQtyRem |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningRem_1  | 10         | 10           | Outgoing            | 4                     | 4                        |
      | deliveryPlanningRem_2  | 10         | 10           | Outgoing            | 3                     | 3                        |
      | deliveryPlanningRem_3  | 10         | 10           | Outgoing            | 3                     | 3                        |

  @Id:S31789_TC_Q5_Allocated
  Scenario: Splitting a delivery planning allocated to an instruction leaves its planned figures untouched and gives the new planning the remaining uncommitted amount

    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyAlloc | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyAlloc | orderQtyAlloc          | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQtyAlloc is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID    |
      | deliveryPlanningAlloc_1 | orderLineQtyAlloc |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID  | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningAlloc_1 | 10         | 10           | Outgoing            | 10                    | 10                       |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID  | PlannedLoadedQuantity |
      | deliveryPlanningAlloc_1 | 6                     |

    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID  | IsComplete |
      | deliveryInstructionAlloc   | deliveryPlanningAlloc_1 | false      |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningAlloc_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                          | C_OrderLine_ID    |
      | deliveryPlanningAlloc_1,deliveryPlanningAlloc_2 | orderLineQtyAlloc |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID  | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningAlloc_1 | 10         | 10           | Outgoing            | 6                     | 10                       |
      | deliveryPlanningAlloc_2 | 10         | 10           | Outgoing            | 4                     | 0                        |

  @Id:S31789_TC_Q5_AllocatedRemainder
  Scenario: Splitting an allocated delivery planning with an uneven divisor puts the remainder on the last new planning

    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyAllocRem | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyAllocRem | orderQtyAllocRem       | product                 | 13         | shipper_DHL                 |

    When the order identified by orderQtyAllocRem is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID    | C_OrderLine_ID       |
      | deliveryPlanningAllocRem_1 | orderLineQtyAllocRem |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID     | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningAllocRem_1 | 13         | 13           | Outgoing            | 13                    | 13                       |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID     | PlannedLoadedQuantity |
      | deliveryPlanningAllocRem_1 | 3                     |

    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID  | M_Delivery_Planning_ID     | IsComplete |
      | deliveryInstructionAllocRem | deliveryPlanningAllocRem_1 | false      |

    When generate 3 additional M_Delivery_Planning records for: deliveryPlanningAllocRem_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                                                    | C_OrderLine_ID       |
      | deliveryPlanningAllocRem_1,deliveryPlanningAllocRem_2,deliveryPlanningAllocRem_3,deliveryPlanningAllocRem_4 | orderLineQtyAllocRem |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID     | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningAllocRem_1 | 13         | 13           | Outgoing            | 3                     | 13                       |
      | deliveryPlanningAllocRem_2 | 13         | 13           | Outgoing            | 3                     | 0                        |
      | deliveryPlanningAllocRem_3 | 13         | 13           | Outgoing            | 3                     | 0                        |
      | deliveryPlanningAllocRem_4 | 13         | 13           | Outgoing            | 4                     | 0                        |

  @Id:S31789_TC_Q5_FullyAllocated
  Scenario: Splitting a fully allocated delivery planning still creates the new planning, carrying 0

    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyFull | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyFull | orderQtyFull           | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQtyFull is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID   |
      | deliveryPlanningFull_1 | orderLineQtyFull |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningFull_1 | 10         | 10           | Outgoing            | 10                    | 10                       |

    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID | IsComplete |
      | deliveryInstructionFull    | deliveryPlanningFull_1 | false      |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningFull_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                        | C_OrderLine_ID   |
      | deliveryPlanningFull_1,deliveryPlanningFull_2 | orderLineQtyFull |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningFull_1 | 10         | 10           | Outgoing            | 10                    | 10                       |
      | deliveryPlanningFull_2 | 10         | 10           | Outgoing            | 0                     | 0                        |

  @Id:S31789_TC12
  Scenario: TC12 - Splitting a planning with both an allocation and a partial receipt leaves the received figure, the allocated portion and the original's planned figure unchanged

    Given metasfresh contains M_PricingSystems
      | Identifier           | OPT.IsActive |
      | pricingSystemQtyTC12 | true         |
    And metasfresh contains M_PriceLists
      | Identifier        | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_QtyTC12 | pricingSystemQtyTC12          | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier               | M_PriceList_ID.Identifier |
      | priceListVersion_QtyTC12 | priceList_QtyTC12         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_QtyTC12          | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQtyTC12      | Y        | N          | pricingSystemQtyTC12          |
      | warehouseBPQtyTC12 |          |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQtyTC12    | 1234564396492 | vendorQtyTC12             | true                | true                |
      | warehouseLocationQtyTC12 | 1203522892492 | warehouseBPQtyTC12        | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQtyTC12            | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                 | Name                 | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQtyTC12          | warehouseValueQtyTC12 | warehouseNameQtyTC12 | warehouseBPQtyTC12           | warehouseLocationQtyTC12               |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locatorQtyTC12          | locatorValueQtyTC12 | warehouseQtyTC12          |
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQtyTC12 | false   | vendorQtyTC12             | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQtyTC12                 | warehouseQtyTC12               | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyTC12 | orderQtyTC12           | product                 | 50         | shipper_DHL                 |

    When the order identified by orderQtyTC12 is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID   |
      | deliveryPlanningTC12_1 | orderLineQtyTC12 |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningTC12_1 | 50         | 50           | Incoming            | 50                    | 50                       |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID | ActualDischargeQuantity |
      | deliveryPlanningTC12_1 | 40                      |

    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID | IsComplete |
      | deliveryInstructionTC12    | deliveryPlanningTC12_1 | false      |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningTC12_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                         | C_OrderLine_ID   |
      | deliveryPlanningTC12_1,deliveryPlanningTC12_2 | orderLineQtyTC12 |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningTC12_1 | 50         | 50           | Incoming            | 50                    | 50                       |
      | deliveryPlanningTC12_2 | 50         | 50           | Incoming            | 0                     | 0                        |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ActualDischargeQuantity |
      | deliveryPlanningTC12_1 | 50         | 50           | Incoming            | 40                      |
