@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@F00701
@allure.label.feature:F00130_Shipment_Schedule
@F00130
@ghActions:run_on_executor7
Feature: Invoice candidates of a split shipment - the second shipment's lines stay invoiceable
## F00701: Invoice Candidates
## F00130: Shipment Schedule (Lieferdisposition)
## One order, one picking job, one pallet, three lines (real-life data shape: delivered 6 / 5 / 6, one shipment for line 1 and one for lines 2 and 3).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product1   | PCE      |
      | product2   | PCE      |
      | product3   | PCE      |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
      | LU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name | HU_UnitType | IsCurrent |
      | TU                            | TU                    | TU   | TU          | Y         |
      | LU                            | LU                    | LU   | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | TU                         | TU                            | 0   | MI       |                                  |
      | LU                         | LU                            | 10  | HU       | TU                               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx1_P1                            | TU                         | product1                | 1   | 2000-01-01 |
      | TUx1_P2                            | TU                         | product2                | 1   | 2000-01-01 |
      | TUx1_P3                            | TU                         | product3                | 1   | 2000-01-01 |

    And load M_Shipper:
      | Identifier | Name          |
      | shipperB   | DPD - Classic |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | PS         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | PL         | PS                 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | PLV        | PL             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | product1     | 6.0      | PCE               | Nominal              | Normal                        |
      | PLV                    | product2     | 8.0      | PCE               | Nominal              | Normal                        |
      | PLV                    | product3     | 8.0      | PCE               | Nominal              | Normal                        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customerLocation | Dummy_GLN | customer                 | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | line1                         | product1                | 0       | 1000     | PCE          |
      | inventory                 | line2                         | product2                | 0       | 1000     | PCE          |
      | inventory                 | line3                         | product3                | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID     |
      | line1              | pickFromCU1 |
      | line2              | pickFromCU2 |
      | line3              | pickFromCU3 |


  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: one workpackage creates two shipments (batch lost on lines 2 and 3), all lines are invoiced
    Given new "GenerateInOutFromShipmentSchedules" workpackages are held
    And for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                |
      | SO                    | L3         | product3                | 6          | TUx1_P3                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
      | shipmentSchedule3 | L3                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |
      | ic3                               | L3                        |

    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
      | pickFromCU2 | pickFromAggregatedLU2 | TU       | 1           | 10          |
      | pickFromCU3 | pickFromAggregatedLU3 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |

    And complete picking job
    And there is 1 pending "GenerateInOutFromShipmentSchedules" workpackage
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule1 gets the batch of the pending "GenerateInOutFromShipmentSchedules" workpackage by direct update
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule2 is set to null by direct update
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule3 is set to null by direct update
    When the held "GenerateInOutFromShipmentSchedules" workpackage is processed in one transaction

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment2             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule2     | true     |
      | shipmentSchedule3     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice |
      | ic1                               | false         | 6            |
      | ic2                               | false         | 5            |
      | ic3                               | false         | 6            |
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
      | ic2                               |
      | ic3                               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic1                               | invoice                 |
      | ic2                               | invoice                 |
      | ic3                               | invoice                 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine1                | invoice                 | product1                | 6           | true      |
      | invoiceLine2                | invoice                 | product2                | 5           | true      |
      | invoiceLine3                | invoice                 | product3                | 6           | true      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: two shipment workpackages (the order line of line 3 has another shipper), all lines are invoiced
    Given new "GenerateInOutFromShipmentSchedules" workpackages are held
    And for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                |                             |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                |                             |
      | SO                    | L3         | product3                | 6          | TUx1_P3                                | shipperB                    |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
      | shipmentSchedule3 | L3                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |
      | ic3                               | L3                        |

    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
      | pickFromCU2 | pickFromAggregatedLU2 | TU       | 1           | 10          |
      | pickFromCU3 | pickFromAggregatedLU3 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |

    And complete picking job
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And there are 2 pending "GenerateInOutFromShipmentSchedules" workpackages
    When the next GenerateInOutFromShipmentSchedules workpackage is processed
    And the next GenerateInOutFromShipmentSchedules workpackage is processed
    And held "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are released

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule1     | true     |
      | shipmentSchedule2     | true     |
      | shipmentSchedule3     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice |
      | ic1                               | false         | 6            |
      | ic2                               | false         | 5            |
      | ic3                               | false         | 6            |
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
      | ic2                               |
      | ic3                               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic1                               | invoice                 |
      | ic2                               | invoice                 |
      | ic3                               | invoice                 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine1                | invoice                 | product1                | 6           | true      |
      | invoiceLine2                | invoice                 | product2                | 5           | true      |
      | invoiceLine3                | invoice                 | product3                | 6           | true      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: reversing the second shipment of a one-workpackage split restores the processed state of its lines
    Given new "GenerateInOutFromShipmentSchedules" workpackages are held
    And for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                |
      | SO                    | L3         | product3                | 6          | TUx1_P3                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
      | shipmentSchedule3 | L3                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |
      | ic3                               | L3                        |

    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
      | pickFromCU2 | pickFromAggregatedLU2 | TU       | 1           | 10          |
      | pickFromCU3 | pickFromAggregatedLU3 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |

    And complete picking job
    And there is 1 pending "GenerateInOutFromShipmentSchedules" workpackage
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule1 gets the batch of the pending "GenerateInOutFromShipmentSchedules" workpackage by direct update
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule2 is set to null by direct update
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule3 is set to null by direct update
    When the held "GenerateInOutFromShipmentSchedules" workpackage is processed in one transaction

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment2             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule2     | true     |
      | shipmentSchedule3     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice |
      | ic1                               | false         | 6            |
      | ic2                               | false         | 5            |
      | ic3                               | false         | 6            |
    When the shipment identified by shipment2 is reversed as shipment2Reversal
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 0            |
      | L3             | 0            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule2     | true     |
      | shipmentSchedule3     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyDelivered | QtyToInvoice | OPT.Processed_Override |
      | ic1                               | false         | 6            | 6            | null                   |
      | ic2                               | true          | 0            | 0            | null                   |
      | ic3                               | true          | 0            | 0            | null                   |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: reactivating the second shipment of a one-workpackage split restores the processed state of its lines
    Given new "GenerateInOutFromShipmentSchedules" workpackages are held
    And for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                |
      | SO                    | L3         | product3                | 6          | TUx1_P3                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
      | shipmentSchedule3 | L3                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |
      | ic3                               | L3                        |

    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
      | pickFromCU2 | pickFromAggregatedLU2 | TU       | 1           | 10          |
      | pickFromCU3 | pickFromAggregatedLU3 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |

    And complete picking job
    And there is 1 pending "GenerateInOutFromShipmentSchedules" workpackage
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule1 gets the batch of the pending "GenerateInOutFromShipmentSchedules" workpackage by direct update
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule2 is set to null by direct update
    And C_Async_Batch_ID of M_ShipmentSchedule shipmentSchedule3 is set to null by direct update
    When the held "GenerateInOutFromShipmentSchedules" workpackage is processed in one transaction

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment2             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule2     | true     |
      | shipmentSchedule3     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice |
      | ic1                               | false         | 6            |
      | ic2                               | false         | 5            |
      | ic3                               | false         | 6            |
    When the shipment identified by shipment2 is reactivated
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 0            |
      | L3             | 0            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule2     | true     |
      | shipmentSchedule3     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyDelivered | QtyToInvoice | OPT.Processed_Override |
      | ic1                               | false         | 6            | 6            | null                   |
      | ic2                               | true          | 0            | 0            | null                   |
      | ic3                               | true          | 0            | 0            | null                   |
