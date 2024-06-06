@ghActions:run_on_executor5
Feature: Call order contract

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_PricingSystems
      | Identifier             |
      | defaultPricingSystem   |
      | callOrderPricingSystem |

  @from:cucumber
  Scenario: Happy flow for call order contract and call order summary - sales order
    Given metasfresh contains M_PriceLists
      | Identifier         | M_PricingSystem_ID     | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | PricePrecision |
      | defaultPriceList   | defaultPricingSystem   | DE                    | EUR                 | true  | 2              |
      | callOrderPriceList | callOrderPricingSystem | DE                    | EUR                 | true  | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID     | ValidFrom  |
      | defaultPLV   | defaultPriceList   | 2022-02-01 |
      | callOrderPLV | callOrderPriceList | 2022-02-01 |
    And metasfresh contains M_Products:
      | Identifier         |
      | call_order_product |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID | M_Product_ID       | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | defaultPP   | defaultPLV             | call_order_product | 5.00     | PCE               | Normal                        |
      | callOrderPP | callOrderPLV           | call_order_product | 2.00     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID   | C_PaymentTerm_ID.Value |
      | bpartner_1 | Y          | defaultPricingSystem | 1000002                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bpartnerLocation_1 | 1234312345487 | bpartner_1               | true                | true                |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier          | Name             | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend |
      | callOrderConditions | CallOrderTest_so | CallOrder       | callOrderPricingSystem            | Ca                       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DocSubType | OPT.C_BPartner_Location_ID.Identifier |
      | order_1    | true    | bpartner_1               | 2022-03-03  | SOO             | SO             | bpartnerLocation_1                    |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | orderLine_1 | order_1               | call_order_product      | 1000       | callOrderConditions                     |

    When the order identified by order_1 is completed

    Then there is no C_Invoice_Candidate for C_Order order_1

    And there is no M_ShipmentSchedule for C_Order order_1

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | orderLine_1               | order_1               | 2022-03-03  | call_order_product      | 1000       | 0            | 0           | 2     | 0        | EUR          | true      | PCE                   | PCE                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual |
      | callOrder_contract_1          | callOrderConditions                 | bpartner_1                  | call_order_product      | orderLine_1                        | order_1                        | call_order_product          | PCE                   | 1000                  | 2.00            |

    And validate created C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | true    |

    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | call_order_product      | PCE                    | KRT                  | 0.25         |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 |
      | defaultPP                    | 6.00         | KRT                   |

    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DocSubType |
      | callOrder_1 | true    | bpartner_1               | 2022-03-04  | SOO             | CAO            |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | callOrderLine_1 | callOrder_1           | call_order_product      | 2          |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | callOrderLine_1           | callOrder_1           | 2022-03-04  | call_order_product      | 2          | 0            | 0           | 6     | 0        | EUR          | false     | PCE                   | KRT                       |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | callOrderLine_1           | callOrder_contract_1              |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | callOrderLine_1           | callOrder_1           | 2022-03-04  | call_order_product      | 2          | 0            | 0           | 8     | 0        | EUR          | false     | PCE                   | KRT                       |

    When the order identified by callOrder_1 is completed

    Then validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 2              |

    When the order identified by callOrder_1 is reactivated

    Then validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 0              |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | callOrderLine_1           | 4              |

    When the order identified by callOrder_1 is completed

    Then validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1 | callOrderLine_1           | N             |
    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | schedule_1                       | D            | false               | false       | 2                                                |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_1                       | shipment_1            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1            | shipment_1            | call_order_product      | 2           | false     | callOrderLine_1               |

    When the shipment identified by shipment_1 is completed

    Then validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | true    | 2                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |

    When the shipment identified by shipment_1 is reactivated

    Then validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | true    | 0                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 0                     |

    And the shipment identified by shipment_1 is completed

    When the shipment identified by shipment_1 is reversed

    Then load M_InOut:
      | M_InOut_ID.Identifier | M_InOutLine_ID.Identifier | QtyEntered | DocStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shipment_1            | shipmentLine_1            | 2          | RE        | callOrder_1               | callOrderLine_1               |
      | shipment_2            | shipmentLine_2            | -2         | RE        | callOrder_1               | callOrderLine_1               |
    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | true    | 0                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1 | callOrderLine_1           | N             |
    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_1                       | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_1                       | shipment_3            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_3            | shipment_3            | call_order_product      | 4           | true      | callOrderLine_1               |
    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | true    | 4                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |
      | orderDetail_shipment_3          | PCE               |                           |                               |                | shipment_3                | shipmentLine_3                | 4                     |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | callOrderLine_1           | 4            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered |
      | invoiceCand_1                     | callOrder_1               | callOrderLine_1               | 4                | 4              | 4                |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | 2                         |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | callOrder_1               | callOrderLine_1               | 2            | 4              | 4                | 2                         |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | invoiceCand_1                     | 2               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_1                     | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | 1000002     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1_1             | invoice_1               | call_order_product      | 2           | true      |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | true    | 4                     | 2                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |                             |                                 |                      |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |                             |                                 |                      |
      | orderDetail_shipment_3          | PCE               |                           |                               |                | shipment_3                | shipmentLine_3                | 4                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1_1                 | 2                    |

    When the invoice identified by invoice_1 is reversed

    Then load C_Invoice:
      | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier | QtyInvoiced | DocStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | invoice_1               | invoiceLine_1_1             | 2           | RE        | callOrder_1               | callOrderLine_1               |
      | invoice_2               | invoiceLine_2_1             | -2          | RE        | callOrder_1               | callOrderLine_1               |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | true    | 4                     | 0                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |                             |                                 |                      |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |                             |                                 |                      |
      | orderDetail_shipment_3          | PCE               |                           |                               |                | shipment_3                | shipmentLine_3                | 4                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1_1                 | 2                    |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_2                   | invoiceLine_2_1                 | -2                   |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | 4                         |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | callOrder_1               | callOrderLine_1               | 4            | 4              | 4                | 4                         |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | invoiceCand_1                     | 4               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_1                     | invoice_3               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus |
      | invoice_3               | bpartner_1               | bpartnerLocation_1                | 1000002     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_3_1             | invoice_3               | call_order_product      | 4           | true      |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | true    | 4                     | 4                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |                             |                                 |                      |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |                             |                                 |                      |
      | orderDetail_shipment_3          | PCE               |                           |                               |                | shipment_3                | shipmentLine_3                | 4                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1_1                 | 2                    |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_2                   | invoiceLine_2_1                 | -2                   |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_3                   | invoiceLine_3_1                 | 4                    |


  @from:cucumber
  Scenario: Happy flow for call order contract and call order summary - purchase order
    Given metasfresh contains M_Products:
      | Identifier            |
      | call_order_product_PO |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID     | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | PricePrecision |
      | defaultPL_PO   | defaultPricingSystem   | DE                    | EUR                 | false | 2              |
      | callOrderPL_PO | callOrderPricingSystem | DE                    | EUR                 | false | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID | ValidFrom  |
      | defaultPLV   | defaultPL_PO   | 2022-02-01 |
      | callOrderPLV | callOrderPL_PO | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID | M_Product_ID          | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | defaultPP   | defaultPLV             | call_order_product_PO | 5.00     | PCE               | Normal                        |
      | callOrderPP | callOrderPLV           | call_order_product_PO | 2.00     | PCE               | Normal                        |

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_HU_PI:
      | Identifier         |
      | huPackingLU        |
      | huPackingTU        |
      | huPackingVirtualPI |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | call_order_product_PO   | 10  | 2022-02-01 |

    And metasfresh contains C_BPartners:
      | Identifier     | IsVendor | M_PricingSystem_ID   | C_PaymentTerm_ID.Value |
      | bp_callOrderPO | Y        | defaultPricingSystem | 1000002                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier              | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | bp_callOrderPO_Location | 5802098505483 | bp_callOrderPO           | true                | true                |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name                   | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend |
      | callOrderConditions_PO | callOrderConditions_po | CallOrder       | callOrderPricingSystem            | Ca                       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.C_BPartner_Location_ID.Identifier | OPT.POReference         |
      | po_order   | false   | bp_callOrderPO           | 2022-03-03  | POO             | bp_callOrderPO_Location               | poCallOrderContract_ref |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | po_orderLine | po_order              | call_order_product_PO   | 1000       | callOrderConditions_PO                  |

    When the order identified by po_order is completed

    Then there is no C_Invoice_Candidate for C_Order po_order

    And there is no M_ReceiptSchedule for C_OrderLine po_orderLine

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | po_orderLine              | po_order              | 2022-03-03  | call_order_product_PO   | 1000       | 0            | 0           | 2     | 0        | EUR          | true      | PCE                   | PCE                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier | Bill_BPartner_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual |
      | callOrder_contract_1          | callOrderConditions_PO              | call_order_product_PO   | bp_callOrderPO              | po_orderLine                       | po_order                       | PCE                   | 1000                  | 2.00            |

    And validate created C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx |
      | callOrderSummary_1               | callOrder_contract_1          | po_orderLine              | po_order              | call_order_product_PO   | 1000       | PCE               | false   |

    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | call_order_product_PO   | PCE                    | KRT                  | 0.25         |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | OPT.PriceStd | OPT.C_UOM_ID.X12DE355 |
      | defaultPP                    | 6.00         | KRT                   |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DocSubType | OPT.POReference |
      | callOrder_po | false   | bp_callOrderPO           | 2022-03-04  | POO             | CAO            | poCallOrder_ref |
    And metasfresh contains C_OrderLines:
      | Identifier       | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | callOrderLine_po | callOrder_po          | call_order_product_PO   | 2          |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | callOrderLine_po          | callOrder_po          | 2022-03-04  | call_order_product_PO   | 2          | 0            | 0           | 6     | 0        | EUR          | false     | PCE                   | KRT                       |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | callOrderLine_po          | callOrder_contract_1              |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | callOrderLine_po          | callOrder_po          | 2022-03-04  | call_order_product_PO   | 2          | 0            | 0           | 8     | 0        | EUR          | false     | PCE                   | KRT                       |

    When the order identified by callOrder_po is completed

    Then validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 2              |

    When the order identified by callOrder_po is reactivated

    Then validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 0              |

    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | callOrderLine_po          | 8              |

    When the order identified by callOrder_po is completed

    Then validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 8              |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | callOrder_po          | callOrderLine_po          | bp_callOrderPO           | bp_callOrderPO_Location           | call_order_product_PO   | 8          | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu                 | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 6     | huItemPurchaseProduct              | huPackingLU                  |
    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu                 | receiptSchedule_PO              | material_receipt_1    |
    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1            | material_receipt_1    | call_order_product_PO   | 6           | true      | callOrderLine_po              |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | po_orderLine              | po_order              | call_order_product_PO   | 1000       | PCE               | false   | 6                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 8              |                           |                               |                       |
      | orderDetail_material_receipt_1  | PCE               |                           |                               |                | material_receipt_1        | shipmentLine_1                | 6                     |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | po_orderLine              | po_order              | call_order_product_PO   | 1000       | PCE               | false   | 0                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 8              |                           |                               |                       |
      | orderDetail_material_receipt_1  | PCE               |                           |                               |                | material_receipt_1        | shipmentLine_1                | 0                     |

    And the material receipt identified by material_receipt_1 is completed

    When the material receipt identified by material_receipt_1 is reversed

    Then load M_InOut:
      | M_InOut_ID.Identifier | M_InOutLine_ID.Identifier | QtyEntered | DocStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | material_receipt_1    | shipmentLine_1            | 6          | RE        | callOrder_po              | callOrderLine_po              |
      | material_receipt_2    | shipmentLine_2            | -6         | RE        | callOrder_po              | callOrderLine_po              |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | po_orderLine              | po_order              | call_order_product_PO   | 1000       | PCE               | false   | 0                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 8              |                           |                               |                       |
      | orderDetail_material_receipt_1  | PCE               |                           |                               |                | material_receipt_1        | shipmentLine_1                | 6                     |
      | orderDetail_material_receipt_2  | PCE               |                           |                               |                | material_receipt_2        | shipmentLine_2                | -6                    |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu_1               | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 8     | huItemPurchaseProduct              | huPackingLU                  |
    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_1               | receiptSchedule_PO              | material_receipt_3    |
    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_3            | material_receipt_3    | call_order_product_PO   | 8           | true      | callOrderLine_po              |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | po_orderLine              | po_order              | call_order_product_PO   | 1000       | PCE               | false   | 8                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 8              |                           |                               |                       |
      | orderDetail_material_receipt_1  | PCE               |                           |                               |                | material_receipt_1        | shipmentLine_1                | 6                     |
      | orderDetail_material_receipt_2  | PCE               |                           |                               |                | material_receipt_2        | shipmentLine_2                | -6                    |
      | orderDetail_material_receipt_3  | PCE               |                           |                               |                | material_receipt_3        | shipmentLine_3                | 8                     |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | callOrderLine_po          | 8            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered |
      | invoiceCand_1                     | callOrder_po              | callOrderLine_po              | 8            | 8              | 8                |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | 6                         |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | callOrder_po              | callOrderLine_po              | 6            | 8              | 8                | 6                         |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | invoiceCand_1                     | 6               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_1                     | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.POReference |
      | invoice_1               | bp_callOrderPO           | bp_callOrderPO_Location           | 1000002     | true      | CO        | poCallOrder_ref |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1               | invoice_1               | call_order_product_PO   | 6           | true      |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | po_orderLine              | po_order              | call_order_product_PO   | 1000       | PCE               | false   | 8                     | 6                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 8              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_material_receipt_1  | PCE               |                           |                               |                | material_receipt_1        | shipmentLine_1                | 6                     |                             |                                 |                      |
      | orderDetail_material_receipt_2  | PCE               |                           |                               |                | material_receipt_2        | shipmentLine_2                | -6                    |                             |                                 |                      |
      | orderDetail_material_receipt_3  | PCE               |                           |                               |                | material_receipt_3        | shipmentLine_3                | 8                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1                   | 6                    |

    When the invoice identified by invoice_1 is reversed

    Then load C_Invoice:
      | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier | QtyInvoiced | DocStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | invoice_1               | invoiceLine_1               | 6           | RE        | callOrder_po              | callOrderLine_po              |
      | invoice_2               | invoiceLine_2               | -6          | RE        | callOrder_po              | callOrderLine_po              |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | po_orderLine              | po_order              | call_order_product_PO   | 1000       | PCE               | false   | 8                     | 0                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 8              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_material_receipt_1  | PCE               |                           |                               |                | material_receipt_1        | shipmentLine_1                | 6                     |                             |                                 |                      |
      | orderDetail_material_receipt_2  | PCE               |                           |                               |                | material_receipt_2        | shipmentLine_2                | -6                    |                             |                                 |                      |
      | orderDetail_material_receipt_3  | PCE               |                           |                               |                | material_receipt_3        | shipmentLine_3                | 8                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1                   | 6                    |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_2                   | invoiceLine_2                   | -6                   |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | 8                         |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | callOrder_po              | callOrderLine_po              | 8            | 8              | 8                | 8                         |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyInvoiced |
      | invoiceCand_1                     | 8               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_1                     | invoice_3               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.POReference |
      | invoice_3               | bp_callOrderPO           | bp_callOrderPO_Location           | 1000002     | true      | CO        | poCallOrder_ref |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_3               | invoice_3               | call_order_product_PO   | 8           | true      |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | IsSOTrx | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | po_orderLine              | po_order              | call_order_product_PO   | 1000       | PCE               | false   | 8                     | 8                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_po              | callOrderLine_po              | 8              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_material_receipt_1  | PCE               |                           |                               |                | material_receipt_1        | shipmentLine_1                | 6                     |                             |                                 |                      |
      | orderDetail_material_receipt_2  | PCE               |                           |                               |                | material_receipt_2        | shipmentLine_2                | -6                    |                             |                                 |                      |
      | orderDetail_material_receipt_3  | PCE               |                           |                               |                | material_receipt_3        | shipmentLine_3                | 8                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1                   | 6                    |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_2                   | invoiceLine_2                   | -6                   |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_3                   | invoiceLine_3                   | 8                    |
