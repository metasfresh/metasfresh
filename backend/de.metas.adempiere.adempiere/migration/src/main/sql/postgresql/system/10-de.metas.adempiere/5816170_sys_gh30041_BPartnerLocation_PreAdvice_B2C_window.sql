-- nShift pre-advice: place C_BPartner_Location.IsPreAdviceRequired on the B2C Business Partner
-- window (540354), C_BPartner_Location "Adresse" tab 540847, "default" element group 540897.
-- Placed in the ADVANCED edit area (IsAdvancedField='Y'), alongside the existing advanced field
-- Attention, at SeqNo 90 (after the tab's last element at SeqNo 80). NO DisplayLogic.
-- Column C_BPartner_Location.IsPreAdviceRequired (AD_Column 592702) + AD_Element 584937 are created
-- by the core migration 5805940; this only places the field on window 540354.
-- IDs allocated from idserver.metas.de on 2026-07-24:
--   AD_MigrationScript 5816170
--   AD_Field 781843 (IsPreAdviceRequired on tab 540847)
--   AD_UI_Element 652767 (advanced-edit element in group 540897)
-- Reused: AD_Column 592702, AD_Element 584937.

-- ============================================================================
-- IsPreAdviceRequired field on tab 540847 + UI element in group 540897 (advanced edit)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592702,781843 /*From ID Server*/,0,540847,TO_TIMESTAMP('2026-07-24 11:06:00','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','N','Voranmeldung erforderlich',TO_TIMESTAMP('2026-07-24 11:06:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584937)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781843
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(781843)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,0,781843,540847,652767 /*From ID Server*/,540897,'F',TO_TIMESTAMP('2026-07-24 11:06:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Voranmeldung erforderlich',90,0,0,TO_TIMESTAMP('2026-07-24 11:06:30','YYYY-MM-DD HH24:MI:SS'),100)
;

SELECT add_missing_translations()
;
