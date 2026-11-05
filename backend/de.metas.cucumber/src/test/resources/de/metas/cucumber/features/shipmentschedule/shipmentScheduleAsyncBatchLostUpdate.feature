@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00130_Shipment_Schedule
@F00130
@ghActions:run_on_executor7
Feature: A schedule recompute does not split one picking job's shipment by clearing the async batch the run assigned
## F00130: Shipment Schedule (Lieferdisposition)
## One order, one picking job, three lines. Completing the job assigns one new async batch to all three schedules
## (ShipmentService.groupSchedulesByAsyncBatch) and enqueues ONE shipment workpackage. The batch is part of the
## shipment header key, so a schedule recompute that writes the order's empty batch over one schedule before the
## workpackage reads it splits the run into two shipments. Each scenario expects one shipment per run.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 210.0      | 210.0       | Y         |

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

    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
      | pickFromCU2 | pickFromAggregatedLU2 | TU       | 1           | 10          |
      | pickFromCU3 | pickFromAggregatedLU3 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 210.0
    And set picking target as new LU identified by LU


  # ###################################################################################################################
  # ###################################################################################################################

  # Stale timing (the shape of the observed production case): a recompute loaded the schedule BEFORE the run assigned the batch (here:
  # before the pick it recomputes) and saves it AFTER. The pick makes the save a real change, so PO writes every column
  # the recompute set, including the order's empty batch over the batch committed in between, without a change-log row.
  @from:cucumber
  @Id:S33362_210
  Scenario: a recompute that loaded a schedule before the batch was assigned and saves it afterwards does not split the run
    Given a copy of M_ShipmentSchedule shipmentSchedule2 is loaded for a later recompute
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And complete picking job
    And there is 1 pending "GenerateInOutFromShipmentSchedules" workpackage
    When the earlier loaded copy of M_ShipmentSchedule shipmentSchedule2 is recomputed from its order and saved
    And the held "GenerateInOutFromShipmentSchedules" workpackage is processed in one transaction as shipmentWorkPackage

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment1             | CO            |
    # the stale save did not write the order's empty batch over the run's batch
    And M_ShipmentSchedule shipmentSchedule2 carries the C_Async_Batch_ID of the C_Queue_WorkPackage shipmentWorkPackage


  # ###################################################################################################################
  # ###################################################################################################################

  # Fresh timing: a recompute loads the schedule AFTER the run assigned the batch, so the loaded value IS the batch,
  # and copies the order's empty batch over it. The run's own batch assignment flags the order's schedules for exactly
  # such a recompute (HeaderAggregationKey changes -> M_ShipmentSchedule.invalidateSchedulesWithOldAndNewHeaderAggregationKey);
  # a bounded recompute pass (MaxToProcess, whole products in M_Product_ID order) can cover only some of them before the
  # shipment workpackage reads them. Here one schedule is recomputed, as such a partial pass does.
  @from:cucumber
  @Id:S33362_220
  Scenario: a recompute that loads a schedule after the batch was assigned does not split the run
    Given pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And complete picking job
    And there is 1 pending "GenerateInOutFromShipmentSchedules" workpackage
    When M_ShipmentSchedule shipmentSchedule2 is loaded now, recomputed from its order and saved
    And the held "GenerateInOutFromShipmentSchedules" workpackage is processed in one transaction

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment1             | CO            |



  # ###################################################################################################################
  # ###################################################################################################################

  # Fresh timing, but the recompute pass covers all three schedules before the shipment workpackage reads them:
  # no schedule carries the run's batch any more. The shipment still belongs to the run: it takes the batch from the workpackage.
  @from:cucumber
  @Id:S33468_240
  Scenario: after a recompute cleared the batch of all schedules of a run, the shipment still carries the run's batch
    Given pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And new "GenerateInOutFromShipmentSchedules" workpackages are held
    And new "UpdateInvalidShipmentSchedulesWorkpackageProcessor" workpackages are held
    And complete picking job
    And there is 1 pending "GenerateInOutFromShipmentSchedules" workpackage
    When M_ShipmentSchedule shipmentSchedule1 is loaded now, recomputed from its order and saved
    And M_ShipmentSchedule shipmentSchedule2 is loaded now, recomputed from its order and saved
    And M_ShipmentSchedule shipmentSchedule3 is loaded now, recomputed from its order and saved
    And the held "GenerateInOutFromShipmentSchedules" workpackage is processed in one transaction as shipmentWorkPackage

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
      | shipmentSchedule2                | shipment1             | CO            |
      | shipmentSchedule3                | shipment1             | CO            |
    And the M_InOut shipment1 carries the C_Async_Batch_ID of the C_Queue_WorkPackage shipmentWorkPackage



  # ###################################################################################################################
  # ###################################################################################################################

  # Batch cleanup after a partial run: run 1 ships part of line 1 and gives schedule 1 the run's batch.
  # The next schedule recompute copies the order's empty batch onto the schedule again.
  # Without that cleanup, run 2 groups schedule 1 (old batch) apart from schedules 2 and 3 (no batch): two shipments.
  @from:cucumber
  @Id:S33362_230
  Scenario: a later run of a partly shipped order is not split by the earlier run's batch
    Given set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy | IsAllowCompletingPartialPickingJob |
      | Y                   | CREATE_AND_COMPLETE  | Y                                  |
    And temporarily set sys config boolean value false for sys config 'M_ShipmentSchedule_Close_PartiallyShipped'
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 3         | 0           |                       | 2027-03-01     | 1111  |
    And complete picking job
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
    And the shipment-schedule recompute work queue drains to zero pending workpackages
    And after not more than 60s, M_ShipmentSchedule shipmentSchedule1 carries no C_Async_Batch_ID
    And after not more than 60s, M_ShipmentSchedule shipmentSchedule2 carries no C_Async_Batch_ID
    And after not more than 60s, M_ShipmentSchedule shipmentSchedule3 carries no C_Async_Batch_ID

    When start picking job for sales order identified by SO
    And scan picking slot identified by 210.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 3         | 0           |                       | 2027-03-01     | 1111  |
      | product2              | pickFromAggregatedLU2 | 5         | 0           |                       | 2027-03-01     | 2222  |
      | product3              | pickFromAggregatedLU3 | 6         | 0           |                       | 2027-03-01     | 3333  |
    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule2                | shipment2             | CO            |
      | shipmentSchedule3                | shipment2             | CO            |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus | OPT.IgnoreCreated.M_InOut_ID.Identifier |
      | shipmentSchedule1                | shipment2             | CO            | shipment1                               |
