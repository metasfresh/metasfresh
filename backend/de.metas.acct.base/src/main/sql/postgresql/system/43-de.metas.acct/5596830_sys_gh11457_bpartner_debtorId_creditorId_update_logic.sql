-- 2021-07-06T09:42:45.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579461,0,'IsAutoSetDebtoridAndCreditorid',TO_TIMESTAMP('2021-07-06 12:42:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Debitor- und Kreditor-ID automatisch setzen','Debitor- und Kreditor-ID automatisch setzen',TO_TIMESTAMP('2021-07-06 12:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:42:45.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579461 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-06T09:43:06.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Set debtor-id and creditor-id automatically', PrintName='Set debtor-id and creditor-id automatically',Updated=TO_TIMESTAMP('2021-07-06 12:43:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='en_US'
;

-- 2021-07-06T09:43:06.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'en_US') 
;

-- 2021-07-06T09:44:28.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='If ticked, then metasfresh from there onwards updates a business partner''s debitorId and creditorId automatically from the bartner''s respective value and an optional prefix, whenever a partner''s value is updated or a new partner is created.',Updated=TO_TIMESTAMP('2021-07-06 12:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='en_US'
;

-- 2021-07-06T09:44:28.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'en_US') 
;

-- 2021-07-06T09:44:29.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.',Updated=TO_TIMESTAMP('2021-07-06 12:44:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='de_CH'
;

-- 2021-07-06T09:44:29.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'de_CH') 
;

-- 2021-07-06T09:44:30.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.',Updated=TO_TIMESTAMP('2021-07-06 12:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='de_DE'
;

-- 2021-07-06T09:44:30.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'de_DE') 
;

-- 2021-07-06T09:44:30.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579461,'de_DE') 
;

-- 2021-07-06T09:44:30.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoSetDebtoridAndCreditorid', Name='Debitor- und Kreditor-ID automatisch setzen', Description=NULL, Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.' WHERE AD_Element_ID=579461
;

-- 2021-07-06T09:44:30.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSetDebtoridAndCreditorid', Name='Debitor- und Kreditor-ID automatisch setzen', Description=NULL, Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', AD_Element_ID=579461 WHERE UPPER(ColumnName)='ISAUTOSETDEBTORIDANDCREDITORID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T09:44:30.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSetDebtoridAndCreditorid', Name='Debitor- und Kreditor-ID automatisch setzen', Description=NULL, Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.' WHERE AD_Element_ID=579461 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T09:44:30.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Debitor- und Kreditor-ID automatisch setzen', Description=NULL, Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579461) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579461)
;

-- 2021-07-06T09:44:30.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Debitor- und Kreditor-ID automatisch setzen', Description=NULL, Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', CommitWarning = NULL WHERE AD_Element_ID = 579461
;

-- 2021-07-06T09:44:30.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Debitor- und Kreditor-ID automatisch setzen', Description=NULL, Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.' WHERE AD_Element_ID = 579461
;

-- 2021-07-06T09:44:30.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Debitor- und Kreditor-ID automatisch setzen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579461
;

-- 2021-07-06T09:44:34.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.',Updated=TO_TIMESTAMP('2021-07-06 12:44:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='nl_NL'
;

-- 2021-07-06T09:44:34.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'nl_NL') 
;

-- 2021-07-06T09:44:37.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.',Updated=TO_TIMESTAMP('2021-07-06 12:44:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='de_CH'
;

-- 2021-07-06T09:44:37.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'de_CH') 
;

-- 2021-07-06T09:44:38.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.',Updated=TO_TIMESTAMP('2021-07-06 12:44:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='de_DE'
;

-- 2021-07-06T09:44:38.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'de_DE') 
;

-- 2021-07-06T09:44:38.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579461,'de_DE') 
;

-- 2021-07-06T09:44:38.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoSetDebtoridAndCreditorid', Name='Debitor- und Kreditor-ID automatisch setzen', Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.' WHERE AD_Element_ID=579461
;

-- 2021-07-06T09:44:38.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSetDebtoridAndCreditorid', Name='Debitor- und Kreditor-ID automatisch setzen', Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', AD_Element_ID=579461 WHERE UPPER(ColumnName)='ISAUTOSETDEBTORIDANDCREDITORID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T09:44:38.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSetDebtoridAndCreditorid', Name='Debitor- und Kreditor-ID automatisch setzen', Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.' WHERE AD_Element_ID=579461 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T09:44:38.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Debitor- und Kreditor-ID automatisch setzen', Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579461) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579461)
;

