DROP FUNCTION IF EXISTS createM_Product_Acct()
;

CREATE FUNCTION createM_Product_Acct()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count numeric;
BEGIN
    PERFORM createM_Product_Category_Acct();

    INSERT
    INTO M_Product_Acct (M_Product_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated,
                         UpdatedBy, P_Asset_Acct, P_Burden_Acct, P_COGS_Acct, P_CostAdjustment_Acct,
                         P_CostOfProduction_Acct, P_Expense_Acct, P_FloorStock_Acct, P_InventoryClearing_Acct,
                         P_InvoicePriceVariance_Acct, P_Labor_Acct, P_MethodChangeVariance_Acct, P_MixVariance_Acct,
                         P_OutsideProcessing_Acct, P_Overhead_Acct, P_PurchasePriceVariance_Acct, P_RateVariance_Acct,
                         P_Revenue_Acct, P_Scrap_Acct, P_TradeDiscountGrant_Acct, P_TradeDiscountRec_Acct,
                         P_UsageVariance_Acct, P_WIP_Acct,
                         P_ExternallyOwnedStock_Acct)
    SELECT p.m_product_id,
           pca.C_AcctSchema_ID,
           p.AD_Client_ID,
           p.AD_Org_ID,
           'Y',
           NOW(),
           99,
           NOW(),
           99,
           pca.P_Asset_Acct,
           pca.P_Burden_Acct,
           pca.P_COGS_Acct,
           pca.P_CostAdjustment_Acct,
           pca.P_CostOfProduction_Acct,
           pca.P_Expense_Acct,
           pca.P_FloorStock_Acct,
           pca.P_InventoryClearing_Acct,
           pca.P_InvoicePriceVariance_Acct,
           pca.P_Labor_Acct,
           pca.P_MethodChangeVariance_Acct,
           pca.P_MixVariance_Acct,
           pca.P_OutsideProcessing_Acct,
           pca.P_Overhead_Acct,
           pca.P_PurchasePriceVariance_Acct,
           pca.P_RateVariance_Acct,
           pca.P_Revenue_Acct,
           pca.P_Scrap_Acct,
           pca.P_TradeDiscountGrant_Acct,
           pca.P_TradeDiscountRec_Acct,
           pca.P_UsageVariance_Acct,
           pca.P_WIP_Acct,
           pca.P_ExternallyOwnedStock_Acct
    FROM M_Product p
             LEFT OUTER JOIN M_Product_Category_Acct pca ON pca.M_Product_Category_ID = p.M_Product_Category_ID
    WHERE TRUE
      AND p.AD_Client_ID = 1000000
      AND NOT EXISTS(SELECT 1 FROM M_Product_Acct e WHERE e.C_AcctSchema_ID = pca.C_AcctSchema_ID AND e.M_Product_ID = p.m_product_id);
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Created % M_Product_Acct records', v_count;
END;
$$
;
