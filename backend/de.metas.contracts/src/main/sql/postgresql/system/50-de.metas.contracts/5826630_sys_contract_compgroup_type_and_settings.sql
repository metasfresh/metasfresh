-- Contract compensation groups: new contract type + settings tables + conditions column.
--
-- IDs allocated from idserver.metas.de on 2026-09-28:
--   AD_Ref_List   544372  (Type_Conditions value 'CompensationGroup')
--   AD_Element    585492  (C_CompensationGroup_ContractSettings_ID -- reused on both the
--                 settings table's own PK and the new C_Flatrate_Conditions column)
--   AD_Element    585493  (C_CompensationGroup_ContractSettings_DocType_ID)
--   AD_Table      542650  (C_CompensationGroup_ContractSettings)
--   AD_Table      542651  (C_CompensationGroup_ContractSettings_DocType)
--   AD_Column     593650, 593651, 593652, 593653, 593654, 593655, 593656, 593657, 593658, 593659 (C_CompensationGroup_ContractSettings columns)
--   AD_Column     593660, 593661, 593662, 593663, 593664, 593665, 593666, 593667, 593668, 593669 (C_CompensationGroup_ContractSettings_DocType columns)
--   AD_Column     593670  (C_Flatrate_Conditions.C_CompensationGroup_ContractSettings_ID)
--
-- Columns whose MandatoryLogic/DisplayLogic already excludes the product-less types
-- (LicenseFee / MarginCommission) and now also exclude CompensationGroup (same reasoning --
-- a compensation-group contract carries no product/UOM/price of its own):
--   AD_Column 545773 (InvoiceRule), 559719 (OnFlatrateTermExtend) -- MandatoryLogic
--   AD_Field  547848 (M_PricingSystem_ID), 548120 (InvoiceRule), 551009 (IsFreeOfCharge),
--             551476 (IsManualPrice), 563597 (OnFlatrateTermExtend) -- DisplayLogic
-- ============================================================================
-- 1) AD_Ref_List: new Type_Conditions value 'CompensationGroup'
-- ============================================================================
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, EntityType, Value, ValueName, Name)
VALUES (544372 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        540271, 'de.metas.contracts', 'CompensationGroup', 'CompensationGroup', 'Kompensationsgruppen-Vertrag')
;
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 544372
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl
SET Name = 'Compensation group contract', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544372 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (already copied by the skeleton insert above); de_DE is set to IsTranslated='Y' by 5827210
UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544372 AND AD_Language = 'de_CH'
;
-- ============================================================================
-- 2) AD_Element for C_CompensationGroup_ContractSettings_ID (shared by the settings
--    table's own PK and the new C_Flatrate_Conditions column)
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585492 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_ID', 'de.metas.contracts', 'Einstellungen für Kompensationsgruppen-Verträge', 'Einstellungen für Kompensationsgruppen-Verträge',
        'Verweis auf die Einstellungen eines Kompensationsgruppen-Vertrags (Schema und Belegarten).')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585492, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585492
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Compensation group contract settings', PrintName = 'Compensation group contract settings',
    Description = 'Reference to a compensation group contract settings record (schema and document types).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585492 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (already copied by the skeleton insert above); de_DE is set to IsTranslated='Y' by 5827210
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585492 AND AD_Language = 'de_CH'
;
-- ============================================================================
-- 3) AD_Element for C_CompensationGroup_ContractSettings_DocType_ID (child table PK)
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585493 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_DocType_ID', 'de.metas.contracts', 'Kompensationsgruppen-Vertragseinstellung Belegart', 'Kompensationsgruppen-Vertragseinstellung Belegart',
        'Verkaufs- oder Einkaufsbelegart, für die ein Kompensationsgruppen-Vertrag gilt.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585493, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585493
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Compensation group contract settings document type', PrintName = 'Compensation group contract settings document type',
    Description = 'Sales or purchase document type this compensation group contract applies to.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585493 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (already copied by the skeleton insert above); de_DE is set to IsTranslated='Y' by 5827210
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585493 AND AD_Language = 'de_CH'
;
-- ============================================================================
-- 4) New table C_CompensationGroup_ContractSettings
-- ============================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      TableName, Name, AccessLevel, EntityType, IsView, ImportTable, IsChangeLog, ReplicationType,
                      IsSecurityEnabled, IsDeleteable, IsHighVolume, IsAutocomplete, IsDLM, CopyColumnsFromTable,
                      LoadSeq, ACTriggerLength)
