



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




-- 2019-03-04T13:24:42.107
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN MobilePhone VARCHAR(250)')
;




-- 2019-03-04T18:41:57.029
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN C_DataImport_ID NUMERIC(10)')
;

-- 2019-03-04T18:41:57.041
-- #298 changing anz. stellen
ALTER TABLE I_User ADD CONSTRAINT CDataImport_IUser FOREIGN KEY (C_DataImport_ID) REFERENCES public.C_DataImport DEFERRABLE INITIALLY DEFERRED
;




-- 2019-03-05T11:33:21.248
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN Phone VARCHAR(250)')
;


-- 2019-03-05T11:54:13.468
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_User','ALTER TABLE public.I_User ADD COLUMN Fax VARCHAR(250)')
;

