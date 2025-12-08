-- Field: EDI Lieferavis (DESADV) -> DESADV Zeile -> TU-GTIN
-- Column: EDI_DesadvLine.GTIN_TU
-- Field: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> DESADV Zeile(540663,de.metas.esb.edi) -> TU-GTIN
-- Column: EDI_DesadvLine.GTIN_TU
-- 2025-03-11T17:00:27.794Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588988,740376,0,540663,0,TO_TIMESTAMP('2025-03-11 17:00:26.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.esb.edi',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'TU-GTIN',0,0,190,0,1,1,TO_TIMESTAMP('2025-03-11 17:00:26.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-11T17:00:28.166Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740376 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-11T17:00:28.238Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583258) 
;

-- 2025-03-11T17:00:28.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740376
;

-- 2025-03-11T17:00:28.350Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740376)
;

-- UI Element: EDI Lieferavis (DESADV) -> DESADV Zeile.TU-GTIN
-- Column: EDI_DesadvLine.GTIN_TU
-- UI Element: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> DESADV Zeile(540663,de.metas.esb.edi) -> main -> 10 -> default.TU-GTIN
-- Column: EDI_DesadvLine.GTIN_TU
-- 2025-03-11T17:01:17.506Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740376,0,540663,541228,630679,'F',TO_TIMESTAMP('2025-03-11 17:01:16.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'TU-GTIN',43,0,0,TO_TIMESTAMP('2025-03-11 17:01:16.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: EDI Lieferavis (DESADV) -> DESADV Zeile.EAN_TU
-- Column: EDI_DesadvLine.EAN_TU
-- UI Element: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> DESADV Zeile(540663,de.metas.esb.edi) -> main -> 10 -> default.EAN_TU
-- Column: EDI_DesadvLine.EAN_TU
-- 2025-03-11T17:02:43.497Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-03-11 17:02:43.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=563125
;

-- UI Element: EDI Lieferavis (DESADV) -> DESADV Zeile.TU-GTIN
-- Column: EDI_DesadvLine.GTIN_TU
-- UI Element: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> DESADV Zeile(540663,de.metas.esb.edi) -> main -> 10 -> default.TU-GTIN
-- Column: EDI_DesadvLine.GTIN_TU
-- 2025-03-11T17:02:44.007Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-03-11 17:02:44.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630679
;

