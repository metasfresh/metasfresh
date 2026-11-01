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



  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: TC7b - reversing the second shipment of a one-workpackage split after invoicing leaves its lines processed, without a credit
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

    When the shipment identified by shipment2 is reversed as shipment2Reversal
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 0            |
      | L3             | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyDelivered | QtyInvoiced | QtyToInvoice | OPT.Processed_Override |
      | ic1                               | true          | 6            | 6           | 0            | null                   |
      | ic2                               | true          | 0            | 5           | 0            | null                   |
      | ic3                               | true          | 0            | 6           | 0            | null                   |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a quantity-to-deliver run without handling units splits into two shipments, all lines are invoiced
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
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
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule1     |
      | shipmentSchedule2     |
      | shipmentSchedule3     |
    And there are 2 pending "GenerateInOutFromShipmentSchedules" workpackages
    When the next GenerateInOutFromShipmentSchedules workpackage is processed
    And the next GenerateInOutFromShipmentSchedules workpackage is processed
    And held "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are released

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
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


  # Line 2 is in another warehouse: its draft shipment needs a different header key than line 1's. A different shipper
  # does not do it, because an open draft shipment is reused regardless of the shipper (HUShipmentScheduleBL.getOpenShipmentOrNull).
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: two draft shipments, completing the second one makes its line invoiceable again
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value            |
      | warehouseB                | Leergebindelager |
    # warehouseB needs its own stock of product2: line 2 is delivered from there, the Background inventory is in the standard warehouse
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventoryB                | 2024-03-20   | warehouseB     |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventoryB                | lineB2                        | product2                | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventoryB'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID      |
      | lineB2             | stockHUB2    |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                |                               |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                | warehouseB                    |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule1     | D            | false               | false       |
      | shipmentSchedule2     | D            | false               | false       |
    And there are 2 pending "GenerateInOutFromShipmentSchedules" workpackages
    And the next GenerateInOutFromShipmentSchedules workpackage is processed
    And the next GenerateInOutFromShipmentSchedules workpackage is processed
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | DR            |
      | shipmentSchedule2                | shipment2             | DR            |

    When the shipment identified by shipment1 is completed
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule2     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed |
      | ic1                               | false         |
      | ic2                               | true          |

    When the shipment identified by shipment2 is completed
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyDelivered | QtyToInvoice |
      | ic1                               | false         | 6            | 6            |
      | ic2                               | false         | 5            | 5            |
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
      | ic2                               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic1                               | invoice                 |
      | ic2                               | invoice                 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine1                | invoice                 | product1                | 6           | true      |
      | invoiceLine2                | invoice                 | product2                | 5           | true      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a line closed by the other line's shipment keeps its approval for invoicing and is invoiced in an approved-only run
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value            |
      | warehouseB                | Leergebindelager |
    # warehouseB needs its own stock of product2: line 2 is delivered from there, the Background inventory is in the standard warehouse
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventoryB                | 2024-03-20   | warehouseB     |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventoryB                | lineB2                        | product2                | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventoryB'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID      |
      | lineB2             | stockHUB2    |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                |                               |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                | warehouseB                    |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | ic1                               | true                     |
      | ic2                               | true                     |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule1     | D            | false               | false       |
      | shipmentSchedule2     | D            | false               | false       |
    And there are 2 pending "GenerateInOutFromShipmentSchedules" workpackages
    And the next GenerateInOutFromShipmentSchedules workpackage is processed
    And the next GenerateInOutFromShipmentSchedules workpackage is processed
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | DR            |
      | shipmentSchedule2                | shipment2             | DR            |

    When the shipment identified by shipment1 is completed
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule2     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.ApprovalForInvoicing |
      | ic1                               | false         | true                     |
      | ic2                               | true          | true                     |

    When the shipment identified by shipment2 is completed
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.ApprovalForInvoicing | OPT.Processed_Override | QtyDelivered | QtyToInvoice |
      | ic1                               | false         | true                     | null                   | 6            | 6            |
      | ic2                               | false         | true                     | null                   | 5            | 5            |
    When process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.OnlyApprovedForInvoicing |
      | ic1                               | true                         |
      | ic2                               | true                         |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic1                               | invoice                 |
      | ic2                               | invoice                 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine1                | invoice                 | product1                | 6           | true      |
      | invoiceLine2                | invoice                 | product2                | 5           | true      |

  # A later shipment workpackage must still ship the schedules an earlier shipment of the same order closed, even when a
  # schedule recompute marked them processed in between. The recompute is held after the picking job and released
  # between the two workpackages.
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: two shipment workpackages with a recompute in between, the second one still ships its picked line, all lines are invoiced
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
    And held "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are released
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    # the first shipment closed line 3 and the recompute marked it processed, before the second workpackage runs
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed | QtyDelivered |
      | shipmentSchedule3     | true     | true      | 0            |
    When the next GenerateInOutFromShipmentSchedules workpackage is processed

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 5            |
      | L3             | 6            |
    # every pick is on a shipment line: no picked quantity left off a shipment
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule1
      | QtyPicked | M_InOutLine_ID |
      | 6         | shipmentLine1  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule2
      | QtyPicked | M_InOutLine_ID |
      | 5         | shipmentLine2  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule3
      | QtyPicked | M_InOutLine_ID |
      | 6         | shipmentLine3  |
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

  # As "Rüstliste erfassen" -> "Auswahl Liefern": the quantity to deliver is entered per line, then the selection is
  # shipped with quantity type "to deliver", without handling units.
  # Here lines 1 and 2 carry the other shipper: the selection enqueuer orders the workpackages by header key, so the
  # shipper-B workpackage (lines 1, 2) runs first and line 3 is in the second one, as in the picking-job scenario.
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: two quantity-to-deliver workpackages with a recompute in between, the second one still ships its entered quantity, all lines are invoiced
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                | shipperB                    |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                | shipperB                    |
      | SO                    | L3         | product3                | 6          | TUx1_P3                                |                             |
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
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | shipmentSchedule1                | 6                         |
      | shipmentSchedule2                | 5                         |
      | shipmentSchedule3                | 6                         |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver_Override | QtyToDeliver |
      | shipmentSchedule1     | 6                     | 6            |
      | shipmentSchedule2     | 5                     | 5            |
      | shipmentSchedule3     | 6                     | 6            |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule1     |
      | shipmentSchedule2     |
      | shipmentSchedule3     |
    And there are 2 pending "GenerateInOutFromShipmentSchedules" workpackages
    When the next GenerateInOutFromShipmentSchedules workpackage is processed
    And held "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are released
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    # the first shipment closed line 3 and the recompute marked it processed, before the second workpackage runs
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed | QtyDelivered |
      | shipmentSchedule3     | true     | true      | 0            |
    When the next GenerateInOutFromShipmentSchedules workpackage is processed

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 5            |
      | L3             | 6            |
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

  # Line 3 is closed by hand after its workpackage was enqueued, and the recompute marks it processed before the
  # workpackage runs. Its entered quantity is still shipped.
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a line closed by hand after its quantity-to-deliver workpackage was enqueued is still shipped with its entered quantity
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                | shipperB                    |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                | shipperB                    |
      | SO                    | L3         | product3                | 6          | TUx1_P3                                |                             |
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
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | shipmentSchedule1                | 6                         |
      | shipmentSchedule2                | 5                         |
      | shipmentSchedule3                | 6                         |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver_Override | QtyToDeliver |
      | shipmentSchedule1     | 6                     | 6            |
      | shipmentSchedule2     | 5                     | 5            |
      | shipmentSchedule3     | 6                     | 6            |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule1     |
      | shipmentSchedule2     |
      | shipmentSchedule3     |
    And there are 2 pending "GenerateInOutFromShipmentSchedules" workpackages
    When the M_ShipmentSchedule identified by shipmentSchedule3 is closed
    And held "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are released
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed | QtyDelivered |
      | shipmentSchedule3     | true     | true      | 0            |
    When the next GenerateInOutFromShipmentSchedules workpackage is processed
    And the next GenerateInOutFromShipmentSchedules workpackage is processed

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 5            |
      | L3             | 6            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice |
      | ic1                               | false         | 6            |
      | ic2                               | false         | 5            |
      | ic3                               | false         | 6            |

  # Boundary: a closed line for which the second workpackage carries no picked quantity stays unshipped, without an
  # error, and its invoice candidate stays processed. The picks are recorded by a picking job that creates no shipment;
  # the shipments are generated afterwards from the selection with quantity type "picked" ("Auswahl Liefern").
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a closed line with nothing picked in the second picked-quantity workpackage stays unshipped and its candidate stays processed
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob |
      | Y                   | DO_NOT_CREATE        | Y                                  |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product4   | PCE      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx1_P4                            | TU                         | product4                | 1   | 2000-01-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | product4     | 9.0      | PCE               | Nominal              | Normal                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                | shipperB                    |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                | shipperB                    |
      | SO                    | L3         | product3                | 6          | TUx1_P3                                |                             |
      | SO                    | L4         | product4                | 4          | TUx1_P4                                |                             |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
      | shipmentSchedule3 | L3                        | N             |
      | shipmentSchedule4 | L4                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |
      | ic3                               | L3                        |
      | ic4                               | L4                        |

    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
      | pickFromCU2 | pickFromAggregatedLU2 | TU       | 1           | 10          |
      | pickFromCU3 | pickFromAggregatedLU3 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    # nothing is picked for line 4
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |
    And complete picking job
    And validate no M_InOut found for C_Order identified by SO
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed |
      | shipmentSchedule4     | false    | false     |
    And there are no M_ShipmentSchedule_QtyPicked records created for the following shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule4                |

    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And 'generate shipments' process is invoked with QuantityType=P, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule1     |
      | shipmentSchedule2     |
      | shipmentSchedule3     |
      | shipmentSchedule4     |
    And there are 2 pending "GenerateInOutFromShipmentSchedules" workpackages
    When the next GenerateInOutFromShipmentSchedules workpackage is processed
    And held "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are released
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed | QtyDelivered |
      | shipmentSchedule3     | true     | true      | 0            |
      | shipmentSchedule4     | true     | true      | 0            |
    # the step fails if the workpackage processor throws or does not return SUCCESS: no error for line 4
    When the next GenerateInOutFromShipmentSchedules workpackage is processed

    # line 4: no shipment line, its schedule stays closed and its candidate processed
    Then there are no M_ShipmentSchedule_QtyPicked records created for the following shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule4                |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed | QtyDelivered |
      | shipmentSchedule4     | true     | true      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyDelivered | QtyToInvoice |
      | ic4                               | true          | 0            | 0            |
    # the picked lines are shipped
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 5            |
      | L3             | 6            |
      | L4             | 0            |

  # Boundary: a line without an entered quantity to deliver (the system computes it) is not shipped by the second
  # quantity-to-deliver workpackage once the first shipment closed it and the recompute marked it processed (as today).
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a closed line without an entered quantity in the second quantity-to-deliver workpackage stays unshipped and its candidate stays processed
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product4   | PCE      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx1_P4                            | TU                         | product4                | 1   | 2000-01-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | product4     | 9.0      | PCE               | Nominal              | Normal                        |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory4                | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory4                | line4                         | product4                | 0       | 100      | PCE          |
    And complete inventory with inventoryIdentifier 'inventory4'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID     |
      | line4              | pickFromCU4 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                | shipperB                    |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                | shipperB                    |
      | SO                    | L3         | product3                | 6          | TUx1_P3                                |                             |
      | SO                    | L4         | product4                | 4          | TUx1_P4                                |                             |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
      | shipmentSchedule3 | L3                        | N             |
      | shipmentSchedule4 | L4                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |
      | ic3                               | L3                        |
      | ic4                               | L4                        |
    # no entered quantity for line 4
    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | shipmentSchedule1                | 6                         |
      | shipmentSchedule2                | 5                         |
      | shipmentSchedule3                | 6                         |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | QtyToDeliver_Override | QtyToDeliver |
      | shipmentSchedule1     | 6                     | 6            |
      | shipmentSchedule2     | 5                     | 5            |
      | shipmentSchedule3     | 6                     | 6            |
      | shipmentSchedule4     |                       | 4            |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | shipmentSchedule1     |
      | shipmentSchedule2     |
      | shipmentSchedule3     |
      | shipmentSchedule4     |
    And there are 2 pending "GenerateInOutFromShipmentSchedules" workpackages
    When the next GenerateInOutFromShipmentSchedules workpackage is processed
    And held "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are released
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed | QtyDelivered |
      | shipmentSchedule3     | true     | true      | 0            |
      | shipmentSchedule4     | true     | true      | 0            |
    # the step fails if the workpackage processor throws or does not return SUCCESS
    When the next GenerateInOutFromShipmentSchedules workpackage is processed

    # line 4: not shipped, its schedule stays closed and its candidate processed
    Then there are no M_ShipmentSchedule_QtyPicked records created for the following shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule4                |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed | QtyDelivered |
      | shipmentSchedule4     | true     | true      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyDelivered | QtyToInvoice |
      | ic4                               | true          | 0            | 0            |
    # the lines with an entered quantity are shipped
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 5            |
      | L3             | 6            |
      | L4             | 0            |

  # A later picked-quantity run ships the picks of a line that an earlier shipment closed (as with ship-on-LU-close or
  # HU consolidation: one shipment per LU, the first shipment closes the order's other lines that still have picks in a
  # later LU). The picks are recorded by a picking job that creates no shipment; each shipment is then generated through
  # the shipment service with quantity type "picked", as the picking and consolidation flows do.
  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a later picked-quantity run still ships the picks of a line that an earlier shipment closed, all lines are invoiced
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob |
      | Y                   | DO_NOT_CREATE        | Y                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                |
      | SO                    | L2         | product2                | 5          | TUx1_P2                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
      | shipmentSchedule2 | L2                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
      | ic2                               | L2                        |

    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
      | pickFromCU2 | pickFromAggregatedLU2 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
    And complete picking job
    And validate no M_InOut found for C_Order identified by SO

    # first run: line 1 only; its shipment's completion closes line 2, which is not delivered yet
    When shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment1             | shipmentSchedule1                | P                 | Y                  |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed | Processed | QtyDelivered |
      | shipmentSchedule2     | true     | true      | 0            |

    # later run: line 2, closed by the first shipment, still carries its picks
    When shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment2             | shipmentSchedule2                | P                 | Y                  |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment2             | CO            |
    And validate C_OrderLine:
      | C_OrderLine_ID | qtydelivered |
      | L1             | 6            |
      | L2             | 5            |
    # every pick is on a shipment line: no picked quantity left off a shipment
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule1
      | QtyPicked | M_InOutLine_ID |
      | 6         | shipmentLine1  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by shipmentSchedule2
      | QtyPicked | M_InOutLine_ID |
      | 5         | shipmentLine2  |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice |
      | ic1                               | false         | 6            |
      | ic2                               | false         | 5            |
