-- 2019-10-11T10:09:54.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577190,0,'User',TO_TIMESTAMP('2019-10-11 13:09:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Details: https://entwickler.dhl.de/group/ep/authentifizierung4','Y','User','User',TO_TIMESTAMP('2019-10-11 13:09:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-11T10:09:54.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577190 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-11T10:09:57.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-11 13:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577190 AND AD_Language='de_CH'
;

-- 2019-10-11T10:09:57.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577190,'de_CH') 
;

-- 2019-10-11T10:09:57.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-11 13:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577190 AND AD_Language='de_DE'
;

-- 2019-10-11T10:09:57.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577190,'de_DE') 
;

-- 2019-10-11T10:09:57.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577190,'de_DE') 
;

-- 2019-10-11T10:09:58.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-11 13:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577190 AND AD_Language='en_US'
;

-- 2019-10-11T10:09:58.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577190,'en_US') 
;

-- 2019-10-11T10:10:08.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Benutzer', PrintName='Benutzer',Updated=TO_TIMESTAMP('2019-10-11 13:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577190 AND AD_Language='de_CH'
;

-- 2019-10-11T10:10:08.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577190,'de_CH') 
;

-- 2019-10-11T10:10:11.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Benutzer', PrintName='Benutzer',Updated=TO_TIMESTAMP('2019-10-11 13:10:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577190 AND AD_Language='de_DE'
;

-- 2019-10-11T10:10:11.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577190,'de_DE') 
;

-- 2019-10-11T10:10:11.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577190,'de_DE') 
;

-- 2019-10-11T10:10:11.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='User', Name='Benutzer', Description=NULL, Help='Details: https://entwickler.dhl.de/group/ep/authentifizierung4' WHERE AD_Element_ID=577190
;

-- 2019-10-11T10:10:11.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='User', Name='Benutzer', Description=NULL, Help='Details: https://entwickler.dhl.de/group/ep/authentifizierung4', AD_Element_ID=577190 WHERE UPPER(ColumnName)='USER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-11T10:10:11.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='User', Name='Benutzer', Description=NULL, Help='Details: https://entwickler.dhl.de/group/ep/authentifizierung4' WHERE AD_Element_ID=577190 AND IsCentrallyMaintained='Y'
;

-- 2019-10-11T10:10:11.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benutzer', Description=NULL, Help='Details: https://entwickler.dhl.de/group/ep/authentifizierung4' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577190) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577190)
;

-- 2019-10-11T10:10:11.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Benutzer', Name='Benutzer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577190)
;

-- 2019-10-11T10:10:11.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Benutzer', Description=NULL, Help='Details: https://entwickler.dhl.de/group/ep/authentifizierung4', CommitWarning = NULL WHERE AD_Element_ID = 577190
;

-- 2019-10-11T10:10:11.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Benutzer', Description=NULL, Help='Details: https://entwickler.dhl.de/group/ep/authentifizierung4' WHERE AD_Element_ID = 577190
;

-- 2019-10-11T10:10:11.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Benutzer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577190
;

-- 2019-10-11T10:11:17.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569141,577190,0,10,541411,'User',TO_TIMESTAMP('2019-10-11 13:11:17','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.shipper.gateway.dhl',255,'Details: https://entwickler.dhl.de/group/ep/authentifizierung4','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Benutzer',0,0,TO_TIMESTAMP('2019-10-11 13:11:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-11T10:11:17.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569141 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-11T10:11:17.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577190) 
;

-- 2019-10-11T10:12:11.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577191,0,'Signature',TO_TIMESTAMP('2019-10-11 13:12:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Signature','Signature',TO_TIMESTAMP('2019-10-11 13:12:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-11T10:12:11.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577191 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-11T10:12:13.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-11 13:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577191 AND AD_Language='de_CH'
;

-- 2019-10-11T10:12:13.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577191,'de_CH') 
;

-- 2019-10-11T10:12:13.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-11 13:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577191 AND AD_Language='de_DE'
;

-- 2019-10-11T10:12:13.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577191,'de_DE') 
;

-- 2019-10-11T10:12:13.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577191,'de_DE') 
;

-- 2019-10-11T10:12:14.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-11 13:12:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577191 AND AD_Language='en_US'
;

-- 2019-10-11T10:12:14.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577191,'en_US') 
;

-- 2019-10-11T10:12:16.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Unterschrift', PrintName='Unterschrift',Updated=TO_TIMESTAMP('2019-10-11 13:12:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577191 AND AD_Language='de_CH'
;

-- 2019-10-11T10:12:16.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577191,'de_CH') 
;

