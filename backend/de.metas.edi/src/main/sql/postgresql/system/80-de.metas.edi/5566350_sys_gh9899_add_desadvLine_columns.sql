-- 2020-09-04T08:07:46.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578051,0,'C_UOM_Invoice_ID',TO_TIMESTAMP('2020-09-04 10:07:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechnungs-Maßeinheit','Rechnungs-Maßeinheit',TO_TIMESTAMP('2020-09-04 10:07:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T08:07:46.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578051 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-04T08:07:53.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-04 10:07:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051 AND AD_Language='de_CH'
;

-- 2020-09-04T08:07:53.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578051,'de_CH') 
;

-- 2020-09-04T08:07:56.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-04 10:07:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051 AND AD_Language='de_DE'
;

-- 2020-09-04T08:07:56.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578051,'de_DE') 
;

-- 2020-09-04T08:07:56.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578051,'de_DE') 
;

-- 2020-09-04T08:08:11.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoicing-UOM', PrintName='Invoicing-UOM',Updated=TO_TIMESTAMP('2020-09-04 10:08:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051 AND AD_Language='en_US'
;

-- 2020-09-04T08:08:11.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578051,'en_US') 
;

-- 2020-09-04T08:08:52.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit in der die betreffende Zeile abgerechnet wird',Updated=TO_TIMESTAMP('2020-09-04 10:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051 AND AD_Language='de_DE'
;

-- 2020-09-04T08:08:52.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578051,'de_DE') 
;

-- 2020-09-04T08:08:52.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578051,'de_DE') 
;

-- 2020-09-04T08:08:52.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Invoice_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID=578051
;

-- 2020-09-04T08:08:52.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Invoice_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL, AD_Element_ID=578051 WHERE UPPER(ColumnName)='C_UOM_INVOICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-04T08:08:52.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Invoice_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID=578051 AND IsCentrallyMaintained='Y'
;

-- 2020-09-04T08:08:52.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578051) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578051)
;

-- 2020-09-04T08:08:52.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578051
;

-- 2020-09-04T08:08:52.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID = 578051
;

-- 2020-09-04T08:08:52.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungs-Maßeinheit', Description = 'Maßeinheit in der die betreffende Zeile abgerechnet wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578051
;

-- 2020-09-04T08:08:55.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit in der die betreffende Zeile abgerechnet wird',Updated=TO_TIMESTAMP('2020-09-04 10:08:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051 AND AD_Language='de_CH'
;

-- 2020-09-04T08:08:55.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578051,'de_CH') 
;

-- 2020-09-04T08:10:39.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_UOM_Invoicing_ID',Updated=TO_TIMESTAMP('2020-09-04 10:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051
;

-- 2020-09-04T08:10:39.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Invoicing_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID=578051
;

-- 2020-09-04T08:10:39.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Invoicing_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL, AD_Element_ID=578051 WHERE UPPER(ColumnName)='C_UOM_INVOICING_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-04T08:10:39.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Invoicing_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID=578051 AND IsCentrallyMaintained='Y'
;

-- 2020-09-04T08:11:02.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571145,578051,0,19,540645,'C_UOM_Invoicing_ID',TO_TIMESTAMP('2020-09-04 10:11:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit in der die betreffende Zeile abgerechnet wird','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungs-Maßeinheit',0,0,TO_TIMESTAMP('2020-09-04 10:11:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-04T08:11:02.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571145 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-04T08:11:02.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578051) 
;

-- 2020-09-04T08:11:22.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=114,Updated=TO_TIMESTAMP('2020-09-04 10:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571145
;

-- 2020-09-04T09:19:49.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_UOM_Invoice_ID',Updated=TO_TIMESTAMP('2020-09-04 11:19:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051
;

-- 2020-09-04T09:19:49.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Invoice_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID=578051
;

-- 2020-09-04T09:19:49.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Invoice_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL, AD_Element_ID=578051 WHERE UPPER(ColumnName)='C_UOM_INVOICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-04T09:19:49.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Invoice_ID', Name='Rechnungs-Maßeinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID=578051 AND IsCentrallyMaintained='Y'
;


-- 2020-09-04T09:29:59.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrechnungseinheit', PrintName='Abrechnungseinheit',Updated=TO_TIMESTAMP('2020-09-04 11:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051 AND AD_Language='de_CH'
;

-- 2020-09-04T09:29:59.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578051,'de_CH') 
;

-- 2020-09-04T09:30:04.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrechnungseinheit', PrintName='Abrechnungseinheit',Updated=TO_TIMESTAMP('2020-09-04 11:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578051 AND AD_Language='de_DE'
;

-- 2020-09-04T09:30:04.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578051,'de_DE') 
;

-- 2020-09-04T09:30:04.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578051,'de_DE') 
;

