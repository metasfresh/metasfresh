-- 2024-08-02T05:45:26.866Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583209,0,'DemandDate',TO_TIMESTAMP('2024-08-02 08:45:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Demand Date','Demand Date',TO_TIMESTAMP('2024-08-02 08:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-02T05:45:26.870Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583209 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-08-02T05:45:59.914Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583210,0,'SupplyDate',TO_TIMESTAMP('2024-08-02 08:45:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Supply Date','Supply Date',TO_TIMESTAMP('2024-08-02 08:45:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-02T05:45:59.916Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583210 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: DD_Order_Candidate.SupplyDate
-- Column: DD_Order_Candidate.SupplyDate
-- 2024-08-02T05:46:31.596Z
UPDATE AD_Column SET AD_Element_ID=583210, ColumnName='SupplyDate', Description=NULL, FieldLength=7, Help=NULL, IsCalculated='N', Name='Supply Date',Updated=TO_TIMESTAMP('2024-08-02 08:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588742
;

-- 2024-08-02T05:46:31.598Z
UPDATE AD_Field SET Name='Supply Date', Description=NULL, Help=NULL WHERE AD_Column_ID=588742
;

-- 2024-08-02T05:46:31.600Z
/* DDL */  select update_Column_Translation_From_AD_Element(583210) 
;

alter table dd_order_candidate rename datepromised to supplydate;

-- Column: DD_Order_Candidate.DemandDate
-- Column: DD_Order_Candidate.DemandDate
-- 2024-08-02T05:48:06.704Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588899,583209,0,16,542424,'XX','DemandDate',TO_TIMESTAMP('2024-08-02 08:48:06','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Demand Date',0,0,TO_TIMESTAMP('2024-08-02 08:48:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-02T05:48:06.707Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588899 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-02T05:48:06.711Z
/* DDL */  select update_Column_Translation_From_AD_Element(583209) 
;

-- Column: DD_Order_Candidate.SupplyDate
-- Column: DD_Order_Candidate.SupplyDate
-- 2024-08-02T05:48:28.321Z
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2024-08-02 08:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588742
;

-- 2024-08-02T05:51:04.230Z
INSERT INTO t_alter_column values('dd_order_candidate','SupplyDate','TIMESTAMP WITH TIME ZONE',null,null)
;

