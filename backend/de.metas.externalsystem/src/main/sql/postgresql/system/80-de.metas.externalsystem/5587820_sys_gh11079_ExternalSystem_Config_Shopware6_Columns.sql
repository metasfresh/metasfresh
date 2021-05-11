-- 2021-05-09T18:06:51.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579145,0,'BPartner_IfExists',TO_TIMESTAMP('2021-05-09 21:06:51','YYYY-MM-DD HH24:MI:SS'),100,'Specifies what to do if a Shopware customer already exists as business partner in metasfresh.','D','Y','If business partner exists','If business partner exists',TO_TIMESTAMP('2021-05-09 21:06:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-09T18:06:51.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579145 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-09T18:07:26.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', Name='Wenn Gesch.-Partner ex.', PrintName='Wenn Gesch.-Partner ex.',Updated=TO_TIMESTAMP('2021-05-09 21:07:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579145 AND AD_Language='de_CH'
;

-- 2021-05-09T18:07:26.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579145,'de_CH') 
;

-- 2021-05-09T18:07:38.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', Name='Wenn Gesch.-Partner ex.', PrintName='Wenn Gesch.-Partner ex.',Updated=TO_TIMESTAMP('2021-05-09 21:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579145 AND AD_Language='de_DE'
;

-- 2021-05-09T18:07:38.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579145,'de_DE') 
;

-- 2021-05-09T18:07:38.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579145,'de_DE') 
;

-- 2021-05-09T18:07:38.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartner_IfExists', Name='Wenn Gesch.-Partner ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579145
;

-- 2021-05-09T18:07:38.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartner_IfExists', Name='Wenn Gesch.-Partner ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', Help=NULL, AD_Element_ID=579145 WHERE UPPER(ColumnName)='BPARTNER_IFEXISTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-09T18:07:38.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartner_IfExists', Name='Wenn Gesch.-Partner ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579145 AND IsCentrallyMaintained='Y'
;

-- 2021-05-09T18:07:38.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wenn Gesch.-Partner ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579145) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579145)
;

-- 2021-05-09T18:07:38.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wenn Gesch.-Partner ex.', Name='Wenn Gesch.-Partner ex.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579145)
;

-- 2021-05-09T18:07:38.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Wenn Gesch.-Partner ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579145
;

-- 2021-05-09T18:07:38.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Wenn Gesch.-Partner ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID = 579145
;

-- 2021-05-09T18:07:38.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Wenn Gesch.-Partner ex.', Description = 'Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579145
;

