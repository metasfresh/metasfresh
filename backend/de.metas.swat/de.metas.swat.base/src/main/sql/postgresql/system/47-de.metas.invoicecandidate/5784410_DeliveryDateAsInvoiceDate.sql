-- Run mode: SWING_CLIENT

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: IsDeliveryDateAsInvoiceDate
-- 2026-01-19T12:42:16.860Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540304,543098,20,'IsDeliveryDateAsInvoiceDate',TO_TIMESTAMP('2026-01-19 12:42:16.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Legt fest, ob das Lieferdatum bei der Rechnungserstelung automatisch als Rechnungsdatum verwendet wird','de.metas.invoicecandidate',0,'','Y','N','Y','N','N','N','Lieferdatum als Rechnungsdatum',55,TO_TIMESTAMP('2026-01-19 12:42:16.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-19T12:42:16.862Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543098 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-01-19T12:42:55.332Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584412,0,'IsDeliveryDateAsInvoiceDate',TO_TIMESTAMP('2026-01-19 12:42:55.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Legt fest, ob das Lieferdatum bei der Rechnungserstelung automatisch als Rechnungsdatum verwendet wird','de.metas.invoicecandidate','Y','Lieferdatum als Rechnungsdatum','Lieferdatum als Rechnungsdatum',TO_TIMESTAMP('2026-01-19 12:42:55.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-19T12:42:55.334Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584412 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsDeliveryDateAsInvoiceDate
-- 2026-01-19T12:43:12.147Z
UPDATE AD_Element_Trl SET Description='Specifies whether the delivery date is automatically used as the invoice date when creating invoices.', Name='Delivery Date as Invoice Date', PrintName='Delivery Date as Invoice Date',Updated=TO_TIMESTAMP('2026-01-19 12:43:12.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584412 AND AD_Language='en_US'
;

-- 2026-01-19T12:43:12.148Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-19T12:43:12.463Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584412,'en_US')
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: IsDeliveryDateAsInvoiceDate
-- 2026-01-19T12:43:27.570Z
UPDATE AD_Process_Para SET AD_Element_ID=584412,Updated=TO_TIMESTAMP('2026-01-19 12:43:27.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543098
;

-- Element: IsDeliveryDateAsInvoiceDate
-- 2026-01-19T12:43:59.527Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-19 12:43:59.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584412 AND AD_Language='en_US'
;

-- 2026-01-19T12:43:59.529Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584412,'en_US')
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: IsDeliveryDateAsInvoiceDate
-- 2026-01-19T12:44:13.229Z
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2026-01-19 12:44:13.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543098
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: IsDeliveryDateAsInvoiceDate
-- 2026-01-19T12:44:15.091Z
UPDATE AD_Process_Para SET IsCentrallyMaintained='Y',Updated=TO_TIMESTAMP('2026-01-19 12:44:15.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543098
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: DateInvoiced
-- 2026-01-20T07:03:37.520Z
UPDATE AD_Process_Para SET DisplayLogic='@IsDeliveryDateAsInvoiceDate@ ! ''Y''',Updated=TO_TIMESTAMP('2026-01-20 07:03:37.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540424
;
