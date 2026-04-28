-- 2019-04-19T15:53:11.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540970,TO_TIMESTAMP('2019-04-19 15:53:10','YYYY-MM-DD HH24:MI:SS'),100,'SDD',1,'de.metas.vertical.pharma',1000000,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','Shipment Declaration Narcotics','Shipment Declaration Narcotics',TO_TIMESTAMP('2019-04-19 15:53:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-19T15:53:11.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_DocType_ID=540970 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2019-04-19T15:53:11.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540970 AND rol.IsManual='N')
;

-- 2019-04-19T16:08:08.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='NAR',Updated=TO_TIMESTAMP('2019-04-19 16:08:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540970
;

-- 2019-04-19T16:17:44.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,DecimalPattern,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,0,555005,TO_TIMESTAMP('2019-04-19 16:17:44','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,'00000000',1,'Y','N','N','N','Shipment Declaration Narcotics','N',1000000,TO_TIMESTAMP('2019-04-19 16:17:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-19T16:17:55.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET CurrentNext=1, IsAutoSequence='Y',Updated=TO_TIMESTAMP('2019-04-19 16:17:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555005
;

-- 2019-04-19T16:25:06.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=555005,Updated=TO_TIMESTAMP('2019-04-19 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540970
;



update c_doctype set isSOTrx = 'Y' where c_doctype_id = 540970;



-- 2019-04-24T16:07:36.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET IsTranslated='Y', Name='Betäubungsmittel Abgabemeldung', PrintName='Betäubungsmittel Abgabemeldung',Updated=TO_TIMESTAMP('2019-04-24 16:07:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=540970
;

-- 2019-04-24T16:07:42.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET IsTranslated='Y', Name='Betäubungsmittel Abgabemeldung', PrintName='Betäubungsmittel Abgabemeldung',Updated=TO_TIMESTAMP('2019-04-24 16:07:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=540970
;

