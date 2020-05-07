-- 2018-12-12T08:34:40.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575916,0,'ExportedXmlMode',TO_TIMESTAMP('2018-12-12 08:34:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Modus der Exportdatei','Modus der Exportdatei',TO_TIMESTAMP('2018-12-12 08:34:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T08:34:40.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575916 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-12-12T08:34:43.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:34:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575916 AND AD_Language='de_CH'
;

-- 2018-12-12T08:34:43.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575916,'de_CH') 
;

-- 2018-12-12T08:34:46.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:34:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575916 AND AD_Language='de_DE'
;

-- 2018-12-12T08:34:46.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575916,'de_DE') 
;

-- 2018-12-12T08:34:46.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575916,'de_DE') 
;

-- 2018-12-12T08:35:14.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:35:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Exported files'' mode',PrintName='Exported files'' mode' WHERE AD_Element_ID=575916 AND AD_Language='en_US'
;

-- 2018-12-12T08:35:14.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575916,'en_US') 
;

-- 2018-12-12T08:35:26.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:35:26','YYYY-MM-DD HH24:MI:SS'),Name='Modus der Exportdateien',PrintName='Modus der Exportdateien' WHERE AD_Element_ID=575916 AND AD_Language='de_DE'
;

-- 2018-12-12T08:35:26.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575916,'de_DE') 
;

-- 2018-12-12T08:35:26.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575916,'de_DE') 
;

-- 2018-12-12T08:35:26.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExportedXmlMode', Name='Modus der Exportdateien', Description=NULL, Help=NULL WHERE AD_Element_ID=575916
;

-- 2018-12-12T08:35:26.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExportedXmlMode', Name='Modus der Exportdateien', Description=NULL, Help=NULL, AD_Element_ID=575916 WHERE UPPER(ColumnName)='EXPORTEDXMLMODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-12T08:35:26.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExportedXmlMode', Name='Modus der Exportdateien', Description=NULL, Help=NULL WHERE AD_Element_ID=575916 AND IsCentrallyMaintained='Y'
;

-- 2018-12-12T08:35:26.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Modus der Exportdateien', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575916) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575916)
;

-- 2018-12-12T08:35:26.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Modus der Exportdateien', Name='Modus der Exportdateien' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575916)
;

-- 2018-12-12T08:35:26.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Modus der Exportdateien', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575916
;

-- 2018-12-12T08:35:26.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Modus der Exportdateien', Description=NULL, Help=NULL WHERE AD_Element_ID = 575916
;

-- 2018-12-12T08:35:26.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Modus der Exportdateien', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575916
;

-- 2018-12-12T08:35:34.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:35:34','YYYY-MM-DD HH24:MI:SS'),Name='Modus der Exportdateien',PrintName='Modus der Exportdateien' WHERE AD_Element_ID=575916 AND AD_Language='de_CH'
;

-- 2018-12-12T08:35:34.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575916,'de_CH') 
;

