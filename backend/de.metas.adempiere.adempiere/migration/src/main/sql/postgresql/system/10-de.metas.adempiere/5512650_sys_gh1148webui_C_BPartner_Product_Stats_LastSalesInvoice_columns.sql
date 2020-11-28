-- 2019-02-13T15:47:33.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576110,0,'LastSalesPrice',TO_TIMESTAMP('2019-02-13 15:47:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Last Sales Price','Last Sales Price',TO_TIMESTAMP('2019-02-13 15:47:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-13T15:47:33.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576110 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-02-13T15:47:51.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576111,0,'LastSalesPrice_Currency_ID',TO_TIMESTAMP('2019-02-13 15:47:51','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Last Sales Price Currency','Last Sales Price Currency',TO_TIMESTAMP('2019-02-13 15:47:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-13T15:47:51.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576111 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-02-13T15:48:12.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564138,576110,0,37,541171,'LastSalesPrice',TO_TIMESTAMP('2019-02-13 15:48:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Last Sales Price',0,0,TO_TIMESTAMP('2019-02-13 15:48:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-02-13T15:48:12.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564138 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-13T15:48:24.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product_Stats','ALTER TABLE public.C_BPartner_Product_Stats ADD COLUMN LastSalesPrice NUMERIC')
;

-- 2019-02-13T15:50:23.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564139,576111,0,30,112,541171,'LastSalesPrice_Currency_ID',TO_TIMESTAMP('2019-02-13 15:50:23','YYYY-MM-DD HH24:MI:SS'),100,'N','U',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Last Sales Price Currency',0,0,TO_TIMESTAMP('2019-02-13 15:50:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-02-13T15:50:23.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564139 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-13T15:50:31.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-13 15:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564139
;

-- 2019-02-13T15:50:36.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-13 15:50:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576111
;

-- 2019-02-13T15:50:40.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product_Stats','ALTER TABLE public.C_BPartner_Product_Stats ADD COLUMN LastSalesPrice_Currency_ID NUMERIC(10)')
;

-- 2019-02-13T15:50:40.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner_Product_Stats ADD CONSTRAINT LastSalesPriceCurrency_CBPartnerProductStats FOREIGN KEY (LastSalesPrice_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- 2019-02-13T15:51:30.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 15:51:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Letzter VK',PrintName='Letzter VK',Description='letzter Verkaufspreis' WHERE AD_Element_ID=576110 AND AD_Language='de_CH'
;

-- 2019-02-13T15:51:30.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576110,'de_CH') 
;

-- 2019-02-13T15:51:38.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 15:51:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='letzter Verkaufspreis' WHERE AD_Element_ID=576110 AND AD_Language='de_DE'
;

-- 2019-02-13T15:51:38.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576110,'de_DE') 
;

-- 2019-02-13T15:51:38.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576110,'de_DE') 
;

-- 2019-02-13T15:51:39.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSalesPrice', Name='Last Sales Price', Description='letzter Verkaufspreis', Help=NULL WHERE AD_Element_ID=576110
;

-- 2019-02-13T15:51:39.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSalesPrice', Name='Last Sales Price', Description='letzter Verkaufspreis', Help=NULL, AD_Element_ID=576110 WHERE UPPER(ColumnName)='LASTSALESPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-13T15:51:39.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSalesPrice', Name='Last Sales Price', Description='letzter Verkaufspreis', Help=NULL WHERE AD_Element_ID=576110 AND IsCentrallyMaintained='Y'
;

-- 2019-02-13T15:51:39.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Last Sales Price', Description='letzter Verkaufspreis', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576110) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576110)
;

-- 2019-02-13T15:51:39.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Last Sales Price', Description='letzter Verkaufspreis', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576110
;

-- 2019-02-13T15:51:39.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Last Sales Price', Description='letzter Verkaufspreis', Help=NULL WHERE AD_Element_ID = 576110
;

-- 2019-02-13T15:51:39.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Last Sales Price', Description='letzter Verkaufspreis', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576110
;

-- 2019-02-13T15:51:45.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 15:51:45','YYYY-MM-DD HH24:MI:SS'),Name='Letzter VK',PrintName='Letzter VK' WHERE AD_Element_ID=576110 AND AD_Language='de_DE'
;

-- 2019-02-13T15:51:45.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576110,'de_DE') 
;

