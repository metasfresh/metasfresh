

DO
$$
    BEGIN
        IF exists(SELECT s.AD_SysConfig_ID
                   FROM AD_SysConfig s
                   WHERE s.name='db.postgresql.unreturnedConnectionTimeoutMillis')
        THEN
            RAISE NOTICE 'AD_SysConfig db.postgresql.unreturnedConnectionTimeoutMillis already added. Nothing to do here.';
        ELSE
            RAISE NOTICE 'AD_SysConfig db.postgresql.unreturnedConnectionTimeoutMillis not yet added. Running now';



-- 2023-01-10T12:54:53.059Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,1000011,'S',TO_TIMESTAMP('2023-01-10 13:54:53','YYYY-MM-DD HH24:MI:SS'),100,'The default if not set is 2hrs = 7200000ms
','U','Y','db.postgresql.unreturnedConnectionTimeoutMillis',TO_TIMESTAMP('2023-01-10 13:54:53','YYYY-MM-DD HH24:MI:SS'),100,'7200000')
;

-- 2023-01-10T12:55:16.100Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2023-01-10 13:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=1000011
;

-- 2023-01-10T12:55:28.619Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='The default (if not set is 2hrs = 7200000ms)
',Updated=TO_TIMESTAMP('2023-01-10 13:55:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=1000011
;

-- 2023-01-10T12:56:11.518Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Timeout unreturned DB connection-pool-connections. I.e. kill them and get them back to the pool.
The default (if not set) is 2hrs = 7200000ms.
',Updated=TO_TIMESTAMP('2023-01-10 13:56:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=1000011
;


        END IF;
    END
$$
;
