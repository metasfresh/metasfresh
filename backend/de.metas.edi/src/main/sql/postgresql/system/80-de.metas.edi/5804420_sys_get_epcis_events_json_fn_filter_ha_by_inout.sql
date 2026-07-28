-- Migration: get_epcis_events_json_fn — restrict TUs (individual + HA aggregates) to the current M_InOut
-- Issue: https://github.com/metasfresh/me03/issues/29231
--
-- Context:
--   When two M_InOuts share the same physical LU pallet (real-world: picker consolidates
--   two orders onto one pallet), the export of shipment-1 used to also include the TUs
--   that belong to the sibling shipment. Original failure: shipment-1's JSON contained
--   35 crates instead of 15 (LAF1010-3 testcase, Migros / Spavetti EPCIS).
--
--   Root cause: the individual_tu_ids and ha_items_with_vtu CTEs walked every TU /
--   HA m_hu_item under the LU with no link back to the M_InOut. pallet_list already
--   filters LUs to the current shipment via m_hu_assignment, but the TU-level walkers
--   did not.
--
--   Fix: both CTEs now add an EXISTS gate against m_hu_assignment.
--   - CASE A (individual_tu_ids): match assignment.m_tu_hu_id or .vhu_id = tu_hu.m_hu_id
--   - CASE B (ha_items_with_vtu): match assignment.vhu_id = ha_vtu.m_hu_id
--   For HA, ha_item.qty remains the crate count source (m_hu_assignment.qty is 0 in
--   the observed production data — the assignment is presence-only).

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
--
-- Changes in this function version:
-- desadvReferences[] and poReferences[] are jsonb arrays built via the EDI_Desadv_M_InOut
-- junction (one element per linked DESADV). The scalar desadvReference / poReference fields
-- are kept for backward compatibility. The single-FK edi_desadv join is retained for
-- GLN / location / handover lookups.
--
-- Performance design:
--   - Flat CTEs only (no nested LATERALs)
--   - m_hu ha_vtu join computed once in ha_items_with_vtu, reused for attrs + items
--   - m_hu_attribute scanned once via hu_attrs pivot (vs 6 separate joins previously)
--   - c_bpartner_product scanned once via bp_prod_lookup (vs N LATERAL calls)
--   - Scalar variables for buyer_bpartner_id and poreference (vs scalar subqueries per row)

CREATE OR REPLACE FUNCTION "de.metas.edi".get_epcis_events_json_fn(p_m_inout_id NUMERIC)
    RETURNS JSONB
AS
$$
DECLARE
    v_result               JSONB;
    v_grai_attribute_id    NUMERIC;
    v_sscc_attribute_id    NUMERIC;
    v_lot_attribute_id     NUMERIC;
    v_bbd_attribute_id     NUMERIC;
    v_dummy_grai_prefix    TEXT;
    v_m_inoutline_table_id NUMERIC;

