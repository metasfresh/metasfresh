-- Roles window (AD_Window 111) - correct the 'Berecih' -> 'Bereich' spelling error on the SHARED
-- element AD_Element 405 (IsReadOnly), in its en_GB / en_US / it_CH / fr_CH rows.
--
-- Earlier rounds excluded element 405 on the grounds that it is a shared element. That exclusion is
-- wrong for this change: AD_Field 785050 sits on AD_Tab 549493 "Tabellen-Zugriff" of window 111 -
-- the tab this issue creates - and inherits element 405 (AD_Field.AD_Name_ID is NULL, so the text
-- comes from AD_Column 8568 IsReadOnly -> AD_Element 405). The misspelling therefore renders on the
-- new surface this issue delivers, for every non-German login. The German rows (de_DE / de_CH) were
-- already correct.
--
-- Propagation is run per corrected language. AD_Field_Trl 785050 exists for en_US and fr_CH only;
-- en_GB and it_CH have no field row at all, so nothing is left behind there.
--
-- Deliberately NOT changed here (do not re-litigate):
--   - AD_Element 405's en_US *Name*, which renders as the lowercase "readonly". Changing a shared
--     element's label reaches every window that uses it; that is deliberately deferred.
--   - The fact that several of these non-German rows hold GERMAN text. That is a separate,
--     pre-existing content defect. Only the misspelled word inside the string that is already there
--     is corrected - nothing is translated or rewritten, and IsTranslated is left untouched.

UPDATE AD_Element_Trl SET Description='Feld / Eintrag / Bereich ist schreibgeschützt', Updated=TO_TIMESTAMP('2026-09-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=405 AND AD_Language='en_GB';
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(405, 'en_GB');

UPDATE AD_Element_Trl SET Description='Feld / Eintrag / Bereich ist schreibgeschützt', Updated=TO_TIMESTAMP('2026-09-22 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=405 AND AD_Language='en_US';
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(405, 'en_US');

UPDATE AD_Element_Trl SET Description='Feld / Eintrag / Bereich ist schreibgeschützt', Updated=TO_TIMESTAMP('2026-09-22 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=405 AND AD_Language='it_CH';
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(405, 'it_CH');

-- fr_CH is partly French already; only the misspelled word is corrected, the rest of the string is left as-is.
UPDATE AD_Element_Trl SET Description='Champ / entrée / Bereich écrit -Protected', Updated=TO_TIMESTAMP('2026-09-22 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=405 AND AD_Language='fr_CH';
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(405, 'fr_CH');
