-- 2019-05-09T09:40:11.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AvailableinAPI',Updated=TO_TIMESTAMP('2019-05-09 09:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576703
;

-- 2019-05-09T09:40:11.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AvailableinAPI', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL WHERE AD_Element_ID=576703
;

-- 2019-05-09T09:40:11.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AvailableinAPI', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL, AD_Element_ID=576703 WHERE UPPER(ColumnName)='AVAILABLEINAPI' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;


-- 2019-05-09T09:40:11.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AvailableinAPI', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL WHERE AD_Element_ID=576703 AND IsCentrallyMaintained='Y'
;

-- 2019-05-09T09:40:19.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AvailableInAPI',Updated=TO_TIMESTAMP('2019-05-09 09:40:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576703
;

-- 2019-05-09T09:40:19.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AvailableInAPI', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL WHERE AD_Element_ID=576703
;

-- 2019-05-09T09:40:19.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AvailableInAPI', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL, AD_Element_ID=576703 WHERE UPPER(ColumnName)='AVAILABLEINAPI' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-09T09:40:19.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AvailableInAPI', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL WHERE AD_Element_ID=576703 AND IsCentrallyMaintained='Y'
;

alter table dataentry_field rename includedinapicall to availableinapi;

