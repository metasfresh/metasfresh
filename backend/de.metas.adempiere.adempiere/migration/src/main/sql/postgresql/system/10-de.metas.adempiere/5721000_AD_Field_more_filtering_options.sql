-- Column: AD_Field.SelectionColumnSeqNo
-- Column: AD_Field.SelectionColumnSeqNo
-- 2024-04-08T07:46:53.704Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588170,543406,0,22,107,'SelectionColumnSeqNo',TO_TIMESTAMP('2024-04-08 07:46:53.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Selection column ordering',0,0,TO_TIMESTAMP('2024-04-08 07:46:53.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-04-08T07:46:53.707Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588170 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T07:46:53.736Z
/* DDL */  select update_Column_Translation_From_AD_Element(543406) 
;

-- 2024-04-08T07:46:54.574Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN SelectionColumnSeqNo NUMERIC')
;

-- Column: AD_Field.FilterOperator
-- Column: AD_Field.FilterOperator
-- 2024-04-08T07:47:49.767Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588171,578627,0,17,541241,107,'FilterOperator',TO_TIMESTAMP('2024-04-08 07:47:49.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Filter Operator',0,0,TO_TIMESTAMP('2024-04-08 07:47:49.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-04-08T07:47:49.769Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588171 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T07:47:49.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(578627) 
;

-- 2024-04-08T07:47:50.327Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN FilterOperator CHAR(1)')
;

-- Column: AD_Field.IsShowFilterInline
-- Column: AD_Field.IsShowFilterInline
-- 2024-04-08T07:48:23.989Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588172,578617,0,17,540528,107,'IsShowFilterInline',TO_TIMESTAMP('2024-04-08 07:48:23.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','Renders the filter as a component (e.g. text box) directly in the filters lane','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Show filter inline',0,0,TO_TIMESTAMP('2024-04-08 07:48:23.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-04-08T07:48:23.990Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588172 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T07:48:23.993Z
/* DDL */  select update_Column_Translation_From_AD_Element(578617) 
;

-- 2024-04-08T07:48:24.507Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN IsShowFilterInline CHAR(1)')
;

-- Column: AD_Field.FilterDefaultValue
-- Column: AD_Field.FilterDefaultValue
-- 2024-04-08T07:50:20.578Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588173,543491,0,10,107,'FilterDefaultValue',TO_TIMESTAMP('2024-04-08 07:50:20.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Filter Default Value',0,0,TO_TIMESTAMP('2024-04-08 07:50:20.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-04-08T07:50:20.581Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588173 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T07:50:20.584Z
/* DDL */  select update_Column_Translation_From_AD_Element(543491) 
;

-- 2024-04-08T07:50:21.130Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN FilterDefaultValue VARCHAR(255)')
;

-- 2024-04-08T07:50:47.327Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583076,0,'IsOverrideFilterDefaultValue',TO_TIMESTAMP('2024-04-08 07:50:47.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Override Filter Default Value','Override Filter Default Value',TO_TIMESTAMP('2024-04-08 07:50:47.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T07:50:47.331Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583076 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Field.IsOverrideFilterDefaultValue
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- 2024-04-08T07:51:09.900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588174,583076,0,20,107,'IsOverrideFilterDefaultValue',TO_TIMESTAMP('2024-04-08 07:51:09.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Override Filter Default Value',0,0,TO_TIMESTAMP('2024-04-08 07:51:09.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-04-08T07:51:09.902Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588174 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T07:51:09.905Z
/* DDL */  select update_Column_Translation_From_AD_Element(583076) 
;

-- 2024-04-08T07:51:10.460Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN IsOverrideFilterDefaultValue CHAR(1) DEFAULT ''N'' CHECK (IsOverrideFilterDefaultValue IN (''Y'',''N'')) NOT NULL')
;

-- Field: Fenster Verwaltung -> Feld -> Selection column ordering
-- Column: AD_Field.SelectionColumnSeqNo
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Selection column ordering
-- Column: AD_Field.SelectionColumnSeqNo
-- 2024-04-08T07:51:57.229Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588170,727812,0,107,TO_TIMESTAMP('2024-04-08 07:51:57.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Selection column ordering',TO_TIMESTAMP('2024-04-08 07:51:57.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T07:51:57.232Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T07:51:57.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543406) 
;

-- 2024-04-08T07:51:57.248Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727812
;

-- 2024-04-08T07:51:57.253Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727812)
;

-- Field: Fenster Verwaltung -> Feld -> Filter Operator
-- Column: AD_Field.FilterOperator
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Operator
-- Column: AD_Field.FilterOperator
-- 2024-04-08T07:51:57.372Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588171,727813,0,107,TO_TIMESTAMP('2024-04-08 07:51:57.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Filter Operator',TO_TIMESTAMP('2024-04-08 07:51:57.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T07:51:57.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T07:51:57.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578627) 
;

-- 2024-04-08T07:51:57.378Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727813
;

-- 2024-04-08T07:51:57.378Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727813)
;

-- Field: Fenster Verwaltung -> Feld -> Show filter inline
-- Column: AD_Field.IsShowFilterInline
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Show filter inline
-- Column: AD_Field.IsShowFilterInline
-- 2024-04-08T07:51:57.485Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588172,727814,0,107,TO_TIMESTAMP('2024-04-08 07:51:57.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Renders the filter as a component (e.g. text box) directly in the filters lane',1,'D','Y','N','N','N','N','N','N','N','Show filter inline',TO_TIMESTAMP('2024-04-08 07:51:57.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T07:51:57.487Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T07:51:57.488Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578617) 
;

-- 2024-04-08T07:51:57.491Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727814
;

-- 2024-04-08T07:51:57.492Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727814)
;

-- Field: Fenster Verwaltung -> Feld -> Filter Default Value
-- Column: AD_Field.FilterDefaultValue
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Default Value
-- Column: AD_Field.FilterDefaultValue
-- 2024-04-08T07:51:57.595Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588173,727815,0,107,TO_TIMESTAMP('2024-04-08 07:51:57.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Filter Default Value',TO_TIMESTAMP('2024-04-08 07:51:57.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T07:51:57.597Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T07:51:57.598Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543491) 
;

-- 2024-04-08T07:51:57.602Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727815
;

-- 2024-04-08T07:51:57.602Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727815)
;

-- Field: Fenster Verwaltung -> Feld -> Override Filter Default Value
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Override Filter Default Value
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- 2024-04-08T07:51:57.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588174,727816,0,107,TO_TIMESTAMP('2024-04-08 07:51:57.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Override Filter Default Value',TO_TIMESTAMP('2024-04-08 07:51:57.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T07:51:57.711Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T07:51:57.713Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583076) 
;

-- 2024-04-08T07:51:57.715Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727816
;

-- 2024-04-08T07:51:57.716Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727816)
;

-- Field: Fenster Verwaltung -> Feld -> Selection column ordering
-- Column: AD_Field.SelectionColumnSeqNo
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Selection column ordering
-- Column: AD_Field.SelectionColumnSeqNo
-- 2024-04-08T07:53:07.869Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2024-04-08 07:53:07.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727812
;

-- Field: Fenster Verwaltung -> Feld -> Filter Operator
-- Column: AD_Field.FilterOperator
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Operator
-- Column: AD_Field.FilterOperator
-- 2024-04-08T07:53:07.876Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=400,Updated=TO_TIMESTAMP('2024-04-08 07:53:07.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727813
;

-- Field: Fenster Verwaltung -> Feld -> Override Filter Default Value
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Override Filter Default Value
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- 2024-04-08T07:53:07.883Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=410,Updated=TO_TIMESTAMP('2024-04-08 07:53:07.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727816
;

-- Field: Fenster Verwaltung -> Feld -> Filter Default Value
-- Column: AD_Field.FilterDefaultValue
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Default Value
-- Column: AD_Field.FilterDefaultValue
-- 2024-04-08T07:53:07.889Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=420,Updated=TO_TIMESTAMP('2024-04-08 07:53:07.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727815
;

-- Field: Fenster Verwaltung -> Feld -> Show filter inline
-- Column: AD_Field.IsShowFilterInline
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Show filter inline
-- Column: AD_Field.IsShowFilterInline
-- 2024-04-08T07:53:07.896Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=430,Updated=TO_TIMESTAMP('2024-04-08 07:53:07.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727814
;

-- Field: Fenster Verwaltung -> Feld -> Selection column ordering
-- Column: AD_Field.SelectionColumnSeqNo
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Selection column ordering
-- Column: AD_Field.SelectionColumnSeqNo
-- 2024-04-08T07:53:36.136Z
UPDATE AD_Field SET DisplayLogic='@IsFilterField/X@=Y',Updated=TO_TIMESTAMP('2024-04-08 07:53:36.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727812
;

-- Field: Fenster Verwaltung -> Feld -> Filter Operator
-- Column: AD_Field.FilterOperator
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Operator
-- Column: AD_Field.FilterOperator
-- 2024-04-08T07:53:54.995Z
UPDATE AD_Field SET DisplayLogic='@IsFilterField/X@=Y',Updated=TO_TIMESTAMP('2024-04-08 07:53:54.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727813
;

-- Field: Fenster Verwaltung -> Feld -> Override Filter Default Value
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Override Filter Default Value
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- 2024-04-08T07:54:21.299Z
UPDATE AD_Field SET DisplayLogic='@IsFilterField/X@=Y',Updated=TO_TIMESTAMP('2024-04-08 07:54:21.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727816
;

-- Field: Fenster Verwaltung -> Feld -> Filter Default Value
-- Column: AD_Field.FilterDefaultValue
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Default Value
-- Column: AD_Field.FilterDefaultValue
-- 2024-04-08T07:54:36.084Z
UPDATE AD_Field SET DisplayLogic='@IsFilterField/X@=Y & @IsOverrideFilterDefaultValue/X@=Y',Updated=TO_TIMESTAMP('2024-04-08 07:54:36.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727815
;

-- Field: Fenster Verwaltung -> Feld -> Show filter inline
-- Column: AD_Field.IsShowFilterInline
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Show filter inline
-- Column: AD_Field.IsShowFilterInline
-- 2024-04-08T07:54:42.430Z
UPDATE AD_Field SET DisplayLogic='@IsFilterField/X@=Y',Updated=TO_TIMESTAMP('2024-04-08 07:54:42.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727814
;

-- Field: Fenster Verwaltung -> Feld -> Filter
-- Column: AD_Field.IsFilterField
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter
-- Column: AD_Field.IsFilterField
-- 2024-04-08T10:28:00.662Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-04-08 10:28:00.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727294
;

