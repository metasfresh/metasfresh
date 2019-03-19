-- 2019-03-19T14:37:14.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=564176,Updated=TO_TIMESTAMP('2019-03-19 14:37:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541559
;

-- 2019-03-19T14:39:51.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540424,'C_Phonecall_Schema.C_Phonecall_Schema_ID = (select C_Phonecall_Schema_ID from C_Phonecall_Schema_Version where C_Phonecall_Schema_Version_ID = @C_Phonecall_Schema_Version_ID/0@',TO_TIMESTAMP('2019-03-19 14:39:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Phonecall_Schema','S',TO_TIMESTAMP('2019-03-19 14:39:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-19T14:40:05.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='C_Phonecall_Schema_Of_Version',Updated=TO_TIMESTAMP('2019-03-19 14:40:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540424
;

-- 2019-03-19T14:40:26.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540424,Updated=TO_TIMESTAMP('2019-03-19 14:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564175
;

-- 2019-03-19T14:42:41.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@C_Phonecall_Schema_ID/0@',Updated=TO_TIMESTAMP('2019-03-19 14:42:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=574935
;

-- 2019-03-19T14:43:44.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-03-19 14:43:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=574940
;

-- 2019-03-19T14:43:47.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-03-19 14:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=574941
;

-- 2019-03-19T14:50:39.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-03-19 14:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=574932
;

-- 2019-03-19T14:50:40.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-03-19 14:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=574931
;

-- 2019-03-19T15:00:24.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2019-03-19 15:00:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557935
;

-- 2019-03-19T15:00:27.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2019-03-19 15:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557938
;

-- 2019-03-19T15:00:31.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2019-03-19 15:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557937
;

