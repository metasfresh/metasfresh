
-- Run mode: WEBUI

-- SysConfig Name: de.metas.archive.AttachDateAndTime
-- SysConfig Value: N
-- 2025-08-14T07:50:35.604Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) 
VALUES (0,0,541771,'S',TO_TIMESTAMP('2025-08-14 07:50:35.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
100,'D','Y','de.metas.archive.AttachDateAndTime',TO_TIMESTAMP('2025-08-14 07:50:35.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- SysConfig Name: de.metas.archive.AttachDateAndTime
-- SysConfig Value: N
-- 2025-08-14T07:56:09.576Z
UPDATE AD_SysConfig SET Description='When "Y", the date and time of the archive creation will be added to the generated archive name.',Updated=TO_TIMESTAMP('2025-08-14 07:56:09.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541771
;
