/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- EPCIS event JSON for a given M_InOut (shipment).
-- HU-based, DESADV-optional: core data from HU hierarchy, DESADV only for optional biz references.
--
-- Changes in me03#28815:
--   - Pallet discovery: M_InOut → M_InOutLine → M_HU_Assignment.M_LU_HU_ID (primary)
--                        + fallback via M_ShipmentSchedule_QtyPicked.M_LU_HU_ID
--   - Crate (TU) discovery: M_HU_Item (itemtype='HU' individual, 'HA' aggregated)
--   - Items: M_HU_Item_Storage per TU (not DESADV pack items)
--   - GRAI: M_HU_Attribute on TU level (comma-separated for aggregated)
--   - DESADV: LEFT JOIN (optional, for buyer/handover/PO references)
--   - New field: cuGTIN from M_Product.GTIN

CREATE OR REPLACE FUNCTION "de.metas.edi".get_epcis_events_json_fn(p_m_inout_id NUMERIC)
    RETURNS JSONB
AS
$$
DECLARE
    v_result            JSONB;
    v_grai_attribute_id NUMERIC;
    v_sscc_attribute_id NUMERIC;
    v_lot_attribute_id  NUMERIC;
    v_bbd_attribute_id  NUMERIC;
BEGIN
    -- Cache attribute IDs
    SELECT MAX(CASE WHEN value = 'GRAI' THEN m_attribute_id END),
           MAX(CASE WHEN value = 'SSCC18' THEN m_attribute_id END),
           MAX(CASE WHEN value = 'Lot-Nummer' THEN m_attribute_id END),
           MAX(CASE WHEN value = 'HU_BestBeforeDate' THEN m_attribute_id END)
    INTO v_grai_attribute_id, v_sscc_attribute_id, v_lot_attribute_id, v_bbd_attribute_id
    FROM m_attribute
    WHERE value IN ('GRAI', 'SSCC18', 'Lot-Nummer', 'HU_BestBeforeDate');

    WITH inout_context AS (
        -- Materialize all context data ONCE to avoid correlated subqueries
        SELECT io.m_inout_id,
               io.documentno,
               io.movementdate,
               io.m_warehouse_id,
               io.ad_org_id,
               io.edi_desadv_id,
               io.c_bpartner_location_id,
               io.dropship_location_id,
               io.poreference,
               io.docstatus,
               io.isactive,
               -- Resolve buyer c_bpartner_id once (for c_bpartner_product lookups)
               COALESCE(bpl_desadv_drop.c_bpartner_id,
                        bpl_desadv_buyer.c_bpartner_id,
                        bpl_ship_drop.c_bpartner_id,
                        bpl_ship_buyer.c_bpartner_id) AS buyer_bpartner_id,
               -- Pre-materialize GLNs to avoid duplicate joins in main query
               bpl_desadv_buyer.gln                   AS buyer_gln_desadv,
               bpl_desadv_buyer.gln_gcpLength         AS buyer_gcplength_desadv,
               bpl_ship_buyer.gln                     AS buyer_gln_ship,
               bpl_ship_buyer.gln_gcpLength           AS buyer_gcplength_ship,
               bpl_desadv_drop.gln                    AS drop_gln_desadv,
               bpl_desadv_drop.gln_gcpLength          AS drop_gcplength_desadv,
               bpl_ship_drop.gln                      AS drop_gln_ship,
               bpl_ship_drop.gln_gcpLength            AS drop_gcplength_ship,
               d.handover_location_id,
               d.documentno                           AS desadv_documentno,
               d.poreference                          AS desadv_poreference
        FROM m_inout io
                 LEFT JOIN edi_desadv d ON d.edi_desadv_id = io.edi_desadv_id
                 LEFT JOIN c_bpartner_location bpl_desadv_buyer
                           ON bpl_desadv_buyer.c_bpartner_location_id = d.c_bpartner_location_id
                 LEFT JOIN c_bpartner_location bpl_ship_buyer
                           ON bpl_ship_buyer.c_bpartner_location_id = io.c_bpartner_location_id
                 LEFT JOIN c_bpartner_location bpl_desadv_drop
                           ON bpl_desadv_drop.c_bpartner_location_id = d.dropship_location_id
                 LEFT JOIN c_bpartner_location bpl_ship_drop
                           ON bpl_ship_drop.c_bpartner_location_id = io.dropship_location_id
        WHERE io.m_inout_id = p_m_inout_id)
    SELECT JSONB_BUILD_OBJECT(
                   'shipmentId', ctx.m_inout_id,
                   'documentNo', ctx.documentno,
                   'movementDate', TO_CHAR(ctx.movementdate, 'YYYY-MM-DD"T"HH24:MI:SS'),
                   'timezone', '+01:00',
               -- Supplier GLN (org's BPartner location)
                   'supplierGLN', bpl_supplier.gln,
                   'supplierGcpLength', bpl_supplier.gln_gcpLength,
               -- Warehouse GLN: primary from warehouse BPartner, fallback to org
                   'warehouseGLN', COALESCE(bpl_wh.gln, bpl_supplier.gln),
                   'warehouseGcpLength',
                   CASE
                       WHEN bpl_wh.gln IS NOT NULL THEN bpl_wh.gln_gcpLength
                                                   ELSE bpl_supplier.gln_gcpLength
                   END,
               -- Warehouse value for SGLN extension
                   'warehouseValue', wh.value,
               -- Buyer GLN: DESADV → shipment fallback (pre-materialized in CTE)
                   'buyerGLN', COALESCE(ctx.buyer_gln_desadv, ctx.buyer_gln_ship),
                   'buyerGcpLength',
                   CASE
                       WHEN ctx.buyer_gln_desadv IS NOT NULL THEN ctx.buyer_gcplength_desadv
                                                             ELSE ctx.buyer_gcplength_ship
                   END,
               -- Handover (DESADV only)
                   'handoverGLN', bpl_handover.gln,
                   'handoverGcpLength', bpl_handover.gln_gcpLength,
               -- Dropship: DESADV → shipment fallback (pre-materialized in CTE)
                   'dropshipGLN', COALESCE(ctx.drop_gln_desadv, ctx.drop_gln_ship),
                   'dropshipGcpLength',
                   CASE
                       WHEN ctx.drop_gln_desadv IS NOT NULL THEN ctx.drop_gcplength_desadv
                                                            ELSE ctx.drop_gcplength_ship
                   END,
               -- DESADV reference (NULL if no DESADV)
                   'desadvReference', ctx.desadv_documentno,
               -- PO reference: DESADV → shipment
                   'poReference', COALESCE(ctx.desadv_poreference, ctx.poreference),
               -- Pallets
                   'pallets', COALESCE(pallets_data.pallets_json, '[]'::jsonb)
           )
    INTO v_result
    FROM inout_context ctx
             -- Warehouse
             JOIN m_warehouse wh ON wh.m_warehouse_id = ctx.m_warehouse_id
             LEFT JOIN c_bpartner_location bpl_wh
                       ON bpl_wh.c_bpartner_location_id = wh.c_bpartner_location_id
        -- Supplier (org)
             LEFT JOIN ad_orginfo org ON org.ad_org_id = ctx.ad_org_id
             LEFT JOIN c_bpartner_location bpl_supplier
                       ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
        -- Handover (DESADV only)
             LEFT JOIN c_bpartner_location bpl_handover
                       ON bpl_handover.c_bpartner_location_id = ctx.handover_location_id
        -- Pallets subquery
             LEFT JOIN LATERAL (
        WITH pallet_list AS (
            -- Materialize pallet list ONCE (avoid repeated execution)
            SELECT DISTINCT ha.m_lu_hu_id
            FROM m_inoutline iol
                     JOIN m_hu_assignment ha
                          ON ha.ad_table_id = 320
                              AND ha.record_id = iol.m_inoutline_id
            WHERE iol.m_inout_id = ctx.m_inout_id
              AND ha.m_lu_hu_id IS NOT NULL
              AND ha.isactive = 'Y'

            UNION

            SELECT DISTINCT qp.m_lu_hu_id
            FROM m_inoutline iol
                     JOIN m_shipmentschedule_qtypicked qp
                          ON qp.m_inoutline_id = iol.m_inoutline_id
            WHERE iol.m_inout_id = ctx.m_inout_id
              AND qp.m_lu_hu_id IS NOT NULL
              AND qp.isactive = 'Y'),
             all_crates_raw AS (
                 -- Collect ALL crates from ALL pallets FIRST (before GRAI numbering)
                 SELECT lu_hu.m_hu_id AS lu_hu_id,
                        crate_raw.*
                 FROM pallet_list
                          JOIN m_hu lu_hu ON lu_hu.m_hu_id = pallet_list.m_lu_hu_id
                          CROSS JOIN LATERAL (
                     SELECT grai_attr.value            AS grai_raw,
                            tu_hu.m_hu_id              AS tu_hu_id,
                            lot_attr.value             AS lot_number,
                            bbd_attr.valuedate         AS best_before_date,
                            1                          AS sort_ord,
                            (SELECT JSONB_AGG(
                                            JSONB_BUILD_OBJECT(
                                                    'cuGTIN', COALESCE(bp_prod.gtin, bp_prod.ean_cu, prod.gtin),
                                                    'tuGTIN', COALESCE(pi_prod.ean_tu, pi_prod.gtin),
                                                    'quantity', stor.qty,
                                                    'movementqty', stor.qty,
                                                    'uom', COALESCE(uom.x12de355, 'KGM'),
                                                    'productValue', prod.value,
                                                    'productNetWeight', prod.weight,
                                                    'productGrossWeight', prod.grossweight
                                            )
                                    )
                             FROM m_hu_item mi
                                      JOIN m_hu_item_storage stor ON stor.m_hu_item_id = mi.m_hu_item_id
                                      JOIN m_product prod ON prod.m_product_id = stor.m_product_id
                                      LEFT JOIN c_uom uom ON uom.c_uom_id = stor.c_uom_id
                                      LEFT JOIN m_hu_pi_item_product pi_prod
                                                ON pi_prod.m_hu_pi_item_product_id = tu_hu.m_hu_pi_item_product_id
                                      LEFT JOIN LATERAL (SELECT gtin, ean_cu
                                                         FROM c_bpartner_product bp_prod
                                                         WHERE bp_prod.m_product_id = prod.m_product_id
                                                           AND bp_prod.isactive = 'Y'
                                                           AND bp_prod.c_bpartner_id = ctx.buyer_bpartner_id
                                                         ORDER BY SeqNo DESC
                                                         LIMIT 1
                                 ) AS bp_prod ON TRUE
                             WHERE mi.m_hu_id = tu_hu.m_hu_id
                               AND mi.itemtype = 'MI') AS items_json
                     FROM m_hu_item parent_item
                              JOIN m_hu tu_hu ON tu_hu.m_hu_item_parent_id = parent_item.m_hu_item_id
                              LEFT JOIN m_hu_attribute grai_attr
                                        ON grai_attr.m_hu_id = tu_hu.m_hu_id
                                            AND grai_attr.m_attribute_id = v_grai_attribute_id
                              LEFT JOIN m_hu_attribute lot_attr
                                        ON lot_attr.m_hu_id = tu_hu.m_hu_id
                                            AND lot_attr.m_attribute_id = v_lot_attribute_id
                              LEFT JOIN m_hu_attribute bbd_attr
                                        ON bbd_attr.m_hu_id = tu_hu.m_hu_id
                                            AND bbd_attr.m_attribute_id = v_bbd_attribute_id
                     WHERE parent_item.m_hu_id = lu_hu.m_hu_id
                       AND parent_item.itemtype = 'HU'

                     UNION ALL

                     -- CASE B: Aggregated TUs
                     SELECT NULLIF(grai_expanded.grai_raw, '')                     AS grai_raw,
                            ha_item.m_hu_item_id                                   AS tu_hu_id,
                            lot_attr.value                                         AS lot_number,
                            bbd_attr.valuedate                                     AS best_before_date,
                            grai_expanded.ord                                      AS sort_ord,
                            (SELECT JSONB_AGG(
                                            JSONB_BUILD_OBJECT(
                                                    'cuGTIN', COALESCE(bp_prod.gtin, bp_prod.ean_cu, prod.gtin),
                                                    'tuGTIN', COALESCE(pi_prod.ean_tu, pi_prod.gtin),
                                                    'quantity',
                                                    CASE
                                                        WHEN COALESCE(ha_item.qty, 1) > 0
                                                            THEN stor.qty / ha_item.qty
                                                            ELSE stor.qty
                                                    END,
                                                    'movementqty',
                                                    CASE
                                                        WHEN COALESCE(ha_item.qty, 1) > 0
                                                            THEN stor.qty / ha_item.qty
                                                            ELSE stor.qty
                                                    END,
                                                    'uom', COALESCE(uom.x12de355, 'KGM'),
                                                    'productValue', prod.value,
                                                    'productNetWeight', prod.weight,
                                                    'productGrossWeight', prod.grossweight
                                            )
                                    )
                             FROM m_hu vtu
                                      JOIN m_hu_item mi ON mi.m_hu_id = vtu.m_hu_id AND mi.itemtype = 'MI'
                                      JOIN m_hu_item_storage stor ON stor.m_hu_item_id = mi.m_hu_item_id
                                      JOIN m_product prod ON prod.m_product_id = stor.m_product_id
                                      LEFT JOIN c_uom uom ON uom.c_uom_id = stor.c_uom_id
                                      LEFT JOIN m_hu_pi_item_product pi_prod
                                                ON pi_prod.m_hu_pi_item_product_id = vtu.m_hu_pi_item_product_id
                                      LEFT JOIN LATERAL (SELECT gtin, ean_cu
                                                         FROM c_bpartner_product bp_prod
                                                         WHERE bp_prod.m_product_id = prod.m_product_id
                                                           AND bp_prod.isactive = 'Y'
                                                           AND bp_prod.c_bpartner_id = ctx.buyer_bpartner_id
                                                         ORDER BY SeqNo DESC
                                                         LIMIT 1
                                 ) AS bp_prod ON TRUE
                             WHERE vtu.m_hu_item_parent_id = ha_item.m_hu_item_id) AS items_json
                     FROM m_hu_item ha_item
                              LEFT JOIN m_hu ha_vtu ON ha_vtu.m_hu_item_parent_id = ha_item.m_hu_item_id
                              LEFT JOIN m_hu_attribute grai_attr
                                        ON grai_attr.m_hu_id = COALESCE(ha_vtu.m_hu_id, ha_item.m_hu_id)
                                            AND grai_attr.m_attribute_id = v_grai_attribute_id
                              CROSS JOIN LATERAL UNNEST(
                             COALESCE(
                                     NULLIF(STRING_TO_ARRAY(TRIM(grai_attr.value), ','), ARRAY [NULL]::text[]),
                                     ARRAY(SELECT ''::text FROM GENERATE_SERIES(1, GREATEST(ha_item.qty::int, 1)))
                             )
                                                 ) WITH ORDINALITY AS grai_expanded(grai_raw, ord)
                              LEFT JOIN m_hu_attribute lot_attr
                                        ON lot_attr.m_hu_id = COALESCE(ha_vtu.m_hu_id, ha_item.m_hu_id)
                                            AND lot_attr.m_attribute_id = v_lot_attribute_id
                              LEFT JOIN m_hu_attribute bbd_attr
                                        ON bbd_attr.m_hu_id = COALESCE(ha_vtu.m_hu_id, ha_item.m_hu_id)
                                            AND bbd_attr.m_attribute_id = v_bbd_attribute_id
                     WHERE ha_item.m_hu_id = lu_hu.m_hu_id
                       AND ha_item.itemtype = 'HA'
                       AND ha_item.qty IS NOT NULL
                       AND ha_item.qty > 0
                     ) crate_raw),
             all_crates_with_grai AS (
                 -- Apply GLOBAL GRAI numbering across ALL crates
                 SELECT lu_hu_id,
                        tu_hu_id,
                        COALESCE(
                                grai_raw,
                                '7613204.00307.' || LPAD(COALESCE(ctx.poreference, '0'), 10, '0')
                                    || LPAD(ROW_NUMBER() OVER (ORDER BY lu_hu_id, tu_hu_id, sort_ord)::text, 2, '0')
                        ) AS grai,
                        lot_number,
                        best_before_date,
                        items_json
                 FROM all_crates_raw)
        SELECT JSONB_AGG(
                       JSONB_BUILD_OBJECT(
                               'sscc', pallet_agg.sscc,
                               'huId', pallet_agg.lu_hu_id,
                               'crates', pallet_agg.crates_json
                       ) ORDER BY pallet_agg.lu_hu_id
               ) AS pallets_json
        FROM (SELECT c.lu_hu_id,
                     sscc_attr.value AS sscc,
                     JSONB_AGG(
                             JSONB_BUILD_OBJECT(
                                     'grai', c.grai,
                                     'lotNumber', c.lot_number,
                                     'bestBeforeDate', c.best_before_date,
                                     'tuHuId', c.tu_hu_id,
                                     'items', COALESCE(c.items_json, '[]'::jsonb)
                             ) ORDER BY c.tu_hu_id
                     )               AS crates_json
              FROM all_crates_with_grai c
                       LEFT JOIN m_hu_attribute sscc_attr
                                 ON sscc_attr.m_hu_id = c.lu_hu_id
                                     AND sscc_attr.m_attribute_id = v_sscc_attribute_id
              GROUP BY c.lu_hu_id, sscc_attr.value
              ORDER BY c.lu_hu_id) pallet_agg
        ) pallets_data ON TRUE
    WHERE ctx.isactive = 'Y'
      AND ctx.docstatus IN ('CO', 'CL');

    RETURN COALESCE(v_result, '{}'::jsonb);
END;
$$
    LANGUAGE plpgsql STABLE
;
