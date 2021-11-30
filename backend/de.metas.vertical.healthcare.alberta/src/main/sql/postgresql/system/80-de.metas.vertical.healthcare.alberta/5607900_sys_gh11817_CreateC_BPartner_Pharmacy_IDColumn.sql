
-- Create new column in C_Order table
-- 2021-10-05T07:37:55.005Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN C_BPartner_Pharmacy_ID NUMERIC(10)')
;

-- 2021-10-05T07:37:56.129Z
-- URL zum Konzept
ALTER TABLE C_Order ADD CONSTRAINT CBPartnerPharmacy_COrder FOREIGN KEY (C_BPartner_Pharmacy_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;
