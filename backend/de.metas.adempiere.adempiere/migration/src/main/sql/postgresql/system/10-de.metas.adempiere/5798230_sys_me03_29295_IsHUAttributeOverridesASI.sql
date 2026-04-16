-- me03#29295: Add IsHUAttributeOverridesASI to M_ShipmentSchedule_AttributeConfig
--
-- Controls whether the HU attribute value overwrites the order line's ASI value
-- on the shipment line. Default Y = backward compatible (HU wins, as before).
-- Set N for attributes where the customer's order intent (schedule ASI) should
-- take precedence over what was physically picked (e.g., Herkunft/Positions Nr).

-- =============================================================================
-- 1. AD_Element
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help)
VALUES (584759 /*From ID Server*/, 0, 0, 'Y', '2026-04-16 18:00', 0, '2026-04-16 18:00', 0,
        'IsHUAttributeOverridesASI',
        'ME-Merkmal überschreibt Merkmalsatz',
        'ME-Merkmal überschreibt Merkmalsatz',
        'Wenn Ja, überschreibt der ME-Merkmalwert den Wert der Auftragsposition auf der Lieferzeile.',
        'Steuert, ob der physische Merkmalwert der Handling Unit (z.B. Lot-Nummer, MHD) den Wert aus dem Merkmalsatz der Auftragsposition überschreibt.'
        || ' Bei "Ja" (Standard) gewinnt der ME-Wert — das ist das bisherige Verhalten.'
        || ' Bei "Nein" hat der Merkmalsatz der Auftragsposition Vorrang — z.B. für kundenspezifische Merkmale wie "Herkunft" oder "Positions Nr.".');

-- Skeleton Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584759
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation
UPDATE AD_Element_Trl
SET Name        = 'HU Attribute Overrides ASI',
    PrintName   = 'HU Attribute Overrides ASI',
    Description = 'If Yes, the HU attribute value overwrites the order line''s ASI value on the shipment line.',
    Help        = 'Controls whether the physical HU attribute value (e.g., Lot Number, Best Before) overwrites the value from the order line''s attribute set instance.'
                  || ' "Yes" (default) = HU value wins — this is the existing behavior.'
                  || ' "No" = the order line''s ASI value takes precedence — use for customer-specific attributes like "Origin" or "Position Nr.".',
    IsTranslated = 'Y',
    Updated     = '2026-04-16 18:00',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584759 AND AD_Language = 'en_US';

-- German translation (base language)
UPDATE AD_Element_Trl
SET Name        = 'ME-Merkmal überschreibt Merkmalsatz',
    PrintName   = 'ME-Merkmal überschreibt Merkmalsatz',
    Description = 'Wenn Ja, überschreibt der ME-Merkmalwert den Wert der Auftragsposition auf der Lieferzeile.',
    Help        = 'Steuert, ob der physische Merkmalwert der Handling Unit (z.B. Lot-Nummer, MHD) den Wert aus dem Merkmalsatz der Auftragsposition überschreibt.'
                  || ' Bei "Ja" (Standard) gewinnt der ME-Wert — das ist das bisherige Verhalten.'
                  || ' Bei "Nein" hat der Merkmalsatz der Auftragsposition Vorrang — z.B. für kundenspezifische Merkmale wie "Herkunft" oder "Positions Nr.".',
    IsTranslated = 'N',
    Updated     = '2026-04-16 18:00',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584759 AND AD_Language = 'de_DE';

-- de_CH = same as de_DE
UPDATE AD_Element_Trl
SET Name        = 'ME-Merkmal überschreibt Merkmalsatz',
    PrintName   = 'ME-Merkmal überschreibt Merkmalsatz',
    Description = 'Wenn Ja, überschreibt der ME-Merkmalwert den Wert der Auftragsposition auf der Lieferzeile.',
    Help        = 'Steuert, ob der physische Merkmalwert der Handling Unit (z.B. Lot-Nummer, MHD) den Wert aus dem Merkmalsatz der Auftragsposition überschreibt.'
                  || ' Bei "Ja" (Standard) gewinnt der ME-Wert — das ist das bisherige Verhalten.'
                  || ' Bei "Nein" hat der Merkmalsatz der Auftragsposition Vorrang — z.B. für kundenspezifische Merkmale wie "Herkunft" oder "Positions Nr.".',
    IsTranslated = 'N',
    Updated     = '2026-04-16 18:00',
    UpdatedBy   = 0
WHERE AD_Element_ID = 584759 AND AD_Language = 'de_CH';

-- =============================================================================
-- 2. AD_Column
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592363 /*From ID Server*/, 0, 0, 'Y', '2026-04-16 18:00', 0, '2026-04-16 18:00', 0,
        0, 540951, 584759, 20,
        'IsHUAttributeOverridesASI',
        'ME-Merkmal überschreibt Merkmalsatz',
        'Wenn Ja, überschreibt der ME-Merkmalwert den Wert der Auftragsposition auf der Lieferzeile.',
        NULL,
        1, 'Y', 'Y', 'N',
        'Y', 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows for AD_Column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592363
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 3. Physical column (new column, use ALTER TABLE ADD COLUMN)
-- =============================================================================
ALTER TABLE M_ShipmentSchedule_AttributeConfig ADD COLUMN IF NOT EXISTS IsHUAttributeOverridesASI CHAR(1) DEFAULT 'Y';
UPDATE M_ShipmentSchedule_AttributeConfig SET IsHUAttributeOverridesASI = 'Y' WHERE IsHUAttributeOverridesASI IS NULL;
ALTER TABLE M_ShipmentSchedule_AttributeConfig ALTER COLUMN IsHUAttributeOverridesASI SET NOT NULL;

-- =============================================================================
-- 4. AD_Field on tab 541052 (Shipment Line Attribute Config)
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
VALUES (777075 /*From ID Server*/, 0, 0, 'Y', '2026-04-16 18:00', 0, '2026-04-16 18:00', 0,
        541052, 592363, NULL,
        'ME-Merkmal überschreibt Merkmalsatz',
        'Wenn Ja, überschreibt der ME-Merkmalwert den Wert der Auftragsposition auf der Lieferzeile.',
        'Y', 'Y', 'N', 'N',
        60, 60, 0, 'D');

-- Skeleton Trl rows for AD_Field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 777075
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 5. Propagate translations from AD_Element to all dependent records
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584759);
