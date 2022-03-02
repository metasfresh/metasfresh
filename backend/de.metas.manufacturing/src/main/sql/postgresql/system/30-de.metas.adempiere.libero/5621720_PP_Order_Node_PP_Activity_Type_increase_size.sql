-- 2022-01-17T12:11:05.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=40,Updated=TO_TIMESTAMP('2022-01-17 14:11:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578132
;

-- 2022-01-17T12:11:06.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_node','PP_Activity_Type','VARCHAR(40)',null,'WR')
;

-- 2022-01-17T12:11:06.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE PP_Order_Node SET PP_Activity_Type='WR' WHERE PP_Activity_Type IS NULL
;

