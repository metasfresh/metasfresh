
-- 2019-09-19T19:03:33.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_ID,Discontinued,Fresh_CropPlanning,Fresh_WashSampleRequired,GuaranteeDaysMin,IsActive,IsBOM,IsCategoryProduct,IsColdChain,IsDiverse,IsDropShip,IsExcludeAutoDelivery,IsInvoicePrintDetails,IsNarcotic,IsPharmaProduct,IsPickListPrintDetails,IsPrescription,IsPurchased,IsQuotationGroupping,IsSelfService,IsSold,IsStocked,IsSummary,IsTFG,IsVerified,IsWebStoreFeatured,LowLevel,M_Product_Category_ID,M_Product_ID,Name,Processing,ProductType,SalesRep_ID,ShelfDepth,ShelfHeight,ShelfWidth,UnitsPerPack,UnitsPerPallet,Updated,UpdatedBy,Value,Volume,Weight) VALUES 
(1000000,0,TO_TIMESTAMP('2019-09-19 21:03:33','YYYY-MM-DD HH24:MI:SS'),100,100,'N','N','N',0,'Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N',0,2001603,540420,'Provision','N','S',100,0,0,0,0,0,TO_TIMESTAMP('2019-09-19 21:03:33','YYYY-MM-DD HH24:MI:SS'),100,'COMMISSION',0,0)
;

-- 2019-09-19T19:03:33.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product_Trl (AD_Language,M_Product_ID, CustomerLabelName,Description,DocumentNote,Ingredients,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.M_Product_ID, t.CustomerLabelName,t.Description,t.DocumentNote,t.Ingredients,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, M_Product t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.M_Product_ID=540420 AND NOT EXISTS (SELECT 1 FROM M_Product_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Product_ID=t.M_Product_ID)
;

-- 2019-09-19T19:03:33.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product_Acct (M_Product_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,P_Asset_Acct,P_Burden_Acct,P_COGS_Acct,P_CostAdjustment_Acct,P_CostOfProduction_Acct,P_Expense_Acct,P_FloorStock_Acct,P_InventoryClearing_Acct,P_InvoicePriceVariance_Acct,P_Labor_Acct,P_MethodChangeVariance_Acct,P_MixVariance_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_PurchasePriceVariance_Acct,P_RateVariance_Acct,P_Revenue_Acct,P_Scrap_Acct,P_TradeDiscountGrant_Acct,P_TradeDiscountRec_Acct,P_UsageVariance_Acct,P_WIP_Acct) SELECT 540420, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.P_Asset_Acct,p.P_Burden_Acct,p.P_COGS_Acct,p.P_CostAdjustment_Acct,p.P_CostOfProduction_Acct,p.P_Expense_Acct,p.P_FloorStock_Acct,p.P_InventoryClearing_Acct,p.P_InvoicePriceVariance_Acct,p.P_Labor_Acct,p.P_MethodChangeVariance_Acct,p.P_MixVariance_Acct,p.P_OutsideProcessing_Acct,p.P_Overhead_Acct,p.P_PurchasePriceVariance_Acct,p.P_RateVariance_Acct,p.P_Revenue_Acct,p.P_Scrap_Acct,p.P_TradeDiscountGrant_Acct,p.P_TradeDiscountRec_Acct,p.P_UsageVariance_Acct,p.P_WIP_Acct FROM M_Product_Category_Acct p WHERE p.AD_Client_ID=1000000 AND p.M_Product_Category_ID=2001603 AND NOT EXISTS (SELECT * FROM M_Product_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.M_Product_ID=540420)
;

-- 2019-09-19T19:03:33.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT  INTO AD_TreeNodePR (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540420, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=1000000 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=208 AND NOT EXISTS (SELECT * FROM AD_TreeNodePR e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540420)
--;


-- 2019-09-19T19:03:33.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,C_Currency_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,C_UOM_ID,CurrentCostPrice,CurrentCostPriceLL,CurrentQty,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,318,TO_TIMESTAMP('2019-09-19 21:03:33','YYYY-MM-DD HH24:MI:SS'),100,0,0,100,0,0,0,'Y',0,1000000,544328,1000000,540420,TO_TIMESTAMP('2019-09-19 21:03:33','YYYY-MM-DD HH24:MI:SS'),100)
--;

-- 2019-09-19T19:03:33.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,C_Currency_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,C_UOM_ID,CurrentCostPrice,CurrentCostPriceLL,CurrentQty,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,318,TO_TIMESTAMP('2019-09-19 21:03:33','YYYY-MM-DD HH24:MI:SS'),100,0,0,100,0,0,0,'Y',0,1000002,544329,1000000,540420,TO_TIMESTAMP('2019-09-19 21:03:33','YYYY-MM-DD HH24:MI:SS'),100)
--;

-- 2019-09-19T19:03:49.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product_Trl SET IsTranslated='Y', Name='Commission',Updated=TO_TIMESTAMP('2019-09-19 21:03:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Product_ID=540420
;

