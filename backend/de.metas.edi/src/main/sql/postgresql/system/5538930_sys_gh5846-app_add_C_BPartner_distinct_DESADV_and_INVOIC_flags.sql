-- 2019-12-13T12:27:45.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@EDI_ExportStatus/''E''@=''E''',Updated=TO_TIMESTAMP('2019-12-13 13:27:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555657
;

-- 2019-12-13T13:48:33.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='EDI INVOIC Receipient', PrintName='EDI INVOIC Receipient',Updated=TO_TIMESTAMP('2019-12-13 14:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542000 AND AD_Language='en_US'
;

-- 2019-12-13T13:48:34.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542000,'en_US') 
;

-- 2019-12-13T13:48:53.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Erhält EDI-INVOIC', PrintName='Erhält EDI-INVOIC',Updated=TO_TIMESTAMP('2019-12-13 14:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542000 AND AD_Language='de_DE'
;

-- 2019-12-13T13:48:53.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542000,'de_DE') 
;

-- 2019-12-13T13:48:53.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542000,'de_DE') 
;

-- 2019-12-13T13:48:53.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEdiRecipient', Name='Erhält EDI-INVOIC', Description='', Help=NULL WHERE AD_Element_ID=542000
;

-- 2019-12-13T13:48:53.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEdiRecipient', Name='Erhält EDI-INVOIC', Description='', Help=NULL, AD_Element_ID=542000 WHERE UPPER(ColumnName)='ISEDIRECIPIENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-13T13:48:53.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEdiRecipient', Name='Erhält EDI-INVOIC', Description='', Help=NULL WHERE AD_Element_ID=542000 AND IsCentrallyMaintained='Y'
;

-- 2019-12-13T13:48:53.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erhält EDI-INVOIC', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542000) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542000)
;

-- 2019-12-13T13:48:53.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erhält EDI-INVOIC', Name='Erhält EDI-INVOIC' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542000)
;

-- 2019-12-13T13:48:53.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erhält EDI-INVOIC', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542000
;

-- 2019-12-13T13:48:53.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erhält EDI-INVOIC', Description='', Help=NULL WHERE AD_Element_ID = 542000
;

-- 2019-12-13T13:48:53.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erhält EDI-INVOIC', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542000
;

-- 2019-12-13T13:49:03.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Erhält EDI-INVOIC', PrintName='Erhält EDI-INVOIC',Updated=TO_TIMESTAMP('2019-12-13 14:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542000 AND AD_Language='de_CH'
;

-- 2019-12-13T13:49:03.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542000,'de_CH') 
;

-- 2019-12-13T13:49:10.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-13 14:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542000 AND AD_Language='de_CH'
;

-- 2019-12-13T13:49:10.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542000,'de_CH') 
;

ALTER TABLE C_BPArtner RENAME COLUMN IsEdiRecipient TO IsEdiInvoicRecipient;
-- 2019-12-13T13:51:01.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsEdiInvoicRecipient',Updated=TO_TIMESTAMP('2019-12-13 14:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542000
;

-- 2019-12-13T13:51:01.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEdiInvoicRecipient', Name='Erhält EDI-INVOIC', Description='', Help=NULL WHERE AD_Element_ID=542000
;

-- 2019-12-13T13:51:01.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEdiInvoicRecipient', Name='Erhält EDI-INVOIC', Description='', Help=NULL, AD_Element_ID=542000 WHERE UPPER(ColumnName)='ISEDIINVOICRECIPIENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-13T13:51:01.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEdiInvoicRecipient', Name='Erhält EDI-INVOIC', Description='', Help=NULL WHERE AD_Element_ID=542000 AND IsCentrallyMaintained='Y'
;

-- 2019-12-13T13:51:37.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577426,0,'IsEdiDesadvRecipient',TO_TIMESTAMP('2019-12-13 14:51:36','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','Erhält EDI-DESADV','Erhält EDI-DESADV',TO_TIMESTAMP('2019-12-13 14:51:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-13T13:51:37.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577426 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-13T13:51:43.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-13 14:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577426 AND AD_Language='de_CH'
;

-- 2019-12-13T13:51:43.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577426,'de_CH') 
;

-- 2019-12-13T13:51:46.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-13 14:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577426 AND AD_Language='de_DE'
;

-- 2019-12-13T13:51:46.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577426,'de_DE') 
;

-- 2019-12-13T13:51:46.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577426,'de_DE') 
;

-- 2019-12-13T13:52:19.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI DESADV Receipient', PrintName='EDI DESADV Receipient',Updated=TO_TIMESTAMP('2019-12-13 14:52:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577426 AND AD_Language='en_US'
;

-- 2019-12-13T13:52:19.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577426,'en_US') 
;

-- 2019-12-13T13:52:37.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569749,577426,0,20,291,'IsEdiDesadvRecipient',TO_TIMESTAMP('2019-12-13 14:52:37','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','U',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Erhält EDI-DESADV',0,0,TO_TIMESTAMP('2019-12-13 14:52:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-12-13T13:52:37.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-13T13:52:37.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577426) 
;

-- 2019-12-13T13:52:38.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsEdiDesadvRecipient CHAR(1) DEFAULT ''N'' CHECK (IsEdiDesadvRecipient IN (''Y'',''N'')) NOT NULL')
;

COMMIT;
UPDATE C_BPartner SET IsEdiDesadvRecipient='Y' WHERE IsEdiInvoicRecipient='Y';

-- 2019-12-13T13:54:11.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569749,593783,0,220,0,TO_TIMESTAMP('2019-12-13 14:54:10','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.esb.edi',0,'Y','Y','Y','N','N','N','N','N','Erhält EDI-DESADV',390,380,0,1,1,TO_TIMESTAMP('2019-12-13 14:54:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-13T13:54:11.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-12-13T13:54:11.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577426) 
;

-- 2019-12-13T13:54:11.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593783
;

-- 2019-12-13T13:54:11.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593783)
;

-- 2019-12-13T13:54:28.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=155, SeqNoGrid=155,Updated=TO_TIMESTAMP('2019-12-13 14:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593783
;

-- 2019-12-13T13:54:30.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2019-12-13 14:54:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593783
;

-- 2019-12-13T13:55:37.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='IsEdiInvoicRecipient',Updated=TO_TIMESTAMP('2019-12-13 14:55:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000083
;

-- 2019-12-13T13:56:05.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,593783,0,220,1000011,564728,'F',TO_TIMESTAMP('2019-12-13 14:56:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsEdiDesadvRecipient',55,0,0,TO_TIMESTAMP('2019-12-13 14:56:05','YYYY-MM-DD HH24:MI:SS'),100)
;

