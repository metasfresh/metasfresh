-- 2019-08-15T09:22:31.899Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Project Type2',Updated=TO_TIMESTAMP('2019-08-15 11:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2033 AND AD_Language='en_US'
;

-- 2019-08-15T09:22:31.932Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2033,'en_US') 
;

-- 2019-08-15T09:23:28.764Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Project Type', PrintName='Project Type',Updated=TO_TIMESTAMP('2019-08-15 11:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2033 AND AD_Language='en_US'
;

-- 2019-08-15T09:23:28.766Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2033,'en_US') 
;

-- 2019-08-15T09:32:26.697Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577009,0,TO_TIMESTAMP('2019-08-15 11:32:26','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Projekt Nummer','Projekt Nummer',TO_TIMESTAMP('2019-08-15 11:32:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-15T09:32:26.700Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577009 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-08-15T09:32:47.266Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Project No', PrintName='Project No',Updated=TO_TIMESTAMP('2019-08-15 11:32:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577009 AND AD_Language='en_US'
;

-- 2019-08-15T09:32:47.269Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577009,'en_US') 
;

-- 2019-08-15T09:32:49.192Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-15 11:32:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577009 AND AD_Language='en_US'
;

-- 2019-08-15T09:32:49.194Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577009,'en_US') 
;

-- 2019-08-15T09:33:40.464Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577009, Description=NULL, Help=NULL, Name='Projekt Nummer',Updated=TO_TIMESTAMP('2019-08-15 11:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582274
;

-- 2019-08-15T09:33:40.468Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577009) 
;

-- 2019-08-15T09:33:40.478Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582274
;

-- 2019-08-15T09:33:40.482Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582274)
;

-- 2019-08-15T09:41:23.029Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Project Status', PrintName='Project Status',Updated=TO_TIMESTAMP('2019-08-15 11:41:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544077 AND AD_Language='en_US'
;

-- 2019-08-15T09:41:23.031Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544077,'en_US') 
