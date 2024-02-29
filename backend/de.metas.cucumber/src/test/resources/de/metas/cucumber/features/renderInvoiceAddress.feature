@from:cucumber
@topic:invoiceAddress
@ghActions:run_on_executor7
Feature: Render invoice address
  Especially tracing the "BPartnerName" part of the address

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-25T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier         | Name               |
      | test_product_26_02 | test_product_26_02 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name              | Value             |
      | ps_12      | pricing_system_12 | pricing_system_12 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_12                         | DE                        | EUR                 | price_list_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_so     | pl_so                     | plv_so | 2022-01-20 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product | plv_so                            | test_product_26_02      | 10.0     | Normal                        | PCE               |

    # create bpartner with invoice-rule "immediate", because we need just an invoice without a shipment
    And metasfresh contains C_BPartners without locations:
      | Identifier        | Name              | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName       | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | customer_bp_26_02 | customer_bp_26_02 | ps_12                         | Y              | customer_bp_26_02_cmp | I               | 1000002                    |

  Scenario: Invoice when business partner location doesn't have BPartnerName
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City       |
      | location_1               | DE          | addr 11      | 123        | locationCity_1 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.Name         |
      | bpLocation_1 | 1234567891133 | customer_bp_26_02        | location_1                   | true         | true         | locationNameTest |

    # create order with invoice-rule "immediate", because we need just an invoice without a shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.POReference | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | order_1    | true    | customer_bp_26_02        | 2022-02-02  | bpLocation_1                          | order_ref_12307 | F                | S                   | I               | 1000002                    |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | test_product_26_02      | 1          |

    And the order identified by order_1 is completed

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference     | processed | docStatus | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | order_1               | customer_bp_26_02        | bpLocation_1                      | 2022-02-02  | SOO         | EUR          | F            | S               | order_ref_12307 | true      | CO        | I               | 1000002                    |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02      | test_product_26_02      | 0            | 1          | 0           | 10.0  | 0        | EUR          | true      |

    # note that we wait for the IC to also have QtyToInvoice=1; that means that it it completely up to date and ready to be processed
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyToInvoice |
      | invoiceCand_1                     | orderLine_1               | 1                |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_1                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_1                     |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.BPartnerAddress                            |
      | invoice_1               | customer_bp_26_02        | bpLocation_1                      | order_ref_12307 | 1000002     | true      | CO        | customer_bp_26_02\naddr 11\n123 locationCity_1 |

  Scenario: Invoice when business partner location has BPartnerName
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City       |
      | location_2               | DE          | addr 22      | 456        | locationCity_2 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name     |
      | bpLocation_2 | 1234567891144 | customer_bp_26_02        | location_2                   | true         | true         | locationBPName   | locationName |

    # create order with invoice-rule "immediate", because we need just an invoice without a shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.POReference | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | order_1    | true    | customer_bp_26_02        | 2022-02-02  | bpLocation_2                          | order_ref_23407 | F                | S                   | I               | 1000002                    |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | test_product_26_02      | 1          |

    And the order identified by order_1 is completed

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference     | processed | docStatus | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | order_1               | customer_bp_26_02        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | order_ref_23407 | true      | CO        | I               | 1000002                    |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02      | test_product_26_02      | 0            | 1          | 0           | 10.0  | 0        | EUR          | true      |

    # note that we wait for the IC to also have QtyToInvoice=1; that means that it it completely up to date and ready to be processed
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyToInvoice |
      | invoiceCand_1                     | orderLine_1               | 1                |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_1                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_1                     |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.BPartnerAddress                         |
      | invoice_1               | customer_bp_26_02        | bpLocation_2                      | order_ref_23407 | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 |


  Scenario: Invoice when business partner bill location is from Switzerland
    Given metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_CH      | ps_12                         | CH                        | EUR                 | price_list_CH | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_CH     | pl_CH                     | plv_CH | 2022-01-20 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product_CH | plv_CH                            | test_product_26_02      | 10.0     | Normal                        | PCE               |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City        | OPT.RegionName |
      | location_DE              | DE          | addr 22      | 456        | locationCity_DE |                |
      | location_CH              | CH          | addr 33      | 258        | locationCity_CH | AI             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier    | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name       |
      | bpLocation_DE | 1234567891144 | customer_bp_26_02        | location_DE                  | true         | false        | locationBPNameDE | locationNameDE |
      | bpLocation_CH | 1234567895858 | customer_bp_26_02        | location_CH                  | false        | true         | locationBPNameCH | locationNameCH |

    # create order with invoice-rule "immediate", because we need just an invoice without a shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.POReference | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | order_1    | true    | customer_bp_26_02        | 2022-02-02  | bpLocation_DE                         | bpLocation_CH                   | order_ref_45607 | F                | S                   | I               | 1000002                    |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | test_product_26_02      | 1          |

    And the order identified by order_1 is completed

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference     | processed | docStatus | OPT.InvoiceRule |
      | order_1               | customer_bp_26_02        | bpLocation_DE                     | 2022-02-02  | SOO         | EUR          | F            | S               | order_ref_45607 | true      | CO        | I               |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02      | test_product_26_02      | 0            | 1          | 0           | 10.0  | 0        | EUR          | true      |
    # note that we wait for the IC to also have QtyToInvoice=1; that means that it it completely up to date and ready to be processed
    And after not more than 60s, C_Invoice_Candidates are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.COLUMNNAME_Bill_BPartner_ID.Identifier | OPT.COLUMNNAME_Bill_Location_ID.Identifier | OPT.QtyToInvoice |
      | invoiceCand_1                     | orderLine_1               | customer_bp_26_02                          | bpLocation_CH                              | 1                |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_1                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_1                     |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.BPartnerAddress                                     |
      | invoice_1               | customer_bp_26_02        | bpLocation_CH                     | order_ref_45607 | 1000002     | true      | CO        | locationBPNameCH\naddr 33\n258 locationCity_CH\nSchweiz |


  @Id:S0150_250
  Scenario: Invoice when origin order disposition has bpartnerName
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City       |
      | location_2               | DE          | addr 22      | 456        | locationCity_2 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name     |
      | bpLocation_2 | 1234567892266 | customer_bp_26_02        | location_2                   | true         | true         | locationBPName   | locationName |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": 10001,
    "externalLineId": 2222,
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567892266",
        "bpartnerLocationIdentifier": "gln-1234567892266"
    },
    "dateRequired": "2022-02-10",
    "dateOrdered": "2022-02-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-test_product_26_02",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "olCand_ref_10001",
    "deliveryViaRule": "S",
    "deliveryRule": "F",
    "bpartnerName": "olCandBPartnerName"
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "10001",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | invoice_1               |

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference      | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | customer_bp_26_02        | bpLocation_2                      | 2022-02-02  | SOO         | EUR          | F            | S               | olCand_ref_18231 | true      | CO        | Shopware                               |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02      | test_product_26_02      | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference      | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | shipment_1            | customer_bp_26_02        | bpLocation_2                      | 2022-02-02  | olCand_ref_10001 | true      | CO        | Shopware                               |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine1_1           | shipment_1            | test_product_26_02      | 1           | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference  | paymentTerm | processed | docStatus | OPT.BPartnerAddress                         | OPT.AD_InputDataSource_ID.InternalName |
      | invoice_1               | customer_bp_26_02        | bpLocation_2                      | olCand_ref_10001 | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 | Shopware                               |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_1_1                 | invoice_1               | test_product_26_02      | 1           | true      |