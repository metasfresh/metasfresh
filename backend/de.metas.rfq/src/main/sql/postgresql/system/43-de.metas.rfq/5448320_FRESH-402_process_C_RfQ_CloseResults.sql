-- 14.07.2016 14:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540704,'Y','de.metas.rfq.process.C_RfQ_CloseResults','N',TO_TIMESTAMP('2016-07-14 14:17:30','YYYY-MM-DD HH24:MI:SS'),100,'Close & publish the RfQ results','de.metas.rfq',NULL,'Y','N','N','N','N','N','N','Y',0,'Close RfQ results','N','Y',0,0,'Java',TO_TIMESTAMP('2016-07-14 14:17:30','YYYY-MM-DD HH24:MI:SS'),100,'C_RfQ_CloseResults')
;

-- 14.07.2016 14:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540704 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 14.07.2016 14:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540704,677,TO_TIMESTAMP('2016-07-14 14:17:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.rfq','Y',TO_TIMESTAMP('2016-07-14 14:17:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.07.2016 14:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='Y', ReadOnlyLogic='@DocStatus@!CO',Updated=TO_TIMESTAMP('2016-07-14 14:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11006
;

-- 14.07.2016 14:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL,Updated=TO_TIMESTAMP('2016-07-14 14:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540704
;

-- 14.07.2016 14:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540704
;