-- 2021-05-09T18:09:27.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541309,TO_TIMESTAMP('2021-05-09 21:09:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','SyncAdvice_IfExists',TO_TIMESTAMP('2021-05-09 21:09:26','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-05-09T18:09:27.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541309 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-05-09T18:10:21.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542492,541309,TO_TIMESTAMP('2021-05-09 21:10:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Update',TO_TIMESTAMP('2021-05-09 21:10:21','YYYY-MM-DD HH24:MI:SS'),100,'UPDATE_MERGE','Update')
;

-- 2021-05-09T18:10:21.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542492 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-09T18:11:07.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Aktualisisieren',Updated=TO_TIMESTAMP('2021-05-09 21:11:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542492
;

-- 2021-05-09T18:11:12.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Aktualisisieren',Updated=TO_TIMESTAMP('2021-05-09 21:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542492
;

-- 2021-05-09T18:11:42.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542493,541309,TO_TIMESTAMP('2021-05-09 21:11:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nothing',TO_TIMESTAMP('2021-05-09 21:11:42','YYYY-MM-DD HH24:MI:SS'),100,'DONT_UPDATE','Nothing')
;

-- 2021-05-09T18:11:42.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542493 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-09T18:11:58.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Nichts',Updated=TO_TIMESTAMP('2021-05-09 21:11:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542493
;

-- 2021-05-09T18:12:02.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Nichts',Updated=TO_TIMESTAMP('2021-05-09 21:12:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542493
;

-- 2021-05-09T18:14:48.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573829,579145,0,17,541309,541585,'BPartner_IfExists',TO_TIMESTAMP('2021-05-09 21:14:47','YYYY-MM-DD HH24:MI:SS'),100,'N','UPDATE','Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Wenn Gesch.-Partner ex.',0,0,TO_TIMESTAMP('2021-05-09 21:14:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-09T18:14:48.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573829 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-09T18:14:48.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579145) 
;

-- 2021-05-09T18:15:33.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541310,TO_TIMESTAMP('2021-05-09 21:15:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','SyncAdvice_IfNotExists',TO_TIMESTAMP('2021-05-09 21:15:33','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-05-09T18:15:33.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541310 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-05-09T18:16:04.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542494,541310,TO_TIMESTAMP('2021-05-09 21:16:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Create',TO_TIMESTAMP('2021-05-09 21:16:04','YYYY-MM-DD HH24:MI:SS'),100,'CREATE','Create')
;

-- 2021-05-09T18:16:04.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542494 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-09T18:16:20.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Erstellen',Updated=TO_TIMESTAMP('2021-05-09 21:16:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542494
;

-- 2021-05-09T18:16:25.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Erstellen',Updated=TO_TIMESTAMP('2021-05-09 21:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542494
;

-- 2021-05-09T18:17:28.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542495,541310,TO_TIMESTAMP('2021-05-09 21:17:28','YYYY-MM-DD HH24:MI:SS'),100,'The Shopware order can''t be created in metasfresh','D','Y','Fail',TO_TIMESTAMP('2021-05-09 21:17:28','YYYY-MM-DD HH24:MI:SS'),100,'FAIL','Fail')
;

-- 2021-05-09T18:17:28.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542495 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-09T18:17:59.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der betreffende Shopware-Auftrag kann nicht nach metasfresh übermittelt werden.', Name='Fehlschlagen',Updated=TO_TIMESTAMP('2021-05-09 21:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542495
;

-- 2021-05-09T18:18:09.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der betreffende Shopware-Auftrag kann nicht nach metasfresh übermittelt werden.', Name='Fehlschlagen',Updated=TO_TIMESTAMP('2021-05-09 21:18:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542495
;

-- 2021-05-09T18:19:30.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579146,0,'BPartner_IfNotExists',TO_TIMESTAMP('2021-05-09 21:19:29','YYYY-MM-DD HH24:MI:SS'),100,'Specifies what to do if a Shopware customer does not yet exist as business partner in metasfresh.','D','Y','If business partner doesn''t exist','If business partner doesn''t exist',TO_TIMESTAMP('2021-05-09 21:19:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-09T18:19:30.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579146 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-09T18:20:01.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', Name='Wenn Gesch.-Partner nicht ex.', PrintName='Wenn Gesch.-Partner nicht ex.',Updated=TO_TIMESTAMP('2021-05-09 21:20:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579146 AND AD_Language='de_CH'
;

-- 2021-05-09T18:20:01.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579146,'de_CH') 
;

-- 2021-05-09T18:20:15.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', Name='Wenn Gesch.-Partner nicht ex.', PrintName='Wenn Gesch.-Partner nicht ex.',Updated=TO_TIMESTAMP('2021-05-09 21:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579146 AND AD_Language='de_DE'
;

-- 2021-05-09T18:20:15.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579146,'de_DE') 
;

-- 2021-05-09T18:20:15.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579146,'de_DE') 
;

-- 2021-05-09T18:20:15.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartner_IfNotExists', Name='Wenn Gesch.-Partner nicht ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579146
;

-- 2021-05-09T18:20:15.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartner_IfNotExists', Name='Wenn Gesch.-Partner nicht ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', Help=NULL, AD_Element_ID=579146 WHERE UPPER(ColumnName)='BPARTNER_IFNOTEXISTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-09T18:20:15.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartner_IfNotExists', Name='Wenn Gesch.-Partner nicht ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579146 AND IsCentrallyMaintained='Y'
;

-- 2021-05-09T18:20:15.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wenn Gesch.-Partner nicht ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579146) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579146)
;

-- 2021-05-09T18:20:15.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wenn Gesch.-Partner nicht ex.', Name='Wenn Gesch.-Partner nicht ex.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579146)
;

-- 2021-05-09T18:20:15.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Wenn Gesch.-Partner nicht ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579146
;

-- 2021-05-09T18:20:15.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Wenn Gesch.-Partner nicht ex.', Description='Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID = 579146
;

-- 2021-05-09T18:20:15.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Wenn Gesch.-Partner nicht ex.', Description = 'Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579146
;

