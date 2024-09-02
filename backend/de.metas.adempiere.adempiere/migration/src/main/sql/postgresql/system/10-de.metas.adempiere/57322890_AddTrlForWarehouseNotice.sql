
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

-- Column: M_Shipping_Notification.ShipFrom_User_ID
-- 2024-09-02T12:16:29.363Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588940,583240,0,30,540376,542365,'ShipFrom_User_ID',TO_TIMESTAMP('2024-09-02 15:16:29.232','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ShipFrom User',0,0,TO_TIMESTAMP('2024-09-02 15:16:29.232','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-02T12:16:29.379Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588940 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-02T12:16:29.401Z
/* DDL */  select update_Column_Translation_From_AD_Element(583240)
;

-- Column: M_Shipping_Notification.ShipFrom_User_ID
-- 2024-09-02T12:17:16.051Z
UPDATE AD_Column SET AD_Reference_Value_ID=110,Updated=TO_TIMESTAMP('2024-09-02 15:17:16.051','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588940
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

