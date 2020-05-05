-- 2018-07-24T18:06:35.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540744, IsDirected='Y', Name='C_Invoice (Purchase) -> C_PaySelection',Updated=TO_TIMESTAMP('2018-07-24 18:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540148
;

-- 2018-07-24T18:07:08.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540626
;

-- 2018-07-24T18:07:08.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540626
;

-- 2018-07-24T18:08:32.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540876,TO_TIMESTAMP('2018-07-24 18:08:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','PaySelection Target for C_Invoice (Purchase)',TO_TIMESTAMP('2018-07-24 18:08:32','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-24T18:08:32.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540876 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-24T18:13:58.027
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,5609,5609,0,540876,426,TO_TIMESTAMP('2018-07-24 18:13:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2018-07-24 18:13:58','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from C_PaySelectionLine  psl join C_Invoice i on psl.C_Invoice_ID = i.C_Invoice_ID where i.IsSOTrx = ''N'' and C_PaySelection.C_PaySelection_ID = psl.C_PaySelection_ID and i.C_Invoice_ID = @C_Invoice_ID@)')
;

-- 2018-07-24T18:15:38.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_PaySelection Target for C_Invoice (Purchase)',Updated=TO_TIMESTAMP('2018-07-24 18:15:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540876
;

-- 2018-07-24T18:15:50.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540876,Updated=TO_TIMESTAMP('2018-07-24 18:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540148
;

-- 2018-07-24T18:15:55.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540627
;

-- 2018-07-24T18:15:55.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540627
;

