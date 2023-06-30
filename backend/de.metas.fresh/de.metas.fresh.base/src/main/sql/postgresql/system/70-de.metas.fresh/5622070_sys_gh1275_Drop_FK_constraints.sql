UPDATE AD_Column
SET DDL_NoForeignKey='Y'
WHERE AD_Element_ID = (SELECT AD_Element_ID FROM AD_Element WHERE columnname = 'AD_Issue_ID')
;


DO
$$
    DECLARE
        r record;
    BEGIN
        FOR r IN (SELECT tc.table_name AS table_name, tc.constraint_name AS constraint_name
                  FROM information_schema.table_constraints AS tc
                           JOIN information_schema.key_column_usage AS kcu
                                ON tc.constraint_name = kcu.constraint_name
                                    AND tc.table_schema = kcu.table_schema
                           JOIN information_schema.constraint_column_usage AS ccu
                                ON ccu.constraint_name = tc.constraint_name
                                    AND ccu.table_schema = tc.table_schema
                  WHERE tc.constraint_type = 'FOREIGN KEY'
                    AND kcu.column_name = 'ad_issue_id'
                  ORDER BY tc.table_name, kcu.column_name)
            LOOP
                RAISE INFO '%','dropping constraint: ' || r.constraint_name || ' - from table: ' || r.table_name;
                EXECUTE CONCAT('ALTER TABLE ' || r.table_name || ' DROP CONSTRAINT ' || r.constraint_name);
            END LOOP;
    END;
$$
;

UPDATE AD_Column
SET DDL_NoForeignKey='Y'
WHERE AD_Element_ID = (SELECT AD_Element_ID FROM AD_Element WHERE columnname = 'AD_PInstance_ID')
;

DO
$$
    DECLARE
        r record;
    BEGIN
        FOR r IN (SELECT tc.table_name AS table_name, tc.constraint_name AS constraint_name
                  FROM information_schema.table_constraints AS tc
                           JOIN information_schema.key_column_usage AS kcu
                                ON tc.constraint_name = kcu.constraint_name
                                    AND tc.table_schema = kcu.table_schema
                           JOIN information_schema.constraint_column_usage AS ccu
                                ON ccu.constraint_name = tc.constraint_name
                                    AND ccu.table_schema = tc.table_schema
                  WHERE tc.constraint_type = 'FOREIGN KEY'
                    AND kcu.column_name = 'ad_pinstance_id'
                  ORDER BY tc.table_name, kcu.column_name)
            LOOP
                RAISE INFO '%','dropping constraint: ' || r.constraint_name || ' - from table: ' || r.table_name;
                EXECUTE CONCAT('ALTER TABLE ' || r.table_name || ' DROP CONSTRAINT ' || r.constraint_name);
            END LOOP;
    END;
$$
;