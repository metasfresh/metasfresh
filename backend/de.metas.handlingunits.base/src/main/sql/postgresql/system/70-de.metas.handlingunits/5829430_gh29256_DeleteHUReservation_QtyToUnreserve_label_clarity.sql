-- Run mode: SWING_CLIENT

-- F19012 / gh29256: Partial reservation reversal.
-- Clarifies the label of the editable quantity field of the reverse overlay (AD_Process 540985,
-- WEBUI_C_OrderLineSO_Delete_HUReservation): it is the quantity to REVERSE, not the quantity to reserve.
-- Only AD_Process_Para 543346 (QtyToUnreserve) is changed; it has no AD_Element, so Name/_Trl are set directly.
-- The make-reservation parameter (AD_Process_Para 541321, QtyToReserve) stays untouched.
--
-- IDs allocated from the central ID server (idserver.metas.de) on 2026-10-14:
--   AD_MigrationScript 5829430 (file name prefix)

UPDATE AD_Process_Para
SET Name='Zu stornierende Menge', Updated=TO_TIMESTAMP('2026-10-14 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0
WHERE AD_Process_Para_ID=543346;

UPDATE AD_Process_Para_Trl
SET Name='Zu stornierende Menge', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-14 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0
WHERE AD_Process_Para_ID=543346 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl
SET Name='Qty to reverse', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-14 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0
WHERE AD_Process_Para_ID=543346 AND AD_Language='en_US';
