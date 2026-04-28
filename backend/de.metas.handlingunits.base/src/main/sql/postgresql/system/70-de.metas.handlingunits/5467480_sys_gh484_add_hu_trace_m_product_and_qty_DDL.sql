

-- 2017-07-07T22:15:29.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_hu_trace','ALTER TABLE public.M_HU_Trace ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2017-07-07T22:15:30.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Trace ADD CONSTRAINT MProduct_MHUTrace FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2017-07-07T22:16:03.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_hu_trace','ALTER TABLE public.M_HU_Trace ADD COLUMN Qty NUMERIC')
;

-- 2017-07-07T22:16:40.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_trace','Qty','NUMERIC',null,null)
;

-- 2017-07-07T22:16:40.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_trace','Qty',null,'NOT NULL',null)
;

-- 2017-07-07T22:16:55.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_trace','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2017-07-07T22:16:55.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_trace','M_Product_ID',null,'NOT NULL',null)
;

