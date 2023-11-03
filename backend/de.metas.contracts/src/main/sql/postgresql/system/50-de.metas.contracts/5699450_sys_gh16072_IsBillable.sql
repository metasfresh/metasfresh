-- 2023-08-17T14:52:43.172295100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582653,0,'IsBillable',TO_TIMESTAMP('2023-08-17 17:52:42.982','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Abrechenbar','Abrechenbar',TO_TIMESTAMP('2023-08-17 17:52:42.982','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T14:52:43.175450600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582653 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsBillable
-- 2023-08-17T14:52:53.849144600Z
UPDATE AD_Element_Trl SET Name='Invoiceable', PrintName='Invoiceable',Updated=TO_TIMESTAMP('2023-08-17 17:52:53.849','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582653 AND AD_Language='en_US'
;

-- 2023-08-17T14:52:53.851143900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582653,'en_US') 
;

-- Column: ModCntr_Log.IsBillable
-- 2023-08-17T14:53:54.914634600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587302,582653,0,20,542338,'IsBillable',TO_TIMESTAMP('2023-08-17 17:53:54.784','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','de.metas.contracts',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abrechenbar',0,0,TO_TIMESTAMP('2023-08-17 17:53:54.784','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-17T14:53:54.917633Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587302 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-17T14:53:55.688459400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582653) 
;

-- 2023-08-17T14:53:58.939786Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN IsBillable CHAR(1) DEFAULT ''Y'' CHECK (IsBillable IN (''Y'',''N''))')
;

