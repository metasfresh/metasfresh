-- 2019-01-24T12:41:09.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554782,TO_TIMESTAMP('2019-01-24 12:41:09','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,'Sequence for update events that are send to the MSV3 server. The server can use them to discard outdated events or deal correctly with outdated records.',1,'Y','N','Y','Y','MSV3_EventVersion','N',1000000,TO_TIMESTAMP('2019-01-24 12:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-24T12:41:29.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET CurrentNext=1,Updated=TO_TIMESTAMP('2019-01-24 12:41:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554782
;

