-- 2019-09-30T10:03:58.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=577129, ColumnName='GeocodingProvider', Description=NULL, Help=NULL, Name='Geocoding Dienst Provider',Updated=TO_TIMESTAMP('2019-09-30 13:03:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568972
;

-- 2019-09-30T10:03:58.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geocoding Dienst Provider', Description=NULL, Help=NULL WHERE AD_Column_ID=568972
;

-- 2019-09-30T10:03:58.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577129) 
;

-- 2019-09-30T10:04:03.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('GeocodingConfig','ALTER TABLE public.GeocodingConfig ADD COLUMN GeocodingProvider VARCHAR(666)')
;

-- 2019-09-30T10:05:43.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@GeocodingProvider@=OpenStreetMaps',Updated=TO_TIMESTAMP('2019-09-30 13:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=588623
;

-- 2019-09-30T10:05:46.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@GeocodingProvider@=OpenStreetMaps',Updated=TO_TIMESTAMP('2019-09-30 13:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=588624
;

-- 2019-09-30T10:05:49.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@GeocodingProvider@=GoogleMaps',Updated=TO_TIMESTAMP('2019-09-30 13:05:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=588625
;

-- 2019-09-30T10:05:59.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@GeocodingProvider@=OpenStreetMaps | @GeocodingProvider@=GoogleMaps',Updated=TO_TIMESTAMP('2019-09-30 13:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=588626
;