-- 2021-07-06T09:44:38.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Debitor- und Kreditor-ID automatisch setzen', Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', CommitWarning = NULL WHERE AD_Element_ID = 579461
;

-- 2021-07-06T09:44:38.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Debitor- und Kreditor-ID automatisch setzen', Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', Help='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.' WHERE AD_Element_ID = 579461
;

-- 2021-07-06T09:44:38.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Debitor- und Kreditor-ID automatisch setzen', Description = 'Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579461
;

-- 2021-07-06T09:44:44.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.',Updated=TO_TIMESTAMP('2021-07-06 12:44:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='nl_NL'
;

-- 2021-07-06T09:44:44.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'nl_NL') 
;

-- 2021-07-06T09:44:46.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If ticked, then metasfresh from there onwards updates a business partner''s debitorId and creditorId automatically from the bartner''s respective value and an optional prefix, whenever a partner''s value is updated or a new partner is created.',Updated=TO_TIMESTAMP('2021-07-06 12:44:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579461 AND AD_Language='en_US'
;

-- 2021-07-06T09:44:46.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579461,'en_US') 
;

-- 2021-07-06T09:45:04.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574967,579461,0,20,265,'IsAutoSetDebtoridAndCreditorid',TO_TIMESTAMP('2021-07-06 12:45:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.','D',0,1,'Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Debitor- und Kreditor-ID automatisch setzen',0,0,TO_TIMESTAMP('2021-07-06 12:45:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-06T09:45:04.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574967 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-06T09:45:04.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579461) 
;

-- 2021-07-06T09:45:57.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579462,0,'DebtorIdPrefix',TO_TIMESTAMP('2021-07-06 12:45:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prefix DebitorenId','Prefix DebitorenId',TO_TIMESTAMP('2021-07-06 12:45:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:45:57.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579462 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-06T09:46:10.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='DebtorId prefix', PrintName='DebtorId prefix',Updated=TO_TIMESTAMP('2021-07-06 12:46:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579462 AND AD_Language='en_US'
;

-- 2021-07-06T09:46:10.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579462,'en_US') 
;

-- 2021-07-06T09:46:30.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579463,0,TO_TIMESTAMP('2021-07-06 12:46:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prefix KreditorenId','Prefix KreditorenId',TO_TIMESTAMP('2021-07-06 12:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:46:30.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579463 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-06T09:46:51.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='DebtorId prefix', PrintName='DebtorId prefix',Updated=TO_TIMESTAMP('2021-07-06 12:46:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579463 AND AD_Language='en_US'
;

-- 2021-07-06T09:46:51.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579463,'en_US') 
;

-- 2021-07-06T09:47:10.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='CreditorIdPrefix',Updated=TO_TIMESTAMP('2021-07-06 12:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579463
;

-- 2021-07-06T09:47:10.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CreditorIdPrefix', Name='Prefix KreditorenId', Description=NULL, Help=NULL WHERE AD_Element_ID=579463
;

-- 2021-07-06T09:47:10.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CreditorIdPrefix', Name='Prefix KreditorenId', Description=NULL, Help=NULL, AD_Element_ID=579463 WHERE UPPER(ColumnName)='CREDITORIDPREFIX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T09:47:10.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CreditorIdPrefix', Name='Prefix KreditorenId', Description=NULL, Help=NULL WHERE AD_Element_ID=579463 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T09:47:18.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='CreditorId prefix', PrintName='CreditorId prefix',Updated=TO_TIMESTAMP('2021-07-06 12:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579463 AND AD_Language='en_US'
;

-- 2021-07-06T09:47:18.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579463,'en_US') 
;

-- 2021-07-06T09:48:07.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574968,579462,0,10,265,'DebtorIdPrefix',TO_TIMESTAMP('2021-07-06 12:48:07','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,8,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Prefix DebitorenId',0,0,TO_TIMESTAMP('2021-07-06 12:48:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-06T09:48:07.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574968 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-06T09:48:07.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579462) 
;

-- 2021-07-06T09:48:15.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_AcctSchema','ALTER TABLE public.C_AcctSchema ADD COLUMN IsAutoSetDebtoridAndCreditorid CHAR(1) DEFAULT ''N'' CHECK (IsAutoSetDebtoridAndCreditorid IN (''Y'',''N'')) NOT NULL')
;

-- 2021-07-06T09:48:25.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_AcctSchema','ALTER TABLE public.C_AcctSchema ADD COLUMN DebtorIdPrefix VARCHAR(8)')
;

-- 2021-07-06T09:48:50.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574969,579463,0,10,265,'CreditorIdPrefix',TO_TIMESTAMP('2021-07-06 12:48:50','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,8,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Prefix KreditorenId',0,0,TO_TIMESTAMP('2021-07-06 12:48:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-06T09:48:50.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574969 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-06T09:48:50.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579463) 
;

-- 2021-07-06T09:48:50.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_AcctSchema','ALTER TABLE public.C_AcctSchema ADD COLUMN CreditorIdPrefix VARCHAR(8)')
;
-- 2021-07-06T09:54:47.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574967,650072,0,199,0,TO_TIMESTAMP('2021-07-06 12:54:46','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.',0,'D','Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.',0,'Y','Y','Y','N','N','N','N','N','Debitor- und Kreditor-ID automatisch setzen',0,280,0,1,1,TO_TIMESTAMP('2021-07-06 12:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:54:47.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-06T09:54:47.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579461) 
;

-- 2021-07-06T09:54:47.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650072
;

-- 2021-07-06T09:54:47.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(650072)
;

-- 2021-07-06T09:54:54.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574968,650073,0,199,0,TO_TIMESTAMP('2021-07-06 12:54:54','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Prefix DebitorenId',0,290,0,1,1,TO_TIMESTAMP('2021-07-06 12:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:54:54.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-06T09:54:54.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579462) 
;

-- 2021-07-06T09:54:54.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650073
;

-- 2021-07-06T09:54:54.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(650073)
;

-- 2021-07-06T09:55:07.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574969,650074,0,199,0,TO_TIMESTAMP('2021-07-06 12:55:07','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Prefix KreditorenId',0,300,0,1,1,TO_TIMESTAMP('2021-07-06 12:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:55:07.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-06T09:55:07.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579463) 
;

-- 2021-07-06T09:55:07.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650074
;

-- 2021-07-06T09:55:07.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(650074)
;

-- 2021-07-06T09:56:30.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540775,546185,TO_TIMESTAMP('2021-07-06 12:56:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','prefixes',30,TO_TIMESTAMP('2021-07-06 12:56:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:56:57.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650072,0,199,546185,586999,'F',TO_TIMESTAMP('2021-07-06 12:56:57','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.','Wenn angehakt, dann aktualisiert metasfresh die Debitoren- und Kreditoren-IDs eines Geschäftspartners aus einem optionalem Prefix und dem jeweiligen Suchschlüssel, sobald der Suchschlüssel geändert oder ein neue Geschäftspartner angelegt wird.','Y','N','N','Y','N','N','N',0,'Debitor- und Kreditor-ID automatisch setzen',10,0,0,TO_TIMESTAMP('2021-07-06 12:56:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:57:16.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650073,0,199,546185,587000,'F',TO_TIMESTAMP('2021-07-06 12:57:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Prefix DebitorenId',20,0,0,TO_TIMESTAMP('2021-07-06 12:57:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:57:28.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650074,0,199,546185,587001,'F',TO_TIMESTAMP('2021-07-06 12:57:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Prefix KreditorenId',30,0,0,TO_TIMESTAMP('2021-07-06 12:57:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T09:59:35.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsAutoSetDebtoridAndCreditorid/N@=Y',Updated=TO_TIMESTAMP('2021-07-06 12:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574969
;

-- 2021-07-06T09:59:42.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsAutoSetDebtoridAndCreditorid/N@=Y',Updated=TO_TIMESTAMP('2021-07-06 12:59:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574968
;

-- 2021-07-06T10:03:17.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Prefix Debitoren-Nr', PrintName='Prefix Debitoren-Nr',Updated=TO_TIMESTAMP('2021-07-06 13:03:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579462 AND AD_Language='de_CH'
;

-- 2021-07-06T10:03:17.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579462,'de_CH') 
;

-- 2021-07-06T10:03:19.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Prefix Debitoren-Nr', PrintName='Prefix Debitoren-Nr',Updated=TO_TIMESTAMP('2021-07-06 13:03:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579462 AND AD_Language='de_DE'
;

-- 2021-07-06T10:03:19.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579462,'de_DE') 
;

-- 2021-07-06T10:03:19.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579462,'de_DE') 
;

-- 2021-07-06T10:03:19.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DebtorIdPrefix', Name='Prefix Debitoren-Nr', Description=NULL, Help=NULL WHERE AD_Element_ID=579462
;

-- 2021-07-06T10:03:19.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DebtorIdPrefix', Name='Prefix Debitoren-Nr', Description=NULL, Help=NULL, AD_Element_ID=579462 WHERE UPPER(ColumnName)='DEBTORIDPREFIX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T10:03:19.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DebtorIdPrefix', Name='Prefix Debitoren-Nr', Description=NULL, Help=NULL WHERE AD_Element_ID=579462 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T10:03:19.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Prefix Debitoren-Nr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579462) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579462)
;

-- 2021-07-06T10:03:19.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Prefix Debitoren-Nr', Name='Prefix Debitoren-Nr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579462)
;

-- 2021-07-06T10:03:19.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Prefix Debitoren-Nr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579462
;

-- 2021-07-06T10:03:19.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Prefix Debitoren-Nr', Description=NULL, Help=NULL WHERE AD_Element_ID = 579462
;

-- 2021-07-06T10:03:19.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Prefix Debitoren-Nr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579462
;

-- 2021-07-06T10:04:03.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Prefix Kreditoren-Nr',Updated=TO_TIMESTAMP('2021-07-06 13:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579463 AND AD_Language='de_CH'
;

-- 2021-07-06T10:04:03.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579463,'de_CH') 
;

-- 2021-07-06T10:04:05.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Prefix Kreditoren-Nr', PrintName='Prefix Kreditoren-Nr',Updated=TO_TIMESTAMP('2021-07-06 13:04:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579463 AND AD_Language='de_DE'
;

-- 2021-07-06T10:04:05.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579463,'de_DE') 
;

-- 2021-07-06T10:04:05.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579463,'de_DE') 
;

-- 2021-07-06T10:04:05.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CreditorIdPrefix', Name='Prefix Kreditoren-Nr', Description=NULL, Help=NULL WHERE AD_Element_ID=579463
;

-- 2021-07-06T10:04:05.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CreditorIdPrefix', Name='Prefix Kreditoren-Nr', Description=NULL, Help=NULL, AD_Element_ID=579463 WHERE UPPER(ColumnName)='CREDITORIDPREFIX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T10:04:05.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CreditorIdPrefix', Name='Prefix Kreditoren-Nr', Description=NULL, Help=NULL WHERE AD_Element_ID=579463 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T10:04:05.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Prefix Kreditoren-Nr', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579463) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579463)
;

-- 2021-07-06T10:04:05.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Prefix Kreditoren-Nr', Name='Prefix Kreditoren-Nr' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579463)
;

-- 2021-07-06T10:04:05.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Prefix Kreditoren-Nr', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579463
;

-- 2021-07-06T10:04:05.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Prefix Kreditoren-Nr', Description=NULL, Help=NULL WHERE AD_Element_ID = 579463
;

-- 2021-07-06T10:04:05.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Prefix Kreditoren-Nr', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579463
;

-- 2021-07-06T10:04:06.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Prefix Kreditoren-Nr',Updated=TO_TIMESTAMP('2021-07-06 13:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579463 AND AD_Language='de_CH'
;

-- 2021-07-06T10:04:06.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579463,'de_CH') 
;

-- 2021-07-06T10:04:32.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Prefix Kreditoren-Nr', PrintName='Prefix Kreditoren-Nr',Updated=TO_TIMESTAMP('2021-07-06 13:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579463 AND AD_Language='nl_NL'
;

-- 2021-07-06T10:04:32.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579463,'nl_NL') 
;

-- 2021-07-06T10:04:43.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Creditor-Nr prefix', PrintName='Creditor-Nr prefix',Updated=TO_TIMESTAMP('2021-07-06 13:04:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579463 AND AD_Language='en_US'
;

-- 2021-07-06T10:04:43.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579463,'en_US') 
;

-- 2021-07-06T10:05:19.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Debtor-Nr prefix', PrintName='Debtor-Nr prefix',Updated=TO_TIMESTAMP('2021-07-06 13:05:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579462 AND AD_Language='en_US'
;

-- 2021-07-06T10:05:19.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579462,'en_US') 
;

-- 2021-07-06T11:29:23.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-07-06 14:29:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574969
;

-- 2021-07-06T11:29:34.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-07-06 14:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574968
;

-- 2021-07-06T12:33:35.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584857,'Y','de.metas.acct.process.C_AcctSchema_Update_DebtorIdAndCreditorId','N',TO_TIMESTAMP('2021-07-06 15:33:35','YYYY-MM-DD HH24:MI:SS'),100,'Aktualisiert die Debitoren- und Kreditoren-IDs aller Geschäftspartner','D','Y','N','N','N','Y','Y','N','N','Y','Y',0,'Debitoren- und Kreditoren-Id aktualisieren','json','N','N','Java',TO_TIMESTAMP('2021-07-06 15:33:35','YYYY-MM-DD HH24:MI:SS'),100,'C_AcctSchema_Update_DebtorIdAndCreditorId')
;

-- 2021-07-06T12:33:35.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584857 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-07-06T12:34:43.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Updates the Debtor-Nrs and and Creditor-Nrs of all business partners', Name='Update Debtor-Nr and Creditor-Nr',Updated=TO_TIMESTAMP('2021-07-06 15:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584857
;

-- 2021-07-06T12:35:18.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Debitoren- und Kreditoren-Nr aktualisieren',Updated=TO_TIMESTAMP('2021-07-06 15:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584857
;

-- 2021-07-06T12:35:19.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Aktualisiert die Debitoren- und Kreditoren-Nrn aller zugehörigen Geschäftspartner.', Help=NULL, Name='Debitoren- und Kreditoren-Nr aktualisieren',Updated=TO_TIMESTAMP('2021-07-06 15:35:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584857
;

-- 2021-07-06T12:35:19.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Debitoren- und Kreditoren-Nr aktualisieren',Updated=TO_TIMESTAMP('2021-07-06 15:35:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584857
;

-- 2021-07-06T12:35:30.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Debitoren- und Kreditoren-Nr aktualisieren',Updated=TO_TIMESTAMP('2021-07-06 15:35:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584857
;

-- 2021-07-06T12:35:41.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Aktualisiert die Debitoren- und Kreditoren-Nrn aller zugehörigen Geschäftspartner.',Updated=TO_TIMESTAMP('2021-07-06 15:35:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584857
;

-- 2021-07-06T12:35:43.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Aktualisiert die Debitoren- und Kreditoren-Nrn aller zugehörigen Geschäftspartner.', Help=NULL, Name='Debitoren- und Kreditoren-Nr aktualisieren',Updated=TO_TIMESTAMP('2021-07-06 15:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584857
;

-- 2021-07-06T12:35:43.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Aktualisiert die Debitoren- und Kreditoren-Nrn aller zugehörigen Geschäftspartner.',Updated=TO_TIMESTAMP('2021-07-06 15:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584857
;

-- 2021-07-06T12:36:07.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Aktualisiert die Debitoren- und Kreditoren-Nrn aller zugehörigen Geschäftspartner.',Updated=TO_TIMESTAMP('2021-07-06 15:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584857
;

-- 2021-07-06T12:36:13.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-06 15:36:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584857
;

-- 2021-07-06T12:36:47.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584857,265,540953,TO_TIMESTAMP('2021-07-06 15:36:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-07-06 15:36:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-07-06T12:40:16.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584857,542040,30,'AD_Org_ID',TO_TIMESTAMP('2021-07-06 15:40:16','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',22,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion',10,TO_TIMESTAMP('2021-07-06 15:40:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T12:40:16.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542040 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-07-06T12:42:27.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='@AD_Org_ID/0@>0',Updated=TO_TIMESTAMP('2021-07-06 15:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542040
;

-- 2021-07-06T13:33:26.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_acctschema','IsAutoSetDebtoridAndCreditorid','CHAR(1)',null,'N')
;

-- 2021-07-06T13:33:26.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_AcctSchema SET IsAutoSetDebtoridAndCreditorid='N' WHERE IsAutoSetDebtoridAndCreditorid IS NULL
;

-- 2021-07-08T07:15:38.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner','CreditorId','NUMERIC(10)',null,null)
;

-- 2021-07-08T07:16:26.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner','DebtorId','NUMERIC(10)',null,null)
;