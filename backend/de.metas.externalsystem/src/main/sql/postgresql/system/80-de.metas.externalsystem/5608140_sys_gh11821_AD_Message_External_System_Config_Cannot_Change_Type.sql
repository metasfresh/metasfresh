-- 2021-10-06T10:11:33.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545063,0,TO_TIMESTAMP('2021-10-06 13:11:32','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Cannot change type!','I',TO_TIMESTAMP('2021-10-06 13:11:32','YYYY-MM-DD HH24:MI:SS'),100,'External_System_Config_Cannot_Change_Type')
;

-- 2021-10-06T10:11:33.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545063 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-10-06T10:12:54.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2021-10-06 13:12:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545063
;

-- 2021-10-06T10:54:00.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Cannot change external system config type!',Updated=TO_TIMESTAMP('2021-10-06 13:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545063
;

-- 2021-10-06T10:54:15.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Cannot change external system config type!',Updated=TO_TIMESTAMP('2021-10-06 13:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545063
;

-- 2021-10-06T10:54:29.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Cannot change external system config type!',Updated=TO_TIMESTAMP('2021-10-06 13:54:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545063
;

-- 2021-10-07T03:47:16.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Config type cannot be changed',Updated=TO_TIMESTAMP('2021-10-07 06:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545063
;

-- 2021-10-07T03:47:25.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Konfig-Art kann nicht mehr geändert werden',Updated=TO_TIMESTAMP('2021-10-07 06:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545063
;

-- 2021-10-07T03:47:28.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Konfig-Art kann nicht mehr geändert werden',Updated=TO_TIMESTAMP('2021-10-07 06:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545063
;

-- 2021-10-07T03:47:32.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Konfig-Art kann nicht mehr geändert werden',Updated=TO_TIMESTAMP('2021-10-07 06:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545063
;

-- 2021-10-07T03:54:00.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Die Konfig-Art kann nicht mehr geändert werden',Updated=TO_TIMESTAMP('2021-10-07 06:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545063
;

