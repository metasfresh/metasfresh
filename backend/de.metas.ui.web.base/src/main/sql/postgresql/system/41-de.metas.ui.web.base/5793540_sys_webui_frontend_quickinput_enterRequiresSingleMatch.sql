-- make sure this sysconfig is really created
INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, Name, Value, Description, ConfigurationLevel, EntityType)
VALUES (579353, 0, 0, '2026-03-03', 100, '2026-03-03', 100, 'Y',
        'webui.frontend.quickinput.enterRequiresSingleMatch',
        'N',
        'Quick input: when Y, Enter only auto-selects with exactly 1 match and beeps on multiple. When N, Enter selects the first/highlighted item even with multiple matches.',
        'S',
        'D')
ON CONFLICT DO NOTHING;
