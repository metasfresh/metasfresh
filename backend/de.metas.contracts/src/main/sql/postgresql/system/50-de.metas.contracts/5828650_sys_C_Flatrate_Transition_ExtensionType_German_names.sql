-- German names (de_DE, de_CH) for the AD_Ref_List values of AD_Reference 540843 ExtensionType
-- (column C_Flatrate_Transition.ExtensionType, field "Vertrag autom. verlängern" in window "Vertrags-Übergang").
-- de_DE still carried the English text (IsTranslated='N'); de_CH was already translated, its names are reused:
--    541603 EO  "Extend contract for first period" -> "Erste Vertragsperiode verlängern"
--    541602 EA  "Extend contract for all periods"  -> "Sofort alle Vertragsperioden verlängern"
--               (ExtendAll immediately extends the contract through the whole chain of transitions, see FlatrateBL)
--
-- en_US and fr_CH are left unchanged. Where the base language is German, the base AD_Ref_List.Name is synced too.

-- 541603 EO ExtendOne
UPDATE AD_Ref_List_Trl
SET Name         = 'Erste Vertragsperiode verlängern',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-08 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541603
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- 541602 EA ExtendAll
UPDATE AD_Ref_List_Trl
SET Name         = 'Sofort alle Vertragsperioden verlängern',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-08 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541602
  AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Ref_List base
SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Ref_List_Trl trl
WHERE trl.AD_Ref_List_ID = base.AD_Ref_List_ID
  AND base.AD_Ref_List_ID IN (541602, 541603)
  AND trl.AD_Language = getBaseLanguage()
;
