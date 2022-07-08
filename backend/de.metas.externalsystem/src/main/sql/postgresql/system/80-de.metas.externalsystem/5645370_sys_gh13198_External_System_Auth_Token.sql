-- 2022-06-28T13:55:04.441Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541464,'S',TO_TIMESTAMP('2022-06-28 16:55:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EXTERNAL_SYSTEM_AUTHORIZATION_TOKEN',TO_TIMESTAMP('2022-06-28 16:55:04','YYYY-MM-DD HH24:MI:SS'),100,'2ac8d2ac2c0d485c970db5d96f89d809')
;

-- 2022-06-30T14:59:46.179Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='This is a sys config with an authorization token that is used to check that metasfresh has the appropriate authorization token.', Name='de.metas.externalsystem.externalservice.authorization.authToken',Updated=TO_TIMESTAMP('2022-06-30 17:59:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541464
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

-- 2022-07-05T15:43:42.067Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545139,0,TO_TIMESTAMP('2022-07-05 18:43:41','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Camel-ExtermalSystem authorization','I',TO_TIMESTAMP('2022-07-05 18:43:41','YYYY-MM-DD HH24:MI:SS'),100,'External_Systems_Authorization_Subject')
;

-- 2022-07-05T15:43:42.084Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545139 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-07-05T15:44:26.204Z
UPDATE AD_Message SET MsgText='Camel-ExtermalSystem authorization issue',Updated=TO_TIMESTAMP('2022-07-05 18:44:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545139
;

-- 2022-07-05T15:45:25.213Z
UPDATE AD_Message_Trl SET MsgText='Camel-ExtermalSystem Autorisierungsproblem',Updated=TO_TIMESTAMP('2022-07-05 18:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545139
;

-- 2022-07-05T15:46:43.151Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545140,0,TO_TIMESTAMP('2022-07-05 18:46:43','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','The sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" is not set. So Camel-ExternalSystem will not be able to authenticate into metasfresh.','I',TO_TIMESTAMP('2022-07-05 18:46:43','YYYY-MM-DD HH24:MI:SS'),100,'External_Systems_Authorization_SysConfig_Not_Found_Error')
;

-- 2022-07-05T15:46:43.151Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545140 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-07-05T15:47:06.086Z
UPDATE AD_Message_Trl SET MsgText='Die Sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" ist nicht gesetzt. Daher kann sich Camel-ExternalSystem nicht bei metasfresh authentifizieren.',Updated=TO_TIMESTAMP('2022-07-05 18:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545140
;

-- 2022-07-05T15:48:06.185Z
UPDATE AD_Message_Trl SET MsgText='Le sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" n''est pas défini. Donc Camel-ExternalSystem ne pourra pas s''authentifier dans metasfresh.',Updated=TO_TIMESTAMP('2022-07-05 18:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545140
;

-- 2022-07-05T15:48:37.757Z
UPDATE AD_Message_Trl SET MsgText='De sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" is niet ingesteld. Dus Camel-ExternalSystem zal niet in staat zijn om zich te authenticeren in metasfresh.',Updated=TO_TIMESTAMP('2022-07-05 18:48:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545140
;

-- 2022-07-05T15:49:10.760Z
UPDATE AD_Message_Trl SET MsgText='Die Sysconfig "de.metas.externalsystem.externalservice.authorization.authToken" ist nicht gesetzt. Daher kann sich Camel-ExternalSystem nicht bei metasfresh authentifizieren.',Updated=TO_TIMESTAMP('2022-07-05 18:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545140
;

-- 2022-07-05T15:50:51.128Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545141,0,TO_TIMESTAMP('2022-07-05 18:50:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Something went wrong when trying to send an authorization token to Camel-ExternalSystem. {0}','I',TO_TIMESTAMP('2022-07-05 18:50:50','YYYY-MM-DD HH24:MI:SS'),100,'ExternalSystem_Authorization_Verification_Error')
;

-- 2022-07-05T15:50:51.128Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545141 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-07-05T15:51:25.424Z
UPDATE AD_Message_Trl SET MsgText='Beim Versuch, ein Autorisierungs-Token an Camel-ExternalSystem zu senden, ist etwas schiefgegangen. {0}',Updated=TO_TIMESTAMP('2022-07-05 18:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545141
;

-- 2022-07-05T15:51:35.189Z
UPDATE AD_Message_Trl SET MsgText='Beim Versuch, ein Autorisierungs-Token an Camel-ExternalSystem zu senden, ist etwas schiefgegangen. {0}',Updated=TO_TIMESTAMP('2022-07-05 18:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545141
;

-- 2022-07-05T15:51:58.899Z
UPDATE AD_Message_Trl SET MsgText='Er ging iets mis toen ik probeerde een autorisatie token naar Camel-ExternalSystem te sturen. {0}',Updated=TO_TIMESTAMP('2022-07-05 18:51:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545141
;

-- 2022-07-05T15:52:22.496Z
UPDATE AD_Message_Trl SET MsgText='Un problème est survenu lors de l''envoi d''un jeton d''autorisation à Camel-ExternalSystem. {0}',Updated=TO_TIMESTAMP('2022-07-05 18:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545141
;

-- 2022-07-07T15:26:06.510Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specifies the authorization token used by the External Systems when connecting to Metasfresh which is hardcoded from the AD_User_AuthToken.authToken associated to the AD_User with the name ''Support, IT''.',Updated=TO_TIMESTAMP('2022-07-07 18:26:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541464
;

-- 2022-07-07T15:26:48.986Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specifies the user group id to be notified when the authorization process for the External Systems fails.',Updated=TO_TIMESTAMP('2022-07-07 18:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541465
;

-- 2022-07-07T15:43:57.961Z
UPDATE AD_UserGroup SET Description='User group to be notified when the authorization process for the External Systems fails.',Updated=TO_TIMESTAMP('2022-07-07 18:43:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UserGroup_ID=540005
;

-- 2022-07-08T15:22:54.157Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specifies the authorization token used by the External Systems when connecting to Metasfresh. The value is put into an AuthToken-Record of the User ''''Support, IT'''' (AD_User_ID=2188223).',Updated=TO_TIMESTAMP('2022-07-08 18:22:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541464
;