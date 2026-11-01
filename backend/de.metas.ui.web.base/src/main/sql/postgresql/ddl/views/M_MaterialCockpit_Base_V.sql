-- M_MaterialCockpit_Base_V: Base view for Material Cockpit V2
-- 5 data sources: Shipment Schedules, Receipt Schedules, Production Candidates, Forecasts, Current Stock
-- The query itself lives in the set-returning function M_MaterialCockpit_Base_V_Fn() (composite type
-- M_MaterialCockpit_Base_V_Row, LANGUAGE sql STABLE ROWS 5000 SET jit = off), see migration
-- 5832320_sys_gh33275_MaterialCockpit_Base_V_perf.sql. The function body is not dependency-tracked: a change of the
-- return type needs DROP FUNCTION M_MaterialCockpit_Base_V_Fn() CASCADE and a rebuild.
-- Uses db_alter_view pattern for safe dependency handling

DROP VIEW IF EXISTS M_MaterialCockpit_Base_V$new
;

CREATE OR REPLACE VIEW M_MaterialCockpit_Base_V$new AS
-- IMPORTANT: PLEASE DO NOT CHANGE THIS VIEW, but
-- * create a new view called CUS123_MaterialCockpit_V
-- * run
SELECT *
FROM M_MaterialCockpit_Base_V_Fn()
;

SELECT db_alter_view(
               'M_MaterialCockpit_Base_V',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('m_materialcockpit_base_v$new'))
       )
;

DROP VIEW IF EXISTS M_MaterialCockpit_Base_V$new
;
