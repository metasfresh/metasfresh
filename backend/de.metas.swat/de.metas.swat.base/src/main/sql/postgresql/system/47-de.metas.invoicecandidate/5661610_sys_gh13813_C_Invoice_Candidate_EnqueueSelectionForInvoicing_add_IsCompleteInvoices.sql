-- 2022-10-24T09:41:05.410Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581600,0,'IsCompleteInvoices',TO_TIMESTAMP('2022-10-24 11:41:05','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob die neu erstellten Rechungen fertig gestellt oder nur vorbereitet werden sollen.','de.metas.invoicecandidate','Y','Rechnungen fertigstellen','Rechnungen fertigstellen',TO_TIMESTAMP('2022-10-24 11:41:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-24T09:41:05.424Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581600 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsCompleteInvoices
-- 2022-10-24T09:41:09.351Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-24 11:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581600 AND AD_Language='de_CH'
;

-- 2022-10-24T09:41:09.397Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581600,'de_CH') 
;

-- Element: IsCompleteInvoices
-- 2022-10-24T09:41:11.881Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-24 11:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581600 AND AD_Language='de_DE'
;

-- 2022-10-24T09:41:11.892Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581600,'de_DE') 
;

-- 2022-10-24T09:41:11.903Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581600,'de_DE') 
;

-- Element: IsCompleteInvoices
-- 2022-10-24T09:42:09.043Z
UPDATE AD_Element_Trl SET Description='Specifies whether the newly created invoices should be completed or only prepared.', IsTranslated='Y', Name='Complete invoices', PrintName='Complete invoices',Updated=TO_TIMESTAMP('2022-10-24 11:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581600 AND AD_Language='en_US'
;

-- 2022-10-24T09:42:09.061Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581600,'en_US') 
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: IsCompleteInvoices
-- 2022-10-24T09:42:43.545Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581600,0,540304,542337,20,'IsCompleteInvoices',TO_TIMESTAMP('2022-10-24 11:42:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','Legt fest, ob die neu erstellten Rechungen fertig gestellt oder nur vorbereitet werden sollen.','de.metas.invoicecandidate',0,'Y','N','Y','N','Y','N','Rechnungen fertigstellen',90,TO_TIMESTAMP('2022-10-24 11:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-24T09:42:43.553Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542337 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: IsCompleteInvoices
-- 2022-10-24T10:01:30.743Z
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=50,Updated=TO_TIMESTAMP('2022-10-24 12:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542337
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: DateInvoiced
-- 2022-10-24T10:01:30.803Z
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=60,Updated=TO_TIMESTAMP('2022-10-24 12:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540424
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: DateAcct
-- 2022-10-24T10:01:30.843Z
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=70,Updated=TO_TIMESTAMP('2022-10-24 12:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540640
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: POReference
-- 2022-10-24T10:01:30.883Z
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=80,Updated=TO_TIMESTAMP('2022-10-24 12:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540610
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: Check_NetAmtToInvoice
-- 2022-10-24T10:01:30.932Z
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=90,Updated=TO_TIMESTAMP('2022-10-24 12:01:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540653
;