-- 2019-02-13T15:51:45.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576110,'de_DE') 
;

-- 2019-02-13T15:51:45.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSalesPrice', Name='Letzter VK', Description='letzter Verkaufspreis', Help=NULL WHERE AD_Element_ID=576110
;

-- 2019-02-13T15:51:45.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSalesPrice', Name='Letzter VK', Description='letzter Verkaufspreis', Help=NULL, AD_Element_ID=576110 WHERE UPPER(ColumnName)='LASTSALESPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-13T15:51:45.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSalesPrice', Name='Letzter VK', Description='letzter Verkaufspreis', Help=NULL WHERE AD_Element_ID=576110 AND IsCentrallyMaintained='Y'
;

-- 2019-02-13T15:51:45.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzter VK', Description='letzter Verkaufspreis', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576110) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576110)
;

-- 2019-02-13T15:51:45.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter VK', Name='Letzter VK' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576110)
;

-- 2019-02-13T15:51:45.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Letzter VK', Description='letzter Verkaufspreis', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576110
;

-- 2019-02-13T15:51:45.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Letzter VK', Description='letzter Verkaufspreis', Help=NULL WHERE AD_Element_ID = 576110
;

-- 2019-02-13T15:51:45.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Letzter VK', Description='letzter Verkaufspreis', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576110
;

-- 2019-02-13T15:51:50.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 15:51:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=576110 AND AD_Language='en_US'
;

-- 2019-02-13T15:51:50.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576110,'en_US') 
;

-- 2019-02-13T15:52:03.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 15:52:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=576111 AND AD_Language='en_US'
;

-- 2019-02-13T15:52:03.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576111,'en_US') 
;

-- 2019-02-13T15:52:49.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 15:52:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Letzter VK Währung',PrintName='Letzter VK Währung',Description='Letzter Verkaufspreis Währung' WHERE AD_Element_ID=576111 AND AD_Language='de_DE'
;

-- 2019-02-13T15:52:49.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576111,'de_DE') 
;

-- 2019-02-13T15:52:49.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576111,'de_DE') 
;

-- 2019-02-13T15:52:49.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSalesPrice_Currency_ID', Name='Letzter VK Währung', Description='Letzter Verkaufspreis Währung', Help=NULL WHERE AD_Element_ID=576111
;

-- 2019-02-13T15:52:49.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSalesPrice_Currency_ID', Name='Letzter VK Währung', Description='Letzter Verkaufspreis Währung', Help=NULL, AD_Element_ID=576111 WHERE UPPER(ColumnName)='LASTSALESPRICE_CURRENCY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-13T15:52:49.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSalesPrice_Currency_ID', Name='Letzter VK Währung', Description='Letzter Verkaufspreis Währung', Help=NULL WHERE AD_Element_ID=576111 AND IsCentrallyMaintained='Y'
;

-- 2019-02-13T15:52:49.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzter VK Währung', Description='Letzter Verkaufspreis Währung', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576111) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576111)
;

-- 2019-02-13T15:52:49.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter VK Währung', Name='Letzter VK Währung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576111)
;

-- 2019-02-13T15:52:49.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Letzter VK Währung', Description='Letzter Verkaufspreis Währung', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576111
;

-- 2019-02-13T15:52:49.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Letzter VK Währung', Description='Letzter Verkaufspreis Währung', Help=NULL WHERE AD_Element_ID = 576111
;

-- 2019-02-13T15:52:49.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Letzter VK Währung', Description='Letzter Verkaufspreis Währung', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576111
;

