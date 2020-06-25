
-- 11.12.2015 15:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543726,0,TO_TIMESTAMP('2015-12-11 15:42:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Ein Druck-Job ist schon zu lange im Status "Im Druck"','E',TO_TIMESTAMP('2015-12-11 15:42:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing.C_Print_Job_Instructions.ClientSendTimeOut')
;

-- 11.12.2015 15:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543726 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 11.12.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543727,0,TO_TIMESTAMP('2015-12-11 15:44:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Der Status steht seit mindestens {0} Sekunden auf "Im Druck".','E',TO_TIMESTAMP('2015-12-11 15:44:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing.C_Print_Job_Instructions.ClientSendTimeOutDetails')
;

-- 11.12.2015 15:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543727 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 11.12.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Status steht seit mindestens {0} Sekunden auf {1}.', Value='de.metas.printing.C_Print_Job_Instructions.ClientStatusTimeOutDetails',Updated=TO_TIMESTAMP('2015-12-11 15:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543727
;

-- 11.12.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543727
;

-- 11.12.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Ein Druck-Job ist schon zu lange im Status {0}.', Value='de.metas.printing.C_Print_Job_Instructions.ClientStatusTimeOut',Updated=TO_TIMESTAMP('2015-12-11 15:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543726
;

-- 11.12.2015 15:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543726
;

-- 11.12.2015 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Ein Druck-Job wurde zu lange nicht weiter verarbeitet.',Updated=TO_TIMESTAMP('2015-12-11 15:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543726
;

-- 11.12.2015 15:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543726
;

----------------------------------

-- 11.12.2015 16:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540926,'S',TO_TIMESTAMP('2015-12-11 16:00:11','YYYY-MM-DD HH24:MI:SS'),100,'Integer value. If greater than zero, then when a C_Print_Job_Instruction is sent to the printing client, the system will check after the specified number of seconds, whether the printing client returned any result. If not, the user who was supposed to receive the printout will be notified, according to their settings.','de.metas.printing','Y','de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnSendTimeoutSeconds',TO_TIMESTAMP('2015-12-11 16:00:11','YYYY-MM-DD HH24:MI:SS'),100,'0')
;

-- 11.12.2015 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540927,'S',TO_TIMESTAMP('2015-12-11 16:01:50','YYYY-MM-DD HH24:MI:SS'),100,'Integer value. If greater than zero, then when a C_Print_Job_Instruction is created (inititally with status "pending"), then the system will check after the specified number of seconds, whether status is still "pending". If so, then the user who was supposed to receive the printout will be notified, according to their settings.','de.metas.printing','Y','de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnPendingTimeoutSeconds',TO_TIMESTAMP('2015-12-11 16:01:50','YYYY-MM-DD HH24:MI:SS'),100,'0')
;

-- 11.12.2015 16:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='120',Updated=TO_TIMESTAMP('2015-12-11 16:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540927
;

-- 11.12.2015 16:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='120',Updated=TO_TIMESTAMP('2015-12-11 16:02:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540926
;

