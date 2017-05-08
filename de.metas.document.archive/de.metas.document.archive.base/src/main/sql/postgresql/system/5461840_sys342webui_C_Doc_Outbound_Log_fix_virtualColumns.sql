-- 2017-05-04T19:50:07.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT "de.metas.edi".getEDIExportStatus(C_Doc_Outbound_Log.AD_Table_ID, C_Doc_Outbound_Log.Record_ID))',Updated=TO_TIMESTAMP('2017-05-04 19:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551500
;

-- 2017-05-04T20:06:36.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT getDocumentPOReference(C_Doc_Outbound_Log.AD_Table_ID, C_Doc_Outbound_Log.Record_ID))',Updated=TO_TIMESTAMP('2017-05-04 20:06:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551964
;

