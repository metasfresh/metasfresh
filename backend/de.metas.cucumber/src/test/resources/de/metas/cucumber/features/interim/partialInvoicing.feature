@from:cucumber
Feature: Partial Payment Invoicing

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-09-02T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load product by value:
      | M_Product_ID.Identifier | Value       |
      | grey_box_product        | Graue Kiste |
    And metasfresh contains M_PricingSystems
      | Identifier | Name              | Value             |
      | ps_PO      | ps_interim_020922 | ps_interim_020922 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_interim_020922 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_PO     | pl_PO                     | 2022-03-01 |

    And metasfresh contains C_BPartners without locations:
      | Identifier      | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | vendor_29082022 | Vendor_29082022 | Y            | N              | ps_PO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | C_BPartner_ID.Identifier | GLN           | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | vendor_location_29082022 | vendor_29082022          | 2908202212345 | Y                   | Y                   |
    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                   |
      | harvesting_calendar      | Kalender - Verrechnung |
    And metasfresh contains C_Interim_Invoice_Settings
      | C_Interim_Invoice_Settings_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | M_Product_Witholding_ID.Identifier |
      | interim_settings                         | harvesting_calendar                 | grey_box_product                   |
    And metasfresh contains C_Flatrate_Conditions:
      | C_Flatrate_Conditions_ID.Identifier | Name                    | Type_Conditions | OPT.InvoiceRule | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.C_Interim_Invoice_Settings_ID.Identifier |
      | interim_invoice_payment             | Interim Invoice Payment | InterimInvoice  | I               | ps_PO                             | Co                       | interim_settings                             |

  Scenario: Happy flow

  _Given x1 C_Interim_Invoice_Settings with the withholding product set to be 'Graue Kiste' (interim_settings)
  _And x1 C_Flatrate_Conditions configured with 'interim_settings'
  _And x1 C_Order with with IsSOTrx='N' (purchase order)
  _And x1 C_OrderLine ordering x100 products
  __________________________________________
  _When the purchase order is completed
  _Then x1 C_Invoice_Candidate is created with QtyOrdered=100 (invoice_candidate)
  _When x30 CUs are received
  _And an interim invoice contract created for C_OrderLine
  _Then x1 C_InterimInvoice_FlatrateTerm is created with QtyDelivered=30 (IIFT1) having x1 C_InterimInvoice_FlatrateTerm_Line (IIFT_L1)
  _And x2 C_Invoice_Candidate are generated -> one interim (I_IC1) with QtyDelivered=QtyToInvoice=30 and one withholding (W_IC1) with QtyDelivered=QtyToInvoice=-30
  __________________________________________
  _When 'I_IC1' is processed
  _Then x1 C_Invoice (I_I1) and x1 C_InvoiceLine (I_IL1) are generated
  _And 'I_IC1' has QtyInvoiced=30 and QtyToInvoice=0
  _And 'IIFT1' has QtyInvoiced=30
  _And 'I_I1' and 'I_IL1' are associated to 'IIFT_L1'
  __________________________________________
  _When x20 CUs are received
  _Then x1 C_InterimInvoice_FlatrateTerm is created with QtyDelivered=20 (IIFT2) having x1 C_InterimInvoice_FlatrateTerm_Line (IIFT_L2)
  _And x2 C_Invoice_Candidate are generated -> one interim (I_IC2) with QtyDelivered=QtyToInvoice=20 and one withholding (W_IC2) with QtyDelivered=QtyToInvoice=-20
  __________________________________________
  _When 'I_IC2' is processed
  _Then x1 C_Invoice (I_I2) and x1 C_InvoiceLine (I_IL2) are generated
  _And 'I_IC2' has QtyInvoiced=20 and QtyToInvoice=0
  _And 'IIFT2' has QtyInvoiced=20
  _And 'I_I2' and 'I_IL2' are associated to 'IIFT_L2'
  __________________________________________
  _When 'invoice_candidate', 'W_IC1' and 'W_IC2' are processed
  _Then 'W_IC1' has QtyInvoiced=-30 and QtyToInvoice=0
  _And 'W_IC2' has QtyInvoiced=-20 and QtyToInvoice=0
  _And 'invoice_candidate' has QtyInvoiced=50 and QtyToInvoice=0

    And metasfresh contains M_Products:
      | Identifier                | Value                     | Name                      |
      | product_PO_happy_29082022 | product_PO_happy_29082022 | product_PO_happy_29082022 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier   | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | product_PO_happy_29082022 | 1.0      | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order_PO_29082022 | N       | vendor_29082022          | 2022-09-02  | po_ref_29082022 | POO             | ps_PO                             |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier   | QtyEntered |
      | ol_PO_29082022 | order_PO_29082022     | product_PO_happy_29082022 | 100        |

    When the order identified by order_PO_29082022 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier   | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_29082022     | order_PO_29082022     | ol_PO_29082022            | vendor_29082022          | vendor_location_29082022          | product_PO_happy_29082022 | 100        | warehouseStd              |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 0            | 100            | 0                | product_PO_happy_29082022   |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_29082022                 | hu_29082022        | receiptSchedule_PO_29082022     | N               | 1     | N               | 1     | N               | 30    | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_29082022        | receiptSchedule_PO_29082022     | inOut_PO_29082022_1   |

    And create interim invoice contract for C_OrderLine
      | C_Flatrate_Conditions_ID.Identifier | C_OrderLine_ID.Identifier | DateFrom   | DateTo     |
      | interim_invoice_payment             | ol_PO_29082022            | 2022-08-01 | 2022-09-28 |

    And retrieve C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier   |
      | contract_interim_PO_29082022  | interim_invoice_payment             | product_PO_happy_29082022 |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier     | OPT.IsInterimInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 30           |                           |                              |                      |
      | invoiceCand_PO_29082022_interim_1 | null                      | 30           | C_Flatrate_Term           | contract_interim_PO_29082022 | Y                    |
      | invoiceCand_PO_29082022_box_1     | null                      | -30          | C_Flatrate_Term           | contract_interim_PO_29082022 | null                 |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 30           | 100            | 30               | product_PO_happy_29082022   |
      | invoiceCand_PO_29082022_interim_1 | null                          | 30           | 30             | 30               | product_PO_happy_29082022   |
      | invoiceCand_PO_29082022_box_1     | null                          | -30          | -30            | -30              | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_1             | invoiceCand_PO_29082022_interim_1         | invoiceCand_PO_29082022_box_1                 | 30                | 0           |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_PO_29082022_interim_1 |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_PO_29082022_interim_1 | invoice_PO_29082022_1   |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus |
      | invoice_PO_29082022_1   | vendor_29082022          | vendor_location_29082022          | 30 Tage netto | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier   | qtyinvoiced | processed |
      | invoice_line_PO_29082022_1  | invoice_PO_29082022_1   | product_PO_happy_29082022 | 30          | true      |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_1             | invoiceCand_PO_29082022_interim_1         | invoiceCand_PO_29082022_box_1                 | 30                | 30          |
    And validate C_InterimInvoice_FlatrateTerm_Line:
      | C_InterimInvoice_FlatrateTerm_Line_ID.Identifier | C_InterimInvoice_FlatrateTerm_ID.Identifier | M_InOut_ID.Identifier | C_InvoiceLine_ID.Identifier |
      | interim_invoice_term_line_29082022_1             | interim_invoice_term_29082022_1             | inOut_PO_29082022_1   | invoice_line_PO_29082022_1  |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_29082022                 | hu_29082022        | receiptSchedule_PO_29082022     | N               | 1     | N               | 1     | N               | 20    | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_29082022        | receiptSchedule_PO_29082022     | inOut_PO_29082022_2   |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier     | OPT.IsInterimInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 50           |                           |                              |                      |
      | invoiceCand_PO_29082022_interim_2 | null                      | 20           | C_Flatrate_Term           | contract_interim_PO_29082022 | Y                    |
      | invoiceCand_PO_29082022_box_2     | null                      | -20          | C_Flatrate_Term           | contract_interim_PO_29082022 | null                 |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 50           | 100            | 50               | product_PO_happy_29082022   |
      | invoiceCand_PO_29082022_interim_2 | null                          | 20           | 20             | 20               | product_PO_happy_29082022   |
      | invoiceCand_PO_29082022_box_2     | null                          | -20          | -20            | -20              | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_2             | invoiceCand_PO_29082022_interim_2         | invoiceCand_PO_29082022_box_2                 | 20                | 0           |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_PO_29082022_interim_2 |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_PO_29082022_interim_2 | invoice_PO_29082022_2   |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus |
      | invoice_PO_29082022_2   | vendor_29082022          | vendor_location_29082022          | 30 Tage netto | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier   | qtyinvoiced | processed |
      | invoice_line_PO_29082022_2  | invoice_PO_29082022_2   | product_PO_happy_29082022 | 20          | true      |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_2             | invoiceCand_PO_29082022_interim_2         | invoiceCand_PO_29082022_box_2                 | 20                | 20          |
    And validate C_InterimInvoice_FlatrateTerm_Line:
      | C_InterimInvoice_FlatrateTerm_Line_ID.Identifier | C_InterimInvoice_FlatrateTerm_ID.Identifier | M_InOut_ID.Identifier | C_InvoiceLine_ID.Identifier |
      | interim_invoice_term_line_29082022_3             | interim_invoice_term_29082022_2             | inOut_PO_29082022_2   | invoice_line_PO_29082022_2  |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.PriceEntered_Override |
      | invoiceCand_PO_29082022           | 1.01                      |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_PO_29082022_box_1     |
      | invoiceCand_PO_29082022_box_2     |
      | invoiceCand_PO_29082022           |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Processed |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 0            | 100            | 50               | 50              | product_PO_happy_29082022   | Y             |
      | invoiceCand_PO_29082022_interim_1 | null                          | 0            | 30             | 30               | 30              | product_PO_happy_29082022   | Y             |
      | invoiceCand_PO_29082022_box_1     | null                          | 0            | -30            | -30              | -30             | grey_box_product            | Y             |
      | invoiceCand_PO_29082022_interim_2 | null                          | 0            | 20             | 20               | 20              | product_PO_happy_29082022   | Y             |
      | invoiceCand_PO_29082022_box_2     | null                          | 0            | -20            | -20              | -20             | grey_box_product            | Y             |

  Scenario: Order completed, interim invoice contract created, material receipt created, no interim invoice created, material receipt reversed

  _Given x1 C_Interim_Invoice_Settings with the withholding product set to be 'Graue Kiste' (interim_settings)
  _And x1 C_Flatrate_Conditions configured with 'interim_settings'
  _And x1 C_Order with with IsSOTrx='N' (purchase order)
  _And x1 C_OrderLine ordering x100 products
  __________________________________________
  _When the purchase order is completed
  _Then x1 C_Invoice_Candidate is created with QtyOrdered=100 (invoice_candidate)
  _When x30 CUs are received generating a material receipt (material_receipt)
  _And an interim invoice contract created for C_OrderLine
  _Then x1 C_InterimInvoice_FlatrateTerm is created with QtyDelivered=30 (IIFT1) having x1 C_InterimInvoice_FlatrateTerm_Line (IIFT_L1)
  _And x2 C_Invoice_Candidate are generated -> one interim (I_IC1) with QtyDelivered=QtyToInvoice=30 and one withholding (W_IC1) with QtyDelivered=QtyToInvoice=-30
  __________________________________________
  _When 'material_receipt' is reversed
  _Then 'I_IC1' has QtyDelivered=0, QtyToInvoice=0
  _And 'W_IC1' has QtyDelivered=0, QtyToInvoice=0
  _And 'invoice_candidate' has QtyDelivered=0, QtyToInvoice=0

    And metasfresh contains M_Products:
      | Identifier            | Value                 | Name                  |
      | product_PO_1_29082022 | product_PO_1_29082022 | product_PO_1_29082022 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | product_PO_1_29082022   | 1.0      | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order_PO_29082022 | N       | vendor_29082022          | 2022-09-02  | po_ref_29082022 | POO             | ps_PO                             |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_PO_29082022 | order_PO_29082022     | product_PO_1_29082022   | 100        |

    When the order identified by order_PO_29082022 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_29082022     | order_PO_29082022     | ol_PO_29082022            | vendor_29082022          | vendor_location_29082022          | product_PO_1_29082022   | 100        | warehouseStd              |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 0            | 100            | 0                | product_PO_1_29082022       |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_29082022                 | hu_29082022        | receiptSchedule_PO_29082022     | N               | 1     | N               | 1     | N               | 30    | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_29082022        | receiptSchedule_PO_29082022     | inOut_PO_29082022     |

    And create interim invoice contract for C_OrderLine
      | C_Flatrate_Conditions_ID.Identifier | C_OrderLine_ID.Identifier | DateFrom   | DateTo     |
      | interim_invoice_payment             | ol_PO_29082022            | 2022-08-02 | 2022-09-29 |

    And retrieve C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier |
      | contract_interim_PO_29082022  | interim_invoice_payment             | product_PO_1_29082022   |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier     | OPT.IsInterimInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 30           |                           |                              |                      |
      | invoiceCand_PO_29082022_interim   | null                      | 30           | C_Flatrate_Term           | contract_interim_PO_29082022 | Y                    |
      | invoiceCand_PO_29082022_box       | null                      | -30          | C_Flatrate_Term           | contract_interim_PO_29082022 | null                 |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 30           | 100            | 30               | product_PO_1_29082022       |
      | invoiceCand_PO_29082022_interim   | null                          | 30           | 30             | 30               | product_PO_1_29082022       |
      | invoiceCand_PO_29082022_box       | null                          | -30          | -30            | -30              | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | contract_interim_PO_29082022                | invoiceCand_PO_29082022_interim           | invoiceCand_PO_29082022_box                   | 30                | 0           |

    When the material receipt identified by inOut_PO_29082022 is reversed

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 0            | 100            | 0                | product_PO_1_29082022       |
      | invoiceCand_PO_29082022_interim   | null                          | 0            | 0              | 0                | product_PO_1_29082022       |
      | invoiceCand_PO_29082022_box       | null                          | 0            | 0              | 0                | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | contract_interim_PO_29082022                | invoiceCand_PO_29082022_interim           | invoiceCand_PO_29082022_box                   | 0                 | 0           |

  Scenario: Order completed, interim invoice contract created, material receipt created, interim invoice created, material receipt created, interim invoice reversed

  _Given x1 C_Interim_Invoice_Settings with thw withholding product set to be 'Graue Kiste' (interim_settings)
  _And x1 C_Flatrate_Conditions configured with 'interim_settings'
  _And x1 C_Order with with IsSOTrx='N' (purchase order)
  _And x1 C_OrderLine ordering x100 products
  __________________________________________
  _When the purchase order is completed
  _Then x1 C_Invoice_Candidate is created with QtyOrdered=100 (invoice_candidate)
  _When x30 CUs are received generating a material receipt (material_receipt)
  _And an interim invoice contract created for C_OrderLine
  _Then x1 C_InterimInvoice_FlatrateTerm is created with QtyDelivered=30 (IIFT1) having x1 C_InterimInvoice_FlatrateTerm_Line (IIFT_L1)
  _And x2 C_Invoice_Candidate are generated -> one interim (I_IC1) with QtyDelivered=QtyToInvoice=30 and one withholding (W_IC1) with QtyDelivered=QtyToInvoice=-30
  __________________________________________
  _When 'I_IC1' is processed
  _Then x1 C_Invoice (I_I1) and x1 C_InvoiceLine (I_IL1) are generated
  _And 'I_IC1' has QtyInvoiced=30 and QtyToInvoice=0
  _And 'IIFT1' has QtyInvoiced=30
  _And 'I_I1' and 'I_IL1' are associated to 'IIFT_L1'
  __________________________________________
  _When x20 CUs are received
  _Then x1 C_InterimInvoice_FlatrateTerm is created with QtyDelivered=20 (IIFT2) having x1 C_InterimInvoice_FlatrateTerm_Line (IIFT_L2)
  _And x2 C_Invoice_Candidate are generated -> one interim (I_IC2) with QtyDelivered=QtyToInvoice=20 and one withholding (W_IC2) with QtyDelivered=QtyToInvoice=-20
  __________________________________________
  _When 'I_I1' is reversed
  _Then 'I_IC1' has QtyInvoiced=0 and QtyToInvoice=30
  _And 'IIFT1' has QtyInvoiced=0

    And metasfresh contains M_Products:
      | Identifier            | Value                 | Name                  |
      | product_PO_2_29082022 | product_PO_2_29082022 | product_PO_2_29082022 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | product_PO_2_29082022   | 1.0      | PCE               | Normal                        |

    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order_PO_29082022 | N       | vendor_29082022          | 2022-06-10  | po_ref_29082022 | POO             | ps_PO                             |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_PO_29082022 | order_PO_29082022     | product_PO_2_29082022   | 100        |

    When the order identified by order_PO_29082022 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_29082022     | order_PO_29082022     | ol_PO_29082022            | vendor_29082022          | vendor_location_29082022          | product_PO_2_29082022   | 100        | warehouseStd              |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 0            | 100            | 0                | product_PO_2_29082022       |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_29082022                 | hu_29082022        | receiptSchedule_PO_29082022     | N               | 1     | N               | 1     | N               | 30    | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_29082022        | receiptSchedule_PO_29082022     | inOut_PO_29082022_1   |

    And create interim invoice contract for C_OrderLine
      | C_Flatrate_Conditions_ID.Identifier | C_OrderLine_ID.Identifier | DateFrom   | DateTo     |
      | interim_invoice_payment             | ol_PO_29082022            | 2022-08-03 | 2022-09-30 |

    And retrieve C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier |
      | contract_interim_PO_29082022  | interim_invoice_payment             | product_PO_2_29082022   |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier     | OPT.IsInterimInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 30           |                           |                              |                      |
      | invoiceCand_PO_29082022_interim_1 | null                      | 30           | C_Flatrate_Term           | contract_interim_PO_29082022 | Y                    |
      | invoiceCand_PO_29082022_box_1     | null                      | -30          | C_Flatrate_Term           | contract_interim_PO_29082022 | null                 |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 30           | 100            | 30               | product_PO_2_29082022       |
      | invoiceCand_PO_29082022_interim_1 | null                          | 30           | 30             | 30               | product_PO_2_29082022       |
      | invoiceCand_PO_29082022_box_1     | null                          | -30          | -30            | -30              | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | contract_interim_PO_29082022                | invoiceCand_PO_29082022_interim_1         | invoiceCand_PO_29082022_box_1                 | 30                | 0           |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_PO_29082022_interim_1 |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_PO_29082022_interim_1 | invoice_PO_29082022     |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus |
      | invoice_PO_29082022     | vendor_29082022          | vendor_location_29082022          | 30 Tage netto | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_line_PO_29082022    | invoice_PO_29082022     | product_PO_2_29082022   | 30          | true      |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Processed |
      | invoiceCand_PO_29082022_interim_1 | null                          | 0            | 30             | 30               | 30              | product_PO_2_29082022       | Y             |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | contract_interim_PO_29082022                | invoiceCand_PO_29082022_interim_1         | invoiceCand_PO_29082022_box_1                 | 30                | 30          |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_29082022                 | hu_29082022        | receiptSchedule_PO_29082022     | N               | 1     | N               | 1     | N               | 20    | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_29082022        | receiptSchedule_PO_29082022     | inOut_PO_29082022_2   |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier     | OPT.IsInterimInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 50           |                           |                              |                      |
      | invoiceCand_PO_29082022_interim_2 | null                      | 20           | C_Flatrate_Term           | contract_interim_PO_29082022 | Y                    |
      | invoiceCand_PO_29082022_box_2     | null                      | -20          | C_Flatrate_Term           | contract_interim_PO_29082022 | null                 |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 50           | 100            | 50               | product_PO_2_29082022       |
      | invoiceCand_PO_29082022_interim_2 | null                          | 20           | 20             | 20               | product_PO_2_29082022       |
      | invoiceCand_PO_29082022_box_2     | null                          | -20          | -20            | -20              | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | contract_interim_PO_29082022                | invoiceCand_PO_29082022_interim_2         | invoiceCand_PO_29082022_box_2                 | 20                | 0           |

    And the invoice identified by invoice_PO_29082022 is reversed

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier     | OPT.IsInterimInvoice |
      | invoiceCand_PO_29082022_interim_1 | null                      | 30           | C_Flatrate_Term           | contract_interim_PO_29082022 | Y                    |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 50           | 100            | 50               | 0               | product_PO_2_29082022       |
      | invoiceCand_PO_29082022_interim_1 | null                          | 30           | 30             | 30               | 0               | product_PO_2_29082022       |
      | invoiceCand_PO_29082022_box_1     | null                          | -30          | -30            | -30              | 0               | grey_box_product            |
      | invoiceCand_PO_29082022_interim_2 | null                          | 20           | 20             | 20               | 0               | product_PO_2_29082022       |
      | invoiceCand_PO_29082022_box_2     | null                          | -20          | -20            | -20              | 0               | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | contract_interim_PO_29082022                | invoiceCand_PO_29082022_interim_1         | invoiceCand_PO_29082022_box_1                 | 30                | 0           |