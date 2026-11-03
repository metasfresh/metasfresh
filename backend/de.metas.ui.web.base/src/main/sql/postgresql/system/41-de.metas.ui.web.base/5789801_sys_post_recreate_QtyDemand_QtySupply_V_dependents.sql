-- Recreate dependent views that were saved and dropped by script 5789799.
-- This runs after 5789800 has successfully recreated QtyDemand_QtySupply_V.

DO
$$
    DECLARE
        v_rec RECORD;
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'tmp_qtydemand_dependent_views')
        THEN
            RAISE NOTICE 'No saved dependent views staging table — nothing to do';
            RETURN;
        END IF;

        -- Recreate dependent views shallowest-first (reverse of drop order).
        -- Some views may reference old columns that no longer exist (e.g. rv_purchasecockpit
        -- referencing qtyReserved/qtyToMove/qtyStock which were replaced by SupplyType/QtyOnHand).
        -- Log a warning and continue instead of failing the entire migration.
        FOR v_rec IN (SELECT * FROM tmp_qtydemand_dependent_views ORDER BY depth ASC)
            LOOP
                BEGIN
                    EXECUTE 'CREATE OR REPLACE VIEW "' || v_rec.view_schema || '".' || v_rec.view_name
                                || ' AS ' || v_rec.view_definition;
                    RAISE NOTICE 'Recreated dependent view: %.%', v_rec.view_schema, v_rec.view_name;
                EXCEPTION
                    WHEN OTHERS THEN
                        RAISE WARNING 'Could not recreate dependent view %.%: %. This view must be updated manually to match the new QtyDemand_QtySupply_V columns.',
                            v_rec.view_schema, v_rec.view_name, SQLERRM;
                END;
            END LOOP;

        -- Clean up staging table
        DROP TABLE tmp_qtydemand_dependent_views;
    END
$$;
