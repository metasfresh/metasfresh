-- 2020-09-18T08:00:51.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541184,TO_TIMESTAMP('2020-09-18 11:00:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order(Quotation) -> C_Order',TO_TIMESTAMP('2020-09-18 11:00:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-09-18T08:00:51.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541184 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-09-18T08:01:52.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order(Quotation) -> C_Order(SO)',Updated=TO_TIMESTAMP('2020-09-18 11:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541184
;

-- 2020-09-18T08:03:37.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541184,259,143,TO_TIMESTAMP('2020-09-18 11:03:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2020-09-18 11:03:37','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from C_Order o where o.Ref_Proposal_ID=@C_Order_ID/-1@)')
;

-- 2020-09-18T08:04:00.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order_SO_Target_For_C_Order_Quotation',Updated=TO_TIMESTAMP('2020-09-18 11:04:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541184
;

-- 2020-09-18T08:07:57.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540666,541184,540260,TO_TIMESTAMP('2020-09-18 11:07:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','C_Order(Quotation) -> C_Order(SO)',TO_TIMESTAMP('2020-09-18 11:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-18T08:09:32.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.Ref_Proposal_ID=@C_Order_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID)',Updated=TO_TIMESTAMP('2020-09-18 11:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541184
;

-- 2020-09-18T08:14:49.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541185,TO_TIMESTAMP('2020-09-18 11:14:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order_Quotation_Target_For_C_Order_SO',TO_TIMESTAMP('2020-09-18 11:14:48','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-09-18T08:14:49.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541185 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-09-18T08:15:12.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,2161,0,541185,259,143,TO_TIMESTAMP('2020-09-18 11:15:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2020-09-18 11:15:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-18T08:19:32.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.Ref_Proposal_ID=@C_Order_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID and o.c_doctypetarget_id = 1000027)',Updated=TO_TIMESTAMP('2020-09-18 11:19:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541185
;

-- 2020-09-18T08:20:22.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540666,541185,540261,TO_TIMESTAMP('2020-09-18 11:20:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','C_Order(SO) -> C_Order(Quotation)',TO_TIMESTAMP('2020-09-18 11:20:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-18T08:21:31.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_Order_ID=@Ref_Proposal_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID and o.c_doctypetarget_id = 1000027)',Updated=TO_TIMESTAMP('2020-09-18 11:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541185
;

-- 2020-09-18T11:33:03.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_Order_ID=@Ref_Proposal_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID)',Updated=TO_TIMESTAMP('2020-09-18 14:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541185
;

