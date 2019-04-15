-- 2019-04-10T08:14:56.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576574,0,TO_TIMESTAMP('2019-04-10 08:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Geographical latitude ','D','Y','Latitude','Latitude',TO_TIMESTAMP('2019-04-10 08:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-10T08:14:56.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576574 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-10T08:15:10.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576575,0,TO_TIMESTAMP('2019-04-10 08:15:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Longitude','Geographical longitude',TO_TIMESTAMP('2019-04-10 08:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-10T08:15:10.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576575 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-10T08:15:37.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Breite',Updated=TO_TIMESTAMP('2019-04-10 08:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576575 AND AD_Language='de_DE'
;

-- 2019-04-10T08:15:37.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576575,'de_DE') 
;

-- 2019-04-10T08:15:37.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576575,'de_DE') 
;

-- 2019-04-10T08:15:37.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Breite', Description=NULL, Help=NULL WHERE AD_Element_ID=576575
;

-- 2019-04-10T08:15:37.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Breite', Description=NULL, Help=NULL WHERE AD_Element_ID=576575 AND IsCentrallyMaintained='Y'
;

-- 2019-04-10T08:15:37.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Breite', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576575) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576575)
;

-- 2019-04-10T08:15:37.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geographical longitude', Name='Breite' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576575)
;

-- 2019-04-10T08:15:37.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Breite', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:15:37.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Breite', Description=NULL, Help=NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:15:37.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Breite', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:16:39.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Breite',Updated=TO_TIMESTAMP('2019-04-10 08:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576574 AND AD_Language='de_DE'
;

-- 2019-04-10T08:16:39.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576574,'de_DE') 
;

-- 2019-04-10T08:16:39.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576574,'de_DE') 
;

-- 2019-04-10T08:16:39.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Breite', Name='Latitude' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576574)
;

-- 2019-04-10T08:16:41.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-10 08:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576574 AND AD_Language='en_US'
;

-- 2019-04-10T08:16:41.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576574,'en_US') 
;

-- 2019-04-10T08:17:10.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-10 08:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576575 AND AD_Language='en_US'
;

-- 2019-04-10T08:17:10.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576575,'en_US') 
;

-- 2019-04-10T08:17:13.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Längengrad',Updated=TO_TIMESTAMP('2019-04-10 08:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576575 AND AD_Language='de_DE'
;

-- 2019-04-10T08:17:13.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576575,'de_DE') 
;

-- 2019-04-10T08:17:13.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576575,'de_DE') 
;

-- 2019-04-10T08:17:13.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Längengrad', Description=NULL, Help=NULL WHERE AD_Element_ID=576575
;

-- 2019-04-10T08:17:13.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Längengrad', Description=NULL, Help=NULL WHERE AD_Element_ID=576575 AND IsCentrallyMaintained='Y'
;

-- 2019-04-10T08:17:13.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Längengrad', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576575) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576575)
;

-- 2019-04-10T08:17:13.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geographical longitude', Name='Längengrad' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576575)
;

