-- 2022-08-17T06:05:39.249Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581315,0,'S_ExternalProjectReference_Effort_ID',TO_TIMESTAMP('2022-08-17 09:05:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','External project reference Effort ID','External project reference Effort ID',TO_TIMESTAMP('2022-08-17 09:05:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-17T06:05:39.266Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581315 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: S_ExternalProjectReferenceEffort
-- 2022-08-17T06:06:20.348Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541638,TO_TIMESTAMP('2022-08-17 09:06:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','S_ExternalProjectReferenceEffort',TO_TIMESTAMP('2022-08-17 09:06:20','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-17T06:06:20.359Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541638 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: S_ExternalProjectReferenceEffort
-- Table: S_ExternalProjectReference
-- Key: S_ExternalProjectReference.S_ExternalProjectReference_ID
-- 2022-08-17T06:07:12.043Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,570136,570136,0,541638,541466,TO_TIMESTAMP('2022-08-17 09:07:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','N',TO_TIMESTAMP('2022-08-17 09:07:12','YYYY-MM-DD HH24:MI:SS'),100,'S_ExternalProjectReference.ProjectType IN (''Effort'')')
;

-- Column: S_ExternalProjectReference.S_ExternalProjectReference_Effort_ID
-- 2022-08-17T06:08:02.215Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584053,581315,0,18,541638,541466,'S_ExternalProjectReference_Effort_ID',TO_TIMESTAMP('2022-08-17 09:08:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.serviceprovider',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External project reference Effort ID',0,0,TO_TIMESTAMP('2022-08-17 09:08:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-17T06:08:02.224Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584053 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-17T06:08:02.280Z
/* DDL */  select update_Column_Translation_From_AD_Element(581315)
;

-- 2022-08-17T06:08:04.768Z
/* DDL */ SELECT public.db_alter_table('S_ExternalProjectReference','ALTER TABLE public.S_ExternalProjectReference ADD COLUMN S_ExternalProjectReference_Effort_ID NUMERIC(10)')
;

-- 2022-08-17T06:08:04.811Z
ALTER TABLE S_ExternalProjectReference ADD CONSTRAINT SExternalProjectReferenceEffort_SExternalProjectReference FOREIGN KEY (S_ExternalProjectReference_Effort_ID) REFERENCES public.S_ExternalProjectReference DEFERRABLE INITIALLY DEFERRED
;

-- Field: External project reference -> External project reference ID -> External project reference Effort ID
-- Column: S_ExternalProjectReference.S_ExternalProjectReference_Effort_ID
-- 2022-08-17T06:09:13.901Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584053,704833,0,542339,TO_TIMESTAMP('2022-08-17 09:09:13','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','External project reference Effort ID',TO_TIMESTAMP('2022-08-17 09:09:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-17T06:09:13.904Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-17T06:09:13.911Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581315)
;

-- 2022-08-17T06:09:13.930Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704833
;

-- 2022-08-17T06:09:13.944Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(704833)
;

-- UI Element: External project reference -> External project reference ID.External project reference Effort ID
-- Column: S_ExternalProjectReference.S_ExternalProjectReference_Effort_ID
-- 2022-08-17T06:09:41.529Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,704833,0,542339,611782,543560,'F',TO_TIMESTAMP('2022-08-17 09:09:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External project reference Effort ID',80,0,0,TO_TIMESTAMP('2022-08-17 09:09:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: S_ExternalProjectReference.S_ExternalProjectReference_Effort_ID
-- 2022-08-17T06:23:39.362Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2022-08-17 09:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584053
;

-- 2022-08-17T06:23:41.097Z
INSERT INTO t_alter_column values('s_externalprojectreference','S_ExternalProjectReference_Effort_ID','NUMERIC(10)',null,null)
;
