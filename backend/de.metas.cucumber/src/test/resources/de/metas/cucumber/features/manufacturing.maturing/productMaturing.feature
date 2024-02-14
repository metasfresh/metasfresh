@from:cucumber
@ghActions:run_on_executor6
Feature: Maturing scenarios

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-01T16:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_Products:
      | Identifier   | Value        | Name         |
      | finishedGood | productValue | finishedGood |
      | rawGood      | rawGood      | rawGood      |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | finishedGood            | 2022-05-09 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | rawGood                 | 2022-05-09 | 1        |
    And the PP_Product_BOM identified by bom_1 is completed
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | maturingWarehouse         | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | maturingWarehouse         | Hauptlager |
    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |
    And metasfresh contains M_Maturing_Configurations
      | M_Maturing_Configuration_ID.Identifier | Name           |
      | maturingConfig                         | maturingConfig |
    And metasfresh contains M_Maturing_Configuration_Lines
      | M_Maturing_Configuration_Line_ID.Identifier | M_Maturing_Configuration_ID.Identifier | From_Product_ID.Identifier | Matured_Product_ID.Identifier | MaturityAge |
      | maturingConfigLine                          | maturingConfig                         | rawGood                    | finishedGood                  | 5           |
    And metasfresh contains PP_Product_Plannings
      | Identifier   | M_Product_ID.Identifier | IsCreatePlan | OPT.M_Warehouse_ID.Identifier | OPT.IsMatured | OPT.M_Maturing_Configuration_ID.Identifier | OPT.M_Maturing_Configuration_Line_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier |
      | prodPlanning | finishedGood            | false        | maturingWarehouse             | true          | maturingConfig                             | maturingConfigLine                              | bomVersions_1                            |

  @from:cucumber
  Scenario: Happy flow, raw good product HU exists, maturing candidate created and processed
    When metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate | DocumentNo   |
      | maturingInv               | 2024-01-01   | maturingInv1 |

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
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | OPT.M_AttributeSetInstance_ID.Identifier |
      | maturingInv               | maturing_inv_10               | rawGood                 | 0       | 10       | huASI_10                                 |
    And complete inventory with inventoryIdentifier 'maturingInv'

    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | maturing_inv_10               | maturing_hu_10     |

    And AD_Scheduler for classname 'org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates' is ran once

    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.isMatured | OPT.M_Maturing_Configuration_ID.Identifier | OPT.M_Maturing_Configuration_Line_ID.Identifier | OPT.Issue_HU_ID.Identifier |
      | oc_1       | false     | finishedGood            | bom_1                        | prodPlanning                      | 540006        | 10         | 10           | 0            | PCE               | 2023-05-31T22:00:00Z | 2023-05-31T22:00:00Z | false    | true          | maturingConfig                             | maturingConfigLine                              | maturing_hu_10             |

    And AD_Scheduler for classname 'org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering' is ran once

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.isMatured | OPT.M_Maturing_Configuration_ID.Identifier | OPT.M_Maturing_Configuration_Line_ID.Identifier | OPT.Issue_HU_ID.Identifier |
      | oc_1       | true      | finishedGood            | bom_1                        | prodPlanning                      | 540006        | 10         | 0            | 10           | PCE               | 2023-05-31T22:00:00Z | 2023-05-31T22:00:00Z | true     | true          | maturingConfig                             | maturingConfigLine                              | maturing_hu_10             |

    And after not more than 60s, load PP_Order by candidate id: oc_1
      | PP_Order_ID.Identifier | QtyEntered |
      | ppOrder_1              | 10         |

    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | DatePromised         | OPT.DocStatus |
      | ppOrder_1  | finishedGood            | bom_1                        | prodPlanning                      | 540006        | 10         | 10         | PCE               | 2023-05-31T22:00:00Z | CL            |

    And load manufactured HU for PP_Order:
      | PP_Order_ID.Identifier | M_HU_ID.Identifier |
      | ppOrder_1              | finishedGood_hu_10 |

    And validate M_HUs:
      | M_HU_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier |
      | finishedGood_hu_10 | A        | locatorHauptlager           |
      | maturing_hu_10     | D        | locatorHauptlager           |

    And validate M_HU_Storage:
      | M_HU_Storage_ID.Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | finishedGood_hus_10        | finishedGood_hu_10 | finishedGood            | 10  |
      | maturing_hus_10            | maturing_hu_10     | rawGood                 | 0   |