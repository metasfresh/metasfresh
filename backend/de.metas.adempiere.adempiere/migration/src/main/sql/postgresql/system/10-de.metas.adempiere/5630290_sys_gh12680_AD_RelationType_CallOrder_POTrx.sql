

-- 2022-03-16T13:38:36.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL, WhereClause='EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ AND ol.C_Flatrate_Term_ID IS NOT NULL)',Updated=TO_TIMESTAMP('2022-03-16 15:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541549
;

-- 2022-03-16T13:43:37.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL, WhereClause='EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ AND ol.C_Flatrate_Conditions_ID IS NOT NULL)',Updated=TO_TIMESTAMP('2022-03-16 15:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

