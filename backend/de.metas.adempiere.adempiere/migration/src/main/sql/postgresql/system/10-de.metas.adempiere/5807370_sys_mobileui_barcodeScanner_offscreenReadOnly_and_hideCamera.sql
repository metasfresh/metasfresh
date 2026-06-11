-- mobileUI barcode scanner configuration for hardware-scanner / keystroke-mode handheld
-- deployments (e.g. Honeywell), delivered on the deep_tundra_release line.
--
--   1) Create the System-level setting mobileui.frontend.barcodeScanner.offscreenInput.readOnly
--      with the default value 'N' (= unchanged behaviour) if it does not already exist. The
--      NOT EXISTS guard keeps this forward-compatible with the wider scanner-config framework
--      that may introduce the same setting in the core line.
--   2) Turn it ON here: when set, the off-screen scan input carries the HTML readOnly attribute,
--      which suppresses the on-screen keyboard on keystroke-mode devices where inputMode=none is
--      not honoured. The Zebra DataWedge IME path is unaffected when the setting is off.
--   3) Hide the device camera (useCamera = N): operators use the hardware scanner, so the video
--      preview is not wanted.

INSERT INTO AD_SysConfig (
    AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, EntityType, IsActive,
    Name, Value, Description, Created, CreatedBy, Updated, UpdatedBy
)
SELECT
    0, 0, 541813 /*From ID Server*/, 'S', 'D', 'Y',
    'mobileui.frontend.barcodeScanner.offscreenInput.readOnly', 'N',
    'Wenn ''Y'', wird am ausgeblendeten Barcode-Scan-Eingabefeld das HTML-Attribut readOnly gesetzt, um die Bildschirmtastatur auf Geräten mit Tastatur-Modus (z. B. Honeywell) zu unterdrücken, bei denen inputMode=none nicht beachtet wird. Standard ''N''.',
    TO_TIMESTAMP('2026-06-11 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-11 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.offscreenInput.readOnly'
);

-- Suppress the on-screen keyboard on the scan screens (keystroke-mode handheld).
SELECT set_sysconfig_value('mobileui.frontend.barcodeScanner.offscreenInput.readOnly', 'Y');

-- Hide the device camera everywhere in the mobile UI (hardware scanner is used).
SELECT set_sysconfig_value('mobileui.frontend.barcodeScanner.useCamera', 'N');
