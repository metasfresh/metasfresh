-- 2024-12-05T14:12:06.888467970Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583386,0,TO_TIMESTAMP('2024-12-05 15:12:06.887','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','BPGroupName','BPGroupName',TO_TIMESTAMP('2024-12-05 15:12:06.887','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-05T14:12:06.894554646Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583386 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-05T14:12:18.741768237Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-05 15:12:18.741','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386
;

-- 2024-12-05T14:12:18.768862089Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'de_DE') 
;

-- Element: null
-- 2024-12-05T14:13:11.295742031Z
UPDATE AD_Element_Trl SET Name='Geschäftspartnergruppe',Updated=TO_TIMESTAMP('2024-12-05 15:13:11.295','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386 AND AD_Language='de_CH'
;

-- 2024-12-05T14:13:11.297119464Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'de_CH') 
;

-- Element: null
-- 2024-12-05T14:13:12.242408580Z
UPDATE AD_Element_Trl SET PrintName='Geschäftspartnergruppe',Updated=TO_TIMESTAMP('2024-12-05 15:13:12.242','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386 AND AD_Language='de_CH'
;

-- 2024-12-05T14:13:12.243658722Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'de_CH') 
;

-- Element: null
-- 2024-12-05T14:13:18.724048746Z
UPDATE AD_Element_Trl SET Name='Geschäftspartnergruppe',Updated=TO_TIMESTAMP('2024-12-05 15:13:18.723','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386 AND AD_Language='de_DE'
;

-- 2024-12-05T14:13:18.725866003Z
UPDATE AD_Element SET Name='Geschäftspartnergruppe', Updated=TO_TIMESTAMP('2024-12-05 15:13:18.724','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=583386
;

-- 2024-12-05T14:13:19.063319618Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583386,'de_DE') 
;

-- 2024-12-05T14:13:19.064223757Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'de_DE') 
;

-- Element: null
-- 2024-12-05T14:13:19.574337932Z
UPDATE AD_Element_Trl SET PrintName='Geschäftspartnergruppe',Updated=TO_TIMESTAMP('2024-12-05 15:13:19.574','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386 AND AD_Language='de_DE'
;

-- 2024-12-05T14:13:19.574836046Z
UPDATE AD_Element SET PrintName='Geschäftspartnergruppe', Updated=TO_TIMESTAMP('2024-12-05 15:13:19.574','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=583386
;

-- 2024-12-05T14:13:19.854967011Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583386,'de_DE') 
;

-- 2024-12-05T14:13:19.855483556Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'de_DE') 
;

-- Element: null
-- 2024-12-05T14:13:24.608167257Z
UPDATE AD_Element_Trl SET Name='BP Group Name',Updated=TO_TIMESTAMP('2024-12-05 15:13:24.607','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386 AND AD_Language='en_US'
;

-- 2024-12-05T14:13:24.609278357Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'en_US') 
;

-- Element: null
-- 2024-12-05T14:13:26.048713379Z
UPDATE AD_Element_Trl SET PrintName='BP Group Name',Updated=TO_TIMESTAMP('2024-12-05 15:13:26.048','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386 AND AD_Language='en_US'
;

-- 2024-12-05T14:13:26.049488696Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'en_US') 
;

-- Element: null
-- 2024-12-05T14:13:30.445918497Z
UPDATE AD_Element_Trl SET Name='BP Group Name',Updated=TO_TIMESTAMP('2024-12-05 15:13:30.445','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386 AND AD_Language='fr_CH'
;

-- 2024-12-05T14:13:30.446907487Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'fr_CH') 
;

-- Element: null
-- 2024-12-05T14:13:32.000613827Z
UPDATE AD_Element_Trl SET PrintName='BP Group Name',Updated=TO_TIMESTAMP('2024-12-05 15:13:32.0','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583386 AND AD_Language='fr_CH'
;

-- 2024-12-05T14:13:32.001588526Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583386,'fr_CH') 
;
