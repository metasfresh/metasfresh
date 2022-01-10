-- 2021-11-05T16:58:49.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1  from PMM_PurchaseCandidate pc  join PMM_PurchaseCandidate_OrderLine pco on pc.PMM_PurchaseCandidate_ID = pco.PMM_PurchaseCandidate_ID  join C_OrderLine ol on pco.C_OrderLine_ID = ol.C_OrderLine_ID  join C_Order o on ol.C_Order_ID = o.C_Order_ID  where  PMM_PurchaseCandidate.PMM_PurchaseCandidate_ID = pc.PMM_PurchaseCandidate_ID and  ( o.C_Order_ID = @C_Order_ID/-1@) )',Updated=TO_TIMESTAMP('2021-11-05 17:58:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540653
;

