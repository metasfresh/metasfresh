-- IDs allocated from idserver.metas.de on 2026-11-19:
--   AD_Field      788191 (Auftrag window 143, tab 186)
--   AD_UI_Element 656510 (same tab, "advanced edit" group 540499)
-- Reused: AD_Element 585626 + AD_Column 593856 (IsPOReferenceBuyerOrderNo, migration 5836630)
-- Customer override windows of this window are handled in the respective customer repo, not here.

-- Field: Auftrag(143,D) -> Auftrag(186) -> PO-Referenz ist Bestellnr. des Kunden. User-editable (IsReadOnly='N').
-- 2026-11-19T15:10:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593856,788191 /*From ID Server*/,0,186,TO_TIMESTAMP('2026-11-19 15:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','Y','N','N','N','N','N','N','PO-Referenz ist Bestellnr. des Kunden',TO_TIMESTAMP('2026-11-19 15:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-11-19T15:10:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=788191
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-11-19T15:10:02.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585626)
;

-- 2026-11-19T15:10:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=788191
;

-- 2026-11-19T15:10:04.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(788191)
;

-- UI Element: Auftrag(143) -> Auftrag(186) -> advanced edit group: after "Eingegangen via" (250), before "Gültig bis" (270); not in grid
-- 2026-11-19T15:11:00.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,788191,0,186,540499,656510 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-19 15:11:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','Y','N','N','PO-Referenz ist Bestellnr. des Kunden',255,0,0,TO_TIMESTAMP('2026-11-19 15:11:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
