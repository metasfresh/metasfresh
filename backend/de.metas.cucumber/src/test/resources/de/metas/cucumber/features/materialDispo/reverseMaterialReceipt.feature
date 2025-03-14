@from:cucumber
@ghActions:run_on_executor6
Feature: Reversal of material receipt

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2021-04-14T13:30:13+01:00[Europe/Berlin]
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
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
  Scenario: Check Material Dispo
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        |
      | po1        | N       | vendor        | 2021-04-16  | 1000012          | POO         | ps_1               | 2021-04-15T15:00:00 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po1_l1     | po1        | product      | 10         |
    And the order identified by po1 is completed
    Then after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected       | Qty | ATP |
      | c_1        | SUPPLY            | PURCHASE                  | product      | 2021-04-15T15:00:00 | 10  | 10  |
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs1                             | po1                   | po1_l1                    | vendor                   | vendorLocation                    | product                 | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu1                | rs1                             | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu1                | rs1                             | receipt1              |
    And after not more than 60s, MD_Candidates are found
      | Identifier | MD_Candidate_Type   | MD_Candidate_BusinessCase | M_Product_ID | DateProjected       | Qty | ATP |
      | c_1        | SUPPLY              | PURCHASE                  | product      | 2021-04-15T15:00:00 | 0   | 10  |
      | c_2        | UNEXPECTED_INCREASE | PURCHASE                  | product      | 2021-04-14T00:00:00 | 10  | 10  |
    And the material receipt identified by receipt1 is reversed
    And after not more than 60s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type   | MD_Candidate_BusinessCase | M_Product_ID | DateProjected       | Qty | ATP |
      | c_1        | SUPPLY              | PURCHASE                  | product      | 2021-04-15T15:00:00 | 0   | 0   |
      | c_2        | UNEXPECTED_INCREASE | PURCHASE                  | product      | 2021-04-14T00:00:00 | 10  | 10  |
      | c_3        | UNEXPECTED_DECREASE | PURCHASE                  | product      | 2021-04-14T00:00:00 | 10  | -10 |

























# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Check costing - no initial stock
    #
    # Create material receipt 
    #
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        | M_Warehouse_ID |
      | po1        | N       | vendor        | 2021-04-16  | 1000012          | POO         | ps_1               | 2021-04-15T15:00:00 | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | Price   | Description                    |
      | po1_l1     | po1        | product      | 10         | 1000000 | intentionally wrong huge price |
    And the order identified by po1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs1                             | po1                   | po1_l1                    | vendor                   | vendorLocation                    | product                 | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu1                | rs1                             | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu1                | rs1                             | receipt1              |
    And validate the created material receipt lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | C_OrderLine_ID |
      | receipt1_line1 | receipt1   | product      | po1_l1         |
    And Wait until receipt receipt1 is posted
    And M_MatchPO are found
      | Identifier | C_OrderLine_ID |
      | mpo1       | po1_l1         |
    And Wait until M_MatchPO mpo1 is posted
    And after not more than 10s, M_CostDetails are found for product product and cost element 1000002
      | TableName   | Record_ID      | IsSOTrx | Amt          | Qty    | IsChangingCosts |
      | M_MatchPO   | mpo1           | N       | 10000000 CHF | 10 PCE | Y               |
      | M_InOutLine | receipt1_line1 | N       | 10000000 CHF | 10 PCE | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | 1000002          | 1000000 CHF      | 10 PCE     |
    
    
    #
    # Reverse the material receipt 
    #
    And the material receipt identified by receipt1 is reversed as reversal1
    And validate the created material receipt lines
      | M_InOutLine_ID  | M_InOut_ID | M_Product_ID |
      | reversal1_line1 | reversal1  | product      |
    And Wait until receipt reversal1 are posted
    And after not more than 10s, M_CostDetails are found for product product and cost element 1000002
      | TableName   | Record_ID       | IsSOTrx | Amt           | Qty     | IsChangingCosts |
      | M_InOutLine | receipt1_line1  | N       | 10000000 CHF  | 10 PCE  | N               |
      | M_InOutLine | reversal1_line1 | N       | -10000000 CHF | -10 PCE | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      # FIXME: not sure, but IMHO CurrentCostPrice has to get to zero in this case
      | acctSchema      | product      | 1000002          | 1000000 CHF      | 0 PCE      |

























# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  Scenario: Check costing - with initial stock
    #
    # Create the initial inventory 
    #
    When metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 | CostPrice |
      | inv1           | inv1_l1            | 2021-01-01   | warehouseStd   | product      | 0       | 100      | PCE          | 10        |
    And after not more than 10s, M_CostDetails are found for product product and cost element 1000002
      | TableName       | Record_ID | IsSOTrx | Amt      | Qty     | IsChangingCosts |
      | M_InventoryLine | inv1_l1   | N       | 1000 CHF | 100 PCE | Y               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | 1000002          | 10 CHF           | 100 PCE    |

    #
    # Create material receipt 
    #
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        | M_Warehouse_ID |
      | po1        | N       | vendor        | 2021-04-16  | 1000012          | POO         | ps_1               | 2021-04-15T15:00:00 | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | Price   | Description                    |
      | po1_l1     | po1        | product      | 10         | 1000000 | intentionally wrong huge price |
    And the order identified by po1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs1                             | po1                   | po1_l1                    | vendor                   | vendorLocation                    | product                 | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | hu1                | rs1                             | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu1                | rs1                             | receipt1              |
    And validate the created material receipt lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | C_OrderLine_ID |
      | receipt1_line1 | receipt1   | product      | po1_l1         |
    And Wait until receipt receipt1 is posted
    And M_MatchPO are found
      | Identifier | C_OrderLine_ID |
      | mpo1       | po1_l1         |
    And Wait until M_MatchPO mpo1 is posted
    And after not more than 10s, M_CostDetails are found for product product and cost element 1000002
      | TableName       | Record_ID      | IsSOTrx | Amt          | Qty     | IsChangingCosts |
      | M_InventoryLine | inv1_l1        | N       | 1000 CHF     | 100 PCE | Y               |
      | M_MatchPO       | mpo1           | N       | 10000000 CHF | 10 PCE  | Y               |
      | M_InOutLine     | receipt1_line1 | N       | 10000000 CHF | 10 PCE  | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty | Comment                     |
      | acctSchema      | product      | 1000002          | 90918.1818 CHF   | 110 PCE    | (1_000+10_000_000)/(100+10) |
    
    
    #
    # Reverse the material receipt 
    #
    And the material receipt identified by receipt1 is reversed as reversal1
    And validate the created material receipt lines
      | M_InOutLine_ID  | M_InOut_ID | M_Product_ID |
      | reversal1_line1 | reversal1  | product      |
    And Wait until receipt reversal1 are posted
    And after not more than 10s, M_CostDetails are found for product product and cost element 1000002
      | TableName       | Record_ID       | IsSOTrx | Amt           | Qty     | IsChangingCosts |
      | M_InventoryLine | inv1_l1         | N       | 1000 CHF      | 100 PCE | Y               |
      | M_InOutLine     | receipt1_line1  | N       | 10000000 CHF  | 10 PCE  | N               |
      | M_InOutLine     | reversal1_line1 | N       | -10000000 CHF | -10 PCE | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | 1000002          | 10 CHF           | 100 PCE    |
