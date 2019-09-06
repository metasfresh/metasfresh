-- 2019-08-28T22:11:21.351
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,541186,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2019-08-28 22:11:21','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','N','N','N','N','N','Y','Y',0,'fix_native_sequences','N','N','select dba_seq_check_native();select update_sequences();','SQL',TO_TIMESTAMP('2019-08-28 22:11:21','YYYY-MM-DD HH24:MI:SS'),100,'fix_native_sequences')
;

-- 2019-08-28T22:11:21.353
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541186 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-08-28T22:12:08.188
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577033,0,TO_TIMESTAMP('2019-08-28 22:12:08','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Fix Native Sequences','Fix Native Sequences',TO_TIMESTAMP('2019-08-28 22:12:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-28T22:12:08.190
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577033 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-08-28T22:12:23.648
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,577033,541339,0,541186,TO_TIMESTAMP('2019-08-28 22:12:23','YYYY-MM-DD HH24:MI:SS'),100,'U','Fix Native Sequences','Y','N','N','N','N','Fix Native Sequences',TO_TIMESTAMP('2019-08-28 22:12:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-28T22:12:23.649
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541339 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-08-28T22:12:23.650
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541339, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541339)
;
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=(select max(seqno)+10 from ad_treenodemm where Parent_ID=1000098), Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2019-08-28T22:14:32.072
-- URL zum Konzept
UPDATE AD_Process SET Name='dba_seq_check_native', SQLStatement='select dba_seq_check_native();', Value='dba_seq_check_native',Updated=TO_TIMESTAMP('2019-08-28 22:14:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541186
;

-- 2019-08-28T22:14:32.074
-- URL zum Konzept
UPDATE AD_Menu SET Description='', IsActive='Y', Name='dba_seq_check_native',Updated=TO_TIMESTAMP('2019-08-28 22:14:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541339
;

-- 2019-08-28T22:16:00.474
-- URL zum Konzept
UPDATE AD_Process SET Name='Fix Native Sequences (dba_seq_check_native)', Value='Fix Native Sequences',Updated=TO_TIMESTAMP('2019-08-28 22:16:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541186
;

-- 2019-08-28T22:16:00.476
-- URL zum Konzept
UPDATE AD_Menu SET Description='', IsActive='Y', Name='Fix Native Sequences (dba_seq_check_native)',Updated=TO_TIMESTAMP('2019-08-28 22:16:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541339
;

