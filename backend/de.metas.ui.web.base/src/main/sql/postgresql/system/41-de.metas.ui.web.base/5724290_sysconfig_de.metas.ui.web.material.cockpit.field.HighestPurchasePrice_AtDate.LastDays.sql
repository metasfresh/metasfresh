delete from ad_sysconfig where ad_sysconfig_id=541721;

-- 2024-05-23T10:42:44.483Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541721,'S',TO_TIMESTAMP('2024-05-23 10:42:43.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'How many days in the past to be used for determining the highest purchase price','de.metas.material.cockpit','Y','de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.LastDays',TO_TIMESTAMP('2024-05-23 10:42:43.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'7')
;

