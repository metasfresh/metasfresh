-- 2022-06-09T13:38:21.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Language SET IsActive = 'Y', IsSystemLanguage='Y',Updated=TO_TIMESTAMP('2022-06-09 14:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language_ID=156
;

-- 2022-06-09T15:09:45.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-06-09 16:09:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577189
;


ALTER TABLE  C_Incoterms_Trl DROP COLUMN c_incoterms_trl_id
;


