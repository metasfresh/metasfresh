-- 2019-12-07T10:57:43.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,DecimalPattern,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,0,555092,TO_TIMESTAMP('2019-12-07 11:57:43','YYYY-MM-DD HH24:MI:SS'),100,1,100,'','Sequence to provide serial numbers for SSCC18 labels that are *not* assigned to a real M_HU in metasfresh',1,'Y','N','Y','N','SSCC18','N',1000000,TO_TIMESTAMP('2019-12-07 11:57:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-07T10:58:05.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='SSCC18_SerialNumber',Updated=TO_TIMESTAMP('2019-12-07 11:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555092
;

-- 2019-12-07T11:00:56.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='DocumentNo_SSCC18_SerialNumber',Updated=TO_TIMESTAMP('2019-12-07 12:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555092
;

-- 2019-12-07T11:32:38.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Sequence to provide serial numbers for SSCC18 labels that are *not* assigned to a real M_HU in metasfresh; can be overriden with a particular org.',Updated=TO_TIMESTAMP('2019-12-07 12:32:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555092
;

-- 2019-12-07T11:40:23.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Sequence to provide serial numbers for SSCC18 labels that are *not* assigned to a real M_HU in metasfresh; can be overriden with a particular org. Resulting numbers must be integers!',Updated=TO_TIMESTAMP('2019-12-07 12:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555092
;

