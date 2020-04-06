--
-- tweak AD_PrintFormat.JasperProcess_ID
-- 2018-11-23T12:34:39.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2018-11-23 12:34:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=50209
;

-- 2018-11-23T12:38:19.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Type='JasperReports',Updated=TO_TIMESTAMP('2018-11-23 12:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540978
;

-- make sure that the processes we know to be jasper processes are flagged correctly
UPDATE AD_Process 
SET Type='JasperReports', IsReport='Y', Updated=now(), UpdatedBy=99
WHERE ClassName='de.metas.report.jasper.client.process.JasperReportStarter' AND (Type!='JasperReports' OR IsReport!='Y');
