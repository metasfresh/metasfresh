-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/functions/desadv_json/get_desadv_lines_no_pack_json_fn.sql
--
-- me03#29063: Fix regression introduced in migration 5802240.
--
-- When inlining the (now-dropped) edi_desadv_line_object_v into the no-pack function,
-- the c_orderline join was routed via iol.c_orderline_id. For UNSHIPPED desadv lines
-- there is no m_inoutline (iol is NULL via the LATERAL+LIMIT 1), which made ol NULL
-- and produced OrderLine=0 in the output JSON. The original view resolved c_orderline
-- directly from edi_desadvline (`ol.edi_desadvline_id = dl.edi_desadvline_id`) precisely
-- so that unshipped lines still expose their order context.
--
-- This script recreates the function with c_orderline / c_order resolved DIRECTLY from
-- edi_desadvline via a separate LATERAL+LIMIT 1, restoring the unshipped-line behaviour
-- while still guarding against the 1:N multiplication risk the old view had.
--
-- Caught by cucumber CI on PR https://github.com/metasfresh/metasfresh/pull/23942:
-- scenario "DESADV export includes unshipped order lines in DesadvLineWithNoPacking"
-- expected OrderLine=20 but received 0 (test (cucumber) (profile5) failure).

CREATE OR REPLACE FUNCTION "de.metas.edi".get_desadv_lines_no_pack_json_fn(p_edi_desadv_id NUMERIC, p_m_inout_id NUMERIC)
    RETURNS JSONB AS $$
DECLARE
    v_lines_no_pack_json JSONB;
BEGIN
    SELECT jsonb_agg(
                   jsonb_build_object(
                           'QtyCUsPerTU', dl.qtyitemcapacity,
                           'DesadvLine', JSONB_BUILD_OBJECT(
                                   'Product', JSONB_BUILD_OBJECT(
                                           'SupplierProductNo', p.value,
                                           'Name', p.name,
                                           'Description', p.description,
                                           'BuyerProductNo', COALESCE(dl.productno, asi_data.productno),
                                           'GTIN_CU', COALESCE(dl.gtin_cu, asi_data.gtin, asi_data.ean13_productcode, p.gtin),
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
             -- Direct edi_desadvline -> c_orderline lookup. Required because unshipped lines
             -- have no m_inoutline (iol NULL) but still need OrderLine + Order context in JSON.
             LEFT JOIN LATERAL (
                 SELECT ol_inner.line, ol_inner.c_order_id
                 FROM c_orderline ol_inner
                 WHERE ol_inner.edi_desadvline_id = dl.edi_desadvline_id
                 ORDER BY ol_inner.c_orderline_id
                 LIMIT 1
             ) ol ON TRUE
             LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
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
             LEFT JOIN LATERAL (
                 SELECT gtin
                 FROM m_hu_pi_item_product
                 WHERE isactive = 'Y'
                   AND m_product_id = p.m_product_id
                   AND COALESCE(c_bpartner_id, d.c_bpartner_id) = d.c_bpartner_id
                 ORDER BY c_bpartner_id NULLS LAST
                 LIMIT 1
             ) pip ON TRUE
             LEFT JOIN LATERAL (
                 SELECT gtin, ean13_productcode, productno
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
    );

    RETURN COALESCE(v_lines_no_pack_json, '[]'::jsonb);
END;
$$ LANGUAGE plpgsql STABLE;
