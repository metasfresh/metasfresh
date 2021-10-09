-- 2021-05-28T07:30:42.829Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Write-off date', PrintName='Write-off date',Updated=TO_TIMESTAMP('2021-05-28 10:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579264 AND AD_Language='en_US'
;

-- 2021-05-28T07:30:42.961Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579264,'en_US') 
;

-- 2021-05-28T07:31:08.784Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Datum der Abschreibung', PrintName='Datum der Abschreibung',Updated=TO_TIMESTAMP('2021-05-28 10:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579264 AND AD_Language='nl_NL'
;

-- 2021-05-28T07:31:08.954Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579264,'nl_NL') 
;

-- 2021-05-28T07:31:14.361Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Datum der Abschreibung', PrintName='Datum der Abschreibung',Updated=TO_TIMESTAMP('2021-05-28 10:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579264 AND AD_Language='de_DE'
;

-- 2021-05-28T07:31:14.489Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579264,'de_DE') 
;

-- 2021-05-28T07:31:14.552Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579264,'de_DE') 
;

-- 2021-05-28T07:31:14.604Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='WriteOffDate', Name='Datum der Abschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID=579264
;

-- 2021-05-28T07:31:14.635Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='WriteOffDate', Name='Datum der Abschreibung', Description=NULL, Help=NULL, AD_Element_ID=579264 WHERE UPPER(ColumnName)='WRITEOFFDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-28T07:31:14.673Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='WriteOffDate', Name='Datum der Abschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID=579264 AND IsCentrallyMaintained='Y'
;

-- 2021-05-28T07:31:14.704Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Datum der Abschreibung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579264) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579264)
;

-- 2021-05-28T07:31:14.751Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Datum der Abschreibung', Name='Datum der Abschreibung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579264)
;

-- 2021-05-28T07:31:14.789Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Datum der Abschreibung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579264
;

-- 2021-05-28T07:31:14.820Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Datum der Abschreibung', Description=NULL, Help=NULL WHERE AD_Element_ID = 579264
;

-- 2021-05-28T07:31:14.858Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Datum der Abschreibung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579264
;

-- 2021-05-28T07:31:19.504Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Datum der Abschreibung', PrintName='Datum der Abschreibung',Updated=TO_TIMESTAMP('2021-05-28 10:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579264 AND AD_Language='de_CH'
;

-- 2021-05-28T07:31:19.635Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579264,'de_CH') 
;

-- 2021-05-28T07:31:47.305Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Date with which the write-off''s payment allocations will be created',Updated=TO_TIMESTAMP('2021-05-28 10:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579264 AND AD_Language='en_US'
;

-- 2021-05-28T07:31:47.452Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579264,'en_US') 
;

-- 2021-05-28T07:32:03.878Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird',Updated=TO_TIMESTAMP('2021-05-28 10:32:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579264 AND AD_Language='nl_NL'
;

-- 2021-05-28T07:32:04.016Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579264,'nl_NL') 
;

-- 2021-05-28T07:32:09.023Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird',Updated=TO_TIMESTAMP('2021-05-28 10:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579264 AND AD_Language='de_DE'
;

-- 2021-05-28T07:32:09.145Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579264,'de_DE') 
;

-- 2021-05-28T07:32:09.223Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579264,'de_DE') 
;

-- 2021-05-28T07:32:09.246Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='WriteOffDate', Name='Datum der Abschreibung', Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird', Help=NULL WHERE AD_Element_ID=579264
;

-- 2021-05-28T07:32:09.277Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='WriteOffDate', Name='Datum der Abschreibung', Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird', Help=NULL, AD_Element_ID=579264 WHERE UPPER(ColumnName)='WRITEOFFDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-28T07:32:09.324Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='WriteOffDate', Name='Datum der Abschreibung', Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird', Help=NULL WHERE AD_Element_ID=579264 AND IsCentrallyMaintained='Y'
;

-- 2021-05-28T07:32:09.346Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Datum der Abschreibung', Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579264) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579264)
;

-- 2021-05-28T07:32:09.393Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Datum der Abschreibung', Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579264
;

-- 2021-05-28T07:32:09.424Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Datum der Abschreibung', Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird', Help=NULL WHERE AD_Element_ID = 579264
;

-- 2021-05-28T07:32:09.464Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Datum der Abschreibung', Description = 'Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579264
;

-- 2021-05-28T07:32:12.402Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Datum, das beim Erstellen der Abschreibe-Zahlungs-Zuordnungen benutzt wird',Updated=TO_TIMESTAMP('2021-05-28 10:32:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579264 AND AD_Language='de_CH'
;

-- 2021-05-28T07:32:12.548Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579264,'de_CH') 
;