-- 2021-05-09T18:21:04.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573830,579146,0,17,541310,541585,'BPartner_IfNotExists',TO_TIMESTAMP('2021-05-09 21:21:04','YYYY-MM-DD HH24:MI:SS'),100,'N','CREATE','Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Wenn Gesch.-Partner nicht ex.',0,0,TO_TIMESTAMP('2021-05-09 21:21:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-09T18:21:04.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573830 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-09T18:21:04.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579146) 
;

-- 2021-05-09T18:21:48.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579147,0,'BPartnerLocation_IfExists',TO_TIMESTAMP('2021-05-09 21:21:48','YYYY-MM-DD HH24:MI:SS'),100,'Specifies what to do if a Shopware customer''s address already exists in metasfresh.','D','Y','If address exists','If address exists',TO_TIMESTAMP('2021-05-09 21:21:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-09T18:21:48.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579147 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-09T18:22:10.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Name='Wenn Adresse ex.', PrintName='Wenn Adresse ex.',Updated=TO_TIMESTAMP('2021-05-09 21:22:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579147 AND AD_Language='de_CH'
;

-- 2021-05-09T18:22:10.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579147,'de_CH') 
;

-- 2021-05-09T18:22:24.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Name='Wenn Adresse ex.', PrintName='Wenn Adresse ex.',Updated=TO_TIMESTAMP('2021-05-09 21:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579147 AND AD_Language='de_DE'
;

-- 2021-05-09T18:22:24.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579147,'de_DE') 
;

-- 2021-05-09T18:22:24.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579147,'de_DE') 
;

-- 2021-05-09T18:22:24.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartnerLocation_IfExists', Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579147
;

-- 2021-05-09T18:22:24.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerLocation_IfExists', Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL, AD_Element_ID=579147 WHERE UPPER(ColumnName)='BPARTNERLOCATION_IFEXISTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-09T18:22:24.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerLocation_IfExists', Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579147 AND IsCentrallyMaintained='Y'
;

-- 2021-05-09T18:22:24.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579147) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579147)
;

-- 2021-05-09T18:22:24.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wenn Adresse ex.', Name='Wenn Adresse ex.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579147)
;

-- 2021-05-09T18:22:24.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579147
;

-- 2021-05-09T18:22:24.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID = 579147
;

-- 2021-05-09T18:22:24.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Wenn Adresse ex.', Description = 'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579147
;

-- 2021-05-09T18:23:10.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573831,579147,0,17,541309,541585,'BPartnerLocation_IfExists',TO_TIMESTAMP('2021-05-09 21:23:10','YYYY-MM-DD HH24:MI:SS'),100,'N','UPDATE','Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Wenn Adresse ex.',0,0,TO_TIMESTAMP('2021-05-09 21:23:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-09T18:23:10.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573831 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-09T18:23:10.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579147) 
;

-- 2021-05-09T18:23:49.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579148,0,'BPartnerLocation_IfNotExists',TO_TIMESTAMP('2021-05-09 21:23:49','YYYY-MM-DD HH24:MI:SS'),100,'Specifies what to do if a Shopware customer''s address already exists in metasfresh.','D','Y','If addr doesn''t exist','If addr doesn''t exist',TO_TIMESTAMP('2021-05-09 21:23:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-09T18:23:49.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579148 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-09T18:24:10.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Name='Wenn Adresse ex.', PrintName='Wenn Adresse ex.',Updated=TO_TIMESTAMP('2021-05-09 21:24:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579148 AND AD_Language='de_CH'
;

-- 2021-05-09T18:24:10.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579148,'de_CH') 
;

-- 2021-05-09T18:24:22.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Name='Wenn Adresse ex.', PrintName='Wenn Adresse ex.',Updated=TO_TIMESTAMP('2021-05-09 21:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579148 AND AD_Language='de_DE'
;

-- 2021-05-09T18:24:22.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579148,'de_DE') 
;

-- 2021-05-09T18:24:22.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579148,'de_DE') 
;

-- 2021-05-09T18:24:22.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartnerLocation_IfNotExists', Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579148
;

-- 2021-05-09T18:24:22.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerLocation_IfNotExists', Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL, AD_Element_ID=579148 WHERE UPPER(ColumnName)='BPARTNERLOCATION_IFNOTEXISTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-09T18:24:22.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerLocation_IfNotExists', Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579148 AND IsCentrallyMaintained='Y'
;

-- 2021-05-09T18:24:22.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579148) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579148)
;

-- 2021-05-09T18:24:22.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wenn Adresse ex.', Name='Wenn Adresse ex.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579148)
;

-- 2021-05-09T18:24:22.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:24:22.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Wenn Adresse ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:24:22.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Wenn Adresse ex.', Description = 'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:26:01.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.', Name='If addr doesn''t exist', PrintName='If addr doesn''t exist',Updated=TO_TIMESTAMP('2021-05-09 21:26:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579148
;

