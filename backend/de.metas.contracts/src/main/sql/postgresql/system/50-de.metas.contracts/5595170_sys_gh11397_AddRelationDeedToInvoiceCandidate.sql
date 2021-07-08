-- 2021-06-26T12:42:44.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541353,TO_TIMESTAMP('2021-06-26 15:42:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','N','C_Invoice_Candidate_Source',TO_TIMESTAMP('2021-06-26 15:42:44','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-06-26T12:42:44.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541353 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-06-26T12:43:44.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,544906,0,541353,540270,540983,TO_TIMESTAMP('2021-06-26 15:43:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','N','N',TO_TIMESTAMP('2021-06-26 15:43:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-26T12:44:40.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541354,TO_TIMESTAMP('2021-06-26 15:44:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','N','C_Commission_Deed_Target',TO_TIMESTAMP('2021-06-26 15:44:40','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-06-26T12:44:40.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541354 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-06-26T12:45:37.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,568762,0,541354,541406,540702,TO_TIMESTAMP('2021-06-26 15:45:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','N','N',TO_TIMESTAMP('2021-06-26 15:45:37','YYYY-MM-DD HH24:MI:SS'),100,'exists(select 1               from c_invoice_candidate cand               where cand.ad_table_id = get_table_id(''C_Commission_Share'')                 and cand.record_id = C_Commission_Share.c_commission_share_id       and cand.c_invoice_candidate_id=@C_Invoice_Candidate_ID@     )')
;

-- 2021-06-26T12:46:10.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-06-26 15:46:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568762
;

-- 2021-06-26T12:47:53.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541353,541354,540291,TO_TIMESTAMP('2021-06-26 15:47:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Y','N','C_Invoice_Candidate => C_Commission_Deed',TO_TIMESTAMP('2021-06-26 15:47:53','YYYY-MM-DD HH24:MI:SS'),100)
;