-- 2020-09-04T09:30:04.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Invoice_ID', Name='Abrechnungseinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID=578051
;

-- 2020-09-04T09:30:04.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Invoice_ID', Name='Abrechnungseinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL, AD_Element_ID=578051 WHERE UPPER(ColumnName)='C_UOM_INVOICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-04T09:30:04.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Invoice_ID', Name='Abrechnungseinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID=578051 AND IsCentrallyMaintained='Y'
;

-- 2020-09-04T09:30:04.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abrechnungseinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578051) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578051)
;

-- 2020-09-04T09:30:04.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abrechnungseinheit', Name='Abrechnungseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578051)
;

-- 2020-09-04T09:30:04.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abrechnungseinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578051
;

-- 2020-09-04T09:30:04.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abrechnungseinheit', Description='Maßeinheit in der die betreffende Zeile abgerechnet wird', Help=NULL WHERE AD_Element_ID = 578051
;

-- 2020-09-04T09:30:04.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abrechnungseinheit', Description = 'Maßeinheit in der die betreffende Zeile abgerechnet wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578051
;

-- 2020-09-04T09:30:27.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578052,0,'QtyDeliveredInInvoiceUOM',TO_TIMESTAMP('2020-09-04 11:30:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Liefermenge (Abrechnungseinheit)','Liefermenge (Abrechnungseinheit)',TO_TIMESTAMP('2020-09-04 11:30:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T09:30:27.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578052 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-04T09:30:32.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-04 11:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578052 AND AD_Language='de_CH'
;

-- 2020-09-04T09:30:32.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578052,'de_CH') 
;

-- 2020-09-04T09:30:34.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-04 11:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578052 AND AD_Language='de_DE'
;

-- 2020-09-04T09:30:34.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578052,'de_DE') 
;

-- 2020-09-04T09:30:34.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578052,'de_DE') 
;

-- 2020-09-04T09:31:33.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delviered (invoicing UOM)', PrintName='Delviered (invoicing UOM)',Updated=TO_TIMESTAMP('2020-09-04 11:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578052 AND AD_Language='en_US'
;

-- 2020-09-04T09:31:33.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578052,'en_US') 
;

