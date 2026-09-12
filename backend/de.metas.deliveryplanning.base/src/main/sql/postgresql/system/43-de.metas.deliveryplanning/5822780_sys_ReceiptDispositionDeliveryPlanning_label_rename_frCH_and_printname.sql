-- Completes the window / table label rename of 5822740, which left two things behind.
--
-- 1. fr_CH. 5822740 argued fr_CH was out of scope because its row "only mirrors the old de_DE text as
--    an unmaintained fallback". That contradicts the fr_CH CONVENTION this delivery-planning change set
--    states once, in 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql, and
--    that 5822770 follows: an fr_CH row carries the en_US text with IsTranslated='N', because German in
--    a French row is unusable rather than merely untranslated, and 'N' keeps the row visible to whoever
--    eventually translates it. Leaving fr_CH alone is not the neutral option - it is the defect.
--    fr_CH is an active system language (AD_Language.IsActive='Y', IsSystemLanguage='Y'), and after
--    5822740 a Swiss-French session's menu tree and window title read "Wareneingangslogistik" - a name
--    that now exists nowhere else in the system, in any language, so a support call about it cannot be
--    matched to the entry the user is pointing at. AD_Table_Trl[fr_CH] is not even the German fallback
--    that comment describes: it holds the stale ENGLISH "Receipt Logistics" that 5822460 seeded.
--
-- 2. PrintName. 5822740 moved Name on all four AD_Element_Trl rows of 585424 but left PrintName on the
--    old wording, so the element now reads "Wareneingangsdisposition inkl. Lieferplanung" on screen and
--    "Wareneingangslogistik" wherever PrintName is used. Same reasoning 5822770 applies to element
--    581958: PrintName moves with Name, or the retired wording survives on printed output.
--
-- AD_Element 585424 drives AD_Window 542190 and AD_Menu 542359 and nothing else (the impact query in
-- 5822740 returns exactly those two rows), so it is still safe to mutate directly.
-- AD_Table 542644 is self-owned like AD_Process - no AD_Element_ID - so its _Trl row is set directly.

-- Part 1: AD_Element 585424 - PrintName on the three already-renamed rows.
UPDATE AD_Element_Trl SET PrintName='Wareneingangsdisposition inkl. Lieferplanung', Updated=TO_TIMESTAMP('2026-09-04 15:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585424 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET PrintName='Wareneingangsdisposition inkl. Lieferplanung', Updated=TO_TIMESTAMP('2026-09-04 15:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585424 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET PrintName='Receipt Disposition including Delivery Planning', Updated=TO_TIMESTAMP('2026-09-04 15:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585424 AND AD_Language='en_US'
;

-- Part 2: AD_Element 585424 - fr_CH takes the en_US text, IsTranslated='N' (the change set's convention).
UPDATE AD_Element_Trl trl
   SET Name         = en.Name,
       PrintName    = en.PrintName,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-04 15:00:03','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Element_Trl en
 WHERE en.AD_Element_ID = trl.AD_Element_ID
   AND en.AD_Language   = 'en_US'
   AND trl.AD_Language  = 'fr_CH'
   AND trl.AD_Element_ID = 585424
;

-- Propagate into AD_Element (base), AD_Window / AD_Window_Trl, AD_Menu / AD_Menu_Trl.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585424)
;

-- Part 3: AD_Table 542644 (RV_ReceiptDisposition_DeliveryPlanning) - fr_CH takes the en_US text.
UPDATE AD_Table_Trl trl
   SET Name         = en.Name,
       Description  = en.Description,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-04 15:00:04','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Table_Trl en
 WHERE en.AD_Table_ID = trl.AD_Table_ID
   AND en.AD_Language  = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Table_ID = 542644
;
