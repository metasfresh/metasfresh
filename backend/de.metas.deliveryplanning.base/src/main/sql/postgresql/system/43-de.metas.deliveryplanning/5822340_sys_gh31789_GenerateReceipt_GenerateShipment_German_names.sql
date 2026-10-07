-- Give the two delivery-planning generate processes their German name. Neither had a real
-- AD_Process_Trl entry -- every language row (including en_US/en_GB/it_CH) carried the identical
-- untranslated English placeholder ("Generate Goods Receipt" / "Generate Goods Issue").
--
-- Naming rule (owner decision): mirror the process each one is the planning-side counterpart of,
-- in the <Quelle> zu <Ziel> idiom already used by 540557 (M_ReceiptSchedule_Generate_M_InOuts,
-- "Wareneingangsdispo zu Wareneingang").
--
-- GenerateReceipt (585192) -- owner-approved verbatim: "Lieferplanung zu Wareneingang".
--
-- GenerateShipment (585194) -- the plan's draft assumed the direction word "Warenausgang", to be
-- confirmed by grepping the product's own German. That grep contradicts the assumption: querying
-- the concept's own ref-list (M_InOut.MovementType, AD_Reference_ID=189 -- the authoritative
-- source for how this product names each M_InOut direction) gives V+ = "Wareneingang" and
-- C- = "Lieferung", never "Warenausgang" (which occurs exactly once in the whole AD, on the
-- unrelated element "Warenausgangsdatum"). The M_InOut window itself is named "Lieferung"
-- (AD_Window 169) for the very same direction. So the mirrored name uses "Lieferung":
-- "Lieferplanung zu Lieferung".
--
-- en_US is left as-is (it is already a sensible English name for the action; only the German
-- side was missing).

-- 585192 M_Delivery_Planning_GenerateReceipt
UPDATE AD_Process_Trl
   SET Name='Lieferplanung zu Wareneingang', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Process_ID=585192 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Process
   SET Name='Lieferplanung zu Wareneingang',
       Updated=TO_TIMESTAMP('2026-09-03 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Process_ID=585192
;

-- 585194 M_Delivery_Planning_GenerateShipment
UPDATE AD_Process_Trl
   SET Name='Lieferplanung zu Lieferung', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-03 11:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Process_ID=585194 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Process
   SET Name='Lieferplanung zu Lieferung',
       Updated=TO_TIMESTAMP('2026-09-03 11:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Process_ID=585194
;

-- Deactivated 540360 ("Auswahl zu Wareneingängen verarbeiten") sorts adjacent to the live
-- "Wareneingangsdispo zu Wareneingang" (540557) family and reads almost the same as the new
-- "Lieferplanung zu Wareneingang" (585192) -- mark it legacy so nobody mistakes the dead process
-- for the live one. Uses the dominant in-product "<name> - LEGACY" suffix convention (4 of 8
-- existing retired-record cases; all 8 prior cases are windows, this is the first on a process).
-- All 6 AD_Process_Trl rows already carry the identical untranslated text, so the same suffix
-- is appended to each.
UPDATE AD_Process_Trl
   SET Name=Name || ' - LEGACY',
       Updated=TO_TIMESTAMP('2026-09-03 11:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Process_ID=540360
;
UPDATE AD_Process
   SET Name=Name || ' - LEGACY',
       Updated=TO_TIMESTAMP('2026-09-03 11:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Process_ID=540360
;
