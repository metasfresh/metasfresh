-- 2017-11-24T13:37:48.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540196,TO_TIMESTAMP('2017-11-24 13:37:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','C_Order (SO) -> C_PurchaseCandidate',TO_TIMESTAMP('2017-11-24 13:37:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T13:39:00.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540666,Updated=TO_TIMESTAMP('2017-11-24 13:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540196
;

-- 2017-11-24T13:39:44.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540766,TO_TIMESTAMP('2017-11-24 13:39:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','N','C_PurchaseCandidate_Target',TO_TIMESTAMP('2017-11-24 13:39:43','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-11-24T13:39:44.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540766 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-11-24T13:51:36.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,557857,0,540766,540861,TO_TIMESTAMP('2017-11-24 13:51:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','N',TO_TIMESTAMP('2017-11-24 13:51:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T13:54:29.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (  select 1 from C_PurchaseCandidate pc  join C_Order o on pc.C_OrderSO_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''Y'' and C_PurchaseCandidate.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate ) ',Updated=TO_TIMESTAMP('2017-11-24 13:54:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540766
;

-- 2017-11-24T14:04:18.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540766,Updated=TO_TIMESTAMP('2017-11-24 14:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540196
;

-- 2017-11-24T14:04:25.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.purchasecandidate',Updated=TO_TIMESTAMP('2017-11-24 14:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540196
;

-- 2017-11-24T14:24:32.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540375,Updated=TO_TIMESTAMP('2017-11-24 14:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540766
;

-- 2017-11-24T14:26:58.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (  select 1 from C_PurchaseCandidate pc  join C_Order o on pc.C_OrderSO_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''Y'' and C_PurchaseCandidate.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID ) ',Updated=TO_TIMESTAMP('2017-11-24 14:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540766
;

-- 2017-11-24T15:51:34.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (  select 1 from C_PurchaseCandidate pc  join C_Order o on pc.C_OrderSO_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrxh = ''Y'' and C_PurchaseCandidate.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID ) ',Updated=TO_TIMESTAMP('2017-11-24 15:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540766
;

-- 2017-11-24T15:53:04.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (  select 1 from C_PurchaseCandidate pc  join C_Order o on pc.C_OrderSO_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''Y'' and C_PurchaseCandidate.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID ) ',Updated=TO_TIMESTAMP('2017-11-24 15:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540766
;

-- 2017-11-24T16:04:25.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=557857,Updated=TO_TIMESTAMP('2017-11-24 16:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540766
;

