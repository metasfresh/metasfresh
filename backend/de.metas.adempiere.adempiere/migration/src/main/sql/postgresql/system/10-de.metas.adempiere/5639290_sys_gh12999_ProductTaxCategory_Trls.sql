-- 2022-05-17T17:02:44.675Z
UPDATE AD_Table_Trl SET Name='Produkt Steuerkategorie',Updated=TO_TIMESTAMP('2022-05-17 20:02:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542131
;

-- 2022-05-17T17:02:47.383Z
UPDATE AD_Table_Trl SET Name='Produkt Steuerkategorie',Updated=TO_TIMESTAMP('2022-05-17 20:02:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=542131
;

-- 2022-05-17T17:02:50.333Z
UPDATE AD_Table_Trl SET Name='Produkt Steuerkategorie',Updated=TO_TIMESTAMP('2022-05-17 20:02:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542131
;

-- 2022-05-17T17:05:30.154Z
UPDATE AD_Element_Trl SET Name='Produkt Steuerkategorie', PrintName='Produkt Steuerkategorie',Updated=TO_TIMESTAMP('2022-05-17 20:05:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580821 AND AD_Language='de_CH'
;

-- 2022-05-17T17:05:30.199Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580821,'de_CH')
;

-- 2022-05-17T17:05:33.677Z
UPDATE AD_Element_Trl SET Name='Produkt Steuerkategorie', PrintName='Produkt Steuerkategorie',Updated=TO_TIMESTAMP('2022-05-17 20:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580821 AND AD_Language='de_DE'
;

-- 2022-05-17T17:05:33.679Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580821,'de_DE')
;

-- 2022-05-17T17:05:33.703Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580821,'de_DE')
;

-- 2022-05-17T17:05:33.706Z
UPDATE AD_Column SET ColumnName='M_Product_TaxCategory_ID', Name='Produkt Steuerkategorie', Description=NULL, Help=NULL WHERE AD_Element_ID=580821
;

-- 2022-05-17T17:05:33.707Z
UPDATE AD_Process_Para SET ColumnName='M_Product_TaxCategory_ID', Name='Produkt Steuerkategorie', Description=NULL, Help=NULL, AD_Element_ID=580821 WHERE UPPER(ColumnName)='M_PRODUCT_TAXCATEGORY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-17T17:05:33.708Z
UPDATE AD_Process_Para SET ColumnName='M_Product_TaxCategory_ID', Name='Produkt Steuerkategorie', Description=NULL, Help=NULL WHERE AD_Element_ID=580821 AND IsCentrallyMaintained='Y'
;

-- 2022-05-17T17:05:33.709Z
UPDATE AD_Field SET Name='Produkt Steuerkategorie', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580821) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580821)
;

-- 2022-05-17T17:05:33.738Z
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt Steuerkategorie', Name='Produkt Steuerkategorie' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580821)
;

-- 2022-05-17T17:05:33.741Z
UPDATE AD_Tab SET Name='Produkt Steuerkategorie', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580821
;

-- 2022-05-17T17:05:33.743Z
UPDATE AD_WINDOW SET Name='Produkt Steuerkategorie', Description=NULL, Help=NULL WHERE AD_Element_ID = 580821
;

-- 2022-05-17T17:05:33.744Z
UPDATE AD_Menu SET   Name = 'Produkt Steuerkategorie', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580821
;

-- 2022-05-17T17:05:37.100Z
UPDATE AD_Element_Trl SET Name='Produkt Steuerkategorie', PrintName='Produkt Steuerkategorie',Updated=TO_TIMESTAMP('2022-05-17 20:05:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580821 AND AD_Language='nl_NL'
;

-- 2022-05-17T17:05:37.102Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580821,'nl_NL')
;