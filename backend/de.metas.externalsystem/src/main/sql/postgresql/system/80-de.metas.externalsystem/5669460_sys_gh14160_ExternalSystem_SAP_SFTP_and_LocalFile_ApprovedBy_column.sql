-- Column: ExternalSystem_Config_SAP_LocalFile.ApprovedBy_ID
-- 2022-12-20T16:50:33.537Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585420,543844,0,18,110,542258,'ApprovedBy_ID',TO_TIMESTAMP('2022-12-20 18:50:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Approved By',0,0,TO_TIMESTAMP('2022-12-20 18:50:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-20T16:50:33.540Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585420 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-20T16:50:33.563Z
/* DDL */  select update_Column_Translation_From_AD_Element(543844) 
;

-- 2022-12-20T16:50:34.482Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN ApprovedBy_ID NUMERIC(10)')
;

-- 2022-12-20T16:50:34.495Z
ALTER TABLE ExternalSystem_Config_SAP_LocalFile ADD CONSTRAINT ApprovedBy_ExternalSystemConfigSAPLocalFile FOREIGN KEY (ApprovedBy_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Column: ExternalSystem_Config_SAP_SFTP.ApprovedBy_ID
-- 2022-12-20T16:51:00.332Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585421,543844,0,18,110,542257,'ApprovedBy_ID',TO_TIMESTAMP('2022-12-20 18:51:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Approved By',0,0,TO_TIMESTAMP('2022-12-20 18:51:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-20T16:51:00.336Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585421 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-20T16:51:00.342Z
/* DDL */  select update_Column_Translation_From_AD_Element(543844) 
;

-- 2022-12-20T16:51:00.950Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN ApprovedBy_ID NUMERIC(10)')
;

-- 2022-12-20T16:51:00.959Z
ALTER TABLE ExternalSystem_Config_SAP_SFTP ADD CONSTRAINT ApprovedBy_ExternalSystemConfigSAPSFTP FOREIGN KEY (ApprovedBy_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> Approved By
-- Column: ExternalSystem_Config_SAP_SFTP.ApprovedBy_ID
-- 2022-12-20T17:03:24.493Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585421,710050,0,546672,0,TO_TIMESTAMP('2022-12-20 19:03:24','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Approved By',0,180,0,1,1,TO_TIMESTAMP('2022-12-20 19:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-20T17:03:24.501Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-20T17:03:24.504Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543844) 
;

-- 2022-12-20T17:03:24.513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710050
;

-- 2022-12-20T17:03:24.518Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710050)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: approval
-- 2022-12-20T17:03:50.980Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546448,550196,TO_TIMESTAMP('2022-12-20 19:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','approval',15,TO_TIMESTAMP('2022-12-20 19:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> approval.Approved By
-- Column: ExternalSystem_Config_SAP_SFTP.ApprovedBy_ID
-- 2022-12-20T17:04:06.976Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710050,0,546672,614568,550196,'F',TO_TIMESTAMP('2022-12-20 19:04:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Approved By',10,0,0,TO_TIMESTAMP('2022-12-20 19:04:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Approved By
-- Column: ExternalSystem_Config_SAP_LocalFile.ApprovedBy_ID
-- 2022-12-20T17:05:07.189Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585420,710051,0,546673,0,TO_TIMESTAMP('2022-12-20 19:05:07','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Approved By',0,150,0,1,1,TO_TIMESTAMP('2022-12-20 19:05:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-20T17:05:07.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-20T17:05:07.196Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543844) 
;

-- 2022-12-20T17:05:07.199Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710051
;

-- 2022-12-20T17:05:07.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710051)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: approval
-- 2022-12-20T17:05:20.776Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546450,550197,TO_TIMESTAMP('2022-12-20 19:05:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','approval',15,TO_TIMESTAMP('2022-12-20 19:05:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> approval.Approved By
-- Column: ExternalSystem_Config_SAP_LocalFile.ApprovedBy_ID
-- 2022-12-20T17:05:32.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710051,0,546673,614569,550197,'F',TO_TIMESTAMP('2022-12-20 19:05:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Approved By',10,0,0,TO_TIMESTAMP('2022-12-20 19:05:32','YYYY-MM-DD HH24:MI:SS'),100)
;

