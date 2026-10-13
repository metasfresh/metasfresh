-- Follow-up to 5815110: fix two review findings on the Factoring config columns migration.
--   HIGH  — de_DE / de_CH AD_Element_Trl rows had IsTranslated='Y'; German is the base language,
--            so those rows must carry IsTranslated='N' (only en_US is a genuine translation).
--   CRIT  — the previous script never called update_TRL_Tables_On_AD_Element_TRL_Update, so
--            AD_Column_Trl rows for FactoringContractNo / FactoringClientAccountId were never
--            populated; grid headers and future AD_Field labels would fall back to English base.
--
-- IDs referenced (from earlier script 5815110):
--   AD_Element 585119 (FactoringContractNo)
--   AD_Element 585120 (FactoringClientAccountId)

-- HIGH — flip IsTranslated to 'N' on the German (base-language) Trl rows and bump Updated so
-- propagation's f_trl.updated <> e_trl.updated guard triggers on the follow-up sync.
UPDATE ad_element_trl
SET istranslated = 'N',
    updated = TO_TIMESTAMP('2026-07-21 10:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby = 100
WHERE ad_element_id = 585119
  AND ad_language IN ('de_DE', 'de_CH');

UPDATE ad_element_trl
SET istranslated = 'N',
    updated = TO_TIMESTAMP('2026-07-21 10:05:01', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby = 100
WHERE ad_element_id = 585120
  AND ad_language IN ('de_DE', 'de_CH');

-- Also bump the en_US rows so propagation fires for them too (they already have IsTranslated='Y').
UPDATE ad_element_trl
SET updated = TO_TIMESTAMP('2026-07-21 10:05:02', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby = 100
WHERE ad_element_id = 585119
  AND ad_language = 'en_US';

UPDATE ad_element_trl
SET updated = TO_TIMESTAMP('2026-07-21 10:05:03', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby = 100
WHERE ad_element_id = 585120
  AND ad_language = 'en_US';

-- Populate AD_Column.Name / Description from AD_Element BEFORE add_missing_translations().
-- add_missing_translations() seeds AD_Column_Trl by SELECTing AD_Column.Name; if that Name is
-- still null (i.e. before after_migration's propagation batch has run), the seed INSERT hits
-- the AD_Column_Trl.Name NOT NULL constraint. Locally this is masked because the previous
-- script (5815110) was applied in a batch that ended with after_migration(), leaving
-- AD_Column.Name populated by the time this script runs. On CI both scripts apply in one
-- batch so after_migration() has not yet fired here; the explicit UPDATE is what covers
-- that gap. Timestamp 10:04 keeps this LATER than the AD_Column INSERTs in 5815110 (10:00:02).
UPDATE ad_column c
SET name = e.name,
    description = e.description,
    updated = TO_TIMESTAMP('2026-07-21 10:04:00', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby = 100
FROM ad_element e
WHERE c.ad_element_id = e.ad_element_id
  AND e.ad_element_id IN (585119, 585120);

-- Seed the missing AD_Column_Trl (and any other _Trl) rows; the propagation function below
-- only UPDATEs existing rows.
SELECT add_missing_translations();

-- Now propagate AD_Element_Trl to AD_Column_Trl (and to AD_Field_Trl once Task 3 adds the fields).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585119);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585120);
