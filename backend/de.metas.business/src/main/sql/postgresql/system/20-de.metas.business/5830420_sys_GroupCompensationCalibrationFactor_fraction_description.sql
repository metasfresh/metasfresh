-- Quantity calibration: the "Kalibrierfaktor" (AD_Element 585522, GroupCompensationCalibrationFactor) is now a factor the user types
-- (0.8 = 80 %, 1 = unchanged, 0 = quantity 0) instead of a 100-based percent - on the calibration rule and on the order line.
-- Description and help say so; the new texts are propagated to every AD_Column / AD_Field (and their _Trl rows) that use this element.

UPDATE AD_Element_Trl SET Description = 'Kalibrierfaktor: Die Menge einer Komponente der Kompensationsgruppe wird mit diesem Faktor multipliziert. 0,8 = 80 %, 1 = unverändert, 0 = Komponentenzeile mit Menge 0.',
    Help = 'Geben Sie einen Faktor ein, z. B. 0,8 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 1,2 für 120 % oder 1 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Maßeinheit gerundet, danach mit dem Faktor multipliziert und das Ergebnis auf die Genauigkeit der Maßeinheit aufgerundet; eine von 0 verschiedene Ausgangsmenge ergibt daher mit einem Faktor über 0 nie Menge 0. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-20 22:02:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'de_DE');

UPDATE AD_Element_Trl SET Description = 'Kalibrierfaktor: Die Menge einer Komponente der Kompensationsgruppe wird mit diesem Faktor multipliziert. 0,8 = 80 %, 1 = unverändert, 0 = Komponentenzeile mit Menge 0.',
    Help = 'Geben Sie einen Faktor ein, z. B. 0,8 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 1,2 für 120 % oder 1 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Masseinheit gerundet, danach mit dem Faktor multipliziert und das Ergebnis auf die Genauigkeit der Masseinheit aufgerundet; eine von 0 verschiedene Ausgangsmenge ergibt daher mit einem Faktor über 0 nie Menge 0. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-20 22:02:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'de_CH');

UPDATE AD_Element_Trl SET Description = 'Calibration factor: the quantity of a compensation group component is multiplied by this factor. 0.8 = 80 %, 1 = unchanged, 0 = component line with quantity 0.',
    Help = 'Enter a factor, e.g. 0.8 for 80 % (the component quantity is reduced to 80 %), 1.2 for 120 %, or 1 for unchanged. The base quantity (template line quantity × number of menus ordered) is rounded to the precision of the unit of measure; then it is multiplied by the factor and the result is rounded up to the precision of the unit of measure, so a base quantity other than 0 never gives quantity 0 with a factor above 0. With 0, the component line is created with quantity 0. Negative values are not allowed.',
    Updated = TO_TIMESTAMP('2026-10-20 22:02:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'en_US');

-- other system languages still carry the untranslated base text: refresh them with the new base text
UPDATE AD_Element_Trl SET Description = 'Kalibrierfaktor: Die Menge einer Komponente der Kompensationsgruppe wird mit diesem Faktor multipliziert. 0,8 = 80 %, 1 = unverändert, 0 = Komponentenzeile mit Menge 0.',
    Help = 'Geben Sie einen Faktor ein, z. B. 0,8 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 1,2 für 120 % oder 1 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Maßeinheit gerundet, danach mit dem Faktor multipliziert und das Ergebnis auf die Genauigkeit der Maßeinheit aufgerundet; eine von 0 verschiedene Ausgangsmenge ergibt daher mit einem Faktor über 0 nie Menge 0. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-20 22:02:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language NOT IN ('de_DE', 'de_CH', 'en_US') AND IsTranslated = 'N' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522);
