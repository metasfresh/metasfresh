-- 01.03.2016 13:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540040,'de.metas.procurement.base.event.async.PMM_Qty_Event_Processor',TO_TIMESTAMP('2016-03-01 13:07:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','de.metas.procurement.base.event.async.PMM_Qty_Event_Processor','Y',TO_TIMESTAMP('2016-03-01 13:07:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.03.2016 13:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540040,540064,540039,TO_TIMESTAMP('2016-03-01 13:07:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2016-03-01 13:07:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.03.2016 13:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Name='Procurement',Updated=TO_TIMESTAMP('2016-03-01 13:07:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540039
;

-- 01.03.2016 13:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Classname='de.metas.procurement.base.event.async.PMM_QtyReport_Event_Processor', InternalName='de.metas.procurement.base.event.async.PMM_QtyReport_Event_Processor',Updated=TO_TIMESTAMP('2016-03-01 13:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540040
;

