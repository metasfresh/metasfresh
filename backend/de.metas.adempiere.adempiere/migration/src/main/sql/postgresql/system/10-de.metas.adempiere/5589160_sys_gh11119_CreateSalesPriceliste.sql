-- 2021-05-19T15:08:11.953Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584829,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2021-05-19 17:08:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','N','N','N','Y','Y','Y','@PREFIX@de/metas/reports/pricelist/report.jasper','@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper',0,'Verkaufpreisliste','json','N','Y','JasperReportsSQL',TO_TIMESTAMP('2021-05-19 17:08:11','YYYY-MM-DD HH24:MI:SS'),100,'RV_Fresh_SalesPriceList')
;

-- 2021-05-19T15:08:11.961Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584829 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-05-19T15:08:42.408Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Create Sales Price List',Updated=TO_TIMESTAMP('2021-05-19 17:08:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584829
;

-- 2021-05-19T15:08:46.704Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-19 17:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584829
;

-- 2021-05-19T15:08:50.980Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-19 17:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584829
;

-- 2021-05-19T15:09:09.625Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Verkaufspreisliste',Updated=TO_TIMESTAMP('2021-05-19 17:09:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584829
;

-- 2021-05-19T15:09:12.869Z
-- URL zum Konzept
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Verkaufspreisliste',Updated=TO_TIMESTAMP('2021-05-19 17:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584829
;

-- 2021-05-19T15:09:12.867Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Verkaufspreisliste',Updated=TO_TIMESTAMP('2021-05-19 17:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584829
;

-- 2021-05-19T15:09:26.425Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Verkaufspreisliste',Updated=TO_TIMESTAMP('2021-05-19 17:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584829
;

-- 2021-05-19T15:12:57.046Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,450,0,584829,541981,18,199,540488,'M_PriceList_Version_ID',TO_TIMESTAMP('2021-05-19 17:12:56','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT report.BPartner_Pricelist_Version(@C_BPartner_ID@, ''Y'')','Bezeichnet eine einzelne Version der Preisliste','de.metas.fresh',0,'Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','N','N','Version Preisliste',10,TO_TIMESTAMP('2021-05-19 17:12:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-19T15:12:57.049Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541981 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-19T15:16:13.116Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,584829,541982,30,'C_BPartner_ID',TO_TIMESTAMP('2021-05-19 17:16:13','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner','de.metas.fresh',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','Y','N','Geschäftspartner',20,TO_TIMESTAMP('2021-05-19 17:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-19T15:16:13.118Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541982 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-19T15:17:14.983Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-05-19 17:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541981
;

-- 2021-05-19T15:19:15.961Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,189,0,584829,541983,18,131,'C_BPartner_Location_ID',TO_TIMESTAMP('2021-05-19 17:19:15','YYYY-MM-DD HH24:MI:SS'),100,'@Default_Bill_Location_ID@','Identifiziert die (Liefer-) Adresse des Geschäftspartners','de.metas.fresh',0,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','Y','N','Standort',30,TO_TIMESTAMP('2021-05-19 17:19:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-19T15:19:15.962Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541983 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-19T15:21:38.074Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577666,0,584829,541984,20,'p_show_product_price_pi_flag',TO_TIMESTAMP('2021-05-19 17:21:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',0,'Y','N','Y','N','N','N','Preisliste Packvorschriften',50,TO_TIMESTAMP('2021-05-19 17:21:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-19T15:21:38.076Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541984 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-19T15:22:46.742Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577492,0,584829,541985,17,541097,'ReportFormat',TO_TIMESTAMP('2021-05-19 17:22:46','YYYY-MM-DD HH24:MI:SS'),100,'PDF','de.metas.fresh',16,'Y','N','Y','N','Y','N','Report format',40,TO_TIMESTAMP('2021-05-19 17:22:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-19T15:22:46.744Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541985 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-19T15:26:40.743Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584829,291,540932,TO_TIMESTAMP('2021-05-19 17:26:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2021-05-19 17:26:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

