-- Pre-save and drop views that depend on QtyDemand_QtySupply_V
-- so that script 5789800 (which uses DROP VIEW instead of db_alter_view) can succeed.
--
-- Script 5789801 recreates these dependent views after 5789800 has run.
--
-- On databases where 5789800 already ran successfully (no dependents existed),
-- this script is a harmless no-op: the staging table is created empty.

DO
$$
    DECLARE
        v_rec RECORD;
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_views WHERE viewname = 'qtydemand_qtysupply_v')
        THEN
            RAISE NOTICE 'QtyDemand_QtySupply_V does not exist — nothing to do';
            RETURN;
        END IF;

        -- Permanent staging table to pass definitions to script 5789801
        CREATE TABLE IF NOT EXISTS tmp_qtydemand_dependent_views
        (
            view_schema     TEXT,
            view_name       TEXT,
            view_definition TEXT,
            depth           INT
        );
        DELETE FROM tmp_qtydemand_dependent_views;

        -- Save definitions of all dependent views
        INSERT INTO tmp_qtydemand_dependent_views (view_schema, view_name, view_definition, depth)
        SELECT v.view_schema,
               v.view_name,
               (SELECT vw.view_definition
                FROM information_schema.views vw
                WHERE vw.table_schema = v.view_schema
                  AND vw.table_name = v.view_name),
               v.depth
        FROM db_dependent_views('qtydemand_qtysupply_v') v;

        -- Drop dependent views deepest-first
        FOR v_rec IN (SELECT * FROM tmp_qtydemand_dependent_views ORDER BY depth DESC)
            LOOP
                EXECUTE 'DROP VIEW IF EXISTS "' || v_rec.view_schema || '".' || v_rec.view_name;
                RAISE NOTICE 'Dropped dependent view: %.%', v_rec.view_schema, v_rec.view_name;
            END LOOP;
    END
$$;
