-- Run mode: SWING_CLIENT

-- Name: Speditionsauftrag
-- Action Type: R
-- Report: Speditionsauftrag (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2025-07-18T10:18:16.296Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540062
;

-- 2025-07-18T10:18:16.298Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=540062
;

-- 2025-07-18T10:18:16.304Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540062 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

UPDATE ad_archive
SET ad_process_id = 540061, Updated=TO_TIMESTAMP('2025-08-05 17:11:51.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=99
WHERE ad_process_id = 540046
;

-- Value: Speditionsauftrag (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/sales/shippingorder/report.jasper
-- 2025-07-18T10:18:55.554Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540046
;

-- 2025-07-18T10:18:55.556Z
DELETE FROM AD_Process WHERE AD_Process_ID=540046
;

