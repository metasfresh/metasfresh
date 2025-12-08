-- Value: Commission Forecast (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-03-13T19:41:00.570Z
UPDATE AD_Process SET Value='Commission Forecast (Excel)',Updated=TO_TIMESTAMP('2025-03-13 20:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585456
;

-- Process: Commission Forecast (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_BPartner_SalesRep_ID
-- 2025-03-13T19:41:20.583Z
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-03-13 20:41:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542936
;

-- Process: Commission Forecast (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: CommissionDate_From
-- 2025-03-13T19:42:57.497Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583515,0,585456,542938,16,'CommissionDate_From',TO_TIMESTAMP('2025-03-13 20:42:56','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Provisionsdatum von',20,TO_TIMESTAMP('2025-03-13 20:42:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:42:57.550Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542938 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Commission Forecast (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: CommissionDate_To
-- 2025-03-13T19:43:26.250Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583516,0,585456,542939,16,'CommissionDate_To',TO_TIMESTAMP('2025-03-13 20:43:25','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','N','N','Provisionsdatum bis',30,TO_TIMESTAMP('2025-03-13 20:43:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-13T19:43:26.301Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542939 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: Commission Forecast (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-03-13T19:45:18.705Z
UPDATE AD_Process SET SQLStatement='select * from de_metas_endcustomer_fresh_reports.Docs_Purchase_Commission_Forecast(@C_BPartner_SalesRep_ID@,''@CommissionDate_From@''::date,''@CommissionDate_To@''::date,  ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2025-03-13 20:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585456
;

