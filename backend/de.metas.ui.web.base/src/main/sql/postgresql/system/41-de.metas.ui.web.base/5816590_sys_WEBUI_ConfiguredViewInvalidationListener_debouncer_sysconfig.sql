-- Seed the two AD_SysConfig rows read by the ConfiguredViewInvalidationListener debouncer.
-- The listener coalesces config-driven WebUI view invalidations; these tune its debouncer.
-- Both are read with a code-side default equal to the value seeded here.
-- IDs allocated from idserver.metas.de on 2026-07-28:
--   AD_SysConfig 541840 (webui.ConfiguredViewInvalidationListener.debouncer.bufferMaxSize)
--   AD_SysConfig 541841 (webui.ConfiguredViewInvalidationListener.debouncer.delayInMillis)

-- Max. number of collected events the debouncer buffers before flushing.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID /*From ID Server*/,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541840,'S',TO_TIMESTAMP('2026-07-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Max. number of collected events the ConfiguredViewInvalidationListener debouncer buffers before flushing a WebUI view invalidation.','de.metas.ui.web','Y','webui.ConfiguredViewInvalidationListener.debouncer.bufferMaxSize',TO_TIMESTAMP('2026-07-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'500')
;

-- Max. number of milliseconds the debouncer waits for more events before flushing.
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID /*From ID Server*/,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541841,'S',TO_TIMESTAMP('2026-07-28 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Max. number of milliseconds the ConfiguredViewInvalidationListener debouncer waits for more events before flushing a WebUI view invalidation.','de.metas.ui.web','Y','webui.ConfiguredViewInvalidationListener.debouncer.delayInMillis',TO_TIMESTAMP('2026-07-28 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'100')
;
