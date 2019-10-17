-- 2019-07-30T09:26:15.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=576941
;

-- 2019-07-30T09:26:15.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=576941
;

-- 2019-07-30T09:32:35.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyToInvoiceInPriceUOM',Updated=TO_TIMESTAMP('2019-07-30 11:32:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529
;

-- 2019-07-30T09:32:35.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM', Name='Geliefert Nominal', Description='', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-30T09:32:35.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM', Name='Geliefert Nominal', Description='', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T09:32:35.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM', Name='Geliefert Nominal', Description='', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T09:33:12.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Name='Zu berechn. Menge In Preiseinheit', PrintName='Zu berechn. Menge In Preiseinheit',Updated=TO_TIMESTAMP('2019-07-30 11:33:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_CH'
;

-- 2019-07-30T09:33:12.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_CH') 
;

-- 2019-07-30T09:36:48.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.',Updated=TO_TIMESTAMP('2019-07-30 11:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_DE'
;

-- 2019-07-30T09:36:48.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_DE') 
;

-- 2019-07-30T09:36:48.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542529,'de_DE') 
;

-- 2019-07-30T09:36:48.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM', Name='Geliefert Nominal', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-30T09:36:48.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM', Name='Geliefert Nominal', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T09:36:48.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM', Name='Geliefert Nominal', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T09:36:48.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert Nominal', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542529)
;

-- 2019-07-30T09:36:48.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert Nominal', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-30T09:36:48.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert Nominal', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-30T09:36:48.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert Nominal', Description = 'Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-30T09:37:30.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zu berechn. Menge In Preiseinheit', PrintName='Zu berechn. Menge In Preiseinheit',Updated=TO_TIMESTAMP('2019-07-30 11:37:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_DE'
;

-- 2019-07-30T09:37:30.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_DE') 
;

-- 2019-07-30T09:37:30.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542529,'de_DE') 
;

-- 2019-07-30T09:37:30.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM', Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-30T09:37:30.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM', Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T09:37:30.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM', Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T09:37:30.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542529)
;

-- 2019-07-30T09:37:30.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zu berechn. Menge In Preiseinheit', Name='Zu berechn. Menge In Preiseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542529)
;

-- 2019-07-30T09:37:30.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-30T09:37:30.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu berechn. Menge In Preiseinheit', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', Help=NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-30T09:37:30.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu berechn. Menge In Preiseinheit', Description = 'Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-30T09:38:14.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoicable quantity in price UOM', PrintName='Invoicable quantity in price UOM',Updated=TO_TIMESTAMP('2019-07-30 11:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='en_US'
;

-- 2019-07-30T09:38:14.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'en_US') 
;

-- 2019-07-30T09:39:19.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576979,0,'QtyDeliveredInUOM_Nominal',TO_TIMESTAMP('2019-07-30 11:39:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geliefert Nominal','Geliefert Nominal',TO_TIMESTAMP('2019-07-30 11:39:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-30T09:39:19.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576979 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-30T09:39:23.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-30 11:39:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576979 AND AD_Language='de_CH'
;

-- 2019-07-30T09:39:23.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576979,'de_CH') 
;

-- 2019-07-30T09:39:25.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-30 11:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576979 AND AD_Language='de_DE'
;

-- 2019-07-30T09:39:25.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576979,'de_DE') 
;

-- 2019-07-30T09:39:25.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576979,'de_DE') 
;

-- 2019-07-30T09:39:37.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivered nominal', PrintName='Delivered nominal',Updated=TO_TIMESTAMP('2019-07-30 11:39:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576979 AND AD_Language='en_US'
;

-- 2019-07-30T09:39:37.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576979,'en_US') 
;

