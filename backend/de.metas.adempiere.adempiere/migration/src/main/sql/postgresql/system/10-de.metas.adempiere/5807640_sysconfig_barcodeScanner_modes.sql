-- IDs allocated from idserver.metas.de on 2026-06-12:
--   AD_SysConfig 541816 (mobileui.frontend.barcodeScanner.defaultMode)
--   AD_SysConfig 541817 (mobileui.frontend.barcodeScanner.mode.hardware.enabled)
--   AD_SysConfig 541818 (mobileui.frontend.barcodeScanner.mode.camera.enabled)
--   AD_SysConfig 541819 (mobileui.frontend.barcodeScanner.mode.manual.enabled)
--   AD_SysConfig 541820 (mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly)
--   AD_SysConfig 541821 (mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode)

-- SysConfig Name: mobileui.frontend.barcodeScanner.defaultMode
-- Default: hardware — welcher Scanner-Modus beim Start der mobilen Barcode-UI aktiv ist.
-- Erlaubte Werte: hardware | camera | manual
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
SELECT 0,0,541816 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
       'Scanner-Modus beim Start der mobilen Barcode-UI. Erlaubte Werte: hardware | camera | manual. Standard: hardware.',
       'D','Y','mobileui.frontend.barcodeScanner.defaultMode',TO_TIMESTAMP('2026-06-12 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'hardware'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='mobileui.frontend.barcodeScanner.defaultMode')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.hardware.enabled
-- Default: Y — Hardware-Scanner-Modus (Tastatur-Wedge / HID) ist standardmäßig aktiv.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
SELECT 0,0,541817 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,
       'Hardware-Scanner-Modus (Tastatur-Wedge / HID) aktivieren. Y=aktiv, N=deaktiviert. Standard: Y.',
       'D','Y','mobileui.frontend.barcodeScanner.mode.hardware.enabled',TO_TIMESTAMP('2026-06-12 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Y'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='mobileui.frontend.barcodeScanner.mode.hardware.enabled')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.camera.enabled
-- Default: Y — Kamera-Scanner-Modus ist standardmäßig aktiv.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
SELECT 0,0,541818 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,
       'Kamera-Scanner-Modus aktivieren. Y=aktiv, N=deaktiviert. Standard: Y.',
       'D','Y','mobileui.frontend.barcodeScanner.mode.camera.enabled',TO_TIMESTAMP('2026-06-12 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'Y'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='mobileui.frontend.barcodeScanner.mode.camera.enabled')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.manual.enabled
-- Default: N — manuelle Barcode-Eingabe ist standardmäßig deaktiviert.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
SELECT 0,0,541819 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,
       'Manuelle Barcode-Eingabe (getippter Text) aktivieren. Y=aktiv, N=deaktiviert. Standard: N.',
       'D','Y','mobileui.frontend.barcodeScanner.mode.manual.enabled',TO_TIMESTAMP('2026-06-12 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'N'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='mobileui.frontend.barcodeScanner.mode.manual.enabled')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly
-- Default: N — das Eingabefeld im Hardware-Modus ist bearbeitbar.
-- Dieses Feld steuert das HARDWARE-MODUS-Eingabefeld (mode.hardware.*).
-- Es ist NICHT identisch mit barcodeScanner.offscreenInput.readOnly (Off-Screen-Eingabefeld, Modus A)
-- oder barcodeScanner.visibleInput.readOnly (sichtbares Eingabefeld, Modus C3).
-- Nur für Keystroke-Firmware-Deployments setzen, bei denen inputMode=none nicht unterdrückt (z. B. Honeywell CT60).
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
SELECT 0,0,541820 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,
       'Hardware-Modus-Eingabefeld schreibschützen (Y=readOnly, N=editierbar; Standard: N). Nur für Keystroke-Firmware ohne inputMode=none-Unterstützung. Nicht verwechseln mit offscreenInput.readOnly / visibleInput.readOnly.',
       'D','Y','mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly',TO_TIMESTAMP('2026-06-12 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='mobileui.frontend.barcodeScanner.mode.hardware.input.readOnly')
;

-- SysConfig Name: mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode
-- Default: none — HTML-inputmode für das Hardware-Scanner-Eingabefeld. 'none' unterdrückt die
-- Bildschirmtastatur auf Touch-Geräten, sodass nur der Hardware-Scanner in das Feld eingeben kann.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
SELECT 0,0,541821 /*From ID Server*/,'S',TO_TIMESTAMP('2026-06-12 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,
       'HTML inputmode für das Hardware-Scanner-Eingabefeld. ''none'' unterdrückt die Bildschirmtastatur auf Touch-Geräten. Standard: none.',
       'D','Y','mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode',TO_TIMESTAMP('2026-06-12 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'none'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='mobileui.frontend.barcodeScanner.mode.hardware.input.inputMode')
;
