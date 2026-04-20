-- Run mode: SWING_CLIENT

-- Value: AD_Printer_Config_PrintQRCode
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/label/printer_config/Label.jasper
-- 2026-04-20T09:53:20.453Z
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/label/printer_config/Label.jasper',Updated=TO_TIMESTAMP('2026-04-20 09:53:20.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585517
;

