-- 2018-04-26T16:37:05.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-26 16:37:05','YYYY-MM-DD HH24:MI:SS'),Name='Qty Ordered',IsTranslated='Y' WHERE AD_Column_ID=559748 AND AD_Language='en_US'
;

-- 2018-04-26T16:37:36.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Kommissionierte Menge',Updated=TO_TIMESTAMP('2018-04-26 16:37:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559749
;

-- 2018-04-26T16:37:36.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionierte Menge', Description=NULL, Help=NULL WHERE AD_Column_ID=559749
;

-- 2018-04-26T16:37:41.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-26 16:37:41','YYYY-MM-DD HH24:MI:SS'),Name='Kommissionierte Menge' WHERE AD_Column_ID=559749 AND AD_Language='de_CH'
;

-- 2018-04-26T16:37:49.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-26 16:37:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Column_ID=559749 AND AD_Language='en_US'
;

-- 2018-04-26T16:45:58.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-26 16:45:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Qty Ordered' WHERE AD_Field_ID=563824 AND AD_Language='en_US'
;

-- 2018-04-26T16:46:06.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-26 16:46:06','YYYY-MM-DD HH24:MI:SS'),Description='Qty Ordered',Help='' WHERE AD_Field_ID=563824 AND AD_Language='en_US'
;

-- 2018-04-26T16:46:23.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-26 16:46:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Field_ID=563825 AND AD_Language='en_US'
;

