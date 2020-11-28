-- 2020-02-21T12:18:31.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=540460
;

-- 2020-02-21T12:18:35.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=540460
;

-- 2020-02-21T12:18:58.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542210 AND AD_Language='de_CH'
;

-- 2020-02-21T12:18:58.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542210,'de_CH') 
;

-- 2020-02-21T12:19:00.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542210 AND AD_Language='de_DE'
;

-- 2020-02-21T12:19:00.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542210,'de_DE') 
;

-- 2020-02-21T12:19:00.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542210,'de_DE') 
;

-- 2020-02-21T12:19:23.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.',Updated=TO_TIMESTAMP('2020-02-21 13:19:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542210 AND AD_Language='en_US'
;

-- 2020-02-21T12:19:23.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542210,'en_US') 
;

-- 2020-02-21T12:19:27.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.',Updated=TO_TIMESTAMP('2020-02-21 13:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542210 AND AD_Language='de_DE'
;

-- 2020-02-21T12:19:27.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542210,'de_DE') 
;

-- 2020-02-21T12:19:27.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542210,'de_DE') 
;

-- 2020-02-21T12:19:27.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsRecreatePrintout', Name='Druckstücke neu erstellen', Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.', Help=NULL WHERE AD_Element_ID=542210
;

-- 2020-02-21T12:19:27.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRecreatePrintout', Name='Druckstücke neu erstellen', Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.', Help=NULL, AD_Element_ID=542210 WHERE UPPER(ColumnName)='ISRECREATEPRINTOUT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-21T12:19:27.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRecreatePrintout', Name='Druckstücke neu erstellen', Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.', Help=NULL WHERE AD_Element_ID=542210 AND IsCentrallyMaintained='Y'
;

-- 2020-02-21T12:19:27.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Druckstücke neu erstellen', Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542210) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542210)
;

-- 2020-02-21T12:19:27.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Druckstücke neu erstellen', Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542210
;

-- 2020-02-21T12:19:27.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Druckstücke neu erstellen', Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.', Help=NULL WHERE AD_Element_ID = 542210
;

-- 2020-02-21T12:19:27.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Druckstücke neu erstellen', Description = 'Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542210
;

-- 2020-02-21T12:19:34.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird pro neuem Warteschlangen-Datensatz auch ein neues Druckstück und ein neuer Archiv-Datensatz erstellt. Ansonsten wird nur ein neuer Warteschlangeneintrag für das bestehende Druckstück erstellt.',Updated=TO_TIMESTAMP('2020-02-21 13:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542210 AND AD_Language='de_CH'
;

-- 2020-02-21T12:19:34.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542210,'de_CH') 
;

-- 2020-02-21T12:23:51.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577556,0,'IsProcessedPrintingqueueItems',TO_TIMESTAMP('2020-02-21 13:23:51','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob nach verarbeiteten oder nach nicht verarbeiteten Druck-Warteschlangen-Datensätzen gefiltert werden soll','de.metas.printing','Y','Verarbeitete Datensätze','Verarbeitete Datensätze',TO_TIMESTAMP('2020-02-21 13:23:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-21T12:23:51.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577556 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-21T12:23:55.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:23:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577556 AND AD_Language='de_CH'
;

-- 2020-02-21T12:23:55.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577556,'de_CH') 
;

-- 2020-02-21T12:23:57.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577556 AND AD_Language='de_DE'
;

-- 2020-02-21T12:23:57.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577556,'de_DE') 
;

-- 2020-02-21T12:23:57.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577556,'de_DE') 
;

-- 2020-02-21T12:24:18.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Processed', PrintName='Processed',Updated=TO_TIMESTAMP('2020-02-21 13:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577556 AND AD_Language='en_US'
;

-- 2020-02-21T12:24:18.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577556,'en_US') 
;

-- 2020-02-21T12:24:23.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577556 AND AD_Language='en_US'
;

-- 2020-02-21T12:24:23.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577556,'en_US') 
;

-- 2020-02-21T12:24:40.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ProcessedQueueItems',Updated=TO_TIMESTAMP('2020-02-21 13:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577556
;

-- 2020-02-21T12:24:40.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProcessedQueueItems', Name='Verarbeitete Datensätze', Description='Legt fest, ob nach verarbeiteten oder nach nicht verarbeiteten Druck-Warteschlangen-Datensätzen gefiltert werden soll', Help=NULL WHERE AD_Element_ID=577556
;

