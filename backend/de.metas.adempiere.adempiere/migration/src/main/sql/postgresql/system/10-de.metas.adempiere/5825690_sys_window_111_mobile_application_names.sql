-- Follow-up to 5825680 (Roles window / AD_Window 111 presentation sweep).
--
-- 5825680 reverted the de_DE / de_CH Name of AD_Element 583308 (Mobile_Application_ID) and 584031
-- (Mobile_Application_Action_ID) to their English wording, on the premise that a German rename was
-- outside the scope of the window 111 presentation sweep. That premise was wrong: both elements back
-- fields that are rendered in window 111.
--   - Tab 548440 ("Mobile Application Action Access") is section-backed, so the AD_UI_* layer governs
--     display. AD_UI_Element 637302 (AD_Field 754210, element 583308) and 637296 (AD_Field 754206,
--     element 584031) are both IsDisplayed='Y' and IsDisplayedGrid='Y'.
--   - AD_Field 731870 on tab 547620 also renders element 583308.
-- Their de_DE Name therefore equalled their en_US Name inside window 111 - exactly the defect the
-- sweep exists to remove. This script restores the German wording set by 5825670.
--
-- DECLARED PROPAGATION - intended, not collateral. The German label is authored per ELEMENT, not per
-- field (the chosen method of the window 111 sweep), so every record binding these shared elements
-- picks it up. The records that intentionally change caption here are:
--   AD_Window 541826 "Mobile Application"        (AD_Element_ID 583308)
--   AD_Tab    547618 "Mobile Application"        (AD_Element_ID 583308)
--   AD_Tab    548438 "Mobile Application Action" (AD_Element_ID 584031)
--   AD_Menu   542180 "Mobile Application"        (AD_Element_ID 583308)
-- "Mobile Anwendung" / "Aktion der mobilen Anwendung" is the correct German wording for a
-- German-language UI in all four, so no element is forked to decouple them.
--
-- Descriptions and the en_US rows written by 5825670 are left untouched.

-- AD_Element 583308 (Mobile_Application_ID): restore the German Name.
UPDATE AD_Element_Trl SET Name='Mobile Anwendung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 14:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583308 AND AD_Language IN ('de_DE','de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583308, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583308, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583308, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583308, 'de_CH');

-- AD_Element 584031 (Mobile_Application_Action_ID): restore the German Name.
UPDATE AD_Element_Trl SET Name='Aktion der mobilen Anwendung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 14:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584031 AND AD_Language IN ('de_DE','de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584031, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584031, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584031, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584031, 'de_CH');
