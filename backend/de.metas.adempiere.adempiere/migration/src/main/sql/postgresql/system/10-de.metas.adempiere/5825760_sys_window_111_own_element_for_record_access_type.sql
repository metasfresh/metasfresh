-- Roles window (AD_Window 111) - give the record-access type field its OWN element, and restore the
-- shared AD_Element 600 (Type / Art) to its pre-5825740 text.
--
-- WHY THE SHAPE OF THE FIX CHANGES.
-- Exactly ONE field needed a Description in window 111: AD_Field 580151 on tab 541756 "Role Record
-- Access Config" (column AD_Role_Record_Access_Config.Type). That field carries no AD_Name_ID, so it
-- inherited shared AD_Element 600. 5825740 and 5825750 therefore rewrote element 600's Description
-- GLOBALLY to serve that one field. Element 600 is bound by 32 AD_Columns across ~36 active window
-- tabs, plus - a reach neither earlier header enumerated - one per-field AD_Name_ID override
-- (AD_Field 583494, window 540680 "Seminar", C_Project.C_ProjectType_ID, a Table Direct FK). Four
-- review rounds failed to find a sentence true in every one of those bindings; the latest finding is
-- that "die moeglichen Werte haengen vom jeweiligen Kontext ab" / "the available values depend on the
-- respective context" is still false on 583494, whose value set is the C_ProjectType master data and
-- depends on nothing.
--
-- A per-field presentation need has a per-field mechanism: AD_Field.AD_Name_ID. This script uses it.
-- A new AD_Element is created for window 111's field alone, written for ITS context (the access-type
-- of a role record-access rule), AD_Field 580151 is pointed at it, and the propagation is run so the
-- field's own AD_Field_Trl rows carry the new text. Nothing outside window 111 is affected.
--
-- ELEMENT 600 IS RESTORED to its pre-5825740 Description in de_DE, de_CH, en_US, en_GB and it_CH,
-- together with its pre-5825740 IsTranslated flags (de_DE 'N', de_CH 'N', en_US 'Y', en_GB 'N',
-- it_CH 'N'). fr_CH was never touched by 5825740/5825750 and is not touched here. No Name and no Help
-- is changed, here or by the two reverted scripts.
--   The restored sentence "Type of Validation (SQL, Java Script, Java Language)" describes
--   AD_Val_Rule.Type and is therefore wrong in 35 of element 600's 36 AD_Column bindings, and its
--   de_DE Description is empty. That defect is PRE-EXISTING - it predates this issue and this stack -
--   and is deliberately NOT fixed here: owning it cost four review rounds and is still not correct.
--   Re-wording a 33-binding shared element is its own piece of work and should be raised as a
--   follow-up issue, not carried by a window-111 presentation sweep.
--
-- Standing exclusions carried forward from the earlier scripts of this stack (unaffected by this
-- change, restated for completeness):
--   * AD_Element 405's en_US Name ("readonly") stays unchanged (stated at 5825710:15-16).
--   * German text sitting in non-German-language rows (e.g. Name 'Art' in en_GB / it_CH) is a
--     separate, pre-existing content defect and is not addressed here (stated at 5825700:18-20).
--   * AD_Element 144 (Workflow) and 469 (Name) legitimately have en_US Name = de_DE Name: the
--     English term genuinely equals the German term, so that match is correct, not a gap. Any
--     completeness query over window 111 must carve those two out rather than invent a German word.
--
-- Statement order is load-bearing: AD_Field 580151 is repointed to the new element BEFORE element 600
-- is reverted, so element 600's propagation (which skips fields carrying an AD_Name_ID) can no longer
-- reach it.

-- ---------------------------------------------------------------------------------------------
-- 1. The new, window-111-specific element. Base row is German, per the base-language convention.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, EntityType, Name, PrintName, Description)
VALUES (585484 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'AD_Role_Record_Access_Config_Type', 'D', 'Art der Zugriffsregel', 'Art der Zugriffsregel', 'Legt fest, ob diese Zugriffsregel der Rolle für eine DB-Tabelle oder für eine Geschäftspartner-Hierarchie gilt.');

-- Skeleton translation rows for every active system language (de_CH, de_DE, en_US, fr_CH).
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585484
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- de_DE / de_CH: the German text is final. de_CH equals de_DE (no 'ß' in it, so no Swiss variant).
UPDATE AD_Element_Trl SET Name='Art der Zugriffsregel', PrintName='Art der Zugriffsregel', Description='Legt fest, ob diese Zugriffsregel der Rolle für eine DB-Tabelle oder für eine Geschäftspartner-Hierarchie gilt.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 14:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585484 AND AD_Language IN ('de_DE','de_CH');

-- en_US.
UPDATE AD_Element_Trl SET Name='Access rule type', PrintName='Access rule type', Description='Determines whether this access rule of the role applies to a database table or to a business partner hierarchy.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 14:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585484 AND AD_Language='en_US';

-- fr_CH keeps the seeded row (German base text, IsTranslated='N'): authoring French is outside this
-- sweep, exactly as element 600's empty fr_CH Description was left alone by 5825740. This script runs
-- no propagation for fr_CH, but the DB's own after-migration translation sync
-- (after_migration_sync_translations, all elements / all languages) does push that seeded row onto
-- AD_Field_Trl 580151's fr_CH row, replacing the 'Règle' label the field inherited from element 600.
-- That is the standard state of every newly created element's fr_CH row, and 'Règle' ("rule") was
-- itself element 600's AD_Val_Rule-era wording rather than a translation of this field.

-- ---------------------------------------------------------------------------------------------
-- 2. Point the field at the new element and propagate.
-- ---------------------------------------------------------------------------------------------
UPDATE AD_Field SET AD_Name_ID=585484, Updated=TO_TIMESTAMP('2026-09-22 14:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=580151;

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585484, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585484, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585484, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585484, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585484, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585484, 'en_US');

-- Rebuild the field's AD_Element_Link row, which still points at element 600.
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580151;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(580151);

-- ---------------------------------------------------------------------------------------------
-- 3. Restore AD_Element 600 to its pre-5825740 state (Description + IsTranslated only).
-- ---------------------------------------------------------------------------------------------
UPDATE AD_Element_Trl SET Description='', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-22 14:00:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language='de_DE';
UPDATE AD_Element_Trl SET Description='Type of Validation (SQL, Java Script, Java Language)', IsTranslated='N', Updated=TO_TIMESTAMP('2026-09-22 14:00:31', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language IN ('de_CH','en_GB','it_CH');
UPDATE AD_Element_Trl SET Description='Type of Validation (SQL, Java Script, Java Language)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 14:00:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language='en_US';

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
