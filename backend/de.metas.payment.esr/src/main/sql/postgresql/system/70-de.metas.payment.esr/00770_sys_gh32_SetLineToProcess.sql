-- 15.04.2016 13:24:03 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540680,'de.metas.payment.esr.process.ESR_SetLineToProcessed','N',TO_TIMESTAMP('2016-04-15 13:24:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','N','N','N','N','N',0,'Process line','Y','Java',TO_TIMESTAMP('2016-04-15 13:24:02','YYYY-MM-DD HH24:MI:SS'),100,'ESR_SetLineToProcessed')
;

-- 15.04.2016 13:24:03 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540680 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 15.04.2016 13:24:03 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Access (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540680,0,TO_TIMESTAMP('2016-04-15 13:24:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-04-15 13:24:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 15.04.2016 13:24:03 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Access (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,0,540680,1000000,TO_TIMESTAMP('2016-04-15 13:24:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-04-15 13:24:03','YYYY-MM-DD HH24:MI:SS'),100)
;


---------------


-- 15.04.2016 14:27:18 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Stornobuchung. Bitte Support kontaktieren!',Updated=TO_TIMESTAMP('2016-04-15 14:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543864
;

-- 15.04.2016 14:27:18 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543864
;

-- 15.04.2016 14:29:21 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Wurde durch Admin auf verarbeitet gesetzt.', Name='Manuell auf verarbeitet setzen',Updated=TO_TIMESTAMP('2016-04-15 14:29:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540680
;

-- 15.04.2016 14:29:21 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540680
;



-- 15.04.2016 13:49:40 OESZ
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540680,540410,TO_TIMESTAMP('2016-04-15 13:49:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y',TO_TIMESTAMP('2016-04-15 13:49:40','YYYY-MM-DD HH24:MI:SS'),100)
;

