-- 2021-05-07T10:55:03.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579139,0,'FreightCost_NormalVAT_Rates',TO_TIMESTAMP('2021-05-07 13:55:02','YYYY-MM-DD HH24:MI:SS'),100,'Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"','de.metas.externalsystem','Y','Normal VAT rates','Normal VAT rates',TO_TIMESTAMP('2021-05-07 13:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T10:55:03.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579139 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-07T10:55:17.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Normale MWSt. Sätze', PrintName='Normale MWSt. Sätze',Updated=TO_TIMESTAMP('2021-05-07 13:55:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579139 AND AD_Language='de_CH'
;

-- 2021-05-07T10:55:17.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579139,'de_CH') 
;

-- 2021-05-07T10:55:21.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Normale MWSt. Sätze', PrintName='Normale MWSt. Sätze',Updated=TO_TIMESTAMP('2021-05-07 13:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579139 AND AD_Language='de_DE'
;

-- 2021-05-07T10:55:21.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579139,'de_DE') 
;

-- 2021-05-07T10:55:21.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579139,'de_DE') 
;

-- 2021-05-07T10:55:21.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FreightCost_NormalVAT_Rates', Name='Normale MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID=579139
;

-- 2021-05-07T10:55:21.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FreightCost_NormalVAT_Rates', Name='Normale MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL, AD_Element_ID=579139 WHERE UPPER(ColumnName)='FREIGHTCOST_NORMALVAT_RATES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T10:55:21.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FreightCost_NormalVAT_Rates', Name='Normale MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID=579139 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T10:55:21.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Normale MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579139) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579139)
;

-- 2021-05-07T10:55:21.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Normale MWSt. Sätze', Name='Normale MWSt. Sätze' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579139)
;

-- 2021-05-07T10:55:21.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Normale MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579139
;

-- 2021-05-07T10:55:21.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Normale MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID = 579139
;

-- 2021-05-07T10:55:21.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Normale MWSt. Sätze', Description = 'Comma delimited list of freightcost tax rates for which the "Freight cost product (normal VAT)" shall be used. Example: "0, 7.7, 19"', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579139
;

-- 2021-05-07T10:55:34.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"', Name='Normale MWSt. Sätze', PrintName='Normale MWSt. Sätze',Updated=TO_TIMESTAMP('2021-05-07 13:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579139 AND AD_Language='nl_NL'
;

-- 2021-05-07T10:55:34.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579139,'nl_NL') 
;

-- 2021-05-07T10:55:40.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"',Updated=TO_TIMESTAMP('2021-05-07 13:55:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579139 AND AD_Language='de_DE'
;

-- 2021-05-07T10:55:40.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579139,'de_DE') 
;

-- 2021-05-07T10:55:40.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579139,'de_DE') 
;

-- 2021-05-07T10:55:40.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FreightCost_NormalVAT_Rates', Name='Normale MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID=579139
;

-- 2021-05-07T10:55:40.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FreightCost_NormalVAT_Rates', Name='Normale MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"', Help=NULL, AD_Element_ID=579139 WHERE UPPER(ColumnName)='FREIGHTCOST_NORMALVAT_RATES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T10:55:40.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FreightCost_NormalVAT_Rates', Name='Normale MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID=579139 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T10:55:40.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Normale MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579139) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579139)
;

-- 2021-05-07T10:55:40.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Normale MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579139
;

-- 2021-05-07T10:55:40.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Normale MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID = 579139
;

-- 2021-05-07T10:55:40.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Normale MWSt. Sätze', Description = 'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579139
;

-- 2021-05-07T10:55:43.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"',Updated=TO_TIMESTAMP('2021-05-07 13:55:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579139 AND AD_Language='de_CH'
;

-- 2021-05-07T10:55:43.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579139,'de_CH') 
;

-- 2021-05-07T10:56:25.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579140,0,'M_FreightCost_NormalVAT_Product_ID',TO_TIMESTAMP('2021-05-07 13:56:25','YYYY-MM-DD HH24:MI:SS'),100,'If a shopware order''s freight costs have the normal VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.','de.metas.externalsystem','Y','Freight cost product (normal VAT)','Freight cost product (normal VAT)',TO_TIMESTAMP('2021-05-07 13:56:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T10:56:25.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579140 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-07T10:56:40.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Versandkostenprodukt (Normale MWSt.)', PrintName='Versandkostenprodukt (Normale MWSt.)',Updated=TO_TIMESTAMP('2021-05-07 13:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579140 AND AD_Language='de_CH'
;

-- 2021-05-07T10:56:40.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579140,'de_CH') 
;

-- 2021-05-07T10:56:43.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Versandkostenprodukt (Normale MWSt.)', PrintName='Versandkostenprodukt (Normale MWSt.)',Updated=TO_TIMESTAMP('2021-05-07 13:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579140 AND AD_Language='de_DE'
;

-- 2021-05-07T10:56:43.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579140,'de_DE') 
;

