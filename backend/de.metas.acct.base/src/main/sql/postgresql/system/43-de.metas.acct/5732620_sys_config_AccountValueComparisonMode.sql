-- 2024-09-06T15:00:26.897Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) 
VALUES (0,0,541738,'S',TO_TIMESTAMP('2024-09-06 15:00:26.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,
'When value is "RESPECT_ACCOUNTS_TREE", the filter "Account Value" will provide the whole tree of accounts between the two parameter values, no matter how long the account values are. 
For example, if the parameters are 1000 - 2000 , the result 10000 will also be retrieved.
When value is "NUMERIC", the filter "Account Value" will be applied as a numeric comparison between the two parameter values.
For example, if the parameters are 1000 - 2000 , the result 10000 will NOT be retrieved.
The default is considered "RESPECT_ACCOUNTS_TREE".','de.metas.acct','Y','ElementValueService.AccountValueComparisonMode',
TO_TIMESTAMP('2024-09-06 15:00:26.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RESPECT_ACCOUNTS_TREE')
;

-- 2024-09-06T15:02:55.462Z
UPDATE AD_SysConfig SET ConfigurationLevel='O',
Updated=TO_TIMESTAMP('2024-09-06 15:02:55.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 
WHERE AD_SysConfig_ID=541738
;

