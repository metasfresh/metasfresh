-- 2022-04-05T12:47:25.619Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585035,'Y','de.metas.process.CauseOutOfMemoryError','N',TO_TIMESTAMP('2022-04-05 14:47:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Verusachen einer OOME','json','N','N','xls','Java',TO_TIMESTAMP('2022-04-05 14:47:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.process.CauseOutOfMemoryError')
;

-- 2022-04-05T12:47:25.627Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585035 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-04-05T12:56:39.117Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580753,0,TO_TIMESTAMP('2022-04-05 14:56:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Verusachen einer OOME','Verusachen einer OOME',TO_TIMESTAMP('2022-04-05 14:56:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-05T12:56:39.122Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580753 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-05T12:56:50.426Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,580753,541918,0,585035,TO_TIMESTAMP('2022-04-05 14:56:50','YYYY-MM-DD HH24:MI:SS'),100,'U','de.metas.process.CauseOutOfMemoryError','Y','N','N','N','N','Verursachen einer OOME',TO_TIMESTAMP('2022-04-05 14:56:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-05T12:56:50.428Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541918 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-04-05T12:56:50.430Z
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541918, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541918)
;

-- 2022-04-05T12:56:50.493Z
/* DDL */  select update_menu_translation_from_ad_element(580753) 
;

-- 2022-04-05T14:31:52.316Z
UPDATE AD_Element SET EntityType='U',Updated=TO_TIMESTAMP('2022-04-05 16:31:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580753
;


-- 2022-04-05T14:33:39.647Z
UPDATE AD_TreeNodeMM SET Parent_ID=540077, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541918 AND AD_Tree_ID=10
;


-- 2022-04-05T15:39:23.829Z
UPDATE AD_Process SET Help='Dieser Prozess führt zu einer OutOfMemoryError der JVM.',Updated=TO_TIMESTAMP('2022-04-05 17:39:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585035
;

-- 2022-04-05T15:39:35.590Z
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2022-04-05 17:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585035
;

-- 2022-04-06T07:44:53.889Z
UPDATE AD_Process SET Description='Dieser Prozess führt zu einer OutOfMemoryError der JVM.',Updated=TO_TIMESTAMP('2022-04-06 09:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585035
;

-- 2022-04-06T07:44:53.904Z
UPDATE AD_Menu SET Description='Dieser Prozess führt zu einer OutOfMemoryError der JVM.', IsActive='Y', Name='Verursachen einer OOME',Updated=TO_TIMESTAMP('2022-04-06 09:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541918
;

-- 2022-04-06T08:20:47.113Z
UPDATE AD_Process_Trl SET Name='Cause an OutOfMemoryError',Updated=TO_TIMESTAMP('2022-04-06 10:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585035
;