-- 2021-05-07T10:56:43.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579140,'de_DE') 
;

-- 2021-05-07T10:56:43.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_FreightCost_NormalVAT_Product_ID', Name='Versandkostenprodukt (Normale MWSt.)', Description='If a shopware order''s freight costs have the normal VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL WHERE AD_Element_ID=579140
;

-- 2021-05-07T10:56:43.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_FreightCost_NormalVAT_Product_ID', Name='Versandkostenprodukt (Normale MWSt.)', Description='If a shopware order''s freight costs have the normal VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL, AD_Element_ID=579140 WHERE UPPER(ColumnName)='M_FREIGHTCOST_NORMALVAT_PRODUCT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T10:56:43.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_FreightCost_NormalVAT_Product_ID', Name='Versandkostenprodukt (Normale MWSt.)', Description='If a shopware order''s freight costs have the normal VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL WHERE AD_Element_ID=579140 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T10:56:43.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Versandkostenprodukt (Normale MWSt.)', Description='If a shopware order''s freight costs have the normal VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579140) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579140)
;

-- 2021-05-07T10:56:43.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Versandkostenprodukt (Normale MWSt.)', Name='Versandkostenprodukt (Normale MWSt.)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579140)
;

-- 2021-05-07T10:56:44.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Versandkostenprodukt (Normale MWSt.)', Description='If a shopware order''s freight costs have the normal VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579140
;

-- 2021-05-07T10:56:44.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Versandkostenprodukt (Normale MWSt.)', Description='If a shopware order''s freight costs have the normal VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL WHERE AD_Element_ID = 579140
;

-- 2021-05-07T10:56:44.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Versandkostenprodukt (Normale MWSt.)', Description = 'If a shopware order''s freight costs have the normal VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579140
;

-- 2021-05-07T10:56:48.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Versandkostenprodukt (Normale MWSt.)', PrintName='Versandkostenprodukt (Normale MWSt.)',Updated=TO_TIMESTAMP('2021-05-07 13:56:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579140 AND AD_Language='nl_NL'
;

-- 2021-05-07T10:56:48.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579140,'nl_NL') 
;

-- 2021-05-07T10:57:17.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',Updated=TO_TIMESTAMP('2021-05-07 13:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579140 AND AD_Language='nl_NL'
;

-- 2021-05-07T10:57:17.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579140,'nl_NL') 
;

-- 2021-05-07T10:57:21.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',Updated=TO_TIMESTAMP('2021-05-07 13:57:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579140 AND AD_Language='de_DE'
;

-- 2021-05-07T10:57:21.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579140,'de_DE') 
;

-- 2021-05-07T10:57:21.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579140,'de_DE') 
;

-- 2021-05-07T10:57:21.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_FreightCost_NormalVAT_Product_ID', Name='Versandkostenprodukt (Normale MWSt.)', Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL WHERE AD_Element_ID=579140
;

-- 2021-05-07T10:57:21.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_FreightCost_NormalVAT_Product_ID', Name='Versandkostenprodukt (Normale MWSt.)', Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL, AD_Element_ID=579140 WHERE UPPER(ColumnName)='M_FREIGHTCOST_NORMALVAT_PRODUCT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T10:57:21.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_FreightCost_NormalVAT_Product_ID', Name='Versandkostenprodukt (Normale MWSt.)', Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL WHERE AD_Element_ID=579140 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T10:57:21.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Versandkostenprodukt (Normale MWSt.)', Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579140) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579140)
;

-- 2021-05-07T10:57:21.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Versandkostenprodukt (Normale MWSt.)', Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579140
;

-- 2021-05-07T10:57:21.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Versandkostenprodukt (Normale MWSt.)', Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL WHERE AD_Element_ID = 579140
;

-- 2021-05-07T10:57:21.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Versandkostenprodukt (Normale MWSt.)', Description = 'Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579140
;

