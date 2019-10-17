-- 2019-02-13T09:24:41.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576107,0,'DataEntryRecordData',TO_TIMESTAMP('2019-02-13 09:24:41','YYYY-MM-DD HH24:MI:SS'),100,'Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display','de.metas.dataentry','Y','DataEntryRecordData','DataEntryRecordData',TO_TIMESTAMP('2019-02-13 09:24:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-13T09:24:41.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576107 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-02-13T09:25:03.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='DataEntry_RecordData',Updated=TO_TIMESTAMP('2019-02-13 09:25:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576107
;

-- 2019-02-13T09:25:03.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DataEntry_RecordData', Name='DataEntryRecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL WHERE AD_Element_ID=576107
;

-- 2019-02-13T09:25:03.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_RecordData', Name='DataEntryRecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL, AD_Element_ID=576107 WHERE UPPER(ColumnName)='DATAENTRY_RECORDDATA' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-13T09:25:03.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_RecordData', Name='DataEntryRecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL WHERE AD_Element_ID=576107 AND IsCentrallyMaintained='Y'
;

-- 2019-02-13T09:25:15.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 09:25:15','YYYY-MM-DD HH24:MI:SS'),Name='DataEntry_RecordData',PrintName='DataEntry_RecordData' WHERE AD_Element_ID=576107 AND AD_Language='de_CH'
;

-- 2019-02-13T09:25:15.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576107,'de_CH') 
;

-- 2019-02-13T09:25:19.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 09:25:19','YYYY-MM-DD HH24:MI:SS'),Name='DataEntry_RecordData',PrintName='DataEntry_RecordData' WHERE AD_Element_ID=576107 AND AD_Language='de_DE'
;

-- 2019-02-13T09:25:19.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576107,'de_DE') 
;

-- 2019-02-13T09:25:19.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576107,'de_DE') 
;

-- 2019-02-13T09:25:19.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DataEntry_RecordData', Name='DataEntry_RecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL WHERE AD_Element_ID=576107
;

-- 2019-02-13T09:25:19.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_RecordData', Name='DataEntry_RecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL, AD_Element_ID=576107 WHERE UPPER(ColumnName)='DATAENTRY_RECORDDATA' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-13T09:25:19.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_RecordData', Name='DataEntry_RecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL WHERE AD_Element_ID=576107 AND IsCentrallyMaintained='Y'
;

-- 2019-02-13T09:25:19.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='DataEntry_RecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576107) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576107)
;

-- 2019-02-13T09:25:19.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='DataEntry_RecordData', Name='DataEntry_RecordData' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576107)
;

-- 2019-02-13T09:25:19.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='DataEntry_RecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576107
;

-- 2019-02-13T09:25:19.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='DataEntry_RecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', Help=NULL WHERE AD_Element_ID = 576107
;

-- 2019-02-13T09:25:19.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='DataEntry_RecordData', Description='Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576107
;

-- 2019-02-13T09:25:22.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 09:25:22','YYYY-MM-DD HH24:MI:SS'),Name='DataEntry_RecordData',PrintName='DataEntry_RecordData' WHERE AD_Element_ID=576107 AND AD_Language='en_US'
;

-- 2019-02-13T09:25:22.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576107,'en_US') 
;

-- 2019-02-13T09:25:26.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 09:25:26','YYYY-MM-DD HH24:MI:SS'),Name='DataEntry_RecordData',PrintName='DataEntry_RecordData' WHERE AD_Element_ID=576107 AND AD_Language='nl_NL'
;

-- 2019-02-13T09:25:26.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576107,'nl_NL') 
;

-- 2019-02-13T09:25:30.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 09:25:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=576107 AND AD_Language='en_US'
;

-- 2019-02-13T09:25:30.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576107,'en_US') 
;

-- 2019-02-13T09:25:54.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564136,576107,0,36,541169,'DataEntry_RecordData',TO_TIMESTAMP('2019-02-13 09:25:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display','de.metas.dataentry',-469762048,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','DataEntry_RecordData',0,0,TO_TIMESTAMP('2019-02-13 09:25:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-02-13T09:25:54.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564136 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2019-02-13T14:29:30.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DataEntry_Record','ALTER TABLE public.DataEntry_Record ADD COLUMN DataEntry_RecordData TEXT')
;

-- 2019-02-13T14:31:24.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=563974
;

-- 2019-02-13T14:31:24.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=563974
;

-- 2019-02-13T14:31:31.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=576043
;

-- 2019-02-13T14:31:31.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=576043
;

-- 2019-02-13T14:31:41.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=563972
;

-- 2019-02-13T14:31:41.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=563972
;

-- 2019-02-13T14:32:08.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=563973
;

-- 2019-02-13T14:32:08.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=563973
;

-- 2019-02-13T14:32:19.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=563971
;

-- 2019-02-13T14:32:19.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=563971
;


/* DDL */ SELECT public.db_alter_table('DataEntry_Record','ALTER TABLE public.DataEntry_Record DROP COLUMN ValueDate');
/* DDL */ SELECT public.db_alter_table('DataEntry_Record','ALTER TABLE public.DataEntry_Record DROP COLUMN ValueNumber');
/* DDL */ SELECT public.db_alter_table('DataEntry_Record','ALTER TABLE public.DataEntry_Record DROP COLUMN ValueStr');
/* DDL */ SELECT public.db_alter_table('DataEntry_Record','ALTER TABLE public.DataEntry_Record DROP COLUMN ValueYesNo');
