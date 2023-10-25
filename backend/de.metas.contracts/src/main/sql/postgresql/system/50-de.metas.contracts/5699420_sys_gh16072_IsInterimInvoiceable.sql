-- 2023-08-17T13:49:00.769763900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582652,0,'IsInterimInvoiceable',TO_TIMESTAMP('2023-08-17 16:49:00.593','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Für Zwischenabrechnung freigegeben','Für Zwischenabrechnung freigegeben',TO_TIMESTAMP('2023-08-17 16:49:00.593','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T13:49:00.790149100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582652 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsInterimInvoiceable
-- 2023-08-17T13:49:10.257793Z
UPDATE AD_Element_Trl SET Name='Approved for interim invoicing', PrintName='Approved for interim invoicing',Updated=TO_TIMESTAMP('2023-08-17 16:49:10.257','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582652 AND AD_Language='en_US'
;

-- 2023-08-17T13:49:10.286144600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582652,'en_US') 
;

-- Column: M_InOut.IsInterimInvoiceable
-- 2023-08-17T13:52:36.756625200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587301,582652,0,20,319,'IsInterimInvoiceable',TO_TIMESTAMP('2023-08-17 16:52:36.599','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Für Zwischenabrechnung freigegeben','1=1',0,0,TO_TIMESTAMP('2023-08-17 16:52:36.599','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-17T13:52:36.760657700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587301 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-17T13:52:37.297845200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582652) 
;

-- 2023-08-17T13:52:41.966595800Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN IsInterimInvoiceable CHAR(1) DEFAULT ''Y'' CHECK (IsInterimInvoiceable IN (''Y'',''N''))')
;

