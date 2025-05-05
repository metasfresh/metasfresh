-- Run mode: SWING_CLIENT

-- Element: QtyPlan
-- 2024-05-29T10:44:45.603Z
UPDATE AD_Element_Trl SET Description='Geplante Menge', Help='Geplante Menge', Name='Mengenplan', PrintName='Mengenplan',Updated=TO_TIMESTAMP('2024-05-29 13:44:45.603','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=2900 AND AD_Language='de_CH'
;

-- 2024-05-29T10:44:45.611Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2900,'de_CH')
;

-- Element: QtyPlan
-- 2024-05-29T10:44:57.658Z
UPDATE AD_Element_Trl SET Description='Geplante Menge', Help='Geplante Menge', Name='Mengenplan', PrintName='Mengenplan',Updated=TO_TIMESTAMP('2024-05-29 13:44:57.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=2900 AND AD_Language='de_DE'
;

-- 2024-05-29T10:44:57.661Z
UPDATE AD_Element SET Description='Geplante Menge', Help='Geplante Menge', Name='Mengenplan', PrintName='Mengenplan' WHERE AD_Element_ID=2900
;

-- 2024-05-29T10:44:59.108Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2900,'de_DE')
;

-- 2024-05-29T10:44:59.111Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2900,'de_DE')
;

-- 2024-05-29T10:45:54.958Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583130,0,'PackingInfo',TO_TIMESTAMP('2024-05-29 13:45:54.789','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Verpackungsinformationen','Verpackungsinformationen',TO_TIMESTAMP('2024-05-29 13:45:54.789','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-29T10:45:54.963Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583130 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PackingInfo
-- 2024-05-29T10:46:03.865Z
UPDATE AD_Element_Trl SET Name='Packing Info', PrintName='Packing Info',Updated=TO_TIMESTAMP('2024-05-29 13:46:03.865','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583130 AND AD_Language='en_US'
;

-- 2024-05-29T10:46:03.867Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583130,'en_US')
;

-- Process: WEBUI_PP_Order_Pick_ReceivedHUs(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Pick_ReceivedHUs)
-- 2024-05-29T10:57:01.580Z
UPDATE AD_Process_Trl SET Name='Kommissionieren',Updated=TO_TIMESTAMP('2024-05-29 13:57:01.58','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585028
;

-- Process: WEBUI_PP_Order_Pick_ReceivedHUs(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Pick_ReceivedHUs)
-- 2024-05-29T10:57:03.875Z
UPDATE AD_Process_Trl SET Name='Kommissionieren',Updated=TO_TIMESTAMP('2024-05-29 13:57:03.875','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585028
;

-- 2024-05-29T10:57:03.881Z
UPDATE AD_Process SET Name='Kommissionieren' WHERE AD_Process_ID=585028
;

-- Run mode: SWING_CLIENT

-- Element: Scrap
-- 2024-05-29T12:25:07.717Z
UPDATE AD_Element_Trl SET Description='Geben Sie den Prozentsatz des Schrotts an, um die Schrottmenge zu berechnen.', Name='% Schrott', PrintName='% Schrott',Updated=TO_TIMESTAMP('2024-05-29 15:25:07.717','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53257 AND AD_Language='de_CH'
;

-- 2024-05-29T12:25:07.775Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53257,'de_CH')
;

-- Element: Scrap
-- 2024-05-29T12:25:22.465Z
UPDATE AD_Element_Trl SET Description='Geben Sie den Prozentsatz des Schrotts an, um die Schrottmenge zu berechnen.', Name='% Schrott', PrintName='% Schrott',Updated=TO_TIMESTAMP('2024-05-29 15:25:22.465','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53257 AND AD_Language='de_DE'
;

-- 2024-05-29T12:25:22.467Z
UPDATE AD_Element SET Description='Geben Sie den Prozentsatz des Schrotts an, um die Schrottmenge zu berechnen.', Name='% Schrott', PrintName='% Schrott' WHERE AD_Element_ID=53257
;

-- 2024-05-29T12:25:22.971Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53257,'de_DE')
;

-- 2024-05-29T12:25:22.973Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53257,'de_DE')
;

-- Run mode: SWING_CLIENT

-- Process: CU Label (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-05-29T12:51:00.039Z
UPDATE AD_Process_Trl SET Description='CU-Etiketten (Jasper)', Name='CU-Etiketten',Updated=TO_TIMESTAMP('2024-05-29 15:51:00.039','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540802
;

-- 2024-05-29T12:51:00.056Z
UPDATE AD_Process SET Description='CU-Etiketten (Jasper)', Name='CU-Etiketten' WHERE AD_Process_ID=540802
;

-- Process: CU Label (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-05-29T12:51:04.678Z
UPDATE AD_Process_Trl SET Description='CU-Etiketten (Jasper)', Name='CU-Etiketten',Updated=TO_TIMESTAMP('2024-05-29 15:51:04.678','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540802
;

