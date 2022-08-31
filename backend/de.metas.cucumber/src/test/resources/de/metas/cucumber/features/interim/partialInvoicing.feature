@partialPayment
@from:cucumber
Feature: Partial Payment Invoicing

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  Scenario: Happy flow
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier          | Value               | Name                |
      | product_PO_29082022 | product_PO_29082022 | product_PO_29082022 |
      | grey_box_product    | Graue Kiste         | Graue Kiste         |
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
      | pp_PO      | plv_PO                            | product_PO_29082022     | 1.0      | PCE               | Normal                        |

    And metasfresh contains C_BPartners without locations:
      | Identifier      | Name            | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | vendor_29082022 | Vendor_29082022 | Y            | N              | ps_PO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | C_BPartner_ID.Identifier | GLN          | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | vendor_location_29082022 | vendor_29082022          | gln-29082022 | Y                   | Y                   |
    And load C_Calendar from metasfresh:
      | C_Calendar_ID.Identifier | Name                   |
      | harvesting_calendar      | Kalender - Verrechnung |
    And metasfresh contains C_Interim_Invoice_Settings
      | C_Interim_Invoice_Settings_ID.Identifier | C_Harvesting_Calendar_ID.Identifier | M_Product_Witholding_ID.Identifier |
      | interim_settings                         | harvesting_calendar                 | grey_box_product                   |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier              | Name                    | Type_Conditions | OPT.C_Flatrate_Transition_ID.Name | OPT.InvoiceRule | OPT.M_PricingSystem_ID.Identifier | OPT.OnFlatrateTermExtend | OPT.C_Interim_Invoice_Settings_ID.Identifier |
      | interim_invoice_payment | Interim Invoice Payment | InterimInvoice  | 1 Jahr, autom. verlängern         | I               | ps_PO                             | Co                       | interim_settings                             |

    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order_PO_29082022 | N       | vendor_29082022          | 2022-06-10  | po_ref_29082022 | POO             | ps_PO                             |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_PO_29082022 | order_PO_29082022     | product_PO_29082022     | 10000      |

    When the order identified by order_PO_29082022 is completed

    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO_29082022     | order_PO_29082022     | ol_PO_29082022            | vendor_29082022          | vendor_location_29082022          | product_PO_29082022     | 10000      | warehouseStd              |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 0            | 10000          | 0                | product_PO_29082022         |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_29082022                 | hu_29082022        | receiptSchedule_PO_29082022     | N               | 1     | N               | 1     | N               | 950   | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_29082022        | receiptSchedule_PO_29082022     | inOut_PO_29082022_1   |

    And create interim invoice contract for C_OrderLine
      | C_Flatrate_Conditions_ID.Identifier | C_OrderLine_ID.Identifier | DateFrom   | DateTo     |
      | interim_invoice_payment             | ol_PO_29082022            | 2022-08-01 | 2022-08-31 |

    And retrieve C_Flatrate_Term:
      | C_Flatrate_Term_ID.Identifier | C_Flatrate_Conditions_ID.Identifier | M_Product_ID.Identifier |
      | contract_interim_PO_29082022  | interim_invoice_payment             | product_PO_29082022     |

    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier     | OPT.IsInterimInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 950          |                           |                              |                      |
      | invoiceCand_PO_29082022_interim_1 | null                      | 950          | C_Flatrate_Term           | contract_interim_PO_29082022 | Y                    |
      | invoiceCand_PO_29082022_box_1     | null                      | -950         | C_Flatrate_Term           | contract_interim_PO_29082022 | null                 |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 950          | 10000          | 950              | product_PO_29082022         |
      | invoiceCand_PO_29082022_interim_1 | null                          | 950          | 950            | 950              | product_PO_29082022         |
      | invoiceCand_PO_29082022_box_1     | null                          | -950         | -950           | -950             | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_1             | invoiceCand_PO_29082022_interim_1         | invoiceCand_PO_29082022_box_1                 | 950               | 0           |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_29082022                 | hu_29082022        | receiptSchedule_PO_29082022     | N               | 1     | N               | 1     | N               | 1050  | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_29082022        | receiptSchedule_PO_29082022     | inOut_PO_29082022_2   |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 2000         | 10000          | 2000             | product_PO_29082022         |
      | invoiceCand_PO_29082022_interim_1 | null                          | 2000         | 2000           | 2000             | product_PO_29082022         |
      | invoiceCand_PO_29082022_box_1     | null                          | -2000        | -2000          | -2000            | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_1             | invoiceCand_PO_29082022_interim_1         | invoiceCand_PO_29082022_box_1                 | 2000              | 0           |

    When process invoice candidates and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_PO_29082022_interim_1 |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_PO_29082022_interim_1 | invoice_PO_29082022_1   |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus |
      | invoice_PO_29082022_1   | vendor_29082022          | vendor_location_29082022          | 30 Tage netto | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_line_PO_29082022_1  | invoice_PO_29082022_1   | product_PO_29082022     | 2000        | true      |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_1             | invoiceCand_PO_29082022_interim_1         | invoiceCand_PO_29082022_box_1                 | 2000              | 2000        |
    And validate C_InterimInvoice_FlatrateTerm_Line:
      | C_InterimInvoice_FlatrateTerm_Line_ID.Identifier | C_InterimInvoice_FlatrateTerm_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier |
      | interim_invoice_term_line_29082022_1             | interim_invoice_term_29082022_1             | inOut_PO_29082022_1   | invoice_PO_29082022_1   | invoice_line_PO_29082022_1  |
      | interim_invoice_term_line_29082022_2             | interim_invoice_term_29082022_1             | inOut_PO_29082022_2   | invoice_PO_29082022_1   | invoice_line_PO_29082022_1  |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig_29082022                 | hu_29082022        | receiptSchedule_PO_29082022     | N               | 1     | N               | 1     | N               | 1350  | 101                                | 1000006                      |

    When create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_29082022        | receiptSchedule_PO_29082022     | inOut_PO_29082022_3   |

    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.AD_Table_ID.TableName | OPT.Record_ID.Identifier     | OPT.IsInterimInvoice |
      | invoiceCand_PO_29082022           | ol_PO_29082022            | 3350         |                           |                              |                      |
      | invoiceCand_PO_29082022_interim_2 | null                      | 1350         | C_Flatrate_Term           | contract_interim_PO_29082022 | Y                    |
      | invoiceCand_PO_29082022_box_2     | null                      | -1350        | C_Flatrate_Term           | contract_interim_PO_29082022 | null                 |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.M_Product_ID.Identifier |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 3350         | 10000          | 3350             | product_PO_29082022         |
      | invoiceCand_PO_29082022_interim_2 | null                          | 1350         | 1350           | 1350             | product_PO_29082022         |
      | invoiceCand_PO_29082022_box_2     | null                          | -1350        | -1350          | -1350            | grey_box_product            |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_2             | invoiceCand_PO_29082022_interim_2         | invoiceCand_PO_29082022_box_2                 | 1350              | 0           |

    When process invoice candidates and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_PO_29082022_interim_2 |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCand_PO_29082022_interim_2 | invoice_PO_29082022_2   |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm   | processed | docStatus |
      | invoice_PO_29082022_2   | vendor_29082022          | vendor_location_29082022          | 30 Tage netto | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoice_line_PO_29082022_2  | invoice_PO_29082022_2   | product_PO_29082022     | 1350        | true      |

    And validate C_InterimInvoice_FlatrateTerm:
      | C_InterimInvoice_FlatrateTerm_ID.Identifier | C_Interim_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_Withholding_ID.Identifier | QtyDeliveredInUOM | QtyInvoiced |
      | interim_invoice_term_29082022_2             | invoiceCand_PO_29082022_interim_2         | invoiceCand_PO_29082022_box_2                 | 1350              | 1350        |
    And validate C_InterimInvoice_FlatrateTerm_Line:
      | C_InterimInvoice_FlatrateTerm_Line_ID.Identifier | C_InterimInvoice_FlatrateTerm_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier |
      | interim_invoice_term_line_29082022_3             | interim_invoice_term_29082022_2             | inOut_PO_29082022_3   | invoice_PO_29082022_2   | invoice_line_PO_29082022_2  |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.PriceEntered_Override |
      | invoiceCand_PO_29082022           | 1.01                      |

    And process invoice candidates batch and wait 30s for each C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_PO_29082022           |
      | invoiceCand_PO_29082022_box_1     |
      | invoiceCand_PO_29082022_box_2     |

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.QtyInvoiced | OPT.M_Product_ID.Identifier | OPT.Processed |
      | invoiceCand_PO_29082022           | ol_PO_29082022                | 0            | 3350           | 3350             | 3350            | product_PO_29082022         | Y             |
      | invoiceCand_PO_29082022_interim_1 | null                          | 0            | 2000           | 2000             | 2000            | product_PO_29082022         | Y             |
      | invoiceCand_PO_29082022_box_1     | null                          | 0            | -2000          | -2000            | -2000           | grey_box_product            | Y             |
      | invoiceCand_PO_29082022_interim_2 | null                          | 0            | 1350           | 1350             | 1350            | product_PO_29082022         | Y             |
      | invoiceCand_PO_29082022_box_2     | null                          | 0            | -1350          | -1350            | -1350           | grey_box_product            | Y             |