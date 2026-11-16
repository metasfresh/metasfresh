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

-- defensive: detach/delete rows that would block the AD_Process delete (FKs without ON DELETE CASCADE); no-ops where nothing references the process
DELETE FROM AD_Table_Process WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Access WHERE AD_Process_ID=53072
;

UPDATE AD_Column SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE AD_Tab SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE AD_Issue SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE AD_Archive SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID=53134
;

DELETE FROM AD_Process_Para WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Trl WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process WHERE AD_Process_ID=53072
;
