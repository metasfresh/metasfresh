@from:cucumber
@ghActions:run_on_executor6
Feature: match inv is created when in transit

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  Scenario: match inv is created when invoice generated before receipt (in transit)
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier | Name                     |
      | p_1        | purchaseProduct_13072023 |
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
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 10  | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huItemPurchaseProduct              | huPiItemTU                 | p_1                     | 10  | 2021-01-01 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                         | Value                         | OPT.Description                     | OPT.IsActive |
      | ps_1       | pricing_system_name_13072023 | pricing_system_value_13072023 | pricing_system_description_13072023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_13072023 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | purchaseOrder-PLV_13072023 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endvendor_1 | Endvendor_13072023 | Y            | N              | ps_1                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endvendor_1              | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_PaymentTerm_ID | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | o_1        | N       | endvendor_1              | 2021-04-16  | 1000012              | po_ref_mock     | POO             | ps_1                              |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    Then after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 0            |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | ic_1                              | I                        |
    When process invoice candidates and wait 30s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | poReference | paymentTerm | processed | docStatus |
      | invoice_1               | endvendor_1              | l_1                               | po_ref_mock | 1000002     | true      | CO        |
    And validate invoice lines for invoice_1:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed | OPT.PriceEntered | OPT.PriceActual | OPT.LineNetAmt | OPT.Discount |
      | invoiceLine1_1              | p_1                     | 10          | true      | 10               | 10              | 100            | 0            |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_13072023        | o_1                   | ol_1                      | endvendor_1              | l_1                               | p_1                     | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedTopHU_1   | receiptSchedule_13072023        | N               | 1     | N               | 1     | N               | 10          | huItemPurchaseProduct              | huPackingLU                  |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | processedTopHU_1   | receiptSchedule_13072023        | inOut_13072023        |
    And validate the created material receipt
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | processed | docStatus |
      | inOut_13072023        | endvendor_1              | l_1                               | 2021-04-16  | true      | CO        |
    And validate the created material receipt lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | inOutLine_13072023        | inOut_13072023        | p_1                     | 10          | true      |
    And after not more than 30s, M_MatchInv are found:
      | M_MatchInv_ID.Identifier | M_Product_ID.Identifier | C_Invoice_ID.Identifier | C_InvoiceLine_ID.Identifier | OPT.M_InOut_ID.Identifier | OPT.M_InOutLine_ID.Identifier |
      | matchInv_1               | p_1                     | invoice_1               | invoiceLine1_1              | inOut_13072023            | inOutLine_13072023            |
