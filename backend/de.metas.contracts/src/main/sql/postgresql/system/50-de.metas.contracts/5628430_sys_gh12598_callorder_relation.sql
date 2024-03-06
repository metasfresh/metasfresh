-- 2022-03-01T15:54:40.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Order (CallOrder) -> C_CallOrderSummary',Updated=TO_TIMESTAMP('2022-03-01 17:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540340
;

-- 2022-03-01T15:55:27.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order_with_lines_with_CallOrder_Contract',Updated=TO_TIMESTAMP('2022-03-01 17:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541549
;

-- 2022-03-01T16:01:12.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_CallOrderSummary_ID IN (select s.C_CallOrderSummary_ID from C_CallOrderSummary s inner join C_CallOrderDetail detail on detail.C_CallOrderSummary_ID = s.C_CallOrderSummary_ID inner join C_OrderLine ol on detail.c_orderline_id = ol.c_orderline_id where ol.c_order_id=@C_Order_ID/-1@)',Updated=TO_TIMESTAMP('2022-03-01 18:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541550
;

