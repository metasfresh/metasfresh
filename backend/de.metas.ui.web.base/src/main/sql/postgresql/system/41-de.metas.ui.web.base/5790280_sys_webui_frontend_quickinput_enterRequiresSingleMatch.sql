-- 2026-03-03T00:00:00
-- webui.frontend.quickinput.enterRequiresSingleMatch
-- When Y: Enter in quick input only auto-selects when exactly 1 product matches; beeps on multiple matches.
-- When N (default): Enter selects the first/highlighted item even with multiple matches; no beep.

INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, Name, Value, Description, ConfigurationLevel, EntityType)
VALUES (541795, 0, 0, '2026-03-03', 100, '2026-03-03', 100, 'Y',
        'webui.frontend.quickinput.enterRequiresSingleMatch',
        'N',
        'Quick input: when Y, Enter only auto-selects with exactly 1 match and beeps on multiple. When N, Enter selects the first/highlighted item even with multiple matches.',
        'S',
        'de.metas.ui.web.base')
ON CONFLICT DO NOTHING;
