-- 2020-10-23T06:48:58.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2020-10-23 08:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540015
;

-- 2020-10-23T06:49:31.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2020-10-23 08:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540049
;

-- 2020-10-23T06:50:01.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540053,TO_TIMESTAMP('2020-10-23 08:50:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'M_ShipmentScheduleQueue',1,TO_TIMESTAMP('2020-10-23 08:50:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-23T06:50:37.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540025,540085,540053,TO_TIMESTAMP('2020-10-23 08:50:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2020-10-23 08:50:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-23T06:50:54.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540026,540086,540053,TO_TIMESTAMP('2020-10-23 08:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2020-10-23 08:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-23T06:51:17.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540011,540087,540053,TO_TIMESTAMP('2020-10-23 08:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2020-10-23 08:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-23T06:54:33.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2020-10-23 08:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540050
;


-- 2020-10-23T07:02:10.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Description='contains the processors that work deal with shipment candidates; goal: prevent different processors from doing their thing to the same M_ShipmentSchedule concurrently',Updated=TO_TIMESTAMP('2020-10-23 09:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540053
;

-- 2020-10-23T07:02:34.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Description='Deactivated in favor of M_ShipmentScheduleQueue',Updated=TO_TIMESTAMP('2020-10-23 09:02:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540049
;

-- 2020-10-23T07:02:39.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET IsActive='Y', Description='Deactivated in favor of M_ShipmentScheduleQueue',Updated=TO_TIMESTAMP('2020-10-23 09:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540015
;

-- 2020-10-23T07:02:42.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET IsActive='N',Updated=TO_TIMESTAMP('2020-10-23 09:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540015
;

-- 2020-10-23T07:02:48.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Description='Deactivated in favor of M_ShipmentScheduleQueue',Updated=TO_TIMESTAMP('2020-10-23 09:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540050
;

-- 2020-10-23T07:03:00.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Description='Contains the processors that work deal with shipment candidates; goal: prevent different processors from doing their thing to the same M_ShipmentSchedule concurrently',Updated=TO_TIMESTAMP('2020-10-23 09:03:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540053
;

-- 2020-10-23T07:03:26.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET IsActive='N', Description='not used anymore',Updated=TO_TIMESTAMP('2020-10-23 09:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540043
;

-- 2020-10-23T07:07:08.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET IsActive='N', Description='Deactivated in favor of M_ShipmentScheduleQueue',Updated=TO_TIMESTAMP('2020-10-23 09:07:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540014
;

-- 2020-10-23T07:07:38.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540010,540088,540053,TO_TIMESTAMP('2020-10-23 09:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2020-10-23 09:07:38','YYYY-MM-DD HH24:MI:SS'),100)
;

