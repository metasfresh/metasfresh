-- 2019-10-20T19:36:51.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Flatrate_Conditions.Type_Conditions NOT IN (''FlatFee'',''HoldingFee'', ''Subscr'', ''Procuremnt'', ''Commission'')',Updated=TO_TIMESTAMP('2019-10-20 21:36:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540237
;

-- 2019-10-20T19:51:43.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''Refund',Updated=TO_TIMESTAMP('2019-10-20 21:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551009
;

-- 2019-10-20T19:53:49.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission''',Updated=TO_TIMESTAMP('2019-10-20 21:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551476
;

-- 2019-10-20T19:54:32.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission''',Updated=TO_TIMESTAMP('2019-10-20 21:54:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547848
;

-- 2019-10-20T20:02:11.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Flatrate_Conditions.Type_Conditions NOT IN (''FlatFee'',''HoldingFee'', ''Subscr'', ''Procuremnt'')',Updated=TO_TIMESTAMP('2019-10-20 22:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540237
;

-- 2019-10-20T20:45:38.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='', Name='Ist Vertriebspartner', PO_Description='', PrintName='Ist Vertriebspartner',Updated=TO_TIMESTAMP('2019-10-20 22:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=409 AND AD_Language='de_CH'
;

-- 2019-10-20T20:45:38.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(409,'de_CH') 
;

-- 2019-10-20T20:45:50.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='',Updated=TO_TIMESTAMP('2019-10-20 22:45:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=409 AND AD_Language='de_DE'
;

-- 2019-10-20T20:45:50.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(409,'de_DE') 
;

-- 2019-10-20T20:45:50.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(409,'de_DE') 
;

-- 2019-10-20T20:45:50.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSalesRep', Name='Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='' WHERE AD_Element_ID=409
;

-- 2019-10-20T20:45:50.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesRep', Name='Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='', AD_Element_ID=409 WHERE UPPER(ColumnName)='ISSALESREP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-20T20:45:50.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesRep', Name='Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='' WHERE AD_Element_ID=409 AND IsCentrallyMaintained='Y'
;

-- 2019-10-20T20:45:50.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=409) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 409)
;

-- 2019-10-20T20:45:50.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 409
;

-- 2019-10-20T20:45:50.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='' WHERE AD_Element_ID = 409
;

-- 2019-10-20T20:45:50.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertriebspartner', Description = 'Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 409
;

-- 2019-10-20T20:46:27.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ist Vertriebspartner', PrintName='Ist Vertriebspartner',Updated=TO_TIMESTAMP('2019-10-20 22:46:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=409 AND AD_Language='de_DE'
;

-- 2019-10-20T20:46:27.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(409,'de_DE') 
;

-- 2019-10-20T20:46:27.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(409,'de_DE') 
;

-- 2019-10-20T20:46:27.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSalesRep', Name='Ist Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='' WHERE AD_Element_ID=409
;

-- 2019-10-20T20:46:27.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesRep', Name='Ist Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='', AD_Element_ID=409 WHERE UPPER(ColumnName)='ISSALESREP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-20T20:46:27.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesRep', Name='Ist Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='' WHERE AD_Element_ID=409 AND IsCentrallyMaintained='Y'
;

-- 2019-10-20T20:46:27.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ist Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=409) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 409)
;

-- 2019-10-20T20:46:27.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ist Vertriebspartner', Name='Ist Vertriebspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=409)
;

-- 2019-10-20T20:46:27.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ist Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 409
;

-- 2019-10-20T20:46:27.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ist Vertriebspartner', Description='Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', Help='' WHERE AD_Element_ID = 409
;

-- 2019-10-20T20:46:27.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ist Vertriebspartner', Description = 'Mit Vertriebspartnern können Provisionsverträge abgeschlossen werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 409
;

-- 2019-10-20T20:47:04.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugeordneter Vertriebspartner', PrintName='Zugeordneter Vertriebspartner',Updated=TO_TIMESTAMP('2019-10-20 22:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='de_CH'
;

-- 2019-10-20T20:47:04.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'de_CH') 
;

