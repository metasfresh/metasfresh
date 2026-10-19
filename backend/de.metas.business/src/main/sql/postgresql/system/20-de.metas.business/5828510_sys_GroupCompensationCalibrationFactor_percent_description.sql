-- Quantity calibration: the "Kalibrierfaktor" (AD_Element 585522, GroupCompensationCalibrationFactor) is a 100-based percent
-- the user types (80 = 80 %, 100 = unchanged, 0 = quantity 0) - on the calibration rule and on the order line.
-- Description and help say so; the new texts are propagated to every AD_Column / AD_Field (and their _Trl rows) that use this element.

UPDATE AD_Element_Trl SET Description = 'Kalibrierung in Prozent: Die Menge einer Komponente der Kompensationsgruppe wird auf diesen Prozentsatz gesetzt. 100 = unverändert, 0 = Komponentenzeile mit Menge 0.',
    Help = 'Geben Sie einen Prozentwert ein, z. B. 80 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 120 für 120 % oder 100 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Maßeinheit gerundet, danach wird der Prozentsatz angewendet und das Ergebnis kaufmännisch auf die Genauigkeit der Maßeinheit gerundet. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-07 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'de_DE');

UPDATE AD_Element_Trl SET Description = 'Kalibrierung in Prozent: Die Menge einer Komponente der Kompensationsgruppe wird auf diesen Prozentsatz gesetzt. 100 = unverändert, 0 = Komponentenzeile mit Menge 0.',
    Help = 'Geben Sie einen Prozentwert ein, z. B. 80 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 120 für 120 % oder 100 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Masseinheit gerundet, danach wird der Prozentsatz angewendet und das Ergebnis kaufmännisch auf die Genauigkeit der Masseinheit gerundet. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-07 21:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'de_CH');

UPDATE AD_Element_Trl SET Description = 'Calibration in percent: the quantity of a compensation group component is set to this percentage. 100 = unchanged, 0 = component line with quantity 0.',
    Help = 'Enter a percentage, e.g. 80 for 80 % (the component quantity is reduced to 80 %), 120 for 120 %, or 100 for unchanged. The base quantity (template line quantity × number of menus ordered) is rounded to the precision of the unit of measure; then the percentage is applied and the result is rounded half-up to the precision of the unit of measure. With 0, the component line is created with quantity 0. Negative values are not allowed.',
    Updated = TO_TIMESTAMP('2026-10-07 21:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'en_US');

-- other system languages still carry the untranslated base text: refresh them with the new base text
UPDATE AD_Element_Trl SET Description = 'Kalibrierung in Prozent: Die Menge einer Komponente der Kompensationsgruppe wird auf diesen Prozentsatz gesetzt. 100 = unverändert, 0 = Komponentenzeile mit Menge 0.',
    Help = 'Geben Sie einen Prozentwert ein, z. B. 80 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 120 für 120 % oder 100 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Maßeinheit gerundet, danach wird der Prozentsatz angewendet und das Ergebnis kaufmännisch auf die Genauigkeit der Maßeinheit gerundet. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-07 21:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language NOT IN ('de_DE', 'de_CH', 'en_US') AND IsTranslated = 'N' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522);
