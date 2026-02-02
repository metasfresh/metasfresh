-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.C_Invoice_Candidate_ID
-- 2026-02-02T08:45:11.304Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591907,541266,0,30,541234,500221,'XX','C_Invoice_Candidate_ID','(SELECT C_Invoice_Candidate_ID from C_Invoice_Candidate where isSOTrx = ''N'' AND M_ShipmentSchedule_ID = M_ShipmentSchedule.M_ShipmentSchedule_ID)',TO_TIMESTAMP('2026-02-02 08:45:10.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','de.metas.inoutcandidate',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N',0,'Rechnungskandidat',0,0,TO_TIMESTAMP('2026-02-02 08:45:10.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-02T08:45:11.376Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591907 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-02T08:45:11.497Z
/* DDL */  select update_Column_Translation_From_AD_Element(541266)
;

-- Field: Lieferdisposition_OLD(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Rechnungsdisposition Einkauf
-- Column: M_ShipmentSchedule.C_Invoice_Candidate_ID
-- 2026-02-02T08:47:20.359Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591907,771159,578409,0,500221,0,TO_TIMESTAMP('2026-02-02 08:47:19.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Rechnungsdisposition Einkauf',0,0,770,0,1,1,TO_TIMESTAMP('2026-02-02 08:47:19.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-02T08:47:20.421Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771159 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-02T08:47:20.564Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578409)
;

-- 2026-02-02T08:47:20.634Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771159
;

-- 2026-02-02T08:47:20.769Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771159)
;

-- UI Element: Lieferdisposition_OLD(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Rechnungsdisposition Einkauf
-- Column: M_ShipmentSchedule.C_Invoice_Candidate_ID
-- 2026-02-02T08:48:29.712Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771159,0,500221,540972,646289,'F',TO_TIMESTAMP('2026-02-02 08:48:29.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Rechnungsdisposition Einkauf',70,0,0,TO_TIMESTAMP('2026-02-02 08:48:29.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferdisposition_OLD(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Rechnungsdisposition Einkauf
-- Column: M_ShipmentSchedule.C_Invoice_Candidate_ID
-- 2026-02-02T08:49:48.779Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-02-02 08:49:48.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646289
;

-- UI Element: Lieferdisposition_OLD(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> org.Sektion
-- Column: M_ShipmentSchedule.AD_Org_ID
-- 2026-02-02T08:49:49.150Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-02-02 08:49:49.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547302
;

-- Column: M_ShipmentSchedule.M_Product_ID
-- 2026-02-02T08:53:02.915Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-02-02 08:53:02.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=500231
;

-- Column: C_Invoice_Candidate.M_Product_ID
-- 2026-02-02T08:56:41.703Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2026-02-02 08:56:41.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544923
;

-- Column: C_Invoice_Candidate.Bill_BPartner_ID
-- 2026-02-02T08:57:33.510Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2026-02-02 08:57:33.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544920
;

