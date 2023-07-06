-- Cleanup:
-- There may be manually created log records in the DB for demo-purposes,
-- but they have at least two columns that now need to be mandatory and make this migration script fail.
-- From this PR onwards, the logs are created automatically (or via export and reimport)
delete from modcntr_log where true;
commit;

---

-- Column: ModCntr_Log.Amount
-- 2023-06-28T13:56:34.833298400Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-06-28 16:56:34.832','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586855
;

-- 2023-06-28T13:56:35.836629500Z
INSERT INTO t_alter_column values('modcntr_log','Amount','NUMERIC',null,null)
;

-- 2023-06-28T13:56:35.839853500Z
INSERT INTO t_alter_column values('modcntr_log','Amount',null,'NULL',null)
;

-- Column: ModCntr_Log.DateTrx
-- 2023-06-28T13:57:06.490988800Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-06-28 16:57:06.49','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586772
;

-- 2023-06-28T13:57:07.043205800Z
INSERT INTO t_alter_column values('modcntr_log','DateTrx','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2023-06-28T13:57:07.045296700Z
INSERT INTO t_alter_column values('modcntr_log','DateTrx',null,'NOT NULL',null)
;

-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-06-28T13:57:31.693247500Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-06-28 16:57:31.693','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586851
;

-- 2023-06-28T13:57:33.693877300Z
INSERT INTO t_alter_column values('modcntr_log','ModCntr_Log_DocumentType','VARCHAR(250)',null,null)
;

-- 2023-06-28T13:57:33.697022400Z
INSERT INTO t_alter_column values('modcntr_log','ModCntr_Log_DocumentType',null,'NOT NULL',null)
;

-- Column: ModCntr_Log.AD_Table_ID
-- 2023-06-28T14:24:49.423117800Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-06-28 17:24:49.423','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586769
;

-- 2023-06-28T14:24:49.952186500Z
INSERT INTO t_alter_column values('modcntr_log','AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2023-06-28T14:24:49.954995300Z
INSERT INTO t_alter_column values('modcntr_log','AD_Table_ID',null,'NOT NULL',null)
;

-- 2023-06-28T14:24:57.786381100Z
INSERT INTO t_alter_column values('modcntr_log','Record_ID','NUMERIC(10)',null,null)
;

-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2023-06-28T14:25:23.583862800Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-06-28 17:25:23.583','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586853
;

-- 2023-06-28T14:25:26.584336300Z
INSERT INTO t_alter_column values('modcntr_log','Harvesting_Year_ID','NUMERIC(10)',null,null)
;

-- 2023-06-28T14:25:26.586946300Z
INSERT INTO t_alter_column values('modcntr_log','Harvesting_Year_ID',null,'NOT NULL',null)
;

