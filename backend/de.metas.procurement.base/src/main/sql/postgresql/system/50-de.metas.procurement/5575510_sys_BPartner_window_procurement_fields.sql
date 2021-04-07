-- 2021-01-05T09:08:43.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540338,544753,TO_TIMESTAMP('2021-01-05 11:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','procurement',20,TO_TIMESTAMP('2021-01-05 11:08:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-05T09:09:21.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=544753, SeqNo=10,Updated=TO_TIMESTAMP('2021-01-05 11:09:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000316
;

-- 2021-01-05T09:10:11.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,565102,0,496,576282,544753,'F',TO_TIMESTAMP('2021-01-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Passwort in Mengenmeldung App',20,0,0,TO_TIMESTAMP('2021-01-05 11:10:11','YYYY-MM-DD HH24:MI:SS'),100)
;