-- 2018-12-12T08:36:10.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540940,TO_TIMESTAMP('2018-12-12 08:36:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','N','ExportedXmlMode',TO_TIMESTAMP('2018-12-12 08:36:09','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-12-12T08:36:10.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=540940 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-12-12T08:36:50.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540940,541782,TO_TIMESTAMP('2018-12-12 08:36:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Produktion',TO_TIMESTAMP('2018-12-12 08:36:50','YYYY-MM-DD HH24:MI:SS'),100,'p','production')
;

-- 2018-12-12T08:36:50.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541782 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-12-12T08:36:55.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:36:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541782 AND AD_Language='de_CH'
;

-- 2018-12-12T08:37:03.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:37:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='production' WHERE AD_Ref_List_ID=541782 AND AD_Language='en_US'
;

-- 2018-12-12T08:37:22.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540940,541783,TO_TIMESTAMP('2018-12-12 08:37:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Test',TO_TIMESTAMP('2018-12-12 08:37:22','YYYY-MM-DD HH24:MI:SS'),100,'t','test')
;

-- 2018-12-12T08:37:22.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541783 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-12-12T08:37:25.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:37:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541783 AND AD_Language='de_CH'
;

-- 2018-12-12T08:37:31.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:37:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='test' WHERE AD_Ref_List_ID=541783 AND AD_Language='en_US'
;

-- 2018-12-12T08:38:03.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='P',Updated=TO_TIMESTAMP('2018-12-12 08:38:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541782
;

-- 2018-12-12T08:38:07.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='T',Updated=TO_TIMESTAMP('2018-12-12 08:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541783
;

-- 2018-12-12T08:38:18.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,563707,575916,0,17,540940,541145,'ExportedXmlMode',TO_TIMESTAMP('2018-12-12 08:38:18','YYYY-MM-DD HH24:MI:SS'),100,'N','P','de.metas.vertical.healthcare.forum_datenaustausch_ch',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Modus der Exportdateien',0,0,TO_TIMESTAMP('2018-12-12 08:38:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-12-12T08:38:18.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563707 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-12-12T08:38:19.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('HC_Forum_Datenaustausch_Config','ALTER TABLE public.HC_Forum_Datenaustausch_Config ADD COLUMN ExportedXmlMode CHAR(1) DEFAULT ''P'' NOT NULL')
;

-- 2018-12-12T08:38:40.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='Configs for http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2018-12-12 08:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541145
;

-- 2018-12-12T08:40:39.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575917,0,'From_EAN',TO_TIMESTAMP('2018-12-12 08:40:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.cables','Y','Absender EAN','Absender EAN',TO_TIMESTAMP('2018-12-12 08:40:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T08:40:39.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575917 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-12-12T08:40:42.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:40:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575917 AND AD_Language='de_CH'
;

-- 2018-12-12T08:40:42.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575917,'de_CH') 
;

-- 2018-12-12T08:40:45.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:40:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575917 AND AD_Language='de_DE'
;

-- 2018-12-12T08:40:45.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575917,'de_DE') 
;

-- 2018-12-12T08:40:45.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575917,'de_DE') 
;

-- 2018-12-12T08:43:14.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:43:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sender EAN',PrintName='Sender EAN',Description='If set, then this value is used to replace the XML''s request/processing/transport from attribute' WHERE AD_Element_ID=575917 AND AD_Language='en_US'
;

-- 2018-12-12T08:43:14.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575917,'en_US') 
;

-- 2018-12-12T08:43:26.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:43:26','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=575917 AND AD_Language='en_US'
;

-- 2018-12-12T08:43:26.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575917,'en_US') 
;

-- 2018-12-12T08:44:21.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:44:21','YYYY-MM-DD HH24:MI:SS'),Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei' WHERE AD_Element_ID=575917 AND AD_Language='de_DE'
;

-- 2018-12-12T08:44:21.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575917,'de_DE') 
;

-- 2018-12-12T08:44:21.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575917,'de_DE') 
;

-- 2018-12-12T08:44:21.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='From_EAN', Name='Absender EAN', Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei', Help=NULL WHERE AD_Element_ID=575917
;

-- 2018-12-12T08:44:21.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='From_EAN', Name='Absender EAN', Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei', Help=NULL, AD_Element_ID=575917 WHERE UPPER(ColumnName)='FROM_EAN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-12T08:44:21.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='From_EAN', Name='Absender EAN', Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei', Help=NULL WHERE AD_Element_ID=575917 AND IsCentrallyMaintained='Y'
;

-- 2018-12-12T08:44:21.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Absender EAN', Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575917) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575917)
;

-- 2018-12-12T08:44:21.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Absender EAN', Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575917
;

-- 2018-12-12T08:44:21.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Absender EAN', Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei', Help=NULL WHERE AD_Element_ID = 575917
;

-- 2018-12-12T08:44:21.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Absender EAN', Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575917
;

-- 2018-12-12T08:44:24.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 08:44:24','YYYY-MM-DD HH24:MI:SS'),Description='Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei' WHERE AD_Element_ID=575917 AND AD_Language='de_CH'
;

-- 2018-12-12T08:44:24.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575917,'de_CH') 
;

