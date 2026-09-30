-- mobileUI barcode scanner: ship the offscreenInput.readOnly capability (default OFF).
--
-- Creates the System-level setting mobileui.frontend.barcodeScanner.offscreenInput.readOnly with
-- its safe default value 'N' (= unchanged behaviour) if it does not already exist. The NOT EXISTS
-- guard keeps this forward-compatible with the wider scanner-config framework
-- (https://github.com/metasfresh/me03/issues/29246) that may introduce the same setting in core.
--
-- When set to 'Y', the off-screen scan input carries the HTML readOnly attribute, which suppresses
-- the on-screen keyboard on keystroke-mode devices (e.g. Honeywell) where inputMode=none is not
-- honoured. The Zebra DataWedge IME path is unaffected while the setting is off.
--
-- This core script ships ONLY the capability + safe default. Turning the setting ON for a specific
-- instance (and setting useCamera=N) is deployment configuration and lives in the customer
-- repo via set_sysconfig_value(...), NOT here. See https://github.com/metasfresh/me03/issues/30363.

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
