-- 2017-10-11T15:56:36.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Liefereinschränkung',Updated=TO_TIMESTAMP('2017-10-11 15:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560384
;

-- 2017-10-11T15:56:45.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 15:56:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=560384 AND AD_Language='en_US'
;

-- 2017-10-11T15:58:00.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560384,0,500221,540972,549073,'F',TO_TIMESTAMP('2017-10-11 15:58:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Liefereinschränkung',60,0,0,TO_TIMESTAMP('2017-10-11 15:58:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-11T15:58:35.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560383,0,500221,540972,549074,'F',TO_TIMESTAMP('2017-10-11 15:58:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferstopp',70,0,0,TO_TIMESTAMP('2017-10-11 15:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-11T15:58:47.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferstopp',Updated=TO_TIMESTAMP('2017-10-11 15:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560383
;

-- 2017-10-11T15:58:56.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 15:58:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=560383 AND AD_Language='en_US'
;

-- 2017-10-11T15:58:59.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 15:58:59','YYYY-MM-DD HH24:MI:SS'),Name='Delivery Stop' WHERE AD_Field_ID=560383 AND AD_Language='en_US'
;

-- 2017-10-11T15:59:19.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2017-10-11 15:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549074
;

-- 2017-10-11T16:00:55.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540050, SeqNo=90,Updated=TO_TIMESTAMP('2017-10-11 16:00:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549073
;

-- 2017-10-11T16:02:07.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge',Updated=TO_TIMESTAMP('2017-10-11 16:02:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553766
;

-- 2017-10-11T16:02:22.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 16:02:22','YYYY-MM-DD HH24:MI:SS'),Name='Qty' WHERE AD_Field_ID=553766 AND AD_Language='en_US'
;

