-- 19.02.2016 13:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540036,540945,TO_TIMESTAMP('2016-02-19 13:56:57','YYYY-MM-DD HH24:MI:SS'),100,'MMR',545463,0,'de.metas.handlingunits',1000005,'N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','Saldokorrektur Lieferant','Saldokorrektur Lieferant',TO_TIMESTAMP('2016-02-19 13:56:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.02.2016 13:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540945 AND NOT EXISTS (SELECT * FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 19.02.2016 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540036,540946,TO_TIMESTAMP('2016-02-19 13:58:38','YYYY-MM-DD HH24:MI:SS'),100,'MMR',545464,0,'de.metas.handlingunits',1000005,'N','N','Y','N','N','N','Y','N','N','N','N','N','N','Y','N','Saldokorrektur Kunde','Saldokorrektur Kunde',TO_TIMESTAMP('2016-02-19 13:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.02.2016 13:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540946 AND NOT EXISTS (SELECT * FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 19.02.2016 13:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_PrintFormat_ID=540030,Updated=TO_TIMESTAMP('2016-02-19 13:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540945
;

-- 19.02.2016 14:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocBaseType='MMR',Updated=TO_TIMESTAMP('2016-02-19 14:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540945
;

-- 19.02.2016 14:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocBaseType='MMS',Updated=TO_TIMESTAMP('2016-02-19 14:59:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540945
;

-- 19.02.2016 14:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocBaseType='MMS',Updated=TO_TIMESTAMP('2016-02-19 14:59:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540946
;

-- 19.02.2016 14:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocBaseType='MMR',Updated=TO_TIMESTAMP('2016-02-19 14:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540946
;

