update AD_Process set classname='de.metas.process.ExecuteUpdateSQL' where classname='de.metas.adempiere.process.ExecuteUpdateSQL';
update AD_Process set classname='de.metas.process.processtools.AD_Process_Para_UpdateFromAnnotations' where classname='org.adempiere.ad.process.AD_Process_Para_UpdateFromAnnotations';
update AD_Process set classname='de.metas.process.processtools.AD_Process_Copy' where classname='org.compiere.process.CopyReportProcess';

-- Callout_AD_Process_Para
delete from AD_ColumnCallout where AD_ColumnCallout_ID=50186;

