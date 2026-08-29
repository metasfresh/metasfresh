@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1500_Costing
@allure.label.feature:F1514_Cost_Type_Moving_Average_Invoice
@ghActions:run_on_executor6
Feature: Moving Average Invoice - explicit cost price and negative on-hand
## F1500: Costing
# Moving-Average-Invoice costing under two conditions the on-hand quantity can reach:
#  - an explicit-cost (year-end) inventory revalues the on-hand cost even when stock is already on hand
#  - a genuine over-issue (a non-reversal shipment of more than is on hand) drives the on-hand quantity
#    negative; the moving average must carry the negative on-hand honestly (no floor to zero) and an
#    invoice price variance must not adjust a negative on-hand.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2021-04-14T13:30:13+01:00[Europe/Berlin]
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
    And cost elements for material costing methods MovingAverageInvoice are active
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | purchasePS |
      | salesPS    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | purchasePL | purchasePS         | CH           | CHF           | false |
      | salesPL    | salesPS            | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier  | M_PriceList_ID |
      | purchasePLV | purchasePL     |
      | salesPLV    | salesPL        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | purchasePLV            | product      | 10.0     | PCE      |
      | salesPLV               | product      | 19.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | purchasePS         |
      | customer   | N        | Y          | salesPS            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation   | vendor        | CH           | Y               | Y               |
      | customerLocation | customer      | CH           | Y               | Y               |

  @Id:S26253_TC20
  Scenario: A year-end explicit-cost inventory revalues the on-hand cost even with stock on hand
    #
    # Opening stock: a physical inventory books 10 PCE at cost price 10.
    #
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice |
      | invOpening     | invOpening_l1      | 2021-01-01   | warehouseStd   | product      | 0       | 10       | PCE          | 10        |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName       | Record_ID     | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_InventoryLine | invOpening_l1 | N       | 100 CHF | 10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 10 PCE     | 100 CHF      |
    #
    # Year-end revaluation: a physical inventory recounts the product (finding 10 more) and books an
    # explicit cost price of 15. Even though there is already stock on hand, the explicit cost price is
    # adopted for the whole on-hand quantity (the operator setting the price owns that decision).
    #
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice |
      | invYearEnd     | invYearEnd_l1      | 2021-12-31   | warehouseStd   | product      | 10      | 20       | PCE          | 15        |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName       | Record_ID     | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_InventoryLine | invOpening_l1 | N       | 100 CHF | 10 PCE | Y               |
      | M_InventoryLine | invYearEnd_l1 | N       | 150 CHF | 10 PCE | Y               |
    #
    # The cumulated amount is the running sum of the per-transaction deltas (100 + 150 = 250), not the
    # revalued stock value (15 * 20 = 300).
    #
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 15 CHF           | 20 PCE     | 250 CHF      |

  @Id:S26253_TC21
  Scenario: A non-reversal over-issue drives the on-hand quantity negative and it is carried honestly
    #
    # Receive 10 PCE at 10 CHF.
    #
    Given for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po_l1          | vendor        | 2021-04-16  | POO         | warehouseStd   | product      | 10         | 10    |
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
    # Ship all 10 PCE that are on hand: the on-hand quantity returns to zero.
    #
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | soFull_l1      | customer      | 2021-04-16  | SOO         | warehouseStd   | product      | 10         | 19    |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID     |
      | soFull_l1      | shipmentFull_line1 |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID          | IsSOTrx | Amt      | Qty     | IsChangingCosts |
      | M_InOutLine | receipt_line1      | N       | 100 CHF  | 10 PCE  | Y               |
      | M_InOutLine | shipmentFull_line1 | Y       | -100 CHF | -10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 0 PCE      | 0 CHF        |
    #
    # Over-issue: a force-delivery sales order ships another 5 PCE though nothing is on hand. The on-hand
    # goes to -5 through CurrentCost.addToCurrentQtyAndCumulate, which carries the quantity UNCLAMPED (a
    # floor to zero would leave it at 0). The cost price stays 10; the cumulated amount follows the running
    # sum of deltas: 100 - 100 - 50 = -50.
    #
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price | DeliveryRule |
      | soOver_l1      | customer      | 2021-04-16  | SOO         | warehouseStd   | product      | 5          | 19    | F            |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID     |
      | soOver_l1      | shipmentOver_line1 |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID          | IsSOTrx | Amt      | Qty     | IsChangingCosts |
      | M_InOutLine | receipt_line1      | N       | 100 CHF  | 10 PCE  | Y               |
      | M_InOutLine | shipmentFull_line1 | Y       | -100 CHF | -10 PCE | Y               |
      | M_InOutLine | shipmentOver_line1 | Y       | -50 CHF  | -5 PCE  | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | -5 PCE     | -50 CHF      |
    #
    # A corrected receipt of 15 PCE at 12 CHF blends onto the negative on-hand honestly:
    # (10 * -5 + 12 * 15) / (-5 + 15) = 130 / 10 = 13 CHF. Flooring the earlier over-issue to zero
    # would have produced a wrong average.
    #
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | poFix_l1       | vendor        | 2021-04-16  | POO         | warehouseStd   | product      | 15         | 12    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID   |
      | poFix_l1       | receiptFix | receiptFix_line1 |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID          | IsSOTrx | Amt      | Qty     | IsChangingCosts |
      | M_InOutLine | receipt_line1      | N       | 100 CHF  | 10 PCE  | Y               |
      | M_InOutLine | shipmentFull_line1 | Y       | -100 CHF | -10 PCE | Y               |
      | M_InOutLine | shipmentOver_line1 | Y       | -50 CHF  | -5 PCE  | Y               |
      | M_InOutLine | receiptFix_line1   | N       | 180 CHF  | 15 PCE  | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 13 CHF           | 10 PCE     | 130 CHF      |

  @Id:S26253_TC22
  Scenario: An invoice price variance against a negative on-hand posts entirely to COGS, not to on-hand
    #
    # Receive 10 PCE at 10 CHF.
    #
    Given for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | poA_l1         | vendor        | 2021-04-16  | POO         | warehouseStd   | product      | 10         | 10    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | poA_l1         | receiptA   | receiptA_line1 |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID      | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receiptA_line1 | N       | 100 CHF | 10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 10 PCE     | 100 CHF      |
    #
    # Ship all 10 PCE that are on hand: the on-hand quantity returns to zero.
    #
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | soFull_l1      | customer      | 2021-04-16  | SOO         | warehouseStd   | product      | 10         | 19    |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID     |
      | soFull_l1      | shipmentFull_line1 |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID          | IsSOTrx | Amt      | Qty     | IsChangingCosts |
      | M_InOutLine | receiptA_line1     | N       | 100 CHF  | 10 PCE  | Y               |
      | M_InOutLine | shipmentFull_line1 | Y       | -100 CHF | -10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 0 PCE      | 0 CHF        |
    #
    # Over-issue: a force-delivery sales order ships another 10 PCE though nothing is on hand, driving the
    # on-hand to -10 through the non-reversal shipment path (carried unclamped). Delivery A's receipt is
    # untouched, so its vendor invoice can still be matched.
    #
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price | DeliveryRule |
      | soOver_l1      | customer      | 2021-04-16  | SOO         | warehouseStd   | product      | 10         | 19    | F            |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID     |
      | soOver_l1      | shipmentOver_line1 |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID          | IsSOTrx | Amt      | Qty     | IsChangingCosts |
      | M_InOutLine | receiptA_line1     | N       | 100 CHF  | 10 PCE  | Y               |
      | M_InOutLine | shipmentFull_line1 | Y       | -100 CHF | -10 PCE | Y               |
      | M_InOutLine | shipmentOver_line1 | Y       | -100 CHF | -10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | -10 PCE    | -100 CHF     |
    #
    # Delivery A's vendor invoice arrives at 13 CHF (a 3 CHF/PCE price variance). Because the on-hand
    # quantity is negative, NONE of the difference may adjust the on-hand cost price: the whole 30 CHF
    # variance is period cost (ALREADY_SHIPPED / COGS), the on-hand adjustment is 0 (so no ADJUSTMENT cost
    # detail is produced), and the current cost price stays 10.
    #
    When for costing, create completed invoice with one line
      | C_OrderLine_ID | PriceEntered_Override | M_MatchInv_ID |
      | poA_l1         | 13                    | matchInvA     |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID          | IsSOTrx | AmtType         | Amt      | Qty     | IsChangingCosts |
      | M_InOutLine | receiptA_line1     | N       |                 | 100 CHF  | 10 PCE  | Y               |
      | M_InOutLine | shipmentFull_line1 | Y       |                 | -100 CHF | -10 PCE | Y               |
      | M_InOutLine | shipmentOver_line1 | Y       |                 | -100 CHF | -10 PCE | Y               |
      | M_MatchInv  | matchInvA          | N       | MAIN            | 130 CHF  | 10 PCE  | N               |
      | M_MatchInv  | matchInvA          | N       | ALREADY_SHIPPED | 30 CHF   | 0 PCE   | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | -10 PCE    | -100 CHF     |
