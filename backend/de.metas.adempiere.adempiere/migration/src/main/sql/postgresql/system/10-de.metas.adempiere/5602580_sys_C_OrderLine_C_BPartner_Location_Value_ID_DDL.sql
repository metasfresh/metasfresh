-- 2021-08-31T10:41:49.030Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN C_BPartner_Location_Value_ID NUMERIC(10)')
;

-- 2021-08-31T10:41:51.774Z
-- URL zum Konzept
ALTER TABLE C_OrderLine ADD CONSTRAINT CBPartnerLocationValue_COrderLine FOREIGN KEY (C_BPartner_Location_Value_ID) REFERENCES public.C_Location DEFERRABLE INITIALLY DEFERRED
;

