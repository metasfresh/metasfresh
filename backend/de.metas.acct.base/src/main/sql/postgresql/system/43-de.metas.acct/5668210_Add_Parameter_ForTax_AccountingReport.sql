-- Process: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: DateFrom
-- 2022-12-13T15:32:57.531Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,585167,542427,15,'DateFrom',TO_TIMESTAMP('2022-12-13 17:32:57.299','YYYY-MM-DD HH24:MI:SS.US'),100,'Startdatum eines Abschnittes','de.metas.acct',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',10,TO_TIMESTAMP('2022-12-13 17:32:57.299','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-12-13T15:32:57.533Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542427 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: DateTo
-- 2022-12-13T15:33:20.690Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585167,542428,15,'DateTo',TO_TIMESTAMP('2022-12-13 17:33:20.584','YYYY-MM-DD HH24:MI:SS.US'),100,'Enddatum eines Abschnittes','de.metas.acct',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','Y','N','Datum bis',20,TO_TIMESTAMP('2022-12-13 17:33:20.584','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-12-13T15:33:20.692Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542428 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: AD_Org_ID
-- 2022-12-13T15:33:42.687Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,585167,542429,30,'AD_Org_ID',TO_TIMESTAMP('2022-12-13 17:33:42.584','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','de.metas.acct',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Organisation',30,TO_TIMESTAMP('2022-12-13 17:33:42.584','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-12-13T15:33:42.688Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542429 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_VAT_Code_ID
-- 2022-12-13T15:34:07.028Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542958,0,585167,542430,30,541071,'C_VAT_Code_ID',TO_TIMESTAMP('2022-12-13 17:34:06.92','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct',0,'Y','N','Y','N','N','N','VAT Code',40,TO_TIMESTAMP('2022-12-13 17:34:06.92','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-12-13T15:34:07.029Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542430 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: Account_ID
-- 2022-12-13T15:34:35.089Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,148,0,585167,542431,30,540322,'Account_ID',TO_TIMESTAMP('2022-12-13 17:34:34.96','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto',50,TO_TIMESTAMP('2022-12-13 17:34:34.96','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-12-13T15:34:35.090Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542431 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Mehrwertsteuer-Verprobung(Jasper) 3(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: showdetails
-- 2022-12-13T15:34:56.296Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543019,0,585167,542432,20,'showdetails',TO_TIMESTAMP('2022-12-13 17:34:56.16','YYYY-MM-DD HH24:MI:SS.US'),100,'N','U',0,'Y','N','Y','N','Y','N','Show Details',60,TO_TIMESTAMP('2022-12-13 17:34:56.16','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-12-13T15:34:56.297Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542432 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

