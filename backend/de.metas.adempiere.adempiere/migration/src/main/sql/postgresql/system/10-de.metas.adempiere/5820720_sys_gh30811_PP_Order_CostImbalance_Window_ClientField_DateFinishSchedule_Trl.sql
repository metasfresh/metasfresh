-- Review follow-up to 5814680_sys_gh30811_PP_Order_CostImbalance_Window.sql (already released,
-- therefore immutable). Two corrections to the manufacturing cost-monitoring window (542175):
--
-- 1) The bottom-right org/client element group (AD_UI_ElementGroup 555517) only carried AD_Org_ID.
--    The standard layout requires AD_Org_ID first, then AD_Client_ID. AD_Client_ID is added to the
--    form only -- it must never become a grid column, so IsDisplayedGrid='N' on both the AD_Field
--    and the AD_UI_Element.
--
-- 2) AD_Element 53278 (DateFinishSchedule) carried the raw column name as its de_DE / de_CH
--    Name and PrintName, so every window that shows that column rendered "DateFinishSchedule"
--    to German users (windows 53009, 53064 and the new 542175). The German text is written into
--    the element translations and cascaded, which also repairs the base AD_Element row (de_DE is
--    the base language) and every AD_Column / AD_Field that resolves its caption from it.
--
-- IDs allocated from idserver.metas.de:
--   AD_Field      783023 (PP_Order.AD_Client_ID on tab 549352)
--   AD_UI_Element 653672 (AD_Client_ID in element group 555517)

-- 1) AD_Client_ID field on tab 549352.
--    AD_Column 53680 = PP_Order.AD_Client_ID, backed by AD_Element 102 ("Mandant").
--    SeqNo 20 places it directly after AD_Org_ID (SeqNo 10) in the org/client group.
--    SeqNoGrid 0 + IsDisplayedGrid 'N' keep it out of the grid, matching how the same script
--    treats the other form-only field (IsActive).
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53680,783023 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-08-27 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N','N','N','N','N','N','Mandant',20,0,TO_TIMESTAMP('2026-08-27 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=783023
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(102)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783023
;
select AD_Element_Link_Create_Missing_Field(783023)
;

-- AD_UI_Element for the new field: same group 555517, right after AD_Org_ID, form only.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,783023,0,549352,555517,653672 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-27 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2026-08-27 10:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2) AD_Element 53278 (DateFinishSchedule): supply the German caption for the German languages.
UPDATE AD_Element_Trl SET Name='Datum Fertigstellung geplant', PrintName='Datum Fertigstellung geplant',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=53278 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Datum Fertigstellung geplant', PrintName='Datum Fertigstellung geplant',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 10:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=53278 AND AD_Language='de_CH'
;
-- Cascade into AD_Element (base language), AD_Column_Trl and AD_Field_Trl, so windows 53009,
-- 53064 and 542175 all show the translated caption.
select update_TRL_Tables_On_AD_Element_TRL_Update(53278)
;