-- 2019-07-30T09:40:51.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568527,576979,0,29,540579,'QtyDeliveredInUOM_Nominal',TO_TIMESTAMP('2019-07-30 11:40:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geliefert Nominal',0,0,TO_TIMESTAMP('2019-07-30 11:40:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-30T09:40:51.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568527 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-30T09:40:51.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576979) 
;

-- 2019-07-30T09:40:53.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_InvoiceCandidate_InOutLine','ALTER TABLE public.C_InvoiceCandidate_InOutLine ADD COLUMN QtyDeliveredInUOM_Nominal NUMERIC')
;

-- 2019-07-30T09:41:03.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2019-07-30 11:41:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568522
;

-- 2019-07-30T09:41:05.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_InvoiceCandidate_InOutLine','ALTER TABLE public.C_InvoiceCandidate_InOutLine ADD COLUMN QtyDeliveredInUOM_Catch NUMERIC')
;

-- 2019-07-30T09:41:18.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2019-07-30 11:41:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568524
;

-- 2019-07-30T09:41:20.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_InvoiceCandidate_InOutLine','ALTER TABLE public.C_InvoiceCandidate_InOutLine ADD COLUMN QtyDeliveredInUOM_Override NUMERIC')
;

-- 2019-07-30T09:41:26.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2019-07-30 11:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568523
;

-- 2019-07-30T09:41:29.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_InvoiceCandidate_InOutLine','ALTER TABLE public.C_InvoiceCandidate_InOutLine ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2019-07-30T09:42:27.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=568527, Description=NULL, Name='Geliefert Nominal',Updated=TO_TIMESTAMP('2019-07-30 11:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582508
;

-- 2019-07-30T09:42:27.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576979) 
;

-- 2019-07-30T09:42:27.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582508
;

-- 2019-07-30T09:42:27.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582508)
;

-- 2019-07-30T09:42:33.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568525
;

-- 2019-07-30T09:42:33.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568525
;

-- 2019-07-30T09:50:02.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568528,542529,0,29,540270,'QtyToInvoiceInPriceUOM',TO_TIMESTAMP('2019-07-30 11:50:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Zu berechn. Menge In Preiseinheit',0,0,TO_TIMESTAMP('2019-07-30 11:50:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-30T09:50:02.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568528 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-30T09:50:02.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542529) 
;

-- 2019-07-30T09:50:18.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568528,582510,0,540279,TO_TIMESTAMP('2019-07-30 11:50:18','YYYY-MM-DD HH24:MI:SS'),100,'Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde, umgerechnet in die Einheit auf die sich der Preis bezieht.',10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Zu berechn. Menge In Preiseinheit',TO_TIMESTAMP('2019-07-30 11:50:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-30T09:50:18.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-30T09:50:18.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542529) 
;

-- 2019-07-30T09:50:18.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582510
;

-- 2019-07-30T09:50:18.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582510)
;

-- 2019-07-30T12:01:43.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554109
;

-- 2019-07-30T12:01:43.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554109
;

-- 2019-07-30T12:01:43.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554109
;

-- 2019-07-30T12:01:43.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554107
;

-- 2019-07-30T12:01:43.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554107
;

-- 2019-07-30T12:01:43.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554107
;

-- 2019-07-30T12:01:43.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554092
;

-- 2019-07-30T12:01:43.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554092
;

-- 2019-07-30T12:01:43.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554092
;

-- 2019-07-30T12:01:43.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554093
;

-- 2019-07-30T12:01:43.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554093
;

-- 2019-07-30T12:01:43.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554093
;

-- 2019-07-30T12:01:43.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554101
;

-- 2019-07-30T12:01:43.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554101
;

-- 2019-07-30T12:01:43.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554101
;

-- 2019-07-30T12:01:43.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554106
;

-- 2019-07-30T12:01:43.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554106
;

-- 2019-07-30T12:01:43.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554106
;

-- 2019-07-30T12:01:43.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554111
;

-- 2019-07-30T12:01:43.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554111
;

-- 2019-07-30T12:01:43.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554111
;

-- 2019-07-30T12:01:43.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554112
;

-- 2019-07-30T12:01:43.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554112
;

-- 2019-07-30T12:01:43.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554112
;

-- 2019-07-30T12:01:43.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554113
;

-- 2019-07-30T12:01:43.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554113
;

-- 2019-07-30T12:01:43.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554113
;

-- 2019-07-30T12:01:43.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554114
;

-- 2019-07-30T12:01:43.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554114
;

-- 2019-07-30T12:01:43.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554114
;

-- 2019-07-30T12:01:43.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554115
;

-- 2019-07-30T12:01:43.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554115
;

-- 2019-07-30T12:01:43.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554115
;

-- 2019-07-30T12:01:43.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554116
;

-- 2019-07-30T12:01:43.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554116
;

-- 2019-07-30T12:01:43.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554116
;

-- 2019-07-30T12:01:43.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554119
;

-- 2019-07-30T12:01:43.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554119
;

-- 2019-07-30T12:01:43.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554119
;

-- 2019-07-30T12:01:43.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554120
;

-- 2019-07-30T12:01:43.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554120
;

-- 2019-07-30T12:01:43.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554120
;

-- 2019-07-30T12:01:43.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554121
;

-- 2019-07-30T12:01:43.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554121
;

-- 2019-07-30T12:01:43.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554121
;

-- 2019-07-30T12:01:43.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554122
;

-- 2019-07-30T12:01:43.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554122
;

-- 2019-07-30T12:01:43.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554122
;

-- 2019-07-30T12:01:43.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554123
;

-- 2019-07-30T12:01:43.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554123
;

-- 2019-07-30T12:01:43.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554123
;

-- 2019-07-30T12:01:43.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554125
;

-- 2019-07-30T12:01:43.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554125
;

-- 2019-07-30T12:01:43.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554125
;

-- 2019-07-30T12:01:43.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554126
;

-- 2019-07-30T12:01:43.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554126
;

-- 2019-07-30T12:01:43.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554126
;

-- 2019-07-30T12:01:43.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554127
;

-- 2019-07-30T12:01:43.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554127
;

-- 2019-07-30T12:01:43.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554127
;

-- 2019-07-30T12:01:43.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554128
;

-- 2019-07-30T12:01:43.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554128
;

-- 2019-07-30T12:01:43.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554128
;

-- 2019-07-30T12:01:43.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554098
;

-- 2019-07-30T12:01:43.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554098
;

-- 2019-07-30T12:01:43.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554098
;

-- 2019-07-30T12:01:43.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554100
;

-- 2019-07-30T12:01:43.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554100
;

-- 2019-07-30T12:01:43.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554100
;

-- 2019-07-30T12:01:43.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554099
;

-- 2019-07-30T12:01:43.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554099
;

-- 2019-07-30T12:01:43.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554099
;

-- 2019-07-30T12:01:43.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554104
;

-- 2019-07-30T12:01:43.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554104
;

-- 2019-07-30T12:01:43.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554104
;

-- 2019-07-30T12:01:43.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554103
;

-- 2019-07-30T12:01:43.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554103
;

-- 2019-07-30T12:01:43.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554103
;

-- 2019-07-30T12:01:43.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554108
;

-- 2019-07-30T12:01:43.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554108
;

-- 2019-07-30T12:01:43.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554108
;

-- 2019-07-30T12:01:43.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554118
;

-- 2019-07-30T12:01:43.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554118
;

-- 2019-07-30T12:01:43.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554118
;

-- 2019-07-30T12:01:43.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554097
;

-- 2019-07-30T12:01:43.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554097
;

-- 2019-07-30T12:01:43.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554097
;

-- 2019-07-30T12:01:43.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554095
;

-- 2019-07-30T12:01:43.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554095
;

-- 2019-07-30T12:01:43.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554095
;

-- 2019-07-30T12:01:43.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554129
;

-- 2019-07-30T12:01:43.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554129
;

-- 2019-07-30T12:01:43.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554129
;

-- 2019-07-30T12:01:43.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554094
;

-- 2019-07-30T12:01:43.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554094
;

-- 2019-07-30T12:01:43.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554094
;

-- 2019-07-30T12:01:43.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554105
;

-- 2019-07-30T12:01:43.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554105
;

-- 2019-07-30T12:01:43.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554105
;

-- 2019-07-30T12:01:43.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554124
;

-- 2019-07-30T12:01:43.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554124
;

-- 2019-07-30T12:01:43.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554124
;

-- 2019-07-30T12:01:43.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554130
;

-- 2019-07-30T12:01:43.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554130
;

-- 2019-07-30T12:01:43.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554130
;

-- 2019-07-30T12:01:43.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554096
;

-- 2019-07-30T12:01:43.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554096
;

-- 2019-07-30T12:01:43.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554096
;

-- 2019-07-30T12:01:43.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554110
;

-- 2019-07-30T12:01:43.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554110
;

-- 2019-07-30T12:01:43.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554110
;

-- 2019-07-30T12:01:43.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554117
;

-- 2019-07-30T12:01:43.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554117
;

-- 2019-07-30T12:01:43.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554117
;

-- 2019-07-30T12:01:43.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554102
;

-- 2019-07-30T12:01:43.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554102
;

-- 2019-07-30T12:01:43.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554102
;

-- 2019-07-30T12:01:43.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=540585
;

-- 2019-07-30T12:01:43.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab WHERE AD_Tab_ID=540585
;

-- 2019-07-30T12:25:30.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.', Name='Faktor aus Ziel-Einheit', PrintName='Faktor aus Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_CH'
;

-- 2019-07-30T12:25:30.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_CH') 
;

-- 2019-07-30T12:25:35.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Faktor aus Ziel-Einheit', PrintName='Faktor aus Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_DE'
;

-- 2019-07-30T12:25:35.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_DE') 
;

-- 2019-07-30T12:25:35.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576693,'de_DE') 
;

