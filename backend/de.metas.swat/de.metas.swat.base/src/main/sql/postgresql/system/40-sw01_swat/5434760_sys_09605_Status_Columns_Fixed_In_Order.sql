-- 04.12.2015 17:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE WHEN C_Order.QtyOrdered <= C_Order.QtyMoved AND C_Order.QtyOrdered > 0 THEN ''CD'' WHEN C_Order.QtyOrdered > C_Order.QtyMoved AND C_Order.QtyMoved > 0 THEN ''PD'' ELSE ''O'' END )',Updated=TO_TIMESTAMP('2015-12-04 17:00:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552710
;

-- 04.12.2015 17:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE WHEN C_Order.QtyOrdered <= C_Order.QtyInvoiced AND C_Order.QtyOrdered > 0 THEN ''CI'' WHEN C_Order.QtyOrdered > C_Order.QtyInvoiced AND C_Order.QtyInvoiced > 0 THEN ''PI'' ELSE ''O'' END )',Updated=TO_TIMESTAMP('2015-12-04 17:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552695
;
