CREATE OR REPLACE FUNCTION m_hu_pi_item_product_consolidate(
    p_normalize_gtin_ean boolean,
    p_consolidate        boolean,
    p_ad_pinstance_id    numeric DEFAULT 0
)
    RETURNS text
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_count          bigint;
    v_summary        text := '';
    v_backup_name    text;
    v_groups_count   bigint := 0;
    v_deactivated    bigint := 0;
    v_fk_record      record;
BEGIN
    -- Early return if nothing requested
    IF NOT p_normalize_gtin_ean AND NOT p_consolidate THEN
        RETURN 'No action requested';
    END IF;

    -- Backup main table
    v_backup_name := backup_table('M_HU_PI_Item_Product', '_pi' || p_ad_pinstance_id);
    RAISE NOTICE 'Backup: %', v_backup_name;

    -- ==========================================
    -- Step 1: Normalize GTIN/EAN
    -- ==========================================
    IF p_normalize_gtin_ean THEN
        -- Copy EAN_TU to GTIN where GTIN is missing
        UPDATE M_HU_PI_Item_Product
        SET GTIN    = EAN_TU,
            Updated = now(),
            UpdatedBy = 99
        WHERE GTIN IS NULL
          AND EAN_TU IS NOT NULL
          AND IsActive = 'Y';
        GET DIAGNOSTICS v_count = ROW_COUNT;
        RAISE NOTICE 'Normalized EAN→GTIN: % records', v_count;
        v_summary := v_summary || 'Normalized EAN→GTIN: ' || v_count || ' records. ';

        -- Clear EAN_TU where it duplicates GTIN
        UPDATE M_HU_PI_Item_Product
        SET EAN_TU  = NULL,
            Updated = now(),
            UpdatedBy = 99
        WHERE GTIN = EAN_TU
          AND EAN_TU IS NOT NULL
          AND IsActive = 'Y';
        GET DIAGNOSTICS v_count = ROW_COUNT;
        RAISE NOTICE 'Cleared duplicate EAN: % records', v_count;
        v_summary := v_summary || 'Cleared duplicate EAN: ' || v_count || ' records. ';
    END IF;

    -- ==========================================
    -- Step 2: Consolidate duplicates
    -- (Intentionally global — no AD_Client/AD_Org filter.
    --  This is a one-time migration tool meant to run across
    --  all records in the database.)
    -- ==========================================
    IF p_consolidate THEN
        -- Clean up temp tables in case of re-run after error
        DROP TABLE IF EXISTS tmp_consolidation_groups;
        DROP TABLE IF EXISTS tmp_survivors;

        -- 2a: Find consolidation groups (same Product + GTIN + PI_Item, multiple active records)
        CREATE TEMP TABLE tmp_consolidation_groups AS
        SELECT M_Product_ID,
               GTIN,
               M_HU_PI_Item_ID,
               COUNT(*) AS record_count
        FROM M_HU_PI_Item_Product
        WHERE IsActive = 'Y'
          AND GTIN IS NOT NULL
        GROUP BY M_Product_ID, GTIN, M_HU_PI_Item_ID
        HAVING COUNT(*) > 1;

        SELECT COUNT(*) INTO v_groups_count FROM tmp_consolidation_groups;
        RAISE NOTICE 'Found % consolidation groups', v_groups_count;

        IF v_groups_count = 0 THEN
            DROP TABLE IF EXISTS tmp_consolidation_groups;
            v_summary := v_summary || 'Consolidation: 0 groups found, nothing to do.';
            RETURN v_summary;
        END IF;

        -- 2b: Remove groups with field conflicts
        DELETE FROM tmp_consolidation_groups g
        WHERE EXISTS (
            SELECT 1
            FROM M_HU_PI_Item_Product p1
                     JOIN M_HU_PI_Item_Product p2
                          ON p1.M_Product_ID = p2.M_Product_ID
                              AND p1.GTIN = p2.GTIN
                              AND p1.M_HU_PI_Item_ID = p2.M_HU_PI_Item_ID
                              AND p1.M_HU_PI_Item_Product_ID < p2.M_HU_PI_Item_Product_ID
            WHERE p1.IsActive = 'Y'
              AND p2.IsActive = 'Y'
              AND p1.M_Product_ID = g.M_Product_ID
              AND p1.GTIN = g.GTIN
              AND p1.M_HU_PI_Item_ID = g.M_HU_PI_Item_ID
              AND (p1.Qty != p2.Qty
                OR COALESCE(p1.C_UOM_ID, 0) != COALESCE(p2.C_UOM_ID, 0)
                OR p1.IsInfiniteCapacity != p2.IsInfiniteCapacity
                OR COALESCE(p1.EAN_TU, '') != COALESCE(p2.EAN_TU, '')
                OR COALESCE(p1.UPC, '') != COALESCE(p2.UPC, '')
                OR p1.IsAllowAnyProduct != p2.IsAllowAnyProduct
                OR p1.IsDefaultForProduct != p2.IsDefaultForProduct)
        );
        GET DIAGNOSTICS v_count = ROW_COUNT;
        RAISE NOTICE 'Skipped % groups with field conflicts', v_count;

        SELECT COUNT(*) INTO v_groups_count FROM tmp_consolidation_groups;
        RAISE NOTICE 'Proceeding with % conflict-free groups', v_groups_count;

        IF v_groups_count = 0 THEN
            DROP TABLE IF EXISTS tmp_consolidation_groups;
            v_summary := v_summary || 'Consolidation: all groups had conflicts, nothing consolidated.';
            RETURN v_summary;
        END IF;

        -- 2c: Pick survivors (prefer C_BPartner_ID IS NULL, then lowest ID)
        CREATE TEMP TABLE tmp_survivors AS
        SELECT DISTINCT ON (g.M_Product_ID, g.GTIN, g.M_HU_PI_Item_ID)
            p.M_HU_PI_Item_Product_ID AS survivor_id,
            g.M_Product_ID,
            g.GTIN,
            g.M_HU_PI_Item_ID
        FROM tmp_consolidation_groups g
                 JOIN M_HU_PI_Item_Product p
                      ON p.M_Product_ID = g.M_Product_ID
                          AND p.GTIN = g.GTIN
                          AND p.M_HU_PI_Item_ID = g.M_HU_PI_Item_ID
                          AND p.IsActive = 'Y'
        ORDER BY g.M_Product_ID, g.GTIN, g.M_HU_PI_Item_ID,
                 (CASE WHEN p.C_BPartner_ID IS NULL THEN 0 ELSE 1 END),
                 p.M_HU_PI_Item_Product_ID;

        -- 2d: Backup FK tables (each table only once, even if it has multiple FK columns)
        FOR v_fk_record IN
            SELECT DISTINCT tc.table_name AS fk_table
            FROM information_schema.table_constraints tc
                     JOIN information_schema.constraint_column_usage ccu
                          ON tc.constraint_name = ccu.constraint_name
            WHERE tc.constraint_type = 'FOREIGN KEY'
              AND ccu.table_name = 'm_hu_pi_item_product'
              AND ccu.column_name = 'm_hu_pi_item_product_id'
            LOOP
                v_backup_name := backup_table(v_fk_record.fk_table, '_pi' || p_ad_pinstance_id);
                RAISE NOTICE 'Backup FK table: %', v_backup_name;
            END LOOP;

        -- 2e: Update FK references (per FK column)
        FOR v_fk_record IN
            SELECT DISTINCT
                tc.table_name  AS fk_table,
                kcu.column_name AS fk_column
            FROM information_schema.table_constraints tc
                     JOIN information_schema.key_column_usage kcu
                          ON tc.constraint_name = kcu.constraint_name
                     JOIN information_schema.constraint_column_usage ccu
                          ON tc.constraint_name = ccu.constraint_name
            WHERE tc.constraint_type = 'FOREIGN KEY'
              AND ccu.table_name = 'm_hu_pi_item_product'
              AND ccu.column_name = 'm_hu_pi_item_product_id'
            LOOP
                -- Update FK references from non-survivors to survivors
                BEGIN
                    EXECUTE format(
                            'UPDATE %I SET %I = s.survivor_id, Updated = now(), UpdatedBy = 99
                             FROM tmp_survivors s
                             JOIN M_HU_PI_Item_Product p ON p.M_Product_ID = s.M_Product_ID
                                 AND p.GTIN = s.GTIN AND p.M_HU_PI_Item_ID = s.M_HU_PI_Item_ID
                                 AND p.M_HU_PI_Item_Product_ID != s.survivor_id AND p.IsActive = ''Y''
                             WHERE %I.%I = p.M_HU_PI_Item_Product_ID',
                            v_fk_record.fk_table, v_fk_record.fk_column,
                            v_fk_record.fk_table, v_fk_record.fk_column
                            );
                    GET DIAGNOSTICS v_count = ROW_COUNT;
                    IF v_count > 0 THEN
                        RAISE NOTICE 'Updated % FK references in %.%', v_count, v_fk_record.fk_table, v_fk_record.fk_column;
                    END IF;
                EXCEPTION
                    WHEN unique_violation THEN
                        RAISE NOTICE 'WARNING: Could not update FK references in %.% due to unique constraint violation — skipping', v_fk_record.fk_table, v_fk_record.fk_column;
                END;
            END LOOP;

        -- 2f: Update survivors with widest date range and clear C_BPartner_ID
        UPDATE M_HU_PI_Item_Product survivor
        SET C_BPartner_ID = NULL,
            ValidFrom     = sub.min_validfrom,
            ValidTo       = sub.max_validto,
            Updated       = now(),
            UpdatedBy     = 99
        FROM (SELECT s.survivor_id,
                     MIN(p.ValidFrom) AS min_validfrom,
                     MAX(p.ValidTo)   AS max_validto
              FROM tmp_survivors s
                       JOIN M_HU_PI_Item_Product p
                            ON p.M_Product_ID = s.M_Product_ID
                                AND p.GTIN = s.GTIN
                                AND p.M_HU_PI_Item_ID = s.M_HU_PI_Item_ID
                                AND p.IsActive = 'Y'
              GROUP BY s.survivor_id) sub
        WHERE survivor.M_HU_PI_Item_Product_ID = sub.survivor_id;
        GET DIAGNOSTICS v_count = ROW_COUNT;
        RAISE NOTICE 'Updated % survivors (date range + cleared partner)', v_count;

        -- 2g: Deactivate non-survivors
        UPDATE M_HU_PI_Item_Product p
        SET IsActive  = 'N',
            Updated   = now(),
            UpdatedBy = 99
        FROM tmp_survivors s
        WHERE p.M_Product_ID = s.M_Product_ID
          AND p.GTIN = s.GTIN
          AND p.M_HU_PI_Item_ID = s.M_HU_PI_Item_ID
          AND p.M_HU_PI_Item_Product_ID != s.survivor_id
          AND p.IsActive = 'Y';
        GET DIAGNOSTICS v_deactivated = ROW_COUNT;
        RAISE NOTICE 'Deactivated % non-survivor records across % groups', v_deactivated, v_groups_count;

        -- 2h: Drop temp tables
        DROP TABLE IF EXISTS tmp_consolidation_groups;
        DROP TABLE IF EXISTS tmp_survivors;

        v_summary := v_summary || 'Consolidated ' || v_groups_count || ' groups, deactivated ' || v_deactivated || ' records.';
    END IF;

    RETURN v_summary;
END;
$$;

COMMENT ON FUNCTION m_hu_pi_item_product_consolidate(boolean, boolean, numeric) IS
    'Normalizes GTIN/EAN fields and consolidates duplicate M_HU_PI_Item_Product records with the same GTIN into one partner-less record. Backs up affected tables before modifications.';
