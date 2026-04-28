-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: AD_Column rows for IsPartialInvoice on C_DocType (AD_Table_ID=217) and C_Invoice (AD_Table_ID=318).
-- Both reference shared AD_Element 584794.
-- C_DocType: IsSelectionColumn='N' (admin window, few rows, no grid filter needed).
-- C_Invoice: IsSelectionColumn='Y', FilterOperator='E' (users filter by partial/final).

-- =============================================================================
-- 1. AD_Column on C_DocType (AD_Table_ID=217)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsKey, IsParent, IsUpdateable,
                       IsEncrypted, IsIdentifier, IsAllowLogging, IsSyncDatabase,
                       DefaultValue, Version, EntityType,
                       IsSelectionColumn, PersonalDataCategory)
VALUES (592433 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        217, 584794 /*AD_Element IsPartialInvoice*/, 20 /*Yes-No*/,
        'IsPartialInvoice', 'Teilrechnung',
        'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
        'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
        || 'wie der Anzahlungsbetrag zugeordnet wird: '
        || 'Teilrechnung (Y) → MIN(Wareneingang × LC%, verbleibende Anzahlung); '
        || 'Endrechnung (N) → verbleibende Anzahlung vollständig verbrauchen.',
        1, 'Y', 'N', 'N', 'Y',
        'N', 'N', 'Y', 'Y',
        'Y', 0, 'D',
        'N', 'NP');

-- Skeleton AD_Column_Trl rows (will be filled by AD_Element propagation below)
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592433
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 2. AD_Column on C_Invoice (AD_Table_ID=318)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsKey, IsParent, IsUpdateable,
                       IsEncrypted, IsIdentifier, IsAllowLogging, IsSyncDatabase,
                       DefaultValue, Version, EntityType,
                       IsSelectionColumn, FilterOperator, PersonalDataCategory)
VALUES (592434 /*From ID Server*/, 0, 0, 'Y', '2026-04-28 21:30', 0, '2026-04-28 21:30', 0,
        318, 584794 /*AD_Element IsPartialInvoice*/, 20 /*Yes-No*/,
        'IsPartialInvoice', 'Teilrechnung',
        'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
        'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, '
        || 'wie der Anzahlungsbetrag zugeordnet wird: '
        || 'Teilrechnung (Y) → MIN(Wareneingang × LC%, verbleibende Anzahlung); '
        || 'Endrechnung (N) → verbleibende Anzahlung vollständig verbrauchen. '
        || 'Editierbar solange Belegstatus Entwurf (DR) oder In Bearbeitung (IP); danach schreibgeschützt.',
        1, 'Y', 'N', 'N', 'Y',
        'N', 'N', 'Y', 'Y',
        'Y', 0, 'D',
        'Y', 'E', 'NP');

-- Skeleton AD_Column_Trl rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592434
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 3. Propagate AD_Element translations to the new AD_Column_Trl rows
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584794);
