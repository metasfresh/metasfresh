@from:cucumber
@allure.label.epic:E0226_Costing
@allure.label.feature:F1530_Recreate_product_costs
@allure.label.feature:F1514_Cost_Type_Moving_Average_Invoice
@Id:S26253_TC9
@ghActions:run_on_executor7
Feature: Recompute Costs - last pre-range cost detail is a weighted-average MatchPO inbound
  ## F1530: Recreate product costs

  Regression guard: when the recompute range (>= start date) contains no cost-changing detail,
  m_costdetail_delete_from_date rolls the LAST cost detail before the start date forward to
  reconstruct the current cost as-of the start date. If that last detail is a NON-inventory,
  cost-changing inbound (an M_MatchPO here), the function used to abort with
  `RAISE EXCEPTION 'Extracting current costs from an inbound transaction is not implemented'`
  (only inventory-line inbounds were handled). It must instead reconstruct the current cost price
  via weighted average (mirroring CurrentCost.addWeightedAverage), NOT leave it at the pre-inbound
  price.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2024-02-10T09:00:00+01:00[Europe/Berlin]
    And load and update C_AcctSchema:
      | C_AcctSchema_ID | Name                  |
      | acctSchema      | metas fresh UN/34 CHF |
    And cost elements for material costing methods AveragePO are active
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

  Scenario: Recompute reconstructs current cost from a weighted-average MatchPO inbound dated before the start date
    #
    # First purchase: 10 PCE @ 20 CHF (dated 2024-02-10).
    # The M_MatchPO cost detail is cost-changing and drives the average to 20 CHF / 10 PCE.
    #
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        | M_Warehouse_ID |
      | po1        | N       | vendor        | 2024-02-10  | 1000012          | POO         | ps_1               | 2024-02-10T15:00:00 | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | Price | Description   |
      | po1_l1     | po1        | product      | 10         | 20    | first receipt |
    And the order identified by po1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs1                             | po1                   | po1_l1                    | vendor                   | vendorLocation                    | product                 | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig1                         | hu1                | rs1                             | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
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
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | AveragePO        | 20 CHF           | 10 PCE     |

    #
    # Second purchase: 10 PCE @ 40 CHF (dated 2024-02-20).
    # Weighted average: (20*10 + 40*10) / 20 = 30 CHF / 20 PCE.
    # This M_MatchPO is the LAST cost-changing detail; it is dated BEFORE the recompute start date.
    #
    And metasfresh has date and time 2024-02-20T09:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        | M_Warehouse_ID |
      | po2        | N       | vendor        | 2024-02-20  | 1000012          | POO         | ps_1               | 2024-02-20T15:00:00 | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | Price | Description    |
      | po2_l1     | po2        | product      | 10         | 40    | second receipt |
    And the order identified by po2 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs2                             | po2                   | po2_l1                    | vendor                   | vendorLocation                    | product                 | 10         | warehouseStd              |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig2                         | hu2                | rs2                             | N               | 1     | N               | 1     | N               | 10          | 101                                | 1000006                      |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu2                | rs2                             | receipt2              |
    And validate the created material receipt lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | C_OrderLine_ID |
      | receipt2_line1 | receipt2   | product      | po2_l1         |
    And Wait until receipt receipt2 is posted
    And M_MatchPO are found
      | Identifier | C_OrderLine_ID |
      | mpo2       | po2_l1         |
    And Wait until M_MatchPO mpo2 is posted
    # Each purchase receipt books TWO AveragePO cost details: the cost-changing M_MatchPO (which drives
    # the average) and a non-cost-changing M_InOutLine for the same amount and qty. Both are listed
    # because this step asserts the COMPLETE set of cost details for the product and cost element.
    And after not more than 10s, M_CostDetails are found for product product and cost element AveragePO
      | TableName   | Record_ID      | IsSOTrx | Amt     | Qty    | IsChangingCosts |
      | M_MatchPO   | mpo1           | N       | 200 CHF | 10 PCE | Y               |
      | M_InOutLine | receipt1_line1 | N       | 200 CHF | 10 PCE | N               |
      | M_MatchPO   | mpo2           | N       | 400 CHF | 10 PCE | Y               |
      | M_InOutLine | receipt2_line1 | N       | 400 CHF | 10 PCE | N               |
    And validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | AveragePO        | 30 CHF           | 20 PCE     |

    #
    # A cost-recount inventory dated 2024-03-01 with zero difference (QtyBook = QtyCount, no explicit
    # cost price) — it creates NO cost detail, so the recompute range (>= 2024-03-01) holds no
    # cost-changing detail. This forces m_costdetail_delete_from_date down the Approach-2 branch that
    # rolls the last pre-range detail (mpo2, a weighted-average MatchPO inbound) forward.
    #
    And metasfresh has date and time 2024-03-01T09:00:00+01:00[Europe/Berlin]
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_trigger    | inv_trigger_l1     | 2024-03-01   | warehouseStd   | product      | 0       | 0        | PCE          |

    #
    # Run the recompute from 2024-03-01. Before the fix this aborts with
    # "Extracting current costs from an inbound transaction is not implemented".
    # After the fix, the current cost is reconstructed by weighted average = 30 CHF / 20 PCE
    # (NOT the pre-inbound 20 CHF that a naive copy of the inventory-line branch would leave).
    #
    When invoke M_Inventory_RecomputeCosts:
      | M_Inventory_ID | C_AcctSchema_ID | CostingMethod |
      | inv_trigger    | acctSchema      | A             |
    Then validate current costs
      | C_AcctSchema_ID | M_Product_ID | M_CostElement_ID | CurrentCostPrice | CurrentQty |
      | acctSchema      | product      | AveragePO        | 30 CHF           | 20 PCE     |
