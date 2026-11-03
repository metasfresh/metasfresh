-- 2024-11-18T07:25:29.021Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN IsSOTrx TEXT')
;

-- 2024-11-18T07:25:56.187Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN PostingType TEXT')
;

-- 2024-11-18T07:27:21.549Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN DateTrx TIMESTAMP WITH TIME ZONE')
;

-- 2024-11-18T07:27:42.349Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN POReference TEXT')
;

-- 2024-11-18T07:28:14.067Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN VATCode VARCHAR(10)')
;

-- 2024-11-18T07:28:38.317Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN AmtSource NUMERIC')
;

-- 2024-11-18T07:29:19.231Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN TaxAmtSource NUMERIC')
;

-- 2024-11-18T07:32:48.281Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN C_AcctSchema_ID NUMERIC(10)')
;

-- 2024-11-18T07:32:48.343Z
ALTER TABLE DATEV_ExportLine ADD CONSTRAINT CAcctSchema_DATEVExportLine FOREIGN KEY (C_AcctSchema_ID) REFERENCES public.C_AcctSchema DEFERRABLE INITIALLY DEFERRED
;

-- 2024-11-18T07:33:44.097Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN C_DocType_ID NUMERIC(10)')
;

-- 2024-11-18T07:33:44.150Z
ALTER TABLE DATEV_ExportLine ADD CONSTRAINT CDocType_DATEVExportLine FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- 2024-11-18T07:34:01.471Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN C_Tax_ID NUMERIC(10)')
;

-- 2024-11-18T07:34:01.524Z
ALTER TABLE DATEV_ExportLine ADD CONSTRAINT CTax_DATEVExportLine FOREIGN KEY (C_Tax_ID) REFERENCES public.C_Tax DEFERRABLE INITIALLY DEFERRED
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.sv178_datev_grandtotal
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.sv178_datev_grandtotal
-- SQL: (SELECT GrandTotal from c_invoice i where i.c_invoice_id=@JoinTableNameOrAliasIncludingDot@c_invoice_id)*(case when IsSOTrx='Y' then 1 else -1 end)
-- 2024-11-18T07:39:48.085Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585965
;

-- 2024-11-18T07:39:48.365Z
DELETE FROM AD_Column WHERE AD_Column_ID=585965
;

