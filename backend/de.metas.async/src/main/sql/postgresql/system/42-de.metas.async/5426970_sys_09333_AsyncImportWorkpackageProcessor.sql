-- 13.09.2015 01:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540027,'org.adempiere.impexp.spi.impl.AsyncImportWorkpackageProcessor',TO_TIMESTAMP('2015-09-13 01:38:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y',TO_TIMESTAMP('2015-09-13 01:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.09.2015 01:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Imports data from import tables (e.g. I_BPartner).',Updated=TO_TIMESTAMP('2015-09-13 01:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540027
;

-- 13.09.2015 01:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540027,TO_TIMESTAMP('2015-09-13 01:39:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'ImportData_From_ImportTables',1,TO_TIMESTAMP('2015-09-13 01:39:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.09.2015 01:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540027,540051,540027,TO_TIMESTAMP('2015-09-13 01:40:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2015-09-13 01:40:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.09.2015 02:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543614,0,TO_TIMESTAMP('2015-09-13 02:42:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','{0} records imported, {1} records updated to {2} table.','I',TO_TIMESTAMP('2015-09-13 02:42:43','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.impexp.async.Event_RecordsImported')
;

-- 13.09.2015 02:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543614 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