-- 2021-05-07T10:57:23.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',Updated=TO_TIMESTAMP('2021-05-07 13:57:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579140 AND AD_Language='de_CH'
;

-- 2021-05-07T10:57:23.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579140,'de_CH') 
;

-- 2021-05-07T10:58:19.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579141,0,'FreightCost_Reduced_VAT_Rates',TO_TIMESTAMP('2021-05-07 13:58:18','YYYY-MM-DD HH24:MI:SS'),100,'Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"','de.metas.externalsystem','Y','Reduced VAT rates','Reduced VAT rates',TO_TIMESTAMP('2021-05-07 13:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T10:58:19.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579141 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-07T10:58:30.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Reduzierte MWSt. Sätze', PrintName='Reduzierte MWSt. Sätze',Updated=TO_TIMESTAMP('2021-05-07 13:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579141 AND AD_Language='de_CH'
;

-- 2021-05-07T10:58:30.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579141,'de_CH') 
;

-- 2021-05-07T10:58:33.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Reduzierte MWSt. Sätze', PrintName='Reduzierte MWSt. Sätze',Updated=TO_TIMESTAMP('2021-05-07 13:58:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579141 AND AD_Language='de_DE'
;

-- 2021-05-07T10:58:33.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579141,'de_DE') 
;

-- 2021-05-07T10:58:33.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579141,'de_DE') 
;

-- 2021-05-07T10:58:34Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FreightCost_Reduced_VAT_Rates', Name='Reduzierte MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID=579141
;

-- 2021-05-07T10:58:34.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FreightCost_Reduced_VAT_Rates', Name='Reduzierte MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL, AD_Element_ID=579141 WHERE UPPER(ColumnName)='FREIGHTCOST_REDUCED_VAT_RATES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T10:58:34.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FreightCost_Reduced_VAT_Rates', Name='Reduzierte MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID=579141 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T10:58:34.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reduzierte MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579141) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579141)
;

-- 2021-05-07T10:58:34.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Reduzierte MWSt. Sätze', Name='Reduzierte MWSt. Sätze' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579141)
;

-- 2021-05-07T10:58:34.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reduzierte MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579141
;

-- 2021-05-07T10:58:34.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reduzierte MWSt. Sätze', Description='Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"', Help=NULL WHERE AD_Element_ID = 579141
;

-- 2021-05-07T10:58:34.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reduzierte MWSt. Sätze', Description = 'Comma delimited list of freightcost tax rates for which the "Freight cost product (reduced VAT)" shall be used. Example: "0, 7.7, 19"', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579141
;

-- 2021-05-07T10:58:37.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Reduzierte MWSt. Sätze', PrintName='Reduzierte MWSt. Sätze',Updated=TO_TIMESTAMP('2021-05-07 13:58:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579141 AND AD_Language='nl_NL'
;

-- 2021-05-07T10:58:37.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579141,'nl_NL') 
;

-- 2021-05-07T10:59:05.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"',Updated=TO_TIMESTAMP('2021-05-07 13:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579141 AND AD_Language='nl_NL'
;

-- 2021-05-07T10:59:05.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579141,'nl_NL') 
;

-- 2021-05-07T10:59:10.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"',Updated=TO_TIMESTAMP('2021-05-07 13:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579141 AND AD_Language='de_DE'
;

-- 2021-05-07T10:59:10.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579141,'de_DE') 
;

-- 2021-05-07T10:59:10.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579141,'de_DE') 
;

-- 2021-05-07T10:59:10.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FreightCost_Reduced_VAT_Rates', Name='Reduzierte MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"', Help=NULL WHERE AD_Element_ID=579141
;

-- 2021-05-07T10:59:10.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FreightCost_Reduced_VAT_Rates', Name='Reduzierte MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"', Help=NULL, AD_Element_ID=579141 WHERE UPPER(ColumnName)='FREIGHTCOST_REDUCED_VAT_RATES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T10:59:10.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FreightCost_Reduced_VAT_Rates', Name='Reduzierte MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"', Help=NULL WHERE AD_Element_ID=579141 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T10:59:10.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reduzierte MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579141) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579141)
;

-- 2021-05-07T10:59:10.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reduzierte MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579141
;

-- 2021-05-07T10:59:10.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reduzierte MWSt. Sätze', Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"', Help=NULL WHERE AD_Element_ID = 579141
;

-- 2021-05-07T10:59:10.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reduzierte MWSt. Sätze', Description = 'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579141
;

