-- C_BPartner_EDI_Setting — re-introduce a uniqueness guard.
--
-- A partner's EDI config rows are resolved per document by lowest SeqNo (then
-- lowest id) among the rows matching the document's location or carrying no
-- location. To keep that resolution unambiguous a partner must not hold two
-- rows that collide on (SeqNo, C_BPartner_ID, location) — including two
-- no-location "partner default" rows sharing a SeqNo.
--
-- Declared via AD_Index_Table so a violation surfaces a translatable message in
-- the WebUI instead of a raw DB error. Partial (WHERE IsActive='Y') so a
-- soft-deleted row never blocks recreating an equivalent active one, matching
-- the resolution query which only reads active rows.
--
-- IDs from idserver.metas.de:
--   AD_Index_Table  540872
--   AD_Index_Column 541541 (SeqNo), 541542 (C_BPartner_ID), 541543 (location)
-- Reused: AD_Table 542610; AD_Column 592791 (SeqNo), 592678 (C_BPartner_ID),
--         592679 (C_BPartner_Location_ID)

-- ============================================================
-- 1. AD_Index_Table — dictionary declaration + error message
-- ============================================================
INSERT INTO AD_Index_Table
    (AD_Client_ID, AD_Index_Table_ID, AD_Org_ID, AD_Table_ID, Created, CreatedBy,
     EntityType, IsActive, IsUnique, Name, Processing, Updated, UpdatedBy,
     WhereClause, ErrorMsg)
VALUES
    (0, 540872 /*From ID Server*/, 0, 542610,
     TO_TIMESTAMP('2026-08-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.esb.edi', 'Y', 'Y', 'UC_C_BPartner_EDI_Setting_SeqNo', 'N',
     TO_TIMESTAMP('2026-08-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'IsActive=''Y''',
     'Es existiert bereits eine EDI-Konfiguration mit dieser Reihenfolge für diesen Geschäftspartner bzw. Standort.')
;

-- Seed _Trl rows for all active system languages (copies the German base text)
INSERT INTO AD_Index_Table_Trl
    (AD_Language, AD_Index_Table_ID, ErrorMsg, IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Index_Table t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Index_Table_ID = 540872
  AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Index_Table_ID = t.AD_Index_Table_ID)
;

-- English override
UPDATE AD_Index_Table_Trl
SET ErrorMsg = 'An EDI configuration with this sequence number already exists for this business partner / location.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-08-28 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Index_Table_ID = 540872 AND AD_Language = 'en_US'
;

-- ============================================================
-- 2. AD_Index_Column — the three key columns, in index order
-- ============================================================
INSERT INTO AD_Index_Column
    (AD_Client_ID, AD_Column_ID, AD_Index_Column_ID, AD_Index_Table_ID, AD_Org_ID,
     Created, CreatedBy, EntityType, IsActive, SeqNo, Updated, UpdatedBy)
VALUES
    (0, 592791, 541541 /*From ID Server*/, 540872, 0,
     TO_TIMESTAMP('2026-08-28 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.esb.edi', 'Y', 10,
     TO_TIMESTAMP('2026-08-28 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Index_Column
    (AD_Client_ID, AD_Column_ID, AD_Index_Column_ID, AD_Index_Table_ID, AD_Org_ID,
     Created, CreatedBy, EntityType, IsActive, SeqNo, Updated, UpdatedBy)
VALUES
    (0, 592678, 541542 /*From ID Server*/, 540872, 0,
     TO_TIMESTAMP('2026-08-28 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.esb.edi', 'Y', 20,
     TO_TIMESTAMP('2026-08-28 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- location column keyed via COALESCE(...,0) so a NULL (partner-default) row
-- participates in the uniqueness bucket
INSERT INTO AD_Index_Column
    (AD_Client_ID, AD_Column_ID, AD_Index_Column_ID, AD_Index_Table_ID, AD_Org_ID,
     ColumnSQL, Created, CreatedBy, EntityType, IsActive, SeqNo, Updated, UpdatedBy)
VALUES
    (0, 592679, 541543 /*From ID Server*/, 540872, 0,
     'COALESCE(C_BPartner_EDI_Setting.C_BPartner_Location_ID, 0)',
     TO_TIMESTAMP('2026-08-28 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.esb.edi', 'Y', 30,
     TO_TIMESTAMP('2026-08-28 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ============================================================
-- 3. Physical partial unique index
-- ============================================================
DROP INDEX IF EXISTS UC_C_BPartner_EDI_Setting_SeqNo
;
CREATE UNIQUE INDEX UC_C_BPartner_EDI_Setting_SeqNo
    ON C_BPartner_EDI_Setting (SeqNo, C_BPartner_ID, COALESCE(C_BPartner_Location_ID, 0))
    WHERE IsActive = 'Y'
;
