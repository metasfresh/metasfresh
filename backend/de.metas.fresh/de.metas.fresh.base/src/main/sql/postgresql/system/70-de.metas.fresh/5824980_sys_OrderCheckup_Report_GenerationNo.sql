-- Order Checkup: generation discriminator on the Bestellkontrolle report table.
-- Created is stamped per record from wall-clock, so records of one print generation
-- differ by milliseconds and max(Created) is unreliable; the plant report is also
-- created after the warehouse reports, so a naive max would restore only the plant
-- report. This numeric generation number makes "the most recent generation" a
-- deterministic set. Nullable and unbackfilled on purpose: NULL means the row
-- predates this column, so a later reprint must fall back to a rebuild.
--
-- IDs allocated from idserver.metas.de on 2026-09-17:
--   AD_Element 585475 (OrderCheckupGeneration)
--   AD_Column  593633 (C_Order_MFGWarehouse_Report.OrderCheckupGeneration)

-- 1) AD_Element (base language = German)
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    EntityType, ColumnName, Name, PrintName, Description, Help
) VALUES (
    585475 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-17 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-17 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'de.metas.fresh', 'OrderCheckupGeneration', 'Bestellkontrolle Generation', 'Bestellkontrolle Generation',
    'Interne Kennzahl der Druckgeneration der Bestellkontrolle.',
    'Technische, fortlaufende Nummer, die alle Bestellkontroll-Berichte einer gemeinsamen Druckgeneration kennzeichnet. Wird nicht auf der Oberfläche angezeigt.'
);

-- 2) AD_Element_Trl skeleton rows for every active system language
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585475
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- 3) English override (en_US)
UPDATE AD_Element_Trl
   SET Name = 'Order Checkup Generation',
       PrintName = 'Order Checkup Generation',
       Description = 'Internal discriminator for the order checkup print generation.',
       Help = 'Technical, monotonically increasing number that groups all order checkup reports produced by the same print generation. Not shown in the UI.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-17 11:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language = 'en_US' AND AD_Element_ID = 585475;

-- 4) Mark de_DE / de_CH as actively translated (text already matches the German base)
UPDATE AD_Element_Trl
   SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-17 11:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language = 'de_DE' AND AD_Element_ID = 585475;

UPDATE AD_Element_Trl
   SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-17 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
 WHERE AD_Language = 'de_CH' AND AD_Element_ID = 585475;

-- 5) AD_Column on C_Order_MFGWarehouse_Report (AD_Table_ID = 540683)
-- Nullable, no default: existing rows (and rows created before a later task starts
-- stamping this column) MUST remain valid with NULL here.
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, Name, Description, Help,
    AD_Reference_ID, FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
    IsIdentifier, SeqNo, IsTranslated, IsEncrypted, EntityType, Version, PersonalDataCategory,
    IsSelectionColumn
) VALUES (
    593633 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-17 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-17 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    540683, 585475, 'OrderCheckupGeneration', 'Bestellkontrolle Generation',
    'Interne Kennzahl der Druckgeneration der Bestellkontrolle.',
    'Technische, fortlaufende Nummer, die alle Bestellkontroll-Berichte einer gemeinsamen Druckgeneration kennzeichnet. Wird nicht auf der Oberfläche angezeigt.',
    11, 10, 'N', 'N', 'N', 'Y', 'N',
    'N', 0, 'N', 'N', 'de.metas.fresh', 0, 'NP',
    'N'
);

-- 6) AD_Column_Trl skeleton rows for every active system language
-- (AD_Column_Trl has no Help column — only Name/Description are translatable here)
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593633
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 7) Propagate element translations onto the new column's _Trl rows
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585475);

-- 8) Physical column DDL — brand-new column, so ALTER TABLE ADD COLUMN (t_alter_column only
-- works on pre-existing columns). Nullable, no default, no backfill by design: existing rows
-- must keep NULL here so a later task can treat NULL as "predates this column, rebuild".
ALTER TABLE C_Order_MFGWarehouse_Report ADD COLUMN IF NOT EXISTS OrderCheckupGeneration NUMERIC(10);
