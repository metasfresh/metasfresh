-- 2019-10-22T19:45:18.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577238,0,'IsPickOrder',TO_TIMESTAMP('2019-10-22 22:45:18','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','IsPickOrder','IsPickOrder',TO_TIMESTAMP('2019-10-22 22:45:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T19:45:18.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577238 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-22T19:45:55.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Picking Order', PrintName='Picking Order',Updated=TO_TIMESTAMP('2019-10-22 22:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577238 AND AD_Language='de_CH'
;

-- 2019-10-22T19:45:55.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577238,'de_CH') 
;

-- 2019-10-22T19:45:59.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2019-10-22 22:45:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577238 AND AD_Language='de_CH'
;

-- 2019-10-22T19:45:59.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577238,'de_CH') 
;

-- 2019-10-22T19:46:02.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Picking Order', PrintName='Picking Order',Updated=TO_TIMESTAMP('2019-10-22 22:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577238 AND AD_Language='de_DE'
;

-- 2019-10-22T19:46:02.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577238,'de_DE') 
;

-- 2019-10-22T19:46:02.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577238,'de_DE') 
;

-- 2019-10-22T19:46:02.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPickOrder', Name='Picking Order', Description=NULL, Help=NULL WHERE AD_Element_ID=577238
;

-- 2019-10-22T19:46:02.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPickOrder', Name='Picking Order', Description=NULL, Help=NULL, AD_Element_ID=577238 WHERE UPPER(ColumnName)='ISPICKORDER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T19:46:02.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPickOrder', Name='Picking Order', Description=NULL, Help=NULL WHERE AD_Element_ID=577238 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T19:46:02.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Picking Order', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577238) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577238)
;

-- 2019-10-22T19:46:02.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Picking Order', Name='Picking Order' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577238)
;

-- 2019-10-22T19:46:02.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Picking Order', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577238
;

-- 2019-10-22T19:46:02.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Picking Order', Description=NULL, Help=NULL WHERE AD_Element_ID = 577238
;

-- 2019-10-22T19:46:02.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Picking Order', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577238
;

-- 2019-10-22T19:46:05.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Picking Order', PrintName='Picking Order',Updated=TO_TIMESTAMP('2019-10-22 22:46:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577238 AND AD_Language='en_US'
;

-- 2019-10-22T19:46:05.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577238,'en_US') 
;

-- 2019-10-22T19:46:15.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsPickingOrder',Updated=TO_TIMESTAMP('2019-10-22 22:46:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577238
;

-- 2019-10-22T19:46:15.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPickingOrder', Name='Picking Order', Description=NULL, Help=NULL WHERE AD_Element_ID=577238
;

-- 2019-10-22T19:46:15.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPickingOrder', Name='Picking Order', Description=NULL, Help=NULL, AD_Element_ID=577238 WHERE UPPER(ColumnName)='ISPICKINGORDER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T19:46:15.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPickingOrder', Name='Picking Order', Description=NULL, Help=NULL WHERE AD_Element_ID=577238 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T19:52:00.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kommissionierauftrag', PrintName='Kommissionierauftrag',Updated=TO_TIMESTAMP('2019-10-22 22:52:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577238 AND AD_Language='de_CH'
;

-- 2019-10-22T19:52:00.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577238,'de_CH') 
;

-- 2019-10-22T19:52:04.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kommissionierauftrag', PrintName='Kommissionierauftrag',Updated=TO_TIMESTAMP('2019-10-22 22:52:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577238 AND AD_Language='de_DE'
;

-- 2019-10-22T19:52:04.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577238,'de_DE') 
;

-- 2019-10-22T19:52:04.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577238,'de_DE') 
;

-- 2019-10-22T19:52:04.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPickingOrder', Name='Kommissionierauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID=577238
;

-- 2019-10-22T19:52:04.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPickingOrder', Name='Kommissionierauftrag', Description=NULL, Help=NULL, AD_Element_ID=577238 WHERE UPPER(ColumnName)='ISPICKINGORDER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-22T19:52:04.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPickingOrder', Name='Kommissionierauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID=577238 AND IsCentrallyMaintained='Y'
;

-- 2019-10-22T19:52:04.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionierauftrag', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577238) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577238)
;

-- 2019-10-22T19:52:04.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kommissionierauftrag', Name='Kommissionierauftrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577238)
;

-- 2019-10-22T19:52:04.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kommissionierauftrag', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577238
;

-- 2019-10-22T19:52:04.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kommissionierauftrag', Description=NULL, Help=NULL WHERE AD_Element_ID = 577238
;

-- 2019-10-22T19:52:04.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kommissionierauftrag', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577238
;

-- 2019-10-22T19:52:24.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569253,577238,0,20,53027,'IsPickingOrder',TO_TIMESTAMP('2019-10-22 22:52:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Kommissionierauftrag',0,0,TO_TIMESTAMP('2019-10-22 22:52:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-22T19:52:25Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569253 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-22T19:52:25.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577238) 
;

-- 2019-10-22T19:52:26.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Order','ALTER TABLE public.PP_Order ADD COLUMN IsPickingOrder CHAR(1) DEFAULT ''N'' CHECK (IsPickingOrder IN (''Y'',''N'')) NOT NULL')
;

-- 2019-10-22T19:53:34.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569254,577238,0,20,53020,'IsPickingOrder',TO_TIMESTAMP('2019-10-22 22:53:33','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Kommissionierauftrag',0,0,TO_TIMESTAMP('2019-10-22 22:53:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-22T19:53:34.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569254 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-22T19:53:34.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577238) 
;

-- 2019-10-22T19:53:36.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN IsPickingOrder CHAR(1) DEFAULT ''N'' CHECK (IsPickingOrder IN (''Y'',''N'')) NOT NULL')
;

-- 2019-10-22T19:54:04.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569254,590598,0,53030,TO_TIMESTAMP('2019-10-22 22:54:04','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Kommissionierauftrag',TO_TIMESTAMP('2019-10-22 22:54:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T19:54:04.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590598 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-22T19:54:04.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577238) 
;

-- 2019-10-22T19:54:04.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590598
;

-- 2019-10-22T19:54:04.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590598)
;

-- 2019-10-22T19:55:15.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,590598,0,53030,563586,540382,TO_TIMESTAMP('2019-10-22 22:55:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kommissionierauftrag',210,0,0,TO_TIMESTAMP('2019-10-22 22:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-22T19:55:25.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2019-10-22 22:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563586
;

-- 2019-10-22T19:55:59.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManufactured/N@=Y',Updated=TO_TIMESTAMP('2019-10-22 22:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=590598
;

-- 2019-10-22T19:57:58.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-22 22:57:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552944
;

-- 2019-10-22T19:58:04.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='EE01',Updated=TO_TIMESTAMP('2019-10-22 22:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552944
;

-- 2019-10-22T19:58:12.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-22 22:58:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542934
;

-- 2019-10-22T19:58:22.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Trading Product',Updated=TO_TIMESTAMP('2019-10-22 22:58:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542934 AND AD_Language='en_US'
;

-- 2019-10-22T19:58:22.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542934,'en_US') 
;

