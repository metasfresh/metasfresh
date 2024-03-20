@from:cucumber
@ghActions:run_on_executor6
Feature: tax departure country for SO and PO

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]

    When load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load C_Country by country code:
      | C_Country_ID.Identifier | CountryCode |
      | Romania                 | RO          |
    And metasfresh contains M_Products:
      | Identifier | Name                |
      | p_1        | salesProduct_290622 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                       | Value                       | OPT.Description                   | OPT.IsActive |
      | ps_1       | pricing_system_name_290622 | pricing_system_value_290622 | pricing_system_description_290622 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                      | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_SO_290622 | null            | true  | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | price_list_name_PO_290622 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                     | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_290622    | 2021-04-01 |
      | plv_2      | pl_2                      | purchaseOrder-PLV_290622 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name               |
      | huPackingLU           | huPackingLU_290622 |
      | huPackingTU           | huPackingTU_290622 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                    | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU_290622 | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU_290622 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 10  | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier    | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_290622 | N            | Y              | ps_1                          |
      | endvendor_1   | Endvendor_290622   | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 2906202289012 | endcustomer_1            | Y                   | Y                   |
      | l_2        | 2906202289011 | endvendor_1              | Y                   | Y                   |

  @from:cucumber
  Scenario: tax departure country is propagated from sales order to sales invoice
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_Tax_Departure_Country_ID.Identifier | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-14  | Romania                                   | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_ol_1                           | D            | true                | false       |
    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Departure_Country_ID.Identifier |
      | ic_1                              | ol_1                      | 10           | Romania                                   |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Tax_Departure_Country_ID.Identifier |
      | invoice_1               | endcustomer_1            | l_1                               | 1000002     | true      | CO        | Romania                                   |

  @from:cucumber
  Scenario: tax departure country is propagated from purchase order to purchase invoice
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | OPT.POReference | OPT.DocBaseType | DateOrdered | OPT.C_Tax_Departure_Country_ID.Identifier | OPT.C_PaymentTerm_ID |
      | o_1        | false   | endvendor_1              | po_ref_mock     | POO             | 2021-04-14  | Romania                                   | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | o_1                   | ol_1                      | endvendor_1              | l_2                               | p_1                     | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU     | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU     | receiptSchedule_PO              | material_receipt_1    |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | receiptLine1              | material_receipt_1    | p_1                     | 10          | true      |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Departure_Country_ID.Identifier |
      | ic_1                              | ol_1                      | 10           | Romania                                   |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Tax_Departure_Country_ID.Identifier |
      | invoice_1               | endvendor_1              | l_2                               | 1000002     | true      | CO        | Romania                                   |

  @from:cucumber
  Scenario: Different tax departure country in invoice candidates will always result in multiple invoices
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_Tax_Departure_Country_ID.Identifier | OPT.C_PaymentTerm_ID |
      | o_1        | true    | endcustomer_1            | 2021-04-14  | Romania                                   | 1000012              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
      | ol_2       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_ol_1     | ol_1                      | N             |
      | s_ol_2     | ol_2                      | N             |
    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_ol_1                           | D            | true                | false       |
      | s_ol_2                           | D            | true                | false       |
    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_ol_1                           | shipment_1            |
      | s_ol_2                           | shipment_2            |
    And load C_Country by country code:
      | C_Country_ID.Identifier | CountryCode |
      | Albania                 | AL          |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.C_Tax_Departure_Country_ID.Identifier |
      | ic_1                              | ol_1                      | 10           | Romania                                   |
      | ic_2                              | ol_2                      | 10           | Romania                                   |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Departure_Country_ID.Identifier |
      | ic_2                              | Albania                                   |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
      | ic_2                              |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
      | ic_2                              | invoice_2               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.C_Tax_Departure_Country_ID.Identifier |
      | invoice_1               | endcustomer_1            | l_1                               | 1000002     | true      | CO        | Romania                                   |
      | invoice_2               | endcustomer_1            | l_1                               | 1000002     | true      | CO        | Albania                                   |

  @from:cucumber
  Scenario: after generating PO from SO, the tax departure country is not propagated from sales order to purchase order
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.IsCurrentVendor |
      | endvendor_1              | p_1                     | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_Tax_Departure_Country_ID.Identifier |
      | o_1        | true    | endcustomer_1            | 2021-04-14  | Romania                                   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then generate PO from SO is invoked with parameters:
      | C_BPartner_ID.Identifier | C_Order_ID.Identifier | PurchaseType |
      | endvendor_1              | o_1                   | Standard     |
    And the order is created:
      | Link_Order_ID.Identifier | IsSOTrx | DocBaseType |
      | o_1                      | false   | POO         |
