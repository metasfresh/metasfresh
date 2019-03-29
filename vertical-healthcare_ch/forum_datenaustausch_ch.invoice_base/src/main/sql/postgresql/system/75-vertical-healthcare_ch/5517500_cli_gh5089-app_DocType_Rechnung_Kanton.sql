-- 2019-03-27T16:57:23.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,Description,DocBaseType,DocNoSequence_ID,DocSubType,DocumentCopies,DocumentNote,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) 
VALUES (1000000,0,1000015,540967,TO_TIMESTAMP('2019-03-27 16:57:23','YYYY-MM-DD HH24:MI:SS'),100,'https://github.com/metasfresh/metasfresh/issues/4575
https://github.com/metasfresh/metasfresh/issues/5089','ARI',554604,'KV',0,'','de.metas.vertical.healthcare.forum_datenaustausch_ch',1000003,'N','N','Y','Y','N','Y','N','Y','Y','N','N','N','N','N','Y','N','Rechnung - Kantonn','Rechnung - Kanton',TO_TIMESTAMP('2019-03-27 16:57:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T16:57:23.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_DocType_ID=540967 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2019-03-27T16:57:23.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540967 AND rol.IsManual='N')
;

-- 2019-03-27T16:57:32.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Rechnung - Kanton',Updated=TO_TIMESTAMP('2019-03-27 16:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540967
;

-- 2019-03-27T17:04:26.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='KT',Updated=TO_TIMESTAMP('2019-03-27 17:04:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540967
;

-- 2019-03-27T17:34:15.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=NULL,Updated=TO_TIMESTAMP('2019-03-27 17:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540967
;

-- 2019-03-27T17:37:21.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,CustomSequenceNoProvider_JavaClass_ID,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,Prefix,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,0,554856,TO_TIMESTAMP('2019-03-27 17:37:21','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,540049,'https://github.com/metasfresh/metasfresh/issues/4575 and https://github.com/metasfresh/metasfresh/issues/5089',1,'Y','N','Y','N','DocumentNo_Healthcare-CH_Invoice_Kanton','KT_','N',1000000,TO_TIMESTAMP('2019-03-27 17:37:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-27T17:38:32.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=554856,Updated=TO_TIMESTAMP('2019-03-27 17:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540967
;

