-- SysConfig Name: WorkflowRestAPIService.LaunchersLimit
-- SysConfig Value: 20
-- 2025-07-23T08:38:52.683Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
SELECT 0,
       0,
       541768,
       'S',
       TO_TIMESTAMP('2025-07-23 08:38:52.403000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       'D',
       'Y',
       'WorkflowRestAPIService.LaunchersLimit',
       TO_TIMESTAMP('2025-07-23 08:38:52.403000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
       100,
       '20'
WHERE NOT EXISTS (SELECT 1
                  FROM AD_SysConfig
                  WHERE Name = 'WorkflowRestAPIService.LaunchersLimit'
                    AND AD_Client_ID = 0
                    AND AD_Org_ID = 0)
;

update ad_sysconfig set description = 'The value represents the maximum number of manufacturing orders available in the Mobile UI Production. The default hardcoded value is 20.' where name = 'WorkflowRestAPIService.LaunchersLimit';
