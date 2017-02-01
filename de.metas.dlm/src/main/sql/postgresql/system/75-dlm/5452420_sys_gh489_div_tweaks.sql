
--
-- DDL for deleting DLM_Referenced_Table_Partition_Config_Line_ID
--
ALTER TABLE DLM_Partition_Config_Reference DROP COLUMN IF EXISTS DLM_Referenced_Table_Partition_Config_Line_ID;

COMMIT;


--
-- Change DLM_Level so that we also see records with are in the "test" level
--
-- 26.10.2016 15:44
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='1',Updated=TO_TIMESTAMP('2016-10-26 15:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541045
;

--
-- DML for deleting DLM_Referenced_Table_Partition_Config_Line_ID
--
-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=557330
;

-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=557330
;

-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=555151
;

-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=555151
;

-- 26.10.2016 15:59
-- URL zum Konzept
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540350
;

--
-- add log setting, but inactive. that way it's easier to activate the logging
--
-- 21.10.2016 15:23
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541042,'S',TO_TIMESTAMP('2016-10-21 15:23:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','N','CLogger.Level.de.metas.dlm.migrator.impl.MigratorService',TO_TIMESTAMP('2016-10-21 15:23:55','YYYY-MM-DD HH24:MI:SS'),100,'DEBUG')
;

