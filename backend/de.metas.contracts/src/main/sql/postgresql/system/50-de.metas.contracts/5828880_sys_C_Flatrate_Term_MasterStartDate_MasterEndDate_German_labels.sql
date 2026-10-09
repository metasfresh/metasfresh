-- German labels + description for the contract fields MasterStartDate / MasterEndDate.
-- de_DE (the base language) still showed the English "Master Start Date" / "Master End Date"; de_CH showed
-- "Contract Start Date" / "Contract End Date". The fields hold the start / end of the whole contract relationship
-- across all extensions.
--
-- AD_Element 543413 MasterStartDate: de_DE, de_CH, base -> "Vertragspartner seit"
-- AD_Element 543414 MasterEndDate:   de_DE, de_CH, base -> "Vertragspartner bis"
-- en_US keeps its names; all three languages get a description. fr_CH unchanged.
--
-- Usages (both elements, same set), all meant by the new wording:
--   AD_Column C_Flatrate_Term, I_Flatrate_Term, C_BPartner_Export
--   AD_Field in the windows Verträge (540359), Geschäftspartner Dist-Orgs (540366), Import Abos (540358),
--   Laufender Vertrag (540112) and Partner Export (541174). Fields with their own AD_Name_ID keep their caption
--   (Laufender Vertrag: both fields; Partner Export: MasterEndDate).

-- 543413 MasterStartDate
UPDATE AD_Element_Trl
SET Name         = 'Vertragspartner seit',
    PrintName    = 'Vertragspartner seit',
    Description  = 'Beginn der gesamten Vertragsbeziehung über alle Verlängerungen',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 13:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 543413
  AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Element_Trl
SET Description  = 'Start of the whole contract relationship across all extensions',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 13:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 543413
  AND AD_Language = 'en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543413, 'de_DE')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543413, 'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543413, 'en_US')
;

-- 543414 MasterEndDate
UPDATE AD_Element_Trl
SET Name         = 'Vertragspartner bis',
    PrintName    = 'Vertragspartner bis',
    Description  = 'Ende der gesamten Vertragsbeziehung über alle Verlängerungen',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 13:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 543414
  AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Element_Trl
SET Description  = 'End of the whole contract relationship across all extensions',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 13:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 543414
  AND AD_Language = 'en_US'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543414, 'de_DE')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543414, 'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543414, 'en_US')
;