-- 2019-10-20T20:47:17.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugeordneter Vertriebspartner', PrintName='Zugeordneter Vertriebspartner',Updated=TO_TIMESTAMP('2019-10-20 22:47:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541357 AND AD_Language='de_DE'
;

-- 2019-10-20T20:47:17.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541357,'de_DE') 
;

-- 2019-10-20T20:47:17.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541357,'de_DE') 
;

-- 2019-10-20T20:47:17.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_SalesRep_ID', Name='Zugeordneter Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=541357
;

-- 2019-10-20T20:47:17.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_SalesRep_ID', Name='Zugeordneter Vertriebspartner', Description=NULL, Help=NULL, AD_Element_ID=541357 WHERE UPPER(ColumnName)='C_BPARTNER_SALESREP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-20T20:47:17.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_SalesRep_ID', Name='Zugeordneter Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=541357 AND IsCentrallyMaintained='Y'
;

-- 2019-10-20T20:47:17.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordneter Vertriebspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541357) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541357)
;

-- 2019-10-20T20:47:17.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugeordneter Vertriebspartner', Name='Zugeordneter Vertriebspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541357)
;

-- 2019-10-20T20:47:17.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zugeordneter Vertriebspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541357
;

-- 2019-10-20T20:47:17.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zugeordneter Vertriebspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 541357
;

-- 2019-10-20T20:47:17.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zugeordneter Vertriebspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541357
;

