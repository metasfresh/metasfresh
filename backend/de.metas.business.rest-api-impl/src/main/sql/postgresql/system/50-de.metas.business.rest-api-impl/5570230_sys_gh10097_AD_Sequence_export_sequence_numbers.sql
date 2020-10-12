-- 2020-10-11T18:40:10.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,0,555208,TO_TIMESTAMP('2020-10-11 20:40:10','YYYY-MM-DD HH24:MI:SS'),100,1000,100,'Each invokcation of the shipmentCandidates REST API with exports any candidates will get a number from this sequence',1,'Y','N','Y','N','ShipmentCandidate_Export','N',1000000,TO_TIMESTAMP('2020-10-11 20:40:10','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2020-10-11T18:57:00.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555209,TO_TIMESTAMP('2020-10-11 20:57:00','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,'Each invokcation of the shipmentCandidates REST API with exports any candidates will get a number from this sequence',1,'Y','N','Y','Y','ShipmentCandidate_Export_Id','N',1000000,TO_TIMESTAMP('2020-10-11 20:57:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-11T19:07:11.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555210,TO_TIMESTAMP('2020-10-11 21:07:10','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,'Each invokcation of the receiptCandidates REST API that exports any candidates will get a number from this sequence',1,'Y','N','Y','Y','ReceiptCandidate_Export_Id','N',1000000,TO_TIMESTAMP('2020-10-11 21:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-11T19:07:18.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Each invokcation of the shipmentCandidates REST API that exports any candidates will get a number from this sequence',Updated=TO_TIMESTAMP('2020-10-11 21:07:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555209
;

-- 2020-10-11T19:07:27.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET CurrentNext=1,Updated=TO_TIMESTAMP('2020-10-11 21:07:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555209
;

-- 2020-10-11T19:07:31.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET CurrentNext=1,Updated=TO_TIMESTAMP('2020-10-11 21:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555210
;

-- 2020-10-12T05:20:23.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555211,TO_TIMESTAMP('2020-10-12 07:20:23','YYYY-MM-DD HH24:MI:SS'),100,1,100,'Each invocation of the manufactoringOrders REST API that exports any order will get a number from this sequence',1,'Y','N','Y','Y','ManufacturingOrder_Export_SeqNo','N',1000000,TO_TIMESTAMP('2020-10-12 07:20:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-12T05:20:29.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Each invocation of the receiptCandidates REST API that exports any candidates will get a number from this sequence',Updated=TO_TIMESTAMP('2020-10-12 07:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555210
;

-- 2020-10-12T05:20:34.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Each invocation of the shipmentCandidates REST API that exports any candidates will get a number from this sequence',Updated=TO_TIMESTAMP('2020-10-12 07:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555209
;

UPDATE AD_Sequence SET Name='ShipmentCandidate_Export_SeqNo' WHERE Name='ShipmentCandidate_Export_Id';
UPDATE AD_Sequence SET Name='ReceiptCandidate_Export_SeqNo' WHERE Name='ReceiptCandidate_Export_Id';
