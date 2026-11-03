-- Window 111 (Roles): add the missing tab DESCRIPTIONS. The presentation sweep (5825670/5825740)
-- gave the rendered *fields* meaningful Descriptions but never the tab-caption elements, so 8 tabs
-- carry no tooltip - which is exactly why their purpose was unclear. Per REQUIREMENTS (window 111 in
-- its entirety; "the Description is the only text that reaches the client - Help is not shown"), each
-- tab gets a Description saying what it is for, in de_DE/de_CH/en_US.
--
-- Method matches 5825740/5826550: author per ELEMENT into AD_Element_Trl (Description) and let the two
-- propagation functions write the base row + every dependent _Trl table. Base rows are never
-- hand-edited. fr_CH untouched. de_CH == de_DE (no eszett in any string).
--
-- The Updated timestamp is deliberately 12:00:00, DISTINCT from 5826550's date-only 2026-09-25
-- (00:00:00): update_Tab_Translation_From_AD_Element propagates to AD_Tab_Trl only where
-- t_trl.updated <> e_trl.updated. Six of these elements were already touched by 5826550 (names), so a
-- matching timestamp would make the description propagation silently no-op. Do not align the two.
--
-- Fan-out: 573425/576597/573461/573538/573597/579926 are caption-only (single tab), so tab-specific
-- wording is safe. 583313 and 584032 are SHARED by the Role-window tab AND the stand-alone Mobile
-- Application windows 541827/541950 (same concept), so their Descriptions are written ROLE-NEUTRAL to
-- read correctly in every binding (they, too, lacked a Description).

-- 573425  tab 53240 "Enthaltene Rollen" (AD_Role_Included) -----------------------------
UPDATE AD_Element_Trl SET Description='Rollen, deren Berechtigungen in diese Rolle einbezogen werden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=573425 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Roles whose permissions are included in this role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=573425 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573425, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573425, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573425, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573425, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573425, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573425, 'en_US');

-- 576597  tab 541756 "Datensatz-Zugriff (Konfiguration)" (AD_Role_Record_Access_Config) -
-- Describe the two modes WITHOUT quoting the Type option labels: the Type field's list (AD_Reference
-- 540987) is untranslated English in every language ("Table" / "Business Partner Hierarchy"), so a
-- German-quoted label would point at text not on screen. Same fix 5825770 applied to element 585484.
-- (12:00:01 so this reworded row re-propagates over the already-applied 12:00:00.)
UPDATE AD_Element_Trl SET Description='Aktiviert die datensatzbezogene Zugriffssteuerung dieser Rolle - je Tabelle oder entlang der Geschäftspartner-Hierarchie.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=576597 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Enables record-level access control for this role - per table or along the business partner hierarchy.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=576597 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576597, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576597, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576597, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576597, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576597, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576597, 'en_US');

-- 573461  tab 53316 "Berechtigung Anfrage" (AD_Role_PermRequest, all requests) -----------
UPDATE AD_Element_Trl SET Description='Anfragen auf zusätzliche Berechtigungen für diese Rolle.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=573461 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Requests for additional permissions for this role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=573461 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573461, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573461, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573461, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573461, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573461, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573461, 'en_US');

-- 573538  tab 53317 "Berechtigung verweigert" (AD_Role_PermRequest, IsPermissionGranted<>'Y') -
UPDATE AD_Element_Trl SET Description='Berechtigungsanfragen dieser Rolle, die (noch) nicht gewährt wurden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=573538 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Permission requests of this role that have not (yet) been granted.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=573538 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573538, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573538, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573538, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573538, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573538, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573538, 'en_US');

-- 573597  tab 541101 "Benachrichtigungsgruppen" (AD_Role_NotificationGroup) --------------
UPDATE AD_Element_Trl SET Description='Benachrichtigungsgruppen, die dieser Rolle zugeordnet sind.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=573597 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Notification groups assigned to this role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=573597 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573597, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573597, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573597, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573597, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(573597, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573597, 'en_US');

-- 579926  tab 544582 "Tabellen-Organisations-Zugriff" (AD_Role_TableOrg_Access) ----------
UPDATE AD_Element_Trl SET Description='Zugriffsrechte dieser Rolle je Tabelle und Organisation (Lesen, Schreiben, Bericht, Export).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=579926 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='This role''s access per table and organization (read, write, report, export).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=579926 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(579926, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579926, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(579926, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579926, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(579926, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579926, 'en_US');

-- 583313  SHARED (tab 547620 + stand-alone window 541827/tab 547621 + col 589277) - role-neutral -
UPDATE AD_Element_Trl SET Description='Mobile Anwendungen, auf die eine Rolle zugreifen darf.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583313 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Mobile applications a role may access.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583313 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583313, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583313, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583313, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583313, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583313, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583313, 'en_US');

-- 584032  SHARED (tab 548440 + stand-alone window 541950/tab 548439 + col 591114) - role-neutral -
UPDATE AD_Element_Trl SET Description='Aktionen mobiler Anwendungen, die eine Rolle ausführen darf.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584032 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Mobile application actions a role may perform.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-25 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584032 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584032, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584032, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584032, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584032, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584032, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584032, 'en_US');
