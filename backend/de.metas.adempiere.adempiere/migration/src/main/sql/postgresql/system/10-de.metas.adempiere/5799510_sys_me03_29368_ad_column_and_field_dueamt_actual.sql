-- 2026-04-24 https://github.com/metasfresh/me03/issues/29368
-- Iter 2: AD_Column + AD_Field for DueAmt_Actual on C_OrderPaySchedule.
-- The column is system-managed (authority function writes it); users must not edit it.
-- IsUpdateable='N' + ReadOnlyLogic='Y' enforce read-only in the UI.

-- =============================================================================
-- 1. AD_Column
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       ReadOnlyLogic, DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592416 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
        0, 542539 /*C_OrderPaySchedule*/, 584784 /*From ID Server*/, 12 /*Amount*/,
        'DueAmt_Actual',
        'Tatsächlich fälliger Betrag',
        'Tatsächlicher Betrag, der diesem Zahlungsplan-Schritt zugewiesen wurde',
        'Wird durch die LC-Autoritätsfunktion gesetzt, wenn eine Proforma-Rechnung zugewiesen wird. '
        || 'DueAmt bleibt an order.GrandTotal × Prozentsatz gebunden; DueAmt_Actual erfasst '
        || 'den tatsächlichen Betrag aus der Proforma-Rechnung. NULL bei allen Nicht-LC-Zeilen.',
        10, 'N', 'N', 'N',
        'Y', NULL, 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows for AD_Column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592416
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate AD_Element translations to the new AD_Column
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584784);

-- =============================================================================
-- 2. AD_Field on tab 548449 (Bestellung_OLD — window 181)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
VALUES (777249 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
        548449 /*Zahlungsplan tab in Bestellung_OLD*/, 592416 /*From ID Server*/, NULL,
        'Tatsächlich fälliger Betrag',
        'Tatsächlicher Betrag, der diesem Zahlungsplan-Schritt zugewiesen wurde',
        'Y', 'Y', 'Y', 'N',
        10, 10, 0, 'D');

-- Skeleton Trl rows for AD_Field on tab 548449
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777249
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 3. AD_Field on tab 548450 (Zahlungsplan tab in Bestellung — window 541889)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
VALUES (777250 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
        548450 /*Zahlungsplan tab in Bestellung*/, 592416 /*From ID Server*/, NULL,
        'Tatsächlich fälliger Betrag',
        'Tatsächlicher Betrag, der diesem Zahlungsplan-Schritt zugewiesen wurde',
        'Y', 'Y', 'Y', 'N',
        10, 10, 0, 'D');

-- Skeleton Trl rows for AD_Field on tab 548450
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777250
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Propagate translations again now that AD_Field rows exist
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584784);
