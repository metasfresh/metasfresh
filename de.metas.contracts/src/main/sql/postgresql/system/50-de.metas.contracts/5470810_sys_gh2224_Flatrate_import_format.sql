-- 2017-09-05T13:31:12.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FormatType,IsActive,Name,Processing,Updated,UpdatedBy) VALUES (0,540010,0,540836,TO_TIMESTAMP('2017-09-05 13:31:12','YYYY-MM-DD HH24:MI:SS'),100,'C','Y','Subscriptions (Flatrate)','N',TO_TIMESTAMP('2017-09-05 13:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-05T13:32:19.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557121,540010,540106,0,TO_TIMESTAMP('2017-09-05 13:32:19','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Geschäftspartner-Schlüssel',10,1,TO_TIMESTAMP('2017-09-05 13:32:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-05T13:33:33.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557125,540010,540107,0,TO_TIMESTAMP('2017-09-05 13:33:33','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Vertragsbedingungen Key',20,2,TO_TIMESTAMP('2017-09-05 13:33:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-05T13:36:08.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557119,540010,540108,0,TO_TIMESTAMP('2017-09-05 13:36:08','YYYY-MM-DD HH24:MI:SS'),100,'YYYY-MM-dd','D','.','N',0,'Y','Anfangsdatum',30,3,TO_TIMESTAMP('2017-09-05 13:36:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-05T13:36:29.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557120,540010,540109,0,TO_TIMESTAMP('2017-09-05 13:36:29','YYYY-MM-DD HH24:MI:SS'),100,'YYYY-MM-dd','D','.','N',0,'Y','Enddatum',40,4,TO_TIMESTAMP('2017-09-05 13:36:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-05T13:47:39.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataFormat='yyyy-MM-dd',Updated=TO_TIMESTAMP('2017-09-05 13:47:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540108
;

-- 2017-09-05T13:47:45.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataFormat='yyyy-MM-dd',Updated=TO_TIMESTAMP('2017-09-05 13:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540109
;

