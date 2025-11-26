-- Run mode: SWING_CLIENT

-- Column: C_Order.ETA
-- 2025-10-27T16:31:32.284Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591441,584067,0,15,259,'XX','ETA',TO_TIMESTAMP('2025-10-27 16:31:32.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Voraussichtliches Ankunftsdatum','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ETA',0,0,TO_TIMESTAMP('2025-10-27 16:31:32.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-27T16:31:32.296Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591441 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-27T16:31:32.329Z
/* DDL */  select update_Column_Translation_From_AD_Element(584067)
;

-- 2025-10-27T16:31:39.225Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN ETA TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: C_Order.BLDate
-- 2025-10-27T16:31:48.064Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591442,584075,0,15,259,'XX','BLDate',TO_TIMESTAMP('2025-10-27 16:31:47.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'B/L Datum',0,0,TO_TIMESTAMP('2025-10-27 16:31:47.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-27T16:31:48.069Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591442 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-27T16:31:48.190Z
/* DDL */  select update_Column_Translation_From_AD_Element(584075)
;

-- 2025-10-27T16:31:54.388Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN BLDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: C_Order.InvoiceDate
-- 2025-10-27T16:32:44.516Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591443,578690,0,15,259,'XX','InvoiceDate',TO_TIMESTAMP('2025-10-27 16:32:44.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Invoice date',0,0,TO_TIMESTAMP('2025-10-27 16:32:44.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-27T16:32:44.523Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591443 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-27T16:32:44.643Z
/* DDL */  select update_Column_Translation_From_AD_Element(578690)
;

-- 2025-10-27T16:32:46.555Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN InvoiceDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Field: Bestellung_OLD(181,D) -> Bestellung(294,D) -> ETA
-- Column: C_Order.ETA
-- 2025-10-28T08:40:32.265Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591441,755107,0,294,0,TO_TIMESTAMP('2025-10-28 08:40:32.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ETA',0,0,230,0,1,1,TO_TIMESTAMP('2025-10-28 08:40:32.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-28T08:40:32.280Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-28T08:40:32.290Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584067)
;

-- 2025-10-28T08:40:32.315Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755107
;

-- 2025-10-28T08:40:32.328Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755107)
;

-- Field: Bestellung_OLD(181,D) -> Bestellung(294,D) -> B/L Datum
-- Column: C_Order.BLDate
-- 2025-10-28T08:40:41.682Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591442,755108,0,294,0,TO_TIMESTAMP('2025-10-28 08:40:41.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'B/L Datum',0,0,240,0,1,1,TO_TIMESTAMP('2025-10-28 08:40:41.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-28T08:40:41.685Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-28T08:40:41.690Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584075)
;

-- 2025-10-28T08:40:41.699Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755108
;

-- 2025-10-28T08:40:41.701Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755108)
;

-- Field: Bestellung_OLD(181,D) -> Bestellung(294,D) -> Invoice date
-- Column: C_Order.InvoiceDate
-- 2025-10-28T08:40:47.790Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591443,755109,0,294,0,TO_TIMESTAMP('2025-10-28 08:40:47.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Invoice date',0,0,250,0,1,1,TO_TIMESTAMP('2025-10-28 08:40:47.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-28T08:40:47.794Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-28T08:40:47.796Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578690)
;

-- 2025-10-28T08:40:47.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755109
;

-- 2025-10-28T08:40:47.804Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755109)
;

-- Field: Bestellung_OLD(181,D) -> Bestellung(294,D) -> Akkreditivdatum
-- Column: C_Order.LC_Date
-- 2025-10-28T08:41:19.442Z
UPDATE AD_Field SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2025-10-28 08:41:19.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754989
;

-- UI Column: Bestellung_OLD(181,D) -> Bestellung(294,D) -> advanced edit -> 10
-- UI Element Group: dates
-- 2025-10-28T08:41:54.649Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540543,553653,TO_TIMESTAMP('2025-10-28 08:41:54.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dates',20,'',TO_TIMESTAMP('2025-10-28 08:41:54.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> dates.Invoice date
-- Column: C_Order.InvoiceDate
-- 2025-10-28T08:42:07.315Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755109,0,294,553653,637957,'F',TO_TIMESTAMP('2025-10-28 08:42:07.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Invoice date',10,0,0,TO_TIMESTAMP('2025-10-28 08:42:07.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> dates.B/L Datum
-- Column: C_Order.BLDate
-- 2025-10-28T08:42:13.236Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755108,0,294,553653,637958,'F',TO_TIMESTAMP('2025-10-28 08:42:13.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'B/L Datum',20,0,0,TO_TIMESTAMP('2025-10-28 08:42:13.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> dates.ETA
-- Column: C_Order.ETA
-- 2025-10-28T08:42:18.799Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755107,0,294,553653,637959,'F',TO_TIMESTAMP('2025-10-28 08:42:18.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum','Y','N','N','Y','N','N','N',0,'ETA',30,0,0,TO_TIMESTAMP('2025-10-28 08:42:18.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

----------------------


-- Column: C_Order.InvoiceDate
-- 2025-10-28T14:00:17.629Z
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2025-10-28 14:00:17.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591443
;

-- Column: C_Order.ETA
-- 2025-10-28T14:00:28.560Z
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2025-10-28 14:00:28.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591441
;

-- Column: C_Order.BLDate
-- 2025-10-28T14:00:38.518Z
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2025-10-28 14:00:38.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591442
;

