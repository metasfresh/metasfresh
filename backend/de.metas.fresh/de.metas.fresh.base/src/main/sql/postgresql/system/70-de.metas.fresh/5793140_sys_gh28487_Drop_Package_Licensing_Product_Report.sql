-- Run mode: SWING_CLIENT

-- Name: Produkt Verpackungslizenzierung Export
-- Action Type: R
-- Report: Package-Licensing-Product-Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-03-09T09:35:23.151Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=542300
;

-- 2026-03-09T09:35:23.498Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=542300
;

-- 2026-03-09T09:35:23.619Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=542300 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: Package-Licensing-Product-Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-03-09T09:36:33.993Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=585578
;

-- 2026-03-09T09:36:34.732Z
DELETE FROM AD_Process WHERE AD_Process_ID=585578
;

DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric);
