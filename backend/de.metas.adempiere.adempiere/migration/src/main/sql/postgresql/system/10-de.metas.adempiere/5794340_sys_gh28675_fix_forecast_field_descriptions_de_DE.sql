-- gh#28675: Fix forecast line field descriptions for de_DE (base language)
--
-- Root cause: Migration 5793890 created override elements (584657, 584658) with
-- AD_Name_ID on AD_Field, then called update_FieldTranslation_From_AD_Name_Element().
-- But the INSERT for AD_Element_Trl excluded the base language (IsBaseLanguage='N'),
-- so the sync function had no de_DE source row and skipped:
--   - AD_Field.description/help (base record)
--   - AD_Field_Trl for de_DE
-- en_US and de_CH translations were synced correctly.
--
-- Fix: Directly set AD_Field.description/help and AD_Field_Trl for de_DE.

-- ==========================================================================
-- 1. Ensure AD_Element_Trl for de_DE exists for override elements
--    (may have been created by app sync, but ensure it's there for preloaded-db)
-- ==========================================================================
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 'de_DE', 584657,
       e.Name, e.PrintName, e.Description, e.Help,
       'N', 0, 0,
       TO_TIMESTAMP('2026-03-13 18:00', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-13 18:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Element e
WHERE e.AD_Element_ID = 584657
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language = 'de_DE' AND t.AD_Element_ID = 584657);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 'de_DE', 584658,
       e.Name, e.PrintName, e.Description, e.Help,
       'N', 0, 0,
       TO_TIMESTAMP('2026-03-13 18:00', 'YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-13 18:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Element e
WHERE e.AD_Element_ID = 584658
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language = 'de_DE' AND t.AD_Element_ID = 584658);

-- ==========================================================================
-- 2. Fix AD_Field base record: Qty (10306) — forecast-specific description
-- ==========================================================================
UPDATE AD_Field SET
  Description = 'Prognosemenge (editierbar). Entspricht initial der berechneten Menge, kann aber manuell angepasst werden.',
  Help = 'Die vom System berechnete Prognosemenge wird hier als Vorschlag eingetragen. Sie können den Wert manuell überschreiben, z.B. um saisonale Schwankungen oder Sonderaktionen zu berücksichtigen. Die ursprüngliche Berechnung bleibt im Feld "Berechnete Menge" erhalten.',
  Updated = TO_TIMESTAMP('2026-03-13 18:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 10306;

-- ==========================================================================
-- 3. Fix AD_Field base record: QtyCalculated (10310) — forecast-specific description
-- ==========================================================================
UPDATE AD_Field SET
  Description = 'Vom System berechnete Prognosemenge (schreibgeschützt). Wird bei der Prognoseerstellung automatisch ermittelt.',
  Help = 'Zeigt die ursprünglich vom System berechnete Prognosemenge an. Dieser Wert wird bei der Prognoseerstellung automatisch aus der Verkaufshistorie ermittelt und kann nicht manuell geändert werden. Vergleichen Sie diesen Wert mit dem Feld "Menge", um manuelle Anpassungen nachzuvollziehen.',
  Updated = TO_TIMESTAMP('2026-03-13 18:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 10310;

-- ==========================================================================
-- 4. Fix AD_Field_Trl for de_DE: Qty (10306)
-- ==========================================================================
UPDATE AD_Field_Trl SET
  Description = 'Prognosemenge (editierbar). Entspricht initial der berechneten Menge, kann aber manuell angepasst werden.',
  Help = 'Die vom System berechnete Prognosemenge wird hier als Vorschlag eingetragen. Sie können den Wert manuell überschreiben, z.B. um saisonale Schwankungen oder Sonderaktionen zu berücksichtigen. Die ursprüngliche Berechnung bleibt im Feld "Berechnete Menge" erhalten.',
  Updated = TO_TIMESTAMP('2026-03-13 18:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 10306 AND AD_Language = 'de_DE';

-- ==========================================================================
-- 5. Fix AD_Field_Trl for de_DE: QtyCalculated (10310)
-- ==========================================================================
UPDATE AD_Field_Trl SET
  Description = 'Vom System berechnete Prognosemenge (schreibgeschützt). Wird bei der Prognoseerstellung automatisch ermittelt.',
  Help = 'Zeigt die ursprünglich vom System berechnete Prognosemenge an. Dieser Wert wird bei der Prognoseerstellung automatisch aus der Verkaufshistorie ermittelt und kann nicht manuell geändert werden. Vergleichen Sie diesen Wert mit dem Feld "Menge", um manuelle Anpassungen nachzuvollziehen.',
  Updated = TO_TIMESTAMP('2026-03-13 18:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 10310 AND AD_Language = 'de_DE';
