-- Migration: 5794490
-- EPCIS event JSON: rewrite to HU-based, DESADV-optional
-- me03#28672: Pallet/crate/item data now derived from HU hierarchy
-- instead of DESADV pack items. DESADV LEFT JOINed for optional biz references.

CREATE OR REPLACE FUNCTION "de.metas.edi".get_epcis_events_json_fn(p_m_inout_id NUMERIC)
    RETURNS JSONB
AS
$$
DECLARE
    v_result             JSONB;
    v_grai_attribute_id  NUMERIC;
    v_sscc_attribute_id  NUMERIC;
    v_lot_attribute_id   NUMERIC;
    v_bbd_attribute_id   NUMERIC;
BEGIN
    -- Cache attribute IDs
    SELECT m_attribute_id INTO v_grai_attribute_id FROM m_attribute WHERE value = 'GRAI' LIMIT 1;
    SELECT m_attribute_id INTO v_sscc_attribute_id FROM m_attribute WHERE value = 'SSCC18' LIMIT 1;
    SELECT m_attribute_id INTO v_lot_attribute_id FROM m_attribute WHERE value = 'Lot-Nummer' LIMIT 1;
    SELECT m_attribute_id INTO v_bbd_attribute_id FROM m_attribute WHERE value = 'HU_BestBeforeDate' LIMIT 1;

    SELECT JSONB_BUILD_OBJECT(
                   'shipmentId', io.m_inout_id,
                   'documentNo', io.documentno,
                   'movementDate', TO_CHAR(io.movementdate, 'YYYY-MM-DD"T"HH24:MI:SS'),
                   'timezone', '+01:00',
                   -- Supplier GLN (org's BPartner location)
                   'supplierGLN', bpl_supplier.gln,
                   -- Warehouse GLN: primary from warehouse BPartner, fallback to org
                   'warehouseGLN', COALESCE(bpl_wh.gln, bpl_supplier.gln),
                   -- Warehouse value for SGLN extension
                   'warehouseValue', wh.value,
                   -- Buyer GLN: DESADV → shipment fallback
                   'buyerGLN', COALESCE(bpl_desadv_buyer.gln, bpl_ship_buyer.gln),
                   -- Handover (DESADV only)
                   'handoverGLN', bpl_handover.gln,
                   -- Dropship: DESADV → shipment fallback
                   'dropshipGLN', COALESCE(bpl_desadv_drop.gln, bpl_ship_drop.gln),
                   -- DESADV reference (NULL if no DESADV)
                   'desadvReference', d.documentno,
                   -- PO reference: DESADV → shipment
                   'poReference', COALESCE(d.poreference, io.poreference),
                   -- Pallets
                   'pallets', COALESCE(pallets_data.pallets_json, '[]'::jsonb)
           )
    INTO v_result
    FROM m_inout io
             -- DESADV: LEFT JOIN (optional)
             LEFT JOIN edi_desadv d ON d.edi_desadv_id = io.edi_desadv_id
             -- Warehouse
             JOIN m_warehouse wh ON wh.m_warehouse_id = io.m_warehouse_id
             LEFT JOIN c_bpartner_location bpl_wh
                       ON bpl_wh.c_bpartner_location_id = wh.c_bpartner_location_id
             -- Supplier (org)
             LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
             LEFT JOIN c_bpartner_location bpl_supplier
                       ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
             -- Buyer: DESADV path
             LEFT JOIN c_bpartner_location bpl_desadv_buyer
                       ON bpl_desadv_buyer.c_bpartner_location_id = d.c_bpartner_location_id
             -- Buyer: shipment path (fallback)
             LEFT JOIN c_bpartner_location bpl_ship_buyer
                       ON bpl_ship_buyer.c_bpartner_location_id = io.c_bpartner_location_id
             -- Handover (DESADV only)
             LEFT JOIN c_bpartner_location bpl_handover
                       ON bpl_handover.c_bpartner_location_id = d.handover_location_id
             -- Dropship: DESADV path
             LEFT JOIN c_bpartner_location bpl_desadv_drop
                       ON bpl_desadv_drop.c_bpartner_location_id = d.dropship_location_id
             -- Dropship: shipment path (fallback)
             LEFT JOIN c_bpartner_location bpl_ship_drop
                       ON bpl_ship_drop.c_bpartner_location_id = io.dropship_location_id
             -- Pallets subquery
             LEFT JOIN LATERAL (
        SELECT JSONB_AGG(
                       JSONB_BUILD_OBJECT(
                               'sscc', sscc_attr.value,
                               'huId', lu_hu.m_hu_id,
                               'crates', COALESCE(crates_data.crates_json, '[]'::jsonb)
                       )
               ) AS pallets_json
        FROM (
                 -- Discover pallets via M_HU_Assignment (reliable for both picked and auto-generated shipments)
                 SELECT DISTINCT ha.m_lu_hu_id
                 FROM m_inoutline iol
                          JOIN m_hu_assignment ha
                               ON ha.ad_table_id = 320 -- M_InOutLine
                                   AND ha.record_id = iol.m_inoutline_id
                 WHERE iol.m_inout_id = io.m_inout_id
                   AND ha.m_lu_hu_id IS NOT NULL
                   AND ha.isactive = 'Y'

                 UNION

                 -- Fallback: QtyPicked path (for picking-terminal workflows)
                 SELECT DISTINCT qp.m_lu_hu_id
                 FROM m_inoutline iol
                          JOIN m_shipmentschedule_qtypicked qp
                               ON qp.m_inoutline_id = iol.m_inoutline_id
                 WHERE iol.m_inout_id = io.m_inout_id
                   AND qp.m_lu_hu_id IS NOT NULL
                   AND qp.isactive = 'Y'
             ) lu_ids
                 JOIN m_hu lu_hu ON lu_hu.m_hu_id = lu_ids.m_lu_hu_id
            -- SSCC18 on LU
                 LEFT JOIN m_hu_attribute sscc_attr
                           ON sscc_attr.m_hu_id = lu_hu.m_hu_id
                               AND sscc_attr.m_attribute_id = v_sscc_attribute_id
            -- Crates subquery
                 LEFT JOIN LATERAL (
            SELECT JSONB_AGG(
                           JSONB_BUILD_OBJECT(
                                   'grai', crate.grai,
                                   'lotNumber', crate.lot_number,
                                   'bestBeforeDate', crate.best_before_date,
                                   'tuHuId', crate.tu_hu_id,
                                   'items', COALESCE(crate.items_json, '[]'::jsonb)
                           )
                   ) AS crates_json
            FROM (
                     -- CASE A: Individual TUs (itemtype='HU')
                     SELECT COALESCE(
                                grai_attr.value,
                                -- Dummy-GRAI fallback for individual TU
                                '7613204.00307.' || LPAD(COALESCE(io.poreference, '0'), 10, '0')
                                    || LPAD(ROW_NUMBER() OVER (PARTITION BY lu_hu.m_hu_id ORDER BY tu_hu.m_hu_id)::text, 2, '0')
                            )                                                  AS grai,
                            lot_attr.value                                     AS lot_number,
                            bbd_attr.value                                     AS best_before_date,
                            tu_hu.m_hu_id                                      AS tu_hu_id,
                            (SELECT JSONB_AGG(
                                            JSONB_BUILD_OBJECT(
                                                    'cuGTIN', prod.gtin,
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
                                 -- TU GTIN from PI Item Product
                                      LEFT JOIN m_hu_pi_item_product pi_prod
                                                ON pi_prod.m_hu_pi_item_product_id = tu_hu.m_hu_pi_item_product_id
                             WHERE mi.m_hu_id = tu_hu.m_hu_id
                               AND mi.itemtype = 'MI'
                            )                                                  AS items_json
                     FROM m_hu_item parent_item
                              JOIN m_hu tu_hu ON tu_hu.m_hu_item_parent_id = parent_item.m_hu_item_id
                         -- GRAI on TU
                              LEFT JOIN m_hu_attribute grai_attr
                                        ON grai_attr.m_hu_id = tu_hu.m_hu_id
                                            AND grai_attr.m_attribute_id = v_grai_attribute_id
                         -- LOT on TU
                              LEFT JOIN m_hu_attribute lot_attr
                                        ON lot_attr.m_hu_id = tu_hu.m_hu_id
                                            AND lot_attr.m_attribute_id = v_lot_attribute_id
                         -- BestBeforeDate on TU
                              LEFT JOIN m_hu_attribute bbd_attr
                                        ON bbd_attr.m_hu_id = tu_hu.m_hu_id
                                            AND bbd_attr.m_attribute_id = v_bbd_attribute_id
                     WHERE parent_item.m_hu_id = lu_hu.m_hu_id
                       AND parent_item.itemtype = 'HU'

                     UNION ALL

                     -- CASE B: Aggregated TUs (itemtype='HA') — expand via GRAI list
                     SELECT grai_expanded.grai,
                            lot_attr.value                                     AS lot_number,
                            bbd_attr.value                                     AS best_before_date,
                            ha_item.m_hu_item_id                               AS tu_hu_id,
                            (SELECT JSONB_AGG(
                                            JSONB_BUILD_OBJECT(
                                                    'cuGTIN', prod.gtin,
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
                             WHERE vtu.m_hu_item_parent_id = ha_item.m_hu_item_id
                            )                                                  AS items_json
                     FROM m_hu_item ha_item
                              -- Virtual TU under HA (for GRAI attribute lookup)
                              LEFT JOIN m_hu ha_vtu ON ha_vtu.m_hu_item_parent_id = ha_item.m_hu_item_id
                              -- GRAI attribute: check virtual TU first, then LU
                              LEFT JOIN m_hu_attribute grai_attr
                                        ON grai_attr.m_hu_id = COALESCE(ha_vtu.m_hu_id, ha_item.m_hu_id)
                                            AND grai_attr.m_attribute_id = v_grai_attribute_id
                         -- Expand comma-separated GRAIs (or generate dummies)
                              CROSS JOIN LATERAL unnest(
                             COALESCE(
                                     NULLIF(string_to_array(TRIM(grai_attr.value), ','), ARRAY [NULL]::text[]),
                                     -- Dummy-GRAI fallback
                                     ARRAY(SELECT '7613204.00307.' ||
                                                  LPAD(COALESCE(io.poreference, '0'), 10, '0') ||
                                                  LPAD(gs::text, 2, '0')
                                           FROM generate_series(1, GREATEST(ha_item.qty::int, 1)) gs)
                             )
                             ) WITH ORDINALITY AS grai_expanded(grai, ord)
                         -- LOT on virtual TU (fallback: LU)
                              LEFT JOIN m_hu_attribute lot_attr
                                        ON lot_attr.m_hu_id = COALESCE(ha_vtu.m_hu_id, ha_item.m_hu_id)
                                            AND lot_attr.m_attribute_id = v_lot_attribute_id
                         -- BestBeforeDate on virtual TU (fallback: LU)
                              LEFT JOIN m_hu_attribute bbd_attr
                                        ON bbd_attr.m_hu_id = COALESCE(ha_vtu.m_hu_id, ha_item.m_hu_id)
                                            AND bbd_attr.m_attribute_id = v_bbd_attribute_id
                     WHERE ha_item.m_hu_id = lu_hu.m_hu_id
                       AND ha_item.itemtype = 'HA'
                 ) crate
            ) crates_data ON TRUE
        ) pallets_data ON TRUE
    WHERE io.m_inout_id = p_m_inout_id
      AND io.isactive = 'Y'
      AND io.docstatus IN ('CO', 'CL');

    RETURN COALESCE(v_result, '{}'::jsonb);
END;
$$
    LANGUAGE plpgsql STABLE
;
