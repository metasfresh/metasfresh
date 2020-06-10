-- 2020-04-29T15:02:20.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577691,0,'ShowInactiveValues',TO_TIMESTAMP('2020-04-29 18:02:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Show Inactive Values','Show Inactive Values',TO_TIMESTAMP('2020-04-29 18:02:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T15:02:20.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577691 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-04-29T15:03:35.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inaktive Werte anzeigen', PrintName='Inaktive Werte anzeigen',Updated=TO_TIMESTAMP('2020-04-29 18:03:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691 AND AD_Language='de_CH'
;

-- 2020-04-29T15:03:35.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577691,'de_CH') 
;

-- 2020-04-29T15:03:40.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inaktive Werte anzeigen', PrintName='Inaktive Werte anzeigen',Updated=TO_TIMESTAMP('2020-04-29 18:03:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691 AND AD_Language='de_DE'
;

-- 2020-04-29T15:03:40.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577691,'de_DE') 
;

-- 2020-04-29T15:03:40.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577691,'de_DE') 
;

-- 2020-04-29T15:03:40.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description=NULL, Help=NULL WHERE AD_Element_ID=577691
;

-- 2020-04-29T15:03:40.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description=NULL, Help=NULL, AD_Element_ID=577691 WHERE UPPER(ColumnName)='SHOWINACTIVEVALUES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-29T15:03:40.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description=NULL, Help=NULL WHERE AD_Element_ID=577691 AND IsCentrallyMaintained='Y'
;

-- 2020-04-29T15:03:40.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inaktive Werte anzeigen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577691) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577691)
;

-- 2020-04-29T15:03:40.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Inaktive Werte anzeigen', Name='Inaktive Werte anzeigen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577691)
;

-- 2020-04-29T15:03:40.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inaktive Werte anzeigen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:03:40.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inaktive Werte anzeigen', Description=NULL, Help=NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:03:40.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inaktive Werte anzeigen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:03:45.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-04-29 18:03:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691 AND AD_Language='en_US'
;

-- 2020-04-29T15:03:45.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577691,'en_US') 
;

-- 2020-04-29T15:04:02.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570653,577691,0,20,103,'ShowInactiveValues',TO_TIMESTAMP('2020-04-29 18:04:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Inaktive Werte anzeigen',0,0,TO_TIMESTAMP('2020-04-29 18:04:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-04-29T15:04:02.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570653 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-04-29T15:04:02.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577691) 
;

-- 2020-04-29T15:04:08.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Ref_Table','ALTER TABLE public.AD_Ref_Table ADD COLUMN ShowInactiveValues CHAR(1) DEFAULT ''N'' CHECK (ShowInactiveValues IN (''Y'',''N'')) NOT NULL')
;

-- 2020-04-29T15:47:16.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Inaktive referenziere Datenästze mit anzeigen',Updated=TO_TIMESTAMP('2020-04-29 18:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691
;

-- 2020-04-29T15:47:16.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL WHERE AD_Element_ID=577691
;

-- 2020-04-29T15:47:16.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL, AD_Element_ID=577691 WHERE UPPER(ColumnName)='SHOWINACTIVEVALUES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-29T15:47:16.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL WHERE AD_Element_ID=577691 AND IsCentrallyMaintained='Y'
;

-- 2020-04-29T15:47:16.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577691) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577691)
;

-- 2020-04-29T15:47:16.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:47:16.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:47:16.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inaktive Werte anzeigen', Description = 'Inaktive referenziere Datenästze mit anzeigen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:47:20.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Inaktive referenziere Datenästze mit anzeigen',Updated=TO_TIMESTAMP('2020-04-29 18:47:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691 AND AD_Language='de_CH'
;

-- 2020-04-29T15:47:20.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577691,'de_CH') 
;

-- 2020-04-29T15:47:22.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Inaktive referenziere Datenästze mit anzeigen',Updated=TO_TIMESTAMP('2020-04-29 18:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691 AND AD_Language='de_DE'
;

-- 2020-04-29T15:47:22.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577691,'de_DE') 
;

