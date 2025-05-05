DROP FUNCTION IF EXISTS m_product_acct_createIfNeeded(numeric)
;

CREATE FUNCTION m_product_acct_createIfNeeded(p_productId numeric = NULL)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count integer;
BEGIN
    INSERT INTO M_Product_Acct (M_Product_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, P_Asset_Acct, P_Burden_Acct, P_COGS_Acct, P_CostAdjustment_Acct, P_CostOfProduction_Acct, P_Expense_Acct, P_FloorStock_Acct, P_InventoryClearing_Acct, P_InvoicePriceVariance_Acct, P_Labor_Acct, P_MethodChangeVariance_Acct, P_MixVariance_Acct,
                                P_OutsideProcessing_Acct, P_Overhead_Acct, P_PurchasePriceVariance_Acct, P_RateVariance_Acct, P_Revenue_Acct, P_Scrap_Acct, P_TradeDiscountGrant_Acct, P_TradeDiscountRec_Acct, P_UsageVariance_Acct, P_WIP_Acct)
    SELECT p.M_Product_ID,
           pc.C_AcctSchema_ID,
           pc.AD_Client_ID,
           pc.AD_Org_ID,
           'Y',
           NOW(),
           pc.CreatedBy,
           NOW(),
           pc.UpdatedBy,
           pc.P_Asset_Acct,
           pc.P_Burden_Acct,
           pc.P_COGS_Acct,
           pc.P_CostAdjustment_Acct,
           pc.P_CostOfProduction_Acct,
           pc.P_Expense_Acct,
           pc.P_FloorStock_Acct,
           pc.P_InventoryClearing_Acct,
           pc.P_InvoicePriceVariance_Acct,
           pc.P_Labor_Acct,
           pc.P_MethodChangeVariance_Acct,
           pc.P_MixVariance_Acct,
           pc.P_OutsideProcessing_Acct,
           pc.P_Overhead_Acct,
           pc.P_PurchasePriceVariance_Acct,
           pc.P_RateVariance_Acct,
           pc.P_Revenue_Acct,
           pc.P_Scrap_Acct,
           pc.P_TradeDiscountGrant_Acct,
           pc.P_TradeDiscountRec_Acct,
           pc.P_UsageVariance_Acct,
           pc.P_WIP_Acct
    FROM M_Product p
             JOIN M_Product_Category_Acct pc ON pc.M_Product_Category_ID = p.M_Product_Category_ID
    WHERE pc.AD_Client_ID = p.AD_Client_ID
      AND pc.M_Product_Category_ID = p.M_Product_Category_ID
      AND (COALESCE(p_productId, 0) <= 0 OR p.M_Product_ID = p_productId)
      AND NOT EXISTS(SELECT *
                     FROM M_Product_Acct e
                     WHERE e.C_AcctSchema_ID = pc.C_AcctSchema_ID
                       AND e.M_Product_ID = p.M_Product_ID);
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Created % M_Product_Acct records for M_Product_ID=%', v_count, p_productId;
END;
$$
;


