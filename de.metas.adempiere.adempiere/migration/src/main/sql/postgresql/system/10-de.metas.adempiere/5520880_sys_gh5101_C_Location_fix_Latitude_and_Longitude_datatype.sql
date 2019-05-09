-- 2019-05-06T12:43:24.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22, FieldLength=10,Updated=TO_TIMESTAMP('2019-05-06 12:43:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567648
;

-- 2019-05-06T12:43:47.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=22, FieldLength=10,Updated=TO_TIMESTAMP('2019-05-06 12:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567649
;

ALTER TABLE C_Location ALTER COLUMN Latitude TYPE numeric(10) USING Latitude::numeric;
ALTER TABLE C_Location ALTER COLUMN Longitude TYPE numeric(10) USING Longitude::numeric;

