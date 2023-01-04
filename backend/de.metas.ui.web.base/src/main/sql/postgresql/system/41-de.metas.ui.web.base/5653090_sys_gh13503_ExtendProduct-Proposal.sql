-- Value: WEBUI_ProductsProposal_OfferHistory
-- Classname: de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_OfferHistory
-- 2022-08-22T17:01:25.743Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585102,'Y','de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_OfferHistory','N',TO_TIMESTAMP('2022-08-22 20:01:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Angebotshistorie','json','N','N','xls','Java',TO_TIMESTAMP('2022-08-22 20:01:25','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_ProductsProposal_OfferHistory')
;

-- 2022-08-22T17:01:25.750Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585102 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_ProductsProposal_OfferHistory(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_OfferHistory)
-- 2022-08-22T17:01:37.204Z
UPDATE AD_Process_Trl SET Name='Offer history',Updated=TO_TIMESTAMP('2022-08-22 20:01:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585102
;

-- 2022-08-23T12:03:20.117Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581366,0,'OfferDate',TO_TIMESTAMP('2022-08-23 15:03:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','OfferDate','OfferDate',TO_TIMESTAMP('2022-08-23 15:03:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T12:03:20.129Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581366 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: OfferDate
-- 2022-08-23T12:03:28.562Z
UPDATE AD_Element_Trl SET Name='Angebotsdatum', PrintName='Angebotsdatum',Updated=TO_TIMESTAMP('2022-08-23 15:03:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581366 AND AD_Language='de_CH'
;

-- 2022-08-23T12:03:28.612Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581366,'de_CH') 
;

-- Element: OfferDate
-- 2022-08-23T12:03:31.091Z
UPDATE AD_Element_Trl SET Name='Angebotsdatum', PrintName='Angebotsdatum',Updated=TO_TIMESTAMP('2022-08-23 15:03:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581366 AND AD_Language='de_DE'
;

-- 2022-08-23T12:03:31.093Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581366,'de_DE') 
;

-- 2022-08-23T12:03:31.117Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581366,'de_DE') 
;

-- 2022-08-23T12:03:31.123Z
UPDATE AD_Column SET ColumnName='OfferDate', Name='Angebotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=581366
;

-- 2022-08-23T12:03:31.124Z
UPDATE AD_Process_Para SET ColumnName='OfferDate', Name='Angebotsdatum', Description=NULL, Help=NULL, AD_Element_ID=581366 WHERE UPPER(ColumnName)='OFFERDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-23T12:03:31.125Z
UPDATE AD_Process_Para SET ColumnName='OfferDate', Name='Angebotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=581366 AND IsCentrallyMaintained='Y'
;

-- 2022-08-23T12:03:31.127Z
UPDATE AD_Field SET Name='Angebotsdatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581366) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581366)
;

-- 2022-08-23T12:03:31.153Z
UPDATE AD_PrintFormatItem pi SET PrintName='Angebotsdatum', Name='Angebotsdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581366)
;

-- 2022-08-23T12:03:31.155Z
UPDATE AD_Tab SET Name='Angebotsdatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581366
;

-- 2022-08-23T12:03:31.157Z
UPDATE AD_WINDOW SET Name='Angebotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 581366
;

-- 2022-08-23T12:03:31.158Z
UPDATE AD_Menu SET   Name = 'Angebotsdatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581366
;

-- Element: OfferDate
-- 2022-08-23T12:03:39.524Z
UPDATE AD_Element_Trl SET Name='Offer Date', PrintName='Offer Date',Updated=TO_TIMESTAMP('2022-08-23 15:03:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581366 AND AD_Language='en_US'
;

-- 2022-08-23T12:03:39.525Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581366,'en_US') 
;

-- Element: OfferDate
-- 2022-08-23T12:03:41.696Z
UPDATE AD_Element_Trl SET Name='Angebotsdatum', PrintName='Angebotsdatum',Updated=TO_TIMESTAMP('2022-08-23 15:03:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581366 AND AD_Language='nl_NL'
;

-- 2022-08-23T12:03:41.698Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581366,'nl_NL') 
;

-- Value: WEBUI_ProductsProposal_QuotationHistory
-- Classname: de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_OfferHistory
-- 2022-08-23T16:04:21.858Z
UPDATE AD_Process SET Value='WEBUI_ProductsProposal_QuotationHistory',Updated=TO_TIMESTAMP('2022-08-23 19:04:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585102
;

-- Value: WEBUI_ProductsProposal_QuotationHistory
-- Classname: de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory
-- 2022-08-23T16:04:44.758Z
UPDATE AD_Process SET Classname='de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory',Updated=TO_TIMESTAMP('2022-08-23 19:04:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585102
;

-- Value: WEBUI_ProductsProposal_ZoomToQuotations
-- Classname: de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations
-- 2022-08-23T16:08:13.144Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585103,'Y','de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations','N',TO_TIMESTAMP('2022-08-23 19:08:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Zoom zum Angebot','json','N','N','xls','Java',TO_TIMESTAMP('2022-08-23 19:08:12','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_ProductsProposal_ZoomToQuotations')
;

-- 2022-08-23T16:08:13.150Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585103 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_ProductsProposal_ZoomToQuotations(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations)
-- 2022-08-23T16:08:36.749Z
UPDATE AD_Process_Trl SET Name='Zoom To Quotation',Updated=TO_TIMESTAMP('2022-08-23 19:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585103
;

-- Process: WEBUI_ProductsProposal_QuotationHistory(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory)
-- 2022-08-23T18:34:36.503Z
UPDATE AD_Process_Trl SET Name='Quotation History',Updated=TO_TIMESTAMP('2022-08-23 21:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585102
;

-- 2022-08-24T08:45:27.162Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581367,0,'TermsOfDelivery',TO_TIMESTAMP('2022-08-24 11:45:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Terms of delivery','Terms of delivery',TO_TIMESTAMP('2022-08-24 11:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T08:45:27.174Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581367 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TermsOfDelivery
-- 2022-08-24T08:45:38.836Z
UPDATE AD_Element_Trl SET Name='Lieferbedingungen', PrintName='Lieferbedingungen',Updated=TO_TIMESTAMP('2022-08-24 11:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581367 AND AD_Language='de_CH'
;

-- 2022-08-24T08:45:38.884Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581367,'de_CH') 
;

-- Element: TermsOfDelivery
-- 2022-08-24T08:45:41.020Z
UPDATE AD_Element_Trl SET Name='Lieferbedingungen', PrintName='Lieferbedingungen',Updated=TO_TIMESTAMP('2022-08-24 11:45:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581367 AND AD_Language='de_DE'
;

-- 2022-08-24T08:45:41.023Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581367,'de_DE') 
;

-- 2022-08-24T08:45:41.044Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581367,'de_DE') 
;

-- 2022-08-24T08:45:41.047Z
UPDATE AD_Column SET ColumnName='TermsOfDelivery', Name='Lieferbedingungen', Description=NULL, Help=NULL WHERE AD_Element_ID=581367
;

-- 2022-08-24T08:45:41.048Z
UPDATE AD_Process_Para SET ColumnName='TermsOfDelivery', Name='Lieferbedingungen', Description=NULL, Help=NULL, AD_Element_ID=581367 WHERE UPPER(ColumnName)='TERMSOFDELIVERY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-24T08:45:41.050Z
UPDATE AD_Process_Para SET ColumnName='TermsOfDelivery', Name='Lieferbedingungen', Description=NULL, Help=NULL WHERE AD_Element_ID=581367 AND IsCentrallyMaintained='Y'
;

-- 2022-08-24T08:45:41.050Z
UPDATE AD_Field SET Name='Lieferbedingungen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581367) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581367)
;

-- 2022-08-24T08:45:41.076Z
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferbedingungen', Name='Lieferbedingungen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581367)
;

-- 2022-08-24T08:45:41.079Z
UPDATE AD_Tab SET Name='Lieferbedingungen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581367
;

-- 2022-08-24T08:45:41.080Z
UPDATE AD_WINDOW SET Name='Lieferbedingungen', Description=NULL, Help=NULL WHERE AD_Element_ID = 581367
;

-- 2022-08-24T08:45:41.081Z
UPDATE AD_Menu SET   Name = 'Lieferbedingungen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581367
;

-- Element: TermsOfDelivery
-- 2022-08-24T08:45:45.342Z
UPDATE AD_Element_Trl SET Name='Lieferbedingungen', PrintName='Lieferbedingungen',Updated=TO_TIMESTAMP('2022-08-24 11:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581367 AND AD_Language='nl_NL'
;

-- 2022-08-24T08:45:45.344Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581367,'nl_NL') 
;

-- 2022-08-24T09:17:22.546Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581368,0,'LastQuotationDate',TO_TIMESTAMP('2022-08-24 12:17:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Last Quotation Date','Last Quotation Date',TO_TIMESTAMP('2022-08-24 12:17:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T09:17:22.557Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581368 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LastQuotationDate
-- 2022-08-24T09:17:38.396Z
UPDATE AD_Element_Trl SET Name='Letztes Angebotsdatum', PrintName='Letztes Angebotsdatum',Updated=TO_TIMESTAMP('2022-08-24 12:17:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581368 AND AD_Language='de_CH'
;

-- 2022-08-24T09:17:38.398Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581368,'de_CH') 
;

-- Element: LastQuotationDate
-- 2022-08-24T09:17:40.635Z
UPDATE AD_Element_Trl SET Name='Letztes Angebotsdatum', PrintName='Letztes Angebotsdatum',Updated=TO_TIMESTAMP('2022-08-24 12:17:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581368 AND AD_Language='de_DE'
;

-- 2022-08-24T09:17:40.637Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581368,'de_DE') 
;

-- 2022-08-24T09:17:40.686Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581368,'de_DE') 
;

-- 2022-08-24T09:17:40.687Z
UPDATE AD_Column SET ColumnName='LastQuotationDate', Name='Letztes Angebotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=581368
;

-- 2022-08-24T09:17:40.688Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationDate', Name='Letztes Angebotsdatum', Description=NULL, Help=NULL, AD_Element_ID=581368 WHERE UPPER(ColumnName)='LASTQUOTATIONDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-24T09:17:40.689Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationDate', Name='Letztes Angebotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=581368 AND IsCentrallyMaintained='Y'
;

-- 2022-08-24T09:17:40.690Z
UPDATE AD_Field SET Name='Letztes Angebotsdatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581368) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581368)
;

-- 2022-08-24T09:17:40.704Z
UPDATE AD_PrintFormatItem pi SET PrintName='Letztes Angebotsdatum', Name='Letztes Angebotsdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581368)
;

-- 2022-08-24T09:17:40.705Z
UPDATE AD_Tab SET Name='Letztes Angebotsdatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581368
;

-- 2022-08-24T09:17:40.706Z
UPDATE AD_WINDOW SET Name='Letztes Angebotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 581368
;

-- 2022-08-24T09:17:40.707Z
UPDATE AD_Menu SET   Name = 'Letztes Angebotsdatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581368
;

-- Element: LastQuotationDate
-- 2022-08-24T09:17:43.880Z
UPDATE AD_Element_Trl SET Name='Letztes Angebotsdatum', PrintName='Letztes Angebotsdatum',Updated=TO_TIMESTAMP('2022-08-24 12:17:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581368 AND AD_Language='nl_NL'
;

-- 2022-08-24T09:17:43.882Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581368,'nl_NL') 
;

-- 2022-08-24T09:21:34.166Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581369,0,'LastQuotationPrice',TO_TIMESTAMP('2022-08-24 12:21:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Last Quotation Price','Last Quotation Price',TO_TIMESTAMP('2022-08-24 12:21:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T09:21:34.166Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581369 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LastQuotationPrice
-- 2022-08-24T09:21:44.655Z
UPDATE AD_Element_Trl SET Name='Letzter Angebotspreis', PrintName='Letzter Angebotspreis',Updated=TO_TIMESTAMP('2022-08-24 12:21:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581369 AND AD_Language='de_CH'
;

-- 2022-08-24T09:21:44.656Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581369,'de_CH') 
;

-- Element: LastQuotationPrice
-- 2022-08-24T09:21:47.310Z
UPDATE AD_Element_Trl SET Name='Letzter Angebotspreis', PrintName='Letzter Angebotspreis',Updated=TO_TIMESTAMP('2022-08-24 12:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581369 AND AD_Language='de_DE'
;

-- 2022-08-24T09:21:47.312Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581369,'de_DE') 
;

-- 2022-08-24T09:21:47.320Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581369,'de_DE') 
;

-- 2022-08-24T09:21:47.321Z
UPDATE AD_Column SET ColumnName='LastQuotationPrice', Name='Letzter Angebotspreis', Description=NULL, Help=NULL WHERE AD_Element_ID=581369
;

-- 2022-08-24T09:21:47.322Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationPrice', Name='Letzter Angebotspreis', Description=NULL, Help=NULL, AD_Element_ID=581369 WHERE UPPER(ColumnName)='LASTQUOTATIONPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-24T09:21:47.323Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationPrice', Name='Letzter Angebotspreis', Description=NULL, Help=NULL WHERE AD_Element_ID=581369 AND IsCentrallyMaintained='Y'
;

-- 2022-08-24T09:21:47.323Z
UPDATE AD_Field SET Name='Letzter Angebotspreis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581369) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581369)
;

-- 2022-08-24T09:21:47.341Z
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter Angebotspreis', Name='Letzter Angebotspreis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581369)
;

-- 2022-08-24T09:21:47.343Z
UPDATE AD_Tab SET Name='Letzter Angebotspreis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581369
;

-- 2022-08-24T09:21:47.345Z
UPDATE AD_WINDOW SET Name='Letzter Angebotspreis', Description=NULL, Help=NULL WHERE AD_Element_ID = 581369
;

-- 2022-08-24T09:21:47.346Z
UPDATE AD_Menu SET   Name = 'Letzter Angebotspreis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581369
;

-- Element: LastQuotationPrice
-- 2022-08-24T09:21:50.352Z
UPDATE AD_Element_Trl SET Name='Letzter Angebotspreis', PrintName='Letzter Angebotspreis',Updated=TO_TIMESTAMP('2022-08-24 12:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581369 AND AD_Language='nl_NL'
;

-- 2022-08-24T09:21:50.354Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581369,'nl_NL') 
;

-- 2022-08-24T10:30:00.073Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581372,0,'QuotationOrdered',TO_TIMESTAMP('2022-08-24 13:29:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Quotation Ordered','Quotation Ordered',TO_TIMESTAMP('2022-08-24 13:29:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T10:30:00.083Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581372 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QuotationOrdered
-- 2022-08-24T10:30:11.151Z
UPDATE AD_Element_Trl SET Name='Angebot Bestellt', PrintName='Angebot Bestellt',Updated=TO_TIMESTAMP('2022-08-24 13:30:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581372 AND AD_Language='de_CH'
;

-- 2022-08-24T10:30:11.154Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581372,'de_CH') 
;

-- Element: QuotationOrdered
-- 2022-08-24T10:30:13.678Z
UPDATE AD_Element_Trl SET Name='Angebot Bestellt', PrintName='Angebot Bestellt',Updated=TO_TIMESTAMP('2022-08-24 13:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581372 AND AD_Language='de_DE'
;

-- 2022-08-24T10:30:13.683Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581372,'de_DE') 
;

-- 2022-08-24T10:30:13.731Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581372,'de_DE') 
;

-- 2022-08-24T10:30:13.732Z
UPDATE AD_Column SET ColumnName='QuotationOrdered', Name='Angebot Bestellt', Description=NULL, Help=NULL WHERE AD_Element_ID=581372
;

-- 2022-08-24T10:30:13.733Z
UPDATE AD_Process_Para SET ColumnName='QuotationOrdered', Name='Angebot Bestellt', Description=NULL, Help=NULL, AD_Element_ID=581372 WHERE UPPER(ColumnName)='QUOTATIONORDERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-24T10:30:13.734Z
UPDATE AD_Process_Para SET ColumnName='QuotationOrdered', Name='Angebot Bestellt', Description=NULL, Help=NULL WHERE AD_Element_ID=581372 AND IsCentrallyMaintained='Y'
;

-- 2022-08-24T10:30:13.734Z
UPDATE AD_Field SET Name='Angebot Bestellt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581372) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581372)
;

-- 2022-08-24T10:30:13.747Z
UPDATE AD_PrintFormatItem pi SET PrintName='Angebot Bestellt', Name='Angebot Bestellt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581372)
;

-- 2022-08-24T10:30:13.749Z
UPDATE AD_Tab SET Name='Angebot Bestellt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581372
;

-- 2022-08-24T10:30:13.750Z
UPDATE AD_WINDOW SET Name='Angebot Bestellt', Description=NULL, Help=NULL WHERE AD_Element_ID = 581372
;

-- 2022-08-24T10:30:13.750Z
UPDATE AD_Menu SET   Name = 'Angebot Bestellt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581372
;

-- Element: QuotationOrdered
-- 2022-08-24T10:30:17.804Z
UPDATE AD_Element_Trl SET Name='Angebot Bestellt', PrintName='Angebot Bestellt',Updated=TO_TIMESTAMP('2022-08-24 13:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581372 AND AD_Language='nl_NL'
;

-- 2022-08-24T10:30:17.805Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581372,'nl_NL') 
;

-- 2022-08-25T11:50:07.777Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541473,'S',TO_TIMESTAMP('2022-08-25 14:50:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.field.termsOfDelivery.IsDisplayed',TO_TIMESTAMP('2022-08-25 14:50:07','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2022-08-25T11:50:19.101Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541474,'S',TO_TIMESTAMP('2022-08-25 14:50:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.field.lastQuotationDate.IsDisplayed',TO_TIMESTAMP('2022-08-25 14:50:18','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2022-08-25T11:50:28.815Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541475,'S',TO_TIMESTAMP('2022-08-25 14:50:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.field.lastQuotationPrice.IsDisplayed',TO_TIMESTAMP('2022-08-25 14:50:28','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2022-08-25T11:50:39.319Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541476,'S',TO_TIMESTAMP('2022-08-25 14:50:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.field.lastQuotationUOM.IsDisplayed',TO_TIMESTAMP('2022-08-25 14:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2022-08-25T11:50:48.623Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541477,'S',TO_TIMESTAMP('2022-08-25 14:50:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.order.products_proposal.model.ProductsProposalRow.field.quotationOrdered.IsDisplayed',TO_TIMESTAMP('2022-08-25 14:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- Element: TermsOfDelivery
-- 2022-08-25T13:26:09.564Z
UPDATE AD_Element_Trl SET Name='Lieferkonditionen', PrintName='Lieferkonditionen',Updated=TO_TIMESTAMP('2022-08-25 16:26:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581367 AND AD_Language='de_CH'
;

-- 2022-08-25T13:26:09.607Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581367,'de_CH') 
;

-- Element: TermsOfDelivery
-- 2022-08-25T13:26:12.542Z
UPDATE AD_Element_Trl SET Name='Lieferkonditionen', PrintName='Lieferkonditionen',Updated=TO_TIMESTAMP('2022-08-25 16:26:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581367 AND AD_Language='de_DE'
;

-- 2022-08-25T13:26:12.544Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581367,'de_DE') 
;

-- 2022-08-25T13:26:12.553Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581367,'de_DE') 
;

-- 2022-08-25T13:26:12.560Z
UPDATE AD_Column SET ColumnName='TermsOfDelivery', Name='Lieferkonditionen', Description=NULL, Help=NULL WHERE AD_Element_ID=581367
;

-- 2022-08-25T13:26:12.561Z
UPDATE AD_Process_Para SET ColumnName='TermsOfDelivery', Name='Lieferkonditionen', Description=NULL, Help=NULL, AD_Element_ID=581367 WHERE UPPER(ColumnName)='TERMSOFDELIVERY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T13:26:12.562Z
UPDATE AD_Process_Para SET ColumnName='TermsOfDelivery', Name='Lieferkonditionen', Description=NULL, Help=NULL WHERE AD_Element_ID=581367 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T13:26:12.563Z
UPDATE AD_Field SET Name='Lieferkonditionen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581367) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581367)
;

-- 2022-08-25T13:26:12.584Z
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferkonditionen', Name='Lieferkonditionen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581367)
;

-- 2022-08-25T13:26:12.585Z
UPDATE AD_Tab SET Name='Lieferkonditionen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581367
;

-- 2022-08-25T13:26:12.586Z
UPDATE AD_WINDOW SET Name='Lieferkonditionen', Description=NULL, Help=NULL WHERE AD_Element_ID = 581367
;

-- 2022-08-25T13:26:12.586Z
UPDATE AD_Menu SET   Name = 'Lieferkonditionen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581367
;

-- Element: TermsOfDelivery
-- 2022-08-25T13:26:16.220Z
UPDATE AD_Element_Trl SET Name='Lieferkonditionen', PrintName='Lieferkonditionen',Updated=TO_TIMESTAMP('2022-08-25 16:26:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581367 AND AD_Language='nl_NL'
;

-- 2022-08-25T13:26:16.223Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581367,'nl_NL') 
;

-- Element: LastQuotationDate
-- 2022-08-25T13:27:02.193Z
UPDATE AD_Element_Trl SET Name='Letztes Anbegotsdatum', PrintName='Letztes Anbegotsdatum',Updated=TO_TIMESTAMP('2022-08-25 16:27:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581368 AND AD_Language='de_CH'
;

-- 2022-08-25T13:27:02.194Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581368,'de_CH') 
;

-- Element: LastQuotationDate
-- 2022-08-25T13:27:04.410Z
UPDATE AD_Element_Trl SET Name='Letztes Anbegotsdatum', PrintName='Letztes Anbegotsdatum',Updated=TO_TIMESTAMP('2022-08-25 16:27:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581368 AND AD_Language='de_DE'
;

-- 2022-08-25T13:27:04.411Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581368,'de_DE') 
;

-- 2022-08-25T13:27:04.419Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581368,'de_DE') 
;

-- 2022-08-25T13:27:04.424Z
UPDATE AD_Column SET ColumnName='LastQuotationDate', Name='Letztes Anbegotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=581368
;

-- 2022-08-25T13:27:04.426Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationDate', Name='Letztes Anbegotsdatum', Description=NULL, Help=NULL, AD_Element_ID=581368 WHERE UPPER(ColumnName)='LASTQUOTATIONDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T13:27:04.427Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationDate', Name='Letztes Anbegotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=581368 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T13:27:04.428Z
UPDATE AD_Field SET Name='Letztes Anbegotsdatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581368) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581368)
;

-- 2022-08-25T13:27:04.452Z
UPDATE AD_PrintFormatItem pi SET PrintName='Letztes Anbegotsdatum', Name='Letztes Anbegotsdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581368)
;

-- 2022-08-25T13:27:04.454Z
UPDATE AD_Tab SET Name='Letztes Anbegotsdatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581368
;

-- 2022-08-25T13:27:04.456Z
UPDATE AD_WINDOW SET Name='Letztes Anbegotsdatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 581368
;

-- 2022-08-25T13:27:04.458Z
UPDATE AD_Menu SET   Name = 'Letztes Anbegotsdatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581368
;

-- Element: LastQuotationDate
-- 2022-08-25T13:27:07.377Z
UPDATE AD_Element_Trl SET Name='Letztes Anbegotsdatum', PrintName='Letztes Anbegotsdatum',Updated=TO_TIMESTAMP('2022-08-25 16:27:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581368 AND AD_Language='nl_NL'
;

-- 2022-08-25T13:27:07.379Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581368,'nl_NL') 
;

-- Element: LastQuotationPrice
-- 2022-08-25T13:28:19.467Z
UPDATE AD_Element_Trl SET Name='Letzter Anbegotspreis', PrintName='Letzter Anbegotspreis',Updated=TO_TIMESTAMP('2022-08-25 16:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581369 AND AD_Language='de_CH'
;

-- 2022-08-25T13:28:19.469Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581369,'de_CH') 
;

-- Element: LastQuotationPrice
-- 2022-08-25T13:28:21.845Z
UPDATE AD_Element_Trl SET Name='Letzter Anbegotspreis', PrintName='Letzter Anbegotspreis',Updated=TO_TIMESTAMP('2022-08-25 16:28:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581369 AND AD_Language='de_DE'
;

-- 2022-08-25T13:28:21.846Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581369,'de_DE') 
;

-- 2022-08-25T13:28:21.855Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581369,'de_DE') 
;

-- 2022-08-25T13:28:21.856Z
UPDATE AD_Column SET ColumnName='LastQuotationPrice', Name='Letzter Anbegotspreis', Description=NULL, Help=NULL WHERE AD_Element_ID=581369
;

-- 2022-08-25T13:28:21.857Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationPrice', Name='Letzter Anbegotspreis', Description=NULL, Help=NULL, AD_Element_ID=581369 WHERE UPPER(ColumnName)='LASTQUOTATIONPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T13:28:21.858Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationPrice', Name='Letzter Anbegotspreis', Description=NULL, Help=NULL WHERE AD_Element_ID=581369 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T13:28:21.858Z
UPDATE AD_Field SET Name='Letzter Anbegotspreis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581369) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581369)
;

-- 2022-08-25T13:28:21.868Z
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter Anbegotspreis', Name='Letzter Anbegotspreis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581369)
;

-- 2022-08-25T13:28:21.869Z
UPDATE AD_Tab SET Name='Letzter Anbegotspreis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581369
;

-- 2022-08-25T13:28:21.870Z
UPDATE AD_WINDOW SET Name='Letzter Anbegotspreis', Description=NULL, Help=NULL WHERE AD_Element_ID = 581369
;

-- 2022-08-25T13:28:21.870Z
UPDATE AD_Menu SET   Name = 'Letzter Anbegotspreis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581369
;

-- Element: LastQuotationPrice
-- 2022-08-25T13:28:25.210Z
UPDATE AD_Element_Trl SET Name='Letzter Anbegotspreis', PrintName='Letzter Anbegotspreis',Updated=TO_TIMESTAMP('2022-08-25 16:28:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581369 AND AD_Language='nl_NL'
;

-- 2022-08-25T13:28:25.212Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581369,'nl_NL') 
;

-- Element: QuotationOrdered
-- 2022-08-25T13:29:45.625Z
UPDATE AD_Element_Trl SET Name='Auftrag erteilt', PrintName='Auftrag erteilt',Updated=TO_TIMESTAMP('2022-08-25 16:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581372 AND AD_Language='de_CH'
;

-- 2022-08-25T13:29:45.627Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581372,'de_CH') 
;

-- Element: QuotationOrdered
-- 2022-08-25T13:29:47.729Z
UPDATE AD_Element_Trl SET Name='Auftrag erteilt', PrintName='Auftrag erteilt',Updated=TO_TIMESTAMP('2022-08-25 16:29:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581372 AND AD_Language='de_DE'
;

-- 2022-08-25T13:29:47.730Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581372,'de_DE') 
;

-- 2022-08-25T13:29:47.736Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581372,'de_DE') 
;

-- 2022-08-25T13:29:47.739Z
UPDATE AD_Column SET ColumnName='QuotationOrdered', Name='Auftrag erteilt', Description=NULL, Help=NULL WHERE AD_Element_ID=581372
;

-- 2022-08-25T13:29:47.740Z
UPDATE AD_Process_Para SET ColumnName='QuotationOrdered', Name='Auftrag erteilt', Description=NULL, Help=NULL, AD_Element_ID=581372 WHERE UPPER(ColumnName)='QUOTATIONORDERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T13:29:47.740Z
UPDATE AD_Process_Para SET ColumnName='QuotationOrdered', Name='Auftrag erteilt', Description=NULL, Help=NULL WHERE AD_Element_ID=581372 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T13:29:47.741Z
UPDATE AD_Field SET Name='Auftrag erteilt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581372) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581372)
;

-- 2022-08-25T13:29:47.752Z
UPDATE AD_PrintFormatItem pi SET PrintName='Auftrag erteilt', Name='Auftrag erteilt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581372)
;

-- 2022-08-25T13:29:47.753Z
UPDATE AD_Tab SET Name='Auftrag erteilt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581372
;

-- 2022-08-25T13:29:47.754Z
UPDATE AD_WINDOW SET Name='Auftrag erteilt', Description=NULL, Help=NULL WHERE AD_Element_ID = 581372
;

-- 2022-08-25T13:29:47.755Z
UPDATE AD_Menu SET   Name = 'Auftrag erteilt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581372
;

-- Element: QuotationOrdered
-- 2022-08-25T13:29:50.760Z
UPDATE AD_Element_Trl SET Name='Auftrag erteilt', PrintName='Auftrag erteilt',Updated=TO_TIMESTAMP('2022-08-25 16:29:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581372 AND AD_Language='nl_NL'
;

-- 2022-08-25T13:29:50.761Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581372,'nl_NL') 
;

-- Process: WEBUI_ProductsProposal_QuotationHistory(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory)
-- 2022-08-25T13:32:16.090Z
UPDATE AD_Process_Trl SET Name='Angebots-Historie',Updated=TO_TIMESTAMP('2022-08-25 16:32:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585102
;

-- Value: WEBUI_ProductsProposal_QuotationHistory
-- Classname: de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory
-- 2022-08-25T13:32:19.213Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Angebots-Historie',Updated=TO_TIMESTAMP('2022-08-25 16:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585102
;

-- Process: WEBUI_ProductsProposal_QuotationHistory(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory)
-- 2022-08-25T13:32:19.169Z
UPDATE AD_Process_Trl SET Name='Angebots-Historie',Updated=TO_TIMESTAMP('2022-08-25 16:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585102
;

-- Process: WEBUI_ProductsProposal_QuotationHistory(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_QuotationHistory)
-- 2022-08-25T13:32:25.371Z
UPDATE AD_Process_Trl SET Name='Angebots-Historie',Updated=TO_TIMESTAMP('2022-08-25 16:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585102
;

-- Process: WEBUI_ProductsProposal_ZoomToQuotations(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations)
-- 2022-08-25T13:33:56.073Z
UPDATE AD_Process_Trl SET Name='Angebote',Updated=TO_TIMESTAMP('2022-08-25 16:33:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585103
;

-- Value: WEBUI_ProductsProposal_ZoomToQuotations
-- Classname: de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations
-- 2022-08-25T13:33:58.314Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Angebote',Updated=TO_TIMESTAMP('2022-08-25 16:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585103
;

-- Process: WEBUI_ProductsProposal_ZoomToQuotations(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations)
-- 2022-08-25T13:33:58.311Z
UPDATE AD_Process_Trl SET Name='Angebote',Updated=TO_TIMESTAMP('2022-08-25 16:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585103
;

-- Process: WEBUI_ProductsProposal_ZoomToQuotations(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations)
-- 2022-08-25T13:34:05.803Z
UPDATE AD_Process_Trl SET Name='Quotations',Updated=TO_TIMESTAMP('2022-08-25 16:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585103
;

-- Process: WEBUI_ProductsProposal_ZoomToQuotations(de.metas.ui.web.order.products_proposal.process.WEBUI_ProductsProposal_ZoomToQuotations)
-- 2022-08-25T13:34:07.715Z
UPDATE AD_Process_Trl SET Name='Angebote',Updated=TO_TIMESTAMP('2022-08-25 16:34:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585103
;

-- 2022-08-25T13:35:09.845Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581390,0,'LastQuotationUOM',TO_TIMESTAMP('2022-08-25 16:35:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Last Quotation UOM','Last Quotation UOM',TO_TIMESTAMP('2022-08-25 16:35:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-25T13:35:09.848Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581390 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LastQuotationUOM
-- 2022-08-25T13:35:22.394Z
UPDATE AD_Element_Trl SET Name='Letztes Angebot OUM', PrintName='Letztes Angebot OUM',Updated=TO_TIMESTAMP('2022-08-25 16:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581390 AND AD_Language='de_CH'
;

-- 2022-08-25T13:35:22.395Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581390,'de_CH') 
;

-- Element: LastQuotationUOM
-- 2022-08-25T13:35:25.451Z
UPDATE AD_Element_Trl SET Name='Letztes Angebot OUM', PrintName='Letztes Angebot OUM',Updated=TO_TIMESTAMP('2022-08-25 16:35:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581390 AND AD_Language='de_DE'
;

-- 2022-08-25T13:35:25.452Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581390,'de_DE') 
;

-- 2022-08-25T13:35:25.465Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581390,'de_DE') 
;

-- 2022-08-25T13:35:25.466Z
UPDATE AD_Column SET ColumnName='LastQuotationUOM', Name='Letztes Angebot OUM', Description=NULL, Help=NULL WHERE AD_Element_ID=581390
;

-- 2022-08-25T13:35:25.466Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationUOM', Name='Letztes Angebot OUM', Description=NULL, Help=NULL, AD_Element_ID=581390 WHERE UPPER(ColumnName)='LASTQUOTATIONUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-25T13:35:25.467Z
UPDATE AD_Process_Para SET ColumnName='LastQuotationUOM', Name='Letztes Angebot OUM', Description=NULL, Help=NULL WHERE AD_Element_ID=581390 AND IsCentrallyMaintained='Y'
;

-- 2022-08-25T13:35:25.467Z
UPDATE AD_Field SET Name='Letztes Angebot OUM', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581390) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581390)
;

-- 2022-08-25T13:35:25.477Z
UPDATE AD_PrintFormatItem pi SET PrintName='Letztes Angebot OUM', Name='Letztes Angebot OUM' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581390)
;

-- 2022-08-25T13:35:25.478Z
UPDATE AD_Tab SET Name='Letztes Angebot OUM', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581390
;

-- 2022-08-25T13:35:25.479Z
UPDATE AD_WINDOW SET Name='Letztes Angebot OUM', Description=NULL, Help=NULL WHERE AD_Element_ID = 581390
;

-- 2022-08-25T13:35:25.480Z
UPDATE AD_Menu SET   Name = 'Letztes Angebot OUM', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581390
;

-- Element: LastQuotationUOM
-- 2022-08-25T13:35:28.859Z
UPDATE AD_Element_Trl SET Name='Letztes Angebot OUM', PrintName='Letztes Angebot OUM',Updated=TO_TIMESTAMP('2022-08-25 16:35:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581390 AND AD_Language='nl_NL'
;

-- 2022-08-25T13:35:28.860Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581390,'nl_NL') 
;