-- 2019-07-30T12:25:35.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', Help=NULL WHERE AD_Element_ID=576693
;

-- 2019-07-30T12:25:35.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', Help=NULL, AD_Element_ID=576693 WHERE UPPER(ColumnName)='C_UOM_CONVERSION_MULTIPLYRATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T12:25:35.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', Help=NULL WHERE AD_Element_ID=576693 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T12:25:35.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Faktor aus Ziel-Einheit', Description='Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576693) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576693)
;

-- 2019-07-30T12:25:35.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Faktor aus Ziel-Einheit', Name='Faktor aus Ziel-Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576693)
;

-- 2019-07-30T12:25:35.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Faktor aus Ziel-Einheit', Description='Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:25:35.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Faktor aus Ziel-Einheit', Description='Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', Help=NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:25:35.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Faktor aus Ziel-Einheit', Description = 'Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:25:49.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Factor from Target-UOM', PrintName='Factor from Target-UOM',Updated=TO_TIMESTAMP('2019-07-30 14:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='en_US'
;

-- 2019-07-30T12:25:49.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'en_US') 
;

-- 2019-07-30T12:25:59.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.',Updated=TO_TIMESTAMP('2019-07-30 14:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_DE'
;

-- 2019-07-30T12:25:59.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_DE') 
;

-- 2019-07-30T12:25:59.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576693,'de_DE') 
;

