@from:cucumber
@ghActions:run_on_executor6
Feature: Moving Average Invoice - receive and ship

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

    
    #
    # Purchase and receive 10 items
    #
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | po1_l1         | vendor        | 2021-04-10  | POO         | warehouseStd   | product      | 10         | 10    |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOutLine_ID |
      | po1_l1         | receipt1_line1 |
    And after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID      | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1 | N       | 100 CHF | 10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 10 PCE     | 100 CHF      |
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: No initial stock, no invoice price variance
    When for costing, create completed invoice with one line
      | C_OrderLine_ID | M_MatchInv_ID |
      | po1_l1         | matchInv1     |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID      | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1 | N       | 100 CHF | 10 PCE | Y               |
      | M_MatchInv  | matchInv1      | N       | 100 CHF | 10 PCE | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 10 PCE     | 100 CHF      |
    

    
    
    
    
    
    
    
    
    
    
    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: No initial stock, invoice price variance
    When for costing, create completed invoice with one line
      | C_OrderLine_ID | PriceEntered_Override | M_MatchInv_ID |
      | po1_l1         | 13                    | matchInv1     |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID      | IsSOTrx | AmtType    | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1 | N       | MAIN       | 100 CHF | 10 PCE | Y               |
      | M_MatchInv  | matchInv1      | N       | MAIN       | 130 CHF | 10 PCE | N               |
      | M_MatchInv  | matchInv1      | N       | ADJUSTMENT | 30 CHF  | 0 PCE  | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 13 CHF           | 10 PCE     | 130 CHF      |
    

    
    
    
    
    
    
    
    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: No initial stock, receive, ship, get vendor invoice with price variance, ship again
    #
    # Ship 7 items
    #
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | so1_l1         | customer      | 2021-04-10  | SOO         | warehouseStd   | product      | 7          | 19    |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID  |
      | so1_l1         | shipment1_line1 |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID       | IsSOTrx | AmtType | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1  | N       | MAIN    | 100 CHF | 10 PCE | Y               |
      | M_InOutLine | shipment1_line1 | Y       | MAIN    | -70 CHF | -7 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 3 PCE      | 30 CHF       |
    
    #
    # Get the invoice for those 10 we purchase, but with a different price
    #
    When for costing, create completed invoice with one line
      | C_OrderLine_ID | PriceEntered_Override | M_MatchInv_ID |
      | po1_l1         | 13                    | matchInv1     |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID       | IsSOTrx | AmtType         | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1  | N       | MAIN            | 100 CHF | 10 PCE | Y               |
      | M_InOutLine | shipment1_line1 | Y       | MAIN            | -70 CHF | -7 PCE | Y               |
      | M_MatchInv  | matchInv1       | N       | MAIN            | 130 CHF | 10 PCE | N               |
      | M_MatchInv  | matchInv1       | N       | ADJUSTMENT      | 9 CHF   | 0 PCE  | Y               |
      | M_MatchInv  | matchInv1       | N       | ALREADY_SHIPPED | 21 CHF  | 0 PCE  | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 13 CHF           | 3 PCE      | 39 CHF       |
    
    #
    # Ship the remaining 3 items
    #
    When for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price |
      | so2_l1         | customer      | 2021-04-10  | SOO         | warehouseStd   | product      | 3          | 23    |
    And for costing, create completed shipment with one line
      | C_OrderLine_ID | M_InOutLine_ID  |
      | so2_l1         | shipment2_line1 |
    Then after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID       | IsSOTrx | AmtType         | Amt     | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1  | N       | MAIN            | 100 CHF | 10 PCE | Y               |
      | M_InOutLine | shipment1_line1 | Y       | MAIN            | -70 CHF | -7 PCE | Y               |
      | M_MatchInv  | matchInv1       | N       | MAIN            | 130 CHF | 10 PCE | N               |
      | M_MatchInv  | matchInv1       | N       | ADJUSTMENT      | 9 CHF   | 0 PCE  | Y               |
      | M_MatchInv  | matchInv1       | N       | ALREADY_SHIPPED | 21 CHF  | 0 PCE  | Y               |
      | M_InOutLine | shipment2_line1 | Y       | MAIN            | -39 CHF | -3 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 13 CHF           | 0 PCE      | 0 CHF        |
