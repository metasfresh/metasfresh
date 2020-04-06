-- 2018-10-11T07:15:33.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Text', Name='Text', ColumnName='TextSnippet',Updated=TO_TIMESTAMP('2018-10-11 07:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=504411
;

-- 2018-10-11T07:15:33.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TextSnippet', Name='Text', Description=NULL, Help=NULL WHERE AD_Element_ID=504411
;

-- 2018-10-11T07:15:33.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TextSnippet', Name='Text', Description=NULL, Help=NULL, AD_Element_ID=504411 WHERE UPPER(ColumnName)='TEXTSNIPPET' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-11T07:15:33.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TextSnippet', Name='Text', Description=NULL, Help=NULL WHERE AD_Element_ID=504411 AND IsCentrallyMaintained='Y'
;

-- 2018-10-11T07:15:33.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Text', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=504411) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 504411)
;

-- 2018-10-11T07:15:33.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Text', Name='Text' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=504411)
;

select db_alter_table('AD_BoilerPlate', 'ALTER TABLE AD_BoilerPlate RENAME COLUMN TextSnippext TO TextSnippet');
select db_alter_table('AD_BoilerPlate_Trl', 'ALTER TABLE AD_BoilerPlate_Trl RENAME COLUMN TextSnippext TO TextSnippet');


-- 2018-10-11T07:41:55.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='AD_BoilerPlate_Trl',Updated=TO_TIMESTAMP('2018-10-11 07:41:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540026
;

-- 2018-10-11T07:42:23.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übersetzung',Updated=TO_TIMESTAMP('2018-10-11 07:42:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540058
;

-- 2018-10-11T07:42:40.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 07:42:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Übersetzung' WHERE AD_Tab_ID=540058 AND AD_Language='de_CH'
;

-- 2018-10-11T07:42:43.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-11 07:42:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Tab_ID=540058 AND AD_Language='en_US'
;

-- 2018-10-11T07:42:56.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Language='en_GB' AND AD_Tab_ID=540058
;

-- 2018-10-11T07:42:59.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Language='it_CH' AND AD_Tab_ID=540058
;

-- 2018-10-11T07:43:05.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab_Trl WHERE AD_Language='fr_CH' AND AD_Tab_ID=540058
;

