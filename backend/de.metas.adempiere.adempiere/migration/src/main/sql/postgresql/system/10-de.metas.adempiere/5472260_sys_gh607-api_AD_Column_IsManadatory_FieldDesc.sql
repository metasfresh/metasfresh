

-- AD_Column.IsMandatory shall be overwritten by MandatoryLogic https://github.com/metasfresh/metasfresh-webui-api/issues/607
--
-- Update the docs for the AD_Column.IsMandatory field
--
-- 2017-09-21T11:22:53.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Description='Specifies whether the field must have a value for the record to be saved to the database.
The value of this Y/N field can be overwritten via the mandatory logic field!', Help='', IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2017-09-21 11:22:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=166
;

-- 2017-09-21T11:23:24.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-21 11:23:24','YYYY-MM-DD HH24:MI:SS'),Description='Specifies whether the field must have a value for the record to be saved to the database. The value of this Y/N field can be overwritten via the mandatory logic field!',Help='' WHERE AD_Field_ID=166 AND AD_Language='en_US'
;

