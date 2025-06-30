@from:cucumber
@ghActions:run_on_executor3
Feature: Shipment DeliveryDateRule

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | DeliveryDateRuleTest |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                 | Value                |
      | ps_1       | DeliveryDateRuleTest | DeliveryDateRuleTest |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_1       | ps_1                          | DE                        | EUR                 | DeliveryDateRuleTest | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV18032022_12 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer18032022_12 | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |


  @Id:S0470.1_100
  @from:cucumber
  Scenario: Shipment DeliveryDateRule on generate shipments - PreparationDate as movementDate
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.PreparationDate     | OPT.POReference        |
      | o_1        | true    | endcustomer_1            | 2021-04-16  | 1000012              | 2021-04-18T21:00:00.00Z | DeliveryDateRuleTest_1 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments |
      | s_s_1                            | D            | Y                   |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.MovementDate | OPT.POReference        |
      | s_1                   | endcustomer_1            | l_1                               | 2021-04-16  | true      | CO        | 2021-04-18       | DeliveryDateRuleTest_1 |

  @Id:S0470.1_200
  @from:cucumber
  Scenario: Shipment DeliveryDateRule on generate shipments - Today as movementDate if PreparationDate is in the past
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.PreparationDate     | OPT.POReference        |
      | o_2        | true    | endcustomer_1            | 2021-04-10  | 1000012              | 2021-04-12T21:00:00.00Z | DeliveryDateRuleTest_2 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | o_2                   | p_1                     | 10         |
    When the order identified by o_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_2      | ol_2                      | N             |
    And 'generate shipments' process is invoked
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments |
      | s_2                   | s_s_2                            | D            | Y                   |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_2                            | s_2                   |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.MovementDate | OPT.POReference        |
      | s_2                   | endcustomer_1            | l_1                               | 2021-04-10  | true      | CO        | 2021-04-16       | DeliveryDateRuleTest_2 |

  @Id:S0470.1_300
  @from:cucumber
  Scenario: Shipment DeliveryDateRule on generate shipments - Today as movementDate
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.PreparationDate     | OPT.POReference        |
      | o_3        | true    | endcustomer_1            | 2021-04-10  | 1000012              | 2021-04-12T21:00:00.00Z | DeliveryDateRuleTest_3 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_3       | o_3                   | p_1                     | 10         |
    When the order identified by o_3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_3      | ol_3                      | N             |
    And 'generate shipments' process is invoked
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | OPT.IsShipToday |
      | s_3                   | s_s_3                            | D            | Y                   | Y               |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_3                            | s_3                   |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.MovementDate | OPT.POReference        |
      | s_3                   | endcustomer_1            | l_1                               | 2021-04-10  | true      | CO        | 2021-04-16       | DeliveryDateRuleTest_3 |

  @Id:S0470.1_400
  @from:cucumber
  Scenario: Shipment DeliveryDateRule on generate shipments - PreparationDate even if in the past as movementDate
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.PreparationDate     | OPT.POReference        |
      | o_4        | true    | endcustomer_1            | 2021-04-10  | 1000012              | 2021-04-12T21:00:00.00Z | DeliveryDateRuleTest_4 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_4       | o_4                   | p_1                     | 10         |
    When the order identified by o_4 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_4      | ol_4                      | N             |
    And 'generate shipments' process is invoked
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | OPT.IsShipDateDeliveryDate |
      | s_4                   | s_s_4                            | D            | Y                   | Y                          |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_4                            | s_4                   |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.MovementDate | OPT.POReference        |
      | s_4                   | endcustomer_1            | l_1                               | 2021-04-10  | true      | CO        | 2021-04-12       | DeliveryDateRuleTest_4 |


  @Id:S0470.1_500
  @from:cucumber
  Scenario: Shipment DeliveryDateRule on generate shipments - FixedDate as movementDate
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.PreparationDate     | OPT.POReference        |
      | o_5        | true    | endcustomer_1            | 2021-04-10  | 1000012              | 2021-04-12T21:00:00.00Z | DeliveryDateRuleTest_5 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_5       | o_5                   | p_1                     | 10         |
    When the order identified by o_5 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_5      | ol_5                      | N             |
    And 'generate shipments' process is invoked
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | OPT.FixedShipmentDate |
      | s_5                   | s_s_5                            | D            | Y                   | 2021-04-13            |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_5                            | s_5                   |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.MovementDate | OPT.POReference        |
      | s_5                   | endcustomer_1            | l_1                               | 2021-04-10  | true      | CO        | 2021-04-13       | DeliveryDateRuleTest_5 |

  @Id:S0470.2_100
  @from:cucumber
  Scenario: Shipment DeliveryDateRule on split shipments
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.PreparationDate     | OPT.POReference        |
      | o_6        | true    | endcustomer_1            | 2021-04-16  | 1000012              | 2021-04-18T21:00:00.00Z | DeliveryDateRuleTest_6 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_6       | o_6                   | p_1                     | 100        |
    When the order identified by o_6 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_6      | ol_6                      | N             |
    And set sys config String value D for sys config de.metas.ui.web.split_shipment.SplitShipmentView_ProcessAllRows.ShipDateRule
    And the shipment schedule is split
      | Identifier | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | C_UOM_ID.X12DE355 | DeliveryDate |
      | spl_1      | s_s_6                            | 1            | PCE               | 2021-04-14   |
      | spl_2      | s_s_6                            | 2            | PCE               | 2021-04-18   |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType   | IsCompleteShipments |
      | s_s_6                            | SPLIT_SHIPMENT | true                |

    And after not more than 60s, split inOutId is set:
    | M_ShipmentSchedule_Split_ID.Identifier |
    | spl_1                                  |
    | spl_2                                  |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.M_ShipmentSchedule_Split_ID.Identifier |
      | s_s_6                            | s_6_1                 | spl_1                                      |
      | s_s_6                            | s_6_2                 | spl_2                                      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.MovementDate | OPT.POReference        |
      | s_6_1                 | endcustomer_1            | l_1                               | 2021-04-16  | true      | CO        | 2021-04-14       | DeliveryDateRuleTest_6 |
      | s_6_2                 | endcustomer_1            | l_1                               | 2021-04-16  | true      | CO        | 2021-04-18       | DeliveryDateRuleTest_6 |

    When set sys config String value T for sys config de.metas.ui.web.split_shipment.SplitShipmentView_ProcessAllRows.ShipDateRule

    And the shipment schedule is split
      | Identifier | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | C_UOM_ID.X12DE355 | DeliveryDate |
      | spl_3      | s_s_6                            | 3            | PCE               | 2021-04-14   |
      | spl_4      | s_s_6                            | 4            | PCE               | 2021-04-18   |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType   | IsCompleteShipments |
      | s_s_6                            | SPLIT_SHIPMENT | true                |

    And after not more than 60s, split inOutId is set:
      | M_ShipmentSchedule_Split_ID.Identifier |
      | spl_3                                  |
      | spl_4                                  |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.M_ShipmentSchedule_Split_ID.Identifier |
      | s_s_6                            | s_6_3                 | spl_3                                      |
      | s_s_6                            | s_6_4                 | spl_4                                      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.MovementDate | OPT.POReference        |
      | s_6_3                 | endcustomer_1            | l_1                               | 2021-04-16  | true      | CO        | 2021-04-16       | DeliveryDateRuleTest_6 |
      | s_6_4                 | endcustomer_1            | l_1                               | 2021-04-16  | true      | CO        | 2021-04-16       | DeliveryDateRuleTest_6 |

    When set sys config String value O for sys config de.metas.ui.web.split_shipment.SplitShipmentView_ProcessAllRows.ShipDateRule

    And the shipment schedule is split
      | Identifier | M_ShipmentSchedule_ID.Identifier | QtyToDeliver | C_UOM_ID.X12DE355 | DeliveryDate |
      | spl_5      | s_s_6                            | 5            | PCE               | 2021-04-14   |
      | spl_6      | s_s_6                            | 6            | PCE               | 2021-04-18   |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType   | IsCompleteShipments |
      | s_s_6                            | SPLIT_SHIPMENT | true                |

    And after not more than 60s, split inOutId is set:
      | M_ShipmentSchedule_Split_ID.Identifier |
      | spl_5                                  |
      | spl_6                                  |

    And after not more than 600s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.M_ShipmentSchedule_Split_ID.Identifier |
      | s_s_6                            | s_6_5                 | spl_5                                      |
      | s_s_6                            | s_6_6                 | spl_6                                      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus | OPT.MovementDate | OPT.POReference        |
      | s_6_5                 | endcustomer_1            | l_1                               | 2021-04-16  | true      | CO        | 2021-04-16       | DeliveryDateRuleTest_6 |
      | s_6_6                 | endcustomer_1            | l_1                               | 2021-04-16  | true      | CO        | 2021-04-18       | DeliveryDateRuleTest_6 |

    And set sys config String value D for sys config de.metas.ui.web.split_shipment.SplitShipmentView_ProcessAllRows.ShipDateRule

