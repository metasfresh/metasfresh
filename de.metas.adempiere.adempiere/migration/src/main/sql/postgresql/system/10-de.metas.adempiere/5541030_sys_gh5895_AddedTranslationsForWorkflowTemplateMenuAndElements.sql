-- 2020-01-09T14:43:58.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=107,Updated=TO_TIMESTAMP('2020-01-09 16:43:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564568
;

-- 2020-01-09T14:51:13.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Workflow Steps Template ID', PrintName='Workflow Steps Template ID',Updated=TO_TIMESTAMP('2020-01-09 16:51:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577407 AND AD_Language='en_US'
;

-- 2020-01-09T14:51:14.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577407,'en_US') 
;

-- 2020-01-09T15:31:37.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Workflow Steps Template (ID)', PrintName='Workflow Steps Template (ID)',Updated=TO_TIMESTAMP('2020-01-09 17:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577407 AND AD_Language='en_US'
;

-- 2020-01-09T15:31:37.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577407,'en_US') 
;

-- 2020-01-09T15:44:27.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vorlage Arbeitsschritte (ID)', PrintName='Vorlage Arbeitsschritte (ID)',Updated=TO_TIMESTAMP('2020-01-09 17:44:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577407 AND AD_Language='de_CH'
;

-- 2020-01-09T15:44:28.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577407,'de_CH') 
;

-- 2020-01-09T15:44:32.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vorlage Arbeitsschritte (ID)', PrintName='Vorlage Arbeitsschritte (ID)',Updated=TO_TIMESTAMP('2020-01-09 17:44:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577407 AND AD_Language='de_DE'
;

-- 2020-01-09T15:44:32.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577407,'de_DE') 
;

-- 2020-01-09T15:44:32.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577407,'de_DE') 
;

-- 2020-01-09T15:44:32.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_WF_Node_Template_ID', Name='Vorlage Arbeitsschritte (ID)', Description=NULL, Help=NULL WHERE AD_Element_ID=577407
;

-- 2020-01-09T15:44:32.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_WF_Node_Template_ID', Name='Vorlage Arbeitsschritte (ID)', Description=NULL, Help=NULL, AD_Element_ID=577407 WHERE UPPER(ColumnName)='AD_WF_NODE_TEMPLATE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-09T15:44:32.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_WF_Node_Template_ID', Name='Vorlage Arbeitsschritte (ID)', Description=NULL, Help=NULL WHERE AD_Element_ID=577407 AND IsCentrallyMaintained='Y'
;

-- 2020-01-09T15:44:32.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vorlage Arbeitsschritte (ID)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577407) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577407)
;

-- 2020-01-09T15:44:32.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vorlage Arbeitsschritte (ID)', Name='Vorlage Arbeitsschritte (ID)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577407)
;

-- 2020-01-09T15:44:32.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vorlage Arbeitsschritte (ID)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577407
;

-- 2020-01-09T15:44:32.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vorlage Arbeitsschritte (ID)', Description=NULL, Help=NULL WHERE AD_Element_ID = 577407
;

-- 2020-01-09T15:44:32.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vorlage Arbeitsschritte (ID)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577407
;

-- 2020-01-09T15:44:38.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vorlage Arbeitsschritte (ID)', PrintName='Vorlage Arbeitsschritte (ID)',Updated=TO_TIMESTAMP('2020-01-09 17:44:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577407 AND AD_Language='nl_NL'
;

-- 2020-01-09T15:44:38.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577407,'nl_NL') 
;

-- 2020-01-09T15:46:26.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Fertigungsabläufe (Routing) bestehen aus einer Reihe von Informationen, die die Methode zur Herstellung eines bestimmten Artikels detailliert beschreiben. Zu diesen Informationen gehören die auszuführenden Arbeitsvorgänge, ihre Reihenfolge, die verschiedenen zu beteiligenden Arbeitsplätze und die Vorgaben für Rüst- und Laufzeiten. In einigen Unternehmen enthält der Fertigungsplan auch Informationen über die Werkzeugausstattung, den Kenntnisstand des Bedieners, die Prüfvorgänge, die Prüfanforderungen usw.', IsTranslated='Y', Name='Produktion Standardarbeitsablauf', PrintName='Produktion Standardarbeitsablauf',Updated=TO_TIMESTAMP('2020-01-09 17:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577413 AND AD_Language='de_CH'
;

-- 2020-01-09T15:46:26.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577413,'de_CH') 
;

-- 2020-01-09T15:46:50.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktion Standardarbeitsablauf', PrintName='Produktion Standardarbeitsablauf',Updated=TO_TIMESTAMP('2020-01-09 17:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577413 AND AD_Language='de_DE'
;

