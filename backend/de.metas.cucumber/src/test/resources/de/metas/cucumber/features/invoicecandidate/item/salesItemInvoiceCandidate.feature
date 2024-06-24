@from:cucumber
@ghActions:run_on_executor6
Feature: Product items invoice candidates: shipments

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    When load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                 |
      | p_1        | product_03082022-SIC |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value                | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name | pricing_system_value | pricing_system_description | true         |
    And metasfresh contains C_BPartners:
      | Identifier    | Name        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer | N            | Y              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    When metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_03082022-SIC | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |

  @Id:03082022-SIC.100
  @from:cucumber
  Scenario: Ship 100, complete shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | s_ol_1                           | D            | false               | false       | 100                                              |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | false     | DR        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | false     | ol_1                          | 100            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |

    When the shipment identified by shipment_1 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

  @Id:03082022-SIC.110
  @from:cucumber
  Scenario: Ship 100, complete shipment then reactivate it
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | s_ol_1                           | D            | false               | false       | 100                                              |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |

    When the shipment identified by shipment_1 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | true      | ol_1                          | 100            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

    When the shipment identified by shipment_1 is reactivated

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | false     | IP        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

  @Id:03082022-SIC.120
  @from:cucumber
  Scenario: Ship 100, complete shipment, reactivate it, complete it again

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | s_ol_1                           | D            | false               | false       | 100                                              |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |

    When the shipment identified by shipment_1 is completed

    And the shipment identified by shipment_1 is reactivated

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 0            | 0           | 10    | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | false     | IP        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | false     | ol_1                          | 100            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

    When the shipment identified by shipment_1 is completed

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

  @Id:03082022-SIC.121
  @from:cucumber
  Scenario: Ship 100, complete shipment, reactivate it, increase qty, complete it again
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | s_ol_1                           | D            | false               | false       | 100                                              |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |

    When the shipment identified by shipment_1 is completed

    And the shipment identified by shipment_1 is reactivated

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 0            | 0           | 10    | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | false     | IP        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | false     | ol_1                          | 100            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

    Then update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_1            | 200        | 200         |

    When the shipment identified by shipment_1 is completed

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 200              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 200              |

  @Id:03082022-SIC.122
  @from:cucumber
  Scenario: Ship 100, complete shipment, reactivate it, decrease qty, complete it again
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | s_ol_1                           | D            | false               | false       | 100                                              |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |

    When the shipment identified by shipment_1 is completed

    And the shipment identified by shipment_1 is reactivated

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 0            | 0           | 10    | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | false     | IP        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | false     | ol_1                          | 100            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

    Then update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_1            | 50         | 50          |

    When the shipment identified by shipment_1 is completed

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 50               |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 50               |

  @Id:03082022-SIC.130
  @from:cucumber
  Scenario: Ship 100, complete shipment then void it
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | s_ol_1                           | D            | false               | false       | 100                                              |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |

    When the shipment identified by shipment_1 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | true      | ol_1                          | 100            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

    When the shipment identified by shipment_1 is reactivated

    And the shipment identified by shipment_1 is voided

    Then validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | VO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

  @Id:03082022-SIC.140
  @from:cucumber
  Scenario: Ship 100, complete shipment then revert it
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | s_ol_1                           | D            | false               | false       | 100                                              |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |

    When the shipment identified by shipment_1 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | true      | ol_1                          | 100            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

    When the shipment identified by shipment_1 is reversed

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | RE        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

  @Id:03082022-SIC.150
  @from:cucumber
  Scenario: Ship 100, complete shipment then close it
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | s_ol_1                           | D            | false               | false       | 100                                              |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |

    When the shipment identified by shipment_1 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CO        |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | p_1                     | 100         | true      | ol_1                          | 100            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

    When the shipment identified by shipment_1 is closed

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | shipment_1            | endcustomer_1            | l_1                               | 2021-04-17  | true      | CL        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |