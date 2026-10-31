@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@F00701
@ghActions:run_on_executor7
Feature: Invoice candidates of order lines that were never delivered - a closed shipment schedule processes them, a reopened one makes them invoiceable again
## F00701: Invoice Candidates
## Guards for the computed "delivery closed, nothing delivered" processed rule.

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
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID     |
      | line1              | pickFromCU1 |
      | line2              | pickFromCU2 |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: TC4 - line 2 is never delivered and its shipment schedule gets closed: its invoice candidate stays processed, line 1 is still invoiced
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
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
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 6         | 0           |                       | 2027-03-01     | 1111  |
    And complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule1     | true     |
      | shipmentSchedule2     | true     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed_Override |
      | ic1                               | false         | 6            | true                 | null                   |
      | ic2                               | true          | 0            | true                 | null                   |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic1                               | invoice                 |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.Processed_Override |
      | ic2                               | true          | 0            | null                   |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: TC6 - closing an undelivered shipment schedule processes its invoice candidate, reactivating it makes the candidate invoiceable again
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 6          | TUx1_P1                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed |
      | ic1                               | false         |

    When the M_ShipmentSchedule identified by shipmentSchedule1 is closed
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed_Override |
      | ic1                               | true          | 0            | true                 | null                   |

    When the M_ShipmentSchedule identified by shipmentSchedule1 is reactivated
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.IsDeliveryClosed |
      | ic1                               | false         | false                |