-- 2019-02-13T15:53:04.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-13 15:53:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Letzter VK Währung',PrintName='Letzter VK Währung',Description='Letzter Verkaufspreis Währung' WHERE AD_Element_ID=576111 AND AD_Language='de_CH'
;

-- 2019-02-13T15:53:04.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576111,'de_CH') 
;

-- 2019-02-14T08:12:33.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576114,0,'LastSales_Invoice_ID',TO_TIMESTAMP('2019-02-14 08:12:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Letzte Debitoren Rechnung','Letzte Debitoren Rechnung',TO_TIMESTAMP('2019-02-14 08:12:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-14T08:12:33.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576114 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-02-14T08:12:38.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-14 08:12:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=576114 AND AD_Language='de_CH'
;

-- 2019-02-14T08:12:38.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576114,'de_CH') 
;

-- 2019-02-14T08:12:41.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-14 08:12:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=576114 AND AD_Language='de_DE'
;

-- 2019-02-14T08:12:41.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576114,'de_DE') 
;

-- 2019-02-14T08:12:41.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576114,'de_DE') 
;

-- 2019-02-14T08:12:49.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-14 08:12:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Last Sales Invoice',PrintName='Last Sales Invoice' WHERE AD_Element_ID=576114 AND AD_Language='en_US'
;

-- 2019-02-14T08:12:49.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576114,'en_US') 
;

-- 2019-02-14T08:13:53.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,Name,IsAutoApplyValidationRule) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-02-14 08:13:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 08:13:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541171,'N',564146,'N','N','N','N','N','N','N','N',0,0,576114,'N','N','D','LastSales_Invoice_ID','Letzte Debitoren Rechnung','N')
;

-- 2019-02-14T08:13:53.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564146 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T08:14:01.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2019-02-14 08:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564146
;

-- 2019-02-14T08:14:05.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product_Stats','ALTER TABLE public.C_BPartner_Product_Stats ADD COLUMN LastSales_Invoice_ID NUMERIC(10)')
;

