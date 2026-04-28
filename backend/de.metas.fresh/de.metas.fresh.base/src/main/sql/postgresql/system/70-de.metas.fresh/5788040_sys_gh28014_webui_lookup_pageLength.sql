-- gh#28014: Set WebUI lookup page length to 15
--
-- The hardcoded default is 10, which cuts off results in any lookup with more than 10 matches.
-- Most notably, period dropdowns filtered by year have 12 entries (Jan–Dec), so the last 2
-- are hidden behind "Es gibt weitere Ergebnisse". The value should be at least 12 to
-- accommodate our usual 12 periods per year; we use 15 to leave a small buffer.

INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          Name, Value,
                          Description, EntityType, ConfigurationLevel)
SELECT nextval('ad_sysconfig_seq'), 0, 0, 'Y',
       TO_TIMESTAMP('2026-02-21', 'YYYY-MM-DD'), 100, TO_TIMESTAMP('2026-02-21', 'YYYY-MM-DD'), 100,
       'webui.lookup.pageLength', '15',
       'Max items returned per lookup/typeahead request. Must be at least 12 to show all periods of a year without truncation.',
       'de.metas.fresh', 'S'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig
    WHERE Name = 'webui.lookup.pageLength'
      AND IsActive = 'Y'
);
