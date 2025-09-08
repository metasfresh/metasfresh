-- Element: null
-- 2024-05-06T14:25:50.927Z
UPDATE AD_Element_Trl SET Name='Verträge', PrintName='Verträge',Updated=TO_TIMESTAMP('2024-05-06 14:25:50.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='de_CH'
;

-- 2024-05-06T14:25:51.043Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579915,'de_CH') 
;

-- 2024-05-06T14:25:51.094Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'de_CH') 
;

-- Element: null
-- 2024-05-06T14:25:50.927Z
UPDATE AD_Element_Trl SET Name='Verträge', PrintName='Verträge',Updated=TO_TIMESTAMP('2024-05-06 14:25:50.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='de_DE'
;

-- 2024-05-06T14:25:51.043Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579915,'de_DE') 
;

-- 2024-05-06T14:25:51.094Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'de_DE') 
;

-- Name: Verträge
-- Action Type: W
-- Window: Verträge(540359,de.metas.contracts)
-- 2024-05-06T14:29:00.171Z
UPDATE AD_Menu SET AD_Element_ID=579915, Description=NULL, Name='Verträge', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2024-05-06 14:29:00.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540951
;

-- 2024-05-06T14:29:00.487Z
/* DDL */  select update_menu_translation_from_ad_element(579915) 
;

-- Column: C_Flatrate_Term.M_Product_ID
-- Column: C_Flatrate_Term.M_Product_ID
-- 2024-05-06T16:25:04.428Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2024-05-06 16:25:04.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=547283
;

