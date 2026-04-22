-- Run mode: SWING_CLIENT

-- Extend AD_Val_Rule 540604 ("HU Label print format options") to cover ALL HU label
-- Jasper processes listed in me03#29310 (10 processes total).
-- Value-pattern matching is replaced with an explicit AD_Process_ID list so renames
-- do not silently drop entries. New label Jaspers must be added here explicitly.
--
-- Included processes (from me03#29310):
--   540370 Wareneingangsetikett LU
--   540412 HU_Labels_SSCC_LU
--   540413 Halbfabrikat Etikett LU
--   540414 Halbfabrikat Etikett TU
--   540415 IFCO Etikett TU
--   540416 IFCO Etikett LU
--   540933 Picking TU Label
--   541195 Label CU
--   584694 Finishedproduct Label
--   585387 SSCC Etikett LU Coop
--
-- Excluded by design (they have their own entry points, not reached through the multi-HU launcher):
--   584980 M_HU_Report_QRCode — separate process class with its own IsPrintPreview
--   585114 M_HU_Report_Print_Labels, 584997 M_HU_Report_LU_Label — single-HU launchers that
--           already preview by default via M_HU_Report_Print_Template

UPDATE AD_Val_Rule
SET Code = 'AD_Process.AD_Process_ID IN (540370, 540412, 540413, 540414, 540415, 540416, 540933, 541195, 584694, 585387)',
    Updated = TO_TIMESTAMP('2026-04-22 10:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy = 0
WHERE AD_Val_Rule_ID = 540604
;
