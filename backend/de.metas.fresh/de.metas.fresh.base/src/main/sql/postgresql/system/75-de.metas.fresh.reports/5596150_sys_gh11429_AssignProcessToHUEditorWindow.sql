-- 2021-07-01T09:25:35.982Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541380,'O',TO_TIMESTAMP('2021-07-01 11:25:35','YYYY-MM-DD HH24:MI:SS'),100,'This specifies the process which the system shall use when printing material receipts labels for HUs. Note: AD_Process_ID=540370 is the process with the value "Wareneingangsetikett LU (Jasper)".','de.metas.handlingunits','Y','de.metas.handlingunits.FinishedGoodsLabel.AD_Process_ID',TO_TIMESTAMP('2021-07-01 11:25:35','YYYY-MM-DD HH24:MI:SS'),100,'584694')
;




-- 2021-07-01T10:31:56.219Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584694,540516,540947,TO_TIMESTAMP('2021-07-01 12:31:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2021-07-01 12:31:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-07-01T10:35:06.776Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584852,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_PrintReceiptLabel','N',TO_TIMESTAMP('2021-07-01 12:35:06','YYYY-MM-DD HH24:MI:SS'),100,'Ein Wareneingangsetikett für die HU ausdrucken','U','Y','N','N','N','N','N','N','Y','Y',0,'Fertigprodukt Label','json','N','Y','Java',TO_TIMESTAMP('2021-07-01 12:35:06','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_PrintFinishedGoodsLabel')
;

-- 2021-07-01T10:35:06.779Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584852 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-07-01T10:35:13.365Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-01 12:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584852
;

-- 2021-07-01T10:35:43.126Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='', IsTranslated='Y', Name='Finishedproduct Label',Updated=TO_TIMESTAMP('2021-07-01 12:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Process_ID=584852
;

-- 2021-07-01T10:35:48.510Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='', IsTranslated='Y', Name='Finishedproduct Label',Updated=TO_TIMESTAMP('2021-07-01 12:35:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584852
;

-- 2021-07-01T10:36:11.264Z
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_HU_PrintFinishedGoodsLabel',Updated=TO_TIMESTAMP('2021-07-01 12:36:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584852
;

-- 2021-07-01T10:36:53.244Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584852,540516,540948,TO_TIMESTAMP('2021-07-01 12:36:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2021-07-01 12:36:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-07-01T10:37:08.329Z
-- URL zum Konzept
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2021-07-01 12:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540948
;

