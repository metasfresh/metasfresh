
-- 2019-04-05T06:17:29.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567632,542936,0,20,541343,'IsAsync',TO_TIMESTAMP('2019-04-05 06:17:28','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.material.cockpit',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Async',0,0,TO_TIMESTAMP('2019-04-05 06:17:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-05T06:17:29.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567632 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-05T06:18:43.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576559,0,'AsyncTimeoutMillis',TO_TIMESTAMP('2019-04-05 06:18:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AsyncTimeoutMillis','AsyncTimeoutMillis',TO_TIMESTAMP('2019-04-05 06:18:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T06:18:43.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576559 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-05T06:21:02.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.', IsTranslated='Y', Name='Max. Wartezeit auf asynchrone Antwort (ms)', PrintName='Max. Wartezeit auf asynchrone Antwort (ms)',Updated=TO_TIMESTAMP('2019-04-05 06:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='de_CH'
;

-- 2019-04-05T06:21:02.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'de_CH') 
;

-- 2019-04-05T06:21:10.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Max. Wartezeit auf asynchrone Antwort (ms)', PrintName='Max. Wartezeit auf asynchrone Antwort (ms)',Updated=TO_TIMESTAMP('2019-04-05 06:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='de_DE'
;

-- 2019-04-05T06:21:10.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'de_DE') 
;

-- 2019-04-05T06:21:10.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576559,'de_DE') 
;

-- 2019-04-05T06:21:10.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AsyncTimeoutMillis', Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description=NULL, Help=NULL WHERE AD_Element_ID=576559
;

-- 2019-04-05T06:21:10.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AsyncTimeoutMillis', Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description=NULL, Help=NULL, AD_Element_ID=576559 WHERE UPPER(ColumnName)='ASYNCTIMEOUTMILLIS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-05T06:21:10.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AsyncTimeoutMillis', Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description=NULL, Help=NULL WHERE AD_Element_ID=576559 AND IsCentrallyMaintained='Y'
;

-- 2019-04-05T06:21:10.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576559) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576559)
;

-- 2019-04-05T06:21:10.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Max. Wartezeit auf asynchrone Antwort (ms)', Name='Max. Wartezeit auf asynchrone Antwort (ms)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576559)
;

-- 2019-04-05T06:21:10.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576559
;

-- 2019-04-05T06:21:10.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description=NULL, Help=NULL WHERE AD_Element_ID = 576559
;

-- 2019-04-05T06:21:10.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Max. Wartezeit auf asynchrone Antwort (ms)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576559
;

-- 2019-04-05T06:21:22.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.',Updated=TO_TIMESTAMP('2019-04-05 06:21:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='de_DE'
;

-- 2019-04-05T06:21:22.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'de_DE') 
;

-- 2019-04-05T06:21:22.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576559,'de_DE') 
;

-- 2019-04-05T06:21:22.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AsyncTimeoutMillis', Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description='Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.', Help=NULL WHERE AD_Element_ID=576559
;

-- 2019-04-05T06:21:22.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AsyncTimeoutMillis', Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description='Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.', Help=NULL, AD_Element_ID=576559 WHERE UPPER(ColumnName)='ASYNCTIMEOUTMILLIS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-05T06:21:22.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AsyncTimeoutMillis', Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description='Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.', Help=NULL WHERE AD_Element_ID=576559 AND IsCentrallyMaintained='Y'
;

-- 2019-04-05T06:21:22.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description='Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576559) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576559)
;

-- 2019-04-05T06:21:22.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description='Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576559
;

-- 2019-04-05T06:21:22.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Max. Wartezeit auf asynchrone Antwort (ms)', Description='Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.', Help=NULL WHERE AD_Element_ID = 576559
;

-- 2019-04-05T06:21:22.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Max. Wartezeit auf asynchrone Antwort (ms)', Description = 'Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576559
;

-- 2019-04-05T06:21:43.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Async timeout (ms)', PrintName='Async timeout (ms)',Updated=TO_TIMESTAMP('2019-04-05 06:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='en_US'
;

-- 2019-04-05T06:21:43.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'en_US') 
;

-- 2019-04-05T06:22:44.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maximum time in milli seconds to wait for an asynchronour result, before the request is aborted with an error message.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-05 06:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576559 AND AD_Language='en_US'
;

-- 2019-04-05T06:22:44.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576559,'en_US') 
;

