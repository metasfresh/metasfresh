-- me03 #30443 — F01010.4 — fix AD_Field_Trl propagation for the resolved-override-account
-- display field 781219 (element 585025).
--
-- 5808720 seeded AD_Field_Trl (field 781219) with the SAME Updated timestamp as AD_Element_Trl
-- (element 585025), so update_FieldTranslation_From_AD_Name_Element's guard `f_trl.updated <> e_trl.updated`
-- permanently no-ops → the field's _Trl rows kept the seeded German Name in ALL languages
-- (en_US wrongly showed "Überschreibungskonto (aufgelöst)" instead of "Override account (resolved)").
-- Append-only fix: copy the element's translated Name/Description/Help directly into the field _Trl,
-- bypassing the timestamp guard.

UPDATE AD_Field_Trl ft
SET    Name         = et.Name,
       Description  = et.Description,
       Help         = et.Help,
       IsTranslated = et.IsTranslated,
       Updated      = TO_TIMESTAMP('2026-06-18 15:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
FROM   AD_Element_Trl et
WHERE  et.AD_Element_ID = 585025 /*From ID Server*/
  AND  ft.AD_Field_ID   = 781219 /*From ID Server*/
  AND  ft.AD_Language   = et.AD_Language
  AND  et.IsTranslated  = 'Y';
