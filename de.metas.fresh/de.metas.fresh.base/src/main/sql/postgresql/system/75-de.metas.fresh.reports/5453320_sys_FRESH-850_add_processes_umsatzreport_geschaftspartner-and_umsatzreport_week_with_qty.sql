-- 14.11.2016 11:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540740,'Y','org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2016-11-14 11:21:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','N','N','Y','N','Y','@PREFIX@de/metas/reports/umsatzreport_bpartner_with_qty/report.jasper','@PREFIX@de/metas/reports/umsatzreport_bpartner_with_qty/report_TabularView.jasper',0,'Umsatzreport Geschäftspartner mit Menge','N','Y',0,0,'Java',TO_TIMESTAMP('2016-11-14 11:21:58','YYYY-MM-DD HH24:MI:SS'),100,'Umsatzreport Geschäftspartner mit Menge (Jasper)')
;

-- 14.11.2016 11:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540740 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 14.11.2016 11:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540740,541053,15,'Base_Period_Start',TO_TIMESTAMP('2016-11-14 11:22:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','Y','N','Zeitperiode von',10,TO_TIMESTAMP('2016-11-14 11:22:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541053 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540740,541054,15,'Base_Period_End',TO_TIMESTAMP('2016-11-14 11:23:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','Y','N','Zeitperiode bis',20,TO_TIMESTAMP('2016-11-14 11:23:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541054 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540740,541055,15,'Comp_Period_Start',TO_TIMESTAMP('2016-11-14 11:23:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Vergleichsperiode von',30,TO_TIMESTAMP('2016-11-14 11:23:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541055 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540740,541056,15,'Comp_Period_End',TO_TIMESTAMP('2016-11-14 11:23:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Vergleichsperiode bis',40,TO_TIMESTAMP('2016-11-14 11:23:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541056 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1106,0,540740,541057,20,'IsSOTrx',TO_TIMESTAMP('2016-11-14 11:24:28','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction','de.metas.fresh',0,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','Y','N','Verkaufstransaktion',50,TO_TIMESTAMP('2016-11-14 11:24:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541057 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,540740,541058,19,'C_BPartner_ID',TO_TIMESTAMP('2016-11-14 11:24:46','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','de.metas.fresh',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',60,TO_TIMESTAMP('2016-11-14 11:24:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541058 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1005,0,540740,541059,19,'C_Activity_ID',TO_TIMESTAMP('2016-11-14 11:25:01','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','de.metas.fresh',0,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','Kostenstelle',70,TO_TIMESTAMP('2016-11-14 11:25:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541059 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,540740,541060,19,'M_Product_ID',TO_TIMESTAMP('2016-11-14 11:25:21','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.fresh',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',80,TO_TIMESTAMP('2016-11-14 11:25:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541060 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,453,0,540740,541061,19,'M_Product_Category_ID',TO_TIMESTAMP('2016-11-14 11:25:35','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','de.metas.fresh',0,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','Produkt-Kategorie',90,TO_TIMESTAMP('2016-11-14 11:25:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541061 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2019,0,540740,541062,35,'M_AttributeSetInstance_ID',TO_TIMESTAMP('2016-11-14 11:25:53','YYYY-MM-DD HH24:MI:SS'),100,'Instanz des Merkmals-Satzes zum Produkt','de.metas.fresh',0,'The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','N','Ausprägung Merkmals-Satz',100,TO_TIMESTAMP('2016-11-14 11:25:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541062 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540741,'Y','org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2016-11-14 11:27:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','N','N','Y','N','Y','@PREFIX@de/metas/reports/umsatzreport_bpartner_week_with_qty/report.jasper','@PREFIX@de/metas/reports/umsatzreport_bpartner_week_with_qty/report_TabularView.jasper',0,'Umsatzreport Geschäftspartner Woche mit Menge','N','Y',0,0,'Java',TO_TIMESTAMP('2016-11-14 11:27:38','YYYY-MM-DD HH24:MI:SS'),100,'Umsatzreport Geschäftspartner Woche mit Menge (Jasper)')
;

-- 14.11.2016 11:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540741 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 14.11.2016 11:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,223,0,540741,541063,18,540133,'C_Year_ID',TO_TIMESTAMP('2016-11-14 11:28:10','YYYY-MM-DD HH24:MI:SS'),100,'Kalenderjahr','de.metas.fresh',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','Y','N','Jahr',10,TO_TIMESTAMP('2016-11-14 11:28:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541063 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin) VALUES (0,543077,0,540741,541064,11,'Base_Week_Start',TO_TIMESTAMP('2016-11-14 11:28:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','Y','N','Woche von',20,TO_TIMESTAMP('2016-11-14 11:28:55','YYYY-MM-DD HH24:MI:SS'),100,'53','1')
;

-- 14.11.2016 11:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541064 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,223,0,540741,541065,18,540133,'Base_Year_End',TO_TIMESTAMP('2016-11-14 11:29:39','YYYY-MM-DD HH24:MI:SS'),100,'Kalenderjahr','de.metas.fresh',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','Y','N','Jahr bis',30,TO_TIMESTAMP('2016-11-14 11:29:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541065 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin) VALUES (0,543077,0,540741,541066,11,'Base_Week_End',TO_TIMESTAMP('2016-11-14 11:30:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','Y','N','Woche bis',40,TO_TIMESTAMP('2016-11-14 11:30:15','YYYY-MM-DD HH24:MI:SS'),100,'53','1')
;

-- 14.11.2016 11:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541066 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Name='Jahr von',Updated=TO_TIMESTAMP('2016-11-14 11:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541063
;

-- 14.11.2016 11:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541063
;

-- 14.11.2016 11:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,223,0,540741,541067,18,540133,'Comp_Year_Start',TO_TIMESTAMP('2016-11-14 11:31:13','YYYY-MM-DD HH24:MI:SS'),100,'Kalenderjahr','de.metas.fresh',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','N','N','Vergleich Jahr von',50,TO_TIMESTAMP('2016-11-14 11:31:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541067 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin) VALUES (0,543077,0,540741,541068,11,'Comp_Week_Start',TO_TIMESTAMP('2016-11-14 11:31:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Vergleich Woche von',60,TO_TIMESTAMP('2016-11-14 11:31:49','YYYY-MM-DD HH24:MI:SS'),100,'53','1')
;

-- 14.11.2016 11:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541068 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,223,0,540741,541069,18,540133,'Comp_Year_End',TO_TIMESTAMP('2016-11-14 11:32:12','YYYY-MM-DD HH24:MI:SS'),100,'Kalenderjahr','de.metas.fresh',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','N','N','Vergleich Jahr bis',70,TO_TIMESTAMP('2016-11-14 11:32:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541069 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin) VALUES (0,543077,0,540741,541070,11,'Comp_Week_End',TO_TIMESTAMP('2016-11-14 11:32:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Vergleich Woche bis',80,TO_TIMESTAMP('2016-11-14 11:32:33','YYYY-MM-DD HH24:MI:SS'),100,'53','1')
;

-- 14.11.2016 11:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541070 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,193,0,540741,541071,19,'C_Currency_ID',TO_TIMESTAMP('2016-11-14 11:33:33','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT C_Currency_ID AS DefaultValue FROM C_Currency WHERE iso_code = ''CHF''','Die Währung für diesen Eintrag','de.metas.fresh',0,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','Y','N','Währung',90,TO_TIMESTAMP('2016-11-14 11:33:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541071 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1106,0,540741,541072,20,'IsSOTrx',TO_TIMESTAMP('2016-11-14 11:33:55','YYYY-MM-DD HH24:MI:SS'),100,'This is a Sales Transaction','de.metas.fresh',0,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','Sales Transaction',100,TO_TIMESTAMP('2016-11-14 11:33:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541072 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,540741,541073,30,'C_BPartner_ID',TO_TIMESTAMP('2016-11-14 11:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','de.metas.fresh',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',110,TO_TIMESTAMP('2016-11-14 11:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541073 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,540741,541074,19,'M_Product_ID',TO_TIMESTAMP('2016-11-14 11:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.fresh',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',120,TO_TIMESTAMP('2016-11-14 11:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541074 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,453,0,540741,541075,19,'M_Product_Category_ID',TO_TIMESTAMP('2016-11-14 11:34:40','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','de.metas.fresh',0,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','Produkt-Kategorie',130,TO_TIMESTAMP('2016-11-14 11:34:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541075 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2019,0,540741,541076,35,'M_AttributeSetInstance_ID',TO_TIMESTAMP('2016-11-14 11:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Instanz des Merkmals-Satzes zum Produkt','de.metas.fresh',0,'The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','N','Ausprägung Merkmals-Satz',140,TO_TIMESTAMP('2016-11-14 11:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541076 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,540739,0,540740,TO_TIMESTAMP('2016-11-14 11:37:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Umsatzreport Geschäftspartner mit Menge (Jasper)','Y','N','N','N','N','Umsatzreport Geschäftspartner mit Menge',TO_TIMESTAMP('2016-11-14 11:37:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540739 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540739, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540739)
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 14.11.2016 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,540740,0,540741,TO_TIMESTAMP('2016-11-14 11:38:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Umsatzreport Geschäftspartner Woche mit Menge (Jasper)','Y','N','N','N','N','Umsatzreport Geschäftspartner Woche mit Menge',TO_TIMESTAMP('2016-11-14 11:38:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540740 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540740, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540740)
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 14.11.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 14.11.2016 11:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Base_Year_Start',Updated=TO_TIMESTAMP('2016-11-14 11:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541063
;

