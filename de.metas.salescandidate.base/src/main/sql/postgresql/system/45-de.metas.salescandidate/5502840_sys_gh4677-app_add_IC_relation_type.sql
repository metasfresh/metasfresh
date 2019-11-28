
-- 2018-10-09T07:36:38.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (Created,CreatedBy,IsActive,AD_Client_ID,Updated,UpdatedBy,AD_RelationType_ID,AD_Reference_Target_ID,AD_Org_ID,Name,IsTableRecordIdTarget,IsDirected,EntityType) VALUES (TO_TIMESTAMP('2018-10-09 07:36:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,TO_TIMESTAMP('2018-10-09 07:36:38','YYYY-MM-DD HH24:MI:SS'),100,540221,540266,0,'C_Invoice_Candidate_TableRecordIdTarget','Y','N','de.metas.swat')
;

-- 2018-10-09T07:36:56.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2018-10-09 07:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540221
;


