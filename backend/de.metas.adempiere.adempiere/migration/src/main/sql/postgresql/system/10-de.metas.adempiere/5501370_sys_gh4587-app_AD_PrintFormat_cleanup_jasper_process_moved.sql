

-- 2018-09-13T13:37:31.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='AD_Process.IsReport=''Y''',Updated=TO_TIMESTAMP('2018-09-13 13:37:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=400
;

-- 2018-09-13T13:37:47.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='AD_Process_Report',Updated=TO_TIMESTAMP('2018-09-13 13:37:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=400
;

-- clean up AD_Printformat.Classname
delete from ad_ui_element where ad_field_id in (select ad_field_id from ad_field where ad_column_id=52068);
delete from ad_field where ad_column_id=52068;
-- 2018-09-13T13:26:21.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=52068
;
-- 2018-09-13T13:26:21.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=52068
;
select db_alter_table('AD_PrintFormat', 'ALTER TABLE AD_PrintFormat DROP COLUMN IF EXISTS ClassName;');


UPDATE AD_Process SET ClassName = 'de.metas.report.jasper.client.process.JasperReportStarter' WHERE ClassName = 'org.compiere.report.ReportStarter';
UPDATE AD_Process SET ClassName = 'de.metas.report.jasper.client.process.JasperReportStarter' WHERE ClassName = 'org.compiere.report.ReportStarter';
UPDATE AD_Process SET ClassName = 'de.metas.report.jasper.client.process.JasperReportStarter' WHERE ClassName = 'de.metas.report.ReportStarter';
