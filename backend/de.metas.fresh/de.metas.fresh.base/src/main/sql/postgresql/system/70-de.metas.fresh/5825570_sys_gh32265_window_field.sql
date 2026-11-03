-- Run mode: SWING_CLIENT

-- Field: Bestellkontrolle(540274,D) -> Bestellkontrolle(540703,D) -> default -> 10 -> Belegart
-- Column: C_Order_MFGWarehouse_Report.C_DocType_ID
-- Read-only per the frozen requirements (users must see, not edit, the resolved doctype).
-- Placement — right after the existing DocumentType ("Belegart") field in the same primary element
-- group — holds on the WebUI AD_UI_Element layer below (SeqNo 5 -> 6), and that is the layer this
-- tab renders from: tab 540703 is section-backed (AD_UI_Section 540265 -> AD_UI_Column 540359 ->
-- AD_UI_ElementGroup 540617, all active), so LayoutFactory orders the form from AD_UI_Element only.
-- On the legacy Swing AD_Field layer the order differs harmlessly: this field is SeqNo 6 while
-- DocumentType is SeqNo 10, so Swing renders it BEFORE DocumentType. SeqNo 6 is deliberate (0 would
-- hide the field in Swing), and C_DocType_ID is not a selection column, so filter ordering — which
-- does read AD_Field — never engages. No user-visible WebUI impact.
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
SELECT 0,593637,785045 /*From ID Server*/,0,540703,0,TO_TIMESTAMP('2026-09-22 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',0,'de.metas.fresh',0,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.',0,'Y','Y','N','N','N','N','N','N','Y','N',0,'Belegart',0,6,0,NULL,1,1,TO_TIMESTAMP('2026-09-22 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100
WHERE NOT EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID=785045)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785045
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Sync Name/Description/Help from the shared AD_Element 196 ("Belegart") onto AD_Field_Trl.
-- The function takes only (p_ad_element_id, p_ad_language) — no timestamp — and its guard is a plain
-- INEQUALITY, `f_trl.updated <> e_trl.updated`, not a lateness comparison. What makes it fire here:
-- the AD_Field_Trl seed above copies AD_Field.Updated (2026-09-22 10:00) into every language row,
-- which differs from AD_Element_Trl.Updated on the live AD_Element 196 rows (2012-2018) — so every
-- row qualifies and takes the element's Name/Description/Help plus its Updated.
SELECT update_FieldTranslation_From_AD_Name_Element(196)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=785045
;

SELECT AD_Element_Link_Create_Missing_Field(785045)
;

-- UI Element: Bestellkontrolle(540274,D) -> Bestellkontrolle(540703,D) -> main -> 10 -> default.Belegart (doctype)
-- Column: C_Order_MFGWarehouse_Report.C_DocType_ID
-- Same primary element group (540617) as the existing DocumentType ("Belegart") field, placed
-- right after it (SeqNo 6, between DocumentType's 5 and C_Order_ID's 10). Grid intentionally left
-- off (IsDisplayedGrid='N') to avoid a second grid column captioned identically "Belegart" next to
-- the existing ref-list one — only the form view is in scope per the requirements.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
SELECT 0,785045,0,540703,540617,654787 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','N','N',0,'Belegart',6,0,0,TO_TIMESTAMP('2026-09-22 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100
WHERE NOT EXISTS (SELECT 1 FROM AD_UI_Element u WHERE u.AD_UI_Element_ID=654787)
;
