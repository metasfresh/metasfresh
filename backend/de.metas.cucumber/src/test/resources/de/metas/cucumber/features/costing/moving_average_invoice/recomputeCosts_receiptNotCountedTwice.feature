@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1530_Recreate_product_costs
@allure.label.feature:F1514_Cost_Type_Moving_Average_Invoice
@ghActions:run_on_executor6
Feature: Recompute Costs - Moving Average Invoice receipts are not counted twice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-03-02T09:00:00+01:00[Europe/Berlin]
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  | CostingMethod |
      | acctSchema      | metas fresh UN/34 CHF | M             |
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

  Scenario: Recompute starting at the 2nd receipt keeps 15 @ 6.00 and the shipment cost of -30
    # R1: 10 @ 5 on 2026-03-02
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po1_l1         | vendor        | 2026-03-02  | POO         | warehouseStd   | product      | 10         | 5     |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOutLine_ID |
      | po1_l1         | receipt1_line1 |
    And after not more than 30s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID      | IsSOTrx | Amt    | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1 | N       | 50 CHF | 10 PCE | Y               |
    # R2: 10 @ 7 on 2026-03-04
    When metasfresh has date and time 2026-03-04T09:00:00+01:00[Europe/Berlin]
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po2_l1         | vendor        | 2026-03-04  | POO         | warehouseStd   | product      | 10         | 7     |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOutLine_ID |
      | po2_l1         | receipt2_line1 |
    And after not more than 30s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID      | IsSOTrx | Amt    | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1 | N       | 50 CHF | 10 PCE | Y               |
      | M_InOutLine | receipt2_line1 | N       | 70 CHF | 10 PCE | Y               |
    # S3: ship 5 on 2026-03-06
    When metasfresh has date and time 2026-03-06T09:00:00+01:00[Europe/Berlin]
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | so1_l1         | customer      | 2026-03-06  | SOO         | warehouseStd   | product      | 5          | 19    |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID  |
      | so1_l1         | shipment1_line1 |
    Then after not more than 30s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID       | IsSOTrx | Amt     | Qty    | IsChangingCosts | Prev_CurrentCostPrice | Prev_CurrentQty | Prev_CumulatedAmt | Prev_CumulatedQty |
      | M_InOutLine | receipt1_line1  | N       | 50 CHF  | 10 PCE | Y               | 0 CHF                 | 0 PCE           | 0 CHF             | 0 PCE             |
      | M_InOutLine | receipt2_line1  | N       | 70 CHF  | 10 PCE | Y               | 5 CHF                 | 10 PCE          | 50 CHF            | 10 PCE            |
      | M_InOutLine | shipment1_line1 | Y       | -30 CHF | -5 PCE | Y               | 6 CHF                 | 20 PCE          | 120 CHF           | 20 PCE            |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 6 CHF            | 15 PCE     | 90 CHF       |
    # Zero-difference count dated at R2 -> recompute start date 2026-03-04
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invStart       | invStart_l1        | 2026-03-04   | warehouseStd   | product      | 20      | 20       | PCE          |
    And invoke M_Inventory_RecomputeCosts:
      | M_Inventory_ID | C_AcctSchema_ID | CostingMethod |
      | invStart       | acctSchema      | M             |
    And after not more than 120s, the repost queue is drained
    Then after not more than 60s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName       | Record_ID       | IsSOTrx | Amt     | Qty    | IsChangingCosts | Prev_CurrentCostPrice | Prev_CurrentQty | Prev_CumulatedAmt | Prev_CumulatedQty |
      | M_InOutLine     | receipt1_line1  | N       | 50 CHF  | 10 PCE | Y               | 0 CHF                 | 0 PCE           | 0 CHF             | 0 PCE             |
      | M_InOutLine     | receipt2_line1  | N       | 70 CHF  | 10 PCE | Y               | 5 CHF                 | 10 PCE          | 50 CHF            | 10 PCE            |
      | M_InOutLine     | shipment1_line1 | Y       | -30 CHF | -5 PCE | Y               | 6 CHF                 | 20 PCE          | 120 CHF           | 20 PCE            |
      | M_InventoryLine | invStart_l1     | N       | 0 CHF   | 0 PCE  | Y               |  |  |  |  |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 6 CHF            | 15 PCE     | 90 CHF       |
