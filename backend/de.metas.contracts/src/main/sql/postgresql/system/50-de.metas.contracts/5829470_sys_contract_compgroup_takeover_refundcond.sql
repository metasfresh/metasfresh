-- Contract compensation groups: SO->PO take-over -- refund conditions on the take-over record:
--   C_CompensationGroup_ContractSettings_TakeOver.PercentagePrecision    decimals to which the summed percentage of the take-over line
--                                                                         is rounded (half up); default 2
--   C_CompensationGroup_ContractSettings_TakeOver_RefundCond  the refund conditions (C_Flatrate_Conditions of type Refund) whose
--                                                                         refund percentage is taken over into the take-over line
--   AD_Message ContractCompensationGroup_TakeOverRefundCondNotUnique     refuses listing one refund condition on two take-over records
--                                                                         of the same settings
-- ============================================================================
-- 1) AD_Element for the two new columns
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585540 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'PercentagePrecision', 'de.metas.contracts', 'Nachkommastellen der Summe', 'Nachkommastellen der Summe',
        'Anzahl der Nachkommastellen, auf die der summierte Prozentsatz der Übernahme-Zeile kaufmännisch gerundet wird.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585540
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Decimals of the summed percentage', PrintName = 'Decimals of the summed percentage',
    Description = 'Number of decimals to which the summed percentage of the take-over line is rounded half up.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585540 AND AD_Language = 'en_US'
;
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585541 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID', 'de.metas.contracts', 'Übernahme Rückvergütungsbedingung', 'Übernahme Rückvergütungsbedingung',
        'Rückvergütungsbedingung, deren Rückvergütungs-Prozentsatz in die Übernahme-Zeile übernommen wird.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585541
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Take-over refund condition', PrintName = 'Take-over refund condition',
    Description = 'Refund condition whose refund percentage is taken over into the take-over line.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585541 AND AD_Language = 'en_US'
;
-- ============================================================================
-- 2) New column C_CompensationGroup_ContractSettings_TakeOver.PercentagePrecision (mandatory, default 2)
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593738 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'PercentagePrecision', 542652, 585540, 11, NULL,
        10, 'Nachkommastellen der Summe', 'Anzahl der Nachkommastellen, auf die der summierte Prozentsatz der Übernahme-Zeile kaufmännisch gerundet wird.', NULL,
        'Y', 'Y', 'N', '2', '0', '6',
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593738
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
-- ADD COLUMN with a constant default fills the existing take-over records with 2
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_ContractSettings_TakeOver', 'ALTER TABLE public.C_CompensationGroup_ContractSettings_TakeOver ADD COLUMN PercentagePrecision NUMERIC(10) DEFAULT 2 NOT NULL')
;
-- ============================================================================
-- 3) Validation rule: only refund conditions
-- ============================================================================
INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID, Code, Created, CreatedBy, EntityType, IsActive, Name, Type, Updated, UpdatedBy, Description)
VALUES (0, 0, 540807 /*From ID Server*/, 'C_Flatrate_Conditions.Type_Conditions=''Refund''', TO_TIMESTAMP('2026-10-15 10:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y', 'C_Flatrate_Conditions_Refund', 'S', TO_TIMESTAMP('2026-10-15 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Nur Vertragsbedingungen vom Typ Rückvergütung.')
;
-- ============================================================================
-- 4) New table C_CompensationGroup_ContractSettings_TakeOver_RefundCond
-- ============================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      TableName, Name, AccessLevel, EntityType, IsView, ImportTable, IsChangeLog, ReplicationType,
                      IsSecurityEnabled, IsDeleteable, IsHighVolume, IsAutocomplete, IsDLM, CopyColumnsFromTable,
                      LoadSeq, ACTriggerLength)
VALUES (542656 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_ContractSettings_TakeOver_RefundCond', 'Übernahme Rückvergütungsbedingung', 3, 'de.metas.contracts', 'N', 'N', 'Y', 'L',
        'N', 'Y', 'N', 'N', 'N', 'N',
        0, 0)
