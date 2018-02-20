--
-- fix C_Order (PO) -> C_Order (SO) AD_RelationType
-- the problem was intriduced in MSV3 handle deviating response from remote MSV3 server https://github.com/metasfresh/metasfresh/issues/3437
-- by not adapting the existing relation types' whereClauses.
--
-- 2018-02-20T16:27:55.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2018-02-20 16:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540014
;

-- 2018-02-20T16:28:46.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2018-02-20 16:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540023
;

-- 2018-02-20T16:42:59.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (      select 1 from C_Order po         join C_PurchaseCandidate_Alloc pca on pca.C_OrderPO_ID = po.C_Order_ID       join C_PurchaseCandidate pc on pc.C_PurchaseCandidate_ID = pca.C_PurchaseCandidate_ID       join C_Order so on pc.C_OrderSO_ID = so.C_Order_ID    where so.C_Order_ID = @C_Order_ID/-1@ and so.IsSOTrx = ''Y'' and po.C_Order_ID = C_Order.C_Order_ID )',Updated=TO_TIMESTAMP('2018-02-20 16:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540768
;

-- 2018-02-20T16:45:54.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (     select 1 from C_Order so         join C_PurchaseCandidate pc on pc.C_OrderSO_ID = so.C_Order_ID         join C_PurchaseCandidate_Alloc pca on pca.C_PurchaseCandidate_ID = pc.C_PurchaseCandidate_ID         join C_Order po on po.C_Order_ID = pca.C_OrderPO_ID where po.C_Order_ID = @C_Order_ID/-1@ and po.IsSOTrx = ''N'' and so.C_Order_ID = C_Order.C_Order_ID )',Updated=TO_TIMESTAMP('2018-02-20 16:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540769
;


CREATE INDEX IF NOT EXISTS c_purchasecandidate_alloc_pc_C_OrderPO_ID
  ON public.c_purchasecandidate_alloc
  USING btree
  (pc.C_OrderPO_ID);
COMMENT ON INDEX public.c_purchasecandidate_alloc_pc_c_orderpo_id
  IS 'This index was created to support a relation time that looks up sales orders from purchase orders';
  
--
-- cleanup
-- remove obsolete AD_RelationTypes "C_Order (SO) -> C_PurchaseCandidate" and "C_Order (PO) -> C_PurchaseCandidate"
-- also remove AD_Reference "C_PurchaseCandidate_Target_PO"
-- 2018-02-20T16:53:01.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540196
;

-- 2018-02-20T16:54:05.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540197
;

-- 2018-02-20T16:54:30.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540767
;

-- 2018-02-20T16:54:30.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540767
;

