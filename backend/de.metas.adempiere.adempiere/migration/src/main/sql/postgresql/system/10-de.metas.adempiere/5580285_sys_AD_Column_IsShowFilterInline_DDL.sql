DO $$
    BEGIN
        BEGIN
            /* DDL */ ALTER TABLE public.AD_Column ADD COLUMN IsShowFilterInline CHAR(1) DEFAULT 'N' CHECK (IsShowFilterInline IN ('Y','N')) NOT NULL;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column IsShowFilterInline already exists in AD_Column.';
        END;
    END;
$$;
