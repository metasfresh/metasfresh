-- Contract compensation groups: SO->PO take-over -- two child tables of C_CompensationGroup_ContractSettings:
--   C_CompensationGroup_ContractSettings_TakeOver         one record per product category: the discount product used for the
--                                                         own (purchase) discount line
--   C_CompensationGroup_ContractSettings_TakeOver_Product the customer (sales) discount products that are taken over for that category
-- ============================================================================
-- 1) AD_Element for the two new primary keys
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585505 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_TakeOver_ID', 'de.metas.contracts', 'Kompensationsgruppen-Vertragseinstellung Übernahme', 'Kompensationsgruppen-Vertragseinstellung Übernahme',
        'Übernahme-Einstellung je Produktkategorie: Rabatt-Produkt der eigenen Zeile eines Kompensationsgruppen-Vertrags.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585505, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585505
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Compensation group contract settings take-over', PrintName = 'Compensation group contract settings take-over',
    Description = 'Take-over setting per product category: discount product of the own line of a compensation group contract.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585505 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 09:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585505 AND AD_Language IN ('de_DE', 'de_CH')
;
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585506 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_TakeOver_Product_ID', 'de.metas.contracts', 'Kompensationsgruppen-Übernahme Kundenrabatt-Produkt', 'Kompensationsgruppen-Übernahme Kundenrabatt-Produkt',
        'Kundenrabatt-Produkt, das für eine Übernahme-Einstellung eines Kompensationsgruppen-Vertrags übernommen wird.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585506, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585506
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Compensation group take-over customer discount product', PrintName = 'Compensation group take-over customer discount product',
    Description = 'Customer discount product that is taken over for a take-over setting of a compensation group contract.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 09:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585506 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 09:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585506 AND AD_Language IN ('de_DE', 'de_CH')
;
-- ============================================================================
-- 2) New table C_CompensationGroup_ContractSettings_TakeOver
-- ============================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      TableName, Name, AccessLevel, EntityType, IsView, ImportTable, IsChangeLog, ReplicationType,
                      IsSecurityEnabled, IsDeleteable, IsHighVolume, IsAutocomplete, IsDLM, CopyColumnsFromTable,
                      LoadSeq, ACTriggerLength)
VALUES (542652 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_TakeOver', 'Kompensationsgruppen-Vertragseinstellung Übernahme', 3, 'de.metas.contracts', 'N', 'N', 'Y', 'L',
        'N', 'Y', 'N', 'N', 'N', 'N',
        0, 0)
;
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Table_ID = 542652
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID)
;
UPDATE AD_Table_Trl
SET Name = 'Compensation group contract settings take-over', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542652 AND AD_Language = 'en_US'
;
UPDATE AD_Table_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542652 AND AD_Language IN ('de_DE', 'de_CH')
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593674 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Client_ID', 542652, 102, 19,
        10, 'Mandant', 'Mandant für diese Installation.', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593674
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593675 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Org_ID', 542652, 113, 30,
        10, 'Sektion', 'Organisatorische Einheit des Mandanten', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593675
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593676 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Created', 542652, 245, 16,
        29, 'Erstellt', 'Datum, an dem dieser Eintrag erstellt wurde', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593676
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593677 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'CreatedBy', 542652, 246, 18,
        10, 'Erstellt durch', 'Nutzer, der diesen Eintrag erstellt hat', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593677
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593678 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'IsActive', 542652, 348, 20,
        1, 'Aktiv', 'Der Eintrag ist im System aktiv', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593678
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593679 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Updated', 542652, 607, 16,
        29, 'Aktualisiert', 'Datum, an dem dieser Eintrag aktualisiert wurde', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593679
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593680 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'UpdatedBy', 542652, 608, 18,
        10, 'Aktualisiert durch', 'Nutzer, der diesen Eintrag aktualisiert hat', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593680
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593681 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_TakeOver_ID', 542652, 585505, 13,
        10, 'Kompensationsgruppen-Vertragseinstellung Übernahme', NULL, NULL,
        'Y', 'N', 'N', NULL,
        'Y', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593681
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593682 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_ID', 542652, 585492, 30,
        10, 'Einstellungen für Kompensationsgruppen-Verträge', 'Kompensationsgruppen-Vertragseinstellung, zu der diese Übernahme-Einstellung gehört.', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'Y', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593682
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593683 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'M_Product_Category_ID', 542652, 453, 19,
        10, 'Produkt Kategorie', 'Produktkategorie der Verkaufszeilen, für die die Übernahme gilt.', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593683
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593684 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'M_Product_ID', 542652, 454, 30,
        10, 'Produkt', 'Rabatt-Produkt der eigenen Rabattzeile im Einkaufsbeleg.', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593684
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
-- Physical table + PK + FKs + partial unique index (one take-over record per product category and settings row)
/* DDL */ CREATE TABLE public.C_CompensationGroup_ContractSettings_TakeOver (
    AD_Client_ID NUMERIC(10) NOT NULL,
    AD_Org_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_TakeOver_ID NUMERIC(10) NOT NULL,
    Created TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy NUMERIC(10) NOT NULL,
    IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL,
    M_Product_Category_ID NUMERIC(10) NOT NULL,
    M_Product_ID NUMERIC(10) NOT NULL,
    Updated TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy NUMERIC(10) NOT NULL,
    CONSTRAINT C_CompGroup_ContractSettings_TakeOver_Key PRIMARY KEY (C_CompensationGroup_ContractSettings_TakeOver_ID),
    CONSTRAINT CCompGroupContractSettings_CCompGroupContractSettingsTakeOver FOREIGN KEY (C_CompensationGroup_ContractSettings_ID) REFERENCES public.C_CompensationGroup_ContractSettings DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MProductCategory_CCompGroupContractSettingsTakeOver FOREIGN KEY (M_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MProduct_CCompGroupContractSettingsTakeOver FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
)
;
CREATE UNIQUE INDEX c_compgroup_contractsettings_takeover_category_active_uq
    ON public.C_CompensationGroup_ContractSettings_TakeOver (C_CompensationGroup_ContractSettings_ID, M_Product_Category_ID)
    WHERE IsActive = 'Y'
;
-- ============================================================================
-- 3) New table C_CompensationGroup_ContractSettings_TakeOver_Product
-- ============================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      TableName, Name, AccessLevel, EntityType, IsView, ImportTable, IsChangeLog, ReplicationType,
                      IsSecurityEnabled, IsDeleteable, IsHighVolume, IsAutocomplete, IsDLM, CopyColumnsFromTable,
                      LoadSeq, ACTriggerLength)
