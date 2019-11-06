-- 2019-11-06T08:42:54.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-11-06 10:42:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556465
;

-- 2019-11-06T08:42:57.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_qty','M_Locator_ID','NUMERIC(10)',null,null)
;

-- 2019-11-06T08:42:57.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_qty','M_Locator_ID',null,'NULL',null)
;

