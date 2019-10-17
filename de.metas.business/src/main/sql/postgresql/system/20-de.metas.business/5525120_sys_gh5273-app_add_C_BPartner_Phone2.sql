/*
-- 2019-06-18T22:54:23.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568273
;

-- 2019-06-18T22:54:23.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568273
;
*/

-- 2019-06-18T22:55:39.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Telefon (alternativ)', Help='', IsTranslated='Y', Name='Telefon (alternativ)', Description='Alternative Telefonnummer',Updated=TO_TIMESTAMP('2019-06-18 22:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=506
;

-- 2019-06-18T22:55:39.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(506,'de_DE') 
;

-- 2019-06-18T22:55:39.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(506,'de_DE') 
;

-- 2019-06-18T22:55:39.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Phone2', Name='Telefon (alternativ)', Description='Alternative Telefonnummer', Help='' WHERE AD_Element_ID=506
;

-- 2019-06-18T22:55:39.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Phone2', Name='Telefon (alternativ)', Description='Alternative Telefonnummer', Help='', AD_Element_ID=506 WHERE UPPER(ColumnName)='PHONE2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-18T22:55:39.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Phone2', Name='Telefon (alternativ)', Description='Alternative Telefonnummer', Help='' WHERE AD_Element_ID=506 AND IsCentrallyMaintained='Y'
;

-- 2019-06-18T22:55:39.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Telefon (alternativ)', Description='Alternative Telefonnummer', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=506) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 506)
;

-- 2019-06-18T22:55:39.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Telefon (alternativ)', Name='Telefon (alternativ)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=506)
;

-- 2019-06-18T22:55:39.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Telefon (alternativ)', Description='Alternative Telefonnummer', Help='', CommitWarning = NULL WHERE AD_Element_ID = 506
;

-- 2019-06-18T22:55:39.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Telefon (alternativ)', Description='Alternative Telefonnummer', Help='' WHERE AD_Element_ID = 506
;

-- 2019-06-18T22:55:39.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Telefon (alternativ)', Description = 'Alternative Telefonnummer', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 506
;

-- 2019-06-18T22:55:48.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-18 22:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=506
;

-- 2019-06-18T22:55:48.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(506,'de_CH') 
;

-- 2019-06-18T22:56:20.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Phone (alternative)', Help='', Name='Phone (alternative)', Description='',Updated=TO_TIMESTAMP('2019-06-18 22:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=506
;

-- 2019-06-18T22:56:20.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(506,'en_US') 
;

-- 2019-06-18T22:56:33.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (10,255,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-06-18 22:56:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-06-18 22:56:33','YYYY-MM-DD HH24:MI:SS'),100,'N','N',291,'N','',568274,'N','N','N','N','N','N','N','N',0,0,506,'D','N','N','Phone2','N','Telefon (alternativ)','Alternative Telefonnummer')
;

-- 2019-06-18T22:56:33.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568274 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-06-18T22:56:33.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(506) 
;

-- 2019-06-18T22:56:45.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Phone2 VARCHAR(255)')
;

-- 2019-06-18T22:59:02.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='We added Phone2 as opposed to Phone for reasons of backward compatibility',Updated=TO_TIMESTAMP('2019-06-18 22:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568274
;

