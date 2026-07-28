-- Per-line preparation-date override on the sales order line.
-- C_OrderLine.PreparationDate: NULL => the line's preparation date is derived from its own delivery date
-- (existing behaviour); SET => used verbatim as that line's picking date. For tourless customers the derived
-- date equals the delivery date (no picking lead time), so the operator sets this to pick before the delivery
-- date. Pure override: never auto-filled or broadcast (not by header-date edits, not by olcand/EDI).
-- Reuses AD_Element 542340 (PreparationDate).
--
-- IDs allocated from idserver.metas.de on 2026-06-24:
--   AD_MigrationScript  5809530 (this script)
--   AD_Column           592882  (C_OrderLine.PreparationDate)

-- =============================================================================
-- 1. AD_Column (C_OrderLine = AD_Table 260; reuses AD_Element 542340)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592882 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-24 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-24 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        0,
        260 /*C_OrderLine*/,
        542340 /*PreparationDate*/,
        16 /*Date+Time*/,
        'PreparationDate',
        'Bereitstellungsdatum',
        10, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows (one per active system language; propagation below fills the actual text)
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592882
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 2. Physical DDL — new nullable timestamptz column (mirrors C_Order.PreparationDate)
-- =============================================================================
ALTER TABLE C_OrderLine ADD COLUMN IF NOT EXISTS PreparationDate timestamp with time zone;

-- =============================================================================
-- 3. Propagate translations from AD_Element 542340 to AD_Column_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542340 /*PreparationDate*/);
