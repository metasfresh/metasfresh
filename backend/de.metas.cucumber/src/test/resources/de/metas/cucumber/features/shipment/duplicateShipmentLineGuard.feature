@from:cucumber
@ghActions:run_on_executor5
Feature: duplicate shipment line guard prevents duplicate generation from race condition

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And metasfresh has date and time 2021-12-16T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: Normal shipment generation is not blocked by the guard
    Given metasfresh contains M_Products:
      | Identifier | Name                   |
      | p_1        | dup_guard_normal_prod  |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                  | Value                 | OPT.IsActive |
      | ps_1       | dup_guard_normal_ps   | dup_guard_normal_ps   | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | dup_guard_normal_pl   | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | dup_guard_normal_plv   | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_A | dup_guard_normal_cust  | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_A      | 0123456789101 | endcustomer_A            | Y                   | Y                   |

    # Create stock via inventory
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2021-04-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | p_1                     | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | hu_1               |

    # Create and complete sales order
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference       |
      | o_A        | true    | endcustomer_A            | 2021-04-17  | po_ref_dup_guard_norm |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | o_A                   | p_1                     | 10         |
    When the order identified by o_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |

    # Generate shipment normally (complete)
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_A                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_A                            | ship_A                | CO            |

    # Validate: QtyPicked is Processed=Y (completed shipment), guard did not interfere
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by s_s_A
      | QtyPicked | Processed |
      | 10        | Y         |
    And validate the created shipment lines
      | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | ship_A                | p_1                     | 10          | true      |

  @from:cucumber
  Scenario: Second shipment generation is skipped when draft allocation exists
    # Simulates the race condition: first WP creates a draft shipment (IsCompleteShipments=false),
    # leaving QtyPicked with Processed=N and M_InOutLine_ID set. The guard should detect this
    # and skip the schedule when the second WP processes it.
    Given metasfresh contains M_Products:
      | Identifier | Name                  |
      | p_1        | dup_guard_block_prod  |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                 | Value                | OPT.IsActive |
      | ps_1       | dup_guard_block_ps   | dup_guard_block_ps   | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | dup_guard_block_pl   | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                  | ValidFrom  |
      | plv_1      | pl_1                      | dup_guard_block_plv   | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_A | dup_guard_block_cust  | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_A      | 0123456789102 | endcustomer_A            | Y                   | Y                   |

    # Create stock via inventory
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2021-04-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | p_1                     | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | hu_1               |

    # Create and complete sales order
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference        |
      | o_A        | true    | endcustomer_A            | 2021-04-17  | po_ref_dup_guard_block |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | o_A                   | p_1                     | 10         |
    When the order identified by o_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |

    # First generation: do NOT complete the shipment (leaves draft allocation)
    # This creates M_ShipmentSchedule_QtyPicked with Processed=N and M_InOutLine_ID set
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_A                            | D            | false               | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_A                            | ship_A                | DR            |

    # Validate: exactly 1 QtyPicked record (Processed=N because shipment is draft)
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by s_s_A
      | QtyPicked | Processed |
      | 10        | N         |

    # Second generation attempt (same schedule) — guard should block this
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_A                            | D            | true                | false       |

    # Poll: find the SAME M_InOut from the first generation (no new one created).
    # If a duplicate were created, this step would throw "More than one M_InOut found".
    # We reuse identifier ship_A because it's the same record — using a different identifier
    # would fail with "already mapped to ship_A".
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_A                            | ship_A                | DR            |

    # Validate: still exactly 1 QtyPicked record (guard prevented duplicate)
    # If the guard failed, there would be 2 records and this step would fail on count mismatch.
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by s_s_A
      | QtyPicked | Processed |
      | 10        | N         |

  @from:cucumber
  Scenario: Completed partial delivery does not block next shipment generation
    # After a completed partial delivery, QtyPicked records have Processed=Y.
    # The guard only blocks Processed=N records, so the next generation should proceed normally.
    Given metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | dup_guard_partial_prod  |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                  | OPT.IsActive |
      | ps_1       | dup_guard_partial_ps   | dup_guard_partial_ps   | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                   | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | dup_guard_partial_pl   | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                    | ValidFrom  |
      | plv_1      | pl_1                      | dup_guard_partial_plv   | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_A | dup_guard_partial_cust  | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | loc_A      | 0123456789103 | endcustomer_A            | Y                   | Y                   |

    # Create stock via inventory
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2021-04-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | p_1                     | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | hu_1               |

    # Create order for qty=10
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference          |
      | o_A        | true    | endcustomer_A            | 2021-04-17  | po_ref_dup_guard_partial |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_A       | o_A                   | p_1                     | 10         |
    When the order identified by o_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |

    # First generation: partial delivery of 5 with completion
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | s_s_A                            | D            | true                | false       | 5                                               |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_A                            | ship_A                | CO            |
    And validate the created shipment lines
      | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | ship_A                | p_1                     | 5           | true      |

    # Wait for schedule recompute (QtyToDeliver should reflect the 5 remaining)
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_A      | ol_A                      | N             |

    # Second generation: remaining qty — guard does NOT fire (prior QtyPicked is Processed=Y)
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_A                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus | OPT.IgnoreCreated.M_InOut_ID.Identifier |
      | s_s_A                            | ship_B                | CO            | ship_A                                  |
    And validate the created shipment lines
      | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | ship_B                | p_1                     | 5           | true      |
