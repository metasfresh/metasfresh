-- function for creating default M_Product_Category_Acct
drop function IF EXISTS createM_Product_Category_Acct();

create function createM_Product_Category_Acct()
  returns void
language plpgsql
as $$

BEGIN


WITH categs AS (
    SELECT pc.AD_Client_ID, pc.AD_Org_ID, pc.m_product_category_id
    FROM m_product_category pc
    where not exists(
            select 1 from m_product_category_acct acct where acct.m_product_category_id = pc.m_product_category_id)
)
INSERT
INTO M_Product_Category_Acct (M_Product_Category_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,
                              CreatedBy, Updated, UpdatedBy, P_Asset_Acct, P_Burden_Acct, P_COGS_Acct,
                              P_CostAdjustment_Acct, P_CostOfProduction_Acct, P_Expense_Acct, P_FloorStock_Acct,
                              P_InventoryClearing_Acct, P_InvoicePriceVariance_Acct, P_Labor_Acct,
                              P_MethodChangeVariance_Acct, P_MixVariance_Acct, P_OutsideProcessing_Acct,
                              P_Overhead_Acct, P_PurchasePriceVariance_Acct, P_RateVariance_Acct, P_Revenue_Acct,
                              P_Scrap_Acct, P_TradeDiscountGrant_Acct, P_TradeDiscountRec_Acct, P_UsageVariance_Acct,
                              P_WIP_Acct)
SELECT categs.m_product_category_id,
       p.C_AcctSchema_ID,
       categs.AD_Client_ID,
       categs.AD_Org_ID,
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
     categs
WHERE p.AD_Client_ID = 1000000
  AND NOT EXISTS(SELECT 1
                 FROM M_Product_Category_Acct e
                 WHERE e.C_AcctSchema_ID = p.C_AcctSchema_ID
                   AND e.M_Product_Category_ID = categs.m_product_category_id);

END;
$$;



-- function for creating default M_Product_Acct
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


-- function for creating default C_BP_Group_Acct
drop function IF EXISTS createC_BP_Group_Acct();

create function createC_BP_Group_Acct()
    returns void
    language plpgsql
as
$$

BEGIN

    WITH groups AS (
        SELECT bpg.AD_Client_ID, bpg.AD_Org_ID, bpg.C_BP_Group_id
        FROM C_BP_Group bpg
        where not exists(
                select 1 from C_BP_Group_Acct acct where acct.C_BP_Group_id = bpg.C_BP_Group_id)
    )
    INSERT
    INTO C_BP_Group_Acct (C_BP_Group_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,
                          Updated, UpdatedBy, C_Prepayment_Acct, C_Receivable_Acct, C_Receivable_Services_Acct,
                          NotInvoicedReceipts_Acct, NotInvoicedReceivables_Acct, NotInvoicedRevenue_Acct,
                          PayDiscount_Exp_Acct, PayDiscount_Rev_Acct, UnEarnedRevenue_Acct, V_Liability_Acct,
                          V_Liability_Services_Acct, V_Prepayment_Acct, WriteOff_Acct)
    SELECT groups.C_BP_Group_ID,
           p.C_AcctSchema_ID,
           groups.AD_Client_ID,
           groups.AD_Org_ID,
           'Y',
           now(),
           99,
           now(),
           99,
           p.C_Prepayment_Acct,
           p.C_Receivable_Acct,
           p.C_Receivable_Services_Acct,
           p.NotInvoicedReceipts_Acct,
           p.NotInvoicedReceivables_Acct,
           p.NotInvoicedRevenue_Acct,
           p.PayDiscount_Exp_Acct,
           p.PayDiscount_Rev_Acct,
           p.UnEarnedRevenue_Acct,
           p.V_Liability_Acct,
           p.V_Liability_Services_Acct,
           p.V_Prepayment_Acct,
           p.WriteOff_Acct
    FROM C_AcctSchema_Default p,
         groups
    WHERE p.AD_Client_ID = 1000000
      AND NOT EXISTS(SELECT 1
                     FROM C_BP_Group_Acct e
                     WHERE e.C_AcctSchema_ID = p.C_AcctSchema_ID
                       AND e.C_BP_Group_ID = groups.C_BP_Group_ID);

END;
$$;