BEGIN
    -- Cache attribute IDs in one scan
    SELECT MAX(CASE WHEN value = 'GRAI' THEN m_attribute_id END),
           MAX(CASE WHEN value = 'SSCC18' THEN m_attribute_id END),
           MAX(CASE WHEN value = 'Lot-Nummer' THEN m_attribute_id END),
           MAX(CASE WHEN value = 'HU_BestBeforeDate' THEN m_attribute_id END)
    INTO v_grai_attribute_id, v_sscc_attribute_id, v_lot_attribute_id, v_bbd_attribute_id
    FROM m_attribute
    WHERE value IN ('GRAI', 'SSCC18', 'Lot-Nummer', 'HU_BestBeforeDate');

    -- de.metas.edi.epcis.dummyGRAI.Prefix — GCP.assetType. portion before the 12-char serial, e.g. '7613264.00307.'
    v_dummy_grai_prefix := get_sysconfig_value('de.metas.edi.epcis.dummyGRAI.Prefix', '0000000.11111.');

    -- Cache the AD_Table_ID for M_InOutLine — used 4× below as the m_hu_assignment.ad_table_id filter
    v_m_inoutline_table_id := get_table_id('M_InOutLine');

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
               COALESCE(bpl_desadv_drop.c_bpartner_id,
                        bpl_desadv_buyer.c_bpartner_id,
                        bpl_ship_drop.c_bpartner_id,
                        bpl_ship_buyer.c_bpartner_id) AS buyer_bpartner_id,
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
               d.poreference                          AS desadv_poreference,
               -- arrays from junction (all DESADVs linked to this shipment)
               desadv_agg.desadv_documentnos,
               desadv_agg.desadv_poreferences
        FROM m_inout io
                 LEFT JOIN edi_desadv d ON d.edi_desadv_id = io.edi_desadv_id
                 -- aggregate all DESADV references via N:M junction
                 LEFT JOIN LATERAL (
                     SELECT jsonb_agg(da.documentno ORDER BY da.documentno)   AS desadv_documentnos,
                            jsonb_agg(da.poreference ORDER BY da.documentno)  AS desadv_poreferences
                     FROM edi_desadv_m_inout link
                     JOIN edi_desadv da ON da.edi_desadv_id = link.edi_desadv_id
                     WHERE link.m_inout_id = io.m_inout_id
                       AND link.isactive = 'Y'
                 ) desadv_agg ON true
                 LEFT JOIN c_bpartner_location bpl_desadv_buyer
                           ON bpl_desadv_buyer.c_bpartner_location_id = d.c_bpartner_location_id
                 LEFT JOIN c_bpartner_location bpl_ship_buyer
                           ON bpl_ship_buyer.c_bpartner_location_id = io.c_bpartner_location_id
                 LEFT JOIN c_bpartner_location bpl_desadv_drop
                           ON bpl_desadv_drop.c_bpartner_location_id = d.dropship_location_id
                 LEFT JOIN c_bpartner_location bpl_ship_drop
                           ON bpl_ship_drop.c_bpartner_location_id = io.dropship_location_id
        WHERE io.m_inout_id = p_m_inout_id),

         pallet_list AS MATERIALIZED (
             -- All LU HU IDs for this shipment, enriched with the left-zero-padded POReference of
             -- their source order.  In an n:m consolidated shipment each LU belongs to exactly one
             -- source order; we pick one POReference per LU (MIN) and LPAD it to 10 digits for the
             -- GRAI Serial middle segment per Migros spec:
             --   urn:epc:id:grai:<GCP>.<assetType>.<10-digit Bestellnummer left-padded><2-digit counter>
             -- Production POReferences are numeric (e.g. '1234567890'); LPAD pads shorter values
             -- with leading zeros and leaves 10-digit values unchanged.
             SELECT lu_hu_id,
                    LPAD(COALESCE(MIN(poreference), '0'), 10, '0') AS lu_poreference_padded
             FROM (
                 -- Primary: M_HU_Assignment → InOutLine → OrderLine → Order path
                 SELECT ha.m_lu_hu_id AS lu_hu_id,
                        ord.poreference
                 FROM inout_context ctx
                          JOIN m_inoutline iol ON iol.m_inout_id = ctx.m_inout_id
                          JOIN m_hu_assignment ha
                               ON ha.ad_table_id = v_m_inoutline_table_id -- M_InOutLine
                                   AND ha.record_id = iol.m_inoutline_id
                          LEFT JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id
                          LEFT JOIN c_order ord ON ord.c_order_id = ol.c_order_id
                 WHERE ha.m_lu_hu_id IS NOT NULL
                   AND ha.isactive = 'Y'

                 UNION ALL

                 -- Fallback: M_ShipmentSchedule_QtyPicked path (no per-LU order link available)
                 SELECT qp.m_lu_hu_id AS lu_hu_id,
                        NULL::text AS poreference
                 FROM inout_context ctx
                          JOIN m_inoutline iol ON iol.m_inout_id = ctx.m_inout_id
                          JOIN m_shipmentschedule_qtypicked qp ON qp.m_inoutline_id = iol.m_inoutline_id
                 WHERE qp.m_lu_hu_id IS NOT NULL
                   AND qp.isactive = 'Y'
                   AND NOT EXISTS (
                       SELECT 1 FROM m_hu_assignment ha2
                       WHERE ha2.m_lu_hu_id = qp.m_lu_hu_id
                         AND ha2.ad_table_id = v_m_inoutline_table_id
                         AND ha2.isactive = 'Y'
                   )
             ) lu_with_poreference
             GROUP BY lu_hu_id),

         individual_tu_ids AS MATERIALIZED (
             -- CASE A: individual TU HU IDs across all pallets — no attribute joins yet.
             --
             -- Restricted to TUs allocated to THIS M_InOut via m_hu_assignment. The
             -- assignment for a non-aggregated TU normally carries m_tu_hu_id (and/or
             -- vhu_id) referencing the TU's m_hu_id. Without this filter, when two
             -- shipments share an LU, the export of shipment-1 would also include the
             -- individual TUs that belong to the sibling shipment (me03#29231).
             SELECT lu_hu.m_hu_id                 AS lu_hu_id,
                    pl.lu_poreference_padded,
                    tu_hu.m_hu_id                 AS tu_hu_id,
                    tu_hu.m_hu_pi_item_product_id AS tu_pi_item_product_id
             FROM pallet_list pl
                      JOIN m_hu lu_hu ON lu_hu.m_hu_id = pl.lu_hu_id
                      JOIN m_hu_item parent_item
                           ON parent_item.m_hu_id = lu_hu.m_hu_id
                               AND parent_item.itemtype = 'HU'
                      JOIN m_hu tu_hu ON tu_hu.m_hu_item_parent_id = parent_item.m_hu_item_id
             WHERE EXISTS (
                 SELECT 1
                 FROM m_hu_assignment ha
                          JOIN m_inoutline iol ON iol.m_inoutline_id = ha.record_id
                 WHERE ha.ad_table_id = v_m_inoutline_table_id
                   AND iol.m_inout_id = (SELECT m_inout_id FROM inout_context)
                   AND ha.isactive = 'Y'
                   AND (ha.m_tu_hu_id = tu_hu.m_hu_id OR ha.vhu_id = tu_hu.m_hu_id)
             )),

         ha_items_with_vtu AS MATERIALIZED (
             -- CASE B: HA items with their virtual TU resolved — computed ONCE, reused for
             --         both attribute lookup and storage item joins (avoids double m_hu scan).
             --
             -- Restricted to HA aggregates allocated to THIS M_InOut via m_hu_assignment.
             -- Each HA aggregate is allocated to exactly one inoutline (one shipment), and
             -- ha_item.qty carries the crate count. Without this filter, when two shipments
             -- share an LU, the export of shipment-1 would also include the HA aggregates
             -- that belong to the sibling shipment (LAF1010-3 scenario).
             SELECT lu_hu.m_hu_id                           AS lu_hu_id,
                    pl.lu_poreference_padded,
                    ha_item.m_hu_item_id                    AS tu_hu_id,
                    ha_item.qty,
                    ha_vtu.m_hu_id                          AS vtu_hu_id,
                    ha_vtu.m_hu_pi_item_product_id          AS vtu_pi_item_product_id,
                    -- HU ID to use for attribute lookups: virtual TU first, fall back to LU
                    COALESCE(ha_vtu.m_hu_id, lu_hu.m_hu_id) AS attr_hu_id
             FROM pallet_list pl
                      JOIN m_hu lu_hu ON lu_hu.m_hu_id = pl.lu_hu_id
                      JOIN m_hu_item ha_item
                           ON ha_item.m_hu_id = lu_hu.m_hu_id
                               AND ha_item.itemtype = 'HA'
                               AND ha_item.qty IS NOT NULL
                               AND ha_item.qty > 0
                      JOIN m_hu ha_vtu ON ha_vtu.m_hu_item_parent_id = ha_item.m_hu_item_id
             WHERE EXISTS (
                 SELECT 1
                 FROM m_hu_assignment ha
                          JOIN m_inoutline iol ON iol.m_inoutline_id = ha.record_id
                 WHERE ha.ad_table_id = v_m_inoutline_table_id
                   AND iol.m_inout_id = (SELECT m_inout_id FROM inout_context)
                   AND ha.isactive = 'Y'
                   AND ha.vhu_id = ha_vtu.m_hu_id
             )),

         hu_attrs AS MATERIALIZED (
             -- Single pivot scan of m_hu_attribute for ALL relevant HU IDs and the 3 needed
             -- attribute IDs — replaces 6 separate LEFT JOIN m_hu_attribute (3 per TU type)
             SELECT ha.m_hu_id,
                    MAX(CASE WHEN ha.m_attribute_id = v_grai_attribute_id THEN ha.value END)    AS grai_value,
                    MAX(CASE WHEN ha.m_attribute_id = v_lot_attribute_id THEN ha.value END)     AS lot_number,
                    MAX(CASE WHEN ha.m_attribute_id = v_bbd_attribute_id THEN ha.valuedate END) AS best_before_date
             FROM m_hu_attribute ha
             WHERE ha.m_hu_id IN (SELECT tu_hu_id
                                  FROM individual_tu_ids
                                  UNION
                                  SELECT attr_hu_id
                                  FROM ha_items_with_vtu)
               AND ha.m_attribute_id IN (v_grai_attribute_id, v_lot_attribute_id, v_bbd_attribute_id)
             GROUP BY ha.m_hu_id),

         bp_prod_lookup AS MATERIALIZED (
             -- Buyer-specific product GTINs: ONE scan replaces N LATERAL calls in items CTEs
             SELECT DISTINCT ON (bp.m_product_id) bp.m_product_id,
                                                  bp.gtin,
                                                  bp.ean_cu
             FROM c_bpartner_product bp
             WHERE bp.c_bpartner_id = (SELECT buyer_bpartner_id FROM inout_context)
               AND bp.isactive = 'Y'
             ORDER BY bp.m_product_id, bp.seqno DESC),

         individual_tus AS MATERIALIZED (
             -- CASE A: individual TUs with attributes joined from hu_attrs
             SELECT it.lu_hu_id,
                    it.lu_poreference_padded,
                    it.tu_hu_id,
                    it.tu_pi_item_product_id,
                    NULLIF(TRIM(ha.grai_value), '') AS grai_raw,
                    ha.lot_number,
                    ha.best_before_date
             FROM individual_tu_ids it
                      LEFT JOIN hu_attrs ha ON ha.m_hu_id = it.tu_hu_id),

         individual_tu_items AS (
             -- Items for ALL individual TUs in ONE batch query
             SELECT it.tu_hu_id,
                    JSONB_AGG(
                            JSONB_BUILD_OBJECT(
                                    'cuGTIN', COALESCE(bp_prod.gtin, bp_prod.ean_cu, prod.gtin),
                                    'tuGTIN', COALESCE(pi_prod.gtin, pi_prod.ean_tu),
                                    'quantity', stor.qty,
                                    'movementqty', stor.qty,
                                    'uom', COALESCE(uom.x12de355, 'KGM'),
                                    'productValue', prod.value,
                                    'productNetWeight', prod.weight,
                                    'productGrossWeight', prod.grossweight
                            )
                    ) AS items_json
             FROM individual_tus it
                      JOIN m_hu_item mi ON mi.m_hu_id = it.tu_hu_id AND mi.itemtype = 'MI'
                      JOIN m_hu_item_storage stor ON stor.m_hu_item_id = mi.m_hu_item_id
                      JOIN m_product prod ON prod.m_product_id = stor.m_product_id
                      LEFT JOIN c_uom uom ON uom.c_uom_id = stor.c_uom_id
                      LEFT JOIN m_hu_pi_item_product pi_prod
                                ON pi_prod.m_hu_pi_item_product_id = it.tu_pi_item_product_id
                      LEFT JOIN bp_prod_lookup bp_prod ON bp_prod.m_product_id = prod.m_product_id
             GROUP BY it.tu_hu_id),

         aggregated_tu_base AS MATERIALIZED (
             -- CASE B: aggregated TU base rows with attributes — vtu already resolved in ha_items_with_vtu
             SELECT hwv.lu_hu_id,
                    hwv.lu_poreference_padded,
                    hwv.tu_hu_id,
                    hwv.qty,
                    hwv.vtu_hu_id,
                    hwv.vtu_pi_item_product_id,
                    COALESCE(
                            NULLIF(STRING_TO_ARRAY(TRIM(ha.grai_value), ','), ARRAY [NULL]::text[]),
                            ARRAY []::text[]
                    ) AS grai_arr,
                    ha.lot_number,
                    ha.best_before_date
             FROM ha_items_with_vtu hwv
                      LEFT JOIN hu_attrs ha ON ha.m_hu_id = hwv.attr_hu_id),

         aggregated_tu_items AS (
             -- Items for ALL aggregated TUs in ONE batch query — no m_hu join needed (vtu_hu_id from atb)
             SELECT atb.tu_hu_id,
                    JSONB_AGG(
                            JSONB_BUILD_OBJECT(
                                    'cuGTIN', COALESCE(bp_prod.gtin, bp_prod.ean_cu, prod.gtin),
                                    'tuGTIN', COALESCE(pi_prod.gtin, pi_prod.ean_tu),
                                    'quantity',
                                    CASE
                                        WHEN COALESCE(atb.qty, 1) > 0 THEN stor.qty / atb.qty
                                                                      ELSE stor.qty
                                    END,
                                    'movementqty',
                                    CASE
                                        WHEN COALESCE(atb.qty, 1) > 0 THEN stor.qty / atb.qty
                                                                      ELSE stor.qty
                                    END,
                                    'uom', COALESCE(uom.x12de355, 'KGM'),
                                    'productValue', prod.value,
                                    'productNetWeight', prod.weight,
                                    'productGrossWeight', prod.grossweight
                            )
                    ) AS items_json
             FROM aggregated_tu_base atb
                      JOIN m_hu_item mi ON mi.m_hu_id = atb.vtu_hu_id AND mi.itemtype = 'MI'
                      JOIN m_hu_item_storage stor ON stor.m_hu_item_id = mi.m_hu_item_id
                      JOIN m_product prod ON prod.m_product_id = stor.m_product_id
                      LEFT JOIN c_uom uom ON uom.c_uom_id = stor.c_uom_id
                      LEFT JOIN m_hu_pi_item_product pi_prod
                                ON pi_prod.m_hu_pi_item_product_id = atb.vtu_pi_item_product_id
                      LEFT JOIN bp_prod_lookup bp_prod ON bp_prod.m_product_id = prod.m_product_id
             GROUP BY atb.tu_hu_id, atb.qty),

         all_crates_raw AS (
             -- Individual TUs (CASE A)
             SELECT it.lu_hu_id,
                    it.lu_poreference_padded,
                    it.tu_hu_id,
                    it.grai_raw,
                    it.lot_number,
                    it.best_before_date,
                    1 AS sort_ord,
                    iti.items_json
             FROM individual_tus it
                      LEFT JOIN individual_tu_items iti ON iti.tu_hu_id = it.tu_hu_id

             UNION ALL

             -- Aggregated TUs (CASE B): expand grai_arr to qty rows, reuse pre-computed items
             SELECT atb.lu_hu_id,
                    atb.lu_poreference_padded,
                    atb.tu_hu_id,
                    NULLIF(
                            CASE
                                WHEN gs <= COALESCE(ARRAY_LENGTH(atb.grai_arr, 1), 0)
                                    THEN TRIM(atb.grai_arr[gs])
                                    ELSE ''
                            END,
                            ''
                    )  AS grai_raw,
                    atb.lot_number,
                    atb.best_before_date,
                    gs AS sort_ord,
                    ati.items_json
             FROM aggregated_tu_base atb
                      LEFT JOIN aggregated_tu_items ati ON ati.tu_hu_id = atb.tu_hu_id
                      CROSS JOIN GENERATE_SERIES(1, GREATEST(atb.qty::int, 1)) AS gs),

         all_crates_with_grai AS (
             -- Dummy GRAI: middle segment = source-order POReference (LPAD to 10 digits, per-LU).
             -- Migros format: urn:epc:id:grai:<GCP>.<assetType>.<10-digit Bestellnummer><2-digit counter>
             -- Counter resets per LU so TUs within the same LU get 01, 02, … while TUs on
             -- different LUs (= different source orders) start at 01 independently.
             -- This avoids both the cross-order POReference leak (pre-PR bug) and the
             -- zero-uniqueness 'DUMMY_____' literal (first PR draft).
             SELECT lu_hu_id,
                    tu_hu_id,
                    CASE
                        WHEN grai_raw IS NOT NULL
                            THEN grai_raw
                            ELSE v_dummy_grai_prefix ||
                                 lu_poreference_padded ||
                                 LPAD(
                                         (COUNT(*) FILTER (WHERE grai_raw IS NULL)
                                             OVER (PARTITION BY lu_hu_id
                                                   ORDER BY tu_hu_id, sort_ord
                                                   ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW))::text,
                                         2, '0')
                    END AS grai,
                    lot_number,
                    best_before_date,
                    sort_ord,
                    items_json
             FROM all_crates_raw)

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
               -- Buyer GLN: DESADV → shipment fallback
                   'buyerGLN', COALESCE(ctx.buyer_gln_desadv, ctx.buyer_gln_ship),
                   'buyerGcpLength',
                   CASE
                       WHEN ctx.buyer_gln_desadv IS NOT NULL THEN ctx.buyer_gcplength_desadv
                                                             ELSE ctx.buyer_gcplength_ship
                   END,
               -- Handover (DESADV only)
                   'handoverGLN', bpl_handover.gln,
                   'handoverGcpLength', bpl_handover.gln_gcpLength,
               -- Dropship: DESADV → shipment fallback
                   'dropshipGLN', COALESCE(ctx.drop_gln_desadv, ctx.drop_gln_ship),
                   'dropshipGcpLength',
                   CASE
                       WHEN ctx.drop_gln_desadv IS NOT NULL THEN ctx.drop_gcplength_desadv
                                                            ELSE ctx.drop_gcplength_ship
                   END,
               -- DESADV references: scalar (first element, backward-compat) AND array.
               -- desadvReferences[] carries all linked DESADV DocumentNos.
               -- Scalar falls back to M_InOut.EDI_Desadv_ID for shipments without junction rows.
                   'desadvReference', COALESCE((ctx.desadv_documentnos ->> 0), ctx.desadv_documentno),
                   'desadvReferences', COALESCE(ctx.desadv_documentnos, '[]'::jsonb),
               -- PO references: same scalar + array pattern.
               -- Scalar falls back to InOut.POReference for shipments without junction rows.
                   'poReference', COALESCE((ctx.desadv_poreferences ->> 0), ctx.poreference),
                   'poReferences', COALESCE(ctx.desadv_poreferences,
                                            CASE WHEN ctx.poreference IS NOT NULL
                                                 THEN jsonb_build_array(ctx.poreference)
                                                 ELSE '[]'::jsonb END),
               -- Pallets
                   'pallets', COALESCE(
                           (SELECT JSONB_AGG(
                                           JSONB_BUILD_OBJECT(
                                                   'sscc', pallet_agg.sscc,
                                                   'huId', pallet_agg.lu_hu_id,
                                                   'crates', pallet_agg.crates_json
                                           ) ORDER BY pallet_agg.lu_hu_id
                                   )
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
                                  ORDER BY c.lu_hu_id) pallet_agg),
                           '[]'::jsonb
                              )
           )
    INTO v_result
    FROM inout_context ctx
             JOIN m_warehouse wh ON wh.m_warehouse_id = ctx.m_warehouse_id
             LEFT JOIN c_bpartner_location bpl_wh
                       ON bpl_wh.c_bpartner_location_id = wh.c_bpartner_location_id
             LEFT JOIN ad_orginfo org ON org.ad_org_id = ctx.ad_org_id
             LEFT JOIN c_bpartner_location bpl_supplier
                       ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
             LEFT JOIN c_bpartner_location bpl_handover
                       ON bpl_handover.c_bpartner_location_id = ctx.handover_location_id
    WHERE ctx.isactive = 'Y'
      AND ctx.docstatus IN ('CO', 'CL');

    RETURN COALESCE(v_result, '{}'::jsonb);
END;
$$
    LANGUAGE plpgsql STABLE
;
