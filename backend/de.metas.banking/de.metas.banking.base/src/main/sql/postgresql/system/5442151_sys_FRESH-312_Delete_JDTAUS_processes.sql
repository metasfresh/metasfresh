drop table if exists TMP_Process_ToDelete;
create temporary table TMP_Process_ToDelete as select * from AD_Process
where classname in (
	'de.schaeffer.compiere.process.RemittanceExport'
	, 'de.schaeffer.compiere.process.DirectDebitExport'
	, 'de.metas.banking.process.PaySelectionExportDTA'
	, 'de.schaeffer.compiere.process.RemittanceCollectionExport'
);
create index on TMP_Process_ToDelete(AD_Process_ID);

delete from AD_Menu p where exists (select 1 from TMP_Process_ToDelete t where t.AD_Process_ID=p.AD_Process_ID);
delete from AD_Process p where exists (select 1 from TMP_Process_ToDelete t where t.AD_Process_ID=p.AD_Process_ID);

