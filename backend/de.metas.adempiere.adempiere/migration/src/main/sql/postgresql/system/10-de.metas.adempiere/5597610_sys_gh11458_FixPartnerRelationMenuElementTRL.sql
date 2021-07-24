-- 2021-07-09T06:59:48.450Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner Verknüpfungen', PrintName='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-09 09:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575566 AND AD_Language='de_CH'
;

-- 2021-07-09T06:59:48.666Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575566,'de_CH') 
;

-- 2021-07-09T06:59:55.921Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-09 09:59:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575566 AND AD_Language='en_US'
;

-- 2021-07-09T06:59:55.960Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575566,'en_US') 
;

-- 2021-07-09T07:00:03.500Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner Verknüpfungen', PrintName='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-09 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575566 AND AD_Language='nl_NL'
;

-- 2021-07-09T07:00:03.538Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575566,'nl_NL') 
;

-- 2021-07-09T07:00:10.788Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner Verknüpfungen', PrintName='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-09 10:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575566 AND AD_Language='de_DE'
;

-- 2021-07-09T07:00:10.827Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575566,'de_DE') 
;

-- 2021-07-09T07:00:10.909Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(575566,'de_DE') 
;

-- 2021-07-09T07:00:10.954Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Partner Verknüpfungen', Description='Beziehungen der Geschäftspartner verwalten', Help=NULL WHERE AD_Element_ID=575566
;

-- 2021-07-09T07:00:10.990Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Partner Verknüpfungen', Description='Beziehungen der Geschäftspartner verwalten', Help=NULL WHERE AD_Element_ID=575566 AND IsCentrallyMaintained='Y'
;

-- 2021-07-09T07:00:11.030Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Partner Verknüpfungen', Description='Beziehungen der Geschäftspartner verwalten', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575566) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575566)
;

-- 2021-07-09T07:00:11.120Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Partner Verknüpfungen', Name='Partner Verknüpfungen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575566)
;

-- 2021-07-09T07:00:11.162Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Partner Verknüpfungen', Description='Beziehungen der Geschäftspartner verwalten', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575566
;

-- 2021-07-09T07:00:11.200Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Partner Verknüpfungen', Description='Beziehungen der Geschäftspartner verwalten', Help=NULL WHERE AD_Element_ID = 575566
;

-- 2021-07-09T07:00:11.242Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Partner Verknüpfungen', Description = 'Beziehungen der Geschäftspartner verwalten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575566
;

-- 2021-07-09T07:01:36.311Z
-- URL zum Konzept
UPDATE AD_Menu SET InternalName='_Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-09 10:01:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000090
;

-- 2021-07-09T07:09:34.001Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-09 10:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575566 AND AD_Language='de_CH'
;

-- 2021-07-09T07:09:34.044Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575566,'de_CH') 
;

-- 2021-07-09T07:09:42.240Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-09 10:09:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575566 AND AD_Language='nl_NL'
;

-- 2021-07-09T07:09:42.291Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575566,'nl_NL') 
;

-- 2021-07-09T07:09:48.329Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-09 10:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575566 AND AD_Language='de_DE'
;

-- 2021-07-09T07:09:48.372Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575566,'de_DE') 
;

-- 2021-07-09T07:09:48.472Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(575566,'de_DE') 
;

-- 2021-07-09T07:09:48.512Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Partner Verknüpfungen', Description = 'Beziehungen der Geschäftspartner verwalten', WEBUI_NameBrowse = 'Partner Verknüpfungen', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575566
;

