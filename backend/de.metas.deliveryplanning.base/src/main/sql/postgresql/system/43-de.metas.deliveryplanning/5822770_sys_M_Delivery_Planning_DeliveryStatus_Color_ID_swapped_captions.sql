-- AD_Element 581958 (M_Delivery_Planning.DeliveryStatus_Color_ID, base name "Lieferstatus") -- the
-- delivery-planning grid's first column, and the one this change set converts from a stored column to
-- a derived one (5822720 / 5822730). Its de_CH and fr_CH captions are swapped: de_CH holds the ENGLISH
-- text and fr_CH holds the GERMAN one, so a Swiss-German session reads "Delivery Status" and a
-- Swiss-French session reads "Lieferstatus". Verified on the delivery-planning stack before this script:
--
--   de_DE  Lieferstatus     IsTranslated='Y'   (correct)
--   en_US  Delivery Status  IsTranslated='N'   (correct)
--   de_CH  Delivery Status  IsTranslated='N'   (wrong -- English)
--   fr_CH  Lieferstatus     IsTranslated='Y'   (wrong -- German, and flagged as translated)
--
-- Pre-dates this change set: the rows were seeded by 5673530 and 5723970, and no script in this change
-- set writes 581958. It is corrected here because this change set is already editing that very column.
--
-- de_CH takes the German caption, matching de_DE, with IsTranslated='Y' -- the same treatment every
-- other element in this change set gives the de_DE/de_CH pair.
--
-- fr_CH follows the fr_CH CONVENTION stated once for this whole delivery-planning change set in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: no French wording exists
-- for the elements this change set touches and none is being commissioned, so an fr_CH row carries the
-- en_US text with IsTranslated='N' -- English is at least readable to a French-speaking user where the
-- seeded German copy is not, and 'N' keeps the row visible to whoever eventually translates it.
-- Leaving fr_CH untouched is NOT the neutral option here: it would leave German sitting in a French
-- row, which is the defect, not the absence of a fix. 5822330 applied this same rule to the four
-- delivery-planning quantity elements.
--
-- PrintName moves with Name on both rows: all four rows of this element currently hold Name = PrintName,
-- and leaving PrintName behind would keep the swapped wording on printed documents after the screen
-- label is fixed. en_US is left alone -- its text is already correct.

UPDATE AD_Element_Trl
   SET Name         = 'Lieferstatus',
       PrintName    = 'Lieferstatus',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-09-04 11:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 581958 AND AD_Language = 'de_CH'
;

UPDATE AD_Element_Trl trl
   SET Name         = en.Name,
       PrintName    = en.PrintName,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-04 11:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Element_Trl en
 WHERE en.AD_Element_ID = trl.AD_Element_ID
   AND en.AD_Language   = 'en_US'
   AND trl.AD_Language  = 'fr_CH'
   AND trl.AD_Element_ID = 581958
;

-- Propagate the corrected element text down to AD_Element (base), AD_Column_Trl and AD_Field_Trl.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581958);
