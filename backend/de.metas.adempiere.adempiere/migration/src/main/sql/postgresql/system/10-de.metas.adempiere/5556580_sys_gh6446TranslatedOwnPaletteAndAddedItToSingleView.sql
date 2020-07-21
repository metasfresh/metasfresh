-- 2020-04-08T13:33:52.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Own Palette', PrintName='Own Palette',Updated=TO_TIMESTAMP('2020-04-08 16:33:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542641 AND AD_Language='en_GB'
;

-- 2020-04-08T13:33:53.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542641,'en_GB') 
;

-- 2020-04-08T13:33:57.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Own Palette', PrintName='Own Palette',Updated=TO_TIMESTAMP('2020-04-08 16:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542641 AND AD_Language='en_US'
;

-- 2020-04-08T13:33:57.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542641,'en_US') 
;

-- 2020-04-08T13:49:47.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,565245,0,540508,567192,540915,'F',TO_TIMESTAMP('2020-04-08 16:49:46','YYYY-MM-DD HH24:MI:SS'),100,'If true, then the packing material''s owner is "us" (the guys who ordered it). If false, then the packing material''s owner is the PO''s partner.','Y','N','N','Y','N','N','N',0,'eigene Gebinde',70,0,0,TO_TIMESTAMP('2020-04-08 16:49:46','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2020-04-08T14:02:41.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Set Own Palette',Updated=TO_TIMESTAMP('2020-04-08 17:02:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=541160
;
