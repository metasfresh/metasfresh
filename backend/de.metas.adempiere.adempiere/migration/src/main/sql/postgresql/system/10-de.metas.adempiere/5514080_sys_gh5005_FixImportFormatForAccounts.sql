-- 2019-02-28T11:36:37.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET IsActive='Y',Updated=TO_TIMESTAMP('2019-02-28 11:36:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=102
;

-- 2019-02-28T11:37:38.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,FormatType,IsActive,IsMultiLine,Name,Processing,Updated,UpdatedBy) VALUES (0,540028,0,534,TO_TIMESTAMP('2019-02-28 11:37:38','YYYY-MM-DD HH24:MI:SS'),100,'','S','Y','N','Import Accounts','N',TO_TIMESTAMP('2019-02-28 11:37:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:37:45.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET IsActive='N',Updated=TO_TIMESTAMP('2019-02-28 11:37:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=102
;

-- 2019-02-28T11:37:48.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET IsActive='Y',Updated=TO_TIMESTAMP('2019-02-28 11:37:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=102
;

-- 2019-02-28T11:38:02.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7929,540028,541030,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','A - Key',10,1,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:02.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7913,540028,541031,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','B - Name',20,2,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:02.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7933,540028,541032,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','C - Description',30,3,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:02.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7919,540028,541033,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','D - Account Type (Asset, ..)',40,4,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:02.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7925,540028,541034,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','E - Account Sign',50,5,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:02.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7927,540028,541035,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','F - Document Controlled',60,6,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:02.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7969,540028,541036,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','G - Summary Account',70,7,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:02.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7918,540028,541037,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','H - Account Assignment for default account',80,8,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:02.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7970,540028,541038,0,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','I - Parent (Summary) Account',90,9,TO_TIMESTAMP('2019-02-28 11:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:38:07.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET IsActive='N',Updated=TO_TIMESTAMP('2019-02-28 11:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=102
;

-- 2019-02-28T11:48:20.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7931, Name='ElementName',Updated=TO_TIMESTAMP('2019-02-28 11:48:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541030
;

-- 2019-02-28T11:48:29.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7929, Name='Value',Updated=TO_TIMESTAMP('2019-02-28 11:48:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541031
;

-- 2019-02-28T11:48:37.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7913, Name='Name',Updated=TO_TIMESTAMP('2019-02-28 11:48:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541032
;

-- 2019-02-28T11:48:52.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7970, Name='ParentValue',Updated=TO_TIMESTAMP('2019-02-28 11:48:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541033
;

-- 2019-02-28T11:49:11.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7919, Name='Account Type',Updated=TO_TIMESTAMP('2019-02-28 11:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541034
;

-- 2019-02-28T11:49:27.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7925, Name='Account Sign',Updated=TO_TIMESTAMP('2019-02-28 11:49:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541035
;

-- 2019-02-28T11:49:41.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET Name='Summary',Updated=TO_TIMESTAMP('2019-02-28 11:49:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541036
;

-- 2019-02-28T11:50:02.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7923, Name='PostActual',Updated=TO_TIMESTAMP('2019-02-28 11:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541037
;

-- 2019-02-28T11:50:12.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7916, Name='PostBudget',Updated=TO_TIMESTAMP('2019-02-28 11:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541038
;

-- 2019-02-28T11:50:33.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7915,540028,541039,0,TO_TIMESTAMP('2019-02-28 11:50:33','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','PostStatistical',100,9,TO_TIMESTAMP('2019-02-28 11:50:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:50:39.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=10,Updated=TO_TIMESTAMP('2019-02-28 11:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541039
;

-- 2019-02-28T11:50:56.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,7915,540028,541040,0,TO_TIMESTAMP('2019-02-28 11:50:56','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','PostStatistical',110,11,TO_TIMESTAMP('2019-02-28 11:50:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-28T11:51:07.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=7927, Name='IsDocControlled',Updated=TO_TIMESTAMP('2019-02-28 11:51:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541040
;


-- add the import format in Importer
INSERT INTO public.c_dataimport (ad_client_id, ad_impformat_id, ad_org_id, c_dataimport_id, created, createdby, isactive, updated, updatedby) VALUES (1000000, 540028, 1000000, 540001, '2019-02-26 15:55:19.000000', 100, 'Y', '2019-02-26 15:55:19.000000', 100);

