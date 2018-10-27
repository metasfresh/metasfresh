-- 2018-09-18T17:52:03.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Lagerbewegung (inkl. Quarantäne)',Updated=TO_TIMESTAMP('2018-09-18 17:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541006
;

-- 2018-09-18T17:52:12.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-18 17:52:12','YYYY-MM-DD HH24:MI:SS'),Name='Move to another Warehouse (incl. quarantine)' WHERE AD_Process_ID=541006 AND AD_Language='en_US'
;