VALUES (542650 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings', 'Kompensationsgruppen-Vertragseinstellungen', 3, 'de.metas.contracts', 'N', 'N', 'Y', 'L',
        'N', 'Y', 'N', 'N', 'N', 'N',
        0, 0)
;
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Table_ID = 542650
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID)
;
UPDATE AD_Table_Trl
SET Name = 'Compensation Group Contract Settings', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542650 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (already copied by the skeleton insert above); de_DE is set to IsTranslated='Y' by 5827210
UPDATE AD_Table_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542650 AND AD_Language = 'de_CH'
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593650 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Client_ID', 542650, 102, 19,
        10, 'Mandant', 'Mandant für diese Installation.', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593650
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593651 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Org_ID', 542650, 113, 30,
        10, 'Sektion', 'Organisatorische Einheit des Mandanten', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593651
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593652 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Created', 542650, 245, 16,
        29, 'Erstellt', 'Datum, an dem dieser Eintrag erstellt wurde', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593652
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593653 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'CreatedBy', 542650, 246, 18,
        10, 'Erstellt durch', 'Nutzer, der diesen Eintrag erstellt hat', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593653
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593654 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'IsActive', 542650, 348, 20,
        1, 'Aktiv', 'Der Eintrag ist im System aktiv', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593654
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593655 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Updated', 542650, 607, 16,
        29, 'Aktualisiert', 'Datum, an dem dieser Eintrag aktualisiert wurde', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593655
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593656 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'UpdatedBy', 542650, 608, 18,
        10, 'Aktualisiert durch', 'Nutzer, der diesen Eintrag aktualisiert hat', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593656
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593657 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_ID', 542650, 585492, 13,
        10, 'Einstellungen für Kompensationsgruppen-Verträge', NULL, NULL,
        'Y', 'N', 'N', NULL,
        'Y', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593657
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593658 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Name', 542650, 469, 10,
        255, 'Name', 'Alphanumerische Kennung der Entität', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593658
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593659 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_Schema_ID', 542650, 543889, 30,
        10, 'Kompensationsgruppe Schema', 'Rabattschema, das der Kompensationsgruppen-Vertrag auf die Auftragszeilen anwendet.', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593659
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
-- Physical table + PK + FK
/* DDL */ CREATE TABLE public.C_CompensationGroup_ContractSettings (
    AD_Client_ID NUMERIC(10) NOT NULL,
    AD_Org_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_ID NUMERIC(10) NOT NULL,
    Created TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy NUMERIC(10) NOT NULL,
    IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL,
    Name VARCHAR(255) NOT NULL,
    C_CompensationGroup_Schema_ID NUMERIC(10) NOT NULL,
    Updated TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy NUMERIC(10) NOT NULL,
    CONSTRAINT C_CompensationGroup_ContractSettings_Key PRIMARY KEY (C_CompensationGroup_ContractSettings_ID),
    CONSTRAINT CCompensationGroupSchema_CCompensationGroupContractSettings FOREIGN KEY (C_CompensationGroup_Schema_ID) REFERENCES public.C_CompensationGroup_Schema DEFERRABLE INITIALLY DEFERRED
)
;
-- ============================================================================
-- 5) New table C_CompensationGroup_ContractSettings_DocType
-- ============================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      TableName, Name, AccessLevel, EntityType, IsView, ImportTable, IsChangeLog, ReplicationType,
                      IsSecurityEnabled, IsDeleteable, IsHighVolume, IsAutocomplete, IsDLM, CopyColumnsFromTable,
                      LoadSeq, ACTriggerLength)
