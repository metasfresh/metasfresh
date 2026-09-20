@from:cucumber
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5020
@ghActions:run_on_executor7
@Id:S24821_TC1
Feature: MD_Stock is re-keyed when a storage-relevant HU attribute changes
  ## F5020: Stock
  Proves the full chain:
  storage-relevant attribute change on a VHU
    -> AttributesChangedEvent fired
    -> AttributesChangedEventHandlerForStockRecords re-keys MD_Stock
       from the old AttributesKey to the new.

  # Uses the standard, storage-relevant "Article_Flavor" attribute, which is part
  # of the default HU attribute set. Only an attribute the VHU actually carries can
  # be changed via the HU attribute path, and that change is what fires the
  # AttributesChangedEvent. (A custom attribute assigned only to the product's
  # M_AttributeSet does NOT reach the VHU's attribute storage, so it cannot be used.)

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_AttributeSetInstance with identifier "asiA":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"Article_Flavor",
          "valueStr":"A"
        }
      ]
    }
    """

    And metasfresh contains M_AttributeSetInstance with identifier "asiB":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"Article_Flavor",
          "valueStr":"B"
        }
      ]
    }
    """

    # No fixed Value/Name: each scenario gets a distinct product so MD_Stock rows
    # (keyed by product) never leak between scenarios sharing the executor's DB.
    And metasfresh contains M_Products:
      | Identifier   |
      | rekeyProduct |

  Scenario: Whole-VHU attribute change re-keys MD_Stock
    # Setup: one inventory line with attribute A, qty 10 -> VHU gets attribute A
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate |
      | inv1                      | warehouseStd   | 2026-06-28   |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.M_AttributeSetInstance_ID.Identifier |
      | invLine1                      | inv1                      | rekeyProduct            | PCE          | 10       | 0       | asiA                                     |
    When the inventory identified by inv1 is completed

    Then after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine1                      | vhu1               |

    # Assert initial MD_Stock under key A
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | rekeyProduct            | 10        | warehouseStd                  | asiA                                     |

    # Act: change attribute on VHU from A to B
    And M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | OPT.Value |
      | vhu1               | Article_Flavor       | B         |

    # Assert: old key A -> 0, new key B -> 10 (total 10 preserved)
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | rekeyProduct            | 0         | warehouseStd                  | asiA                                     |
      | rekeyProduct            | 10        | warehouseStd                  | asiB                                     |

  Scenario: Partial attribute change - only changed VHU qty moves, remainder stays on old key
    # Setup: TWO inventory lines with attribute A, 10 PCE each -> two VHUs, both key A, total 20
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate |
      | inv2                      | warehouseStd   | 2026-06-28   |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.M_AttributeSetInstance_ID.Identifier |
      | invLine2a                     | inv2                      | rekeyProduct            | PCE          | 10       | 0       | asiA                                     |
      | invLine2b                     | inv2                      | rekeyProduct            | PCE          | 10       | 0       | asiA                                     |
    When the inventory identified by inv2 is completed

    Then after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine2a                     | vhu2a              |
      | invLine2b                     | vhu2b              |

    # Assert initial MD_Stock: key A = 20 (two VHUs combined)
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | rekeyProduct            | 20        | warehouseStd                  | asiA                                     |

    # Act: change attribute on ONE of the two VHUs from A to B
    And M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | OPT.Value |
      | vhu2a              | Article_Flavor       | B         |

    # Assert: key A = 10 (unchanged VHU remains), key B = 10 (changed VHU)
    # This proves the handler moves only the changed VHU's qty, not the whole key.
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | rekeyProduct            | 10        | warehouseStd                  | asiA                                     |
      | rekeyProduct            | 10        | warehouseStd                  | asiB                                     |

  @Id:S30640_TC1
  Scenario: HU attribute change creates BOTH the ATTRIBUTES_CHANGED_FROM and _TO MD_Candidates
    # The same storage-attribute change that re-keys MD_Stock (scenarios above) also fires an AttributesChangedEvent
    # that the material-dispo AttributesChangedEventHandler turns into a pair of MD_Candidate records:
    #   - ATTRIBUTES_CHANGED_FROM: a negative-delta (decrease) candidate on the OLD attributes key (A)
    #   - ATTRIBUTES_CHANGED_TO:   a positive-delta (increase) candidate on the NEW attributes key (B)
    # The FROM candidate is created first. A negative delta must only enter DemandCandiateHandler's
    # "supply-required-decreased" branch for a DEMAND candidate; SupplyRequiredEventCreator.verifyCandidateType
    # accepts only DEMAND/STOCK_UP and rejects an ATTRIBUTES_CHANGED_FROM candidate with an IllegalArgumentException.
    # If that guard is missing, the throw (though swallowed) aborts the handler AFTER the FROM was persisted and
    # BEFORE the TO was created, leaving a half-applied attributes change (FROM present, TO missing).
    # This scenario asserts BOTH candidates exist, i.e. the attributes change is fully applied.
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | M_Warehouse_ID | MovementDate |
      | inv1                      | warehouseStd   | 2026-06-28   |
    And metasfresh contains M_InventoriesLines:
      | M_InventoryLine_ID.Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook | OPT.M_AttributeSetInstance_ID.Identifier |
      | invLine1                      | inv1                      | rekeyProduct            | PCE          | 10       | 0       | asiA                                     |
    When the inventory identified by inv1 is completed

    Then after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine1                      | vhu1               |

    # Assert initial MD_Stock under key A (setup sanity check, same as the re-key scenarios)
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | rekeyProduct            | 10        | warehouseStd                  | asiA                                     |

    # Act: change attribute on the VHU from A to B
    And M_HU_Attribute is changed
      | M_HU_ID.Identifier | M_Attribute_ID.Value | OPT.Value |
      | vhu1               | Article_Flavor       | B         |

    # Assert: the attributes change is FULLY applied -> BOTH candidates exist
    # (FROM decrease on old key A, TO increase on new key B). Without the guard the TO candidate is missing
    # (the handler aborted after persisting FROM), so this step fails.
    Then after not more than 60s, MD_Candidate records exist for product
      | M_Product_ID.Identifier | MD_Candidate_Type       | Qty | OPT.M_AttributeSetInstance_ID.Identifier |
      | rekeyProduct            | ATTRIBUTES_CHANGED_FROM | 10  | asiA                                     |
      | rekeyProduct            | ATTRIBUTES_CHANGED_TO   | 10  | asiB                                     |

    # And no swallowed exception was logged: without the guard, DemandCandiateHandler would pass the
    # ATTRIBUTES_CHANGED_FROM candidate to SupplyRequiredEventCreator.verifyCandidateType and log an
    # IllegalArgumentException to AD_Issue.
    And there is no AD_Issue whose stack trace references product
      | M_Product_ID.Identifier |
      | rekeyProduct            |
