-- 2017-07-26T14:52:16.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=10999,Updated=TO_TIMESTAMP('2017-07-26 14:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540084
;

-- 2017-07-26T14:52:28.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540063,540951,TO_TIMESTAMP('2017-07-26 14:52:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','name',10,'primary',TO_TIMESTAMP('2017-07-26 14:52:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T14:52:38.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540951, SeqNo=10,Updated=TO_TIMESTAMP('2017-07-26 14:52:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541362
;

-- 2017-07-26T14:52:44.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540951, SeqNo=20,Updated=TO_TIMESTAMP('2017-07-26 14:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541363
;

-- 2017-07-26T14:53:02.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540063,540952,TO_TIMESTAMP('2017-07-26 14:53:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2017-07-26 14:53:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T14:53:09.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540952, SeqNo=10,Updated=TO_TIMESTAMP('2017-07-26 14:53:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541360
;

-- 2017-07-26T14:53:26.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540064,540953,TO_TIMESTAMP('2017-07-26 14:53:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','active',10,TO_TIMESTAMP('2017-07-26 14:53:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T14:53:35.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540064,540954,TO_TIMESTAMP('2017-07-26 14:53:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2017-07-26 14:53:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-26T14:53:45.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540953, SeqNo=10,Updated=TO_TIMESTAMP('2017-07-26 14:53:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541361
;

-- 2017-07-26T14:53:54.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540954, SeqNo=10,Updated=TO_TIMESTAMP('2017-07-26 14:53:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541359
;

-- 2017-07-26T14:53:59.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540954, SeqNo=20,Updated=TO_TIMESTAMP('2017-07-26 14:53:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541358
;

-- 2017-07-26T14:54:04.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540084
;

-- 2017-07-26T14:54:22.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 14:54:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Description' WHERE AD_Field_ID=557509 AND AD_Language='en_US'
;

-- 2017-07-26T14:54:45.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 14:54:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Value',Description='',Help='' WHERE AD_Field_ID=557512 AND AD_Language='en_US'
;

-- 2017-07-26T14:57:48.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2017-07-26 14:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557511
;

