-- 2017-10-23T11:38:18.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anmeldung als Benutzer Hostschlüssel',Updated=TO_TIMESTAMP('2017-10-23 11:38:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556935
;

-- 2017-10-23T11:38:32.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-23 11:38:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Login As User HostKey' WHERE AD_Field_ID=556935 AND AD_Language='en_US'
;

