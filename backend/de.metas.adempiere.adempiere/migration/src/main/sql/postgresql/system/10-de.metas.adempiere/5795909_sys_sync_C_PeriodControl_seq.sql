-- Fixes failure of: 5795910_add_missing_period_controls.sql
-- 5760750_sys_gh26910_Create_C_Period_for_2026_to_2030.sql inserted period controls with MAX(C_PeriodControl_ID)+1 without advancing the sequence,
-- so the NEXTVAL('c_periodcontrol_seq') of add_missing_period_controls() can hit an existing ID when both run in the same migration run.
SELECT dba_seq_check_native('C_PeriodControl')
;
