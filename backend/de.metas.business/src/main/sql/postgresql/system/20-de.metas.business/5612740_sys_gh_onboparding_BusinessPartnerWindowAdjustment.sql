-- 2021-11-09T16:07:39.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb) VALUES (0,580206,0,TO_TIMESTAMP('2021-11-09 17:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner verwalten','D','Y','Geschäftspartner_OLD','Geschäftspartner_OLD',TO_TIMESTAMP('2021-11-09 17:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartner','Neuer Geschäftspartner','Geschäftspartner')
;

-- 2021-11-09T16:07:39.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580206 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-09T16:11:09.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maintain Business Partners', Help='The Business Partner window allows you do define any party with whom you transact.  This includes customers, vendors and employees.  Prior to entering or importing products, you must define your vendors.  Prior to generating Orders you must define your customers.  This window holds all information about your business partner and the values entered will be used to generate all document transactions', Name='Business Partner OLD', PrintName='Business Partner OLD', WEBUI_NameBrowse='', WEBUI_NameNew='', WEBUI_NameNewBreadcrumb='',Updated=TO_TIMESTAMP('2021-11-09 17:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580206 AND AD_Language='en_US'
;

-- 2021-11-09T16:11:09.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580206,'en_US') 
;

-- 2021-11-09T16:11:57.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maintain Business Partners', Help='The Business Partner window allows you do define any party with whom you transact.  This includes customers, vendors and employees.  Prior to entering or importing products, you must define your vendors.  Prior to generating Orders you must define your customers.  This window holds all information about your business partner and the values entered will be used to generate all document transactions', WEBUI_NameBrowse='', WEBUI_NameNew='', WEBUI_NameNewBreadcrumb='',Updated=TO_TIMESTAMP('2021-11-09 17:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580206 AND AD_Language='de_CH'
;

-- 2021-11-09T16:11:57.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580206,'de_CH') 
;

-- 2021-11-09T16:12:14.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.',Updated=TO_TIMESTAMP('2021-11-09 17:12:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580206 AND AD_Language='de_CH'
;

-- 2021-11-09T16:12:14.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580206,'de_CH') 
;

-- 2021-11-09T16:12:28.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.',Updated=TO_TIMESTAMP('2021-11-09 17:12:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580206 AND AD_Language='de_DE'
;

-- 2021-11-09T16:12:28.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580206,'de_DE') 
;

-- 2021-11-09T16:12:28.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580206,'de_DE') 
;

-- 2021-11-09T16:12:28.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Geschäftspartner_OLD', Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.' WHERE AD_Element_ID=580206
;

-- 2021-11-09T16:12:28.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Geschäftspartner_OLD', Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.' WHERE AD_Element_ID=580206 AND IsCentrallyMaintained='Y'
;

-- 2021-11-09T16:12:28.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschäftspartner_OLD', Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580206) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580206)
;

-- 2021-11-09T16:12:28.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geschäftspartner_OLD', Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.', CommitWarning = NULL WHERE AD_Element_ID = 580206
;

-- 2021-11-09T16:12:28.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geschäftspartner_OLD', Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.' WHERE AD_Element_ID = 580206
;

-- 2021-11-09T16:12:28.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geschäftspartner_OLD', Description = 'Geschäftspartner verwalten', WEBUI_NameBrowse = 'Geschäftspartner', WEBUI_NameNew = 'Neuer Geschäftspartner', WEBUI_NameNewBreadcrumb = 'Geschäftspartner' WHERE AD_Element_ID = 580206
;

-- 2021-11-09T16:12:56.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.', WEBUI_NameBrowse='', WEBUI_NameNew='', WEBUI_NameNewBreadcrumb='',Updated=TO_TIMESTAMP('2021-11-09 17:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580206 AND AD_Language='nl_NL'
;

-- 2021-11-09T16:12:56.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580206,'nl_NL') 
;

-- 2021-11-09T16:13:20.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='', WEBUI_NameNew='', WEBUI_NameNewBreadcrumb='',Updated=TO_TIMESTAMP('2021-11-09 17:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580206 AND AD_Language='de_DE'
;

-- 2021-11-09T16:13:20.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580206,'de_DE') 
;

-- 2021-11-09T16:13:20.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580206,'de_DE') 
;

-- 2021-11-09T16:13:20.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geschäftspartner_OLD', Description = 'Geschäftspartner verwalten', WEBUI_NameBrowse = '', WEBUI_NameNew = '', WEBUI_NameNewBreadcrumb = '' WHERE AD_Element_ID = 580206
;

-- 2021-11-09T16:14:35.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=580206, Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.', Name='Geschäftspartner_OLD',Updated=TO_TIMESTAMP('2021-11-09 17:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=123
;

-- 2021-11-09T16:14:35.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Geschäftspartner verwalten', IsActive='Y', Name='Geschäftspartner_OLD',Updated=TO_TIMESTAMP('2021-11-09 17:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000021
;

-- 2021-11-09T16:14:35.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.', Name='Geschäftspartner_OLD',Updated=TO_TIMESTAMP('2021-11-09 17:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=139
;

-- 2021-11-09T16:14:35.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Description='Geschäftspartner verwalten', Help='Das Fenster "Geschäftspartner" ermöglicht, alle Einheiten zu definieren, mit denen Sie interagieren. Dies umfasst Kunden, Lieferanten und Mitarbeiter. Vor dem Eingeben oder Importieren von Produkten müssen Sie Ihre Lieferanten definieren. Vor dem Erstellen von Aufträgen müssen Sie Ihre Kunden anlegen. Dieses Fenster verwaltet alle Informationen zu Ihren Geschäftspartnern und die Daten werden bei der Erzeugung von Dokumenten verwendet.', Name='Geschäftspartner_OLD',Updated=TO_TIMESTAMP('2021-11-09 17:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=164
;

-- 2021-11-09T16:14:35.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(580206) 
;

-- 2021-11-09T16:14:35.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=123
;

-- 2021-11-09T16:14:35.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(123)
;

-- 2021-11-09T16:16:26.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=580206, Description='Geschäftspartner verwalten', Name='Geschäftspartner_OLD', WEBUI_NameBrowse='', WEBUI_NameNew='', WEBUI_NameNewBreadcrumb='',Updated=TO_TIMESTAMP('2021-11-09 17:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000021
;

-- 2021-11-09T16:16:26.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(580206) 
;

