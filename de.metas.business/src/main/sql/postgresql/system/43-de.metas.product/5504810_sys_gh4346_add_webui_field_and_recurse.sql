-- 2018-10-29T16:28:38.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,Name,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount) VALUES (100,553991,565101,'N',0,TO_TIMESTAMP('2018-10-29 16:28:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',15,540295,TO_TIMESTAMP('2018-10-29 16:28:37','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,189,'F','Produkt-Nummerfolge','N','N',0)
;

-- 2018-10-29T16:31:39.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-29 16:31:39','YYYY-MM-DD HH24:MI:SS'),Description='Optionale Nummerfolge für Produkt-Suchschlüssel; falls leer wird ggf die Eltern-Produktkategorie überprüft.' WHERE AD_Element_ID=544173 AND AD_Language='de_CH'
;

-- 2018-10-29T16:31:39.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544173,'de_CH') 
;

-- 2018-10-29T16:31:42.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Optionale Nummerfolge für Produkt-Suchschlüssel; falls leer wird ggf die Eltern-Produktkategorie überprüft.',Updated=TO_TIMESTAMP('2018-10-29 16:31:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544173
;

-- 2018-10-29T16:31:42.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Sequence_ProductValue_ID', Name='Produkt-Nummerfolge', Description='Optionale Nummerfolge für Produkt-Suchschlüssel; falls leer wird ggf die Eltern-Produktkategorie überprüft.', Help=NULL WHERE AD_Element_ID=544173
;

-- 2018-10-29T16:31:42.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Sequence_ProductValue_ID', Name='Produkt-Nummerfolge', Description='Optionale Nummerfolge für Produkt-Suchschlüssel; falls leer wird ggf die Eltern-Produktkategorie überprüft.', Help=NULL, AD_Element_ID=544173 WHERE UPPER(ColumnName)='AD_SEQUENCE_PRODUCTVALUE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-29T16:31:42.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Sequence_ProductValue_ID', Name='Produkt-Nummerfolge', Description='Optionale Nummerfolge für Produkt-Suchschlüssel; falls leer wird ggf die Eltern-Produktkategorie überprüft.', Help=NULL WHERE AD_Element_ID=544173 AND IsCentrallyMaintained='Y'
;

-- 2018-10-29T16:31:42.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt-Nummerfolge', Description='Optionale Nummerfolge für Produkt-Suchschlüssel; falls leer wird ggf die Eltern-Produktkategorie überprüft.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544173) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544173)
;

-- 2018-10-29T16:32:14.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-29 16:32:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Sequence for product value; if empty, the system checks this category''s parent.' WHERE AD_Element_ID=544173 AND AD_Language='en_US'
;

-- 2018-10-29T16:32:14.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544173,'en_US') 
;

-- 2018-10-29T16:50:52.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2018-10-29 16:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=209
;
