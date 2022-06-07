-- 2022-06-03T12:12:00.016Z
UPDATE AD_Element_Trl SET Description='a',Updated=TO_TIMESTAMP('2022-06-03 15:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='de_CH'
;

-- 2022-06-03T12:12:00.064Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'de_CH') 
;

-- 2022-06-03T12:12:09.716Z
UPDATE AD_Element_Trl SET Description='Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2022-06-03 15:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='de_CH'
;

-- 2022-06-03T12:12:09.718Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'de_CH') 
;

-- 2022-06-03T12:12:14.261Z
UPDATE AD_Element_Trl SET Description='a',Updated=TO_TIMESTAMP('2022-06-03 15:12:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='de_DE'
;

-- 2022-06-03T12:12:14.263Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'de_DE') 
;

-- 2022-06-03T12:12:14.276Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580078,'de_DE') 
;

-- 2022-06-03T12:12:14.278Z
UPDATE AD_Column SET ColumnName=NULL, Name='Preisliste', Description='a', Help=NULL WHERE AD_Element_ID=580078
;

-- 2022-06-03T12:12:14.279Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Preisliste', Description='a', Help=NULL WHERE AD_Element_ID=580078 AND IsCentrallyMaintained='Y'
;

-- 2022-06-03T12:12:14.281Z
UPDATE AD_Field SET Name='Preisliste', Description='a', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580078) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580078)
;

-- 2022-06-03T12:12:14.309Z
UPDATE AD_Tab SET Name='Preisliste', Description='a', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580078
;

-- 2022-06-03T12:12:14.311Z
UPDATE AD_WINDOW SET Name='Preisliste', Description='a', Help=NULL WHERE AD_Element_ID = 580078
;

-- 2022-06-03T12:12:14.311Z
UPDATE AD_Menu SET   Name = 'Preisliste', Description = 'a', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580078
;

-- 2022-06-03T12:12:16.817Z
UPDATE AD_Element_Trl SET Description='Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2022-06-03 15:12:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='de_DE'
;

-- 2022-06-03T12:12:16.819Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'de_DE') 
;

-- 2022-06-03T12:12:16.828Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580078,'de_DE') 
;

-- 2022-06-03T12:12:16.830Z
UPDATE AD_Column SET ColumnName=NULL, Name='Preisliste', Description='Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.', Help=NULL WHERE AD_Element_ID=580078
;

-- 2022-06-03T12:12:16.831Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Preisliste', Description='Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.', Help=NULL WHERE AD_Element_ID=580078 AND IsCentrallyMaintained='Y'
;

-- 2022-06-03T12:12:16.832Z
UPDATE AD_Field SET Name='Preisliste', Description='Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580078) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580078)
;

-- 2022-06-03T12:12:16.852Z
UPDATE AD_Tab SET Name='Preisliste', Description='Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580078
;

-- 2022-06-03T12:12:16.853Z
UPDATE AD_WINDOW SET Name='Preisliste', Description='Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.', Help=NULL WHERE AD_Element_ID = 580078
;

-- 2022-06-03T12:12:16.854Z
UPDATE AD_Menu SET   Name = 'Preisliste', Description = 'Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580078
;

-- 2022-06-03T12:12:21.922Z
UPDATE AD_Element_Trl SET Description='a',Updated=TO_TIMESTAMP('2022-06-03 15:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='nl_NL'
;

-- 2022-06-03T12:12:21.923Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'nl_NL') 
;

-- 2022-06-03T12:12:24.337Z
UPDATE AD_Element_Trl SET Description='Die Preise werden der neuesten Version der angegebenen Preisliste hinzugefügt. Außerdem werden Kunden dem Preissystem der Preisliste zugeordnet.',Updated=TO_TIMESTAMP('2022-06-03 15:12:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='nl_NL'
;

-- 2022-06-03T12:12:24.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'nl_NL') 
;

-- 2022-06-03T12:12:32.483Z
UPDATE AD_Element_Trl SET Description='a',Updated=TO_TIMESTAMP('2022-06-03 15:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='en_US'
;

-- 2022-06-03T12:12:32.485Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'en_US') 
;

-- 2022-06-03T12:12:35.593Z
UPDATE AD_Element_Trl SET Description='Prices will be added to the latest price list version of the specified price list. Also, customers will be upserted to the pricelist''s pricing system',Updated=TO_TIMESTAMP('2022-06-03 15:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580078 AND AD_Language='en_US'
;

-- 2022-06-03T12:12:35.594Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580078,'en_US') 
;