-- 2020-01-09T15:46:50.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577413,'de_DE') 
;

-- 2020-01-09T15:46:50.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577413,'de_DE') 
;

-- 2020-01-09T15:46:50.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Manufacturing Workflows (Routing) is a set of information detailing the method of manufacture of a particular item .It includes the operations to be performed their sequence the various work centers to be involved and the standards for setup and run. In some companies routing also includes information on tooling operator skill levels inspection operations, testing requirements etc.' WHERE AD_Element_ID=577413
;

-- 2020-01-09T15:46:50.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Manufacturing Workflows (Routing) is a set of information detailing the method of manufacture of a particular item .It includes the operations to be performed their sequence the various work centers to be involved and the standards for setup and run. In some companies routing also includes information on tooling operator skill levels inspection operations, testing requirements etc.' WHERE AD_Element_ID=577413 AND IsCentrallyMaintained='Y'
;

-- 2020-01-09T15:46:50.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Manufacturing Workflows (Routing) is a set of information detailing the method of manufacture of a particular item .It includes the operations to be performed their sequence the various work centers to be involved and the standards for setup and run. In some companies routing also includes information on tooling operator skill levels inspection operations, testing requirements etc.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577413) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577413)
;

-- 2020-01-09T15:46:50.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produktion Standardarbeitsablauf', Name='Produktion Standardarbeitsablauf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577413)
;

-- 2020-01-09T15:46:50.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Manufacturing Workflows (Routing) is a set of information detailing the method of manufacture of a particular item .It includes the operations to be performed their sequence the various work centers to be involved and the standards for setup and run. In some companies routing also includes information on tooling operator skill levels inspection operations, testing requirements etc.', CommitWarning = NULL WHERE AD_Element_ID = 577413
;

-- 2020-01-09T15:46:50.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Manufacturing Workflows (Routing) is a set of information detailing the method of manufacture of a particular item .It includes the operations to be performed their sequence the various work centers to be involved and the standards for setup and run. In some companies routing also includes information on tooling operator skill levels inspection operations, testing requirements etc.' WHERE AD_Element_ID = 577413
;

-- 2020-01-09T15:46:50.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktion Standardarbeitsablauf', Description = 'Einstellungen zum Produktion Arbeitsablauf (Routing)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577413
;

-- 2020-01-09T15:46:55.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktion Standardarbeitsablauf', PrintName='Produktion Standardarbeitsablauf',Updated=TO_TIMESTAMP('2020-01-09 17:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577413 AND AD_Language='nl_NL'
;

-- 2020-01-09T15:46:56.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577413,'nl_NL') 
;

-- 2020-01-09T15:46:58.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-09 17:46:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577413 AND AD_Language='en_US'
;

-- 2020-01-09T15:46:58.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577413,'en_US') 
;

-- 2020-01-09T15:47:05.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Fertigungsabläufe (Routing) bestehen aus einer Reihe von Informationen, die die Methode zur Herstellung eines bestimmten Artikels detailliert beschreiben. Zu diesen Informationen gehören die auszuführenden Arbeitsvorgänge, ihre Reihenfolge, die verschiedenen zu beteiligenden Arbeitsplätze und die Vorgaben für Rüst- und Laufzeiten. In einigen Unternehmen enthält der Fertigungsplan auch Informationen über die Werkzeugausstattung, den Kenntnisstand des Bedieners, die Prüfvorgänge, die Prüfanforderungen usw.',Updated=TO_TIMESTAMP('2020-01-09 17:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577413 AND AD_Language='de_DE'
;

-- 2020-01-09T15:47:05.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577413,'de_DE') 
;

-- 2020-01-09T15:47:05.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577413,'de_DE') 
;

-- 2020-01-09T15:47:05.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Fertigungsabläufe (Routing) bestehen aus einer Reihe von Informationen, die die Methode zur Herstellung eines bestimmten Artikels detailliert beschreiben. Zu diesen Informationen gehören die auszuführenden Arbeitsvorgänge, ihre Reihenfolge, die verschiedenen zu beteiligenden Arbeitsplätze und die Vorgaben für Rüst- und Laufzeiten. In einigen Unternehmen enthält der Fertigungsplan auch Informationen über die Werkzeugausstattung, den Kenntnisstand des Bedieners, die Prüfvorgänge, die Prüfanforderungen usw.' WHERE AD_Element_ID=577413
;