-- 2019-10-20T20:49:14.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568799,590593,0,223,0,TO_TIMESTAMP('2019-10-20 22:49:14','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.contracts.commission',0,'Y','Y','Y','N','N','N','N','N','Zugeordneter Vertriebspartner',340,330,0,1,1,TO_TIMESTAMP('2019-10-20 22:49:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-20T20:49:14.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-20T20:49:14.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541357) 
;

-- 2019-10-20T20:49:14.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590593
;

-- 2019-10-20T20:49:14.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590593)
;

-- 2019-10-20T20:49:56.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568800,590594,0,223,0,TO_TIMESTAMP('2019-10-20 22:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Legt für den Rechnungspartner fest, ob bei einer Beauftragung der Vertriebspartner angegeben werden muss.',0,'de.metas.contracts.commission',0,'Y','Y','Y','N','N','N','N','N','Nur mit Vertriebspartner',350,340,0,1,1,TO_TIMESTAMP('2019-10-20 22:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-20T20:49:56.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590594 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-20T20:49:56.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577106) 
;

-- 2019-10-20T20:49:56.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590594
;

-- 2019-10-20T20:49:56.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590594)
;

-- 2019-10-20T20:51:10.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590594,0,223,540672,563579,'F',TO_TIMESTAMP('2019-10-20 22:51:10','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','N','Y','N','N','N',0,'IsSalesPartnerRequired',310,0,0,TO_TIMESTAMP('2019-10-20 22:51:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-20T20:51:40.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590593,0,223,540672,563580,'F',TO_TIMESTAMP('2019-10-20 22:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BPartner_SalesRep_ID',320,0,0,TO_TIMESTAMP('2019-10-20 22:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-20T20:59:57.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceActual/null@!null',Updated=TO_TIMESTAMP('2019-10-20 22:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548289
;

-- 2019-10-20T21:00:06.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''X''@=''Subscr''',Updated=TO_TIMESTAMP('2019-10-20 23:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547322
;

-- 2019-10-20T21:00:13.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''X''@=''Subscr''',Updated=TO_TIMESTAMP('2019-10-20 23:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547323
;

-- 2019-10-20T21:00:18.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''X''@=''Subscr''',Updated=TO_TIMESTAMP('2019-10-20 23:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547296
;

-- 2019-10-20T21:00:26.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''X''@=''Subscr''',Updated=TO_TIMESTAMP('2019-10-20 23:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547297
;

-- 2019-10-20T21:00:29.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions@=''Subscr''  | @Type_Conditions/''X''@=''Refundable''',Updated=TO_TIMESTAMP('2019-10-20 23:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547761
;

-- 2019-10-20T21:00:32.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''X''@=''Subscr'' | @Type_Conditions/''X''@=''Refund''',Updated=TO_TIMESTAMP('2019-10-20 23:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- 2019-10-20T21:00:34.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''X''@=''Subscr''  | @Type_Conditions/''X''@=''Refundable''',Updated=TO_TIMESTAMP('2019-10-20 23:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547761
;

-- 2019-10-20T21:00:44.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''X''@=''FlatFee''|@Type_Conditions/''X''@=''Subscr''',Updated=TO_TIMESTAMP('2019-10-20 23:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545989
;

-- 2019-10-20T21:00:48.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@C_FlatrateTerm_Next_ID/0@!0',Updated=TO_TIMESTAMP('2019-10-20 23:00:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546050
;

-- 2019-10-20T21:00:52.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Type_Conditions/''X''@=''Subscr''',Updated=TO_TIMESTAMP('2019-10-20 23:00:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557315
;

-- 2019-10-20T21:01:05.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Type_Conditions@=''Procuremnt'' | @Type_Conditions/''X''@=''Subscr'' | @Type_Conditions/''X''@=''Refund''',Updated=TO_TIMESTAMP('2019-10-20 23:01:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- 2019-10-20T21:01:13.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Processed/Y@=Y',Updated=TO_TIMESTAMP('2019-10-20 23:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546056
;

-- 2019-10-20T21:01:20.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Processed/N@=Y',Updated=TO_TIMESTAMP('2019-10-20 23:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546054
;

-- 2019-10-20T21:01:25.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@Processed/N@=Y',Updated=TO_TIMESTAMP('2019-10-20 23:01:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546056
;

-- 2019-10-20T22:04:32.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_ILCandHandler (AD_Client_ID,AD_Org_ID,C_ILCandHandler_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,Is_AD_User_InCharge_UI_Setting,Name,TableName,Updated,UpdatedBy) VALUES (0,0,540021,'de.metas.contracts.commission.invoicecandidate.CommissionShareHandler',TO_TIMESTAMP('2019-10-21 00:04:31','YYYY-MM-DD HH24:MI:SS'),100,'Creates commission releated invoice candidates for C_Commission_Share records','de.metas.contracts.commission','Y','N','C_Commission_Share','C_Commission_Share',TO_TIMESTAMP('2019-10-21 00:04:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-20T22:07:53.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='de.metas.contracts.commission', Name='Provisionsabrechnung', ValueName='CommissionSettlement',Updated=TO_TIMESTAMP('2019-10-21 00:07:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540267
;

-- 2019-10-20T22:09:29.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Provisionsabrechnung',Updated=TO_TIMESTAMP('2019-10-21 00:09:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=540267
;

-- 2019-10-20T22:09:38.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Commission settlement',Updated=TO_TIMESTAMP('2019-10-21 00:09:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=540267
;

-- 2019-10-20T22:09:44.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Provisionsabrechnung',Updated=TO_TIMESTAMP('2019-10-21 00:09:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=540267
;

-- 2019-10-20T22:12:28.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT'', ''CommissionSettlement''))
OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))
OR (''@DocBaseType@'' IN(''API'', ''MOP'') AND AD_Ref_List.Value IN (''QI'', ''VI''))
OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value = ''MD'')
OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'')
OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'')
OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS''))
OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'')) /* fallback for the rest of the entries */
',Updated=TO_TIMESTAMP('2019-10-21 00:12:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2019-10-20T22:20:46.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT''))
OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC''))
OR (''@DocBaseType@''=''API'' AND (AD_Ref_List.Value IN (''QI'', ''VI'') OR AD_Ref_List.ValueName IN (''CommissionSettlement'')))
OR (''@DocBaseType@''=''MOP'' AND AD_Ref_List.Value IN (''QI'', ''VI''))
OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value = ''MD'')
OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'')
OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'')
OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS''))
OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'')) /* fallback for the rest of the entries */
',Updated=TO_TIMESTAMP('2019-10-21 00:20:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

