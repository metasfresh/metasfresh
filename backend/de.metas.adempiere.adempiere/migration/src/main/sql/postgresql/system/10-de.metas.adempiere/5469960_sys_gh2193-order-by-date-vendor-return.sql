-- 2017-08-25T09:28:54.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=559016, Name='memo',Updated=TO_TIMESTAMP('2017-08-25 09:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546977
;

-- 2017-08-25T09:43:53.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2017-08-25 09:43:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7791
;

-- 2017-08-25T09:54:57.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=SELECT R_RequestType_ID from R_RequestType WHERE Name = ''Information''',Updated=TO_TIMESTAMP('2017-08-25 09:54:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5860
;

-- 2017-08-25T09:55:04.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=SELECT R_RequestType_ID from R_RequestType WHERE InternalName = ''A_CustomerComplaint''',Updated=TO_TIMESTAMP('2017-08-25 09:55:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5860
;

