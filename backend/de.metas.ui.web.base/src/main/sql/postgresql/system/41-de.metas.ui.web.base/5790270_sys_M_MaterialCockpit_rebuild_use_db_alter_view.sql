-- Fix M_MaterialCockpit_rebuild() to use db_alter_view instead of DROP/CREATE.
-- This prevents failures when other views (e.g. rv_purchasecockpit) depend on QtyDemand_QtySupply_V.
-- db_alter_view automatically handles dependent views by saving, dropping, and recreating them.

CREATE OR REPLACE FUNCTION M_MaterialCockpit_rebuild()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_source_view   TEXT;
    v_count         INTEGER;
    v_missing_cols  TEXT;
BEGIN
    --
    -- Step 1: Find customer/override views matching '%_MaterialCockpit_V' excluding the base view
    --
    SELECT COUNT(*), MAX(viewname)
    INTO v_count, v_source_view
    FROM pg_views
    WHERE viewname LIKE '%_materialcockpit_v'
      AND viewname <> 'm_materialcockpit_base_v'
      AND viewname <> 'qtydemand_qtysupply_v';

    --
    -- Step 2: Decide which source view to use
    --
    IF v_count = 0 THEN
        v_source_view := 'm_materialcockpit_base_v';
        RAISE NOTICE 'M_MaterialCockpit_rebuild: No customer override found. Using base view: %', v_source_view;
    ELSIF v_count = 1 THEN
        RAISE NOTICE 'M_MaterialCockpit_rebuild: Found customer override: %', v_source_view;
    ELSE
        RAISE EXCEPTION 'M_MaterialCockpit_rebuild: Found % customer override views matching %%_MaterialCockpit_V. Expected at most 1.', v_count;
    END IF;

    --
    -- Step 3: Validate that the chosen source has all required base columns
    --
    SELECT string_agg(bc.column_name, ', ')
    INTO v_missing_cols
    FROM information_schema.columns bc
    WHERE bc.table_name = 'm_materialcockpit_base_v'
      AND bc.table_schema = 'public'
      AND NOT EXISTS (SELECT 1
                      FROM information_schema.columns sc
                      WHERE sc.table_name = v_source_view
                        AND sc.table_schema = 'public'
                        AND sc.column_name = bc.column_name);

    IF v_missing_cols IS NOT NULL AND v_source_view <> 'm_materialcockpit_base_v' THEN
        RAISE EXCEPTION 'M_MaterialCockpit_rebuild: Source view "%" is missing base columns: %', v_source_view, v_missing_cols;
    END IF;

    --
    -- Step 4: Recreate the API view QtyDemand_QtySupply_V using db_alter_view
    -- (handles dependent views like rv_purchasecockpit automatically)
    --
    PERFORM db_alter_view('QtyDemand_QtySupply_V', 'SELECT * FROM ' || v_source_view);

    RAISE NOTICE 'M_MaterialCockpit_rebuild: Successfully created QtyDemand_QtySupply_V from %', v_source_view;
END;
$$;
