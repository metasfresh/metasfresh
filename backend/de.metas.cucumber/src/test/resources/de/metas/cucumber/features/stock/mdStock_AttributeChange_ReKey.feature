@from:cucumber
@allure.label.epic:E0350_Warehouse_Managment
@allure.label.feature:F5020
@ghActions:run_on_executor7
Feature: MD_Stock is re-keyed when a storage-relevant HU attribute changes
  ## F5020: Stock
  Proves the full chain:
  storage-relevant attribute change on a VHU
    -> AttributesChangedEvent fired
    -> AttributesChangedEventHandlerForStockRecords re-keys MD_Stock
       from the old AttributesKey to the new.

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Attributes:
      | Identifier | Value                     | Name                          | OPT.AttributeValueType | OPT.IsStorageRelevant |
      | attr_rekey | stock_attr_rekey_20260628 | Stock Attr ReKey Test 20260628 | L                      | Y                     |

    And metasfresh contains M_AttributeValues:
      | Identifier | M_Attribute_ID.Identifier | Value | Name    | IsNullFieldValue |
      | attrVal_A  | attr_rekey                | A     | Value-A | N                |
      | attrVal_B  | attr_rekey                | B     | Value-B | N                |

    And metasfresh contains M_AttributeSetInstance with identifier "asiA":
    """
    {
      "attributeInstances":[
        {
          "attributeCode":"stock_attr_rekey_20260628",
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
          "attributeCode":"stock_attr_rekey_20260628",
          "valueStr":"B"
        }
      ]
    }
    """

    And metasfresh contains M_Products:
      | Identifier     | Value                        | Name                         |
      | rekeyProduct   | rekey_product_test_20260628  | Rekey Product Test 20260628  |

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
      | M_HU_ID.Identifier | M_Attribute_ID.Value              | OPT.Value |
      | vhu1               | stock_attr_rekey_20260628         | B         |

    # Assert: old key A -> 0, new key B -> 10
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
      | M_HU_ID.Identifier | M_Attribute_ID.Value              | OPT.Value |
      | vhu2a              | stock_attr_rekey_20260628         | B         |

    # Assert: key A = 10 (unchanged VHU remains), key B = 10 (changed VHU)
    # This proves the handler moves only the changed VHU's qty, not the whole key.
    Then after not more than 60 seconds metasfresh has MD_Stock data
      | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | rekeyProduct            | 10        | warehouseStd                  | asiA                                     |
      | rekeyProduct            | 10        | warehouseStd                  | asiB                                     |
