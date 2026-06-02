-- Tax Declaration — rename AD_Elements to ERP/SAP-style German base names
-- PR review feedback on https://github.com/metasfresh/metasfresh/pull/24064
-- 4 elements affected:
--   2863  C_TaxDeclarationLine_ID  -> Steuererklärungsposition
--   2864  C_TaxDeclarationAcct_ID  -> Steuererklärungs-Kontierung
--   584856 LineCount               -> Positionsanzahl
--   584857 C_TaxDeclaration_Legacy -> Steuererklärung (alt)
-- 584856 + 584857 introduced by integrated scripts 5801980 + 5802040 (PR https://github.com/metasfresh/metasfresh/pull/23924),
-- which are immutable per metasfresh-db rule — hence a follow-up rename script here rather than in-place edits.
--
-- Pattern per metasfresh-application-dictionary skill: base language is de_DE; always update
-- AD_Element_Trl (de_DE, de_CH, en_US), never AD_Element directly. The cascade
-- update_TRL_Tables_On_AD_Element_TRL_Update(id, NULL) then syncs AD_Element.Name from
-- AD_Element_Trl[de_DE] and propagates the rename to AD_Column, AD_Field, AD_Process_Para,
-- AD_PrintFormatItem, AD_Tab, AD_Window, AD_Menu.

-- ===========================================================================================
-- AD_Element 2863 — C_TaxDeclarationLine_ID
-- ===========================================================================================
UPDATE AD_Element_Trl
SET Name = 'Steuererklärungsposition', PrintName = 'Steuererklärungsposition',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:01', UpdatedBy = 100
WHERE AD_Element_ID = 2863 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET Name = 'Steuererklärungsposition', PrintName = 'Steuererklärungsposition',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:02', UpdatedBy = 100
WHERE AD_Element_ID = 2863 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET Name = 'Tax Declaration Line', PrintName = 'Tax Declaration Line',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:03', UpdatedBy = 100
WHERE AD_Element_ID = 2863 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2863, NULL);

-- ===========================================================================================
-- AD_Element 2864 — C_TaxDeclarationAcct_ID
-- ===========================================================================================
UPDATE AD_Element_Trl
SET Name = 'Steuererklärungs-Kontierung', PrintName = 'Steuererklärungs-Kontierung',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:04', UpdatedBy = 100
WHERE AD_Element_ID = 2864 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET Name = 'Steuererklärungs-Kontierung', PrintName = 'Steuererklärungs-Kontierung',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:05', UpdatedBy = 100
WHERE AD_Element_ID = 2864 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET Name = 'Tax Declaration Accounting', PrintName = 'Tax Declaration Accounting',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:06', UpdatedBy = 100
WHERE AD_Element_ID = 2864 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2864, NULL);

-- ===========================================================================================
-- AD_Element 584856 — LineCount
-- ===========================================================================================
UPDATE AD_Element_Trl
SET Name = 'Positionsanzahl', PrintName = 'Positionsanzahl',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:07', UpdatedBy = 100
WHERE AD_Element_ID = 584856 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET Name = 'Positionsanzahl', PrintName = 'Positionsanzahl',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:08', UpdatedBy = 100
WHERE AD_Element_ID = 584856 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET Name = 'Line Count', PrintName = 'Line Count',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:09', UpdatedBy = 100
WHERE AD_Element_ID = 584856 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584856, NULL);

-- ===========================================================================================
-- AD_Element 584857 — C_TaxDeclaration_Legacy
-- ===========================================================================================
UPDATE AD_Element_Trl
SET Name = 'Steuererklärung (alt)', PrintName = 'Steuererklärung (alt)',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:10', UpdatedBy = 100
WHERE AD_Element_ID = 584857 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET Name = 'Steuererklärung (alt)', PrintName = 'Steuererklärung (alt)',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:11', UpdatedBy = 100
WHERE AD_Element_ID = 584857 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET Name = 'Tax Declaration (legacy)', PrintName = 'Tax Declaration (legacy)',
    IsTranslated = 'Y', Updated = TIMESTAMP '2026-05-22 13:00:12', UpdatedBy = 100
WHERE AD_Element_ID = 584857 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584857, NULL);
