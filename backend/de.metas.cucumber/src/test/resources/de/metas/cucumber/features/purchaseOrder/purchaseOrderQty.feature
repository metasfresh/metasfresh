@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
@ghActions:run_on_executor7
Feature: Purchase order
## F00600: Purchase Order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier          | Value               | Name                |
      | product_PO_17062022 | product_PO_17062022 | product_PO_17062022 |

    And load M_HU_PI:
      | M_HU_PI_ID.Identifier  | M_HU_PI_ID |
      | huPackingTauschpalette | 1000006    |
    And load M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_Version_ID |
      | packingItem_IFCO              | 2002669            |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItem_IFCO              | packingItem_IFCO              | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemProduct_17062022           | huPiItem_IFCO              | product_PO_17062022     | 10  | 2022-03-01 |

    And metasfresh contains M_PricingSystems
      | Identifier | Name  | Value |
      | ps_PO      | ps_PO | ps_PO |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_PO_name | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_PO     | pl_PO                     | plv_PO | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | product_PO_17062022     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | Value                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | supplier_PO | supplier_PO_17062022 | supplier_PO_17062022 | Y            | N              | ps_PO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | supplierLocation_PO | 1706202211111 | supplier_PO              | Y                   | Y                   |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  @Id:S0156_100
  Scenario: Create a new purchase order, receive partial quantity and then close receipt schedule. Validate that `QtyOrdered` from order is not overridden and there is no qty allocated for the order in question
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO_S0156_100 | N       | supplier_PO              | 2022-06-10  | po_ref_S0156_100 | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_S0156_100 | order_PO_S0156_100    | product_PO_17062022     | 26         |

    When the order identified by order_PO_S0156_100 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_100    | order_PO_S0156_100    | product_PO_17062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_100    | order_PO_S0156_100    | orderLine_PO_S0156_100    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 0            | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_100          | orderLine_PO_S0156_100    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_100          | order_PO_S0156_100        | orderLine_PO_S0156_100        | 0            | 26             | 0                | false                |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_S0156_100                | hu_S0156_100       | receiptSchedule_PO_S0156_100    | N               | 1     | N               | 3     | N               | 8           | huPiItemProduct_17062022           | huPackingTauschpalette       |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_S0156_100       | receiptSchedule_PO_S0156_100    | inOut_PO_S0156_100    |
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_100    | order_PO_S0156_100    | product_PO_17062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 2               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_100    | order_PO_S0156_100    | orderLine_PO_S0156_100    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 24           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_100          | orderLine_PO_S0156_100    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_100          | order_PO_S0156_100        | orderLine_PO_S0156_100        | 24           | 26             | 24               | false                |

    When the M_ReceiptSchedule identified by receiptSchedule_PO_S0156_100 is closed
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_100    | order_PO_S0156_100    | product_PO_17062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 0               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_100    | order_PO_S0156_100    | orderLine_PO_S0156_100    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 24           | true          |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_100          | orderLine_PO_S0156_100    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_100          | order_PO_S0156_100        | orderLine_PO_S0156_100        | 24           | 26             | 24               | true                 |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  @Id:S0156_200
  Scenario: Create a new purchase order, receive partial quantity, close the receipt schedule and then reactivate it. Validate that `QtyOrdered` from order is not overridden and the unreceived qty is still allocated for the order in question
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO_S0156_200 | N       | supplier_PO              | 2022-06-10  | po_ref_S0156_200 | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_S0156_200 | order_PO_S0156_200    | product_PO_17062022     | 26         |

    When the order identified by order_PO_S0156_200 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_200    | order_PO_S0156_200    | product_PO_17062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_200    | order_PO_S0156_200    | orderLine_PO_S0156_200    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 0            | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_200          | orderLine_PO_S0156_200    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_200          | order_PO_S0156_200        | orderLine_PO_S0156_200        | 0            | 26             | 0                | false                |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_S0156_200                | hu_S0156_200       | receiptSchedule_PO_S0156_200    | N               | 1     | N               | 3     | N               | 8           | huPiItemProduct_17062022           | huPackingTauschpalette       |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_S0156_200       | receiptSchedule_PO_S0156_200    | inOut_PO_S0156_200    |
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_200    | order_PO_S0156_200    | product_PO_17062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 2               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_200    | order_PO_S0156_200    | orderLine_PO_S0156_200    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 24           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_200          | orderLine_PO_S0156_200    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_200          | order_PO_S0156_200        | orderLine_PO_S0156_200        | 24           | 26             | 24               | false                |

    When the M_ReceiptSchedule identified by receiptSchedule_PO_S0156_200 is closed
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_200    | order_PO_S0156_200    | product_PO_17062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 0               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_200    | order_PO_S0156_200    | orderLine_PO_S0156_200    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 24           | true          |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_200          | orderLine_PO_S0156_200    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_200          | order_PO_S0156_200        | orderLine_PO_S0156_200        | 24           | 26             | 24               | true                 |

    When the M_ReceiptSchedule identified by receiptSchedule_PO_S0156_200 is reactivated
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_200    | order_PO_S0156_200    | product_PO_17062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 2               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_200    | order_PO_S0156_200    | orderLine_PO_S0156_200    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 24           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_200          | orderLine_PO_S0156_200    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_200          | order_PO_S0156_200        | orderLine_PO_S0156_200        | 24           | 26             | 24               | false                |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  @Id:S0156_300
  Scenario: Create a new purchase order and receive all the ordered quantity. Validate that `QtyOrdered` is propagated accordingly
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO_S0156_300 | N       | supplier_PO              | 2022-06-10  | po_ref_S0156_300 | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_S0156_300 | order_PO_S0156_300    | product_PO_17062022     | 27         |

    When the order identified by order_PO_S0156_300 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_300    | order_PO_S0156_300    | product_PO_17062022     | 27         | 0            | 0           | 10    | 0        | EUR          | true      | 27              |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_300    | order_PO_S0156_300    | orderLine_PO_S0156_300    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 27         | warehouseStd              | 0            | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_300          | orderLine_PO_S0156_300    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_300          | order_PO_S0156_300        | orderLine_PO_S0156_300        | 0            | 27             | 0                | false                |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_S0156_300                | hu_S0156_300       | receiptSchedule_PO_S0156_300    | N               | 1     | N               | 3     | N               | 9           | huPiItemProduct_17062022           | huPackingTauschpalette       |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_S0156_300       | receiptSchedule_PO_S0156_300    | inOut_PO_S0156_300    |
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_300    | order_PO_S0156_300    | product_PO_17062022     | 27         | 27           | 0           | 10    | 0        | EUR          | true      | 0               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_300    | order_PO_S0156_300    | orderLine_PO_S0156_300    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 27         | warehouseStd              | 27           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_300          | orderLine_PO_S0156_300    | 27           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_300          | order_PO_S0156_300        | orderLine_PO_S0156_300        | 27           | 27             | 27               | false                |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  @Id:S0156_400
  Scenario: Create new purchase order and receive the ordered quantity in two shipments. Validate that `QtyOrdered` is propagated accordingly and there is no qty allocated for the order in question
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO_S0156_400 | N       | supplier_PO              | 2022-06-10  | po_ref_S0156_400 | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_S0156_400 | order_PO_S0156_400    | product_PO_17062022     | 26         |

    When the order identified by order_PO_S0156_400 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_400    | order_PO_S0156_400    | product_PO_17062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_400    | order_PO_S0156_400    | orderLine_PO_S0156_400    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 0            | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_400          | orderLine_PO_S0156_400    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_400          | order_PO_S0156_400        | orderLine_PO_S0156_400        | 0            | 26             | 0                | false                |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_S0156_400_1              | hu_S0156_400_1     | receiptSchedule_PO_S0156_400    | N               | 1     | N               | 3     | N               | 8           | huPiItemProduct_17062022           | huPackingTauschpalette       |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_S0156_400_1     | receiptSchedule_PO_S0156_400    | inOut_PO_S0156_400_1  |
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_400    | order_PO_S0156_400    | product_PO_17062022     | 26         | 24           | 0           | 10    | 0        | EUR          | true      | 2               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_400    | order_PO_S0156_400    | orderLine_PO_S0156_400    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 24           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_400          | orderLine_PO_S0156_400    | 24           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_400          | order_PO_S0156_400        | orderLine_PO_S0156_400        | 24           | 26             | 24               | false                |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_S0156_400_2              | hu_S0156_400_2     | receiptSchedule_PO_S0156_400    | N               | 1     | N               | 1     | N               | 2           | huPiItemProduct_17062022           | huPackingTauschpalette       |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_S0156_400_2     | receiptSchedule_PO_S0156_400    | inOut_PO_S0156_400    |
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_400    | order_PO_S0156_400    | product_PO_17062022     | 26         | 26           | 0           | 10    | 0        | EUR          | true      | 0               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_400    | order_PO_S0156_400    | orderLine_PO_S0156_400    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 26           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_400          | orderLine_PO_S0156_400    | 26           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_400          | order_PO_S0156_400        | orderLine_PO_S0156_400        | 26           | 26             | 26               | false                |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  @Id:S0156_500
  Scenario: Create new purchase order and receive more than the ordered quantity. Validate that `QtyOrdered` is propagated accordingly
    Given metasfresh contains C_Orders:
      | Identifier         | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO_S0156_500 | N       | supplier_PO              | 2022-06-10  | po_ref_S0156_500 | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier             | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_S0156_500 | order_PO_S0156_500    | product_PO_17062022     | 26         |

    When the order identified by order_PO_S0156_500 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_500    | order_PO_S0156_500    | product_PO_17062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_500    | order_PO_S0156_500    | orderLine_PO_S0156_500    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 0            | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_500          | orderLine_PO_S0156_500    | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_500          | order_PO_S0156_500        | orderLine_PO_S0156_500        | 0            | 26             | 0                | false                |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_S0156_500                | hu_S0156_500       | receiptSchedule_PO_S0156_500    | N               | 1     | N               | 3     | N               | 10          | huPiItemProduct_17062022           | huPackingTauschpalette       |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_S0156_500       | receiptSchedule_PO_S0156_500    | inOut_PO_S0156_500    |
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_S0156_500    | order_PO_S0156_500    | product_PO_17062022     | 26         | 30           | 0           | 10    | 0        | EUR          | true      | 0               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_S0156_500    | order_PO_S0156_500    | orderLine_PO_S0156_500    | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 30           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_S0156_500          | orderLine_PO_S0156_500    | 30           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_S0156_500          | order_PO_S0156_500        | orderLine_PO_S0156_500        | 30           | 26             | 30               | false                |

  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  Scenario: Create purchase order, create material receipt for it, reactivate, add a new order line to it and recomplete the order
    Given set sys config boolean value true for sys config PO_AllowReactivationIfReceiptsCreated
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO_12192025 | N       | supplier_PO              | 2022-06-10  | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_12192025 | order_PO_12192025     | product_PO_17062022     | 10         |

    And the order identified by order_PO_12192025 is completed

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_12192025     | order_PO_12192025     | product_PO_17062022     | 10         | 0            | 0           | 10    | 0        | EUR          | true      | 10              |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_12192025     | order_PO_12192025     | orderLine_PO_12192025     | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 10         | warehouseStd              | 0            | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_12192025           | orderLine_PO_12192025     | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_12192025           | order_PO_12192025         | orderLine_PO_12192025         | 0            | 10             | 0                | false                |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_PO_12192025              | hu_PO_12192025     | receiptSchedule_PO_12192025     | N               | 1     | N               | 1     | N               | 10          | huPiItemProduct_17062022           | huPackingTauschpalette       |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_PO_12192025     | receiptSchedule_PO_12192025     | inOut_PO_12192025     |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_12192025     | order_PO_12192025     | product_PO_17062022     | 10         | 10           | 0           | 10    | 0        | EUR          | true      | 0               |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_12192025     | order_PO_12192025     | orderLine_PO_12192025     | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 10         | warehouseStd              | 10           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_12192025           | orderLine_PO_12192025     | 10           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_12192025           | order_PO_12192025         | orderLine_PO_12192025         | 10           | 10             | 10               | false                |

    When the order identified by order_PO_12192025 is reactivated
    Then metasfresh contains C_OrderLines:
      | Identifier              | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_12192025_2 | order_PO_12192025     | product_PO_17062022     | 50         |

    And the order identified by order_PO_12192025 is completed
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_12192025_2   | order_PO_12192025     | product_PO_17062022     | 50         | 0            | 0           | 10    | 0        | EUR          | true      | 50              |
    And set sys config boolean value false for sys config PO_AllowReactivationIfReceiptsCreated
    
  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  Scenario: Create purchase order with 1 line for 100 pieces, create material receipt for 10 pieces, reactivate, decrease qty to 50, recomplete the order.
    Given set sys config boolean value true for sys config PO_AllowReactivationIfReceiptsCreated
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO_12192025 | N       | supplier_PO              | 2022-06-10  | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_12192025 | order_PO_12192025     | product_PO_17062022     | 100        |

    And the order identified by order_PO_12192025 is completed

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_12192025     | order_PO_12192025     | product_PO_17062022     | 100        | 0            | 0           | 10    | 0        | EUR          | true      | 100             |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_12192025     | order_PO_12192025     | orderLine_PO_12192025     | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 100        | warehouseStd              | 0            | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_12192025           | orderLine_PO_12192025     | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_12192025           | order_PO_12192025         | orderLine_PO_12192025         | 0            | 100            | 0                | false                |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_PO_12192025              | hu_PO_12192025     | receiptSchedule_PO_12192025     | N               | 1     | N               | 1     | N               | 10          | huPiItemProduct_17062022           | huPackingTauschpalette       |

    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_PO_12192025     | receiptSchedule_PO_12192025     | inOut_PO_12192025     |
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_12192025     | order_PO_12192025     | product_PO_17062022     | 100        | 10           | 0           | 10    | 0        | EUR          | true      | 90              |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_12192025     | order_PO_12192025     | orderLine_PO_12192025     | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 100        | warehouseStd              | 10           | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_12192025           | orderLine_PO_12192025     | 10           |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_12192025           | order_PO_12192025         | orderLine_PO_12192025         | 10           | 100            | 10               | false                |

    When the order identified by order_PO_12192025 is reactivated
    Then update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.QtyEntered |
      | orderLine_PO_12192025     | 50             |

    And the order identified by order_PO_12192025 is completed
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_12192025     | order_PO_12192025     | product_PO_17062022     | 50         | 10           | 0           | 10    | 0        | EUR          | true      | 40              |
    And set sys config boolean value false for sys config PO_AllowReactivationIfReceiptsCreated

  @from:cucumber
  @Id:S0156_600
  @allure.label.epic:E0140_Purchasing
  @allure.label.feature:F00600_Purchase_Order
  @F00600
  Scenario: Reactivate PO preserves receipt schedules (closed, not deleted); re-complete reopens them
    Given metasfresh contains C_Orders:
      | Identifier               | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO_rs_close_reopen | N       | supplier_PO              | 2022-06-10  | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier                   | C_Order_ID.Identifier    | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_rs_close_reopen | order_PO_rs_close_reopen | product_PO_17062022     | 10         |

    # 1. Complete the PO
    And the order identified by order_PO_rs_close_reopen is completed

    # 2. Validate M_ReceiptSchedule created: IsClosed=false, Processed=false
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier    | C_OrderLine_ID.Identifier    | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.Processed | OPT.IsClosed |
      | rs_PO_close_reopen              | order_PO_rs_close_reopen | orderLine_PO_rs_close_reopen | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 10         | warehouseStd              | false         | false        |

    # 3. Reactivate the PO
    When the order identified by order_PO_rs_close_reopen is reactivated

    # 4. Validate M_ReceiptSchedule STILL EXISTS and is closed: IsClosed=true, Processed=true
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier    | C_OrderLine_ID.Identifier    | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.Processed | OPT.IsClosed |
      | rs_PO_close_reopen              | order_PO_rs_close_reopen | orderLine_PO_rs_close_reopen | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 10         | warehouseStd              | true          | true         |

    # 5. Re-complete the PO
    And the order identified by order_PO_rs_close_reopen is completed

    # 6. Validate M_ReceiptSchedule reopened: IsClosed=false, Processed=false
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier    | C_OrderLine_ID.Identifier    | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.Processed | OPT.IsClosed |
      | rs_PO_close_reopen              | order_PO_rs_close_reopen | orderLine_PO_rs_close_reopen | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 10         | warehouseStd              | false         | false        |
