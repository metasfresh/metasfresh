-- Two corrections to the manufacturing cost-monitoring window (542175), whose own script (5814680) is
-- already released and therefore immutable:
--   1) the org/client element group (AD_UI_ElementGroup 555517) carried only AD_Org_ID; the standard
--      layout wants AD_Org_ID first, then AD_Client_ID, form-only (never a grid column);
--   2) AD_Element 53278 (DateFinishSchedule) carried the raw column name as its German Name/PrintName,
--      so German users saw "DateFinishSchedule" in every window showing that column.
--
-- IDs allocated from idserver.metas.de: AD_Field 783023, AD_UI_Element 653672

-- 1) AD_Client_ID (AD_Column 53680, AD_Element 102) on tab 549352: SeqNo 20 places it right after
--    AD_Org_ID; SeqNoGrid 0 + IsDisplayedGrid 'N' keep it out of the grid, as for IsActive.
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

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,783023,0,549352,555517,653672 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-27 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2026-08-27 10:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2) AD_Element 53278 (DateFinishSchedule): supply the German caption.
UPDATE AD_Element_Trl SET Name='Datum Fertigstellung geplant', PrintName='Datum Fertigstellung geplant',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=53278 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Datum Fertigstellung geplant', PrintName='Datum Fertigstellung geplant',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-27 10:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=53278 AND AD_Language='de_CH'
;
-- Cascades into AD_Element (de_DE is the base language), AD_Column_Trl and AD_Field_Trl.
select update_TRL_Tables_On_AD_Element_TRL_Update(53278)
;
