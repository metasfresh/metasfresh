@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@allure.label.feature:F1514_Cost_Type_Moving_Average_Invoice
@ghActions:run_on_executor6
Feature: Moving Average Invoice - MatchInv repost keeps the invoice-vs-PO price variance out of GR/IR
## F1500: Costing
# A MovingAverageInvoice vendor MatchInv posts the invoice-vs-PO price difference against the on-hand
# inventory cost (P_Asset), while NotInvoicedReceipts (GR/IR) only ever carries the PO-price receipt
# amount, so GR/IR nets to zero against the material receipt. The P_Asset leg is a pure revaluation:
# zero quantity, attributed to the receipt's locator.
#
# On a REPOST the posting must reconstruct itself from ALL the persisted cost-detail legs
# (MAIN + ADJUSTMENT), not just MAIN. If the non-MAIN legs are dropped, GR/IR degenerates to the full
# invoiced amount and the price variance is left stuck in GR/IR. This scenario reposts the MatchInv and
# asserts the GL outcome is identical to the first posting.

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
      | Identifier |
      | product    |
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
      | purchasePLV            | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | purchasePS         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | CH           | Y               | Y               |

  @Id:S26253_TC23
  Scenario: A MovingAverageInvoice MatchInv repost routes the price variance to inventory, keeping GR/IR clean
    #
    # Receive 10 PCE at the PO price 10 CHF = 100 CHF; all 10 stay on hand.
    #
    Given for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po_l1          | vendor        | 2027-03-15  | POO         | warehouseStd   | product      | 10         | 10    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | po_l1          | receipt    | receipt_line1  |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID     | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receipt_line1 | N       | 100 CHF | 10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 10 PCE     | 100 CHF      |
    #
    # The vendor invoice arrives at 12 CHF (a 2 CHF/PCE price increase) = 120 CHF, matched to the receipt.
    # All 10 PCE are still on hand, so the whole 20 CHF variance revalues the on-hand cost (ADJUSTMENT leg):
    # the moving-average price moves (100 + 20) / 10 = 12 CHF. The MatchInv persists a MAIN leg (the invoiced
    # 120) and an ADJUSTMENT leg (the still-in-stock 20); getAmountBeforeAdjustment = 120 - 20 = 100 = the
    # PO-price receipt amount that posts to GR/IR.
    #
    When for costing, create completed invoice with one line
      | C_OrderLine_ID | PriceEntered_Override | M_MatchInv_ID |
      | po_l1          | 12                    | matchInv      |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID     | IsSOTrx | AmtType    | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receipt_line1 | N       |            | 100 CHF | 10 PCE | Y               |
      | M_MatchInv  | matchInv      | N       | MAIN       | 120 CHF | 10 PCE | N               |
      | M_MatchInv  | matchInv      | N       | ADJUSTMENT | 20 CHF  | 0 PCE  | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 12 CHF           | 10 PCE     | 120 CHF      |
    #
    # First posting (baseline). The MatchInv books:
    #   NotInvoicedReceipts (GR/IR) DR 100  = the PO-price receipt amount (NOT the invoiced 120)
    #   P_InventoryClearing         CR 120  = the invoiced amount
    #   P_Asset                     DR 20   = the on-hand price variance
    # so GR/IR nets to zero against the material receipt's GR/IR credit of 100.
    #
    # GR/IR and InventoryClearing are quantity-balanced (+10 / -10) and move no stock. The P_Asset leg has no
    # negative counterpart, so it carries ZERO qty - the receipt already booked the 10 PCE, and a qty here would
    # be counted a second time by the inventory valuation (Lagerwert) report. It is attributed to the receipt's
    # locator, so the revaluation lands in the warehouse the goods were received into.
    #
    And Wait until documents receipt,matchInv are posted
    And Fact_Acct records are matching
      | AccountConceptualName    | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Qty     | M_Locator_ID | M_Product_ID | Record_ID | C_BPartner_ID |
      | NotInvoicedReceipts_Acct | 100       |           | 100 CHF     |             | +10 PCE | locatorStd   | product      | matchInv  | vendor        |
      | P_InventoryClearing_Acct |           | 120       |             | 120 CHF     | -10 PCE | null         | product      | matchInv  | vendor        |
      | P_Asset_Acct             | 20        |           | 20 CHF      |             | 0 PCE   | locatorStd   | product      | matchInv  | vendor        |
    And Fact_Acct records balances for documents receipt,matchInv are matching
      | AccountConceptualName    | M_Product_ID | SourceBalance | AcctBalance | Qty   |
      | NotInvoicedReceipts_Acct | product      | 0 CHF         | 0           | 0 PCE |
    #
    # Repost the MatchInv (and the receipt) from before the documents' date. This is the trigger that exposed
    # the bug: the repost recovers the existing cost details and must reuse ALL legs (MAIN + ADJUSTMENT).
    # If only MAIN is recovered, GR/IR would degenerate to the full invoiced 120 and leave +20 stuck in GR/IR.
    #
    # A PLAIN repost is required here (it reuses the existing cost details). A cost RECOMPUTE would instead
    # delete the cost details and recreate every leg on repost, which recreates them correctly and hides the
    # bug -- so the plain-repost driver is the load-bearing trigger, not a recompute.
    #
    Given after not more than 60s, the repost queue is drained
    When the accounting repost driver runs:
      | C_AcctSchema_ID | StartDateAcct |
      | acctSchema      | 2027-03-01    |
    And after not more than 120s, the repost queue is drained
    And Wait until documents receipt,matchInv are posted
    #
    # After the repost the GL outcome is IDENTICAL to the first posting: GR/IR still carries only the
    # PO-price receipt amount (100), the variance still sits on P_Asset (20), and GR/IR still nets to zero.
    #
    Then Fact_Acct records are matching
      | AccountConceptualName    | AmtAcctDr | AmtAcctCr | AmtSourceDr | AmtSourceCr | Qty     | M_Locator_ID | M_Product_ID | Record_ID | C_BPartner_ID |
      | NotInvoicedReceipts_Acct | 100       |           | 100 CHF     |             | +10 PCE | locatorStd   | product      | matchInv  | vendor        |
      | P_InventoryClearing_Acct |           | 120       |             | 120 CHF     | -10 PCE | null         | product      | matchInv  | vendor        |
      | P_Asset_Acct             | 20        |           | 20 CHF      |             | 0 PCE   | locatorStd   | product      | matchInv  | vendor        |
    And Fact_Acct records balances for documents receipt,matchInv are matching
      | AccountConceptualName    | M_Product_ID | SourceBalance | AcctBalance | Qty   |
      | NotInvoicedReceipts_Acct | product      | 0 CHF         | 0           | 0 PCE |
