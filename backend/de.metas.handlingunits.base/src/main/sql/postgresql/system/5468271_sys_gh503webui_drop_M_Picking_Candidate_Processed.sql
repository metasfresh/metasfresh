-- run after 5468270_sys_gh503webui_M_Packageable_V_perf.sql

SELECT public.db_alter_table('m_picking_candidate','ALTER TABLE public.M_Picking_Candidate DROP COLUMN IF EXISTS Processed');
