-- 2020-02-26T12:08:45.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y''',Updated=TO_TIMESTAMP('2020-02-26 14:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540521
;

-- 2020-02-26T12:08:49.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS geocodingconfig_unique
;

-- 2020-02-26T12:08:49.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX GeocodingConfig_Unique ON GeocodingConfig (IsActive) WHERE IsActive = 'Y'
;

