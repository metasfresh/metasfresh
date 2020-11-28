-- 2020-03-20T14:34:43.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2020-03-20 16:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=312
;

-- 2020-03-23T13:28:34.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584673,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2020-03-23 15:28:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','Y','N','Y','Y','@PREFIX@de/metas/reports/pricelist/report.jasper','@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper',0,'Purchase Pricelist Comparison','N','Y','JasperReportsSQL',TO_TIMESTAMP('2020-03-23 15:28:34','YYYY-MM-DD HH24:MI:SS'),100,'PurchasePricelistComparison')
;

-- 2020-03-23T13:28:34.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584673 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-03-23T13:28:46.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='RV_Fresh_SalesPriceList_Comparison',Updated=TO_TIMESTAMP('2020-03-23 15:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540407
;

-- 2020-03-23T13:29:01.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='RV_Fresh_PurchasePriceList_Comparison',Updated=TO_TIMESTAMP('2020-03-23 15:29:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584673
;

-- 2020-03-23T13:34:40.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-03-23 15:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584673
;

-- 2020-03-23T13:37:29.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,450,0,584673,541764,18,199,540243,'M_PriceList_Version_ID',TO_TIMESTAMP('2020-03-23 15:37:29','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@)','Bezeichnet eine einzelne Version der Preisliste','de.metas.fresh',0,'Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','Y','N','Version Preisliste',10,TO_TIMESTAMP('2020-03-23 15:37:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-23T13:37:29.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541764 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-03-23T13:37:29.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541764
;

-- 2020-03-23T13:37:29.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541764, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540503
;

-- 2020-03-23T13:37:29.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542346,0,584673,541765,18,188,540242,'Alt_Pricelist_Version_ID',TO_TIMESTAMP('2020-03-23 15:37:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Vergleichs Preislistenversion',20,TO_TIMESTAMP('2020-03-23 15:37:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-23T13:37:29.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541765 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-03-23T13:37:29.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541765
;

-- 2020-03-23T13:37:29.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541765, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540504
;

-- 2020-03-23T13:37:29.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,584673,541766,30,'C_BPartner_ID',TO_TIMESTAMP('2020-03-23 15:37:29','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner','de.metas.fresh',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','N','N','Geschäftspartner',30,TO_TIMESTAMP('2020-03-23 15:37:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-23T13:37:29.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541766 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-03-23T13:37:29.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541766
;

-- 2020-03-23T13:37:29.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541766, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540505
;

-- 2020-03-23T13:37:30.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,189,0,584673,541767,18,131,'C_BPartner_Location_ID',TO_TIMESTAMP('2020-03-23 15:37:29','YYYY-MM-DD HH24:MI:SS'),100,'@Default_Bill_Location_ID@','Identifiziert die (Liefer-) Adresse des Geschäftspartners','de.metas.fresh',0,'Identifiziert die Adresse des Geschäftspartners','Y','Y','Y','N','Y','N','Standort',40,TO_TIMESTAMP('2020-03-23 15:37:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-23T13:37:30.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541767 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-03-23T13:37:30.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541767
;

-- 2020-03-23T13:37:30.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541767, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540546
;

-- 2020-03-23T13:37:30.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577492,0,584673,541768,17,541097,'ReportFormat',TO_TIMESTAMP('2020-03-23 15:37:30','YYYY-MM-DD HH24:MI:SS'),100,'PDF','de.metas.fresh',16,'Y','N','Y','N','Y','N','Berichte - Format',50,TO_TIMESTAMP('2020-03-23 15:37:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-23T13:37:30.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541768 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-03-23T13:37:30.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541768
;

-- 2020-03-23T13:37:30.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541768, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541677
;

-- 2020-03-23T13:40:43.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Sales Pricelist Comparison',Updated=TO_TIMESTAMP('2020-03-23 15:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540407
;

-- 2020-03-23T13:41:01.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Vergleich Verkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:41:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=540407
;

-- 2020-03-23T13:41:07.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Vergleich Verkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:41:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Process_ID=540407
;

-- 2020-03-23T13:41:12.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Vergleich Verkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=540407
;

-- 2020-03-23T13:41:17.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Vergleich Verkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:41:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540407
;

-- 2020-03-23T13:41:28.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Vergleich Verkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540407
;

-- 2020-03-23T13:41:38.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Sales Pricelist Comparison',Updated=TO_TIMESTAMP('2020-03-23 15:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540407
;

-- 2020-03-23T13:41:44.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Vergleich Verkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540407
;

-- 2020-03-23T13:41:56.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Sales Pricelist Comparison',Updated=TO_TIMESTAMP('2020-03-23 15:41:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Process_ID=540407
;

-- 2020-03-23T13:42:22.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Vergleich Einkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:42:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584673
;

-- 2020-03-23T13:42:36.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Vergleich Einkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:42:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584673
;

-- 2020-03-23T13:43:04.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Vergleich Verkaufspreisliste',Updated=TO_TIMESTAMP('2020-03-23 15:43:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540407
;

-- 2020-03-23T13:43:41.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584673,291,540807,TO_TIMESTAMP('2020-03-23 15:43:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2020-03-23 15:43:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2020-03-23T14:05:48.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ 
AND )',Updated=TO_TIMESTAMP('2020-03-23 16:05:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

-- 2020-03-23T14:35:23.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ 
AND plv.IsSOTrx = ''Y'' )', Name='Fresh_SalesPriceList_Version_of_BPartner',Updated=TO_TIMESTAMP('2020-03-23 16:35:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

-- 2020-03-23T14:35:45.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540487,'EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ 
AND plv.IsSOTrx = ''N'' )',TO_TIMESTAMP('2020-03-23 16:35:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Fresh_PurchasePriceList_Version_of_BPartner','S',TO_TIMESTAMP('2020-03-23 16:35:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-23T14:36:14.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540487,Updated=TO_TIMESTAMP('2020-03-23 16:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541764
;

-- 2020-03-23T15:11:00.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ )', Name='Fresh_PriceList_Version_of_BPartner',Updated=TO_TIMESTAMP('2020-03-23 17:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

-- 2020-03-23T15:11:21.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='Fresh_AnyPriceList_Version_of_BPartner',Updated=TO_TIMESTAMP('2020-03-23 17:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540243
;

-- 2020-03-23T15:12:50.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540488,'EXISTS ( select 1 from Report.Fresh_PriceList_Version_Val_Rule plv
where M_PriceList_Version.M_PriceList_Version_ID=plv.M_PriceList_Version_ID
 AND plv.C_BPartner_ID=@C_BPartner_ID@ 
AND plv.IsSOTrx = ''Y'' )',TO_TIMESTAMP('2020-03-23 17:12:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Fresh_SalesPriceList_Version_of_BPartner','S',TO_TIMESTAMP('2020-03-23 17:12:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-23T15:13:52.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2020-03-23 17:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540488
;

-- 2020-03-23T15:15:16.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540488,Updated=TO_TIMESTAMP('2020-03-23 17:15:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540503
;

-- 2020-03-23T15:21:56.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@, ''Y'')',Updated=TO_TIMESTAMP('2020-03-23 17:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540503
;

-- 2020-03-23T15:33:25.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@, ''N'' )',Updated=TO_TIMESTAMP('2020-03-23 17:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541764
;

-- 2020-03-23T16:28:29.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@, ''Y'')',Updated=TO_TIMESTAMP('2020-03-23 18:28:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541645
;

-- 2020-03-23T16:37:41.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540488,Updated=TO_TIMESTAMP('2020-03-23 18:37:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541645
;

-- 2020-03-23T17:02:55.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='Y',Updated=TO_TIMESTAMP('2020-03-23 19:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541229
;

-- 2020-03-23T17:03:09.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@, ''Y'' )',Updated=TO_TIMESTAMP('2020-03-23 19:03:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541648
;

-- 2020-03-23T17:03:22.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540488,Updated=TO_TIMESTAMP('2020-03-23 19:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541648
;

-- 2020-03-23T17:03:28.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2020-03-23 19:03:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541229
;

-- 2020-03-23T17:13:02.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540488,Updated=TO_TIMESTAMP('2020-03-23 19:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

-- 2020-03-23T17:13:57.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT report.BPartner_Pricelist_Version(@C_BPartner_ID@, ''Y'')',Updated=TO_TIMESTAMP('2020-03-23 19:13:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

