-- 2022-09-26T10:13:09.141Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581490,0,'IsReadOnlyInMetasfresh',TO_TIMESTAMP('2022-09-26 13:13:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Read Only In Metasfresh','Read Only In Metasfresh',TO_TIMESTAMP('2022-09-26 13:13:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-26T10:13:09.141Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581490 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: S_ExternalReference.IsReadOnlyInMetasfresh
-- 2022-09-26T10:14:34.969Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584454,581490,0,20,541486,'IsReadOnlyInMetasfresh',TO_TIMESTAMP('2022-09-26 13:14:34','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.externalreference',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Read Only In Metasfresh',0,0,TO_TIMESTAMP('2022-09-26 13:14:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-26T10:14:34.971Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584454 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-26T10:14:34.996Z
/* DDL */  select update_Column_Translation_From_AD_Element(581490) 
;

-- 2022-09-26T11:25:20.922Z
/* DDL */ SELECT public.db_alter_table('S_ExternalReference','ALTER TABLE public.S_ExternalReference ADD COLUMN IsReadOnlyInMetasfresh CHAR(1) DEFAULT ''N'' CHECK (IsReadOnlyInMetasfresh IN (''Y'',''N'')) NOT NULL')
;

-- Field: External reference(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> Read Only In Metasfresh
-- Column: S_ExternalReference.IsReadOnlyInMetasfresh
-- 2022-09-29T17:22:14.971Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584454,707452,0,542376,0,TO_TIMESTAMP('2022-09-29 20:22:14','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Read Only In Metasfresh',0,30,0,1,1,TO_TIMESTAMP('2022-09-29 20:22:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-29T17:22:14.976Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707452 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-29T17:22:15.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581490)
;

-- 2022-09-29T17:22:15.015Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707452
;

-- 2022-09-29T17:22:15.021Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707452)
;

-- UI Element: External reference(540901,de.metas.externalreference) -> External reference(542376,de.metas.serviceprovider) -> main -> 10 -> default.Read Only In Metasfresh
-- Column: S_ExternalReference.IsReadOnlyInMetasfresh
-- 2022-09-29T17:23:16.939Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707452,0,542376,613105,543614,'F',TO_TIMESTAMP('2022-09-29 20:23:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Read Only In Metasfresh',90,0,0,TO_TIMESTAMP('2022-09-29 20:23:16','YYYY-MM-DD HH24:MI:SS'),100)
;