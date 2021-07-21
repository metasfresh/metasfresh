-- fix label fields trl
-- 2021-07-21T07:56:31.916Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Therapie',Updated=TO_TIMESTAMP('2021-07-21 10:56:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579773
;

-- 2021-07-21T07:57:43.859Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Abrechenbare Therapie',Updated=TO_TIMESTAMP('2021-07-21 10:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579777
;

-- fix window trl
-- 2021-07-21T08:03:53.632Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-21 11:03:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578835 AND AD_Language='nl_NL'
;

-- 2021-07-21T08:03:53.737Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578835,'nl_NL')
;

-- 2021-07-21T08:05:37.916Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Alberta Produkt',Updated=TO_TIMESTAMP('2021-07-21 11:05:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578835 AND AD_Language='de_CH'
;

-- 2021-07-21T08:05:37.959Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578835,'de_CH')
;

-- 2021-07-21T08:05:44.190Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Alberta Produkt',Updated=TO_TIMESTAMP('2021-07-21 11:05:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578835 AND AD_Language='de_DE'
;

-- 2021-07-21T08:05:44.232Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578835,'de_DE')
;

-- 2021-07-21T08:05:44.352Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578835,'de_DE')
;

-- 2021-07-21T08:05:44.391Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Alberta Produkt', Description = NULL, WEBUI_NameBrowse = 'Alberta Produkt', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578835
;

-- 2021-07-21T08:05:50.376Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Alberta Produkt',Updated=TO_TIMESTAMP('2021-07-21 11:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578835 AND AD_Language='nl_NL'
;

-- 2021-07-21T08:05:50.417Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578835,'nl_NL')
;

-- 2021-07-21T08:05:58.398Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Alberta Product',Updated=TO_TIMESTAMP('2021-07-21 11:05:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578835 AND AD_Language='en_US'
;

-- 2021-07-21T08:05:58.438Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578835,'en_US')
;

