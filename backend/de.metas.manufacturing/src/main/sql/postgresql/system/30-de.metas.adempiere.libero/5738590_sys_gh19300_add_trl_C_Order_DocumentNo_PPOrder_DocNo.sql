-- 2024-11-06T16:10:04.982Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583361,0,'C_Order_DocumentNo',TO_TIMESTAMP('2024-11-06 18:10:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auftrag Nr.','Auftrag Nr.',TO_TIMESTAMP('2024-11-06 18:10:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-06T16:10:04.995Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583361 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_Order_DocumentNo
-- 2024-11-06T16:10:47.981Z
UPDATE AD_Element_Trl SET Name='Sales order document no.', PrintName='Sales order document no.',Updated=TO_TIMESTAMP('2024-11-06 18:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583361 AND AD_Language='en_US'
;

-- 2024-11-06T16:10:48.030Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583361,'en_US') 
;

-- 2024-11-06T16:11:39.605Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583362,0,'PP_Order_DocumentNo',TO_TIMESTAMP('2024-11-06 18:11:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produktionsauftrag Nr.','Produktionsauftrag Nr.',TO_TIMESTAMP('2024-11-06 18:11:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-06T16:11:39.608Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583362 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PP_Order_DocumentNo
-- 2024-11-06T16:11:53.701Z
UPDATE AD_Element_Trl SET Name='Manufacturing order documner no.', PrintName='Manufacturing order documner no.',Updated=TO_TIMESTAMP('2024-11-06 18:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583362 AND AD_Language='en_US'
;

-- 2024-11-06T16:11:53.704Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583362,'en_US') 
;

