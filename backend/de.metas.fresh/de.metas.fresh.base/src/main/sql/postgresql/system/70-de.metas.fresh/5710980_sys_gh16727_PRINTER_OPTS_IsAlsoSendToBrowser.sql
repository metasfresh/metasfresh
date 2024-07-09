-- Run mode: SWING_CLIENT

-- 2023-11-19T22:15:59.921Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582810,0,'PRINTER_OPTS_IsAlsoSendToBrowser',TO_TIMESTAMP('2023-11-19 23:15:59.572','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Send To Browser','Send To Browser',TO_TIMESTAMP('2023-11-19 23:15:59.572','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-19T22:15:59.932Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582810 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PRINTER_OPTS_IsAlsoSendToBrowser
-- 2023-11-27T09:20:45.096666900Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='An Browser senden', PrintName='An Browser senden',Updated=TO_TIMESTAMP('2023-11-27 10:20:45.096','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582810 AND AD_Language='de_CH'
;

-- 2023-11-27T09:20:45.269854700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582810,'de_CH')
;

-- Element: PRINTER_OPTS_IsAlsoSendToBrowser
-- 2023-11-27T09:21:02.000238900Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='An Browser senden', PrintName='An Browser senden',Updated=TO_TIMESTAMP('2023-11-27 10:21:02.0','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582810 AND AD_Language='de_DE'
;

-- 2023-11-27T09:21:02.071596Z
UPDATE AD_Element SET Name='An Browser senden', PrintName='An Browser senden', Updated=TO_TIMESTAMP('2023-11-27 10:21:02.07','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=582810
;

-- 2023-11-27T09:22:13.764872400Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582810,'de_DE')
;

-- 2023-11-27T09:22:13.835326100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582810,'de_DE')
;

