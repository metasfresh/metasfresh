-- 2019-03-26T14:04:22.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541273,'S',TO_TIMESTAMP('2019-03-26 14:04:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','de.metas.ui.web.spring.profiles.active1',TO_TIMESTAMP('2019-03-26 14:04:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2019-03-26T14:07:20.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541274,'S',TO_TIMESTAMP('2019-03-26 14:07:20','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','de.metas.spring.profiles.active1',TO_TIMESTAMP('2019-03-26 14:07:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2019-03-26T14:32:10.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544886,0,TO_TIMESTAMP('2019-03-26 14:32:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','XML-Rechnungsantwort mit Belegnummer {0} und Erstellungszeitstempel {1}sec konnte keine metafresh Rechnung gefunden werden','E',TO_TIMESTAMP('2019-03-26 14:32:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceResponseRepo_Invoice_Not_Found')
;

-- 2019-03-26T14:32:10.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544886 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-03-26T15:38:27.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Zur XML-Rechnungsantwort mit Belegnummer {0} und Erstellungszeitstempel {1}sec konnte keine metafresh Rechnung gefunden werden',Updated=TO_TIMESTAMP('2019-03-26 15:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544886
;

-- 2019-03-26T15:38:36.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zur XML-Rechnungsantwort mit Belegnummer {0} und Erstellungszeitstempel {1}sec konnte keine metafresh Rechnung gefunden werden',Updated=TO_TIMESTAMP('2019-03-26 15:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544886
;

-- 2019-03-26T15:39:41.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Unable to find a metasfresh invoice that matches the XML invoice response with document no {0} and creation timestamp {2}s',Updated=TO_TIMESTAMP('2019-03-26 15:39:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544886
;

-- 2019-03-26T15:40:00.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zur XML-Rechnungsantwort mit Belegnummer {0} und Erstellungszeitstempel {1}sec konnte keine metafresh Rechnung gefunden werden',Updated=TO_TIMESTAMP('2019-03-26 15:40:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544886
;

-- 2019-03-26T15:40:09.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Unable to find a metasfresh invoice that matches the XML invoice response with document no {0} and creation timestamp {2}sec',Updated=TO_TIMESTAMP('2019-03-26 15:40:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544886
;


-- 2019-03-26T21:38:18.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544889,0,TO_TIMESTAMP('2019-03-26 21:38:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Not all files were sucessfully imported. Click on this message to check the process revision log for details.','E',TO_TIMESTAMP('2019-03-26 21:38:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse_NotAllFilesImported')
;

-- 2019-03-26T21:38:18.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544889 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-03-26T21:39:36.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Einige Dateien konnten nicht importiert werden. Clicke auf diese Nachricht, um für weitere Infos die Prozess-Revision zu öffnen.',Updated=TO_TIMESTAMP('2019-03-26 21:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544889
;

-- 2019-03-26T21:39:50.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Einige Dateien konnten nicht importiert werden. Clicke auf diese Nachricht, um für weitere Infos die Prozess-Revision zu öffnen.',Updated=TO_TIMESTAMP('2019-03-26 21:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544889
;

-- 2019-03-26T21:41:45.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.NotAllFilesImportedNotification',Updated=TO_TIMESTAMP('2019-03-26 21:41:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544889
;

-- 2019-03-26T21:42:19.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544890,0,TO_TIMESTAMP('2019-03-26 21:42:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Einige Dateien konnten nicht importiert werden.','E',TO_TIMESTAMP('2019-03-26 21:42:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse.NotAllFilesImported')
;

-- 2019-03-26T21:42:19.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544890 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-03-26T21:42:24.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-26 21:42:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544890
;

-- 2019-03-26T21:42:49.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Not all files were sucessfully imported.',Updated=TO_TIMESTAMP('2019-03-26 21:42:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544890
;

-- 2019-03-27T06:40:16.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544891,0,TO_TIMESTAMP('2019-03-27 06:40:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Zur XML-Rechnungsantwort mit Rechnungs-ID (C_Invoice_ID) {0} konnte keine metafresh Rechnung gefunden werden','E',TO_TIMESTAMP('2019-03-27 06:40:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceResponseRepo_Invoice_Not_Found_By_Id')
;

-- 2019-03-27T06:40:16.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544891 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

