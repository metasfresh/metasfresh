-- 2022-07-29T13:13:46.324Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Kalender öffnen',Updated=TO_TIMESTAMP('2022-07-29 16:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585076
;

-- 2022-07-29T13:13:50.662Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Kalender öffnen',Updated=TO_TIMESTAMP('2022-07-29 16:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585076
;

-- 2022-07-29T13:13:50.611Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Kalender öffnen',Updated=TO_TIMESTAMP('2022-07-29 16:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585076
;

-- 2022-07-29T13:13:52.406Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-29 16:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585076
;

-- 2022-07-29T13:14:39.823Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Simulationsplan', PrintName='Simulationsplan',Updated=TO_TIMESTAMP('2022-07-29 16:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581064 AND AD_Language='de_CH'
;

-- 2022-07-29T13:14:39.877Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581064,'de_CH') 
;

-- 2022-07-29T13:14:45.033Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Simulationsplan', PrintName='Simulationsplan',Updated=TO_TIMESTAMP('2022-07-29 16:14:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581064 AND AD_Language='de_DE'
;

-- 2022-07-29T13:14:45.035Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581064,'de_DE') 
;

-- 2022-07-29T13:14:45.088Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581064,'de_DE') 
;

-- 2022-07-29T13:14:45.093Z
UPDATE AD_Column SET ColumnName='C_SimulationPlan_ID', Name='Simulationsplan', Description=NULL, Help=NULL WHERE AD_Element_ID=581064
;

-- 2022-07-29T13:14:45.100Z
UPDATE AD_Process_Para SET ColumnName='C_SimulationPlan_ID', Name='Simulationsplan', Description=NULL, Help=NULL, AD_Element_ID=581064 WHERE UPPER(ColumnName)='C_SIMULATIONPLAN_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-29T13:14:45.106Z
UPDATE AD_Process_Para SET ColumnName='C_SimulationPlan_ID', Name='Simulationsplan', Description=NULL, Help=NULL WHERE AD_Element_ID=581064 AND IsCentrallyMaintained='Y'
;

-- 2022-07-29T13:14:45.107Z
UPDATE AD_Field SET Name='Simulationsplan', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581064) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581064)
;

-- 2022-07-29T13:14:45.203Z
UPDATE AD_PrintFormatItem pi SET PrintName='Simulationsplan', Name='Simulationsplan' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581064)
;

-- 2022-07-29T13:14:45.205Z
UPDATE AD_Tab SET Name='Simulationsplan', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581064
;

-- 2022-07-29T13:14:45.207Z
UPDATE AD_WINDOW SET Name='Simulationsplan', Description=NULL, Help=NULL WHERE AD_Element_ID = 581064
;

-- 2022-07-29T13:14:45.210Z
UPDATE AD_Menu SET   Name = 'Simulationsplan', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581064
;

-- 2022-07-29T13:14:47.554Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-29 16:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581064 AND AD_Language='en_US'
;

-- 2022-07-29T13:14:47.555Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581064,'en_US') 
;

-- 2022-07-29T13:15:16.871Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Fertigstellen',Updated=TO_TIMESTAMP('2022-07-29 16:15:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585074
;

-- 2022-07-29T13:15:20.327Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Fertigstellen',Updated=TO_TIMESTAMP('2022-07-29 16:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585074
;

-- 2022-07-29T13:15:20.317Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Fertigstellen',Updated=TO_TIMESTAMP('2022-07-29 16:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585074
;

-- 2022-07-29T13:15:22.720Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-29 16:15:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585074
;