-- 2020-01-09T15:47:05.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Fertigungsabläufe (Routing) bestehen aus einer Reihe von Informationen, die die Methode zur Herstellung eines bestimmten Artikels detailliert beschreiben. Zu diesen Informationen gehören die auszuführenden Arbeitsvorgänge, ihre Reihenfolge, die verschiedenen zu beteiligenden Arbeitsplätze und die Vorgaben für Rüst- und Laufzeiten. In einigen Unternehmen enthält der Fertigungsplan auch Informationen über die Werkzeugausstattung, den Kenntnisstand des Bedieners, die Prüfvorgänge, die Prüfanforderungen usw.' WHERE AD_Element_ID=577413 AND IsCentrallyMaintained='Y'
;

-- 2020-01-09T15:47:05.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Fertigungsabläufe (Routing) bestehen aus einer Reihe von Informationen, die die Methode zur Herstellung eines bestimmten Artikels detailliert beschreiben. Zu diesen Informationen gehören die auszuführenden Arbeitsvorgänge, ihre Reihenfolge, die verschiedenen zu beteiligenden Arbeitsplätze und die Vorgaben für Rüst- und Laufzeiten. In einigen Unternehmen enthält der Fertigungsplan auch Informationen über die Werkzeugausstattung, den Kenntnisstand des Bedieners, die Prüfvorgänge, die Prüfanforderungen usw.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577413) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577413)
;

-- 2020-01-09T15:47:05.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Fertigungsabläufe (Routing) bestehen aus einer Reihe von Informationen, die die Methode zur Herstellung eines bestimmten Artikels detailliert beschreiben. Zu diesen Informationen gehören die auszuführenden Arbeitsvorgänge, ihre Reihenfolge, die verschiedenen zu beteiligenden Arbeitsplätze und die Vorgaben für Rüst- und Laufzeiten. In einigen Unternehmen enthält der Fertigungsplan auch Informationen über die Werkzeugausstattung, den Kenntnisstand des Bedieners, die Prüfvorgänge, die Prüfanforderungen usw.', CommitWarning = NULL WHERE AD_Element_ID = 577413
;

-- 2020-01-09T15:47:05.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktion Standardarbeitsablauf', Description='Einstellungen zum Produktion Arbeitsablauf (Routing)', Help='Fertigungsabläufe (Routing) bestehen aus einer Reihe von Informationen, die die Methode zur Herstellung eines bestimmten Artikels detailliert beschreiben. Zu diesen Informationen gehören die auszuführenden Arbeitsvorgänge, ihre Reihenfolge, die verschiedenen zu beteiligenden Arbeitsplätze und die Vorgaben für Rüst- und Laufzeiten. In einigen Unternehmen enthält der Fertigungsplan auch Informationen über die Werkzeugausstattung, den Kenntnisstand des Bedieners, die Prüfvorgänge, die Prüfanforderungen usw.' WHERE AD_Element_ID = 577413
;

-- 2020-01-09T15:47:05.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktion Standardarbeitsablauf', Description = 'Einstellungen zum Produktion Arbeitsablauf (Routing)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577413
;

-- 2020-01-09T15:47:12.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Manufacturing Workflows (Routing) consist of a set of information detailing the method for manufacturing a particular item. It includes the operations to be performed, their sequence, the various work centers to be involved, and the standards for set-up and run times. In some companies, the routing also includes information on tooling, operator skill levels, inspection operations, testing requirements, etc.',Updated=TO_TIMESTAMP('2020-01-09 17:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577413 AND AD_Language='en_US'
;

-- 2020-01-09T15:47:12.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577413,'en_US') 
;

-- 2020-01-09T15:48:48.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Settings for Manufacturing Workflows (Routing)',Updated=TO_TIMESTAMP('2020-01-09 17:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577413 AND AD_Language='en_US'
;

-- 2020-01-09T15:48:48.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577413,'en_US') 
;

-- 2020-01-09T15:49:08.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Fertigungsabläufe (Routing) bestehen aus einer Reihe von Informationen, die die Methode zur Herstellung eines bestimmten Artikels detailliert beschreiben. Zu diesen Informationen gehören die auszuführenden Arbeitsvorgänge, ihre Reihenfolge, die verschiedenen zu beteiligenden Arbeitsplätze und die Vorgaben für Rüst- und Laufzeiten. In einigen Unternehmen enthält der Fertigungsplan auch Informationen über die Werkzeugausstattung, den Kenntnisstand des Bedieners, die Prüfvorgänge, die Prüfanforderungen usw.',Updated=TO_TIMESTAMP('2020-01-09 17:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577413 AND AD_Language='nl_NL'
;

-- 2020-01-09T15:49:08.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577413,'nl_NL') 
;

