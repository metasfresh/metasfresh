--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO $$
    BEGIN
        BEGIN
            CREATE SEQUENCE ad_element_link_seq
                MINVALUE 0
                MAXVALUE 2147483647
            ;
        EXCEPTION
            WHEN duplicate_table THEN RAISE NOTICE 'sequence already exists';
        END;
    END;
$$;



