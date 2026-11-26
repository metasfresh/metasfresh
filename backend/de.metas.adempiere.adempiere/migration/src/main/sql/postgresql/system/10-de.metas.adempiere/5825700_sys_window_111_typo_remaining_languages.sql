-- Follow-up to 5825680 (Roles window / AD_Window 111). 5825680 corrected the 'Berecih' -> 'Bereich'
-- spelling error on AD_Element 1001369 for de_DE / de_CH only, but the byte-identical German string
-- also sits in the non-German rows of the very field that renders in window 111 (AD_Field 11258
-- "Schreibgeschützt", tab "Organisation Zugriff", whose text comes from AD_Field.AD_Name_ID=1001369):
--   - AD_Element_Trl (1001369, 'fr_CH')
--   - AD_Field_Trl   (11258, 'fr_CH' / 'it_CH' / 'en_GB')
-- fr_CH is an active system language on the affected instances, so a French (CH) login still read the
-- misspelling in window 111. This script removes it from all of those rows.
--
-- fr_CH is corrected on the element and pushed down by the usual propagation. en_GB and it_CH have NO
-- AD_Element_Trl row for element 1001369, so propagation cannot reach their AD_Field_Trl rows; those
-- two are corrected directly on AD_Field_Trl - the only available path, and safe here because no
-- element row exists for those languages that could later overwrite them.
--
-- Deliberately NOT changed here:
--   - AD_Element 405's en_GB / en_US / fr_CH / it_CH rows, which carry the same German string. They
--     are out of scope for this change (element 405's German rows were already corrected by 5825670).
--   - The fact that these rows hold GERMAN text while sitting in non-German languages is a separate,
--     pre-existing defect that is NOT addressed here. Only the misspelling inside the existing string
--     is corrected; nothing is translated, and IsTranslated is left untouched.

-- AD_Element 1001369, fr_CH: correct the spelling error inside the existing (German) text.
UPDATE AD_Element_Trl SET Description='Feld / Eintrag / Bereich ist schreibgeschützt', Updated=TO_TIMESTAMP('2026-09-22 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=1001369 AND AD_Language='fr_CH';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(1001369, 'fr_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1001369, 'fr_CH');

-- AD_Field 11258, en_GB: no AD_Element_Trl row for element 1001369 in this language, so the field row
-- is corrected directly.
UPDATE AD_Field_Trl SET Description='Feld / Eintrag / Bereich ist schreibgeschützt', Updated=TO_TIMESTAMP('2026-09-22 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=11258 AND AD_Language='en_GB';

-- AD_Field 11258, it_CH: same situation as en_GB.
UPDATE AD_Field_Trl SET Description='Feld / Eintrag / Bereich ist schreibgeschützt', Updated=TO_TIMESTAMP('2026-09-22 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=11258 AND AD_Language='it_CH';
