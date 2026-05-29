-- SysConfig Name: mobileui.frontend.api.completeConfirmation.timeoutMillis
-- SysConfig Value: 20000
-- Timeout (in milliseconds) for the completion confirmation API call on the Mobile UI
-- picking workflow. When the request takes longer than this, an inline retry panel
-- is shown with Retry / Cancel instead of a short-lived toast. Default: 20000 (20s).
-- Read on the frontend via usePositiveNumberSetting('api.completeConfirmation.timeoutMillis', ...).
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
VALUES (0, 0, 541805 /*From ID Server*/, 'S',
        TO_TIMESTAMP('2026-04-23 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-04-23 14:00', 'YYYY-MM-DD HH24:MI'), 0,
        'D', 'Y',
        'mobileui.frontend.api.completeConfirmation.timeoutMillis',
        '20000',
        'Timeout in milliseconds for the completion confirmation API call on the Mobile UI picking workflow. When the request takes longer than this, an inline retry panel is shown (Retry / Cancel) instead of a short-lived toast. Default: 20000 (20s).')
;
