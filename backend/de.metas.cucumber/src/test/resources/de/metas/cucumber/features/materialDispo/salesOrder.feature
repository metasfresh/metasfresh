@from:cucumber
@ghActions:run_on_executor6
Feature: material dispo reacts to order docactions

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-09-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And load M_Product_Category:
      | M_Product_Category_ID   | Name     | Value    |
      | standard_category_S0461 | Standard | Standard |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | WH_S0461       |

  @Id:S0461_10
  @from:cucumber
  Scenario:  If a sales-order is voided, its shipment-schedule's MD_Canddiate's qty is set to zero

    Given metasfresh contains M_Products:
      | Identifier           | M_Product_Category_ID   | C_UOM_ID.X12DE355 |
      | product_1_1_S0461_10 | standard_category_S0461 | PCE               |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | ps_1_S0461_10 |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1_S0461_10 | ps_1_S0461_10      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID |
      | plv_1_S0461_10 | pl_1_S0461_10  |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID | M_Product_ID         | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_1_S0461_10 | plv_1_S0461_10         | product_1_1_S0461_10 | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier        | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_S0461_10 | N        | Y          | ps_1_S0461_10      |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID     | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1_S0461_10 | true    | customer_S0461_10 | 2024-09-20  | 2024-09-22T21:00:00Z | WH_S0461       |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID         | QtyEntered |
      | ol_1_S0461_10 | o_1_S0461_10 | product_1_1_S0461_10 | 12         |
    And the order identified by o_1_S0461_10 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier        | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | 01/d_1_1_S0461_10 | DEMAND            | SHIPMENT                  | product_1_1_S0461_10 | 2024-09-22T21:00:00Z | 12  | -12 | WH_S0461       |

    When the order identified by o_1_S0461_10 is reactivated

    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier        | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | 01/d_1_1_S0461_10 | DEMAND            | SHIPMENT                  | product_1_1_S0461_10 | 2024-09-22T21:00:00Z | 0   | 0   | WH_S0461       |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1_S0461_10             | 16             |

    And the order identified by o_1_S0461_10 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier        | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | 01/d_1_1_S0461_10 | DEMAND            | SHIPMENT                  | product_1_1_S0461_10 | 2024-09-22T21:00:00Z | 16  | -16 | WH_S0461       |

    And the order identified by o_1_S0461_10 is voided

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier        | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID         | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | 01/d_1_1_S0461_10 | DEMAND            | SHIPMENT                  | product_1_1_S0461_10 | 2024-09-22T21:00:00Z | 0   | 0   | WH_S0461       |