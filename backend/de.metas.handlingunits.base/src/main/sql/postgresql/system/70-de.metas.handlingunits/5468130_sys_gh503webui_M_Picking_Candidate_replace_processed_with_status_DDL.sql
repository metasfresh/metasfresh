
-- 2017-07-20T07:29:07.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_picking_candidate','ALTER TABLE public.M_Picking_Candidate ADD COLUMN Status VARCHAR(2) DEFAULT ''IP'' NOT NULL')
;

-- SELECT public.db_alter_table('m_picking_candidate','ALTER TABLE public.M_Picking_Candidate DROP COLUMN IF EXISTS Processed');
