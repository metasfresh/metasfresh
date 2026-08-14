-- Aligns AD_Element 502388 (ColumnName VATaxID) de_DE/de_CH label with the *USt-IdNr.* wording
-- already used in the AD_Message text and throughout this issue's own artefacts. Before this
-- script the element read "Umsatzsteuer ID" in de_DE and "Umsatzsteuer-ID" in de_CH.
--
-- Usage was enumerated against the live DB before mutating: 9 AD_Column rows (C_BPartner,
-- C_BPartner_Location, C_BPartner_QuickInput, C_Fiscal_Representation, C_VAT_SmallBusiness,
-- EDI_cctop_119_v, EDI_cctop_invoic_v, Intrastat_Report_Detail_V, and this module's own
-- VATaxID_CheckLog) plus 1 AD_Process_Para (Initial Setup Wizard's VATaxID parameter). No
-- AD_Field.AD_Name_ID override, no AD_Window, no AD_Tab reference this element. *USt-IdNr.*
-- reads correctly in every one of those 10 usages, so the shared element is mutated, not forked.
--
-- en_US already reads "VAT ID" with IsTranslated='Y' -- left untouched. de_CH gets the
-- identical de_DE text (no Eszett involved, so no Swiss-convention transform is needed).

-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5819190 (this file's prefix)

UPDATE AD_Element_Trl
SET Name = 'USt-IdNr.', PrintName = 'USt-IdNr.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-08-14 13:52:33.131', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy = 100
WHERE AD_Element_ID = 502388 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET Name = 'USt-IdNr.', PrintName = 'USt-IdNr.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-08-14 13:52:33.231', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy = 100
WHERE AD_Element_ID = 502388 AND AD_Language = 'de_CH';

-- Propagate to every dependent AD_Column_Trl / AD_Field_Trl row so the WebUI actually renders
-- the new caption (AD_Element_Trl_Effective_v is the sync source, not AD_Element itself).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(502388, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(502388, 'de_CH');
