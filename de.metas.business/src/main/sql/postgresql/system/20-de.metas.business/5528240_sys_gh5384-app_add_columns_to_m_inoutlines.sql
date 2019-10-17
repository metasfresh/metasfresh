-- 2019-07-26T16:29:42.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576949,0,'QtyDeliveredInPriceUOM_CatchWeight',TO_TIMESTAMP('2019-07-26 18:29:42','YYYY-MM-DD HH24:MI:SS'),100,'Tatsächlich gelieferte Menge in der Mengeneinheit des Preises.','D','Y','Catch Weight Menge in Preiseinheit','Catch Weight Menge in Preiseinheit',TO_TIMESTAMP('2019-07-26 18:29:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-26T16:29:42.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576949 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-26T16:29:52.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 18:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_DE'
;

-- 2019-07-26T16:29:52.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_DE') 
;

-- 2019-07-26T16:29:52.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576949,'de_DE') 
;

-- 2019-07-26T16:29:55.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-26 18:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_CH'
;

-- 2019-07-26T16:29:55.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_CH') 
;

-- 2019-07-26T16:30:54.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Actually delivered quantity in the price''s unit of measurement.', IsTranslated='Y', Name='Catchweight quantity in price-UOM', PrintName='Catchweight quantity in price-UOM',Updated=TO_TIMESTAMP('2019-07-26 18:30:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='en_US'
;

-- 2019-07-26T16:30:54.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'en_US') 
;

-- 2019-07-26T16:31:24.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=576949, ColumnName='QtyDeliveredInPriceUOM_CatchWeight', Description='Tatsächlich gelieferte Menge in der Mengeneinheit des Preises.', EntityType='D', Help=NULL, Name='Catch Weight Menge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-26 18:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568480
;

-- 2019-07-26T16:31:24.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Catch Weight Menge in Preiseinheit', Description='Tatsächlich gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Column_ID=568480
;

-- 2019-07-26T16:31:24.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576949) 
;

--ALTER TABLE M_InOutLine RENAME COLUMN QtyToInvoiceInPriceUOM_CatchWeight TO QtyDeliveredInPriceUOM_CatchWeight;

-- 2019-07-26T16:35:20.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN QtyDeliveredInPriceUOM_CatchWeight NUMERIC')
;

