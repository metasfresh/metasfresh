-- Name: C_Invoce Calculate Tax (menu: "Steuer berechnen")
-- Action Type: P
-- Process: C_Invoce Calculate Tax(org.eevolution.process.InvoiceCalculateTax)
-- Legacy process, removed together with its Java class org.eevolution.process.InvoiceCalculateTax (AD_Process_ID=53072, AD_Menu_ID=53093, AD_Process_Para_ID=53134)
-- 2026-11-16T18:00:00.000Z

-- Removes only the application-dictionary rows of the process, the same way earlier legacy-process removals do.
-- Operator data is not touched:
--   * rows without a foreign key to AD_Process / AD_Menu stay as they are (AD_PInstance, AD_Process_Access, AD_Process_Stats, AD_TreeBar);
--   * any other row that still references the process or its menu entry (e.g. a print queue entry, a role root menu, an export config) makes this migration fail on purpose, so that a human looks at it.
-- ON DELETE CASCADE: AD_Process_Para(_Trl), AD_Process_Trl, AD_Scheduler (with its log and parameters), AD_Role_PermRequest, AD_WF_Node_Para; ON DELETE SET NULL: AD_Column, AD_Issue.
-- A scheduler of this process that has already run (AD_PInstance.AD_Scheduler_ID) or has recipients also makes the migration fail.

DELETE FROM AD_Menu_Trl WHERE AD_Menu_ID=53093
;

DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53093 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

DELETE FROM AD_Menu WHERE AD_Menu_ID=53093
;

DELETE FROM AD_Table_Process WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID IN (SELECT AD_Process_Para_ID FROM AD_Process_Para WHERE AD_Process_ID=53072)
;

DELETE FROM AD_Process_Para WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Trl WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process WHERE AD_Process_ID=53072
;
