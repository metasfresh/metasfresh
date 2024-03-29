@from:cucumber
@ghActions:run_on_executor3
Feature: Packing material invoice candidates: shipments

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-07-26T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Products:
      | Identifier     | Name                        |
      | salesProduct   | salesProduct_S0160_@Date@   |
      | packingProduct | packingProduct_S0160_@Date@ |
    And metasfresh contains M_PricingSystems
      | Identifier | Value        |
      | ps_1       | S0160_@Date@ |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_1                          | DE                        | EUR                 | price_list_name_SO | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | plv_SO     | pl_SO                     | salesOrder-PLV | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_SO                            | salesProduct            | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_SO                            | packingProduct          | 2.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name            | IsCustomer | M_PricingSystem_ID | InvoiceRule |
      | bpartner_1 | BP_S0160_@Date@ | Y          | ps_1               | D           |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name              |
      | huPackingTU           | huPackingTU_S0160 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                   | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU_S0160 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                    | OPT.M_Product_ID.Identifier |
      | huPackingMaterial                  | HUPackingMaterial_S0160 | packingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemTU                 | packingVersionTU              | 0   | PM       | huPackingMaterial                      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemSalesProduct               | huPiItemTU                 | salesProduct            | 10  | 2022-07-01 |


  @from:cucumber
  @Id:S0160.2_100
  Scenario: order product w/ packing material then create and complete shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 10               | 10           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |

  @from:cucumber
  @Id:S0160.2_110
  Scenario: order product w/ packing material, decrease qty for shipment then complete it
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |


    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 9          | 9           |

    When the shipment identified by shipment_1 is completed


    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 9                | 9            | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 9                |

  @from:cucumber
  @Id:S0160.2_120
  Scenario: order product w/ packing material, increase qty for shipment then complete it
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 15         | 15          |

    When the shipment identified by shipment_1 is completed


    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 15               | 15           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |


  @from:cucumber
  @Id:S0160.2_130
  Scenario: order product w/ packing material, create and complete shipment then reactivate it
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 10               | 10           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_140
  Scenario: order product w/ packing material, decrease qty for shipment, complete it then reactivate shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 9          | 9           |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 9                | 9            | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 9                |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_150
  Scenario: order product w/ packing material, increase qty for shipment, complete it then reactivate shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.rocessed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false        |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 15         | 15          |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 15               | 15           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_160
  Scenario: order product w/ packing material, create and complete shipment, reactivate it then complete it again
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 10               | 10           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |

    When the shipment identified by shipment_1 is completed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 100              | 100          | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 10               | 10           | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |


  @from:cucumber
  @Id:S0160.2_170
  Scenario: order product w/ packing material, decrease qty for shipment, complete it then reactivate it and complete shipment again
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 9          | 9           |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 9                | 9            | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 9                |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |

    When the shipment identified by shipment_1 is completed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 100              | 100          | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 9                | 9            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 9                |


  @from:cucumber
  @Id:S0160.2_180
  Scenario: order product w/ packing material, increase qty for shipment, complete it then reactivate it and complete shipment again
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 15         | 15          |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 15               | 15           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    # note that the next step *first* waits for the ICs to be updated
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |

    When the shipment identified by shipment_1 is completed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 100              | 100          | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 15               | 15           | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |


  @from:cucumber
  @Id:S0160.2_190
  Scenario: order product w/ packing material, create and complete and reactivate shipment, reset packingLines then complete it again
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            | ol_1                          |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             | null                          |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 15         | 15          |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 15               | 15           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |

    When reset M_InOut packing lines for shipment shipment_1

    Then validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_3            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_3                     | o_1                       | null                      | 10               | 10           | shipmentLine_3                |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_3                  | invoiceCand_3                         | shipmentLine_3                | 10               |


  @from:cucumber
  @Id:S0160.2_191
  Scenario: order product w/ packing material, create and complete shipment, reactivate it, increase qty then complete it again
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 10               | 10           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 20         | 20          |

    When the shipment identified by shipment_1 is completed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 100              | 100          | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 20               | 20           | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 20               |


  @from:cucumber
  @Id:S0160.2_192
  Scenario: order product w/ packing material, create and complete shipment, reactivate it, decrease qty then complete it again
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 10               | 10           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 5          | 5           |

    When the shipment identified by shipment_1 is completed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 100              | 100          | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 5                | 5            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 5                |


  @from:cucumber
  @Id:S0160.2_200
  Scenario: order product w/ packing material, create and complete shipment then reactivate and void it
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 10               | 10           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |

    When the shipment identified by shipment_1 is voided


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_210
  Scenario: order product w/ packing material, decrease qty for shipment, complete it then reactivate and void shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed


    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 9          | 9           |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 9                | 9            | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 9                |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |

    When the shipment identified by shipment_1 is voided


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_220
  Scenario: order product w/ packing material, increase qty for shipment, complete it then reactivate and void shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 15         | 15          |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 15               | 15           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |

    When the shipment identified by shipment_1 is reactivated


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | IP        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |

    When the shipment identified by shipment_1 is voided


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | VO        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_230
  Scenario: order product w/ packing material, create and complete shipment then revert it
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 10               | 10           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |

    When the shipment identified by shipment_1 is reversed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | RE        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_240
  Scenario: order product w/ packing material, decrease qty for shipment, complete it then revert shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 9          | 9           |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 9                | 9            | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 9                |

    When the shipment identified by shipment_1 is reversed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | RE        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_250
  Scenario: order product w/ packing material, increase qty for shipment, complete it then revert shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 15         | 15          |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 15               | 15           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |

    When the shipment identified by shipment_1 is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | RE        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 0                | 0            | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 0                | 0            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 0                |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 0                |


  @from:cucumber
  @Id:S0160.2_260
  Scenario: order product w/ packing material, create and complete shipment then close it
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 10               | 10           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |

    When the shipment identified by shipment_1 is closed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CL        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 100              | 100          | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 10               | 10           | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 10               |


  @from:cucumber
  @Id:S0160.2_270
  Scenario: order product w/ packing material, decrease qty for shipment, complete it then close shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 9          | 9           |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 9                | 9            | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 9                |

    When the shipment identified by shipment_1 is closed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CL        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 100              | 100          | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 9                | 9            | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 9                |


  @from:cucumber
  @Id:S0160.2_280
  Scenario: order product w/ packing material, increase qty for shipment, complete it then close shipment
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | bpartner_1               | 2022-07-26  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | salesProduct            | 100        | huPiItemSalesProduct                   |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2022-07-26  | salesProduct            | 0            | 100        | 0           | 10    | 0        | EUR          | true      |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 2     | 0        | EUR          | true      |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver | OPT.QtyDelivered | OPT.QtyOrdered | OPT.QtyOrdered_TU | OPT.Processed |
      | s_s_1                            | 100              | 0                | 100            | 10                | false         |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |


    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | s_s_1                            | shipment_1            | DR            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | shipmentLine_1            | shipment_1            | salesProduct            | 100         | false     | 100            |
      | shipmentLine_2            | shipment_1            | packingProduct          | 10          | false     | 10             |
    And there is no C_InvoiceCandidate_InOutLine for M_InOut_Line: shipmentLine_2
    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | shipmentLine_2            | 15         | 15          |

    When the shipment identified by shipment_1 is completed


    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                      | 100              | 100          | shipmentLine_1                |
      | invoiceCand_2                     | o_1                       | null                      | 15               | 15           | shipmentLine_2                |
    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |

    When the shipment identified by shipment_1 is closed


    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | shipment_1            | CL        |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | o_1                       | ol_1                          | 100              | 100          | salesProduct                |
      | invoiceCand_2                     | o_1                       | null                          | 15               | 15           | packingProduct              |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandShipmentLine_1                  | invoiceCand_1                         | shipmentLine_1                | 100              |
      | invoiceCandShipmentLine_2                  | invoiceCand_2                         | shipmentLine_2                | 15               |