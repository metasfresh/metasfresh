-- Searching for all backup tables and insert records in backup.backup_tables 
DO
$$
    DECLARE
        r record;
        v_created_at timestamptz;
        v_source_table_name text;
    BEGIN
        FOR r IN
            SELECT tablename
            FROM pg_tables
            WHERE schemaname = 'backup'
              AND tablename LIKE '%_bkp_%'
            LOOP
                BEGIN
                    -- Extract timestamp from table name
                    BEGIN
                        v_created_at := to_timestamp(
                                substring(r.tablename FROM '_bkp_(\d{8}_\d{6})'),
                                'YYYYMMDD_HH24MISS'
                                        );
                    EXCEPTION
                        WHEN OTHERS THEN
                            RAISE WARNING 'Could not parse timestamp from %', r.tablename;
                            CONTINUE;
                    END;

                    -- Extract source table name from prefix
                    v_source_table_name := substring(r.tablename FROM '^([A-Za-z0-9_]+)_bkp_');

                    -- Insert into tracking table
                    INSERT INTO backup.backup_tables (
                        backup_table_name,
                        source_table_name,
                        created_at
                    )
                    VALUES (
                               r.tablename,
                               v_source_table_name,
                               v_created_at
                           )
                    ON CONFLICT DO NOTHING;

                    RAISE NOTICE 'Registered backup table: % (source: %, created_at: %)', r.tablename, v_source_table_name, v_created_at;

                EXCEPTION
                    WHEN OTHERS THEN
                        RAISE WARNING 'Failed to process table: %', r.tablename;
                END;
            END LOOP;
    END
$$;

