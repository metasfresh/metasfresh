-- Name: Auswertung Produktkosten
-- Action Type: R
-- Report: Product costs per date(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-05-14T09:35:51.252Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=542123
;

-- 2025-05-14T09:35:51.558Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=542123
;

-- 2025-05-14T09:35:51.708Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=542123 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Name: Auswertung Produktkosten (Excel)
-- Action Type: R
-- Report: Product costs per date (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2025-05-14T09:35:51.252Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=542130
;

-- 2025-05-14T09:35:51.558Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=542130
;

-- 2025-05-14T09:35:51.708Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=542130 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: Auswertung Produktkosten (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-05-14T09:39:17.949Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=585339
;

-- 2025-05-14T09:39:18.253Z
DELETE FROM AD_Process WHERE AD_Process_ID=585339
;

-- Value: Product costs per date
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/productcosts/report.jasper
-- 2025-05-14T09:39:31.036Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=585331
;

-- 2025-05-14T09:39:31.346Z
DELETE FROM AD_Process WHERE AD_Process_ID=585331
;

