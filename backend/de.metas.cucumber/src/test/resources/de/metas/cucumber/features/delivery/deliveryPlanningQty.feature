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

    # Task Q8: QtyTotalOpen is now LIVE - Incoming nets discharge, so the actual write above moves it
    # immediately from 9 (QtyOrdered - 0) to 5 (QtyOrdered - 4), rather than staying frozen at creation.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ActualDischargeQuantity |
      | deliveryPlanningFollow | 9          | 5            | Incoming            | 4                       |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID | PlannedLoadedQuantity |
      | deliveryPlanningFollow | 3                     |

    # PlannedLoadedQuantity does not feed QtyTotalOpen for Incoming (it nets discharge), so it stays at 5 -
    # only the discharge actual moves it, never the load side.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningFollow | 9          | 5            | Incoming            | 3                     | 3             | 4                       |

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

  @Id:S31789_TC_Q5_FullyAllocatedMultiple
  Scenario: Splitting a fully allocated delivery planning into MORE THAN ONE new planning still creates them, all carrying 0
  # The natural counterpart of "Create additional delivery plannings" in deliveryPlanningProcesses.feature,
  # which pre-edits PlannedLoadedQuantity down to 3 before splitting so its 3/1/1 expectations still hold under
  # the committed-cargo rule. Here nothing is pre-edited: the target stays fully allocated (own effective ==
  # QtyOrdered), so the pool is 0 and BOTH new plannings carry 0 - the shape the system produces on its own
  # when more than one additional planning is requested of an allocated target.

    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyFullMult | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyFullMult | orderQtyFullMult       | product                 | 5          | shipper_DHL                 |

    When the order identified by orderQtyFullMult is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID     | C_OrderLine_ID       |
      | deliveryPlanningFullMult_1 | orderLineQtyFullMult |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID     | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | deliveryPlanningFullMult_1 | 5          | 5            | Outgoing            | 5                     | 5                        |

    And generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID     | IsComplete |
      | deliveryInstructionFullMult | deliveryPlanningFullMult_1 | false      |

    When generate 2 additional M_Delivery_Planning records for: deliveryPlanningFullMult_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                              | C_OrderLine_ID       |
      | deliveryPlanningFullMult_1,deliveryPlanningFullMult_2,deliveryPlanningFullMult_3 | orderLineQtyFullMult |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID     | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningFullMult_1 | 5          | 5            | Outgoing            | 5                     | 5                        | 0             | 0                       |
      | deliveryPlanningFullMult_2 | 5          | 5            | Outgoing            | 0                     | 0                        | 0             | 0                       |
      | deliveryPlanningFullMult_3 | 5          | 5            | Outgoing            | 0                     | 0                        | 0             | 0                       |

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

    # Task Q8: the write moves QtyTotalOpen immediately - QtyOrdered(50) - actual(40) = 10 - the moment the
    # receipt is recorded, before any instruction or split exists.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | ActualDischargeQuantity |
      | deliveryPlanningTC12_1 | 50         | 10           | Incoming            | 40                      |

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
    #
    # QtyTotalOpen is also live now (Task Q8) and is an ORDER-LINE total, redundantly shown on both rows:
    # Incoming nets discharge, so it is QtyOrdered(50) - the actual discharge summed across BOTH plannings
    # (40 + 0) = 10, not the frozen 50 either row started with. The split touches no actual, so this figure
    # is unchanged by the split itself - it was already 10 the moment the receipt above was recorded.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningTC12_1 | 50         | 10           | Incoming            | 50                    | 50                       | 50            | 40                      |
      | deliveryPlanningTC12_2 | 50         | 10           | Incoming            | 0                     | 10                       | 0             | 0                       |

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

    # Task Q8: QtyTotalOpen is a live ORDER-LINE total (not a per-row figure) - Outgoing nets load, so the
    # write above moves BOTH rows at once from 20 (QtyOrdered - actual 0) to 14 (QtyOrdered - actual 6),
    # before any second split exists. QtyTotalOpenPlanned is untouched (0): nothing PLANNED changed here.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | ActualLoadQty | QtyTotalOpenPlanned |
      | deliveryPlanningPartial_1 | 20         | 14           | Outgoing            | 0             | 0                    |
      | deliveryPlanningPartial_2 | 20         | 14           | Outgoing            | 6             | 0                    |

    # The pool for deliveryPlanningPartial_1's split nets deliveryPlanningPartial_2's ACTUAL (6, nonzero) -
    # QtyOrdered(20) - 6 = 14 - not its still-fully-claimed PLANNED figure (10), which would answer a pool of
    # 10 and give 5/5 instead of the correct 7/7: under-delivering relative to plan frees up the difference.
    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningPartial_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                        | C_OrderLine_ID      |
      | deliveryPlanningPartial_1,deliveryPlanningPartial_2,deliveryPlanningPartial_3 | orderLineQtyPartial |
    # QtyTotalOpen stays 14 - the split touches no actual. QtyTotalOpenPlanned, however, now goes NEGATIVE
    # (-4): the three planned loads sum to 24 (7+10+7), more than QtyOrdered(20) - the split legitimately grew
    # the order line's total planned figure by distributing against the ACTUAL-aware pool (D16's signal for
    # "over-planned", not an arithmetic error - see the plan's Global Constraints, "Two consequences").
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | QtyTotalOpenPlanned |
      | deliveryPlanningPartial_1 | 20         | 14           | Outgoing            | 7                     | 0             | -4                   |
      | deliveryPlanningPartial_2 | 20         | 14           | Outgoing            | 10                    | 6             | -4                   |
      | deliveryPlanningPartial_3 | 20         | 14           | Outgoing            | 7                     | 0             | -4                   |

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

  @Id:S31789_TC_Q8_DeleteRecomputesSurvivor
  Scenario: Deleting a planning off a line with a survivor recomputes the survivor's live open quantities, not a stale copy

    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyDelete1 | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyDelete1 | orderQtyDelete1        | product                 | 16         | shipper_DHL                 |

    When the order identified by orderQtyDelete1 is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID   | C_OrderLine_ID      |
      | deliveryPlanningDelete1_1 | orderLineQtyDelete1 |

    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningDelete1_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                | C_OrderLine_ID      |
      | deliveryPlanningDelete1_1,deliveryPlanningDelete1_2   | orderLineQtyDelete1 |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | QtyTotalOpenPlanned |
      | deliveryPlanningDelete1_1 | 16         | 16           | Outgoing            | 8                     | 0                    |
      | deliveryPlanningDelete1_2 | 16         | 16           | Outgoing            | 8                     | 0                    |

    # The planning ABOUT TO BE DELETED carries the actual, so its removal must visibly move the SURVIVOR's
    # totals - a stale-copy bug would leave deliveryPlanningDelete1_1 showing 13/0 forever.
    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID    | ActualLoadQty |
      | deliveryPlanningDelete1_2 | 3             |

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | ActualLoadQty | QtyTotalOpenPlanned |
      | deliveryPlanningDelete1_1 | 16         | 13           | Outgoing            | 0             | 0                    |
      | deliveryPlanningDelete1_2 | 16         | 13           | Outgoing            | 3             | 0                    |

    # Manual user-action delete (path 1 of the inventory: the direct/UI delete a planner triggers from the
    # window) - succeeds because a survivor remains (deliveryPlanningDelete1_1).
    When delete M_Delivery_Planning:
      | M_Delivery_Planning_ID    | ErrorCode |
      | deliveryPlanningDelete1_2 |           |

    # Task Q8 fix round: QtyTotalOpen/QtyTotalOpenPlanned on the SURVIVOR now reflect the line with the
    # deleted planning's claim gone - both climb back up (16/8), not left frozen at the pre-delete 13/0.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | QtyTotalOpenPlanned |
      | deliveryPlanningDelete1_1 | 16         | 16           | Outgoing            | 8                     | 0             | 8                    |

  @Id:S31789_TC_Q8_DeleteNonUiRecomputesSurvivors
  Scenario: A non-UI-action delete (the shape a receipt/shipment-schedule cascade takes) recomputes the line's survivors the same way

    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQtyDelete2 | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQtyDelete2 | orderQtyDelete2        | product                 | 30         | shipper_DHL                 |

    When the order identified by orderQtyDelete2 is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID    | C_OrderLine_ID      |
      | deliveryPlanningDelete2_1 | orderLineQtyDelete2 |

    When generate 2 additional M_Delivery_Planning records for: deliveryPlanningDelete2_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                                                              | C_OrderLine_ID      |
      | deliveryPlanningDelete2_1,deliveryPlanningDelete2_2,deliveryPlanningDelete2_3 | orderLineQtyDelete2 |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | QtyTotalOpenPlanned |
      | deliveryPlanningDelete2_1 | 30         | 30           | Outgoing            | 10                    | 0                    |
      | deliveryPlanningDelete2_2 | 30         | 30           | Outgoing            | 10                    | 0                    |
      | deliveryPlanningDelete2_3 | 30         | 30           | Outgoing            | 10                    | 0                    |

    And update M_Delivery_Planning:
      | M_Delivery_Planning_ID    | ActualLoadQty |
      | deliveryPlanningDelete2_3 | 5             |

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | ActualLoadQty | QtyTotalOpenPlanned |
      | deliveryPlanningDelete2_1 | 30         | 25           | Outgoing            | 0             | 0                    |
      | deliveryPlanningDelete2_2 | 30         | 25           | Outgoing            | 0             | 0                    |
      | deliveryPlanningDelete2_3 | 30         | 25           | Outgoing            | 5             | 0                    |

    # Non-UI-action delete (OPT.IsUIAction=false) - the programmatic shape M_ReceiptSchedule/M_ShipmentSchedule
    # deletion takes (DeliveryPlanningRepository#deleteForReceiptSchedule / #deleteForShipmentSchedule), which
    # is NOT routed through the "at least one planning per order line" guard. Deletes the planning carrying
    # the actual, leaving two survivors that never had one.
    When delete M_Delivery_Planning:
      | M_Delivery_Planning_ID    | OPT.IsUIAction | ErrorCode |
      | deliveryPlanningDelete2_3 | false          |           |

    # Both survivors climb from 25 to 30 (their actual sum drops from 5 to 0) and from 0 to 10 planned-open
    # (the deleted planning's 10 planned no longer counts) - the SAME recompute the UI-delete scenario above
    # exercises, since AFTER_DELETE fires identically regardless of which of the three inventoried paths
    # triggered it.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | QtyTotalOpenPlanned |
      | deliveryPlanningDelete2_1 | 30         | 30           | Outgoing            | 10                    | 0             | 10                   |
      | deliveryPlanningDelete2_2 | 30         | 30           | Outgoing            | 10                    | 0             | 10                   |

  @Id:S31789_TC_Q10_DeliveredKeepsProcessed
  Scenario: Reopening a closed AND delivered planning keeps Processed set - the invariant Processed == (IsClosed or IsDelivered) survives the reopen

    Given metasfresh contains M_PricingSystems
      | Identifier       | OPT.IsActive |
      | pricingSystemQ10 | true         |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_Q10 | pricingSystemQ10               | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier           | M_PriceList_ID.Identifier |
      | priceListVersion_Q10 | priceList_Q10              |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_Q10              | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier        | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQ10         | Y        | N          | pricingSystemQ10               |
      | warehouseBPQ10    |          |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier           | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQ10    | 1234564396499 | vendorQ10                | true                | true                |
      | warehouseLocationQ10 | 1203522892499 | warehouseBPQ10           | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQ10                | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value             | Name              | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQ10               | warehouseValueQ10 | warehouseNameQ10  | warehouseBPQ10                | warehouseLocationQ10                   |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value            | M_Warehouse_ID.Identifier |
      | locatorQ10               | locatorValueQ10  | warehouseQ10               |
    And metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQ10Delivered  | false   | vendorQ10                 | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQ10                      | warehouseQ10                    | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ10Delivered | orderQ10Delivered      | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQ10Delivered is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID  | C_OrderLine_ID        |
      | deliveryPlanningQ10Deld | orderLineQ10Delivered |

    # deliver it FIRST, through the PRODUCTION generate-receipt process: it generates the receipt and
    # completes it in the same call, so the planning link must already be on the draft for
    # interceptor/M_InOut#afterComplete to set M_InOut_ID - which is exactly what E3's IsDelivered virtual
    # column reads.
    When the delivery planning identified by deliveryPlanningQ10Deld generates a receipt:
      | ReceiptDate | Qty | OPT.M_InOut_ID  |
      | 2023-02-05  | 10  | receiptQ10Deld  |

    # now delivered - close it, which always sets Processed regardless of delivered state
    And M_Delivery_Planning identified by deliveryPlanningQ10Deld is closed

    # QtyTotalOpen is 0, not the pre-Task-Q11 10: completion now writes ActualDischargeQuantity from the
    # booked quantity (Task Q11), and Task Q8 already nets QtyTotalOpen live against it - fully received,
    # so nothing is left open.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID  | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | Processed |
      | deliveryPlanningQ10Deld | 10         | 0            | Incoming            | true     | true      |

    When M_Delivery_Planning identified by deliveryPlanningQ10Deld is opened

    # THE INVARIANT: Processed == (IsClosed || IsDelivered). IsClosed just went back to false, but the
    # planning is delivered, so Processed MUST stay true - the defect Task Q10 fixes is reopen clearing this
    # unconditionally, which would wrongly unlock a delivered planning.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID  | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | Processed |
      | deliveryPlanningQ10Deld | 10         | 0            | Incoming            | false    | true      |

  @Id:S31789_TC_Q10_UndeliveredClearsProcessed
  Scenario: Reopening a closed, UNDELIVERED planning clears Processed

    Given metasfresh contains C_Orders:
      | Identifier          | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderQ10Undelivered | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier              | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ10Undelivered | orderQ10Undelivered    | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQ10Undelivered is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID    | C_OrderLine_ID          |
      | deliveryPlanningQ10Undeld | orderLineQ10Undelivered |

    And M_Delivery_Planning identified by deliveryPlanningQ10Undeld is closed

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | Processed |
      | deliveryPlanningQ10Undeld | 10         | 10           | Outgoing            | true     | true      |

    When M_Delivery_Planning identified by deliveryPlanningQ10Undeld is opened

    # THE INVARIANT: never delivered, so lifting IsClosed must clear Processed too - the mirror of the
    # scenario above.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID    | QtyOrdered | QtyTotalOpen | TransportDirection | IsClosed | Processed |
      | deliveryPlanningQ10Undeld | 10         | 10           | Outgoing            | false    | false     |

  @Id:S31789_TC_Q11_ReversalUnlocksAndRebooks
  Scenario: Reversing a receipt clears the discharge actual and unlocks the planning - which can then book again

    Given metasfresh contains M_PricingSystems
      | Identifier        | OPT.IsActive |
      | pricingSystemQ11R | true         |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_Q11R | pricingSystemQ11R              | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier |
      | priceListVersion_Q11R | priceList_Q11R             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_Q11R             | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQ11R          | Y        | N          | pricingSystemQ11R              |
      | warehouseBPQ11R     |          |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQ11R    | 1234564396495 | vendorQ11R                | true                | true                |
      | warehouseLocationQ11R | 1203522892495 | warehouseBPQ11R           | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQ11R                | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value               | Name                | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQ11R              | warehouseValueQ11R  | warehouseNameQ11R   | warehouseBPQ11R               | warehouseLocationQ11R                  |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value             | M_Warehouse_ID.Identifier |
      | locatorQ11R              | locatorValueQ11R  | warehouseQ11R              |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQ11R      | false   | vendorQ11R                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQ11R                     | warehouseQ11R                  | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ11R | orderQ11R              | product                 | 10         | shipper_DHL                 |

    When the order identified by orderQ11R is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQ11R   | orderLineQ11R  |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed |
      | deliveryPlanningQ11R   | 10         | 10           | Incoming            | 10                    | 10            | 0                       | false    | false     |

    # First booking - through the production generate-receipt process (same as Task Q10's pattern).
    When the delivery planning identified by deliveryPlanningQ11R generates a receipt:
      | ReceiptDate | Qty | OPT.M_InOut_ID |
      | 2023-02-05  | 10  | receiptQ11R_1  |

    # Completion: the discharge end is written from the booked quantity, and the planning is now Processed -
    # the invariant Processed == (IsClosed || IsDelivered) holds via IsDelivered (M_InOut_ID is set).
    # ActualLoadQty is untouched (still 10, Task Q7c's mirror of the plan) - that end is never ours to write.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed |
      | deliveryPlanningQ11R   | 10         | 0            | Incoming            | 10                    | 10            | 10                      | false    | true      |

    When the material receipt identified by receiptQ11R_1 is reversed

    # Reversal: the discharge actual clears back to empty and Processed clears too - the planning is
    # NOT closed, so the mirror of ReOpen's rule unlocks it (Task Q10's invariant, asserted at this site).
    # Without this, the planning would be permanently stuck Processed with no route back except
    # Close-then-ReOpen (the defect this task exists to prevent).
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed |
      | deliveryPlanningQ11R   | 10         | 10           | Incoming            | 10                    | 10            | 0                       | false    | false     |

    # Rebooking: the planning was unlocked by the reversal above, so a second run of the production
    # generate-receipt process against the SAME planning books again - proving the reversal did not leave it
    # permanently stuck.
    When the delivery planning identified by deliveryPlanningQ11R generates a receipt:
      | ReceiptDate | Qty | OPT.M_InOut_ID |
      | 2023-02-06  | 6   | receiptQ11R_2  |

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed |
      | deliveryPlanningQ11R   | 10         | 4            | Incoming            | 10                    | 10            | 6                       | false    | true      |

  @Id:S31789_TC_Q11_OutgoingCompletionWritesBothEnds
  Scenario: Completing a shipment writes the booked quantity onto BOTH ends, and reversing it clears both

    # Driven through the PRODUCTION generate-shipment process, which needs real stock: it refuses to ship
    # more than the shipment schedule's qty-on-hand, and the shared shipment chain picks actual HUs. So the
    # warehouse is stocked by an inventory document first, and the sales order is pinned to that warehouse.
    Given metasfresh contains M_Products:
      | Identifier    | Name          | IsStocked |
      | productQ11Out | ProductQ11Out | true      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_SO               | productQ11Out           | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                | Name                |
      | warehouseQ11Out           | warehouseValueQ11Out | warehouseNameQ11Out |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value              | M_Warehouse_ID.Identifier |
      | locatorQ11Out           | locatorValueQ11Out | warehouseQ11Out           |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID  | MovementDate | OPT.DocumentNo |
      | inventoryQ11Out           | warehouseQ11Out | 2023-02-02   | Q11Out_stock   |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | inventoryLineQ11Out           | inventoryQ11Out           | productQ11Out           | PCE          | 10       | 0       |
    And the inventory identified by inventoryQ11Out is completed
    And after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand |
      | productQ11Out           | 10        |

    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | orderQ11Out  | true    | customer                 | 2023-02-03  | 2023-02-25T00:00:00Z | customerLocation                      | warehouseQ11Out               |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ11Out | orderQ11Out            | productQ11Out           | 10         | shipper_DHL                 |

    When the order identified by orderQ11Out is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID  |
      | deliveryPlanningQ11Out | orderLineQ11Out |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed |
      | deliveryPlanningQ11Out | 10         | 10           | Outgoing            | 10                    | 0             | 0                       | false    | false     |

    # A shipment is the only document that ever exists for an Outgoing planning, so - unlike a receipt,
    # which writes only its own end - completion books the SAME quantity onto both ends: nobody but us
    # ever reports the customer's unload, so the discharge is written as "arrives as shipped unless told
    # otherwise". A partial booking (7 of the 10 planned) makes it visible that the ACTUAL, not the
    # planned figure, is what gets written.
    When the delivery planning identified by deliveryPlanningQ11Out generates a shipment:
      | DeliveryDate | Qty | OPT.M_InOut_ID |
      | 2023-02-05   | 7   | shipmentQ11Out |

    # PlannedLoadedQuantity is now 7, not the ordered 10: a shipment occupies the load end, so the process'
    # own Qty write-back overwrites the planned load with the shipped 7 (Task Q12). That write-back happens
    # under BOTH orderings, so it is the control here - ActualLoadQty / ActualDischargeQuantity / Processed /
    # M_InOut_ID are the ones only completion's interceptor writes.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed | M_InOut_ID     |
      | deliveryPlanningQ11Out | 10         | 3            | Outgoing            | 7                     | 7             | 7                       | false    | true      | shipmentQ11Out |

    When the shipment identified by shipmentQ11Out is reversed

    # Reversal is symmetric: both ends the shipment wrote clear back to empty, and Processed clears -
    # the planning is not closed, so it is unlocked again. The planned load stays at the 7 the process
    # wrote; only the actuals are the reversal's to undo.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed | M_InOut_ID |
      | deliveryPlanningQ11Out | 10         | 10           | Outgoing            | 7                     | 0             | 0                       | false    | false     | null       |

  @Id:S31789_TC_Q11_ConsolidatedShipmentBooksOnlyItsOwnLine
  Scenario: A consolidated shipment books only THIS planning's own line onto it, never the whole document

    # A shipment schedule whose partner allows consolidation (C_BPartner.AllowConsolidateInOut - 'Y' by
    # default) is put onto an ALREADY-DRAFTED shipment of the same org / partner / partner-location /
    # warehouse / consolidation period instead of onto a fresh one, and the delivery-planning back-link is
    # stamped on THAT header (InOutProducerFromShipmentScheduleWithHU#getCreateShipmentHeader). So the
    # document the generate-shipment process completes routinely carries OTHER schedules' lines, and
    # completion must book only the line that belongs to THIS planning.
    #
    # Here: order Q11C_Other's 12 PCE are drafted first (IsCompleteShipments=false leaves the shipment open),
    # then delivery planning Q11C ships 7 PCE of the SAME product - the same product is what makes the
    # difference visible, since resolveBookedQty filters by product. Both land on one document of 19 PCE.
    # Q11C's actual is 7, not 19, and its QtyTotalOpen is 3, not -9.

    # Both orders carry DeliveryRule=Force, so what gets shipped is decided by the order/process quantity
    # rather than by warehouse availability - the subject here is which LINES a completion books, not how
    # much of them is available. Their own warehouse keeps their shipments out of any other scenario's
    # consolidation window.
    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | productQ11C | ProductQ11C |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_SO               | productQ11C             | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value              | Name              |
      | warehouseQ11C             | warehouseValueQ11C | warehouseNameQ11C |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value            | M_Warehouse_ID.Identifier |
      | locatorQ11C             | locatorValueQ11C | warehouseQ11C             |

    # The OTHER order, on the same customer / ship-to location / warehouse. Both orders promise 2023-02-05,
    # which is the date both shipments end up carrying (a shipment generated with IsShipToday=false is dated
    # to its schedule's delivery date) - the day-granular consolidation period matches on exactly that. Its
    # shipment is left DRAFTED (IsCompleteShipments=false), so it is still open for the planning's line to be
    # consolidated onto.
    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DeliveryRule |
      | orderQ11C_Other | true    | customer                 | 2023-02-03  | 2023-02-05T00:00:00Z | customerLocation                      | warehouseQ11C                 | F                |
    And metasfresh contains C_OrderLines:
      | Identifier          | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ11C_Other | orderQ11C_Other       | productQ11C             | 12         | shipper_DHL                 |

    When the order identified by orderQ11C_Other is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID     | C_OrderLine_ID      |
      | deliveryPlanningQ11C_Other | orderLineQ11C_Other |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier      | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedQ11C_Other | orderLineQ11C_Other       | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedQ11C_Other                  | D            | false               | false       |
    # One alias for one document on purpose: this drafted shipment is the very one the planning's line is
    # consolidated onto below, and every later assertion is made against THIS record.
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | DocStatus |
      | schedQ11C_Other                  | shipmentQ11C          | DR        |

    # The planning's own order, same customer / location / warehouse.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DeliveryRule |
      | orderQ11C  | true    | customer                 | 2023-02-03  | 2023-02-05T00:00:00Z | customerLocation                      | warehouseQ11C                 | F                |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ11C | orderQ11C             | productQ11C             | 10         | shipper_DHL                 |

    When the order identified by orderQ11C is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQ11C   | orderLineQ11C  |

    When the delivery planning identified by deliveryPlanningQ11C generates a shipment:
      | DeliveryDate | Qty |
      | 2023-02-05   | 7   |

    # The document really is consolidated: the pre-existing draft now carries BOTH lines, 19 PCE of the same
    # product, and both are completed. Had the producer created a fresh header instead, the first row here
    # would find no line on it and this scenario would be asserting nothing.
    Then validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | C_OrderLine_ID.Identifier | movementqty | processed |
      | shipmentLineQ11C_Own      | shipmentQ11C          | orderLineQ11C             | 7           | true      |
      | shipmentLineQ11C_Other    | shipmentQ11C          | orderLineQ11C_Other       | 12          | true      |

    # THE assertion: 7 (this planning's own line), not 19 (the document). QtyTotalOpen follows it: 10 - 7 = 3,
    # not 10 - 19 = -9.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed | M_InOut_ID   |
      | deliveryPlanningQ11C   | 10         | 3            | Outgoing            | 7                     | 7             | 7                       | false    | true      | shipmentQ11C |

    # The other planning is untouched: the completed document carries THIS planning's back-link, so nothing
    # was booked onto it - it must certainly not have picked up the document's quantity either.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID     | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed | M_InOut_ID |
      | deliveryPlanningQ11C_Other | 12         | 12           | Outgoing            | 12                    | 0             | 0                       | false    | false     | null       |

  @Id:S31789_TC_Q11_SplitSiblingsBookOnlyTheirOwnShare
  Scenario: Two plannings SPLIT from one order line each book only their own share, never the line's whole quantity

    # resolveBookedQty scopes a completing document's lines by C_OrderLine_ID, and a shipment schedule is 1:1
    # with its ORDER LINE - not with the planning. Two plannings split from one order line therefore share
    # both the schedule and the order line, and nothing on a shipment line tells them apart at
    # TIMING_AFTER_COMPLETE. What keeps the attribution right is that each generate run COMPLETES its own
    # document before it returns, and consolidation only ever joins a line onto a header that is still
    # drafted - so a sibling's document is never a consolidation target for the next sibling's.
    #
    # This scenario is that assumption's guard. Both siblings ship 5 of the same order line's 10, on the SAME
    # delivery date and the same customer / location / warehouse - i.e. into the very consolidation window
    # that would merge them if the first shipment were left open. Each must book 5, never 10.
    #
    # DeliveryRule=Force, so what gets shipped is decided by the process quantity rather than by warehouse
    # availability - the subject here is which LINES a completion books. Their own warehouse keeps these
    # shipments out of any other scenario's consolidation window.
    Given metasfresh contains M_Products:
      | Identifier  | Name        |
      | productQ11S | ProductQ11S |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_SO               | productQ11S             | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value              | Name              |
      | warehouseQ11S             | warehouseValueQ11S | warehouseNameQ11S |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value            | M_Warehouse_ID.Identifier |
      | locatorQ11S             | locatorValueQ11S | warehouseQ11S             |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DeliveryRule |
      | orderQ11S  | true    | customer                 | 2023-02-03  | 2023-02-05T00:00:00Z | customerLocation                      | warehouseQ11S                 | F                |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ11S | orderQ11S             | productQ11S             | 10         | shipper_DHL                 |

    When the order identified by orderQ11S is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQ11S_1 | orderLineQ11S  |

    # THE split: one order line, one shipment schedule, two plannings of 5 each.
    When generate 1 additional M_Delivery_Planning records for: deliveryPlanningQ11S_1

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID                        | C_OrderLine_ID |
      | deliveryPlanningQ11S_1,deliveryPlanningQ11S_2 | orderLineQ11S  |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed | M_InOut_ID |
      | deliveryPlanningQ11S_1 | 10         | 10           | Outgoing           | 5                     | 0             | 0                       | false    | false     | null       |
      | deliveryPlanningQ11S_2 | 10         | 10           | Outgoing           | 5                     | 0             | 0                       | false    | false     | null       |

    When the delivery planning identified by deliveryPlanningQ11S_1 generates a shipment:
      | DeliveryDate | Qty | OPT.M_InOut_ID |
      | 2023-02-05   | 5   | shipmentQ11S_1 |

    # The first sibling books its own 5. QtyTotalOpen is the ORDER LINE's figure (QtyOrdered minus the actuals
    # of every planning on it), so both rows drop to 5 - but only the sibling that shipped carries an actual.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed | M_InOut_ID     |
      | deliveryPlanningQ11S_1 | 10         | 5            | Outgoing           | 5                     | 5             | 5                       | false    | true      | shipmentQ11S_1 |
      | deliveryPlanningQ11S_2 | 10         | 5            | Outgoing           | 5                     | 0             | 0                       | false    | false     | null           |

    When the delivery planning identified by deliveryPlanningQ11S_2 generates a shipment:
      | DeliveryDate | Qty | OPT.M_InOut_ID |
      | 2023-02-05   | 5   | shipmentQ11S_2 |

    # The premise, asserted rather than assumed: the two shipments are SEPARATE documents, each carrying
    # exactly one line of the shared order line. Had the first been left drafted, the second run would have
    # consolidated onto it and this step would find two lines of productQ11S on one document.
    Then validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | C_OrderLine_ID.Identifier | movementqty | processed |
      | shipmentLineQ11S_1        | shipmentQ11S_1        | orderLineQ11S             | 5           | true      |
      | shipmentLineQ11S_2        | shipmentQ11S_2        | orderLineQ11S             | 5           | true      |

    # THE assertion: 5 each (each planning's own share), never 10 (the order line). QtyTotalOpen follows:
    # 10 - (5 + 5) = 0 on both rows.
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | IsClosed | Processed | M_InOut_ID     |
      | deliveryPlanningQ11S_1 | 10         | 0            | Outgoing           | 5                     | 5             | 5                       | false    | true      | shipmentQ11S_1 |
      | deliveryPlanningQ11S_2 | 10         | 0            | Outgoing           | 5                     | 5             | 5                       | false    | true      | shipmentQ11S_2 |

  @Id:S31789_TC_Q11_IncomingCompletionWritesDischargeOnly
  Scenario: Completing a receipt writes only the discharge end - the load end stays the Task Q7c mirror of the plan

    Given metasfresh contains M_PricingSystems
      | Identifier        | OPT.IsActive |
      | pricingSystemQ11I | true         |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_Q11I | pricingSystemQ11I              | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier |
      | priceListVersion_Q11I | priceList_Q11I             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_Q11I             | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQ11I          | Y        | N          | pricingSystemQ11I              |
      | warehouseBPQ11I     |          |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQ11I    | 1234564396497 | vendorQ11I                | true                | true                |
      | warehouseLocationQ11I | 1203522892497 | warehouseBPQ11I           | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQ11I                | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value               | Name                | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQ11I              | warehouseValueQ11I  | warehouseNameQ11I   | warehouseBPQ11I               | warehouseLocationQ11I                  |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value             | M_Warehouse_ID.Identifier |
      | locatorQ11I              | locatorValueQ11I  | warehouseQ11I              |
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQ11I      | false   | vendorQ11I                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQ11I                     | warehouseQ11I                  | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ11I | orderQ11I              | product                 | 9          | shipper_DHL                 |

    When the order identified by orderQ11I is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQ11I   | orderLineQ11I  |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningQ11I   | 9          | 9            | Incoming            | 9                     | 9             | 0                       |

    When the delivery planning identified by deliveryPlanningQ11I generates a receipt:
      | ReceiptDate | Qty | OPT.M_InOut_ID |
      | 2023-02-05  | 5   | receiptQ11I    |

    # Only the discharge end moves to the booked 5 - the load end stays 9, Q7c's mirror of the plan,
    # never touched by a receipt's completion.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | Processed |
      | deliveryPlanningQ11I   | 9          | 4            | Incoming            | 9                     | 9             | 5                       | true      |

  @Id:S31789_TC_Q11_DropshipReceiptWritesDischargeOnly
  Scenario: Completing a Dropship receipt writes discharge only, never the load placeholder - and reversal clears only that same end

    # A Dropship-direction planning is created and driven exactly like Incoming today (fix round after
    # commit 242a95f1 - the plan's original write-by-the-END table wrongly described the not-yet-built
    # consolidated planning): GenerateIncomingDeliveryPlanningCommand is the only command that creates it
    # (order.isDropShip() decides Incoming vs Dropship), and it seeds ActualLoadQty from the planned load
    # the same way for both. So a Dropship RECEIPT must write discharge, exactly like an Incoming receipt -
    # never the load end, which stays Task Q7c's never-reported-vendor-load placeholder.
    Given metasfresh contains M_PricingSystems
      | Identifier        | OPT.IsActive |
      | pricingSystemQ11D | true         |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_Q11D | pricingSystemQ11D              | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier |
      | priceListVersion_Q11D | priceList_Q11D             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_Q11D             | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQ11D          | Y        | N          | pricingSystemQ11D              |
      | warehouseBPQ11D     |          |            |                                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQ11D    | 1234564396498 | vendorQ11D                | true                | true                |
      | warehouseLocationQ11D | 1203522892498 | warehouseBPQ11D           | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQ11D                | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value               | Name                | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQ11D              | warehouseValueQ11D  | warehouseNameQ11D   | warehouseBPQ11D               | warehouseLocationQ11D                  |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value             | M_Warehouse_ID.Identifier |
      | locatorQ11D              | locatorValueQ11D  | warehouseQ11D              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType | OPT.IsDropShip | OPT.DropShip_BPartner_ID.Identifier |
      | orderQ11D  | false   | vendorQ11D                | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQ11D                     | warehouseQ11D                  | POO             | true           | customer                             |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ11D | orderQ11D              | product                 | 8          | shipper_DHL                 |

    When the order identified by orderQ11D is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQ11D   | orderLineQ11D  |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity |
      | deliveryPlanningQ11D   | 8          | 8            | Dropship            | 8                     | 8             | 0                       |

    When the delivery planning identified by deliveryPlanningQ11D generates a receipt:
      | ReceiptDate | Qty | OPT.M_InOut_ID |
      | 2023-02-05  | 5   | receiptQ11D    |

    # Discharge moves to the booked 5; the load placeholder stays 8 (Task Q7c's mirror of the plan) -
    # NOT overwritten with the booked quantity, which the pre-fix code did.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | Processed |
      | deliveryPlanningQ11D   | 8          | 3            | Dropship            | 8                     | 8             | 5                       | true      |

    When the material receipt identified by receiptQ11D is reversed

    # Reversal clears the discharge back to empty; the load placeholder is still untouched at 8 - the
    # pre-fix code zeroed it here, with no path to restore it.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | Processed |
      | deliveryPlanningQ11D   | 8          | 8            | Dropship            | 8                     | 8             | 0                       | false     |

  @Id:S31789_TC_Q11_GenerateReceiptProcessOrdering
  Scenario: The production generate-receipt process itself makes completion write the discharge actual - it stamps the planning link on the DRAFT, not on the finished receipt

    # The scenario that exists SPECIFICALLY to pin the draft-vs-post-generation ordering (several others now
    # drive the real generate processes too, but each for its own reason). That process generates the receipt
    # AND completes it in a single call, so if M_Delivery_Planning_ID were put on the receipt only AFTER that
    # call returned, the receipt would be completed with the FK still unset and interceptor/M_InOut
    # #afterComplete - which returns immediately on a null FK - would never write anything: no
    # ActualDischargeQuantity, no Processed, no receipt back-link on the planning, no delivered-state
    # recompute. The raw M_InOut.M_Delivery_Planning_ID would still end up set, so the data looks
    # half-right; only the four assertions below tell the two orderings apart.
    Given metasfresh contains M_PricingSystems
      | Identifier        | OPT.IsActive |
      | pricingSystemQ11P | true         |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | priceList_Q11P | pricingSystemQ11P             | DE                    | EUR                 | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier |
      | priceListVersion_Q11P | priceList_Q11P            |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | priceListVersion_Q11P             | product                 | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID.Identifier |
      | vendorQ11P      | Y        | N          | pricingSystemQ11P             |
      | warehouseBPQ11P |          |            |                               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipToDefault |
      | vendorLocationQ11P    | 1234564396491 | vendorQ11P               | true                | true                |
      | warehouseLocationQ11P | 1203522892491 | warehouseBPQ11P          | true                | true                |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendorQ11P               | product                 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value              | Name              | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | warehouseQ11P             | warehouseValueQ11P | warehouseNameQ11P | warehouseBPQ11P              | warehouseLocationQ11P                 |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value             | M_Warehouse_ID.Identifier |
      | locatorQ11P             | locatorValueQ11P  | warehouseQ11P             |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.DocBaseType |
      | orderQ11P  | false   | vendorQ11P               | 2023-02-03  | 2023-02-20T00:00:00Z | vendorLocationQ11P                    | warehouseQ11P                 | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineQ11P | orderQ11P             | product                 | 9          | shipper_DHL                 |

    When the order identified by orderQ11P is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptScheduleQ11P             | orderLineQ11P             | vendorQ11P               | vendorLocationQ11P                | product                 | 9          | warehouseQ11P             |
    And after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID |
      | deliveryPlanningQ11P   | orderLineQ11P  |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | ActualLoadQty | ActualDischargeQuantity | Processed | M_InOut_ID |
      | deliveryPlanningQ11P   | 9          | 9            | Incoming           | 9                     | 9             | 0                       | false     | null       |

    # The production path: the process picks the planning's receipt schedule, builds the VHU, generates the
    # receipt and completes it - all inside this one step.
    When the delivery planning identified by deliveryPlanningQ11P generates a receipt:
      | ReceiptDate | Qty | OPT.M_InOut_ID |
      | 2023-02-05  | 5   | receiptQ11P    |

    # Everything below is written by interceptor/M_InOut#afterComplete and by nothing else:
    #  - ActualDischargeQuantity 5: the booked quantity on the end a receipt occupies
    #  - Processed true: the planning is now delivered
    #  - M_InOut_ID: the receipt back-link
    # ActualLoadQty stays 9 (Task Q7c's mirror of the plan, never a receipt's to write) and
    # PlannedDischargeQuantity became 5 - that one is the process' own Qty write-back, not the interceptor's,
    # so it is the control: it holds under BOTH orderings.
    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity | Processed | M_InOut_ID  |
      | deliveryPlanningQ11P   | 9          | 4            | Incoming           | 9                     | 5                        | 9             | 5                       | true      | receiptQ11P |

    # And the reversal of a receipt the production path generated undoes exactly those writes.
    When the material receipt identified by receiptQ11P is reversed

    Then validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity | Processed | M_InOut_ID |
      | deliveryPlanningQ11P   | 9          | 9            | Incoming           | 9                     | 5                        | 9             | 0                       | false     | null       |

  @Id:S31789_TC_Q14_ShippingPackageMirrorsPlanningQuantities
  Scenario: Editing all four planning quantities syncs the delivery instruction line, with no propagation step

    # The instruction line has no logic of its own - all four figures (planned load, planned discharge,
    # actual load, actual discharge) are a straight read-through of the planning via the allocation
    # (ColumnSQL, Task Q14). So editing the planning directly - no generate, no receipt/shipment
    # completion, no explicit "sync" step of any kind - must be the only thing this scenario does before
    # the line already shows the new figures.
    Given metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DatePromised     | OPT.C_BPartner_Location_ID.Identifier |
      | orderSyncQty | true    | customer                 | 2023-02-03  | 2023-02-20T00:00:00Z | customerLocation                      |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Shipper_ID.Identifier |
      | orderLineSyncQty | orderSyncQty           | product                 | 10         | shipper_DHL                 |

    When the order identified by orderSyncQty is completed

    Then after not more than 30s, load created M_Delivery_Planning:
      | M_Delivery_Planning_ID | C_OrderLine_ID   |
      | planningSyncQty        | orderLineSyncQty |
    And validate M_Delivery_Planning:
      | M_Delivery_Planning_ID | QtyOrdered | QtyTotalOpen | TransportDirection | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | planningSyncQty        | 10         | 10           | Outgoing            | 10                    | 10                       | 0             | 0                       |

    When generate M_ShipperTransportation for M_Delivery_Planning:
      | M_ShipperTransportation_ID | M_Delivery_Planning_ID | IsComplete |
      | deliveryInstructionSyncQty | planningSyncQty        | true       |

    Then the M_ShipperTransportation identified by deliveryInstructionSyncQty holds exactly the following active M_Delivery_Planning_Alloc:
      | M_Delivery_Planning_ID | M_ShippingPackage_ID   |
      | planningSyncQty        | shippingPackageSyncQty |
    # generation leaves the package mirroring the planning's CURRENT figures - unchanged from before this
    # task, since createShippingPackage no longer copies anything; there is simply nothing else to show yet
    And validate M_Shipping_Package:
      | M_ShippingPackage_ID   | ActualLoadQty | ActualDischargeQuantity | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | shippingPackageSyncQty | 0             | 0                       | 10                    | 10                       |

    # edit all four planning figures directly - no generate, no receipt/shipment completion in this
    # scenario, and no dedicated "propagate to package" step exists: the mirror is the only mechanism
    When update M_Delivery_Planning:
      | M_Delivery_Planning_ID | PlannedLoadedQuantity | PlannedDischargeQuantity | ActualLoadQty | ActualDischargeQuantity |
      | planningSyncQty        | 6                     | 7                        | 8             | 9                        |

    # nothing between the edit above and the assertion below: no re-load step, no propagation step. The
    # step-def re-reads the package from the database itself (M_ShippingPackage_StepDef#reloadFromDatabase),
    # which the derived columns require - so this asserts the mirror, not a cucumber-harness reload.
    Then validate M_Shipping_Package:
      | M_ShippingPackage_ID   | ActualLoadQty | ActualDischargeQuantity | PlannedLoadedQuantity | PlannedDischargeQuantity |
      | shippingPackageSyncQty | 8             | 9                       | 6                     | 7                        |
