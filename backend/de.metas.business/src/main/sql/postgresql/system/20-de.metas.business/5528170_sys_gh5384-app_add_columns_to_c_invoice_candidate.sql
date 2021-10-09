-- 2019-07-26T12:09:21.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyToInvoiceInPriceUOM_Eff NUMERIC')
;

-- 2019-07-26T12:09:34.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','QtyToInvoiceInPriceUOM_CatchWeight','NUMERIC',null,null)
;

-- 2019-07-26T12:10:31.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','QtyToInvoiceInPriceUOM_Nominal','NUMERIC',null,null)
;

-- 2019-07-26T13:33:19.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576947,0,'IsCatchWeight',TO_TIMESTAMP('2019-07-26 15:33:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Catch Weight Abrechnung','Catch Weight Abrechnung',TO_TIMESTAMP('2019-07-26 15:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-26T13:33:19.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576947 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-26T13:37:45.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=576947
;

-- 2019-07-26T13:37:45.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=576947
;

-- moved to 5528169_sys_gh5384-app_add_important_AD_Elements.sql
-- -- 2019-07-26T14:01:01.715Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576948,0,'QtyInPriceUomVia',TO_TIMESTAMP('2019-07-26 16:01:01','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.','D','Y','Menge in Preiseinheit durch','Menge in Preiseinheit durch',TO_TIMESTAMP('2019-07-26 16:01:01','YYYY-MM-DD HH24:MI:SS'),100)
-- ;
--
-- -- 2019-07-26T14:01:01.716Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576948 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
-- ;

-- 2019-07-26T14:01:14.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyInPriceUOMVia',Updated=TO_TIMESTAMP('2019-07-26 16:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948
;

-- 2019-07-26T14:01:14.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyInPriceUOMVia', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL WHERE AD_Element_ID=576948
;

-- 2019-07-26T14:01:14.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInPriceUOMVia', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL, AD_Element_ID=576948 WHERE UPPER(ColumnName)='QTYINPRICEUOMVIA' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T14:01:14.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInPriceUOMVia', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL WHERE AD_Element_ID=576948 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T14:01:20Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 16:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='de_CH'
;

-- 2019-07-26T14:01:20.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'de_CH') 
;

-- 2019-07-26T14:01:23.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 16:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='de_DE'
;

-- 2019-07-26T14:01:23.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'de_DE') 
;

-- 2019-07-26T14:01:23.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576948,'de_DE') 
;

-- 2019-07-26T14:01:54.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Quantity in price UOM per', PrintName='Quantity in price UOM per',Updated=TO_TIMESTAMP('2019-07-26 16:01:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='en_US'
;

-- 2019-07-26T14:01:54.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'en_US') 
;

-- moved to 5528169a_sys_gh5384-app_add_important_AD_References.sql
-- -- 2019-07-26T14:02:43.591Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541023,TO_TIMESTAMP('2019-07-26 16:02:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','QtyInPriceUOMVia',TO_TIMESTAMP('2019-07-26 16:02:43','YYYY-MM-DD HH24:MI:SS'),100,'L')
-- ;
--
-- -- 2019-07-26T14:02:43.593Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541023 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
-- ;
--
-- -- 2019-07-26T14:03:14.706Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541023,541992,TO_TIMESTAMP('2019-07-26 16:03:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nominal',TO_TIMESTAMP('2019-07-26 16:03:14','YYYY-MM-DD HH24:MI:SS'),100,'Nominal','Nominal')
-- ;
--
-- -- 2019-07-26T14:03:14.707Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541992 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
-- ;

-- 2019-07-26T14:06:24.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Der Mengenbetrag wird über das Nominalgewicht d.h. einen Durchschnittswert-basierten Maßeinheiten-Umrechnungsfaktor ermittelt.', Name='Nominalgewicht',Updated=TO_TIMESTAMP('2019-07-26 16:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541992
;

-- 2019-07-26T14:06:32.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der Mengenbetrag wird über das Nominalgewicht d.h. einen Durchschnittswert-basierten Maßeinheiten-Umrechnungsfaktor ermittelt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 16:06:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541992
;

-- 2019-07-26T14:06:49.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 16:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541992
;

