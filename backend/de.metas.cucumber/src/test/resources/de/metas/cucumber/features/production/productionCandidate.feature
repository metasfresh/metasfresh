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
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_2        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_1 is completed

  @Id:S0129.2_140
  @from:cucumber
  Scenario: Try to re-open production candidate after it has been closed (S0129.2_140)
    When metasfresh contains PP_Product_Plannings
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

  @Id:S0129.2_160
  @from:cucumber
  Scenario: Production candidate's QtyToProcess is greater than Qty-QtyProcessed (S0129.2_160)
    When metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |

    And metasfresh contains PP_Order_Candidates
      | Identifier | M_Product_ID.Identifier | OPT.Processed | OPT.IsClosed | M_Warehouse_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | p_1                     |               |              | warehouseStd              | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z |                                          |

    Then update PP_Order_Candidate's qty to process to more than left to be processed expecting exception
      | PP_Order_Candidate_ID.Identifier | QtyToProcess |
      | oc_1                             | 12           |

  @Id:S0129.2_180
  @from:cucumber
  Scenario: Production candidate's QtyToProcess is greater than Qty-QtyProcessed after the production candidate has been previously processed (S0129.2_180)
    When metasfresh contains PP_Product_Plannings
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

  @Id:S0129.2_190
  @from:cucumber
  Scenario: Production candidate's QtyEntered is lower than QtyProcessed after the production candidate has been previously processed (S0129.2_190)
    When metasfresh contains PP_Product_Plannings
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

  @Id:S0129.2_190
  @from:cucumber
  Scenario: Reactivate and reduce QTY for order with negative ATP. Ensure correct qty is used on new PP_Order_Candidate.
    When metasfresh has date and time 2025-02-25T08:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_1                  | p_1          | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID |
      | location_1 | bPLocation_1 | customer      |
    #past order
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1        | true    | customer      | 2024-08-01  | 2024-08-01T21:00:00Z | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_1          | 200        |
    And the order identified by o_1 is completed
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP  | M_Warehouse_ID |
      | 01/d_1_1   | DEMAND            | SHIPMENT                  | p_1          | 2024-08-01T21:00:00Z | 200 | -200 | warehouseStd   |

    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_2        | true    | customer      | 2024-09-20  | 2024-08-22T21:00:00Z | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | o_2        | p_1          | 50         |
    And the order identified by o_2 is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed |
      | oc_1       | p_1          | bom_1             | ppln_1                 | 540006        | 250 PCE    | 250 PCE      | 0 PCE        | 2025-02-25T06:00:00Z | 2025-02-25T06:00:00Z | false        | false         |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP   | M_Warehouse_ID | PP_Order_Candidate_ID |
      | 01/d_1_1   | DEMAND            | SHIPMENT                  | p_1          | 2024-08-01T21:00:00Z | 200  | -200  | warehouseStd   |                       |
      | 02/d_1_2   | DEMAND            | SHIPMENT                  | p_1          | 2024-08-22T21:00:00Z | 50   | -250  | warehouseStd   |                       |
      | 03/s_1_1   | SUPPLY            | PRODUCTION                | p_1          | 2025-02-25T06:00:00Z | 250  | 0     | warehouseStd   | oc_1                  |
      | 04/d_2_1   | DEMAND            | PRODUCTION                | p_2          | 2025-02-25T06:00:00Z | 2500 | -2500 | warehouseStd   | oc_1                  |

    And the order identified by o_2 is reactivated

    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed |
      | oc_1       | p_1          | bom_1             | ppln_1                 | 540006        | 200 PCE    | 200 PCE      | 0 PCE        | 2025-02-25T06:00:00Z | 2025-02-25T06:00:00Z | false        | false         |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP   | M_Warehouse_ID | PP_Order_Candidate_ID |
      | 01/d_1_1   | DEMAND            | SHIPMENT                  | p_1          | 2024-08-01T21:00:00Z | 200  | -200  | warehouseStd   |                       |
      | 02/d_1_2   | DEMAND            | SHIPMENT                  | p_1          | 2024-08-22T21:00:00Z | 0    | -200  | warehouseStd   |                       |
      | 03/s_1_1   | SUPPLY            | PRODUCTION                | p_1          | 2025-02-25T06:00:00Z | 200  | 0     | warehouseStd   | oc_1                  |
      | 04/d_2_1   | DEMAND            | PRODUCTION                | p_2          | 2025-02-25T06:00:00Z | 2000 | -2000 | warehouseStd   | oc_1                  |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_2                      | 30             |

    And the order identified by o_2 is completed

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | M_Product_ID | PP_Product_BOM_ID | PP_Product_Planning_ID | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | DatePromised         | DateStartSchedule    | OPT.IsClosed | OPT.Processed |
      | oc_1       | p_1          | bom_1             | ppln_1                 | 540006        | 200 PCE    | 200 PCE      | 0 PCE        | 2025-02-25T06:00:00Z | 2025-02-25T06:00:00Z | false        | false         |
      | oc_2       | p_1          | bom_1             | ppln_1                 | 540006        | 30 PCE     | 30 PCE       | 0 PCE        | 2025-02-25T06:00:00Z | 2025-02-25T06:00:00Z | false        | false         |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | ATP   | M_Warehouse_ID | PP_Order_Candidate_ID |
      | 01/d_1_1   | DEMAND            | SHIPMENT                  | p_1          | 2024-08-01T06:00:00Z | 200  | -200  | warehouseStd   |                       |
      | 02/d_1_2   | DEMAND            | SHIPMENT                  | p_1          | 2025-02-25T06:00:00Z | 30   | -230  | warehouseStd   |                       |
      #TODO ATP on the next line should be -30
      | 03/s_1_1   | SUPPLY            | PRODUCTION                | p_1          | 2025-02-25T06:00:00Z | 200  | -230  | warehouseStd   | oc_1                  |
      | 04/d_2_1   | DEMAND            | PRODUCTION                | p_2          | 2025-02-25T06:00:00Z | 2000 | -2000 | warehouseStd   | oc_1                  |
      | 05/s_1_2   | SUPPLY            | PRODUCTION                | p_1          | 2025-02-25T06:00:00Z | 30   | 0     | warehouseStd   | oc_2                  |
      | 06/d_2_2   | DEMAND            | PRODUCTION                | p_2          | 2025-02-25T06:00:00Z | 300  | -2300 | warehouseStd   | oc_2                  |