-- 2018-06-29T17:27:19.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FormatType,IsActive,IsMultiLine,Name,Processing,Updated,UpdatedBy) VALUES (0,540020,0,540999,TO_TIMESTAMP('2018-06-29 17:27:19','YYYY-MM-DD HH24:MI:SS'),100,'C','Y','N','Datev Payment','N',TO_TIMESTAMP('2018-06-29 17:27:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-29T17:28:05.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560580,540020,540601,0,TO_TIMESTAMP('2018-06-29 17:28:05','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','Zahlungsbetrag',10,5,TO_TIMESTAMP('2018-06-29 17:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-29T17:28:32.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560564,540020,540602,0,TO_TIMESTAMP('2018-06-29 17:28:32','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','S/H',20,6,TO_TIMESTAMP('2018-06-29 17:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-29T17:28:57.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560543,540020,540603,0,TO_TIMESTAMP('2018-06-29 17:28:57','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Gegenkonto',30,8,TO_TIMESTAMP('2018-06-29 17:28:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-29T17:29:36.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560569,540020,540604,0,TO_TIMESTAMP('2018-06-29 17:29:36','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','InvoiceNo',40,9,TO_TIMESTAMP('2018-06-29 17:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-29T17:30:43.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560562,540020,540605,0,TO_TIMESTAMP('2018-06-29 17:30:43','YYYY-MM-DD HH24:MI:SS'),100,'DD.MM.YYYY','D','.','N',0,'Y','Datum',50,11,TO_TIMESTAMP('2018-06-29 17:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-29T17:37:11.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560563,540020,540606,0,TO_TIMESTAMP('2018-06-29 17:37:11','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','Skonto',60,16,TO_TIMESTAMP('2018-06-29 17:37:11','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2018-06-29T17:52:49.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DecimalPoint=',',Updated=TO_TIMESTAMP('2018-06-29 17:52:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540606
;

-- 2018-06-29T17:52:59.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DecimalPoint=',',Updated=TO_TIMESTAMP('2018-06-29 17:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540601
;

-- 2018-06-29T17:54:40.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataFormat='dd.MM.yyyy',Updated=TO_TIMESTAMP('2018-06-29 17:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540605
;



-- 2018-06-29T18:18:57.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataType='N',Updated=TO_TIMESTAMP('2018-06-29 18:18:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540603
;


