-- When Y, play an audible beep when an invalid product is entered in quick input. Default: N.
INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Value, ConfigurationLevel, EntityType, Description)
SELECT nextval('ad_sysconfig_seq'), 0, 0, 'Y', now(), 100, now(), 100,
       'webui.frontend.quickinput.beepOnInvalidProduct', 'N', 'S', 'de.metas.ui.web',
       'When Y, play an audible beep when an invalid product is entered in quick input. Default: N.'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'webui.frontend.quickinput.beepOnInvalidProduct'
);