-- 2019-07-30T12:25:59.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID=576693
;

-- 2019-07-30T12:25:59.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.', Help=NULL, AD_Element_ID=576693 WHERE UPPER(ColumnName)='C_UOM_CONVERSION_MULTIPLYRATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T12:25:59.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID=576693 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T12:25:59.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576693) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576693)
;

-- 2019-07-30T12:26:00.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:26:00.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:26:00.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Faktor aus Ziel-Einheit', Description = 'Faktor zum Umrechnen aus der Ziel-Maßeinheit. Z.B. für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:26:25.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Factor to convert from target-UOM. For example, for UOM minute and target-UOM hour, the factor is 1/60.',Updated=TO_TIMESTAMP('2019-07-30 14:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='en_US'
;

-- 2019-07-30T12:26:25.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'en_US') 
;

-- 2019-07-30T12:27:36.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Faktor aus Ziel-Einheit', PrintName='Faktor aus Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:27:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='nl_NL'
;

-- 2019-07-30T12:27:36.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'nl_NL') 
;

-- 2019-07-30T12:35:02.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576980,0,'C_UOM_Conversion_DivideRate',TO_TIMESTAMP('2019-07-30 14:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Faktor zum Umrechnen in die Ziel-Maßeinheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.','U','Y','Faktor in Ziel-Einheit','Faktor in Ziel-Einheit',TO_TIMESTAMP('2019-07-30 14:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-30T12:35:02.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576980 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-30T12:35:16.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-30 14:35:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576980 AND AD_Language='de_CH'
;

-- 2019-07-30T12:35:16.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576980,'de_CH') 
;

-- 2019-07-30T12:35:21.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-30 14:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576980 AND AD_Language='de_DE'
;

-- 2019-07-30T12:35:21.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576980,'de_DE') 
;

