-- 2017-05-22T12:01:12.583
-- URL zum Konzept
INSERT INTO S_Resource (AD_Client_ID,AD_Org_ID,AD_User_ID,ChargeableQty,Created,CreatedBy,DailyCapacity,IsActive,IsAvailable,IsManufacturingResource,ManufacturingResourceType,M_Warehouse_ID,Name,PercentUtilization,PlanningHorizon,QueuingTime,S_Resource_ID,S_ResourceType_ID,Updated,UpdatedBy,Value,WaitingTime) VALUES (1000000,1000000,100,0,TO_TIMESTAMP('2017-05-22 12:01:12','YYYY-MM-DD HH24:MI:SS'),100,0,'Y','Y','Y','PT',540008,'test',100,1,0,540006,1000000,TO_TIMESTAMP('2017-05-22 12:01:12','YYYY-MM-DD HH24:MI:SS'),100,'1000000',0)
;

-- 2017-05-22T12:01:12.786
-- URL zum Konzept
INSERT INTO M_Product (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_ID,IsActive,IsBOM,IsExcludeAutoDelivery,IsInvoicePrintDetails,IsPickListPrintDetails,IsPurchased,IsSelfService,IsSold,IsStocked,IsSummary,IsVerified,IsWebStoreFeatured,LowLevel,M_Product_Category_ID,M_Product_ID,Name,Processing,ProductType,S_Resource_ID,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:12','YYYY-MM-DD HH24:MI:SS'),100,101,'Y','N','N','N','N','Y','Y','Y','N','N','N','N',0,2001602,540026,'test','N','R',540006,TO_TIMESTAMP('2017-05-22 12:01:12','YYYY-MM-DD HH24:MI:SS'),100,'PR1000000')
;

-- 2017-05-22T12:01:12.790
-- URL zum Konzept
INSERT INTO M_Product_Trl (AD_Language,M_Product_ID, Description,DocumentNote,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.M_Product_ID, t.Description,t.DocumentNote,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, M_Product t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.M_Product_ID=540026 AND NOT EXISTS (SELECT * FROM M_Product_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Product_ID=t.M_Product_ID)
;

-- 2017-05-22T12:01:12.800
-- URL zum Konzept
INSERT INTO M_Product_Acct (M_Product_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,P_Asset_Acct,P_Burden_Acct,P_COGS_Acct,P_CostAdjustment_Acct,P_CostOfProduction_Acct,P_Expense_Acct,P_FloorStock_Acct,P_InventoryClearing_Acct,P_InvoicePriceVariance_Acct,P_Labor_Acct,P_MethodChangeVariance_Acct,P_MixVariance_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_PurchasePriceVariance_Acct,P_RateVariance_Acct,P_Revenue_Acct,P_Scrap_Acct,P_TradeDiscountGrant_Acct,P_TradeDiscountRec_Acct,P_UsageVariance_Acct,P_WIP_Acct) SELECT 540026, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.P_Asset_Acct,p.P_Burden_Acct,p.P_COGS_Acct,p.P_CostAdjustment_Acct,p.P_CostOfProduction_Acct,p.P_Expense_Acct,p.P_FloorStock_Acct,p.P_InventoryClearing_Acct,p.P_InvoicePriceVariance_Acct,p.P_Labor_Acct,p.P_MethodChangeVariance_Acct,p.P_MixVariance_Acct,p.P_OutsideProcessing_Acct,p.P_Overhead_Acct,p.P_PurchasePriceVariance_Acct,p.P_RateVariance_Acct,p.P_Revenue_Acct,p.P_Scrap_Acct,p.P_TradeDiscountGrant_Acct,p.P_TradeDiscountRec_Acct,p.P_UsageVariance_Acct,p.P_WIP_Acct FROM M_Product_Category_Acct p WHERE p.AD_Client_ID=1000000 AND p.M_Product_Category_ID=2001602 AND NOT EXISTS (SELECT * FROM M_Product_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.M_Product_ID=540026)
;

-- 2017-05-22T12:01:12.826
-- URL zum Konzept
INSERT INTO M_Product_Costing (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,IsActive,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',540026,TO_TIMESTAMP('2017-05-22 12:01:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:13.016
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:12','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000000,540072,1000000,540026,TO_TIMESTAMP('2017-05-22 12:01:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:13.132
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000002,540073,1000000,540026,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:13.232
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000003,540074,1000000,540026,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:13.324
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000004,540075,1000000,540026,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:13.419
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000005,540076,1000000,540026,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:13.519
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000006,540077,1000000,540026,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:13.627
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000007,540078,1000000,540026,TO_TIMESTAMP('2017-05-22 12:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:13.630
-- URL zum Konzept
INSERT  INTO AD_TreeNodePR (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540026, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=1000000 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=208 AND NOT EXISTS (SELECT * FROM AD_TreeNodePR e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540026)
;

-- 2017-05-22T12:01:18.077
-- URL zum Konzept
UPDATE S_Resource SET Value='test',Updated=TO_TIMESTAMP('2017-05-22 12:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE S_Resource_ID=540006
;

-- 2017-05-22T12:01:18.095
-- URL zum Konzept
UPDATE M_Product SET IsStocked='N', Value='PRtest',Updated=TO_TIMESTAMP('2017-05-22 12:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Product_ID=540026
;


-- 2017-05-22T12:01:55.788
-- URL zum Konzept
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationLimit,DurationUnit,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,MovingTime,Name,OverlapUnits,Priority,PublishStatus,QtyBatchSize,QueuingTime,SetupTime,UnitsCycles,Updated,UpdatedBy,ValidateWorkflow,Value,Version,WaitingTime,WorkflowType,WorkingTime,Yield) VALUES ('1',1000000,0,540075,'mf',0,TO_TIMESTAMP('2017-05-22 12:01:55','YYYY-MM-DD HH24:MI:SS'),100,'1000020',0,0,'D','U','Y','N','N','N',0,'test',0,0,'R',1,0,0,0,TO_TIMESTAMP('2017-05-22 12:01:55','YYYY-MM-DD HH24:MI:SS'),100,'N','test',0,0,'M',0,100)
;

-- 2017-05-22T12:01:55.795
-- URL zum Konzept
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Workflow_ID=540075 AND NOT EXISTS (SELECT * FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2017-05-22T12:01:56.012
-- URL zum Konzept
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,1000000,540001,540075,TO_TIMESTAMP('2017-05-22 12:01:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2017-05-22 12:01:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:56.107
-- URL zum Konzept
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (0,0,0,540002,540075,TO_TIMESTAMP('2017-05-22 12:01:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2017-05-22 12:01:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:01:56.193
-- URL zum Konzept
INSERT INTO AD_Workflow_Access (AD_Client_ID,AD_Org_ID,AD_Role_ID,AD_Workflow_Access_ID,AD_Workflow_ID,Created,CreatedBy,IsActive,IsReadWrite,Updated,UpdatedBy) VALUES (1000000,0,540024,540003,540075,TO_TIMESTAMP('2017-05-22 12:01:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2017-05-22 12:01:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:02:18.160
-- URL zum Konzept
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,DynPriorityChange,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,MovingTime,Name,OverlapUnits,Priority,QueuingTime,SetupTime,SplitElement,S_Resource_ID,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,WaitTime,WorkingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,0,540166,540075,0,TO_TIMESTAMP('2017-05-22 12:02:17','YYYY-MM-DD HH24:MI:SS'),100,'CO',0,0,0,'U','Y','Y','N','N','X',0,'1',0,0,0,0,'X',540006,0,TO_TIMESTAMP('2017-05-22 12:02:17','YYYY-MM-DD HH24:MI:SS'),100,'1',0,0,0,0,100,0)
;

-- 2017-05-22T12:02:18.163
-- URL zum Konzept
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=540166 AND NOT EXISTS (SELECT * FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2017-05-22T12:02:26.452
-- URL zum Konzept
UPDATE AD_Workflow SET AD_Table_ID=NULL, AD_WF_Node_ID=540166, IsValid='Y',Updated=TO_TIMESTAMP('2017-05-22 12:02:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540075
;

-- 2017-05-22T12:05:16.417
-- URL zum Konzept
INSERT INTO M_Warehouse_Routing (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DocBaseType,IsActive,M_Warehouse_ID,M_Warehouse_Routing_ID,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2017-05-22 12:05:16','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y',540008,540021,TO_TIMESTAMP('2017-05-22 12:05:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:05:26.775
-- URL zum Konzept
INSERT INTO M_Warehouse_Routing (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DocBaseType,IsActive,M_Warehouse_ID,M_Warehouse_Routing_ID,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2017-05-22 12:05:26','YYYY-MM-DD HH24:MI:SS'),100,'MOP','Y',540008,540022,TO_TIMESTAMP('2017-05-22 12:05:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-22T12:06:11.705
-- URL zum Konzept
INSERT INTO M_Warehouse (AD_Client_ID,AD_Org_ID,AD_User_ID,C_BPartner_Location_ID,C_Location_ID,Created,CreatedBy,IsActive,IsHUStorageDisabled,IsInTransit,IsIssueWarehouse,IsPickingWarehouse,IsPOWarehouse,IsQualityReturnWarehouse,IsSOWarehouse,M_Warehouse_ID,Name,Separator,Updated,UpdatedBy,Value) VALUES (1000000,1000000,100,2202690,2189861,TO_TIMESTAMP('2017-05-22 12:06:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Y','N','Y',540012,'Leergebindelager','*',TO_TIMESTAMP('2017-05-22 12:06:11','YYYY-MM-DD HH24:MI:SS'),100,'Leergebindelager')
;

-- 2017-05-22T12:06:11.717
-- URL zum Konzept
INSERT INTO M_Warehouse_Acct (M_Warehouse_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,W_Differences_Acct,W_InvActualAdjust_Acct,W_Inventory_Acct,W_Revaluation_Acct) SELECT 540012, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.W_Differences_Acct,p.W_InvActualAdjust_Acct,p.W_Inventory_Acct,p.W_Revaluation_Acct FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000 AND NOT EXISTS (SELECT * FROM M_Warehouse_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.M_Warehouse_ID=540012)
;

-- 2017-05-22T12:06:21.964
-- URL zum Konzept
INSERT INTO M_Locator (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsAfterPickingLocator,IsDefault,M_Locator_ID,M_Warehouse_ID,PriorityNo,Updated,UpdatedBy,Value,X,Y,Z) VALUES (1000000,1000000,TO_TIMESTAMP('2017-05-22 12:06:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y',540013,540012,50,TO_TIMESTAMP('2017-05-22 12:06:21','YYYY-MM-DD HH24:MI:SS'),100,'Leergebindelager','1','1','1')
;

-- 2017-05-22T12:06:38.410
-- URL zum Konzept
INSERT INTO M_Warehouse_Routing (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DocBaseType,IsActive,M_Warehouse_ID,M_Warehouse_Routing_ID,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2017-05-22 12:06:38','YYYY-MM-DD HH24:MI:SS'),100,'MMI','Y',540012,540023,TO_TIMESTAMP('2017-05-22 12:06:38','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2017-05-22T12:07:23.968
-- URL zum Konzept
INSERT INTO DD_NetworkDistribution (AD_Client_ID,AD_Org_ID,CopyFrom,Created,CreatedBy,DD_NetworkDistribution_ID,DocumentNo,IsActive,IsHUDestroyed,Name,Processing,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'N',TO_TIMESTAMP('2017-05-22 12:07:23','YYYY-MM-DD HH24:MI:SS'),100,540011,'1000100','Y','Y','Gebinde','N',TO_TIMESTAMP('2017-05-22 12:07:23','YYYY-MM-DD HH24:MI:SS'),100,'Gebinde')
;

-- 2017-05-22T12:07:45.150
-- URL zum Konzept
INSERT INTO DD_NetworkDistributionLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DD_AllowPush,DD_NetworkDistribution_ID,DD_NetworkDistributionLine_ID,IsActive,IsKeepTargetPlant,M_Shipper_ID,M_Warehouse_ID,M_WarehouseSource_ID,Percent,PriorityNo,TransfertTime,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2017-05-22 12:07:45','YYYY-MM-DD HH24:MI:SS'),100,'N',540011,540080,'Y','N',1000000,540012,540008,100,0,0,TO_TIMESTAMP('2017-05-22 12:07:45','YYYY-MM-DD HH24:MI:SS'),100)
;