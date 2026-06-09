-- Run mode: SWING_CLIENT

-- Column: M_Shipper.PickupTimeFrom
-- 2025-11-17T07:47:43.358Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591516,577278,0,24,253,'XX','PickupTimeFrom',TO_TIMESTAMP('2025-11-17 07:47:43.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','1970-01-01 08:00:00.000000','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abhoung Uhrzeit ab',0,0,TO_TIMESTAMP('2025-11-17 07:47:43.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-17T07:47:43.365Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591516 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-17T07:53:56.514Z
/* DDL */ SELECT public.db_alter_table('M_Shipper','ALTER TABLE public.M_Shipper ADD COLUMN PickupTimeFrom TIMESTAMP WITHOUT TIME ZONE')
;

-- 2025-11-17T07:47:43.391Z
/* DDL */  select update_Column_Translation_From_AD_Element(577278)
;

-- Column: M_Shipper.PickupTimeFrom
-- 2025-11-17T07:48:06.251Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-11-17 07:48:06.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591516
;

-- Column: M_Shipper.PickupTimeFrom
-- 2025-11-17T08:33:56.210Z
UPDATE AD_Column SET DefaultValue='1970-01-01 08:00:00.000000',Updated=TO_TIMESTAMP('2025-11-17 08:33:56.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591516
;

-- Column: M_Shipper.PickupTimeTo
-- 2025-11-17T07:49:03.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591517,577279,0,24,253,'XX','PickupTimeTo',TO_TIMESTAMP('2025-11-17 07:49:03.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','1970-01-01 17:00:00.000000','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abholung Uhrzeit bis',0,0,TO_TIMESTAMP('2025-11-17 07:49:03.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-17T07:49:03.430Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591517 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-17T07:49:03.553Z
/* DDL */  select update_Column_Translation_From_AD_Element(577279)
;

-- Column: M_Shipper.PickupTimeTo
-- 2025-11-17T09:18:08.476Z
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2025-11-17 09:18:08.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591517
;

-- 2025-11-17T09:18:11.388Z
/* DDL */ SELECT public.db_alter_table('M_Shipper','ALTER TABLE public.M_Shipper ADD COLUMN PickupTimeTo TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_Shipper.PickupTimeTo
-- 2025-11-17T09:18:15.143Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-11-17 09:18:15.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591517
;

-- Column: M_Shipper.PickupTimeTo
-- 2025-11-17T09:18:36.782Z
UPDATE AD_Column SET DefaultValue='1970-01-01 17:00:00.000000',Updated=TO_TIMESTAMP('2025-11-17 09:18:36.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591517
;

-- Field: Lieferweg(142,D) -> Lieferweg(185,D) -> Abhoung Uhrzeit ab
-- Column: M_Shipper.PickupTimeFrom
-- 2025-11-17T09:49:10.512Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591516,756157,0,185,0,TO_TIMESTAMP('2025-11-17 09:49:10.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Abhoung Uhrzeit ab',0,0,120,0,1,1,TO_TIMESTAMP('2025-11-17 09:49:10.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-17T09:49:10.514Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-17T09:49:10.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577278)
;

-- 2025-11-17T09:49:10.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756157
;

-- 2025-11-17T09:49:10.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756157)
;

-- Field: Lieferweg(142,D) -> Lieferweg(185,D) -> Abholung Uhrzeit bis
-- Column: M_Shipper.PickupTimeTo
-- 2025-11-17T09:49:20.969Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591517,756158,0,185,0,TO_TIMESTAMP('2025-11-17 09:49:20.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Abholung Uhrzeit bis',0,0,130,0,1,1,TO_TIMESTAMP('2025-11-17 09:49:20.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-17T09:49:20.971Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-17T09:49:20.972Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577279)
;

-- 2025-11-17T09:49:20.977Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756158
;

-- 2025-11-17T09:49:20.978Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756158)
;

-- Field: Lieferweg(142,D) -> Lieferweg(185,D) -> Abhoung Uhrzeit ab
-- Column: M_Shipper.PickupTimeFrom
-- 2025-11-17T09:49:24.141Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2025-11-17 09:49:24.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756157
;

-- UI Column: Lieferweg(142,D) -> Lieferweg(185,D) -> main -> 20
-- UI Element Group: org
-- 2025-11-17T09:49:45.650Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2025-11-17 09:49:45.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541022
;

-- UI Column: Lieferweg(142,D) -> Lieferweg(185,D) -> main -> 20
-- UI Element Group: times
-- 2025-11-17T09:49:57.498Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540579,553769,TO_TIMESTAMP('2025-11-17 09:49:57.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','times',20,TO_TIMESTAMP('2025-11-17 09:49:57.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> Lieferweg(185,D) -> main -> 20 -> times.Abhoung Uhrzeit ab
-- Column: M_Shipper.PickupTimeFrom
-- 2025-11-17T09:50:09.477Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756157,0,185,553769,638735,'F',TO_TIMESTAMP('2025-11-17 09:50:09.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Abhoung Uhrzeit ab',10,0,0,TO_TIMESTAMP('2025-11-17 09:50:09.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> Lieferweg(185,D) -> main -> 20 -> times.Abholung Uhrzeit bis
-- Column: M_Shipper.PickupTimeTo
-- 2025-11-17T09:50:18.070Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756158,0,185,553769,638736,'F',TO_TIMESTAMP('2025-11-17 09:50:17.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Abholung Uhrzeit bis',20,0,0,TO_TIMESTAMP('2025-11-17 09:50:17.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

