
/*
ALTER TABLE M_Product 
ALTER COLUMN customerlabelname
SET DATA TYPE  character varying(2000);
*/

ALTER TABLE M_Product_Trl
ALTER COLUMN customerlabelname
SET DATA TYPE  character varying(2000);

-- 2018-10-04T07:59:16.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product','CustomerLabelName','VARCHAR(2000)',null,null)
;
