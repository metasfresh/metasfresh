-- Name: Umsatz mit Gutschriften (Excel)
-- Action Type: P
-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2024-12-05T15:22:58.114921005Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,583387,542186,0,585439,TO_TIMESTAMP('2024-12-05 16:22:58.112','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Sales Invoice Report (Excel)','Y','N','N','N','N','Umsatz mit Gutschriften (Excel)',TO_TIMESTAMP('2024-12-05 16:22:58.112','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-05T15:22:58.116309438Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542186 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-12-05T15:22:58.118535349Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542186, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542186)
;

-- 2024-12-05T15:22:58.120913081Z
/* DDL */  select update_menu_translation_from_ad_element(583387) 
;



-- 2024-12-05T17:01:25.142366300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540384, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542186 AND AD_Tree_ID=10
;


-- Name: Umsatz mit Gutschriften (Excel)
-- Action Type: P
-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2024-12-05T15:54:44.873539083Z
UPDATE AD_Menu SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-05 16:54:44.873','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Menu_ID=542186
;


-- Run mode: SWING_CLIENT

-- Reordering children of `Fakturierung`
-- Node name: `Rechnung-Überprüfung`
-- 2024-12-05T16:38:38.937Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- Node name: `Rechnungsdisposition (C_Invoice_Candidate)`
-- 2024-12-05T16:38:38.942Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- Node name: `Rechnungsdisposition Einkauf (C_Invoice_Candidate)`
-- 2024-12-05T16:38:38.943Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- Node name: `Debitoren Rechnung (C_Invoice)`
-- 2024-12-05T16:38:38.944Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- Node name: `Kreditoren Rechnung (C_Invoice)`
-- 2024-12-05T16:38:38.945Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- Node name: `Zollrechnung (C_Customs_Invoice)`
-- 2024-12-05T16:38:38.945Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- Node name: `Lieferung-Zollrechnung Zuordnung (M_InOutLine_To_C_Customs_Invoice_Line)`
-- 2024-12-05T16:38:38.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- Node name: `Aktionen`
-- 2024-12-05T16:38:38.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- Node name: `Berichte`
-- 2024-12-05T16:38:38.948Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- Node name: `Einstellungen`
-- 2024-12-05T16:38:38.949Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- Node name: `Rechnungspool (C_DocType_Invoicing_Pool)`
-- 2024-12-05T16:38:38.950Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542033 AND AD_Tree_ID=10
;

-- Node name: `Auswahl einreihen für Fakturierung und PDF-Druck (de.metas.printing.process.C_Invoice_Candidate_EnqueueSelectionForInvoicingAndPDFConcatenating)`
-- 2024-12-05T16:38:38.951Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541886 AND AD_Tree_ID=10
;

-- Node name: `Mahnungen verarbeiten und PDF-Druck auslösen (de.metas.dunning.process.C_Dunning_Candidate_Process_AutomaticallyPDFPrinting)`
-- 2024-12-05T16:38:38.952Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541929 AND AD_Tree_ID=10
;

-- Node name: `Umsatz mit Gutschriften (Excel) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-12-05T16:38:38.953Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542186 AND AD_Tree_ID=10
;

