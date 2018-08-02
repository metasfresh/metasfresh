-- 2018-07-30T12:24:24.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT c.cursymbol from C_Currency c where c.C_Currency_ID = C_Order.C_Currency_ID)',Updated=TO_TIMESTAMP('2018-07-30 12:24:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544224
;

-- 2018-07-30T12:26:36.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT bp.AD_Language from C_BPartner bp where bp.C_BPartner_ID = C_Order.C_BPartner_ID) ',Updated=TO_TIMESTAMP('2018-07-30 12:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552551
;

-- 2018-07-30T12:26:40.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT bp.AD_Language from C_BPartner bp where bp.C_BPartner_ID = C_Order.C_BPartner_ID)',Updated=TO_TIMESTAMP('2018-07-30 12:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552551
;

-- 2018-07-30T12:26:43.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT cu.cursymbol from C_Currency cu INNER JOIN C_AcctSchema ac on (cu.C_Currency_ID = ac.C_Currency_ID) where ac.C_AcctSchema_ID = getC_AcctSchema_ID(C_Order.AD_Client_ID, C_Order.AD_Org_ID)) ',Updated=TO_TIMESTAMP('2018-07-30 12:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544226
;

-- 2018-07-30T12:26:47.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT cu.cursymbol from C_Currency cu INNER JOIN C_AcctSchema ac on (cu.C_Currency_ID = ac.C_Currency_ID) where ac.C_AcctSchema_ID = getC_AcctSchema_ID(C_Order.AD_Client_ID, C_Order.AD_Org_ID))',Updated=TO_TIMESTAMP('2018-07-30 12:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544226
;

-- 2018-07-30T12:26:51.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE WHEN C_Order.QtyOrdered <= C_Order.QtyMoved AND C_Order.QtyOrdered > 0 THEN ''CD'' WHEN C_Order.QtyOrdered > C_Order.QtyMoved AND C_Order.QtyMoved > 0 THEN ''PD'' ELSE ''O'' END ) ',Updated=TO_TIMESTAMP('2018-07-30 12:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552710
;

-- 2018-07-30T12:26:54.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE WHEN C_Order.QtyOrdered <= C_Order.QtyMoved AND C_Order.QtyOrdered > 0 THEN ''CD'' WHEN C_Order.QtyOrdered > C_Order.QtyMoved AND C_Order.QtyMoved > 0 THEN ''PD'' ELSE ''O'' END )',Updated=TO_TIMESTAMP('2018-07-30 12:26:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552710
;

-- 2018-07-30T12:27:13.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT currencyBase(coalesce((SELECT sum(ot.taxbaseamt) from C_OrderTax ot where ot.c_order_id = o.c_order_id),0),o.C_Currency_ID,o.DateAcct,o.AD_Client_ID,o.AD_Org_ID) from c_order o where o.c_order_id = c_order.c_order_id)',Updated=TO_TIMESTAMP('2018-07-30 12:27:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544222
;

-- 2018-07-30T12:27:17.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT coalesce(sum(ot.taxbaseamt),0) from C_OrderTax ot where ot.c_order_id = c_order.c_order_id) ',Updated=TO_TIMESTAMP('2018-07-30 12:27:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544448
;

-- 2018-07-30T12:27:20.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT coalesce(sum(ot.taxbaseamt),0) from C_OrderTax ot where ot.c_order_id = c_order.c_order_id)',Updated=TO_TIMESTAMP('2018-07-30 12:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544448
;

