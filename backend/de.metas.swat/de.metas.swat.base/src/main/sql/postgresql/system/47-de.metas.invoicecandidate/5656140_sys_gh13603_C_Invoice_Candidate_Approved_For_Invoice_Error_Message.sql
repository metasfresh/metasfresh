-- 2022-09-14T10:31:21.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545156,0,TO_TIMESTAMP('2022-09-14 13:31:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Operation not supported because there exists associated Invoice Candidates that are approved for invoicing.','I',TO_TIMESTAMP('2022-09-14 13:31:21','YYYY-MM-DD HH24:MI:SS'),100,'Operation_Not_Supported_Approved_For_Invoice')
;

-- 2022-09-14T10:31:21.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545156 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-09-14T10:32:25.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Operation wird nicht unterstützt, da es zugehörige Rechnungskandidaten gibt, die für die Rechnungsstellung genehmigt sind.',Updated=TO_TIMESTAMP('2022-09-14 13:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545156
;

-- 2022-09-14T10:32:40.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-09-14 13:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545156
;

-- 2022-09-14T10:32:53.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die Operation wird nicht unterstützt, da es zugehörige Rechnungskandidaten gibt, die für die Rechnungsstellung genehmigt sind.',Updated=TO_TIMESTAMP('2022-09-14 13:32:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545156
;

-- 2022-09-14T10:47:46.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Operation not supported because there exist associated Invoice Candidates that are approved for invoicing.',Updated=TO_TIMESTAMP('2022-09-14 13:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545156
;

-- 2022-09-14T10:48:06.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Operation not supported because there exist associated Invoice Candidates that are approved for invoicing.',Updated=TO_TIMESTAMP('2022-09-14 13:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545156
;

-- 2022-09-14T10:48:11.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Operation not supported because there exist associated Invoice Candidates that are approved for invoicing.',Updated=TO_TIMESTAMP('2022-09-14 13:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545156
;

-- 2022-09-14T10:48:36.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Operation nicht unterstützt, da es bereits zugehörige Rechnungskandidaten, die für die Rechnungsstellung genehmigt sind.',Updated=TO_TIMESTAMP('2022-09-14 13:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545156
;

-- 2022-09-14T10:48:44.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Operation nicht unterstützt, da es bereits zugehörige Rechnungskandidaten, die für die Rechnungsstellung genehmigt sind.',Updated=TO_TIMESTAMP('2022-09-14 13:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545156
;

-- 2022-09-14T16:34:01.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='There are invoice candidates already apporved for invoicing.',Updated=TO_TIMESTAMP('2022-09-14 19:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545156
;

-- 2022-09-14T17:03:38.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='There are invoice candidates already apporved for invoicing.',Updated=TO_TIMESTAMP('2022-09-14 20:03:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545156
;

-- 2022-09-14T17:04:19.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='There are invoice candidates already approved for invoicing.',Updated=TO_TIMESTAMP('2022-09-14 20:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545156
;

-- 2022-09-14T17:10:31.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='There are invoice candidates already approved for invoicing.',Updated=TO_TIMESTAMP('2022-09-14 20:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545156
;

-- 2022-09-14T17:10:37.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Es gibt bereits Rechnungskandidaten, die für die Rechnungsstellung freigegeben sind.',Updated=TO_TIMESTAMP('2022-09-14 20:10:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545156
;

-- 2022-09-14T17:10:47.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Es gibt bereits Rechnungskandidaten, die für die Rechnungsstellung freigegeben sind.',Updated=TO_TIMESTAMP('2022-09-14 20:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545156
;

