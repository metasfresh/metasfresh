-- IDs allocated from idserver.metas.de on 2026-06-12:
--   AD_SysConfig 541816 (mobileui.frontend.barcodeScanner.defaultMode)
--   AD_SysConfig 541817 (mobileui.frontend.barcodeScanner.mode.hardware.enabled)
--   AD_SysConfig 541818 (mobileui.frontend.barcodeScanner.mode.camera.enabled)
--   AD_SysConfig 541819 (mobileui.frontend.barcodeScanner.mode.manual.enabled)
--   AD_SysConfig 541820 (mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly)
--   AD_SysConfig 541821 (mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode)

-- SysConfig Name: mobileui.frontend.barcodeScanner.defaultMode
-- Default: hardware — which scanner mode the mobile barcode UI activates on startup.
-- Allowed values: hardware | camera | manual
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541816 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','mobileui.frontend.barcodeScanner.defaultMode',TO_TIMESTAMP('2026-06-12 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'hardware')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.hardware.enabled
-- Default: Y — hardware (keyboard-wedge / HID) scanner mode is enabled by default.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541817 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','mobileui.frontend.barcodeScanner.mode.hardware.enabled',TO_TIMESTAMP('2026-06-12 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.camera.enabled
-- Default: Y — camera scanner mode is enabled by default.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541818 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','mobileui.frontend.barcodeScanner.mode.camera.enabled',TO_TIMESTAMP('2026-06-12 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.manual.enabled
-- Default: N — manual (typed) barcode entry mode is disabled by default.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541819 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','mobileui.frontend.barcodeScanner.mode.manual.enabled',TO_TIMESTAMP('2026-06-12 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly
-- Default: N — the hardware-mode input field is editable (not read-only) by default.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541820 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly',TO_TIMESTAMP('2026-06-12 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode
-- Default: none — CSS/HTML inputmode for the hardware-scanner input field. 'none' suppresses
-- the on-screen keyboard on touch devices so only the hardware scanner can type into the field.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541821 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode',TO_TIMESTAMP('2026-06-12 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'none')
;
