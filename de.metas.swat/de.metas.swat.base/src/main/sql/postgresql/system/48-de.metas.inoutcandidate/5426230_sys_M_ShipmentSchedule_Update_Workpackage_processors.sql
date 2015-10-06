-- 07.09.2015 23:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540025,'de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor',TO_TIMESTAMP('2015-09-07 23:00:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y',TO_TIMESTAMP('2015-09-07 23:00:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2015 23:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540026,'de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor',TO_TIMESTAMP('2015-09-07 23:00:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y',TO_TIMESTAMP('2015-09-07 23:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2015 23:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540026,TO_TIMESTAMP('2015-09-07 23:02:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'M_ShipmentSchedule_Update',1,TO_TIMESTAMP('2015-09-07 23:02:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2015 23:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540026,540049,540026,TO_TIMESTAMP('2015-09-07 23:02:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2015-09-07 23:02:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2015 23:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540025,540050,540026,TO_TIMESTAMP('2015-09-07 23:02:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2015-09-07 23:02:59','YYYY-MM-DD HH24:MI:SS'),100)
;


update AD_Scheduler set IsActive='N', updated=now(), updatedBy=0 where AD_Scheduler_ID=1000000 and name='Lieferdispo aktualisieren';

