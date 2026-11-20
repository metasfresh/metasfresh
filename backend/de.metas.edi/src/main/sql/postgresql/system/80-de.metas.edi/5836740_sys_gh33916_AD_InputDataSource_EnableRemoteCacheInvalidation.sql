-- AD_InputDataSource edits made on one node (e.g. the webapi) must invalidate the cached IsEDIChannel flag on the other nodes (e.g. the app server).
UPDATE AD_Table
SET IsEnableRemoteCacheInvalidation = 'Y', Updated = TO_TIMESTAMP('2026-11-19 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = 540273 /* AD_InputDataSource */
;
