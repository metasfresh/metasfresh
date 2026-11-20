-- IDs allocated from idserver.metas.de on 2026-11-19:
--   AD_Field      788190 (Eingabequelle window 540098, tab 540285)
--   AD_UI_Element 656509 (same tab, flags group 543171)
-- Reused: AD_Element 585625 + AD_Column 593855 (IsEDIChannel, migration 5836590)
-- No override window exists for window 540098 (Overrides_Window_ID = 540098 -> none).

-- Field: Eingabequelle(540098,de.metas.impex) -> Eingabequelle(540285) -> EDI-Kanal
-- 2026-11-19T14:10:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593855,788190 /*From ID Server*/,0,540285,TO_TIMESTAMP('2026-11-19 14:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.esb.edi','Y','Y','Y','N','N','N','N','N','EDI-Kanal',TO_TIMESTAMP('2026-11-19 14:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-11-19T14:10:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=788190
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-11-19T14:10:02.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585625)
;

-- 2026-11-19T14:10:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=788190
;

-- 2026-11-19T14:10:04.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(788190)
;

-- UI Element: Eingabequelle(540098) -> Eingabequelle(540285) -> main -> flags group (next to IsDestination): EDI-Kanal
-- grid: after Entitaets-Art (50), before InternalName (80)
-- 2026-11-19T14:11:00.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,788190,0,540285,543171,656509 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-19 14:11:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','EDI-Kanal',30,70,0,TO_TIMESTAMP('2026-11-19 14:11:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
