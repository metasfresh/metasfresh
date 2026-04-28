-- 24.06.2016 17:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540688,'Y','de.metas.rfq.process.C_RfQ_UnClose',TO_TIMESTAMP('2016-06-24 17:52:17','YYYY-MM-DD HH24:MI:SS'),100,NULL,'de.metas.rfq',NULL,'Y','N','Y','N','N','N','N','Y','UnClose RfQ','N','Y',0,0,'Java',TO_TIMESTAMP('2016-06-24 17:52:17','YYYY-MM-DD HH24:MI:SS'),100,'C_RfQ_UnClose')
;

-- 24.06.2016 17:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540688 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 24.06.2016 17:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsBetaFunctionality='N',Updated=TO_TIMESTAMP('2016-06-24 17:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540688
;

-- 24.06.2016 17:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsBetaFunctionality='N',Updated=TO_TIMESTAMP('2016-06-24 17:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=269
;

-- 24.06.2016 17:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsBetaFunctionality='N',Updated=TO_TIMESTAMP('2016-06-24 17:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=261
;

-- 24.06.2016 17:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2016-06-24 17:52:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=277
;

-- 24.06.2016 17:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2016-06-24 17:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=262
;

-- 24.06.2016 17:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540688,677,TO_TIMESTAMP('2016-06-24 17:53:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.rfq','Y',TO_TIMESTAMP('2016-06-24 17:53:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.06.2016 17:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540689,'Y','de.metas.rfq.process.C_RfQ_ReActivate',TO_TIMESTAMP('2016-06-24 17:54:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.rfq','Y','N','N','N','N','N','N','Y','UnClose RfQ','N','Y',0,0,'Java',TO_TIMESTAMP('2016-06-24 17:54:16','YYYY-MM-DD HH24:MI:SS'),100,'C_RfQ_ReActivate')
;

-- 24.06.2016 17:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540689 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 24.06.2016 17:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540689,677,TO_TIMESTAMP('2016-06-24 17:54:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.rfq','Y',TO_TIMESTAMP('2016-06-24 17:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.06.2016 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Ausschreibung wieder öffnen',Updated=TO_TIMESTAMP('2016-06-24 17:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540688
;

-- 24.06.2016 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540688
;

-- 24.06.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Ausschreibung reaktivieren',Updated=TO_TIMESTAMP('2016-06-24 17:58:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540689
;

-- 24.06.2016 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540689
;

