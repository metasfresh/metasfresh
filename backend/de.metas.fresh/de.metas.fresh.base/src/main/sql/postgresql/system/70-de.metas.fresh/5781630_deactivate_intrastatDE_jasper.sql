-- Run mode: SWING_CLIENT

-- Only deactivate because we have customizations of this process

-- Value: Report for Intratrade Germany
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/intratrade/report.jasper
-- 2025-12-17T08:51:44.891Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2025-12-17 08:51:44.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=584656
;

-- Name: Bericht für Intrahandel DE
-- Action Type: R
-- Report: Report for Intratrade Germany(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-12-17T08:51:45.045Z
UPDATE AD_Menu SET Description='Bericht für Intrahandel DE ', IsActive='N', Name='Bericht für Intrahandel DE',Updated=TO_TIMESTAMP('2025-12-17 08:51:45.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=541438
;

-- 2025-12-17T08:51:45.051Z
UPDATE AD_Menu_Trl trl SET Description='Bericht für Intrahandel DE ' WHERE AD_Menu_ID=541438 AND AD_Language='de_DE'
;
