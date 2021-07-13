-- 2021-01-22T12:18:15.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product (AD_Client_ID,IsActive,Created,CreatedBy,IsSummary,LowLevel,Weight,IsStocked,IsSold,Volume,IsPurchased,C_UOM_ID,Processing,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,IsInvoicePrintDetails,IsPickListPrintDetails,IsWebStoreFeatured,IsSelfService,IsDropShip,IsExcludeAutoDelivery,UnitsPerPack,Updated,UpdatedBy,IsDiverse,IsCategoryProduct,Fresh_CropPlanning,Fresh_WashSampleRequired,NetWeight,IsPharmaProduct,IsPrescription,IsColdChain,IsNarcotic,IsTFG,HaddexCheck,GuaranteeDaysMin,M_Product_ID,SalesRep_ID,ProductType,IsQuotationGroupping,IsCommissioned,IsBOM,IsVerified,AD_Org_ID,Value,M_Product_Category_ID,Discontinued,Name)
VALUES
(1000000,'Y',TO_TIMESTAMP('2021-01-22 14:18:15','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,'N','N',0,'N',101,'N',0,0,0,0,'N','N','N','Y','N','N',0,TO_TIMESTAMP('2021-01-22 14:18:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N',0,'N','N','N','N','N','N',0,540496,100,'R','N','Y','N','N',1000000,'Labor',2001602,'N','Labor')
;
update M_Product set Value='Labor' where M_Product_ID=540496;

-- 2021-01-22T12:18:15.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product_Trl (AD_Language,M_Product_ID, Ingredients,DocumentNote,Description,CustomerLabelName,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.M_Product_ID, t.Ingredients,t.DocumentNote,t.Description,t.CustomerLabelName,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, M_Product t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Product_ID=540496 AND NOT EXISTS (SELECT 1 FROM M_Product_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Product_ID=t.M_Product_ID)
;

-- 2021-01-22T12:18:15.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Product_Acct (M_Product_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,P_Asset_Acct,P_Burden_Acct,P_COGS_Acct,P_CostAdjustment_Acct,P_CostOfProduction_Acct,P_Expense_Acct,P_FloorStock_Acct,P_InventoryClearing_Acct,P_InvoicePriceVariance_Acct,P_Labor_Acct,P_MethodChangeVariance_Acct,P_MixVariance_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_PurchasePriceVariance_Acct,P_RateVariance_Acct,P_Revenue_Acct,P_Scrap_Acct,P_TradeDiscountGrant_Acct,P_TradeDiscountRec_Acct,P_UsageVariance_Acct,P_WIP_Acct) SELECT 540496, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.P_Asset_Acct,p.P_Burden_Acct,p.P_COGS_Acct,p.P_CostAdjustment_Acct,p.P_CostOfProduction_Acct,p.P_Expense_Acct,p.P_FloorStock_Acct,p.P_InventoryClearing_Acct,p.P_InvoicePriceVariance_Acct,p.P_Labor_Acct,p.P_MethodChangeVariance_Acct,p.P_MixVariance_Acct,p.P_OutsideProcessing_Acct,p.P_Overhead_Acct,p.P_PurchasePriceVariance_Acct,p.P_RateVariance_Acct,p.P_Revenue_Acct,p.P_Scrap_Acct,p.P_TradeDiscountGrant_Acct,p.P_TradeDiscountRec_Acct,p.P_UsageVariance_Acct,p.P_WIP_Acct FROM M_Product_Category_Acct p WHERE p.AD_Client_ID=1000000 AND p.M_Product_Category_ID=2001602 AND NOT EXISTS (SELECT 1 FROM M_Product_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.M_Product_ID=540496)
;

-- 2021-01-22T12:18:15.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodePR (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540496, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=1000000 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=208 AND NOT EXISTS (SELECT * FROM AD_TreeNodePR e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540496)
;

-- 2021-01-22T12:18:16.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,C_Currency_ID,C_UOM_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentCostPriceLL,CurrentQty,IsActive,M_AttributeSetInstance_ID,M_Cost_ID,M_CostElement_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,318,101,TO_TIMESTAMP('2021-01-22 14:18:15','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,544368,1000000,1000000,540496,TO_TIMESTAMP('2021-01-22 14:18:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-22T12:18:16.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,C_Currency_ID,C_UOM_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentCostPriceLL,CurrentQty,IsActive,M_AttributeSetInstance_ID,M_Cost_ID,M_CostElement_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,318,101,TO_TIMESTAMP('2021-01-22 14:18:16','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,544369,1000002,1000000,540496,TO_TIMESTAMP('2021-01-22 14:18:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-22T12:21:12.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO S_Resource (AD_Client_ID,AD_Org_ID,ChargeableQty,Created,CreatedBy,DailyCapacity,IsActive,IsAvailable,IsManufacturingResource,ManufacturingResourceType,Name,PercentUtilization,PlanningHorizon,QueuingTime,S_Resource_ID,S_ResourceType_ID,Updated,UpdatedBy,Value,WaitingTime) VALUES (1000000,1000000,0,TO_TIMESTAMP('2021-01-22 14:21:12','YYYY-MM-DD HH24:MI:SS'),100,0,'Y','Y','Y','WC','Labor',100,0,0,540013,1000000,TO_TIMESTAMP('2021-01-22 14:21:12','YYYY-MM-DD HH24:MI:SS'),100,'Labor',0)
;

-- 2021-01-22T12:21:23.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product SET IsStocked='N', S_Resource_ID=540013,Updated=TO_TIMESTAMP('2021-01-22 14:21:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Product_ID=540496
;










































-- 2021-01-22T12:26:22.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow (AD_Client_ID,Created,CreatedBy,IsActive,Author,AccessLevel,PublishStatus,Name,DurationUnit,DurationLimit,Priority,WorkingTime,Duration,WaitingTime,ValidateWorkflow,IsValid,WorkflowType,QueuingTime,SetupTime,MovingTime,QtyBatchSize,IsBetaFunctionality,Cost,UnitsCycles,OverlapUnits,Yield,IsDefault,Updated,UpdatedBy,Version,AD_Workflow_ID,Value,DocumentNo,AD_Org_ID,EntityType) VALUES (1000000,TO_TIMESTAMP('2021-01-22 14:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','metasfresh gmbH','1','U','Default Service/Repair routing','h',0,0,0,0,0,'N','N','R',0,0,0,1,'N',0,0,0,100,'Y',TO_TIMESTAMP('2021-01-22 14:26:22','YYYY-MM-DD HH24:MI:SS'),100,0,540110,'Default Service/Repair routing','Default Service/Repair routing',0,'U')
;

-- 2021-01-22T12:26:22.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Name,Help,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Workflow_ID, t.Name,t.Help,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Workflow_ID=540110 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2021-01-22T12:26:48.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (AD_Workflow_ID,IsActive,Created,CreatedBy,Updated,IsCentrallyMaintained,IsSubcontracting,MovingTime,YPosition,XPosition,Duration,Cost,WaitingTime,WorkingTime,Priority,JoinElement,SplitElement,WaitTime,IsMilestone,Name,DynPriorityChange,S_Resource_ID,SetupTime,QueuingTime,AD_Client_ID,UnitsCycles,OverlapUnits,Yield,UpdatedBy,AD_WF_Node_ID,DurationLimit,Action,DocAction,Value,AD_Org_ID,EntityType) VALUES (540110,'Y',TO_TIMESTAMP('2021-01-22 14:26:48','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-22 14:26:48','YYYY-MM-DD HH24:MI:SS'),'Y','N',0,0,0,0,0,0,0,0,'X','X',0,'N','Labor',0,540013,0,0,1000000,0,0,100,100,540243,0,'Z','CO','Labor',0,'U')
;

-- 2021-01-22T12:26:48.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Name,Help,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Name,t.Help,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540243 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2021-01-22T12:28:06.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Workflow SET AD_WF_Node_ID=540243, IsValid='Y',Updated=TO_TIMESTAMP('2021-01-22 14:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540110
;

