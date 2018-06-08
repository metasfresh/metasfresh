
-- 2018-04-25T18:22:37.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FormatType,IsActive,IsMultiLine,Name,Processing,Updated,UpdatedBy) VALUES (0,540019,0,540963,TO_TIMESTAMP('2018-04-25 18:22:37','YYYY-MM-DD HH24:MI:SS'),100,'T','Y','N','Import Discoutn Schema','N',TO_TIMESTAMP('2018-04-25 18:22:37','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2018-04-26T15:40:00.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559772,540019,540539,0,TO_TIMESTAMP('2018-04-26 15:39:59','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Geschäftspartner-Schlüssel',10,1,TO_TIMESTAMP('2018-04-26 15:39:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-26T15:40:27.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559770,540019,540540,0,TO_TIMESTAMP('2018-04-26 15:40:27','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Produktschlüssel',20,0,TO_TIMESTAMP('2018-04-26 15:40:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-26T15:40:47.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559793,540019,540541,0,TO_TIMESTAMP('2018-04-26 15:40:46','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','Menge',30,3,TO_TIMESTAMP('2018-04-26 15:40:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-26T15:40:50.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2018-04-26 15:40:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540540
;

-- 2018-04-26T15:41:25.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559783,540019,540542,0,TO_TIMESTAMP('2018-04-26 15:41:25','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Base_PricingSystem_Value',40,4,TO_TIMESTAMP('2018-04-26 15:41:25','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2018-04-26T16:30:31.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559794,540019,540543,0,TO_TIMESTAMP('2018-04-26 16:30:31','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Discount',50,5,TO_TIMESTAMP('2018-04-26 16:30:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-26T16:31:18.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559787,540019,540544,0,TO_TIMESTAMP('2018-04-26 16:31:17','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','Aufschlag auf Standardpreis',60,6,TO_TIMESTAMP('2018-04-26 16:31:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-26T16:31:34.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,559785,540019,540545,0,TO_TIMESTAMP('2018-04-26 16:31:34','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Zahlungskonditions-Schlüssel',70,7,TO_TIMESTAMP('2018-04-26 16:31:34','YYYY-MM-DD HH24:MI:SS'),100)
;

