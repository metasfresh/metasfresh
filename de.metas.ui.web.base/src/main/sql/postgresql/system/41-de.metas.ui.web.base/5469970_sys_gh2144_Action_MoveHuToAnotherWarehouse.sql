-- 2017-08-25T14:40:56.399
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540820,'Y','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveToAnotherWarehouse','N',TO_TIMESTAMP('2017-08-25 14:40:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y',0,'MoveToAnotherWarehouse','N','Y','Java',TO_TIMESTAMP('2017-08-25 14:40:56','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_MoveToAnotherWarehouse')
;

-- 2017-08-25T14:40:56.419
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540820 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-08-25T14:50:43.786
-- URL zum Konzept
UPDATE AD_Process SET Name='Lagerbewegung',Updated=TO_TIMESTAMP('2017-08-25 14:50:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540820
;

-- 2017-08-25T15:41:47.148
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540820,540516,TO_TIMESTAMP('2017-08-25 15:41:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2017-08-25 15:41:47','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2017-08-25T15:42:06.961
-- URL zum Konzept
UPDATE AD_Table_Process SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2017-08-25 15:42:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540820 AND AD_Table_ID=540516
;

