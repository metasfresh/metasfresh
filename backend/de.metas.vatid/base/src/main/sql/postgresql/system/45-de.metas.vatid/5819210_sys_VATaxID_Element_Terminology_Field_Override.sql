-- Completes the USt-IdNr. relabel started by 5819190_sys_VATaxID_Element_Terminology.sql.
--
-- 5819190 relabelled the shared AD_Element 502388 (ColumnName VATaxID). One AD_Field does not take its
-- caption from that element: AD_Field 645211 (tab 543900 "Fiskalvertretung" of window 110
-- "Organisation", on column C_Fiscal_Representation.VATaxID) carries an AD_Field.AD_Name_ID override
-- pointing at AD_Element 579135, whose de_DE/de_CH text still read "USt-ID". So after 5819190 that one
-- placement kept showing the old wording while all 17 other VATaxID field placements showed the new
-- one -- exactly the inconsistency the relabel set out to remove.
--
-- AD_Field.AD_Name_ID is an element reference just like AD_Column.AD_Element_ID, but a *different* one.
-- That is why an enumeration asking only "does anything reference element 502388" comes back clean
-- while a caption override still renders the pre-relabel text: the override element is what is
-- referenced, not the shared one. The check that actually settles completeness is "does any AD_Field on
-- a VATaxID column carry an AD_Name_ID override, and what does THAT element read".
--
-- Usage of element 579135 was enumerated against a customer-faithful DB before mutating: exactly one
-- renderable placement (AD_Field 645211 via AD_Name_ID) plus its AD_Element_Link bookkeeping row
-- (1006271, window 110 / field 645211). Nothing in AD_Column, AD_UI_Element.AD_Name_ID, AD_Window,
-- AD_Tab, AD_Menu, AD_Process_Para, AD_InfoColumn or WEBUI_KPI_Field references it. The element is
-- therefore dedicated to that single field, and the new wording is correct in its only usage, so the
-- element is mutated rather than forked.
--
-- en_US already reads "VAT ID" with IsTranslated='Y' -- identical to element 502388's en_US -- and is
-- left untouched. de_CH gets the identical de_DE text (no Eszett involved, so no Swiss-convention
-- transform is needed), matching how 5819190 treated 502388.
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5819210 (this file's prefix)

UPDATE AD_Element_Trl
SET Name = 'USt-IdNr.', PrintName = 'USt-IdNr.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-08-14 16:20:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 579135 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET Name = 'USt-IdNr.', PrintName = 'USt-IdNr.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-08-14 16:20:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 579135 AND AD_Language = 'de_CH';

-- Propagate the new text into the dependent rows: AD_Field_Trl via the AD_Name_ID branch of
-- update_FieldTranslation_From_AD_Name_Element, and -- for the base language only -- the AD_Element and
-- AD_Field base rows. AD_Element_Trl_Effective_v is the sync source, not AD_Element itself.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579135, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579135, 'de_CH');
