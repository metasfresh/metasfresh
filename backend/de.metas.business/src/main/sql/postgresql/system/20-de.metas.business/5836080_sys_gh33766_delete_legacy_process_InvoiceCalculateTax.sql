-- Name: C_Invoce Calculate Tax (menu: "Steuer berechnen")
-- Action Type: P
-- Process: C_Invoce Calculate Tax(org.eevolution.process.InvoiceCalculateTax)
-- Legacy process, removed together with its Java class org.eevolution.process.InvoiceCalculateTax (AD_Process_ID=53072, AD_Menu_ID=53093, AD_Process_Para_ID=53134)
-- 2026-11-16T18:00:00.000Z
DELETE FROM AD_Menu_Trl WHERE AD_Menu_ID=53093
;

DELETE FROM AD_Menu WHERE AD_Menu_ID=53093
;

DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53093 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID=53134
;

DELETE FROM AD_Process_Para WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Trl WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process WHERE AD_Process_ID=53072
;
