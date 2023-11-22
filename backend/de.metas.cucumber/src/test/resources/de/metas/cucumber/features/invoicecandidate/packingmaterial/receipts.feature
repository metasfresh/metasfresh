@from:cucumber
@ghActions:run_on_executor1
Feature: Packing material invoice candidates: receipts

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-07-26T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier      | Value                    | Name                     |
      | purchaseProduct | purchaseProduct_04082022 | purchaseProduct_04082022 |
      | packingProduct  | P001512                  | IFCO 6410                |
      | loadingProduct  | P001503                  | EUR-Tauschpalette Holz   |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value               |
      | ps_1       | pricing_system_name | pricing_system_name |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_1                          | DE                        | EUR                 | price_list_name_PO | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_PO     | pl_PO                     | purchaseOrder-PLV | 2022-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_PO                            | purchaseProduct         | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_PO                            | packingProduct          | 1.00     | PCE               | Normal                        |
      | pp_3       | plv_PO                            | loadingProduct          | 0.00     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name                        | M_PricingSystem_ID.Identifier | OPT.IsVendor | OPT.IsCustomer |
      | bpartner_1 | BPartnerName_S0160_Purchase | ps_1                          | Y            | N              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | bpartner_1               | Y                   | Y                   |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name              |
      | huPackingTU           | huPackingTU_S0160 |
      | huPackingLU           | huPackingLU_S0160 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                   | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU_S0160 | TU          | Y         |
      | packingVersionLU              | huPackingLU           | packingVersionLU_S0160 | LU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                   | OPT.M_Product_ID.Identifier |
      | huPackingMaterial                  | IFCO 6410              | packingProduct              |
      | huLoadingMaterial                  | EUR-Tauschpalette Holz | loadingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU                 | packingVersionTU              | 10  | PM       | huPackingMaterial                      |                                  |
      | huPiItemLU                 | packingVersionLU              | 76  | HU       |                                        | huPackingTU                      |
      | huPiItemLU_PM              | packingVersionLU              | 1   | PM       | huLoadingMaterial                      |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct            | huPiItemTU                 | purchaseProduct         | 10  | 2022-07-01 |

  @Id:S0160_100
  @from:cucumber
  Scenario: Order 10 TU, receive what was ordered, receipt automatically completed
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_110
  @from:cucumber
  Scenario: Order 10 TU, receive more than was ordered, receipt automatically completed
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 2xLU x10TU (IFCO) x10CU (200 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 2; C_Invoice_Candidate (TU) qtyDelivered = 20;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 2; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 20;

    Then metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 2     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  | 2                      |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 20          | true      | 20             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 2           | true      | 2              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 2                | 2            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 20               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 2                |

  @Id:S0160_120
  @from:cucumber
  Scenario: Order 10 TU, receive less than was ordered, receipt automatically completed
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x5TU (IFCO) x10CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 5     | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 5           | true      | 5              |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 5                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_130
  @from:cucumber
  Scenario: Order 10 TU, receive without LU, receipt automatically completed
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving x0 LU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for TU packing)
  _And C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    Then metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_130       | huPackingTU_S0160_130 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_130          | huPackingTU_130       | packingVersionTU_S0160_130 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name      | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_130              | IFCO 6410 | packingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemTU_130             | packingVersionTU_130          | 10  | PM       | huPackingMaterial_130                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_130        | huPiItemTU_130             | purchaseProduct         | 10  | 2022-07-01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct_130            | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 0     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | 10                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | loadingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | loadingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |

  @Id:S0160_140
  @from:cucumber
  Scenario: Order 10 TU, receive with no packing item, receipt automatically completed
  _Given packing material (IFCO) x10 CUs
  _And LU packing material (Tauschpalette) set up to carry TUs with no packing material
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU (Tauschpalette) x10TU (no packing) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1;

    When metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingLU_140       | huPackingLU_S0160_140 |
      | noPackingTU_140       | No Packing Item       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionLU_140          | huPackingLU_140       | packingVersionLU_S0160_140 | LU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                   | OPT.M_Product_ID.Identifier |
      | huLoadingMaterial_140              | EUR-Tauschpalette Holz | loadingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_140             | packingVersionLU_140          | 1   | HU       |                                        | noPackingTU_140                  |
      | huPiItemLU_PM_140          | packingVersionLU_140          | 1   | PM       | huLoadingMaterial_140                  |                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | 101                                | huPackingLU_140              |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | packingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | packingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_150
  @from:cucumber
  Scenario: Order 10 TU, receive with other packing instructions
  _Given 1x TU packing material x 10 CUs (IFCO) and 1x TU packing material x 5 CUs (G1)
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (G1) x5CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing  and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    And metasfresh contains M_Products:
      | Identifier         | Value   | Name               |
      | packingProduct_150 | P002734 | Karotten gewaschen |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_150       | huPackingTU_S0160_150 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_150          | huPackingTU_150       | packingVersionTU_S0160_150 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_150              | G1   | packingProduct_150          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU_150             | packingVersionTU_150          | 5   | PM       | huPackingMaterial_150                  |                                  |
      | huPiItemLU_150             | packingVersionLU              | 1   | HU       |                                        | huPackingTU_150                  |
      | huPiItemLU_PM_150          | packingVersionLU              | 1   | PM       | huLoadingMaterial                      |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_150        | huPiItemTU_150             | purchaseProduct         | 10  | 2022-07-01 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 5           | huPiItemPurchaseProduct_150        | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct_150      | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_160
  @from:cucumber
  Scenario: Order 1000 Tus, so it doesn’t fit 1 LU, receive what was ordered, receipt automatically completed
  _Given TU packing material (IFCO) x 10 CUs
  _And LU packing material (Tauschpalette) x 76 TUs
  _And order 1000 x TU (IFCO) (10000 CUs)
  _When receiving 14xLU x76TU x10CU (10640 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 14; C_Invoice_Candidate (TU) qtyDelivered = 1064;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 14; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 1064;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 10000      | huPiItemPurchaseProduct                | 1000             |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 1000       | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 10000      | warehouseStd              | 1000             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_HU_PI_Item_Product_ID.TU.Identifier | M_HU_PI_ID.LU.Identifier |
      | huLuTuConfig                          | receiptSchedule_PO              | huPiItemPurchaseProduct               | huPackingLU              |
    Then validate M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | QtyLU | QtyTU | QtyCUsPerTU |
      | huLuTuConfig                          | 14    | 76    | 10          |
    And receive HUs with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | OPT.HUList.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | receiptSchedule_PO              | processedHUs          | 14                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 1064        | true      | 1064           |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 14          | true      | 14             |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 1064             | 1064         | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 14               | 14           | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 1064             |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 14               |

  @Id:S0160_170
  @from:cucumber
  Scenario: Reactivate receipt similar with case 100
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |


    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_180
  @from:cucumber
  Scenario: Reactivate receipt similar with case 110
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 2xLU x10TU (IFCO) x10CU (200 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 2; C_Invoice_Candidate (TU) qtyDelivered = 20;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 2; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 20;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    Then metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 2     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  | 2                      |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 20          | true      | 20             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 2           | true      | 2              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 2                | 2            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_190
  @from:cucumber
  Scenario: Reactivate receipt similar with case 120
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x5TU (IFCO) x10CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 5     | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 5           | true      | 5              |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_200
  @from:cucumber
  Scenario: Reactivate receipt similar with case 130
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving x0 LU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for TU packing)
  _And C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    Then metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_130       | huPackingTU_S0160_130 |
      | huPackingLU_130       | huPackingLU_S0160_130 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_130          | huPackingTU_130       | packingVersionTU_S0160_130 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name      | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_130              | IFCO 6410 | packingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemTU_130             | packingVersionTU_130          | 10  | PM       | huPackingMaterial_130                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_130        | huPiItemTU_130             | purchaseProduct         | 10  | 2022-07-01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct_130            | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 0     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | 10                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | loadingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | loadingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |

  @Id:S0160_210
  @from:cucumber
  Scenario: Reactivate receipt similar with case 140
  _Given packing material (IFCO) x10 CUs
  _And LU packing material (Tauschpalette) set up to carry TUs with no packing material
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU (Tauschpalette) x10TU (no packing) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0;

    When metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingLU_140       | huPackingLU_S0160_140 |
      | noPackingTU_140       | No Packing Item       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionLU_140          | huPackingLU_140       | packingVersionLU_S0160_140 | LU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                   | OPT.M_Product_ID.Identifier |
      | huLoadingMaterial_140              | EUR-Tauschpalette Holz | loadingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_140             | packingVersionLU_140          | 1   | HU       |                                        | noPackingTU_140                  |
      | huPiItemLU_PM_140          | packingVersionLU_140          | 1   | PM       | huLoadingMaterial_140                  |                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | 101                                | huPackingLU_140              |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | packingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | packingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_220
  @from:cucumber
  Scenario: Reactivate receipt similar with case 150
  _Given 1x TU packing material x 10 CUs (IFCO) and 1x TU packing material x 5 CUs (G1)
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (G1) x5CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    And metasfresh contains M_Products:
      | Identifier         | Value   | Name               |
      | packingProduct_150 | P002734 | Karotten gewaschen |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_150       | huPackingTU_S0160_150 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_150          | huPackingTU_150       | packingVersionTU_S0160_150 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_150              | G1   | packingProduct_150          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU_150             | packingVersionTU_150          | 10  | PM       | huPackingMaterial_150                  |                                  |
      | huPiItemLU_150             | packingVersionLU              | 1   | HU       |                                        | huPackingTU_150                  |
      | huPiItemLU_PM_150          | packingVersionLU              | 1   | PM       | huLoadingMaterial                      |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_150        | huPiItemTU_150             | purchaseProduct         | 10  | 2022-07-01 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 5           | huPiItemPurchaseProduct_150        | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct_150      | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @flaky
  @Id:S0160_230
  @from:cucumber
  Scenario: Reactivate receipt similar with case 160
  _Given TU packing material (IFCO) x 10 CUs
  _And LU packing material (Tauschpalette) x 76 TUs
  _And order 1000 x TU (IFCO) (10000 CUs)
  _When receiving 14xLU x76TU x10CU (10640 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 14; C_Invoice_Candidate (TU) qtyDelivered = 1064;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 14; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 1064;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 10000      | huPiItemPurchaseProduct                | 1000             |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 1000       | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 10000      | warehouseStd              | 1000             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_HU_PI_Item_Product_ID.TU.Identifier | M_HU_PI_ID.LU.Identifier |
      | huLuTuConfig                          | receiptSchedule_PO              | huPiItemPurchaseProduct               | huPackingLU              |
    Then validate M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | QtyLU | QtyTU | QtyCUsPerTU |
      | huLuTuConfig                          | 14    | 76    | 10          |
    And receive HUs with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | OPT.HUList.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | receiptSchedule_PO              | processedHUs          | 14                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 1064        | true      | 1064           |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 14          | true      | 14             |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 1064             | 1064         | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 14               | 14           | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_240
  @from:cucumber
  Scenario: Complete receipt similar with case 170
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;
  _Then complete M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_241
  @from:cucumber
  Scenario: Reactivate receipt similar with case 100, increase qtys, complete again
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;
  _And increase M_InOutLine (LU) qtyDelivered = 20; M_InOutLine (TU) qtyDelivered = 20;
  _Then complete M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 20; C_Invoice_Candidate (TU) qtyDelivered = 20;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 20; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 20;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | receiptLine_1             | 20         | 20          |
      | receiptLine_2             | 20         | 20          |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 20               | 20           | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 20               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 20               |

  @Id:S0160_242
  @from:cucumber
  Scenario: Reactivate receipt similar with case 100, decrease qtys, complete again
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;
  _And decrease M_InOutLine (LU) qtyDelivered = 5; M_InOutLine (TU) qtyDelivered = 5;
  _Then complete M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 5; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 5; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And update M_InOutLine:
      | M_InOutLine_ID.Identifier | QtyEntered | MovementQty |
      | receiptLine_1             | 5          | 5           |
      | receiptLine_2             | 5          | 5           |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 5                | 5            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 5                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 5                |

  @Id:S0160_250
  @from:cucumber
  Scenario: Complete receipt similar with case 180
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 2xLU x10TU (IFCO) x10CU (200 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 2; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 2; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;
  _Then complete M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 2; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 2; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    Then metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 2     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  | 2                      |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 20          | true      | 20             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 2           | true      | 2              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 2                | 2            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 2                | 2            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 20               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 2                |

  @Id:S0160_260
  @from:cucumber
  Scenario: Complete receipt similar with case 190
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x5TU (IFCO) x10CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;
  _Then complete M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 5     | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 5           | true      | 5              |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 5                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @flaky
  @Id:S0160_270
  @from:cucumber
  Scenario: Complete receipt similar with case 200
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving x0 LU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for TU packing)
  _And C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;
  _Then complete M_InOut
  _And C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    Then metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_130       | huPackingTU_S0160_130 |
      | huPackingLU_130       | huPackingLU_S0160_130 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_130          | huPackingTU_130       | packingVersionTU_S0160_130 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name      | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_130              | IFCO 6410 | packingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemTU_130             | packingVersionTU_130          | 10  | PM       | huPackingMaterial_130                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_130        | huPiItemTU_130             | purchaseProduct         | 10  | 2022-07-01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct_130            | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 0     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | 10                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | loadingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | loadingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |

  @Id:S0160_280
  @from:cucumber
  Scenario: Complete receipt similar with case 210
  _Given TU packing material (IFCO) x10 CUs
  _And LU packing material (Tauschpalette) set up to carry TUs with no packing material
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU (Tauschpalette) x10TU (no packing) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0;
  _Then complete M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 1;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1;

    When metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingLU_140       | huPackingLU_S0160_140 |
      | noPackingTU_140       | No Packing Item       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionLU_140          | huPackingLU_140       | packingVersionLU_S0160_140 | LU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                   | OPT.M_Product_ID.Identifier |
      | huLoadingMaterial_140              | EUR-Tauschpalette Holz | loadingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_140             | packingVersionLU_140          | 1   | HU       |                                        | noPackingTU_140                  |
      | huPiItemLU_PM_140          | packingVersionLU_140          | 1   | PM       | huLoadingMaterial_140                  |                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | 101                                | huPackingLU_140              |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | packingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | packingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_290
  @from:cucumber
  Scenario: Complete receipt similar with case 220
  _Given 1x TU packing material x 10 CUs (IFCO) and 1x TU packing material x 5 CUs (G1)
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (G1) x5CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;
  _Then complete M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    And metasfresh contains M_Products:
      | Identifier         | Value   | Name               |
      | packingProduct_150 | P002734 | Karotten gewaschen |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_150       | huPackingTU_S0160_150 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_150          | huPackingTU_150       | packingVersionTU_S0160_150 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_150              | G1   | packingProduct_150          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU_150             | packingVersionTU_150          | 10  | PM       | huPackingMaterial_150                  |                                  |
      | huPiItemLU_150             | packingVersionLU              | 1   | HU       |                                        | huPackingTU_150                  |
      | huPiItemLU_PM_150          | packingVersionLU              | 1   | PM       | huLoadingMaterial                      |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_150        | huPiItemTU_150             | purchaseProduct         | 10  | 2022-07-01 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 5           | huPiItemPurchaseProduct_150        | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct_150      | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_300
  @from:cucumber
  Scenario: Complete receipt similar with case 230
  _Given TU packing material (IFCO) x 10 CUs
  _And LU packing material (Tauschpalette) x 76 TUs
  _And order 1000 x TU (IFCO) (10000 CUs)
  _When receiving 14xLU x76TU x10CU (10640 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 14; C_Invoice_Candidate (TU) qtyDelivered = 1064;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 14; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 1064;
  _Then reactivate M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;
  _Then complete M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 14; C_Invoice_Candidate (TU) qtyDelivered = 1064;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 14; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 1064;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 10000      | huPiItemPurchaseProduct                | 1000             |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 1000       | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 10000      | warehouseStd              | 1000             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_HU_PI_Item_Product_ID.TU.Identifier | M_HU_PI_ID.LU.Identifier |
      | huLuTuConfig                          | receiptSchedule_PO              | huPiItemPurchaseProduct               | huPackingLU              |
    Then validate M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | QtyLU | QtyTU | QtyCUsPerTU |
      | huLuTuConfig                          | 14    | 76    | 10          |
    And receive HUs with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | OPT.HUList.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | receiptSchedule_PO              | processedHUs          | 14                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 1064        | true      | 1064           |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 14          | true      | 14             |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 1064             | 1064         | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 14               | 14           | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | IP        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

    When the material receipt identified by material_receipt_1 is completed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 1064             | 1064         | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 14               | 14           | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 1064             |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 14               |

  @Id:S0160_310
  @from:cucumber
  Scenario: Close receipt similar with case 240
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then close M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CL        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_320
  @from:cucumber
  Scenario: Close receipt similar with case 250
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 2xLU x10TU (IFCO) x10CU (200 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 2; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 2; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then close M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 2; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 2; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    Then metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 2     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  | 2                      |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 20          | true      | 20             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 2           | true      | 2              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 2                | 2            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CL        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 2                | 2            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 20               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 2                |

  @Id:S0160_330
  @from:cucumber
  Scenario: Close receipt similar with case 260
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x5TU (IFCO) x10CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;
  _Then close M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 5     | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 5           | true      | 5              |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CL        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 5                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_340
  @from:cucumber
  Scenario: Close receipt similar with case 270
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving x0 LU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for TU packing)
  _And C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then close M_InOut
  _And C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    Then metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_130       | huPackingTU_S0160_130 |
      | huPackingLU_130       | huPackingLU_S0160_130 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_130          | huPackingTU_130       | packingVersionTU_S0160_130 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name      | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_130              | IFCO 6410 | packingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemTU_130             | packingVersionTU_130          | 10  | PM       | huPackingMaterial_130                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_130        | huPiItemTU_130             | purchaseProduct         | 10  | 2022-07-01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct_130            | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 0     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | 10                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | loadingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | loadingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CL        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |

  @Id:S0160_350
  @from:cucumber
  Scenario: Close receipt similar with case 280
  _Given TU packing material (IFCO) x10 CUs
  _And LU packing material (Tauschpalette) set up to carry TUs with no packing material
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU (Tauschpalette) x10TU (no packing) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1;
  _Then close M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 1;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1;

    When metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingLU_140       | huPackingLU_S0160_140 |
      | noPackingTU_140       | No Packing Item       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionLU_140          | huPackingLU_140       | packingVersionLU_S0160_140 | LU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                   | OPT.M_Product_ID.Identifier |
      | huLoadingMaterial_140              | EUR-Tauschpalette Holz | loadingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_140             | packingVersionLU_140          | 1   | HU       |                                        | noPackingTU_140                  |
      | huPiItemLU_PM_140          | packingVersionLU_140          | 1   | PM       | huLoadingMaterial_140                  |                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | 101                                | huPackingLU_140              |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | packingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | packingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CL        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_360
  @from:cucumber
  Scenario: Close receipt similar with case 290
  _Given 1x TU packing material x 10 CUs (IFCO) and 1x TU packing material x 5 CUs (G1)
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (G1) x5CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then close M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;

    And metasfresh contains M_Products:
      | Identifier         | Value   | Name               |
      | packingProduct_150 | P002734 | Karotten gewaschen |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_150       | huPackingTU_S0160_150 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_150          | huPackingTU_150       | packingVersionTU_S0160_150 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_150              | G1   | packingProduct_150          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU_150             | packingVersionTU_150          | 10  | PM       | huPackingMaterial_150                  |                                  |
      | huPiItemLU_150             | packingVersionLU              | 1   | HU       |                                        | huPackingTU_150                  |
      | huPiItemLU_PM_150          | packingVersionLU              | 1   | PM       | huLoadingMaterial                      |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_150        | huPiItemTU_150             | purchaseProduct         | 10  | 2022-07-01 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 5           | huPiItemPurchaseProduct_150        | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct_150      | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CL        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 10               |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 1                |

  @Id:S0160_370
  @from:cucumber
  Scenario: Close receipt similar with case 300
  _Given TU packing material (IFCO) x 10 CUs
  _And LU packing material (Tauschpalette) x 76 TUs
  _And order 1000 x TU (IFCO) (10000 CUs)
  _When receiving 14xLU x76TU x10CU (10640 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 14; C_Invoice_Candidate (TU) qtyDelivered = 1064;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 14; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 1064;
  _Then close M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 14; C_Invoice_Candidate (TU) qtyDelivered = 1064;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 14; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 1064;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 10000      | huPiItemPurchaseProduct                | 1000             |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 1000       | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 10000      | warehouseStd              | 1000             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_HU_PI_Item_Product_ID.TU.Identifier | M_HU_PI_ID.LU.Identifier |
      | huLuTuConfig                          | receiptSchedule_PO              | huPiItemPurchaseProduct               | huPackingLU              |
    Then validate M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | QtyLU | QtyTU | QtyCUsPerTU |
      | huLuTuConfig                          | 14    | 76    | 10          |
    And receive HUs with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | OPT.HUList.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | receiptSchedule_PO              | processedHUs          | 14                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 1064        | true      | 1064           |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 14          | true      | 14             |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 1064             | 1064         | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 14               | 14           | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is closed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CL        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 1064             | 1064         | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 14               | 14           | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 1064             |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 14               |

  @Id:S0160_380
  @from:cucumber
  Scenario: Revert receipt similar with case 100
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then revert M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | RE        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_390
  @from:cucumber
  Scenario: Revert receipt similar with case 110
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 2xLU x10TU (IFCO) x10CU (200 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 2; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 2; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then revert M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    Then metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 2     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  | 2                      |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 20          | true      | 20             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 2           | true      | 2              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 2                | 2            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | RE        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_400
  @from:cucumber
  Scenario: Revert receipt similar with case 120
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x5TU (IFCO) x10CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;
  _Then revert M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 5     | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 5           | true      | 5              |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | RE        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_410
  @from:cucumber
  Scenario: Revert receipt similar with case 130
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving x0 LU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for TU packing)
  _And C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then revert M_InOut
  _And C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    Then metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_130       | huPackingTU_S0160_130 |
      | huPackingLU_130       | huPackingLU_S0160_130 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_130          | huPackingTU_130       | packingVersionTU_S0160_130 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name      | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_130              | IFCO 6410 | packingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemTU_130             | packingVersionTU_130          | 10  | PM       | huPackingMaterial_130                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_130        | huPiItemTU_130             | purchaseProduct         | 10  | 2022-07-01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct_130            | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 0     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | 10                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | loadingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | loadingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | RE        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |

  @Id:S0160_420
  @from:cucumber
  Scenario: Revert receipt similar with case 140
  _Given packing material (IFCO) x10 CUs
  _And LU packing material (Tauschpalette) set up to carry TUs with no packing material
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU (Tauschpalette) x10TU (no packing) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1;
  _Then revert M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0;

    When metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingLU_140       | huPackingLU_S0160_140 |
      | noPackingTU_140       | No Packing Item       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionLU_140          | huPackingLU_140       | packingVersionLU_S0160_140 | LU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                   | OPT.M_Product_ID.Identifier |
      | huLoadingMaterial_140              | EUR-Tauschpalette Holz | loadingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_140             | packingVersionLU_140          | 1   | HU       |                                        | noPackingTU_140                  |
      | huPiItemLU_PM_140          | packingVersionLU_140          | 1   | PM       | huLoadingMaterial_140                  |                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | 101                                | huPackingLU_140              |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | packingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | packingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | RE        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_430
  @from:cucumber
  Scenario: Revert receipt similar with case 150
  _Given 1x TU packing material x 10 CUs (IFCO) and 1x TU packing material x 5 CUs (G1)
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (G1) x5CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then revert M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    And metasfresh contains M_Products:
      | Identifier         | Value   | Name               |
      | packingProduct_150 | P002734 | Karotten gewaschen |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_150       | huPackingTU_S0160_150 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_150          | huPackingTU_150       | packingVersionTU_S0160_150 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_150              | G1   | packingProduct_150          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU_150             | packingVersionTU_150          | 10  | PM       | huPackingMaterial_150                  |                                  |
      | huPiItemLU_150             | packingVersionLU              | 1   | HU       |                                        | huPackingTU_150                  |
      | huPiItemLU_PM_150          | packingVersionLU              | 1   | PM       | huLoadingMaterial                      |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_150        | huPiItemTU_150             | purchaseProduct         | 10  | 2022-07-01 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 5           | huPiItemPurchaseProduct_150        | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct_150      | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | RE        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_440
  @from:cucumber
  Scenario: Revert receipt similar with case 160
  _Given TU packing material (IFCO) x 10 CUs
  _And LU packing material (Tauschpalette) x 76 TUs
  _And order 1000 x TU (IFCO) (10000 CUs)
  _When receiving 14xLU x76TU x10CU (10640 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 14; C_Invoice_Candidate (TU) qtyDelivered = 1064;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 14; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 1064;
  _Then revert M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 10000      | huPiItemPurchaseProduct                | 1000             |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 1000       | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 10000      | warehouseStd              | 1000             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_HU_PI_Item_Product_ID.TU.Identifier | M_HU_PI_ID.LU.Identifier |
      | huLuTuConfig                          | receiptSchedule_PO              | huPiItemPurchaseProduct               | huPackingLU              |
    Then validate M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | QtyLU | QtyTU | QtyCUsPerTU |
      | huLuTuConfig                          | 14    | 76    | 10          |
    And receive HUs with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | OPT.HUList.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | receiptSchedule_PO              | processedHUs          | 14                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 1064        | true      | 1064           |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 14          | true      | 14             |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 1064             | 1064         | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 14               | 14           | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reversed

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | RE        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_450
  @from:cucumber
  Scenario: Void receipt similar with case 100
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _Then void M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    And the material receipt identified by material_receipt_1 is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | VO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_460
  @from:cucumber
  Scenario: Void receipt similar with case 110
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 2xLU x10TU (IFCO) x10CU (200 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 2; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 2; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _Then void M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    Then metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 2     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  | 2                      |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 20          | true      | 20             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 2           | true      | 2              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 20               | 20           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 2                | 2            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    And the material receipt identified by material_receipt_1 is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | VO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_470
  @from:cucumber
  Scenario: Void receipt similar with case 120
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x5TU (IFCO) x10CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 5;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 5;
  _Then reactivate M_InOut
  _Then void M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 5     | N               | 10          | huPiItemPurchaseProduct            | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 5           | true      | 5              |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 5                | 5            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    And the material receipt identified by material_receipt_1 is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | VO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_480
  @from:cucumber
  Scenario: Void receipt similar with case 130
  _Given TU packing material (IFCO) x 10 CUs
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving x0 LU x10TU (IFCO) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for TU packing)
  _And C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _Then void M_InOut
  _And C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    Then metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_130       | huPackingTU_S0160_130 |
      | huPackingLU_130       | huPackingLU_S0160_130 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_130          | huPackingTU_130       | packingVersionTU_S0160_130 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name      | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_130              | IFCO 6410 | packingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemTU_130             | packingVersionTU_130          | 10  | PM       | huPackingMaterial_130                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_130        | huPiItemTU_130             | purchaseProduct         | 10  | 2022-07-01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct_130            | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | processedHUs          | receiptSchedule_PO              | N               | 0     | N               | 10    | N               | 10          | huPiItemPurchaseProduct            | 10                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 10          | true      | 10             |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | loadingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | loadingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    And the material receipt identified by material_receipt_1 is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | VO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |

  @Id:S0160_490
  @from:cucumber
  Scenario: Void receipt similar with case 140
  _Given packing material (IFCO) x10 CUs
  _And LU packing material (Tauschpalette) set up to carry TUs with no packing material
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU (Tauschpalette) x10TU (no packing) x10CU (100 total qty)
  _Then we have 2x C_Invoice_Candidate records (one for items and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1;
  _Then reactivate M_InOut
  _Then void M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0;

    When metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingLU_140       | huPackingLU_S0160_140 |
      | noPackingTU_140       | No Packing Item       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionLU_140          | huPackingLU_140       | packingVersionLU_S0160_140 | LU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name                   | OPT.M_Product_ID.Identifier |
      | huLoadingMaterial_140              | EUR-Tauschpalette Holz | loadingProduct              |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_140             | packingVersionLU_140          | 1   | HU       |                                        | noPackingTU_140                  |
      | huPiItemLU_PM_140          | packingVersionLU_140          | 1   | PM       | huLoadingMaterial_140                  |                                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 10          | 101                                | huPackingLU_140              |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |
    And validate the material receipt lines do not exist
      | M_InOut_ID.Identifier | M_Product_ID.Identifier |
      | material_receipt_1    | packingProduct          |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |
    And validate C_Invoice_Candidates does not exist
      | C_Order_ID.Identifier | M_Product_ID.Identifier |
      | o_1                   | packingProduct          |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    And the material receipt identified by material_receipt_1 is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | VO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_500
  @from:cucumber
  Scenario: Void receipt similar with case 150
  _Given 1x TU packing material x 10 CUs (IFCO) and 1x TU packing material x 5 CUs (G1)
  _And order 10 x TU (IFCO) (100 CUs)
  _When receiving 1xLU x10TU (G1) x5CU (50 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 1; C_Invoice_Candidate (TU) qtyDelivered = 10;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 1; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 10;
  _Then reactivate M_InOut
  _Then void M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    And metasfresh contains M_Products:
      | Identifier         | Value   | Name               |
      | packingProduct_150 | P002734 | Karotten gewaschen |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name                  |
      | huPackingTU_150       | huPackingTU_S0160_150 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                       | HU_UnitType | IsCurrent |
      | packingVersionTU_150          | huPackingTU_150       | packingVersionTU_S0160_150 | TU          | Y         |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | Name | OPT.M_Product_ID.Identifier |
      | huPackingMaterial_150              | G1   | packingProduct_150          |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU_150             | packingVersionTU_150          | 10  | PM       | huPackingMaterial_150                  |                                  |
      | huPiItemLU_150             | packingVersionLU              | 1   | HU       |                                        | huPackingTU_150                  |
      | huPiItemLU_PM_150          | packingVersionLU              | 1   | PM       | huLoadingMaterial                      |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemPurchaseProduct_150        | huPiItemTU_150             | purchaseProduct         | 10  | 2022-07-01 |
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 100        | huPiItemPurchaseProduct                | 10               |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 10         | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 100        | warehouseStd              | 10               |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 10    | N               | 5           | huPiItemPurchaseProduct_150        | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct_150      | 10          | true      | 10             |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 1           | true      | 1              |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 10               | 10           | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 1                | 1            | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    And the material receipt identified by material_receipt_1 is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | VO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |

  @Id:S0160_510
  @from:cucumber
  Scenario: Void receipt similar with case 160
  _Given TU packing material (IFCO) x 10 CUs
  _And LU packing material (Tauschpalette) x 76 TUs
  _And order 1000 x TU (IFCO) (10000 CUs)
  _When receiving 14xLU x76TU x10CU (10640 total qty)
  _Then we have 3x C_Invoice_Candidate records (one for items, one for TU packing and one for LU packing)
  _And C_Invoice_Candidate (LU) qtyDelivered = 14; C_Invoice_Candidate (TU) qtyDelivered = 1064;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 14; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 1064;
  _Then reversed M_InOut
  _Then void M_InOut
  _And C_Invoice_Candidate (LU) qtyDelivered = 0; C_Invoice_Candidate (TU) qtyDelivered = 0;
  _And C_InvoiceCandidate_InOutLine (LU) qtyDelivered = 0; C_InvoiceCandidate_InOutLine (TU) qtyDelivered = 0;

    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | bpartner_1               | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier | OPT.QtyEnteredTU |
      | ol_1       | o_1                   | purchaseProduct         | 10000      | huPiItemPurchaseProduct                | 1000             |

    When the order identified by o_1 is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_2                      | o_1                   | 2022-07-26  | packingProduct          | 0            | 1000       | 0           | 1     | 0        | EUR          | true      |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_1               | l_1                               | purchaseProduct         | 10000      | warehouseStd              | 1000             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_HU_PI_Item_Product_ID.TU.Identifier | M_HU_PI_ID.LU.Identifier |
      | huLuTuConfig                          | receiptSchedule_PO              | huPiItemPurchaseProduct               | huPackingLU              |
    Then validate M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | QtyLU | QtyTU | QtyCUsPerTU |
      | huLuTuConfig                          | 14    | 76    | 10          |
    And receive HUs with M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | OPT.HUList.Identifier | OPT.numberHUsGenerated |
      | huLuTuConfig                          | receiptSchedule_PO              | processedHUs          | 14                     |
    And create material receipt
      | OPT.HUList.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedHUs          | receiptSchedule_PO              | material_receipt_1    |

    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.QtyEntered |
      | receiptLine_1             | material_receipt_1    | packingProduct          | 1064        | true      | 1064           |
      | receiptLine_2             | material_receipt_1    | loadingProduct          | 14          | true      | 14             |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 1064             | 1064         | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 14               | 14           | receiptLine_2                 |

    And validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | CO        |

    When the material receipt identified by material_receipt_1 is reactivated

    And the material receipt identified by material_receipt_1 is voided

    Then validate M_In_Out status
      | M_InOut_ID.Identifier | DocStatus |
      | material_receipt_1    | VO        |

    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.M_InOutLine_ID.Identifier |
      | invoiceCand_1                     | o_1                       | null                      | 0                | 0            | receiptLine_1                 |
      | invoiceCand_2                     | o_1                       | null                      | 0                | 0            | receiptLine_2                 |

    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | invoiceCandReceiptLine_1                   | invoiceCand_1                         | receiptLine_1                 | 0                |
      | invoiceCandReceiptLine_2                   | invoiceCand_2                         | receiptLine_2                 | 0                |