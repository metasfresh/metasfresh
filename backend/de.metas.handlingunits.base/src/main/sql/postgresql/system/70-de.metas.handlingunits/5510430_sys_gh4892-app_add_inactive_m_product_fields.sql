-- 2019-01-23T09:20:36.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575995,0,'IsHUtracing',TO_TIMESTAMP('2019-01-23 09:20:36','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen','de.metas.handlingunits','Y','HU-Rückverfolgung','HU-Rückverfolgung',TO_TIMESTAMP('2019-01-23 09:20:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-23T09:20:36.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575995 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-23T09:20:41.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:20:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575995 AND AD_Language='de_CH'
;

-- 2019-01-23T09:20:41.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575995,'de_CH') 
;

-- 2019-01-23T09:20:44.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:20:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575995 AND AD_Language='de_DE'
;

-- 2019-01-23T09:20:44.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575995,'de_DE') 
;

-- 2019-01-23T09:20:44.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575995,'de_DE') 
;

-- 2019-01-23T09:21:28.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:21:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='HU Tracing',PrintName='HU Tracing',Description='Specifies if affected handling units should be be traced' WHERE AD_Element_ID=575995 AND AD_Language='en_US'
;

-- 2019-01-23T09:21:28.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575995,'en_US') 
;

-- 2019-01-23T09:21:32.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:21:32','YYYY-MM-DD HH24:MI:SS'),Name='HU Rückverfolgung',PrintName='HU Rückverfolgung' WHERE AD_Element_ID=575995 AND AD_Language='de_DE'
;

-- 2019-01-23T09:21:32.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575995,'de_DE') 
;

-- 2019-01-23T09:21:32.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575995,'de_DE') 
;

-- 2019-01-23T09:21:32.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsHUtracing', Name='HU Rückverfolgung', Description='Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen', Help=NULL WHERE AD_Element_ID=575995
;

-- 2019-01-23T09:21:32.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsHUtracing', Name='HU Rückverfolgung', Description='Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen', Help=NULL, AD_Element_ID=575995 WHERE UPPER(ColumnName)='ISHUTRACING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-23T09:21:32.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsHUtracing', Name='HU Rückverfolgung', Description='Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen', Help=NULL WHERE AD_Element_ID=575995 AND IsCentrallyMaintained='Y'
;

-- 2019-01-23T09:21:32.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HU Rückverfolgung', Description='Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575995) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575995)
;

-- 2019-01-23T09:21:32.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='HU Rückverfolgung', Name='HU Rückverfolgung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575995)
;

-- 2019-01-23T09:21:32.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HU Rückverfolgung', Description='Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575995
;

-- 2019-01-23T09:21:32.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HU Rückverfolgung', Description='Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen', Help=NULL WHERE AD_Element_ID = 575995
;

-- 2019-01-23T09:21:32.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='HU Rückverfolgung', Description='Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575995
;

-- 2019-01-23T09:21:36.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:21:36','YYYY-MM-DD HH24:MI:SS'),Name='HU Rückverfolgung',PrintName='HU Rückverfolgung' WHERE AD_Element_ID=575995 AND AD_Language='de_CH'
;

-- 2019-01-23T09:21:36.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575995,'de_CH') 
;

-- 2019-01-23T09:22:49.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575996,0,'HULabelPer',TO_TIMESTAMP('2019-01-23 09:22:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','HU Label','HU Label',TO_TIMESTAMP('2019-01-23 09:22:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-23T09:22:49.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575996 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-23T09:22:53.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:22:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575996 AND AD_Language='de_CH'
;

-- 2019-01-23T09:22:53.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575996,'de_CH') 
;

-- 2019-01-23T09:22:55.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:22:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575996 AND AD_Language='de_DE'
;

-- 2019-01-23T09:22:55.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575996,'de_DE') 
;

-- 2019-01-23T09:22:55.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575996,'de_DE') 
;

-- 2019-01-23T09:22:59.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:22:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575996 AND AD_Language='en_US'
;

-- 2019-01-23T09:22:59.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575996,'en_US') 
;

