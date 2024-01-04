@from:cucumber
@ghActions:run_on_executor6
Feature: Product items invoice candidates: receipts

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
      | p_1        | product_03082022-PIC |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_15112022_6 | pricing_system_value_15112022_6 | pricing_system_description_15112022_6 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_15112022_6 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_15112022_6 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_15112022_6 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_2        | 0123456789011 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | huPackingLU           | huPackingLU |
      | huPackingTU           | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 100 | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 100 | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 100 | 2021-01-01 |

  @Id:03082022-PIC.200
  @from:cucumber
  Scenario: Receive 100, complete receipt
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | endvendor_1              | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | endvendor_1              | l_2                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | material_receipt_1    | p_1                     | 100         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_1                              | ol_1                      |

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

  @Id:03082022-PIC.210
  @from:cucumber
  Scenario: Receive 100, complete receipt then reactivate it
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | endvendor_1              | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | endvendor_1              | l_2                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    When validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | material_receipt_1    | p_1                     | 100         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_1                              | ol_1                      |

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

    When the material receipt identified by material_receipt_1 is reactivated

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | false     | IP        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

  @Id:03082022-PIC.220
  @from:cucumber
  Scenario: Receive 100, complete receipt then reactivate it, complete it again
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | endvendor_1              | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | endvendor_1              | l_2                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | receiptLine1              | material_receipt_1    | p_1                     | 100         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_1                              | ol_1                      |

    When the material receipt identified by material_receipt_1 is reactivated

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 0            | 0           | 10    | 0        | EUR          | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate the created material receipt
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | false     | IP        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | receiptLine1                  | 0                |

    When the material receipt identified by material_receipt_1 is completed

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created material receipt
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | receiptLine1                  | 100              |

  @Id:03082022-PIC.230
  @from:cucumber
  Scenario: Receive 100, complete receipt then void it
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | endvendor_1              | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | endvendor_1              | l_2                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | material_receipt_1    | p_1                     | 100         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_1                              | ol_1                      |

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

    When the material receipt identified by material_receipt_1 is reactivated

    When the material receipt identified by material_receipt_1 is voided

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | VO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

  @Id:03082022-PIC.240
  @from:cucumber
  Scenario: Receive 100, complete receipt then revert it
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | endvendor_1              | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | endvendor_1              | l_2                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | material_receipt_1    | p_1                     | 100         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_1                              | ol_1                      |

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

    When the material receipt identified by material_receipt_1 is reversed

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 0                |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | RE        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 0                |

  @Id:03082022-PIC.250
  @from:cucumber
  Scenario: Receive 100, complete receipt then close it
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | endvendor_1              | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | endvendor_1              | l_2                               | p_1                     | 100        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 100         | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    Then validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | shipmentLine_1            | material_receipt_1    | p_1                     | 100         | true      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_1                              | ol_1                      |

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 100        | 100          | 0           | 10    | 0        | EUR          | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

    When the material receipt identified by material_receipt_1 is closed

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 100              |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | CL        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 100              |

  @Id:03082022-PIC.260
  @from:cucumber
  Scenario: Receive 42 with quality discount 5%
    When metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU_260       | huPackingLU_260 |
      | huPackingTU_260       | huPackingTU_260 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                 | HU_UnitType | IsCurrent |
      | packingVersionLU_260          | huPackingLU_260       | packingVersionLU_260 | LU          | Y         |
      | packingVersionTU_260          | huPackingTU_260       | packingVersionTU_260 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU_260             | packingVersionLU_260          | 1   | HU       | huPackingTU_260                  |
      | huPiItemTU_260             | packingVersionTU_260          | 330 | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct_260          | huPiItemTU_260             | p_1                     | 330 | 2021-01-01 |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value          | Name           | OPT.IsIssueWarehouse |
      | issueWarehouse            | issueWarehouse | issueWarehouse | true                 |
    And metasfresh contains M_Locator:
      | M_Locator_ID.Identifier | Value        | M_Warehouse_ID.Identifier |
      | issueLocator            | issueLocator | issueWarehouse            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered |
      | o_1        | false   | endvendor_1              | po_ref_mock     | POO             | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 330        |

    When the order identified by o_1 is completed

    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | endvendor_1              | l_2                               | p_1                     | 330        | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 42          | huItemPurchaseProduct_260          | huPackingLU_260              |
    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value                  |
      | attr_qty_discount_percent | QualityDiscountPercent |
    And update M_HU_Attribute recursive:
      | M_HU_ID.Identifier | M_Attribute_ID.Identifier | OPT.ValueNumber |
      | processedTopHU     | attr_qty_discount_percent | 5               |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed | OPT.C_OrderLine_ID.Identifier | OPT.QualityDiscountPercent |
      | shipmentLine_1            | material_receipt_1    | p_1                     | 40          | true      | ol_1                          | 0                          |
      | shipmentLine_2            | material_receipt_1    | p_1                     | 2           | true      | ol_1                          | 5                          |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_1                              | ol_1                      |

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | ol_1                      | o_1                   | 2021-04-17  | p_1                     | 330        | 42           | 0           | 10    | 0        | EUR          | true      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyDelivered |
      | ic_1                              | 42               |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | material_receipt_1    | endvendor_1              | l_2                               | 2021-04-17  | true      | CO        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | OPT.C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered |
      | iciol_1                                    | ic_1                                  | shipmentLine_1                | 40               |
      | iciol_2                                    | ic_1                                  | shipmentLine_2                | 2                |