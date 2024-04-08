-- Field: Fenster Verwaltung -> Feld -> Filter
-- Column: AD_Field.IsFilterField
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter
-- Column: AD_Field.IsFilterField
-- 2024-04-08T10:28:00.662Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-04-08 10:28:00.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727294
;

-- Column: AD_Field.IsFacetFilter
-- Column: AD_Field.IsFacetFilter
-- 2024-04-08T12:52:09.913Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588177,577493,0,17,540528,107,'IsFacetFilter',TO_TIMESTAMP('2024-04-08 12:52:09.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Facet Filter',0,0,TO_TIMESTAMP('2024-04-08 12:52:09.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-04-08T12:52:09.918Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588177 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T12:52:09.948Z
/* DDL */  select update_Column_Translation_From_AD_Element(577493) 
;

-- 2024-04-08T12:52:10.700Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN IsFacetFilter CHAR(1)')
;

-- Column: AD_Field.FacetFilterSeqNo
-- Column: AD_Field.FacetFilterSeqNo
-- 2024-04-08T12:53:08.346Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588178,577495,0,11,107,'FacetFilterSeqNo',TO_TIMESTAMP('2024-04-08 12:53:08.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Facet Filter Sequence No',0,0,TO_TIMESTAMP('2024-04-08 12:53:08.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-04-08T12:53:08.348Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588178 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T12:53:08.351Z
/* DDL */  select update_Column_Translation_From_AD_Element(577495) 
;

-- 2024-04-08T12:53:08.915Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN FacetFilterSeqNo NUMERIC(10)')
;

-- Column: AD_Field.MaxFacetsToFetch
-- Column: AD_Field.MaxFacetsToFetch
-- 2024-04-08T12:53:52.704Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588179,577494,0,11,107,'MaxFacetsToFetch',TO_TIMESTAMP('2024-04-08 12:53:51.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','Maximum number of facets to fetch','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maximum Facets',0,0,TO_TIMESTAMP('2024-04-08 12:53:51.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-04-08T12:53:52.705Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588179 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-08T12:53:52.708Z
/* DDL */  select update_Column_Translation_From_AD_Element(577494) 
;

-- 2024-04-08T12:53:53.274Z
/* DDL */ SELECT public.db_alter_table('AD_Field','ALTER TABLE public.AD_Field ADD COLUMN MaxFacetsToFetch NUMERIC(10)')
;

-- Field: Fenster Verwaltung -> Feld -> Facet Filter
-- Column: AD_Field.IsFacetFilter
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Facet Filter
-- Column: AD_Field.IsFacetFilter
-- 2024-04-08T15:01:15.865Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588177,727818,0,107,TO_TIMESTAMP('2024-04-08 15:01:15.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Facet Filter',TO_TIMESTAMP('2024-04-08 15:01:15.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T15:01:15.868Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T15:01:15.874Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577493) 
;

-- 2024-04-08T15:01:15.897Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727818
;

-- 2024-04-08T15:01:15.902Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727818)
;

-- Field: Fenster Verwaltung -> Feld -> Facet Filter Sequence No
-- Column: AD_Field.FacetFilterSeqNo
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Facet Filter Sequence No
-- Column: AD_Field.FacetFilterSeqNo
-- 2024-04-08T15:01:16.030Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588178,727819,0,107,TO_TIMESTAMP('2024-04-08 15:01:15.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Facet Filter Sequence No',TO_TIMESTAMP('2024-04-08 15:01:15.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T15:01:16.032Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T15:01:16.034Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577495) 
;

-- 2024-04-08T15:01:16.038Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727819
;

-- 2024-04-08T15:01:16.039Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727819)
;

-- Field: Fenster Verwaltung -> Feld -> Maximum Facets
-- Column: AD_Field.MaxFacetsToFetch
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Maximum Facets
-- Column: AD_Field.MaxFacetsToFetch
-- 2024-04-08T15:01:16.154Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588179,727820,0,107,TO_TIMESTAMP('2024-04-08 15:01:16.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maximum number of facets to fetch',10,'D','Y','N','N','N','N','N','N','N','Maximum Facets',TO_TIMESTAMP('2024-04-08 15:01:16.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-08T15:01:16.156Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-08T15:01:16.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577494) 
;

-- 2024-04-08T15:01:16.163Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727820
;

-- 2024-04-08T15:01:16.164Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727820)
;

-- Field: Fenster Verwaltung -> Feld -> Facet Filter
-- Column: AD_Field.IsFacetFilter
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Facet Filter
-- Column: AD_Field.IsFacetFilter
-- 2024-04-08T15:01:32.699Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=440,Updated=TO_TIMESTAMP('2024-04-08 15:01:32.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727818
;

-- Field: Fenster Verwaltung -> Feld -> Facet Filter Sequence No
-- Column: AD_Field.FacetFilterSeqNo
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Facet Filter Sequence No
-- Column: AD_Field.FacetFilterSeqNo
-- 2024-04-08T15:01:32.714Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=450,Updated=TO_TIMESTAMP('2024-04-08 15:01:32.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727819
;

-- Field: Fenster Verwaltung -> Feld -> Maximum Facets
-- Column: AD_Field.MaxFacetsToFetch
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Maximum Facets
-- Column: AD_Field.MaxFacetsToFetch
-- 2024-04-08T15:01:32.725Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=460,Updated=TO_TIMESTAMP('2024-04-08 15:01:32.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727820
;

-- Field: Fenster Verwaltung -> Feld -> Selection column ordering
-- Column: AD_Field.SelectionColumnSeqNo
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Selection column ordering
-- Column: AD_Field.SelectionColumnSeqNo
-- 2024-04-08T15:02:03.890Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2024-04-08 15:02:03.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727812
;

-- Field: Fenster Verwaltung -> Feld -> Filter Operator
-- Column: AD_Field.FilterOperator
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Operator
-- Column: AD_Field.FilterOperator
-- 2024-04-08T15:02:08.469Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2024-04-08 15:02:08.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727813
;

-- Field: Fenster Verwaltung -> Feld -> Override Filter Default Value
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Override Filter Default Value
-- Column: AD_Field.IsOverrideFilterDefaultValue
-- 2024-04-08T15:02:12.050Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2024-04-08 15:02:12.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727816
;

-- Field: Fenster Verwaltung -> Feld -> Filter Default Value
-- Column: AD_Field.FilterDefaultValue
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Default Value
-- Column: AD_Field.FilterDefaultValue
-- 2024-04-08T15:02:16.739Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2024-04-08 15:02:16.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727815
;

-- Field: Fenster Verwaltung -> Feld -> Show filter inline
-- Column: AD_Field.IsShowFilterInline
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Show filter inline
-- Column: AD_Field.IsShowFilterInline
-- 2024-04-08T15:02:21.587Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2024-04-08 15:02:21.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727814
;

-- Field: Fenster Verwaltung -> Feld -> Facet Filter
-- Column: AD_Field.IsFacetFilter
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Facet Filter
-- Column: AD_Field.IsFacetFilter
-- 2024-04-08T15:02:25.936Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2024-04-08 15:02:25.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727818
;

-- Field: Fenster Verwaltung -> Feld -> Facet Filter Sequence No
-- Column: AD_Field.FacetFilterSeqNo
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Facet Filter Sequence No
-- Column: AD_Field.FacetFilterSeqNo
-- 2024-04-08T15:02:28.939Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2024-04-08 15:02:28.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727819
;

-- Field: Fenster Verwaltung -> Feld -> Maximum Facets
-- Column: AD_Field.MaxFacetsToFetch
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Maximum Facets
-- Column: AD_Field.MaxFacetsToFetch
-- 2024-04-08T15:02:33.574Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2024-04-08 15:02:33.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727820
;

-- Field: Fenster Verwaltung -> Feld -> Facet Filter Sequence No
-- Column: AD_Field.FacetFilterSeqNo
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Facet Filter Sequence No
-- Column: AD_Field.FacetFilterSeqNo
-- 2024-04-08T15:03:16.427Z
UPDATE AD_Field SET DisplayLogic='@IsFacetFilter/X@=Y',Updated=TO_TIMESTAMP('2024-04-08 15:03:16.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727819
;

-- Field: Fenster Verwaltung -> Feld -> Maximum Facets
-- Column: AD_Field.MaxFacetsToFetch
-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Maximum Facets
-- Column: AD_Field.MaxFacetsToFetch
-- 2024-04-08T15:03:19.308Z
UPDATE AD_Field SET DisplayLogic='@IsFacetFilter/X@=Y',Updated=TO_TIMESTAMP('2024-04-08 15:03:19.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=727820
;

