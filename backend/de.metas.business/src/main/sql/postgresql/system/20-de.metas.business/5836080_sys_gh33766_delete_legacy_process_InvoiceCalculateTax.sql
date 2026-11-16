-- Name: C_Invoce Calculate Tax (menu: "Steuer berechnen")
-- Action Type: P
-- Process: C_Invoce Calculate Tax(org.eevolution.process.InvoiceCalculateTax)
-- Legacy process, removed together with its Java class org.eevolution.process.InvoiceCalculateTax (AD_Process_ID=53072, AD_Menu_ID=53093, AD_Process_Para_ID=53134)
-- 2026-11-16T18:00:00.000Z

-- Cleanup of everything that references the process (AD_Process_ID=53072) or its menu entries, so the final DELETE cannot fail on a customer DB.
-- Covers every foreign key to AD_Process / AD_Menu / AD_Process_Para of the application dictionary (pg_catalog, checked on a local stack).
-- ON DELETE CASCADE / SET NULL FKs need no cleanup (AD_Scheduler, AD_Role_PermRequest, AD_Process_Trl, AD_Process_Para(_Trl), AD_Scheduler_Para, AD_WF_Node_Para via AD_Process_Para, AD_Menu_Trl, AD_Column, AD_Issue).
-- Idempotent: every statement is a no-op when nothing references the process.

-- 1. Menu entries calling the process (AD_Menu_ID=53093 and any other menu entry with AD_Process_ID=53072), their tree nodes, favourites and role root menus
UPDATE AD_Role SET Root_Menu_ID=NULL WHERE Root_Menu_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Menu_ID=53093 OR AD_Process_ID=53072)
;

DELETE FROM AD_TreeBar WHERE Node_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Menu_ID=53093 OR AD_Process_ID=53072)
;

DELETE FROM AD_TreeNodeMM n WHERE Node_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Menu_ID=53093 OR AD_Process_ID=53072) AND EXISTS (SELECT 1 FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

DELETE FROM AD_Menu_Trl WHERE AD_Menu_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Menu_ID=53093 OR AD_Process_ID=53072)
;

DELETE FROM AD_Menu WHERE AD_Menu_ID=53093 OR AD_Process_ID=53072
;

-- 2. Rows that are meaningless without the process (mandatory FK) -> delete (with their dependents)
DELETE FROM AD_Table_Process WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Access WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Stats WHERE AD_Process_ID=53072
;

DELETE FROM M_HU_Process WHERE AD_Process_ID=53072
;

DELETE FROM M_HU_Label_Config WHERE LabelReport_Process_ID=53072
;

DELETE FROM ExternalSystem_ScriptedExportConversion_Status WHERE ExternalSystem_Config_ScriptedExportConversion_ID IN (SELECT ExternalSystem_Config_ScriptedExportConversion_ID FROM ExternalSystem_Config_ScriptedExportConversion WHERE AD_Process_OutboundData_ID=53072)
;

DELETE FROM EDI_EPCIS_Transmitted_SSCC WHERE ExternalSystem_Config_ScriptedExportConversion_ID IN (SELECT ExternalSystem_Config_ScriptedExportConversion_ID FROM ExternalSystem_Config_ScriptedExportConversion WHERE AD_Process_OutboundData_ID=53072)
;

DELETE FROM ExternalSystem_Config_ScriptedExportConversion WHERE AD_Process_OutboundData_ID=53072
;

-- 3. Optional references -> detach
UPDATE AD_Tab SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE AD_WF_Node SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE AD_Archive SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE AD_Boilerplate SET JasperProcess_ID=NULL WHERE JasperProcess_ID=53072
;

UPDATE AD_MailConfig SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE AD_PrinterRouting SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE AD_PrintFormat SET JasperProcess_ID=NULL WHERE JasperProcess_ID=53072
;

UPDATE AD_WorkbenchWindow SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE PA_Report SET JasperProcess_ID=NULL WHERE JasperProcess_ID=53072
;

UPDATE Leichmehl_PluFile_ConfigGroup SET AD_Process_CustomQuery_ID=NULL WHERE AD_Process_CustomQuery_ID=53072
;

UPDATE C_Queue_Block SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

UPDATE C_Printing_Queue SET AD_Process_ID=NULL WHERE AD_Process_ID=53072
;

-- 4. The process itself (parameters, translations, schedulers cascade)
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID IN (SELECT AD_Process_Para_ID FROM AD_Process_Para WHERE AD_Process_ID=53072)
;

DELETE FROM AD_Process_Para WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process_Trl WHERE AD_Process_ID=53072
;

DELETE FROM AD_Process WHERE AD_Process_ID=53072
;
