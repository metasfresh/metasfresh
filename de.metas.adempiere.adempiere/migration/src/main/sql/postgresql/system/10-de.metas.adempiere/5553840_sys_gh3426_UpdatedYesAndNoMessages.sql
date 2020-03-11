-- 2020-03-04T09:50:41.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Ja',Updated=TO_TIMESTAMP('2020-03-04 11:50:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544963
;

-- 2020-03-04T09:50:45.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Ja',Updated=TO_TIMESTAMP('2020-03-04 11:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544963
;

-- 2020-03-04T09:50:48.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Yes',Updated=TO_TIMESTAMP('2020-03-04 11:50:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544963
;

-- 2020-03-04T09:50:51.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Ja',Updated=TO_TIMESTAMP('2020-03-04 11:50:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544963
;

-- 2020-03-04T09:50:53.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-03-04 11:50:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544963
;

-- 2020-03-04T09:51:04.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Ja',Updated=TO_TIMESTAMP('2020-03-04 11:51:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544963
;

-- 2020-03-04T09:51:11.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Nein',Updated=TO_TIMESTAMP('2020-03-04 11:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544964
;

-- 2020-03-04T09:51:16.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Nein',Updated=TO_TIMESTAMP('2020-03-04 11:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544964
;

-- 2020-03-04T09:51:22.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Nein',Updated=TO_TIMESTAMP('2020-03-04 11:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544964
;

-- 2020-03-04T09:51:25.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No',Updated=TO_TIMESTAMP('2020-03-04 11:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544964
;

-- 2020-03-04T09:51:31.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Nein',Updated=TO_TIMESTAMP('2020-03-04 11:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=544964
;

-- 2020-03-04T09:51:34.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-03-04 11:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544964
;

--dropping old validationmessage column if it still exists (It is removed from ad_column in the other migration script with sys_gh3426...).Now we use ad_message_id column
delete from ad_column where columnname ilike '%validationmessage%' and ad_table_id = 104;
delete from ad_column where columnname ilike '%validationmessage%' and ad_table_id = 136;
alter table ad_ref_list drop column if exists validationmessage;
alter table ad_ref_list_trl drop column if exists validationmessage;
