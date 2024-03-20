-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BPartner_ID
-- 2023-11-27T14:44:03.493Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,585334,542754,30,'C_BPartner_ID',TO_TIMESTAMP('2023-11-27 15:44:02','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner','D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',30,TO_TIMESTAMP('2023-11-27 15:44:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-27T14:44:03.559Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542754 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_BP_Group_ID
-- 2023-11-27T14:45:08.737Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1383,0,585334,542755,30,540439,'C_BP_Group_ID',TO_TIMESTAMP('2023-11-27 15:45:07','YYYY-MM-DD HH24:MI:SS'),100,'@C_BP_Group_ID@','Geschäftspartnergruppe','D',0,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','Geschäftspartnergruppe',40,TO_TIMESTAMP('2023-11-27 15:45:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-27T14:45:08.803Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542755 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: IsSOTrx
-- 2023-11-27T14:45:45.525Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1106,0,585334,542756,20,'IsSOTrx',TO_TIMESTAMP('2023-11-27 15:45:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','Dies ist eine Verkaufstransaktion','U',0,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','Verkaufstransaktion',50,TO_TIMESTAMP('2023-11-27 15:45:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-27T14:45:45.591Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542756 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: p_show_product_price_pi_flag
-- 2023-11-27T14:46:43.679Z
UPDATE AD_Process_Para SET SeqNo=60,Updated=TO_TIMESTAMP('2023-11-27 15:46:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542734
;

-- Process: RV_Fresh_SalesPriceList_ToDate(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: IsSOTrx
-- 2023-11-27T14:47:15.222Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-11-27 15:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542756
;

