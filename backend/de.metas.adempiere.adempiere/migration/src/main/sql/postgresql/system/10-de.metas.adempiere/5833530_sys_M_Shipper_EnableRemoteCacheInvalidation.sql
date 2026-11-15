-- M_Shipper: enable remote cache invalidation, so that a shipper changed in one JVM (e.g. the WebUI API)
-- also resets the shipper caches of the other JVMs (e.g. the app server sending carrier orders).
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y', Updated=TO_TIMESTAMP('2026-11-08 13:40:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Table_ID=253 /* M_Shipper */
;
