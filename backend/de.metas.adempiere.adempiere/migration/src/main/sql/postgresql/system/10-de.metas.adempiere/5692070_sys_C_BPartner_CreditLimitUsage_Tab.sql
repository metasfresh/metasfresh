-- 2023-06-16T15:55:11.186Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582446,0,'CLUsage_Parent_Column_ID.',TO_TIMESTAMP('2023-06-16 18:55:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit Limit Usage Parent','Credit Limit Usage Parent',TO_TIMESTAMP('2023-06-16 18:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-16T15:55:11.217Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582446 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-06-16T15:55:15.327Z
UPDATE AD_Element SET ColumnName='CLUsage_Parent_Column_ID',Updated=TO_TIMESTAMP('2023-06-16 18:55:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582446
;

-- 2023-06-16T15:55:15.356Z
UPDATE AD_Column SET ColumnName='CLUsage_Parent_Column_ID' WHERE AD_Element_ID=582446
;

-- 2023-06-16T15:55:15.383Z
UPDATE AD_Process_Para SET ColumnName='CLUsage_Parent_Column_ID' WHERE AD_Element_ID=582446
;

-- 2023-06-16T15:55:15.516Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582446,'en_US') 
;

-- Column: C_BPartner.CLUsage_Parent_Column_ID
-- 2023-06-16T15:58:32.364Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586820,582446,0,30,541252,291,'XX','CLUsage_Parent_Column_ID','CASE WHEN issectiongrouppartner = ''Y'' THEN C_BPartner_ID ELSE section_group_partner_id END',TO_TIMESTAMP('2023-06-16 18:58:31','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Credit Limit Usage Parent',0,0,TO_TIMESTAMP('2023-06-16 18:58:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-16T15:58:32.391Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586820 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-16T15:58:33.816Z
/* DDL */  select update_Column_Translation_From_AD_Element(582446) 
;

-- Tab: Business Partner(541584,D) -> CL Usage
-- Table: CreditLimit_Usage_V
-- 2023-06-16T16:00:34.767Z
UPDATE AD_Tab SET Parent_Column_ID=586820,Updated=TO_TIMESTAMP('2023-06-16 19:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546817
;

