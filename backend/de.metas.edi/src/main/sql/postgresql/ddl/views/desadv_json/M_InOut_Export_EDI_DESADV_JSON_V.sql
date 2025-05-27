/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2025 metas GmbH
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
--drop view if exists M_InOut_Export_EDI_DESADV_JSON_V;
-- Main view that uses the functions and other views
CREATE OR REPLACE VIEW M_InOut_Export_EDI_DESADV_JSON_V AS
SELECT
    mio.m_inout_id,
    jsonb_build_object(
            '@SequenceNo', ed.edi_desadv_id,
            'C_BPartner_ID', COALESCE(bp_main.bpartner_json, '{}'::jsonb),
            'C_BPartner_Location_ID', COALESCE(bpl_main.bpartner_location_json, '{}'::jsonb),
            'DateOrdered', ed.dateordered,
            'DocumentNo', ed.documentno,
            'ShipmentDocumentNo', mio.documentno,
            'EDI_Desadv_ID', ed.edi_desadv_id,
            'EDI_ExportStatus', ed.edi_exportstatus,
            'MovementDate', mio.movementdate,
            'POReference', COALESCE(ed.poreference, mio.poreference),

            'EDI_Exp_Desadv_Pack', "de.metas.edi".get_desadv_packs_json_fn(ed.edi_desadv_id), -- Call function

            'Bill_Location_ID', COALESCE(bpl_bill.bpartner_location_json, '{}'::jsonb),
            'C_Currency_ID', COALESCE(curr.currency_json, '{}'::jsonb),
            'HandOver_Location_ID', COALESCE(bpl_handover.bpartner_location_json, '{}'::jsonb),
            'DropShip_BPartner_ID', COALESCE(bp_dropship.bpartner_json, '{}'::jsonb),
            'DropShip_Location_ID', COALESCE(bpl_dropship.bpartner_location_json, '{}'::jsonb),
            'HandOver_Partner_ID', COALESCE(bp_handover_partner.bpartner_json, '{}'::jsonb),

            'InvoicableQtyBasedOn', (
                SELECT edl_ib.invoicableqtybasedon
                FROM edi_desadvline edl_ib
                WHERE edl_ib.edi_desadv_id = ed.edi_desadv_id AND edl_ib.isactive='Y'
                ORDER BY edl_ib.line
                LIMIT 1
            ),
            'DeliveryViaRule', COALESCE(ed.deliveryviarule, mio.deliveryviarule),

            'EDI_Exp_DesadvLineWithNoPack', "de.metas.edi".get_desadv_lines_no_pack_json_fn(ed.edi_desadv_id), -- Call function

            'M_InOut_ID', mio.m_inout_id,
            'shipment_documentno', mio.documentno,
            'DatePromised', mio.movementdate
    ) AS data
FROM m_inout mio
         JOIN edi_desadv ed ON mio.edi_desadv_id = ed.edi_desadv_id AND ed.isactive = 'Y'

    -- Joins for other lookup objects
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_main ON bp_main.c_bpartner_id = ed.c_bpartner_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_main ON bpl_main.c_bpartner_location_id = ed.c_bpartner_location_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_bill ON bpl_bill.c_bpartner_location_id = ed.bill_location_id
         LEFT JOIN "de.metas.edi".edi_currency_object_v curr ON curr.c_currency_id = ed.c_currency_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_handover ON bpl_handover.c_bpartner_location_id = ed.handover_location_id
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_dropship ON bp_dropship.c_bpartner_id = ed.dropship_bpartner_id
         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_dropship ON bpl_dropship.c_bpartner_location_id = ed.dropship_location_id
         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_handover_partner ON bp_handover_partner.c_bpartner_id = ed.handover_partner_id
WHERE mio.issotrx='Y'
;
