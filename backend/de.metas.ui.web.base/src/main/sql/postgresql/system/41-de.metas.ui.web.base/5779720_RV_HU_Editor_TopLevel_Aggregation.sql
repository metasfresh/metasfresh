-- 2026-02-13T00:00:00Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- View: RV_HU_Editor_TopLevel_Aggregation
-- Purpose: TopLevelAggregation feature - Aggregated HU counts for HU Editor views

DROP VIEW IF EXISTS RV_HU_Editor_TopLevel_Aggregation;
CREATE OR REPLACE VIEW RV_HU_Editor_TopLevel_Aggregation AS
SELECT
    -- Aggregation ID (MD5 hash of grouping dimensions)
    MD5(
        COALESCE(top_level_unit_type, '') || '|' ||
        COALESCE(hustatus, '') || '|' ||
        COALESCE(isreserved::text, '') || '|' ||
        COALESCE(m_product_id::text, '') || '|' ||
        COALESCE(lotnumber, '') || '|' ||
        COALESCE(bestbeforedate::text, '')
    ) AS aggregation_id,

    -- Grouping dimensions
    top_level_unit_type,
    hustatus,
    isreserved,
    m_product_id,
    lotnumber,
    bestbeforedate,

    -- Aggregated counts
    COUNT(DISTINCT CASE WHEN top_level_unit_type = 'LU' THEN top_level_hu_id END) AS lu_count,

    -- TU count includes:
    -- 1. Direct TUs (top level unit type = TU)
    -- 2. TUs under LUs (children of LUs with unit type = TU)
    -- 3. Aggregated TUs (VHUs with ItemType='HA', counted by Qty field)
    COALESCE(
        COUNT(DISTINCT CASE WHEN top_level_unit_type = 'TU' THEN top_level_hu_id END) +
        SUM(CASE WHEN aggregated_tu_qty IS NOT NULL THEN aggregated_tu_qty ELSE 0 END),
        0
    ) AS tu_count,

    COUNT(DISTINCT CASE WHEN top_level_unit_type = 'V' THEN top_level_hu_id END) AS vhu_count,

    -- Total CU quantity in base UOM
    SUM(cu_qty) AS cu_total_qty,

    -- UOM for CU quantity (assuming all products in same aggregation have same UOM)
    MAX(c_uom_id) AS c_uom_id

FROM (
    SELECT
        -- Top level HU identification
        CASE
            WHEN hu.M_HU_Item_Parent_ID IS NULL THEN hu.M_HU_ID
            ELSE (
                SELECT parent_hu.M_HU_ID
                FROM M_HU parent_hu
                INNER JOIN M_HU_Item parent_item ON parent_hu.M_HU_Item_Parent_ID = parent_item.M_HU_Item_ID
                WHERE parent_item.M_HU_ID = hu.M_HU_ID
                AND parent_hu.M_HU_Item_Parent_ID IS NULL
            )
        END AS top_level_hu_id,

        -- Top level HU unit type (LU, TU, V)
        (
            SELECT piversion.HU_UnitType
            FROM M_HU top_hu
            INNER JOIN M_HU_PI_Version piversion ON top_hu.M_HU_PI_Version_ID = piversion.M_HU_PI_Version_ID
            WHERE top_hu.M_HU_ID = CASE
                WHEN hu.M_HU_Item_Parent_ID IS NULL THEN hu.M_HU_ID
                ELSE (
                    SELECT parent_hu.M_HU_ID
                    FROM M_HU parent_hu
                    INNER JOIN M_HU_Item parent_item ON parent_hu.M_HU_Item_Parent_ID = parent_item.M_HU_Item_ID
                    WHERE parent_item.M_HU_ID = hu.M_HU_ID
                    AND parent_hu.M_HU_Item_Parent_ID IS NULL
                )
            END
        ) AS top_level_unit_type,

        -- HU Status and Reservation
        hu.HUStatus AS hustatus,
        hu.IsReserved AS isreserved,

        -- Product information
        stor.M_Product_ID AS m_product_id,
        stor.C_UOM_ID AS c_uom_id,
        stor.Qty AS cu_qty,

        -- Attributes
        (
            SELECT av.Value
            FROM M_HU_Attribute hua
            INNER JOIN M_Attribute a ON hua.M_Attribute_ID = a.M_Attribute_ID
            INNER JOIN M_AttributeValue av ON hua.M_AttributeValue_ID = av.M_AttributeValue_ID
            WHERE hua.M_HU_ID = hu.M_HU_ID
            AND a.Value = 'LotNumber'
            LIMIT 1
        ) AS lotnumber,

        (
            SELECT hua.ValueDate
            FROM M_HU_Attribute hua
            INNER JOIN M_Attribute a ON hua.M_Attribute_ID = a.M_Attribute_ID
            WHERE hua.M_HU_ID = hu.M_HU_ID
            AND a.Value = 'BestBeforeDate'
            LIMIT 1
        ) AS bestbeforedate,

        -- Aggregated TU quantity (for VHUs with ItemType='HA')
        CASE
            WHEN (
                SELECT piversion.HU_UnitType
                FROM M_HU_PI_Version piversion
                WHERE hu.M_HU_PI_Version_ID = piversion.M_HU_PI_Version_ID
            ) = 'V'
            AND EXISTS (
                SELECT 1
                FROM M_HU_Item hui
                WHERE hui.M_HU_ID = hu.M_HU_ID
                AND hui.ItemType = 'HA'
            )
            THEN (
                SELECT hui.Qty
                FROM M_HU_Item hui
                WHERE hui.M_HU_ID = hu.M_HU_ID
                AND hui.ItemType = 'HA'
                LIMIT 1
            )
            ELSE NULL
        END AS aggregated_tu_qty

    FROM M_HU hu
    INNER JOIN M_HU_Storage stor ON hu.M_HU_ID = stor.M_HU_ID
    WHERE hu.IsActive = 'Y'

) hu_data

GROUP BY
    top_level_unit_type,
    hustatus,
    isreserved,
    m_product_id,
    lotnumber,
    bestbeforedate

HAVING COUNT(*) > 0;

