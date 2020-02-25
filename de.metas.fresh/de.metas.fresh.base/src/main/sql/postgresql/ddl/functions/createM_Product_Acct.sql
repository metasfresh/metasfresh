drop function IF EXISTS createM_Product_Acct();

create function createM_Product_Acct()
  returns void
language plpgsql
as $$

BEGIN



WITH products AS (
    SELECT p.AD_Client_ID, p.AD_Org_ID, p.m_product_id
    FROM m_product p
    where not exists(
            select 1 from m_product_acct acct where acct.m_product_id = p.m_product_id)
)
INSERT
INTO M_Product_Acct (M_Product_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated,
                     UpdatedBy, P_Asset_Acct, P_Burden_Acct, P_COGS_Acct, P_CostAdjustment_Acct,
                     P_CostOfProduction_Acct, P_Expense_Acct, P_FloorStock_Acct, P_InventoryClearing_Acct,
                     P_InvoicePriceVariance_Acct, P_Labor_Acct, P_MethodChangeVariance_Acct, P_MixVariance_Acct,
                     P_OutsideProcessing_Acct, P_Overhead_Acct, P_PurchasePriceVariance_Acct, P_RateVariance_Acct,
                     P_Revenue_Acct, P_Scrap_Acct, P_TradeDiscountGrant_Acct, P_TradeDiscountRec_Acct,
                     P_UsageVariance_Acct, P_WIP_Acct)
SELECT products.m_product_id,
       p.C_AcctSchema_ID,
       products.AD_Client_ID,
       products.AD_Org_ID,
       'Y',
       now(),
       99,
       now(),
       99,
       p.P_Asset_Acct,
       p.P_Burden_Acct,
       p.P_COGS_Acct,
       p.P_CostAdjustment_Acct,
       p.P_CostOfProduction_Acct,
       p.P_Expense_Acct,
       p.P_FloorStock_Acct,
       p.P_InventoryClearing_Acct,
       p.P_InvoicePriceVariance_Acct,
       p.P_Labor_Acct,
       p.P_MethodChangeVariance_Acct,
       p.P_MixVariance_Acct,
       p.P_OutsideProcessing_Acct,
       p.P_Overhead_Acct,
       p.P_PurchasePriceVariance_Acct,
       p.P_RateVariance_Acct,
       p.P_Revenue_Acct,
       p.P_Scrap_Acct,
       p.P_TradeDiscountGrant_Acct,
       p.P_TradeDiscountRec_Acct,
       p.P_UsageVariance_Acct,
       p.P_WIP_Acct
FROM C_AcctSchema_Default p,
     products
WHERE p.AD_Client_ID = 1000000
  AND NOT EXISTS(SELECT 1
                 FROM M_Product_Acct e
                 WHERE e.C_AcctSchema_ID = p.C_AcctSchema_ID AND e.M_Product_ID = products.m_product_id);

END;
$$;
