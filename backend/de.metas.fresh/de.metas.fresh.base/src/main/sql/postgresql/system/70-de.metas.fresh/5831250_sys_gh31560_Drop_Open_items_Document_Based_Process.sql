-- Run mode: SWING_CLIENT

-- Name: Offene Posten (Belegbasiert) -- Open items (Document Based)
-- Action Type: R
-- Report: opent_items_doc_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-10-23T10:48:31.842Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540948
;

-- 2026-10-23T10:48:32.206Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=540948
;

-- 2026-10-23T10:48:32.334Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540948 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Name: Offene Posten (Belegbasiert)
-- Action Type: R
-- Report: opent_items_doc_based(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2026-10-23T10:52:47.445Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=243
;

-- 2026-10-23T10:52:47.815Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=243
;

-- 2026-10-23T10:52:47.944Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=243 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: opent_items_doc_based
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/openitems/report.jasper
-- 2026-10-23T10:53:28.240Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=500174
;

-- 2026-10-23T10:53:28.609Z
DELETE FROM AD_Process WHERE AD_Process_ID=500174
;

