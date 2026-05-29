-- Run mode: SWING_CLIENT



-- 2025-08-27T08:06:31.517Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583880,0,'CalendarWeek',TO_TIMESTAMP('2025-08-27 08:06:30.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','KW','KW',TO_TIMESTAMP('2025-08-27 08:06:30.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-27T08:06:31.586Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583880 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-08-27T08:07:32.747Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590640,583880,0,22,540524,'XX','CalendarWeek',TO_TIMESTAMP('2025-08-27 08:07:31.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.inoutcandidate',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'KW',0,0,TO_TIMESTAMP('2025-08-27 08:07:31.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-27T08:07:32.815Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590640 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-27T08:07:32.977Z
/* DDL */  select update_Column_Translation_From_AD_Element(583880)
;

-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-08-28T09:00:48.368Z
UPDATE AD_Column SET ColumnSQL='(SELECT EXTRACT(WEEK from M_ReceiptSchedule.MovementDate))', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-08-28 09:00:48.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590640
;

-- Field: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-08-27T08:10:04.395Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590640,752521,0,540526,0,TO_TIMESTAMP('2025-08-27 08:10:03.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'KW',0,0,240,0,1,1,TO_TIMESTAMP('2025-08-27 08:10:03.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-27T08:10:04.470Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752521 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-27T08:10:04.540Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583880)
;

-- 2025-08-27T08:10:04.633Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752521
;

-- 2025-08-27T08:10:04.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752521)
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> dates.KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-08-27T08:13:03.464Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,752521,0,540526,540133,636466,'F',TO_TIMESTAMP('2025-08-27 08:13:02.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'KW',15,0,0,TO_TIMESTAMP('2025-08-27 08:13:02.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> dates.KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-08-27T08:15:29.839Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-08-27 08:15:29.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636466
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> dates.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- 2025-08-27T08:15:30.250Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-08-27 08:15:30.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541878
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- 2025-08-27T08:15:30.664Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-08-27 08:15:30.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541817
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- 2025-08-27T08:15:31.389Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-08-27 08:15:31.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541818
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> org.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2025-08-27T08:15:31.804Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-08-27 08:15:31.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541883
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2025-08-27T08:15:32.220Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-08-27 08:15:32.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541821
;

-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> main -> 20 -> org.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2025-08-27T08:15:32.639Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-08-27 08:15:32.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541880
;

-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-08-27T08:16:48.972Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-27 08:16:48.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590640
;

