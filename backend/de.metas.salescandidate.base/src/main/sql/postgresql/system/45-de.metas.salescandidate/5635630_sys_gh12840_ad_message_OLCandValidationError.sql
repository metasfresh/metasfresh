-- 2022-04-19T10:28:20.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545121,0,TO_TIMESTAMP('2022-04-19 13:28:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fail to validate order candidate {0}','E',TO_TIMESTAMP('2022-04-19 13:28:19','YYYY-MM-DD HH24:MI:SS'),100,'Process_C_OLCand_Error')
;

-- 2022-04-19T10:28:20.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545121 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-04-19T10:38:37.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='OLCandValidatorService.OLCandValidationError',Updated=TO_TIMESTAMP('2022-04-19 13:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545121
;


-- 2022-04-20T07:22:16.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Auftragskandidat {0} kann nicht validiert werden',Updated=TO_TIMESTAMP('2022-04-20 10:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545121
;

-- 2022-04-20T07:22:28.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Auftragskandidat {0} kann nicht validiert werden',Updated=TO_TIMESTAMP('2022-04-20 10:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545121
;

-- 2022-04-20T07:22:37.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Auftragskandidat {0} kann nicht validiert werden',Updated=TO_TIMESTAMP('2022-04-20 10:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545121
;

-- 2022-04-26T09:56:39.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Auftragskandidat {0} kann nicht validiert werden',Updated=TO_TIMESTAMP('2022-04-26 12:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545121
;