-- 2019-07-30T12:35:21.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576980,'de_DE') 
;

-- 2019-07-30T12:35:21.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Conversion_DivideRate', Name='Faktor in Ziel-Einheit', Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', Help=NULL WHERE AD_Element_ID=576980
;

-- 2019-07-30T12:35:21.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_DivideRate', Name='Faktor in Ziel-Einheit', Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', Help=NULL, AD_Element_ID=576980 WHERE UPPER(ColumnName)='C_UOM_CONVERSION_DIVIDERATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T12:35:21.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_DivideRate', Name='Faktor in Ziel-Einheit', Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', Help=NULL WHERE AD_Element_ID=576980 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T12:35:21.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Faktor in Ziel-Einheit', Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576980) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576980)
;

-- 2019-07-30T12:35:21.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Faktor in Ziel-Einheit', Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576980
;

-- 2019-07-30T12:35:21.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Faktor in Ziel-Einheit', Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', Help=NULL WHERE AD_Element_ID = 576980
;

-- 2019-07-30T12:35:21.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Faktor in Ziel-Einheit', Description = 'Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576980
;

-- 2019-07-30T12:36:14.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Factor to convert to target-UOM. For example, for UOM minute and target-UOM hour, the value is 60.', IsTranslated='Y', Name='Factor to Target-UOM', PrintName='Factor to Target-UOM',Updated=TO_TIMESTAMP('2019-07-30 14:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576980 AND AD_Language='en_US'
;

-- 2019-07-30T12:36:14.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576980,'en_US') 
;

-- 2019-07-30T12:36:45.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.',Updated=TO_TIMESTAMP('2019-07-30 14:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_DE'
;

-- 2019-07-30T12:36:45.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_DE') 
;

-- 2019-07-30T12:36:45.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576693,'de_DE') 
;

-- 2019-07-30T12:36:45.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID=576693
;

-- 2019-07-30T12:36:45.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL, AD_Element_ID=576693 WHERE UPPER(ColumnName)='C_UOM_CONVERSION_MULTIPLYRATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T12:36:45.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID=576693 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T12:36:45.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576693) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576693)
;

-- 2019-07-30T12:36:45.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:36:45.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Faktor aus Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:36:45.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Faktor aus Ziel-Einheit', Description = 'Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:36:49.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.',Updated=TO_TIMESTAMP('2019-07-30 14:36:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_CH'
;

-- 2019-07-30T12:36:49.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_CH') 
;

-- 2019-07-30T12:37:15.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576980, Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', Name='Faktor in Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:37:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=464
;

-- 2019-07-30T12:37:15.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576980) 
;

-- 2019-07-30T12:37:15.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=464
;

-- 2019-07-30T12:37:15.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(464)
;

-- 2019-07-30T12:37:50.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll.', Name='Ziel-Einheit', PrintName='Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='de_CH'
;

-- 2019-07-30T12:37:50.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'de_CH') 
;

-- 2019-07-30T12:38:03.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll.', Name='Ziel-Einheit', PrintName='Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:38:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='nl_NL'
;

-- 2019-07-30T12:38:03.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'nl_NL') 
;

-- 2019-07-30T12:38:13.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll', Name='Ziel-Einheit', PrintName='Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:38:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='de_DE'
;

-- 2019-07-30T12:38:13.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'de_DE') 
;

-- 2019-07-30T12:38:13.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(217,'de_DE') 
;

