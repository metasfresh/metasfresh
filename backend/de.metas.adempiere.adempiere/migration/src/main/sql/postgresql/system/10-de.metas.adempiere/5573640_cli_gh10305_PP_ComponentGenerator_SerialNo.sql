-- 2020-11-27T20:53:16.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO PP_ComponentGenerator (AD_Client_ID,AD_JavaClass_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Product_ID,PP_ComponentGenerator_ID,Updated,UpdatedBy) VALUES (1000000,540057,1000000,TO_TIMESTAMP('2020-11-27 22:53:16','YYYY-MM-DD HH24:MI:SS'),2188225,'Y',540482,540002,TO_TIMESTAMP('2020-11-27 22:53:16','YYYY-MM-DD HH24:MI:SS'),2188225)
;

-- 2020-11-27T20:54:01.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,555227,TO_TIMESTAMP('2020-11-27 22:54:01','YYYY-MM-DD HH24:MI:SS'),2188225,1000000,100,1,'Y','N','N','N','Router SerialNo','N',1000000,TO_TIMESTAMP('2020-11-27 22:54:01','YYYY-MM-DD HH24:MI:SS'),2188225)
;

-- 2020-11-27T20:54:19.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE PP_ComponentGenerator SET AD_Sequence_ID=555227,Updated=TO_TIMESTAMP('2020-11-27 22:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188225 WHERE PP_ComponentGenerator_ID=540002
;

-- 2020-11-27T20:58:13.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2020-11-27 22:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188225 WHERE AD_Sequence_ID=555227
;

