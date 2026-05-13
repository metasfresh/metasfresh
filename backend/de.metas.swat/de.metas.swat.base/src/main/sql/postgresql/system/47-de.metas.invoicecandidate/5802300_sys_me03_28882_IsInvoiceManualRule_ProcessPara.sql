-- 2026-05-13T16:00:00.000Z
-- me03#28882 — Add AD_Element for the new 'IsInvoiceManualRule' process parameter
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584862 /*From ID Server*/,0,'IsInvoiceManualRule',TO_TIMESTAMP('2026-05-13 16:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','Manuelle Rechnungskandidaten fakturieren','Manuelle Rechnungskandidaten fakturieren',TO_TIMESTAMP('2026-05-13 16:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-05-13T16:00:01.000Z
-- me03#28882 — Seed AD_Element_Trl rows for all system languages and the base language
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584862 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-05-13T16:00:02.000Z
-- me03#28882 — English (en_US) translation for the new element
UPDATE AD_Element_Trl SET Name='Invoice Manual-rule candidates',PrintName='Invoice Manual-rule candidates',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 16:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=584862
;

-- 2026-05-13T16:00:03.000Z
-- me03#28882 — Add the new 'IsInvoiceManualRule' boolean parameter to process C_Invoice_Candidate_EnqueueSelectionForInvoicing (AD_Process_ID=540304).
-- When the user ticks it, the schedule-skip filter no longer skips invoice candidates with InvoiceRule=Manual (which carry DateToInvoice=NULL).
-- Decoupled from IgnoreInvoiceSchedule so the two flags can be set independently.
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584862,0,540304,543195 /*From ID Server*/,20,'IsInvoiceManualRule',TO_TIMESTAMP('2026-05-13 16:00:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',1,'Y','N','Y','N','N','N','Manuelle Rechnungskandidaten fakturieren',25,TO_TIMESTAMP('2026-05-13 16:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-05-13T16:00:04.000Z
-- me03#28882 — Seed AD_Process_Para_Trl rows for all system languages and the base language
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543195 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-05-13T16:00:05.000Z
-- me03#28882 — English (en_US) translation for the process parameter
UPDATE AD_Process_Para_Trl SET Name='Invoice Manual-rule candidates',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 16:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=543195
;

-- 2026-05-13T16:00:06.000Z
-- me03#28882 — Backfill translations for any other active languages (idempotent)
SELECT add_missing_translations()
;
