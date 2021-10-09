
--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO $$
    BEGIN
        BEGIN
-- 2019-07-29T14:06:53.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ PERFORM public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN InvoicableQtyBasedOn VARCHAR(11) DEFAULT ''Nominal''')
;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column InvoicableQtyBasedOn already exists in C_OrderLine.';
        END;
    END;
$$;
