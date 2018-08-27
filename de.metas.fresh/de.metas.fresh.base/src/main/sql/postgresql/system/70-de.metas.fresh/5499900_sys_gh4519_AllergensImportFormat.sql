
-- 2018-08-23T18:14:23.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FormatType,IsActive,IsMultiLine,Name,Processing,Updated,UpdatedBy) VALUES (0,540022,0,532,TO_TIMESTAMP('2018-08-23 18:14:23','YYYY-MM-DD HH24:MI:SS'),100,'C','Y','N','Product Allergens','N',TO_TIMESTAMP('2018-08-23 18:14:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-23T18:29:32.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7856,540022,540613,0,TO_TIMESTAMP('2018-08-23 18:29:31','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','Category_ID',10,0,TO_TIMESTAMP('2018-08-23 18:29:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-23T18:35:32.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7826,540022,540614,0,TO_TIMESTAMP('2018-08-23 18:35:32','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Product_category',20,0,TO_TIMESTAMP('2018-08-23 18:35:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-23T18:36:15.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7841,540022,540615,0,TO_TIMESTAMP('2018-08-23 18:36:14','YYYY-MM-DD HH24:MI:SS'),100,'N','.','N',0,'Y','m_product_ID',30,0,TO_TIMESTAMP('2018-08-23 18:36:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-23T18:36:34.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7864,540022,540616,0,TO_TIMESTAMP('2018-08-23 18:36:34','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','product_Artikelnummer',40,0,TO_TIMESTAMP('2018-08-23 18:36:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-23T18:36:53.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7819,540022,540617,0,TO_TIMESTAMP('2018-08-23 18:36:53','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Product_artikelname',50,0,TO_TIMESTAMP('2018-08-23 18:36:53','YYYY-MM-DD HH24:MI:SS'),100)
;








-- 2018-08-24T17:05:23.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560797,540022,540618,0,TO_TIMESTAMP('2018-08-24 17:05:23','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','DE-Zutaten',60,0,TO_TIMESTAMP('2018-08-24 17:05:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-24T17:05:53.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET Name='Product Ingredients and Customer Label',Updated=TO_TIMESTAMP('2018-08-24 17:05:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540022
;

-- 2018-08-24T17:06:20.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560798,540022,540619,0,TO_TIMESTAMP('2018-08-24 17:06:20','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','DE-Auszeichnungsname',70,0,TO_TIMESTAMP('2018-08-24 17:06:20','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2018-08-27T15:05:44.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=60,Updated=TO_TIMESTAMP('2018-08-27 15:05:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540619
;

-- 2018-08-27T15:06:22.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560805,540022,540620,0,TO_TIMESTAMP('2018-08-27 15:06:21','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','FR-Auszeichnungsname',70,0,TO_TIMESTAMP('2018-08-27 15:06:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-27T15:06:36.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560803,540022,540621,0,TO_TIMESTAMP('2018-08-27 15:06:36','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','FR-Zutaten',80,0,TO_TIMESTAMP('2018-08-27 15:06:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-27T15:07:02.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560806,540022,540622,0,TO_TIMESTAMP('2018-08-27 15:07:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','IT-Auszeichnungsname',90,0,TO_TIMESTAMP('2018-08-27 15:07:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-27T15:07:19.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560804,540022,540623,0,TO_TIMESTAMP('2018-08-27 15:07:19','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','IT-Zutaten',100,0,TO_TIMESTAMP('2018-08-27 15:07:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-27T15:07:46.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560800,540022,540624,0,TO_TIMESTAMP('2018-08-27 15:07:46','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Allergen DE',110,0,TO_TIMESTAMP('2018-08-27 15:07:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-27T15:08:06.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,560802,540022,540625,0,TO_TIMESTAMP('2018-08-27 15:08:06','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Allergen FR',120,0,TO_TIMESTAMP('2018-08-27 15:08:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-27T15:09:10.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=80,Updated=TO_TIMESTAMP('2018-08-27 15:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540620
;

-- 2018-08-27T15:09:14.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=90,Updated=TO_TIMESTAMP('2018-08-27 15:09:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540621
;

-- 2018-08-27T15:09:19.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=100,Updated=TO_TIMESTAMP('2018-08-27 15:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540622
;

-- 2018-08-27T15:09:23.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=110,Updated=TO_TIMESTAMP('2018-08-27 15:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540623
;

-- 2018-08-27T15:09:27.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=120,Updated=TO_TIMESTAMP('2018-08-27 15:09:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540624
;

-- 2018-08-27T15:09:31.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=130,Updated=TO_TIMESTAMP('2018-08-27 15:09:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540625
;

