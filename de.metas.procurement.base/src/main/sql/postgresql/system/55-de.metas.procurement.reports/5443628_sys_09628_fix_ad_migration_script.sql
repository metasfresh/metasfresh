
UPDATE AD_MigrationScript 
SET ProjectName='55-de.metas.procurement.reports', Name=Replace(Name, '75-de.metas.fresh.reports', '55-de.metas.procurement.reports')
WHERE Name='75-de.metas.fresh.reports->5443630_sys_09628_Anbauplanung_Auswertung_report_process.sql'
;
