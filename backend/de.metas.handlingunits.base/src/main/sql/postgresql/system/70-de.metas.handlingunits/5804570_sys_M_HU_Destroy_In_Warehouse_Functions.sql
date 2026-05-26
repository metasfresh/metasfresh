-- 2026-05-26
-- SQL function: m_hu_destroy_in_warehouse
--
-- Destroys all top-level HUs in a given warehouse whose status counts toward
-- warehouse stock (Active='A', Picked='S', Issued='I'). HUs with status
-- Shipped='E' or already Destroyed='D' are left untouched.
--
-- Steps:
--   1. Identify top-level parent HUs to destroy (no M_HU_Item_Parent_ID).
--   2. Capture enriched report data into backup.m_hu_destroy_pi_<AD_PInstance_ID>
--      (always created, even if empty, so the Java caller can always query it).
--   3. Backup m_hu via backup_table() before each UPDATE.
--   4. Set parent HUs to HUStatus='D', IsActive='N'.
--   5. Recursively find and destroy all descendant HUs.
--
-- The report table (backup.m_hu_destroy_pi_<pinstance_id>) persists after the
-- process run so that administrators can audit what was destroyed.

CREATE OR REPLACE FUNCTION m_hu_destroy_in_warehouse(
    p_m_warehouse_id  numeric,
    p_ad_pinstance_id numeric DEFAULT 0
)
    RETURNS text
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_parents_count  bigint := 0;
    v_children_count bigint := 0;
    v_backup_name    text;
    v_report_table   text;
    v_report_sql     text;
    v_summary        text;
BEGIN
    v_report_table := 'backup.m_hu_destroy_pi_' || p_ad_pinstance_id::text;

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
    -- Step 2: Create permanent report capture table (always, even if empty)
    --         Uses a recursive CTE to aggregate product quantities from the
    --         entire HU hierarchy (LU → TU → VHU).
    -- -----------------------------------------------------------------------
    v_report_sql :=
            'CREATE TABLE ' || v_report_table || $q$ AS
            WITH RECURSIVE hu_hierarchy AS (
                SELECT t.m_hu_id AS root_hu_id,
                       t.m_hu_id AS hu_id
                FROM tmp_hu_destroy_parents t
                UNION ALL
                SELECT h.root_hu_id,
                       child.m_hu_id
                FROM hu_hierarchy h
                         JOIN m_hu_item item ON item.m_hu_id = h.hu_id
                         JOIN m_hu child ON child.m_hu_item_parent_id = item.m_hu_item_id
            )
            SELECT p.m_hu_id                                                                     AS hu_id,
                   hu.hustatus                                                                    AS status_vor_vernichtung,
                   l.value                                                                       AS lagerort,
                   w.name                                                                        AS lager,
                   COALESCE(string_agg(DISTINCT pr.name, ', ' ORDER BY pr.name), '-')           AS produkte,
                   SUM(hus.qty)                                                                  AS gesamt_menge,
                   MIN(uom.uomsymbol)                                                            AS mengeneinheit,
                   hu.created                                                                    AS erstellt_am
            FROM tmp_hu_destroy_parents p
                     JOIN m_hu hu ON hu.m_hu_id = p.m_hu_id
                     JOIN m_locator l ON l.m_locator_id = hu.m_locator_id
                     JOIN m_warehouse w ON w.m_warehouse_id = l.m_warehouse_id
                     LEFT JOIN hu_hierarchy hier ON hier.root_hu_id = p.m_hu_id
                     LEFT JOIN m_hu_storage hus ON hus.m_hu_id = hier.hu_id
                     LEFT JOIN m_product pr ON pr.m_product_id = hus.m_product_id
                     LEFT JOIN c_uom uom ON uom.c_uom_id = hus.c_uom_id
            GROUP BY p.m_hu_id, hu.hustatus, l.value, w.name, hu.created
            ORDER BY w.name, l.value, p.m_hu_id
            $q$;
    EXECUTE v_report_sql;
    RAISE NOTICE 'Bericht-Tabelle erstellt: %', v_report_table;

    -- Early exit if nothing to do (report table was created but is empty)
    IF v_parents_count = 0 THEN
        RETURN 'Keine HUs mit Status A/S/I im Lager ' || p_m_warehouse_id::text || ' gefunden. Bericht: ' || v_report_table;
    END IF;

    -- -----------------------------------------------------------------------
    -- Step 3: Backup m_hu before updating parent HUs
    -- -----------------------------------------------------------------------
    v_backup_name := backup_table('m_hu', '_pi' || p_ad_pinstance_id::text);
    RAISE NOTICE 'Backup vor Eltern-HU Update: %', v_backup_name;

    -- -----------------------------------------------------------------------
    -- Step 4: Destroy parent HUs
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
    -- Step 5: Collect all descendant HUs (children, grandchildren, …)
    --         Excludes already-destroyed HUs to avoid redundant work.
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
        -- Backup m_hu before updating child HUs
        v_backup_name := backup_table('m_hu', '_pi' || p_ad_pinstance_id::text);
        RAISE NOTICE 'Backup vor Kind-HU Update: %', v_backup_name;

        UPDATE m_hu
        SET isactive  = 'N',
            hustatus  = 'D',
            updated   = now(),
            updatedby = 99
        WHERE m_hu_id IN (SELECT m_hu_id FROM tmp_hu_destroy_children);
        GET DIAGNOSTICS v_children_count = ROW_COUNT;
        RAISE NOTICE 'Kind-HUs vernichtet: %', v_children_count;
    END IF;

    v_summary := format('Vernichtet: %s Eltern-HUs, %s Kind-HUs. Bericht: %s',
                        v_parents_count, v_children_count, v_report_table);
    RETURN v_summary;
END;
$$;

COMMENT ON FUNCTION m_hu_destroy_in_warehouse(numeric, numeric) IS
    'Destroys all top-level HUs in the given warehouse whose HUStatus counts toward warehouse stock (A/S/I).
     Creates a permanent report table backup.m_hu_destroy_pi_<AD_PInstance_ID> with product/qty details before destruction.
     Backs up m_hu via backup_table() before each UPDATE. Shipped (E) and already Destroyed (D) HUs are skipped.';
