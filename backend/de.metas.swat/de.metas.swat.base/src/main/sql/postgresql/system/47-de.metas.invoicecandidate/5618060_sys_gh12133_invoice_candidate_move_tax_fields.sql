-- Move C_Tax override and C_Tax effective from advanced edit to the main window,
-- if no tax was found, then the user has to invervene. otherwise the IC remains invalid, but the user doesn't see it if the fields are in advanced edit
--
-- 2021-12-10T06:36:45.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541087, SeqNo=40,Updated=TO_TIMESTAMP('2021-12-10 07:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541011
;

-- 2021-12-10T06:37:01.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541087, SeqNo=50,Updated=TO_TIMESTAMP('2021-12-10 07:37:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541012
;
