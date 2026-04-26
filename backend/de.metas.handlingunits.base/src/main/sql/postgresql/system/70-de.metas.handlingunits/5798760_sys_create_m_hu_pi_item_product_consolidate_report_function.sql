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