;
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Table_ID = 542656
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID)
;
UPDATE AD_Table_Trl
SET Name = 'Take-over refund condition', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 542656 AND AD_Language = 'en_US'
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593739 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Client_ID', 542656, 102, 19, NULL,
        10, 'Mandant', 'Mandant für diese Installation.', NULL,
        'Y', 'N', 'N', NULL, NULL, NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593739
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593740 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'AD_Org_ID', 542656, 113, 30, NULL,
        10, 'Sektion', 'Organisatorische Einheit des Mandanten', NULL,
        'Y', 'N', 'N', NULL, NULL, NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593740
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593741 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Created', 542656, 245, 16, NULL,
        29, 'Erstellt', 'Datum, an dem dieser Eintrag erstellt wurde', NULL,
        'Y', 'N', 'N', NULL, NULL, NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593741
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593742 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:19', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'CreatedBy', 542656, 246, 18, NULL,
        10, 'Erstellt durch', 'Nutzer, der diesen Eintrag erstellt hat', NULL,
        'Y', 'N', 'N', NULL, NULL, NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593742
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593743 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:22', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'IsActive', 542656, 348, 20, NULL,
        1, 'Aktiv', 'Der Eintrag ist im System aktiv', NULL,
        'Y', 'Y', 'N', NULL, NULL, NULL,
        'N', 'N', 'N', 'N', 'N', 'Y',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593743
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593744 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:23', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:24', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'Updated', 542656, 607, 16, NULL,
        29, 'Aktualisiert', 'Datum, an dem dieser Eintrag aktualisiert wurde', NULL,
        'Y', 'N', 'N', NULL, NULL, NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593744
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593745 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:25', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'UpdatedBy', 542656, 608, 18, NULL,
        10, 'Aktualisiert durch', 'Nutzer, der diesen Eintrag aktualisiert hat', NULL,
        'Y', 'N', 'N', NULL, NULL, NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593745
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593746 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:27', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:28', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID', 542656, 585541, 13, NULL,
        10, 'Übernahme Rückvergütungsbedingung', 'Rückvergütungsbedingung, deren Rückvergütungs-% bei einer Übernahme-Einstellung eines Kompensationsgruppen-Vertrags in die Bestellung übernommen wird.', NULL,
        'Y', 'N', 'N', NULL, NULL, NULL,
        'Y', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593746
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593747 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:29', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_CompensationGroup_ContractSettings_TakeOver_ID', 542656, 585505, 30, NULL,
        10, 'Kompensationsgruppen-Vertragseinstellung Übernahme', 'Übernahme-Einstellung, zu der diese Rückvergütungsbedingung gehört.', NULL,
        'Y', 'Y', 'N', NULL, NULL, NULL,
        'N', 'Y', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593747
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Val_Rule_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue, ValueMin, ValueMax,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593748 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-15 10:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-15 10:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'C_Flatrate_Conditions_ID', 542656, 541423, 30, 540807,
        10, 'Vertragsbedingungen', 'Rückvergütungsbedingung, deren Rückvergütungs-Prozentsatz in die Übernahme-Zeile übernommen wird.', NULL,
        'Y', 'Y', 'N', NULL, NULL, NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593748
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
-- Physical table + PK + FKs + one index per FK
/* DDL */ CREATE TABLE public.C_CompensationGroup_ContractSettings_TakeOver_RefundCond (
    AD_Client_ID NUMERIC(10) NOT NULL,
    AD_Org_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_TakeOver_ID NUMERIC(10) NOT NULL,
    C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID NUMERIC(10) NOT NULL,
    C_Flatrate_Conditions_ID NUMERIC(10) NOT NULL,
    Created TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy NUMERIC(10) NOT NULL,
    IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL,
    Updated TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy NUMERIC(10) NOT NULL,
    CONSTRAINT C_CompGroup_CSTakeOver_RefundCond_Key PRIMARY KEY (C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID),
    CONSTRAINT CCompGroupCSTakeOver_CCompGroupCSTakeOverRefundCond FOREIGN KEY (C_CompensationGroup_ContractSettings_TakeOver_ID) REFERENCES public.C_CompensationGroup_ContractSettings_TakeOver DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CFlatrateConditions_CCompGroupCSTakeOverRefundCond FOREIGN KEY (C_Flatrate_Conditions_ID) REFERENCES public.C_Flatrate_Conditions DEFERRABLE INITIALLY DEFERRED
)
;
CREATE INDEX c_compgroup_cstakeover_refundcond_takeover_id
    ON public.C_CompensationGroup_ContractSettings_TakeOver_RefundCond (C_CompensationGroup_ContractSettings_TakeOver_ID)
;
CREATE INDEX c_compgroup_cstakeover_refundcond_conditions_id
    ON public.C_CompensationGroup_ContractSettings_TakeOver_RefundCond (C_Flatrate_Conditions_ID)
;
-- ============================================================================
-- 5) Column translations of the element-driven columns, taken from their AD_Element_Trl (scoped to the columns created above)
-- ============================================================================
UPDATE AD_Column_Trl ct
SET Name = et.Name, IsTranslated = et.IsTranslated, Updated = TO_TIMESTAMP('2026-10-15 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ct.AD_Column_ID = c.AD_Column_ID
  AND ct.AD_Language = et.AD_Language
  AND c.AD_Column_ID IN (593738, 593746, 593747, 593748)
;
-- ============================================================================
-- 6) AD_Message ContractCompensationGroup_TakeOverRefundCondNotUnique
-- ============================================================================
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545929 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-15 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 'Y',
        'Die Rückvergütungsbedingung {0} ist bereits in einem anderen Übernahme-Datensatz dieser Einstellungen eingetragen.', 'E', TO_TIMESTAMP('2026-10-15 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'ContractCompensationGroup_TakeOverRefundCondNotUnique')
;
UPDATE AD_Message SET ErrorCode = 'ContractCompGroup_TakeOverRefundCondDup', Updated = TO_TIMESTAMP('2026-10-15 10:00:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Message_ID = 545929
;
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Message_ID = 545929
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;
UPDATE AD_Message_Trl
SET MsgText = 'The refund condition {0} is already listed on another take-over record of these settings.', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 10:00:36', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545929
;
-- de_DE / de_CH already carry the base (German) text as seeded above; just mark them as translated.
UPDATE AD_Message_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-15 10:00:37', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545929
;
