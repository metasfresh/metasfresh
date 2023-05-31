-- 2023-05-31T12:18:18.977Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582392,0,'IsExternalProperty',TO_TIMESTAMP('2023-05-31 15:18:18.721','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Externes Eigentum','Externes Eigentum',TO_TIMESTAMP('2023-05-31 15:18:18.721','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-31T12:18:18.983Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582392 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-05-31T12:18:32.154Z
UPDATE AD_Element_Trl SET Name='External property', PrintName='External property',Updated=TO_TIMESTAMP('2023-05-31 15:18:32.152','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582392 AND AD_Language='en_US'
;

-- 2023-05-31T12:18:32.183Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582392,'en_US') 
;

-- Column: M_HU.IsExternalProperty
-- 2023-05-31T12:19:07.694Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586736,582392,0,20,540516,'IsExternalProperty',TO_TIMESTAMP('2023-05-31 15:19:07.538','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Externes Eigentum',0,0,TO_TIMESTAMP('2023-05-31 15:19:07.538','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-31T12:19:07.695Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586736 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-31T12:19:07.699Z
/* DDL */  select update_Column_Translation_From_AD_Element(582392) 
;

-- 2023-05-31T12:20:40.634Z
/* DDL */ SELECT public.db_alter_table('M_HU','ALTER TABLE public.M_HU ADD COLUMN IsExternalProperty CHAR(1) DEFAULT ''N'' CHECK (IsExternalProperty IN (''Y'',''N'')) NOT NULL')
;

-- Field: Handling Unit -> Handling Unit -> Externes Eigentum
-- Column: M_HU.IsExternalProperty
-- 2023-05-31T12:20:55.290Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586736,716155,0,540508,0,TO_TIMESTAMP('2023-05-31 15:20:55.135','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Externes Eigentum',0,160,0,1,1,TO_TIMESTAMP('2023-05-31 15:20:55.135','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-31T12:20:55.293Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-31T12:20:55.295Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582392) 
;

-- 2023-05-31T12:20:55.305Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716155
;

-- 2023-05-31T12:20:55.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716155)
;

-- UI Element: Handling Unit -> Handling Unit.Externes Eigentum
-- Column: M_HU.IsExternalProperty
-- 2023-05-31T13:07:39.267Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716155,0,540508,540918,617927,'F',TO_TIMESTAMP('2023-05-31 16:07:39.096','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Externes Eigentum',40,0,0,TO_TIMESTAMP('2023-05-31 16:07:39.096','YYYY-MM-DD HH24:MI:SS.US'),100)
;

