@from:cucumber
@ghActions:run_on_executor3
Feature: Stock shortage solved via distribution

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-07-04T08:00:00+00:00
    And there is no in transit M_Warehouse

  @flaky # https://github.com/metasfresh/metasfresh/actions/runs/7568809968/attempts/1?pr=17166
  @from:cucumber
  @Id:S0229_100
  @Id:S0264_800
  Scenario: Create Sales Order for a product of which a stock shortage is solved via distribution, complete it
  Distribution Order is created, validate DD_Order, DD_OrderLine, MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
  Complete the Distribution Order and then validate DD_Order, DD_OrderLine, MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
  Reactivate the Distribution Order and then validate DD_OrderLine, MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
  Change Distribution Order Line's QtyOrdered to a higher value and then validate DD_OrderLine, MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
  Complete the Distribution Order and then validate MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
  Void the Distribution Order and then validate MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
    Given metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_05_12_2022 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | plv_product_05_12_2022 | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | location_1 | bPLocation_1 | bpartner_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Name                        | Value                       | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsInTransit |
      | warehouse_1               | WarehouseTransit_05_12_2022 | WarehouseTransit_05_12_2022 | bpartner_1                   | location_1                            | true            |
      | warehouse_2               | WarehouseSource_05_12_2022  | WarehouseSource_05_12_2022  | bpartner_1                   | location_1                            | false           |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locator_1               | Standard_05_12_2022 | warehouse_2               |
    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | shipper_1               | 540006           |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID.Identifier | Name                     | Value                     | DocumentNo |
      | ddNetwork_1                          | DDNetworkName_05_12_2022 | DDNetworkValue_05_12_2022 | docNo2     |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_1                          | warehouseStd              | warehouse_2                     | shipper_1               |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.DD_NetworkDistribution_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ppln_1     | p_1                     | true         | ddNetwork_1                              | warehouseStd                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Warehouse_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 14         | warehouseStd                  |
    And the order identified by o_1 is completed

    And after not more than 60s, DD_OrderLines are found:
      | DD_OrderLine_ID.Identifier | C_OrderLineSO_ID.Identifier |
      | dd_ol_1                    | ol_1                        |

    And DD_Orders are found:
      | DD_Order_ID.Identifier | DD_OrderLine_ID.Identifier |
      | dd_o_1                 | dd_ol_1                    |

    And DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 14             | 10       | ddNetworkLine_1                              | p_1                         | 14             | 0                | 0               |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | warehouseStd                  |
      | c_2        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    | warehouseStd                  |
      | c_3        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      | warehouse_2                   |
      | c_4        | SUPPLY            |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     | warehouse_2                   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | -14                            | -14                        | 14                            | 0                             | warehouseStd                  | 0                            | 0                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 14                           | -14                           | 14                             | 14                             | 14                         | 0                             | 14                            | warehouse_2                   | 0                            | 0                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    When the dd_order identified by dd_o_1 is completed

    Then DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 14             | 10       | ddNetworkLine_1                              | p_1                         | 14             | 0                | 14              |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.DocStatus |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   | CO            |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | warehouseStd                  |
      | c_2        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    | warehouseStd                  |
      | c_3        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      | warehouse_2                   |
      | c_4        | SUPPLY            |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     | warehouse_2                   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | -14                            | -14                        | 14                            | 0                             | warehouseStd                  | 0                            | 0                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 14                           | -14                           | 14                             | 14                             | 14                         | 0                             | 14                            | warehouse_2                   | 0                            | 0                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    And the dd_order identified by dd_o_1 is reactivated

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | warehouseStd                  |
      | c_2        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    | warehouseStd                  |
      | c_3        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      | warehouse_2                   |
      | c_4        | SUPPLY            |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     | warehouse_2                   |

    And DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 14             | 10       | ddNetworkLine_1                              | p_1                         | 14             | 0                | 14              |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | -14                            | -14                        | 14                            | 0                             | warehouseStd                  | 0                            | 0                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 14                           | -14                           | 14                             | 14                             | 14                         | 0                             | 14                            | warehouse_2                   | 0                            | 0                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    And DD_OrderLines are updated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered |
      | dd_ol_1                    | 16             |

    And DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 16             | 10       | ddNetworkLine_1                              | p_1                         | 16             | 0                | 14              |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | warehouseStd                  |
      | c_2        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    | warehouseStd                  |
      | c_3        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      | warehouse_2                   |
      | c_4        | SUPPLY            |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     | warehouse_2                   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | -14                            | -14                        | 14                            | 0                             | warehouseStd                  | 0                            | 0                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 14                           | -14                           | 14                             | 14                             | 14                         | 0                             | 14                            | warehouse_2                   | 0                            | 0                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    And the dd_order identified by dd_o_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | warehouseStd                  |
      | c_2        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    | warehouseStd                  |
      | c_3        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      | warehouse_2                   |
      | c_4        | SUPPLY            |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     | warehouse_2                   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 16                      | 14                           | 2                             | 0                              | -14                            | -14                        | 16                            | 0                             | warehouseStd                  | 0                            | 0                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 16                      | 0                       | 14                           | -16                           | 14                             | 14                             | 14                         | 0                             | 16                            | warehouse_2                   | 0                            | 0                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 16             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 16             | DEMAND                |

    And the dd_order identified by dd_o_1 is voided

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | warehouseStd                  |
      | c_2        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    | warehouseStd                  |
      | c_3        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      | warehouse_2                   |
      | c_4        | SUPPLY            |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     | warehouse_2                   |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 16                      | 14                           | 2                             | 0                              | -14                            | -14                        | 16                            | 0                             | warehouseStd                  | 0                            | 0                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 16                      | 0                       | 14                           | -16                           | 14                             | 14                             | 14                         | 0                             | 16                            | warehouse_2                   | 0                            | 0                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 16             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 16             | DEMAND                |

  @from:cucumber
  @Id:S0229_200
  @Id:S0264_900
  Scenario: Set `DDOrder_isCreateMovementOnComplete` SysConfig to true
  Create Inventory for a product of which a stock shortage is solved via distribution and complete it
  Create Sales Order for the same product and for less qty than the inventory and complete it
  Distribution Order is created, validate DD_Order, DD_OrderLine, MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
  Change Distribution Order Line's QtyOrdered to the same value as the inventory and then validate DD_OrderLine, MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
  Complete the Distribution Order and then validate DD_Order, DD_OrderLine, M_Transaction, M_HU, MD_Candidate, MD_Cockpit, MD_Cockpit_DocumentDetail and MD_Cockpit_DDOrder_Detail
  Set `DDOrder_isCreateMovementOnComplete` SysConfig back to false
    Given set sys config boolean value true for sys config DDOrder_isCreateMovementOnComplete
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_06_12_2022 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | plv_product_06_12_2022 | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | location_1 | bPLocation_1 | bpartner_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_Version_ID |
      | pi_v_1                        | 101                |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Name                        | Value                       | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsInTransit |
      | warehouse_1               | WarehouseTransit_06_12_2022 | WarehouseTransit_06_12_2022 | bpartner_1                   | location_1                            | true            |
      | warehouse_2               | WarehouseSource_06_12_2022  | WarehouseSource_06_12_2022  | bpartner_1                   | location_1                            | false           |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locator_1               | Standard_06_12_2022 | warehouse_2               |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate         |
      | i_1        | warehouse_2    | 2022-07-04T00:00:00Z |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_1       | i_1                       | p_1                     | PCE          | 16       | 0       |
    And the inventory identified by i_1 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_1                          | hu_1               |

    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | shipper_1               | 540006           |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID.Identifier | Name          | Value          | DocumentNo |
      | ddNetwork_1                          | DDNetworkName | DDNetworkValue | docNo1     |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_1                          | warehouseStd              | warehouse_2                     | shipper_1               |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.DD_NetworkDistribution_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ppln_1     | p_1                     | true         | ddNetwork_1                              | warehouseStd                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Warehouse_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 14         | warehouseStd                  |
    And the order identified by o_1 is completed

    And after not more than 60s, DD_OrderLines are found:
      | DD_OrderLine_ID.Identifier | C_OrderLineSO_ID.Identifier |
      | dd_ol_1                    | ol_1                        |

    And DD_Orders are found:
      | DD_Order_ID.Identifier | DD_OrderLine_ID.Identifier |
      | dd_o_1                 | dd_ol_1                    |

    And DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 14             | 10       | ddNetworkLine_1                              | p_1                         | 14             | 0                | 0               |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 16  | 16                     |
      | c_2        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    |
      | c_3        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    |
      | c_4        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | -14                            | -14                        | 14                            | 0                             | warehouseStd                  | 0                            | 0                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 0                            | -14                           | 0                              | 16                             | 16                         | 0                             | 14                            | warehouse_2                   | 16                           | 16                 |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    And DD_OrderLines are updated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered |
      | dd_ol_1                    | 16             |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 16  | 16                     |
      | c_2        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    |
      | c_3        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    |
      | c_4        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | -14                            | -14                        | 14                            | 0                             | warehouseStd                  | 0                            | 0                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 0                            | -14                           | 0                              | 16                             | 16                         | 0                             | 14                            | warehouse_2                   | 16                           | 16                 |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    When the dd_order identified by dd_o_1 is completed

    Then DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 16             | 10       | ddNetworkLine_1                              | p_1                         | 16             | 0                | 16              |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.DocStatus |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   | CO            |

    And after not more than 60s, metasfresh has this M_Transaction data
      | M_Transaction_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID.Identifier | M_InventoryLine_ID.Identifier | OPT.MovementQty | OPT.MovementType |
      | t_1                         | p_1                     | locator_1               | il_1                          | 16              | I+               |
      | t_2                         | p_1                     | locator_1               | null                          | -16             | M-               |
      | t_3                         | p_1                     | locatorHauptlager       | null                          | 16              | M+               |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier |
      | hu_1               | pi_v_1                        | A        | locatorHauptlager           |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 16  | 16                     | warehouse_2                   |
      | c_2        | DEMAND              | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -14                    | warehouseStd                  |
      | c_3        | SUPPLY              | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -14                    | warehouseStd                  |
      | c_4        | DEMAND              | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     | warehouse_2                   |
      | c_5        | UNEXPECTED_DECREASE | DISTRIBUTION                  | p_1                     | 2022-07-04T08:00:00Z | 16  | 0                      | warehouse_2                   |
      | c_6        | UNEXPECTED_INCREASE | DISTRIBUTION                  | p_1                     | 2022-07-04T08:00:00Z | 16  | 2                      | warehouseStd                  |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 90s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 16                      | 0                            | 2                             | 0                              | 2                              | 2                          | 16                            | 0                             | warehouseStd                  | 0                            | 16                 |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 16                      | 0                       | 0                            | -16                           | 0                              | 0                              | 0                          | 0                             | 16                            | warehouse_2                   | 16                           | 0                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 16             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 16             | DEMAND                |

    And set sys config boolean value false for sys config DDOrder_isCreateMovementOnComplete


  @Id:S0229_300
  @Id:S0264_1000
  @from:cucumber
  Scenario: Create Sales Order for a product of which a demand is solved via distribution. Demand partial available
    Given set sys config boolean value true for sys config DDOrder_isCreateMovementOnComplete
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_28042023_0 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | plv_product_28042023_0 | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | location_1 | bPLocation_1 | bpartner_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_Version_ID |
      | pi_v_1                        | 101                |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Name                        | Value                       | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsInTransit |
      | warehouse_1               | WarehouseTransit_28042023_0 | WarehouseTransit_28042023_0 | bpartner_1                   | location_1                            | true            |
      | warehouse_2               | WarehouseSource_28042023_0  | WarehouseSource_28042023_0  | bpartner_1                   | location_1                            | false           |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locator_1               | Standard_28042023_0 | warehouse_2               |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate         |
      | i_1        | warehouse_2    | 2022-07-04T00:00:00Z |
      | i_2        | warehouseStd   | 2022-07-04T00:00:00Z |
    # HU splitting is currently not supported, so we provide HUs fitting the resulting Quantities of DDOrders
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_11      | i_1                       | p_1                     | PCE          | 8        | 0       |
      | il_12      | i_1                       | p_1                     | PCE          | 8        | 0       |
      | il_21      | i_2                       | p_1                     | PCE          | 6        | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_11                         | hu_1               |
      | il_12                         | hu_2               |
      | il_21                         | hu_3               |

    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | shipper_1               | 540006           |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID.Identifier | Name          | Value          | DocumentNo |
      | ddNetwork_1                          | DDNetworkName | DDNetworkValue | docNo1     |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_1                          | warehouseStd              | warehouse_2                     | shipper_1               |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.DD_NetworkDistribution_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ppln_1     | p_1                     | true         | ddNetwork_1                              | warehouseStd                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Warehouse_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 14         | warehouseStd                  |
    And the order identified by o_1 is completed

    And after not more than 60s, DD_OrderLines are found:
      | DD_OrderLine_ID.Identifier | C_OrderLineSO_ID.Identifier |
      | dd_ol_1                    | ol_1                        |

    And DD_Orders are found:
      | DD_Order_ID.Identifier | DD_OrderLine_ID.Identifier |
      | dd_o_1                 | dd_ol_1                    |

    And DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 8              | 10       | ddNetworkLine_1                              | p_1                         | 8              | 0                | 0               |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 8   | 8                      |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 8   | 16                     |
      | c_3        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 6   | 6                      |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | 14  | -8                     |
      | c_5        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -8                     |
      | c_6        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 8                       | 8                            | -6                            | 0                              | -8                             | -8                         | 8                             | 0                             | warehouseStd                  | 6                            | 6                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 8                       | 0                       | 0                            | -8                            | 0                              | 16                             | 16                         | 0                             | 8                             | warehouse_2                   | 16                           | 16                 |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 8              | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 8              | DEMAND                |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 8   | 8                      |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 8   | 16                     |
      | c_3        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 6   | 6                      |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | 14  | -8                     |
      | c_5        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -8                     |
      | c_6        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 8                       | 8                            | -6                            | 0                              | -8                             | -8                         | 8                             | 0                             | warehouseStd                  | 6                            | 6                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 8                       | 0                       | 0                            | -8                            | 0                              | 16                             | 16                         | 0                             | 8                             | warehouse_2                   | 16                           | 16                 |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 8              | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 8              | DEMAND                |

    When the dd_order identified by dd_o_1 is completed

    Then DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 8              | 10       | ddNetworkLine_1                              | p_1                         | 8              | 0                | 8               |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.DocStatus |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   | CO            |

    And after not more than 60s, metasfresh has this M_Transaction data
      | M_Transaction_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID.Identifier | M_InventoryLine_ID.Identifier | OPT.MovementQty | OPT.MovementType |
      | t_1                         | p_1                     | locator_1               | il_11                         | 8               | I+               |
      | t_2                         | p_1                     | locator_1               | il_12                         | 8               | I+               |
      | t_3                         | p_1                     | locatorHauptlager       | il_21                         | 6               | I+               |
      | t_4                         | p_1                     | locator_1               | null                          | -8              | M-               |
      | t_5                         | p_1                     | locatorHauptlager       | null                          | 8               | M+               |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier |
      | hu_1               | pi_v_1                        | A        | locatorHauptlager           |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 8   | 8                      | warehouse_2                   |
      | c_2        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 8   | 16                     | warehouse_2                   |
      | c_3        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 6   | 6                      | warehouseStd                  |
      | c_4        | DEMAND              | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | 14  | -8                     | warehouseStd                  |
      | c_5        | SUPPLY              | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -8                     | warehouseStd                  |
      | c_6        | DEMAND              | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     | warehouse_2                   |
      | c_7        | UNEXPECTED_DECREASE | DISTRIBUTION                  | p_1                     | 2022-07-04T08:00:00Z | 8   | 8                      | warehouse_2                   |
      | c_8        | UNEXPECTED_INCREASE | DISTRIBUTION                  | p_1                     | 2022-07-04T08:00:00Z | 8   | 0                      | warehouseStd                  |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 8                       | 0                            | -6                            | 0                              | 0                              | 0                          | 8                             | 0                             | warehouseStd                  | 6                            | 14                 |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 8                       | 0                       | 0                            | -8                            | 0                              | 8                              | 8                          | 0                             | 8                             | warehouse_2                   | 16                           | 8                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 8              | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 8              | DEMAND                |

    And set sys config boolean value false for sys config DDOrder_isCreateMovementOnComplete


  @Id:S0229_400
  @Id:S0264_1100
  @from:cucumber
  Scenario: Create Sales Order for a product of which a demand is solved via distribution. Demand fully available
    Given set sys config boolean value true for sys config DDOrder_isCreateMovementOnComplete
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_28042023_1 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | plv_product_28042023_1 | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | location_1 | bPLocation_1 | bpartner_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_Version_ID |
      | pi_v_1                        | 101                |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Name                        | Value                       | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsInTransit |
      | warehouse_1               | WarehouseTransit_28042023_1 | WarehouseTransit_28042023_1 | bpartner_1                   | location_1                            | true            |
      | warehouse_2               | WarehouseSource_28042023_1  | WarehouseSource_28042023_1  | bpartner_1                   | location_1                            | false           |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locator_1               | Standard_28042023_1 | warehouse_2               |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate         |
      | i_1        | warehouse_2    | 2022-07-04T00:00:00Z |
      | i_2        | warehouseStd   | 2022-07-04T00:00:00Z |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_11      | i_1                       | p_1                     | PCE          | 16       | 0       |
      | il_21      | i_2                       | p_1                     | PCE          | 14       | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_11                         | hu_1               |
      | il_21                         | hu_2               |

    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | shipper_1               | 540006           |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID.Identifier | Name          | Value          | DocumentNo |
      | ddNetwork_1                          | DDNetworkName | DDNetworkValue | docNo1     |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_1                          | warehouseStd              | warehouse_2                     | shipper_1               |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.DD_NetworkDistribution_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | ppln_1     | p_1                     | true         | ddNetwork_1                              | warehouseStd                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Warehouse_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 14         | warehouseStd                  |
    And the order identified by o_1 is completed

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 16  | 16                     |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_3        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | 14  | 0                      |

    And set sys config boolean value false for sys config DDOrder_isCreateMovementOnComplete


  @Id:S0229_500
  @Id:S0264_1200
  @from:cucumber
  Scenario: Create Sales Order for a product of which a demand is solved via distribution. Demand partial available Lot for Lot
    Given set sys config boolean value true for sys config DDOrder_isCreateMovementOnComplete
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_06_12_2022 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | plv_product_28042023_2 | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | location_1 | bPLocation_1 | bpartner_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_Version_ID |
      | pi_v_1                        | 101                |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Name                        | Value                       | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsInTransit |
      | warehouse_1               | WarehouseTransit_28042023_2 | WarehouseTransit_28042023_2 | bpartner_1                   | location_1                            | true            |
      | warehouse_2               | WarehouseSource_28042023_2  | WarehouseSource_28042023_2  | bpartner_1                   | location_1                            | false           |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locator_1               | Standard_28042023_2 | warehouse_2               |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate         |
      | i_1        | warehouse_2    | 2022-07-04T00:00:00Z |
      | i_2        | warehouseStd   | 2022-07-04T00:00:00Z |
    # HU splitting is currently not supported, so we provide HUs fitting the resulting Quantities of DDOrders
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_11      | i_1                       | p_1                     | PCE          | 14       | 0       |
      | il_12      | i_1                       | p_1                     | PCE          | 2        | 0       |
      | il_21      | i_2                       | p_1                     | PCE          | 6        | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_11                         | hu_1               |
      | il_12                         | hu_2               |
      | il_21                         | hu_3               |

    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | shipper_1               | 540006           |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID.Identifier | Name          | Value          | DocumentNo |
      | ddNetwork_1                          | DDNetworkName | DDNetworkValue | docNo1     |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_1                          | warehouseStd              | warehouse_2                     | shipper_1               |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.DD_NetworkDistribution_ID.Identifier | OPT.M_Warehouse_ID.Identifier | IsLotForLot |
      | ppln_1     | p_1                     | true         | ddNetwork_1                              | warehouseStd                  | true        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Warehouse_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 14         | warehouseStd                  |
    And the order identified by o_1 is completed

    And after not more than 60s, DD_OrderLines are found:
      | DD_OrderLine_ID.Identifier | C_OrderLineSO_ID.Identifier |
      | dd_ol_1                    | ol_1                        |

    And DD_Orders are found:
      | DD_Order_ID.Identifier | DD_OrderLine_ID.Identifier |
      | dd_o_1                 | dd_ol_1                    |

    And DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 14             | 10       | ddNetworkLine_1                              | p_1                         | 14             | 0                | 0               |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 2   | 16                     |
      | c_3        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 6   | 6                      |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | 14  | -8                     |
      | c_5        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -8                     |
      | c_6        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | -8                             | -8                         | 14                            | 0                             | warehouseStd                  | 6                            | 6                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 0                            | -14                           | 0                              | 16                             | 16                         | 0                             | 14                            | warehouse_2                   | 16                           | 16                 |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |


    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 2   | 16                     |
      | c_3        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 6   | 6                      |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -8                     |
      | c_5        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -8                     |
      | c_6        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | -8                             | -8                         | 14                            | 0                             | warehouseStd                  | 6                            | 6                  |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 0                            | -14                           | 0                              | 16                             | 16                         | 0                             | 14                            | warehouse_2                   | 16                           | 16                 |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    When the dd_order identified by dd_o_1 is completed

    Then DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 14             | 10       | ddNetworkLine_1                              | p_1                         | 14             | 0                | 14              |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.DocStatus |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   | CO            |

    And after not more than 60s, metasfresh has this M_Transaction data
      | M_Transaction_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID.Identifier | M_InventoryLine_ID.Identifier | OPT.MovementQty | OPT.MovementType |
      | t_1                         | p_1                     | locator_1               | il_11                         | 14              | I+               |
      | t_2                         | p_1                     | locator_1               | il_12                         | 2               | I+               |
      | t_3                         | p_1                     | locatorHauptlager       | il_21                         | 6               | I+               |
      | t_4                         | p_1                     | locator_1               | null                          | -14             | M-               |
      | t_5                         | p_1                     | locatorHauptlager       | null                          | 14              | M+               |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier |
      | hu_1               | pi_v_1                        | A        | locatorHauptlager           |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise | OPT.M_Warehouse_ID.Identifier |
      | c_1        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     | warehouse_2                   |
      | c_2        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 2   | 16                     | warehouse_2                   |
      | c_3        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 6   | 6                      | warehouseStd                  |
      | c_4        | DEMAND              | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | -8                     | warehouseStd                  |
      | c_5        | SUPPLY              | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | -8                     | warehouseStd                  |
      | c_6        | DEMAND              | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     | warehouse_2                   |
      | c_7        | UNEXPECTED_DECREASE | DISTRIBUTION                  | p_1                     | 2022-07-04T08:00:00Z | 14  | 2                      | warehouse_2                   |
      | c_8        | UNEXPECTED_INCREASE | DISTRIBUTION                  | p_1                     | 2022-07-04T08:00:00Z | 14  | 6                      | warehouseStd                  |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 0                            | 0                             | 0                              | 6                              | 6                          | 14                            | 0                             | warehouseStd                  | 6                            | 20                 |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 0                            | -14                           | 0                              | 2                              | 2                          | 0                             | 14                            | warehouse_2                   | 16                           | 2                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    And set sys config boolean value false for sys config DDOrder_isCreateMovementOnComplete


  @Id:S0229_600
  @Id:S0264_1300
  @from:cucumber
  Scenario: Create Sales Order for a product of which a demand is solved via distribution. Demand fully available Lot for Lot
    Given set sys config boolean value true for sys config DDOrder_isCreateMovementOnComplete
    And metasfresh contains M_Products:
      | Identifier |
      | p_1        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_06_12_2022 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_1      | pl_1                      | plv_product_28042023_3 | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_1 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN          | C_BPartner_ID.Identifier |
      | location_1 | bPLocation_1 | bpartner_1               |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_Version_ID |
      | pi_v_1                        | 101                |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Name                        | Value                       | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.IsInTransit |
      | warehouse_1               | WarehouseTransit_28042023_3 | WarehouseTransit_28042023_3 | bpartner_1                   | location_1                            | true            |
      | warehouse_2               | WarehouseSource_28042023_3  | WarehouseSource_28042023_3  | bpartner_1                   | location_1                            | false           |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value               | M_Warehouse_ID.Identifier |
      | locator_1               | Standard_28042023_3 | warehouse_2               |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate         |
      | i_1        | warehouse_2    | 2022-07-04T00:00:00Z |
      | i_2        | warehouseStd   | 2022-07-04T00:00:00Z |
    # HU splitting is currently not supported, so we provide HUs fitting the resulting Quantities of DDOrders
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | il_11      | i_1                       | p_1                     | PCE          | 14       | 0       |
      | il_12      | i_1                       | p_1                     | PCE          | 2        | 0       |
      | il_21      | i_2                       | p_1                     | PCE          | 14       | 0       |
    And the inventory identified by i_1 is completed
    And the inventory identified by i_2 is completed

    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | il_11                         | hu_1               |
      | il_12                         | hu_2               |
      | il_21                         | hu_3               |

    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.M_Shipper_ID |
      | shipper_1               | 540006           |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID.Identifier | Name          | Value          | DocumentNo |
      | ddNetwork_1                          | DDNetworkName | DDNetworkValue | docNo1     |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistributionLine_ID.Identifier | DD_NetworkDistribution_ID.Identifier | M_Warehouse_ID.Identifier | M_WarehouseSource_ID.Identifier | M_Shipper_ID.Identifier |
      | ddNetworkLine_1                          | ddNetwork_1                          | warehouseStd              | warehouse_2                     | shipper_1               |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.DD_NetworkDistribution_ID.Identifier | OPT.M_Warehouse_ID.Identifier | IsLotForLot |
      | ppln_1     | p_1                     | true         | ddNetwork_1                              | warehouseStd                  | true        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | bpartner_1               | 2022-07-04  | 2022-07-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Warehouse_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 14         | warehouseStd                  |
    And the order identified by o_1 is completed

    And after not more than 60s, DD_OrderLines are found:
      | DD_OrderLine_ID.Identifier | C_OrderLineSO_ID.Identifier |
      | dd_ol_1                    | ol_1                        |

    And DD_Orders are found:
      | DD_Order_ID.Identifier | DD_OrderLine_ID.Identifier |
      | dd_o_1                 | dd_ol_1                    |

    And DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 14             | 10       | ddNetworkLine_1                              | p_1                         | 14             | 0                | 0               |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 2   | 16                     |
      | c_3        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | 0                      |
      | c_5        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      |
      | c_6        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | 0                              | 0                          | 14                            | 0                             | warehouseStd                  | 14                           | 14                 |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 0                            | -14                           | 0                              | 16                             | 16                         | 0                             | 14                            | warehouse_2                   | 16                           | 16                 |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_2        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 2   | 16                     |
      | c_3        | INVENTORY_UP      |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_4        | DEMAND            | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | 0                      |
      | c_5        | SUPPLY            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      |
      | c_6        | DEMAND            | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 14                           | 0                             | 0                              | 0                              | 0                          | 14                            | 0                             | warehouseStd                  | 14                           | 14                 |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 0                            | -14                           | 0                              | 16                             | 16                         | 0                             | 14                            | warehouse_2                   | 16                           | 16                 |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    When the dd_order identified by dd_o_1 is completed

    Then DD_OrderLines are validated:
      | DD_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.Line | OPT.DD_NetworkDistributionLine_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyEntered | OPT.QtyDelivered | OPT.QtyReserved |
      | dd_ol_1                    | 14             | 10       | ddNetworkLine_1                              | p_1                         | 14             | 0                | 14              |

    And DD_Orders are validated:
      | DD_Order_ID.Identifier | OPT.M_Warehouse_ID.Identifier | OPT.M_Warehouse_From_ID.Identifier | OPT.M_Warehouse_To_ID.Identifier | OPT.M_Shipper_ID.Identifier | OPT.DocStatus |
      | dd_o_1                 | warehouse_1                   | warehouse_2                        | warehouseStd                     | shipper_1                   | CO            |

    And after not more than 60s, metasfresh has this M_Transaction data
      | M_Transaction_ID.Identifier | M_Product_ID.Identifier | M_Locator_ID.Identifier | M_InventoryLine_ID.Identifier | OPT.MovementQty | OPT.MovementType |
      | t_1                         | p_1                     | locator_1               | il_11                         | 14              | I+               |
      | t_2                         | p_1                     | locator_1               | il_12                         | 2               | I+               |
      | t_3                         | p_1                     | locatorHauptlager       | il_21                         | 14              | I+               |
      | t_4                         | p_1                     | locator_1               | null                          | -14             | M-               |
      | t_5                         | p_1                     | locatorHauptlager       | null                          | 14              | M+               |

    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID.Identifier | HUStatus | OPT.M_Locator_ID.Identifier |
      | hu_1               | pi_v_1                        | A        | locatorHauptlager           |

    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty | Qty_AvailableToPromise |
      | c_1        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_2        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 2   | 16                     |
      | c_3        | INVENTORY_UP        |                               | p_1                     | 2022-07-04T00:00:00Z | 14  | 14                     |
      | c_4        | DEMAND              | SHIPMENT                      | p_1                     | 2022-07-04T00:00:00Z | -14 | 0                      |
      | c_5        | SUPPLY              | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 0                      |
      | c_6        | DEMAND              | DISTRIBUTION                  | p_1                     | 2022-07-04T00:00:00Z | 0   | 16                     |
      | c_7        | UNEXPECTED_DECREASE | DISTRIBUTION                  | p_1                     | 2022-07-04T08:00:00Z | 14  | 2                      |
      | c_8        | UNEXPECTED_INCREASE | DISTRIBUTION                  | p_1                     | 2022-07-04T08:00:00Z | 14  | 14                     |

    And after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtyDemand_SalesOrder_AtDate | OPT.QtyDemandSum_AtDate | OPT.QtySupplySum_AtDate | OPT.QtySupplyRequired_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtySupplyToSchedule_AtDate | OPT.MDCandidateQtyStock_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtySupply_DD_Order_AtDate | OPT.QtyDemand_DD_Order_AtDate | OPT.M_Warehouse_ID.Identifier | OPT.QtyInventoryCount_AtDate | OPT.QtyStockChange |
      | cp_1       | p_1                     | 2022-07-04  |                              | 14                              | 14                      | 14                      | 0                            | 0                             | 0                              | 14                             | 14                         | 14                            | 0                             | warehouseStd                  | 14                           | 28                 |
      | cp_2       | p_1                     | 2022-07-04  |                              | 0                               | 14                      | 0                       | 0                            | -14                           | 0                              | 2                              | 2                          | 0                             | 14                            | warehouse_2                   | 16                           | 2                  |

    And after not more than 60s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 14             | 14              |

    And after not more than 60s, metasfresh has this MD_Cockpit_DDOrder_Detail data
      | MD_Cockpit_DDOrder_Detail_ID.Identifier | DD_OrderLine_ID.Identifier | M_Warehouse_ID.Identifier | MD_Cockpit_ID.Identifier | OPT.QtyPending | OPT.DDOrderDetailType |
      | dd_d_1                                  | dd_ol_1                    | warehouseStd              | cp_1                     | 14             | SUPPLY                |
      | dd_d_2                                  | dd_ol_1                    | warehouse_2               | cp_2                     | 14             | DEMAND                |

    And set sys config boolean value false for sys config DDOrder_isCreateMovementOnComplete
