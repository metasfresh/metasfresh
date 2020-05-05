-- 2019-12-12T14:54:19.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541229,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2019-12-12 16:54:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y','@PREFIX@de/metas/reports/pricelist/report.jasper','@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper',0,'Preisliste','N','Y','Java',TO_TIMESTAMP('2019-12-12 16:54:18','YYYY-MM-DD HH24:MI:SS'),100,'RV_Pricelist (Excel)')
;

-- 2019-12-12T14:54:19.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541229 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-12-12T14:54:34.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541229,291,540766,TO_TIMESTAMP('2019-12-12 16:54:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-12-12 16:54:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2019-12-12T14:54:45.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='RV_Fresh_PriceList (Excel)',Updated=TO_TIMESTAMP('2019-12-12 16:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541229
;

-- 2019-12-12T14:55:26.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541230,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2019-12-12 16:55:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','N','Y','Y','@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper','',0,'Preisliste','N','Y','JasperReportsSQL',TO_TIMESTAMP('2019-12-12 16:55:26','YYYY-MM-DD HH24:MI:SS'),100,'RV_Fresh_PriceList_ExcelReport')
;

-- 2019-12-12T14:55:26.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541230 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-12-12T14:56:41.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,450,0,541230,541645,18,199,540243,'M_PriceList_Version_ID',TO_TIMESTAMP('2019-12-12 16:56:40','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@)','Bezeichnet eine einzelne Version der Preisliste','D',0,'Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','Y','N','Version Preisliste',10,TO_TIMESTAMP('2019-12-12 16:56:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T14:56:41.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541645 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-12T14:56:41.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541645
;

-- 2019-12-12T14:56:41.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541645, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540492
;

-- 2019-12-12T14:56:41.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,541230,541646,30,'C_BPartner_ID',TO_TIMESTAMP('2019-12-12 16:56:41','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner','D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','Y','N','Geschäftspartner',20,TO_TIMESTAMP('2019-12-12 16:56:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T14:56:41.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541646 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-12T14:56:41.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541646
;

-- 2019-12-12T14:56:41.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541646, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540493
;

-- 2019-12-12T14:56:41.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,189,0,541230,541647,18,131,'C_BPartner_Location_ID',TO_TIMESTAMP('2019-12-12 16:56:41','YYYY-MM-DD HH24:MI:SS'),100,'@Default_Bill_Location_ID@','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,'Identifiziert die Adresse des Geschäftspartners','Y','Y','Y','N','Y','N','Standort',30,TO_TIMESTAMP('2019-12-12 16:56:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T14:56:41.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541647 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-12T14:56:41.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541647
;

-- 2019-12-12T14:56:41.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541647, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540549
;

-- 2019-12-12T14:57:25.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,450,0,541229,541648,18,199,540243,'M_PriceList_Version_ID',TO_TIMESTAMP('2019-12-12 16:57:25','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@)','Bezeichnet eine einzelne Version der Preisliste','D',0,'Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','Y','N','Version Preisliste',10,TO_TIMESTAMP('2019-12-12 16:57:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T14:57:25.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541648 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-12T14:57:25.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541648
;

-- 2019-12-12T14:57:25.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541648, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540492
;

-- 2019-12-12T14:57:25.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,541229,541649,30,'C_BPartner_ID',TO_TIMESTAMP('2019-12-12 16:57:25','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner','D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','Y','N','Geschäftspartner',20,TO_TIMESTAMP('2019-12-12 16:57:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T14:57:25.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541649 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-12T14:57:25.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541649
;

-- 2019-12-12T14:57:25.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541649, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540493
;

-- 2019-12-12T14:57:25.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,189,0,541229,541650,18,131,'C_BPartner_Location_ID',TO_TIMESTAMP('2019-12-12 16:57:25','YYYY-MM-DD HH24:MI:SS'),100,'@Default_Bill_Location_ID@','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,'Identifiziert die Adresse des Geschäftspartners','Y','Y','Y','N','Y','N','Standort',30,TO_TIMESTAMP('2019-12-12 16:57:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-12T14:57:25.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541650 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-12T14:57:25.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541650
;

-- 2019-12-12T14:57:25.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541650, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540549
;

-- 2019-12-12T15:16:38.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Preisliste (Excel)',Updated=TO_TIMESTAMP('2019-12-12 17:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541230
;

-- 2019-12-12T15:23:01.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste (Excel)',Updated=TO_TIMESTAMP('2019-12-12 17:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541230
;

-- 2019-12-12T15:23:13.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preislist (Excel)',Updated=TO_TIMESTAMP('2019-12-12 17:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541230
;

-- 2019-12-12T15:23:21.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste (Excel)',Updated=TO_TIMESTAMP('2019-12-12 17:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541230
;

-- 2019-12-12T15:24:38.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste (Excel)',Updated=TO_TIMESTAMP('2019-12-12 17:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541229
;

-- 2019-12-12T15:24:45.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preislist (Excel)',Updated=TO_TIMESTAMP('2019-12-12 17:24:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541229
;

-- 2019-12-12T15:24:50.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste (Excel)',Updated=TO_TIMESTAMP('2019-12-12 17:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541229
;

-- 2019-12-12T15:24:59.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Preisliste (Excel)',Updated=TO_TIMESTAMP('2019-12-12 17:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541229
;

-- 2019-12-12T15:28:06.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='', JasperReport_Tabular='@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper',Updated=TO_TIMESTAMP('2019-12-12 17:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541230
;

-- 2019-12-12T15:30:29.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Preisliste Excel Report',Updated=TO_TIMESTAMP('2019-12-12 17:30:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541230
;

-- 2019-12-12T15:30:32.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste Excel Report',Updated=TO_TIMESTAMP('2019-12-12 17:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541230
;

-- 2019-12-12T15:30:34.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste Excel Report',Updated=TO_TIMESTAMP('2019-12-12 17:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541230
;

-- 2019-12-12T15:30:36.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste Excel Report',Updated=TO_TIMESTAMP('2019-12-12 17:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541230
;

-- 2019-12-12T15:32:03.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.pricing.process.M_Pricelist_Excel',Updated=TO_TIMESTAMP('2019-12-12 17:32:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541229
;

-- 2019-12-12T15:33:10.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper', JasperReport_Tabular='',Updated=TO_TIMESTAMP('2019-12-12 17:33:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541230
;




-- 2019-12-13T13:13:41.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Preisliste Excel Auftrag',Updated=TO_TIMESTAMP('2019-12-13 15:13:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541229
;

-- 2019-12-13T13:13:45.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Preisliste Excel Auftrag',Updated=TO_TIMESTAMP('2019-12-13 15:13:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541229
;

-- 2019-12-13T13:13:51.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Preisliste Excel Auftrag',Updated=TO_TIMESTAMP('2019-12-13 15:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541229
;





