-- 2022-10-17T07:01:44.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545165,0,TO_TIMESTAMP('2022-10-17 10:01:43','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Non ''Cleared'' HUs cannot be issued!','I',TO_TIMESTAMP('2022-10-17 10:01:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.pporder.IssuingNotClearedHUsNotAllowed')
;

-- 2022-10-17T07:01:44.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545165 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-10-17T07:18:29.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-10-17 10:18:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545165
;

-- 2022-10-17T07:29:58.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2022-10-17 10:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545165
;

-- 2022-10-17T07:34:01.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545166,0,TO_TIMESTAMP('2022-10-17 10:34:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Already issued','E',TO_TIMESTAMP('2022-10-17 10:34:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.pporder.AlreadyIssuedError')
;

-- 2022-10-17T07:34:01.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545166 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-10-17T08:37:51.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='HUs ohne Freigabe können nicht ausgegeben werden.',Updated=TO_TIMESTAMP('2022-10-17 11:37:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545165
;

-- 2022-10-17T08:37:55.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HUs ohne Freigabe können nicht ausgegeben werden.',Updated=TO_TIMESTAMP('2022-10-17 11:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545165
;

-- 2022-10-17T08:38:01.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HUs ohne Freigabe können nicht ausgegeben werden.',Updated=TO_TIMESTAMP('2022-10-17 11:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545165
;

-- 2022-10-17T08:38:14.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='HUs without clearance cannot be issued.',Updated=TO_TIMESTAMP('2022-10-17 11:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545165
;

-- 2022-10-17T08:38:19.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HUs without clearance cannot be issued.',Updated=TO_TIMESTAMP('2022-10-17 11:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545165
;

-- 2022-10-17T08:38:22.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='HUs without clearance cannot be issued.',Updated=TO_TIMESTAMP('2022-10-17 11:38:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545165
;

-- 2022-10-17T08:38:57.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='HU wurde bereits ausgegeben.',Updated=TO_TIMESTAMP('2022-10-17 11:38:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545166
;

-- 2022-10-17T08:39:04.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU wurde bereits ausgegeben.',Updated=TO_TIMESTAMP('2022-10-17 11:39:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545166
;

-- 2022-10-17T08:39:08.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU wurde bereits ausgegeben.',Updated=TO_TIMESTAMP('2022-10-17 11:39:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545166
;

-- 2022-10-17T08:39:18.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='HU has already been issued.',Updated=TO_TIMESTAMP('2022-10-17 11:39:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545166
;

-- 2022-10-17T08:39:21.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU has already been issued.',Updated=TO_TIMESTAMP('2022-10-17 11:39:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545166
;

-- 2022-10-17T08:39:25.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='HU has already been issued.',Updated=TO_TIMESTAMP('2022-10-17 11:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545166
;

