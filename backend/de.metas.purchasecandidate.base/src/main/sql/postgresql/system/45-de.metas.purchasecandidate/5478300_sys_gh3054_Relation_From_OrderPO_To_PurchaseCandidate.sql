-- 2017-11-24T16:09:15.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540197,TO_TIMESTAMP('2017-11-24 16:09:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order (PO) -> C_PurchaseCandidate',TO_TIMESTAMP('2017-11-24 16:09:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T16:09:24.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540676,Updated=TO_TIMESTAMP('2017-11-24 16:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540197
;

-- 2017-11-24T16:09:45.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_PurchaseCandidate_Target_SO',Updated=TO_TIMESTAMP('2017-11-24 16:09:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540766
;

-- 2017-11-24T16:10:18.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540767,TO_TIMESTAMP('2017-11-24 16:10:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','N','C_PurchaseCandidate_Target_PO',TO_TIMESTAMP('2017-11-24 16:10:18','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-11-24T16:10:18.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540767 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-11-24T16:10:57.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,557857,557857,0,540767,540861,TO_TIMESTAMP('2017-11-24 16:10:57','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2017-11-24 16:10:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T16:11:03.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.purchasecandidate',Updated=TO_TIMESTAMP('2017-11-24 16:11:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540767
;

-- 2017-11-24T16:11:10.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (  select 1 from C_PurchaseCandidate pc  join C_Order o on pc.C_OrderSO_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@ and o.IsSOTrx = ''Y'' and C_PurchaseCandidate.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID ) ',Updated=TO_TIMESTAMP('2017-11-24 16:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540767
;

-- 2017-11-24T16:13:10.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='  exists (    select 1   from C_PurchaseCandidate pc   join C_OrderLine ol on pc.C_OrderLinePO_ID = ol.C_OrderLine_ID  join C_Order o on ol.C_Order_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@  and o.IsSOTrx = ''N''  and C_PurchaseCandidate.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID ) ',Updated=TO_TIMESTAMP('2017-11-24 16:13:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540767
;

-- 2017-11-24T16:13:38.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540375,Updated=TO_TIMESTAMP('2017-11-24 16:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540767
;

-- 2017-11-24T16:13:56.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540767,Updated=TO_TIMESTAMP('2017-11-24 16:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540197
;