-- 2020-02-21T12:24:40.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProcessedQueueItems', Name='Verarbeitete Datensätze', Description='Legt fest, ob nach verarbeiteten oder nach nicht verarbeiteten Druck-Warteschlangen-Datensätzen gefiltert werden soll', Help=NULL, AD_Element_ID=577556 WHERE UPPER(ColumnName)='PROCESSEDQUEUEITEMS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-21T12:24:40.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProcessedQueueItems', Name='Verarbeitete Datensätze', Description='Legt fest, ob nach verarbeiteten oder nach nicht verarbeiteten Druck-Warteschlangen-Datensätzen gefiltert werden soll', Help=NULL WHERE AD_Element_ID=577556 AND IsCentrallyMaintained='Y'
;

-- 2020-02-21T12:30:48.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FilterByProcessedQueueItems',Updated=TO_TIMESTAMP('2020-02-21 13:30:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577556
;

-- 2020-02-21T12:30:48.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FilterByProcessedQueueItems', Name='Verarbeitete Datensätze', Description='Legt fest, ob nach verarbeiteten oder nach nicht verarbeiteten Druck-Warteschlangen-Datensätzen gefiltert werden soll', Help=NULL WHERE AD_Element_ID=577556
;

-- 2020-02-21T12:30:48.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilterByProcessedQueueItems', Name='Verarbeitete Datensätze', Description='Legt fest, ob nach verarbeiteten oder nach nicht verarbeiteten Druck-Warteschlangen-Datensätzen gefiltert werden soll', Help=NULL, AD_Element_ID=577556 WHERE UPPER(ColumnName)='FILTERBYPROCESSEDQUEUEITEMS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-21T12:30:48.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilterByProcessedQueueItems', Name='Verarbeitete Datensätze', Description='Legt fest, ob nach verarbeiteten oder nach nicht verarbeiteten Druck-Warteschlangen-Datensätzen gefiltert werden soll', Help=NULL WHERE AD_Element_ID=577556 AND IsCentrallyMaintained='Y'
;

-- 2020-02-21T12:30:59.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=577556, ColumnName='ProcessedQueueItems',Updated=TO_TIMESTAMP('2020-02-21 13:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540456
;

-- 2020-02-21T12:31:10.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilterByProcessedQueueItems',Updated=TO_TIMESTAMP('2020-02-21 13:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540456
;

-- 2020-02-21T12:36:41.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577557,0,'SelectedQueueItems',TO_TIMESTAMP('2020-02-21 13:36:41','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann werden nur die Datensätze der derzeitigen Selektion/Suchabfrage in Betracht gezogen.','de.metas.printing','Y','Selektierte Datensätze','Selektierte Datensätze',TO_TIMESTAMP('2020-02-21 13:36:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-21T12:36:41.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577557 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-21T12:36:44.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577557 AND AD_Language='de_CH'
;

-- 2020-02-21T12:36:44.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577557,'de_CH') 
;

-- 2020-02-21T12:36:46.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-21 13:36:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577557 AND AD_Language='de_DE'
;

-- 2020-02-21T12:36:46.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577557,'de_DE') 
;

-- 2020-02-21T12:36:47Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577557,'de_DE') 
;

-- 2020-02-21T12:37:03.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Selected queue items', PrintName='Selected queue items',Updated=TO_TIMESTAMP('2020-02-21 13:37:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577557 AND AD_Language='en_US'
;

-- 2020-02-21T12:37:03.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577557,'en_US') 
;

