DROP FUNCTION IF EXISTS createM_Product_Category_Acct()
;

CREATE FUNCTION createM_Product_Category_Acct()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count numeric;
BEGIN
    INSERT
    INTO M_Product_Category_Acct (M_Product_Category_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,
                                  CreatedBy, Updated, UpdatedBy, P_Asset_Acct, P_Burden_Acct, P_COGS_Acct,
                                  P_CostAdjustment_Acct, P_CostOfProduction_Acct, P_Expense_Acct, P_FloorStock_Acct,
                                  P_InventoryClearing_Acct, P_InvoicePriceVariance_Acct, P_Labor_Acct,
                                  P_MethodChangeVariance_Acct, P_MixVariance_Acct, P_OutsideProcessing_Acct,
                                  P_Overhead_Acct, P_PurchasePriceVariance_Acct, P_RateVariance_Acct, P_Revenue_Acct,
                                  P_Scrap_Acct, P_TradeDiscountGrant_Acct, P_TradeDiscountRec_Acct, P_UsageVariance_Acct,
                                  P_WIP_Acct,
                                  P_ExternallyOwnedStock_Acct)
    SELECT pc.m_product_category_id,
           asd.C_AcctSchema_ID,
           pc.AD_Client_ID,
           pc.AD_Org_ID,
           'Y',
           NOW(),
           99,
           NOW(),
           99,
           asd.P_Asset_Acct,
           asd.P_Burden_Acct,
           asd.P_COGS_Acct,
           asd.P_CostAdjustment_Acct,
           asd.P_CostOfProduction_Acct,
           asd.P_Expense_Acct,
           asd.P_FloorStock_Acct,
           asd.P_InventoryClearing_Acct,
           asd.P_InvoicePriceVariance_Acct,
           asd.P_Labor_Acct,
           asd.P_MethodChangeVariance_Acct,
           asd.P_MixVariance_Acct,
           asd.P_OutsideProcessing_Acct,
           asd.P_Overhead_Acct,
           asd.P_PurchasePriceVariance_Acct,
           asd.P_RateVariance_Acct,
           asd.P_Revenue_Acct,
           asd.P_Scrap_Acct,
           asd.P_TradeDiscountGrant_Acct,
           asd.P_TradeDiscountRec_Acct,
           asd.P_UsageVariance_Acct,
           asd.P_WIP_Acct,
           asd.P_ExternallyOwnedStock_Acct
    FROM M_Product_Category pc
             INNER JOIN C_AcctSchema_Default asd ON asd.AD_Client_ID = pc.AD_Client_ID
    WHERE TRUE
      AND asd.AD_Client_ID = 1000000
      AND NOT EXISTS(SELECT 1 FROM M_Product_Category_Acct e WHERE e.C_AcctSchema_ID = asd.C_AcctSchema_ID AND e.M_Product_Category_ID = pc.m_product_category_id);
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Created % M_Product_Category_Acct records', v_count;
END;
$$
;
