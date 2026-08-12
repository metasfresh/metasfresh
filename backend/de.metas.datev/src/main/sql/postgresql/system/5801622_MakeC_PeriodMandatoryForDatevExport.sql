-- verify no unmapped rows remain before making column mandatory
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM datev_export WHERE C_Period_ID IS NULL) THEN
        RAISE EXCEPTION 'Cannot migrate: some DATEV_Export rows have no matching C_Period. Count: %',
            (SELECT COUNT(*) FROM datev_export WHERE C_Period_ID IS NULL);
    END IF;
END $$;

-- 2026-05-11T07:57:50.473Z
INSERT INTO t_alter_column values('datev_export','C_Period_ID','NUMERIC(10)',null,null)
;

-- 2026-05-11T07:57:50.477Z
INSERT INTO t_alter_column values('datev_export','C_Period_ID',null,'NOT NULL',null)
;


