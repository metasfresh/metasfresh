-- Window 111 (Roles) presentation sweep - the tab CAPTIONS missed by 5825670/5825740.
--
-- Those scripts defined their population as the *fields* rendered in window 111, so the
-- tab-caption elements themselves (AD_Tab.AD_Element_ID) were never enumerated. Result: 7 tabs
-- still show English text in a German UI (5 have English sitting in the German base; 2 have a
-- German base but an English de_CH row). REQUIREMENTS scope is window 111 "in its entirety", and
-- the tab labels are part of what the user sees - so they are completed here.
--
-- Method is unchanged from 5825740: text is authored per ELEMENT into AD_Element_Trl (de_DE,
-- de_CH, en_US) and the two propagation functions write the base AD_Element row and every
-- dependent _Trl table (AD_Tab_Trl). The base AD_Element / AD_Tab_Trl rows are never hand-edited.
-- fr_CH keeps its seeded copy (IsTranslated='N'), consistent with the rest of this stack. All 7
-- caption elements are caption-only (each binds a single tab / its surrogate-key column), so there
-- is no cross-window fan-out. Descriptions are NULL on all 7 and are left untouched.
--
-- Sub-class A - English text was sitting in the German base (no German at all):
--   576597 -> Datensatz-Zugriff (Konfiguration)  / Record Access Config             (tab 541756)
--   579926 -> Tabellen-Organisations-Zugriff     / Table Organization Access        (tab 544582)
--   573597 -> Benachrichtigungsgruppen           / Notification Groups              (tab 541101)
--   583313 -> Mobile-Anwendung Rollenzugriff     / Mobile Application Role Access    (tab 547620)
--   584032 -> Mobile-Anwendung Aktionszugriff    / Mobile Application Action Access  (tab 548440)
-- Sub-class B - German base already correct, only the de_CH row carried English:
--   573461 -> de_CH Berechtigung Anfrage    / en_US Permission Requests             (tab 53316)
--   573538 -> de_CH Berechtigung verweigert / en_US Permissions not granted         (tab 53317)

-- 576597 -------------------------------------------------------------------------------
UPDATE AD_Element_Trl SET Name='Datensatz-Zugriff (Konfiguration)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=576597 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Record Access Config', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=576597 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576597, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576597, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576597, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576597, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576597, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576597, 'en_US');

-- 579926 -------------------------------------------------------------------------------
UPDATE AD_Element_Trl SET Name='Tabellen-Organisations-Zugriff', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=579926 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Table Organization Access', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=579926 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(579926, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579926, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(579926, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579926, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(579926, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579926, 'en_US');

-- 573597 -------------------------------------------------------------------------------
UPDATE AD_Element_Trl SET Name='Benachrichtigungsgruppen', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=573597 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Notification Groups', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=573597 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573597, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573597, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573597, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573597, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573597, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573597, 'en_US');

-- 583313 -------------------------------------------------------------------------------
UPDATE AD_Element_Trl SET Name='Mobile-Anwendung Rollenzugriff', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=583313 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Mobile Application Role Access', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=583313 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583313, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583313, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583313, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583313, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583313, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583313, 'en_US');

-- 584032 -------------------------------------------------------------------------------
UPDATE AD_Element_Trl SET Name='Mobile-Anwendung Aktionszugriff', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=584032 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Mobile Application Action Access', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=584032 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584032, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584032, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584032, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584032, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584032, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584032, 'en_US');

-- 573461 (de_CH carried English "Permission Requests"; de_DE base already German) --------
UPDATE AD_Element_Trl SET Name='Berechtigung Anfrage', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=573461 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=573461 AND AD_Language IN ('de_DE','en_US');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573461, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573461, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573461, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573461, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573461, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573461, 'en_US');

-- 573538 (de_CH carried English "Permissions not granted"; de_DE base already German) ----
UPDATE AD_Element_Trl SET Name='Berechtigung verweigert', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=573538 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Element_ID=573538 AND AD_Language IN ('de_DE','en_US');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573538, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573538, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573538, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573538, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573538, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573538, 'en_US');
