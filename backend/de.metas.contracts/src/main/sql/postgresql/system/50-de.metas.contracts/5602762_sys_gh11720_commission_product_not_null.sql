-- 2021-08-31T18:11:28.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Customer_Trade_Margin','ALTER TABLE public.C_Customer_Trade_Margin ADD COLUMN Commission_Product_ID NUMERIC(10)')
;

-- 2021-08-31T18:11:39.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Customer_Trade_Margin','ALTER TABLE public.C_Customer_Trade_Margin ADD COLUMN Name VARCHAR(40)')
;

COMMIT;

UPDATE C_Customer_Trade_Margin
SET Commission_Product_ID=540420 /*standard commission product*/,
    updated='2021-10-01 13:52', updatedby=99
WHERE Commission_Product_ID IS NULL
;

UPDATE C_Customer_Trade_Margin
SET Name=C_Customer_Trade_Margin_ID::text,
    updated='2021-10-01 15:55', updatedby=99
WHERE Name IS NULL
;

COMMIT;

alter table C_Customer_Trade_Margin alter column Commission_Product_ID set not null
;

alter table C_Customer_Trade_Margin alter column Name set not null
;

-- 2021-08-31T18:11:28.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Customer_Trade_Margin ADD CONSTRAINT CommissionProduct_CCustomerTradeMargin FOREIGN KEY (Commission_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

