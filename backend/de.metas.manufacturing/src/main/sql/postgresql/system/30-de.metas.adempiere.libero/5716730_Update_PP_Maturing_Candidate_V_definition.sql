-- 2024-02-07T09:04:06.606Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582954,0,'issue_m_product_id',TO_TIMESTAMP('2024-02-07 11:04:06','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','issue_m_product_id','issue_m_product_id',TO_TIMESTAMP('2024-02-07 11:04:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-07T09:04:06.612Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582954 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PP_Maturing_Candidates_v.PP_Product_BOMVersions_ID
-- Column: PP_Maturing_Candidates_v.PP_Product_BOMVersions_ID
-- 2024-02-07T09:04:07.067Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587887,580087,0,30,542385,'PP_Product_BOMVersions_ID',TO_TIMESTAMP('2024-02-07 11:04:06','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','N','N','N','N','N','N','Stückliste',TO_TIMESTAMP('2024-02-07 11:04:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-07T09:04:07.069Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587887 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T09:04:07.097Z
/* DDL */  select update_Column_Translation_From_AD_Element(580087) 
;

-- 2024-02-07T09:07:13.086Z
UPDATE AD_Element SET ColumnName='Issue_M_Product_ID', EntityType='D',Updated=TO_TIMESTAMP('2024-02-07 11:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582954
;

-- 2024-02-07T09:07:13.087Z
UPDATE AD_Column SET ColumnName='Issue_M_Product_ID' WHERE AD_Element_ID=582954
;

-- 2024-02-07T09:07:13.088Z
UPDATE AD_Process_Para SET ColumnName='Issue_M_Product_ID' WHERE AD_Element_ID=582954
;

-- 2024-02-07T09:07:13.090Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582954,'de_DE') 
;

-- Column: PP_Maturing_Candidates_v.Issue_M_Product_ID
-- Column: PP_Maturing_Candidates_v.Issue_M_Product_ID
-- 2024-02-07T09:07:20.138Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587888,582954,0,18,540272,542385,'XX','Issue_M_Product_ID',TO_TIMESTAMP('2024-02-07 11:07:20','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'issue_m_product_id',0,0,TO_TIMESTAMP('2024-02-07 11:07:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-07T09:07:20.139Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587888 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T09:07:20.141Z
/* DDL */  select update_Column_Translation_From_AD_Element(582954) 
;

