

-- 2017-07-04T16:58:46.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_hu_trace','ALTER TABLE public.M_HU_Trace ADD COLUMN VHU_ID NUMERIC(10)')
;

-- 2017-07-04T16:58:46.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Trace ADD CONSTRAINT VHU_MHUTrace FOREIGN KEY (VHU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;


-- 2017-07-04T17:01:02.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_trace','VHU_ID','NUMERIC(10)',null,null)
;

-- 2017-07-04T17:02:41.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_trace','VHU_ID','NUMERIC(10)',null,null)
;

-- 2017-07-04T17:02:41.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_trace','VHU_ID',null,'NOT NULL',null)
;

-- 2017-07-05T18:33:32.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_hu_trace','ALTER TABLE public.M_HU_Trace ADD COLUMN VHU_Source_ID NUMERIC(10)')
;

/* DDL */ SELECT public.db_alter_table('m_hu_trace','ALTER TABLE public.M_HU_Trace DROP COLUMN IF EXISTS M_HU_Source_ID')
;

-- 2017-07-05T18:33:32.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Trace ADD CONSTRAINT VHUSource_MHUTrace FOREIGN KEY (VHU_Source_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;



-- 2017-07-06T10:29:40.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_hu_trace','ALTER TABLE public.M_HU_Trace ADD COLUMN VHUStatus VARCHAR(2) NOT NULL')
;

-- 2017-07-06T10:30:07.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_hu_trace','ALTER TABLE public.M_HU_Trace ADD COLUMN M_HU_Trx_Line_ID NUMERIC(10)')
;

-- 2017-07-06T10:30:07.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Trace ADD CONSTRAINT MHUTrxLine_MHUTrace FOREIGN KEY (M_HU_Trx_Line_ID) REFERENCES public.M_HU_Trx_Line DEFERRABLE INITIALLY DEFERRED
;



-- 2017-07-06T11:35:36.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_hu_trace','ALTER TABLE public.M_HU_Trace ADD COLUMN PP_Order_ID NUMERIC(10)')
;

-- 2017-07-06T11:35:36.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Trace ADD CONSTRAINT PPOrder_MHUTrace FOREIGN KEY (PP_Order_ID) REFERENCES public.PP_Order DEFERRABLE INITIALLY DEFERRED
;




CREATE INDEX IF NOT EXISTS m_hu_trace_vhu_id
   ON m_hu_trace (vhu_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS m_hu_trace_m_hu_id
   ON m_hu_trace (m_hu_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS m_hu_trace_vhu_source_id
   ON m_hu_trace (vhu_source_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS m_hu_trace_m_inout_id
   ON m_hu_trace (m_inout_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS m_hu_trace_pp_order_id
   ON m_hu_trace (pp_order_id ASC NULLS LAST);
