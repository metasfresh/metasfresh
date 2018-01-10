--
-- change window names of material dispo and cockpit to conform with our convention
--
-- 2018-01-10T07:48:32.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Material Disposition',Updated=TO_TIMESTAMP('2018-01-10 07:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540334
;

-- 2018-01-10T07:48:32.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Material Disposition',Updated=TO_TIMESTAMP('2018-01-10 07:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540801
;

-- 2018-01-10T07:48:32.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Material Disposition',Updated=TO_TIMESTAMP('2018-01-10 07:48:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540805
;

-- 2018-01-10T07:48:43.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Material Cockpit Detail',Updated=TO_TIMESTAMP('2018-01-10 07:48:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540395
;

-- 2018-01-10T07:48:48.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Material Cockpit',Updated=TO_TIMESTAMP('2018-01-10 07:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540376
;

