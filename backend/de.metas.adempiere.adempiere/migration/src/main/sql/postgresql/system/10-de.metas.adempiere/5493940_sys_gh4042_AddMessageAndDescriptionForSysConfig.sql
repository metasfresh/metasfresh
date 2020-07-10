-- 2018-05-18T14:14:43.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='BPartnerImportProcess_BPPrintFormatId',Updated=TO_TIMESTAMP('2018-05-18 14:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541211
;

-- 2018-05-18T14:17:28.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544730,0,TO_TIMESTAMP('2018-05-18 14:17:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The system config variable  BPartnerImportProcess_BPPrintFormatId responsible for giving is AD_PrintFormat_ID is not found!','E',TO_TIMESTAMP('2018-05-18 14:17:28','YYYY-MM-DD HH24:MI:SS'),100,'BPartnerImportProcess_BPPrintFormatId_ErrorMsg')
;

-- 2018-05-18T14:17:28.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544730 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-05-18T14:18:39.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This system variable should contain an id from AD_PrintFormat_ID.',Updated=TO_TIMESTAMP('2018-05-18 14:18:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541211
;

