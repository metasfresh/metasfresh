
-- 26.11.2015 11:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Description='Sagt aus, ob zu diesem Vorgang weitere Abrechnungen stattfinden werden', Help=NULL, IsCentrallyMaintained='N', Name='Material-Vorgang ist abgeschlossen',Updated=TO_TIMESTAMP('2015-11-26 11:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554700
;

-- 26.11.2015 11:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=554700
;

