-- 2021-10-26T09:30:26.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-26 12:30:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577844
;

-- 2021-10-26T09:30:27.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','PP_Product_BOMVersions_ID','NUMERIC(10)',null,null)
;

-- 2021-10-26T09:30:27.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_bom','PP_Product_BOMVersions_ID',null,'NOT NULL',null)
;

-- 2021-10-26T14:17:58.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Stücklistenkonfiguration Version', PrintName='Stücklistenkonfiguration Version',Updated=TO_TIMESTAMP('2021-10-26 17:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574286 AND AD_Language='de_DE'
;

-- 2021-10-26T14:17:58.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574286,'de_DE') 
;

-- 2021-10-26T14:17:58.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574286,'de_DE') 
;

-- 2021-10-26T14:17:58.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Stücklistenkonfiguration Version', Description='Maintain Product Bill of Materials & Formula ', Help='It is a list of all the subassemblies, intermediates, parts and raw material that go into a parent assembly showing the quantity of each required to make an assembly. There are a variety of display formats of bill of material, including single level bill of material, indented bill of material, modular (planning), costed bill of material, etc. May also be called "formula", "recipe", "ingredients list" in certain industries.
It answers the question, what are the components of the product?' WHERE AD_Element_ID=574286
;

-- 2021-10-26T14:17:58.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Stücklistenkonfiguration Version', Description='Maintain Product Bill of Materials & Formula ', Help='It is a list of all the subassemblies, intermediates, parts and raw material that go into a parent assembly showing the quantity of each required to make an assembly. There are a variety of display formats of bill of material, including single level bill of material, indented bill of material, modular (planning), costed bill of material, etc. May also be called "formula", "recipe", "ingredients list" in certain industries.
It answers the question, what are the components of the product?' WHERE AD_Element_ID=574286 AND IsCentrallyMaintained='Y'
;

-- 2021-10-26T14:17:58.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Stücklistenkonfiguration Version', Description='Maintain Product Bill of Materials & Formula ', Help='It is a list of all the subassemblies, intermediates, parts and raw material that go into a parent assembly showing the quantity of each required to make an assembly. There are a variety of display formats of bill of material, including single level bill of material, indented bill of material, modular (planning), costed bill of material, etc. May also be called "formula", "recipe", "ingredients list" in certain industries.
It answers the question, what are the components of the product?' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574286) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574286)
;

-- 2021-10-26T14:17:58.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Stücklistenkonfiguration Version', Name='Stücklistenkonfiguration Version' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574286)
;

-- 2021-10-26T14:17:58.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Stücklistenkonfiguration Version', Description='Maintain Product Bill of Materials & Formula ', Help='It is a list of all the subassemblies, intermediates, parts and raw material that go into a parent assembly showing the quantity of each required to make an assembly. There are a variety of display formats of bill of material, including single level bill of material, indented bill of material, modular (planning), costed bill of material, etc. May also be called "formula", "recipe", "ingredients list" in certain industries.
It answers the question, what are the components of the product?', CommitWarning = NULL WHERE AD_Element_ID = 574286
;

-- 2021-10-26T14:17:58.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Stücklistenkonfiguration Version', Description='Maintain Product Bill of Materials & Formula ', Help='It is a list of all the subassemblies, intermediates, parts and raw material that go into a parent assembly showing the quantity of each required to make an assembly. There are a variety of display formats of bill of material, including single level bill of material, indented bill of material, modular (planning), costed bill of material, etc. May also be called "formula", "recipe", "ingredients list" in certain industries.
It answers the question, what are the components of the product?' WHERE AD_Element_ID = 574286
;

-- 2021-10-26T14:17:58.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Stücklistenkonfiguration Version', Description = 'Maintain Product Bill of Materials & Formula ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574286
;

-- 2021-10-26T14:18:14.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bill of Materials & Formula Version', PrintName='Bill of Materials & Formula Version',Updated=TO_TIMESTAMP('2021-10-26 17:18:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574286 AND AD_Language='en_US'
;

-- 2021-10-26T14:18:14.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574286,'en_US') 
;

-- 2021-10-26T14:19:44.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='BOM & Formula Version', PrintName='BOM & Formula Version',Updated=TO_TIMESTAMP('2021-10-26 17:19:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53245 AND AD_Language='en_US'
;

-- 2021-10-26T14:19:44.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53245,'en_US') 
;

-- 2021-10-26T14:19:52.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='BOM & Formula Version', PrintName='BOM & Formula Version',Updated=TO_TIMESTAMP('2021-10-26 17:19:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53245 AND AD_Language='de_DE'
;

-- 2021-10-26T14:19:52.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53245,'de_DE') 
;

-- 2021-10-26T14:19:52.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(53245,'de_DE') 
;

-- 2021-10-26T14:19:52.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PP_Product_BOM_ID', Name='BOM & Formula Version', Description='BOM & Formula', Help=NULL WHERE AD_Element_ID=53245
;

-- 2021-10-26T14:19:52.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PP_Product_BOM_ID', Name='BOM & Formula Version', Description='BOM & Formula', Help=NULL, AD_Element_ID=53245 WHERE UPPER(ColumnName)='PP_PRODUCT_BOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-26T14:19:52.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PP_Product_BOM_ID', Name='BOM & Formula Version', Description='BOM & Formula', Help=NULL WHERE AD_Element_ID=53245 AND IsCentrallyMaintained='Y'
;

-- 2021-10-26T14:19:52.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='BOM & Formula Version', Description='BOM & Formula', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53245) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53245)
;

-- 2021-10-26T14:19:52.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='BOM & Formula Version', Name='BOM & Formula Version' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53245)
;

-- 2021-10-26T14:19:52.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='BOM & Formula Version', Description='BOM & Formula', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 53245
;

-- 2021-10-26T14:19:52.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='BOM & Formula Version', Description='BOM & Formula', Help=NULL WHERE AD_Element_ID = 53245
;

-- 2021-10-26T14:19:52.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'BOM & Formula Version', Description = 'BOM & Formula', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53245
;