VALUES (542651 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_DocType', 'Kompensationsgruppen-Vertragseinstellungen Belegarten', 3, 'de.metas.contracts', 'N', 'N', 'Y', 'L',
        'N', 'Y', 'N', 'N', 'N', 'N',
        0, 0)
;
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Table_ID = 542651
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID)
;
UPDATE AD_Table_Trl
SET Name = 'Compensation Group Contract Settings Document Types', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:38', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542651 AND AD_Language = 'en_US'
;
-- de_CH mirrors de_DE (already copied by the skeleton insert above); de_DE is set to IsTranslated='Y' by 5827210
UPDATE AD_Table_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-28 10:00:39', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542651 AND AD_Language = 'de_CH'
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593660 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Client_ID', 542651, 102, 19,
        10, 'Mandant', 'Mandant für diese Installation.', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593660
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593661 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Org_ID', 542651, 113, 30,
        10, 'Sektion', 'Organisatorische Einheit des Mandanten', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593661
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593662 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Created', 542651, 245, 16,
        29, 'Erstellt', 'Datum, an dem dieser Eintrag erstellt wurde', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593662
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593663 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'CreatedBy', 542651, 246, 18,
        10, 'Erstellt durch', 'Nutzer, der diesen Eintrag erstellt hat', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593663
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593664 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:48', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:49', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'IsActive', 542651, 348, 20,
        1, 'Aktiv', 'Der Eintrag ist im System aktiv', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593664
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593665 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Updated', 542651, 607, 16,
        29, 'Aktualisiert', 'Datum, an dem dieser Eintrag aktualisiert wurde', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593665
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593666 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'UpdatedBy', 542651, 608, 18,
        10, 'Aktualisiert durch', 'Nutzer, der diesen Eintrag aktualisiert hat', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593666
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593667 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:54', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:55', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_DocType_ID', 542651, 585493, 13,
        10, 'Kompensationsgruppen-Vertragseinstellung Belegart', NULL, NULL,
        'Y', 'N', 'N', NULL,
        'Y', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593667
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593668 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:56', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:57', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_ID', 542651, 585492, 30,
        10, 'Einstellungen für Kompensationsgruppen-Verträge', 'Kompensationsgruppen-Vertragseinstellung, zu der diese Belegart gehört.', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'Y', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593668
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593669 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:00:58', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:00:59', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_DocType_ID', 542651, 196, 19,
        10, 'Belegart', 'Verkaufs- oder Einkaufsbelegart, für die der Kompensationsgruppen-Vertrag ausgelöst wird.', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593669
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
-- Physical table + PK + FKs + partial unique index (one doc type once per settings row)
/* DDL */ CREATE TABLE public.C_CompensationGroup_ContractSettings_DocType (
    AD_Client_ID NUMERIC(10) NOT NULL,
    AD_Org_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_DocType_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_ID NUMERIC(10) NOT NULL,
    C_DocType_ID NUMERIC(10) NOT NULL,
    Created TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy NUMERIC(10) NOT NULL,
    IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL,
    Updated TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy NUMERIC(10) NOT NULL,
    CONSTRAINT C_CompGroup_ContractSettings_DocType_Key PRIMARY KEY (C_CompensationGroup_ContractSettings_DocType_ID),
    CONSTRAINT CCompGroupContractSettings_CCompGroupContractSettingsDocType FOREIGN KEY (C_CompensationGroup_ContractSettings_ID) REFERENCES public.C_CompensationGroup_ContractSettings DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CDocType_CCompensationGroupContractSettingsDocType FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
)
;
CREATE UNIQUE INDEX c_compgroup_contractsettings_doctype_active_uq
    ON public.C_CompensationGroup_ContractSettings_DocType (C_CompensationGroup_ContractSettings_ID, C_DocType_ID)
    WHERE IsActive = 'Y'
