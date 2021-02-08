

--5575960_sys_gh10446_AD_Column_IsRangeFilter_drop_DDL.sql - Not dropping the column yet,
--because it is still present in many customer branches;
--metas-ts made a reminder for 2021-06-01 to drop it then

-- this SQL is to "repair" DBs where it was already dropped

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
