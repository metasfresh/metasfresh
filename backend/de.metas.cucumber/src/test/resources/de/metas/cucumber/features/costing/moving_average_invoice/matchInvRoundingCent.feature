@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@allure.label.feature:F1514_Cost_Type_Moving_Average_Invoice
@ghActions:run_on_executor6
Feature: Moving Average Invoice - MatchInv of a rounding cent posts with a valid accounting structure
## F1500: Costing
# One vendor invoice line (net 202.43 CHF; the matched share of each receipt line is 101.215 CHF) is matched to two receipt lines of the same order line.
# The invoice share and the receipt value are not on the cent grid (101.215 / 133.444), so the MatchInv legs
# (stock share -32.229, sold share 0) end up with a rest of a cent against the invoice-clearing and receipt amounts,
# and the posting is refused with "Invalid accounting operation structure".
# All amounts are in CHF: a single currency is what matters here (one schema, one price list).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2027-03-15T13:30:13+01:00[Europe/Berlin]
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  | CostingMethod |
      | acctSchema      | metas fresh UN/34 CHF | M             |
    And cost elements for material costing methods MovingAverageInvoice are active
    And load M_Warehouse:
      | M_Warehouse_ID | Value        | M_Locator_ID |
      | warehouseStd   | StdWarehouse | locatorStd   |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product    | KGM      |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | purchasePS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | purchasePL | purchasePS         | CH           | CHF           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | purchasePLV | purchasePL     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product      | 10.0     | KGM      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | purchasePS         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | CH           | Y               | Y               |

  @Id:S33773_TC1
  Scenario: A MovingAverageInvoice MatchInv with sub-cent amounts posts as a valid 1:N structure on the cent grid
    #
    # Order 1.828 KGM at 146.00 CHF; received in two parts of 0.914 KGM each.
    #
    Given for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price | OPT.C_UOM_ID.X12DE355 |
      | po_l1          | vendor        | 2027-03-15  | POO         | warehouseStd   | product      | 1.828      | 146   | KGM                   |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID | QtyToMove_Override |
      | po_l1          | receipt1   | receipt1_l1    | 0.914              |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID | QtyToMove_Override |
      | po_l1          | receipt2   | receipt2_l1    | 0.914              |
    #
    # One invoice line for both receipt lines: net 202.43 CHF (110.74 CHF per KGM).
    #
    When for costing, create completed invoice with one line
      | C_OrderLine_ID | PriceEntered_Override | C_Invoice_ID | C_InvoiceLine_ID |
      | po_l1          | 110.74                | invoice      | invoice_l1       |
    And M_MatchInv are found
      | M_MatchInv_ID | C_InvoiceLine_ID | M_InOutLine_ID |
      | matchInv1     | invoice_l1       | receipt1_l1    |
      | matchInv2     | invoice_l1       | receipt2_l1    |
    #
    # Both matches post 1105 / 2060 / 90000 on the cent grid:
    #   NotInvoicedReceipts DR 133.44, P_InventoryClearing CR 101.22, P_Asset CR 32.22 (qty 0).
    #
    And Wait until documents receipt1,receipt2,invoice,matchInv1,matchInv2 are posted
    Then Fact_Acct records are matching
      | AccountConceptualName    | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Qty        | M_Product_ID | Record_ID | C_BPartner_ID |
      | NotInvoicedReceipts_Acct | 133.44    |           | 133.44 CHF  |             | +0.914 KGM | product      | matchInv1 | vendor        |
      | P_InventoryClearing_Acct |           | 101.22    |             | 101.22 CHF  | -0.914 KGM | product      | matchInv1 | vendor        |
      | P_Asset_Acct             |           | 32.22     |             | 32.22 CHF   | 0 KGM      | product      | matchInv1 | vendor        |
      | NotInvoicedReceipts_Acct | 133.44    |           | 133.44 CHF  |             | +0.914 KGM | product      | matchInv2 | vendor        |
      | P_InventoryClearing_Acct |           | 101.22    |             | 101.22 CHF  | -0.914 KGM | product      | matchInv2 | vendor        |
      | P_Asset_Acct             |           | 32.22     |             | 32.22 CHF   | 0 KGM      | product      | matchInv2 | vendor        |
    #
    # The receipts and the invoice post their own side of the same two accounts. Only the asserted line of each
    # document is listed; the row with "*" as account conceptual name accepts the document's other lines.
    # Receipt side: NotInvoicedReceipts CR 133.44 per receipt, the amount the matches debit on the same account.
    # Invoice side: the whole invoice line net 202.43 CHF is booked on P_InventoryClearing (one invoice, so one amount for both matches).
    #
    And Fact_Acct records are matching
      | AccountConceptualName    | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Record_ID |
      | *                        |           |           |             |             | receipt1  |
      | NotInvoicedReceipts_Acct |           | 133.44    |             | 133.44 CHF  | receipt1  |
    And Fact_Acct records are matching
      | AccountConceptualName    | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Record_ID |
      | *                        |           |           |             |             | receipt2  |
      | NotInvoicedReceipts_Acct |           | 133.44    |             | 133.44 CHF  | receipt2  |
    And Fact_Acct records are matching
      | AccountConceptualName    | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Record_ID |
      | *                        |           |           |             |             | invoice   |
      | P_InventoryClearing_Acct | 202.43    |           | 202.43 CHF  |             | invoice   |
