-- Run mode: SWING_CLIENT

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Bestätigt durch Lieferant
-- Column: C_Order.IsConfirmedBySupplier
-- 2025-12-05T08:48:56.575Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=639730
;

-- 2025-12-05T08:48:56.579Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758516
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Bestätigt durch Lieferant
-- Column: C_Order.IsConfirmedBySupplier
-- 2025-12-05T08:48:56.586Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=758516
;

-- 2025-12-05T08:48:56.596Z
DELETE FROM AD_Field WHERE AD_Field_ID=758516
;

-- 2025-12-05T08:48:56.600Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE C_Order DROP COLUMN IF EXISTS IsConfirmedBySupplier')
;

-- Column: C_Order.IsConfirmedBySupplier
-- 2025-12-05T08:48:57.885Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591613
;

-- 2025-12-05T08:48:57.895Z
DELETE FROM AD_Column WHERE AD_Column_ID=591613
;

-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2025-12-05T08:49:39.407Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591673,584306,0,20,540524,'XX','IsConfirmedBySupplier',TO_TIMESTAMP('2025-12-05 08:49:39.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.inoutcandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bestätigt durch Lieferant',0,0,TO_TIMESTAMP('2025-12-05 08:49:39.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-05T08:49:39.411Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591673 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-05T08:49:39.541Z
/* DDL */  select update_Column_Translation_From_AD_Element(584306)
;

-- 2025-12-05T08:49:41.964Z
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN IsConfirmedBySupplier CHAR(1) DEFAULT ''N'' CHECK (IsConfirmedBySupplier IN (''Y'',''N'')) NOT NULL')
;

-- Field: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> Bestätigt durch Lieferant
-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2025-12-05T08:51:53.807Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591673,758542,0,540526,0,TO_TIMESTAMP('2025-12-05 08:51:53.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Bestätigt durch Lieferant',0,0,250,0,1,1,TO_TIMESTAMP('2025-12-05 08:51:53.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-05T08:51:53.810Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758542 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-05T08:51:53.813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584306)
;

-- 2025-12-05T08:51:53.818Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758542
;

-- 2025-12-05T08:51:53.821Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758542)
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bestätigt durch Lieferant
-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2025-12-05T09:08:33.114Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758542,0,540526,540129,639737,'F',TO_TIMESTAMP('2025-12-05 09:08:32.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Bestätigt durch Lieferant',430,0,0,TO_TIMESTAMP('2025-12-05 09:08:32.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

