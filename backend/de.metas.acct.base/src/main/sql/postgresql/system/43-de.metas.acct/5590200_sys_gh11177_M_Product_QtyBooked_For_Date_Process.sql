
-- this was missing on one user's DB, so we insert it again, if needed
-- 2020-09-17T11:17:39.003Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) 
select 0,578113,0,'Parameter_M_Warehouse_ID',TO_TIMESTAMP('2020-09-17 14:17:38','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Lager','Lager',TO_TIMESTAMP('2020-09-17 14:17:38','YYYY-MM-DD HH24:MI:SS'),100
where not exists (select 1 from ad_element where ad_element_id=578113);
;
-- 2020-09-17T11:17:39.110Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578113 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;



-- 2021-05-26T17:37:41.395Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584834,'Y','de.metas.impexp.excel.process.ExportToExcelProcess','N',TO_TIMESTAMP('2021-05-26 20:37:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','N','N','N','Y','Y',0,'Products with QtyBook (Excel)','json','N','N','Excel',TO_TIMESTAMP('2021-05-26 20:37:40','YYYY-MM-DD HH24:MI:SS'),100,'M_Product_QtyBooked_For_Date')
;

-- 2021-05-26T17:37:41.632Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584834 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-05-26T17:39:37.927Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2700,0,584834,542007,30,'M_CostElement_ID',TO_TIMESTAMP('2021-05-26 20:39:37','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT ce.M_CostElement_ID AS DefaultValue  FROM M_CostElement ce JOIN c_acctschema sch on ce.costingmethod = sch.costingmethod JOIN ad_clientinfo ci on sch.c_acctschema_id = ci.c_acctschema1_id WHERE ci.ad_client_id = @#AD_Client_ID@','Produkt-Kostenart','de.metas.acct',0,'Y','N','Y','N','Y','N','Kostenart',10,TO_TIMESTAMP('2021-05-26 20:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T17:39:37.968Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542007 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-26T17:40:13.357Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577422,0,584834,542008,30,540272,'Parameter_M_Product_ID',TO_TIMESTAMP('2021-05-26 20:40:12','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.acct',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',20,TO_TIMESTAMP('2021-05-26 20:40:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T17:40:13.398Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542008 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-26T17:40:37Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578113,0,584834,542009,30,540420,'Parameter_M_Warehouse_ID',TO_TIMESTAMP('2021-05-26 20:40:36','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','de.metas.acct',0,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','Lager',30,TO_TIMESTAMP('2021-05-26 20:40:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T17:40:37.048Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542009 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-26T17:41:21.425Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577762,0,584834,542010,15,'Date',TO_TIMESTAMP('2021-05-26 20:41:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct',0,'Y','N','Y','N','N','N','Datum',40,TO_TIMESTAMP('2021-05-26 20:41:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T17:41:21.453Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542010 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-26T17:42:42.809Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM "de_metas_acct".report_M_Product_QtyBooked_For_Date( @M_CostElement_ID/-1@, @Parameter_M_Product_ID@, @Parameter_M_Warehouse_ID@, ''@Date@'')',Updated=TO_TIMESTAMP('2021-05-26 20:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584834
;




-- 2021-05-26T17:47:09.493Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Products with QtyBook',Updated=TO_TIMESTAMP('2021-05-26 20:47:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584834
;

















-- 2021-05-26T17:47:09.493Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Products with QtyBook',Updated=TO_TIMESTAMP('2021-05-26 20:47:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584834
;

-- 2021-05-26T17:49:12.416Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579258,0,TO_TIMESTAMP('2021-05-26 20:49:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Products with QtyBook','Products with QtyBook',TO_TIMESTAMP('2021-05-26 20:49:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T17:49:12.632Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579258 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-26T17:49:55.870Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,579258,541717,0,584834,TO_TIMESTAMP('2021-05-26 20:49:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','M_Product_QtyBooked_For_Date','Y','N','N','N','N','Products with QtyBook',TO_TIMESTAMP('2021-05-26 20:49:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-26T17:49:55.977Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541717 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-05-26T17:49:56.024Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541717, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541717)
;

-- 2021-05-26T17:49:56.102Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579258) 
;

-- 2021-05-26T17:49:58.598Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.638Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.663Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.710Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.741Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.763Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.810Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.841Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.879Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.910Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.948Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:58.980Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.011Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.042Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.081Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.102Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.133Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.180Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.211Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.242Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.265Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.327Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.365Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.396Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.428Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.465Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.497Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.528Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.566Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.597Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.630Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.650Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.682Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.713Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.751Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.782Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2021-05-26T17:49:59.813Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.450Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.480Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.520Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.551Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.592Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.622Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.659Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.688Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.720Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.759Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.791Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.824Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.859Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.904Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.935Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.966Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:02.997Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.033Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.064Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.094Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.125Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.160Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.198Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.221Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.269Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.310Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.341Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.371Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.402Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.433Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.464Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.505Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.536Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.572Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.613Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.644Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:03.675Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2021-05-26T17:50:24.922Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Products With QtyBook', PrintName='Products With QtyBook',Updated=TO_TIMESTAMP('2021-05-26 20:50:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='de_DE'
;

-- 2021-05-26T17:50:24.962Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'de_DE') 
;

-- 2021-05-26T17:50:25.054Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579258,'de_DE') 
;

-- 2021-05-26T17:50:25.085Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Products With QtyBook', Description=NULL, Help=NULL WHERE AD_Element_ID=579258
;

-- 2021-05-26T17:50:25.123Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Products With QtyBook', Description=NULL, Help=NULL WHERE AD_Element_ID=579258 AND IsCentrallyMaintained='Y'
;

-- 2021-05-26T17:50:25.154Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Products With QtyBook', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579258) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579258)
;

-- 2021-05-26T17:50:25.217Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Products With QtyBook', Name='Products With QtyBook' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579258)
;

-- 2021-05-26T17:50:25.255Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Products With QtyBook', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579258
;

-- 2021-05-26T17:50:25.286Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Products With QtyBook', Description=NULL, Help=NULL WHERE AD_Element_ID = 579258
;

-- 2021-05-26T17:50:25.324Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Products With QtyBook', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579258
;

-- 2021-05-26T17:50:28.463Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Products With QtyBook', PrintName='Products With QtyBook',Updated=TO_TIMESTAMP('2021-05-26 20:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='de_CH'
;

-- 2021-05-26T17:50:28.478Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'de_CH') 
;

-- 2021-05-26T17:50:32.323Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Products With QtyBook', PrintName='Products With QtyBook',Updated=TO_TIMESTAMP('2021-05-26 20:50:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='en_US'
;

-- 2021-05-26T17:50:32.345Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'en_US') 
;

-- 2021-05-26T17:50:35.933Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Products With QtyBook', PrintName='Products With QtyBook',Updated=TO_TIMESTAMP('2021-05-26 20:50:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='nl_NL'
;

-- 2021-05-26T17:50:35.971Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'nl_NL') 
;









