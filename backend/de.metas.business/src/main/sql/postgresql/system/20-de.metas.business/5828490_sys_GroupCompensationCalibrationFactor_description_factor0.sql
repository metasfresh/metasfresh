-- Quantity calibration: correct the description of the "Kalibrierfaktor" element (585522, GroupCompensationCalibrationFactor).
-- A calibration factor of 0 keeps the component line and creates it with quantity 0; it no longer leaves the component out.
-- The new text is propagated to every AD_Column / AD_Field (and their _Trl rows) that use this element.

UPDATE AD_Element_Trl SET Description = 'Faktor, mit dem die Menge einer Komponente der Kompensationsgruppe multipliziert wird. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt.',
    Updated = TO_TIMESTAMP('2026-10-07 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'de_DE');

UPDATE AD_Element_Trl SET Description = 'Faktor, mit dem die Menge einer Komponente der Kompensationsgruppe multipliziert wird. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt.',
    Updated = TO_TIMESTAMP('2026-10-07 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'de_CH');

UPDATE AD_Element_Trl SET Description = 'Factor the quantity of a compensation group component is multiplied by. With 0, the component line is created with quantity 0.',
    Updated = TO_TIMESTAMP('2026-10-07 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'en_US');

-- other system languages still carry the untranslated base text: refresh them with the new base text
UPDATE AD_Element_Trl SET Description = 'Faktor, mit dem die Menge einer Komponente der Kompensationsgruppe multipliziert wird. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt.',
    Updated = TO_TIMESTAMP('2026-10-07 14:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language NOT IN ('de_DE', 'de_CH', 'en_US') AND IsTranslated = 'N' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522);
