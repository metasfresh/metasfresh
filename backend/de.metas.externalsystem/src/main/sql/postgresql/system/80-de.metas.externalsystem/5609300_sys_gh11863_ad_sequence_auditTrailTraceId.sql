
-- 2021-10-14T11:04:52.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555613,TO_TIMESTAMP('2021-10-14 14:04:52','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,1,'Y','N','N','N','Audit_Trail_TraceId','N',1000000,TO_TIMESTAMP('2021-10-14 14:04:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-14T11:05:48.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='TraceId value to be used for auditTrail fillename',Updated=TO_TIMESTAMP('2021-10-14 14:05:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555613
;

-- 2021-10-14T11:05:49.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2021-10-14 14:05:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555613
;

-- 2021-10-14T11:06:08.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET CurrentNext=0,Updated=TO_TIMESTAMP('2021-10-14 14:06:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555613
;

-- 2021-10-20T07:13:36.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET DecimalPattern='00000000',Updated=TO_TIMESTAMP('2021-10-20 10:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555613
;

