-- 2026-03-12
-- Migration script for M_HU_PI_Item_Product consolidation SQL functions.
-- Authoritative source files:
--   backend/de.metas.handlingunits.base/src/main/sql/postgresql/ddl/functions/m_hu_pi_item_product_consolidate.sql
--   backend/de.metas.handlingunits.base/src/main/sql/postgresql/ddl/functions/m_hu_pi_item_product_consolidate_report.sql

-- =============================================
-- Function 1: m_hu_pi_item_product_consolidate
-- =============================================
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
        RAISE NOTICE 'Normalized EAN->GTIN: % records', v_count;
        v_summary := v_summary || 'Normalized EAN->GTIN: ' || v_count || ' records. ';

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
    -- (Intentionally global -- no AD_Client/AD_Org filter.
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

        -- 2d: Discover and update FK references dynamically
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
                -- Backup FK table
                v_backup_name := backup_table(v_fk_record.fk_table, '_pi' || p_ad_pinstance_id);
                RAISE NOTICE 'Backup FK table: %', v_backup_name;

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
                        RAISE NOTICE 'WARNING: Could not update FK references in %.% due to unique constraint violation -- skipping', v_fk_record.fk_table, v_fk_record.fk_column;
                END;
            END LOOP;

        -- 2e: Update survivors with widest date range and clear C_BPartner_ID
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

        -- 2f: Deactivate non-survivors
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

        -- 2g: Drop temp tables
        DROP TABLE IF EXISTS tmp_consolidation_groups;
        DROP TABLE IF EXISTS tmp_survivors;

        v_summary := v_summary || 'Consolidated ' || v_groups_count || ' groups, deactivated ' || v_deactivated || ' records.';
    END IF;

    RETURN v_summary;
END;
$$;

COMMENT ON FUNCTION m_hu_pi_item_product_consolidate(boolean, boolean, numeric) IS
    'Normalizes GTIN/EAN fields and consolidates duplicate M_HU_PI_Item_Product records with the same GTIN into one partner-less record. Backs up affected tables before modifications.';

-- =============================================
-- Function 2: m_hu_pi_item_product_consolidate_report
-- =============================================
CREATE OR REPLACE FUNCTION m_hu_pi_item_product_consolidate_report(p_ad_language text DEFAULT 'de_DE')
    RETURNS TABLE
            (
                issue_type              text,
                gtin                    text,
                product_value           text,
                product_name            text,
                pi_name                 text,
                m_hu_pi_item_product_id numeric,
                bpartner_value          text,
                bpartner_name           text,
                qty                     numeric,
                uom_symbol              text,
                validfrom               timestamp,
                validto                 timestamp,
                conflicting_field       text
            )
    LANGUAGE plpgsql
    STABLE
AS
$BODY$
BEGIN
    RETURN QUERY

        --
        -- 1. CONFLICT_DIFFERENT_PI
        --    Active records sharing a GTIN but with different M_HU_PI_Item_ID
        --
        SELECT COALESCE(trl1.MsgText, msg1.MsgText)::text AS issue_type,
               pip.GTIN::text                              AS gtin,
               prod.Value::text                            AS product_value,
               prod.Name::text                             AS product_name,
               pi.Name::text                               AS pi_name,
               pip.M_HU_PI_Item_Product_ID                 AS m_hu_pi_item_product_id,
               bp.Value::text                              AS bpartner_value,
               bp.Name::text                               AS bpartner_name,
               pip.Qty                                     AS qty,
               uom.UOMSymbol::text                         AS uom_symbol,
               pip.ValidFrom::timestamp                    AS validfrom,
               pip.ValidTo::timestamp                      AS validto,
               NULL::text                                  AS conflicting_field
        FROM M_HU_PI_Item_Product pip
                 JOIN M_Product prod ON prod.M_Product_ID = pip.M_Product_ID
                 JOIN M_HU_PI_Item pi_item ON pi_item.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                 JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = pi_item.M_HU_PI_Version_ID
                 JOIN M_HU_PI pi ON pi.M_HU_PI_ID = piv.M_HU_PI_ID
                 LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = pip.C_BPartner_ID
                 LEFT JOIN C_UOM uom ON uom.C_UOM_ID = pip.C_UOM_ID
                 JOIN AD_Message msg1 ON msg1.Value = 'M_HU_PI_Item_Product_Conflict_DifferentPI'
                 LEFT JOIN AD_Message_Trl trl1
                           ON trl1.AD_Message_ID = msg1.AD_Message_ID
                               AND trl1.AD_Language = p_ad_language
                               AND trl1.IsActive = 'Y'
        WHERE pip.IsActive = 'Y'
          AND pip.GTIN IS NOT NULL
          AND pip.GTIN <> ''
          AND pip.GTIN IN (SELECT sub.GTIN
                           FROM M_HU_PI_Item_Product sub
                           WHERE sub.IsActive = 'Y'
                             AND sub.GTIN IS NOT NULL
                             AND sub.GTIN <> ''
                           GROUP BY sub.GTIN
                           HAVING COUNT(DISTINCT sub.M_HU_PI_Item_ID) > 1)

        UNION ALL

        --
        -- 2. CONFLICT_DIFFERENT_PRODUCT
        --    Active records sharing a GTIN and M_HU_PI_Item_ID but with different M_Product_ID
        --
        SELECT COALESCE(trl2.MsgText, msg2.MsgText)::text AS issue_type,
               pip.GTIN::text                              AS gtin,
               prod.Value::text                            AS product_value,
               prod.Name::text                             AS product_name,
               pi.Name::text                               AS pi_name,
               pip.M_HU_PI_Item_Product_ID                 AS m_hu_pi_item_product_id,
               bp.Value::text                              AS bpartner_value,
               bp.Name::text                               AS bpartner_name,
               pip.Qty                                     AS qty,
               uom.UOMSymbol::text                         AS uom_symbol,
               pip.ValidFrom::timestamp                    AS validfrom,
               pip.ValidTo::timestamp                      AS validto,
               NULL::text                                  AS conflicting_field
        FROM M_HU_PI_Item_Product pip
                 JOIN M_Product prod ON prod.M_Product_ID = pip.M_Product_ID
                 JOIN M_HU_PI_Item pi_item ON pi_item.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                 JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = pi_item.M_HU_PI_Version_ID
                 JOIN M_HU_PI pi ON pi.M_HU_PI_ID = piv.M_HU_PI_ID
                 LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = pip.C_BPartner_ID
                 LEFT JOIN C_UOM uom ON uom.C_UOM_ID = pip.C_UOM_ID
                 JOIN AD_Message msg2 ON msg2.Value = 'M_HU_PI_Item_Product_Conflict_DifferentProduct'
                 LEFT JOIN AD_Message_Trl trl2
                           ON trl2.AD_Message_ID = msg2.AD_Message_ID
                               AND trl2.AD_Language = p_ad_language
                               AND trl2.IsActive = 'Y'
        WHERE pip.IsActive = 'Y'
          AND pip.GTIN IS NOT NULL
          AND pip.GTIN <> ''
          AND (pip.GTIN, pip.M_HU_PI_Item_ID) IN (SELECT sub.GTIN, sub.M_HU_PI_Item_ID
                                                   FROM M_HU_PI_Item_Product sub
                                                   WHERE sub.IsActive = 'Y'
                                                     AND sub.GTIN IS NOT NULL
                                                     AND sub.GTIN <> ''
                                                   GROUP BY sub.GTIN, sub.M_HU_PI_Item_ID
                                                   HAVING COUNT(DISTINCT sub.M_Product_ID) > 1)

        UNION ALL

        --
        -- 3. CONFLICT_DIFFERENT_FIELDS
        --    Active records sharing (M_Product_ID, GTIN, M_HU_PI_Item_ID) with >1 record
        --    where non-date fields differ
        --
        SELECT COALESCE(trl3.MsgText, msg3.MsgText)::text AS issue_type,
               pip.GTIN::text                              AS gtin,
               prod.Value::text                            AS product_value,
               prod.Name::text                             AS product_name,
               pi.Name::text                               AS pi_name,
               pip.M_HU_PI_Item_Product_ID                 AS m_hu_pi_item_product_id,
               bp.Value::text                              AS bpartner_value,
               bp.Name::text                               AS bpartner_name,
               pip.Qty                                     AS qty,
               uom.UOMSymbol::text                         AS uom_symbol,
               pip.ValidFrom::timestamp                    AS validfrom,
               pip.ValidTo::timestamp                      AS validto,
               diffs.conflicting_fields::text              AS conflicting_field
        FROM M_HU_PI_Item_Product pip
                 JOIN M_Product prod ON prod.M_Product_ID = pip.M_Product_ID
                 JOIN M_HU_PI_Item pi_item ON pi_item.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                 JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = pi_item.M_HU_PI_Version_ID
                 JOIN M_HU_PI pi ON pi.M_HU_PI_ID = piv.M_HU_PI_ID
                 LEFT JOIN C_BPartner bp ON bp.C_BPartner_ID = pip.C_BPartner_ID
                 LEFT JOIN C_UOM uom ON uom.C_UOM_ID = pip.C_UOM_ID
                 JOIN AD_Message msg3 ON msg3.Value = 'M_HU_PI_Item_Product_Conflict_DifferentFields'
                 LEFT JOIN AD_Message_Trl trl3
                           ON trl3.AD_Message_ID = msg3.AD_Message_ID
                               AND trl3.AD_Language = p_ad_language
                               AND trl3.IsActive = 'Y'
                 JOIN LATERAL (
                    SELECT string_agg(DISTINCT field_name, ', ' ORDER BY field_name) AS conflicting_fields
                    FROM (
                             SELECT 'Qty' AS field_name
                             FROM M_HU_PI_Item_Product other
                             WHERE other.IsActive = 'Y'
                               AND other.M_Product_ID = pip.M_Product_ID
                               AND other.GTIN = pip.GTIN
                               AND other.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                               AND other.M_HU_PI_Item_Product_ID <> pip.M_HU_PI_Item_Product_ID
                               AND COALESCE(other.Qty, 0) <> COALESCE(pip.Qty, 0)
                             UNION
                             SELECT 'C_UOM_ID'
                             FROM M_HU_PI_Item_Product other
                             WHERE other.IsActive = 'Y'
                               AND other.M_Product_ID = pip.M_Product_ID
                               AND other.GTIN = pip.GTIN
                               AND other.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                               AND other.M_HU_PI_Item_Product_ID <> pip.M_HU_PI_Item_Product_ID
                               AND COALESCE(other.C_UOM_ID, 0) <> COALESCE(pip.C_UOM_ID, 0)
                             UNION
                             SELECT 'IsInfiniteCapacity'
                             FROM M_HU_PI_Item_Product other
                             WHERE other.IsActive = 'Y'
                               AND other.M_Product_ID = pip.M_Product_ID
                               AND other.GTIN = pip.GTIN
                               AND other.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                               AND other.M_HU_PI_Item_Product_ID <> pip.M_HU_PI_Item_Product_ID
                               AND COALESCE(other.IsInfiniteCapacity, 'N') <> COALESCE(pip.IsInfiniteCapacity, 'N')
                             UNION
                             SELECT 'EAN_TU'
                             FROM M_HU_PI_Item_Product other
                             WHERE other.IsActive = 'Y'
                               AND other.M_Product_ID = pip.M_Product_ID
                               AND other.GTIN = pip.GTIN
                               AND other.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                               AND other.M_HU_PI_Item_Product_ID <> pip.M_HU_PI_Item_Product_ID
                               AND COALESCE(other.EAN_TU, '') <> COALESCE(pip.EAN_TU, '')
                             UNION
                             SELECT 'UPC'
                             FROM M_HU_PI_Item_Product other
                             WHERE other.IsActive = 'Y'
                               AND other.M_Product_ID = pip.M_Product_ID
                               AND other.GTIN = pip.GTIN
                               AND other.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                               AND other.M_HU_PI_Item_Product_ID <> pip.M_HU_PI_Item_Product_ID
                               AND COALESCE(other.UPC, '') <> COALESCE(pip.UPC, '')
                             UNION
                             SELECT 'IsAllowAnyProduct'
                             FROM M_HU_PI_Item_Product other
                             WHERE other.IsActive = 'Y'
                               AND other.M_Product_ID = pip.M_Product_ID
                               AND other.GTIN = pip.GTIN
                               AND other.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                               AND other.M_HU_PI_Item_Product_ID <> pip.M_HU_PI_Item_Product_ID
                               AND COALESCE(other.IsAllowAnyProduct, 'N') <> COALESCE(pip.IsAllowAnyProduct, 'N')
                             UNION
                             SELECT 'IsDefaultForProduct'
                             FROM M_HU_PI_Item_Product other
                             WHERE other.IsActive = 'Y'
                               AND other.M_Product_ID = pip.M_Product_ID
                               AND other.GTIN = pip.GTIN
                               AND other.M_HU_PI_Item_ID = pip.M_HU_PI_Item_ID
                               AND other.M_HU_PI_Item_Product_ID <> pip.M_HU_PI_Item_Product_ID
                               AND COALESCE(other.IsDefaultForProduct, 'N') <> COALESCE(pip.IsDefaultForProduct, 'N')
                         ) fields
                    ) diffs ON diffs.conflicting_fields IS NOT NULL
        WHERE pip.IsActive = 'Y'
          AND pip.GTIN IS NOT NULL
          AND pip.GTIN <> ''
          AND (pip.M_Product_ID, pip.GTIN, pip.M_HU_PI_Item_ID) IN
              (SELECT sub.M_Product_ID, sub.GTIN, sub.M_HU_PI_Item_ID
               FROM M_HU_PI_Item_Product sub
               WHERE sub.IsActive = 'Y'
                 AND sub.GTIN IS NOT NULL
                 AND sub.GTIN <> ''
               GROUP BY sub.M_Product_ID, sub.GTIN, sub.M_HU_PI_Item_ID
               HAVING COUNT(*) > 1)

        ORDER BY issue_type, gtin, product_value;
END;
$BODY$;

COMMENT ON FUNCTION m_hu_pi_item_product_consolidate_report(text) IS
    'Returns a data quality report of M_HU_PI_Item_Product records with GTIN conflicts: same GTIN across different packing instructions, different products, or records that could not be consolidated due to differing field values.';
