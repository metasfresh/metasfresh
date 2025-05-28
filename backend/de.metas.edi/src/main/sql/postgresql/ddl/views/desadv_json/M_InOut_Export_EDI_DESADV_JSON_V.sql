
-- Main view that uses the functions and other views
drop VIEW if exists M_InOut_Export_EDI_DESADV_JSON_V;
CREATE OR REPLACE VIEW M_InOut_Export_EDI_DESADV_JSON_V AS
SELECT mio.m_inout_id,
       JSON_BUILD_OBJECT('metasfresh_DESADV', JSONB_BUILD_OBJECT(
               'Buyer', COALESCE(bp_main.bpartner_json, '{}'::jsonb),
               'Buyer_Location', COALESCE(bpl_main.bpartner_location_json, '{}'::jsonb),
               'DateOrdered', ed.dateordered,
               'ShipmentDocumentNo', mio.documentno,
               'EDI_Desadv_ID', ed.edi_desadv_id,
               'MovementDate', mio.movementdate,
               'POReference', COALESCE(ed.poreference, mio.poreference),
               'Packing', "de.metas.edi".get_desadv_packs_json_fn(ed.edi_desadv_id), -- Call function
               'Invoicee_Location', COALESCE(bpl_bill.bpartner_location_json, '{}'::jsonb),
               'Currency', COALESCE(curr.currency_json, '{}'::jsonb),
               'DeliveryParty_Location', COALESCE(bpl_handover.bpartner_location_json, '{}'::jsonb),
               'UltimateConsignee', COALESCE(bp_dropship.bpartner_json, '{}'::jsonb),
               'UltimateConsignee_Location', COALESCE(bpl_dropship.bpartner_location_json, '{}'::jsonb),
               'DeliveryParty', COALESCE(bp_handover_partner.bpartner_json, '{}'::jsonb),
               'InvoicableQtyBasedOn', (SELECT edl_ib.invoicableqtybasedon
                                        FROM edi_desadvline edl_ib
                                        WHERE edl_ib.edi_desadv_id = ed.edi_desadv_id
                                          AND edl_ib.isactive = 'Y'
                                        ORDER BY edl_ib.line
                                        LIMIT 1),
               'DeliveryViaRule', COALESCE(ed.deliveryviarule, mio.deliveryviarule),
               'DesadvLineWithNoPacking', "de.metas.edi".get_desadv_lines_no_pack_json_fn(ed.edi_desadv_id), -- Call function

               'M_InOut_ID', mio.m_inout_id,
               'DatePromised', mio.movementdate
                                              )
       ) as embedded_json
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

WHERE mio.isactive = 'Y'
  AND mio.docstatus IN ('CO', 'CL')
;

-- Example query
--select * from M_InOut_Export_EDI_DESADV_JSON_V where m_inout_id=1273429;