-- 2019-07-26T14:06:53.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2019-07-26 16:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541992
;
-- moved to 5528169a_sys_gh5384-app_add_important_AD_References.sql
-- -- 2019-07-26T14:08:25.637Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541023,541993,TO_TIMESTAMP('2019-07-26 16:08:25','YYYY-MM-DD HH24:MI:SS'),100,'Der Mengenbetrag wird aus den Summen der tatsächlich gelieferten Güter ermittelt.','D','Y','Catch Weight',TO_TIMESTAMP('2019-07-26 16:08:25','YYYY-MM-DD HH24:MI:SS'),100,'CatchWeight','CatchWeight')
-- ;
--
-- -- 2019-07-26T14:08:25.638Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541993 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
-- ;

-- 2019-07-26T14:08:34.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='', IsTranslated='Y', Name='Catch-Weight',Updated=TO_TIMESTAMP('2019-07-26 16:08:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541993
;

-- 2019-07-26T14:08:43.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 16:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541993
;

-- 2019-07-26T14:10:01.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (17,'Nominal',11,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-26 16:10:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-26 16:10:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540270,'N',541023,568478,'N','N','N','N','N','N','N','N',0,0,576948,'de.metas.invoicecandidate','N','N','QtyInPriceUOMVia','N','Menge in Preiseinheit durch','Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.')
;

-- 2019-07-26T14:10:01.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568478 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-26T14:10:01.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;

-- 2019-07-26T14:10:07.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-07-26 16:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568478
;

-- 2019-07-26T14:10:10.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyInPriceUOMVia VARCHAR(11) DEFAULT ''Nominal'' NOT NULL')
;

-- 2019-07-26T15:22:12.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-26 17:22:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-26 17:22:12','YYYY-MM-DD HH24:MI:SS'),100,'N','N',320,'N',114,568479,'N','N','N','N','N','N','N','N',0,0,542464,'de.metas.swat','N','N','Price_UOM_ID','N','Preiseinheit')
;

-- 2019-07-26T15:22:12.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568479 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-26T15:22:12.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542464) 
;

-- 2019-07-26T15:23:10.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-07-26 17:23:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-07-26 17:23:10','YYYY-MM-DD HH24:MI:SS'),100,'N','N',320,'N',568480,'N','N','N','N','N','N','N','N',0,0,576941,'de.metas.invoicecandidate','N','N','QtyToInvoiceInPriceUOM_CatchWeight','N','Catchweight-Menge in Preiseinheit','Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.')
;

-- 2019-07-26T15:23:10.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568480 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-26T15:23:10.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576941) 
;

-- 2019-07-26T15:23:12.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--will get a different name
--/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN QtyToInvoiceInPriceUOM_CatchWeight NUMERIC')
--;


-- 2019-07-26T15:23:32.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Catch Weight Menge in Preiseinheit', PrintName='Catch Weight Menge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 17:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_CH'
;

-- 2019-07-26T15:23:32.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_CH') 
;

-- 2019-07-26T15:23:51.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Catch Weight Menge in Preiseinheit', PrintName='Catch Weight Menge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 17:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_DE'
;

-- 2019-07-26T15:23:51.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_DE') 
;

-- 2019-07-26T15:23:51.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576941,'de_DE') 
;

-- 2019-07-26T15:23:51.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_CatchWeight', Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941
;

-- 2019-07-26T15:23:51.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_CatchWeight', Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=576941 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_CATCHWEIGHT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-26T15:23:51.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_CatchWeight', Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941 AND IsCentrallyMaintained='Y'
;

-- 2019-07-26T15:23:51.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576941) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576941)
;

-- 2019-07-26T15:23:51.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Catch Weight Menge in Preiseinheit', Name='Catch Weight Menge in Preiseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576941)
;

-- 2019-07-26T15:23:51.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-26T15:23:51.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-26T15:23:51.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Catch Weight Menge in Preiseinheit', Description = 'Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-26T15:24:42.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO t_alter_column values('m_inoutline','QtyToInvoiceInPriceUOM_CatchWeight','NUMERIC',null,null)
--;

-- 2019-07-26T15:24:50.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN Price_UOM_ID NUMERIC(10)')
;

-- 2019-07-26T15:24:50.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_InOutLine ADD CONSTRAINT PriceUOM_MInOutLine FOREIGN KEY (Price_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

