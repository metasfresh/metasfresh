-- 2019-07-26T11:10:32.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576941,0,'QtyToInvoiceInPriceUOM_CatchWeight',TO_TIMESTAMP('2019-07-26 13:10:32','YYYY-MM-DD HH24:MI:SS'),100,'Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.','de.metas.invoicecandidate','Y','Catchweight-Menge in Preiseinheit','Catchweight-Menge in Preiseinheit',TO_TIMESTAMP('2019-07-26 13:10:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-26T11:10:32.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576941 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-26T11:10:53.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nominalmenge in Preiseinheit', PrintName='Nominalmenge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 13:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_CH'
;

-- 2019-07-26T11:10:53.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_CH') 
;

-- 2019-07-26T11:11:14.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nominal quantity in price-UOM', PrintName='Nominal quantity in price-UOM',Updated=TO_TIMESTAMP('2019-07-26 13:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='en_US'
;

-- 2019-07-26T11:11:14.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'en_US') 
;

-- 2019-07-26T11:11:25.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nominalmenge in Preiseinheit', PrintName='Nominalmenge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 13:11:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_DE'
;

-- 2019-07-26T11:11:25.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_DE') 
;

-- 2019-07-26T11:11:25.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542529,'de_DE') 
;

-- 2019-07-26T11:11:25.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-26T11:11:25.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_NOMINAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T11:11:25.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T11:11:25.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542529)
;

-- 2019-07-26T11:11:25.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Nominalmenge in Preiseinheit', Name='Nominalmenge in Preiseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542529)
;

-- 2019-07-26T11:11:25.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:11:25.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nominalmenge in Preiseinheit', Description='Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:11:25.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nominalmenge in Preiseinheit', Description = 'Zu berechnende Nominalmenge in der Mengeneinheit des Preises.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-26T11:11:50.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 13:11:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_DE'
;

-- 2019-07-26T11:11:50.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_DE') 
;

-- 2019-07-26T11:11:50.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576941,'de_DE') 
;

-- 2019-07-26T11:11:53.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 13:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_CH'
;

-- 2019-07-26T11:11:53.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_CH') 
;

-- 2019-07-26T11:12:10.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Catchweight quantity in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 13:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='en_US'
;

-- 2019-07-26T11:12:10.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'en_US') 
;

-- 2019-07-26T11:13:20.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Invoicable actually delivered quantity in the price''s unit of measurement.', Name='Catchweight quantity in price-UOM', PrintName='Catchweight quantity in price-UOM',Updated=TO_TIMESTAMP('2019-07-26 13:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='en_US'
;

-- 2019-07-26T11:13:20.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'en_US') 
;

-- 2019-07-26T11:15:17.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Invoicable nominal delivered quantity in the price''s unit of measurement.',Updated=TO_TIMESTAMP('2019-07-26 13:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='en_US'
;

-- 2019-07-26T11:15:17.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'en_US') 
;

-- 2019-07-26T11:15:47.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2019-07-26 13:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529
;

-- 2019-07-26T11:16:19.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568474,576941,0,29,540270,'QtyToInvoiceInPriceUOM_CatchWeight',TO_TIMESTAMP('2019-07-26 13:16:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Catchweight-Menge in Preiseinheit',0,0,TO_TIMESTAMP('2019-07-26 13:16:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-26T11:16:19.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568474 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-26T11:16:19.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576941) 
;

-- 2019-07-26T11:16:21.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyToInvoiceInPriceUOM_CatchWeight NUMERIC')
;

-- 2019-07-26T11:19:32.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576945,0,'QtyToInvoiceInPriceUOM_Eff',TO_TIMESTAMP('2019-07-26 13:19:31','YYYY-MM-DD HH24:MI:SS'),100,'Zu berechnende Menge in der Mengeneinheit des Preises.','de.metas.invoicecandidate','Y','Zu ber. Menge in Preiseinheit','Zu ber. Menge in Preiseinheit',TO_TIMESTAMP('2019-07-26 13:19:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-26T11:19:32.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576945 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-26T11:19:48.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-26 13:19:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-26 13:19:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540270,'N',568475,'N','N','N','N','N','N','N','N',0,0,576945,'de.metas.invoicecandidate','N','N','QtyToInvoiceInPriceUOM_Eff','N','Zu ber. Menge in Preiseinheit','Zu berechnende Menge in der Mengeneinheit des Preises.')
;

-- 2019-07-26T11:19:48.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568475 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-26T11:19:48.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576945) 
;

-- 2019-07-26T11:20:00.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 13:20:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_CH'
;

-- 2019-07-26T11:20:00.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_CH') 
;

-- 2019-07-26T11:20:05.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 13:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_DE'
;

-- 2019-07-26T11:20:05.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_DE') 
;

-- 2019-07-26T11:20:05.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576945,'de_DE') 
;

-- 2019-07-26T11:20:50.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 13:20:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='en_US'
;

-- 2019-07-26T11:20:50.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'en_US') 
;

