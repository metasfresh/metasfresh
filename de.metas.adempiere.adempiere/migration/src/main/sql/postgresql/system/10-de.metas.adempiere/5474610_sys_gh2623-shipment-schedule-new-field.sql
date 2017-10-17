-- 2017-10-17T11:46:29.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,560386,0,500221,540972,549100,'F',TO_TIMESTAMP('2017-10-17 11:46:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geschlossen',60,0,0,TO_TIMESTAMP('2017-10-17 11:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-17T11:46:59.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-10-17 11:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548125
;

-- 2017-10-17T11:47:02.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-10-17 11:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549100
;

-- 2017-10-17T11:47:45.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-17 11:47:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Closed' WHERE AD_Field_ID=560386 AND AD_Language='en_US'
;