-- 2018-12-12T08:45:14.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,VFormat) VALUES (0,563708,575917,0,10,541145,'From_EAN',TO_TIMESTAMP('2018-12-12 08:45:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei','de.metas.vertical.healthcare.forum_datenaustausch_ch',13,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Absender EAN',0,0,TO_TIMESTAMP('2018-12-12 08:45:13','YYYY-MM-DD HH24:MI:SS'),100,0,'')
;

-- 2018-12-12T08:45:14.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563708 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-12-12T08:49:48.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ExportedXmlVersion',Updated=TO_TIMESTAMP('2018-12-12 08:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544451
;

-- 2018-12-12T08:49:48.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExportedXmlVersion', Name='Export XML Version', Description=NULL, Help=NULL WHERE AD_Element_ID=544451
;

-- 2018-12-12T08:49:48.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExportedXmlVersion', Name='Export XML Version', Description=NULL, Help=NULL, AD_Element_ID=544451 WHERE UPPER(ColumnName)='EXPORTEDXMLVERSION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-12T08:49:48.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExportedXmlVersion', Name='Export XML Version', Description=NULL, Help=NULL WHERE AD_Element_ID=544451 AND IsCentrallyMaintained='Y'
;

SELECT public.db_alter_table('HC_Forum_Datenaustausch_Config','ALTER TABLE public.HC_Forum_Datenaustausch_Config RENAME COLUMN ExportXmlVersion TO ExportedXmlVersion')
;
-- 2018-12-12T09:28:00.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563707,570749,0,541327,TO_TIMESTAMP('2018-12-12 09:28:00','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Y','N','N','N','N','N','Modus der Exportdateien',TO_TIMESTAMP('2018-12-12 09:28:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T09:28:00.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570749 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-12-12T09:28:00.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563708,570750,0,541327,TO_TIMESTAMP('2018-12-12 09:28:00','YYYY-MM-DD HH24:MI:SS'),100,'Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei',13,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Y','N','N','N','N','N','Absender EAN',TO_TIMESTAMP('2018-12-12 09:28:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T09:28:00.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570750 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-12-12T09:29:33.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('HC_Forum_Datenaustausch_Config','ALTER TABLE public.HC_Forum_Datenaustausch_Config ADD COLUMN From_EAN VARCHAR(13)')
;

-- 2018-12-12T09:49:22.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575918,0,'Via_EAN',TO_TIMESTAMP('2018-12-12 09:49:22','YYYY-MM-DD HH24:MI:SS'),100,'Falls angegeben wird dieser Wert den Wert bei request/processing/transport/via in der exportierten XML-Datei angehängt','de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Via EAN','Via EAN',TO_TIMESTAMP('2018-12-12 09:49:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T09:49:22.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575918 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-12-12T09:49:27.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 09:49:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575918 AND AD_Language='de_CH'
;

-- 2018-12-12T09:49:27.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575918,'de_CH') 
;

-- 2018-12-12T09:49:29.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 09:49:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575918 AND AD_Language='de_DE'
;

-- 2018-12-12T09:49:29.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575918,'de_DE') 
;

-- 2018-12-12T09:49:29.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575918,'de_DE') 
;

-- 2018-12-12T09:50:14.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 09:50:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='If set, then this value appended to the XML''s request/processing/transport from/via elements' WHERE AD_Element_ID=575918 AND AD_Language='en_US'
;

-- 2018-12-12T09:50:14.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575918,'en_US') 
;

-- 2018-12-12T09:50:38.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,563709,575918,0,10,541145,'Via_EAN',TO_TIMESTAMP('2018-12-12 09:50:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Falls angegeben wird dieser Wert den Wert bei request/processing/transport/via in der exportierten XML-Datei angehängt','de.metas.vertical.healthcare.forum_datenaustausch_ch',13,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Via EAN',0,0,TO_TIMESTAMP('2018-12-12 09:50:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-12-12T09:50:38.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563709 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-12-12T09:50:41.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('HC_Forum_Datenaustausch_Config','ALTER TABLE public.HC_Forum_Datenaustausch_Config ADD COLUMN Via_EAN VARCHAR(13)')
;

-- 2018-12-12T09:50:58.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563709,570751,0,541327,TO_TIMESTAMP('2018-12-12 09:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Falls angegeben wird dieser Wert den Wert bei request/processing/transport/via in der exportierten XML-Datei angehängt',13,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Y','N','N','N','N','N','Via EAN',TO_TIMESTAMP('2018-12-12 09:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T09:50:58.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570751 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-12-12T09:51:27.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2018-12-12 09:51:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563275
;

-- 2018-12-12T09:51:27.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2018-12-12 09:51:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563283
;

-- 2018-12-12T11:57:35.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='11111111111111', IsMandatory='Y',Updated=TO_TIMESTAMP('2018-12-12 11:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563709
;

-- 2018-12-12T11:57:47.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='1111111111111',Updated=TO_TIMESTAMP('2018-12-12 11:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563709
;

-- 2018-12-12T11:57:48.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('hc_forum_datenaustausch_config','Via_EAN','VARCHAR(13)',null,'1111111111111')
;

-- 2018-12-12T11:57:48.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE HC_Forum_Datenaustausch_Config SET Via_EAN='1111111111111' WHERE Via_EAN IS NULL
;

-- 2018-12-12T11:57:48.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('hc_forum_datenaustausch_config','Via_EAN',null,'NOT NULL',null)
;

-- 2018-12-12T11:59:07.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 11:59:07','YYYY-MM-DD HH24:MI:SS'),Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.' WHERE AD_Element_ID=575918 AND AD_Language='de_CH'
;

-- 2018-12-12T11:59:07.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575918,'de_CH') 
;

-- 2018-12-12T11:59:15.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 11:59:15','YYYY-MM-DD HH24:MI:SS'),Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.' WHERE AD_Element_ID=575918 AND AD_Language='de_DE'
;

-- 2018-12-12T11:59:15.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575918,'de_DE') 
;

-- 2018-12-12T11:59:15.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575918,'de_DE') 
;

-- 2018-12-12T11:59:15.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Via_EAN', Name='Via EAN', Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.', Help=NULL WHERE AD_Element_ID=575918
;

-- 2018-12-12T11:59:15.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Via_EAN', Name='Via EAN', Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.', Help=NULL, AD_Element_ID=575918 WHERE UPPER(ColumnName)='VIA_EAN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-12T11:59:15.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Via_EAN', Name='Via EAN', Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.', Help=NULL WHERE AD_Element_ID=575918 AND IsCentrallyMaintained='Y'
;

-- 2018-12-12T11:59:15.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Via EAN', Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575918) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575918)
;

-- 2018-12-12T11:59:15.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Via EAN', Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575918
;

-- 2018-12-12T11:59:15.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Via EAN', Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.', Help=NULL WHERE AD_Element_ID = 575918
;

-- 2018-12-12T11:59:15.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Via EAN', Description='Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575918
;

-- 2018-12-12T11:59:43.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 11:59:43','YYYY-MM-DD HH24:MI:SS'),Description='This value set to the XML''s request/processing/transport from/via element; Posbbile pre-existing "via" nodes are removed.' WHERE AD_Element_ID=575918 AND AD_Language='en_US'
;

-- 2018-12-12T11:59:43.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575918,'en_US') 
;

-- 2018-12-12T11:59:53.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 11:59:53','YYYY-MM-DD HH24:MI:SS'),Description='' WHERE AD_Element_ID=575918 AND AD_Language='nl_NL'
;

-- 2018-12-12T11:59:53.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575918,'nl_NL') 
;

-- 2018-12-12T12:01:36.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541327,540950,TO_TIMESTAMP('2018-12-12 12:01:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-12-12 12:01:36','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-12-12T12:01:36.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=540950 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-12-12T12:01:37.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541235,540950,TO_TIMESTAMP('2018-12-12 12:01:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-12-12 12:01:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:37.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541236,540950,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:37.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541235,541948,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:37.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570749,0,541327,541948,554457,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Modus der Exportdateien',10,10,0,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:37.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570750,0,541327,541948,554458,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei','Y','N','Y','Y','N','Absender EAN',20,20,0,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:37.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570751,0,541327,541948,554459,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.','Y','N','Y','Y','N','Via EAN',30,30,0,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:37.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,569319,0,541327,541948,554460,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',40,40,0,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:37.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,569536,0,541327,541948,554461,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',50,50,0,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:37.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,569321,0,541327,541948,554462,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Export XML Version',60,60,0,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:38.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,569538,0,541327,541948,554463,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, in dem exportierte Dateien gespeichert werden.','Y','N','Y','Y','N','Speicherverzeichnis',70,70,0,TO_TIMESTAMP('2018-12-12 12:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:01:38.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,569537,0,541327,541948,554464,TO_TIMESTAMP('2018-12-12 12:01:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',80,80,0,TO_TIMESTAMP('2018-12-12 12:01:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:02:36.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-12-12 12:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569318
;

-- 2018-12-12T12:03:12.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2018-12-12 12:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569317
;

-- 2018-12-12T12:34:50.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541236,541949,TO_TIMESTAMP('2018-12-12 12:34:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2018-12-12 12:34:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:34:57.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541236,541950,TO_TIMESTAMP('2018-12-12 12:34:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2018-12-12 12:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:35:23.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541949, SeqNo=10,Updated=TO_TIMESTAMP('2018-12-12 12:35:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554460
;

-- 2018-12-12T12:39:38.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,569318,0,541327,541950,554465,'F',TO_TIMESTAMP('2018-12-12 12:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AD_Org_ID',10,0,0,TO_TIMESTAMP('2018-12-12 12:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:39:50.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,569317,0,541327,541950,554466,'F',TO_TIMESTAMP('2018-12-12 12:39:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AD_Client_ID',20,0,0,TO_TIMESTAMP('2018-12-12 12:39:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:41:37.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541235,541951,TO_TIMESTAMP('2018-12-12 12:41:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','aux',20,TO_TIMESTAMP('2018-12-12 12:41:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:41:44.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='main',Updated=TO_TIMESTAMP('2018-12-12 12:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541948
;

-- 2018-12-12T12:41:54.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541951, SeqNo=10,Updated=TO_TIMESTAMP('2018-12-12 12:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554461
;

-- 2018-12-12T12:43:18.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2018-12-12 12:43:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554462
;

-- 2018-12-12T12:43:36.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=11,Updated=TO_TIMESTAMP('2018-12-12 12:43:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554462
;

-- 2018-12-12T12:43:45.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2018-12-12 12:43:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554457
;

-- 2018-12-12T12:44:26.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541949, SeqNo=20,Updated=TO_TIMESTAMP('2018-12-12 12:44:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554464
;

-- 2018-12-12T12:45:53.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541951, SeqNo=20,Updated=TO_TIMESTAMP('2018-12-12 12:45:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554464
;

-- 2018-12-12T12:46:27.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-12-12 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554464
;

-- 2018-12-12T12:46:27.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-12-12 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554463
;

-- 2018-12-12T12:46:27.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-12-12 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554462
;

-- 2018-12-12T12:46:27.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-12-12 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554457
;

-- 2018-12-12T12:46:27.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-12-12 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554458
;

-- 2018-12-12T12:46:27.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-12-12 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554459
;

-- 2018-12-12T12:46:27.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-12-12 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554460
;

-- 2018-12-12T12:46:27.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2018-12-12 12:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554465
;

-- 2018-12-12T12:47:10.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2018-12-12 12:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563709
;

-- 2018-12-12T12:47:12.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('hc_forum_datenaustausch_config','Via_EAN','VARCHAR(13)',null,null)
;

-- 2018-12-12T12:50:47.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=2039, ColumnName='Bill_BPartner_ID', Description='Geschäftspartners für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt', Name='Rechnungspartner',Updated=TO_TIMESTAMP('2018-12-12 12:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563283
;

-- 2018-12-12T12:50:47.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungspartner', Description='Geschäftspartners für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt' WHERE AD_Column_ID=563283
;

select db_alter_table('HC_Forum_Datenaustausch_Config', 'ALTER TABLE HC_Forum_Datenaustausch_Config RENAME COLUMN C_BPartner_ID TO Bill_BPartner_ID');
-- 2018-12-12T12:53:09.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:53:09','YYYY-MM-DD HH24:MI:SS'),Description='Geschäftspartner für die Rechnungsstellung' WHERE AD_Element_ID=2039 AND AD_Language='de_CH'
;

-- 2018-12-12T12:53:09.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2039,'de_CH') 
;

-- 2018-12-12T12:53:18.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:53:18','YYYY-MM-DD HH24:MI:SS'),Description='Geschäftspartner für die Rechnungsstellung' WHERE AD_Element_ID=2039 AND AD_Language='nl_NL'
;

-- 2018-12-12T12:53:18.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2039,'nl_NL') 
;

-- 2018-12-12T12:53:28.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:53:28','YYYY-MM-DD HH24:MI:SS'),PrintName='Factuur van',Description='',Help='' WHERE AD_Element_ID=2039 AND AD_Language='nl_NL'
;

-- 2018-12-12T12:53:28.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2039,'nl_NL') 
;

-- 2018-12-12T12:53:32.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:53:32','YYYY-MM-DD HH24:MI:SS'),Description='Geschäftspartner für die Rechnungsstellung' WHERE AD_Element_ID=2039 AND AD_Language='de_DE'
;

-- 2018-12-12T12:53:32.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2039,'de_DE') 
;

-- 2018-12-12T12:53:32.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2039,'de_DE') 
;

-- 2018-12-12T12:53:32.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Bill_BPartner_ID', Name='Rechnungspartner', Description='Geschäftspartner für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt' WHERE AD_Element_ID=2039
;

-- 2018-12-12T12:53:32.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_ID', Name='Rechnungspartner', Description='Geschäftspartner für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt', AD_Element_ID=2039 WHERE UPPER(ColumnName)='BILL_BPARTNER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-12-12T12:53:32.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_ID', Name='Rechnungspartner', Description='Geschäftspartner für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt' WHERE AD_Element_ID=2039 AND IsCentrallyMaintained='Y'
;

-- 2018-12-12T12:53:32.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungspartner', Description='Geschäftspartner für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2039) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2039)
;

-- 2018-12-12T12:53:32.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungspartner', Description='Geschäftspartner für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt', CommitWarning = NULL WHERE AD_Element_ID = 2039
;

-- 2018-12-12T12:53:32.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungspartner', Description='Geschäftspartner für die Rechnungsstellung', Help='Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt' WHERE AD_Element_ID = 2039
;

-- 2018-12-12T12:53:32.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Rechnungspartner', Description='Geschäftspartner für die Rechnungsstellung', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2039
;

-- 2018-12-12T12:53:33.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:53:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=2039 AND AD_Language='de_DE'
;

-- 2018-12-12T12:53:33.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2039,'de_DE') 
;

-- 2018-12-12T12:53:33.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2039,'de_DE') 
;

-- 2018-12-12T12:54:56.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575919,0,TO_TIMESTAMP('2018-12-12 12:54:55','YYYY-MM-DD HH24:MI:SS'),100,'Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.','de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','Rechnungspartner','Rechnungspartner',TO_TIMESTAMP('2018-12-12 12:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:54:56.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575919 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-12-12T12:55:17.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:55:17','YYYY-MM-DD HH24:MI:SS'),Name='XML-Empfänger',PrintName='XML-Empfänger' WHERE AD_Element_ID=575919 AND AD_Language='de_CH'
;

-- 2018-12-12T12:55:17.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575919,'de_CH') 
;

-- 2018-12-12T12:55:21.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:55:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575919 AND AD_Language='de_CH'
;

-- 2018-12-12T12:55:21.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575919,'de_CH') 
;

-- 2018-12-12T12:55:24.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:55:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='XML-Empfänger',PrintName='XML-Empfänger' WHERE AD_Element_ID=575919 AND AD_Language='de_DE'
;

-- 2018-12-12T12:55:24.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575919,'de_DE') 
;

-- 2018-12-12T12:55:24.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575919,'de_DE') 
;

-- 2018-12-12T12:55:24.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='XML-Empfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL WHERE AD_Element_ID=575919
;

-- 2018-12-12T12:55:24.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='XML-Empfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL WHERE AD_Element_ID=575919 AND IsCentrallyMaintained='Y'
;

-- 2018-12-12T12:55:24.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='XML-Empfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575919) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575919)
;

-- 2018-12-12T12:55:24.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='XML-Empfänger', Name='XML-Empfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575919)
;

-- 2018-12-12T12:55:24.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='XML-Empfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575919
;

-- 2018-12-12T12:55:24.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='XML-Empfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL WHERE AD_Element_ID = 575919
;

-- 2018-12-12T12:55:24.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='XML-Empfänger', Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575919
;

-- 2018-12-12T12:55:34.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 12:55:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='XML Recipient',PrintName='XML Recipient',Description='' WHERE AD_Element_ID=575919 AND AD_Language='en_US'
;

-- 2018-12-12T12:55:34.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575919,'en_US') 
;

-- 2018-12-12T12:55:43.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=623477
;

-- 2018-12-12T12:55:44.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,2039,625052,569536,0,540490,TO_TIMESTAMP('2018-12-12 12:55:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-12 12:55:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:55:46.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=575919, Description='Wenn gesetzt, dann gilt der betreffende Datensatz nur für den jeweiligen Rechnungspartner.', Help=NULL, Name='XML-Empfänger',Updated=TO_TIMESTAMP('2018-12-12 12:55:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569536
;

-- 2018-12-12T12:55:46.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625052
;

-- 2018-12-12T12:55:46.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575919,625053,569536,0,540490,TO_TIMESTAMP('2018-12-12 12:55:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-12 12:55:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-12T12:55:46.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575919) 
;

