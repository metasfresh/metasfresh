@from:cucumber
Feature: A completed invoice whose accounting entries were deleted is posted again
##
## Several actions delete the accounting entries (Fact_Acct) of an invoice and set C_Invoice.Posted='N' while the invoice stays completed:
## reversing the material receipt that the invoice is matched to (M_MatchInv deleted), and the process "Steuer berechnen" (InvoiceCalculateTax).
## The invoice must then be posted again. A drafted or reversed invoice must not be queued.
##

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And set sys config boolean value false for sys config PreventReversingShipmentsWhenInvoiceExists
    And metasfresh has date and time 2027-03-15T13:30:13+01:00[Europe/Berlin]
    And load M_Warehouse:
      | M_Warehouse_ID | Value        | M_Locator_ID |
      | warehouseStd   | StdWarehouse | locatorStd   |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | purchasePL | ps                 | CH           | CHF           | false |
      | salesPL    | ps                 | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | purchasePLV  | purchasePL     |
      | salesPLV     | salesPL        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product      | 10.0     | PCE      |
      | salesPLV               | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | bpartner   | Y        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | bpLocation   | bpartner      | CH           | Y               | Y               |

  @Id:S33766_10
  Scenario: the purchase invoice is posted again after the material receipt it is matched to was reversed
    Given for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po_l1          | bpartner      | 2027-03-15  | POO         | warehouseStd   | product      | 10         | 10    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | po_l1          | receipt    | receipt_line1  |
    And for costing, create completed invoice with one line
      | C_Invoice_ID | C_OrderLine_ID | M_MatchInv_ID |
      | invoice      | po_l1          | matchInv      |
    And Wait until documents invoice are posted

    When the material receipt identified by receipt is reversed as receiptReversal

    Then Wait until documents invoice are posted