-- 2020-09-04T09:33:24.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571146,578052,0,29,540645,'QtyDeliveredInInvoiceUOM',TO_TIMESTAMP('2020-09-04 11:33:24','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Liefermenge (Abrechnungseinheit)',0,0,TO_TIMESTAMP('2020-09-04 11:33:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-04T09:33:24.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571146 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-04T09:33:24.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578052) 
;


-- 2020-09-04T09:34:45.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578053,0,'EanCom_Invoice_UOM',TO_TIMESTAMP('2020-09-04 11:34:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EanCom_Invoice_UOM','EanCom_Invoice_UOM',TO_TIMESTAMP('2020-09-04 11:34:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T09:34:45.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578053 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-04T09:37:09.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571147,578053,0,10,540645,'EanCom_Invoice_UOM','(select x12de355 from C_UOM where C_UOM_C_UOM_ID=EDI_DesadvLine.C_UOM_Invoice_ID)',TO_TIMESTAMP('2020-09-04 11:37:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'EanCom_Invoice_UOM',0,0,TO_TIMESTAMP('2020-09-04 11:37:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-04T09:37:09.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571147 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-04T09:37:09.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578053) 
;

-- 2020-09-04T09:55:10.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline','QtyDeliveredInInvoiceUOM','NUMERIC',null,null)
;

-- 2020-09-04T09:55:40.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571148,542872,0,10,540645,'OrderPOReference',TO_TIMESTAMP('2020-09-04 11:55:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsreferenz',0,0,TO_TIMESTAMP('2020-09-04 11:55:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-04T09:55:40.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571148 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-04T09:55:40.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542872) 
;

-- 2020-09-04T10:01:38.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2020-09-04 12:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552627
;


-- 2020-09-04T10:02:08.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571149,561,0,11,540645,'C_OrderLine_ID',TO_TIMESTAMP('2020-09-04 12:02:08','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Auftragsposition','de.metas.esb.edi',0,10,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Auftragsposition',0,0,TO_TIMESTAMP('2020-09-04 12:02:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-04T10:02:08.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571149 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-04T10:02:08.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(561) 
;

-- 2020-09-04T10:02:09.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN C_OrderLine_ID NUMERIC(10) DEFAULT 0 NOT NULL')
--;

-- 2020-09-04T10:03:15.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571149,0,TO_TIMESTAMP('2020-09-04 12:03:14','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','de.metas.esb.edi',540406,550299,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','N','Y','Auftragsposition',360,'E',TO_TIMESTAMP('2020-09-04 12:03:14','YYYY-MM-DD HH24:MI:SS'),100,'C_OrderLine_ID')
--;

-- 2020-09-04T10:03:15.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571145,0,TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit in der die betreffende Zeile abgerechnet wird','de.metas.esb.edi',540406,550300,'N','N','Abrechnungseinheit',370,'R',TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'C_UOM_Invoice_ID')
;

-- 2020-09-04T10:03:15.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571147,0,TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550301,'N','N','EanCom_Invoice_UOM',380,'E',TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'EanCom_Invoice_UOM')
;

-- 2020-09-04T10:03:15.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569824,0,TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',540406,550302,'N','Y','Abr. Menge basiert auf',390,'E',TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'InvoicableQtyBasedOn')
;

-- 2020-09-04T10:03:15.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571148,0,TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550303,'N','N','Auftragsreferenz',400,'E',TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'OrderPOReference')
;

-- 2020-09-04T10:03:15.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571146,0,TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550304,'N','N','Liefermenge (Abrechnungseinheit)',410,'E',TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'QtyDeliveredInInvoiceUOM')
;

-- 2020-09-04T10:03:15.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569829,0,TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)','de.metas.esb.edi',540406,550305,'N','N','Liefermenge',420,'E',TO_TIMESTAMP('2020-09-04 12:03:15','YYYY-MM-DD HH24:MI:SS'),100,'QtyDeliveredInUOM')
;

-- 2020-09-04T10:06:05.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- was missing only in my DB
--/* DDL */ SELECT public.db_alter_table('EXP_FormatLine','ALTER TABLE public.EXP_FormatLine ADD COLUMN DateFormat VARCHAR(40)')
--;

-- 2020-09-04T10:06:44.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=128,Updated=TO_TIMESTAMP('2020-09-04 12:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54523
;


-- 2020-09-04T10:07:12.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-09-04 12:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550304
;

-- 2020-09-04T10:07:45.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-09-04 12:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550303
;

-- 2020-09-04T10:08:07.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-09-04 12:08:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550301
;

-- 2020-09-04T10:09:11.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571150,542252,0,11,540645,'OrderLine',TO_TIMESTAMP('2020-09-04 12:09:11','YYYY-MM-DD HH24:MI:SS'),100,'N','0','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Auftragszeile',0,0,TO_TIMESTAMP('2020-09-04 12:09:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-04T10:09:11.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571150 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-04T10:09:11.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542252) 
;

-- 2020-09-04T10:09:18.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2020-09-04 12:09:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571150
;


-- 2020-09-04T10:12:24.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571150,0,TO_TIMESTAMP('2020-09-04 12:12:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550306,'N','N','Auftragszeile',430,'E',TO_TIMESTAMP('2020-09-04 12:12:24','YYYY-MM-DD HH24:MI:SS'),100,'OrderLine')
;

-- 2020-09-04T10:12:33.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2020-09-04 12:12:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550306
;

-- 2020-09-04T11:05:19.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='EdiDesadvRecipientGLN',Updated=TO_TIMESTAMP('2020-09-04 13:05:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542001
;

-- 2020-09-04T11:05:19.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EdiDesadvRecipientGLN', Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL WHERE AD_Element_ID=542001
;

-- 2020-09-04T11:05:19.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EdiDesadvRecipientGLN', Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL, AD_Element_ID=542001 WHERE UPPER(ColumnName)='EDIDESADVRECIPIENTGLN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-04T11:05:19.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EdiDesadvRecipientGLN', Name='EDI-ID des DESADV-Empfängers', Description='', Help=NULL WHERE AD_Element_ID=542001 AND IsCentrallyMaintained='Y'
;

-- 2020-09-04T11:14:18.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578054,0,'EdiInvoicRecipientGLN',TO_TIMESTAMP('2020-09-04 13:14:17','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.esb.edi','Y','EDI-ID des INVOIC-Empfängers','EDI-ID des INVOIC-Empfängers',TO_TIMESTAMP('2020-09-04 13:14:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T11:14:18.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578054 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-09-04T11:14:36.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-04 13:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542001 AND AD_Language='de_DE'
;

-- 2020-09-04T11:14:36.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542001,'de_DE') 
;

-- 2020-09-04T11:14:36.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542001,'de_DE') 
;

-- 2020-09-04T11:15:07.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-04 13:15:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578054 AND AD_Language='de_DE'
;

-- 2020-09-04T11:15:07.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578054,'de_DE') 
;

-- 2020-09-04T11:15:07.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578054,'de_DE') 
;

-- 2020-09-04T11:15:10.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-04 13:15:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578054 AND AD_Language='de_CH'
;

-- 2020-09-04T11:15:10.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578054,'de_CH') 
;

-- 2020-09-04T11:15:57.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571151,578054,0,10,291,'EdiInvoicRecipientGLN',TO_TIMESTAMP('2020-09-04 13:15:57','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.esb.edi',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'EDI-ID des INVOIC-Empfängers',0,0,TO_TIMESTAMP('2020-09-04 13:15:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-04T11:15:57.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571151 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-04T11:15:57.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578054) 
;


-- 2020-09-04T11:17:07.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571151,616964,0,220,0,TO_TIMESTAMP('2020-09-04 13:17:07','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.esb.edi',0,'Y','N','N','N','N','N','N','N','EDI-ID des INVOIC-Empfängers',390,380,0,1,1,TO_TIMESTAMP('2020-09-04 13:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T11:17:07.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=616964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-04T11:17:07.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578054) 
;

-- 2020-09-04T11:17:07.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616964
;

-- 2020-09-04T11:17:07.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(616964)
;

-- 2020-09-04T11:19:28.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571145,616965,0,540663,TO_TIMESTAMP('2020-09-04 13:19:28','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit in der die betreffende Zeile abgerechnet wird',10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Abrechnungseinheit',TO_TIMESTAMP('2020-09-04 13:19:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T11:19:28.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=616965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-04T11:19:28.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578051) 
;

-- 2020-09-04T11:19:28.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616965
;

-- 2020-09-04T11:19:28.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(616965)
;

-- 2020-09-04T11:19:28.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571146,616966,0,540663,TO_TIMESTAMP('2020-09-04 13:19:28','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Liefermenge (Abrechnungseinheit)',TO_TIMESTAMP('2020-09-04 13:19:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T11:19:28.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=616966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-04T11:19:28.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578052) 
;

-- 2020-09-04T11:19:28.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616966
;

-- 2020-09-04T11:19:28.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(616966)
;

-- 2020-09-04T11:19:29.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571147,616967,0,540663,TO_TIMESTAMP('2020-09-04 13:19:28','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EanCom_Invoice_UOM',TO_TIMESTAMP('2020-09-04 13:19:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T11:19:29.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=616967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-04T11:19:29.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578053) 
;

-- 2020-09-04T11:19:29.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616967
;

-- 2020-09-04T11:19:29.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(616967)
;

-- 2020-09-04T11:19:29.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571148,616968,0,540663,TO_TIMESTAMP('2020-09-04 13:19:29','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Auftragsreferenz',TO_TIMESTAMP('2020-09-04 13:19:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T11:19:29.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=616968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-04T11:19:29.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542872) 
;

-- 2020-09-04T11:19:29.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616968
;

-- 2020-09-04T11:19:29.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(616968)
;

-- 2020-09-04T11:19:29.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571149,616969,0,540663,TO_TIMESTAMP('2020-09-04 13:19:29','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition',10,'de.metas.esb.edi','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2020-09-04 13:19:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T11:19:29.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=616969 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-04T11:19:29.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2020-09-04T11:19:29.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616969
;

-- 2020-09-04T11:19:29.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(616969)
;

-- 2020-09-04T11:19:29.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571150,616970,0,540663,TO_TIMESTAMP('2020-09-04 13:19:29','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Auftragszeile',TO_TIMESTAMP('2020-09-04 13:19:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-04T11:19:29.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=616970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-04T11:19:29.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542252) 
;

-- 2020-09-04T11:19:29.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616970
;

-- 2020-09-04T11:19:29.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(616970)
;

