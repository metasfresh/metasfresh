DROP VIEW IF EXISTS historical_m_inout_json_v
;

CREATE OR REPLACE VIEW historical_m_inout_json_v AS
SELECT io.m_inout_id                                   AS "Shipment_ID",
       io.documentno                                   AS "Shipment_DocumentNo",
       io.movementdate                                 AS "Shipment_Date",
       io.docstatus                                    AS "DocStatus",
       io.ExternalId                                   AS "ExternalId",
       io.updated::timestamp                           AS "Updated",
       (CASE
            WHEN dsource.internalname IS NOT NULL
                THEN 'int-' || dsource.internalname
                ELSE ''
        END)                                           AS "DataSource",
       o.dateordered                                   AS "Order_Date",
       o.datepromised                                  AS "Order_DatePromised",
       COALESCE(o.poreference, io.poreference)         AS "Order_POReference",
       o.edi_desadv_id                                 AS "DESADV_ID",
       COALESCE(o.deliveryviarule, io.deliveryviarule) AS "DeliveryViaRule",

       bp_supplier.bpartner_json                       AS "Supplier",
       bpl_supplier.bpartner_location_json             AS "Supplier_Location",
       bp_buyer.bpartner_json                          AS "Buyer",
       bpl_buyer.bpartner_location_json                AS "Buyer_Location",
       bp_bill.bpartner_json                           AS "Invoicee",
       bpl_bill.bpartner_location_json                 AS "Invoicee_Location",
       bp_handover.bpartner_json                       AS "DeliveryParty",
       bpl_handover.bpartner_location_json             AS "DeliveryParty_Location",
       bp_dropship.bpartner_json                       AS "UltimateConsignee",
       bpl_dropship.bpartner_location_json             AS "UltimateConsignee_Location",

       curr.currency_json                              AS "Currency",

       (SELECT JSONB_AGG(JSONB_BUILD_OBJECT(
                                 'LineNo', iol.line,
                                 'M_InOutLine_ID', iol.m_inoutline_id,
                                 'Product_ID', iol.m_product_id,
                                 'ProductValue', p.value,
                                 'ProductName', p.name,
                                 'QtyEntered', iol.qtyentered,
                                 'UOM', uom.uomsymbol,
                                 'ExternalId', iol.externalid
                             ) ORDER BY iol.line)
        FROM m_inoutline iol
                 LEFT JOIN m_product p ON p.m_product_id = iol.m_product_id
                 LEFT JOIN c_uom uom ON uom.c_uom_id = iol.c_uom_id
        WHERE iol.m_inout_id = io.m_inout_id
          AND iol.isactive = 'Y')                      AS "Lines"

FROM m_inout io
         LEFT JOIN c_order o ON io.c_order_id = o.c_order_id
         LEFT JOIN AD_InputDataSource dsource ON dsource.AD_InputDataSource_ID = io.AD_InputDataSource_ID
         LEFT JOIN json_object.currency_object_v curr ON curr.c_currency_id = o.c_currency_id
         LEFT JOIN json_object.bpartner_object_v bp_buyer ON bp_buyer.c_bpartner_id = o.c_bpartner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_buyer ON bpl_buyer.c_bpartner_location_id = o.c_bpartner_location_id
         LEFT JOIN c_bpartner_location bpl_bill_table ON bpl_bill_table.c_bpartner_location_id = o.bill_location_id
         LEFT JOIN json_object.bpartner_object_v bp_bill ON bp_bill.c_bpartner_id = bpl_bill_table.c_bpartner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_bill ON bpl_bill.c_bpartner_location_id = o.bill_location_id
         LEFT JOIN json_object.bpartner_object_v bp_handover ON bp_handover.c_bpartner_id = o.handover_partner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_handover ON bpl_handover.c_bpartner_location_id = o.handover_location_id
         LEFT JOIN json_object.bpartner_object_v bp_dropship ON bp_dropship.c_bpartner_id = o.dropship_bpartner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_dropship ON bpl_dropship.c_bpartner_location_id = o.dropship_location_id
         LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
         LEFT JOIN json_object.bpartner_object_v bp_supplier ON bp_supplier.c_bpartner_id = org.org_bpartner_id
         LEFT JOIN json_object.bpartner_location_object_v bpl_supplier ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
WHERE io.isactive = 'Y'
ORDER BY io.m_inout_id
;
