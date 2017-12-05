-- 2017-11-24T16:31:57.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540676,540199,TO_TIMESTAMP('2017-11-24 16:31:57','YYYY-MM-DD HH24:MI:SS'),100,'via C_PurchaseCandidate','de.metas.swat','Y','N','C_Order (PO) -> C_Order (SO) ',TO_TIMESTAMP('2017-11-24 16:31:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T16:32:19.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.purchasecandidate',Updated=TO_TIMESTAMP('2017-11-24 16:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540199
;

-- 2017-11-24T16:39:54.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540769,TO_TIMESTAMP('2017-11-24 16:39:54','YYYY-MM-DD HH24:MI:SS'),100,'via C_PurchaseCandidate','de.metas.purchasecandidate','Y','N','C_Order (SO) Target for C_Order (PO)',TO_TIMESTAMP('2017-11-24 16:39:54','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-11-24T16:39:54.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540769 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-11-24T16:40:25.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2169,2161,0,540769,259,143,TO_TIMESTAMP('2017-11-24 16:40:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','N',TO_TIMESTAMP('2017-11-24 16:40:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-24T16:42:59.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (    select 1 from C_Order so    join C_PurchaseCandidate pc on so.C_Order_ID = pc.C_OrderSO_ID  join C_OrderLine pol on pc.C_OrderLinePO_ID = pol.C_OrderLine_ID  join C_Order po on pol.C_Order_ID = po.C_Order_ID  where po.C_Order_ID = @C_Order_ID/-1@ and po.IsSOTrx = ''N'' and so.C_Order_ID = C_Order.C_Order_ID )',Updated=TO_TIMESTAMP('2017-11-24 16:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540769
;

-- 2017-11-24T16:43:19.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540769,Updated=TO_TIMESTAMP('2017-11-24 16:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540199
;

