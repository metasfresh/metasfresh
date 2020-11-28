-- 2017-09-06T14:27:01.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='Auftragsdisposition', Name='Auftragsdisposition', WEBUI_NameBrowse='Auftragsdisposition',Updated=TO_TIMESTAMP('2017-09-06 14:27:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000097
;

-- 2017-09-06T14:29:55.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-06 14:29:55','YYYY-MM-DD HH24:MI:SS'),Name='Sales Order Disposition',WEBUI_NameBrowse='Sales Order Disposition' WHERE AD_Menu_ID=1000097 AND AD_Language='en_US'
;

-- 2017-09-06T14:30:23.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Transportdisposition', WEBUI_NameBrowse='Transportdisposition',Updated=TO_TIMESTAMP('2017-09-06 14:30:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540856
;