-- 2019-07-30T12:38:13.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_To_ID', Name='Ziel-Einheit', Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll', Help='' WHERE AD_Element_ID=217
;

-- 2019-07-30T12:38:13.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_To_ID', Name='Ziel-Einheit', Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll', Help='', AD_Element_ID=217 WHERE UPPER(ColumnName)='C_UOM_TO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T12:38:13.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_To_ID', Name='Ziel-Einheit', Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll', Help='' WHERE AD_Element_ID=217 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T12:38:13.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ziel-Einheit', Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=217) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 217)
;

-- 2019-07-30T12:38:13.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ziel-Einheit', Name='Ziel-Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=217)
;

-- 2019-07-30T12:38:13.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ziel-Einheit', Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll', Help='', CommitWarning = NULL WHERE AD_Element_ID = 217
;

-- 2019-07-30T12:38:13.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ziel-Einheit', Description='Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll', Help='' WHERE AD_Element_ID = 217
;

-- 2019-07-30T12:38:13.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ziel-Einheit', Description = 'Maßeinheit, zu der eine bestimmte Menge konvertiert werden soll', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 217
;

-- 2019-07-30T12:44:19.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576980, Description='Faktor zum Umrechnen in die Ziel-Einheit. Z.B. für Einheit Minute und Ziel-Einheit Stunde beträgt der Wert 60.', Name='Faktor in Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=57411
;

-- 2019-07-30T12:44:19.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576980) 
;

-- 2019-07-30T12:44:19.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=57411
;

-- 2019-07-30T12:44:19.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(57411)
;

-- 2019-07-30T12:46:26.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Faktor von Ziel-Einheit', PrintName='Faktor von Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_CH'
;

-- 2019-07-30T12:46:26.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_CH') 
;

-- 2019-07-30T12:46:29.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Faktor von Ziel-Einheit', PrintName='Faktor von Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_DE'
;

-- 2019-07-30T12:46:29.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_DE') 
;

-- 2019-07-30T12:46:29.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576693,'de_DE') 
;

-- 2019-07-30T12:46:29.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor von Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID=576693
;

-- 2019-07-30T12:46:29.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor von Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL, AD_Element_ID=576693 WHERE UPPER(ColumnName)='C_UOM_CONVERSION_MULTIPLYRATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T12:46:29.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Conversion_MultiplyRate', Name='Faktor von Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID=576693 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T12:46:29.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Faktor von Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576693) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576693)
;

-- 2019-07-30T12:46:29.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Faktor von Ziel-Einheit', Name='Faktor von Ziel-Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576693)
;

-- 2019-07-30T12:46:29.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Faktor von Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:46:29.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Faktor von Ziel-Einheit', Description='Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', Help=NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:46:29.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Faktor von Ziel-Einheit', Description = 'Faktor zum Umrechnen aus der Ziel-Einheit. Z.B. für Maßeinheit Minute und Ziel-Einheit Stunde beträgt der Faktor 1/60.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576693
;

-- 2019-07-30T12:46:36.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Faktor von Ziel-Einheit', PrintName='Faktor von Ziel-Einheit',Updated=TO_TIMESTAMP('2019-07-30 14:46:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='nl_NL'
;

-- 2019-07-30T12:46:36.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'nl_NL') 
;

-- 2019-07-30T15:53:51.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='M_Product',Updated=TO_TIMESTAMP('2019-07-30 17:53:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=140
;

-- 2019-07-30T16:03:40.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576982,0,'QtyDeliveredCatch',TO_TIMESTAMP('2019-07-30 18:03:40','YYYY-MM-DD HH24:MI:SS'),100,'Tatsächlich gelieferte Menge','D','Y','Geliefert Catch','Geliefert Catch',TO_TIMESTAMP('2019-07-30 18:03:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-30T16:03:40.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576982 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-30T16:03:45.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-30 18:03:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576982 AND AD_Language='de_CH'
;

-- 2019-07-30T16:03:45.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576982,'de_CH') 
;

-- 2019-07-30T16:03:47.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-30 18:03:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576982 AND AD_Language='de_DE'
;

-- 2019-07-30T16:03:47.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576982,'de_DE') 
;

