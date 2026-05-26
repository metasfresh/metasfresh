-- 2026-05-26
-- SQL function: m_hu_destroy_in_warehouse
--
-- Destroys all top-level HUs in a given warehouse whose status counts toward
-- warehouse stock (Active='A', Picked='S', Issued='I').
-- HUs with status Shipped='E' or already Destroyed='D' are left untouched.
--
-- Steps:
--   1. Identify top-level parent HUs (no M_HU_Item_Parent_ID).
--   2. Backup m_hu via backup_table() before each UPDATE.
--   3. Set parent HUs to HUStatus='D', IsActive='N'.
--   4. Recursively find all descendant HUs and destroy them too.
--
-- Backup table names are logged via RAISE NOTICE so they appear in the
-- process log (AD_Process.IsLogWarning='Y' captures PostgreSQL warnings).

CREATE OR REPLACE FUNCTION m_hu_destroy_in_warehouse(p_m_warehouse_id numeric)
    RETURNS text
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_parents_count  bigint := 0;
    v_children_count bigint := 0;
    v_backup_name    text;
BEGIN
    -- -----------------------------------------------------------------------
    -- Step 1: Identify top-level parent HUs eligible for destruction
    -- -----------------------------------------------------------------------
    CREATE TEMP TABLE tmp_hu_destroy_parents ON COMMIT DROP AS
    SELECT hu.m_hu_id
    FROM m_hu hu
             JOIN m_locator l ON l.m_locator_id = hu.m_locator_id
    WHERE l.m_warehouse_id = p_m_warehouse_id
      AND hu.hustatus IN ('A', 'S', 'I')   -- counts for warehouse stock
      AND hu.isactive = 'Y'
      AND hu.m_hu_item_parent_id IS NULL;  -- top-level only

    SELECT COUNT(*) INTO v_parents_count FROM tmp_hu_destroy_parents;
    RAISE NOTICE 'Zu vernichtende Eltern-HUs: %', v_parents_count;

    -- -----------------------------------------------------------------------
    -- Step 2: Backup m_hu unconditionally (even if nothing to destroy)
    -- -----------------------------------------------------------------------
    v_backup_name := backup_table('m_hu', '_before_deactivate');
    RAISE NOTICE 'Backup: %', v_backup_name;

    IF v_parents_count = 0 THEN
        RETURN 'Keine HUs mit Status A/S/I im Lager ' || p_m_warehouse_id::text || ' gefunden.';
    END IF;

    -- -----------------------------------------------------------------------
    -- Step 3: Destroy parent HUs
    -- -----------------------------------------------------------------------
    UPDATE m_hu
    SET isactive  = 'N',
        hustatus  = 'D',
        updated   = now(),
        updatedby = 99
    WHERE m_hu_id IN (SELECT m_hu_id FROM tmp_hu_destroy_parents);
    GET DIAGNOSTICS v_parents_count = ROW_COUNT;
    RAISE NOTICE 'Eltern-HUs vernichtet: %', v_parents_count;

    -- -----------------------------------------------------------------------
    -- Step 4: Collect all descendant HUs (children, grandchildren, …)
    --         Uses a recursive CTE to traverse the full HU hierarchy.
    --         Skips HUs already set to Destroyed to avoid redundant updates.
    -- -----------------------------------------------------------------------
    CREATE TEMP TABLE tmp_hu_destroy_children ON COMMIT DROP AS
    WITH RECURSIVE hu_tree AS (
        SELECT t.m_hu_id
        FROM tmp_hu_destroy_parents t
        UNION ALL
        SELECT child.m_hu_id
        FROM hu_tree p
                 JOIN m_hu_item item ON item.m_hu_id = p.m_hu_id
                 JOIN m_hu child ON child.m_hu_item_parent_id = item.m_hu_item_id
    )
    SELECT DISTINCT ht.m_hu_id
    FROM hu_tree ht
             JOIN m_hu hu ON hu.m_hu_id = ht.m_hu_id
    WHERE ht.m_hu_id NOT IN (SELECT m_hu_id FROM tmp_hu_destroy_parents)
      AND hu.hustatus != 'D';

    SELECT COUNT(*) INTO v_children_count FROM tmp_hu_destroy_children;
    RAISE NOTICE 'Kind-HUs gefunden: %', v_children_count;

    IF v_children_count > 0 THEN
        UPDATE m_hu
        SET isactive  = 'N',
            hustatus  = 'D',
            updated   = now(),
            updatedby = 99
        WHERE m_hu_id IN (SELECT m_hu_id FROM tmp_hu_destroy_children);
        GET DIAGNOSTICS v_children_count = ROW_COUNT;
        RAISE NOTICE 'Kind-HUs vernichtet: %', v_children_count;
    END IF;

    RETURN format('Vernichtet: %s Eltern-HUs, %s Kind-HUs im Lager %s.',
                  v_parents_count, v_children_count, p_m_warehouse_id);
END;
$$;

COMMENT ON FUNCTION m_hu_destroy_in_warehouse(numeric) IS
    'Destroys all top-level HUs in the given warehouse whose HUStatus counts toward warehouse stock (A/S/I).
     Shipped (E) and already Destroyed (D) HUs are skipped.
     Backs up m_hu via backup_table() before each UPDATE.
     Backup table names are emitted as RAISE NOTICE and appear in the process log.';
