INSERT INTO public.ad_userquery (ad_userquery_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, ad_user_id, ad_table_id, code, ad_tab_id, ismanadatoryparams, isshowallparams) VALUES (540051, 1000000, 0, 'Y', '2019-04-08 13:51:06.000000', 100, '2019-04-08 14:41:48.000000', 100, 'Company Phone Book', null, 100, 114, 'AND<^>Firstname<^> ILIKE /*icase*/ <^><^><~>AND<^>Lastname<^> ILIKE /*icase*/ <^><^><~>AND<^>EMail<^> ILIKE /*icase*/ <^><^><~>AND<^>Phone<^> ILIKE /*icase*/ <^><^><~>AND<^>MobilePhone<^> ILIKE /*icase*/ <^><^><~>AND<^>IsDefaultContact<^>=<^>Y<^>', 541699, 'N', 'Y');

-- 2019-04-08T16:45:34.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='Company Phone Book',Updated=TO_TIMESTAMP('2019-04-08 16:45:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540617
;

-- 2019-04-08T16:46:07.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Company Phone Book',Updated=TO_TIMESTAMP('2019-04-08 16:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541234
;

-- 2019-04-08T16:49:47.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Company Phone Book',Updated=TO_TIMESTAMP('2019-04-08 16:49:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

