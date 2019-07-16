-- 2019-07-15T16:02:56.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576910,0,'QtyToInvoiceInPriceUOM_Override',TO_TIMESTAMP('2019-07-15 16:02:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','QtyToInvoiceInPriceUOM_Override','QtyToInvoiceInPriceUOM_Override',TO_TIMESTAMP('2019-07-15 16:02:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-15T16:02:56.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576910 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-15T16:03:22.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zu ber. Menge in Preiseinheit (abw.)', PrintName='Zu ber. Menge in Preiseinheit (abw.)',Updated=TO_TIMESTAMP('2019-07-15 16:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576910 AND AD_Language='de_CH'
;

-- 2019-07-15T16:03:22.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576910,'de_CH') 
;

-- 2019-07-15T16:03:50.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zu ber. Menge in Preiseinheit (abw.)', PrintName='Zu ber. Menge in Preiseinheit (abw.)',Updated=TO_TIMESTAMP('2019-07-15 16:03:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576910 AND AD_Language='de_DE'
;

-- 2019-07-15T16:03:50.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576910,'de_DE') 
;

-- 2019-07-15T16:03:50.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576910,'de_DE') 
;

-- 2019-07-15T16:03:50.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Override', Name='Zu ber. Menge in Preiseinheit (abw.)', Description=NULL, Help=NULL WHERE AD_Element_ID=576910
;

-- 2019-07-15T16:03:50.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Override', Name='Zu ber. Menge in Preiseinheit (abw.)', Description=NULL, Help=NULL, AD_Element_ID=576910 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_OVERRIDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-15T16:03:50.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Override', Name='Zu ber. Menge in Preiseinheit (abw.)', Description=NULL, Help=NULL WHERE AD_Element_ID=576910 AND IsCentrallyMaintained='Y'
;

-- 2019-07-15T16:03:50.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu ber. Menge in Preiseinheit (abw.)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576910) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576910)
;

-- 2019-07-15T16:03:50.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zu ber. Menge in Preiseinheit (abw.)', Name='Zu ber. Menge in Preiseinheit (abw.)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576910)
;

-- 2019-07-15T16:03:50.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu ber. Menge in Preiseinheit (abw.)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576910
;

-- 2019-07-15T16:03:50.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu ber. Menge in Preiseinheit (abw.)', Description=NULL, Help=NULL WHERE AD_Element_ID = 576910
;

-- 2019-07-15T16:03:50.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu ber. Menge in Preiseinheit (abw.)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576910
;

-- 2019-07-15T16:05:53.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,EntityType) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-15 16:05:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-15 16:05:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540270,'N',568423,'N','N','N','N','N','N','N','N',0,0,576910,'N','N','QtyToInvoiceInPriceUOM_Override','N','Zu ber. Menge in Preiseinheit (abw.)','de.metas.invoicecandidate')
;

-- 2019-07-15T16:05:53.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568423 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-15T16:05:53.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576910) 
;

-- 2019-07-15T16:07:03.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyToInvoiceInPriceUOM_Override NUMERIC')
;