-- 2019-07-30T16:03:47.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576982,'de_DE') 
;

-- 2019-07-30T16:04:03.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Actually delviered Menge', IsTranslated='Y', Name='Delviered catch', PrintName='Delviered catch',Updated=TO_TIMESTAMP('2019-07-30 18:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576982 AND AD_Language='en_US'
;

-- 2019-07-30T16:04:03.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576982,'en_US') 
;

-- 2019-07-30T16:04:18.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=576982, ColumnName='QtyDeliveredCatch', Description='Tatsächlich gelieferte Menge', Help=NULL, Name='Geliefert Catch',Updated=TO_TIMESTAMP('2019-07-30 18:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568480
;

-- 2019-07-30T16:04:18.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576982) 
;

-- 2019-07-30T16:04:18.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN QtyDeliveredCatch NUMERIC')
;

-- 2019-07-30T16:04:27.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inoutline','Catch_UOM_ID','NUMERIC(10)',null,null)
;

-- 2019-07-31T13:17:03.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-31 15:17:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-31 15:17:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540321,'N',568539,'N','N','N','N','N','N','N','N',0,0,576971,'de.metas.invoicecandidate','N','N','QtyInvoicedInUOM','N','Abgerechnet')
;

-- 2019-07-31T13:17:03.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568539 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-31T13:17:03.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576971) 
;

-- 2019-07-31T13:17:04.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Line_Alloc','ALTER TABLE public.C_Invoice_Line_Alloc ADD COLUMN QtyInvoicedInUOM NUMERIC')
;

-- 2019-07-31T13:17:16.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-31 15:17:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-31 15:17:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540321,'N','Eine eindeutige (nicht monetäre) Maßeinheit',568540,'N','N','N','N','N','N','N','N',0,0,215,'de.metas.invoicecandidate','N','N','C_UOM_ID','N','Maßeinheit','Maßeinheit')
;

-- 2019-07-31T13:17:16.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568540 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-31T13:17:16.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- 2019-07-31T13:17:17.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Line_Alloc','ALTER TABLE public.C_Invoice_Line_Alloc ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2019-07-31T13:17:17.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice_Line_Alloc ADD CONSTRAINT CUOM_CInvoiceLineAlloc FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

--SELECT public.db_alter_table('C_Invoice_Candidate', 'ALTER TABLE C_Invoice_Candidate RENAME COLUMN QtyToInvoice TO QtyToInvoiceInUOM');


-- 2019-07-31T14:32:03.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abzurechnen eff. (Lagereinheit)', PrintName='Abzurechnen eff. (Lagereinheit)',Updated=TO_TIMESTAMP('2019-07-31 16:32:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='de_CH'
;

-- 2019-07-31T14:32:03.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'de_CH') 
;

-- 2019-07-31T14:32:10.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abzurechnen eff. (Lagereinheit)', PrintName='Abzurechnen eff. (Lagereinheit)',Updated=TO_TIMESTAMP('2019-07-31 16:32:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='de_DE'
;

-- 2019-07-31T14:32:10.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'de_DE') 
;

-- 2019-07-31T14:32:10.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1251,'de_DE') 
;

-- 2019-07-31T14:32:10.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoice', Name='Abzurechnen eff. (Lagereinheit)', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL WHERE AD_Element_ID=1251
;

-- 2019-07-31T14:32:10.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoice', Name='Abzurechnen eff. (Lagereinheit)', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL, AD_Element_ID=1251 WHERE UPPER(ColumnName)='QTYTOINVOICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-31T14:32:10.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoice', Name='Abzurechnen eff. (Lagereinheit)', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL WHERE AD_Element_ID=1251 AND IsCentrallyMaintained='Y'
;

-- 2019-07-31T14:32:10.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abzurechnen eff. (Lagereinheit)', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1251) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1251)
;

-- 2019-07-31T14:32:11.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abzurechnen eff. (Lagereinheit)', Name='Abzurechnen eff. (Lagereinheit)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1251)
;

