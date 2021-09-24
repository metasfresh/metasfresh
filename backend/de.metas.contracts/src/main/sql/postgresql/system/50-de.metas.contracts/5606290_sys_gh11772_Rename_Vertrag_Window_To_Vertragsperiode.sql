-- 2021-09-23T14:32:15.811Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579915,0,TO_TIMESTAMP('2021-09-23 17:32:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vertragsperiode','Vertragsperiode',TO_TIMESTAMP('2021-09-23 17:32:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-23T14:32:15.890Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579915 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-23T14:33:14.421Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Contract', PrintName='Contract',Updated=TO_TIMESTAMP('2021-09-23 17:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='en_US'
;

-- 2021-09-23T14:33:14.490Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'en_US') 
;

-- 2021-09-23T14:34:05.637Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=579915, Description=NULL, Help=NULL, Name='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-23 17:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540359
;

-- 2021-09-23T14:34:05.890Z
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-23 17:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540951
;

-- 2021-09-23T14:34:06.195Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579915) 
;

-- 2021-09-23T14:34:06.275Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540359
;

-- 2021-09-23T14:34:06.367Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540359)
;

