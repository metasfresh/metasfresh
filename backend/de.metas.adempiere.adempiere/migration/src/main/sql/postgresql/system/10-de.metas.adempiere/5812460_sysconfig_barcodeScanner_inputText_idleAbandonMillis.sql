-- mobileUI barcode scanner: ship the inputText.idleAbandonMillis capability (default 15000 ms).
--
-- Creates the System-level setting mobileui.frontend.barcodeScanner.inputText.idleAbandonMillis with
-- its default value '15000' if it does not already exist. Mirrors the sibling setting
-- mobileui.frontend.barcodeScanner.inputText.debounceMillis (see 5664360_...); the frontend reads it
-- via usePositiveNumberSetting('barcodeScanner.inputText.idleAbandonMillis', 15000) and passes it to
-- useKeyboardBarcodeReader as idleAbandonMs. The NOT EXISTS guard keeps this idempotent / forward
-- compatible.
--
-- This is the long "abandon" window for a recognised-but-incomplete (streamed / chunked) HU QR code:
-- a still-open partial is held buffered across inter-keystroke gaps (so a slow chunked scan is never
-- split) and only abandoned/flushed once this window elapses, surfacing the app's "QR not recognised"
-- error instead of hanging. It must sit safely ABOVE the largest legit inter-chunk gap. Lowering it
-- shortens the wait before a genuinely-truncated scan surfaces its error, at the risk of splitting a
-- very slow legitimate chunked scan. Default '15000' keeps the shipped hook default (IDLE_ABANDON_MS).

INSERT INTO AD_SysConfig (
    AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, EntityType, IsActive,
    Name, Value, Description, Created, CreatedBy, Updated, UpdatedBy
)
SELECT
    0, 0, 541831 /*From ID Server*/, 'S', 'D', 'Y',
    'mobileui.frontend.barcodeScanner.inputText.idleAbandonMillis', '15000',
    'How many millis a recognised-but-incomplete (streamed/chunked) barcode may stay buffered across inter-keystroke gaps before it is abandoned and flushed as an error. Must sit safely above the largest legitimate inter-chunk gap. Default 15000.',
    TO_TIMESTAMP('2026-07-07 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-07 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'mobileui.frontend.barcodeScanner.inputText.idleAbandonMillis'
);
