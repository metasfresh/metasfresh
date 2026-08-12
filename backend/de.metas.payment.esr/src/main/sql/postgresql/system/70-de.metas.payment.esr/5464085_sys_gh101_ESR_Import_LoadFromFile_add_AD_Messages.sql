
-- 2017-06-01T08:41:42.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544330,0,TO_TIMESTAMP('2017-06-01 08:41:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Der Datentyp der zu importierenden Datei konnte nicht anhand des Dateinamens festgestellt werden. Bitte setzen Sie den Typ im entsprechenden Feld des Import-Datensates.','E',TO_TIMESTAMP('2017-06-01 08:41:42','YYYY-MM-DD HH24:MI:SS'),100,'ESR_Import_LoadFromFile.CantGuessFileType')
;

-- 2017-06-01T08:41:42.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544330 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-06-01T08:44:17.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544331,0,TO_TIMESTAMP('2017-06-01 08:44:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Der Dateiname der zu importierenden Datei deutet auf einen anderen Datentyp hin als der Typ, der im Import-Datensatz angestellt ist. Bitte überprüfen Sie den Typ und korrigieren gegebenenfalls die Einstellung.','E',TO_TIMESTAMP('2017-06-01 08:44:17','YYYY-MM-DD HH24:MI:SS'),100,'ESR_Import_LoadFromFile.InconsitentTypes')
;

-- 2017-06-01T08:44:17.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544331 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-06-01T08:44:31.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Datentyp der zu importierenden Datei konnte nicht anhand des Dateinamens festgestellt werden. Bitte setzen Sie den Typ im entsprechenden Feld des Import-Datensatzes.',Updated=TO_TIMESTAMP('2017-06-01 08:44:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544330
;

-- 2017-06-01T08:44:31.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544330
;

