
-- 2019-01-09T15:48:51.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='C_BPartner_Org_Link',Updated=TO_TIMESTAMP('2019-01-09 15:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=260
;

--
-- ad_orginfos are inserted by the system, not manually
--
-- 2019-01-09T17:02:09.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2019-01-09 17:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=170
;

