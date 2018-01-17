--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO $$ 
    BEGIN
        BEGIN
-- 2017-11-27T16:40:14.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ ALTER TABLE public.AD_Column ADD COLUMN IsRangeFilter CHAR(1) DEFAULT 'N' CHECK (IsRangeFilter IN ('Y','N')) NOT NULL;

        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column IsRangeFilter already exists in AD_Column.';
        END;
    END;
$$;

DO $$ 
    BEGIN
        BEGIN
-- 2017-11-27T16:41:03.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE public.AD_Column ADD COLUMN IsShowFilterIncrementButtons CHAR(1) DEFAULT 'N' CHECK (IsShowFilterIncrementButtons IN ('Y','N')) NOT NULL;

        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column IsShowFilterIncrementButtons already exists in AD_Column.';
        END;
    END;
$$;


DO $$ 
    BEGIN
        BEGIN
-- 2017-11-27T16:41:34.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ ALTER TABLE public.AD_Column ADD COLUMN FilterDefaultValue VARCHAR(255);

        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column FilterDefaultValue already exists in AD_Column.';
        END;
    END;
$$;
