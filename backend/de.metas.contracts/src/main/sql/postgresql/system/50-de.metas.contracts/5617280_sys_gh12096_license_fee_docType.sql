-- 2021-12-06T18:57:55.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543071,148,TO_TIMESTAMP('2021-12-06 20:57:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Lizenzgebühr',TO_TIMESTAMP('2021-12-06 20:57:55','YYYY-MM-DD HH24:MI:SS'),100,'LS','LS')
;

-- 2021-12-06T18:57:55.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543071 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-12-06T18:58:54.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT'', ''RD'', ''LS'')) OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC'')) OR (''@DocBaseType@''=''API'' AND (AD_Ref_List.Value IN (''QI'', ''VI'') OR AD_Ref_List.ValueName IN (''CommissionSettlement''))) OR (''@DocBaseType@''=''MOP'' AND AD_Ref_List.Value IN (''QI'', ''VI'')) OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value in (''MD'', ''VIY'')) OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS'')) OR (''@DocBaseType@''=''POO'' AND AD_Ref_List.Value = ''MED'') OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'', ''MED'')) /* fallback for the rest of the entries */',Updated=TO_TIMESTAMP('2021-12-06 20:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2021-12-06T18:51:04.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541025,TO_TIMESTAMP('2021-12-06 20:51:04','YYYY-MM-DD HH24:MI:SS'),100,'ARI',1,'de.metas.contracts.commission',1000003,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Lizenzgebühr','Lizenzgebühr',TO_TIMESTAMP('2021-12-06 20:51:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-06T18:51:05.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541025 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2021-12-06T18:51:05.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541025 AND rol.IsManual='N')
;

-- 2021-12-06T18:52:56.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET Name='License fee',Updated=TO_TIMESTAMP('2021-12-06 20:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541025
;

-- 2021-12-06T18:53:02.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET PrintName='License fee',Updated=TO_TIMESTAMP('2021-12-06 20:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541025
;

-- 2021-12-06T18:59:55.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='LS',Updated=TO_TIMESTAMP('2021-12-06 20:59:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541025
;

-- 2021-12-06T19:14:59.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2021-12-06 21:14:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541025
;

-- 2021-12-06T19:48:46.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,555672,TO_TIMESTAMP('2021-12-06 21:48:46','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,1,'Y','N','N','N','Lizenzgebühr Doc No','N',1000000,TO_TIMESTAMP('2021-12-06 21:48:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-06T19:48:48.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2021-12-06 21:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555672
;

-- 2021-12-06T19:50:02.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=555672,Updated=TO_TIMESTAMP('2021-12-06 21:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541025
;

-- 2021-12-07T10:32:15.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-12-07 12:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541025
;

