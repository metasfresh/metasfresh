-- 20.07.2016 11:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50035
;


-- This callout was created by client. But it was commited in adempiere base so I think it's ok to delete it here, too
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID = 541018;