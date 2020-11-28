-- 2018-07-13T15:38:56.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.login.forgotPassword.caption','de.metas.ui.web',544759,'Forgot password',0,100,TO_TIMESTAMP('2018-07-13 15:38:56','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 15:38:56','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T15:38:56.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544759 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-13T15:39:04.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-13 15:39:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544759 AND AD_Language='en_US'
;

-- 2018-07-13T15:45:24.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.forgotPassword.email.caption','de.metas.ui.web',544760,'Email',0,100,TO_TIMESTAMP('2018-07-13 15:45:24','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 15:45:24','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T15:45:24.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544760 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-13T15:49:40.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.forgotPassword.sendResetCode.caption','de.metas.ui.web',544761,'Send reset code',0,100,TO_TIMESTAMP('2018-07-13 15:49:40','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 15:49:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T15:49:40.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544761 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-13T15:56:07.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.forgotPassword.newPassword.caption','de.metas.ui.web',544762,'New password',0,100,TO_TIMESTAMP('2018-07-13 15:56:06','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 15:56:06','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T15:56:07.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544762 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-13T15:57:44.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.forgotPassword.retypeNewPassword.caption','de.metas.ui.web',544763,'Retype new password',0,100,TO_TIMESTAMP('2018-07-13 15:57:43','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 15:57:43','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T15:57:44.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544763 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-13T15:58:52.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.forgotPassword.changePassword.caption','de.metas.ui.web',544764,'Change password',0,100,TO_TIMESTAMP('2018-07-13 15:58:52','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 15:58:52','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T15:58:52.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544764 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-13T16:01:08.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.forgotPassword.resetCodeSent.caption','de.metas.ui.web',544765,'Password reset instructions were sent to your email address.',0,100,TO_TIMESTAMP('2018-07-13 16:01:08','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 16:01:08','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T16:01:08.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544765 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-07-13T16:15:18.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('I',0,'Y',100,'webui.forgotPassword.error.retypedNewPasswordNotMatch','U',544766,'Retyped new password does not match.',0,100,TO_TIMESTAMP('2018-07-13 16:15:18','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-07-13 16:15:18','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-07-13T16:15:18.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544766 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

