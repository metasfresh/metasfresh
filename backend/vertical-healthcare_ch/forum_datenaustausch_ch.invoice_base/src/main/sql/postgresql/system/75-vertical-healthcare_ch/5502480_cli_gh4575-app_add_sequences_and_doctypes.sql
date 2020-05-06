-- 2018-09-19T09:26:02.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (CurrentNext,IsAudited,Prefix,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,Description,AD_Client_ID,Name,AD_Org_ID,CustomSequenceNoProvider_JavaClass_ID) VALUES (1000000,'N','EA_','N','Y','N',TO_TIMESTAMP('2018-09-19 09:26:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2018-09-19 09:26:01','YYYY-MM-DD HH24:MI:SS'),100,554602,'https://github.com/metasfresh/metasfresh/issues/4575',1000000,'DocumentNo_BillCare_Invoice_Klientenanteil',0,540049)
;

-- 2018-09-19T09:26:23.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (CurrentNext,IsAudited,Prefix,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,Description,AD_Client_ID,Name,AD_Org_ID,CustomSequenceNoProvider_JavaClass_ID) VALUES (1000000,'N','GM_','N','Y','N',TO_TIMESTAMP('2018-09-19 09:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2018-09-19 09:26:22','YYYY-MM-DD HH24:MI:SS'),100,554603,'https://github.com/metasfresh/metasfresh/issues/4575',1000000,'DocumentNo_BillCare_Invoice_Gemeinde',0,540049)
;

-- 2018-09-19T09:26:36.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (CurrentNext,IsAudited,Prefix,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,Description,AD_Client_ID,Name,AD_Org_ID,CustomSequenceNoProvider_JavaClass_ID) VALUES (1000000,'N','KV_','N','Y','N',TO_TIMESTAMP('2018-09-19 09:26:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2018-09-19 09:26:36','YYYY-MM-DD HH24:MI:SS'),100,554604,'https://github.com/metasfresh/metasfresh/issues/4575',1000000,'DocumentNo_BillCare_Invoice_Krankenversicherung',0,540049)
;

-- 2018-09-19T09:28:32.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (CreatedBy,IsDocNoControlled,AD_Client_ID,IsActive,Created,DocNoSequence_ID,GL_Category_ID,PrintName,HasCharges,HasProforma,DocumentCopies,IsSOTrx,AD_PrintFormat_ID,IsDefaultCounterDoc,IsPickQAConfirm,IsShipConfirm,IsInTransit,IsSplitWhenDifference,IsCreateCounter,IsIndexed,IsDefault,UpdatedBy,Updated,C_DocType_ID,DocBaseType,AD_Org_ID,Name,IsCopyDescriptionToDocument,DocumentNote,EntityType,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete) VALUES (100,'Y',1000000,'Y',TO_TIMESTAMP('2018-09-19 09:28:32','YYYY-MM-DD HH24:MI:SS'),554602,1000003,'Rechnung','N','N',0,'Y',1000015,'N','N','N','N','N','N','Y','Y',100,TO_TIMESTAMP('2018-09-19 09:28:32','YYYY-MM-DD HH24:MI:SS'),540962,'ARI',0,'Rechnung - Klientenanteil','Y','','de.metas.vertical.healthcare.forum_datenaustausch_ch','N','N')
;

-- 2018-09-19T09:28:32.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, PrintName,Name,Description,DocumentNote, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.PrintName,t.Name,t.Description,t.DocumentNote, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540962 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-09-19T09:28:32.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540962 AND rol.IsManual='N')
;

-- 2018-09-19T09:28:52.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (CreatedBy,IsDocNoControlled,AD_Client_ID,IsActive,Created,DocNoSequence_ID,GL_Category_ID,PrintName,HasCharges,HasProforma,DocumentCopies,IsSOTrx,AD_PrintFormat_ID,IsDefaultCounterDoc,IsPickQAConfirm,IsShipConfirm,IsInTransit,IsSplitWhenDifference,IsCreateCounter,IsIndexed,IsDefault,UpdatedBy,Updated,C_DocType_ID,DocBaseType,AD_Org_ID,Name,IsCopyDescriptionToDocument,DocumentNote,EntityType,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete) VALUES (100,'Y',1000000,'Y',TO_TIMESTAMP('2018-09-19 09:28:52','YYYY-MM-DD HH24:MI:SS'),554603,1000003,'Rechnung','N','N',0,'Y',1000015,'N','N','N','N','N','N','Y','Y',100,TO_TIMESTAMP('2018-09-19 09:28:52','YYYY-MM-DD HH24:MI:SS'),540963,'ARI',0,'Rechnung - Gemeinde','Y','','de.metas.vertical.healthcare.forum_datenaustausch_ch','N','N')
;

-- 2018-09-19T09:28:52.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, PrintName,Name,Description,DocumentNote, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.PrintName,t.Name,t.Description,t.DocumentNote, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540963 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-09-19T09:28:52.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540963 AND rol.IsManual='N')
;

-- 2018-09-19T09:29:18.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (CreatedBy,IsDocNoControlled,AD_Client_ID,IsActive,Created,DocNoSequence_ID,GL_Category_ID,PrintName,HasCharges,HasProforma,DocumentCopies,IsSOTrx,AD_PrintFormat_ID,IsDefaultCounterDoc,IsPickQAConfirm,IsShipConfirm,IsInTransit,IsSplitWhenDifference,IsCreateCounter,IsIndexed,IsDefault,UpdatedBy,Updated,C_DocType_ID,DocBaseType,AD_Org_ID,Name,IsCopyDescriptionToDocument,DocumentNote,EntityType,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete) VALUES (100,'Y',1000000,'Y',TO_TIMESTAMP('2018-09-19 09:29:18','YYYY-MM-DD HH24:MI:SS'),554604,1000003,'Rechnung','N','N',0,'Y',1000015,'N','N','N','N','N','N','Y','Y',100,TO_TIMESTAMP('2018-09-19 09:29:18','YYYY-MM-DD HH24:MI:SS'),540964,'ARI',0,'Rechnung - Krankenversicherung','Y','','de.metas.vertical.healthcare.forum_datenaustausch_ch','N','N')
;

-- 2018-09-19T09:29:18.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, PrintName,Name,Description,DocumentNote, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.PrintName,t.Name,t.Description,t.DocumentNote, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540964 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-09-19T09:29:18.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540964 AND rol.IsManual='N')
;

