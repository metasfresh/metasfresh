-- 2018-07-24T18:46:49.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540878,TO_TIMESTAMP('2018-07-24 18:46:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','N','C_Printing_Queue Source',TO_TIMESTAMP('2018-07-24 18:46:49','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-24T18:46:49.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540878 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-24T18:48:51.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,548048,0,540878,540435,540165,TO_TIMESTAMP('2018-07-24 18:48:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','N',TO_TIMESTAMP('2018-07-24 18:48:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-24T18:49:18.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540878,540207,TO_TIMESTAMP('2018-07-24 18:49:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Printing_Queue -> C_Print_Job',TO_TIMESTAMP('2018-07-24 18:49:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-24T18:49:52.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540879,TO_TIMESTAMP('2018-07-24 18:49:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','N','C_Print_Job Target for C_Print_Queue',TO_TIMESTAMP('2018-07-24 18:49:52','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-24T18:49:52.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540879 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-24T18:54:29.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,548068,548068,0,540879,540437,TO_TIMESTAMP('2018-07-24 18:54:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','N',TO_TIMESTAMP('2018-07-24 18:54:29','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from C_Print_Job_Line pjl join C_Printing_Queue pq on pjl.C_Printing_Queue_ID = pq.C_Printing_Queue_ID where C_Print_Job.C_Print_Job_ID = pjl.C_Print_Job_ID and pq.C_Printing_Queue_ID = @C_Printing_Queue_ID@)')
;

-- 2018-07-24T18:55:01.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540879, EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2018-07-24 18:55:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540207
;

-- 2018-07-30T17:59:00.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540879,Updated=TO_TIMESTAMP('2018-07-30 17:59:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540207
;

