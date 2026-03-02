-- Function for EPCIS event JSON assembly from shipment data
-- See ddl/functions/epcis_json/get_epcis_events_json_fn.sql for the canonical DDL

CREATE OR REPLACE FUNCTION "de.metas.edi".get_epcis_events_json_fn(p_m_inout_id NUMERIC)
    RETURNS JSONB
AS
$$
DECLARE
    v_result JSONB;
BEGIN
    SELECT JSONB_BUILD_OBJECT(
                   'shipmentId', io.m_inout_id,
                   'documentNo', io.documentno,
                   'movementDate', TO_CHAR(io.movementdate, 'YYYY-MM-DD"T"HH24:MI:SS'),
                   'timezone', '+01:00',
                   'supplierGLN', bpl_supplier.gln,
                   'warehouseGLN', bpl_supplier.gln,
                   'buyerGLN', bpl_buyer.gln,
                   'handoverGLN', bpl_handover.gln,
                   'dropshipGLN', bpl_dropship.gln,
                   'desadvReference', d.documentno,
                   'poReference', COALESCE(d.poreference, io.poreference),
                   'pallets', COALESCE(pallets_data.pallets_json, '[]'::jsonb)
           )
    INTO v_result
    FROM m_inout io
             JOIN edi_desadv d ON d.edi_desadv_id = io.edi_desadv_id
             LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
             LEFT JOIN c_bpartner_location bpl_supplier ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
             LEFT JOIN c_bpartner_location bpl_buyer ON bpl_buyer.c_bpartner_location_id = d.c_bpartner_location_id
             LEFT JOIN c_bpartner_location bpl_handover ON bpl_handover.c_bpartner_location_id = d.handover_location_id
             LEFT JOIN c_bpartner_location bpl_dropship ON bpl_dropship.c_bpartner_location_id = d.dropship_location_id
             LEFT JOIN LATERAL (
        SELECT JSONB_AGG(
                       JSONB_BUILD_OBJECT(
                               'sscc', lu_pack.ipa_sscc18,
                               'huId', lu_pack.m_hu_id,
                               'crates', COALESCE(crates_data.crates_json, '[]'::jsonb)
                       ) ORDER BY lu_pack.seqno
               ) AS pallets_json
        FROM edi_desadv_pack lu_pack
                 LEFT JOIN LATERAL (
            SELECT JSONB_AGG(
                           JSONB_BUILD_OBJECT(
                                   'grai', grai_attr.value,
                                   'tuHuId', tu_hu.m_hu_id,
                                   'items', COALESCE(items_data.items_json, '[]'::jsonb)
                           )
                   ) AS crates_json
            FROM m_hu_item parent_item
                     JOIN m_hu tu_hu ON tu_hu.m_hu_item_parent_id = parent_item.m_hu_item_id
                     LEFT JOIN m_hu_attribute grai_attr ON grai_attr.m_hu_id = tu_hu.m_hu_id
                AND grai_attr.m_attribute_id = (SELECT m_attribute_id
                                                FROM m_attribute
                                                WHERE value = 'GRAI'
                                                LIMIT 1)
                     LEFT JOIN LATERAL (
                SELECT JSONB_AGG(
                               JSONB_BUILD_OBJECT(
                                       'productGTIN', prod.gtin,
                                       'productValue', prod.value,
                                       'lotNumber', pi_item.lotnumber,
                                       'bestBeforeDate', TO_CHAR(pi_item.bestbeforedate, 'YYYY-MM-DD'),
                                       'quantity', pi_item.qtycuspertu,
                                       'uom', COALESCE(uom.x12de355, 'KGM')
                               )
                       ) AS items_json
                FROM edi_desadv_pack_item pi_item
                         LEFT JOIN m_inoutline iol ON iol.m_inoutline_id = pi_item.m_inoutline_id
                         LEFT JOIN m_product prod ON prod.m_product_id = iol.m_product_id
                         LEFT JOIN c_uom uom ON uom.c_uom_id = iol.c_uom_id
                WHERE pi_item.edi_desadv_pack_id = lu_pack.edi_desadv_pack_id
                  AND pi_item.isactive = 'Y'
                ) items_data ON TRUE
            WHERE parent_item.m_hu_id = lu_pack.m_hu_id
              AND parent_item.itemtype = 'HU'
            ) crates_data ON TRUE
        WHERE lu_pack.edi_desadv_id = d.edi_desadv_id
          AND lu_pack.isactive = 'Y'
          AND lu_pack.edi_desadv_parent_pack_id IS NULL
        ) pallets_data ON TRUE
    WHERE io.m_inout_id = p_m_inout_id
      AND io.isactive = 'Y'
      AND io.docstatus IN ('CO', 'CL');

    RETURN COALESCE(v_result, '{}'::jsonb);
END;
$$
    LANGUAGE plpgsql STABLE;
