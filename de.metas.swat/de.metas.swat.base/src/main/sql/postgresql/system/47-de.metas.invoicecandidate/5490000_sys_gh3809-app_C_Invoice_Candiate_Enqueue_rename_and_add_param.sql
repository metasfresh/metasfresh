-- 2018-04-05T08:07:31.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing', Value='C_Invoice_Candidate_EnqueueSelectionForInvoicing',Updated=TO_TIMESTAMP('2018-04-05 08:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540304
;

-- 2018-04-05T08:08:18.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540304,541281,20,'SupplementMissingPaymentTermIds',TO_TIMESTAMP('2018-04-05 08:08:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','Legt fest, ob bei RKn mit leeren Zahlungsbedingung (z.B. aus Leergutrücknahmen) automatisch die Zahlungsbedingung eines anderen RK übernommen werden soll.','de.metas.invoicecandidate',0,'Durch die Übernahme der Zahlungsbedingung können betroffenen RKn zusammen mit anderen RKn in eine gemeinsame Rechnung übernommen werden.
Sollten bei den ausgewählten RKn mehrere Zahlungsbedingungen vorkommen, dann wird die Zahlungsbedingung des ältesten ausgewählten Rechnungskandidaten übernommen.','Y','N','Y','N','Y','N','Leere RK-Zahlungsbedingungen Nachtragen',50,TO_TIMESTAMP('2018-04-05 08:08:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-05T08:08:18.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541281 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-04-05T08:08:26.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-05 08:08:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_Para_ID=541281 AND AD_Language='de_CH'
;

-- 2018-04-05T08:08:43.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-05 08:08:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Supplement missing payment terms',Description='Specifies whether invoice candidate that have no payment term shall be updated with the reference of another selected invoice candidate.',Help='By performing this update, those invoice candidates can be aggregated onto one invoice together with other invoice candidates.' WHERE AD_Process_Para_ID=541281 AND AD_Language='en_US'
;

-- 2018-04-05T08:08:49.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsBetaFunctionality='N',Updated=TO_TIMESTAMP('2018-04-05 08:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540304
;

