-- Run mode: SWING_CLIENT

select backup_table('C_Payment');

-- 2026-04-28T16:11:55.082Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE C_Payment DROP COLUMN IF EXISTS C_OrderPaySchedule_ID')
;

-- Column: C_Payment.C_OrderPaySchedule_ID
-- 2026-04-28T16:11:55.596Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591374
;

-- 2026-04-28T16:11:55.608Z
DELETE FROM AD_Column WHERE AD_Column_ID=591374
;

