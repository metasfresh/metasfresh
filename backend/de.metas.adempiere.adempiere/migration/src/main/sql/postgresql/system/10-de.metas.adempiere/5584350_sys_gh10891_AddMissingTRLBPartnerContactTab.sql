-- 2021-03-30T07:58:31.581Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verkaufskontakt', PrintName='Verkaufskontakt',Updated=TO_TIMESTAMP('2021-03-30 09:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540454 AND AD_Language='fr_CH'
;

-- 2021-03-30T07:58:31.597Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540454,'fr_CH')
;

-- 2021-03-30T07:58:41.928Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verkaufskontakt', PrintName='Verkaufskontakt',Updated=TO_TIMESTAMP('2021-03-30 09:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540454 AND AD_Language='de_CH'
;

-- 2021-03-30T07:58:41.929Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540454,'de_CH')
;

-- 2021-03-30T07:58:50.604Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verkaufskontakt', PrintName='Verkaufskontakt',Updated=TO_TIMESTAMP('2021-03-30 09:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540454 AND AD_Language='de_DE'
;

-- 2021-03-30T07:58:50.605Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540454,'de_DE')
;

-- 2021-03-30T07:58:50.611Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(540454,'de_DE')
;

-- 2021-03-30T07:58:50.612Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsSalesContact', Name='Verkaufskontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=540454
;

-- 2021-03-30T07:58:50.613Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsSalesContact', Name='Verkaufskontakt', Description=NULL, Help=NULL, AD_Element_ID=540454 WHERE UPPER(ColumnName)='ISSALESCONTACT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-30T07:58:50.616Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsSalesContact', Name='Verkaufskontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=540454 AND IsCentrallyMaintained='Y'
;

-- 2021-03-30T07:58:50.616Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Verkaufskontakt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540454) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 540454)
;

-- 2021-03-30T07:58:50.638Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Verkaufskontakt', Name='Verkaufskontakt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540454)
;

-- 2021-03-30T07:58:50.640Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Verkaufskontakt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 540454
;

-- 2021-03-30T07:58:50.641Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Verkaufskontakt', Description=NULL, Help=NULL WHERE AD_Element_ID = 540454
;

-- 2021-03-30T07:58:50.642Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Verkaufskontakt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 540454
;

-- 2021-03-30T08:01:09.428Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verkaufskontakt', PrintName='Verkaufskontakt',Updated=TO_TIMESTAMP('2021-03-30 10:01:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543418 AND AD_Language='de_CH'
;

-- 2021-03-30T08:01:09.432Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543418,'de_CH')
;

-- 2021-03-30T08:01:42.853Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rechnungskontakt', PrintName='Rechnungskontakt',Updated=TO_TIMESTAMP('2021-03-30 10:01:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543418 AND AD_Language='de_CH'
;

-- 2021-03-30T08:01:42.856Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543418,'de_CH')
;

-- 2021-03-30T08:01:53.591Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-30 10:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543418 AND AD_Language='de_DE'
;

-- 2021-03-30T08:01:53.592Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543418,'de_DE')
;

-- 2021-03-30T08:01:53.601Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543418,'de_DE')
;

-- 2021-03-30T08:06:35.169Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferkontakt', PrintName='Lieferkontakt',Updated=TO_TIMESTAMP('2021-03-30 10:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543417 AND AD_Language='de_CH'
;

-- 2021-03-30T08:06:35.172Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543417,'de_CH')
;

-- 2021-03-30T08:07:00.662Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Lieferkontakt', PrintName='Lieferkontakt',Updated=TO_TIMESTAMP('2021-03-30 10:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543417 AND AD_Language='nl_NL'
;

-- 2021-03-30T08:07:00.665Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543417,'nl_NL')
;

