-- DHL_Shipper_Config.AccountNumber_International: separate billing number for DHL Paket International (product V53WPAK).
-- The existing AccountNumber stays the national billing number; existing configs get the same value copied as the international one.
-- IDs allocated from the central ID server on 2026-11-06:
--   AD_Element 585597, AD_Column 593790, AD_Field 788149, AD_UI_Element 656466

-- Element (German base language, English via _Trl)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, EntityType, Name, PrintName, Description)
VALUES (585597 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-11-06 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-11-06 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'AccountNumber_International', 'de.metas.shipper.gateway.dhl', 'Kontonummer international', 'Kontonummer international',
        'Internationale Abrechnungsnummer (DHL Paket International, Produkt V53WPAK)');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, PO_Name, PO_PrintName, PO_Description, PO_Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, t.PO_Name, t.PO_PrintName, t.PO_Description, t.PO_Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585597
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'Account number international', PrintName = 'Account number international',
       Description = 'International billing number (DHL Paket International, product V53WPAK)', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-11-06 14:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585597 AND AD_Language = 'en_US';

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-06 14:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585597 AND AD_Language IN ('de_DE', 'de_CH');

-- Column (display type String = 10, length 255 like AccountNumber)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, Version, EntityType, ColumnName, AD_Table_ID, AD_Reference_ID,
                       FieldLength, IsKey, IsParent, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, IsTranslated, IsEncrypted, IsSelectionColumn, AD_Element_ID, IsSyncDatabase, IsAlwaysUpdateable, PersonalDataCategory)
VALUES (593790 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-11-06 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-11-06 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Kontonummer international', 'Internationale Abrechnungsnummer (DHL Paket International, Produkt V53WPAK)', 0, 'de.metas.shipper.gateway.dhl', 'AccountNumber_International',
        (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'DHL_Shipper_Config'), 10,
        255, 'N', 'N', 'N', 'Y', 'N', 0, 'N', 'N', 'N', 585597, 'N', 'N', 'NP');

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593790
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Physical column, nullable
SELECT db_alter_table('DHL_Shipper_Config', 'ALTER TABLE public.DHL_Shipper_Config ADD COLUMN AccountNumber_International VARCHAR(255)');

-- Existing configs: the international billing number starts out equal to the current AccountNumber
SELECT backup_table('dhl_shipper_config', '_gh32526_AccNo_Intl');
UPDATE DHL_Shipper_Config SET AccountNumber_International = AccountNumber, Updated = TO_TIMESTAMP('2026-11-06 14:01:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 99
WHERE AccountNumber_International IS NULL;

-- Field in window "Lieferweg" (142), tab "DHL Konfiguration" (542055)
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, AD_Tab_ID, AD_Column_ID, IsDisplayed, DisplayLength, IsReadOnly, SeqNo, SortNo,
                      IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType, SeqNoGrid, IsDisplayedGrid, SpanX, SpanY, IsAlwaysUpdateable)
VALUES (788149 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-11-06 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-11-06 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Kontonummer international', 'Internationale Abrechnungsnummer (DHL Paket International, Produkt V53WPAK)', 542055, 593790, 'Y', 255, 'N', 45, 0,
        'N', 'N', 'N', 'N', 'de.metas.shipper.gateway.dhl', 45, 'N', 1, 1, 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 788149
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585597);

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 788149;
SELECT AD_Element_Link_Create_Missing_Field(788149);

-- UI element directly after "Kontonummer" (SeqNo 40) in the same element group
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, AD_UI_ElementGroup_ID, SeqNo, AD_Field_ID, AD_Tab_ID, AD_UI_ElementType,
                           IsDisplayed, IsDisplayedGrid, SeqNoGrid, IsAdvancedField, IsAllowFiltering, IsMultiLine, MultiLine_LinesCount)
VALUES (656466 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-11-06 14:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-11-06 14:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Kontonummer international', 'Internationale Abrechnungsnummer (DHL Paket International, Produkt V53WPAK)', 543079, 45, 788149, 542055, 'F',
        'Y', 'N', 0, 'N', 'N', 'N', 0);
