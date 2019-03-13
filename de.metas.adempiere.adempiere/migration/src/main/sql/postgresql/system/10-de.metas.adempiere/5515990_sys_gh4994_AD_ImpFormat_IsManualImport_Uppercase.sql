-- 2019-03-13T10:36:26.535
-- #298 changing anz. stellen
UPDATE AD_Element SET ColumnName='IsManualImport',Updated=TO_TIMESTAMP('2019-03-13 10:36:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576158
;

-- 2019-03-13T10:36:26.555
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName='IsManualImport', Name='isManualImport', Description=NULL, Help=NULL WHERE AD_Element_ID=576158
;

-- 2019-03-13T10:36:26.555
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsManualImport', Name='isManualImport', Description=NULL, Help=NULL, AD_Element_ID=576158 WHERE UPPER(ColumnName)='ISMANUALIMPORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-13T10:36:26.555
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsManualImport', Name='isManualImport', Description=NULL, Help=NULL WHERE AD_Element_ID=576158 AND IsCentrallyMaintained='Y'
;

-- 2019-03-13T10:36:37.890
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='IsManualImport', PrintName='IsManualImport',Updated=TO_TIMESTAMP('2019-03-13 10:36:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576158 AND AD_Language='de_DE'
;

-- 2019-03-13T10:36:37.921
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576158,'de_DE') 
;

-- 2019-03-13T10:36:37.951
-- #298 changing anz. stellen
/* DDL */  select update_ad_element_on_ad_element_trl_update(576158,'de_DE') 
;

-- 2019-03-13T10:36:37.951
-- #298 changing anz. stellen
UPDATE AD_Column SET ColumnName='IsManualImport', Name='IsManualImport', Description=NULL, Help=NULL WHERE AD_Element_ID=576158
;

-- 2019-03-13T10:36:37.951
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsManualImport', Name='IsManualImport', Description=NULL, Help=NULL, AD_Element_ID=576158 WHERE UPPER(ColumnName)='ISMANUALIMPORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-13T10:36:37.951
-- #298 changing anz. stellen
UPDATE AD_Process_Para SET ColumnName='IsManualImport', Name='IsManualImport', Description=NULL, Help=NULL WHERE AD_Element_ID=576158 AND IsCentrallyMaintained='Y'
;

-- 2019-03-13T10:36:37.961
-- #298 changing anz. stellen
UPDATE AD_Field SET Name='IsManualImport', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576158) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576158)
;

-- 2019-03-13T10:36:38.071
-- #298 changing anz. stellen
UPDATE AD_PrintFormatItem pi SET PrintName='IsManualImport', Name='IsManualImport' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576158)
;

-- 2019-03-13T10:36:38.071
-- #298 changing anz. stellen
UPDATE AD_Tab SET Name='IsManualImport', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576158
;

-- 2019-03-13T10:36:38.081
-- #298 changing anz. stellen
UPDATE AD_WINDOW SET Name='IsManualImport', Description=NULL, Help=NULL WHERE AD_Element_ID = 576158
;

-- 2019-03-13T10:36:38.081
-- #298 changing anz. stellen
UPDATE AD_Menu SET   Name = 'IsManualImport', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576158
;




-- 2019-03-13T11:21:39.140
-- #298 changing anz. stellen
INSERT INTO t_alter_column values('ad_impformat','IsManualImport','CHAR(1)',null,'N')
;

-- 2019-03-13T11:21:39.439
-- #298 changing anz. stellen
UPDATE AD_ImpFormat SET IsManualImport='N' WHERE IsManualImport IS NULL
;




-- 2019-03-13T13:19:35.354
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='IsManualImport', PrintName='IsManualImport',Updated=TO_TIMESTAMP('2019-03-13 13:19:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576158 AND AD_Language='en_US'
;

-- 2019-03-13T13:19:35.633
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576158,'en_US') 
;