-- 2019-07-26T11:21:02.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.',Updated=TO_TIMESTAMP('2019-07-26 13:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_DE'
;

-- 2019-07-26T11:21:02.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_DE') 
;

-- 2019-07-26T11:21:02.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576945,'de_DE') 
;

-- 2019-07-26T11:21:02.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-26T11:21:02.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_EFF' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T11:21:02.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T11:21:02.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu ber. Menge in Preiseinheit', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576945) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576945)
;

-- 2019-07-26T11:21:02.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu ber. Menge in Preiseinheit', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:21:02.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu ber. Menge in Preiseinheit', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:21:02.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu ber. Menge in Preiseinheit', Description = 'Effektiv zu berechnende Menge in der Mengeneinheit des Preises.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:21:07.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises.',Updated=TO_TIMESTAMP('2019-07-26 13:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_CH'
;

-- 2019-07-26T11:21:07.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_CH') 
;

-- 2019-07-26T11:22:35.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effeectivly invoicable quantity in the price''s unit of measurement.',Updated=TO_TIMESTAMP('2019-07-26 13:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='en_US'
;

-- 2019-07-26T11:22:35.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'en_US') 
;

-- 2019-07-26T11:23:44.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises, abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Name='Zu ber. Menge in Preiseinheit eff.', PrintName='Zu ber. Menge in Preiseinheit eff.',Updated=TO_TIMESTAMP('2019-07-26 13:23:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_DE'
;

-- 2019-07-26T11:23:44.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_DE') 
;

-- 2019-07-26T11:23:44.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576945,'de_DE') 
;

-- 2019-07-26T11:23:44.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises, abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-26T11:23:44.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises, abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_EFF' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T11:23:44.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises, abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T11:23:44.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises, abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576945) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576945)
;

-- 2019-07-26T11:23:44.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zu ber. Menge in Preiseinheit eff.', Name='Zu ber. Menge in Preiseinheit eff.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576945)
;

-- 2019-07-26T11:23:44.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises, abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:23:44.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises, abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:23:44.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu ber. Menge in Preiseinheit eff.', Description = 'Effektiv zu berechnende Menge in der Mengeneinheit des Preises, abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:23:50.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.',Updated=TO_TIMESTAMP('2019-07-26 13:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_DE'
;

-- 2019-07-26T11:23:50.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_DE') 
;

-- 2019-07-26T11:23:50.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576945,'de_DE') 
;

-- 2019-07-26T11:23:50.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-26T11:23:50.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_EFF' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T11:23:50.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T11:23:50.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576945) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576945)
;

-- 2019-07-26T11:23:50.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:23:50.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu ber. Menge in Preiseinheit eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:23:50.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu ber. Menge in Preiseinheit eff.', Description = 'Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-26T11:24:03.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.',Updated=TO_TIMESTAMP('2019-07-26 13:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_CH'
;

-- 2019-07-26T11:24:03.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_CH') 
;

-- 2019-07-26T11:24:18.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effectivly invoicable quantity in the price''s unit of measurement.', Name='Zu ber. Menge in Preiseinheit eff.', PrintName='Zu ber. Menge in Preiseinheit eff.',Updated=TO_TIMESTAMP('2019-07-26 13:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='en_US'
;

-- 2019-07-26T11:24:18.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'en_US') 
;

-- 2019-07-26T11:24:29.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zu ber. Menge in Preiseinheit eff.', PrintName='Zu ber. Menge in Preiseinheit eff.',Updated=TO_TIMESTAMP('2019-07-26 13:24:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_CH'
;

-- 2019-07-26T11:24:29.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_CH') 
;

-- 2019-07-26T11:25:24.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Effectivly invoicable quantity in the price''s unit of measurement; depends on whether catch weight invoicing is done.', Name='Quantity to invoice in price-UOM eff.', PrintName='Quantity to invoice in price-UOM eff.',Updated=TO_TIMESTAMP('2019-07-26 13:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='en_US'
;

-- 2019-07-26T11:25:24.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'en_US') 
;

