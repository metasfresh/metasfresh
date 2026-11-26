-- VAT-ID online check: track when a check was last ATTEMPTED, separately from when it last
-- SUCCEEDED (VATaxIDCheckedAt). VATaxIDCheckedAt is written only on the success path (see
-- VATaxIDCheckService#check, before updateParentStatus) -- a target whose check always throws (a
-- malformed value that bypassed the save-time gate) or whose triggered order-tax refresh always
-- rolls back the whole check-and-refresh unit never advances it, so it sorts first of every future
-- nightly run forever and can occupy the whole MaxChecksPerRun budget every night without ever
-- making progress -- starving every other, checkable record behind it.
--
-- VATaxIDLastAttemptedAt is stamped unconditionally, before the risky check-and-refresh unit even
-- starts, in its OWN already-committed transaction (see VATaxIDCheckRunService#checkOneInOwnTrx) --
-- so it survives that unit's rollback and the nightly ordering can use it to push a repeatedly-failing
-- target away from the front of the queue after each attempt, without excluding it from ever being
-- retried.
--
-- Deliberately no AD_Field / AD_UI_Element / menu placement: this is pure scheduling bookkeeping with
-- no business meaning to an end user (unlike VATaxIDCheckedAt, which reports when a status was last
-- actually determined) -- so it stays off every window, on both C_BPartner and C_BPartner_Location.

-- IDs allocated from idserver.metas.de:
--   AD_Element 585300 (1: VATaxIDLastAttemptedAt)
--   AD_Column  593309..593310 (2: 1 column x 2 tables)

-- 1. Physical columns
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS VATaxIDLastAttemptedAt TIMESTAMP;
ALTER TABLE C_BPartner_Location ADD COLUMN IF NOT EXISTS VATaxIDLastAttemptedAt TIMESTAMP;

-- 2. AD_Element
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         ColumnName, Name, PrintName, Description, EntityType)
VALUES (585300 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-14 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-14 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'VATaxIDLastAttemptedAt', 'USt-IdNr. zuletzt geprüft (Versuch)', 'USt-IdNr. zuletzt geprüft (Versuch)',
        'Zeitpunkt des letzten USt-IdNr.-Prüfversuchs, unabhängig vom Ergebnis (interne Ablaufsteuerung).', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585300
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Name = 'VAT-ID Last Attempted On', PrintName = 'VAT-ID Last Attempted On',
    Description = 'Point in time of the most recent VAT-ID check attempt, regardless of outcome (internal scheduling only).',
    Updated = TO_TIMESTAMP('2026-08-14 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585300;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-14 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585300;

-- 3. AD_Column -- C_BPartner (291) and C_BPartner_Location (293). Nullable, no default: never checked
-- (or never attempted since this column was introduced) is genuinely "we don't know", not "long ago".
-- No AD_Field/AD_UI_Element on purpose (see the header note) -- so no window/menu placement, and no
-- customer override window placement, is owed for this column.
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        DDL_NoForeignKey, DefaultValue, EntityType, PersonalDataCategory, Version)
VALUES
    (593309 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-14 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-14 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     291, 585300, 'VATaxIDLastAttemptedAt', 16, 29,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', NULL, 'D', 'NP', 0),
    (593310 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-14 09:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-14 09:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
     293, 585300, 'VATaxIDLastAttemptedAt', 16, 29,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', NULL, 'D', 'NP', 0);

-- 4. AD_Column_Trl skeleton rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.ColumnName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID IN (593309, 593310)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 5. Propagate the element translation to the two new columns
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585300);
