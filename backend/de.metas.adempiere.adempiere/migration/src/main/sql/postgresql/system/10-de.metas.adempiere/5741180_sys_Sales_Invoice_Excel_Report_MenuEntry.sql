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
