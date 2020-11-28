-- 2020-05-11T06:07:26.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,AD_Org_ID,Updated,UpdatedBy,AD_Element_ID,ColumnName,PrintName,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-05-11 09:07:25','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2020-05-11 09:07:25','YYYY-MM-DD HH24:MI:SS'),100,577705,'ReferenceTooltipType','Reference Tooltit Type','Reference Tooltip Type','D')
;

-- 2020-05-11T06:07:26.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Description,PO_Help,PO_Name,PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,WEBUI_NameNewBreadcrumb,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.WEBUI_NameNewBreadcrumb,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577705 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-05-11T06:09:07.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-05-11 09:09:07','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2020-05-11 09:09:07','YYYY-MM-DD HH24:MI:SS'),100,541141,'L','TooltipType',0,'D')
;

-- 2020-05-11T06:09:07.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541141 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-05-11T06:10:31.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,Value,Name,AD_Org_ID,EntityType) VALUES (541141,0,'Y',TO_TIMESTAMP('2020-05-11 09:10:31','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-05-11 09:10:31','YYYY-MM-DD HH24:MI:SS'),100,542120,'DescriptionFallbackToTableIdentifier','DTI','DescriptionFallbackToTableIdentifier',0,'D')
;

-- 2020-05-11T06:10:31.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542120 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2020-05-11T06:10:56.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='TooltipType', PrintName='Tooltip Type', Name='Tooltip Type',Updated=TO_TIMESTAMP('2020-05-11 09:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577705
;

-- 2020-05-11T06:10:56.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TooltipType', Name='Tooltip Type', Description=NULL, Help=NULL WHERE AD_Element_ID=577705
;

-- 2020-05-11T06:10:56.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TooltipType', Name='Tooltip Type', Description=NULL, Help=NULL, AD_Element_ID=577705 WHERE UPPER(ColumnName)='TOOLTIPTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-11T06:10:56.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TooltipType', Name='Tooltip Type', Description=NULL, Help=NULL WHERE AD_Element_ID=577705 AND IsCentrallyMaintained='Y'
;

-- 2020-05-11T06:10:56.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Tooltip Type', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577705) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577705)
;

-- 2020-05-11T06:10:56.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Tooltip Type', Name='Tooltip Type' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577705)
;

-- 2020-05-11T06:10:56.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Tooltip Type', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577705
;

-- 2020-05-11T06:10:56.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Tooltip Type', Description=NULL, Help=NULL WHERE AD_Element_ID = 577705
;

-- 2020-05-11T06:10:56.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Tooltip Type', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577705
;

-- 2020-05-11T06:11:06.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Tooltip Type', Name='Tooltip Type',Updated=TO_TIMESTAMP('2020-05-11 09:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577705
;

-- 2020-05-11T06:11:06.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577705,'de_CH') 
;

-- 2020-05-11T06:11:21.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=577705
;

-- 2020-05-11T06:11:21.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=577705
;

-- 2020-05-11T06:11:31.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,AD_Org_ID,Updated,UpdatedBy,AD_Element_ID,ColumnName,PrintName,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-05-11 09:11:31','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2020-05-11 09:11:31','YYYY-MM-DD HH24:MI:SS'),100,577706,'TooltipType','Tooltip Type','Tooltip Type','D')
;

-- 2020-05-11T06:11:31.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Description,PO_Help,PO_Name,PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,WEBUI_NameNewBreadcrumb,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.WEBUI_NameNewBreadcrumb,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577706 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-05-11T06:13:25.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,Value,Name,AD_Org_ID,EntityType) VALUES (541141,0,'Y',TO_TIMESTAMP('2020-05-11 09:13:25','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-05-11 09:13:25','YYYY-MM-DD HH24:MI:SS'),100,542121,'TableIdentifier','TableIdentifier','TableIdentifier',0,'D')
;

-- 2020-05-11T06:13:25.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542121 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2020-05-11T06:13:31.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='T',Updated=TO_TIMESTAMP('2020-05-11 09:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542121
;

-- 2020-05-11T06:13:44.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,Value,Name,AD_Org_ID,EntityType) VALUES (541141,0,'Y',TO_TIMESTAMP('2020-05-11 09:13:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-05-11 09:13:43','YYYY-MM-DD HH24:MI:SS'),100,542122,'Description','D','Description',0,'D')
;

-- 2020-05-11T06:13:44.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542122 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2020-05-11T06:14:13.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,EntityType) VALUES (17,'DTI',5,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-05-11 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2020-05-11 09:14:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N',100,'N',541141,570684,'N','Y','N','N','N','N','N','N',0,'N','N','TooltipType','N','Tooltip Type',0,'N',0,0,577706,'D')
;

-- 2020-05-11T06:14:13.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570684 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-05-11T06:14:13.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577706) 
;

-- 2020-05-11T06:14:25.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Table','ALTER TABLE public.AD_Table ADD COLUMN TooltipType VARCHAR(5) DEFAULT ''DTI'' NOT NULL')
;

-- 2020-05-11T06:17:50.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_table','TooltipType','VARCHAR(5)',null,'DTI')
;

-- 2020-05-11T06:17:50.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TooltipType='DTI' WHERE TooltipType IS NULL
;

-- 2020-05-11T06:19:34.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_table','TooltipType','VARCHAR(5)',null,'DTI')
;

-- 2020-05-11T06:19:34.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TooltipType='DTI' WHERE TooltipType IS NULL
;

