drop function IF EXISTS m_product_acct_createIfNeeded(numeric);

create function m_product_acct_createIfNeeded(p_productId numeric)
returns void
language plpgsql
as $$
BEGIN

INSERT INTO M_Product_Acct (M_Product_ID, C_AcctSchema_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, P_Asset_Acct, P_Burden_Acct, P_COGS_Acct, P_CostAdjustment_Acct, P_CostOfProduction_Acct, P_Expense_Acct, P_FloorStock_Acct, P_InventoryClearing_Acct, P_InvoicePriceVariance_Acct, P_Labor_Acct, P_MethodChangeVariance_Acct, P_MixVariance_Acct, P_OutsideProcessing_Acct, P_Overhead_Acct, P_PurchasePriceVariance_Acct, P_RateVariance_Acct, P_Revenue_Acct, P_Scrap_Acct, P_TradeDiscountGrant_Acct, P_TradeDiscountRec_Acct, P_UsageVariance_Acct, P_WIP_Acct)
  SELECT
	p_productId,
	pc.C_AcctSchema_ID,
	pc.AD_Client_ID,
	pc.AD_Org_ID,
	'Y',
	now(),
	pc.CreatedBy,
	now(),
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
	JOIN M_Product_Category_Acct pc on p.M_Product_Category_ID = p.M_Product_Category_ID
  WHERE pc.AD_Client_ID = p.AD_Client_ID
		AND pc.M_Product_Category_ID = p.M_Product_Category_ID
		AND p.M_Product_ID = p_productId
		AND NOT EXISTS(SELECT *
					   FROM M_Product_Acct e
					   WHERE e.C_AcctSchema_ID = pc.C_AcctSchema_ID AND e.M_Product_ID = p_productId);

END;
$$;