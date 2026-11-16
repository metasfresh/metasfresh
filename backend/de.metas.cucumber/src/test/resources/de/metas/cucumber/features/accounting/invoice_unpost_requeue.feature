@from:cucumber
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01106_Re-Post_Documents
Feature: A completed invoice whose accounting entries were deleted is posted again
##
## Several actions delete the accounting entries (Fact_Acct) of an invoice and set C_Invoice.Posted='N' while the invoice stays completed:
## reversing the material receipt that the invoice is matched to (M_MatchInv deleted), and matching the in/out costs of a receipt to a cost invoice.
## The invoice must then be posted again. A drafted or voided invoice must not be queued; a reversed one is posted again (reversed documents are posted too).
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
    # the invoice match is posted asynchronously (it books costs); a user reverses the receipt only after that is done
    And Wait until documents invoice,matchInv are posted

    When the material receipt identified by receipt is reversed as receiptReversal

    Then Wait until documents invoice are posted
    # the reversed receipt no longer clears the invoiced goods: the whole amount is booked on the expense account
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID |
      | V_Liability_Acct      |             | 108.10 CHF  | invoice   |
      | P_Expense_Acct        | 100 CHF     |             | invoice   |
      | T_Credit_Acct         | 8.10 CHF    |             | invoice   |

  @Id:S33766_11
  Scenario: the reversed invoice and its reversal are posted again when the material receipt is reversed after the invoice was reversed
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
    And the invoice identified by invoice is reversed
    And the reversal of invoice invoice is identified by invoiceReversal
    And validate created invoice lines
      | C_InvoiceLine_ID    | C_Invoice_ID    | M_Product_ID | QtyInvoiced |
      | invoiceReversalLine | invoiceReversal | product      | -10         |
    And M_MatchInv are found
      | M_MatchInv_ID    | C_InvoiceLine_ID    | M_InOutLine_ID |
      | matchInvReversal | invoiceReversalLine | receipt_line1  |
    # the invoice matches are posted asynchronously (they book costs); a user reverses the receipt only after that is done
    And Wait until documents invoice,invoiceReversal,matchInv,matchInvReversal are posted
    And the posting requests of documents invoice,invoiceReversal are remembered

    When the material receipt identified by receipt is reversed as receiptReversal

    Then Wait until documents invoice,invoiceReversal are posted
    # the reversed receipt no longer clears the invoiced goods: the whole amount is booked on the expense account
    # (the reversal books the same lines with negative amounts on the same side)
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID       |
      | V_Liability_Acct      |             | 108.10 CHF  | invoice         |
      | P_Expense_Acct        | 100 CHF     |             | invoice         |
      | T_Credit_Acct         | 8.10 CHF    |             | invoice         |
      | V_Liability_Acct      |             | -108.10 CHF | invoiceReversal |
      | P_Expense_Acct        | -100 CHF    |             | invoiceReversal |
      | T_Credit_Acct         | -8.10 CHF   |             | invoiceReversal |

  @Id:S33766_13
  Scenario: matching the in/out costs adds no posting request for a cost invoice that is not posted yet
    # real flow: order with an order cost -> receipt (creates the in/out cost) -> cost invoice (service line)
    Given metasfresh contains M_Products:
      | Identifier  | ProductType |
      | costService | S           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | costService  | 20.0     | PCE      |
    And metasfresh contains C_Cost_Type:
      | Identifier |
      | freight    |
    And for costing, create drafted order with one line
      | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po         | po_l1          | bpartner      | 2027-03-15  | POO         | warehouseStd   | product      | 10         | 10    |
    And metasfresh contains order costs:
      | C_OrderLine_ID | C_Cost_Type_ID | C_BPartner_ID | Amount |
      | po_l1          | freight        | bpartner      | 20     |
    And the order identified by po is completed
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | po_l1          | receipt    | receipt_line1  |
    # accounting off while the invoice is completed: the invoice is completed but never queued for posting
    And temporarily set sys config boolean value false for sys config 'org.adempiere.acct.Enabled'
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | costInvoice | bpartner      | Eingangsrechnung        | 2027-03-15   | Spot                     | false   | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price |
      | costLine   | costInvoice  | costService  | 1 PCE       | 20    |
    And the invoice identified by costInvoice is completed
    And set sys config boolean value true for sys config org.adempiere.acct.Enabled
    And the documents costInvoice are not posted
    And no Fact_Acct records exist for documents costInvoice
    And the posting requests of documents costInvoice are remembered

    When the in/out costs of material receipt "receipt" are matched to the line of invoice "costInvoice"

    Then documents costInvoice got no new posting request and no note
    And the documents costInvoice are not posted
    And no Fact_Acct records exist for documents costInvoice
    # clean up: post the invoice now, so no completed-but-unposted invoice stays in the database (a later "repost all" in the same database would book it)
    And the documents costInvoice are reposted

  @Id:S33766_12
  Scenario: the posted cost invoice is posted again after the in/out costs of the receipt were matched to it
    # real flow: order with an order cost -> receipt (creates the in/out cost) -> cost invoice (service line) -> the "in/out costs" action matches the cost to the invoice line
    Given metasfresh contains M_Products:
      | Identifier  | ProductType |
      | costService | S           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | costService  | 20.0     | PCE      |
    And metasfresh contains C_Cost_Type:
      | Identifier |
      | freight    |
    And for costing, create drafted order with one line
      | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po         | po_l1          | bpartner      | 2027-03-15  | POO         | warehouseStd   | product      | 10         | 10    |
    And metasfresh contains order costs:
      | C_OrderLine_ID | C_Cost_Type_ID | C_BPartner_ID | Amount |
      | po_l1          | freight        | bpartner      | 20     |
    And the order identified by po is completed
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | po_l1          | receipt    | receipt_line1  |
    And metasfresh contains C_Invoice:
      | Identifier  | C_BPartner_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | costInvoice | bpartner      | Eingangsrechnung        | 2027-03-15   | Spot                     | false   | CHF                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price |
      | costLine   | costInvoice  | costService  | 1 PCE       | 20    |
    And the invoice identified by costInvoice is completed
    And Wait until documents costInvoice are posted
    # not matched yet: the whole cost invoice amount is booked on the expense account
    And Fact_Acct records are matching
      | AccountConceptualName | AmtSourceDr | AmtSourceCr | Record_ID   |
      | V_Liability_Acct      |             | 21.62 CHF   | costInvoice |
      | P_Expense_Acct        | 20 CHF      |             | costInvoice |
      | T_Credit_Acct         | 1.62 CHF    |             | costInvoice |

    When the in/out costs of material receipt "receipt" are matched to the line of invoice "costInvoice"

    Then Wait until documents costInvoice are posted
    And Fact_Acct records are matching
      | AccountConceptualName    | AmtSourceDr | AmtSourceCr | Record_ID   |
      | V_Liability_Acct         |             | 21.62 CHF   | costInvoice |
      | P_InventoryClearing_Acct | 20 CHF      |             | costInvoice |
      | P_Expense_Acct           | 0 CHF       | 0 CHF       | costInvoice |
      | T_Credit_Acct            | 1.62 CHF    |             | costInvoice |
