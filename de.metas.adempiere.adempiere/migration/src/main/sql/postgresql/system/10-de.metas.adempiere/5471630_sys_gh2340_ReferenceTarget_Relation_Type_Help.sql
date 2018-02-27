-- 2017-09-14T11:07:28.996
-- URL zum Konzept
UPDATE AD_Column SET Help='This flag will mark relation types that have no source, and their target is a table which contains the columns "AD_Table_ID" and "Record_ID". 
If the target table contains any entry for the table and record in the context (same AD_Table_ID and ID value equals Record_ID) it will be reachable for zoom into from the context entry, no matter for what table it is. ',Updated=TO_TIMESTAMP('2017-09-14 11:07:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557147
;

-- 2017-09-14T11:07:29.008
-- URL zum Konzept
UPDATE AD_Field SET Name='IsReferenceTarget', Description=NULL, Help='This flag will mark relation types that have no source, and their target is a table which contains the columns "AD_Table_ID" and "Record_ID". 
If the target table contains any entry for the table and record in the context (same AD_Table_ID and ID value equals Record_ID) it will be reachable for zoom into from the context entry, no matter for what table it is. ' WHERE AD_Column_ID=557147 AND IsCentrallyMaintained='Y'
;

-- 2017-09-15T09:57:19.126
-- URL zum Konzept
UPDATE AD_Column SET Help='This flag will mark relation types that have no source, and their target is a table which contains the columns "AD_Table_ID" and "Record_ID". 
If the target table contains any entry for the table and record in the context (same AD_Table_ID and ID value equals Record_ID) it will be reachable for zoom into from the context entry, no matter for what table it is. 
See task #2340',Updated=TO_TIMESTAMP('2017-09-15 09:57:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557147
;

-- 2017-09-15T09:57:19.131
-- URL zum Konzept
UPDATE AD_Field SET Name='IsReferenceTarget', Description=NULL, Help='This flag will mark relation types that have no source, and their target is a table which contains the columns "AD_Table_ID" and "Record_ID". 
If the target table contains any entry for the table and record in the context (same AD_Table_ID and ID value equals Record_ID) it will be reachable for zoom into from the context entry, no matter for what table it is. 
See task #2340' WHERE AD_Column_ID=557147 AND IsCentrallyMaintained='Y'
;

