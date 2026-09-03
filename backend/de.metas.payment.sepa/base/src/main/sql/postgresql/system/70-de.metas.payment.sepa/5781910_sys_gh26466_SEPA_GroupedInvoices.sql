-- 2025-12-17T17:43:31.782Z
/* DDL */ SELECT public.db_alter_table('SEPA_Export_Line_Ref','ALTER TABLE public.SEPA_Export_Line_Ref ADD COLUMN C_Currency_ID NUMERIC(10) NOT NULL')
;

-- 2025-12-17T17:43:31.815Z
ALTER TABLE SEPA_Export_Line_Ref ADD CONSTRAINT CCurrency_SEPAExportLineRef FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- 2025-12-17T17:45:30.014Z
/* DDL */ SELECT public.db_alter_table('SEPA_Export_Line_Ref','ALTER TABLE public.SEPA_Export_Line_Ref ADD COLUMN StructuredRemittanceInfo VARCHAR(35)')
;
