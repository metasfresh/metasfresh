
--
-- delete old swat callout for C_PaySelectionLine. It's now programatically added, and the code is in de.metas.banking
-- 06.01.2016 10:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=540900
;

-- minor stuffs
--
-- 06.01.2016 11:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic=NULL,Updated=TO_TIMESTAMP('2016-01-06 11:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545321
;

-- 06.01.2016 11:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Description='Es wird automatisch der Geschäftspartner der jeweils ausgewählten Rechnung übernommen.', Help=NULL, IsCentrallyMaintained='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-01-06 11:45:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547562
;

-- 06.01.2016 11:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=547562
;

