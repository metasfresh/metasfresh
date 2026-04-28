-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job.PickingJobAggregationType
-- 2025-02-27T12:05:24.857Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589746,583513,0,17,541931,541906,'XX','PickingJobAggregationType',TO_TIMESTAMP('2025-02-27 12:05:24.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','sales_order','de.metas.handlingunits',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Aggregation Type',0,0,TO_TIMESTAMP('2025-02-27 12:05:24.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-27T12:05:24.860Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-27T12:05:24.864Z
/* DDL */  select update_Column_Translation_From_AD_Element(583513)
;

-- 2025-02-27T12:05:25.568Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN PickingJobAggregationType VARCHAR(20) DEFAULT ''sales_order'' NOT NULL')
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Übergabeadresse
-- Column: M_Picking_Job.HandOver_Location_ID
-- 2025-02-27T12:05:43.263Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587879,740350,0,544861,TO_TIMESTAMP('2025-02-27 12:05:42.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Übergabeadresse',TO_TIMESTAMP('2025-02-27 12:05:42.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T12:05:43.266Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740350 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-27T12:05:43.267Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542281)
;

-- 2025-02-27T12:05:43.277Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740350
;

-- 2025-02-27T12:05:43.278Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740350)
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Übergabe-Partner
-- Column: M_Picking_Job.HandOver_Partner_ID
-- 2025-02-27T12:05:43.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587881,740351,0,544861,TO_TIMESTAMP('2025-02-27 12:05:43.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Übergabe-Partner',TO_TIMESTAMP('2025-02-27 12:05:43.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T12:05:43.399Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740351 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-27T12:05:43.400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542280)
;

-- 2025-02-27T12:05:43.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740351
;

-- 2025-02-27T12:05:43.407Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740351)
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Packvorschrift (TU)
-- Column: M_Picking_Job.M_TU_HU_PI_ID
-- 2025-02-27T12:05:43.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588931,740352,0,544861,TO_TIMESTAMP('2025-02-27 12:05:43.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Packvorschrift (TU)',TO_TIMESTAMP('2025-02-27 12:05:43.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T12:05:43.522Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740352 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-27T12:05:43.524Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542489)
;

-- 2025-02-27T12:05:43.532Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740352
;

-- 2025-02-27T12:05:43.533Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740352)
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Aggregation Type
-- Column: M_Picking_Job.PickingJobAggregationType
-- 2025-02-27T12:05:43.650Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589746,740353,0,544861,TO_TIMESTAMP('2025-02-27 12:05:43.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,20,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Aggregation Type',TO_TIMESTAMP('2025-02-27 12:05:43.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T12:05:43.651Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740353 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-27T12:05:43.653Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583513)
;

-- 2025-02-27T12:05:43.660Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740353
;

-- 2025-02-27T12:05:43.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740353)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10 -> main.Aggregation Type
-- Column: M_Picking_Job.PickingJobAggregationType
-- 2025-02-27T12:06:21.641Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740353,0,544861,551273,630659,'F',TO_TIMESTAMP('2025-02-27 12:06:21.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Aggregation Type',60,0,0,TO_TIMESTAMP('2025-02-27 12:06:21.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

