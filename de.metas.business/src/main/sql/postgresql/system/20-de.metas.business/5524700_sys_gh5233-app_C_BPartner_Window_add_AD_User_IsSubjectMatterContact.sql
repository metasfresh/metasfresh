-- 2019-06-14T18:48:13.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-06-14 18:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540455
;

-- 2019-06-14T18:49:17.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=540455 AND AD_Language='fr_CH'
;

-- 2019-06-14T18:49:20.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=540455 AND AD_Language='it_CH'
;

-- 2019-06-14T18:49:29.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=540455 AND AD_Language='en_GB'
;

-- 2019-06-14T18:49:43.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Fachlicher Kontakt', PrintName='Fachlicher Kontakt',Updated=TO_TIMESTAMP('2019-06-14 18:49:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540455 AND AD_Language='de_CH'
;

-- 2019-06-14T18:49:43.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540455,'de_CH') 
;

-- 2019-06-14T18:49:56.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Subject matter contact', PrintName='Subject matter contact',Updated=TO_TIMESTAMP('2019-06-14 18:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540455 AND AD_Language='en_US'
;

-- 2019-06-14T18:49:56.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540455,'en_US') 
;

-- 2019-06-14T18:50:15.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Fachlicher Kontakt', PrintName='Fachlicher Kontakt',Updated=TO_TIMESTAMP('2019-06-14 18:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540455 AND AD_Language='de_DE'
;

-- 2019-06-14T18:50:15.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540455,'de_DE') 
;

-- 2019-06-14T18:50:15.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(540455,'de_DE') 
;

-- 2019-06-14T18:50:15.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSubjectMatterContact', Name='Fachlicher Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=540455
;

-- 2019-06-14T18:50:15.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSubjectMatterContact', Name='Fachlicher Kontakt', Description=NULL, Help=NULL, AD_Element_ID=540455 WHERE UPPER(ColumnName)='ISSUBJECTMATTERCONTACT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-14T18:50:15.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSubjectMatterContact', Name='Fachlicher Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=540455 AND IsCentrallyMaintained='Y'
;

-- 2019-06-14T18:50:15.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fachlicher Kontakt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540455) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 540455)
;

-- 2019-06-14T18:50:15.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Fachlicher Kontakt', Name='Fachlicher Kontakt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540455)
;

-- 2019-06-14T18:50:15.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Fachlicher Kontakt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 540455
;

-- 2019-06-14T18:50:15.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Fachlicher Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID = 540455
;

-- 2019-06-14T18:50:15.299
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Fachlicher Kontakt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 540455
;

-- 2019-06-14T18:51:11.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-06-14 18:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=544641
;

-- 2019-06-14T18:52:42.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,544641,0,496,540578,559997,'F',TO_TIMESTAMP('2019-06-14 18:52:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsSubjectMatterContact',145,0,0,TO_TIMESTAMP('2019-06-14 18:52:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-14T18:52:56.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-06-14 18:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559997
;

-- 2019-06-14T18:52:56.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-06-14 18:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546550
;

