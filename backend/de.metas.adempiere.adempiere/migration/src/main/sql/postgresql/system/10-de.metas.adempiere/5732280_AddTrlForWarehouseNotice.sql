
-- Process: Shipping Notification - Warehouse partner(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-08-30T15:00:22.753Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Übernahmeschein (Jasper)',Updated=TO_TIMESTAMP('2024-08-30 18:00:22.753','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585420
;

-- Process: Shipping Notification - Warehouse partner(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-08-30T15:00:31.013Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Übernahmeschein',Updated=TO_TIMESTAMP('2024-08-30 18:00:31.012','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585420
;

-- 2024-08-30T15:00:31.014Z
UPDATE AD_Process SET Name='Übernahmeschein' WHERE AD_Process_ID=585420
;

-- 2024-09-02T12:15:36.844Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583240,0,'ShipFrom_User_ID',TO_TIMESTAMP('2024-09-02 15:15:36.634','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','ShipFrom User','ShipFrom User',TO_TIMESTAMP('2024-09-02 15:15:36.634','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-02T12:15:36.849Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583240 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: Shipping Notification - Warehouse partner(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-09-02T13:02:08.665Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Print warehouse notice',Updated=TO_TIMESTAMP('2024-09-02 16:02:08.665','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585420
;

-- Process: Shipping Notification - Warehouse partner(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-09-02T13:02:14.550Z
UPDATE AD_Process_Trl SET Name='Print warehouse notice',Updated=TO_TIMESTAMP('2024-09-02 16:02:14.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585420
;

-- Process: Shipping Notification - Warehouse partner(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2024-09-02T13:02:16.954Z
UPDATE AD_Process_Trl SET Name='Print warehouse notice',Updated=TO_TIMESTAMP('2024-09-02 16:02:16.953','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Process_ID=585420
;

