@from:cucumber
@ghActions:run_on_executor6
Feature: Moving Average Invoice - Check costing when reversing a material receipt

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
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_1       | ps_1               | CH           | CHF           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_1      | pl_1           |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp_1       | plv_1                  | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | CH           | Y               | Y               |

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: No initial stock
    #
    # Create material receipt 
    #
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price   | Description                    |
      | po1_l1         | vendor        | 2021-04-16  | POO         | warehouseStd   | product      | 10         | 1000000 | intentionally wrong huge price |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | po1_l1         | receipt1   | receipt1_line1 |
    And after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID      | IsSOTrx | Amt          | Qty    | IsChangingCosts |
      | M_InOutLine | receipt1_line1 | N       | 10000000 CHF | 10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 1000000 CHF      | 10 PCE     | 10000000 CHF |
    
    #
    # Reverse the material receipt 
    #
    And the material receipt identified by receipt1 is reversed as reversal1
    And validate the created material receipt lines
      | M_InOutLine_ID  | M_InOut_ID | M_Product_ID |
      | reversal1_line1 | reversal1  | product      |
    And after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName   | Record_ID       | IsSOTrx | Amt           | Qty     | IsChangingCosts |
      | M_InOutLine | receipt1_line1  | N       | 10000000 CHF  | 10 PCE  | Y               |
      | M_InOutLine | reversal1_line1 | N       | -10000000 CHF | -10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 0 CHF            | 0 PCE      | 0 CHF        |

























# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: With initial stock
    #
    # Create the initial inventory 
    #
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice |
      | inv1           | inv1_l1            | 2021-01-01   | warehouseStd   | product      | 0       | 100      | PCE          | 10        |
    And after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName       | Record_ID | IsSOTrx | Amt      | Qty     | IsChangingCosts |
      | M_InventoryLine | inv1_l1   | N       | 1000 CHF | 100 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 100 PCE    |

    #
    # Create material receipt 
    #
    And for costing, create completed order with one line
      | C_OrderLine_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | M_Product_ID | QtyEntered | Price   | Description                    |
      | po1_l1         | vendor        | 2021-04-16  | POO         | warehouseStd   | product      | 10         | 1000000 | intentionally wrong huge price |
    And for costing, create completed material receipt with one line
      | C_OrderLine_ID | M_InOut_ID | M_InOutLine_ID |
      | po1_l1         | receipt1   | receipt1_line1 |
    And after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName       | Record_ID      | IsSOTrx | Amt          | Qty     | IsChangingCosts |
      | M_InventoryLine | inv1_l1        | N       | 1000 CHF     | 100 PCE | Y               |
      | M_InOutLine     | receipt1_line1 | N       | 10000000 CHF | 10 PCE  | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty | CumulatedAmt |
      | acctSchema      | product      | MovingAverageInvoice | 90918.1818 CHF   | 110 PCE    | 10001000 CHF |
    
    
    #
    # Reverse the material receipt 
    #
    And the material receipt identified by receipt1 is reversed as reversal1
    And validate the created material receipt lines
      | M_InOutLine_ID  | M_InOut_ID | M_Product_ID |
      | reversal1_line1 | reversal1  | product      |
    And after not more than 10s, M_CostDetails are found for product product and cost element MovingAverageInvoice
      | TableName       | Record_ID       | IsSOTrx | Amt           | Qty     | IsChangingCosts |
      | M_InventoryLine | inv1_l1         | N       | 1000 CHF      | 100 PCE | Y               |
      | M_InOutLine     | receipt1_line1  | N       | 10000000 CHF  | 10 PCE  | Y               |
      | M_InOutLine     | reversal1_line1 | N       | -10000000 CHF | -10 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID     | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | MovingAverageInvoice | 10 CHF           | 100 PCE    |
