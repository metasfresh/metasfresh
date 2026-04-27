-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: AD_Column for BaseAmt, M_InOut_ID, C_Invoice_ID on C_OrderPaySchedule (AD_Table_ID=542539).
-- The physical DDL columns were added by 5400030 / 5400040.
-- All three columns are system-managed; IsUpdateable='Y' to allow the authority function to write,
-- but the UI enforces read-only via AD_Field.IsReadOnly='Y'.

-- =============================================================================
-- 1. AD_Column: BaseAmt  (Amount reference, no AD_Reference_Value_ID needed)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent,
                       IsSelectionColumn, FilterOperator,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       IsSyncDatabase, PersonalDataCategory)
VALUES (592430 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        0, 542539 /*C_OrderPaySchedule*/, 584792 /*From ID Server*/, 12 /*Amount*/, NULL,
        'BaseAmt',
        'Berechtigter Betrag',
        'Betrag, auf den der Prozentsatz angewendet wird',
        'Entspricht order.GrandTotal für die LC-Zeile, dem Wareneingangs-Bruttobetrag für die '
        || 'Lieferungs-Unterzeile und dem verbleibenden Restbetrag für die Restzeile.',
        12, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N',
        'N', NULL,
        'N', 'N', 'N', 'Y',
        'Y', 'NP');

-- Skeleton Trl rows for BaseAmt column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592430
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate BaseAmt element translations to the new AD_Column
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584792);

-- =============================================================================
-- 2. AD_Column: M_InOut_ID  (Search/FK → AD_Reference_Value_ID=337 "M_InOut")
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent,
                       IsSelectionColumn, FilterOperator,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       IsSyncDatabase, PersonalDataCategory)
VALUES (592431 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        0, 542539 /*C_OrderPaySchedule*/, 1025 /*M_InOut_ID*/, 30 /*Search*/, 337 /*M_InOut*/,
        'M_InOut_ID',
        'Lieferung/Wareneingang',
        'Material Shipment Document',
        NULL,
        10, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N',
        'Y', 'E' /*Equals*/,
        'N', 'N', 'N', 'Y',
        'Y', 'NP');

-- Skeleton Trl rows for M_InOut_ID column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592431
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate M_InOut_ID element translations to the new AD_Column
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1025);

-- =============================================================================
-- 3. AD_Column: C_Invoice_ID  (Search/FK → AD_Reference_Value_ID=336 "C_Invoice")
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent,
                       IsSelectionColumn, FilterOperator,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       IsSyncDatabase, PersonalDataCategory)
VALUES (592432 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        0, 542539 /*C_OrderPaySchedule*/, 1008 /*C_Invoice_ID*/, 30 /*Search*/, 336 /*C_Invoice*/,
        'C_Invoice_ID',
        'Rechnung',
        'Invoice Identifier',
        NULL,
        10, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N',
        'Y', 'E' /*Equals*/,
        'N', 'N', 'N', 'Y',
        'Y', 'NP');

-- Skeleton Trl rows for C_Invoice_ID column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592432
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate C_Invoice_ID element translations to the new AD_Column
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1008);
