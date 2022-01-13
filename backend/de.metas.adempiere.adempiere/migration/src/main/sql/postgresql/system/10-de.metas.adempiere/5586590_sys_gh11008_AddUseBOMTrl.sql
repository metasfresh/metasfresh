-- 2021-04-27T08:21:50.018Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verwendung', PrintName='Verwendung',Updated=TO_TIMESTAMP('2021-04-27 10:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2784 AND AD_Language='fr_CH'
;

-- 2021-04-27T08:21:50.037Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2784,'fr_CH') 
;

-- 2021-04-27T08:22:01.480Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verwendung', PrintName='Verwendung',Updated=TO_TIMESTAMP('2021-04-27 10:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2784 AND AD_Language='de_CH'
;

-- 2021-04-27T08:22:01.480Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2784,'de_CH') 
;

-- 2021-04-27T08:22:09.275Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Verwendung', PrintName='Verwendung',Updated=TO_TIMESTAMP('2021-04-27 10:22:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2784 AND AD_Language='nl_NL'
;

-- 2021-04-27T08:22:09.276Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2784,'nl_NL') 
;

-- 2021-04-27T08:22:26.442Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verwendung', PrintName='Verwendung',Updated=TO_TIMESTAMP('2021-04-27 10:22:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2784 AND AD_Language='de_DE'
;

-- 2021-04-27T08:22:26.443Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2784,'de_DE') 
;

-- 2021-04-27T08:22:26.448Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(2784,'de_DE') 
;

-- 2021-04-27T08:22:26.453Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='BOMUse', Name='Verwendung', Description='The use of the Bill of Material', Help='By default the Master BOM is used, if the alternatives are not defined' WHERE AD_Element_ID=2784
;

-- 2021-04-27T08:22:26.457Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='BOMUse', Name='Verwendung', Description='The use of the Bill of Material', Help='By default the Master BOM is used, if the alternatives are not defined', AD_Element_ID=2784 WHERE UPPER(ColumnName)='BOMUSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-27T08:22:26.458Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='BOMUse', Name='Verwendung', Description='The use of the Bill of Material', Help='By default the Master BOM is used, if the alternatives are not defined' WHERE AD_Element_ID=2784 AND IsCentrallyMaintained='Y'
;

-- 2021-04-27T08:22:26.458Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Verwendung', Description='The use of the Bill of Material', Help='By default the Master BOM is used, if the alternatives are not defined' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2784) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2784)
;

-- 2021-04-27T08:22:26.471Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Verwendung', Name='Verwendung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2784)
;

-- 2021-04-27T08:22:26.472Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Verwendung', Description='The use of the Bill of Material', Help='By default the Master BOM is used, if the alternatives are not defined', CommitWarning = NULL WHERE AD_Element_ID = 2784
;

-- 2021-04-27T08:22:26.473Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Verwendung', Description='The use of the Bill of Material', Help='By default the Master BOM is used, if the alternatives are not defined' WHERE AD_Element_ID = 2784
;

-- 2021-04-27T08:22:26.474Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Verwendung', Description = 'The use of the Bill of Material', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2784
;

-- 2021-04-27T08:22:33.852Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2021-04-27 10:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2784 AND AD_Language='fr_CH'
;

-- 2021-04-27T08:22:33.856Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2784,'fr_CH') 
;

-- 2021-04-27T08:30:19.419Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Zubehörteile reservieren',Updated=TO_TIMESTAMP('2021-04-27 10:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584783
;

-- 2021-04-27T08:31:00.073Z
-- URL zum Konzept
UPDATE AD_Process SET Name='Reservierte Zubehörteile stornieren',Updated=TO_TIMESTAMP('2021-04-27 10:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584785
;

-- 2021-04-27T10:57:59.199Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Zubehörteile reservieren',Updated=TO_TIMESTAMP('2021-04-27 12:57:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584783
;

-- 2021-04-27T10:58:06.321Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Zubehörteile reservieren',Updated=TO_TIMESTAMP('2021-04-27 12:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584783
;

-- 2021-04-27T10:58:13.849Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Zubehörteile reservieren',Updated=TO_TIMESTAMP('2021-04-27 12:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584783
;

-- 2021-04-27T10:59:19.233Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Reservierte Zubehörteile stornieren',Updated=TO_TIMESTAMP('2021-04-27 12:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584785
;

-- 2021-04-27T10:59:23.862Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Reservierte Zubehörteile stornieren',Updated=TO_TIMESTAMP('2021-04-27 12:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584785
;

-- 2021-04-27T10:59:32.116Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Reservierte Zubehörteile stornieren',Updated=TO_TIMESTAMP('2021-04-27 12:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584785
;


