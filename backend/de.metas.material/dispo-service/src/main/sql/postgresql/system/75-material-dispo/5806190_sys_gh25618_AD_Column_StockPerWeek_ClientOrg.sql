-- gh#25618 — Bestand pro Woche / Stock per week
-- Register the standard AD_Client_ID / AD_Org_ID columns of view MD_Stock_PerWeek_V
-- (AD_Table 542612) in the application dictionary. The view (migration 5806110) already
-- emits both columns physically; these AD_Column rows let the security framework filter the
-- read-only window by client/org.
--
-- Reuses the STANDARD AD_Elements (no new elements for these standard columns):
--   AD_Client_ID -> AD_Element 102 (Mandant / Tenant), AD_Reference 19 (TableDir)
--   AD_Org_ID    -> AD_Element 113 (Sektion / Org),    AD_Reference 30 (Search)
-- These are the dominant reference choices across existing view-backed AD_Tables.
--
-- IDs allocated from idserver.metas.de on 2026-06-04:
--   AD_MigrationScript sequence: 5806190 (filename prefix)
--   AD_Column_ID 592713  (AD_Client_ID)
--   AD_Column_ID 592714  (AD_Org_ID)

-- ============================================================
-- AD_Client_ID — standard, mandatory, non-updateable view column
-- ============================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592713 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-04 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-04 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 102, 19,
     'AD_Client_ID', 'Mandant', 'de.metas.material.dispo',
     10, 'N', 'N', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

-- ============================================================
-- AD_Org_ID — standard, mandatory, non-updateable view column
-- ============================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, AD_Reference_ID,
     ColumnName, Name, EntityType,
     FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
     IsEncrypted, IsIdentifier, IsSelectionColumn, IsTranslated, Version,
     PersonalDataCategory)
VALUES
    (592714 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-04 13:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-04 13:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
     542612, 113, 30,
     'AD_Org_ID', 'Sektion', 'de.metas.material.dispo',
     10, 'N', 'N', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N', 0,
     'NP')
;

-- Seed AD_Column_Trl rows for every active system language, then propagate the standard
-- element translations (Mandant/Tenant, Sektion/Org) from AD_Element_Trl into them.
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-06-04 13:00:02','YYYY-MM-DD HH24:MI:SS'), t.CreatedBy,
       TO_TIMESTAMP('2026-06-04 13:00:02','YYYY-MM-DD HH24:MI:SS'), t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID IN (592713, 592714)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(102);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(113);