-- 2019-02-14T08:32:55.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=336,Updated=TO_TIMESTAMP('2019-02-14 08:32:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564146
;

-- 2019-02-14T08:45:43.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576115,0,'LastSalesInvoiceDate',TO_TIMESTAMP('2019-02-14 08:45:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Last Sales Invoice Date','Last Sales Invoice Date',TO_TIMESTAMP('2019-02-14 08:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-14T08:45:43.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576115 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-02-14T08:45:56.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,EntityType,ColumnName,Name,IsAutoApplyValidationRule) VALUES (15,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-02-14 08:45:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 08:45:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541171,'N',564147,'N','N','N','N','N','N','N','N',0,0,576115,'N','N','D','LastSalesInvoiceDate','Last Sales Invoice Date','N')
;

-- 2019-02-14T08:45:56.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564147 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T08:45:57.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product_Stats','ALTER TABLE public.C_BPartner_Product_Stats ADD COLUMN LastSalesInvoiceDate TIMESTAMP WITHOUT TIME ZONE')
;



-- -----------------------------------------------------------------------------------------------------------
















-- 2019-02-14T09:18:52.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541177,'N',TO_TIMESTAMP('2019-02-14 09:18:52','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'BPartner Product invocing statistics Online View','NP','L','C_BPartner_Product_Stats_Invoice_Online_V',TO_TIMESTAMP('2019-02-14 09:18:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-14T09:18:52.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541177 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-02-14T09:18:53.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554794,TO_TIMESTAMP('2019-02-14 09:18:52','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_BPartner_Product_Stats_Invoice_Online_V',1,'Y','N','Y','Y','C_BPartner_Product_Stats_Invoice_Online_V','N',1000000,TO_TIMESTAMP('2019-02-14 09:18:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-14T09:19:07.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N', IsHighVolume='Y', IsView='Y',Updated=TO_TIMESTAMP('2019-02-14 09:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541177
;

-- 2019-02-14T09:19:15.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (19,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,541177,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',564148,'N',0,187,'D','C_BPartner_ID','Geschäftspartner','Bezeichnet einen Geschäftspartner')
;

-- 2019-02-14T09:19:15.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564148 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:15.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (19,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,541177,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',564149,'N',0,454,'D','M_Product_ID','Produkt','Produkt, Leistung, Artikel')
;

-- 2019-02-14T09:19:15.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564149 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:15.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (20,1,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,541177,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.',564150,'N',0,1106,'D','IsSOTrx','Sales Transaction','This is a Sales Transaction')
;

-- 2019-02-14T09:19:15.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564150 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:15.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (16,29,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,541177,'"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.',564151,'N',0,267,'D','DateInvoiced','Rechnungsdatum','Datum auf der Rechnung')
;

-- 2019-02-14T09:19:15.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564151 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:15.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (19,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,541177,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',564152,'N',0,193,'D','C_Currency_ID','Währung','Die Währung für diesen Eintrag')
;

-- 2019-02-14T09:19:15.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564152 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:15.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (22,131089,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,541177,'Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.',564153,'N',0,519,'D','PriceActual','Einzelpreis','Effektiver Preis')
;

-- 2019-02-14T09:19:15.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564153 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:15.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (19,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,541177,'The Invoice Document.',564154,'N',0,1008,'D','C_Invoice_ID','Rechnung','Invoice Identifier')
;

-- 2019-02-14T09:19:15.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564154 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:15.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (19,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:15','YYYY-MM-DD HH24:MI:SS'),100,541177,'Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.',564155,'N',0,1076,'D','C_InvoiceLine_ID','Rechnungsposition','Rechnungszeile')
;

-- 2019-02-14T09:19:15.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564155 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:16.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (19,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,541177,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',564156,'N',0,102,'D','AD_Client_ID','Mandant','Mandant für diese Installation.')
;

-- 2019-02-14T09:19:16.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564156 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:16.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (19,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,541177,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',564157,'N',0,113,'D','AD_Org_ID','Sektion','Organisatorische Einheit des Mandanten')
;

-- 2019-02-14T09:19:16.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564157 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:16.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (16,35,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,541177,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',564158,'N',0,245,'D','Created','Erstellt','Datum, an dem dieser Eintrag erstellt wurde')
;

-- 2019-02-14T09:19:16.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564158 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:16.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (18,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,541177,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,564159,'N',0,246,'D','CreatedBy','Erstellt durch','Nutzer, der diesen Eintrag erstellt hat')
;

-- 2019-02-14T09:19:16.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564159 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:16.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (16,35,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,541177,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',564160,'N',0,607,'D','Updated','Aktualisiert','Datum, an dem dieser Eintrag aktualisiert wurde')
;

-- 2019-02-14T09:19:16.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564160 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:16.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (18,10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,541177,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,564161,'N',0,608,'D','UpdatedBy','Aktualisiert durch','Nutzer, der diesen Eintrag aktualisiert hat')
;

-- 2019-02-14T09:19:16.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564161 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:19:16.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name,Description) VALUES (20,1,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N',TO_TIMESTAMP('2019-02-14 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,541177,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',564162,'N',0,348,'D','IsActive','Aktiv','Der Eintrag ist im System aktiv')
;

-- 2019-02-14T09:19:16.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564162 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-14T09:21:05.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-02-14 09:21:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564148
;

-- 2019-02-14T09:21:12.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-02-14 09:21:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564152
;

-- 2019-02-14T09:21:18.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-02-14 09:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564154
;

-- 2019-02-14T09:21:22.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-02-14 09:21:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564155
;

-- 2019-02-14T09:21:31.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-02-14 09:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564149
;

-- 2019-02-14T09:21:43.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-02-14 09:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564156
;

-- 2019-02-14T09:21:48.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-02-14 09:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564157
;

-- 2019-02-14T09:36:06.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=15, FieldLength=7,Updated=TO_TIMESTAMP('2019-02-14 09:36:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564151
;

