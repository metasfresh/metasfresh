--
-- rename from -de.metas.adempiere.adempiereJasper
--
UPDATE AD_MigrationScript 
SET 
	Name=replace(Name, '42-de.metas.adempiere.adempiereJasper.server', '42-de.metas.report.jasper.server'),
	ProjectName=replace(ProjectName, '42-de.metas.adempiere.adempiereJasper.server', '42-de.metas.report.jasper.server')
WHERE ProjectName='42-de.metas.adempiere.adempiereJasper.server'
;
