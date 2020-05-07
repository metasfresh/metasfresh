-- 2017-08-21T09:19:08.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555406,0,500221,540972,547358,TO_TIMESTAMP('2017-08-21 09:19:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferant Streckengeschäft',60,0,0,TO_TIMESTAMP('2017-08-21 09:19:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-21T09:19:47.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferant Streckengeschäft',Updated=TO_TIMESTAMP('2017-08-21 09:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555406
;

-- 2017-08-21T09:22:14.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-21 09:22:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Vendor Dropship' WHERE AD_Field_ID=555406 AND AD_Language='en_US'
;

