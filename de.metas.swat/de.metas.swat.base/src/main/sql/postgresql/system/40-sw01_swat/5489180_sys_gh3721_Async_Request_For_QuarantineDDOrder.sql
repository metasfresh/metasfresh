-- 2018-03-22T12:12:43.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Classname='de.metas.request.service.async.spi.impl.C_Request_CreateFromDDOrder_Async',Updated=TO_TIMESTAMP('2018-03-22 12:12:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540047
;

-- 2018-03-22T12:14:09.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Classname='de.metas.request.service.async.spi.impl.C_Request_CreateFromInout_Async',Updated=TO_TIMESTAMP('2018-03-22 12:14:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540047
;

-- 2018-03-22T12:14:32.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,Description,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540060,'de.metas.request.service.async.spi.impl.C_Request_CreateFromDDOrder_Async',TO_TIMESTAMP('2018-03-22 12:14:32','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.swat','C_Request_CreateFromDDOrder','Y',TO_TIMESTAMP('2018-03-22 12:14:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-03-22T16:22:29.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540060,540079,540041,TO_TIMESTAMP('2018-03-22 16:22:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-03-22 16:22:29','YYYY-MM-DD HH24:MI:SS'),100)
;