-- 2020-04-29T15:47:22.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577691,'de_DE') 
;

-- 2020-04-29T15:47:22.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL WHERE AD_Element_ID=577691
;

-- 2020-04-29T15:47:22.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL, AD_Element_ID=577691 WHERE UPPER(ColumnName)='SHOWINACTIVEVALUES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-29T15:47:22.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL WHERE AD_Element_ID=577691 AND IsCentrallyMaintained='Y'
;

-- 2020-04-29T15:47:22.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577691) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577691)
;

-- 2020-04-29T15:47:22.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:47:22.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inaktive Werte anzeigen', Description='Inaktive referenziere Datenästze mit anzeigen', Help=NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:47:22.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inaktive Werte anzeigen', Description = 'Inaktive referenziere Datenästze mit anzeigen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T15:47:32.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Also show inactive referenced records',Updated=TO_TIMESTAMP('2020-04-29 18:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691 AND AD_Language='en_US'
;

-- 2020-04-29T15:47:32.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577691,'en_US') 
;

-- 2020-04-29T15:48:46.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,Description,EntityType,AD_Org_ID) VALUES (103,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2020-04-29 18:48:46','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2020-04-29 18:48:46','YYYY-MM-DD HH24:MI:SS'),100,606032,'Y',130,130,1,1,570653,'Inaktive Werte anzeigen','Inaktive referenziere Datenästze mit anzeigen','D',0)
;

-- 2020-04-29T15:48:46.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=606032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-04-29T15:48:46.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577691) 
;

-- 2020-04-29T15:48:46.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=606032
;

-- 2020-04-29T15:48:46.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(606032)
;

-- 2020-04-29T15:49:09.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=75, SeqNoGrid=75,Updated=TO_TIMESTAMP('2020-04-29 18:49:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=606032
;





-- 2020-04-29T16:11:45.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Inaktive referenzierte Datensätze mit anzeigen',Updated=TO_TIMESTAMP('2020-04-29 19:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691
;

-- 2020-04-29T16:11:45.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL WHERE AD_Element_ID=577691
;

-- 2020-04-29T16:11:45.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL, AD_Element_ID=577691 WHERE UPPER(ColumnName)='SHOWINACTIVEVALUES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-29T16:11:45.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL WHERE AD_Element_ID=577691 AND IsCentrallyMaintained='Y'
;

-- 2020-04-29T16:11:45.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577691) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577691)
;

-- 2020-04-29T16:11:45.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T16:11:45.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T16:11:45.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inaktive Werte anzeigen', Description = 'Inaktive referenzierte Datensätze mit anzeigen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T16:11:49.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Inaktive referenzierte Datensätze mit anzeigen',Updated=TO_TIMESTAMP('2020-04-29 19:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691 AND AD_Language='de_CH'
;

-- 2020-04-29T16:11:49.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577691,'de_CH') 
;

-- 2020-04-29T16:11:51.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Inaktive referenzierte Datensätze mit anzeigen',Updated=TO_TIMESTAMP('2020-04-29 19:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577691 AND AD_Language='de_DE'
;

-- 2020-04-29T16:11:51.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577691,'de_DE') 
;

-- 2020-04-29T16:11:51.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577691,'de_DE') 
;

-- 2020-04-29T16:11:51.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL WHERE AD_Element_ID=577691
;

-- 2020-04-29T16:11:51.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL, AD_Element_ID=577691 WHERE UPPER(ColumnName)='SHOWINACTIVEVALUES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-29T16:11:51.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShowInactiveValues', Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL WHERE AD_Element_ID=577691 AND IsCentrallyMaintained='Y'
;

-- 2020-04-29T16:11:51.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577691) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577691)
;

-- 2020-04-29T16:11:51.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T16:11:51.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inaktive Werte anzeigen', Description='Inaktive referenzierte Datensätze mit anzeigen', Help=NULL WHERE AD_Element_ID = 577691
;

-- 2020-04-29T16:11:51.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inaktive Werte anzeigen', Description = 'Inaktive referenzierte Datensätze mit anzeigen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577691
;




