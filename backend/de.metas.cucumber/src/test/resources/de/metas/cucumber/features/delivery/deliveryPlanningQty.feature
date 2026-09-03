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

  @Id:S31789_TC_Q7c_SeedOnCreate
  Scenario: Creating an incoming delivery planning seeds ActualLoadQty from the planned load, because nothing ever reports the vendor's load

    Given metasfresh contains M_PricingSystems
      | Identifier             | OPT.IsActive |
      | pricingSystemQtySeedIn | true         |
    And metasfresh contains M_PriceLists
      | Identifier          | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_QtySeedIn | pricingSystemQtySeedIn        | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier                 | M_PriceList_ID.Identifier |
      | priceListVersion_QtySeedIn | priceList_QtySeedIn       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_QtySeedIn        | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier          | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQtySeedIn     | Y        | N          | pricingSystemQtySeedIn        |
      | warehouseBPQtySeedIn |         |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQtySeedIn    | 1234564396493 | vendorQtySeedIn          | true                | true                |
      | warehouseLocationQtySeedIn | 1203522892493 | warehouseBPQtySeedIn     | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQtySeedIn          | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier   | Value                   | Name                   | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQtySeedIn          | warehouseValueQtySeedIn | warehouseNameQtySeedIn | warehouseBPQtySeedIn         | warehouseLocationQtySeedIn             |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                 | M_Warehouse_ID.Identifier |
      | locatorQtySeedIn        | locatorValueQtySeedIn | warehouseQtySeedIn        |
    And metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQtySeedIn   | false   | vendorQtySeedIn           | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQtySeedIn                | warehouseQtySeedIn             | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtySeedIn | orderQtySeedIn         | product                 | 7          | shipper_DHL                 |

    When the order identified by orderQtySeedIn is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID     |
      | deliveryPlanningSeedIn | orderLineQtySeedIn |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningSeedIn | 7          | 7            | Incoming            | 7                     | 7             | 0                       |

  @Id:S31789_TC_Q7c_FollowsPlanEdit
  Scenario: Editing the planned load of an incoming delivery planning moves its ActualLoadQty along, but never touches the discharge actual a receipt owns

    Given metasfresh contains M_PricingSystems
      | Identifier              | OPT.IsActive |
      | pricingSystemQtyFollow | true         |
    And metasfresh contains M_PriceLists
      | Identifier           | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_QtyFollow  | pricingSystemQtyFollow        | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier                  | M_PriceList_ID.Identifier |
      | priceListVersion_QtyFollow  | priceList_QtyFollow       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_QtyFollow        | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier           | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQtyFollow      | Y        | N          | pricingSystemQtyFollow        |
      | warehouseBPQtyFollow |          |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQtyFollow    | 1234564396494 | vendorQtyFollow          | true                | true                |
      | warehouseLocationQtyFollow | 1203522892494 | warehouseBPQtyFollow     | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQtyFollow          | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier  | Value                    | Name                    | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQtyFollow         | warehouseValueQtyFollow  | warehouseNameQtyFollow  | warehouseBPQtyFollow         | warehouseLocationQtyFollow             |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                  | M_Warehouse_ID.Identifier |
      | locatorQtyFollow        | locatorValueQtyFollow  | warehouseQtyFollow        |
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQtyFollow    | false   | vendorQtyFollow           | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQtyFollow                | warehouseQtyFollow             | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyFollow  | orderQtyFollow         | product                 | 9          | shipper_DHL                 |

    When the order identified by orderQtyFollow is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID      |
      | deliveryPlanningFollow | orderLineQtyFollow  |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningFollow | 9          | 9            | Incoming            | 9                     | 9             | 0                       |

    # a receipt already wrote the discharge actual - the interceptor must leave this figure alone
    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID | ActualDischargeQuantity |
      | deliveryPlanningFollow | 4                       |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID | PlannedLoadedQuantity |
      | deliveryPlanningFollow | 3                     |

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningFollow | 9          | 9            | Incoming            | 3                     | 3             | 4                       |

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
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningQty_1  | 10         | 10           | Outgoing            | 5                     | 5                        | 0             | 0                       |
      | deliveryPlanningQty_2  | 10         | 10           | Outgoing            | 5                     | 5                        | 0             | 0                       |

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
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningRem_1  | 10         | 10           | Outgoing            | 4                     | 4                        | 0             | 0                       |
      | deliveryPlanningRem_2  | 10         | 10           | Outgoing            | 3                     | 3                        | 0             | 0                       |
      | deliveryPlanningRem_3  | 10         | 10           | Outgoing            | 3                     | 3                        | 0             | 0                       |

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
      | M_Delivery_Planning_ID  | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningAlloc_1 | 10         | 10           | Outgoing            | 6                     | 10                       | 0             | 0                       |
      | deliveryPlanningAlloc_2 | 10         | 10           | Outgoing            | 4                     | 0                        | 0             | 0                       |

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
      | M_Delivery_Planning_ID     | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningAllocRem_1 | 13         | 13           | Outgoing            | 3                     | 13                       | 0             | 0                       |
      | deliveryPlanningAllocRem_2 | 13         | 13           | Outgoing            | 3                     | 0                        | 0             | 0                       |
      | deliveryPlanningAllocRem_3 | 13         | 13           | Outgoing            | 3                     | 0                        | 0             | 0                       |
      | deliveryPlanningAllocRem_4 | 13         | 13           | Outgoing            | 4                     | 0                        | 0             | 0                       |

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
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningFull_1 | 10         | 10           | Outgoing            | 10                    | 10                       | 0             | 0                       |
      | deliveryPlanningFull_2 | 10         | 10           | Outgoing            | 0                     | 0                        | 0             | 0                       |

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
    # Task Q8: the discharge pool now nets coalesce(nullif(actual, 0), planned) instead of copying the
    # target's own committed 50 as zero. The target is allocated, so its OWN claim counts too: the pool is
    # QtyOrdered(50) - its actual(40, nonzero so it wins over its planned 50) = 10, all of which the single
    # new planning receives (additionalLines=1) - not the 0 a planned-only/pre-Q8 reading gave.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningTC12_1 | 50         | 50           | Incoming            | 50                    | 50                       | 50            | 40                      |
      | deliveryPlanningTC12_2 | 50         | 50           | Incoming            | 0                     | 10                       | 0             | 0                       |

  @Id:S31789_TC_Q8_NullifFallback
  Scenario: Splitting again treats a sibling's zero actual as "nothing recorded yet", not a real zero - the pool falls back to its planned figure

    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyNullif  | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyNullif | orderQtyNullif         | product                 | 20         | shipper_DHL                 |

    When the order identified by orderQtyNullif is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID   | C_OrderLine_ID     |
      | deliveryPlanningNullif_1 | orderLineQtyNullif |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID   | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningNullif_1 | 20         | 20           | Outgoing            | 20                    | 20                       |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningNullif_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                            | C_OrderLine_ID     |
      | deliveryPlanningNullif_1,deliveryPlanningNullif_2 | orderLineQtyNullif |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID   | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty |
      | deliveryPlanningNullif_1 | 20         | 20           | Outgoing            | 10                    | 10                       | 0             |
      | deliveryPlanningNullif_2 | 20         | 20           | Outgoing            | 10                    | 10                       | 0             |

    # deliveryPlanningNullif_2's ActualLoadQty is still 0 - nothing has been delivered against it. Splitting
    # deliveryPlanningNullif_1 again must still claim deliveryPlanningNullif_2's full PLANNED share (10) for
    # the pool, not read the zero actual as "nothing left to claim" - a bug reading raw actual (no nullif)
    # would answer a pool of 20 here instead of 10, giving 10/10 instead of the correct 5/5 below.
    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningNullif_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                     | C_OrderLine_ID     |
      | deliveryPlanningNullif_1,deliveryPlanningNullif_2,deliveryPlanningNullif_3 | orderLineQtyNullif |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID   | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty |
      | deliveryPlanningNullif_1 | 20         | 20           | Outgoing            | 5                     | 5                        | 0             |
      | deliveryPlanningNullif_2 | 20         | 20           | Outgoing            | 10                    | 10                       | 0             |
      | deliveryPlanningNullif_3 | 20         | 20           | Outgoing            | 5                     | 5                        | 0             |

  @Id:S31789_TC_Q8_PartialDeliveryPool
  Scenario: Splitting again after a partial delivery on one of two plannings frees up exactly what was not delivered, not the sibling's whole plan

    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyPartial | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyPartial | orderQtyPartial        | product                 | 20         | shipper_DHL                 |

    When the order identified by orderQtyPartial is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID    | C_OrderLine_ID      |
      | deliveryPlanningPartial_1 | orderLineQtyPartial |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningPartial_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                              | C_OrderLine_ID      |
      | deliveryPlanningPartial_1,deliveryPlanningPartial_2 | orderLineQtyPartial |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty |
      | deliveryPlanningPartial_1 | 20         | 20           | Outgoing            | 10                    | 0             |
      | deliveryPlanningPartial_2 | 20         | 20           | Outgoing            | 10                    | 0             |

    # A partial delivery (6 of the 10 planned) on deliveryPlanningPartial_2 - a split must never write an
    # actual, so this is done directly, standing in for whatever future task books the real shipment.
    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID    | ActualLoadQty |
      | deliveryPlanningPartial_2 | 6             |

    # The pool for deliveryPlanningPartial_1's split nets deliveryPlanningPartial_2's ACTUAL (6, nonzero) -
    # QtyOrdered(20) - 6 = 14 - not its still-fully-claimed PLANNED figure (10), which would answer a pool of
    # 10 and give 5/5 instead of the correct 7/7: under-delivering relative to plan frees up the difference.
    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningPartial_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                        | C_OrderLine_ID      |
      | deliveryPlanningPartial_1,deliveryPlanningPartial_2,deliveryPlanningPartial_3 | orderLineQtyPartial |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty |
      | deliveryPlanningPartial_1 | 20         | 20           | Outgoing            | 7                     | 0             |
      | deliveryPlanningPartial_2 | 20         | 20           | Outgoing            | 10                    | 6             |
      | deliveryPlanningPartial_3 | 20         | 20           | Outgoing            | 7                     | 0             |

  @Id:S31789_TC_Q8_AllocatedDischargeRemainder
  Scenario: Splitting an allocated planning with an uneven discharge pool and more than one additional line puts the remainder on the LAST new planning, not the target

    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyDischRem | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyDischRem | orderQtyDischRem       | product                 | 13         | shipper_DHL                 |

    When the order identified by orderQtyDischRem is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID     | C_OrderLine_ID       |
      | deliveryPlanningDischRem_1 | orderLineQtyDischRem |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID     | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedDischargeQuantity |
      | deliveryPlanningDischRem_1 | 13         | 13           | Outgoing            | 13                       |

    # A partial consumption (2 of the 13 planned) recorded directly on the target's own discharge, before it
    # is allocated - standing in for whatever future task books the real receipt against this figure.
    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID     | ActualDischargeQuantity |
      | deliveryPlanningDischRem_1 | 2                        |

    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID  | M_Delivery_Planning_ID     | IsComplete |
      | deliveryInstructionDischRem | deliveryPlanningDischRem_1 | false      |

    # Allocated: the target's own discharge (13) is committed cargo and stays untouched (D8). The pool is
    # QtyOrdered(13) - its own actual(2, nonzero so it wins over its planned 13) = 11, DOWN-divided over 2
    # additional lines (additionalLines=2, a non-dividing quantity): 11/2 = 5 remainder 1 - the remainder
    # goes to the LAST new planning, same rule the load figure already followed (fix round 1, Task Q5),
    # applied here to discharge for the first time (Task Q8).
    When generate 2 additional M_Delivery_Planning records for: deliveryPlanningDischRem_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                           | C_OrderLine_ID       |
      | deliveryPlanningDischRem_1,deliveryPlanningDischRem_2,deliveryPlanningDischRem_3 | orderLineQtyDischRem |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID     | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedDischargeQuantity | ActualDischargeQuantity |
      | deliveryPlanningDischRem_1 | 13         | 13           | Outgoing            | 13                       | 2                       |
      | deliveryPlanningDischRem_2 | 13         | 13           | Outgoing            | 5                        | 0                       |
      | deliveryPlanningDischRem_3 | 13         | 13           | Outgoing            | 6                        | 0                       |

  @Id:S31789_TC_Q7c_SplitSeedsOwnPlannedLoad
  Scenario: Splitting an incoming delivery planning seeds each new planning's ActualLoadQty from its OWN planned load, never copied from the target

    Given metasfresh contains M_PricingSystems
      | Identifier               | OPT.IsActive |
      | pricingSystemQtySplitIn  | true         |
    And metasfresh contains M_PriceLists
      | Identifier            | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_QtySplitIn  | pricingSystemQtySplitIn       | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier                  | M_PriceList_ID.Identifier |
      | priceListVersion_QtySplitIn | priceList_QtySplitIn      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_QtySplitIn       | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier            | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQtySplitIn      | Y        | N          | pricingSystemQtySplitIn       |
      | warehouseBPQtySplitIn |          |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                  | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQtySplitIn    | 1234564396496 | vendorQtySplitIn         | true                | true                |
      | warehouseLocationQtySplitIn | 1203522892496 | warehouseBPQtySplitIn    | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQtySplitIn         | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                    | Name                    | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQtySplitIn       | warehouseValueQtySplitIn | warehouseNameQtySplitIn | warehouseBPQtySplitIn        | warehouseLocationQtySplitIn            |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value                  | M_Warehouse_ID.Identifier |
      | locatorQtySplitIn       | locatorValueQtySplitIn | warehouseQtySplitIn       |
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQtySplitIn   | false   | vendorQtySplitIn          | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQtySplitIn               | warehouseQtySplitIn            | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtySplitIn | orderQtySplitIn        | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQtySplitIn is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID    | C_OrderLine_ID      |
      | deliveryPlanningSplitIn_1 | orderLineQtySplitIn |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty |
      | deliveryPlanningSplitIn_1 | 10         | 10           | Incoming            | 10                    | 10            |

    # unallocated split with an uneven divisor (10 / 3 additionalLines+1): the target absorbs the DOWN-rounding
    # remainder via setPlannedLoadedQuantity, which - through the Task Q7c interceptor - also moves the
    # target's OWN ActualLoadQty to 4. That makes 4 a different, nonzero number from the two new plannings'
    # own planned load of 3: if createRequest ever again copied the TARGET's actual instead of seeding from
    # each new planning's OWN planned load, deliveryPlanningSplitIn_2/_3 would come back 4, not 3.
    When generate 2 additional M_Delivery_Planning records for: deliveryPlanningSplitIn_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                        | C_OrderLine_ID      |
      | deliveryPlanningSplitIn_1,deliveryPlanningSplitIn_2,deliveryPlanningSplitIn_3 | orderLineQtySplitIn |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningSplitIn_1 | 10         | 10           | Incoming            | 4                     | 4             | 0                       |
      | deliveryPlanningSplitIn_2 | 10         | 10           | Incoming            | 3                     | 3             | 0                       |
      | deliveryPlanningSplitIn_3 | 10         | 10           | Incoming            | 3                     | 3             | 0                       |

  @Id:S31789_TC_Q6_CancelAllocated
  Scenario: Cancelling a delivery planning still allocated to an instruction leaves its planned figures untouched and names it in the result

    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyCancel    | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier         | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyCancel | orderQtyCancel         | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQtyCancel is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID     |
      | deliveryPlanningCancel | orderLineQtyCancel |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningCancel | 10         | 10           | Outgoing            | 10                    | 10                       | 0             | 0                       |

    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID  | M_Delivery_Planning_ID | IsComplete |
      | deliveryInstructionCancelQty | deliveryPlanningCancel | false      |

    # deliveryPlanningCancel is still allocated to deliveryInstructionCancelQty when the cancel runs: its
    # planned figures are committed cargo (D8/D19), so cancel leaves them exactly as they were and names the
    # planning in the result instead of silently zeroing them - it is still voided, closed and cancelled.
    When M_Delivery_Planning identified by deliveryPlanningCancel is canceled, retaining planned figures for: deliveryPlanningCancel

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | Processed | OrderStatus | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningCancel | 10         | 10           | Outgoing            | true     | true      | Canceled    | 10                    | 10                       | 0             | 0                       |
    And validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | deliveryInstructionCancelQty           | shipper_DHL              | customer                        | customerLocation                | VO             |