-- 2021-05-09T18:26:01.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartnerLocation_IfNotExists', Name='If addr doesn''t exist', Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.', Help=NULL WHERE AD_Element_ID=579148
;

-- 2021-05-09T18:26:01.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerLocation_IfNotExists', Name='If addr doesn''t exist', Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.', Help=NULL, AD_Element_ID=579148 WHERE UPPER(ColumnName)='BPARTNERLOCATION_IFNOTEXISTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-09T18:26:01.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerLocation_IfNotExists', Name='If addr doesn''t exist', Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.', Help=NULL WHERE AD_Element_ID=579148 AND IsCentrallyMaintained='Y'
;

-- 2021-05-09T18:26:01.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='If addr doesn''t exist', Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579148) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579148)
;

-- 2021-05-09T18:26:01.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='If addr doesn''t exist', Name='If addr doesn''t exist' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579148)
;

-- 2021-05-09T18:26:01.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='If addr doesn''t exist', Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:26:01.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='If addr doesn''t exist', Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.', Help=NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:26:01.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'If addr doesn''t exist', Description = 'Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:26:32.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', Name='Wenn Adr. nicht ex.', PrintName='Wenn Adr. nicht ex.',Updated=TO_TIMESTAMP('2021-05-09 21:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579148 AND AD_Language='de_CH'
;

-- 2021-05-09T18:26:32.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579148,'de_CH') 
;

-- 2021-05-09T18:26:49.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', Name='Wenn Adr. nicht ex.', PrintName='Wenn Adr. nicht ex.',Updated=TO_TIMESTAMP('2021-05-09 21:26:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579148 AND AD_Language='de_DE'
;

-- 2021-05-09T18:26:49.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579148,'de_DE') 
;

-- 2021-05-09T18:26:49.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579148,'de_DE') 
;

-- 2021-05-09T18:26:49.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartnerLocation_IfNotExists', Name='Wenn Adr. nicht ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579148
;

-- 2021-05-09T18:26:49.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerLocation_IfNotExists', Name='Wenn Adr. nicht ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', Help=NULL, AD_Element_ID=579148 WHERE UPPER(ColumnName)='BPARTNERLOCATION_IFNOTEXISTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-09T18:26:49.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerLocation_IfNotExists', Name='Wenn Adr. nicht ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID=579148 AND IsCentrallyMaintained='Y'
;

-- 2021-05-09T18:26:49.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wenn Adr. nicht ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579148) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579148)
;

-- 2021-05-09T18:26:49.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wenn Adr. nicht ex.', Name='Wenn Adr. nicht ex.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579148)
;

-- 2021-05-09T18:26:49.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Wenn Adr. nicht ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:26:49.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Wenn Adr. nicht ex.', Description='Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', Help=NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:26:49.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Wenn Adr. nicht ex.', Description = 'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579148
;

-- 2021-05-09T18:27:00.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.',Updated=TO_TIMESTAMP('2021-05-09 21:27:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579148 AND AD_Language='en_US'
;

-- 2021-05-09T18:27:00.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579148,'en_US') 
;

-- 2021-05-09T18:27:03.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies what to do if a Shopware customer''s address does not yet exist in metasfresh.',Updated=TO_TIMESTAMP('2021-05-09 21:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579148 AND AD_Language='nl_NL'
;

-- 2021-05-09T18:27:03.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579148,'nl_NL') 
;

-- 2021-05-09T18:28:36.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573832,579148,0,17,541310,541585,'BPartnerLocation_IfNotExists',TO_TIMESTAMP('2021-05-09 21:28:35','YYYY-MM-DD HH24:MI:SS'),100,'N','CREATE','Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Wenn Adr. nicht ex.',0,0,TO_TIMESTAMP('2021-05-09 21:28:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-09T18:28:36.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573832 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-09T18:28:36.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579148) 
;

-- 2021-05-09T18:29:14.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN BPartner_IfExists VARCHAR(255) DEFAULT ''UPDATE'' NOT NULL')
;

-- 2021-05-09T18:29:21.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN BPartner_IfNotExists VARCHAR(255) DEFAULT ''CREATE'' NOT NULL')
;

-- 2021-05-09T18:29:25.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN BPartnerLocation_IfExists VARCHAR(255) DEFAULT ''UPDATE'' NOT NULL')
;

-- 2021-05-09T18:29:29.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN BPartnerLocation_IfNotExists VARCHAR(255) DEFAULT ''CREATE'' NOT NULL')
;

