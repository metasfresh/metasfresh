-- 2017-09-15T18:48:46.153
-- URL zum Konzept
UPDATE AD_Column SET Help='This flag will mark ref_tables that are for Relation Types with the same flag , which have no source. The ref_table will be a reference target which means it contains the columns "AD_Table_ID" and "Record_ID" or [Tablename_]Record_ID. 
If the target table contains any entry for the table and record in the context (same AD_Table_ID and ID value equals Record_ID) it will be reachable for zoom into from the context entry, no matter for what table it is. 
See task #2340',Updated=TO_TIMESTAMP('2017-09-15 18:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557176
;

-- 2017-09-15T18:48:46.163
-- URL zum Konzept
UPDATE AD_Field SET Name='IsReferenceTarget', Description=NULL, Help='This flag will mark ref_tables that are for Relation Types with the same flag , which have no source. The ref_table will be a reference target which means it contains the columns "AD_Table_ID" and "Record_ID" or [Tablename_]Record_ID. 
If the target table contains any entry for the table and record in the context (same AD_Table_ID and ID value equals Record_ID) it will be reachable for zoom into from the context entry, no matter for what table it is. 
See task #2340' WHERE AD_Column_ID=557176 AND IsCentrallyMaintained='Y'
;

-- 2017-09-15T18:50:36.757
-- URL zum Konzept
UPDATE AD_Column SET Help='This column specifies which is the Record_ID (or [Table_]Record_ID) of the given reference target table for which the link with the context table entry is made. 
The column is only relevant is the reference table belongs to a relation type and has IsReferenceTarget = true.
 task #2340',Updated=TO_TIMESTAMP('2017-09-15 18:50:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557177
;

-- 2017-09-15T18:50:36.762
-- URL zum Konzept
UPDATE AD_Field SET Name='AD_Column_ReferenceTarget_ID', Description=NULL, Help='This column specifies which is the Record_ID (or [Table_]Record_ID) of the given reference target table for which the link with the context table entry is made. 
The column is only relevant is the reference table belongs to a relation type and has IsReferenceTarget = true.
 task #2340' WHERE AD_Column_ID=557177 AND IsCentrallyMaintained='Y'
;

