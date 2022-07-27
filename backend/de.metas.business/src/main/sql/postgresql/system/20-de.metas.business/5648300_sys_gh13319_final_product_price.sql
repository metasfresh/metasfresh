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
UPDATE C_DocType SET isdefault = 'Y', PrintName='Kosten Neubewertung',Updated=TO_TIMESTAMP('2022-07-27 14:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541047
;

-- 2022-07-27T14:56:43.998Z
-- URL zum Konzept
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Kosten Neubewertung', PrintName='Kosten Neubewertung'  WHERE C_DocType_ID=541047 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

UPDATE c_doctype_trl SET name = 'Cost Revaluation', printname = 'Cost Revaluation' WHERE c_doctype_id = 541047 AND ad_language = 'en_US'
;


-- 2022-07-27T15:30:38.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541612,TO_TIMESTAMP('2022-07-27 16:30:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_CostRevaluation',TO_TIMESTAMP('2022-07-27 16:30:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-07-27T15:30:38.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541612 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-07-27T15:30:51.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='M_CostRevaluation Doctype',Updated=TO_TIMESTAMP('2022-07-27 16:30:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541612
;

-- 2022-07-27T15:32:39.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1501,0,541612,217,TO_TIMESTAMP('2022-07-27 16:32:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Doctype_ID','N',TO_TIMESTAMP('2022-07-27 16:32:39','YYYY-MM-DD HH24:MI:SS'),100,'C_Doctype.DocBaseType  = ''CRD''')
;

-- 2022-07-27T15:34:38.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='IsDefault',Updated=TO_TIMESTAMP('2022-07-27 16:34:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541612
;

-- 2022-07-27T15:34:48.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='IsDefault Desc',Updated=TO_TIMESTAMP('2022-07-27 16:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541612
;

-- 2022-07-27T15:35:28.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541612, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-07-27 16:35:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583803
;
