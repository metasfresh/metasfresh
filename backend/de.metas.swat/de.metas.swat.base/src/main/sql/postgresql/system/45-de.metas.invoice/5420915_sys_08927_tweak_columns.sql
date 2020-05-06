--
-- process CreateCreditMemoFromInvoice
--
-- 02.07.2015 16:39
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542843,0,504600,540716,20,'IsCreditedInvoiceReinvoicable',TO_TIMESTAMP('2015-07-02 16:39:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Wenn dieser Wert "Ja" ist und diese Gutschrift zu einer Rechnung gehÃ¶rt, dann sind die betreffenden Rechnungskandidaten erneut abrechenbar, sofern "komplett abgerechnet abw." nicht auf "Ja" gesetzt wurde.','de.metas.invoicecandidate',0,'Y','N','Y','N','Y','N','Gutgeschriebener Betrag erneut abrechenbar',40,TO_TIMESTAMP('2015-07-02 16:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 02.07.2015 16:39
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540716 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 02.07.2015 16:39
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@IsReferenceInvoice@=''Y''',Updated=TO_TIMESTAMP('2015-07-02 16:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540716
;
