-- Roles window (AD_Window 111) - reword the Description of AD_Element 585484 so it no longer names
-- the option labels of the field's reference list.
--
-- WHY. Element 585484 (created by 5825760) is the window-111-only element behind AD_Field 580151
-- (AD_Role_Record_Access_Config.Type). Its German Description named the two options as "DB-Tabelle"
-- and "Geschaeftspartner-Hierarchie", but those values come from AD_Reference 540987, whose
-- AD_Ref_List_Trl Names are untranslated English in EVERY language ("Table" / "Business Partner
-- Hierarchy"). A German administrator therefore read a tooltip pointing at labels that are not on
-- screen. The Description is reworded to describe WHAT the field selects instead of enumerating the
-- option labels, so it stays correct whether or not the reference list is ever translated.
--   The reference list itself is NOT translated here: AD_Reference 540987 is shared data outside the
--   scope of this window-111 presentation sweep.
--   The en_US Description is reworded in parallel. It did not have the mirror defect (its wording
--   matched the English labels the user actually sees), but the two languages must remain
--   translations of one another, so both are rewritten to the same, label-free sentence.
--   The seeded fr_CH row carries the German base text (IsTranslated='N', the normal state of a newly
--   created element) and is rewritten with it so it does not keep the retired wording.
--
-- THE STATE OF SHARED AD_Element 600 - the true position, correcting 5825760's header.
-- 5825760's header said the re-wording of shared AD_Element 600 "should be raised as a follow-up
-- issue". There is NO such follow-up: no issue has been filed, and filing one needs human approval
-- which has not been given. The factual position is:
--   * 5825760 restored element 600's Description to its pre-existing state - empty in de_DE, and the
--     AD_Val_Rule-specific sentence "Type of Validation (SQL, Java Script, Java Language)" in the
--     other languages.
--   * That text is wrong in most of element 600's ~36 bindings, which span many unrelated windows.
--   * This defect is PRE-EXISTING - it predates this issue and this stack - and is deliberately NOT
--     addressed by this issue.
--   * No follow-up issue has been filed, because filing one requires human approval that has not
--     been given. Nothing here should be read as a promise that such an issue exists.
--
-- Nothing outside AD_Element 585484 (and therefore outside window 111) is touched.

-- ---------------------------------------------------------------------------------------------
-- 1. Reword the Description in every language of element 585484. Name and PrintName stay as they are.
-- ---------------------------------------------------------------------------------------------
UPDATE AD_Element_Trl SET Description='Bestimmt, nach welchem Kriterium diese Zugriffsregel der Rolle die zugreifbaren Datensätze eingrenzt.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585484 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Description='Determines by which criterion this access rule of the role restricts the accessible records.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 15:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585484 AND AD_Language='en_US';

-- fr_CH keeps the German base text and IsTranslated='N' - authoring French remains outside this sweep.
UPDATE AD_Element_Trl SET Description='Bestimmt, nach welchem Kriterium diese Zugriffsregel der Rolle die zugreifbaren Datensätze eingrenzt.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-22 15:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585484 AND AD_Language='fr_CH';

-- ---------------------------------------------------------------------------------------------
-- 2. Propagate, once per language of the one element touched.
-- ---------------------------------------------------------------------------------------------
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585484, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585484, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585484, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585484, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585484, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585484, 'en_US');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585484, 'fr_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585484, 'fr_CH');
