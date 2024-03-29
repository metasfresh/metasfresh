@from:cucumber
@ghActions:run_on_executor4
Feature: create production order
  As a user
  I want to create a Production order record

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_AttributeSet:
      | M_AttributeSet_ID.Identifier   | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_convenienceSalate   |

  @flaky
  @from:cucumber
  @Id:S0129.1_100
  @Id:S0129.2_100
  @Id:S0196_400
  Scenario:  The manufacturing order is created from a manufacturing order candidate
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_1        | standard_category     |
      | p_2        | standard_category     |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemManufacturingProduct         | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_1      | pl_1           | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
      | plv_1                  | p_2          | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_2 | N        | Y          | ps_1               |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_2            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | olASI                                    |
    # Complete the order to set the production dispo in motion
    When the order identified by o_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                     |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0                      | productPlanningASI        |
      | c_l_1      | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -100 | -100                   | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_2          | 2021-04-16T21:00:00Z | 100  | 0                      | bomLineASI                |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    # now create the PP_Order from the candidates
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
    # this is the PP_Order_Candidates from above, but now with processed=Y
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_1      | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_1      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10         | PCE               | endcustomer_2            | 2021-04-16T21:00:00Z | bomASI                                   |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppOrderBOMLine_1               | ppo_1                  | ppol_1     | p_2                     | 100          | false           | PCE               | CO            | bomLineASI                               |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_1                            | ppo_1                  | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_3        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                    |
      | c_l_3      | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 0   | -10                    | bomASI                    |
      | c_l_1      | DEMAND            | PRODUCTION                | p_2          | 2021-04-16T21:00:00Z | 0   | 0                      | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_2          | 2021-04-16T21:00:00Z | 100 | 100                    | bomLineASI                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 10                      | 10                           | 0                             | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | PP_Order_ID.Identifier | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | huLuTuConfig                          | ppo_1                  | ppOrderTU          | N               | 0     | N               | 1     | N               | 10          | huItemManufacturingProduct         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppo_1                  |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.HUStatus |
      | ppOrderTU          | A            |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier | OPT.DateProjected_LocalTimeZone |
      | c_1        | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | 0                      | olASI                                    |                                 |
      | c_2        | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomASI                                   |                                 |
      | c_3        | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomASI                                   |                                 |
      | c_4        | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     |                      | 10   | 10                     | bomASI                                   | 2021-04-11T00:00:00             |
      | c_l_1      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |                                 |
      | c_l_2      | SUPPLY              |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |                                 |
      | c_l_3      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |                                 |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_3       | p_1                     | 2021-04-11  | olASI                        | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

  @flaky
  @Id:S0196_500
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate, it is completed, then it is reactivated and its qtyOrdered is updated and the ordered is completed again. Then, the order is reactivated once again, its qtyOrdered updated to the initial value and the order is completed.
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_28092022_1           | standard_category                    |
      | p_2        | trackedProduct_component_28092022_1 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_28092022_1 | pricing_system_value_28092022_1 | pricing_system_description_28092022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_28092022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_28092022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | EndcustomerPP_28092022_1 | N            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_2            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | olASI                                    |
    # Complete the order to set the production dispo in motion
    When the order identified by o_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | productPlanningASI                       |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      | bomLineASI                               |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    # now create the PP_Order from the candidates
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
    # this is the PP_Order_Candidates from above, but now with processed=Y
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_1      | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_1      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10         | PCE               | endcustomer_2            | 2021-04-16T21:00:00Z | bomASI                                   |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppOrderBOMLine_1               | ppo_1                  | ppol_1     | p_2                     | 100          | false           | PCE               | CO            | bomLineASI                               |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_1                            | ppo_1                  | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0   | -10                    | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0   | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100 | 100                    | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 10                      | 10                           | 0                             | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is reactivated

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And update PP_Order:
      | PP_Order_ID.Identifier | OPT.QtyEntered |
      | ppo_1                  | 12             |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 12   | 2                      | bomASI                                   |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -120 | -20                    | bomLineASI                               |
      | c_l_4      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 20   | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 2                              | 2                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 120                          | 0                             | 120                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 12   | 2                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -120 | -20                    | bomLineASI                               |
      | c_l_4      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 20   | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 12                      | 10                           | 2                             | 0                              | 2                              | 2                          | 12                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 120                     | 0                       | 120                          | -120                          | 120                            | 0                              | 0                          | 0                             | 120                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is reactivated

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 2                              | 2                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 120                          | 0                             | 120                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And update PP_Order:
      | PP_Order_ID.Identifier | OPT.QtyEntered |
      | ppo_1                  | 10             |

    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |
      | c_l_4      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 20   | 20                     | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 120                          | 0                             | 120                            | 20                             | 20                         | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |
      | c_l_4      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 20   | 20                     | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 10                      | 10                           | 0                             | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 120                          | -100                          | 120                            | 20                             | 20                         | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

  @from:cucumber
  @flaky
  @Id:S0129.2_130
  @Id:S0129.2_150
  @Id:S0129.2_170
  @Id:S0196_600
  Scenario:  The manufacturing order is created from a manufacturing order candidate, then the manufacturing order candidate is re-opened, and another manufacturing order is created from it
    # Basic setup
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | p_3        | standard_category     |
      | p_4        | standard_category     |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_2       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | Description | SOTrx | PricePrecision |
      | pl_2       | ps_2               | DE                    | EUR                 | null        | true  | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_2      | pl_2           | 2021-06-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_2                  | p_3          | 10.0     | PCE               | Normal                        |
      | pp_3       | plv_2                  | p_4          | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_2      | p_3                     | 2021-06-01 | bomVersions_2                        | bomASI                                   |
    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier |
      | boml_2     | bom_2                        | p_4                     | 2021-06-01 | 10       | bomLineASI                               |
    And the PP_Product_BOM identified by bom_2 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_2     | p_3                     | bomVersions_2                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_3 | EndcustomerPP_2 | N            | Y              | ps_2                          |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    # Create the order to start it all
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_2        | true    | endcustomer_3            | 2021-06-17  | 2021-06-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_2       | o_2                   | p_3                     | 10         | olASI                                    |
    When the order identified by o_2 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_2        | DEMAND            | SHIPMENT                  | p_3          | 2021-06-16T21:00:00Z | -10  | -10                    | olASI                     |
      | c_3        | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 10   | 0                      | productPlanningASI        |
      | c_l_1      | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | -100 | -100                   | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_4          | 2021-06-16T21:00:00Z | 100  | 0                      | bomLineASI                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 110s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_3                     | 2021-06-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_4                     | 2021-06-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_2                      | 10             | 10              |

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_2       | false     | p_3                     | bom_2                        | ppln_2                            | 540006        | 10         | 10           | 0            | PCE               | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_2                             | olc_2      | p_4                     | 100        | PCE               | CO            | boml_2                           | bomLineASI                               |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_2                             |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_2      | true      | p_3                     | bom_2                        | ppln_2                            | 540006        | 10         | 0            | 10           | PCE               | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_3      | p_3                     | bom_2                        | ppln_2                            | 540006        | 10         | 10         | PCE               | endcustomer_3            | 2021-06-16T21:00:00Z | bomASI                                   |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppOrderBOMLine_3               | ppo_3                  | p_4                     | 100          | false           | PCE               | CO            | bomLineASI                               |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_2                            | ppo_3                  | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_4        | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 10   | 0                      | bomASI                    |
      | c_l_3      | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | -100 | 0                      | bomLineASI                |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_3        | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 0   | -10                    | bomASI                    |
      | c_l_1      | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 0   | 0                      | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_4          | 2021-06-16T21:00:00Z | 100 | 100                    | bomLineASI                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_3                     | 2021-06-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_4                     | 2021-06-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_2                      | 10             | 10              |

    And the following PP_Order_Candidates are re-opened
      | PP_Order_Candidate_ID.Identifier |
      | ocP_2                            |
    # raise the quantity from 10 to 22, so 12 additional items are requried
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | QtyEntered | DateStartSchedule |
      | ocP_2                            | 22         |                   |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_4        | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 10  | 12                     | bomASI                    |
      | c_l_4      | SUPPLY            |                           | p_4          | 2021-06-16T21:00:00Z | 120 | 0                      | bomLineASI                |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_3        | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 12  | 2                      | bomASI                    |
      | c_l_1      | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 120 | -120                   | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_4          | 2021-06-16T21:00:00Z | 100 | -20                    | bomLineASI                |
      | c_l_3      | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 100 | -120                   | bomLineASI                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_3                     | 2021-06-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 12                             | 12                         | 0                             | 0                             |
      | cp_2       | p_4                     | 2021-06-16  | bomLineASI                   | 0                               | 0                       | 0                       | 220                          | 0                             | 220                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_2                      | 10             | 10              |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | ocP_2                            |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_3      | true      | p_3                     | bom_2                        | ppln_2                            | 540006        | 22         | 0            | 22           | PCE               | 2021-06-16T21:00:00Z | 2021-06-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_4      | p_3                     | bom_2                        | ppln_2                            | 540006        | 12         | 12         | PCE               | endcustomer_3            | 2021-06-16T21:00:00Z | bomASI                                   |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppOrderBOMLine_3               | ppo_4                  | p_4                     | 120          | false           | PCE               | CO            | bomLineASI                               |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_3                            | ppo_4                  | 12         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_5        | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 12   | 12                     | bomASI                    |
      | c_l_5      | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | -120 | 0                      | bomLineASI                |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_4        | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 10  | 0                      | bomASI                    |
      | c_3        | SUPPLY            | PRODUCTION                | p_3          | 2021-06-16T21:00:00Z | 0   | -10                    | bomASI                    |
      | c_l_1      | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 0   | 0                      | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_4          | 2021-06-16T21:00:00Z | 100 | 100                    | bomLineASI                |
      | c_l_3      | DEMAND            | PRODUCTION                | p_4          | 2021-06-16T21:00:00Z | 100 | 0                      | bomLineASI                |
      | c_l_4      | SUPPLY            |                           | p_4          | 2021-06-16T21:00:00Z | 120 | 120                    | bomLineASI                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_3                     | 2021-06-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 12                             | 12                         | 0                             | 0                             |
      | cp_2       | p_4                     | 2021-06-16  | bomLineASI                   | 0                               | 0                       | 0                       | 220                          | 0                             | 220                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_2                      | 10             | 10              |

    And the manufacturing order identified by ppo_3 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_3                     | 2021-06-16  | olASI                        | 10                              | 10                      | 10                      | 10                           | 0                             | 0                              | 12                             | 12                         | 10                            | 0                             |
      | cp_2       | p_4                     | 2021-06-16  | bomLineASI                   | 0                               | 100                     | 0                       | 220                          | -100                          | 220                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 90s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_2                      | 10             | 10              |

    And the manufacturing order identified by ppo_4 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_3                     | 2021-06-16  | olASI                        | 10                              | 10                      | 22                      | 10                           | 12                            | 0                              | 12                             | 12                         | 22                            | 0                             |
      | cp_2       | p_4                     | 2021-06-16  | bomLineASI                   | 0                               | 220                     | 0                       | 220                          | -220                          | 220                            | 0                              | 0                          | 0                             | 220                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_2                      | 10             | 10              |

  @Id:S0196_1300
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate and other md_candidates already exist
    Given metasfresh contains M_Products:
      | Identifier |
      | p_11       |
      | p_22       |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_11      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_11      | ps_11              | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_11     | pl_11          | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_11      | plv_11                            | p_11                    | 10.0     | PCE               | Normal                        |
      | pp_22      | plv_11                            | p_22                    | 10.0     | PCE               | Normal                        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_11     | p_11                    | 2021-04-01 | bomVersions_3                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_11    | bom_11                       | p_22                    | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_11 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_11    | p_11                    | bomVersions_3                            | false        |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10  | 10                     |
      | s_2        | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10  | 20                     |
    And metasfresh contains C_BPartners:
      | Identifier     | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_22 | N        | Y          | ps_11              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_11       | true    | endcustomer_22           | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_11      | o_11                  | p_11                    | 20         |
    When the order identified by o_11 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10   | 10                     |
      | c_11       | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | -20  | -10                    |
      | c_22       | SUPPLY            | PRODUCTION                | p_11         | 2021-04-12T21:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                | p_22         | 2021-04-12T21:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                           | p_22         | 2021-04-12T21:00:00Z | 100  | 0                      |
      | s_2        | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10   | 10                     |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_11      | false     | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 10           | 0            | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_11                            | olc_11     | p_22                    | 100        | PCE               | CO            | boml_11                          |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_11                            |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_11     | true      | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 0            | 10           | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_11     | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 10         | PCE               | endcustomer_22           | 2021-04-12T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | PP_Order_BOMLine_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_11                 | ppol_11                        | p_22                    | 100          | false           | PCE               | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_11                           | ppo_11                 | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10   | 10                     |
      | c_11       | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | -20  | -10                    |
      | c_22       | SUPPLY            | PRODUCTION                | p_11         | 2021-04-12T21:00:00Z | 0    | -10                    |
      | c_l_1      | DEMAND            | PRODUCTION                | p_22         | 2021-04-12T21:00:00Z | 0    | 0                      |
      | c_l_2      | SUPPLY            |                           | p_22         | 2021-04-12T21:00:00Z | 100  | 100                    |
      | c_33       | SUPPLY            | PRODUCTION                | p_11         | 2021-04-12T21:00:00Z | 10   | 0                      |
      | c_l_3      | DEMAND            | PRODUCTION                | p_22         | 2021-04-12T21:00:00Z | -100 | 0                      |
      | s_2        | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10   | 10                     |

  @from:cucumber
  @flaky
  @Id:S0129.2_200
  @Id:S0196_700
  Scenario:  The manufacturing order is created from a manufacturing order candidate and the date of the manufacturing order candidate is changed in the past
    Given metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID.Identifier |
      | p_111      | standard_category                |
      | p_222      | standard_category                |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_111     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_111     | ps_111             | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_111    | pl_111         | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_111     | plv_111                | p_111        | 10.0     | PCE               | Normal                        |
      | pp_222     | plv_111                | p_222        | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_111    | p_111                   | 2021-04-01 | bomVersions_4                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier |
      | boml_111   | bom_111                      | p_222                   | 2021-04-01 | 10       | bomLineASI                               |
    And the PP_Product_BOM identified by bom_111 is completed

    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_111   | p_111                   | bomVersions_4                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier      | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_222 | N        | Y          | ps_111             |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_111      | true    | endcustomer_222          | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_111     | o_111                 | p_111                   | 10         | olASI                                    |
    When the order identified by o_111 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_111      | DEMAND            | SHIPMENT                  | p_111        | 2021-04-12T21:00:00Z | -10  | -10                    | olASI                     |
      | c_222      | SUPPLY            | PRODUCTION                | p_111        | 2021-04-12T21:00:00Z | 10   | 0                      | productPlanningASI        |
      | c_l_1      | DEMAND            | PRODUCTION                | p_222        | 2021-04-12T21:00:00Z | -100 | -100                   | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_222        | 2021-04-12T21:00:00Z | 100  | 0                      | bomLineASI                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_111                   | 2021-04-12  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_222                   | 2021-04-12  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_111                    | 10             | 10              |

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_111     | false     | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 10           | 0            | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_111                           | olc_111    | p_222                   | 100        | PCE               | CO            | boml_111                         | bomLineASI                               |
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | QtyEntered | DateStartSchedule    |
      | oc_111                           |            | 2021-04-11T21:00:00Z |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_222      | SUPPLY            | PRODUCTION                | p_111        | 2021-04-11T21:00:00Z | 10  | 10                     | bomASI                    |
      | c_l_3      | SUPPLY            |                           | p_222        | 2021-04-11T21:00:00Z | 100 | 0                      | bomLineASI                |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_111      | DEMAND            | SHIPMENT                  | p_111        | 2021-04-12T21:00:00Z | 10  | 0                      | bomASI                    |
      | c_l_1      | DEMAND            | PRODUCTION                | p_222        | 2021-04-11T21:00:00Z | 100 | -100                   | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_222        | 2021-04-12T21:00:00Z | 100 | 100                    | bomLineASI                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_111                   | 2021-04-12  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_222                   | 2021-04-12  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 100                            | 100                        | 0                             | 0                             |
      | cp_3       | p_222                   | 2021-04-11  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_111                    | 10             | 10              |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_111                           |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_111    | true      | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 0            | 10           | PCE               | 2021-04-11T21:00:00Z | 2021-04-11T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_111    | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 10         | PCE               | endcustomer_222          | 2021-04-11T21:00:00Z | bomASI                                   |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | PP_Order_BOMLine_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_111                | ppol_111                       | p_222                   | 100          | false           | PCE               | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_111                          | ppo_111                | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise | M_AttributeSetInstance_ID |
      | c_333      | SUPPLY            | PRODUCTION                | p_111        | 2021-04-11T21:00:00Z | 10   | 10                     | bomASI                    |
      | c_l_4      | DEMAND            | PRODUCTION                | p_222        | 2021-04-11T21:00:00Z | -100 | 0                      | bomLineASI                |
      | c_222      | SUPPLY            | PRODUCTION                | p_111        | 2021-04-11T21:00:00Z | 0    | 0                      | bomASI                    |
      | c_l_1      | DEMAND            | PRODUCTION                | p_222        | 2021-04-11T21:00:00Z | 0    | 0                      | bomLineASI                |
      | c_l_2      | SUPPLY            |                           | p_222        | 2021-04-12T21:00:00Z | 100  | 100                    | bomLineASI                |
      | c_l_3      | SUPPLY            |                           | p_222        | 2021-04-11T21:00:00Z | 100  | 100                    | bomLineASI                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_111                   | 2021-04-12  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_4       | p_111                   | 2021-04-11  | olASI                        | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                             | 0                             |
      | cp_2       | p_222                   | 2021-04-12  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 100                            | 100                        | 0                             | 0                             |
      | cp_3       | p_222                   | 2021-04-11  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_111                    | 10             | 10              |

    And the manufacturing order identified by ppo_111 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_111                   | 2021-04-12  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_4       | p_111                   | 2021-04-11  | olASI                        | 0                               | 0                       | 10                      | 0                            | 10                            | 0                              | 10                             | 10                         | 10                            | 0                             |
      | cp_2       | p_222                   | 2021-04-12  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 100                            | 100                        | 0                             | 0                             |
      | cp_3       | p_222                   | 2021-04-11  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_111                    | 10             | 10              |

  @flaky
  @Id:S0196_800
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate and the date of the manufacturing order candidate is changed in the future
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_111      | trackedProduct_29092022_4           | standard_category                    |
      | p_222      | trackedProduct_component_29092022_4 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_111     | pricing_system_name_29092022_4 | pricing_system_value_29092022_4 | pricing_system_description_29092022_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_111     | ps_111                        | DE                        | EUR                 | price_list_name_29092022_4 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_111    | pl_111                    | trackedProduct-PLV_29092022_4 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_111     | plv_111                           | p_111                   | 10.0     | PCE               | Normal                        |
      | pp_222     | plv_111                           | p_222                   | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_111    | p_111                   | 2021-04-01 | bomVersions_4                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier |
      | boml_111   | bom_111                      | p_222                   | 2021-04-01 | 10       | bomLineASI                               |
    And the PP_Product_BOM identified by bom_111 is completed

    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_111   | p_111                   | bomVersions_4                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier      | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_222 | EndcustomerPP_29092022_4 | N            | Y              | ps_111                        |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_111      | true    | endcustomer_222          | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_111     | o_111                 | p_111                   | 10         | olASI                                    |
    When the order identified by o_111 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_111      | DEMAND            | SHIPMENT                      | p_111                   | 2021-04-12T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_222      | SUPPLY            | PRODUCTION                    | p_111                   | 2021-04-12T21:00:00Z | 10   | 0                      | productPlanningASI                       |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_222                   | 2021-04-12T21:00:00Z | -100 | -100                   | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_222                   | 2021-04-12T21:00:00Z | 100  | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_111                   | 2021-04-12  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_222                   | 2021-04-12  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_111                    | 10             | 10              |

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_111     | false     | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 10           | 0            | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_111                           | olc_111    | p_222                   | 100        | PCE               | CO            | boml_111                         | bomLineASI                               |
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | QtyEntered | DateStartSchedule    |
      | oc_111                           |            | 2021-04-13T21:00:00Z |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_111      | DEMAND            | SHIPMENT                      | p_111                   | 2021-04-12T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_222      | SUPPLY            | PRODUCTION                    | p_111                   | 2021-04-13T21:00:00Z | 10   | 0                      | productPlanningASI                       |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_222                   | 2021-04-13T21:00:00Z | -100 | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_222                   | 2021-04-12T21:00:00Z | 100  | 100                    | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_111                   | 2021-04-12  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | -10                            | -10                        | 0                             | 0                             |
      | cp_3       | p_111                   | 2021-04-13  | olASI                        | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_222                   | 2021-04-12  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 100                            | 100                        | 0                             | 0                             |
      | cp_4       | p_222                   | 2021-04-13  | bomLineASI                   | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_111                    | 10             | 10              |

    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_111                           |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_111    | true      | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 0            | 10           | PCE               | 2021-04-13T21:00:00Z | 2021-04-13T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_111    | p_111                   | bom_111                      | ppln_111                          | 540006        | 10         | 10         | PCE               | endcustomer_222          | 2021-04-13T21:00:00Z | bomASI                                   |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | PP_Order_BOMLine_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_111                | ppol_111                       | p_222                   | 100          | false           | PCE               | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_111                          | ppo_111                | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_111      | DEMAND            | SHIPMENT                      | p_111                   | 2021-04-12T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_222      | SUPPLY            | PRODUCTION                    | p_111                   | 2021-04-13T21:00:00Z | 10   | 0                      | productPlanningASI                       |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_222                   | 2021-04-13T21:00:00Z | -100 | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_222                   | 2021-04-12T21:00:00Z | 100  | 100                    | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_111                   | 2021-04-12  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | -10                            | -10                        | 0                             | 0                             |
      | cp_3       | p_111                   | 2021-04-13  | olASI                        | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_222                   | 2021-04-12  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 100                            | 100                        | 0                             | 0                             |
      | cp_4       | p_222                   | 2021-04-13  | bomLineASI                   | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_111                    | 10             | 10              |

    And the manufacturing order identified by ppo_111 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_111                   | 2021-04-12  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | -10                            | -10                        | 0                             | 0                             |
      | cp_3       | p_111                   | 2021-04-13  | olASI                        | 0                               | 0                       | 10                      | 0                            | 10                            | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_222                   | 2021-04-12  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 100                            | 100                        | 0                             | 0                             |
      | cp_4       | p_222                   | 2021-04-13  | bomLineASI                   | 0                               | 100                     | 0                       | 0                            | -100                          | 0                              | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_111                    | 10             | 10              |

  @Id:S0196_1200
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate, other md_candidates already exist and the date is changed in the future
    Given metasfresh contains M_Products:
      | Identifier |
      | p_11       |
      | p_22       |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_11      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_11      | ps_11              | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_11     | pl_11          | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_11      | plv_11                 | p_11         | 10.0     | PCE               | Normal                        |
      | pp_22      | plv_11                 | p_22         | 10.0     | PCE               | Normal                        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_11     | p_11                    | 2021-04-01 | bomVersions_5                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_11    | bom_11                       | p_22                    | 2021-04-01 | 10       |
    And the PP_Product_BOM identified by bom_11 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_11    | p_11                    | bomVersions_5                            | false        |
    And metasfresh initially has this MD_Candidate data
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                               | p_11                    | 2021-04-10T21:00:00Z | 10  | 10                     |
      | s_2        | INVENTORY_UP      |                               | p_11                    | 2021-04-14T21:00:00Z | 10  | 20                     |
    And metasfresh contains C_BPartners:
      | Identifier     | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_22 | N        | Y          | ps_11              |
    # Create a sales order at a date in between the two MD_Candidates
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_11       | true    | endcustomer_22           | 2021-04-13  | 2021-04-12T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_11      | o_11                  | p_11                    | 20         |
    When the order identified by o_11 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10   | 10                     |
      | c_11       | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | -20  | -10                    |
      | c_22       | SUPPLY            | PRODUCTION                | p_11         | 2021-04-12T21:00:00Z | 10   | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                | p_22         | 2021-04-12T21:00:00Z | -100 | -100                   |
      | c_l_2      | SUPPLY            |                           | p_22         | 2021-04-12T21:00:00Z | 100  | 0                      |
      | s_2        | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10   | 10                     |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_11      | false     | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 10           | 0            | PCE               | 2021-04-12T21:00:00Z | 2021-04-12T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_11                            | olc_11     | p_22                    | 100        | PCE               | CO            | boml_11                          |
    # leave QtyEntered untouched, but update DateStartSchedule, to move the candidate "behind" the second inventory-up
    And update PP_Order_Candidates
      | PP_Order_Candidate_ID.Identifier | QtyEntered | DateStartSchedule    |
      | oc_11                            |            | 2021-04-15T21:00:00Z |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10  | 10                     |
      | c_11       | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | 20  | -10                    |
      | s_2        | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10  | 0                      |
      | c_22       | SUPPLY            | PRODUCTION                | p_11         | 2021-04-15T21:00:00Z | 10  | 10                     |
      | c_l_1      | DEMAND            | PRODUCTION                | p_22         | 2021-04-15T21:00:00Z | 100 | 0                      |
      | c_l_2      | SUPPLY            |                           | p_22         | 2021-04-12T21:00:00Z | 100 | 100                    |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_11                            |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_11     | true      | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 0            | 10           | PCE               | 2021-04-15T21:00:00Z | 2021-04-15T21:00:00Z | false    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_11     | p_11                    | bom_11                       | ppln_11                           | 540006        | 10         | 10         | PCE               | endcustomer_22           | 2021-04-15T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | PP_Order_BOMLine_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_11                 | ppol_11                        | p_22                    | 100          | false           | PCE               | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_11                           | ppo_11                 | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | s_1        | INVENTORY_UP      |                           | p_11         | 2021-04-10T21:00:00Z | 10  | 10                     |
      | c_l_2      | SUPPLY            |                           | p_22         | 2021-04-12T21:00:00Z | 100 | 100                    |
      | c_11       | DEMAND            | SHIPMENT                  | p_11         | 2021-04-12T21:00:00Z | 20  | -10                    |
      | s_2        | INVENTORY_UP      |                           | p_11         | 2021-04-14T21:00:00Z | 10  | 0                      |
      | c_22       | SUPPLY            | PRODUCTION                | p_11         | 2021-04-15T21:00:00Z | 0   | 0                      |
      | c_33       | SUPPLY            | PRODUCTION                | p_11         | 2021-04-15T21:00:00Z | 10  | 10                     |
      | c_l_3      | DEMAND            | PRODUCTION                | p_22         | 2021-04-15T21:00:00Z | 100 | 0                      |
      | c_l_1      | DEMAND            | PRODUCTION                | p_22         | 2021-04-15T21:00:00Z | 0   | 100                    |

  @Id:S0196_1100
  @from:cucumber
  Scenario: BOM bom_1 is created with two components. Manufacturing order candidate is generated,
  then another BOM (newer than the previous one in terms of validFrom) is created from the API,
  Manufacturing order candidate and Material schedules are updated accordingly,
  then the manufacturing order is created from the manufacturing order candidate.
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
      | p_comp_1   |
      | p_comp_2   |
      | p_comp_3   |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1               | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_1      | pl_1           | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                  | p_1          | 10.0     | PCE               | Normal                        |
    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | boml_1     | bom_1                        | p_comp_1                | 2021-04-01 | 10       |
      | boml_2     | bom_1                        | p_comp_2                | 2021-04-01 | 15       |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan |
      | ppln_1     | p_1                     | bomVersions_1                            | false        |
    And metasfresh contains C_BPartners:
      | Identifier    | IsVendor | IsCustomer | M_PricingSystem_ID |
      | endcustomer_2 | N        | Y          | ps_1               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_2            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty  | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | -10  | -10                    |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10   | 0                      |
      | c_l_1_d    | DEMAND            | PRODUCTION                | p_comp_1     | 2021-04-16T21:00:00Z | -100 | -100                   |
      | c_l_1_s    | SUPPLY            |                           | p_comp_1     | 2021-04-16T21:00:00Z | 100  | 0                      |
      | c_l_2_d    | DEMAND            | PRODUCTION                | p_comp_2     | 2021-04-16T21:00:00Z | -150 | -150                   |
      | c_l_2_s    | SUPPLY            |                           | p_comp_2     | 2021-04-16T21:00:00Z | 150  | 0                      |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_comp_1                | 100        | PCE               | CO            | boml_1                           |
      | oc_1                             | olc_2      | p_comp_2                | 150        | PCE               | CO            | boml_2                           |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | ExternalReference        | Type    | OPT.M_Product_ID.Identifier |
      | finishedGoodExternalRef6          | GRSSignum      | finishedGoodExternalRef6 | Product | p_1                         |
      | component1ExternalRef6            | GRSSignum      | component1ExternalRef6   | Product | p_comp_1                    |
      | component3ExternalRef6            | GRSSignum      | component3ExternalRef6   | Product | p_comp_3                    |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bom/version/001' and fulfills with '200' status code
    """
 {
    "uomCode": "PCE",
    "productIdentifier": "ext-GRSSignum-finishedGoodExternalRef6",
    "name": "trackedProduct_6",
    "isActive": true,
    "validFrom":"2021-04-10T12:00:00.000000Z",
    "bomLines": [
        {
            "productIdentifier": "ext-GRSSignum-component1ExternalRef6",
            "qtyBom": {
                "qty": 20.0,
                "uomCode": "PCE"
            },
            "line": 1
        },
        {
            "productIdentifier": "ext-GRSSignum-component3ExternalRef6",
            "qtyBom": {
                "qty": 30.0,
                "uomCode": "PCE"
            },
            "line": 2
        }
    ]
}
    """
    And process metasfresh response:
      | PP_Product_BOM_ID | PP_Product_BOMVersions_ID |
      | bom_2             | bomVersions_1             |
    And verify that bomVersions was created for product
      | PP_Product_BOMVersions_ID | M_Product_ID |
      | bomVersions_1             | p_1          |
    And verify that bom was created for product
      | PP_Product_BOM_ID | M_Product_ID | PP_Product_BOMVersions_ID | UomCode | ValidFrom                   |
      | bom_2             | p_1          | bomVersions_1             | PCE     | 2021-04-10T12:00:00.000000Z |
    And verify that bomLine was created for bom
      | OPT.PP_Product_BOMLine_ID.Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | Qty  | UomCode | IsQtyPercentage | OPT.Scrap | Line |
      | boml_3                               | bom_2                        | p_comp_1                | 20.0 | PCE     | false           |           | 1    |
      | boml_4                               | bom_2                        | p_comp_3                | 30.0 | PCE     | false           |           | 2    |
    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | oc_1       | false     | p_1                     | bom_2                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier |
      | oc_1                             | olc_1      | p_comp_1                | 200        | PCE               | CO            | boml_3                           |
      | oc_1                             | olc_2      | p_comp_2                | 0          | PCE               | CO            | boml_2                           |
      | oc_1                             | olc_3      | p_comp_3                | 300        | PCE               | CO            | boml_4                           |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | 10  | -10                    |
      | c_2        | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10  | 0                      |
      | c_l_1_d    | DEMAND            | PRODUCTION                | p_comp_1     | 2021-04-16T21:00:00Z | 200 | -200                   |
      | c_l_1_s    | SUPPLY            |                           | p_comp_1     | 2021-04-16T21:00:00Z | 100 | -100                   |
      | c_l_1_s2   | SUPPLY            |                           | p_comp_1     | 2021-04-16T21:00:00Z | 100 | 0                      |
      | c_l_2_d    | DEMAND            | PRODUCTION                | p_comp_2     | 2021-04-16T21:00:00Z | 0   | 0                      |
      | c_l_2_s    | SUPPLY            |                           | p_comp_2     | 2021-04-16T21:00:00Z | 150 | 150                    |
      | c_l_3_d    | DEMAND            | PRODUCTION                | p_comp_3     | 2021-04-16T21:00:00Z | 300 | -300                   |
      | c_l_3_s    | SUPPLY            |                           | p_comp_3     | 2021-04-16T21:00:00Z | 300 | 0                      |
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed |
      | ocP_1      | true      | p_1                     | bom_2                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         |
      | ppo_1      | p_1                     | bom_2                        | ppln_1                            | 540006        | 10         | 10         | PCE               | endcustomer_2            | 2021-04-16T21:00:00Z |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_ID.Identifier | PP_Order_BOMLine_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppo_1                  | ppol_1                         | p_comp_1                | 200          | false           | PCE               | CO            |
      | ppo_1                  | ppol_2                         | p_comp_3                | 300          | false           | PCE               | CO            |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_1                            | ppo_1                  | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier  | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1         | DEMAND            | SHIPMENT                  | p_1          | 2021-04-16T21:00:00Z | 10  | -10                    |
      | c_2         | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 0   | -10                    |
      | c_3         | SUPPLY            | PRODUCTION                | p_1          | 2021-04-16T21:00:00Z | 10  | 0                      |
      | c_l_1_d     | DEMAND            | PRODUCTION                | p_comp_1     | 2021-04-16T21:00:00Z | 0   | 0                      |
      | c_l_1_s     | SUPPLY            |                           | p_comp_1     | 2021-04-16T21:00:00Z | 100 | 100                    |
      | c_l_1_s2    | SUPPLY            |                           | p_comp_1     | 2021-04-16T21:00:00Z | 100 | 200                    |
      | c_l_2_d     | DEMAND            | PRODUCTION                | p_comp_2     | 2021-04-16T21:00:00Z | 0   | 0                      |
      | c_l_2_s     | SUPPLY            |                           | p_comp_2     | 2021-04-16T21:00:00Z | 150 | 150                    |
      | c_l_3_d     | DEMAND            | PRODUCTION                | p_comp_3     | 2021-04-16T21:00:00Z | 0   | 0                      |
      | c_l_3_s     | SUPPLY            |                           | p_comp_3     | 2021-04-16T21:00:00Z | 300 | 300                    |
      | c_ppo_l_1_d | DEMAND            | PRODUCTION                | p_comp_1     | 2021-04-16T21:00:00Z | 200 | 0                      |
      | c_ppo_l_2_d | DEMAND            | PRODUCTION                | p_comp_3     | 2021-04-16T21:00:00Z | 300 | 0                      |

  @flaky
  @Id:S0196_900
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate, it is reactivated and its DatePromised is updated in the future
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_03102022_1           | standard_category                    |
      | p_2        | trackedProduct_component_03102022_1 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_03102022_1 | pricing_system_value_03102022_1 | pricing_system_description_03102022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_03102022_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_03102022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | EndcustomerPP_03102022_1 | N            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_2            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | olASI                                    |
    # Complete the order to set the production dispo in motion
    When the order identified by o_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | productPlanningASI                       |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           | bomLineASI                               |
    # now create the PP_Order from the candidates
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
    # this is the PP_Order_Candidates from above, but now with processed=Y
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_1      | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_1      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10         | PCE               | endcustomer_2            | 2021-04-16T21:00:00Z | bomASI                                   |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppOrderBOMLine_1               | ppo_1                  | ppol_1     | p_2                     | 100          | false           | PCE               | CO            | bomLineASI                               |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_1                            | ppo_1                  | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0   | -10                    | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0   | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100 | 100                    | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 10                      | 10                           | 0                             | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is reactivated

    And update PP_Order:
      | PP_Order_ID.Identifier | OPT.DatePromised     |
      | ppo_1                  | 2021-04-18T21:00:00Z |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_3       | p_1                     | 2021-04-18  | olASI                        | 0                               | 0                       | 10                      | 0                            | 10                            | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

  @Id:S0196_1000
  @flaky
  @from:cucumber
  Scenario:  The manufacturing order is created from a manufacturing order candidate, it is reactivated and its DatePromised is updated in the past
    Given metasfresh contains M_Products:
      | Identifier | Name                                | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_03102022_2           | standard_category                    |
      | p_2        | trackedProduct_component_03102022_2 | standard_category                    |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_03102022_2 | pricing_system_value_03102022_2 | pricing_system_description_03102022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_03102022_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV_03102022_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | EndcustomerPP_03102022_2 | N            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_2            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | olASI                                    |
    # Complete the order to set the production dispo in motion
    When the order identified by o_1 is completed
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | productPlanningASI                       |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           | bomLineASI                               |
    # now create the PP_Order from the candidates
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
    # this is the PP_Order_Candidates from above, but now with processed=Y
    Then after not more than 60s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_1      | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 60s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_1      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10         | PCE               | endcustomer_2            | 2021-04-16T21:00:00Z | bomASI                                   |
    And after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppOrderBOMLine_1               | ppo_1                  | ppol_1     | p_2                     | 100          | false           | PCE               | CO            | bomLineASI                               |
    And after not more than 60s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_1                            | ppo_1                  | 10         | PCE               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0   | -10                    | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0   | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100 | 100                    | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 10                      | 10                           | 0                             | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is reactivated

    And update PP_Order:
      | PP_Order_ID.Identifier | OPT.DatePromised     |
      | ppo_1                  | 2021-04-14T21:00:00Z |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | -10                    | bomASI                                   |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_3       | p_1                     | 2021-04-14  | olASI                        | 0                               | 0                       | 10                      | 0                            | 10                            | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |


  @Id:S0264_1900
  @from:cucumber
  @ignore # flaky
  Scenario:  The manufacturing order is created with Lot for Lot
    Given metasfresh contains M_Products:
      | Identifier | Name                                 | OPT.M_Product_Category_ID.Identifier |
      | p_1        | trackedProduct_27042023_4            | standard_category                    |
      | p_2        | trackedProduct__component 27042023_4 | standard_category                    |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemManufacturingProduct         | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |

    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                  | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_27042023_4 | pricing_system_value_1 | pricing_system_description_27042023_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_1      | pl_1                      | trackedProduct-PLV1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |

    And metasfresh contains M_AttributeSetInstance with identifier "bomASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | bom_1      | p_1                     | 2021-04-01 | bomVersions_1                        | bomASI                                   |

    And metasfresh contains M_AttributeSetInstance with identifier "bomLineASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | OPT.M_AttributeSetInstance_ID.Identifier |
      | boml_1     | bom_1                        | p_2                     | 2021-04-01 | 10       | bomLineASI                               |
    And the PP_Product_BOM identified by bom_1 is completed
    And metasfresh contains M_AttributeSetInstance with identifier "productPlanningASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | OPT.PP_Product_BOMVersions_ID.Identifier | IsCreatePlan | OPT.M_AttributeSetInstance_ID.Identifier | IsLotForLot |
      | ppln_1     | p_1                     | bomVersions_1                            | false        | productPlanningASI                       | true        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_2 | EndcustomerPP_1 | N            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | endcustomer_2            | 2021-04-17  | 2021-04-16T21:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | olASI                                    |
    # Complete the order to set the production dispo in motion
    When the order identified by o_1 is completed
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | -10                    | olASI                                    |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | productPlanningASI                       |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | -100                   | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 0                      | bomLineASI                               |
    And after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1       | false     | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10           | 0            | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 30s, PP_OrderLine_Candidates are found
      | PP_Order_Candidate_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | ComponentType | PP_Product_BOMLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | oc_1                             | olc_1      | p_2                     | 100        | PCE               | CO            | boml_1                           | bomLineASI                               |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    # now create the PP_Order from the candidates
    And the following PP_Order_Candidates are enqueued for generating PP_Orders
      | PP_Order_Candidate_ID.Identifier |
      | oc_1                             |
    # this is the PP_Order_Candidates from above, but now with processed=Y
    Then after not more than 30s, PP_Order_Candidates are found
      | Identifier | Processed | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyToProcess | QtyProcessed | C_UOM_ID.X12DE355 | DatePromised         | DateStartSchedule    | IsClosed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ocP_1      | true      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 0            | 10           | PCE               | 2021-04-16T21:00:00Z | 2021-04-16T21:00:00Z | false    | bomASI                                   |
    And after not more than 30s, PP_Orders are found
      | Identifier | M_Product_ID.Identifier | PP_Product_BOM_ID.Identifier | PP_Product_Planning_ID.Identifier | S_Resource_ID | QtyEntered | QtyOrdered | C_UOM_ID.X12DE355 | C_BPartner_ID.Identifier | DatePromised         | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppo_1      | p_1                     | bom_1                        | ppln_1                            | 540006        | 10         | 10         | PCE               | endcustomer_2            | 2021-04-16T21:00:00Z | bomASI                                   |
    And after not more than 30s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType | OPT.M_AttributeSetInstance_ID.Identifier |
      | ppOrderBOMLine_1               | ppo_1                  | ppol_1     | p_2                     | 100          | false           | PCE               | CO            | bomLineASI                               |
    And after not more than 30s, PP_OrderCandidate_PP_Order are found
      | PP_Order_Candidate_ID.Identifier | PP_Order_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | ocP_1                            | ppo_1                  | 10         | PCE               |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_3        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 10   | 0                      | bomASI                                   |
      | c_l_3      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |
    And after not more than 30s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier |
      | c_2        | SUPPLY            | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0   | -10                    | bomASI                                   |
      | c_l_1      | DEMAND            | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0   | 0                      | bomLineASI                               |
      | c_l_2      | SUPPLY            |                               | p_2                     | 2021-04-16T21:00:00Z | 100 | 100                    | bomLineASI                               |
    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 0                       | 0                       | 100                          | 0                             | 100                            | 0                              | 0                          | 0                             | 0                             |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And the manufacturing order identified by ppo_1 is completed

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 10                      | 10                           | 0                             | 0                              | 0                              | 0                          | 10                            | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | PP_Order_ID.Identifier | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | huLuTuConfig                          | ppo_1                  | ppOrderTU          | N               | 0     | N               | 1     | N               | 10          | huItemManufacturingProduct         |

    When complete planning for PP_Order:
      | PP_Order_ID.Identifier |
      | ppo_1                  |

    And after not more than 60s, M_HUs should have
      | M_HU_ID.Identifier | OPT.HUStatus |
      | ppOrderTU          | A            |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.M_AttributeSetInstance_ID.Identifier | OPT.DateProjected_LocalTimeZone |
      | c_1        | DEMAND              | SHIPMENT                      | p_1                     | 2021-04-16T21:00:00Z | -10  | 0                      | olASI                                    |                                 |
      | c_2        | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomASI                                   |                                 |
      | c_3        | SUPPLY              | PRODUCTION                    | p_1                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomASI                                   |                                 |
      | c_4        | UNEXPECTED_INCREASE | PRODUCTION                    | p_1                     |                      | 10   | 10                     | bomASI                                   | 2021-04-11T00:00:00             |
      | c_l_1      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | 0    | 0                      | bomLineASI                               |                                 |
      | c_l_2      | SUPPLY              |                               | p_2                     | 2021-04-16T21:00:00Z | 100  | 100                    | bomLineASI                               |                                 |
      | c_l_3      | DEMAND              | PRODUCTION                    | p_2                     | 2021-04-16T21:00:00Z | -100 | 0                      | bomLineASI                               |                                 |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_PP_Order_AtDate | OPT.QtyDemand_PP_Order_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | olASI                        | 10                              | 10                      | 0                       | 10                           | -10                           | 10                             | 0                              | 0                          | 0                             | 0                             |
      | cp_3       | p_1                     | 2021-04-11  | olASI                        | 0                               | 0                       | 0                       | 0                            | 0                             | 0                              | 10                             | 10                         | 0                             | 0                             |
      | cp_2       | p_2                     | 2021-04-16  | bomLineASI                   | 0                               | 100                     | 0                       | 100                          | -100                          | 100                            | 0                              | 0                          | 0                             | 100                           |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |
