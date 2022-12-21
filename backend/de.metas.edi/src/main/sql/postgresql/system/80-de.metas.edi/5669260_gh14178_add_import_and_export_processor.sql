
--
-- EXP_Processor
--
-- 2022-12-19T07:20:35.092Z
INSERT INTO EXP_Processor (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EXP_Processor_ID,EXP_Processor_Type_ID,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-12-19 08:20:35','YYYY-MM-DD HH24:MI:SS'),100,540014,540007,'Y','exportProcessor',TO_TIMESTAMP('2022-12-19 08:20:35','YYYY-MM-DD HH24:MI:SS'),100,'exportProcessor')
;

-- 2022-12-19T07:20:35.309Z
INSERT INTO EXP_ProcessorParameter (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,EXP_Processor_ID,EXP_ProcessorParameter_ID,Help,IsActive,Name,ParameterValue,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-12-19 08:20:35','YYYY-MM-DD HH24:MI:SS'),100,'Export Processor Parameter Description',540014,540036,'AMQP Export Processor Parameter Help','Y','Name of AMQP exchange from where xml will be exported','ExampleExchange',TO_TIMESTAMP('2022-12-19 08:20:35','YYYY-MM-DD HH24:MI:SS'),100,'exchangeName')
;

-- 2022-12-19T07:20:35.443Z
INSERT INTO EXP_ProcessorParameter (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,EXP_Processor_ID,EXP_ProcessorParameter_ID,Help,IsActive,Name,ParameterValue,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-12-19 08:20:35','YYYY-MM-DD HH24:MI:SS'),100,'Export Processor Parameter Description',540014,540037,'AMQP Export Processor Parameter Help','Y','AMQP routing key for the messages that will be exported','ExpRoutingKey',TO_TIMESTAMP('2022-12-19 08:20:35','YYYY-MM-DD HH24:MI:SS'),100,'routingKey')
;

-- 2022-12-19T07:20:35.580Z
INSERT INTO EXP_ProcessorParameter (AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,EXP_Processor_ID,EXP_ProcessorParameter_ID,Help,IsActive,Name,ParameterValue,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2022-12-19 08:20:35','YYYY-MM-DD HH24:MI:SS'),100,'Export Processor Parameter Description',540014,540038,'AMQP Export Processor Parameter Help','Y','AMQP durable queue used for export','true',TO_TIMESTAMP('2022-12-19 08:20:35','YYYY-MM-DD HH24:MI:SS'),100,'isDurableQueue')
;

-- 2022-12-19T07:20:40.066Z
UPDATE EXP_Processor SET PasswordInfo='metasfresh',Updated=TO_TIMESTAMP('2022-12-19 08:20:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Processor_ID=540014
;

-- 2022-12-19T07:20:42.960Z
UPDATE EXP_Processor SET Account='metasfresh',Updated=TO_TIMESTAMP('2022-12-19 08:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Processor_ID=540014
;

-- 2022-12-19T07:20:48.618Z
UPDATE EXP_Processor SET Port=5672,Updated=TO_TIMESTAMP('2022-12-19 08:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Processor_ID=540014
;

-- 2022-12-19T07:20:56.680Z
UPDATE EXP_Processor SET Host='rabbitmq',Updated=TO_TIMESTAMP('2022-12-19 08:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Processor_ID=540014
;

-- 2022-12-19T07:21:28.194Z
UPDATE EXP_ProcessorParameter SET ParameterValue='de.metas.esb.durable.exchange',Updated=TO_TIMESTAMP('2022-12-19 08:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_ProcessorParameter_ID=540036
;

-- 2022-12-19T07:21:35.150Z
UPDATE EXP_ProcessorParameter SET ParameterValue='de.metas.esb.from.metasfresh.durable',Updated=TO_TIMESTAMP('2022-12-19 08:21:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_ProcessorParameter_ID=540037
;

-- 2022-12-19T07:22:02.148Z
UPDATE EXP_Processor SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2022-12-19 08:22:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Processor_ID=540014
;


--
-- IMP_Processor
--
-- 2022-12-19T07:23:05.435Z
INSERT INTO IMP_Processor (Account,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Frequency,FrequencyType,Host,IMP_Processor_ID,IMP_Processor_Type_ID,IsActive,IsLogOnlyImportErrors,KeepLogDays,Name,PasswordInfo,Port,Processing,Updated,UpdatedBy,Value) VALUES ('metasfresh',1000000,1000000,TO_TIMESTAMP('2022-12-19 08:23:05','YYYY-MM-DD HH24:MI:SS'),100,0,'M','rabbitmq',540010,540008,'Y','N',7,'importProcessor','metasfresh',5672,'N',TO_TIMESTAMP('2022-12-19 08:23:05','YYYY-MM-DD HH24:MI:SS'),100,'importProcessor')
ON CONFLICT (name)
    DO NOTHING
;

-- 2022-12-19T07:23:05.681Z
INSERT INTO IMP_ProcessorParameter (AD_Client_ID, AD_Org_ID, Created, CreatedBy, Description, Help, IMP_Processor_ID, IMP_ProcessorParameter_ID, IsActive, Name, ParameterValue, Updated, UpdatedBy, Value)
SELECT  1000000, 1000000, TO_TIMESTAMP('2022-12-19 08:23:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Import Processor Parameter Description', 'AMQP Import Processor Parameter Help', 540010, 540027, 'Y', 'Name of AMQP Queue from where xml will be Imported', 'ExampleQueue', TO_TIMESTAMP('2022-12-19 08:23:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'queueName'
WHERE NOT EXISTS (SELECT 1 FROM IMP_ProcessorParameter WHERE Value='queueName')
;

-- 2022-12-19T07:23:05.821Z
INSERT INTO IMP_ProcessorParameter (AD_Client_ID, AD_Org_ID, Created, CreatedBy, Description, Help, IMP_Processor_ID, IMP_ProcessorParameter_ID, IsActive, Name, ParameterValue, Updated, UpdatedBy, Value)
SELECT 1000000, 1000000, TO_TIMESTAMP('2022-12-19 08:23:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Import Processor Parameter Description', 'AMQP Import Processor Parameter Help', 540010, 540028, 'Y', 'Cumsumer Tag', 'exampleConsumerTag', TO_TIMESTAMP('2022-12-19 08:23:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'consumerTag'
WHERE NOT EXISTS (SELECT 1 FROM IMP_ProcessorParameter WHERE Value='consumerTag')
;

-- 2022-12-19T07:23:05.964Z
INSERT INTO IMP_ProcessorParameter (AD_Client_ID, AD_Org_ID, Created, CreatedBy, Description, Help, IMP_Processor_ID, IMP_ProcessorParameter_ID, IsActive, Name, ParameterValue, Updated, UpdatedBy, Value)
SELECT 1000000, 1000000, TO_TIMESTAMP('2022-12-19 08:23:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Export Processor Parameter Description', 'AMQP Export Processor Parameter Help', 540010, 540029, 'Y', 'Name of AMQP exchange from where xml will be imported', 'ExampleExchange', TO_TIMESTAMP('2022-12-19 08:23:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'exchangeName'
WHERE NOT EXISTS (SELECT 1 FROM IMP_ProcessorParameter WHERE Value='exchangeName')
;

-- 2022-12-19T07:23:06.100Z
INSERT INTO IMP_ProcessorParameter (AD_Client_ID, AD_Org_ID, Created, CreatedBy, Description, Help, IMP_Processor_ID, IMP_ProcessorParameter_ID, IsActive, Name, ParameterValue, Updated, UpdatedBy, Value)
SELECT 1000000, 1000000, TO_TIMESTAMP('2022-12-19 08:23:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Export Processor Parameter Description', 'AMQP Export Processor Parameter Help', 540010, 540030, 'Y', 'AMQP durable queue used for import', 'true', TO_TIMESTAMP('2022-12-19 08:23:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'isDurableQueue'
WHERE NOT EXISTS (SELECT 1 FROM IMP_ProcessorParameter WHERE Value='isDurableQueue')
;

-- 2022-12-19T07:23:09.325Z
UPDATE IMP_Processor SET Frequency=1,Updated=TO_TIMESTAMP('2022-12-19 08:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Name='importProcessor'
;

-- 2022-12-19T07:23:28.515Z
UPDATE IMP_ProcessorParameter SET ParameterValue='de.metas.esb.to.metasfresh.durable',Updated=TO_TIMESTAMP('2022-12-19 08:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Value='queueName'
;

-- 2022-12-19T07:23:39.602Z
UPDATE IMP_ProcessorParameter SET ParameterValue='edi',Updated=TO_TIMESTAMP('2022-12-19 08:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Value='consumerTag'
;

-- 2022-12-19T07:23:52.547Z
UPDATE IMP_ProcessorParameter SET ParameterValue='de.metas.esb.durable.exchange',Updated=TO_TIMESTAMP('2022-12-19 08:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Value='exchangeName'
;


--
-- AD_ReplicationStrategy
--
-- 2022-12-19T07:34:35.465Z
INSERT INTO AD_ReplicationStrategy (AD_Client_ID, AD_Org_ID, AD_ReplicationStrategy_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (1000000, 0, 540037, TO_TIMESTAMP('2022-12-19 08:34:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 'EDI', TO_TIMESTAMP('2022-12-19 08:34:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'EDI')
ON CONFLICT ON CONSTRAINT ad_replicationstrategy_value
    DO NOTHING;
;

-- 2022-12-19T07:34:46.513Z
UPDATE AD_ReplicationStrategy SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2022-12-19 08:34:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Value='EDI'
;

-- 2022-12-19T07:34:48.906Z
UPDATE AD_ReplicationStrategy SET EXP_Processor_ID=540014,Updated=TO_TIMESTAMP('2022-12-19 08:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Value='EDI'
;


--
-- ad_client
-- 
-- note that i didn't find this field in the UAT, but we never had to check of change it after this stetup
UPDATE ad_client
SET ad_replicationstrategy_id=(SELECT AD_ReplicationStrategy_ID FROM AD_ReplicationStrategy WHERE Value = 'EDI' AND AD_Client_ID = 1000000)
WHERE ad_client_id = 1000000
;
