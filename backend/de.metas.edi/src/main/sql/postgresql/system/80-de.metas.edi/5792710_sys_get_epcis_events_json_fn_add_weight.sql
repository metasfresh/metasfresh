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

-- Function to assemble EPCIS event data as JSON for a given M_InOut (shipment).
-- Returns a flat JSON structure that the JavaScript transform converts to EPCIS 1.2 XML.
--
-- The JSON contains:
-- - Shipment header info (dates, GLNs, DESADV reference)
-- - Pallets array: each pallet has SSCC18 and crates
-- - Each crate has GRAI, product GTIN, lot, best-before, quantity, UOM
--
-- The JavaScript layer constructs the 4 CD events:
--   1.0.1 ObjectEvent LOT, 1.1.1 AggregationEvent PACKING,
--   3.3.3 AggregationEvent PICKING, 3.5.1 AggregationEvent COMMISSIONING

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
               -- Supplier GLN (org's BPartner location)
                   'supplierGLN', bpl_supplier.gln,
               -- Warehouse GLN (same as supplier for now; SGLN extension=0)
                   'warehouseGLN', bpl_supplier.gln,
               -- Warehouse value (for SGLN sub-location extension in JS template)
                   'warehouseValue', wh.value,
               -- Buyer GLN (from DESADV)
                   'buyerGLN', bpl_buyer.gln,
               -- Handover partner GLN (delivery point = DP)
                   'handoverGLN', bpl_handover.gln,
               -- Dropship/Ultimate Consignee GLN (UC)
                   'dropshipGLN', bpl_dropship.gln,
               -- DESADV reference number
                   'desadvReference', d.documentno,
               -- PO reference
                   'poReference', COALESCE(d.poreference, io.poreference),
               -- Pallets array
                   'pallets', COALESCE(pallets_data.pallets_json, '[]'::jsonb)
           )
    INTO v_result
    FROM m_inout io
             JOIN edi_desadv d ON d.edi_desadv_id = io.edi_desadv_id
        -- Warehouse value (for SGLN sub-location extension)
             LEFT JOIN m_warehouse wh ON wh.m_warehouse_id = io.m_warehouse_id
        -- Supplier (org) GLN
             LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
             LEFT JOIN c_bpartner_location bpl_supplier ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
        -- Buyer GLN
             LEFT JOIN c_bpartner_location bpl_buyer ON bpl_buyer.c_bpartner_location_id = d.c_bpartner_location_id
        -- Handover (delivery point) GLN
             LEFT JOIN c_bpartner_location bpl_handover ON bpl_handover.c_bpartner_location_id = d.handover_location_id
        -- Dropship (ultimate consignee) GLN
             LEFT JOIN c_bpartner_location bpl_dropship ON bpl_dropship.c_bpartner_location_id = d.dropship_location_id
        -- Pallets: top-level packs (those without a parent pack = LU level)
             LEFT JOIN LATERAL (
        SELECT JSONB_AGG(
                       JSONB_BUILD_OBJECT(
                               'sscc', lu_pack.ipa_sscc18,
                               'huId', lu_pack.m_hu_id,
                               'crates', COALESCE(crates_data.crates_json, '[]'::jsonb)
                       ) ORDER BY lu_pack.seqno
               ) AS pallets_json
        FROM edi_desadv_pack lu_pack
                 -- Crates: child packs under this LU, or pack_items directly on the LU
                 LEFT JOIN LATERAL (
            SELECT JSONB_AGG(
                           JSONB_BUILD_OBJECT(
                                   'grai', grai_attr.value,
                                   'lotNumber', lot_attr.value,
                                   'bestBeforeDate', bestbeforedate_attr.value,
                                   'tuHuId', tu_hu.m_hu_id,
                                   'items', COALESCE(items_data.items_json, '[]'::jsonb)
                           )
                   ) AS crates_json
            FROM m_hu_item parent_item
                     JOIN m_hu tu_hu ON tu_hu.m_hu_item_parent_id = parent_item.m_hu_item_id
                -- GRAI attribute on the TU
                     LEFT JOIN m_hu_attribute grai_attr ON grai_attr.m_hu_id = tu_hu.m_hu_id
                AND grai_attr.m_attribute_id = (SELECT m_attribute_id
                                                FROM m_attribute
                                                WHERE value = 'GRAI'
                                                LIMIT 1)
                -- LOT attribute on the TU
                     LEFT JOIN m_hu_attribute lot_attr ON lot_attr.m_hu_id = tu_hu.m_hu_id
                AND lot_attr.m_attribute_id = (SELECT m_attribute_id
                                               FROM m_attribute
                                               WHERE value = 'Lot-Nummer'
                                               LIMIT 1)
                -- HU_BestBeforeDate on the TU
                     LEFT JOIN m_hu_attribute bestbeforedate_attr ON bestbeforedate_attr.m_hu_id = tu_hu.m_hu_id
                AND bestbeforedate_attr.m_attribute_id = (SELECT m_attribute_id
                                                          FROM m_attribute
                                                          WHERE value = 'HU_BestBeforeDate'
                                                          LIMIT 1)
                -- Items on this TU from the desadv pack items
                     LEFT JOIN LATERAL (
                SELECT JSONB_AGG(
                               JSONB_BUILD_OBJECT(
                                       'tuGTIN', pi_item.gtin_tu_packingmaterial,
                                       'quantity', pi_item.qtycuspertu,
                                       'movementqty', pi_item.movementqty,
                                       'productValue', prod.value,
                                       'productNetWeight', prod.weight,
                                       'productGrossWeight', prod.grossweight,
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
          AND lu_pack.edi_desadv_parent_pack_id IS NULL -- top-level packs only (LU/pallets)
        ) pallets_data ON TRUE
    WHERE io.m_inout_id = p_m_inout_id
      AND io.isactive = 'Y'
      AND io.docstatus IN ('CO', 'CL');

    RETURN COALESCE(v_result, '{}'::jsonb);
END;
$$
    LANGUAGE plpgsql STABLE
;
