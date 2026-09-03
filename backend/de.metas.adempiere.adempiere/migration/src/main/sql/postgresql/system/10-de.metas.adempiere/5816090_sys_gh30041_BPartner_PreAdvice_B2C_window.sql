-- nShift pre-advice: place C_BPartner.IsPreAdviceRequired on the B2C Business Partner window
-- (540354, C_BPartner main tab 540843), main-view element group 540901 "default", after Memo.
-- Pre-advice is a master-data config default: NO DisplayLogic, main view (IsAdvancedField='N').
-- Column C_BPartner.IsPreAdviceRequired (AD_Column 592703) and its AD_Element (584937) are
-- created by migration 5805950; this only places the field on this window.
-- IDs allocated from idserver.metas.de on 2026-07-24:
--   AD_MigrationScript 5816090
--   AD_Field 781836 (IsPreAdviceRequired on tab 540843)
--   AD_UI_Element 652743 (main-view element in group 540901)
-- Reused: AD_Column 592703, AD_Element 584937.

-- ============================================================================
-- IsPreAdviceRequired field on tab 540843 + UI element in group 540901 (default, main view)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592703,781836 /*From ID Server*/,0,540843,TO_TIMESTAMP('2026-07-24 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','N','Voranmeldung erforderlich',TO_TIMESTAMP('2026-07-24 11:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=781836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584937)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781836
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(781836)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,0,781836,540843,652743 /*From ID Server*/,540901,'F',TO_TIMESTAMP('2026-07-24 11:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Voranmeldung erforderlich',60,0,0,TO_TIMESTAMP('2026-07-24 11:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

SELECT add_missing_translations()
;
