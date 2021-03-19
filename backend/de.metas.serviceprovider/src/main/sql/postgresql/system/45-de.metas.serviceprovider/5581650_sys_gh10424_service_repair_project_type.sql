-- 2020-12-15T11:59:19.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,555233,TO_TIMESTAMP('2020-12-15 13:59:19','YYYY-MM-DD HH24:MI:SS'),2188225,1000000,100,1,'Y','N','N','N','Service/Reparatur Projekt','N',1000000,TO_TIMESTAMP('2020-12-15 13:59:19','YYYY-MM-DD HH24:MI:SS'),2188225)
;

-- 2020-12-15T11:59:20.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2020-12-15 13:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188225 WHERE AD_Sequence_ID=555233
;

-- 2020-12-15T12:00:11.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_ProjectType (AD_Client_ID,AD_Org_ID,AD_Sequence_ProjectValue_ID,C_ProjectType_ID,Created,CreatedBy,IsActive,Name,ProjectCategory,Updated,UpdatedBy) VALUES (1000000,1000000,555233,540004,TO_TIMESTAMP('2020-12-15 14:00:11','YYYY-MM-DD HH24:MI:SS'),2188225,'Y','Service/Reparatur Projekt','N',TO_TIMESTAMP('2020-12-15 14:00:11','YYYY-MM-DD HH24:MI:SS'),2188225)
;

-- 2020-12-15T12:00:15.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ProjectType SET ProjectCategory='R',Updated=TO_TIMESTAMP('2020-12-15 14:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188225 WHERE C_ProjectType_ID=540004
;

