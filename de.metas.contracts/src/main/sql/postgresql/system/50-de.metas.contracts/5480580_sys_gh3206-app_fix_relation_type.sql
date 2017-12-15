-- 2017-12-13T16:52:04.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Order -> C_Flatrate_Term',Updated=TO_TIMESTAMP('2017-12-13 16:52:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540191
;

-- 2017-12-13T16:52:22.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order_with_lines_with_C_Flatrate_Conditions_ID',Updated=TO_TIMESTAMP('2017-12-13 16:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2017-12-13T16:53:53.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ AND ol.C_Flatrate_Conditions_ID IS NOT NULL)',Updated=TO_TIMESTAMP('2017-12-13 16:53:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2017-12-13T16:54:11.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='IsSOTrx=''Y'' AND EXISTS (select 1 from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ AND ol.C_Flatrate_Conditions_ID IS NOT NULL)',Updated=TO_TIMESTAMP('2017-12-13 16:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2017-12-13T16:54:36.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Flatrate_Term_For_C_Order',Updated=TO_TIMESTAMP('2017-12-13 16:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540755
;

-- 2017-12-13T16:55:29.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_OrderLine_Term_ID IN (select ol.C_OrderLine_ID from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@',Updated=TO_TIMESTAMP('2017-12-13 16:55:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540755
;

-- 2017-12-13T16:58:20.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=2213,Updated=TO_TIMESTAMP('2017-12-13 16:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2017-12-13T17:06:18.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=2161, AD_Table_ID=259,Updated=TO_TIMESTAMP('2017-12-13 17:06:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2017-12-13T17:10:10.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_OrderLine_Term_ID IN (select ol.C_OrderLine_ID from C_OrderLine ol where ol.C_Order_ID = @C_Order_ID/-1@ )',Updated=TO_TIMESTAMP('2017-12-13 17:10:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540755
;