-- 2020-02-21T12:37:34.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=577557, ColumnName='SelectedQueueItems',Updated=TO_TIMESTAMP('2020-02-21 13:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540455
;

-- 2020-02-21T12:38:20.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilterBySelectedQueueItems',Updated=TO_TIMESTAMP('2020-02-21 13:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540455
;

-- 2020-02-21T12:38:25.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FilterBySelectedQueueItems',Updated=TO_TIMESTAMP('2020-02-21 13:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577557
;

-- 2020-02-21T12:38:25.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FilterBySelectedQueueItems', Name='Selektierte Datensätze', Description='Wenn angehakt, dann werden nur die Datensätze der derzeitigen Selektion/Suchabfrage in Betracht gezogen.', Help=NULL WHERE AD_Element_ID=577557
;

-- 2020-02-21T12:38:25.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilterBySelectedQueueItems', Name='Selektierte Datensätze', Description='Wenn angehakt, dann werden nur die Datensätze der derzeitigen Selektion/Suchabfrage in Betracht gezogen.', Help=NULL, AD_Element_ID=577557 WHERE UPPER(ColumnName)='FILTERBYSELECTEDQUEUEITEMS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-21T12:38:25.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FilterBySelectedQueueItems', Name='Selektierte Datensätze', Description='Wenn angehakt, dann werden nur die Datensätze der derzeitigen Selektion/Suchabfrage in Betracht gezogen.', Help=NULL WHERE AD_Element_ID=577557 AND IsCentrallyMaintained='Y'
;

-- 2020-02-21T12:47:35.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='3', Name='Auswahl Druckjobs erzeugen', Value='C_Print_Job_Create',Updated=TO_TIMESTAMP('2020-02-21 13:47:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540314
;

-- 2020-02-21T13:00:39.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2020-02-21 14:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565072
;

-- 2020-02-21T13:02:09.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='C_Print_Job',Updated=TO_TIMESTAMP('2020-02-21 14:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540437
;

-- 2020-02-21T13:02:17.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='C_Print_Job',Updated=TO_TIMESTAMP('2020-02-21 14:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540166
;

-- 2020-02-21T13:06:33.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', IsTranslated='Y', Name='Login als Druck-HostKey benutzen', PrintName='Login als Druck-HostKey benutzen',Updated=TO_TIMESTAMP('2020-02-21 14:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543081 AND AD_Language='de_CH'
;

-- 2020-02-21T13:06:33.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543081,'de_CH') 
;

-- 2020-02-21T13:06:42.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet',Updated=TO_TIMESTAMP('2020-02-21 14:06:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543081 AND AD_Language='de_DE'
;

-- 2020-02-21T13:06:42.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543081,'de_DE') 
;

-- 2020-02-21T13:06:42.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543081,'de_DE') 
;

-- 2020-02-21T13:06:42.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLoginAsHostKey', Name='Login As HostKey ', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL WHERE AD_Element_ID=543081
;

-- 2020-02-21T13:06:42.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLoginAsHostKey', Name='Login As HostKey ', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL, AD_Element_ID=543081 WHERE UPPER(ColumnName)='ISLOGINASHOSTKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-21T13:06:42.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLoginAsHostKey', Name='Login As HostKey ', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL WHERE AD_Element_ID=543081 AND IsCentrallyMaintained='Y'
;

-- 2020-02-21T13:06:42.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Login As HostKey ', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543081) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543081)
;

-- 2020-02-21T13:06:42.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Login As HostKey ', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543081
;

-- 2020-02-21T13:06:42.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Login As HostKey ', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL WHERE AD_Element_ID = 543081
;

-- 2020-02-21T13:06:42.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Login As HostKey ', Description = 'Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543081
;

-- 2020-02-21T13:06:54.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Login als Druck-HostKey benutzen', PrintName='Login als Druck-HostKey benutzen',Updated=TO_TIMESTAMP('2020-02-21 14:06:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543081 AND AD_Language='de_DE'
;

-- 2020-02-21T13:06:54.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543081,'de_DE') 
;

-- 2020-02-21T13:06:54.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543081,'de_DE') 
;

-- 2020-02-21T13:06:54.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsLoginAsHostKey', Name='Login als Druck-HostKey benutzen', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL WHERE AD_Element_ID=543081
;

-- 2020-02-21T13:06:54.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLoginAsHostKey', Name='Login als Druck-HostKey benutzen', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL, AD_Element_ID=543081 WHERE UPPER(ColumnName)='ISLOGINASHOSTKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-21T13:06:54.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsLoginAsHostKey', Name='Login als Druck-HostKey benutzen', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL WHERE AD_Element_ID=543081 AND IsCentrallyMaintained='Y'
;

-- 2020-02-21T13:06:54.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Login als Druck-HostKey benutzen', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543081) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543081)
;

-- 2020-02-21T13:06:54.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Login als Druck-HostKey benutzen', Name='Login als Druck-HostKey benutzen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543081)
;

-- 2020-02-21T13:06:54.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Login als Druck-HostKey benutzen', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543081
;

-- 2020-02-21T13:06:54.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Login als Druck-HostKey benutzen', Description='Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', Help=NULL WHERE AD_Element_ID = 543081
;

-- 2020-02-21T13:06:54.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Login als Druck-HostKey benutzen', Description = 'Wenn gesetzt und ein Nutzer meldet sich an, dann wird immer der jeweilige User-Login als Hostkey benutzt, egal von welchem Computer aus sich der Nutzer anmeldet', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543081
;

-- 2020-02-21T13:07:12.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Use Login As Printing HostKey', PrintName='Use Login As Printing HostKey',Updated=TO_TIMESTAMP('2020-02-21 14:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543081 AND AD_Language='en_US'
;

-- 2020-02-21T13:07:12.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543081,'en_US') 
;

-- 2020-02-21T13:51:50.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='ConcatenatePdfs',Updated=TO_TIMESTAMP('2020-02-21 14:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540315
;

-- 2020-02-21T13:57:43.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N', TechnicalNote='Deaktivating for now; it tried to store the PDFs in a local folder; could be modernized to provide the DBs as download to the webui-user',Updated=TO_TIMESTAMP('2020-02-21 14:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540315
;

-- 2020-02-21T13:59:30.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='3', Name='Job-Druckanweisungen neu erstellen',Updated=TO_TIMESTAMP('2020-02-21 14:59:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540324
;