-- 2019-01-23T09:23:23.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,563844,575995,0,20,208,'IsHUtracing',TO_TIMESTAMP('2019-01-23 09:23:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen','de.metas.handlingunits',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','HU Rückverfolgung',0,0,TO_TIMESTAMP('2019-01-23 09:23:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-01-23T09:23:23.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563844 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-01-23T09:23:27.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN IsHUtracing CHAR(1) DEFAULT ''N'' CHECK (IsHUtracing IN (''Y'',''N'')) NOT NULL')
;

-- 2019-01-23T09:23:50.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Adding this column now so users might prepare their product master.
The business logic is added later.',Updated=TO_TIMESTAMP('2019-01-23 09:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563844
;

-- 2019-01-23T09:24:30.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540946,TO_TIMESTAMP('2019-01-23 09:24:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','HULabelPer',TO_TIMESTAMP('2019-01-23 09:24:30','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2019-01-23T09:24:30.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=540946 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-01-23T09:25:36.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540946,541852,TO_TIMESTAMP('2019-01-23 09:25:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Label pro Teil',TO_TIMESTAMP('2019-01-23 09:25:36','YYYY-MM-DD HH24:MI:SS'),100,'HULabelPerCU','HULabelPerCU')
;

-- 2019-01-23T09:25:36.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541852 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-01-23T09:25:39.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:25:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541852 AND AD_Language='de_CH'
;

-- 2019-01-23T09:25:47.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:25:47','YYYY-MM-DD HH24:MI:SS'),Name='Label per CU' WHERE AD_Ref_List_ID=541852 AND AD_Language='en_US'
;

-- 2019-01-23T09:25:51.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:25:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541852 AND AD_Language='en_US'
;

-- 2019-01-23T09:26:15.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540946,541853,TO_TIMESTAMP('2019-01-23 09:26:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Label pro Kiste',TO_TIMESTAMP('2019-01-23 09:26:14','YYYY-MM-DD HH24:MI:SS'),100,'HULabelPerTU','HULabelPerTU')
;

-- 2019-01-23T09:26:15.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541853 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-01-23T09:26:24.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:26:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Label per TU' WHERE AD_Ref_List_ID=541853 AND AD_Language='en_US'
;

-- 2019-01-23T09:26:30.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-23 09:26:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541853 AND AD_Language='de_CH'
;

-- 2019-01-23T09:26:57.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,563845,575996,0,17,540946,208,'HULabelPer',TO_TIMESTAMP('2019-01-23 09:26:57','YYYY-MM-DD HH24:MI:SS'),100,'N','HULabelPerTU','de.metas.handlingunits',12,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','HU Label',0,0,'Adding this column now so users might prepare their product master.
The business logic is added later.',TO_TIMESTAMP('2019-01-23 09:26:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-01-23T09:26:57.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=563845 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-01-23T09:27:03.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN HULabelPer VARCHAR(12) DEFAULT ''HULabelPerTU''')
;

-- 2019-01-23T09:27:56.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563844,572919,0,180,TO_TIMESTAMP('2019-01-23 09:27:56','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob betroffene Handlingunits ggf. der Rückverfolgung unterliegen sollen',1,'D','Y','Y','N','N','N','N','N','HU Rückverfolgung',TO_TIMESTAMP('2019-01-23 09:27:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-23T09:27:56.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=572919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-23T09:27:57.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563845,572920,0,180,TO_TIMESTAMP('2019-01-23 09:27:56','YYYY-MM-DD HH24:MI:SS'),100,12,'D','Y','Y','N','N','N','N','N','HU Label',TO_TIMESTAMP('2019-01-23 09:27:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-23T09:27:57.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=572920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-01-23T09:28:46.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,1000025,542064,TO_TIMESTAMP('2019-01-23 09:28:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','hu',5,'primary',TO_TIMESTAMP('2019-01-23 09:28:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-23T09:29:19.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,572919,0,180,542064,555354,'F',TO_TIMESTAMP('2019-01-23 09:29:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'advEdit_hu_IsHUtracing',10,0,0,TO_TIMESTAMP('2019-01-23 09:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-23T09:29:51.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,572920,0,180,542064,555355,'F',TO_TIMESTAMP('2019-01-23 09:29:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'advEdit_hu_HULabelPer',20,0,0,TO_TIMESTAMP('2019-01-23 09:29:51','YYYY-MM-DD HH24:MI:SS'),100)
;

--
-- deactivate the new columns until the BL is in place and/or they are needed
--
-- 2019-01-23T10:10:01.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-01-23 10:10:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563845
;

-- 2019-01-23T10:10:10.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-01-23 10:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563844
;

