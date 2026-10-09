-- Names of the C_CompensationGroup_SchemaLine.Type list values (AD_Reference 540836), replacing the literal
-- translations set by 5828810 ("Pauschale" / "Umsatz"). What the values do:
--   F (CONTRACT):       the schema line applies if the group contains lines with the line's contract conditions
--   R (REVENUE_BREAKS): the schema line applies if the net amount of the group's regular lines is between this line's
--                       break value and that of the next revenue-break line
--
--    541594 F  de_DE, de_CH, base "Vertrag",       en_US "Contract"
--    541593 R  de_DE, de_CH, base "Umsatzstaffel", en_US "Revenue Breaks"
-- fr_CH unchanged.

-- 541594 F
UPDATE AD_Ref_List_Trl
SET Name         = 'Vertrag',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 17:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541594
  AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Ref_List_Trl
SET Name         = 'Contract',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 17:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541594
  AND AD_Language = 'en_US'
;

-- 541593 R
UPDATE AD_Ref_List_Trl
SET Name         = 'Umsatzstaffel',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 17:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541593
  AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Ref_List_Trl
SET Name         = 'Revenue Breaks',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 17:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541593
  AND AD_Language = 'en_US'
;

UPDATE AD_Ref_List base
SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Ref_List_Trl trl
WHERE trl.AD_Ref_List_ID = base.AD_Ref_List_ID
  AND base.AD_Ref_List_ID IN (541593, 541594)
  AND trl.AD_Language = getBaseLanguage()
;
