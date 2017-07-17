-- 2017-07-17T17:16:54.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='',Updated=TO_TIMESTAMP('2017-07-17 17:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545279
;

-- 2017-07-17T17:33:05.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-07-17 17:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544686
;

-- 2017-07-17T17:33:05.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-07-17 17:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544685
;

-- 2017-07-17T17:38:29.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2017-07-17 17:38:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12718
;

-- 2017-07-17T17:39:36.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2017-07-17 17:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12716
;

-- 2017-07-17T17:46:40.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) 
SELECT 0,3276,0,223,1000032,546573,TO_TIMESTAMP('2017-07-17 17:46:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Rabatte drucken',150,0,0,TO_TIMESTAMP('2017-07-17 17:46:40','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from AD_UI_Element where AD_UI_Element_ID=546573)
;

