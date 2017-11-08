-- 2017-09-28T14:00:17.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2098,0,180,1000040,548950,'F',TO_TIMESTAMP('2017-09-28 14:00:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Notiz / Zeilentext',100,0,0,TO_TIMESTAMP('2017-09-28 14:00:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-28T14:01:30.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-09-28 14:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548950
;

