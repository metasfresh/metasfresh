UPDATE AD_Process
SET classname='de.metas.ordercandidate.process.C_OLCand_SetOverrideValues', updated=TO_TIMESTAMP('2022-02-022 12:12:12', 'YYYY-MM-DD HH24:MI:SS'), updatedBy=100
WHERE classname = 'de.metas.ordercandidate.process.OLCandSetOverrideValues'
;

-- 2022-02-22T16:26:56.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545097,0,TO_TIMESTAMP('2022-02-22 18:26:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kein Standort verfügbar und mehr als eine GLN für die betreffenden Einträge vorhanden.','E',TO_TIMESTAMP('2022-02-22 18:26:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.MultipleGLNs')
;

-- 2022-02-22T16:26:56.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545097 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-02-22T16:27:07.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No location provided and more than one GLN found for the given records.',Updated=TO_TIMESTAMP('2022-02-22 18:27:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545097
;

-- 2022-02-22T16:27:44.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545098,0,TO_TIMESTAMP('2022-02-22 18:27:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kein Standort verfügbar und keine GLN für die betreffenden Einträge vorhanden.','E',TO_TIMESTAMP('2022-02-22 18:27:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.NoGLNs')
;

-- 2022-02-22T16:27:44.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545098 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-02-22T16:28:35.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545100,0,TO_TIMESTAMP('2022-02-22 18:28:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kein Standort mit GLN: {0} für den betreffenden Geschäftspartner vorhanden.','E',TO_TIMESTAMP('2022-02-22 18:28:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.NoLocationForGLN')
;

-- 2022-02-22T16:28:35.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545100 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-02-22T16:28:48.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No location with GLN: {0} found for the given business partner.',Updated=TO_TIMESTAMP('2022-02-22 18:28:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545100
;

-- 2022-02-22T16:29:05.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No location provided and no GLN found for the given records.',Updated=TO_TIMESTAMP('2022-02-22 18:29:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545098
;
