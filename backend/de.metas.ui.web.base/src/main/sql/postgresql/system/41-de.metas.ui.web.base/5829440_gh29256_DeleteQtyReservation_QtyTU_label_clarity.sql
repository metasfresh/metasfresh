-- Run mode: SWING_CLIENT

-- F19012 / gh29256: Partial reservation reversal.
-- Clarifies the label of the editable TU quantity field of the reverse overlay (AD_Process 585589,
-- MD_CockpitV2_DeleteQtyReservation): it is the TU quantity to REVERSE, not the quantity to reserve.
-- Only AD_Process_Para 543342 (QtyTU) is changed. The WebUI caption comes from AD_Process_Para.Name/_Trl,
-- so the shared AD_Element 542490 ("TU Anzahl") and the make overlay parameter 543136 (process 585588)
-- are deliberately left untouched.
--
-- IDs allocated from the central ID server (idserver.metas.de) on 2026-10-14:
--   AD_MigrationScript 5829440 (file name prefix)

UPDATE AD_Process_Para
SET Name='Zu stornierende TU Menge', Updated=TO_TIMESTAMP('2026-10-14 10:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_Para_ID=543342;

UPDATE AD_Process_Para_Trl
SET Name='Zu stornierende TU Menge', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-14 10:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_Para_ID=543342 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para_Trl
SET Name='TU Qty to reverse', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-14 10:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_Para_ID=543342 AND AD_Language='en_US';