-- 2021-05-07T10:59:14.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"',Updated=TO_TIMESTAMP('2021-05-07 13:59:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579141 AND AD_Language='de_CH'
;

-- 2021-05-07T10:59:14.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579141,'de_CH') 
;

-- 2021-05-07T10:59:49.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579142,0,'M_FreightCost_ReducedVAT_Product_ID',TO_TIMESTAMP('2021-05-07 13:59:49','YYYY-MM-DD HH24:MI:SS'),100,'If a shopware order''s freight costs have the reduced VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.','de.metas.externalsystem','Y','Freight cost product (reduced VAT)','Freight cost product (reduced VAT)',TO_TIMESTAMP('2021-05-07 13:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T10:59:49.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579142 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-07T11:00:01.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Versandkostenprodukt (Reduzierte MWSt.)', PrintName='Versandkostenprodukt (Reduzierte MWSt.)',Updated=TO_TIMESTAMP('2021-05-07 14:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579142 AND AD_Language='de_CH'
;

-- 2021-05-07T11:00:01.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579142,'de_CH') 
;

-- 2021-05-07T11:00:04.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Versandkostenprodukt (Reduzierte MWSt.)', PrintName='Versandkostenprodukt (Reduzierte MWSt.)',Updated=TO_TIMESTAMP('2021-05-07 14:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579142 AND AD_Language='de_DE'
;

-- 2021-05-07T11:00:04.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579142,'de_DE') 
;

-- 2021-05-07T11:00:04.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579142,'de_DE') 
;

-- 2021-05-07T11:00:04.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_FreightCost_ReducedVAT_Product_ID', Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='If a shopware order''s freight costs have the reduced VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL WHERE AD_Element_ID=579142
;

-- 2021-05-07T11:00:04.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_FreightCost_ReducedVAT_Product_ID', Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='If a shopware order''s freight costs have the reduced VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL, AD_Element_ID=579142 WHERE UPPER(ColumnName)='M_FREIGHTCOST_REDUCEDVAT_PRODUCT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T11:00:04.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_FreightCost_ReducedVAT_Product_ID', Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='If a shopware order''s freight costs have the reduced VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL WHERE AD_Element_ID=579142 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T11:00:04.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='If a shopware order''s freight costs have the reduced VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579142) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579142)
;

-- 2021-05-07T11:00:04.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Versandkostenprodukt (Reduzierte MWSt.)', Name='Versandkostenprodukt (Reduzierte MWSt.)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579142)
;

-- 2021-05-07T11:00:04.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='If a shopware order''s freight costs have the reduced VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579142
;

-- 2021-05-07T11:00:04.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='If a shopware order''s freight costs have the reduced VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', Help=NULL WHERE AD_Element_ID = 579142
;

-- 2021-05-07T11:00:04.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Versandkostenprodukt (Reduzierte MWSt.)', Description = 'If a shopware order''s freight costs have the reduced VAT rate, then a line with this product is created. The product''s tax category needs to have a tax with a matching rate.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579142
;

-- 2021-05-07T11:00:16.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Name='Versandkostenprodukt (Reduzierte MWSt.)', PrintName='Versandkostenprodukt (Reduzierte MWSt.)',Updated=TO_TIMESTAMP('2021-05-07 14:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579142 AND AD_Language='nl_NL'
;

-- 2021-05-07T11:00:16.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579142,'nl_NL') 
;

-- 2021-05-07T11:00:24.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',Updated=TO_TIMESTAMP('2021-05-07 14:00:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579142 AND AD_Language='de_DE'
;

-- 2021-05-07T11:00:24.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579142,'de_DE') 
;

-- 2021-05-07T11:00:24.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579142,'de_DE') 
;

-- 2021-05-07T11:00:24.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_FreightCost_ReducedVAT_Product_ID', Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL WHERE AD_Element_ID=579142
;

-- 2021-05-07T11:00:24.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_FreightCost_ReducedVAT_Product_ID', Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL, AD_Element_ID=579142 WHERE UPPER(ColumnName)='M_FREIGHTCOST_REDUCEDVAT_PRODUCT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T11:00:24.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_FreightCost_ReducedVAT_Product_ID', Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL WHERE AD_Element_ID=579142 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T11:00:24.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579142) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579142)
;

-- 2021-05-07T11:00:24.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579142
;

-- 2021-05-07T11:00:24.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Versandkostenprodukt (Reduzierte MWSt.)', Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', Help=NULL WHERE AD_Element_ID = 579142
;