VALUES (542653 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_TakeOver_Product', 'Kompensationsgruppen-Übernahme Kundenrabatt-Produkt', 3, 'de.metas.contracts', 'N', 'N', 'Y', 'L',
        'N', 'Y', 'N', 'N', 'N', 'N',
        0, 0)
;
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Table_ID = 542653
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID)
;
UPDATE AD_Table_Trl
SET Name = 'Compensation group take-over customer discount product', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 09:00:36', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542653 AND AD_Language = 'en_US'
;
UPDATE AD_Table_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 09:00:37', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542653 AND AD_Language IN ('de_DE', 'de_CH')
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593685 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:38', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:39', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Client_ID', 542653, 102, 19,
        10, 'Mandant', 'Mandant für diese Installation.', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593685
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593686 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Org_ID', 542653, 113, 30,
        10, 'Sektion', 'Organisatorische Einheit des Mandanten', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593686
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593687 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Created', 542653, 245, 16,
        29, 'Erstellt', 'Datum, an dem dieser Eintrag erstellt wurde', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593687
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593688 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'CreatedBy', 542653, 246, 18,
        10, 'Erstellt durch', 'Nutzer, der diesen Eintrag erstellt hat', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593688
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593689 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'IsActive', 542653, 348, 20,
        1, 'Aktiv', 'Der Eintrag ist im System aktiv', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593689
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593690 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:48', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:49', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Updated', 542653, 607, 16,
        29, 'Aktualisiert', 'Datum, an dem dieser Eintrag aktualisiert wurde', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593690
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593691 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'UpdatedBy', 542653, 608, 18,
        10, 'Aktualisiert durch', 'Nutzer, der diesen Eintrag aktualisiert hat', NULL,
        'Y', 'N', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593691
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593692 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_TakeOver_Product_ID', 542653, 585506, 13,
        10, 'Kompensationsgruppen-Übernahme Kundenrabatt-Produkt', NULL, NULL,
        'Y', 'N', 'N', NULL,
        'Y', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593692
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593693 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:54', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:55', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_TakeOver_ID', 542653, 585505, 30,
        10, 'Kompensationsgruppen-Vertragseinstellung Übernahme', 'Übernahme-Einstellung, zu der dieses Kundenrabatt-Produkt gehört.', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'Y', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593693
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593694 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 09:00:56', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 09:00:57', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'M_Product_ID', 542653, 454, 30,
        10, 'Produkt', 'Kundenrabatt-Produkt der Verkaufsrabattzeile, das übernommen wird.', NULL,
        'Y', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593694
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
-- Physical table + PK + FKs
/* DDL */ CREATE TABLE public.C_CompensationGroup_ContractSettings_TakeOver_Product (
    AD_Client_ID NUMERIC(10) NOT NULL,
    AD_Org_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_TakeOver_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_TakeOver_Product_ID NUMERIC(10) NOT NULL,
    Created TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy NUMERIC(10) NOT NULL,
    IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL,
    M_Product_ID NUMERIC(10) NOT NULL,
    Updated TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy NUMERIC(10) NOT NULL,
    CONSTRAINT C_CompGroup_CSTakeOver_Product_Key PRIMARY KEY (C_CompensationGroup_ContractSettings_TakeOver_Product_ID),
    CONSTRAINT CCompGroupCSTakeOver_CCompGroupCSTakeOverProduct FOREIGN KEY (C_CompensationGroup_ContractSettings_TakeOver_ID) REFERENCES public.C_CompensationGroup_ContractSettings_TakeOver DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MProduct_CCompGroupCSTakeOverProduct FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
)
;
-- ============================================================================
-- 4) Column translations of the element-driven columns, taken from their AD_Element_Trl (scoped to the columns created above)
-- ============================================================================
UPDATE AD_Column_Trl ct
SET Name = et.Name, IsTranslated = et.IsTranslated, Updated = TO_TIMESTAMP('2026-10-01 09:00:58', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID
  AND ct.AD_Language = et.AD_Language
  AND c.AD_Column_ID IN (593681, 593682, 593683, 593684, 593692, 593693, 593694)
;
