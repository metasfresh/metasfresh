-- 2019-10-21T19:45:43.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Punktzahl', PrintName='Gelieferte Punktzahl',Updated=TO_TIMESTAMP('2019-10-21 21:45:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577082 AND AD_Language='de_CH'
;

-- 2019-10-21T19:45:43.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577082,'de_CH') 
;

-- 2019-10-21T19:45:49.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Punktzahl', PrintName='Gelieferte Punktzahl',Updated=TO_TIMESTAMP('2019-10-21 21:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577082 AND AD_Language='de_DE'
;

-- 2019-10-21T19:45:49.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577082,'de_DE') 
;

-- 2019-10-21T19:45:49.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577082,'de_DE') 
;

-- 2019-10-21T19:45:49.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PointsSum_Invoiceable', Name='Gelieferte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577082
;

-- 2019-10-21T19:45:49.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsSum_Invoiceable', Name='Gelieferte Punktzahl', Description=NULL, Help=NULL, AD_Element_ID=577082 WHERE UPPER(ColumnName)='POINTSSUM_INVOICEABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-21T19:45:49.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsSum_Invoiceable', Name='Gelieferte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577082 AND IsCentrallyMaintained='Y'
;

-- 2019-10-21T19:45:49.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gelieferte Punktzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577082) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577082)
;

-- 2019-10-21T19:45:49.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gelieferte Punktzahl', Name='Gelieferte Punktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577082)
;

-- 2019-10-21T19:45:49.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gelieferte Punktzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577082
;

-- 2019-10-21T19:45:49.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gelieferte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 577082
;

-- 2019-10-21T19:45:49.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gelieferte Punktzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577082
;

-- 2019-10-21T19:46:00.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Delivered points', PrintName='Delivered points',Updated=TO_TIMESTAMP('2019-10-21 21:46:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577082 AND AD_Language='en_US'
;

-- 2019-10-21T19:46:00.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577082,'en_US') 
;

-- 2019-10-21T19:46:41.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierte Punktzahl', PrintName='Fakturierte Punktzahl',Updated=TO_TIMESTAMP('2019-10-21 21:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577083 AND AD_Language='de_CH'
;

-- 2019-10-21T19:46:41.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577083,'de_CH') 
;

-- 2019-10-21T19:46:47.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierte Punktzahl', PrintName='Fakturierte Punktzahl',Updated=TO_TIMESTAMP('2019-10-21 21:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577083 AND AD_Language='de_DE'
;

-- 2019-10-21T19:46:47.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577083,'de_DE') 
;

-- 2019-10-21T19:46:47.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577083,'de_DE') 
;

-- 2019-10-21T19:46:47.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PointsSum_Invoiced', Name='Fakturierte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577083
;

-- 2019-10-21T19:46:47.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsSum_Invoiced', Name='Fakturierte Punktzahl', Description=NULL, Help=NULL, AD_Element_ID=577083 WHERE UPPER(ColumnName)='POINTSSUM_INVOICED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-21T19:46:47.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsSum_Invoiced', Name='Fakturierte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577083 AND IsCentrallyMaintained='Y'
;

-- 2019-10-21T19:46:47.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fakturierte Punktzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577083) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577083)
;

-- 2019-10-21T19:46:47.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Fakturierte Punktzahl', Name='Fakturierte Punktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577083)
;

-- 2019-10-21T19:46:47.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Fakturierte Punktzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577083
;

-- 2019-10-21T19:46:47.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Fakturierte Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 577083
;

-- 2019-10-21T19:46:47.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Fakturierte Punktzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577083
;

-- 2019-10-21T19:48:32.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577235,0,'PointsSum_ToSettle',TO_TIMESTAMP('2019-10-21 21:48:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Abzurechnende Punktzahl','Abzurechnende Punktzahl',TO_TIMESTAMP('2019-10-21 21:48:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T19:48:32.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577235 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-21T19:48:36.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-21 21:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577235 AND AD_Language='de_CH'
;

-- 2019-10-21T19:48:36.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577235,'de_CH') 
;

-- 2019-10-21T19:48:38.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-21 21:48:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577235 AND AD_Language='de_DE'
;

-- 2019-10-21T19:48:38.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577235,'de_DE') 
;

-- 2019-10-21T19:48:38.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577235,'de_DE') 
;

-- 2019-10-21T19:48:50.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Points to settle', PrintName='Points to settle',Updated=TO_TIMESTAMP('2019-10-21 21:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577235 AND AD_Language='en_US'
;

-- 2019-10-21T19:48:50.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577235,'en_US') 
;

-- 2019-10-21T19:49:27.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577236,0,'PointsSum_Settled',TO_TIMESTAMP('2019-10-21 21:49:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Abgerechnete Punktzahl','Abgerechnete Punktzahl',TO_TIMESTAMP('2019-10-21 21:49:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T19:49:27.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577236 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-21T19:49:48.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Settled points', PrintName='Settled points',Updated=TO_TIMESTAMP('2019-10-21 21:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577236 AND AD_Language='en_US'
;

-- 2019-10-21T19:49:48.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577236,'en_US') 
;

-- 2019-10-21T19:49:53.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-21 21:49:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577236 AND AD_Language='de_DE'
;

-- 2019-10-21T19:49:53.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577236,'de_DE') 
;

-- 2019-10-21T19:49:53.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577236,'de_DE') 
;

-- 2019-10-21T19:49:56.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-21 21:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577236 AND AD_Language='de_CH'
;

-- 2019-10-21T19:49:56.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577236,'de_CH') 
;

