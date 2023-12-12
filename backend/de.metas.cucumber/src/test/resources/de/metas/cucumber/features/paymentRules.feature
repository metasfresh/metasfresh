@from:cucumber
@ghActions:run_on_executor6
Feature: Validate that PaymentRule is correctly set on C_Order and C_Invoice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-03-22T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | Name    |
      | product_1  | product |
    And metasfresh contains M_PricingSystems
      | Identifier   | Name           | Value           | OPT.Description       | OPT.IsActive |
      | pricingSys_1 | pricingSysName | pricingSysValue | pricingSysDescription | true         |

    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name         | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | priceList_SO | pricingSys_1                  | DE                        | EUR                 | priceList_SO | null            | true  | false         | 2              | true         |
      | priceList_PO | pricingSys_1                  | DE                        | EUR                 | priceList_PO | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | priceListVersion_SO | priceList_SO              | salesOrder-PLV    | 2022-02-01 |
      | priceListVersion_PO | priceList_PO              | purchaseOrder-PLV | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrices_SO | priceListVersion_SO               | product_1               | 10.0     | PCE               | Normal                        |
      | productPrices_PO | priceListVersion_PO               | product_1               | 10.0     | PCE               | Normal                        |

  @from:cucumber
  Scenario: C_BPartner.PaymentRule = 'DirectDebit' correctly propagates to C_Invoice, when created from C_Order; IsSOTrx = 'Y'
    Given metasfresh contains C_BPartners:
      | Identifier | Name                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule | OPT.InvoiceRule |
      | bpartner_1 | BPartnerTest_SO_D_29_03_2022 | N            | Y              | pricingSys_1                  | D               | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0123411217731 | bpartner_1               | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | order_1    | true    | bpartner_1               | 2022-03-20  | 1000012              | SO_DirectDebit  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | product_1               | 10         |

    When the order identified by order_1 is completed

    # C_BPartner.PaymentRule => C_Order.PaymentRule (due to org/compiere/model/MOrder.java:951 -> org.compiere.model.MOrder.setBPartner)
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | OPT.POReference | processed | docStatus | OPT.PaymentRule |
      | order_1               | bpartner_1               | location_1                        | 2022-03-20  | SOO         | EUR          | F            | P               | SO_DirectDebit  | true      | CO        | D               |

    And enqueue candidate for invoicing and after not more than 60s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | invoice_1               |

    # C_Order.PaymentRule => C_Invoice.PaymentRule
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | POReference    | paymentTerm | processed | docStatus | OPT.PaymentRule |
      | invoice_1               | bpartner_1               | location_1                        | SO_DirectDebit | 1000002     | true      | CO        | D               |


  @from:cucumber
  Scenario: C_BPartner.PaymentRule = 'OnCredit' correctly propagates to C_Invoice, when created from C_Order; IsSOTrx = 'Y'
    Given metasfresh contains C_BPartners:
      | Identifier | Name                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule | OPT.InvoiceRule |
      | bpartner_1 | BPartnerTest_SO_P_29_03_2022 | N            | Y              | pricingSys_1                  | P               | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0123456795211 | bpartner_1               | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | order_1    | true    | bpartner_1               | 2022-03-20  | 1000012              | SO_OnCredit     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | product_1               | 10         |

    When the order identified by order_1 is completed

     # C_BPartner.PaymentRule => C_Order.PaymentRule (due to org/compiere/model/MOrder.java:951 -> org.compiere.model.MOrder.setBPartner)
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | OPT.POReference | processed | docStatus | OPT.PaymentRule |
      | order_1               | bpartner_1               | location_1                        | 2022-03-20  | SOO         | EUR          | F            | P               | SO_OnCredit     | true      | CO        | P               |

    And enqueue candidate for invoicing and after not more than 60s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | invoice_1               |

     # C_Order.PaymentRule => C_Invoice.PaymentRule
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | POReference | paymentTerm | processed | docStatus | OPT.PaymentRule |
      | invoice_1               | bpartner_1               | location_1                        | SO_OnCredit | 1000002     | true      | CO        | P               |


  @from:cucumber
  Scenario: C_BPartner.PaymentRule = 'Cash' correctly propagates to C_Invoice, when created from C_Order; IsSOTrx = 'Y'
    Given metasfresh contains C_BPartners:
      | Identifier | Name                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule | OPT.InvoiceRule |
      | bpartner_1 | BPartnerTest_SO_B_29_03_2022 | N            | Y              | pricingSys_1                  | B               | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0123189751011 | bpartner_1               | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference |
      | order_1    | true    | bpartner_1               | 2022-03-20  | 1000012              | SO_Cash         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | product_1               | 10         |

    When the order identified by order_1 is completed

     # C_BPartner.PaymentRule => C_Order.PaymentRule (due to org/compiere/model/MOrder.java:951 -> org.compiere.model.MOrder.setBPartner)
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | OPT.POReference | processed | docStatus | OPT.PaymentRule |
      | order_1               | bpartner_1               | location_1                        | 2022-03-20  | SOO         | EUR          | F            | P               | SO_Cash         | true      | CO        | B               |

    And enqueue candidate for invoicing and after not more than 60s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | invoice_1               |

     # C_Order.PaymentRule => C_Invoice.PaymentRule
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | POReference | paymentTerm | processed | docStatus | OPT.PaymentRule |
      | invoice_1               | bpartner_1               | location_1                        | SO_Cash     | 1000002     | true      | CO        | B               |


  @from:cucumber
  Scenario: C_BPartner.PaymentRule = 'DirectDebit' correctly propagates to C_Invoice, when created from C_Order; IsSOTrx = 'N'
    Given metasfresh contains C_BPartners:
      | Identifier | Name                        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRulePO | OPT.PO_InvoiceRule |
      | bpartner_1 | BPartnerTestPO_D_29_03_2022 | Y            | N              | pricingSys_1                  | D                 | I                  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0115687834011 | bpartner_1               | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.POReference |
      | order_1    | false   | bpartner_1               | 2022-03-20  | POO             | pricingSys_1                      | PO_Direct_Debit |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | product_1               | 10         |

    When the order identified by order_1 is completed

     # C_BPartner.PaymentRule => C_Order.PaymentRule (due to org/compiere/model/MOrder.java:951 -> org.compiere.model.MOrder.setBPartner)
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | OPT.POReference | processed | docStatus | OPT.PaymentRule |
      | order_1               | bpartner_1               | location_1                        | 2022-03-20  | POO         | EUR          | F            | P               | PO_Direct_Debit | true      | CO        | D               |

    And enqueue candidate for invoicing and after not more than 60s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | invoice_1               |
    
     # C_Order.PaymentRule => C_Invoice.PaymentRule
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | POReference     | paymentTerm   | processed | docStatus | OPT.PaymentRule |
      | invoice_1               | bpartner_1               | location_1                        | PO_Direct_Debit | 30 Tage netto | true      | CO        | D               |


  @from:cucumber
  Scenario: C_BPartner.PaymentRule = 'OnCredit' correctly propagates to C_Invoice, when created from C_Order; IsSOTrx = 'N'
    Given metasfresh contains C_BPartners:
      | Identifier | Name                        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRulePO | OPT.PO_InvoiceRule |
      | bpartner_1 | BPartnerTestPO_P_29_03_2022 | Y            | N              | pricingSys_1                  | P                 | I                  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0445911392811 | bpartner_1               | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.POReference |
      | order_1    | false   | bpartner_1               | 2022-03-20  | POO             | pricingSys_1                      | PO_On_Credit    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | product_1               | 10         |

    When the order identified by order_1 is completed

     # C_BPartner.PaymentRule => C_Order.PaymentRule (due to org/compiere/model/MOrder.java:951 -> org.compiere.model.MOrder.setBPartner)
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | OPT.POReference | processed | docStatus | OPT.PaymentRule |
      | order_1               | bpartner_1               | location_1                        | 2022-03-20  | POO         | EUR          | F            | P               | PO_On_Credit    | true      | CO        | P               |

    Then enqueue candidate for invoicing and after not more than 60s, the invoice is found
      | C_Order_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | invoice_1               |
    
     # C_Order.PaymentRule => C_Invoice.PaymentRule
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | POReference  | paymentTerm   | processed | docStatus | OPT.PaymentRule |
      | invoice_1               | bpartner_1               | location_1                        | PO_On_Credit | 30 Tage netto | true      | CO        | P               |


  @from:cucumber
  Scenario: Payment rule 'DirectDebit' correctly propagates when SO is cloned
    Given metasfresh contains C_BPartners:
      | Identifier | Name                               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRule |
      | bpartner_1 | BPartnerTestSO_D_Cloned_29_03_2022 | N            | Y              | pricingSys_1                  | B               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0130456809011 | bpartner_1               | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_PricingSystem_ID.Identifier | OPT.PaymentRule | OPT.C_BPartner_Location_ID.Identifier |
      | order_so   | true    | bpartner_1               | 2022-03-20  | pricingSys_1                      | D               | location_1                            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_so              | product_1               | 10         |

    When the order identified by order_so is completed

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | processed | docStatus | OPT.PaymentRule |
      | order_so              | bpartner_1               | location_1                        | 2022-03-20  | SOO         | EUR          | A            | P               | true      | CO        | D               |

    When C_Order is cloned
      | C_Order_ID.Identifier | ClonedOrder.C_Order_ID.Identifier |
      | order_so              | clonedOrder_so                    |

    # C_Order.PaymentRule => C_Order.PaymentRule
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | processed | docStatus | OPT.PaymentRule |
      | clonedOrder_so        | bpartner_1               | location_1                        | 2022-03-22  | SOO         | EUR          | A            | P               | false     | DR        | D               |


  @from:cucumber
  Scenario: Payment rule 'DirectDebit' correctly propagates when PO is cloned
    Given metasfresh contains C_BPartners:
      | Identifier | Name                               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PaymentRulePO |
      | bpartner_1 | BPartnerTestPO_D_Cloned_29_03_2022 | Y            | N              | pricingSys_1                  | B                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0123256005011 | bpartner_1               | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.PaymentRule | OPT.C_BPartner_Location_ID.Identifier |
      | order_po   | false   | bpartner_1               | 2022-03-20  | POO             | pricingSys_1                      | D               | location_1                            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | order_po              | product_1               | 10         |

    When the order identified by order_po is completed

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | processed | docStatus | OPT.PaymentRule |
      | order_po              | bpartner_1               | location_1                        | 2022-03-20  | POO         | EUR          | A            | P               | true      | CO        | D               |

    When C_Order is cloned
      | C_Order_ID.Identifier | ClonedOrder.C_Order_ID.Identifier |
      | order_po              | clonedOrder_po                    |

     # C_Order.PaymentRule => C_Order.PaymentRule
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | processed | docStatus | OPT.PaymentRule |
      | clonedOrder_po        | bpartner_1               | location_1                        | 2022-03-22  | POO         | EUR          | A            | P               | false     | DR        | D               |


  @from:cucumber
  @Id:S0150_230
  Scenario: PaymentRule = `DirectDebit` from order disposition is correctly propagated to C_Order
    Given metasfresh contains C_BPartners:
      | Identifier | Name                        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | bpartner_1 | BPartnerTestOL_D_29_03_2022 | N            | Y              | pricingSys_1                  | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0199722596654 | bpartner_1               | Y                   | Y                   |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "555555",
    "externalHeaderId": "1188",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-0199722596654",
        "bpartnerLocationIdentifier": "gln-0199722596654"
    },
    "dateRequired": "2023-04-22",
    "dateOrdered": "2022-03-22",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-product",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock",
    "deliveryViaRule": "S",
    "deliveryRule": "F",
    "paymentRule": "DirectDebit"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "1188",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | OPT.POReference | processed | docStatus | OPT.PaymentRule | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 1188           | bpartner_1               | location_1                        | 2022-03-22  | SOO         | EUR          | F            | S               | po_ref_mock     | true      | CO        | D               | Shopware                               |

  @from:cucumber
  @Id:S0150_240
  Scenario: PaymentRule = `OnCredit` from order disposition is correctly propagated to C_Order
    Given metasfresh contains C_BPartners:
      | Identifier | Name                        | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | bpartner_1 | BPartnerTestOL_P_29_03_2022 | N            | Y              | pricingSys_1                  | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0893711184488 | bpartner_1               | Y                   | Y                   |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "222",
    "externalHeaderId": "9208",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-0893711184488",
        "bpartnerLocationIdentifier": "gln-0893711184488"
    },
    "dateRequired": "2023-04-22",
    "dateOrdered": "2022-03-22",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-product",
    "qty": 10,
    "price": 5,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock",
    "deliveryViaRule": "S",
    "deliveryRule": "F",
    "paymentRule": "OnCredit"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "9208",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | OPT.POReference | processed | docStatus | OPT.PaymentRule | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | 9208           | bpartner_1               | location_1                        | 2022-03-22  | SOO         | EUR          | F            | S               | po_ref_mock     | true      | CO        | P               | Shopware                               |
