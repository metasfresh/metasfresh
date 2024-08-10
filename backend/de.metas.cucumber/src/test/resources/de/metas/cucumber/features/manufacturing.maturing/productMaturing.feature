@from:cucumber
@ghActions:run_on_executor6
Feature: Maturing scenarios

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-01T16:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_Products:
      | Identifier  |
      | maturedGood |
      | rawGood     |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID |
      | bom_1      | maturedGood  | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | boml_1     | bom_1             | rawGood      | 1        |
    And the PP_Product_BOM identified by bom_1 is completed
    And load M_Warehouse:
      | M_Warehouse_ID    | Value        |
      | maturingWarehouse | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID      | M_Warehouse_ID    | Value      |
      | locatorHauptlager | maturingWarehouse | Hauptlager |
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |
    And metasfresh contains M_Maturing_Configurations
      | Identifier     | Name           |
      | maturingConfig | maturingConfig |
    And metasfresh contains M_Maturing_Configuration_Lines
      | Identifier         | M_Maturing_Configuration_ID | From_Product_ID | Matured_Product_ID | MaturityAge |
      | maturingConfigLine | maturingConfig              | rawGood         | maturedGood        | 5           |
    And metasfresh contains PP_Product_Plannings
      | Identifier   | M_Product_ID | IsCreatePlan | M_Warehouse_ID    | IsMatured | M_Maturing_Configuration_ID | M_Maturing_Configuration_Line_ID | PP_Product_BOMVersions_ID |
      | prodPlanning | maturedGood  | false        | maturingWarehouse | true      | maturingConfig              | maturingConfigLine               | bomVersions_1             |

  @from:cucumber
  @Id:S0382_100
  Scenario: Happy flow, raw good product HU created via inventory, maturing candidate created and processed
    When metasfresh initially has M_Inventory data
      | M_Inventory_ID | MovementDate | DocumentNo   |
      | maturingInv    | 2024-01-01   | maturingInv1 |

    And metasfresh contains M_AttributeSetInstance with identifier "huASI_10":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"ProductionDate",
          "valueStr":"2023-01-01"
      }
    ]
  }
  """

    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | M_AttributeSetInstance_ID |
      | maturingInv    | maturing_inv_10    | rawGood      | 0       | 10       | huASI_10                  |
    And complete inventory with inventoryIdentifier 'maturingInv'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID       |
      | maturing_inv_10    | rawgood_hu_10 |
    And validate M_HU_Storage:
      | M_HU_Storage_ID | M_HU_ID       | M_Product_ID | Qty |
      | maturing_hus_10 | rawgood_hu_10 | rawGood      | 10  |

    And AD_Scheduler for classname 'org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates' is ran once

    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | IsMaturing | M_Maturing_Configuration_ID | M_Maturing_Configuration_Line_ID | Issue_HU_ID   |
      | oc_1       | false     | maturedGood  | bom_1             | prodPlanning           | 540006        | 10 PCE     | 10 PCE       | 0 PCE        | 2023-05-31T22:00:00Z | 2023-05-31T22:00:00Z | false    | true       | maturingConfig              | maturingConfigLine               | rawgood_hu_10 |

    And AD_Scheduler for classname 'org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering' is ran once

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | IsMaturing | M_Maturing_Configuration_ID | M_Maturing_Configuration_Line_ID | Issue_HU_ID   |
      | oc_1       | true      | maturedGood  | bom_1             | prodPlanning           | 540006        | 10 PCE     | 0 PCE        | 10 PCE       | 2023-05-31T22:00:00Z | 2023-05-31T22:00:00Z | true     | true       | maturingConfig              | maturingConfigLine               | rawgood_hu_10 |

    And after not more than 60s, load PP_Order by candidate id: oc_1
      | PP_Order_ID | QtyEntered |
      | ppOrder_1   | 10         |

    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyOrdered | DatePromised         | DocStatus |
      | ppOrder_1  | maturedGood  | bom_1             | prodPlanning           | 540006        | 10 PCE     | 10         | 2023-05-31T22:00:00Z | CL        |

    And load manufactured HU for PP_Order:
      | PP_Order_ID | M_HU_ID           |
      | ppOrder_1   | maturedGood_hu_10 |

    And validate M_HUs:
      | M_HU_ID           | HUStatus | M_Locator_ID      |
      | maturedGood_hu_10 | A        | locatorHauptlager |
      | rawgood_hu_10     | D        | locatorHauptlager |

    And validate M_HU_Storage:
      | M_HU_Storage_ID    | M_HU_ID           | M_Product_ID | Qty |
      | maturedGood_hus_10 | maturedGood_hu_10 | maturedGood  | 10  |
      | maturing_hus_10    | rawgood_hu_10     | rawGood      | 0   |


  @from:cucumber
  @Id:S0382_200
  Scenario: Maturing candidate created, then HU qty is adjusted. Maturing candidate is updated
    When metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate | DocumentNo   |
      | maturingInv2              | 2024-01-01   | maturingInv2 |

    And metasfresh contains M_AttributeSetInstance with identifier "huASI_20":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"ProductionDate",
          "valueStr":"2023-02-01"
      }
    ]
  }
  """

    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | OPT.M_AttributeSetInstance_ID.Identifier |
      | maturingInv2              | maturing_inv_20               | rawGood                 | 0       | 20       | huASI_20                                 |
    And complete inventory with inventoryIdentifier 'maturingInv2'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | maturing_inv_20               | rawgood_hu_20      |
    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | rawgood_hus_20             | rawgood_hu_20      | rawGood                 | 20  |

    And AD_Scheduler for classname 'org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates' is ran once

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | IsMaturing | M_Maturing_Configuration_ID | M_Maturing_Configuration_Line_ID | Issue_HU_ID   |
      | oc_2       | false     | maturedGood  | bom_1             | prodPlanning           | 540006        | 20 PCE     | 20 PCE       | 0 PCE        | 2023-06-30T21:00:00Z | 2023-06-30T21:00:00Z | false    | true       | maturingConfig              | maturingConfigLine               | rawgood_hu_20 |

    And update M_HU_Storage:
      | M_HU_Storage_ID | Qty |
      | rawgood_hus_20  | 15  |

    And AD_Scheduler for classname 'org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates' is ran once

    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | IsMaturing | M_Maturing_Configuration_ID | M_Maturing_Configuration_Line_ID | Issue_HU_ID   |
      | oc_2       | false     | maturedGood  | bom_1             | prodPlanning           | 540006        | 15 PCE     | 15 PCE       | 0 PCE        | 2023-06-30T21:00:00Z | 2023-06-30T21:00:00Z | false    | true       | maturingConfig              | maturingConfigLine               | rawgood_hu_20 |


  @from:cucumber
  @Id:S0382_300
  Scenario: Maturing candidate created, then HU is disposed. Maturing candidate is deleted.
    When metasfresh initially has M_Inventory data
      | M_Inventory_ID | MovementDate | DocumentNo   |
      | maturingInv3   | 2024-01-01   | maturingInv3 |

    And metasfresh contains M_AttributeSetInstance with identifier "huASI_30":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"ProductionDate",
          "valueStr":"2023-02-01"
      }
    ]
  }
  """

    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID | M_InventoryLine_ID | M_Product_ID | QtyBook | QtyCount | M_AttributeSetInstance_ID |
      | maturingInv3   | maturing_inv_30    | rawGood      | 0       | 30       | huASI_30                  |
    And complete inventory with inventoryIdentifier 'maturingInv3'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID       |
      | maturing_inv_30    | rawgood_hu_30 |
    And validate M_HU_Storage:
      | M_HU_Storage_ID | M_HU_ID       | M_Product_ID | Qty |
      | rawgood_hus_30  | rawgood_hu_30 | rawGood      | 30  |

    And AD_Scheduler for classname 'org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates' is ran once

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | IsClosed | IsMaturing | M_Maturing_Configuration_ID | M_Maturing_Configuration_Line_ID | Issue_HU_ID   |
      | oc_3       | false     | maturedGood  | bom_1             | prodPlanning           | 540006        | 30 PCE     | 30 PCE       | 0 PCE        | 2023-06-30T21:00:00Z | 2023-06-30T21:00:00Z | false    | true       | maturingConfig              | maturingConfigLine               | rawgood_hu_30 |

    And M_HU are disposed:
      | M_HU_ID       | MovementDate         |
      | rawgood_hu_30 | 2024-01-01T21:00:00Z |

    And AD_Scheduler for classname 'org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates' is ran once

    Then after not more than 60s, no PP_Order_Candidates are found for Issue_HU_ID: 'rawgood_hu_30'
