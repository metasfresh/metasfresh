Feature: Call order contract

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: Happy flow for call order contract and call order summary
    Given metasfresh contains M_PricingSystems
      | Identifier             | Name                   | Value                  |
      | defaultPricingSystem   | defaultPricingSystem   | defaultPricingSystem   |
      | callOrderPricingSystem | callOrderPricingSystem | callOrderPricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier         | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | defaultPriceList   | defaultPricingSystem          | DE                        | EUR                 | PriceListName1 | true  | false         | 2              |
      | callOrderPriceList | callOrderPricingSystem        | DE                        | EUR                 | PriceListName2 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID.Identifier | Name     | ValidFrom  |
      | defaultPLV   | defaultPriceList          | PLVName1 | 2022-02-01 |
      | callOrderPLV | callOrderPriceList        | PLVName2 | 2022-02-01 |
    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | call_order_product | call_order_product |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | defaultPP   | defaultPLV                        | call_order_product      | 5.00     | PCE               | Normal                        |
      | callOrderPP | callOrderPLV                      | call_order_product      | 2.00     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.C_PaymentTerm_ID.Value |
      | bpartner_1 | BPartnerTest | Y              | defaultPricingSystem          | 1000002                    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier |
      | bpartnerLocation_1 | 1234312345487 | bpartner_1               |

    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name          | Type_Conditions | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend |
      | callOrderConditions                 | CallOrderTest | CallOrder       | callOrderPricingSystem            | Ca                       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DocSubType | OPT.C_BPartner_Location_ID.Identifier |
      | order_1    | true    | bpartner_1               | 2022-03-03  | SOO             | SO             | bpartnerLocation_1                    |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.C_Flatrate_Conditions_ID.Identifier |
      | orderLine_1 | order_1               | call_order_product      | 1000       | callOrderConditions                     |

    When the order identified by order_1 is completed

    And there is no C_Invoice_Candidate for C_Order order_1

    And there is no M_ShipmentSchedule for C_Order order_1

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtyordered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | orderLine_1               | order_1               | 2022-03-03  | call_order_product      | 1000       | 0            | 0           | 2     | 0        | EUR          | true      | PCE                   | PCE                       |

    And validate created C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | OPT.C_OrderLine_Term_ID.Identifier | OPT.C_Order_Term_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | OPT.PlannedQtyPerUnit | OPT.PriceActual |
      | callOrder_contract_1          | callOrderConditions                 | orderLine_1                        | order_1                        | call_order_product          | PCE                   | 1000                  | 2.00            |

    And validate created C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               |

    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | call_order_product      | PCE                    | KRT                  | 0.25         |
    And update M_ProductPrice:
      | M_ProductPrice_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 |
      | defaultPP                    | 6.00     | KRT               |

    And metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.DocSubType |
      | callOrder_1 | true    | bpartner_1               | 2022-03-04  | SOO             | CAO            |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | callOrderLine_1 | callOrder_1           | call_order_product      | 2          |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtyordered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
      | callOrderLine_1           | callOrder_1           | 2022-03-04  | call_order_product      | 2          | 0            | 0           | 6     | 0        | EUR          | false     | PCE                   | KRT                       |
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.C_Flatrate_Term_ID.Identifier |
      | callOrderLine_1           | callOrder_contract_1              |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtyordered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_ID.X12DE355 | OPT.Price_UOM_ID.X12DE355 |
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

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1 | callOrderLine_1           | N             |
    And generate shipments process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID_ |
      | schedule_1                       | D            | false               | false       | 2                                                |
    And after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_1                       | shipment_1            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_1            | shipment_1            | call_order_product      | 2           | false     | callOrderLine_1               |

    When the shipment identified by shipment_1 is completed

    Then validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | 2                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |

    When the shipment identified by shipment_1 is reactivated

    Then validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | 0                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 0                     |

    And the shipment identified by shipment_1 is completed

    When the shipment identified by shipment_1 is reversed

    Then load shipment:
      | M_InOut_ID.Identifier | M_InOutLine_ID.Identifier | QtyEntered | DocStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shipment_1            | shipmentLine_1            | 2          | RE        | callOrder_1               | callOrderLine_1               |
      | shipment_2            | shipmentLine_2            | -2         | RE        | callOrder_1               | callOrderLine_1               |
    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | 0                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_2 | callOrderLine_1           | N             |
    When generate shipments process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_2                       | D            | true                | false       |
    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_2                       | shipment_3            |
    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier |
      | shipmentLine_3            | shipment_3            | call_order_product      | 4           | true      | callOrderLine_1               |
    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | OPT.QtyDeliveredInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | 4                     |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |
      | orderDetail_shipment_3          | PCE               |                           |                               |                | shipment_3                | shipmentLine_3                | 4                     |

    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | callOrderLine_1           | 4            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered |
      | invoiceCand_1                     | callOrder_1               | callOrderLine_1               | 4            | 4              | 4                |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | 2                         |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | callOrder_1               | callOrderLine_1               | 2            | 4              | 4                | 2                         |
    When enqueue invoice candidate of order callOrder_1 for invoicing and after not more than 30s, the invoice is found
      | C_Invoice_ID.Identifier |
      | invoice_1               |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus |
      | invoice_1               | bpartner_1               | bpartnerLocation_1                | 1000002     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine_1               | invoice_1               | call_order_product      | 2           | true      |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | 4                     | 2                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |                             |                                 |                      |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |                             |                                 |                      |
      | orderDetail_shipment_3          | PCE               |                           |                               |                | shipment_3                | shipmentLine_3                | 4                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1                   | 2                    |

    When the invoice identified by invoice_1 is reversed

    Then load invoice:
      | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier | QtyInvoiced | DocStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | invoice_1               | invoiceLine_1               | 2           | RE        | callOrder_1               | callOrderLine_1               |
      | invoice_2               | invoiceLine_2               | -2          | RE        | callOrder_1               | callOrderLine_1               |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | 4                     | 0                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |                             |                                 |                      |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |                             |                                 |                      |
      | orderDetail_shipment_3          | PCE               |                           |                               |                | shipment_3                | shipmentLine_3                | 4                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1                   | 2                    |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_2                   | invoiceLine_2                   | -2                   |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | 4                         |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyToInvoice_Override |
      | invoiceCand_1                     | callOrder_1               | callOrderLine_1               | 4            | 4              | 4                | 4                         |
    When enqueue invoice candidate of order callOrder_1 for invoicing and after not more than 30s, the invoice is found
      | C_Invoice_ID.Identifier | OPT.InvoicesSize |
      | invoice_3               | 3                |
    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus |
      | invoice_3               | bpartner_1               | bpartnerLocation_1                | 1000002     | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine_3               | invoice_3               | call_order_product      | 4           | true      |

    And validate updated C_CallOrderSummary:
      | C_CallOrderSummary_ID.Identifier | C_Flatrate_Term_ID.Identifier | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_UOM_ID.X12DE355 | OPT.QtyDeliveredInUOM | OPT.QtyInvoicedInUOM |
      | callOrderSummary_1               | callOrder_contract_1          | orderLine_1               | order_1               | call_order_product      | 1000       | PCE               | 4                     | 4                    |
    And validate C_CallOrderDetail for callOrderSummary_1:
      | C_CallOrderDetail_ID.Identifier | C_UOM_ID.X12DE355 | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.QtyEntered | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDeliveredInUOM | OPT.C_Invoice_ID.Identifier | OPT.C_InvoiceLine_ID.Identifier | OPT.QtyInvoicedInUOM |
      | orderDetail_order_1             | PCE               | callOrder_1               | callOrderLine_1               | 4              |                           |                               |                       |                             |                                 |                      |
      | orderDetail_shipment_1          | PCE               |                           |                               |                | shipment_1                | shipmentLine_1                | 2                     |                             |                                 |                      |
      | orderDetail_shipment_2          | PCE               |                           |                               |                | shipment_2                | shipmentLine_2                | -2                    |                             |                                 |                      |
      | orderDetail_shipment_3          | PCE               |                           |                               |                | shipment_3                | shipmentLine_3                | 4                     |                             |                                 |                      |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_1                   | invoiceLine_1                   | 2                    |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_2                   | invoiceLine_2                   | -2                   |
      | orderDetail_invoice_1           | PCE               |                           |                               |                |                           |                               |                       | invoice_3                   | invoiceLine_3                   | 4                    |