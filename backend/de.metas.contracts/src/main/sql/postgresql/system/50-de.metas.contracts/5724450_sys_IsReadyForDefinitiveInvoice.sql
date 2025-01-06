-- Run mode: SWING_CLIENT

-- 2024-05-27T10:48:14.049Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583126,0,'IsDefinitiveInvoiceReady',TO_TIMESTAMP('2024-05-27 13:48:13.905','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Ist bereit für die Definitive Schlussabrechnung','Ist bereit für die Definitive Schlussabrechnung',TO_TIMESTAMP('2024-05-27 13:48:13.905','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-27T10:48:14.054Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583126 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsDefinitiveInvoiceReady
-- 2024-05-27T10:48:25.635Z
UPDATE AD_Element_Trl SET Name='Is ready for the definitive final invoice', PrintName='Is ready for the definitive final invoice',Updated=TO_TIMESTAMP('2024-05-27 13:48:25.635','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583126 AND AD_Language='en_US'
;

-- 2024-05-27T10:48:25.666Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583126,'en_US')
;

-- 2024-05-27T10:49:23.942Z
UPDATE AD_Element SET ColumnName='IsReadyForDefinitiveInvoice',Updated=TO_TIMESTAMP('2024-05-27 13:49:23.942','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583126
;

-- 2024-05-27T10:49:23.943Z
UPDATE AD_Column SET ColumnName='IsReadyForDefinitiveInvoice' WHERE AD_Element_ID=583126
;

-- 2024-05-27T10:49:23.944Z
UPDATE AD_Process_Para SET ColumnName='IsReadyForDefinitiveInvoice' WHERE AD_Element_ID=583126
;

-- 2024-05-27T10:49:23.946Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583126,'de_DE')
;

-- Column: C_Flatrate_Term.IsReadyForDefinitiveInvoice
-- 2024-05-27T10:49:38.523Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588323,583126,0,20,540320,'IsReadyForDefinitiveInvoice',TO_TIMESTAMP('2024-05-27 13:49:38.376','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.contracts',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ist bereit für die Definitive Schlussabrechnung',0,0,TO_TIMESTAMP('2024-05-27 13:49:38.376','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-27T10:49:38.524Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588323 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-27T10:49:38.527Z
/* DDL */  select update_Column_Translation_From_AD_Element(583126)
;

-- 2024-05-27T10:50:20.543Z
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN IsReadyForDefinitiveInvoice CHAR(1) DEFAULT ''N'' CHECK (IsReadyForDefinitiveInvoice IN (''Y'',''N'')) NOT NULL')
;

-- Element: IsReadyForDefinitiveInvoice
-- 2024-05-27T10:51:38.877Z
UPDATE AD_Element_Trl SET Description='If true, this means that the current contract is ready for the definitive invoice to be created. No further final invoices can be created.',Updated=TO_TIMESTAMP('2024-05-27 13:51:38.877','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583126 AND AD_Language='en_US'
;

-- 2024-05-27T10:51:38.878Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583126,'en_US')
;

