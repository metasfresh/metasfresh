-- Fix Column name Rechnung-Überprüfungselement

-- Add missing TRL
-- 2021-06-08T11:34:31.420Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungselement', PrintName='Rechnung-Überprüfungselement',Updated=TO_TIMESTAMP('2021-06-08 13:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579236 AND AD_Language='de_CH'
;

-- 2021-06-08T11:34:31.420Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579236,'de_CH')
;

-- 2021-06-08T11:34:37.076Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungselement', PrintName='Rechnung-Überprüfungselement',Updated=TO_TIMESTAMP('2021-06-08 13:34:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579236 AND AD_Language='de_DE'
;

-- 2021-06-08T11:34:37.076Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579236,'de_DE')
;

-- 2021-06-08T11:34:37.082Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579236,'de_DE')
;

-- 2021-06-08T11:34:37.083Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Invoice_Verification_SetLine_ID', Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL WHERE AD_Element_ID=579236
;

-- 2021-06-08T11:34:37.083Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_SetLine_ID', Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL, AD_Element_ID=579236 WHERE UPPER(ColumnName)='C_INVOICE_VERIFICATION_SETLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-08T11:34:37.086Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Invoice_Verification_SetLine_ID', Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL WHERE AD_Element_ID=579236 AND IsCentrallyMaintained='Y'
;

-- 2021-06-08T11:34:37.086Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579236) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579236)
;

-- 2021-06-08T11:34:37.113Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnung-Überprüfungselement', Name='Rechnung-Überprüfungselement' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579236)
;

-- 2021-06-08T11:34:37.114Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579236
;

-- 2021-06-08T11:34:37.115Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rechnung-Überprüfungselement', Description=NULL, Help=NULL WHERE AD_Element_ID = 579236
;

-- 2021-06-08T11:34:37.116Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rechnung-Überprüfungselement', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579236
;

-- 2021-06-08T11:34:48.751Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 13:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579236 AND AD_Language='en_US'
;

-- 2021-06-08T11:34:48.751Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579236,'en_US')
;

-- 2021-06-08T11:34:55.404Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnung-Überprüfungselement', PrintName='Rechnung-Überprüfungselement',Updated=TO_TIMESTAMP('2021-06-08 13:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579236 AND AD_Language='nl_NL'
;


-- Fix default value for C_Invoice_Verification_Run.status
-- 2021-06-09T06:09:44.756Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_invoice_verification_run','Status','CHAR(1)',null,'P')
;

-- 2021-06-09T06:09:44.779Z
-- URL zum Konzept
UPDATE C_Invoice_Verification_Run SET Status='P' WHERE Status IS NULL
;

-- 2021-06-09T06:15:04.627Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2021-06-09 08:15:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574072
;

-- 2021-06-09T06:15:16.721Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='P',Updated=TO_TIMESTAMP('2021-06-09 08:15:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574072
;

-- 2021-06-09T06:15:18.107Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_invoice_verification_run','Status','CHAR(1)',null,'P')
;

-- 2021-06-09T06:15:18.115Z
-- URL zum Konzept
UPDATE C_Invoice_Verification_Run SET Status='P' WHERE Status IS NULL
;