-- gh26253: Seed System-level SysConfig for the accounting-docs-to-repost poller poll interval.
-- Sets the background reposting poller's sleep interval to 10 seconds (matches the code default
-- Duration.ofSeconds(10)); making it an explicit AD_SysConfig row lets ops tune it without a rebuild.
-- Creation only (INSERT guarded by NOT EXISTS) -> idempotent, safe to re-run.

INSERT INTO AD_SysConfig (AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, Name, Value, Description, ConfigurationLevel, EntityType)
SELECT 541838 /*From ID Server*/,
       0, 0,
       TO_TIMESTAMP('2026-07-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'Y',
       'de.metas.acct.accounting_docs_to_repost.pollIntervalInSeconds',
       '10',
       'Poll interval (in seconds) the accounting-docs-to-repost background poller waits between scans for documents to repost. Default 10.',
       'S',
       'D'
WHERE NOT EXISTS (SELECT 1 FROM AD_SysConfig WHERE Name='de.metas.acct.accounting_docs_to_repost.pollIntervalInSeconds');
