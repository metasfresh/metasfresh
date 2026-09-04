-- The second element of the rename. 5822460 seeded TWO elements for this view; 5822740 renamed only
-- 585424 (AD_Window 542190 + AD_Menu 542359) and left 585423 - the element behind AD_Column 593472
-- (RV_ReceiptDisposition_DeliveryPlanning_ID) and AD_Tab 549491 - on the retired name
-- "Wareneingangslogistik" / "Receipt Logistics", which after 5822740 exists nowhere else in the
-- system in any language. AD_Field 784923 inherits from the column (its AD_Name_ID is NULL), so it
-- carries the old name too.
--
-- This is not the window title: DefaultDocumentDescriptorLoader builds the root caption from the
-- WINDOW's name, and a tab caption only becomes a caption for INCLUDED tabs, of which this
-- single-tab window has none. So nothing a dispatcher sees changes here. It is renamed anyway for
-- the same reason the title was: the system should not carry two names for one thing, and the
-- records are visible in the System-Administrator maintenance windows.
--
-- Impact query for 585423 (AD_Column / AD_Field.AD_Name_ID / AD_Window / AD_Tab / AD_Menu /
-- AD_Process_Para / AD_UI_Element.AD_Name_ID / WEBUI_KPI_Field) returns exactly AD_Column 593472 and
-- AD_Tab 549491. The new wording is correct in both, so the shared element is mutated directly - no
-- fork needed.
--
-- Base language is German, so the base column carries German and en_US is the translation override.
-- de_CH mirrors de_DE (no szett in the new name). fr_CH takes the en_US text with IsTranslated='N',
-- per the fr_CH CONVENTION stated once for this change set in
-- 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql. PrintName moves with
-- Name on every row - all four currently hold Name = PrintName.

UPDATE AD_Element_Trl SET Name='Wareneingangsdisposition inkl. Lieferplanung', PrintName='Wareneingangsdisposition inkl. Lieferplanung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 16:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585423 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Wareneingangsdisposition inkl. Lieferplanung', PrintName='Wareneingangsdisposition inkl. Lieferplanung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 16:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585423 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Receipt Disposition including Delivery Planning', PrintName='Receipt Disposition including Delivery Planning', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-04 16:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585423 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl trl
   SET Name         = en.Name,
       PrintName    = en.PrintName,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-04 16:00:03','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Element_Trl en
 WHERE en.AD_Element_ID = trl.AD_Element_ID
   AND en.AD_Language   = 'en_US'
   AND trl.AD_Language  = 'fr_CH'
   AND trl.AD_Element_ID = 585423
;

-- Propagate into AD_Element (base), AD_Column / AD_Column_Trl, AD_Field / AD_Field_Trl,
-- AD_Tab / AD_Tab_Trl.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585423)
;
