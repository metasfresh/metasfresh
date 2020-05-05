-- 2017-08-17T18:11:07.224
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540819,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_InOut_Shipment_SelectHUs','N',TO_TIMESTAMP('2017-08-17 18:11:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'Shipped HUs','Y','Y','Java',TO_TIMESTAMP('2017-08-17 18:11:06','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_InOut_Shipment_SelectHUs')
;

-- 2017-08-17T18:11:07.246
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540819 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

