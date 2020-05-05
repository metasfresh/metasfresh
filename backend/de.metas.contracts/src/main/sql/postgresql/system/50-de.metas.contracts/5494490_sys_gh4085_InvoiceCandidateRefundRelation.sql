-- 2018-05-24T18:17:12.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) 
VALUES (0,0,540732,540204,TO_TIMESTAMP('2018-05-24 18:17:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N','C_Invoice_Candidate -> Refund Invoice Candidates',
TO_TIMESTAMP('2018-05-24 18:17:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-24T18:18:02.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) 
VALUES (0,0,540867,TO_TIMESTAMP('2018-05-24 18:18:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_Invoice_Candidate_Refund target for C_Invoice_Candidate',
TO_TIMESTAMP('2018-05-24 18:18:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-05-24T18:18:02.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) 
SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t 
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540867 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language 
AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-05-24T18:19:33.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy)
 VALUES (0,545845,545845,0,540867,540321,540092,TO_TIMESTAMP('2018-05-24 18:19:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2018-05-24 18:19:33','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2018-05-24T18:24:43.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540867,Updated=TO_TIMESTAMP('2018-05-24 18:24:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540204
;

-- 2018-05-24T18:24:51.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2018-05-24 18:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540204
;









-- 2018-05-25T11:20:04.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL, AD_Key=544906, AD_Table_ID=540270,Updated=TO_TIMESTAMP('2018-05-25 11:20:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540867
;

-- 2018-05-25T15:21:07.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1 from C_Invoice_Candidate_Assignment ica  join C_Invoice_Candidate ic on ica.C_Invoice_Candidate_Assigned_ID = ic.C_Invoice_Candidate_ID  where ic.C_Invoice_Candidate_ID = @C_Invoice_Candidate_ID/-1@ and ica.C_Invoice_Candidate_Term_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID  )  ',Updated=TO_TIMESTAMP('2018-05-25 15:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540867
;


