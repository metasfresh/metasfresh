DROP FUNCTION IF EXISTS updateM_Product_Acct(p_M_Product_ID)
;

CREATE FUNCTION updateM_Product_Acct(p_M_Product_ID numeric)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_M_Product_Category_ID		NUMERIC := -1;
BEGIN

    SELECT M_Product_Category_ID
    INTO v_M_Product_Category_ID
    FROM  M_Product
    WHERE  M_Product_id = p_M_Product_ID;

    WITH accounts AS (SELECT c_acctschema_id,
                             m_product_category_id,
                             P_Revenue_Acct,
                             P_Expense_Acct,
                             P_CostAdjustment_Acct,
                             P_InventoryClearing_Acct,
                             P_Asset_Acct,
                             P_COGS_Acct,
                             P_PurchasePriceVariance_Acct,
                             P_InvoicePriceVariance_Acct,
                             P_TradeDiscountRec_Acct,
                             P_TradeDiscountGrant_Acct,
                             P_WIP_Acct,
                             P_FloorStock_Acct,
                             P_MethodChangeVariance_Acct,
                             P_UsageVariance_Acct,
                             P_RateVariance_Acct,
                             P_MixVariance_Acct,
                             P_Labor_Acct,
                             P_Burden_Acct,
                             P_CostOfProduction_Acct,
                             P_OutsideProcessing_Acct,
                             P_Overhead_Acct,
                             P_Scrap_Acct
                      FROM M_Product_Category_Acct
                      WHERE M_Product_Category_ID = v_M_Product_Category_ID)
    UPDATE M_Product_Acct
    SET P_Revenue_Acct               = accounts.P_Revenue_Acct,
        P_Expense_Acct               = accounts.P_Expense_Acct,
        P_CostAdjustment_Acct        = accounts.P_CostAdjustment_Acct,
        P_InventoryClearing_Acct     = accounts.P_InventoryClearing_Acct,
        P_Asset_Acct                 = accounts.P_Asset_Acct,
        P_COGS_Acct                  = accounts.P_COGS_Acct,
        P_PurchasePriceVariance_Acct = accounts.P_PurchasePriceVariance_Acct,
        P_InvoicePriceVariance_Acct  = accounts.P_InvoicePriceVariance_Acct,
        P_TradeDiscountRec_Acct      = accounts.P_TradeDiscountRec_Acct,
        P_TradeDiscountGrant_Acct    = accounts.P_TradeDiscountGrant_Acct,
        P_WIP_Acct                   = accounts.P_WIP_Acct,
        P_FloorStock_Acct            = accounts.P_FloorStock_Acct,
        P_MethodChangeVariance_Acct  = accounts.P_MethodChangeVariance_Acct,
        P_UsageVariance_Acct         = accounts.P_UsageVariance_Acct,
        P_RateVariance_Acct          = accounts.P_RateVariance_Acct,
        P_MixVariance_Acct           = accounts.P_MixVariance_Acct,
        P_Labor_Acct                 = accounts.P_Labor_Acct,
        P_Burden_Acct                = accounts.P_Burden_Acct,
        P_CostOfProduction_Acct      = accounts.P_CostOfProduction_Acct,
        P_OutsideProcessing_Acct     = accounts.P_OutsideProcessing_Acct,
        P_Overhead_Acct              = accounts.P_Overhead_Acct,
        P_Scrap_Acct                 = accounts.P_Scrap_Acct
    FROM accounts
    WHERE M_Product_Acct.m_product_id = p_M_Product_ID
      AND M_Product_Acct.c_acctschema_id = accounts.c_acctschema_id;

END;
$$
;
