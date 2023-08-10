@from:cucumber
Feature: desadv and invoic

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: 1

  in:
  C_OLCand:
  - IsManualPrice = false
  - C_UOM_ID.X12DE355 = TU
  - C_Internal_UOM_Id.X12DE355 = TU
  - Qty = 10
  - QtyItemCapacity = 5
  - PackingMaterialId.Qty = 10

  M_ProductPrice:
  - C_UOM_ID.X12DE355 = KGM

  C_UOM_Conversion
  - PCE/KGM (1/4)

  M_HU_PI_Item_Product
  - Qty = 10

  out:
  C_OrderLine
  - C_UOM_BPartner_ID.X12DE355 = TU
  - QtyOrdered = 100
  - BPartner_QtyItemCapacity = 5
  - QtyEnteredInBPartnerUOM = 10
  - QtyEntered = 10
  - C_UOM_ID.X12DE355 = TU
  - QtyItemCapacity = 10

  DesadvLine
  - QtyEntered = 10
  - QtyOrdered = 100
  - QtyDeliveredInUOM = 5
  - C_UOM_Invoice_ID.X12DE355 = KGM
  - C_UOM_BPartner_ID.X12DE355 = TU
  - QtyEnteredInBPartnerUOM = 5
  - BPartner_QtyItemCapacity = 5

    Given metasfresh contains M_Products:
      | Identifier | Name                     | IsStocked |
      | p_1        | desadvProduct_29042022_1 | true      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name             | OPT.IsDefaultForProduct |
      | hu_pi_item_product_1               | 4010001                     | PCE                   | 3008003                    | p_1                     | 10  | 2021-04-01 | false                  | false                 | IFCO_Test_1 x 10 PCE | false                   |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | 2002141                           | p_1                     | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.IsEdiInvoicRecipient |
      | endcustomer_1 | Endcustomer_29042022_1 | N            | Y              | 2000837                       | true                     | true                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | 1111111111111 | endcustomer_1            | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
    """
{
      "orgCode": "001",
      "externalLineId": "externalLineId_29042022_1",
      "externalHeaderId": "externalHeaderId_29042022_1",
      "dataSource": "int-Shopware",
      "bpartner": {
        "bpartnerIdentifier": "gln-1111111111111",
        "bpartnerLocationIdentifier": "gln-1111111111111",
        "contactIdentifier": null
      },
      "dateRequired": "2021-04-15",
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-desadvProduct_29042022_1",
      "qty": 10,
      "uomCode": "TU",
      "packingMaterialId": 4010001,
      "qtyItemCapacity": 5,
      "poReference": "poReference_29042022_1",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-04-15",
      "line": 1,
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "description": "lineDescription",
      "dateOrdered": "2021-04-15"
    }
    """
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
        """
{
  "externalHeaderId": "externalHeaderId_29042022_1",
  "inputDataSourceName": "int-Shopware",
  "ship": false,
  "invoice": false,
  "closeOrder": false
}
"""

    And process metasfresh response
      | C_Order_ID.Identifier |
      | o_1              |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId              | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference            | processed | docStatus |
      | o_1              | externalHeaderId_29042022_1 | endcustomer_1            | l_1                               | 2021-04-15  | SOO         | EUR          | F            | S               | poReference_29042022_1 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.IsManualPrice | OPT.BPartner_QtyItemCapacity | OPT.QtyEnteredInBPartnerUOM | OPT.QtyEntered | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1                      | o_1              | 2021-04-15  | p_1                     | 0            | 100        | 0           | 10    | 0        | EUR          | true      | TU                             | false             | 5                            | 10                          | 10             | TU                    | 10                  |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 50                        |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/shipments/process' and fulfills with '200' status code
    """
{
  "createShipmentRequest": {
    "shipmentList": [
      {
        "shipmentScheduleIdentifier ": {
          "externalHeaderId": "externalHeaderId_29042022_1",
          "externalLineId": "externalLineId_29042022_1"
        }
      }
    ]
  },
  "invoice": true,
  "closeShipmentSchedule": true
}
"""

    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | null             | shipment_1          | invoice_1          |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference        | processed | docStatus |
      | shipment_1          | endcustomer_1            | l_1                               | 2021-04-15  | poReference_29042022_1 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_UOM_ID.X12DE355 |
      | shipmentLine_1            | shipment_1            | p_1                     | 50          | true      | PCE                   |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference        | paymentTerm   | processed | docStatus |
      | invoice_1          | endcustomer_1            | l_1                               | poReference_29042022_1 | 30 Tage netto | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered | OPT.QtyEnteredInBPartnerUOM | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.C_UOM_ID.X12DE355 |
      | invoiceLine_1               | invoice_1               | p_1                     | 50          | true      | 12.50          | 5                           | TU                             | KGM                   |

    And validate created edi desadv
      | Identifier | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM |
      | edi_1      | o_1                   | 50                        | 100                     |

    And validate created edi desadv line
      | Identifier | EDI_Desadv_ID.Identifier | C_UOM_ID.X12DE355 | Line | M_Product_ID.Identifier | QtyEntered | QtyDeliveredInUOM | QtyOrdered | C_UOM_Invoice_ID.X12DE355 | QtyDeliveredInInvoiceUOM | QtyItemCapacity | C_UOM_BPartner_ID.X12DE355 | QtyEnteredInBPartnerUOM | BPartner_QtyItemCapacity | QtyDeliveredInStockingUOM |
      | edi_l_1    | edi_1                    | TU                | 1    | p_1                     | 10         | 5                 | 100        | KGM                       | 12.500                   | 10              | TU                         | 5                       | 5                        | 50                        |


  @from:cucumber
  Scenario: 2

  in:
  C_UOM_Conversions
  - PCE/TU (1/10)

  M_ProductPrice:
  - C_UOM_ID.X12DE355 = TU

  M_HU_PI_Item_Product
  - Qty = 10

  C_OLCand:
  - IsManualPrice = false
  - C_UOM_ID.X12DE355 = PCE
  - C_Internal_UOM_Id.X12DE355 = TU
  - Qty = 10
  - QtyItemCapacity = 5
  - PackingMaterialId.Qty = 10

  out:
  C_OrderLine
  - C_UOM_BPartner_ID.X12DE355 = PCE
  - QtyOrdered = 100
  - BPartner_QtyItemCapacity = 5
  - QtyEnteredInBPartnerUOM = 10
  - QtyEntered = 10
  - C_UOM_ID.X12DE355 = TU
  - QtyItemCapacity = 10

  DesadvLine
  - QtyEntered = 10
  - QtyOrdered = 100
  - QtyDeliveredInUOM = 5
  - C_UOM_Invoice_ID.X12DE355 = TU
  - C_UOM_BPartner_ID.X12DE355 = PCE
  - QtyEnteredInBPartnerUOM = 5
  - BPartner_QtyItemCapacity = 5

    Given metasfresh contains M_Products:
      | Identifier | Name                     | IsStocked |
      | p_1        | desadvProduct_02052022_2 | true      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | PCE                    | TU                   | 0.1          |
    And metasfresh contains M_HU_PI_Item_Product:
      | OPT.M_HU_PI_Item_Product_ID | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name             | OPT.IsDefaultForProduct |
      | 4010002                     | hu_pi_item_product_1               | PCE                   | 3008003                    | p_1                     | 10  | 2021-04-01 | false                  | false                 | IFCO_Test_2 x 10 PCE | false                   |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_1       | 2002141                           | p_1                     | 10.0     | TU                | Normal                        | hu_pi_item_product_1                   |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.IsEdiInvoicRecipient |
      | endcustomer_1 | Endcustomer_02052022_2 | N            | Y              | 2000837                       | true                     | true                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | 2222222222222 | endcustomer_1            | true                | true         |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
    """
{
      "orgCode": "001",
      "externalLineId": "externalLineId02052022_2",
      "externalHeaderId": "externalHeaderId02052022_2",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-2222222222222",
        "bpartnerLocationIdentifier": "gln-2222222222222",
        "contactIdentifier": null
      },
      "dateRequired": "2021-04-15",
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-desadvProduct_02052022_2",
      "qty": 10,
      "uomCode": "PCE",
      "packingMaterialId": 4010002,
      "qtyItemCapacity": 5,
      "poReference": "poReference_02052022_2",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-04-15",
      "line": 1,
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "description": "lineDescription",
      "dateOrdered": "2021-04-15"
    }
    """
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
        """
{
  "externalHeaderId": "externalHeaderId02052022_2",
  "inputDataSourceName": "int-Shopware",
  "ship": false,
  "invoice": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | o_1              |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId             | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference            | processed | docStatus |
      | o_1              | externalHeaderId02052022_2 | endcustomer_1            | l_1                               | 2021-04-15  | SOO         | EUR          | F            | S               | poReference_02052022_2 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.IsManualPrice | OPT.BPartner_QtyItemCapacity | OPT.QtyEnteredInBPartnerUOM | OPT.QtyEntered | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1                      | o_1              | 2021-04-15  | p_1                     | 0            | 100        | 0           | 10    | 0        | EUR          | true      | PCE                            | false             | 5                            | 10                          | 10             | TU                    | 10                  |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 50                        |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/shipments/process' and fulfills with '200' status code
    """
{
  "createShipmentRequest": {
    "shipmentList": [
      {
        "shipmentScheduleIdentifier ": {
          "externalHeaderId": "externalHeaderId02052022_2",
          "externalLineId": "externalLineId02052022_2"
        }
      }
    ]
  },
  "invoice": true,
  "closeShipmentSchedule": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | null             | shipment_1          | invoice_1          |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference        | processed | docStatus |
      | shipment_1          | endcustomer_1            | l_1                               | 2021-04-15  | poReference_02052022_2 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_UOM_ID.X12DE355 |
      | shipmentLine_1            | shipment_1            | p_1                     | 50          | true      | PCE                   |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference        | paymentTerm   | processed | docStatus |
      | invoice_1          | endcustomer_1            | l_1                               | poReference_02052022_2 | 30 Tage netto | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered | OPT.QtyEnteredInBPartnerUOM | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.C_UOM_ID.X12DE355 |
      | invoiceLine_1               | invoice_1               | p_1                     | 50          | true      | 5              | 5                           | PCE                            | TU                    |

    And validate created edi desadv
      | Identifier | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM |
      | edi_1      | o_1                   | 50                        | 100                     |

    And validate created edi desadv line
      | Identifier | EDI_Desadv_ID.Identifier | C_UOM_ID.X12DE355 | Line | M_Product_ID.Identifier | QtyEntered | QtyDeliveredInUOM | QtyOrdered | C_UOM_Invoice_ID.X12DE355 | QtyDeliveredInInvoiceUOM | QtyItemCapacity | C_UOM_BPartner_ID.X12DE355 | QtyEnteredInBPartnerUOM | BPartner_QtyItemCapacity | QtyDeliveredInStockingUOM |
      | edi_l_1    | edi_1                    | TU                | 1    | p_1                     | 10         | 5                 | 100        | TU                        | 5                        | 10              | PCE                        | 5                       | 5                        | 50                        |


  @from:cucumber
  Scenario: 3
  in:

  M_ProductPrice:
  - C_UOM_ID.X12DE355 = PCE

  M_HU_PI_Item_Product
  - Qty = 10

  C_OLCand:
  - IsManualPrice = true
  - C_UOM_ID.X12DE355 = PCE
  - C_Internal_UOM_Id.X12DE355 = PCE
  - Qty = 10
  - QtyItemCapacity = 5
  - PackingMaterialId.Qty = 10

  out:
  C_OrderLine
  - C_UOM_BPartner_ID.X12DE355 = PCE
  - QtyOrdered = 10
  - BPartner_QtyItemCapacity = 5
  - QtyEnteredInBPartnerUOM = 10
  - QtyEntered = 10
  - C_UOM_ID.X12DE355 = PCE
  - QtyItemCapacity = 10

  DesadvLine
  - QtyEntered = 10
  - QtyOrdered = 10
  - QtyDeliveredInUOM = 5
  - C_UOM_Invoice_ID.X12DE355 = PCE
  - C_UOM_BPartner_ID.X12DE355 = PCE
  - QtyEnteredInBPartnerUOM = 5
  - BPartner_QtyItemCapacity = 5

    Given metasfresh contains M_Products:
      | Identifier | Name                     | IsStocked |
      | p_1        | desadvProduct_03052022_3 | true      |
    And metasfresh contains M_HU_PI_Item_Product:
      | OPT.M_HU_PI_Item_Product_ID | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name             | OPT.IsDefaultForProduct |
      | 4010003                     | hu_pi_item_product_1               | PCE                   | 3008003                    | p_1                     | 10  | 2021-04-01 | false                  | false                 | IFCO_Test_3 x 10 PCE | false                   |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | 2002141                           | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.IsEdiInvoicRecipient |
      | endcustomer_1 | Endcustomer_03052022_3 | N            | Y              | 2000837                       | true                     | true                     |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | 3333333333333 | endcustomer_1            | true                | true         |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
    """
{
      "orgCode": "001",
      "externalLineId": "externalLineId03052022_3",
      "externalHeaderId": "externalHeaderId03052022_3",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-3333333333333",
        "bpartnerLocationIdentifier": "gln-3333333333333",
        "contactIdentifier": null
      },
      "dateRequired": "2021-04-15",
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-desadvProduct_03052022_3",
      "qty": 10,
      "uomCode": "PCE",
      "packingMaterialId": 4010003,
      "qtyItemCapacity": 5,
      "poReference": "poReference_03052022_3",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-04-15",
      "line": 1,
      "isManualPrice": true,
      "currencyCode": "EUR",
      "price": 10,
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "description": "lineDescription",
      "dateOrdered": "2021-04-15"
    }
    """
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
        """
{
  "externalHeaderId": "externalHeaderId03052022_3",
  "inputDataSourceName": "int-Shopware",
  "ship": false,
  "invoice": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | o_1              |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId             | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference            | processed | docStatus |
      | o_1              | externalHeaderId03052022_3 | endcustomer_1            | l_1                               | 2021-04-15  | SOO         | EUR          | F            | S               | poReference_03052022_3 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.IsManualPrice | OPT.BPartner_QtyItemCapacity | OPT.QtyEnteredInBPartnerUOM | OPT.QtyEntered | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1                      | o_1              | 2021-04-15  | p_1                     | 0            | 10         | 0           | 10    | 0        | EUR          | true      | PCE                            | true              | 5                            | 10                          | 10             | PCE                   | 10                  |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 5                         |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/shipments/process' and fulfills with '200' status code
    """
{
  "createShipmentRequest": {
    "shipmentList": [
      {
        "shipmentScheduleIdentifier ": {
          "externalHeaderId": "externalHeaderId03052022_3",
          "externalLineId": "externalLineId03052022_3"
        }
      }
    ]
  },
  "invoice": true,
  "closeShipmentSchedule": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | null             | shipment_1          | invoice_1          |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference        | processed | docStatus |
      | shipment_1          | endcustomer_1            | l_1                               | 2021-04-15  | poReference_03052022_3 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_UOM_ID.X12DE355 |
      | shipmentLine_1            | shipment_1            | p_1                     | 5           | true      | PCE                   |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference        | paymentTerm   | processed | docStatus |
      | invoice_1          | endcustomer_1            | l_1                               | poReference_03052022_3 | 30 Tage netto | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered | OPT.QtyEnteredInBPartnerUOM | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.C_UOM_ID.X12DE355 |
      | invoiceLine_1               | invoice_1               | p_1                     | 5           | true      | 5              | 5                           | PCE                            | PCE                   |

    And validate created edi desadv
      | Identifier | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM |
      | edi_1      | o_1                   | 5                         | 10                      |

    And validate created edi desadv line
      | Identifier | EDI_Desadv_ID.Identifier | C_UOM_ID.X12DE355 | Line | M_Product_ID.Identifier | QtyEntered | QtyDeliveredInUOM | QtyOrdered | C_UOM_Invoice_ID.X12DE355 | QtyDeliveredInInvoiceUOM | QtyItemCapacity | C_UOM_BPartner_ID.X12DE355 | QtyEnteredInBPartnerUOM | BPartner_QtyItemCapacity | QtyDeliveredInStockingUOM |
      | edi_l_1    | edi_1                    | PCE               | 1    | p_1                     | 10         | 5                 | 10         | PCE                       | 5                        | 10              | PCE                        | 5                       | 5                        | 5                         |

  @from:cucumber
  Scenario: 4

  in:
  C_UOM_Conversion:
  - PCE/KGM (1/4)

  M_ProductPrice:
  - C_UOM_ID.X12DE355 = KGM

  M_HU_PI_Item_Product
  - Qty = 10

  C_OLCand:
  - IsManualPrice = false
  - C_UOM_ID.X12DE355 = PCE
  - C_Internal_UOM_Id.X12DE355 = PCE
  - Qty = 10
  - QtyItemCapacity = 5
  - PackingMaterialId.Qty = 10

  out:
  C_OrderLine
  - C_UOM_BPartner_ID.X12DE355 = PCE
  - QtyOrdered = 10
  - BPartner_QtyItemCapacity = 5
  - QtyEnteredInBPartnerUOM = 10
  - QtyEntered = 10
  - C_UOM_ID.X12DE355 = PCE
  - QtyItemCapacity = 5

  DesadvLine
  - QtyEntered = 10
  - QtyOrdered = 10
  - QtyDeliveredInUOM = 5
  - C_UOM_Invoice_ID.X12DE355 = KGM
  - C_UOM_BPartner_ID.X12DE355 = PCE
  - QtyEnteredInBPartnerUOM = 5
  - BPartner_QtyItemCapacity = 5

    Given metasfresh contains M_Products:
      | Identifier | Name                     | IsStocked |
      | p_1        | desadvProduct_03052022_4 | true      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_HU_PI_Item_Product:
      | OPT.M_HU_PI_Item_Product_ID | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name             | OPT.IsDefaultForProduct |
      | 4010005                     | hu_pi_item_product_1               | PCE                   | 3008003                    | p_1                     | 10  | 2021-04-01 | false                  | false                 | IFCO_Test_4 x 10 PCE | false                   |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | 2002141                           | p_1                     | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.IsEdiInvoicRecipient |
      | endcustomer_1 | Endcustomer_03052022_4 | N            | Y              | 2000837                       | true                     | true                     |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | 4444444444444 | endcustomer_1            | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
    """
{
      "orgCode": "001",
      "externalLineId": "externalLineId03052022_4",
      "externalHeaderId": "externalHeaderId03052022_4",
      "dataSource": "int-Shopware",
      "dataDest": null,
      "bpartner": {
        "bpartnerIdentifier": "gln-4444444444444",
        "bpartnerLocationIdentifier": "gln-4444444444444",
        "contactIdentifier": null
      },
      "dateRequired": "2021-04-15",
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-desadvProduct_03052022_4",
      "qty": 10,
      "uomCode": "PCE",
      "qtyItemCapacity": 5,
      "poReference": "poReference_03052022_4",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-04-15",
      "line": 1,
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "description": "lineDescription",
      "dateOrdered": "2021-04-15"
    }
    """
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
        """
{
  "externalHeaderId": "externalHeaderId03052022_4",
  "inputDataSourceName": "int-Shopware",
  "ship": false,
  "invoice": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | o_1              |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId             | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference            | processed | docStatus |
      | o_1              | externalHeaderId03052022_4 | endcustomer_1            | l_1                               | 2021-04-15  | SOO         | EUR          | F            | S               | poReference_03052022_4 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.IsManualPrice | OPT.BPartner_QtyItemCapacity | OPT.QtyEnteredInBPartnerUOM | OPT.QtyEntered | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1                      | o_1              | 2021-04-15  | p_1                     | 0            | 10         | 0           | 10    | 0        | EUR          | true      | PCE                            | false             | 5                            | 10                          | 10             | PCE                   | 5                   |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 5                         |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/shipments/process' and fulfills with '200' status code
    """
{
  "createShipmentRequest": {
    "shipmentList": [
      {
        "shipmentScheduleIdentifier ": {
          "externalHeaderId": "externalHeaderId03052022_4",
          "externalLineId": "externalLineId03052022_4"
        }
      }
    ]
  },
  "invoice": true,
  "closeShipmentSchedule": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | null             | shipment_1          | invoice_1          |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference        | processed | docStatus |
      | shipment_1          | endcustomer_1            | l_1                               | 2021-04-15  | poReference_03052022_4 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_UOM_ID.X12DE355 |
      | shipmentLine_1            | shipment_1            | p_1                     | 5           | true      | PCE                   |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference        | paymentTerm   | processed | docStatus |
      | invoice_1          | endcustomer_1            | l_1                               | poReference_03052022_4 | 30 Tage netto | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered | OPT.QtyEnteredInBPartnerUOM | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.C_UOM_ID.X12DE355 |
      | invoiceLine_1               | invoice_1               | p_1                     | 5           | true      | 1.25           | 5                           | PCE                            | KGM                   |

    And validate created edi desadv
      | C_InvoiceLine_ID.Identifier | Identifier | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM |
      | invoiceLine_1               | edi_1      | o_1                   | 5                         | 10                      |

    And validate created edi desadv line
      | Identifier | EDI_Desadv_ID.Identifier | C_UOM_ID.X12DE355 | Line | M_Product_ID.Identifier | QtyEntered | QtyDeliveredInUOM | QtyOrdered | C_UOM_Invoice_ID.X12DE355 | QtyDeliveredInInvoiceUOM | QtyItemCapacity | C_UOM_BPartner_ID.X12DE355 | QtyEnteredInBPartnerUOM | BPartner_QtyItemCapacity | QtyDeliveredInStockingUOM |
      | edi_l_1    | edi_1                    | PCE               | 1    | p_1                     | 10         | 5                 | 10         | KGM                       | 1.25                     | 5               | PCE                        | 5                       | 5                        | 5                         |

  @from:cucumber
  Scenario: 5

  in:
  C_UOM_Conversion:
  - PCE/KGM (1/4)

  M_ProductPrice:
  - C_UOM_ID.X12DE355 = PCE
  - Price = 10 EUR

  M_HU_PI_Item_Product
  - Qty = 10

  C_OLCand:
  - IsManualPrice = true
  - Price = 50 EUR
  - C_UOM_ID.X12DE355 = KGM
  - C_Internal_UOM_Id.X12DE355 = KGM
  - Qty = 10
  - QtyItemCapacity = 5
  - PackingMaterialId.Qty = 10

  out:
  C_OrderLine
  - C_UOM_BPartner_ID.X12DE355 = KGM
  - QtyEnteredInBPartnerUOM = 10
  - BPartner_QtyItemCapacity = 5
  - QtyOrdered = 40
  - QtyEntered = 10
  - C_UOM_ID.X12DE355 = KGM
  - QtyItemCapacity = 10

  DesadvLine (Shipped half the qty)
  - QtyEntered = 10
  - QtyOrdered = 40
  - QtyDeliveredInUOM = 5
  - C_UOM_ID = KGM
  - C_UOM_BPartner_ID.X12DE355 = KGM
  - QtyEnteredInBPartnerUOM = 5
  - BPartner_QtyItemCapacity = 5

    Given metasfresh contains M_Products:
      | Identifier | Name                     | IsStocked |
      | p_1        | desadvProduct_03052022_5 | true      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_HU_PI_Item_Product:
      | OPT.M_HU_PI_Item_Product_ID | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name             | OPT.IsDefaultForProduct |
      | 4010005                     | hu_pi_item_product_1               | PCE                   | 3008003                    | p_1                     | 10  | 2021-04-01 | false                  | false                 | IFCO_Test_5 x 10 PCE | false                   |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | 2002141                           | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.IsEdiInvoicRecipient |
      | endcustomer_1 | Endcustomer_03052022_5 | N            | Y              | 2000837                       | true                     | true                     |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | 5555555555555 | endcustomer_1            | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
    """
{
      "orgCode": "001",
      "externalLineId": "externalLineId03052022_5",
      "externalHeaderId": "externalHeaderId03052022_5",
      "dataSource": "int-Shopware",
      "bpartner": {
        "bpartnerIdentifier": "gln-5555555555555",
        "bpartnerLocationIdentifier": "gln-5555555555555",
        "contactIdentifier": null
      },
      "dateRequired": "2021-04-15",
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-desadvProduct_03052022_5",
      "qty": 10,
      "uomCode": "KGM",
      "packingMaterialId": 4010005,
      "poReference": "poReference_03052022_5",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-04-15",
      "qtyItemCapacity": 5,
      "line": 1,
      "isManualPrice": true,
      "currencyCode": "EUR",
      "price": 50,
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "description": "lineDescription",
      "dateOrdered": "2021-04-15"
    }
    """
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
        """
{
  "externalHeaderId": "externalHeaderId03052022_5",
  "inputDataSourceName": "int-Shopware",
  "ship": false,
  "invoice": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | o_1              |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId             | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference            | processed | docStatus |
      | o_1              | externalHeaderId03052022_5 | endcustomer_1            | l_1                               | 2021-04-15  | SOO         | EUR          | F            | S               | poReference_03052022_5 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.IsManualPrice | OPT.BPartner_QtyItemCapacity | OPT.QtyEnteredInBPartnerUOM | OPT.QtyEntered | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1                      | o_1              | 2021-04-15  | p_1                     | 0            | 40         | 0           | 50    | 0        | EUR          | true      | KGM                            | true              | 5                            | 10                          | 10             | KGM                   | 10                  |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 20                        |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/shipments/process' and fulfills with '200' status code
    """
{
  "createShipmentRequest": {
    "shipmentList": [
      {
        "shipmentScheduleIdentifier ": {
          "externalHeaderId": "externalHeaderId03052022_5",
          "externalLineId": "externalLineId03052022_5"
        }
      }
    ]
  },
  "invoice": true,
  "closeShipmentSchedule": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | null             | shipment_1          | invoice_1          |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference        | processed | docStatus |
      | shipment_1          | endcustomer_1            | l_1                               | 2021-04-15  | poReference_03052022_5 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_UOM_ID.X12DE355 |
      | shipmentLine_1            | shipment_1            | p_1                     | 20          | true      | PCE                   |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference        | paymentTerm   | processed | docStatus |
      | invoice_1          | endcustomer_1            | l_1                               | poReference_03052022_5 | 30 Tage netto | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered | OPT.QtyEnteredInBPartnerUOM | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.C_UOM_ID.X12DE355 |
      | invoiceLine_1               | invoice_1               | p_1                     | 20          | true      | 20             | 5                           | KGM                            | PCE                   |

    And validate created edi desadv
      | Identifier | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM |
      | edi_1      | o_1                   | 20                        | 40                      |

    And validate created edi desadv line
      | Identifier | EDI_Desadv_ID.Identifier | C_UOM_ID.X12DE355 | Line | M_Product_ID.Identifier | QtyEntered | QtyDeliveredInUOM | QtyOrdered | C_UOM_Invoice_ID.X12DE355 | QtyDeliveredInInvoiceUOM | QtyItemCapacity | C_UOM_BPartner_ID.X12DE355 | QtyEnteredInBPartnerUOM | BPartner_QtyItemCapacity | QtyDeliveredInStockingUOM |
      | edi_l_1    | edi_1                    | KGM               | 1    | p_1                     | 10         | 5                 | 40         | PCE                       | 20                       | 10              | KGM                        | 5                       | 5                        | 20                        |

  @from:cucumber
  Scenario: 6

  in:
  C_UOM_Conversion:
  - PCE/TU (1/1)

  M_ProductPrice:
  - C_UOM_ID.X12DE355 = PCE

  M_HU_PI_Item_Product
  - IsInfiniteCapacity = true

  C_OLCand:
  - IsManualPrice = true
  - Price = 30
  - C_UOM_ID.X12DE355 = TU
  - C_Internal_UOM_Id.X12DE355 = TU
  - Qty = 10
  - QtyItemCapacity = 5
  - PackingMaterialId.Qty = 10

  out:
  C_OrderLine
  - C_UOM_BPartner_ID.X12DE355 = TU
  - QtyOrdered = 50
  - BPartner_QtyItemCapacity = 5
  - QtyEnteredInBPartnerUOM = 10
  - QtyEntered = 10
  - C_UOM_ID.X12DE355 = TU
  - QtyItemCapacity = 5

  DesadvLine
  - QtyEntered = 10
  - QtyOrdered = 50
  - QtyDeliveredInUOM = 5
  - C_UOM_Invoice_ID.X12DE355 = PCE
  - C_UOM_BPartner_ID.X12DE355 = TU
  - QtyEnteredInBPartnerUOM = 5
  - BPartner_QtyItemCapacity = 5

    Given metasfresh contains M_Products:
      | Identifier | Name                     | IsStocked |
      | p_1        | desadvProduct_04052022_6 | true      |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_1                     | PCE                    | TU                   | 1            |
    And metasfresh contains M_HU_PI_Item_Product:
      | OPT.M_HU_PI_Item_Product_ID | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name             | OPT.IsDefaultForProduct |
      | 4010006                     | hu_pi_item_product_1               | PCE                   | 3008003                    | p_1                     | 10  | 2021-04-01 | true                   | false                 | IFCO_Test_6 x 10 PCE | false                   |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | 2002141                           | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.IsEdiInvoicRecipient |
      | endcustomer_1 | Endcustomer_04052022_6 | N            | Y              | 2000837                       | true                     | true                     |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | l_1        | 6666666666666 | endcustomer_1            | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
    """
{
      "orgCode": "001",
      "externalLineId": "externalLineId04052022_6",
      "externalHeaderId": "externalHeaderId04052022_6",
      "dataSource": "int-Shopware",
      "bpartner": {
        "bpartnerIdentifier": "gln-6666666666666",
        "bpartnerLocationIdentifier": "gln-6666666666666",
        "contactIdentifier": null
      },
      "dateRequired": "2021-04-15",
      "orderDocType": "SalesOrder",
      "productIdentifier": "val-desadvProduct_04052022_6",
      "qty": 10,
      "uomCode": "TU",
      "packingMaterialId": 4010006,
      "poReference": "poReference_04052022_6",
      "warehouseCode": null,
      "warehouseDestCode": null,
      "dateCandidate": "2021-04-15",
      "qtyItemCapacity": 5,
      "line": 1,
      "isManualPrice": true,
      "currencyCode": "EUR",
      "price": 10,
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "description": "lineDescription",
      "dateOrdered": "2021-04-15"
    }
    """
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
        """
{
  "externalHeaderId": "externalHeaderId04052022_6",
  "inputDataSourceName": "int-Shopware",
  "ship": false,
  "invoice": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | o_1              |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId             | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference            | processed | docStatus |
      | o_1              | externalHeaderId04052022_6 | endcustomer_1            | l_1                               | 2021-04-15  | SOO         | EUR          | F            | S               | poReference_04052022_6 | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.IsManualPrice | OPT.BPartner_QtyItemCapacity | OPT.QtyEnteredInBPartnerUOM | OPT.QtyEntered | OPT.C_UOM_ID.X12DE355 | OPT.QtyItemCapacity |
      | ol_1                      | o_1              | 2021-04-15  | p_1                     | 0            | 50         | 0           | 10    | 0        | EUR          | true      | TU                             | true              | 5                            | 10                          | 10             | TU                    | 5                   |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | s_s_1                            | 25                        |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/shipments/process' and fulfills with '200' status code
    """
{
  "createShipmentRequest": {
    "shipmentList": [
      {
        "shipmentScheduleIdentifier ": {
          "externalHeaderId": "externalHeaderId04052022_6",
          "externalLineId": "externalLineId04052022_6"
        }
      }
    ]
  },
  "invoice": true,
  "closeShipmentSchedule": true
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | null             | shipment_1          | invoice_1          |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | OPT.POReference        | processed | docStatus |
      | shipment_1          | endcustomer_1            | l_1                               | 2021-04-15  | poReference_04052022_6 | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_UOM_ID.X12DE355 |
      | shipmentLine_1            | shipment_1            | p_1                     | 25          | true      | PCE                   |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference        | paymentTerm   | processed | docStatus |
      | invoice_1          | endcustomer_1            | l_1                               | poReference_04052022_6 | 30 Tage netto | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.QtyEntered | OPT.QtyEnteredInBPartnerUOM | OPT.C_UOM_BPartner_ID.X12DE355 | OPT.C_UOM_ID.X12DE355 |
      | invoiceLine_1               | invoice_1               | p_1                     | 25          | true      | 25             | 5                           | TU                             | PCE                   |

    And validate created edi desadv
      | Identifier | C_Order_ID.Identifier | SumDeliveredInStockingUOM | SumOrderedInStockingUOM |
      | edi_1      | o_1                   | 25                        | 50                      |

    And validate created edi desadv line
      | Identifier | EDI_Desadv_ID.Identifier | C_UOM_ID.X12DE355 | Line | M_Product_ID.Identifier | QtyEntered | QtyDeliveredInUOM | QtyOrdered | C_UOM_Invoice_ID.X12DE355 | QtyDeliveredInInvoiceUOM | QtyItemCapacity | C_UOM_BPartner_ID.X12DE355 | QtyEnteredInBPartnerUOM | BPartner_QtyItemCapacity | QtyDeliveredInStockingUOM |
      | edi_l_1    | edi_1                    | TU                | 1    | p_1                     | 10         | 5                 | 50         | PCE                       | 25                       | 5               | TU                         | 5                       | 5                        | 25                        |