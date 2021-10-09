

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

DROP FUNCTION IF EXISTS after_migration_migrate_IsRangeFilter();
CREATE FUNCTION after_migration_migrate_IsRangeFilter() RETURNS VOID
    LANGUAGE plpgsql
AS $$
DECLARE
    count_updated NUMERIC;
BEGIN
    UPDATE ad_column SET filteroperator=(CASE WHEN israngefilter='Y' THEN 'B' ELSE 'E' END)
    WHERE isselectioncolumn='Y'
      AND filteroperator IS NULL;

    GET DIAGNOSTICS count_updated = ROW_COUNT;
    RAISE NOTICE 'Migrated AD_Column.IsRangeFilter for % records', count_updated;
END;
$$;