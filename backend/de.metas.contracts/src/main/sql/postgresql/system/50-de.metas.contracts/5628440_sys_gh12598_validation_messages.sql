-- 2022-03-01T16:10:09.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545106,0,TO_TIMESTAMP('2022-03-01 18:10:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Missing C_Flatrate_Term_ID on line: {0}','E',TO_TIMESTAMP('2022-03-01 18:10:09','YYYY-MM-DD HH24:MI:SS'),100,'MISSING_FLATRATE_TERM')
;

-- 2022-03-01T16:10:09.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545106 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-01T16:11:28.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545107,0,TO_TIMESTAMP('2022-03-01 18:11:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Wrong contract type on line: {0}! It must be {1}.','E',TO_TIMESTAMP('2022-03-01 18:11:27','YYYY-MM-DD HH24:MI:SS'),100,'WRONG_TYPE_CONDITIONS')
;

-- 2022-03-01T16:11:28.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545107 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-01T16:11:34.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Missing C_Flatrate_Term_ID on line: {0}.',Updated=TO_TIMESTAMP('2022-03-01 18:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545106
;

-- 2022-03-01T16:12:56.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545108,0,TO_TIMESTAMP('2022-03-01 18:12:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','BPartner doesn''t match! See {0}.','E',TO_TIMESTAMP('2022-03-01 18:12:56','YYYY-MM-DD HH24:MI:SS'),100,'BPARTNERS_DO_NOT_MATCH')
;

-- 2022-03-01T16:12:56.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545108 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-01T16:13:49.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='BPartner doesn''t match on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545108
;

-- 2022-03-01T16:18:50.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='BPartner doesn''t match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545108
;

-- 2022-03-01T16:22:04.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545109,0,TO_TIMESTAMP('2022-03-01 18:22:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Product doesn''t match with contract on line: {0}! See {1}.','E',TO_TIMESTAMP('2022-03-01 18:22:03','YYYY-MM-DD HH24:MI:SS'),100,'PRODUCTS_DO_NOT_MATCH')
;

-- 2022-03-01T16:22:04.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545109 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-03-01T16:40:57.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Product does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:40:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545109
;

-- 2022-03-01T16:41:19.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='BPartner does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:41:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545108
;

-- 2022-03-01T16:47:21.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='BPartner does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:47:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545108
;

-- 2022-03-01T16:47:24.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='BPartner does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:47:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545108
;

-- 2022-03-01T16:47:27.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='BPartner does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545108
;

-- 2022-03-01T16:47:31.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='BPartner does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:47:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545108
;

-- 2022-03-01T16:47:46.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Product does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545109
;

-- 2022-03-01T16:47:49.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Product does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:47:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545109
;

-- 2022-03-01T16:47:53.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Product does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:47:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545109
;

-- 2022-03-01T16:47:59.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Product does not match with contract on line: {0}! See {1}.',Updated=TO_TIMESTAMP('2022-03-01 18:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545109
;

