@from:cucumber
@ghActions:run_on_executor3
Feature: approved for invoicing

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-09-14T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | Name                |
      | p_SO       | product_SO_15092022 |
      | p_PO       | product_PO_15092022 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                            | Value                            | OPT.Description                        | OPT.IsActive |
      | ps_SO      | pricing_system_name_SO_15092022 | pricing_system_value_SO_15092022 | pricing_system_description_SO_15092022 | true         |
      | ps_PO      | pricing_system_name_PO_15092022 | pricing_system_value_PO_15092022 | pricing_system_description_PO_15092022 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                        | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_SO      | ps_SO                         | DE                        | EUR                 | price_list_name_SO_15092022 | null            | true  | false         | 2              | true         |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | price_list_name_PO_15092022 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name            | ValidFrom  |
      | plv_SO     | pl_SO                     | PLV_SO_15092022 | 2021-04-01 |
      | plv_PO     | pl_PO                     | PLV_PO_15092022 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_SO      | plv_SO                            | p_SO                    | 10.0     | PCE               | Normal                        |
      | pp_PO      | plv_PO                            | p_PO                    | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_SO | Customer_SO_15092022 | N            | Y              | ps_SO                         |
      | bpartner_PO | Vendor_PO_15092022   | Y            | N              | ps_PO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | bpartnerLocation_SO | bpartner_SO              | true         | true         |
      | bpartnerLocation_PO | bpartner_PO              | true         | true         |
    And load AD_Message:
      | Identifier                 | Value                                        |
      | approved_for_invoice_error | Operation_Not_Supported_Approved_For_Invoice |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouse                 | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locator                 | warehouse                 | Hauptlager |

  @from:cucumber
  Scenario: create sales order, complete it, approve invoice candidates for invoicing and then try to reactivate order with error
    Given update C_BPartner:
      | Identifier  | OPT.InvoiceRule |
      | bpartner_SO | I               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | bpartner_SO              | 2022-09-15  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_SO                    | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 10           |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | invoiceCand_1                     | true                     |

    Then the order identified by o_1 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | approved_for_invoice_error   |

  @from:cucumber
  Scenario: create sales order, complete it, approve invoice candidates for invoicing, generate shipment, complete it and then try to reactivate shipment with error
    Given update C_BPartner:
      | Identifier  | OPT.InvoiceRule |
      | bpartner_SO | D               |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | bpartner_SO              | 2021-09-15  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_SO                    | 10         |
    When the order identified by o_1 is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |

    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_ol_1                           | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | inOut_1               |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 10           |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | invoiceCand_1                     | true                     |

    Then the shipment identified by inOut_1 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | approved_for_invoice_error   |

    Then the order identified by o_1 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | approved_for_invoice_error   |

  @from:cucumber
  Scenario: create purchase order, complete it, approve invoice candidates for invoicing and then try to reactivate order with error
    Given update C_BPartner:
      | Identifier  | OPT.PO_InvoiceRule |
      | bpartner_PO | I                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType |
      | o_1        | false   | bpartner_PO              | 2022-09-15  | po_ref_mock     | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_PO                    | 10         |
    When the order identified by o_1 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 10           |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | invoiceCand_1                     | true                     |

    Then the order identified by o_1 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | approved_for_invoice_error   |

  @from:cucumber
  Scenario: create purchase order, complete it, approve invoice candidates for invoicing, generate receipt, complete it and then try to reactivate receipt with error
    Given update C_BPartner:
      | Identifier  | OPT.PO_InvoiceRule |
      | bpartner_PO | D                  |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType |
      | o_1        | false   | bpartner_PO              | 2022-09-15  | po_ref_mock     | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_PO                    | 10         |
    When the order identified by o_1 is completed

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | bpartner_PO              | bpartnerLocation_PO               | p_PO                    | 10         | warehouse                 |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 5           | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | inOut_1               |

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_1                     | ol_1                      | 5            |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | invoiceCand_1                     | true                     |

    Then the material receipt identified by inOut_1 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | approved_for_invoice_error   |

  @from:cucumber
  Scenario: create shipment, complete it, approve invoice candidate for invoicing then try to reverse shipment with error
    Given metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | IsSOTrx | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier | DeliveryRule | DeliveryViaRule | FreightCostRule | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | inOut_1               | true    | bpartner_SO              | bpartnerLocation_SO               | warehouse                 | A            | P               | I               | 2022-09-15   | C-           | 5            | MMS             | MS             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | MovementQty | UomCode | OPT.M_Locator_ID.Identifier | OPT.IsManualPackingMaterial |
      | inOutLine_1               | inOut_1               | p_SO                        | 10         | 10          | PCE     | locator                     | Y                           |

    When the shipment identified by inOut_1 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice |
      | invoiceCand_1                     | inOutLine_1                   | 10               | 10           |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | invoiceCand_1                     | true                     |

    Then the shipment identified by inOut_1 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | approved_for_invoice_error   |

  @from:cucumber
  Scenario: create receipt, complete it, approve invoice candidate for invoicing then try to reverse shipment with error
    Given metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | IsSOTrx | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier | DeliveryRule | DeliveryViaRule | FreightCostRule | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | inOut_1               | false   | bpartner_PO              | bpartnerLocation_PO               | warehouse                 | A            | P               | I               | 2022-09-15   | V+           | 5            | MMR             | MR             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | MovementQty | UomCode | OPT.M_Locator_ID.Identifier |
      | inOutLine_1               | inOut_1               | p_PO                        | 10         | 10          | PCE     | locator                     |

    When the shipment identified by inOut_1 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice |
      | invoiceCand_1                     | inOutLine_1                   | 10               | 10           |

    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | invoiceCand_1                     | true                     |

    Then the material receipt identified by inOut_1 is reactivated expecting error
      | OPT.AD_Message_ID.Identifier |
      | approved_for_invoice_error   |