-- 2021-05-07T11:00:24.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Versandkostenprodukt (Reduzierte MWSt.)', Description = 'Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579142
;

-- 2021-05-07T11:00:27.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',Updated=TO_TIMESTAMP('2021-05-07 14:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579142 AND AD_Language='de_CH'
;

-- 2021-05-07T11:00:27.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579142,'de_CH') 
;

-- 2021-05-07T13:50:06.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573824,579139,0,10,541585,'FreightCost_NormalVAT_Rates',TO_TIMESTAMP('2021-05-07 16:50:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Normale MWSt. Sätze',0,0,TO_TIMESTAMP('2021-05-07 16:50:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-07T13:50:06.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-07T13:50:06.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579139) 
;

-- 2021-05-07T13:50:09.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN FreightCost_NormalVAT_Rates VARCHAR(255)')
;

-- 2021-05-07T13:50:28.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573825,579140,0,30,540272,541585,'M_FreightCost_NormalVAT_Product_ID',TO_TIMESTAMP('2021-05-07 16:50:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Versandkostenprodukt (Normale MWSt.)',0,0,TO_TIMESTAMP('2021-05-07 16:50:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-07T13:50:28.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573825 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-07T13:50:28.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579140) 
;

-- 2021-05-07T13:50:30.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN M_FreightCost_NormalVAT_Product_ID NUMERIC(10)')
;

-- 2021-05-07T13:50:30.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE ExternalSystem_Config_Shopware6 ADD CONSTRAINT MFreightCostNormalVATProduct_ExternalSystemConfigShopware6 FOREIGN KEY (M_FreightCost_NormalVAT_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2021-05-07T13:50:56.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573826,579142,0,30,540272,541585,'M_FreightCost_ReducedVAT_Product_ID',TO_TIMESTAMP('2021-05-07 16:50:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Versandkostenprodukt (Reduzierte MWSt.)',0,0,TO_TIMESTAMP('2021-05-07 16:50:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-07T13:50:56.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573826 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-07T13:50:56.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579142) 
;

-- 2021-05-07T13:50:58.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN M_FreightCost_ReducedVAT_Product_ID NUMERIC(10)')
;

-- 2021-05-07T13:50:58.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE ExternalSystem_Config_Shopware6 ADD CONSTRAINT MFreightCostReducedVATProduct_ExternalSystemConfigShopware6 FOREIGN KEY (M_FreightCost_ReducedVAT_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2021-05-07T13:51:33.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573827,579141,0,10,541585,'FreightCost_Reduced_VAT_Rates',TO_TIMESTAMP('2021-05-07 16:51:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reduzierte MWSt. Sätze',0,0,TO_TIMESTAMP('2021-05-07 16:51:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-07T13:51:33.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573827 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-07T13:51:33.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579141) 
;

-- 2021-05-07T13:51:35.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN FreightCost_Reduced_VAT_Rates VARCHAR(255)')
;

-- 2021-05-07T13:55:34.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573824,645423,0,543435,TO_TIMESTAMP('2021-05-07 16:55:34','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Normale MWSt. Sätze',TO_TIMESTAMP('2021-05-07 16:55:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:55:34.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645423 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:55:34.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579139) 
;

-- 2021-05-07T13:55:34.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645423
;

-- 2021-05-07T13:55:34.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645423)
;

-- 2021-05-07T13:55:35.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573825,645424,0,543435,TO_TIMESTAMP('2021-05-07 16:55:34','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Versandkostenprodukt (Normale MWSt.)',TO_TIMESTAMP('2021-05-07 16:55:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:55:35.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645424 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:55:35.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579140) 
;

-- 2021-05-07T13:55:35.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645424
;

-- 2021-05-07T13:55:35.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645424)
;

-- 2021-05-07T13:55:35.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573826,645425,0,543435,TO_TIMESTAMP('2021-05-07 16:55:35','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Versandkostenprodukt (Reduzierte MWSt.)',TO_TIMESTAMP('2021-05-07 16:55:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:55:35.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645425 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:55:35.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579142) 
;

-- 2021-05-07T13:55:35.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645425
;

-- 2021-05-07T13:55:35.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645425)
;

-- 2021-05-07T13:55:35.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573827,645426,0,543435,TO_TIMESTAMP('2021-05-07 16:55:35','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Reduzierte MWSt. Sätze',TO_TIMESTAMP('2021-05-07 16:55:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:55:35.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645426 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:55:35.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579141) 
;

-- 2021-05-07T13:55:35.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645426
;

-- 2021-05-07T13:55:35.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645426)
;

-- 2021-05-07T13:56:25.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645423,0,543435,584685,544975,'F',TO_TIMESTAMP('2021-05-07 16:56:25','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"','Y','N','N','Y','N','N','N',0,'Normale MWSt. Sätze',70,0,0,TO_TIMESTAMP('2021-05-07 16:56:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:56:51.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645424,0,543435,584686,544975,'F',TO_TIMESTAMP('2021-05-07 16:56:51','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.','Y','N','N','Y','N','N','N',0,'Versandkostenprodukt (Normale MWSt.)',80,0,0,TO_TIMESTAMP('2021-05-07 16:56:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:57:05.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645426,0,543435,584687,544975,'F',TO_TIMESTAMP('2021-05-07 16:57:05','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"','Y','N','N','Y','N','N','N',0,'Reduzierte MWSt. Sätze',90,0,0,TO_TIMESTAMP('2021-05-07 16:57:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:57:14.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645425,0,543435,584688,544975,'F',TO_TIMESTAMP('2021-05-07 16:57:14','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.','Y','N','N','Y','N','N','N',0,'Versandkostenprodukt (Reduzierte MWSt.)',100,0,0,TO_TIMESTAMP('2021-05-07 16:57:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:57:44.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573639,645427,0,543838,TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Vertriebpartner JSON-Path',TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:57:44.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645427 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:57:44.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579062) 
;

-- 2021-05-07T13:57:44.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645427
;

-- 2021-05-07T13:57:44.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645427)
;

-- 2021-05-07T13:57:44.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573824,645428,0,543838,TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Normale MWSt. Sätze',TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:57:44.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645428 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:57:44.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579139) 
;

-- 2021-05-07T13:57:44.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645428
;

-- 2021-05-07T13:57:44.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645428)
;

-- 2021-05-07T13:57:44.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573825,645429,0,543838,TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Versandkostenprodukt (Normale MWSt.)',TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:57:44.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645429 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:57:44.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579140) 
;

-- 2021-05-07T13:57:44.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645429
;

-- 2021-05-07T13:57:44.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645429)
;

-- 2021-05-07T13:57:44.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573826,645430,0,543838,TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.',10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Versandkostenprodukt (Reduzierte MWSt.)',TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:57:44.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645430 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:57:44.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579142) 
;

-- 2021-05-07T13:57:44.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645430
;

-- 2021-05-07T13:57:44.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645430)
;

-- 2021-05-07T13:57:45.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573827,645431,0,543838,TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Reduzierte MWSt. Sätze',TO_TIMESTAMP('2021-05-07 16:57:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:57:45.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645431 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:57:45.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579141) 
;

-- 2021-05-07T13:57:45.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645431
;

-- 2021-05-07T13:57:45.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645431)
;

-- 2021-05-07T13:58:38.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543770,545810,TO_TIMESTAMP('2021-05-07 16:58:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','tax',20,TO_TIMESTAMP('2021-05-07 16:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:58:51.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645428,0,543838,584689,545810,'F',TO_TIMESTAMP('2021-05-07 16:58:51','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"','Y','N','N','Y','N','N','N',0,'Normale MWSt. Sätze',10,0,0,TO_TIMESTAMP('2021-05-07 16:58:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:58:59.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645429,0,543838,584690,545810,'F',TO_TIMESTAMP('2021-05-07 16:58:58','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.','Y','N','N','Y','N','N','N',0,'Versandkostenprodukt (Normale MWSt.)',20,0,0,TO_TIMESTAMP('2021-05-07 16:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:59:07.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645431,0,543838,584691,545810,'F',TO_TIMESTAMP('2021-05-07 16:59:07','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"','Y','N','N','Y','N','N','N',0,'Reduzierte MWSt. Sätze',30,0,0,TO_TIMESTAMP('2021-05-07 16:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:59:18.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645430,0,543838,584692,545810,'F',TO_TIMESTAMP('2021-05-07 16:59:17','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.','Y','N','N','Y','N','N','N',0,'Versandkostenprodukt (Reduzierte MWSt.)',40,0,0,TO_TIMESTAMP('2021-05-07 16:59:17','YYYY-MM-DD HH24:MI:SS'),100)
;

