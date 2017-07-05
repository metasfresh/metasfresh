

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


CREATE INDEX IF NOT EXISTS m_hu_trace_vhu_id
   ON m_hu_trace (vhu_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS m_hu_trace_m_hu_id
   ON m_hu_trace (m_hu_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS m_hu_trace_vhu_source_id
   ON m_hu_trace (vhu_source_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS m_hu_trace_m_inout_id
   ON m_hu_trace (m_inout_id ASC NULLS LAST);
   
CREATE INDEX IF NOT EXISTS m_hu_trace_pp_cost_collector_id
   ON m_hu_trace (pp_cost_collector_id ASC NULLS LAST);
