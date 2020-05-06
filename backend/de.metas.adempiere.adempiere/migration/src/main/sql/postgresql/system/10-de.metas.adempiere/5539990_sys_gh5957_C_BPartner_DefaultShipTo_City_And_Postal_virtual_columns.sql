-- 2019-12-20T12:39:16.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577445,0,'DefaultShipTo_City',TO_TIMESTAMP('2019-12-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Standard Lieferstadt','Standard Lieferstadt',TO_TIMESTAMP('2019-12-20 14:39:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-20T12:39:16.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577445 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-20T12:39:27.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-20 14:39:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577445 AND AD_Language='de_CH'
;

-- 2019-12-20T12:39:27.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577445,'de_CH') 
;

-- 2019-12-20T12:39:30.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-20 14:39:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577445 AND AD_Language='de_DE'
;

-- 2019-12-20T12:39:30.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577445,'de_DE') 
;

-- 2019-12-20T12:39:30.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577445,'de_DE') 
;

-- 2019-12-20T12:39:43.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Default Ship To City', PrintName='Default Ship To City',Updated=TO_TIMESTAMP('2019-12-20 14:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577445 AND AD_Language='en_US'
;

-- 2019-12-20T12:39:43.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577445,'en_US') 
;

-- 2019-12-20T12:40:21.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577446,0,'DefaultShipTo_Postal',TO_TIMESTAMP('2019-12-20 14:40:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Standard Liefer PLZ','Standard Liefer PLZ',TO_TIMESTAMP('2019-12-20 14:40:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-20T12:40:21.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577446 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-20T12:40:34.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-20 14:40:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577446 AND AD_Language='de_CH'
;

-- 2019-12-20T12:40:34.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577446,'de_CH') 
;

-- 2019-12-20T12:40:35.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-20 14:40:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577446 AND AD_Language='de_DE'
;

-- 2019-12-20T12:40:35.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577446,'de_DE') 
;

-- 2019-12-20T12:40:35.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577446,'de_DE') 
;

-- 2019-12-20T12:40:51.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Default Ship To Postal', PrintName='Default Ship To Postal',Updated=TO_TIMESTAMP('2019-12-20 14:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577446 AND AD_Language='en_US'
;

-- 2019-12-20T12:40:51.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577446,'en_US') 
;

-- 2019-12-20T12:40:54.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-20 14:40:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577446 AND AD_Language='en_US'
;

-- 2019-12-20T12:40:54.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577446,'en_US') 
;

-- 2019-12-20T12:43:51.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,ColumnSQL,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,EntityType,AD_Org_ID) VALUES (10,255,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-12-20 14:43:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-12-20 14:43:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',291,'N',569793,'N','N','N','N','N','N','(select MAX(city) from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isshiptodefault=''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID)','N','N',0,577445,'N','N','DefaultShipTo_City','N','Standard Lieferstadt','D',0)
;

-- 2019-12-20T12:43:51.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569793 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-20T12:43:51.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577445) 
;

-- 2019-12-20T12:44:07.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2019-12-20 14:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546704
;

-- 2019-12-20T12:44:16.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2019-12-20 14:44:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557178
;

-- 2019-12-20T12:44:30.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2019-12-20 14:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557180
;

-- 2019-12-20T12:45:22.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,ColumnSQL,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,EntityType,AD_Org_ID) VALUES (10,40,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-12-20 14:45:22','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-12-20 14:45:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',291,'N',569794,'N','N','N','N','N','N','(select MAX(postal) from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isshiptodefault=''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID)','N','N',0,577446,'N','N','DefaultShipTo_Postal','N','Standard Liefer PLZ','D',0)
;

-- 2019-12-20T12:45:22.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569794 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-20T12:45:22.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577446) 
;

