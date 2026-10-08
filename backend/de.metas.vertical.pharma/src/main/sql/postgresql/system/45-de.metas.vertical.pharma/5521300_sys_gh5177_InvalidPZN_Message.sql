

-- 2019-05-09T16:38:16.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544916,0,TO_TIMESTAMP('2019-05-09 16:38:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','The PZN {1}  is not valid.','E',TO_TIMESTAMP('2019-05-09 16:38:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.model.interceptor.M_Product.Invalid_PZN')
;

-- 2019-05-09T16:38:16.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544916 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-05-09T16:39:03.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The PZN {0} is not valid.',Updated=TO_TIMESTAMP('2019-05-09 16:39:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544916
;



-- 2019-05-09T18:23:45.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Es wurde anhand der Prüfziffer festgestellt, dass die PZN {0} nicht gültig ist.',Updated=TO_TIMESTAMP('2019-05-09 18:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544916
;

-- 2019-05-09T18:23:55.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Es wurde anhand der Prüfziffer festgestellt, dass die PZN {0} nicht gültig ist.',Updated=TO_TIMESTAMP('2019-05-09 18:23:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544916
;

-- 2019-05-09T18:24:00.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-09 18:24:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544916
;

-- 2019-05-09T18:24:32.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='According to the check digit verification, the PZN {1}  is not valid.',Updated=TO_TIMESTAMP('2019-05-09 18:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544916
;

-- 2019-05-09T18:24:35.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='According to the check digit verification, the PZN {0}  is not valid.',Updated=TO_TIMESTAMP('2019-05-09 18:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544916
;

-- 2019-05-09T18:25:04.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='According to the check digit verification, the PZN {0}  is not valid.',Updated=TO_TIMESTAMP('2019-05-09 18:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544916
;


-- 2019-05-09T18:27:14.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='According to the checksum verification, the PZN {0}  is not valid.',Updated=TO_TIMESTAMP('2019-05-09 18:27:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544916
;

-- 2019-05-09T18:27:20.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='According to the checksum verification, the PZN {0}  is not valid.',Updated=TO_TIMESTAMP('2019-05-09 18:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544916
;

