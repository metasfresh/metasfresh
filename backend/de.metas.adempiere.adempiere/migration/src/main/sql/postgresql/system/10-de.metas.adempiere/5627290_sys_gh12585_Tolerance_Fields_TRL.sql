-- 2022-02-22T18:40:11.333Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Toleranz erzw.', PrintName='Toleranz erzw.',Updated=TO_TIMESTAMP('2022-02-22 20:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580594 AND AD_Language='de_CH'
;

-- 2022-02-22T18:40:11.415Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580594,'de_CH') 
;

-- 2022-02-22T18:40:51.914Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Toleranz erzw.', PrintName='Toleranz erzw.',Updated=TO_TIMESTAMP('2022-02-22 20:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580594 AND AD_Language='de_DE'
;

-- 2022-02-22T18:40:51.975Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580594,'de_DE') 
;

-- 2022-02-22T18:40:52.190Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580594,'de_DE') 
;

-- 2022-02-22T18:40:52.283Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsEnforceTolerance', Name='Toleranz erzw.', Description=NULL, Help=NULL WHERE AD_Element_ID=580594
;

-- 2022-02-22T18:40:52.382Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsEnforceTolerance', Name='Toleranz erzw.', Description=NULL, Help=NULL, AD_Element_ID=580594 WHERE UPPER(ColumnName)='ISENFORCETOLERANCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-02-22T18:40:52.474Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsEnforceTolerance', Name='Toleranz erzw.', Description=NULL, Help=NULL WHERE AD_Element_ID=580594 AND IsCentrallyMaintained='Y'
;

-- 2022-02-22T18:40:52.567Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Toleranz erzw.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580594) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580594)
;

-- 2022-02-22T18:40:52.689Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranz erzw.', Name='Toleranz erzw.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580594)
;

-- 2022-02-22T18:40:52.782Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Toleranz erzw.', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580594
;

-- 2022-02-22T18:40:52.874Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Toleranz erzw.', Description=NULL, Help=NULL WHERE AD_Element_ID = 580594
;

-- 2022-02-22T18:40:52.966Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Toleranz erzw.', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580594
;

-- 2022-02-22T18:41:43.358Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Toleranz %', PrintName='Toleranz %',Updated=TO_TIMESTAMP('2022-02-22 20:41:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580593 AND AD_Language='de_DE'
;

-- 2022-02-22T18:41:43.453Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580593,'de_DE') 
;

-- 2022-02-22T18:41:43.636Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(580593,'de_DE') 
;

-- 2022-02-22T18:41:43.766Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Tolerance_Perc', Name='Toleranz %', Description=NULL, Help=NULL WHERE AD_Element_ID=580593
;

-- 2022-02-22T18:41:43.858Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Tolerance_Perc', Name='Toleranz %', Description=NULL, Help=NULL, AD_Element_ID=580593 WHERE UPPER(ColumnName)='TOLERANCE_PERC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-02-22T18:41:43.965Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Tolerance_Perc', Name='Toleranz %', Description=NULL, Help=NULL WHERE AD_Element_ID=580593 AND IsCentrallyMaintained='Y'
;

-- 2022-02-22T18:41:44.057Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Toleranz %', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580593) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580593)
;

-- 2022-02-22T18:41:44.150Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Toleranz %', Name='Toleranz %' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580593)
;

-- 2022-02-22T18:41:44.243Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Toleranz %', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580593
;

-- 2022-02-22T18:41:44.335Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Toleranz %', Description=NULL, Help=NULL WHERE AD_Element_ID = 580593
;

-- 2022-02-22T18:41:44.427Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Toleranz %', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580593
;

-- 2022-02-22T18:41:48.170Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Toleranz %', PrintName='Toleranz %',Updated=TO_TIMESTAMP('2022-02-22 20:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580593 AND AD_Language='de_CH'
;

-- 2022-02-22T18:41:48.246Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580593,'de_CH') 
;

