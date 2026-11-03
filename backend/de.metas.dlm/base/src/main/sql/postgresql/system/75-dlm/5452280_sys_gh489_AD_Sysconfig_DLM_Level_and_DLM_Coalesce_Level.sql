-- 25.10.2016 14:52
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541044,'S',TO_TIMESTAMP('2016-10-25 14:52:13','YYYY-MM-DD HH24:MI:SS'),100,'For records of DLM''ed tables that still have DLM_Level IS NULL, the system will assume the DLM_Level to be the given value.','de.metas.dlm','Y','de.metas.dlm.DLM_Coalesce_Level',TO_TIMESTAMP('2016-10-25 14:52:13','YYYY-MM-DD HH24:MI:SS'),100,'0')
;

-- 25.10.2016 14:54
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541045,'S',TO_TIMESTAMP('2016-10-25 14:54:12','YYYY-MM-DD HH24:MI:SS'),100,'ForDLM''ed tables, the system will "see" records whose DLM_Level is less or equals to this value. Note that for records which do not yet have an explicit DLM_Level (i.e. a NULL value in that column), the value from de.metas.dlm.DLM_Coalesce_Level is assumed.','de.metas.dlm','Y','de.metas.dlm.DLM_Level',TO_TIMESTAMP('2016-10-25 14:54:12','YYYY-MM-DD HH24:MI:SS'),100,'0')
;

