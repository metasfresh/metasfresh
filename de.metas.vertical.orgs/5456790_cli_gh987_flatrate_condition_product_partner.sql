-- 18.02.2017 13:38
-- URL zum Konzept
INSERT INTO C_Flatrate_Conditions (AD_Client_ID,AD_Org_ID,C_Flatrate_Conditions_ID,ClearingAmtBaseOn,Created,CreatedBy,C_UOM_ID,DocAction,DocStatus,InvoiceRule,IsActive,IsClosingWithActualSum,IsClosingWithCorrectionSum,IsCorrectionAmtAtClosing,IsCreateNoInvoice,IsFreeOfCharge,IsManualPrice,IsNewTermCreatesOrder,IsSimulation,Margin_Max,Margin_Min,Name,Processed,Processing,Type_Clearing,Type_Conditions,Type_Flatrate,Updated,UpdatedBy) VALUES (1000000,1000000,540003,'FlatrateAmount',TO_TIMESTAMP('2017-02-18 13:38:49','YYYY-MM-DD HH24:MI:SS'),100,100,'CO','DR','I','Y','N','N','N','N','N','N','Y','N',0,0,'Mitgliedschaft 1 Kalenderjahr','N','N','EX','Subscr','NONE',TO_TIMESTAMP('2017-02-18 13:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:40
-- URL zum Konzept
INSERT INTO C_Flatrate_Transition (AD_Client_ID,AD_Org_ID,C_Calendar_Contract_ID,C_Flatrate_Transition_ID,Created,CreatedBy,DeliveryInterval,DeliveryIntervalUnit,DocAction,DocStatus,EndsWithCalendarYear,IsActive,IsAutoCompleteNewTerm,IsAutoRenew,IsNotifyUserInCharge,Name,Processed,Processing,TermDuration,TermDurationUnit,TermOfNotice,TermOfNoticeUnit,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,540002,TO_TIMESTAMP('2017-02-18 13:40:52','YYYY-MM-DD HH24:MI:SS'),100,1,'year','CO','DR','Y','Y','N','N','N','1 Jahr, autom. Verlängerung, Lieferung','N','N',1,'year',0,'day',TO_TIMESTAMP('2017-02-18 13:40:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:40
-- URL zum Konzept
UPDATE C_Flatrate_Transition SET DocStatus='IP',Updated=TO_TIMESTAMP('2017-02-18 13:40:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Transition_ID=540002
;

-- 18.02.2017 13:40
-- URL zum Konzept
UPDATE C_Flatrate_Transition SET DocAction='RE', DocStatus='CO', Processed='Y',Updated=TO_TIMESTAMP('2017-02-18 13:40:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Transition_ID=540002
;

-- 18.02.2017 13:41
-- URL zum Konzept
UPDATE C_Flatrate_Conditions SET C_Flatrate_Transition_ID=540002,Updated=TO_TIMESTAMP('2017-02-18 13:41:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Conditions_ID=540003
;


-- 18.02.2017 13:41
-- URL zum Konzept
UPDATE C_Flatrate_Conditions SET DocStatus='IP',Updated=TO_TIMESTAMP('2017-02-18 13:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Conditions_ID=540003
;

-- 18.02.2017 13:41
-- URL zum Konzept
UPDATE C_Flatrate_Conditions SET DocAction='RE', DocStatus='CO', Processed='Y',Updated=TO_TIMESTAMP('2017-02-18 13:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Flatrate_Conditions_ID=540003
;



-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Product (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_ID,Discontinued,Fresh_CropPlanning,Fresh_WashSampleRequired,IsActive,IsBOM,IsCategoryProduct,IsDiverse,IsDropShip,IsExcludeAutoDelivery,IsInvoicePrintDetails,IsPickListPrintDetails,IsPurchased,IsSelfService,IsSold,IsStocked,IsSummary,IsVerified,IsWebStoreFeatured,LowLevel,M_Product_Category_ID,M_Product_ID,Name,Processing,ProductType,SalesRep_ID,ShelfDepth,ShelfHeight,ShelfWidth,UnitsPerPack,UnitsPerPallet,Updated,UpdatedBy,Value,Volume,Weight) VALUES (1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,100,'N','N','N','Y','N','N','N','N','N','N','N','Y','Y','Y','N','N','N','N',0,1000000,540022,'Mitgliedschaft','N','I',100,0,0,0,0,0,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,'m01',0,0)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Product_Trl (AD_Language,M_Product_ID, Description,DocumentNote,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.M_Product_ID, t.Description,t.DocumentNote,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, M_Product t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.M_Product_ID=540022 AND NOT EXISTS (SELECT * FROM M_Product_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Product_ID=t.M_Product_ID)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Product_Acct (M_Product_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,P_Asset_Acct,P_Burden_Acct,P_COGS_Acct,P_CostAdjustment_Acct,P_CostOfProduction_Acct,P_Expense_Acct,P_FloorStock_Acct,P_InventoryClearing_Acct,P_InvoicePriceVariance_Acct,P_Labor_Acct,P_MethodChangeVariance_Acct,P_MixVariance_Acct,P_OutsideProcessing_Acct,P_Overhead_Acct,P_PurchasePriceVariance_Acct,P_RateVariance_Acct,P_Revenue_Acct,P_Scrap_Acct,P_TradeDiscountGrant_Acct,P_TradeDiscountRec_Acct,P_UsageVariance_Acct,P_WIP_Acct) SELECT 540022, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.P_Asset_Acct,p.P_Burden_Acct,p.P_COGS_Acct,p.P_CostAdjustment_Acct,p.P_CostOfProduction_Acct,p.P_Expense_Acct,p.P_FloorStock_Acct,p.P_InventoryClearing_Acct,p.P_InvoicePriceVariance_Acct,p.P_Labor_Acct,p.P_MethodChangeVariance_Acct,p.P_MixVariance_Acct,p.P_OutsideProcessing_Acct,p.P_Overhead_Acct,p.P_PurchasePriceVariance_Acct,p.P_RateVariance_Acct,p.P_Revenue_Acct,p.P_Scrap_Acct,p.P_TradeDiscountGrant_Acct,p.P_TradeDiscountRec_Acct,p.P_UsageVariance_Acct,p.P_WIP_Acct FROM M_Product_Category_Acct p WHERE p.AD_Client_ID=1000000 AND p.M_Product_Category_ID=1000000 AND NOT EXISTS (SELECT * FROM M_Product_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.M_Product_ID=540022)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Product_Costing (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,IsActive,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',540022,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000000,540002,1000000,540022,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000002,540003,1000000,540022,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000003,540004,1000000,540022,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000004,540005,1000000,540022,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000005,540006,1000000,540022,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000006,540007,1000000,540022,TO_TIMESTAMP('2017-02-18 13:51:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT INTO M_Cost (AD_Client_ID,AD_Org_ID,C_AcctSchema_ID,Created,CreatedBy,CumulatedAmt,CumulatedQty,CurrentCostPrice,CurrentQty,FutureCostPrice,IsActive,M_AttributeSetInstance_ID,M_CostElement_ID,M_Cost_ID,M_CostType_ID,M_Product_ID,Updated,UpdatedBy) VALUES (1000000,1000000,1000000,TO_TIMESTAMP('2017-02-18 13:51:24','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0,0,'Y',0,1000007,540008,1000000,540022,TO_TIMESTAMP('2017-02-18 13:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:51
-- URL zum Konzept
INSERT  INTO AD_TreeNodePR (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540022, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=1000000 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=208 AND NOT EXISTS (SELECT * FROM AD_TreeNodePR e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540022)
;

-- 18.02.2017 13:53
-- URL zum Konzept
INSERT INTO M_PricingSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsSubscriptionOnly,M_PricingSystem_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2017-02-18 13:53:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540004,'Mitgliedschaften',TO_TIMESTAMP('2017-02-18 13:53:12','YYYY-MM-DD HH24:MI:SS'),100,'Mitgliedschaften')
;

-- 18.02.2017 13:53
-- URL zum Konzept
INSERT INTO M_PriceList (AD_Client_ID,AD_Org_ID,C_Currency_ID,Created,CreatedBy,EnforcePriceLimit,IsActive,IsDefault,IsMandatory,IsPresentForProduct,IsSOPriceList,IsTaxIncluded,M_PriceList_ID,M_PricingSystem_ID,Name,PricePrecision,Updated,UpdatedBy) VALUES (1000000,1000000,318,TO_TIMESTAMP('2017-02-18 13:53:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N','N','N','Y','N',540006,540004,'Mitgliedschaften',2,TO_TIMESTAMP('2017-02-18 13:53:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:53
-- URL zum Konzept
INSERT INTO M_PriceList_Version (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_PriceList_ID,M_PriceList_Version_ID,Name,ProcCreate,Processed,Updated,UpdatedBy,ValidFrom) VALUES (1000000,1000000,TO_TIMESTAMP('2017-02-18 13:53:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',540006,540006,'Mitgliedschaften 2017','N','N',TO_TIMESTAMP('2017-02-18 13:53:56','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2017-01-01','YYYY-MM-DD'))
;

-- 18.02.2017 13:54
-- URL zum Konzept
INSERT INTO M_ProductPrice (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_TaxCategory_ID,C_UOM_ID,IsActive,IsAttributeDependant,IsSeasonFixedPrice,M_PriceList_Version_ID,M_Product_ID,M_ProductPrice_ID,PriceLimit,PriceList,PriceStd,SeqNo,Updated,UpdatedBy,UseScalePrice) VALUES (1000000,1000000,TO_TIMESTAMP('2017-02-18 13:54:22','YYYY-MM-DD HH24:MI:SS'),100,1000003,100,'Y','N','N',540006,540022,540027,0,0,50.000000000000,10,TO_TIMESTAMP('2017-02-18 13:54:22','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT INTO C_BP_Group (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,C_BP_Group_ID,Created,CreatedBy,CreditWatchPercent,IsActive,IsConfidentialInfo,IsCustomer,IsDefault,IsPrintTax,IsPrintTaxSales,M_FreightCost_ID,M_PriceList_ID,M_PricingSystem_ID,Name,PO_PricingSystem_ID,PriceMatchTolerance,PriorityBase,Updated,UpdatedBy,Value) VALUES (1000000,1000000,100,540002,TO_TIMESTAMP('2017-02-18 13:55:11','YYYY-MM-DD HH24:MI:SS'),100,0,'Y','N','N','N','Y','Y',1000000,2001003,540004,'Mitglied',540004,0,'S',TO_TIMESTAMP('2017-02-18 13:55:11','YYYY-MM-DD HH24:MI:SS'),100,'Mitglied')
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT INTO C_BP_Group_Acct (C_BP_Group_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,C_Prepayment_Acct,C_Receivable_Acct,C_Receivable_Services_Acct,NotInvoicedReceipts_Acct,NotInvoicedReceivables_Acct,NotInvoicedRevenue_Acct,PayDiscount_Exp_Acct,PayDiscount_Rev_Acct,UnEarnedRevenue_Acct,V_Liability_Acct,V_Liability_Services_Acct,V_Prepayment_Acct,WriteOff_Acct) SELECT 540002, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.C_Prepayment_Acct,p.C_Receivable_Acct,p.C_Receivable_Services_Acct,p.NotInvoicedReceipts_Acct,p.NotInvoicedReceivables_Acct,p.NotInvoicedRevenue_Acct,p.PayDiscount_Exp_Acct,p.PayDiscount_Rev_Acct,p.UnEarnedRevenue_Acct,p.V_Liability_Acct,p.V_Liability_Services_Acct,p.V_Prepayment_Acct,p.WriteOff_Acct FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000 AND NOT EXISTS (SELECT * FROM C_BP_Group_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_BP_Group_ID=540002)
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT INTO C_BPartner (AcqusitionCost,AD_Client_ID,AD_Language,AD_Org_ID,AllowConsolidateInOut,C_BPartner_ID,C_BP_Group_ID,C_PaymentTerm_ID,Created,CreatedBy,CreateSO,CreditorId,DebtorId,DeliveryRule,DeliveryViaRule,DocumentCopies,EdiDESADVDefaultItemCapacity,FlatDiscount,Fresh_AllowLineDiscount,Fresh_IsPrintESR,Fresh_Produzentenabrechnung,Fresh_Urproduzent,InvoiceRule,IsActive,IsADRCustomer,IsADRVendor,IsCompany,IsCreateDefaultPOReference,IsCustomer,IsDisableOrderCheckup,IsDiscountPrinted,IsEdiRecipient,IsEmployee,IsHidePackingMaterialInShipmentPrint,IsOneTime,IsParentSponsorReadWrite,IsPlanning,IsPOTaxExempt,IsProducerAllotment,IsProspect,IsReplicationLookupDefault,IsSalesRep,IsShippingNotificationEmail,IsSubscriptionConfirmRequired,IsSummary,IsTaxExempt,IsVendor,M_FreightCost_ID,M_PriceList_ID,M_Warehouse_ID,Name,NumberEmployees,PaymentRule,PaymentRulePO,PostageFree,PostageFreeAmt,PotentialLifeTimeValue,SalesVolume,SendEMail,ShareOfCustomer,ShelfLifeMinPct,SO_CreditLimit,Updated,UpdatedBy,Value) VALUES (0,1000000,'de_DE',1000000,'Y',540005,540002,1000002,TO_TIMESTAMP('2017-02-18 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,'F','P',0,1,0,'N','N','N','N','D','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',1000000,2001003,540008,'Standardmitglied',0,'P','P','Su',0,0,0,'N',0,0,0,TO_TIMESTAMP('2017-02-18 13:55:20','YYYY-MM-DD HH24:MI:SS'),100,'1000001')
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT INTO C_BP_Customer_Acct (C_BPartner_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,C_Prepayment_Acct,C_Receivable_Acct,C_Receivable_Services_Acct) SELECT 540005, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.C_Prepayment_Acct,p.C_Receivable_Acct,p.C_Receivable_Services_Acct FROM C_BP_Group_Acct p WHERE p.AD_Client_ID=1000000 AND p.C_BP_Group_ID=540002 AND NOT EXISTS (SELECT * FROM C_BP_Customer_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_BPartner_ID=540005)
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT INTO C_BP_Vendor_Acct (C_BPartner_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,V_Liability_Acct,V_Liability_Services_Acct,V_Prepayment_Acct) SELECT 540005, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.V_Liability_Acct,p.V_Liability_Services_Acct,p.V_Prepayment_Acct FROM C_BP_Group_Acct p WHERE p.AD_Client_ID=1000000 AND p.C_BP_Group_ID=540002 AND NOT EXISTS (SELECT * FROM C_BP_Vendor_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_BPartner_ID=540005)
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT INTO C_BP_Employee_Acct (C_BPartner_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ,E_Expense_Acct,E_Prepayment_Acct) SELECT 540005, p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),100,now(),100,p.E_Expense_Acct,p.E_Prepayment_Acct FROM C_AcctSchema_Default p WHERE p.AD_Client_ID=1000000 AND NOT EXISTS (SELECT * FROM C_BP_Employee_Acct e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.C_BPartner_ID=540005)
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT  INTO AD_TreeNodeBP (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540005, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=1000000 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=291 AND NOT EXISTS (SELECT * FROM AD_TreeNodeBP e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540005)
;

-- 18.02.2017 13:55
-- URL zum Konzept
UPDATE C_BPartner SET Value='M0001',Updated=TO_TIMESTAMP('2017-02-18 13:55:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_BPartner_ID=540005
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT INTO C_Location (AD_Client_ID,Address1,Address2,Address3,Address4,AD_Org_ID,C_Country_ID,City,C_Location_ID,Created,CreatedBy,IsActive,Postal,Postal_Add,Updated,UpdatedBy) VALUES (1000000,'','','','',0,107,'test',540003,TO_TIMESTAMP('2017-02-18 13:55:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','1234','',TO_TIMESTAMP('2017-02-18 13:55:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:55
-- URL zum Konzept
INSERT INTO C_BPartner_Location (AD_Client_ID,Address,AD_Org_ID,C_BPartner_ID,C_BPartner_Location_ID,C_Location_ID,Created,CreatedBy,IsActive,IsBillTo,IsBillToDefault,IsCommissionTo,IsCommissionToDefault,IsHandOverLocation,IsPayFrom,IsRemitTo,IsReplicationLookupDefault,IsShipTo,IsShipToDefault,IsSubscriptionTo,IsSubscriptionToDefault,Migration_Key,Name,Updated,UpdatedBy) VALUES (1000000,'1234 test
Schweiz',1000000,540005,540003,540003,TO_TIMESTAMP('2017-02-18 13:55:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','N','Y','Y','N','N','Y','N','N','N',0,'test',TO_TIMESTAMP('2017-02-18 13:55:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.02.2017 13:58
-- URL zum Konzept
UPDATE C_Location SET Address1='Am Nossbacher Weg 2', Address2='', Address3='', Address4='', C_City_ID=NULL, C_Country_ID=107, City='', C_Region_ID=NULL, Postal='12345', Postal_Add='', RegionName=NULL,Updated=TO_TIMESTAMP('2017-02-18 13:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Location_ID=2192334
;

-- 18.02.2017 13:58
-- URL zum Konzept
UPDATE C_BPartner_Location SET Name='standard',Updated=TO_TIMESTAMP('2017-02-18 13:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_BPartner_Location_ID=2202690
;

