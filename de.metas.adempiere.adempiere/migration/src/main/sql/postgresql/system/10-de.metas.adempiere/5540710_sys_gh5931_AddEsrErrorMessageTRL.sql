-- 2020-01-08T09:33:19.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544954,0,TO_TIMESTAMP('2020-01-08 11:33:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lines already exist. Please choose a new ESR.','I',TO_TIMESTAMP('2020-01-08 11:33:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr.dataimporter.ESRImportEnqueuer.LinesAlreadyExistChoseNewESR')
;

-- 2020-01-08T09:33:19.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544954 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-01-08T09:33:29.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-08 11:33:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544954
;

-- 2020-01-08T09:35:23.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Zeilen existieren bereits. Bitte wählen Sie einen neuen ESR aus.',Updated=TO_TIMESTAMP('2020-01-08 11:35:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544954
;

-- 2020-01-08T09:35:46.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zeilen existieren bereits. Bitte wählen Sie einen neuen ESR aus.',Updated=TO_TIMESTAMP('2020-01-08 11:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544954
;

-- 2020-01-08T09:35:54.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Zeilen existieren bereits. Bitte wählen Sie einen neuen ESR aus.',Updated=TO_TIMESTAMP('2020-01-08 11:35:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544954
;

-- 2020-01-08T09:39:08.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Lines already exist. Please choose a new ESR.',Updated=TO_TIMESTAMP('2020-01-08 11:39:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544954
;

-- 2020-01-08T09:39:21.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Lines already exist. Please choose a new ESR.',Updated=TO_TIMESTAMP('2020-01-08 11:39:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544954
;

-- 2020-01-08T09:39:25.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zeilen existieren bereits. Bitte wählen Sie einen neuen ESR aus.',Updated=TO_TIMESTAMP('2020-01-08 11:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544954
;

