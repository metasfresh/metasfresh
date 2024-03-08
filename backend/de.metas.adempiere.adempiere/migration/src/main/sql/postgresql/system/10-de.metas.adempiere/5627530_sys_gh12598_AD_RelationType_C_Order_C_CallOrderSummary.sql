-- 2022-02-24T11:17:14.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541549,TO_TIMESTAMP('2022-02-24 13:17:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_Order_with_lines_with_C_CallOrderSummary_ID',TO_TIMESTAMP('2022-02-24 13:17:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-02-24T11:17:14.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541549 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-02-24T11:18:31.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541549,259,143,TO_TIMESTAMP('2022-02-24 13:18:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2022-02-24 13:18:31','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx=''Y'' AND EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ AND ol.C_CallOrderSummary_ID IS NOT NULL)')
;

-- 2022-02-24T11:19:31.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541550,TO_TIMESTAMP('2022-02-24 13:19:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_CallOrderSummary_For_C_Order_CallOrder',TO_TIMESTAMP('2022-02-24 13:19:31','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-02-24T11:19:31.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541550 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-02-24T11:26:58.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,579296,0,541550,541984,TO_TIMESTAMP('2022-02-24 13:26:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2022-02-24 13:26:58','YYYY-MM-DD HH24:MI:SS'),100,'C_CallOrderSummary_ID IN ( select s.C_CallOrderSummary_ID from C_CallOrderSummary s inner join C_OrderLine ol on ol.C_CallOrderSummary_ID = s.C_CallOrderSummary_ID)')
;

-- 2022-02-24T11:27:33.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=541430,Updated=TO_TIMESTAMP('2022-02-24 13:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541550
;

-- 2022-02-24T11:29:00.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541549,540340,TO_TIMESTAMP('2022-02-24 13:29:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order -> C_CallOrderSummary',TO_TIMESTAMP('2022-02-24 13:29:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-24T11:29:12.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541550,Updated=TO_TIMESTAMP('2022-02-24 13:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540340
;

-- 2022-02-24T11:29:19.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2022-02-24 13:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540340
;

-- 2022-02-24T11:39:55.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_CallOrderSummary_ID IN (select s.C_CallOrderSummary_ID from C_CallOrderSummary s inner join C_Flatrate_Term t on t.c_flatrate_term_id = s.c_flatrate_term_id inner join C_OrderLine ol on ol.c_flatrate_term_id = s.c_flatrate_term_id)',Updated=TO_TIMESTAMP('2022-02-24 13:39:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541550
;

-- 2022-02-24T11:42:42.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='IsSOTrx=''Y'' AND EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ AND ol.C_Flatrate_Term_ID IS NOT NULL)',Updated=TO_TIMESTAMP('2022-02-24 13:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541549
;

