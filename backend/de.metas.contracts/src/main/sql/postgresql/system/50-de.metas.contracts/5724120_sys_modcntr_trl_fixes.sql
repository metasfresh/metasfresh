-- Run mode: SWING_CLIENT

-- 2024-05-21T19:30:12.518Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583118,0,'InvoicingGroup_Name',TO_TIMESTAMP('2024-05-21 22:30:12.211','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Name der Abrechnungszeilengruppe','Name der Abrechnungszeilengruppe',TO_TIMESTAMP('2024-05-21 22:30:12.211','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-21T19:30:12.532Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583118 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InvoicingGroup_Name
-- 2024-05-21T19:30:37.372Z
UPDATE AD_Element_Trl SET Name='Invoicing Group Name', PrintName='Invoicing Group Name',Updated=TO_TIMESTAMP('2024-05-21 22:30:37.372','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583118 AND AD_Language='en_US'
;

-- 2024-05-21T19:30:37.402Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583118,'en_US')
;

-- 2024-05-21T20:19:08.493Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583119,0,'InterimInvoice_documentNo',TO_TIMESTAMP('2024-05-21 23:19:08.362','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Vorfinanzierungsnummer','Vorfinanzierungsnummer',TO_TIMESTAMP('2024-05-21 23:19:08.362','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-21T20:19:08.496Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583119 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InterimInvoice_documentNo
-- 2024-05-21T20:19:47.178Z
UPDATE AD_Element_Trl SET Name='Interim Document Number', PrintName='Interim Document Number',Updated=TO_TIMESTAMP('2024-05-21 23:19:47.178','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583119 AND AD_Language='en_US'
;

-- 2024-05-21T20:19:47.180Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583119,'en_US')
;

-- Element: TotalInterest
-- 2024-05-21T20:29:45.811Z
UPDATE AD_Element_Trl SET Name='Gesamtzins',Updated=TO_TIMESTAMP('2024-05-21 23:29:45.811','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583091 AND AD_Language='de_CH'
;

-- 2024-05-21T20:29:45.813Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583091,'de_CH')
;

-- Element: TotalInterest
-- 2024-05-21T20:29:47.664Z
UPDATE AD_Element_Trl SET PrintName='Gesamtzins',Updated=TO_TIMESTAMP('2024-05-21 23:29:47.664','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583091 AND AD_Language='de_CH'
;

-- 2024-05-21T20:29:47.666Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583091,'de_CH')
;

-- Element: TotalInterest
-- 2024-05-21T20:29:49.379Z
UPDATE AD_Element_Trl SET Name='Gesamtzins', PrintName='Gesamtzins',Updated=TO_TIMESTAMP('2024-05-21 23:29:49.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583091 AND AD_Language='de_DE'
;

-- 2024-05-21T20:29:49.383Z
UPDATE AD_Element SET Name='Gesamtzins', PrintName='Gesamtzins' WHERE AD_Element_ID=583091
;

-- 2024-05-21T20:29:49.636Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583091,'de_DE')
;

-- 2024-05-21T20:29:49.637Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583091,'de_DE')
;

-- Element: TotalInterest
-- 2024-05-21T20:29:51.631Z
UPDATE AD_Element_Trl SET Name='Gesamtzins', PrintName='Gesamtzins',Updated=TO_TIMESTAMP('2024-05-21 23:29:51.631','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583091 AND AD_Language='fr_CH'
;

-- 2024-05-21T20:29:51.633Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583091,'fr_CH')
;

-- Element: TotalInterest
-- 2024-05-21T20:30:01.601Z
UPDATE AD_Element_Trl SET Name='Gesamtzins', PrintName='Gesamtzins',Updated=TO_TIMESTAMP('2024-05-21 23:30:01.601','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583091 AND AD_Language='it_IT'
;

-- 2024-05-21T20:30:01.602Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583091,'it_IT')
;
