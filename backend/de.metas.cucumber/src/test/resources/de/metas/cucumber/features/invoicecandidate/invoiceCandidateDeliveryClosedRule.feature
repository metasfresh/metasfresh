@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@F00701
@allure.label.feature:F00130_Shipment_Schedule
@F00130
@ghActions:run_on_executor7
Feature: Invoice candidates of order lines that were never delivered - a closed shipment schedule processes them, a reopened one makes them invoiceable again
## F00701: Invoice Candidates
## F00130: Shipment Schedule (Lieferdisposition)
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
  Scenario: line 2 is never delivered and its shipment schedule gets closed: its invoice candidate stays processed, line 1 is still invoiced
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
  Scenario: closing an undelivered shipment schedule processes its invoice candidate, reactivating it makes the candidate invoiceable again
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

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a candidate closed by hand stays closed when its line is delivered after the shipment schedule was closed and reactivated
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

    When the C_Invoice_Candidate identified by ic1 is closed by hand
    And the M_ShipmentSchedule identified by shipmentSchedule1 is closed
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.Processed_Override |
      | ic1                               | true          | 0            | Y                      |

    When the M_ShipmentSchedule identified by shipmentSchedule1 is reactivated
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.Processed_Override |
      | ic1                               | true          | Y                      |

    When shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment1             | shipmentSchedule1                | D                 | Y                  |
    Then validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine1             | shipment1             | product1                | 6           | true      |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.Processed_Override |
      | ic1                               | true          | 0            | Y                      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a hand close after the shipment schedule close wins over a later delivery
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

    When the M_ShipmentSchedule identified by shipmentSchedule1 is closed
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed_Override |
      | ic1                               | true          | 0            | true                 | null                   |

    When the C_Invoice_Candidate identified by ic1 is closed by hand
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.Processed_Override |
      | ic1                               | true          | Y                      |

    When the M_ShipmentSchedule identified by shipmentSchedule1 is reactivated
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment1             | shipmentSchedule1                | D                 | Y                  |
    Then validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine1             | shipment1             | product1                | 6           | true      |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.Processed_Override |
      | ic1                               | true          | 0            | Y                      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a flatrate "to clear" candidate stays closed when its line is delivered after the shipment schedule was closed and reactivated
    Given for this scenario only, set sys config boolean value true for sys config C_Invoice_Candidate_Close_IsToClear
    And metasfresh contains C_UOMs:
      | Identifier  | X12DE355 | Name     | UOMSymbol | StdPrecision | CostingPrecision |
      | flatfee_uom | MEAL     | Meal_7d_@Date@ | M         | 2            | 2                |
    And metasfresh contains M_Product_Categories:
      | Identifier     | Name           | Value          |
      | flatfee_cat    | flatfee_cat_7d_@Date@ | flatfee_cat_7d_@Date@ |
    And metasfresh contains M_Products:
      | Identifier   | Name         | ProductType | OPT.X12DE355 | OPT.M_Product_Category_ID.Identifier |
      | flatfee_prod | flatfee_prod_@Date@ | S           | MEAL         |                                      |
      | product3     | product3_@Date@ |             | PCE          | flatfee_cat                          |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | PLV                    | flatfee_prod | 1.0      | MEAL              | Normal                        |
      | PLV                    | product3     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory3                | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory3                | line3                         | product3                | 0       | 1000     | PCE          |
    And complete inventory with inventoryIdentifier 'inventory3'
    And metasfresh contains C_BPartners without locations:
      | Identifier   | Name          | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | flatCustomer | flatCustomer7d_@Date@ | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN        | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | flatCustomerLoc    | Dummy_GLN7_@Date@ | flatCustomer             | true                | true         |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name         | Type_Conditions | OPT.Type_Flatrate | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_UOM_ID.Identifier |
      | conditions | flatfee_7d_@Date@ | FlatFee         | RPTD              | flatfee_prod                         | flatfee_uom             |
    And metasfresh contains C_Flatrate_Matchings:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | SeqNo | M_Product_Category_Matching_ID.Identifier |
      | matching   | conditions                          | 10    | flatfee_cat                          |
    When metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | term       | conditions                          | flatCustomer                  | 2024-01-01 | 2024-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by term is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | flatCustomer             | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO                    | L1         | product3                | 4          |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.Processed_Override |
      | ic1                               | true          | Y                      |

    When the M_ShipmentSchedule identified by shipmentSchedule1 is closed
    And the M_ShipmentSchedule identified by shipmentSchedule1 is reactivated
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment1             | shipmentSchedule1                | D                 | Y                  |
    Then validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine1             | shipment1             | product3                | 4           | true      |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.Processed_Override |
      | ic1                               | true          | Y                      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: reactivating and re-completing the order reopens only the line its disposition close closed, not the hand-closed or the "to clear" line
    Given for this scenario only, set sys config boolean value true for sys config C_Invoice_Candidate_Close_IsToClear
    And metasfresh contains C_UOMs:
      | Identifier  | X12DE355 | Name               | UOMSymbol | StdPrecision | CostingPrecision |
      | flatfee_uom | MEAL     | Meal_7e_@Date@     | M         | 2            | 2                |
    And metasfresh contains M_Product_Categories:
      | Identifier  | Name                  | Value                 |
      | flatfee_cat | flatfee_cat_7e_@Date@ | flatfee_cat_7e_@Date@ |
    And metasfresh contains M_Products:
      | Identifier   | Name                | ProductType | OPT.X12DE355 | OPT.M_Product_Category_ID.Identifier |
      | flatfee_prod | flatfee_prod_7e_@Date@ | S           | MEAL         |                                      |
      | product3     | product3_7e_@Date@  |             | PCE          | flatfee_cat                          |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | PLV                    | flatfee_prod | 1.0      | MEAL              | Normal                        |
      | PLV                    | product3     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier   | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | flatCustomer | flatCustomer7e_@Date@ | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | GLN                | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | flatCustomerLoc | Dummy_GLN7e_@Date@ | flatCustomer             | true                | true         |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name              | Type_Conditions | OPT.Type_Flatrate | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_UOM_ID.Identifier |
      | conditions | flatfee_7e_@Date@ | FlatFee         | RPTD              | flatfee_prod                         | flatfee_uom             |
    And metasfresh contains C_Flatrate_Matchings:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | SeqNo | M_Product_Category_Matching_ID.Identifier |
      | matching   | conditions                          | 10    | flatfee_cat                          |
    When metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | term       | conditions                          | flatCustomer                | 2024-01-01 | 2024-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by term is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | flatCustomer             | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO                    | L1         | product1                | 6          |
      | SO                    | L2         | product3                | 4          |
      | SO                    | L3         | product2                | 5          |
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
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
      | ic2                               |
      | ic3                               |

    # L1 is closed by hand, L2 by the "to clear" rule, L3 only by closing its shipment schedule
    When the C_Invoice_Candidate identified by ic1 is closed by hand
    And the M_ShipmentSchedule identified by shipmentSchedule3 is closed
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.Processed_Override |
      | ic1                               | true          | Y                      |
      | ic2                               | true          | Y                      |
      | ic3                               | true          | null                   |

    When the order identified by SO is reactivated
    And the order identified by SO is completed
    Then after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
      | ic2                               |
      | ic3                               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.Processed_Override |
      | ic1                               | true          | Y                      |
      | ic2                               | true          | Y                      |
      | ic3                               | false         | null                   |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: a line closed by its own partial shipment is never processed, the delivered quantity stays invoiceable
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 12         | TUx1_P1                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |

    # hold the candidate recompute: the state stored by the shipment completion itself is then readable, before any recompute corrects it
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed |
      | ic1                               | false         |
    And new "UpdateInvalidInvoiceCandidatesWorkpackageProcessor" workpackages are held
    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 8         | 0           |                       | 2027-03-01     | 1111  |
    When complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule1     | true     |
    And validate C_Invoice_Candidate as stored right now, without waiting for its recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed |
      | ic1                               | false         |
    And held "UpdateInvalidInvoiceCandidatesWorkpackageProcessor" workpackages are released
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.Processed_Override |
      | ic1                               | false         | null                   |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice |
      | ic1                               | false         | 8            |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: an approval for invoicing survives the shipment completion that closes the line
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO                    | L1         | product1                | 12         | TUx1_P1                                |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier        | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule1 | L1                        | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic1                               | L1                        |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | ic1                               | true                     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.ApprovalForInvoicing |
      | ic1                               | false         | true                     |


    # hold the candidate recompute: the state stored by the shipment completion itself is then readable, before any recompute corrects it
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed |
      | ic1                               | false         |
    And new "UpdateInvalidInvoiceCandidatesWorkpackageProcessor" workpackages are held
    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 8         | 0           |                       | 2027-03-01     | 1111  |
    When complete picking job

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | shipmentSchedule1                | shipment1             | CO            |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsClosed |
      | shipmentSchedule1     | true     |
    And validate C_Invoice_Candidate as stored right now, without waiting for its recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.ApprovalForInvoicing |
      | ic1                               | false         | true                     |
    And held "UpdateInvalidInvoiceCandidatesWorkpackageProcessor" workpackages are released
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.ApprovalForInvoicing | OPT.Processed_Override |
      | ic1                               | false         | true                     | null                   |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.ApprovalForInvoicing | QtyToInvoice |
      | ic1                               | false         | true                     | 8            |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: an order line of a reactivated order whose shipment schedule was closed can be deleted with its invoice candidate
    # L2 is the line with the highest id of the run and stays: the deleted L1 must not be the last record the run created, else the next run reuses its id
    Given metasfresh contains C_Orders:
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

    When the order identified by SO is reactivated
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.IsDeliveryClosed |
      | ic1                               | true          | 0            | true                 |
      | ic2                               | true          | 0            | true                 |

    When delete C_OrderLine identified by L1, but keep its id into identifierIds table
    Then validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | SO                    | product1                |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed |
      | ic2                               | true          |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: closing the shipment schedules of four undelivered lines processes only the candidate whose quantity to invoice comes from the delivery
    # Immediate (ordered qty), After pick (picked qty) and a service product (ordered qty) are not invoiced by the delivered quantity,
    # so a closed disposition does not make them uninvoiceable. After delivery on a stock item is.
    # A service product gets no shipment schedule at all (only items do), so its line has no schedule to close: it is checked to stay untouched.
    Given for this scenario only, set sys config boolean value true for sys config M_ShipmentSchedule_Close_PartiallyShipped
    And metasfresh contains M_Products:
      | Identifier | Name                   | ProductType | OPT.X12DE355 |
      | service1   | service1_16_@Date@     | S           | PCE          |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | service1     | 5.0      | PCE               | Nominal              | Normal                        |
    And set mobile UI picking profile
      | CreateShipmentPolicy |
      | CREATE_DRAFT         |
    # the order takes its invoice rule from the business partner
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customerI  | customerI16_@Date@   | N            | Y              | PS                            | I               |
      | customerP  | customerP16_@Date@   | N            | Y              | PS                            | P               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN                  | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | locationI   | Dummy_GLN16I_@Date@  | customerI                | true                | true         |
      | locationP   | Dummy_GLN16P_@Date@  | customerP                | true                | true         |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO_I       | true    | customerI                | 2024-03-26  |
      | SO_P       | true    | customerP                | 2024-03-26  |
      | SO_D       | true    | customer                 | 2024-03-26  |
      | SO_S       | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | SO_I                  | L_I        | product1                | 6          | TUx1_P1                                |
      | SO_P                  | L_P        | product1                | 6          | TUx1_P1                                |
      | SO_P                  | L_P2       | product2                | 5          | TUx1_P2                                |
      | SO_D                  | L_D        | product2                | 5          | TUx1_P2                                |
      | SO_S                  | L_S        | service1                | 4          |                                        |
    And the order identified by SO_I is completed
    And the order identified by SO_P is completed
    And the order identified by SO_D is completed
    And the order identified by SO_S is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_I | L_I                       | N             |
      | schedule_P | L_P                       | N             |
      | schedule_P2 | L_P2                     | N             |
      | schedule_D | L_D                       | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_I                              | L_I                       |
      | ic_P                              | L_P                       |
      | ic_D                              | L_D                       |
      | ic_S                              | L_S                       |

    # the After pick line: 2 of 6 picked, the shipment is only drafted, so the picked quantity is not delivered
    And transform CU to new LU
      | sourceCU    | newLU                 | TU_PI_ID | QtyCUsPerTU | QtyTUsPerLU |
      | pickFromCU1 | pickFromAggregatedLU1 | TU       | 1           | 10          |
    And start picking job for sales order identified by SO_P
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    And pick lines
      | PickingLine.byProduct | PickFromHU            | QtyPicked | QtyRejected | QtyRejectedReasonCode | BestBeforeDate | LotNo |
      | product1              | pickFromAggregatedLU1 | 2         | 0           |                       | 2027-03-01     | 1111  |
    And complete picking job
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | QtyPickList | OPT.IsClosed |
      | schedule_P                       | 2           | false        |
    And after not more than 120s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | ic_P                              |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | InvoiceRule | OPT.Processed | QtyToInvoice |
      | ic_I                              | I           | false         | 6            |
      | ic_P                              | P           | false         | 2            |
      | ic_D                              | D           | false         | 0            |
      | ic_S                              | D           | false         | 4            |

    When the M_ShipmentSchedule identified by schedule_I is closed
    # the After pick schedule is closed the way production closes a picked, unshipped schedule (the user Close process refuses it, see
    # closeWithUnfinishedPicking.feature): the completion of the shipment of the order's other line closes the siblings that are not fully
    # delivered (ShipmentScheduleBL.closePartiallyShipped_ShipmentSchedules, sysconfig M_ShipmentSchedule_Close_PartiallyShipped)
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_P2           | schedule_P2                      | D                 | Y                  |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.IsClosed |
      | schedule_P                       | true         |
    And the M_ShipmentSchedule identified by schedule_D is closed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed_Override |
      | ic_I                              | false         | 6            | true                 | null                   |
      | ic_P                              | false         | 2            | true                 | null                   |
      | ic_D                              | true          | 0            | true                 | null                   |
      | ic_S                              | false         | 4            | false                | null                   |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  Scenario: an After delivery candidate whose invoice rule override is Immediate keeps its quantity to invoice when its shipment schedule is closed
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
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic1                               | I                        |
    And after not more than 120s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | ic1                               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice |
      | ic1                               | false         | 6            |

    When the M_ShipmentSchedule identified by shipmentSchedule1 is closed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed_Override |
      | ic1                               | false         | 6            | true                 | null                   |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | InvoiceRule_Override |
      | ic1                               | I                    |
