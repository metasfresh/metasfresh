-- German names (de_DE, de_CH) for the AD_Ref_List values of the compensation group lists.
-- de_DE and de_CH still carried the English text (IsTranslated='N'). en_US keeps its English text (except for the
-- schema line types, see below) and is marked as translated. fr_CH is left unchanged. Where the base language is
-- German, the base AD_Ref_List.Name is synced too.
--
-- AD_Reference 540758 GroupCompensationType (C_OrderLine.GroupCompensationType, "Preisminderung Art"):
--    541326 D  "Discount"           -> "Rabatt"
--    541325 S  "Surcharge"          -> "Zuschlag"
-- AD_Reference 540759 GroupCompensationAmtType (C_OrderLine.GroupCompensationAmtType, "Preisminderung Betrag Typ"):
--    541327 P  "Percent"            -> "Prozent"
--    541328 Q  "Price and Quantity" -> "Preis und Menge"
-- AD_Reference 540836 C_CompensationGroup_SchemaLine_Type (C_CompensationGroup_SchemaLine.Type):
--    541594 F  "Flatrate"           -> "Vertrag",       en_US "Contract"
--              (the schema line applies if the group contains lines with the line's contract conditions)
--    541593 R  "Revenue"            -> "Umsatzstaffel", en_US "Revenue Breaks"
--              (the schema line applies if the net amount of the group's regular lines is between this line's
--               break value and that of the next revenue-break line)

-- 541326 D Discount
UPDATE AD_Ref_List_Trl
SET Name         = 'Rabatt',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541326
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- 541325 S Surcharge
UPDATE AD_Ref_List_Trl
SET Name         = 'Zuschlag',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541325
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- 541327 P Percent
UPDATE AD_Ref_List_Trl
SET Name         = 'Prozent',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541327
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- 541328 Q Price and Quantity
UPDATE AD_Ref_List_Trl
SET Name         = 'Preis und Menge',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541328
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- 541594 F Flatrate
UPDATE AD_Ref_List_Trl
SET Name         = 'Vertrag',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541594
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- 541593 R Revenue
UPDATE AD_Ref_List_Trl
SET Name         = 'Umsatzstaffel',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:06', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541593
  AND AD_Language IN ('de_DE', 'de_CH')
;

-- en_US keeps the existing English names; they are final, so mark them as translated
UPDATE AD_Ref_List_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:07', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID IN (541325, 541326, 541327, 541328)
  AND AD_Language = 'en_US'
;
-- en_US of the schema line types: what the values do
UPDATE AD_Ref_List_Trl
SET Name         = 'Contract',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:08', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541594
  AND AD_Language = 'en_US'
;
UPDATE AD_Ref_List_Trl
SET Name         = 'Revenue Breaks',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-09 10:00:09', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Ref_List_ID = 541593
  AND AD_Language = 'en_US'
;

UPDATE AD_Ref_List base
SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy
FROM AD_Ref_List_Trl trl
WHERE trl.AD_Ref_List_ID = base.AD_Ref_List_ID
  AND base.AD_Ref_List_ID IN (541325, 541326, 541327, 541328, 541593, 541594)
  AND trl.AD_Language = getBaseLanguage()
;
