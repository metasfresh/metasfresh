-- Quantity calibration: the calibrated component quantity is now rounded up (away from zero) to the UOM precision instead of half-up.
-- Update the help of the "Kalibrierfaktor" element (585522, GroupCompensationCalibrationFactor) accordingly;
-- the new texts are propagated to every AD_Column / AD_Field (and their _Trl rows) that use this element.

UPDATE AD_Element_Trl SET Help = 'Geben Sie einen Prozentwert ein, z. B. 80 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 120 für 120 % oder 100 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Maßeinheit gerundet, danach wird der Prozentsatz angewendet und das Ergebnis auf die Genauigkeit der Maßeinheit aufgerundet; ein Prozentsatz über 0 ergibt daher nie Menge 0. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'de_DE' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'de_DE');

UPDATE AD_Element_Trl SET Help = 'Geben Sie einen Prozentwert ein, z. B. 80 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 120 für 120 % oder 100 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Masseinheit gerundet, danach wird der Prozentsatz angewendet und das Ergebnis auf die Genauigkeit der Masseinheit aufgerundet; ein Prozentsatz über 0 ergibt daher nie Menge 0. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-08 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'de_CH' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'de_CH');

UPDATE AD_Element_Trl SET Help = 'Enter a percentage, e.g. 80 for 80 % (the component quantity is reduced to 80 %), 120 for 120 %, or 100 for unchanged. The base quantity (template line quantity × number of menus ordered) is rounded to the precision of the unit of measure; then the percentage is applied and the result is rounded up to the precision of the unit of measure, so a percentage above 0 never gives quantity 0. With 0, the component line is created with quantity 0. Negative values are not allowed.',
    Updated = TO_TIMESTAMP('2026-10-08 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522, 'en_US');

-- other system languages still carry the untranslated base text: refresh them with the new base text
UPDATE AD_Element_Trl SET Help = 'Geben Sie einen Prozentwert ein, z. B. 80 für 80 % (die Komponentenmenge wird auf 80 % reduziert), 120 für 120 % oder 100 für unverändert. Die Ausgangsmenge (Menge der Vorlagenzeile × bestellte Menüanzahl) wird auf die Genauigkeit der Maßeinheit gerundet, danach wird der Prozentsatz angewendet und das Ergebnis auf die Genauigkeit der Maßeinheit aufgerundet; ein Prozentsatz über 0 ergibt daher nie Menge 0. Bei 0 wird die Komponentenzeile mit Menge 0 angelegt. Negative Werte sind nicht erlaubt.',
    Updated = TO_TIMESTAMP('2026-10-08 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language NOT IN ('de_DE', 'de_CH', 'en_US') AND IsTranslated = 'N' AND AD_Element_ID = 585522;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585522);
