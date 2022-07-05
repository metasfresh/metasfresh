-- 2022-06-28T13:55:04.441Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541464,'S',TO_TIMESTAMP('2022-06-28 16:55:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EXTERNAL_SYSTEM_AUTHORIZATION_TOKEN',TO_TIMESTAMP('2022-06-28 16:55:04','YYYY-MM-DD HH24:MI:SS'),100,'2ac8d2ac2c0d485c970db5d96f89d809')
;

-- 2022-06-30T14:59:46.179Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='This is a sys config with an authorization token that is used to check that metasfresh has the appropriate authorization token.', Name='de.metas.externalsystem.externalservice.authorization.authToken',Updated=TO_TIMESTAMP('2022-06-30 17:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541464
;

-- 2022-07-01T13:31:59.527Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545136,0,TO_TIMESTAMP('2022-07-01 16:31:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Metasfresh Authorization Token for External Systems','I',TO_TIMESTAMP('2022-07-01 16:31:59','YYYY-MM-DD HH24:MI:SS'),100,'External_Systems_Authorization')
;

-- 2022-07-01T13:31:59.527Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545136 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-07-01T13:35:17.592Z
UPDATE AD_Message_Trl SET MsgText='Metasfresh-Autorisierungstoken für externe Systeme',Updated=TO_TIMESTAMP('2022-07-01 16:35:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545136
;

-- 2022-07-01T13:37:14.815Z
UPDATE AD_Message SET Value='External_Systems_Authorization_Subject',Updated=TO_TIMESTAMP('2022-07-01 16:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545136
;

-- 2022-07-01T13:40:54.829Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545137,0,TO_TIMESTAMP('2022-07-01 16:40:54','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','The authorization token could not be verified by metasfresh using sysconfig.','I',TO_TIMESTAMP('2022-07-01 16:40:54','YYYY-MM-DD HH24:MI:SS'),100,'ExternalSystem_Authorization_Verification_Error')
;

-- 2022-07-01T13:40:54.830Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545137 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-07-01T13:41:40.623Z
UPDATE AD_Message_Trl SET MsgText='Das Autorisierungstoken konnte von metasfresh mit sysconfig nicht verifiziert werden.',Updated=TO_TIMESTAMP('2022-07-01 16:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545137
;

-- 2022-07-01T13:44:53.098Z
UPDATE AD_Message SET MsgText='The authorization token could not be verified by metasfresh using sysconfig "de.metas.externalsystem.externalservice.authorization.authToken".',Updated=TO_TIMESTAMP('2022-07-01 16:44:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545137
;

-- 2022-07-01T13:45:01.126Z
UPDATE AD_Message_Trl SET MsgText='Das Autorisierungstoken konnte von metasfresh mit sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" nicht verifiziert werden.',Updated=TO_TIMESTAMP('2022-07-01 16:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545137
;

-- 2022-07-01T13:47:56.119Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545138,0,TO_TIMESTAMP('2022-07-01 16:47:55','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','The sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" was not found in metasfresh database.','I',TO_TIMESTAMP('2022-07-01 16:47:55','YYYY-MM-DD HH24:MI:SS'),100,'External_Systems_Authorization_SysConfig_Not_Found_Error')
;

-- 2022-07-01T13:47:56.119Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545138 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-07-01T13:48:16.123Z
UPDATE AD_Message_Trl SET MsgText='Die Sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" wurde in der metasfresh-Datenbank nicht gefunden.',Updated=TO_TIMESTAMP('2022-07-01 16:48:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545138
;

-- 2022-07-01T14:27:12.019Z
INSERT INTO AD_UserGroup (AD_Client_ID,AD_Org_ID,AD_UserGroup_ID,Created,CreatedBy,IsActive,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540005,TO_TIMESTAMP('2022-07-01 17:27:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','API-Setup',TO_TIMESTAMP('2022-07-01 17:27:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-01T14:27:15.050Z
INSERT INTO AD_UserGroup_User_Assign (AD_Client_ID,AD_Org_ID,AD_User_ID,AD_UserGroup_ID,AD_UserGroup_User_Assign_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,100,540005,540003,TO_TIMESTAMP('2022-07-01 17:27:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2022-07-01 17:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-05T09:17:19.378Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541465,'S',TO_TIMESTAMP('2022-07-05 12:17:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.externalsystem.externalservice.authorization.notificationUserGroupId',TO_TIMESTAMP('2022-07-05 12:17:19','YYYY-MM-DD HH24:MI:SS'),100,'540005')
;

-- 2022-07-05T09:38:01.459Z
UPDATE AD_Message SET MsgText='The authorization token could not be verified by metasfresh using sysconfig "de.metas.externalsystem.externalservice.authorization.authToken". Error details : "{0}" .',Updated=TO_TIMESTAMP('2022-07-05 12:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545137
;

-- 2022-07-05T09:41:16.989Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2022-07-05 12:41:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545137
;

-- 2022-07-05T09:42:03.048Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2022-07-05 12:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545138
;

-- 2022-07-05T10:42:44.220Z
UPDATE AD_Message SET MsgText='Something went wrong when trying to send an authorization token to Camel-ExternalSystem. {0}
',Updated=TO_TIMESTAMP('2022-07-05 13:42:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545137
;

-- 2022-07-05T10:44:06.523Z
UPDATE AD_Message_Trl SET MsgText='Beim Versuch, ein Autorisierungstoken an Camel-ExternalSystem zu senden, ist ein Fehler aufgetreten. {0}',Updated=TO_TIMESTAMP('2022-07-05 13:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545137
;

-- 2022-07-05T10:45:57.254Z
UPDATE AD_Message SET MsgText='The sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" is not set. So Camel-ExternalSystem will not be able to authenticate into metasfresh.',Updated=TO_TIMESTAMP('2022-07-05 13:45:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545138
;

-- 2022-07-05T10:46:06.188Z
UPDATE AD_Message_Trl SET MsgText='Die Sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" ist nicht gesetzt. Daher kann sich Camel-ExternalSystem nicht bei metasfresh authentifizieren.',Updated=TO_TIMESTAMP('2022-07-05 13:46:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545138
;