-- me03#29295: Fix description of IsHUAttributeOverridesASI
-- The ASI comes from the shipment schedule (Lieferdisposition), not directly from the order line.
-- The schedule ASI can also be edited independently.

-- German (base language on AD_Element)
UPDATE AD_Element
SET Description = 'Wenn Ja, überschreibt der HU-Merkmalwert den Wert aus der Lieferdisposition auf der Lieferzeile.',
    Help = 'Steuert, ob der physische Merkmalwert der Handling Unit (z.B. Lot-Nummer, MHD) den Wert aus dem Merkmalsatz der Lieferdisposition überschreibt.'
           || ' Bei "Ja" (Standard) gewinnt der HU-Wert — das ist das bisherige Verhalten.'
           || ' Bei "Nein" hat der Merkmalsatz der Lieferdisposition Vorrang — z.B. für kundenspezifische Merkmale wie "Herkunft" oder "Positions Nr.".',
    Updated = '2026-04-18 14:00',
    UpdatedBy = 0
WHERE AD_Element_ID = 584759;

-- de_DE
UPDATE AD_Element_Trl
SET Description = 'Wenn Ja, überschreibt der HU-Merkmalwert den Wert aus der Lieferdisposition auf der Lieferzeile.',
    Help = 'Steuert, ob der physische Merkmalwert der Handling Unit (z.B. Lot-Nummer, MHD) den Wert aus dem Merkmalsatz der Lieferdisposition überschreibt.'
           || ' Bei "Ja" (Standard) gewinnt der HU-Wert — das ist das bisherige Verhalten.'
           || ' Bei "Nein" hat der Merkmalsatz der Lieferdisposition Vorrang — z.B. für kundenspezifische Merkmale wie "Herkunft" oder "Positions Nr.".',
    Updated = '2026-04-18 14:00',
    UpdatedBy = 0
WHERE AD_Element_ID = 584759 AND AD_Language = 'de_DE';

-- de_CH
UPDATE AD_Element_Trl
SET Description = 'Wenn Ja, überschreibt der HU-Merkmalwert den Wert aus der Lieferdisposition auf der Lieferzeile.',
    Help = 'Steuert, ob der physische Merkmalwert der Handling Unit (z.B. Lot-Nummer, MHD) den Wert aus dem Merkmalsatz der Lieferdisposition überschreibt.'
           || ' Bei "Ja" (Standard) gewinnt der HU-Wert — das ist das bisherige Verhalten.'
           || ' Bei "Nein" hat der Merkmalsatz der Lieferdisposition Vorrang — z.B. für kundenspezifische Merkmale wie "Herkunft" oder "Positions Nr.".',
    Updated = '2026-04-18 14:00',
    UpdatedBy = 0
WHERE AD_Element_ID = 584759 AND AD_Language = 'de_CH';

-- en_US
UPDATE AD_Element_Trl
SET Description = 'If Yes, the HU attribute value overwrites the shipment schedule''s ASI value on the shipment line.',
    Help = 'Controls whether the physical HU attribute value (e.g., Lot Number, Best Before) overwrites the value from the shipment schedule''s attribute set instance.'
           || ' "Yes" (default) = HU value wins — this is the existing behavior.'
           || ' "No" = the shipment schedule''s ASI value takes precedence — use for customer-specific attributes like "Origin" or "Position Nr.".',
    Updated = '2026-04-18 14:00',
    UpdatedBy = 0
WHERE AD_Element_ID = 584759 AND AD_Language = 'en_US';

-- Propagate to all dependent records (AD_Column, AD_Field, etc.)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584759);