-- 2019-04-05T06:23:11.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMin,Version) VALUES (0,567633,576559,0,11,541343,'AsyncTimeoutMillis',TO_TIMESTAMP('2019-04-05 06:23:11','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.','de.metas.material.cockpit',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Max. Wartezeit auf asynchrone Antwort (ms)',0,0,TO_TIMESTAMP('2019-04-05 06:23:11','YYYY-MM-DD HH24:MI:SS'),100,'1',0)
;

-- 2019-04-05T06:23:11.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567633 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-05T06:23:30.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsAsync/N@=Y',Updated=TO_TIMESTAMP('2019-04-05 06:23:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567633
;

-- 2019-04-05T06:23:39.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_AvailableForSales_Config','ALTER TABLE public.MD_AvailableForSales_Config ADD COLUMN IsAsync CHAR(1) DEFAULT ''N'' CHECK (IsAsync IN (''Y'',''N'')) NOT NULL')
;

-- 2019-04-05T06:23:42.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_AvailableForSales_Config','ALTER TABLE public.MD_AvailableForSales_Config ADD COLUMN AsyncTimeoutMillis NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- 2019-04-05T06:24:18.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567632,578444,0,541694,TO_TIMESTAMP('2019-04-05 06:24:18','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.material.cockpit','Y','Y','N','N','N','N','N','Async',TO_TIMESTAMP('2019-04-05 06:24:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T06:24:18.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578444 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T06:24:18.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567633,578445,0,541694,TO_TIMESTAMP('2019-04-05 06:24:18','YYYY-MM-DD HH24:MI:SS'),100,'Maximale Zeit in Millisekunden, die bei einer asynchronen Abfrage gewartet wird, bevor ein mit einer Fehlermeldung abgebrochen wird.',14,'de.metas.material.cockpit','Y','Y','N','N','N','N','N','Max. Wartezeit auf asynchrone Antwort (ms)',TO_TIMESTAMP('2019-04-05 06:24:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T06:24:18.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578445 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-05T06:24:42.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsAsync/N@=Y',Updated=TO_TIMESTAMP('2019-04-05 06:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578445
;

-- 2019-04-05T06:25:53.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='misc_settings',Updated=TO_TIMESTAMP('2019-04-05 06:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542428
;

-- 2019-04-05T06:26:19.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,578444,0,541694,542428,558226,'F',TO_TIMESTAMP('2019-04-05 06:26:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsAsync',20,0,0,TO_TIMESTAMP('2019-04-05 06:26:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T06:26:38.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,578445,0,541694,542428,558227,'F',TO_TIMESTAMP('2019-04-05 06:26:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AsyncTimeoutMillis',30,0,0,TO_TIMESTAMP('2019-04-05 06:26:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T06:26:59.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-04-05 06:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558226
;

-- 2019-04-05T06:26:59.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-04-05 06:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558227
;

-- 2019-04-05T06:27:17.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='InsufficientQtyAvailableForSalesColor_ID',Updated=TO_TIMESTAMP('2019-04-05 06:27:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558199
;

-- 2019-04-05T08:32:17.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576560,0,'IsAsync_MD_AvailableForSales_Config',TO_TIMESTAMP('2019-04-05 08:32:17','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.','de.metas.material.cockpit','','Y','Asynchrone Ausführung','Asynchrone Ausführung',TO_TIMESTAMP('2019-04-05 08:32:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T08:32:17.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576560 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-05T08:32:20.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-05 08:32:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_CH'
;

-- 2019-04-05T08:32:20.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_CH') 
;

-- 2019-04-05T08:32:59.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Asynchrone Abfrage', PrintName='Asynchrone Abfrage',Updated=TO_TIMESTAMP('2019-04-05 08:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_DE'
;

-- 2019-04-05T08:32:59.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_DE') 
;

-- 2019-04-05T08:32:59.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576560,'de_DE') 
;

-- 2019-04-05T08:32:59.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID=576560
;

-- 2019-04-05T08:32:59.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.', Help='', AD_Element_ID=576560 WHERE UPPER(ColumnName)='ISASYNC_MD_AVAILABLEFORSALES_CONFIG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-05T08:32:59.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID=576560 AND IsCentrallyMaintained='Y'
;

-- 2019-04-05T08:32:59.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576560) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576560)
;

-- 2019-04-05T08:32:59.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Asynchrone Abfrage', Name='Asynchrone Abfrage' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576560)
;

-- 2019-04-05T08:32:59.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:32:59.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:32:59.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Asynchrone Abfrage', Description = 'Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:33:02.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-05 08:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_DE'
;

-- 2019-04-05T08:33:02.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_DE') 
;

-- 2019-04-05T08:33:02.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576560,'de_DE') 
;

-- 2019-04-05T08:33:06.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Asynchrone Abfrage', PrintName='Asynchrone Abfrage',Updated=TO_TIMESTAMP('2019-04-05 08:33:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_CH'
;

-- 2019-04-05T08:33:06.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_CH') 
;

-- 2019-04-05T08:34:59.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies whether availability request is done asynchronously in the background when working in the web-UI. The respective order lines are updated as soon as the result is found.', IsTranslated='Y', Name='Asynchronous request', PrintName='Asynchronous request',Updated=TO_TIMESTAMP('2019-04-05 08:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='en_US'
;

-- 2019-04-05T08:34:59.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'en_US') 
;

-- 2019-04-05T08:35:26.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,542936,628432,578444,0,540614,TO_TIMESTAMP('2019-04-05 08:35:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-04-05 08:35:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T08:35:43.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert, sobald das Ergebnis vorliegt.',Updated=TO_TIMESTAMP('2019-04-05 08:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_CH'
;

-- 2019-04-05T08:35:43.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_CH') 
;

-- 2019-04-05T08:35:46.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.',Updated=TO_TIMESTAMP('2019-04-05 08:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_CH'
;

-- 2019-04-05T08:35:46.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_CH') 
;

-- 2019-04-05T08:35:51.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.',Updated=TO_TIMESTAMP('2019-04-05 08:35:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_DE'
;

-- 2019-04-05T08:35:51.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_DE') 
;

-- 2019-04-05T08:35:51.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576560,'de_DE') 
;

-- 2019-04-05T08:35:51.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID=576560
;

-- 2019-04-05T08:35:51.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='', AD_Element_ID=576560 WHERE UPPER(ColumnName)='ISASYNC_MD_AVAILABLEFORSALES_CONFIG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-05T08:35:51.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID=576560 AND IsCentrallyMaintained='Y'
;

-- 2019-04-05T08:35:51.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576560) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576560)
;

-- 2019-04-05T08:35:51.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:35:51.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:35:51.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Asynchrone Abfrage', Description = 'Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:36:03.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576560, Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffeden Auftragspositionen werden aktualisert, sobald das Ergebnis vorliegt.', Name='Asynchrone Abfrage',Updated=TO_TIMESTAMP('2019-04-05 08:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578444
;

-- 2019-04-05T08:36:03.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=628432
;

-- 2019-04-05T08:36:03.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576560,628433,578444,0,540614,TO_TIMESTAMP('2019-04-05 08:36:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-04-05 08:36:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-05T08:36:03.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576560) 
;

-- 2019-04-05T08:36:26.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.',Updated=TO_TIMESTAMP('2019-04-05 08:36:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_CH'
;

-- 2019-04-05T08:36:26.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_CH') 
;

-- 2019-04-05T08:36:29.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.',Updated=TO_TIMESTAMP('2019-04-05 08:36:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='de_DE'
;

-- 2019-04-05T08:36:29.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'de_DE') 
;

-- 2019-04-05T08:36:29.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576560,'de_DE') 
;

-- 2019-04-05T08:36:29.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID=576560
;

-- 2019-04-05T08:36:29.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='', AD_Element_ID=576560 WHERE UPPER(ColumnName)='ISASYNC_MD_AVAILABLEFORSALES_CONFIG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-05T08:36:29.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAsync_MD_AvailableForSales_Config', Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID=576560 AND IsCentrallyMaintained='Y'
;

-- 2019-04-05T08:36:29.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576560) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576560)
;

-- 2019-04-05T08:36:29.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:36:29.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Asynchrone Abfrage', Description='Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', Help='' WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:36:29.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Asynchrone Abfrage', Description = 'Entscheidet, ob die Verfügbarkeitsabfrage innerhalb der Web-UI im Hintergrund erfolgt. Die betreffenden Auftragspositionen werden aktualisiert sobald das Ergebnis vorliegt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576560
;

-- 2019-04-05T08:36:45.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies whether availability requests are done asynchronously in the background when working in the web-UI. The respective order lines are updated as soon as the result is found.',Updated=TO_TIMESTAMP('2019-04-05 08:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576560 AND AD_Language='en_US'
;

-- 2019-04-05T08:36:45.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576560,'en_US') 
;

