-- When Y, play an audible beep when an invalid product is entered in quick input. Default: N.
INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Value, ConfigurationLevel, EntityType, Description)
VALUES (541795, 0, 0, 'Y', TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100, TO_TIMESTAMP('2026-02-26', 'YYYY-MM-DD'), 100,
       'webui.frontend.quickinput.beepOnInvalidProduct', 'N', 'S', 'de.metas.ui.web',
       'When Y, play an audible beep when an invalid product is entered in quick input. Default: N.')
ON CONFLICT DO NOTHING;
