
-- 2019-03-04T13:24:16.301
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN Dusie VARCHAR(250)')
;



-- 2019-03-04T13:21:01.153
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN IsNewsletter CHAR(1) DEFAULT ''N'' CHECK (IsNewsletter IN (''Y'',''N'')) NOT NULL')
;



-- 2019-03-04T13:21:16.036
-- #298 changing anz. stellen
INSERT INTO t_alter_column values('i_user','IsNewsletter','CHAR(1)',null,'N')
;

-- 2019-03-04T13:21:16.079
-- #298 changing anz. stellen
UPDATE I_User SET IsNewsletter='N' WHERE IsNewsletter IS NULL
;




-- 2019-03-04T13:22:02.815
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN Gender VARCHAR(250)')
;



-- 2019-03-04T13:24:42.107
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN MobilePhone VARCHAR(250)')
;





-- 2019-03-04T13:27:00.139
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN GlobalID VARCHAR(250)')
;

