-- 2021-11-09T16:32:43.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=574089, Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.', Name='Geschäftspartner',Updated=TO_TIMESTAMP('2021-11-09 17:32:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541328
;

-- 2021-11-09T16:32:43.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Geschäftspartner verwalten', IsActive='Y', Name='Geschäftspartner',Updated=TO_TIMESTAMP('2021-11-09 17:32:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=110
;

-- 2021-11-09T16:32:43.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(574089) 
;

-- 2021-11-09T16:32:43.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541328
;

-- 2021-11-09T16:32:43.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541328)
;

-- 2021-11-09T16:33:52.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsOverrideInMenu='Y',Updated=TO_TIMESTAMP('2021-11-09 17:33:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541328
;

-- 2021-11-09T16:41:40.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-11-09 17:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594720
;

-- 2021-11-09T16:44:07.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-11-09 17:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594728
;

-- 2021-11-09T16:44:12.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-11-09 17:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594727
;

-- 2021-11-09T16:45:05.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-11-09 17:45:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594730
;

-- 2021-11-09T16:47:00.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2021-11-09 17:47:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544829
;

-- 2021-11-09T16:47:33.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2021-11-09 17:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544830
;

-- 2021-11-09T16:48:47.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2021-11-09 17:48:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544833
;

