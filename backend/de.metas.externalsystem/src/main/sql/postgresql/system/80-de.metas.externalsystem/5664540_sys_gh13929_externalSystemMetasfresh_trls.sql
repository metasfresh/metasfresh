-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-16T11:26:07.935Z
UPDATE AD_Element_Trl SET Name='metasfresh', PrintName='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:26:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='de_CH'
;

-- 2022-11-16T11:26:08.009Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'de_CH') 
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-16T11:26:10.504Z
UPDATE AD_Element_Trl SET Name='metasfresh', PrintName='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:26:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='de_DE'
;

-- 2022-11-16T11:26:10.508Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581641,'de_DE') 
;

-- 2022-11-16T11:26:10.523Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'de_DE') 
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-16T11:26:12.809Z
UPDATE AD_Element_Trl SET Name='metasfresh', PrintName='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:26:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='en_US'
;

-- 2022-11-16T11:26:12.813Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'en_US') 
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-16T11:26:15.320Z
UPDATE AD_Element_Trl SET Name='metasfresh', PrintName='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:26:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='fr_CH'
;

-- 2022-11-16T11:26:15.322Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'fr_CH') 
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-16T11:26:17.513Z
UPDATE AD_Element_Trl SET Name='metasfresh', PrintName='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='nl_NL'
;

-- 2022-11-16T11:26:17.516Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'nl_NL') 
;

-- Reference Item: Type -> Metasfresh_Metasfresh
-- 2022-11-16T11:30:49.301Z
UPDATE AD_Ref_List_Trl SET Name='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543332
;

-- Reference Item: Type -> Metasfresh_Metasfresh
-- 2022-11-16T11:30:51.210Z
UPDATE AD_Ref_List_Trl SET Name='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:30:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543332
;

-- Reference Item: Type -> Metasfresh_Metasfresh
-- 2022-11-16T11:30:52.962Z
UPDATE AD_Ref_List_Trl SET Name='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:30:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543332
;

-- Reference Item: Type -> Metasfresh_Metasfresh
-- 2022-11-16T11:30:54.837Z
UPDATE AD_Ref_List_Trl SET Name='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:30:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543332
;

-- Reference Item: Type -> Metasfresh_Metasfresh
-- 2022-11-16T11:30:56.807Z
UPDATE AD_Ref_List_Trl SET Name='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:30:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543332
;

-- Reference: Type
-- Value: metasfresh
-- ValueName: Metasfresh
-- 2022-11-16T11:32:23.997Z
UPDATE AD_Ref_List SET Name='metasfresh', Value='metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:32:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543332
;

-- Value: Call_External_System_Metasfresh
-- Classname: de.metas.externalsystem.process.InvokeMetasfreshAction
-- 2022-11-16T11:56:44.927Z
UPDATE AD_Process SET Name='Invoke metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T11:56:50.991Z
UPDATE AD_Process_Trl SET Name='metasfresh aufrufen',Updated=TO_TIMESTAMP('2022-11-16 13:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585141
;

-- Value: Call_External_System_Metasfresh
-- Classname: de.metas.externalsystem.process.InvokeMetasfreshAction
-- 2022-11-16T11:56:54.747Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='metasfresh aufrufen',Updated=TO_TIMESTAMP('2022-11-16 13:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T11:56:54.742Z
UPDATE AD_Process_Trl SET Name='metasfresh aufrufen',Updated=TO_TIMESTAMP('2022-11-16 13:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T11:56:58.977Z
UPDATE AD_Process_Trl SET Name='Invoke metasfresh',Updated=TO_TIMESTAMP('2022-11-16 13:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T11:57:01.109Z
UPDATE AD_Process_Trl SET Name='metasfresh aufrufen',Updated=TO_TIMESTAMP('2022-11-16 13:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585141
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- 2022-11-16T11:57:02.873Z
UPDATE AD_Process_Trl SET Name='metasfresh aufrufen',Updated=TO_TIMESTAMP('2022-11-16 13:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585141
;

