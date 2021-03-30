-- 2021-03-09T18:03:51.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product_Category (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsDefault,IsPackagingMaterial,IsSelfService,IsSummary,isTradingUnit,MMPolicy,M_Product_Category_ID,Name,PlannedMargin,Updated,UpdatedBy,Value) VALUES (1000000,0,TO_TIMESTAMP('2021-03-09 20:03:51','YYYY-MM-DD HH24:MI:SS'),2188223,'Y','N','N','Y','N','N','F',540084,'Membership',0,TO_TIMESTAMP('2021-03-09 20:03:51','YYYY-MM-DD HH24:MI:SS'),2188223,'Membership')
;

-- 2021-03-09T18:03:51.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product_Category_Trl (AD_Language,M_Product_Category_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.M_Product_Category_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, M_Product_Category t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Product_Category_ID=540084 AND NOT EXISTS (SELECT 1 FROM M_Product_Category_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Product_Category_ID=t.M_Product_Category_ID)
;

-- 2021-03-09T18:03:51.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product_Category_Acct (M_Product_Category_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,P_Asset_Acct,P_Burden_Acct,P_COGS_Acct,P_CostAdjustment_Acct,P_CostOfProduction_Acct,P_Expense_Acct,P_FloorStock_Acct,P_InventoryClearing_Acct,P_InvoicePriceVariance_Acct,P_Labor_Acct,P_MethodChangeVariance_Acct,P_MixVariance_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_PurchasePriceVariance_Acct,P_RateVariance_Acct,P_Revenue_Acct,P_Scrap_Acct,P_TradeDiscountGrant_Acct,P_TradeDiscountRec_Acct,P_UsageVariance_Acct,P_WIP_Acct) SELECT 540084, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),2188223,now(),2188223,p.P_Asset_Acct,p.P_Burden_Acct,p.P_COGS_Acct,p.P_CostAdjustment_Acct,p.P_CostOfProduction_Acct,p.P_Expense_Acct,p.P_FloorStock_Acct,p.P_InventoryClearing_Acct,p.P_InvoicePriceVariance_Acct,p.P_Labor_Acct,p.P_MethodChangeVariance_Acct,p.P_MixVariance_Acct,p.P_OutsideProcessing_Acct,p.P_Overhead_Acct,p.P_PurchasePriceVariance_Acct,p.P_RateVariance_Acct,p.P_Revenue_Acct,p.P_Scrap_Acct,p.P_TradeDiscountGrant_Acct,p.P_TradeDiscountRec_Acct,p.P_UsageVariance_Acct,p.P_WIP_Acct FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000 AND NOT EXISTS (SELECT 1 FROM M_Product_Category_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.M_Product_Category_ID=540084)
;

-- 2021-03-09T18:03:51.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT  INTO AD_TreeNode (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 2188223, now(), 2188223,t.AD_Tree_ID, 540084, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=1000000 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=209 AND NOT EXISTS (SELECT * FROM AD_TreeNode e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540084)
--;



