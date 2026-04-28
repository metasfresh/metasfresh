-- delete them just in case they were already added
delete from AD_UI_Element where AD_UI_Element_ID in (549480, 549481);


-- 2017-11-29T12:29:35.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,560566,0,441,541262,549481,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Pharmacie Permission',540,530,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-29T12:29:35.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,560564,0,441,541262,549480,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','Y','N','Pharmaproductpermlaw52',530,520,0,TO_TIMESTAMP('2017-11-29 12:29:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-01T12:42:16.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Apotheken Zulassung',Updated=TO_TIMESTAMP('2017-12-01 12:42:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560566
;

-- 2017-12-01T12:52:11.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Arzneimittel Gesetz §52a',Updated=TO_TIMESTAMP('2017-12-01 12:52:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560564
;

-- 2017-12-01T12:58:43.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549481
;

-- 2017-12-01T12:58:43.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-12-01 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549480
;

