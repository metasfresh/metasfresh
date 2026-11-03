-- The C_Order_ID field on the receipt-disposition window (AD_Window 542190) read "Auftrag" / en_US
-- "Sales order" -- wrong on a purchase-side window. The view selects only incoming/dropship plannings and
-- receipt schedules, so the order behind every row is a PURCHASE order.
--
-- Cause: AD_Field 784929 had AD_Name_ID NULL and a hand-set Name='Auftrag', so English fell through to the
-- SHARED AD_Element 558 (de_DE/de_CH "Auftrag", en_US "Sales order"), which is correctly named for the sales
-- side and is used by many other tables.
--
-- Fix follows the two receipt-side precedents rather than inventing one:
--
--   AD_Window 541954 "Receipt Schedule Logistics"   AD_Field 754573  AD_Name_ID=572882, AD_UI_Element.Name='Bestellung'
--   AD_Window 540196 "Material Receipt Candidates"  same shape
--
-- AD_Element 572882 is a caption-only element (no ColumnName, EntityType 'D') reading
-- de_DE/de_CH "Bestellungen" / en_US "Purchase Orders". Pointing a THIRD field at it is purely additive: its
-- text is not modified, so the two existing users are unaffected. Shared AD_Element 558 is NOT touched.
--
-- Going through the element (AD_Field.AD_Name_ID) rather than hand-editing AD_Field_Trl.Name is required by
-- metasfresh-designing-windows section 4; the after-migration translation sync then propagates the element's
-- names into AD_Field / AD_Field_Trl (its "via AD_Name" path).
--
-- AD_UI_Element.Name is a design-time label with no element behind it on either precedent, so it is set to the
-- same singular "Bestellung" they use.
--
-- Routing: metasfresh repo only. Window 542190 is created by this change set (migration 5822460), is
-- EntityType 'D' with Overrides_Window_ID NULL, and exists as an AD_Window_ID in no other branch -- so no
-- customer override of it can exist and no customer-repo companion script is needed.

UPDATE AD_Field
   SET AD_Name_ID = 572882,
       Updated    = TO_TIMESTAMP('2026-09-10 20:40:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 100
 WHERE AD_Field_ID = 784929
   AND AD_Name_ID IS DISTINCT FROM 572882
;

UPDATE AD_UI_Element
   SET Name      = 'Bestellung',
       Updated   = TO_TIMESTAMP('2026-09-10 20:40:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_Element_ID = 654696
   AND Name IS DISTINCT FROM 'Bestellung'
;

-- The after-migration translation sync propagates an element's name into the NON-base languages only
-- (de_CH, fr_CH, en_US here) and leaves the BASE-language row untouched -- de_DE is IsBaseLanguage='Y' on this
-- instance. Measured: only 7 of 35181 active de_DE AD_Field_Trl rows diverge from their AD_Field.Name, so the
-- base-language row is kept in step by convention and a divergent one is an anomaly, not a don't-care. Without
-- this the German caption -- the one the users actually read -- would still say "Auftrag" while base Name,
-- de_CH, fr_CH and en_US all said Bestellungen / Purchase Orders.
--
-- The value is SELECTed from AD_Element 572882 rather than hardcoded, so the element remains the single source
-- of truth (metasfresh-designing-windows section 4) and this is completing its propagation, not overriding it.

UPDATE AD_Field_Trl t
   SET Name      = (SELECT e.Name FROM AD_Element e WHERE e.AD_Element_ID = 572882),
       Updated   = TO_TIMESTAMP('2026-09-10 20:40:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE t.AD_Field_ID = 784929
   AND t.AD_Language = 'de_DE'
   AND t.Name IS DISTINCT FROM (SELECT e.Name FROM AD_Element e WHERE e.AD_Element_ID = 572882)
;
