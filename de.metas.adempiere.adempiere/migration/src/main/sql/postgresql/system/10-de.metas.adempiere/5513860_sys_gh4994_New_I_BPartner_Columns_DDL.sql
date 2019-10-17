
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN Memo_Invoicing VARCHAR(250)')
;


-- 2019-02-26T16:23:14.091
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN POBox VARCHAR(250)')
;

-- 2019-02-26T16:24:44.278
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN CountryName VARCHAR(250)')
;


-- 2019-02-26T16:28:52.336
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN Memo_Delivery VARCHAR(2000)')
;
