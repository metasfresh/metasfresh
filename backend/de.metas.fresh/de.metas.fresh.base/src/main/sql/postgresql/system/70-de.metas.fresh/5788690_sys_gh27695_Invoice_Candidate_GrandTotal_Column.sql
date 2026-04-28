-- Run mode: SWING_CLIENT

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Summe Gesamt
-- Column: C_Invoice_Candidate.GrandTotal
-- 2026-02-16T08:36:10.580Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592007,773917,0,543052,0,TO_TIMESTAMP('2026-02-16 08:36:09.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Summe über Alles zu diesem Beleg',0,'D',0,'Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Summe Gesamt',0,0,550,0,1,1,TO_TIMESTAMP('2026-02-16 08:36:09.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-16T08:36:10.645Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-16T08:36:10.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(316)
;

-- 2026-02-16T08:36:10.796Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773917
;

-- 2026-02-16T08:36:10.852Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773917)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> totals.Summe Gesamt
-- Column: C_Invoice_Candidate.GrandTotal
-- 2026-02-16T08:38:06.563Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773917,0,543052,544368,647942,'F',TO_TIMESTAMP('2026-02-16 08:38:05.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Summe über Alles zu diesem Beleg','Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','N','Y','N','N','N',0,'Summe Gesamt',30,0,0,TO_TIMESTAMP('2026-02-16 08:38:05.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> totals.Summe Gesamt
-- Column: C_Invoice_Candidate.GrandTotal
-- 2026-02-16T08:38:36.797Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-02-16 08:38:36.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647942
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2026-02-16T08:38:37.135Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-02-16 08:38:37.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573420
;

