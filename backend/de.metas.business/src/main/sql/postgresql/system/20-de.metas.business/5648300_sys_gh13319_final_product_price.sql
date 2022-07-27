-- 2022-07-27T14:49:13.255Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581183,0,TO_TIMESTAMP('2022-07-27 14:49:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kosten Neubewertung Position','Kosten Neubewertung Position',TO_TIMESTAMP('2022-07-27 14:49:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-27T14:49:13.257Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581183 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-27T14:49:39.400Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Element_ID=581183, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Kosten Neubewertung Position',Updated=TO_TIMESTAMP('2022-07-27 14:49:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546465
;

-- 2022-07-27T14:49:39.426Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(581183) 
;

-- 2022-07-27T14:49:39.431Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546465)
;

-- 2022-07-27T14:51:15.021Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Cost Revaluation Line', PrintName='Cost Revaluation Line',Updated=TO_TIMESTAMP('2022-07-27 14:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581183 AND AD_Language='en_US'
;

-- 2022-07-27T14:51:15.045Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581183,'en_US') 
;

-- 2022-07-27T14:56:39.483Z
-- URL zum Konzept
UPDATE C_DocType SET Name='Kosten Neubewertung',Updated=TO_TIMESTAMP('2022-07-27 14:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541047
;

-- 2022-07-27T14:56:39.494Z
-- URL zum Konzept
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Kosten Neubewertung', PrintName='Cost Revaluation'  WHERE C_DocType_ID=541047 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-07-27T14:56:43.997Z
-- URL zum Konzept
UPDATE C_DocType SET PrintName='Kosten Neubewertung',Updated=TO_TIMESTAMP('2022-07-27 14:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541047
;

-- 2022-07-27T14:56:43.998Z
-- URL zum Konzept
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Kosten Neubewertung', PrintName='Kosten Neubewertung'  WHERE C_DocType_ID=541047 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;
