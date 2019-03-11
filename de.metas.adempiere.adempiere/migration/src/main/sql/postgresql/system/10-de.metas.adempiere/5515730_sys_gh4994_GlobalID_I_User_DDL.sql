

ALTER TABLE I_User
DROP COLUMN IF EXISTS 
GlobalID;




-- 2019-03-11T17:56:52.703
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN BP_GlobalID VARCHAR(10)')
;

