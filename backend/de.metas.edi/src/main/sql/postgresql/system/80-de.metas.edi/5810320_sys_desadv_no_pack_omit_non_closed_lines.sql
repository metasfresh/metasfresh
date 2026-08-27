-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/functions/desadv_json/get_desadv_lines_no_pack_json_fn.sql
--
-- DESADV JSON: optionally omit no-pack lines whose delivery is not closed.
-- Adds a SysConfig-gated filter to get_desadv_lines_no_pack_json_fn: when
-- de.metas.edi.desadv.OmitNonClosedNoPackLines = 'Y', a no-pack line is kept only if its
-- delivery is closed. Some receiving systems treat every no-pack line they receive as a
-- closed delivery link, so a not-closed no-pack line would be mis-interpreted.
-- Default 'N' preserves the previous behaviour (all matching no-pack lines emitted).

CREATE OR REPLACE FUNCTION "de.metas.edi".get_desadv_lines_no_pack_json_fn(p_edi_desadv_id NUMERIC, p_m_inout_id NUMERIC)
    RETURNS JSONB AS $$
DECLARE
    v_lines_no_pack_json JSONB;
    v_omit_non_closed    TEXT;
BEGIN
    -- When this toggle is 'Y', a no-pack line whose delivery is NOT closed is omitted
    -- entirely from the export: some receiving systems treat every no-pack line they
    -- receive as a closed delivery link, so sending a not-closed line would be mis-read.
    -- Default 'N' preserves the previous behaviour (all matching no-pack lines emitted).
    v_omit_non_closed := get_sysconfig_value('de.metas.edi.desadv.OmitNonClosedNoPackLines', 'N');

    SELECT jsonb_agg(
                   jsonb_build_object(
                           'QtyCUsPerTU', dl.qtyitemcapacity,
                           'DesadvLine', JSONB_BUILD_OBJECT(
                                   'Product', JSONB_BUILD_OBJECT(
                                           'SupplierProductNo', p.value,
                                           'Name', p.name,
                                           'Description', p.description,
                                           'BuyerProductNo', COALESCE(dl.productno, asi_data.productno),
                                           'GTIN_CU', COALESCE(dl.gtin_cu, asi_data.gtin, asi_data.ean_cu, asi_data.ean13_productcode, p.gtin),
                                           'GTIN_TU', COALESCE(dl.gtin_tu, pip.gtin),
                                           'NetWeight', p.weight,
                                           'GrossWeight', p.grossweight,
                                           'GrossWeightUOM', COALESCE(gw_uom.uom_json, '{}'::jsonb)
                                   ),
                                   'QtyOrderedInDesadvLineUOM', dl.qtyentered,
                                   'QtyDeliveredInDesadvLineUOM', dl.qtydeliveredinuom,
                                   'DesadvLineUOM', COALESCE(dl_uom.uom_json, '{}'::jsonb),
                                   'QtyDeliveredInInvoicingUOM', dl.qtydeliveredininvoiceuom,
                                   'InvoicingUOM', COALESCE(inv_uom.uom_json, '{}'::jsonb),
                                   'OrderLine', ol.line,
                                   'ShipmentLine', iol.line,
                                   'OrderPOReference', o.poreference,
                                   'OrderDocumentNo', o.documentno,
                                   'DesadvLine', dl.line,
                                   'IsDeliveryClosed',
                                       CASE WHEN diol.desadvlinetotalqtydelivered IS NOT NULL
                                            THEN diol.desadvlinetotalqtydelivered >= COALESCE(dl.qtyordered_override, dl.qtyordered)
                                            WHEN COALESCE(dl.qtyordered_override, dl.qtyordered, 0) = 0
                                            THEN true
                                            ELSE false
                                       END
                           )
                   ) ORDER BY dl.line
           )
    INTO v_lines_no_pack_json
    FROM edi_desadvline dl
             JOIN m_product p ON p.m_product_id = dl.m_product_id
             JOIN edi_desadv d ON d.edi_desadv_id = dl.edi_desadv_id
             JOIN "de.metas.edi".edi_uom_object_v dl_uom ON dl_uom.c_uom_id = dl.c_uom_id
             LEFT JOIN "de.metas.edi".edi_uom_object_v inv_uom ON inv_uom.c_uom_id = dl.c_uom_invoice_id
             LEFT JOIN "de.metas.edi".edi_uom_object_v gw_uom ON gw_uom.c_uom_id = p.grossweight_uom_id
             -- Pick exactly one m_inoutline per desadv line for this shipment. Without LIMIT 1
             -- a desadv line that maps to multiple m_inoutline rows would multiply the output
             -- and could resolve different GTINs per multiplied row via the asi_data join.
             -- When the line is unshipped (or shipped under a different M_InOut), iol is NULL.
             LEFT JOIN LATERAL (
                 SELECT iol_inner.m_inoutline_id,
                        iol_inner.line,
                        iol_inner.m_attributesetinstance_id
                 FROM m_inoutline iol_inner
                 WHERE iol_inner.edi_desadvline_id = dl.edi_desadvline_id
                   AND iol_inner.m_inout_id = p_m_inout_id
                 ORDER BY iol_inner.m_inoutline_id
                 LIMIT 1
             ) iol ON TRUE
             -- Resolve OrderLine / Order context DIRECTLY from edi_desadvline -> c_orderline
             -- (not via iol). For unshipped desadv lines, iol is NULL but c_orderline still
             -- exists and we must still expose its line number / order header in the JSON
             -- (test scenario "DESADV export includes unshipped order lines in
             -- DesadvLineWithNoPacking" requires OrderLine=20 for the unshipped line).
             -- LATERAL + LIMIT 1 guards against the same 1:N multiplication risk.
             LEFT JOIN LATERAL (
                 SELECT ol_inner.line, ol_inner.c_order_id
                 FROM c_orderline ol_inner
                 WHERE ol_inner.edi_desadvline_id = dl.edi_desadvline_id
                 ORDER BY ol_inner.c_orderline_id
                 LIMIT 1
             ) ol ON TRUE
             LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
             -- Junction table for per-shipment-line delivery totals (used for IsDeliveryClosed).
             -- LATERAL + LIMIT 1 prevents row multiplication when one edi_desadvline maps to
             -- multiple m_inoutline records in the same shipment.
             LEFT JOIN LATERAL (
                 SELECT diol_inner.desadvlinetotalqtydelivered
                 FROM m_inoutline iol_diol
                          JOIN edi_desadvline_inoutline diol_inner
                               ON diol_inner.m_inoutline_id = iol_diol.m_inoutline_id
                              AND diol_inner.edi_desadvline_id = dl.edi_desadvline_id
                 WHERE iol_diol.m_inout_id = p_m_inout_id
                   AND iol_diol.edi_desadvline_id = dl.edi_desadvline_id
                 LIMIT 1
             ) diol ON TRUE
             -- Packing instruction product lookup (preferred BPartner, fall back to wildcard)
             LEFT JOIN LATERAL (
                 SELECT gtin
                 FROM m_hu_pi_item_product
                 WHERE isactive = 'Y'
                   AND m_product_id = p.m_product_id
                   AND COALESCE(c_bpartner_id, d.c_bpartner_id) = d.c_bpartner_id
                 ORDER BY c_bpartner_id NULLS LAST
                 LIMIT 1
             ) pip ON TRUE
             -- ASI-aware product data lookup (M_Product_ASI_Data with content-based ASI
             -- subset matching). When iol is NULL (unshipped line) iol.m_attributesetinstance_id
             -- is NULL and only wildcard records (m_attributesetinstance_id IS NULL in
             -- M_Product_ASI_Data) match — that is the documented fallback for unshipped lines.
             LEFT JOIN LATERAL (
                 SELECT gtin, ean_cu, ean13_productcode, productno
                 FROM m_product_asi_data
                 WHERE isactive = 'Y'
                   AND m_product_id = p.m_product_id
                   AND (c_bpartner_id IS NULL OR c_bpartner_id = d.c_bpartner_id)
                   AND IsASIAttributesKeySubset(m_attributesetinstance_id, iol.m_attributesetinstance_id)
                 ORDER BY seqno
                 LIMIT 1
             ) asi_data ON TRUE
    WHERE dl.edi_desadv_id = p_edi_desadv_id
      AND dl.isactive = 'Y'
      -- Include lines that either have a shipment line in this InOut (shipped but no pack),
      -- OR have no shipment line at all (not shipped — qty delivered = 0, must still appear in DESADV).
      AND (
          EXISTS (
              SELECT 1 FROM m_inoutline iol_exist
              WHERE iol_exist.m_inout_id = p_m_inout_id
                AND iol_exist.edi_desadvline_id = dl.edi_desadvline_id
          )
          OR NOT EXISTS (
              SELECT 1 FROM m_inoutline iol_any
                       JOIN m_inout io_any ON io_any.m_inout_id = iol_any.m_inout_id AND io_any.docstatus IN ('CO', 'CL')
              WHERE iol_any.edi_desadvline_id = dl.edi_desadvline_id
          )
      )
      -- Exclude lines that have a pack in this shipment (those are handled by get_desadv_packs_json_fn).
      AND NOT EXISTS (
        SELECT 1
        FROM edi_desadv_pack_item edpi_check
                 JOIN edi_desadv_pack edp_check ON edp_check.edi_desadv_pack_id = edpi_check.edi_desadv_pack_id
            AND edp_check.edi_desadv_id = dl.edi_desadv_id
            AND edp_check.isactive = 'Y'
                 JOIN m_inoutline iol_check ON iol_check.m_inoutline_id = edpi_check.m_inoutline_id
            AND iol_check.m_inout_id = p_m_inout_id
        WHERE edpi_check.edi_desadvline_id = dl.edi_desadvline_id
          AND edpi_check.isactive = 'Y'
    )
      -- Omit toggle (see header comment): when 'Y', keep a no-pack line only if its delivery
      -- is closed. The condition below is the exact IsDeliveryClosed expression emitted above.
      AND (
          v_omit_non_closed <> 'Y'
          OR (CASE WHEN diol.desadvlinetotalqtydelivered IS NOT NULL
                   THEN diol.desadvlinetotalqtydelivered >= COALESCE(dl.qtyordered_override, dl.qtyordered)
                   WHEN COALESCE(dl.qtyordered_override, dl.qtyordered, 0) = 0
                   THEN true
                   ELSE false
              END)
      );

    RETURN COALESCE(v_lines_no_pack_json, '[]'::jsonb);
END;
$$ LANGUAGE plpgsql STABLE;

-- AD_SysConfig default for the new toggle (default OFF preserves existing behaviour).
-- Guarded INSERT: idempotent if the row already exists (re-apply / faithful-DB rebuild).
INSERT INTO AD_SysConfig
    (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
SELECT 0, 0, 541829 /*From ID Server*/, 'S',
        TO_TIMESTAMP('2026-07-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'When Y, get_desadv_lines_no_pack_json_fn omits no-pack lines whose delivery is not closed (some receiving systems treat every received no-pack line as a closed delivery link). Default N keeps all matching no-pack lines. Set per instance via set_sysconfig_value.',
        'D', 'Y',
        'de.metas.edi.desadv.OmitNonClosedNoPackLines',
        TO_TIMESTAMP('2026-07-01 00:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
        100,
        'N'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig
    WHERE Name = 'de.metas.edi.desadv.OmitNonClosedNoPackLines'
      AND AD_Client_ID = 0 AND AD_Org_ID = 0
);
