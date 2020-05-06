-- 2018-08-02T12:23:34.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zutaten',Updated=TO_TIMESTAMP('2018-08-02 12:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565189
;

-- 2018-08-02T12:28:40.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Zutaten', PrintName='Zutaten',Updated=TO_TIMESTAMP('2018-08-02 12:28:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544186
;

-- 2018-08-02T12:28:40.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Ingredients', Name='Zutaten', Description=NULL, Help=NULL WHERE AD_Element_ID=544186
;

-- 2018-08-02T12:28:40.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ingredients', Name='Zutaten', Description=NULL, Help=NULL, AD_Element_ID=544186 WHERE UPPER(ColumnName)='INGREDIENTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-02T12:28:40.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ingredients', Name='Zutaten', Description=NULL, Help=NULL WHERE AD_Element_ID=544186 AND IsCentrallyMaintained='Y'
;

-- 2018-08-02T12:28:40.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zutaten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544186) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544186)
;

-- 2018-08-02T12:28:40.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zutaten', Name='Zutaten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544186)
;

-- 2018-08-02T12:28:48.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544186 AND AD_Language='en_US'
;


-- 2018-08-02T12:28:54.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Zutaten',PrintName='Zutaten' WHERE AD_Element_ID=544186 AND AD_Language='de_CH'
;