;
-- ============================================================================
-- 6) C_Flatrate_Conditions.C_CompensationGroup_ContractSettings_ID (Table Direct, optional,
--    mandatory only for the new CompensationGroup type)
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593670 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-28 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-28 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_ID', 540311, 585492, 19,
        10, 'Einstellungen für Kompensationsgruppen-Verträge', 'Kompensationsgruppen-Vertragseinstellung, die diese Konditionen auslösen.', '@Type_Conditions/''''@=''CompensationGroup''',
        'N', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593670
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
-- Physical column + FK (existing table -> ALTER TABLE ADD COLUMN via db_alter_table)
SELECT public.db_alter_table('C_Flatrate_Conditions', 'ALTER TABLE public.C_Flatrate_Conditions ADD COLUMN C_CompensationGroup_ContractSettings_ID NUMERIC(10)')
;
ALTER TABLE C_Flatrate_Conditions
    ADD CONSTRAINT CCompGroupContractSettings_CFlatrateConditions
    FOREIGN KEY (C_CompensationGroup_ContractSettings_ID) REFERENCES public.C_CompensationGroup_ContractSettings DEFERRABLE INITIALLY DEFERRED
;
-- Propagate the shared element's translations to both AD_Column rows referencing it
SELECT update_Column_Translation_From_AD_Element(585492)
;
-- Propagate the DocType-child PK element's translation
SELECT update_Column_Translation_From_AD_Element(585493)
;
-- ============================================================================
-- 7) Add CompensationGroup to the existing product-less-type (LicenseFee / MarginCommission)
--    exclusions on C_Flatrate_Conditions -- a compensation-group contract carries no
--    product/UOM/price/invoice-rule of its own, same reasoning as LicenseFee/MarginCommission
-- ============================================================================
-- AD_Column 545773 (InvoiceRule).MandatoryLogic
UPDATE AD_Column
SET MandatoryLogic = '@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee'' & @Type_Conditions/''CompensationGroup''@!''CompensationGroup''', Updated = TO_TIMESTAMP('2026-09-28 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 545773
;
-- AD_Column 559719 (OnFlatrateTermExtend).MandatoryLogic
UPDATE AD_Column
SET MandatoryLogic = '@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CompensationGroup''@!''CompensationGroup''', Updated = TO_TIMESTAMP('2026-09-28 10:01:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 559719
;
-- AD_Field 547848 (M_PricingSystem_ID).DisplayLogic
UPDATE AD_Field
SET DisplayLogic = '@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''LicenseFee''@!''LicenseFee'' & @Type_Conditions/''CompensationGroup''@!''CompensationGroup''', Updated = TO_TIMESTAMP('2026-09-28 10:01:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 547848
;
-- AD_Field 548120 (InvoiceRule).DisplayLogic
UPDATE AD_Field
SET DisplayLogic = '@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder'' & @Type_Conditions/''CompensationGroup''@!''CompensationGroup''', Updated = TO_TIMESTAMP('2026-09-28 10:01:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 548120
;
-- AD_Field 551009 (IsFreeOfCharge).DisplayLogic
UPDATE AD_Field
SET DisplayLogic = '@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder'' & @Type_Conditions/''CompensationGroup''@!''CompensationGroup''', Updated = TO_TIMESTAMP('2026-09-28 10:01:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 551009
;
-- AD_Field 551476 (IsManualPrice).DisplayLogic
UPDATE AD_Field
SET DisplayLogic = '@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder'' & @Type_Conditions/''CompensationGroup''@!''CompensationGroup''', Updated = TO_TIMESTAMP('2026-09-28 10:01:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 551476
;
-- AD_Field 563597 (OnFlatrateTermExtend).DisplayLogic
UPDATE AD_Field
SET DisplayLogic = '@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CompensationGroup''@!''CompensationGroup''', Updated = TO_TIMESTAMP('2026-09-28 10:01:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 563597
;
