-- 2021-07-12T08:28:30.909Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('Alberta_PrescriptionRequest','ALTER TABLE public.Alberta_PrescriptionRequest ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2021-07-12T08:28:30.917Z
-- URL zum Konzept
ALTER TABLE Alberta_PrescriptionRequest ADD CONSTRAINT COrder_AlbertaPrescriptionRequest FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;


