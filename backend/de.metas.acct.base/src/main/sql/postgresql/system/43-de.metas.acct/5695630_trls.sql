-- 2023-07-13T15:01:22.780Z
UPDATE AD_Element SET ColumnName='OI',Updated=TO_TIMESTAMP('2023-07-13 18:01:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582535
;

-- 2023-07-13T15:01:22.786Z
UPDATE AD_Column SET ColumnName='OI' WHERE AD_Element_ID=582535
;

-- 2023-07-13T15:01:22.787Z
UPDATE AD_Process_Para SET ColumnName='OI' WHERE AD_Element_ID=582535
;

-- 2023-07-13T15:01:22.817Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582535,'en_US') 
;

-- Element: IsOpenItem
-- 2023-07-13T15:02:32.176Z
UPDATE AD_Element_Trl SET PrintName='OI',Updated=TO_TIMESTAMP('2023-07-13 18:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582534 AND AD_Language='en_US'
;

-- 2023-07-13T15:02:32.177Z
UPDATE AD_Element SET PrintName='OI', Updated=TO_TIMESTAMP('2023-07-13 18:02:32','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582534
;

-- 2023-07-13T15:02:32.916Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582534,'en_US') 
;

-- 2023-07-13T15:02:32.918Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582534,'en_US') 
;

-- Element: IsOpenItem
-- 2023-07-13T15:02:35.783Z
UPDATE AD_Element_Trl SET PrintName='OI',Updated=TO_TIMESTAMP('2023-07-13 18:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582534 AND AD_Language='de_DE'
;

-- 2023-07-13T15:02:35.786Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582534,'de_DE') 
;

