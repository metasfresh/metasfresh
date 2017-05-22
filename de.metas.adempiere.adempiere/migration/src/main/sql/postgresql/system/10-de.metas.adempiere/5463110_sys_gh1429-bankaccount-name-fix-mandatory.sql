-- 2017-05-19T17:27:48.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-05-19 17:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5232
;

-- 2017-05-19T17:28:13.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543552
;

-- 2017-05-19T17:28:43.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558327,0,540812,540335,544481,TO_TIMESTAMP('2017-05-19 17:28:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',60,0,0,TO_TIMESTAMP('2017-05-19 17:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

