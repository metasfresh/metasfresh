-- Column: ModCntr_InvoicingGroup.C_Currency_ID
-- 2024-05-13T15:32:40.352Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588264,193,0,19,542359,'C_Currency_ID',TO_TIMESTAMP('2024-05-13 18:32:36.977','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Währung für diesen Eintrag','de.metas.contracts',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2024-05-13 18:32:36.977','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-13T15:32:40.354Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588264 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-13T15:32:40.357Z
/* DDL */  select update_Column_Translation_From_AD_Element(193)
;

-- 2024-05-13T15:32:43.439Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE public.ModCntr_InvoicingGroup ADD COLUMN C_Currency_ID NUMERIC(10)')
;

UPDATE modcntr_invoicinggroup
SET c_currency_id=318 --CHF
;

COMMIT
;

INSERT INTO t_alter_column values('ModCntr_InvoicingGroup','C_Currency_ID','NUMERIC(10)',null,null)
;

INSERT INTO t_alter_column values('ModCntr_InvoicingGroup','C_Currency_ID',null,'NOT NULL',null)
;

-- 2024-05-13T15:32:43.510Z
ALTER TABLE ModCntr_InvoicingGroup ADD CONSTRAINT CCurrency_ModCntrInvoicingGroup FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- 2024-05-13T15:34:33.926Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup','C_Currency_ID','NUMERIC(10)',null,null)
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- ParameterName: ModCntr_InvoicingGroup_ID
-- 2024-05-13T16:15:55.414Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582647,0,585386,542820,19,'ModCntr_InvoicingGroup_ID',TO_TIMESTAMP('2024-05-13 19:15:55.254','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',0,'Y','N','Y','N','N','N','Rechnungsgruppe',30,TO_TIMESTAMP('2024-05-13 19:15:55.254','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-13T16:15:55.415Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542820 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-13T16:15:55.416Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582647)
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- ParameterName: TotalInterest
-- 2024-05-13T16:18:04.635Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,583091,0,585386,542821,12,'TotalInterest',TO_TIMESTAMP('2024-05-13 19:18:04.483','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',0,'Y','N','Y','N','N','N','Total Zins','1=1',40,TO_TIMESTAMP('2024-05-13 19:18:04.483','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-13T16:18:04.636Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542821 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-13T16:18:04.637Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583091)
;

-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- ParameterName: C_Currency_ID
-- 2024-05-13T16:18:47.416Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,193,0,585386,542822,19,'C_Currency_ID',TO_TIMESTAMP('2024-05-13 19:18:47.276','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','de.metas.contracts',0,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','Währung','1=1',50,TO_TIMESTAMP('2024-05-13 19:18:47.276','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-13T16:18:47.418Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542822 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-13T16:18:47.419Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(193)
;

-- 2024-05-13T16:22:02.542Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583108,0,TO_TIMESTAMP('2024-05-13 19:22:02.396','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Zins berechnen','Zins berechnen',TO_TIMESTAMP('2024-05-13 19:22:02.396','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-13T16:22:02.544Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583108 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-05-13T16:22:11.927Z
UPDATE AD_Element_Trl SET Name='Compute Interest', PrintName='Compute Interest',Updated=TO_TIMESTAMP('2024-05-13 19:22:11.927','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='en_US'
;

-- 2024-05-13T16:22:11.929Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'en_US')
;

-- Element: null
-- 2024-05-13T16:22:39.286Z
UPDATE AD_Element_Trl SET Name='Zins Berechnen', PrintName='Zins Berechnen',Updated=TO_TIMESTAMP('2024-05-13 19:22:39.285','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='de_CH'
;

-- 2024-05-13T16:22:39.287Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'de_CH')
;

-- Element: null
-- 2024-05-13T16:22:41.297Z
UPDATE AD_Element_Trl SET Name='Zins Berechnen', PrintName='Zins Berechnen',Updated=TO_TIMESTAMP('2024-05-13 19:22:41.297','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='de_DE'
;

-- 2024-05-13T16:22:41.298Z
UPDATE AD_Element SET Name='Zins Berechnen', PrintName='Zins Berechnen' WHERE AD_Element_ID=583108
;

-- 2024-05-13T16:22:41.563Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583108,'de_DE')
;

-- 2024-05-13T16:22:41.564Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'de_DE')
;

-- Element: null
-- 2024-05-13T16:22:43.299Z
UPDATE AD_Element_Trl SET Name='Zins Berechnen', PrintName='Zins Berechnen',Updated=TO_TIMESTAMP('2024-05-13 19:22:43.299','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='fr_CH'
;

-- 2024-05-13T16:22:43.301Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'fr_CH')
;

-- Element: null
-- 2024-05-13T16:22:45.178Z
UPDATE AD_Element_Trl SET Name='Zins Berechnen', PrintName='Zins Berechnen',Updated=TO_TIMESTAMP('2024-05-13 19:22:45.178','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='it_IT'
;

-- 2024-05-13T16:22:45.180Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'it_IT')
;

-- Name: Zins Berechnen
-- Action Type: P
-- Process: ModCntr_Compute_Interest(de.metas.contracts.modular.interim.invoice.process.ModCntr_Compute_Interest)
-- 2024-05-13T16:22:59.849Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,583108,542156,0,585386,TO_TIMESTAMP('2024-05-13 19:22:59.707','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','ModCntr_Compute_Interest','Y','N','N','N','N','Zins Berechnen',TO_TIMESTAMP('2024-05-13 19:22:59.707','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-13T16:22:59.851Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542156 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-05-13T16:22:59.853Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542156, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542156)
;

-- 2024-05-13T16:22:59.863Z
/* DDL */  select update_menu_translation_from_ad_element(583108)
;

-- Reordering children of `Actions`
-- Node name: `Initial Setup Wizard (de.metas.fresh.setup.process.AD_Client_Setup)`
-- 2024-05-13T16:23:00.422Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000099, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540809 AND AD_Tree_ID=10
;

-- Node name: `Zins Berechnen`
-- 2024-05-13T16:23:00.431Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000099, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542156 AND AD_Tree_ID=10
;

-- Reordering children of `Actions`
-- Node name: `Zins Berechnen`
-- 2024-05-13T16:23:16.202Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000054, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542156 AND AD_Tree_ID=10
;

