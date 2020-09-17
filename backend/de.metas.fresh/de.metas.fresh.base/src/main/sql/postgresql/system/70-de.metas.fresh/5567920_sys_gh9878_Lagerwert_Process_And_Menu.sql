-- 2020-09-17T11:13:32.871Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584744,'Y','de.metas.impexp.excel.process.ExportToExcelProcess','N',TO_TIMESTAMP('2020-09-17 14:13:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','N','Y','Y',0,'Lagerwert (Excel)','N','N','Excel',TO_TIMESTAMP('2020-09-17 14:13:32','YYYY-MM-DD HH24:MI:SS'),100,'Lagerwert (Excel)')
;

-- 2020-09-17T11:13:33.264Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584744 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-09-17T11:15:04.580Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,584744,541860,30,'M_Product_ID',TO_TIMESTAMP('2020-09-17 14:15:04','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','D',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','Y','N','N','N','Produkt',10,TO_TIMESTAMP('2020-09-17 14:15:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-17T11:15:04.614Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541860 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-09-17T11:15:04.693Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541860
;

-- 2020-09-17T11:15:04.728Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541860, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540594
;

-- 2020-09-17T11:15:05.093Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,459,0,584744,541861,19,'M_Warehouse_ID',TO_TIMESTAMP('2020-09-17 14:15:04','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','D',0,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','Lager',20,TO_TIMESTAMP('2020-09-17 14:15:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-17T11:15:05.129Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541861 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-09-17T11:15:05.203Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541861
;

-- 2020-09-17T11:15:05.237Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541861, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540595
;

-- 2020-09-17T11:15:05.566Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,584744,541862,15,'keydate',TO_TIMESTAMP('2020-09-17 14:15:05','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Stichtag',30,TO_TIMESTAMP('2020-09-17 14:15:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-17T11:15:05.602Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541862 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-09-17T11:15:05.677Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541862
;

-- 2020-09-17T11:15:05.713Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541862, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540611
;

-- 2020-09-17T11:16:01.956Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=577422, AD_Reference_Value_ID=540272, ColumnName='Parameter_M_Product_ID',Updated=TO_TIMESTAMP('2020-09-17 14:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541860
;

-- 2020-09-17T11:17:39.003Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578113,0,'Parameter_M_Warehouse_ID',TO_TIMESTAMP('2020-09-17 14:17:38','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Lager','Lager',TO_TIMESTAMP('2020-09-17 14:17:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-17T11:17:39.110Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578113 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-17T11:17:52.001Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Warehouse', PrintName='Warehouse',Updated=TO_TIMESTAMP('2020-09-17 14:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578113 AND AD_Language='en_US'
;

-- 2020-09-17T11:17:52.103Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578113,'en_US') 
;

-- 2020-09-17T11:18:26.528Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=578113, AD_Reference_ID=30, AD_Reference_Value_ID=540420, ColumnName='Parameter_M_Warehouse_ID',Updated=TO_TIMESTAMP('2020-09-17 14:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541861
;

-- 2020-09-17T11:19:23.256Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584744,541863,25,'AD_Org_ID',TO_TIMESTAMP('2020-09-17 14:19:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Sektion',40,TO_TIMESTAMP('2020-09-17 14:19:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-17T11:19:23.296Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541863 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-09-17T11:20:04.729Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@#AD_Org_ID/-1@',Updated=TO_TIMESTAMP('2020-09-17 14:20:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541863
;

-- 2020-09-17T11:26:49.708Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select *
from report.M_Cost_CostPrice_Function(''@keyDate@'',
                                      @parameter_M_Product_ID / -1@,
                                      @parameter_M_Warehouse_ID / -1@,
                                      ''Y'',
                                      @AD_Language@,
                                      @#AD_Client_ID / -1 @,
                                      @AD_Org_ID / -1@
    )',Updated=TO_TIMESTAMP('2020-09-17 14:26:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2020-09-17T11:27:22.108Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578114,0,TO_TIMESTAMP('2020-09-17 14:27:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lagerwert (Excel)','Lagerwert (Excel)',TO_TIMESTAMP('2020-09-17 14:27:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-17T11:27:22.181Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578114 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-17T11:30:28.620Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,578114,541511,0,584744,TO_TIMESTAMP('2020-09-17 14:30:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Lagerwert (Excel)','Y','N','N','N','N','Lagerwert (Excel)',TO_TIMESTAMP('2020-09-17 14:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-17T11:30:28.700Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541511 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2020-09-17T11:30:28.736Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541511, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541511)
;

-- 2020-09-17T11:30:28.806Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(578114) 
;

-- 2020-09-17T11:30:31.319Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000061, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540890 AND AD_Tree_ID=10
;

-- 2020-09-17T11:30:31.356Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000061, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541511 AND AD_Tree_ID=10
;

-- 2020-09-17T11:39:50.082Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2020-09-17 14:39:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541863
;

-- 2020-09-17T11:40:16.602Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select *
from report.M_Cost_CostPrice_Function(''@keyDate@'',
                                      @parameter_M_Product_ID/-1@,
                                      @parameter_M_Warehouse_ID/-1@,
                                      ''Y'',
                                      @AD_Language@,
                                      @#AD_Client_ID/-1 @,
                                      @AD_Org_ID/-1@
    )',Updated=TO_TIMESTAMP('2020-09-17 14:40:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2020-09-17T11:41:27.466Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select *
from report.M_Cost_CostPrice_Function(''@keyDate@''::date,
                                      @parameter_M_Product_ID/-1@,
                                      @parameter_M_Warehouse_ID/-1@,
                                      ''Y'',
                                      @AD_Language@,
                                      @#AD_Client_ID/-1 @,
                                      @AD_Org_ID/-1@
    )',Updated=TO_TIMESTAMP('2020-09-17 14:41:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2020-09-17T11:44:40.415Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select *
from report.M_Cost_CostPrice_Function(''@keydate@''::date,
                                      @parameter_M_Product_ID/-1@,
                                      @parameter_M_Warehouse_ID/-1@,
                                      ''Y'',
                                      @#AD_Language@,
                                      @#AD_Client_ID/-1 @,
                                      @AD_Org_ID/-1@
    )',Updated=TO_TIMESTAMP('2020-09-17 14:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2020-09-17T11:48:04.576Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select *
from report.M_Cost_CostPrice_Function(''@keydate@''::date,
                                      @parameter_M_Product_ID/-1@,
                                      @parameter_M_Warehouse_ID/-1@,
                                      ''Y'',
                                      ''@#AD_Language@'',
                                      @#AD_Client_ID/-1 @,
                                      @AD_Org_ID/-1@
    )',Updated=TO_TIMESTAMP('2020-09-17 14:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2020-09-17T11:50:14.466Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select *
from report.M_Cost_CostPrice_Function(''@keydate@''::date,
                                      @parameter_M_Product_ID/-1@,
                                      @parameter_M_Warehouse_ID/-1@,
                                      ''Y'',
                                      ''@AD_Language@'',
                                      @AD_Client_ID/-1@,
                                      @AD_Org_ID/-1@
    )',Updated=TO_TIMESTAMP('2020-09-17 14:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2020-09-17T11:55:13.121Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select *
from report.M_Cost_CostPrice_Function(''@keydate@''::date,
                                      @parameter_M_Product_ID/-1@,
                                      @parameter_M_Warehouse_ID/-1@,
                                      ''Y'',
                                      ''@#AD_Language@'',
                                      @AD_Client_ID@,
                                      @AD_Org_ID@
    )',Updated=TO_TIMESTAMP('2020-09-17 14:55:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

-- 2020-09-17T11:56:03.246Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select *
from report.M_Cost_CostPrice_Function(''@keydate@''::date,
                                      @parameter_M_Product_ID/NULL@,
                                      @parameter_M_Warehouse_ID/NULL@,
                                      ''Y'',
                                      ''@#AD_Language@'',
                                      @AD_Client_ID@,
                                      @AD_Org_ID@
    )',Updated=TO_TIMESTAMP('2020-09-17 14:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584744
;

