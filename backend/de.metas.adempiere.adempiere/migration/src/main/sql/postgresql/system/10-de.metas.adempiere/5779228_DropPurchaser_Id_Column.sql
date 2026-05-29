-- Column: C_BP_Group.Purchaser_User_ID
-- 2025-12-02T12:07:10.187Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590816
;

-- 2025-12-02T12:07:10.192Z
DELETE FROM AD_Column WHERE AD_Column_ID=590816
;


-- 2025-12-02T12:07:09.816Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE C_BP_Group DROP COLUMN IF EXISTS Purchaser_User_ID')
;
