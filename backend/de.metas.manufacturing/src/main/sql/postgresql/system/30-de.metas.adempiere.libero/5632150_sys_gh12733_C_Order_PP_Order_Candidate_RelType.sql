-- 2022-03-28T10:36:35.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541572,TO_TIMESTAMP('2022-03-28 13:36:34','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','RelType C_Order (SO) -> PP_Order_Candidate',TO_TIMESTAMP('2022-03-28 13:36:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-03-28T10:36:35.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541572 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-03-28T10:37:40.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,577875,0,541572,541913,541316,TO_TIMESTAMP('2022-03-28 13:37:40','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N',TO_TIMESTAMP('2022-03-28 13:37:40','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from pp_order_candidate cand             inner join c_orderline line on cand.c_orderline_id = line.c_orderline_id             inner join c_order so on so.c_order_id = line.c_order_id where so.c_order_id = @C_Order_ID@)')
;

-- 2022-03-28T10:39:00.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540100,541572,540345,TO_TIMESTAMP('2022-03-28 13:39:00','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','C_Order (SO) -> PP_Order_Candidate',TO_TIMESTAMP('2022-03-28 13:39:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-28T10:45:30.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from pp_order_candidate cand               inner join c_orderline line on cand.c_orderline_id = line.c_orderline_id             inner join c_order so on so.c_order_id = line.c_order_id where line.c_order_id = @C_Order_ID@)',Updated=TO_TIMESTAMP('2022-03-28 13:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541572
;

-- 2022-03-28T11:15:55.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='PP_Order_Candidate_ID in (select cand.pp_order_candidate_id from pp_order_candidate cand         inner join c_orderline line on line.c_orderline_id = cand.c_orderline_id where line.C_Order_ID = @C_Order_ID@)',Updated=TO_TIMESTAMP('2022-03-28 14:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541572
;