-- 2021-05-26T17:55:41.479Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM "de_metas_acct".report_M_Product_QtyBooked_For_Date( @M_CostElement_ID/-1@, @Parameter_M_Product_ID/-1@, @Parameter_M_Warehouse_ID/-1@, ''@Date@'')',Updated=TO_TIMESTAMP('2021-05-26 20:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584834
;



-- 2021-05-27T16:30:27.270Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Buchbestand (Excel)',Updated=TO_TIMESTAMP('2021-05-27 19:30:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584834
;

-- 2021-05-27T16:30:27.730Z
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Buchbestand (Excel)',Updated=TO_TIMESTAMP('2021-05-27 19:30:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541717
;

-- 2021-05-27T16:30:37.901Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Buchbestand (Excel)',Updated=TO_TIMESTAMP('2021-05-27 19:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584834
;

-- 2021-05-27T16:31:27.412Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Buchbestand (Excel)', PrintName='Buchbestand (Excel)',Updated=TO_TIMESTAMP('2021-05-27 19:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='de_CH'
;

-- 2021-05-27T16:31:27.549Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'de_CH') 
;

-- 2021-05-27T16:31:31.001Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Buchbestand (Excel)', PrintName='Buchbestand (Excel)',Updated=TO_TIMESTAMP('2021-05-27 19:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='de_DE'
;

-- 2021-05-27T16:31:31.147Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'de_DE') 
;

-- 2021-05-27T16:31:31.220Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579258,'de_DE') 
;

-- 2021-05-27T16:31:31.257Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Buchbestand (Excel)', Description=NULL, Help=NULL WHERE AD_Element_ID=579258
;

-- 2021-05-27T16:31:31.291Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Buchbestand (Excel)', Description=NULL, Help=NULL WHERE AD_Element_ID=579258 AND IsCentrallyMaintained='Y'
;

-- 2021-05-27T16:31:31.323Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Buchbestand (Excel)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579258) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579258)
;

-- 2021-05-27T16:31:31.589Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Buchbestand (Excel)', Name='Buchbestand (Excel)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579258)
;

-- 2021-05-27T16:31:31.621Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Buchbestand (Excel)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579258
;

-- 2021-05-27T16:31:31.656Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Buchbestand (Excel)', Description=NULL, Help=NULL WHERE AD_Element_ID = 579258
;

-- 2021-05-27T16:31:31.691Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Buchbestand (Excel)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579258
;

-- 2021-05-27T16:31:40.109Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Products With Booked Quantity', PrintName='Products With Booked Quantity',Updated=TO_TIMESTAMP('2021-05-27 19:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='en_US'
;

-- 2021-05-27T16:31:40.235Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'en_US') 
;

-- 2021-05-27T16:31:45.789Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Products With Booked Quantity', PrintName='Products With Booked Quantity',Updated=TO_TIMESTAMP('2021-05-27 19:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='nl_NL'
;

-- 2021-05-27T16:31:45.914Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'nl_NL') 
;

-- 2021-05-27T16:31:49.299Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-27 19:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579258 AND AD_Language='de_CH'
;

-- 2021-05-27T16:31:49.446Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579258,'de_CH') 
;

-- 2021-05-27T16:32:01.510Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Products With Booked Quantity',Updated=TO_TIMESTAMP('2021-05-27 19:32:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584834
;

-- 2021-05-27T16:32:06.444Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Products With Booked Quantity',Updated=TO_TIMESTAMP('2021-05-27 19:32:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584834
;





