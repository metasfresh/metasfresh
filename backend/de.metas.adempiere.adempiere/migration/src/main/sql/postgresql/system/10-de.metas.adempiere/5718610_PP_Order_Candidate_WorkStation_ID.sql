-- 2024-03-06T13:40:43.329Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583018,0,'WorkStation_ID',TO_TIMESTAMP('2024-03-06 15:40:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Arbeitsstation','Arbeitsstation',TO_TIMESTAMP('2024-03-06 15:40:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T13:40:43.336Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583018 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: WorkStation_ID
-- 2024-03-06T13:40:55.859Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-06 15:40:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583018 AND AD_Language='de_CH'
;

-- 2024-03-06T13:40:55.898Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583018,'de_CH') 
;

-- Element: WorkStation_ID
-- 2024-03-06T13:40:59.256Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Work Station', PrintName='Work Station',Updated=TO_TIMESTAMP('2024-03-06 15:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583018 AND AD_Language='en_US'
;

-- 2024-03-06T13:40:59.259Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583018,'en_US') 
;

-- Element: WorkStation_ID
-- 2024-03-06T13:41:01.151Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-06 15:41:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583018 AND AD_Language='de_DE'
;

-- 2024-03-06T13:41:01.154Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583018,'de_DE') 
;

-- 2024-03-06T13:41:01.157Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583018,'de_DE') 
;

-- Element: WorkStation_ID
-- 2024-03-06T13:41:05.713Z
UPDATE AD_Element_Trl SET Name='Work Station', PrintName='Work Station',Updated=TO_TIMESTAMP('2024-03-06 15:41:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583018 AND AD_Language='fr_CH'
;

-- 2024-03-06T13:41:05.716Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583018,'fr_CH') 
;

-- Name: S_Resource
-- 2024-03-06T13:43:32.789Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541855,TO_TIMESTAMP('2024-03-06 15:43:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','S_Resource',TO_TIMESTAMP('2024-03-06 15:43:32','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-03-06T13:43:32.791Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541855 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: S_Resource
-- Table: S_Resource
-- Key: S_Resource.S_Resource_ID
-- 2024-03-06T13:43:47.548Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,6862,0,541855,487,TO_TIMESTAMP('2024-03-06 15:43:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2024-03-06 15:43:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: S_Resource Manufacturing WorkStation
-- 2024-03-06T13:45:08.328Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540669,'IsManufacturingResource=''Y'' AND ManufacturingResourceType=''WS''',TO_TIMESTAMP('2024-03-06 15:45:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','S_Resource Manufacturing WorkStation','S',TO_TIMESTAMP('2024-03-06 15:45:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: PP_Order_Candidate.WorkStation_ID
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-06T13:45:30.159Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587967,583018,0,30,541855,541913,540669,'XX','WorkStation_ID',TO_TIMESTAMP('2024-03-06 15:45:30','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Arbeitsstation',0,0,TO_TIMESTAMP('2024-03-06 15:45:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T13:45:30.163Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587967 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T13:45:30.167Z
/* DDL */  select update_Column_Translation_From_AD_Element(583018) 
;

-- 2024-03-06T13:45:33.600Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN WorkStation_ID NUMERIC(10)')
;

-- 2024-03-06T13:45:33.803Z
ALTER TABLE PP_Order_Candidate ADD CONSTRAINT WorkStation_PPOrderCandidate FOREIGN KEY (WorkStation_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED
;

-- Field: Produktionsdisposition -> Produktionsdisposition -> Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- Field: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> Arbeitsstation
-- Column: PP_Order_Candidate.WorkStation_ID
-- 2024-03-06T13:46:14.599Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587967,726562,0,544794,TO_TIMESTAMP('2024-03-06 15:46:14','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Arbeitsstation',TO_TIMESTAMP('2024-03-06 15:46:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T13:46:14.603Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726562 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-06T13:46:14.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583018) 
;

-- 2024-03-06T13:46:14.621Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726562
;

-- 2024-03-06T13:46:14.628Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726562)
;