-- 2019-10-21T19:50:24.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569251,577235,0,22,541406,'PointsSum_ToSettle',TO_TIMESTAMP('2019-10-21 21:50:24','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Abzurechnende Punktzahl',0,0,TO_TIMESTAMP('2019-10-21 21:50:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-21T19:50:24.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569251 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-21T19:50:24.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577235) 
;

-- 2019-10-21T19:50:31.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2019-10-21 21:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569251
;

-- 2019-10-21T19:50:42.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-10-21 21:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569251
;

-- 2019-10-21T19:50:43.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Commission_Share','ALTER TABLE public.C_Commission_Share ADD COLUMN PointsSum_ToSettle NUMERIC DEFAULT 0 NOT NULL')
;

-- 2019-10-21T19:51:04.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569252,577236,0,22,541406,'PointsSum_Settled',TO_TIMESTAMP('2019-10-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,'N','0','de.metas.contracts.commission',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Abgerechnete Punktzahl',0,0,TO_TIMESTAMP('2019-10-21 21:51:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-21T19:51:04.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569252 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-21T19:51:04.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577236) 
;

-- 2019-10-21T19:51:09.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Commission_Share','ALTER TABLE public.C_Commission_Share ADD COLUMN PointsSum_Settled NUMERIC DEFAULT 0 NOT NULL')
;

-- 2019-10-21T20:19:45.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Provision Buchauszug', PrintName='Provision Buchauszug',Updated=TO_TIMESTAMP('2019-10-21 22:19:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577096 AND AD_Language='de_CH'
;

-- 2019-10-21T20:19:45.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577096,'de_CH') 
;

-- 2019-10-21T20:19:49.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Provision Buchauszug', PrintName='Provision Buchauszug',Updated=TO_TIMESTAMP('2019-10-21 22:19:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577096 AND AD_Language='de_DE'
;

-- 2019-10-21T20:19:49.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577096,'de_DE') 
;

-- 2019-10-21T20:19:49.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577096,'de_DE') 
;

-- 2019-10-21T20:19:49.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Commission_Share', Name='Provision Buchauszug', Description=NULL, Help=NULL WHERE AD_Element_ID=577096
;

-- 2019-10-21T20:19:49.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Commission_Share', Name='Provision Buchauszug', Description=NULL, Help=NULL, AD_Element_ID=577096 WHERE UPPER(ColumnName)='C_COMMISSION_SHARE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-21T20:19:49.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Commission_Share', Name='Provision Buchauszug', Description=NULL, Help=NULL WHERE AD_Element_ID=577096 AND IsCentrallyMaintained='Y'
;

-- 2019-10-21T20:19:49.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Provision Buchauszug', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577096) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577096)
;

-- 2019-10-21T20:19:49.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Provision Buchauszug', Name='Provision Buchauszug' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577096)
;

-- 2019-10-21T20:19:49.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Provision Buchauszug', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577096
;

-- 2019-10-21T20:19:49.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Provision Buchauszug', Description=NULL, Help=NULL WHERE AD_Element_ID = 577096
;

-- 2019-10-21T20:19:49.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Provision Buchauszug', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577096
;

-- 2019-10-21T20:20:16.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569251,590595,0,541943,TO_TIMESTAMP('2019-10-21 22:20:16','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.contracts.commission','Y','Y','N','N','N','N','N','Abzurechnende Punktzahl',TO_TIMESTAMP('2019-10-21 22:20:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T20:20:16.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-21T20:20:16.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577235) 
;

-- 2019-10-21T20:20:16.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590595
;

-- 2019-10-21T20:20:16.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590595)
;

-- 2019-10-21T20:20:16.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569252,590596,0,541943,TO_TIMESTAMP('2019-10-21 22:20:16','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.contracts.commission','Y','Y','N','N','N','N','N','Abgerechnete Punktzahl',TO_TIMESTAMP('2019-10-21 22:20:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T20:20:16.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-21T20:20:16.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577236) 
;

-- 2019-10-21T20:20:16.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590596
;

-- 2019-10-21T20:20:16.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590596)
;

-- 2019-10-21T20:21:38.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='Seales points',Updated=TO_TIMESTAMP('2019-10-21 22:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542900
;

-- 2019-10-21T20:21:46.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-10-21 22:21:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542901
;

-- 2019-10-21T20:22:01.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541916,543118,TO_TIMESTAMP('2019-10-21 22:22:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','Settlement points',20,TO_TIMESTAMP('2019-10-21 22:22:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T20:22:08.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='Sales points',Updated=TO_TIMESTAMP('2019-10-21 22:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542900
;

-- 2019-10-21T20:22:33.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590595,0,541943,543118,563583,'F',TO_TIMESTAMP('2019-10-21 22:22:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PointsSum_ToSettle',10,0,0,TO_TIMESTAMP('2019-10-21 22:22:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T20:22:50.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590596,0,541943,543118,563584,'F',TO_TIMESTAMP('2019-10-21 22:22:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PointsSum_Settled',20,0,0,TO_TIMESTAMP('2019-10-21 22:22:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-21T20:23:10.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-10-21 22:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563312
;

-- 2019-10-21T20:23:10.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-10-21 22:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561848
;

-- 2019-10-21T20:23:10.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-10-21 22:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561849
;

-- 2019-10-21T20:23:10.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-10-21 22:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561850
;

-- 2019-10-21T20:23:10.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-10-21 22:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563583
;

-- 2019-10-21T20:23:10.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-10-21 22:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563584
;

