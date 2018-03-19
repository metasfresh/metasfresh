-- 2017-12-11T13:11:59.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(M_AttributeSetInstance_ID, 0)',Updated=TO_TIMESTAMP('2017-12-11 13:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540830
;
