@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor6
Feature: material dispo reacts to order docactions
## F5100: Material Disposition

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
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
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

  @Id:S0461_20
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: Sales order with manufactured product triggers PP order candidates via SupplyRequired
    # SO for a product with BOM + product planning → DEMAND(SHIPMENT) + SUPPLY(PRODUCTION) + DEMAND(PRODUCTION)
    Given load M_AttributeSet:
      | M_AttributeSet_ID.Identifier   | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category_S0461          | attributeSet_convenienceSalate   |
    And update duration for AD_Workflow nodes
      | AD_Workflow_ID | Duration |
      | 540075         | 0        |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_Product_Category_ID.Identifier |
      | p_finished | standard_category_S0461              |
      | p_component | standard_category_S0461              |
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
      | plv_1                  | p_finished   | 10.0     | PCE      | Normal           |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID | ValidFrom  | PP_Product_BOMVersions_ID |
      | bom_1      | p_finished   | 2024-01-01 | bomVersions_1             |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | ValidFrom  | QtyBatch |
      | boml_1     | bom_1             | p_component  | 2024-01-01 | 5        |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | PP_Product_BOMVersions_ID | IsCreatePlan |
      | ppln_1     | p_finished   | bomVersions_1             | false        |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_1 | N        | Y          | ps_1               |
    And metasfresh contains M_HU_PI:
      | Identifier         |
      | huPackingLU        |
      | huPackingTU        |
      | huPackingVirtualPI |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID         | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU        | LU          | Y         |
      | packingVersionTU              | huPackingTU        | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemProduct                      | huPiItemTU                 | p_finished              | 10  | 2020-02-01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1        | true    | customer_1    | 2024-09-20  | 2024-09-22T21:00:00Z | WH_S0461       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_finished   | 10         |
    When the order identified by o_1 is completed
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP |
      | c_demand   | DEMAND            | SHIPMENT                  | p_finished   | 2024-09-22T21:00:00Z | 10  | -10 |
      | c_supply   | SUPPLY            | PRODUCTION                | p_finished   | 2024-09-22T21:00:00Z | 10  | 0   |
      | c_comp     | DEMAND            | PRODUCTION                | p_component  | 2024-09-22T21:00:00Z | 50  | -50 |

  @Id:S0461_30
  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: Sales order with purchased product creates only DEMAND SHIPMENT candidate
    # SO for a product without BOM or product planning → only DEMAND(SHIPMENT) candidate
    Given metasfresh contains M_Products:
      | Identifier  | M_Product_Category_ID   | C_UOM_ID.X12DE355 |
      | p_purchased | standard_category_S0461 | PCE               |
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
      | plv_1                  | p_purchased  | 10.0     | PCE      | Normal           |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer_1 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | PreparationDate      | M_Warehouse_ID |
      | o_1        | true    | customer_1    | 2024-09-20  | 2024-09-22T21:00:00Z | WH_S0461       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | o_1        | p_purchased  | 8          |
    When the order identified by o_1 is completed
    Then after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
      | c_demand   | DEMAND            | SHIPMENT                  | p_purchased  | 2024-09-22T21:00:00Z | 8   | -8  | WH_S0461       |
