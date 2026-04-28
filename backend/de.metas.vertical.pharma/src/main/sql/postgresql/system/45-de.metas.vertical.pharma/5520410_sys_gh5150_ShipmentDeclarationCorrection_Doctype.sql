-- 2019-04-24T17:39:37.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocSubType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540972,TO_TIMESTAMP('2019-04-24 17:39:37','YYYY-MM-DD HH24:MI:SS'),100,'SDD',555005,'NAR',1,'D',1000000,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','Y','N','Shipment Declaration Narcotics Correction','Shipment Declaration Narcotics',TO_TIMESTAMP('2019-04-24 17:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-24T17:39:37.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_DocType_ID=540972 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2019-04-24T17:39:37.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540972 AND rol.IsManual='N')
;


-- 2019-04-24T17:45:31.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,DecimalPattern,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,0,555008,TO_TIMESTAMP('2019-04-24 17:45:30','YYYY-MM-DD HH24:MI:SS'),100,1,100,'00000000',1,'Y','N','Y','N','Shipment Declaration Narcotics Correction','N',1000000,TO_TIMESTAMP('2019-04-24 17:45:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-24T17:45:47.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=555008,Updated=TO_TIMESTAMP('2019-04-24 17:45:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540972
;

-- 2019-04-24T17:46:04.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET IsTranslated='Y', Name='Lieferscheindoppel', PrintName='Lieferscheindoppel',Updated=TO_TIMESTAMP('2019-04-24 17:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=540972
;

-- 2019-04-24T17:46:15.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Lieferscheindoppel',Updated=TO_TIMESTAMP('2019-04-24 17:46:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540972
;

-- 2019-04-24T17:46:19.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-24 17:46:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=540972
;

-- 2019-04-24T17:46:23.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET PrintName='Shipment Declaration Narcotics Correction',Updated=TO_TIMESTAMP('2019-04-24 17:46:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=540972
;

-- 2019-04-24T17:46:33.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Lieferscheindoppel',Updated=TO_TIMESTAMP('2019-04-24 17:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540972
;

-- 2019-04-24T17:46:41.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET PrintName='Shipment Declaration Narcotics Correction',Updated=TO_TIMESTAMP('2019-04-24 17:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=540972
;

-- 2019-04-24T19:16:28.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocBaseType='SDC', DocNoSequence_ID=555008,Updated=TO_TIMESTAMP('2019-04-24 19:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540972
;

-- 2019-04-24T19:31:29.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=555005,Updated=TO_TIMESTAMP('2019-04-24 19:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540972
;

