-- Follow-up to 5825670 (Roles window / AD_Window 111 presentation sweep). Two corrections.
--
-- 1) The 'Berecih' -> 'Bereich' spelling fix in 5825670 touched AD_Element 405 only. The field that
--    actually renders in window 111 (AD_Field 11258 "Schreibgeschützt", tab "Organisation Zugriff")
--    overrides its text via AD_Field.AD_Name_ID=1001369, so the misspelling was still on screen in
--    the very window the sweep was written for. Element 1001369 is the ONLY element carrying the
--    typo that is reachable as an AD_Name_ID override of a field rendered in window 111; the other
--    eleven elements holding the same string render in other windows (229, 196, 102, 268, 105, 108)
--    or nowhere, and are deliberately out of scope here. The en_GB / en_US / fr_CH / it_CH rows of
--    element 405 carry the same German string and remain untouched - a separate, pre-existing defect.
--
-- 2) 5825670 also renamed AD_Element 583308 (Mobile_Application_ID) and 584031
--    (Mobile_Application_Action_ID) in de_DE / de_CH. That rename is outside window 111 and outside
--    the task's scope, and propagation carried it into AD_Window 541826, AD_Tab 547618 / 548438 and
--    AD_Menu 542180 - renaming an unrelated window and its menu entry. The de_DE / de_CH Name values
--    are reverted here to their pre-5825670 values and the propagation re-run, so those records read
--    their original labels again. The Descriptions and the en_US rows added by 5825670 are kept.

-- AD_Element 1001369 (IsReadOnly override used by AD_Field 11258 in window 111): correct the German
-- spelling error 'Berecih' -> 'Bereich'.
UPDATE AD_Element_Trl SET Description='Feld / Eintrag / Bereich ist schreibgeschützt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=1001369 AND AD_Language IN ('de_DE','de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(1001369, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1001369, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(1001369, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1001369, 'de_CH');

-- AD_Element 583308 (Mobile_Application_ID): revert the de_DE / de_CH Name to its pre-5825670 value.
UPDATE AD_Element_Trl SET Name='Mobile Application', Updated=TO_TIMESTAMP('2026-09-22 11:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583308 AND AD_Language IN ('de_DE','de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583308, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583308, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583308, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583308, 'de_CH');

-- AD_Element 584031 (Mobile_Application_Action_ID): revert the de_DE / de_CH Name to its pre-5825670 value.
UPDATE AD_Element_Trl SET Name='Mobile Application Action', Updated=TO_TIMESTAMP('2026-09-22 11:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584031 AND AD_Language IN ('de_DE','de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584031, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584031, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584031, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584031, 'de_CH');
