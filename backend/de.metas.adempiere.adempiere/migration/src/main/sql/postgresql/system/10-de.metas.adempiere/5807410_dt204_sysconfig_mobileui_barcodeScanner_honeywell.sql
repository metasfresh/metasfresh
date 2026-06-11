-- me03 #30363 (dt204 / Dantherm) — Honeywell handheld barcode-scanner tuning for the dt204 mobile UI.
--
-- Two mobile-webui frontend settings (consumed via useBooleanSetting in
-- mobile-webui-frontend/src/components/BarcodeScannerComponent.jsx):
--   * mobileui.frontend.barcodeScanner.useCamera            -> N : hide the device camera/video scanner
--   * mobileui.frontend.barcodeScanner.offscreenInput.readOnly -> Y : set HTML readOnly on the off-screen
--                                                                     scan input to suppress the on-screen
--                                                                     keyboard on Honeywell keystroke-mode devices
--
-- Scope: System-level (AD_Client_ID=0, AD_Org_ID=0, ConfigurationLevel='S'), matching every existing
-- mobileui.frontend.barcodeScanner.* sibling. On the deep_tundra customer flavor this applies to all dt204 instances.

-- 1) Turn the camera OFF.
--    The row already exists in the seed DB (introduced by 5773840). set_sysconfig_value fails loudly
--    if it is ever missing, and is idempotent / re-runnable.
SELECT set_sysconfig_value('mobileui.frontend.barcodeScanner.useCamera', 'N');

-- 2) Turn the keyboard-suppression knob ON.
--    This sysconfig is new (no prior migration creates it) -> INSERT it, idempotently.
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
SELECT 0, 0, 541814 /*From ID Server*/, 'S',
       TO_TIMESTAMP('2026-06-11 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
       'mobileui.frontend.barcodeScanner.offscreenInput.readOnly',
       TO_TIMESTAMP('2026-06-11 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.offscreenInput.readOnly'
);

-- Ensure the value is Y even if the row pre-exists with a different value (re-runnable convergence).
SELECT set_sysconfig_value('mobileui.frontend.barcodeScanner.offscreenInput.readOnly', 'Y');
