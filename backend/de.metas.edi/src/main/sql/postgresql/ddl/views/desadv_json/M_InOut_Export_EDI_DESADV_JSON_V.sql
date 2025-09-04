
-- Main view that uses the functions and other views
drop VIEW if exists M_InOut_Export_EDI_DESADV_JSON_V;
CREATE OR REPLACE VIEW M_InOut_Export_EDI_DESADV_JSON_V AS
SELECT io.m_inout_id,
       JSON_BUILD_OBJECT('metasfresh_DESADV', JSONB_BUILD_OBJECT(
               'Version', '0.2',
               'TechnicalRecipientGLN', buyer.edidesadvrecipientgln,
               'Parties', JSONB_BUILD_OBJECT(
                       'Supplier', COALESCE(bp_supplier.bpartner_json, '{}'::jsonb),
                       'Supplier_Location', COALESCE(bpl_supplier.bpartner_location_json, '{}'::jsonb),
                       'Buyer', COALESCE(bp_buyer.bpartner_json, '{}'::jsonb),
                       'Buyer_Location', COALESCE(bpl_buyer.bpartner_location_json, '{}'::jsonb),
                       'Invoicee', COALESCE(bp_bill.bpartner_json, '{}'::jsonb),
                       'Invoicee_Location', COALESCE(bpl_bill.bpartner_location_json, '{}'::jsonb),
                       'DeliveryParty', COALESCE(bp_handover.bpartner_json, '{}'::jsonb),
                       'DeliveryParty_Location', COALESCE(bpl_handover.bpartner_location_json, '{}'::jsonb),
                       'UltimateConsignee', COALESCE(bp_dropship.bpartner_json, '{}'::jsonb),
                       'UltimateConsignee_Location', COALESCE(bpl_dropship.bpartner_location_json, '{}'::jsonb)
               ),
               'DateOrdered', d.dateordered,
               'ShipmentDocumentNo', io.documentno,
               'EDI_Desadv_ID', d.edi_desadv_id,
               'MovementDate', io.movementdate,
               'POReference', COALESCE(d.poreference, io.poreference),
               'Packings', "de.metas.edi".get_desadv_packs_json_fn(d.edi_desadv_id),
               'Currency', COALESCE(curr.currency_json, '{}'::jsonb),
               'InvoicableQtyBasedOn', (SELECT edl_ib.invoicableqtybasedon
                                FROM edi_desadvline edl_ib
                                WHERE edl_ib.edi_desadv_id = d.edi_desadv_id
                                  AND edl_ib.isactive = 'Y'
                                ORDER BY edl_ib.line
                                LIMIT 1),
               'DeliveryViaRule', COALESCE(d.deliveryviarule, io.deliveryviarule),
               'DesadvLineWithNoPacking', "de.metas.edi".get_desadv_lines_no_pack_json_fn(d.edi_desadv_id),
               'M_InOut_ID', io.m_inout_id,
               'DatePromised', o.datepromised)
       ) as embedded_json
                         FROM m_inout io
                         LEFT JOIN c_order o ON io.c_order_id = o.c_order_id 
                         JOIN edi_desadv d ON io.edi_desadv_id = d.edi_desadv_id
                         JOIN c_bpartner buyer ON d.c_bpartner_id = buyer.c_bpartner_id
    -- Joins for other lookup objects
                         LEFT JOIN "de.metas.edi".edi_currency_object_v curr ON curr.c_currency_id = d.c_currency_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_buyer ON bp_buyer.c_bpartner_id = d.c_bpartner_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_buyer ON bpl_buyer.c_bpartner_location_id = d.c_bpartner_location_id
                         LEFT JOIN c_bpartner_location bpl_bill_table ON bpl_bill_table.c_bpartner_location_id = d.bill_location_id -- helper, bc desadv has no direct invoicee
                         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_bill ON bp_bill.c_bpartner_id = bpl_bill_table.c_bpartner_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_bill ON bpl_bill.c_bpartner_location_id = d.bill_location_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_handover ON bp_handover.c_bpartner_id = d.handover_partner_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_handover ON bpl_handover.c_bpartner_location_id = d.handover_location_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_dropship ON bp_dropship.c_bpartner_id = d.dropship_bpartner_id
                         LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_dropship ON bpl_dropship.c_bpartner_location_id = d.dropship_location_id
    -- Joins for the supplier. Note that we not output the m_inout.m_warehouse's partner, because the WH might be owned by a third party
                         LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
                         JOIN "de.metas.edi".edi_bpartner_object_v bp_supplier ON bp_supplier.c_bpartner_id = org.org_bpartner_id
                         JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_supplier ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id

                         WHERE io.isactive = 'Y'
  AND io.docstatus IN ('CO', 'CL')
;

-- Example query
--select * from M_InOut_Export_EDI_DESADV_JSON_V where m_inout_id=1001857;
