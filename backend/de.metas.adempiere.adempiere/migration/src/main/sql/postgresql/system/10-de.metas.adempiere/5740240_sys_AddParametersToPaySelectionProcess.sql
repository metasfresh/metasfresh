-- Run mode: SWING_CLIENT

-- Process: C_PaySelection_CreateFrom(de.metas.banking.process.C_PaySelection_CreateFrom)
-- ParameterName: DateTrx
-- 2024-11-21T08:35:15.714Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1297,0,156,542905,15,'DateTrx',TO_TIMESTAMP('2024-11-21 10:35:15.52','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum','de.metas.banking',0,'Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','Y','N','N','N','Vorgangsdatum',80,TO_TIMESTAMP('2024-11-21 10:35:15.52','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-21T08:35:15.730Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542905 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-11-21T08:35:15.763Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(1297)
;

-- Process: C_PaySelection_CreateFrom(de.metas.banking.process.C_PaySelection_CreateFrom)
-- ParameterName: POReference
-- 2024-11-21T08:36:17.155Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,952,0,156,542906,10,'POReference',TO_TIMESTAMP('2024-11-21 10:36:17.04','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','de.metas.banking',0,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','Referenz',90,TO_TIMESTAMP('2024-11-21 10:36:17.04','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-21T08:36:17.156Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542906 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-11-21T08:36:17.158Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(952)
;

-- Name: C_Invoice C_PaySelection Invoices Not paid
-- 2024-11-21T10:24:38.548Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540696,'C_Invoice.IsPaid=''N''',TO_TIMESTAMP('2024-11-21 12:24:38.379','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','C_Invoice C_PaySelection Invoices Not paid','S',TO_TIMESTAMP('2024-11-21 12:24:38.379','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: C_Invoice C_PaySelection Invoices Not paid
-- 2024-11-21T10:25:13.812Z
DELETE FROM AD_Val_Rule WHERE AD_Val_Rule_ID=540696
;

-- 2024-11-21T10:25:17.732Z
INSERT INTO AD_Val_Rule_Included (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,AD_Val_Rule_Included_ID,Created,CreatedBy,Included_Val_Rule_ID,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540262,540042,TO_TIMESTAMP('2024-11-21 12:25:17.61','YYYY-MM-DD HH24:MI:SS.US'),100,146,'Y',0,TO_TIMESTAMP('2024-11-21 12:25:17.61','YYYY-MM-DD HH24:MI:SS.US'),100)
;

