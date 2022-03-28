-- 2021-08-30T06:55:33.587Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579607,0,TO_TIMESTAMP('2021-08-30 08:55:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zusammensetzung','Zusammensetzung',TO_TIMESTAMP('2021-08-30 08:55:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-30T06:55:33.636Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579607 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-08-30T06:55:38.764Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-08-30 08:55:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579607 AND AD_Language='de_CH'
;

-- 2021-08-30T06:55:38.781Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579607,'de_CH') 
;

-- 2021-08-30T06:55:39.032Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579607,'de_CH') 
;

-- 2021-08-30T06:55:59.818Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ingredients', PrintName='Ingredients',Updated=TO_TIMESTAMP('2021-08-30 08:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579607 AND AD_Language='en_US'
;

-- 2021-08-30T06:55:59.819Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579607,'en_US') 
;

-- 2021-08-30T06:56:28.491Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=579607, Description=NULL, Help=NULL, Name='Zusammensetzung',Updated=TO_TIMESTAMP('2021-08-30 08:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565189
;

-- 2021-08-30T06:56:28.576Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579607) 
;

-- 2021-08-30T06:56:28.615Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=565189
;

-- 2021-08-30T06:56:28.724Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(565189)
;

