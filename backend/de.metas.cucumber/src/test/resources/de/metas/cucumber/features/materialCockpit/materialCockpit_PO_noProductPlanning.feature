@from:cucumber
@ghActions:run_on_executor6
Feature: purchase order interaction with material cockpit - no product planning

  Background: Initial Data
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2021-04-16T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @Id:S0189_1200
  @from:cucumber
  Scenario: PO with qty = 10, no ASI
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22092022_1 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_1 | pricing_system_value_22092022_1 | pricing_system_description_22092022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_1 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_1 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_1 | Y            | N              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-02  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

  @Id:S0189_1300
  @from:cucumber
  Scenario: PO with qty = 10, no ASI, reactivated, changed the qty to 20
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22092022_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_2 | pricing_system_value_22092022_2 | pricing_system_description_22092022_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_2 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_2 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_2 | Y            | N              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.C_OrderLine_ID.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | ol_1                          | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    When the order identified by o_1 is reactivated

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.C_OrderLine_ID.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | ol_1                          | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |

    And metasfresh has no MD_Cockpit_DocumentDetail data
      | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier |
      | cp_1                     | ol_1                      |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1                      | 20             |
    When the order identified by o_1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 20                                 | 20                      | 20                         | 20                            | 0                            | 20                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 20             | 20              |

  @Id:S0189_1400
  @from:cucumber
  Scenario: 2 POs, each with qty = 10, no ASI, same product
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22092022_3 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_3 | pricing_system_value_22092022_3 | pricing_system_description_22092022_3 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_3 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_3 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_3 | Y            | Y              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_2        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_1                     | 10         |
    And the order identified by o_2 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 20                                 | 20                      | 20                         | 20                            | 0                            | 20                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |
      | cp_dd_2                                 | cp_1                     | ol_2                      | 10             | 10              |

  @Id:S0189_1500
  @from:cucumber
  Scenario: 2 POs, each with qty = 10, no ASI, different product
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22092022_4 |
      | p_2        | purchaseProduct_22092022_5 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_4 | pricing_system_value_22092022_4 | pricing_system_description_22092022_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_4 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_4 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_1       | plv_1                             | p_2                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_4 | Y            | Y              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_2        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_2                     | 10         |
    When the order identified by o_2 is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
      | cp_2       | p_2                     | 2021-04-16  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |
      | cp_dd_2                                 | cp_2                     | ol_2                      | 10             | 10              |

  @Id:S0189_1600
  @from:cucumber
  Scenario: PO with qty = 10 and ASI
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22092022_6 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_6 | pricing_system_value_22092022_6 | pricing_system_description_22092022_6 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_6 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_6 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_6 | Y            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "lineASI":
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
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | lineASI                                  |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI                      | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

  @Id:S0189_1700
  @from:cucumber
  Scenario: 2 POs with qty = 10 and different ASI
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22092022_7 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_7 | pricing_system_value_22092022_7 | pricing_system_description_22092022_7 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_7 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_7 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_7 | Y            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "lineASI_1":
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
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | lineASI_1                                |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And metasfresh contains M_AttributeSetInstance with identifier "lineASI_2":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000015",
          "valueStr":"03"
      }
    ]
  }
  """
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_2        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_2       | o_2                   | p_1                     | 10         | lineASI_2                                |
    When the order identified by o_2 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
      | cp_2       | p_1                     | 2021-04-16  | lineASI_2                    | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |
      | cp_dd_2                                 | cp_2                     | ol_2                      | 10             | 10              |

  @Id:S0189_1800
  @from:cucumber
  Scenario: PO with 2 lines (qty=10, same product) and different ASIs, reactivated, changed ASI to the same one
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22092022_8 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_8 | pricing_system_value_22092022_8 | pricing_system_description_22092022_8 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_8 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_8 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_8 | Y            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "lineASI_1":
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
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | lineASI_1                                |
      | ol_2       | o_1                   | p_1                     | 10         |                                          |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
      | cp_2       | p_1                     | 2021-04-16  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |
      | cp_dd_2                                 | cp_2                     | ol_2                      | 10             | 10              |

    When the order identified by o_1 is reactivated

    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.C_OrderLine_ID.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                     | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |
      | cp_2       | p_1                     | 2021-04-16  |                               | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |

    And metasfresh has no MD_Cockpit_DocumentDetail data
      | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier |
      | cp_1                     | ol_1                      |
      | cp_2                     | ol_2                      |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_2                      | lineASI_1                                |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 20                                 | 20                      | 20                         | 20                            | 0                            | 20                                  |
      | cp_2       | p_1                     | 2021-04-16  |                              | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |
      | cp_dd_2                                 | cp_1                     | ol_2                      | 10             | 10              |

  @Id:S0189_1900
  @from:cucumber
  Scenario: 2 POs with qty = 10 and same ASI
    Given metasfresh contains M_Products:
      | Identifier | Name                       |
      | p_1        | purchaseProduct_22092022_9 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_9 | pricing_system_value_22092022_9 | pricing_system_description_22092022_9 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_9 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_9 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_9 | Y            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "lineASI_1":
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
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1       | o_1                   | p_1                     | 10         | lineASI_1                                |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_2        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_2       | o_2                   | p_1                     | 10         | lineASI_1                                |
    When the order identified by o_2 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 20                                 | 20                      | 20                         | 20                            | 0                            | 20                                  |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |
      | cp_dd_2                                 | cp_1                     | ol_2                      | 10             | 10              |

  @Id:S0189_2000
  @from:cucumber
  Scenario: PO with 1 line (qty=10) and ASI, reactivated, changed ASI and qty=12
    Given metasfresh contains M_Products:
      | Identifier | Name                        |
      | p_1        | purchaseProduct_22092022_10 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                            | OPT.Description                        | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_10 | pricing_system_value_22092022_10 | pricing_system_description_22092022_10 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                        | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_10 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_22092022_10 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_10 | Y            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "lineASI_1":
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
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    When the order identified by o_1 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1                      | lineASI_1                                |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
      | cp_2       | p_1                     | 2021-04-16  |                              | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    When the order identified by o_1 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | ol_1                      | 12             |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 12                                 | 12                      | 12                         | 12                            | 0                            | 12                                  |
      | cp_2       | p_1                     | 2021-04-16  |                              | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 12             | 12              |

  @Id:S0189_2100
  @from:cucumber
  Scenario: PO with qty = 10, no ASI, reactivated, changed the date promised
    Given metasfresh contains M_Products:
      | Identifier | Name                        |
      | p_1        | purchaseProduct_22092022_11 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                            | OPT.Description                        | OPT.IsActive |
      | ps_1       | pricing_system_name_22092022_11 | pricing_system_value_22092022_11 | pricing_system_description_22092022_11 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                        | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22092022_11 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                          | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_22092022_11 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_22092022_11 | Y            | N              | ps_1                          |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-15T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-15  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    When the order identified by o_1 is reactivated
    And update order
      | C_Order_ID.Identifier | OPT.DatePromised     |
      | o_1                   | 2021-04-17T00:00:00Z |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-17  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
      | cp_2       | p_1                     | 2021-04-15  |                              | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

  @Id:S0189_2200
  @from:cucumber
  Scenario: PO with 1 lines (qty=10) and ASI, reactivated, changed ASI and qty=12
    Given metasfresh contains M_Products:
      | Identifier | Name                        |
      | p_1        | purchaseProduct_23092022_01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                            | OPT.Description                        | OPT.IsActive |
      | ps_1       | pricing_system_name_23092022_01 | pricing_system_value_23092022_01 | pricing_system_description_23092022_01 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                        | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_23092022_01 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_23092022_01 | 2020-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endBPartner_1 | EndPartner_23092022_01 | Y            | Y              | ps_1                          |
    And metasfresh contains M_AttributeSetInstance with identifier "lineASI_1":
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
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DatePromised     |
      | o_1        | false   | endBPartner_1            | 2021-12-01  | POO             | 2021-04-16T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  |                              | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    When the order identified by o_1 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_1                      | lineASI_1                                |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 10                                 | 10                      | 10                         | 10                            | 0                            | 10                                  |
      | cp_2       | p_1                     | 2021-04-16  |                              | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |
    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 10             | 10              |

    When the order identified by o_1 is reactivated
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier | OPT.QtyEntered |
      | ol_1                      | lineASI_1                                | 8              |
    When the order identified by o_1 is completed
    Then after not more than 120s, metasfresh has this MD_Cockpit data
      | Identifier | M_Product_ID.Identifier | DateGeneral | OPT.AttributesKey.Identifier | OPT.QtySupply_PurchaseOrder_AtDate | OPT.QtySupplySum_AtDate | OPT.QtyStockCurrent_AtDate | OPT.QtyExpectedSurplus_AtDate | OPT.QtyInventoryCount_AtDate | OPT.QtyOrdered_PurchaseOrder_AtDate |
      | cp_1       | p_1                     | 2021-04-16  | lineASI_1                    | 8                                  | 8                       | 8                          | 8                             | 0                            | 8                                   |
      | cp_2       | p_1                     | 2021-04-16  |                              | 0                                  | 0                       | 0                          | 0                             | 0                            | 0                                   |

    And after not more than 120s, metasfresh has this MD_Cockpit_DocumentDetail data
      | MD_Cockpit_DocumentDetail_ID.Identifier | MD_Cockpit_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyOrdered | OPT.QtyReserved |
      | cp_dd_1                                 | cp_1                     | ol_1                      | 8              | 8               |