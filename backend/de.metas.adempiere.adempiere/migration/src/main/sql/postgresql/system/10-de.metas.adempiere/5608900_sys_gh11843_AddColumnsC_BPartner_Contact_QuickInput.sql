-- 2021-10-12T06:12:54.986Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Contact_QuickInput','ALTER TABLE public.C_BPartner_Contact_QuickInput ADD COLUMN Birthday TIMESTAMP WITHOUT TIME ZONE')
;

-- 2021-10-12T06:13:02.636Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Contact_QuickInput','ALTER TABLE public.C_BPartner_Contact_QuickInput ADD COLUMN Title VARCHAR(40)')
;


-- 2021-10-12T06:13:37.489Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Contact_QuickInput','ALTER TABLE public.C_BPartner_Contact_QuickInput ADD COLUMN Phone2 VARCHAR(40)')
;


-- 2021-10-12T06:14:20.985Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Contact_QuickInput','ALTER TABLE public.C_BPartner_Contact_QuickInput ADD COLUMN IsDefaultContact CHAR(1) DEFAULT ''N'' CHECK (IsDefaultContact IN (''Y'',''N'')) NOT NULL')
;

-- 2021-10-12T06:15:24.979Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Contact_QuickInput','ALTER TABLE public.C_BPartner_Contact_QuickInput ADD COLUMN IsInvoiceEmailEnabled CHAR(1)')
;