-- 2019-10-11T10:12:21.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Unterschrift', PrintName='Unterschrift',Updated=TO_TIMESTAMP('2019-10-11 13:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577191 AND AD_Language='de_DE'
;

-- 2019-10-11T10:12:21.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577191,'de_DE') 
;

-- 2019-10-11T10:12:21.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577191,'de_DE') 
;

-- 2019-10-11T10:12:21.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Signature', Name='Unterschrift', Description=NULL, Help=NULL WHERE AD_Element_ID=577191
;

-- 2019-10-11T10:12:21.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Signature', Name='Unterschrift', Description=NULL, Help=NULL, AD_Element_ID=577191 WHERE UPPER(ColumnName)='SIGNATURE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-11T10:12:21.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Signature', Name='Unterschrift', Description=NULL, Help=NULL WHERE AD_Element_ID=577191 AND IsCentrallyMaintained='Y'
;

-- 2019-10-11T10:12:21.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Unterschrift', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577191) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577191)
;

-- 2019-10-11T10:12:21.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Unterschrift', Name='Unterschrift' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577191)
;

-- 2019-10-11T10:12:21.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Unterschrift', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577191
;

-- 2019-10-11T10:12:21.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Unterschrift', Description=NULL, Help=NULL WHERE AD_Element_ID = 577191
;

-- 2019-10-11T10:12:21.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Unterschrift', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577191
;

-- 2019-10-11T10:12:34.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='https://entwickler.dhl.de/group/ep/authentifizierung4',Updated=TO_TIMESTAMP('2019-10-11 13:12:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577191
;

-- 2019-10-11T10:12:34.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Signature', Name='Unterschrift', Description=NULL, Help='https://entwickler.dhl.de/group/ep/authentifizierung4' WHERE AD_Element_ID=577191
;

-- 2019-10-11T10:12:34.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Signature', Name='Unterschrift', Description=NULL, Help='https://entwickler.dhl.de/group/ep/authentifizierung4', AD_Element_ID=577191 WHERE UPPER(ColumnName)='SIGNATURE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-11T10:12:34.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Signature', Name='Unterschrift', Description=NULL, Help='https://entwickler.dhl.de/group/ep/authentifizierung4' WHERE AD_Element_ID=577191 AND IsCentrallyMaintained='Y'
;

-- 2019-10-11T10:12:34.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Unterschrift', Description=NULL, Help='https://entwickler.dhl.de/group/ep/authentifizierung4' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577191) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577191)
;

-- 2019-10-11T10:12:34.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Unterschrift', Description=NULL, Help='https://entwickler.dhl.de/group/ep/authentifizierung4', CommitWarning = NULL WHERE AD_Element_ID = 577191
;

-- 2019-10-11T10:12:34.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Unterschrift', Description=NULL, Help='https://entwickler.dhl.de/group/ep/authentifizierung4' WHERE AD_Element_ID = 577191
;

-- 2019-10-11T10:12:34.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Unterschrift', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577191
;

-- 2019-10-11T10:14:22.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569142,577191,0,10,541411,'Signature',TO_TIMESTAMP('2019-10-11 13:14:22','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.shipper.gateway.dhl',255,'https://entwickler.dhl.de/group/ep/authentifizierung4','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Unterschrift',0,0,TO_TIMESTAMP('2019-10-11 13:14:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-11T10:14:22.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569142 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-11T10:14:22.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577191) 
;

-- 2019-10-11T10:14:49.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569142,589550,0,542055,0,TO_TIMESTAMP('2019-10-11 13:14:49','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.shipper.gateway.dhl','https://entwickler.dhl.de/group/ep/authentifizierung4',0,'Y','Y','Y','N','N','N','N','N','Unterschrift',50,50,0,1,1,TO_TIMESTAMP('2019-10-11 13:14:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-11T10:14:49.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589550 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-11T10:14:49.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577191) 
;

-- 2019-10-11T10:14:49.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589550
;

-- 2019-10-11T10:14:49.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589550)
;

-- 2019-10-11T10:15:05.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569141,589551,0,542055,0,TO_TIMESTAMP('2019-10-11 13:15:05','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.shipper.gateway.dhl','Details: https://entwickler.dhl.de/group/ep/authentifizierung4',0,'Y','Y','Y','N','N','N','N','N','Benutzer',60,60,0,1,1,TO_TIMESTAMP('2019-10-11 13:15:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-11T10:15:05.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589551 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-11T10:15:05.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577190) 
;

-- 2019-10-11T10:15:05.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589551
;

-- 2019-10-11T10:15:05.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589551)
;

-- 2019-10-11T10:15:23.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-11 13:15:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589550
;

-- 2019-10-11T10:15:26.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-11 13:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589551
;

