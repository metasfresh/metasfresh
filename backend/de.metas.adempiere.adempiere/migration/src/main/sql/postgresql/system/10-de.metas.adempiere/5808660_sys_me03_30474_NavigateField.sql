-- Expose MobileUI_UserProfile_DD.IsNavigateToJobsListAfterPickFromComplete in the Mobile Distribution Profile window.
-- Column 591661 already exists; this migration adds AD_Field 781216 and AD_UI_Element 652328
-- so the toggle renders in the "job" element group (553846) at SeqNo 40 next to its sibling
-- completion toggles (IsRequireScanningProductCode=10, IsCompleteJobAutomatically=20,
-- IsPrintDDOrderOnComplete=30).
-- Reuses existing AD_Element 584328 and its de_DE/de_CH/en_US translations.
-- me03 #30474

--
-- AD_Field on Mobile Distribution Profile tab (AD_Tab 547735)
-- Reuses AD_Column 591661 (IsNavigateToJobsListAfterPickFromComplete) and AD_Element 584328.
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,591661,781216 /*From ID Server*/,0,547735,TO_TIMESTAMP('2026-06-18 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','Y','N','N','N','N','N','N','Gehe zu Liste nach Pick-Ende',TO_TIMESTAMP('2026-06-18 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=781216
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584328)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781216
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781216)
;

--
-- AD_UI_Element: section 546319 / column 547725 (right column) / group 553846 (job) / SeqNo 40
-- Places the field after IsPrintDDOrderOnComplete (SeqNo 30) in the completion-toggle group.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781216,0,547735,553846,652328 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-18 10:00:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Gehe zu Liste nach Pick-Ende',40,0,0,TO_TIMESTAMP('2026-06-18 10:00:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

--
-- Defensive: ensure every translatable table has rows for every active language.
SELECT add_missing_translations()
;

-- Propagate the element's translations across all derived _Trl tables.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584328 /*IsNavigateToJobsListAfterPickFromComplete, From ID Server*/)
;
