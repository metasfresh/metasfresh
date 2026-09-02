-- Barcode scanner: carry legacy knob values forward into the new mode scheme and retire dead knobs.
-- Order: all carry-forward UPDATEs first, then DELETEs.
-- Missing legacy rows are harmless (WHERE EXISTS guards).

-- Back up AD_SysConfig before the carry-forward UPDATEs and retire DELETEs below.
SELECT backup_table('AD_SysConfig');

-- ============================================================
-- A1. useCamera → mode.camera.enabled (alias: keep useCamera row)
-- ============================================================
UPDATE AD_SysConfig
SET    Value      = COALESCE(
                       (SELECT Value FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.useCamera'),
                       Value  -- keep Task-1 default when legacy row has NULL Value
                   ),
       Updated    = TO_TIMESTAMP('2026-06-13 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 99
WHERE  Name = 'mobileui.frontend.barcodeScanner.mode.camera.enabled'
  AND  EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.useCamera')
;

-- ============================================================
-- A2. offscreenInput.readOnly → mode.hardware.input.readOnly (alias: keep offscreenInput.readOnly row)
-- ============================================================
UPDATE AD_SysConfig
SET    Value      = COALESCE(
                       (SELECT Value FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.offscreenInput.readOnly'),
                       Value  -- keep Task-1 default when legacy row has NULL Value
                   ),
       Updated    = TO_TIMESTAMP('2026-06-13 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 99
WHERE  Name = 'mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly'
  AND  EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.offscreenInput.readOnly')
;

-- ============================================================
-- B3. showInputText: if Value='Y' → set mode.manual.enabled='Y', then DELETE showInputText
-- ============================================================
UPDATE AD_SysConfig
SET    Value      = 'Y',
       Updated    = TO_TIMESTAMP('2026-06-13 09:00:02', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 99
WHERE  Name = 'mobileui.frontend.barcodeScanner.mode.manual.enabled'
  AND  EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.showInputText' AND Value = 'Y')
;

-- DELETE regardless of Value: showInputText is retired unconditionally;
-- only the mode.manual.enabled carry-forward above is conditional on Value='Y'.
DELETE FROM AD_SysConfig
WHERE  Name = 'mobileui.frontend.barcodeScanner.showInputText'
;

-- ============================================================
-- B4. showInputVideo: if exists → set mode.camera.enabled to its Value, then DELETE showInputVideo
-- ============================================================
-- Guard: only apply showInputVideo when useCamera (A1, the authoritative alias) did NOT already set the target.
-- This prevents B4 from overwriting A1 on DBs that carry both legacy rows simultaneously.
UPDATE AD_SysConfig
SET    Value      = COALESCE(
                       (SELECT Value FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.showInputVideo'),
                       Value  -- keep current value when legacy row has NULL Value
                   ),
       Updated    = TO_TIMESTAMP('2026-06-13 09:00:03', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 99
WHERE  Name = 'mobileui.frontend.barcodeScanner.mode.camera.enabled'
  AND  EXISTS     (SELECT 1 FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.showInputVideo')
  AND  NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.useCamera')
;

DELETE FROM AD_SysConfig
WHERE  Name = 'mobileui.frontend.barcodeScanner.showInputVideo'
;

-- ============================================================
-- B5. isInputTextReadonly: DELETE (keyboard suppression now decided by mode)
-- ============================================================
DELETE FROM AD_SysConfig
WHERE  Name = 'mobileui.frontend.barcodeScanner.isInputTextReadonly'
;

-- ============================================================
-- B6. visibleInput.readOnly: DELETE (visible read-only input no longer exists)
-- ============================================================
DELETE FROM AD_SysConfig
WHERE  Name = 'mobileui.frontend.barcodeScanner.visibleInput.readOnly'
;