-- 2019-07-31T14:32:11.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abzurechnen eff. (Lagereinheit)', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 1251
;

-- 2019-07-31T14:32:11.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abzurechnen eff. (Lagereinheit)', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL WHERE AD_Element_ID = 1251
;

-- 2019-07-31T14:32:11.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abzurechnen eff. (Lagereinheit)', Description = 'Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1251
;

-- 2019-07-31T14:32:40.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ordered (stock unit)', PrintName='Ordered (stock unit)',Updated=TO_TIMESTAMP('2019-07-31 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576974 AND AD_Language='en_US'
;

-- 2019-07-31T14:32:40.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576974,'en_US') 
;

-- 2019-07-31T14:33:42.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576983,0,'QtyToInvoiceInUOM',TO_TIMESTAMP('2019-07-31 16:33:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abzurechnen eff.','Abzurechnen eff.',TO_TIMESTAMP('2019-07-31 16:33:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-31T14:33:42.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576983 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-31T14:33:47.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-31 16:33:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576983 AND AD_Language='de_CH'
;

-- 2019-07-31T14:33:47.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576983,'de_CH') 
;

-- 2019-07-31T14:33:49.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-31 16:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576983 AND AD_Language='de_DE'
;

-- 2019-07-31T14:33:49.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576983,'de_DE') 
;

-- 2019-07-31T14:33:49.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576983,'de_DE') 
;

-- 2019-07-31T14:34:06.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='To invoice eff.', PrintName='To invoice eff.',Updated=TO_TIMESTAMP('2019-07-31 16:34:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576983 AND AD_Language='en_US'
;

-- 2019-07-31T14:34:06.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576983,'en_US') 
;

-- 2019-07-31T14:34:19.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-31 16:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-31 16:34:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540270,'N',568541,'N','N','N','N','N','N','N','N',0,0,576983,'de.metas.invoicecandidate','N','N','QtyToInvoiceInUOM','N','Abzurechnen eff.')
;

-- 2019-07-31T14:34:19.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568541 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-31T14:34:19.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576983) 
;

-- 2019-07-31T14:34:22.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyToInvoiceInUOM NUMERIC')
;

-- 2019-07-31T19:00:00.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-31 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-31 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N',472,'N','Eine eindeutige (nicht monetäre) Maßeinheit',568542,'N','N','N','N','N','N','N','N',0,0,215,'D','N','N','C_UOM_ID','N','Maßeinheit','Maßeinheit')
;

-- 2019-07-31T19:00:00.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568542 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-31T19:00:00.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- 2019-07-31T19:00:01.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2019-07-31T19:00:01.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_MatchInv ADD CONSTRAINT CUOM_MMatchInv FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2019-07-31T19:01:49.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576984,0,'QtyInUOM',TO_TIMESTAMP('2019-07-31 21:01:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Menge in Maßeinheit','Menge in Maßeinheit',TO_TIMESTAMP('2019-07-31 21:01:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-31T19:01:49.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576984 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-31T19:01:53.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-31 21:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576984 AND AD_Language='de_CH'
;

-- 2019-07-31T19:01:53.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576984,'de_CH') 
;

-- 2019-07-31T19:01:56.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-31 21:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576984 AND AD_Language='de_DE'
;

-- 2019-07-31T19:01:56.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576984,'de_DE') 
;

-- 2019-07-31T19:01:56.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576984,'de_DE') 
;

-- 2019-07-31T19:02:05.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Quantity in UOM', PrintName='Quantity in UOM',Updated=TO_TIMESTAMP('2019-07-31 21:02:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576984 AND AD_Language='en_US'
;

-- 2019-07-31T19:02:05.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576984,'en_US') 
;

-- 2019-07-31T19:02:18.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-31 21:02:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-31 21:02:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N',472,'N',568543,'N','N','N','N','N','N','N','N',0,0,576984,'D','N','N','QtyInUOM','N','Menge in Maßeinheit')
;

-- 2019-07-31T19:02:18.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568543 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-31T19:02:18.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576984) 
;

-- 2019-07-31T19:02:19.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN QtyInUOM NUMERIC')
;

