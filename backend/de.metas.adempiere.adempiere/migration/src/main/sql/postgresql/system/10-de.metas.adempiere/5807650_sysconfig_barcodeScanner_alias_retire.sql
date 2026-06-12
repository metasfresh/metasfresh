-- Barcode scanner: carry legacy knob values forward into the new mode scheme and retire dead knobs.
-- Order: all carry-forward UPDATEs first, then DELETEs.
-- Missing legacy rows are harmless (WHERE EXISTS guards).

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

-- showInputText DELETE: NOT executed here.
-- BarcodeScannerComponent.jsx still reads barcodeScanner.showInputText via useBooleanSetting.
-- Deleting this row would silently reset Mode C3 (visible input) on any deployment with showInputText=Y,
-- causing the visible input field to disappear without warning.
-- This DELETE ships in the task that updates BarcodeScannerComponent to read the new per-mode knobs.

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
-- B5. isInputTextReadonly: NOT deleted here.
-- BarcodeScannerComponent.jsx still reads barcodeScanner.isInputTextReadonly via useBooleanSetting.
-- Deleting this row while the JS consumer exists would silently reset any customer-configured
-- N value to isMobileOrTablet() default, losing the keyboard-suppression override on Mode C3.
-- This DELETE ships in the task that updates BarcodeScannerComponent to read the new per-mode knobs.
-- ============================================================

-- ============================================================
-- B6. visibleInput.readOnly: NOT deleted here.
-- BarcodeScannerComponent.jsx still reads barcodeScanner.visibleInput.readOnly via useBooleanSetting.
-- Deleting this row while the JS consumer exists would silently reset any customer-configured
-- Y value to false (the hook default), losing keyboard suppression on the visible input (Mode C3).
-- This DELETE ships in the task that updates BarcodeScannerComponent to read the new per-mode knob.
-- ============================================================
