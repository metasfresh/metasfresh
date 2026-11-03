-- Roles window (AD_Window 111) presentation sweep - the tabs that render WITHOUT UI sections.
--
-- Scope correction to 5825670: that script defined its population as the fields rendered in
-- window 111 "via the AD_UI_* chain". Window 111 has three active tabs with zero AD_UI_Section
-- rows - 541101 Notification Groups, 541756 Role Record Access Config, 547620 Mobile Application
-- Role Access - and on a section-less tab AD_Field.IsDisplayed / IsDisplayedGrid governs
-- rendering, so those tabs were never enumerated. The full rendered set of window 111 is the
-- AD_UI_* chain (plus AD_UI_ElementField) UNION the AD_Field path for every tab of the window
-- having no AD_UI_Section: 85 elements, of which 4 are reachable only through the field path
-- (600, 2755, 543947, 584034). 2755 was already complete; the other three are completed here.
--
-- Method is unchanged from 5825670: text is authored per ELEMENT, not per field - de_DE, de_CH
-- and en_US Name and Description together - so every window binding the same element benefits,
-- and both propagation functions are called once per language per element. Descriptions say what
-- the field does for a role administrator, because Help never reaches the client. Help is left
-- untouched.
--
-- AD_Element 600 (Type / Art) is shared: 32 AD_Columns and 36 active window tabs bind it
-- (AD_Val_Rule, AD_Process, EXP_FormatLine, GL_JournalLine, AD_AttachmentEntry, HR_Concept, ...),
-- so its text must read correctly in all of them. Its de_DE Description was empty and its
-- de_CH / en_US / en_GB / it_CH Descriptions carried "Type of Validation (SQL, Java Script, Java
-- Language)" - a sentence about AD_Val_Rule, i.e. wrong in 35 of the 36 bindings. It is replaced
-- by a generic sentence that is true for every binding, in the same style the sweep already used
-- for the other high-fan-out generic elements (275 Description, 469 Name). en_GB and it_CH are
-- corrected here as well because they carry that very sentence; fr_CH keeps its empty Description
-- (authoring French is outside this sweep). Element 600's Names are already correct
-- (de 'Art' / en 'Type') and are not touched.
--
-- Standing exclusions carried forward from the earlier scripts of this stack:
--   * AD_Element 405's en_US Name ("readonly") stays unchanged (stated at 5825710:15-16).
--   * German text sitting in non-German-language rows (e.g. Name 'Art' in en_GB / it_CH) is a
--     separate, pre-existing content defect and is not addressed here (stated at 5825700:18-20).
--   * AD_Element 144 (Workflow) and 469 (Name) legitimately have en_US Name = de_DE Name: the
--     English term genuinely equals the German term, so that match is correct, not a gap. Any
--     completeness query over window 111 must carve those two out rather than invent a German word.

-- AD_Element 543947 (AD_NotificationGroup_ID) - field 564077 on tab 541101 "Notification Groups";
-- also bound by AD_User_NotificationGroup, AD_NotificationGroup, AD_NotificationGroup_CC
-- (windows 53100 "Mein Profil", 540427, 541870).
UPDATE AD_Element_Trl SET Name='Benachrichtigungsgruppe', Description='Gruppe zusammengehöriger Benachrichtigungen, die einer Rolle oder einem Benutzer gemeinsam zugeordnet wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=543947 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Notification group', Description='Group of related notifications assigned together to a role or a user.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=543947 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543947, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543947, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543947, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543947, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543947, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543947, 'en_US');

-- AD_Element 584034 (IsAllowAllActions) - field 754208 on tab 547620 "Mobile Application Role
-- Access"; also bound by window 541827, the stand-alone Mobile Application Role Access window.
UPDATE AD_Element_Trl SET Name='Alle Aktionen erlauben', Description='Die Rolle darf alle Aktionen dieser mobilen Anwendung ausführen, ohne dass einzelne Aktionen freigegeben werden müssen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584034 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow all Actions', Description='The role may perform every action of this mobile application without individual actions being granted.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584034 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584034, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584034, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584034, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584034, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584034, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584034, 'en_US');

-- AD_Element 600 (Type) - field 580151 "Art" on tab 541756 "Role Record Access Config".
-- Description only; see the header note on the 36 bindings and on en_GB / it_CH / fr_CH.
UPDATE AD_Element_Trl SET Description='Art des Eintrags; die möglichen Werte hängen vom jeweiligen Fenster ab.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Type of the record; the available values depend on the respective window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language='en_US';
-- en_GB and it_CH: the wrong AD_Val_Rule sentence is replaced, but IsTranslated stays as it is -
-- their Name is still the German 'Art' (the standing non-German-row exclusion), so these rows are
-- not a completed translation.
UPDATE AD_Element_Trl SET Description='Type of the record; the available values depend on the respective window.', Updated=TO_TIMESTAMP('2026-09-22 12:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language IN ('en_GB','it_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'en_US');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'en_GB');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'en_GB');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'it_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'it_CH');