-- 2019-04-10T08:17:13.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Längengrad', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:17:13.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Längengrad', Description=NULL, Help=NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:17:13.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Längengrad', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:17:57.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Longitude',Updated=TO_TIMESTAMP('2019-04-10 08:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576575
;

-- 2019-04-10T08:17:57.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Longitude', Name='Längengrad', Description=NULL, Help=NULL WHERE AD_Element_ID=576575
;

-- 2019-04-10T08:17:57.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Longitude', Name='Längengrad', Description=NULL, Help=NULL, AD_Element_ID=576575 WHERE UPPER(ColumnName)='LONGITUDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-10T08:17:57.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Longitude', Name='Längengrad', Description=NULL, Help=NULL WHERE AD_Element_ID=576575 AND IsCentrallyMaintained='Y'
;

-- 2019-04-10T08:18:01.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Latitude',Updated=TO_TIMESTAMP('2019-04-10 08:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576574
;

-- 2019-04-10T08:18:01.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Latitude', Name='Latitude', Description='Geographical latitude ', Help=NULL WHERE AD_Element_ID=576574
;

-- 2019-04-10T08:18:01.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Latitude', Name='Latitude', Description='Geographical latitude ', Help=NULL, AD_Element_ID=576574 WHERE UPPER(ColumnName)='LATITUDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-10T08:18:01.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Latitude', Name='Latitude', Description='Geographical latitude ', Help=NULL WHERE AD_Element_ID=576574 AND IsCentrallyMaintained='Y'
;

-- 2019-04-10T08:20:33.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567648,576574,0,10,162,'Latitude',TO_TIMESTAMP('2019-04-10 08:20:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Geographical latitude ','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Latitude',0,0,TO_TIMESTAMP('2019-04-10 08:20:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-10T08:20:33.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567648 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-10T08:21:04.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567649,576575,0,10,162,'Longitude',TO_TIMESTAMP('2019-04-10 08:21:03','YYYY-MM-DD HH24:MI:SS'),100,'N','D',15,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Längengrad',0,0,TO_TIMESTAMP('2019-04-10 08:21:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-10T08:21:04.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567649 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-10T08:21:21.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=15,Updated=TO_TIMESTAMP('2019-04-10 08:21:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567648
;

-- 2019-04-10T08:22:06.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Geographical longitude', PrintName='Longitude',Updated=TO_TIMESTAMP('2019-04-10 08:22:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576575
;

-- 2019-04-10T08:22:06.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Longitude', Name='Längengrad', Description='Geographical longitude', Help=NULL WHERE AD_Element_ID=576575
;

-- 2019-04-10T08:22:06.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Longitude', Name='Längengrad', Description='Geographical longitude', Help=NULL, AD_Element_ID=576575 WHERE UPPER(ColumnName)='LONGITUDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-10T08:22:06.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Longitude', Name='Längengrad', Description='Geographical longitude', Help=NULL WHERE AD_Element_ID=576575 AND IsCentrallyMaintained='Y'
;

-- 2019-04-10T08:22:06.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Längengrad', Description='Geographical longitude', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576575) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576575)
;

-- 2019-04-10T08:22:06.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Longitude', Name='Längengrad' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576575)
;

-- 2019-04-10T08:22:06.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Längengrad', Description='Geographical longitude', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:22:06.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Längengrad', Description='Geographical longitude', Help=NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:22:06.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Längengrad', Description = 'Geographical longitude', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:25:09.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Latitude',Updated=TO_TIMESTAMP('2019-04-10 08:25:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576574
;

-- 2019-04-10T08:25:09.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Latitude', Name='Latitude' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576574)
;

-- 2019-04-10T08:25:35.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-10 08:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576575 AND AD_Language='de_DE'
;

-- 2019-04-10T08:25:35.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576575,'de_DE') 
;

-- 2019-04-10T08:25:35.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576575,'de_DE') 
;

-- 2019-04-10T08:27:06.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Geographical longitude', PrintName='Longitude',Updated=TO_TIMESTAMP('2019-04-10 08:27:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576575
;

-- 2019-04-10T08:27:06.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Longitude', Name='Längengrad', Description='Geographical longitude', Help=NULL WHERE AD_Element_ID=576575
;

-- 2019-04-10T08:27:06.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Longitude', Name='Längengrad', Description='Geographical longitude', Help=NULL, AD_Element_ID=576575 WHERE UPPER(ColumnName)='LONGITUDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-10T08:27:06.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Longitude', Name='Längengrad', Description='Geographical longitude', Help=NULL WHERE AD_Element_ID=576575 AND IsCentrallyMaintained='Y'
;

-- 2019-04-10T08:27:06.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Längengrad', Description='Geographical longitude', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576575) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576575)
;

-- 2019-04-10T08:27:06.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Longitude', Name='Längengrad' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576575)
;

-- 2019-04-10T08:27:06.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Längengrad', Description='Geographical longitude', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:27:06.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Längengrad', Description='Geographical longitude', Help=NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:27:06.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Längengrad', Description = 'Geographical longitude', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576575
;

-- 2019-04-10T08:29:16.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Location','ALTER TABLE public.C_Location ADD COLUMN Longitude VARCHAR(15)')
;

-- 2019-04-10T08:29:21.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Location','ALTER TABLE public.C_Location ADD COLUMN Latitude VARCHAR(15)')
;

