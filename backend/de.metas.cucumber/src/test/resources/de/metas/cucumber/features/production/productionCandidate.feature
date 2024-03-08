@from:cucumber
@ghActions:run_on_executor6
Feature: Production dispo scenarios

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

  @from:cucumber
  Scenario: Try to re-open production candidate after it has been closed (S0129.2_140)
    Given metasfresh contains M_Products:
      | Identifier | Name                                |
      | p_1        | trackedProduct_04052022_1           |
      | p_2        | trackedProduct_component_04052022_1 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |

    And metasfresh contains PP_Order_Candidates
      | Identifier | M_Product_ID.Identifier | OPT.Processed | OPT.IsClosed | M_Warehouse_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | p_1                     |               |              | warehouseStd              | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z |                                          |
    And the following PP_Order_Candidates are closed
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |

    Then the following PP_Order_Candidates are closed and cannot be re-opened
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |

  @from:cucumber
  Scenario: Production candidate's QtyToProcess is greater than Qty-QtyProcessed (S0129.2_160)
    Given metasfresh contains M_Products:
      | Identifier | Name                                |
      | p_1        | trackedProduct_04052022_2           |
      | p_2        | trackedProduct_component_04052022_2 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |

    And metasfresh contains PP_Order_Candidates
      | Identifier | M_Product_ID.Identifier | OPT.Processed | OPT.IsClosed | M_Warehouse_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | p_1                     |               |              | warehouseStd              | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z |                                          |

    Then update PP_Order_Candidate's qty to process to more than left to be processed expecting exception
      | PP_Order_Candidate_ID.Identifier | QtyToProcess |
      | oc_1                             | 12           |

  @from:cucumber
  Scenario: Production candidate's QtyToProcess is greater than Qty-QtyProcessed after the production candidate has been previously processed (S0129.2_180)
    Given metasfresh contains M_Products:
      | Identifier | Name                                |
      | p_1        | trackedProduct_04052022_3           |
      | p_2        | trackedProduct_component_04052022_3 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |

    And metasfresh contains PP_Order_Candidates
      | Identifier | M_Product_ID.Identifier | OPT.Processed | OPT.IsClosed | M_Warehouse_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | p_1                     |               |              | warehouseStd              | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z |                                          |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    And the following PP_Order_Candidates are re-opened
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |

    Then update PP_Order_Candidate's qty to process to more than left to be processed expecting exception
      | PP_Order_Candidate_ID.Identifier | QtyToProcess |
      | oc_1                             | 2            |

  @from:cucumber
  Scenario: Production candidate's QtyEntered is lower than QtyProcessed after the production candidate has been previously processed (S0129.2_190)
    Given metasfresh contains M_Products:
      | Identifier | Name                                |
      | p_1        | trackedProduct_04052022_4           |
      | p_2        | trackedProduct_component_04052022_4 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |

    And metasfresh contains PP_Order_Candidates
      | Identifier | M_Product_ID.Identifier | OPT.Processed | OPT.IsClosed | M_Warehouse_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | p_1                     |               |              | warehouseStd              | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z |                                          |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |

    And the following PP_Order_Candidates are re-opened
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |

    Then update PP_Order_Candidate's qty entered to less than processed expecting exception
      | PP_Order_Candidate_ID.Identifier | QtyEntered |
      | oc_1                             | 2          |