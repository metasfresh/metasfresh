-- gh#28675: Polish forecast line field descriptions
--
-- 1. Line DatePromised: add "Stichtag" override (reuse element 584644 from header)
-- 2. Line Qty: add forecast-specific override explaining editable quantity
-- 3. Line QtyCalculated: add forecast-specific override explaining system-generated quantity
-- 4. Fix element 2500 (QtyCalculated) base language from English to German

-- ==========================================================================
-- 1. Line DatePromised → "Stichtag" (reuse element 584644 created in 5793150)
-- ==========================================================================
UPDATE AD_Field SET
  AD_Name_ID=584644,
  Updated=TO_TIMESTAMP('2026-03-11 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=53639; -- M_ForecastLine.DatePromised field on line tab

SELECT update_FieldTranslation_From_AD_Name_Element(584644);

-- ==========================================================================
-- 2. Line Qty: forecast-specific override (new element 584657)
--    Explains that Qty is the editable forecast quantity
-- ==========================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType,
                        Name, PrintName,
                        Description, Help)
VALUES (584657, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-11 22:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-11 22:00', 'YYYY-MM-DD HH24:MI'), 100,
        NULL, 'D',
        'Menge', 'Menge',
        'Prognosemenge (editierbar). Entspricht initial der berechneten Menge, kann aber manuell angepasst werden.',
        'Die vom System berechnete Prognosemenge wird hier als Vorschlag eingetragen. Sie können den Wert manuell überschreiben, z.B. um saisonale Schwankungen oder Sonderaktionen zu berücksichtigen. Die ursprüngliche Berechnung bleibt im Feld "Berechnete Menge" erhalten.');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584657,
       'Menge', 'Menge',
       'Prognosemenge (editierbar). Entspricht initial der berechneten Menge, kann aber manuell angepasst werden.',
       'Die vom System berechnete Prognosemenge wird hier als Vorschlag eingetragen. Sie können den Wert manuell überschreiben, z.B. um saisonale Schwankungen oder Sonderaktionen zu berücksichtigen. Die ursprüngliche Berechnung bleibt im Feld "Berechnete Menge" erhalten.',
       'N', 0, 0,
       TO_TIMESTAMP('2026-03-11 22:00', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-11 22:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Language l
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Element_ID=584657);

UPDATE AD_Element_Trl SET
  Name='Quantity', PrintName='Quantity',
  Description='Forecast quantity (editable). Initially equals the calculated quantity but can be manually adjusted.',
  Help='The system-calculated forecast quantity is pre-filled here as a suggestion. You can override the value manually, e.g. to account for seasonal fluctuations or promotions. The original calculation is preserved in the "Calculated Quantity" field.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-11 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584657 AND AD_Language='en_US';

UPDATE AD_Field SET
  AD_Name_ID=584657,
  Updated=TO_TIMESTAMP('2026-03-11 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=10306; -- M_ForecastLine.Qty field on line tab

SELECT update_FieldTranslation_From_AD_Name_Element(584657);

-- ==========================================================================
-- 3. Line QtyCalculated: forecast-specific override (new element 584658)
--    Explains that QtyCalculated is read-only, system-generated
-- ==========================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType,
                        Name, PrintName,
                        Description, Help)
VALUES (584658, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-11 22:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-11 22:00', 'YYYY-MM-DD HH24:MI'), 100,
        NULL, 'D',
        'Berechnete Menge', 'Berechnete Menge',
        'Vom System berechnete Prognosemenge (schreibgeschützt). Wird bei der Prognoseerstellung automatisch ermittelt.',
        'Zeigt die ursprünglich vom System berechnete Prognosemenge an. Dieser Wert wird bei der Prognoseerstellung automatisch aus der Verkaufshistorie ermittelt und kann nicht manuell geändert werden. Vergleichen Sie diesen Wert mit dem Feld "Menge", um manuelle Anpassungen nachzuvollziehen.');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584658,
       'Berechnete Menge', 'Berechnete Menge',
       'Vom System berechnete Prognosemenge (schreibgeschützt). Wird bei der Prognoseerstellung automatisch ermittelt.',
       'Zeigt die ursprünglich vom System berechnete Prognosemenge an. Dieser Wert wird bei der Prognoseerstellung automatisch aus der Verkaufshistorie ermittelt und kann nicht manuell geändert werden. Vergleichen Sie diesen Wert mit dem Feld "Menge", um manuelle Anpassungen nachzuvollziehen.',
       'N', 0, 0,
       TO_TIMESTAMP('2026-03-11 22:00', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-11 22:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Language l
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Element_ID=584658);

UPDATE AD_Element_Trl SET
  Name='Calculated Quantity', PrintName='Calculated Quantity',
  Description='System-calculated forecast quantity (read-only). Automatically determined during forecast generation.',
  Help='Shows the original forecast quantity calculated by the system from sales history. This value is set automatically during forecast generation and cannot be manually changed. Compare with the "Quantity" field to track manual adjustments.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-11 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584658 AND AD_Language='en_US';

UPDATE AD_Field SET
  AD_Name_ID=584658,
  Updated=TO_TIMESTAMP('2026-03-11 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Field_ID=10310; -- M_ForecastLine.QtyCalculated field on line tab

SELECT update_FieldTranslation_From_AD_Name_Element(584658);

-- ==========================================================================
-- 4. Fix element 2500 (QtyCalculated) base language: English → German
--    This element is only used in Prognose and Bedarf windows
-- ==========================================================================
UPDATE AD_Element SET
  Name='Berechnete Menge',
  PrintName='Berechnete Menge',
  Description='Berechnete Menge',
  Updated=TO_TIMESTAMP('2026-03-11 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=2500;

UPDATE AD_Element_Trl SET
  Name='Berechnete Menge',
  PrintName='Berechnete Menge',
  Description='Berechnete Menge',
  IsTranslated='N',
  Updated=TO_TIMESTAMP('2026-03-11 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=2500 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl SET
  Name='Calculated Quantity',
  PrintName='Calculated Quantity',
  Description='Calculated Quantity',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-11 22:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=2500 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2500, 'en_US');
