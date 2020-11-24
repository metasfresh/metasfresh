
-- 07.12.2015 14:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540917,'S',TO_TIMESTAMP('2015-12-07 14:51:06','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y and a print job fails due to an error retured by the printing client, then the user for whoom the print was intended (AD_User_ToPrint) is notified.','de.metas.printing','Y','de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnError',TO_TIMESTAMP('2015-12-07 14:51:06','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

------------------------------

-- 08.12.2015 10:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543723,0,TO_TIMESTAMP('2015-12-08 10:34:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Druckclient meldet einen Fehler','E',TO_TIMESTAMP('2015-12-08 10:34:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing.ClientReturnedError')
;

-- 08.12.2015 10:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543723 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 08.12.2015 10:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.printing.ClientReturnsError',Updated=TO_TIMESTAMP('2015-12-08 10:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543723
;

-- 08.12.2015 10:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.printing.C_Print_Job_Instructions.ClientReportsPrintError',Updated=TO_TIMESTAMP('2015-12-08 10:35:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543723
;

