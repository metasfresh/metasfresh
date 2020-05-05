

-- 07.12.2015 14:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ablaufsteuerung',Updated=TO_TIMESTAMP('2015-12-07 14:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=589
;

-- 07.12.2015 14:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=589
;

-- 07.12.2015 14:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Description='Benutzer, mit dessen Rechten der Prozess ausgeführt wird, und der ggf. bei Problemen vom System benachrichtigt wird.', Help=NULL, Name='Ansprechpartner',Updated=TO_TIMESTAMP('2015-12-07 14:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=9432
;

-- 07.12.2015 14:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=9432
;