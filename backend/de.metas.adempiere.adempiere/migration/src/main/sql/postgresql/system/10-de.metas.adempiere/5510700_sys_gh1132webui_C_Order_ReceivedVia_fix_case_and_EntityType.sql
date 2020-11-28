-- 2019-01-24T13:49:54.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ReceivedVia', EntityType='D',Updated=TO_TIMESTAMP('2019-01-24 13:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540621
;

-- 2019-01-24T13:49:54.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ReceivedVia', Name='Eingegangen via', Description=NULL, Help=NULL WHERE AD_Element_ID=540621
;

-- 2019-01-24T13:49:54.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReceivedVia', Name='Eingegangen via', Description=NULL, Help=NULL, AD_Element_ID=540621 WHERE UPPER(ColumnName)='RECEIVEDVIA' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-24T13:49:54.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReceivedVia', Name='Eingegangen via', Description=NULL, Help=NULL WHERE AD_Element_ID=540621 AND IsCentrallyMaintained='Y'
;

-- 2019-01-24T13:50:26.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-01-24 13:50:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542514
;

