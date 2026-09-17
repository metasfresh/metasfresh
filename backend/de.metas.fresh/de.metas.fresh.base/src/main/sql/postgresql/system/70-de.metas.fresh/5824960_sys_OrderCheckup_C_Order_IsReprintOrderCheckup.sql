-- Order Checkup: new flag on C_Order controlling whether reactivating the order
-- forces the "Bestellkontrolle" (order checkup) print to be re-triggered.
-- Default 'Y' preserves today's behaviour (checkup is reprinted on reactivate).
--
-- IDs allocated from idserver.metas.de on 2026-09-17:
--   AD_Element 585474 (IsReprintOrderCheckup)
--   AD_Column  593632 (C_Order.IsReprintOrderCheckup)

-- 1) AD_Element (base language = German)
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    EntityType, ColumnName, Name, PrintName, Description, Help
) VALUES (
    585474 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-17 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-17 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'de.metas.fresh', 'IsReprintOrderCheckup', 'Bestellkontrolle neu drucken', 'Bestellkontrolle neu drucken',
    'Legt fest, ob die Bestellkontrolle beim Reaktivieren dieses Auftrags erneut gedruckt wird.',
    'Wenn aktiviert, wird die Bestellkontrolle beim Reaktivieren des Auftrags automatisch erneut gedruckt. Wird das Kontrollkästchen deaktiviert, unterbleibt der erneute Druck.'
);

-- 2) AD_Element_Trl skeleton rows for every active system language (de_DE, de_CH, en_US, fr_CH)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585474
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- 3) English override (en_US)
UPDATE AD_Element_Trl
   SET Name = 'Reprint Order Checkup',
       PrintName = 'Reprint Order Checkup',
       Description = 'Controls whether the order checkup is reprinted when this order is reactivated.',
       Help = 'When enabled, the order checkup is automatically reprinted when the order is reactivated. Disable this checkbox to skip the reprint.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-17 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language = 'en_US' AND AD_Element_ID = 585474;

-- 4) Mark de_DE / de_CH as actively translated (text already matches the German base)
UPDATE AD_Element_Trl
   SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-17 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language = 'de_DE' AND AD_Element_ID = 585474;

UPDATE AD_Element_Trl
   SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-17 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language = 'de_CH' AND AD_Element_ID = 585474;

-- 5) AD_Column on C_Order (AD_Table_ID = 259)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, Name, Description, Help,
    AD_Reference_ID, FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
    IsIdentifier, SeqNo, IsTranslated, IsEncrypted, EntityType, Version, PersonalDataCategory,
    DefaultValue, IsSelectionColumn
) VALUES (
    593632 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-17 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-17 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    259, 585474, 'IsReprintOrderCheckup', 'Bestellkontrolle neu drucken',
    'Legt fest, ob die Bestellkontrolle beim Reaktivieren dieses Auftrags erneut gedruckt wird.',
    'Wenn aktiviert, wird die Bestellkontrolle beim Reaktivieren des Auftrags automatisch erneut gedruckt. Wird das Kontrollkästchen deaktiviert, unterbleibt der erneute Druck.',
    20, 1, 'N', 'N', 'Y', 'Y', 'N',
    'N', 0, 'N', 'N', 'de.metas.fresh', 0, 'NP',
    'Y', 'N'
);

-- 6) AD_Column_Trl skeleton rows for every active system language
-- (AD_Column_Trl has no Help column — only Name/Description are translatable here)
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593632
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 7) Propagate element translations onto the new column's _Trl rows
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585474);

-- 8) Physical column DDL — new column, so ALTER TABLE ADD COLUMN (t_alter_column only works on
-- pre-existing columns), with the same Y/N check constraint style as C_Order.IsActive.
-- Backup first: C_Order is a business table and this script UPDATEs its rows to backfill the
-- new NOT NULL column before the constraint is applied.
SELECT backup_table('c_order', '_gh30709_IsReprintOrderCheckup');

ALTER TABLE C_Order ADD COLUMN IF NOT EXISTS IsReprintOrderCheckup CHAR(1) DEFAULT 'Y';
UPDATE C_Order SET IsReprintOrderCheckup = 'Y' WHERE IsReprintOrderCheckup IS NULL;
ALTER TABLE C_Order ALTER COLUMN IsReprintOrderCheckup SET NOT NULL;
ALTER TABLE C_Order ADD CONSTRAINT c_order_isreprintordercheckup_check CHECK (IsReprintOrderCheckup IN ('Y', 'N'));
