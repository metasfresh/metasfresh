-- 2017-11-24T16:18:07.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540198,TO_TIMESTAMP('2017-11-24 16:18:07','YYYY-MM-DD HH24:MI:SS'),100,'via C_PurchaseCandidate','de.metas.swat','Y','N','C_Order (SO) -> C_Order (PO) ',TO_TIMESTAMP('2017-11-24 16:18:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T16:18:19.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540666, EntityType='de.metas.purchasecandidate',Updated=TO_TIMESTAMP('2017-11-24 16:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540198
;

-- 2017-11-24T16:19:26.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540768,TO_TIMESTAMP('2017-11-24 16:19:26','YYYY-MM-DD HH24:MI:SS'),100,'via C_PurchaseCandidate','de.metas.purchasecandidate','Y','N','C_Order (PO) Target for C_Order (SO)',TO_TIMESTAMP('2017-11-24 16:19:26','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-11-24T16:19:26.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540768 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-11-24T16:20:19.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2169,2161,0,540768,259,181,TO_TIMESTAMP('2017-11-24 16:20:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','N',TO_TIMESTAMP('2017-11-24 16:20:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T16:23:33.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1 from C_Order po  join C_OrderLine pol on po.C_Order_ID = pol.C_Order_ID  join C_PurchaseCandidate pc on pol.C_OrderLine_ID = pc.C_OrderLinePO_ID  join C_Order so on pc.C_OrderSO_ID = so.C_Order_ID',Updated=TO_TIMESTAMP('2017-11-24 16:23:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540768
;

-- 2017-11-24T16:25:04.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1 from C_Order po  join C_OrderLine pol on po.C_Order_ID = pol.C_Order_ID  join C_PurchaseCandidate pc on pol.C_OrderLine_ID = pc.C_OrderLinePO_ID  join C_Order so on pc.C_OrderSO_ID = so.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''Y'' and po.C_Order_ID = C_Order.C_Order_ID )',Updated=TO_TIMESTAMP('2017-11-24 16:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540768
;

-- 2017-11-24T16:26:24.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540768,Updated=TO_TIMESTAMP('2017-11-24 16:26:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540198
;

-- 2017-11-24T16:27:09.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1 from C_Order po  join C_OrderLine pol on po.C_Order_ID = pol.C_Order_ID  join C_PurchaseCandidate pc on pol.C_OrderLine_ID = pc.C_OrderLinePO_ID  join C_Order so on pc.C_OrderSO_ID = so.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@ and po.IsSOTrx = ''Y'' and po.C_Order_ID = C_Order.C_Order_ID )',Updated=TO_TIMESTAMP('2017-11-24 16:27:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540768
;

-- 2017-11-24T16:27:59.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1 from C_Order po  join C_OrderLine pol on po.C_Order_ID = pol.C_Order_ID  join C_PurchaseCandidate pc on pol.C_OrderLine_ID = pc.C_OrderLinePO_ID  join C_Order so on pc.C_OrderSO_ID = so.C_Order_ID  where so.C_Order_ID = @C_Order_ID/-1@ and so.IsSOTrx = ''Y'' and po.C_Order_ID = C_Order.C_Order_ID )',Updated=TO_TIMESTAMP('2017-11-24 16:27:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540768
;

