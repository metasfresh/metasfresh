-- me03 #29231: rewrite sibling line-level views M_InOut_DesadvLine_V + M_InOut_DesadvLine2_V
-- (Replication-Interface RPL export path -- LineItems & DesadvLineWithNoPack EXP_Format children)
-- to enumerate (shipment, DESADV) pairs via the EDI_Desadv_M_InOut junction instead of the legacy
-- single-FK join on M_InOut.EDI_Desadv_ID. For consolidated multi-source-order shipments the legacy
-- join would have caused N-1 source DESADVs to lose their line-level rows in the RPL export.
--
-- PK columns (M_InOut_DesadvLine_V_ID / M_InOut_DesadvLine2_V_ID and the parent M_InOut_Desadv_ID)
-- are now synthetic composites (base_id * 10000000 + ordinal) so they stay unique when one shipment
-- has N>=2 DESADVs. The PKs are opaque (Record_ID for EXP_Format-driven exports); no Java consumer
-- assumes PK == M_InOutLine_ID or PK == EDI_DesadvLine_ID.

DROP VIEW IF EXISTS M_InOut_DesadvLine_V$new;

CREATE OR REPLACE VIEW M_InOut_DesadvLine_V$new AS
SELECT (shipment.m_inout_id::bigint * 10000000
            + (row_number() OVER (PARTITION BY shipment.m_inout_id ORDER BY desadv.edi_desadv_id) - 1)
       )::numeric                                                                                                                           AS M_InOut_Desadv_ID,

       (shipmentLine.m_inoutline_id::bigint * 10000000
            + (row_number() OVER (PARTITION BY shipmentLine.m_inoutline_id ORDER BY desadv.edi_desadv_id) - 1)
       )::numeric                                                                                                                           AS M_InOut_DesadvLine_V_ID,
       shipment.m_inout_id,
       shipmentLine.m_inoutline_id,

       desadv.edi_desadv_id,
       desadv.AD_Client_ID,
       desadv.ad_org_id,
       COALESCE(desadvInOutLine.updated, dline.updated)                                                                                     AS updated,
       COALESCE(desadvInOutLine.updatedby, dline.updatedby)                                                                                 AS updatedby,
       COALESCE(desadvInOutLine.created, dline.created)                                                                                     AS created,
       COALESCE(desadvInOutLine.createdby, dline.createdby)                                                                                 AS createdby,

       COALESCE(desadvInOutLine.edi_desadvline_id, dline.edi_desadvline_id)                                                                 AS edi_desadvline_id,
       COALESCE(desadvInOutLine.M_Product_ID, dline.M_Product_ID)                                                                           AS M_Product_ID,

       COALESCE(desadvInOutLine.qtydeliveredinstockinguom, 0)                                                                               AS qtydeliveredinstockinguom,
       COALESCE(desadvInOutLine.C_UOM_ID, dline.C_UOM_ID)                                                                                   AS C_UOM_ID,
       COALESCE(desadvInOutLine.QtyDeliveredInUOM, 0)                                                                                       AS QtyDeliveredInUOM,
       COALESCE(desadvInOutLine.C_UOM_Invoice_ID, dline.C_UOM_Invoice_ID)                                                                   AS C_UOM_Invoice_ID,
       COALESCE(desadvInOutLine.QtyDeliveredInInvoiceUOM, 0)                                                                                AS QtyDeliveredInInvoiceUOM,
       COALESCE(desadvInOutLine.C_UOM_BPartner_ID, dline.C_UOM_BPartner_ID)                                                                 AS C_UOM_BPartner_ID,
       COALESCE(desadvInOutLine.QtyEnteredInBPartnerUOM, 0)                                                                                 AS QtyEnteredInBPartnerUOM,

       dline.isactive,
       dline.Line,
       dline.ProductDescription,
       dline.ProductNo,
       dline.QtyEntered,
       dline.IsSubsequentDeliveryPlanned,
       dline.EAN_CU,
       dline.EAN_TU,
       dline.GTIN_CU,
       dline.GTIN_TU,
       dline.UPC_TU,
       dline.upc_cu,
       dline.PriceActual,
       dline.QtyOrdered,
       dline.QtyOrdered_Override,
       (SELECT x12de355 FROM C_UOM WHERE C_UOM.C_UOM_ID = dline.C_UOM_Invoice_ID)                                                           AS EanCom_Invoice_UOM,
       dline.InvoicableQtyBasedOn,
       dline.OrderPOReference,
       dline.QtyItemCapacity,
       dline.OrderLine,
       dline.ExternalSeqNo,
       dline.BPartner_QtyItemCapacity,
       CASE WHEN desadvInOutLine.DesadvLineTotalQtyDelivered >= COALESCE(dline.QtyOrdered_Override, dline.QtyOrdered) THEN 'Y' ELSE 'N' END AS IsDeliveryClosed
FROM edi_desadv desadv
         INNER JOIN edi_desadvline dline ON desadv.edi_desadv_id = dline.edi_desadv_id
         INNER JOIN edi_desadv_m_inout link ON link.edi_desadv_id = desadv.edi_desadv_id AND link.isactive = 'Y'
         INNER JOIN m_inout shipment ON shipment.m_inout_id = link.m_inout_id
         LEFT JOIN m_inoutline shipmentLine ON shipmentLine.m_inout_id = shipment.m_inout_id AND shipmentLine.edi_desadvline_id = dline.edi_desadvline_id
         LEFT JOIN EDI_DesadvLine_InOutLine desadvInOutLine ON shipmentLine.m_inoutline_id = desadvInOutLine.m_inoutline_id

WHERE (COALESCE(dline.QtyOrdered_Override, dline.QtyOrdered) > dline.qtydeliveredinstockinguom
    OR dline.edi_desadvline_id = shipmentLine.edi_desadvline_id)
  AND desadvInOutLine.edi_desadvline_id > 0
;

SELECT db_alter_view(
    'M_InOut_DesadvLine_V',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('M_InOut_DesadvLine_V$new'))
);

DROP VIEW IF EXISTS M_InOut_DesadvLine_V$new;


DROP VIEW IF EXISTS M_InOut_DesadvLine2_V$new;

CREATE OR REPLACE VIEW M_InOut_DesadvLine2_V$new AS
SELECT (shipment.m_inout_id::bigint * 10000000
            + (row_number() OVER (PARTITION BY shipment.m_inout_id ORDER BY desadv.edi_desadv_id) - 1)
       )::numeric                                                                                                                           AS M_InOut_Desadv_ID,

       (dline.edi_desadvline_id::bigint * 10000000
            + (row_number() OVER (PARTITION BY dline.edi_desadvline_id ORDER BY shipment.m_inout_id) - 1)
       )::numeric                                                                                                                           AS M_InOut_DesadvLine2_V_ID,
       shipment.m_inout_id,
       shipmentLine.m_inoutline_id,

       desadv.edi_desadv_id,
       desadv.AD_Client_ID,
       desadv.ad_org_id,
       COALESCE(desadvInOutLine.updated, dline.updated)                                                                                     AS updated,
       COALESCE(desadvInOutLine.updatedby, dline.updatedby)                                                                                 AS updatedby,
       COALESCE(desadvInOutLine.created, dline.created)                                                                                     AS created,
       COALESCE(desadvInOutLine.createdby, dline.createdby)                                                                                 AS createdby,

       COALESCE(desadvInOutLine.edi_desadvline_id, dline.edi_desadvline_id)                                                                 AS edi_desadvline_id,
       COALESCE(desadvInOutLine.M_Product_ID, dline.M_Product_ID)                                                                           AS M_Product_ID,

       COALESCE(desadvInOutLine.qtydeliveredinstockinguom, 0)                                                                               AS qtydeliveredinstockinguom,
       COALESCE(desadvInOutLine.C_UOM_ID, dline.C_UOM_ID)                                                                                   AS C_UOM_ID,
       COALESCE(desadvInOutLine.QtyDeliveredInUOM, 0)                                                                                       AS QtyDeliveredInUOM,
       COALESCE(desadvInOutLine.C_UOM_Invoice_ID, dline.C_UOM_Invoice_ID)                                                                   AS C_UOM_Invoice_ID,
       COALESCE(desadvInOutLine.QtyDeliveredInInvoiceUOM, 0)                                                                                AS QtyDeliveredInInvoiceUOM,
       COALESCE(desadvInOutLine.C_UOM_BPartner_ID, dline.C_UOM_BPartner_ID)                                                                 AS C_UOM_BPartner_ID,
       COALESCE(desadvInOutLine.QtyEnteredInBPartnerUOM, 0)                                                                                 AS QtyEnteredInBPartnerUOM,

       dline.isactive,
       dline.Line,
       dline.ProductDescription,
       dline.ProductNo,
       dline.QtyEntered,
       dline.IsSubsequentDeliveryPlanned,
       dline.EAN_CU,
       dline.EAN_TU,
       dline.GTIN_CU,
       dline.GTIN_TU,
       dline.UPC_TU,
       dline.upc_cu,
       dline.PriceActual,
       dline.QtyOrdered,
       dline.QtyOrdered_Override,
       (SELECT x12de355 FROM C_UOM WHERE C_UOM.C_UOM_ID = dline.C_UOM_Invoice_ID)                                                           AS EanCom_Invoice_UOM,
       dline.InvoicableQtyBasedOn,
       dline.OrderPOReference,
       dline.QtyItemCapacity,
       dline.OrderLine,
       dline.ExternalSeqNo,
       dline.BPartner_QtyItemCapacity,
       CASE WHEN desadvInOutLine.DesadvLineTotalQtyDelivered >= COALESCE(dline.QtyOrdered_Override, dline.QtyOrdered) THEN 'Y' ELSE 'N' END AS IsDeliveryClosed
FROM edi_desadv desadv
         INNER JOIN edi_desadvline dline ON desadv.edi_desadv_id = dline.edi_desadv_id
         INNER JOIN edi_desadv_m_inout link ON link.edi_desadv_id = desadv.edi_desadv_id AND link.isactive = 'Y'
         INNER JOIN m_inout shipment ON shipment.m_inout_id = link.m_inout_id
         LEFT JOIN m_inoutline shipmentLine ON shipmentLine.m_inout_id = shipment.m_inout_id AND shipmentLine.edi_desadvline_id = dline.edi_desadvline_id
         LEFT JOIN EDI_DesadvLine_InOutLine desadvInOutLine ON shipmentLine.m_inoutline_id = desadvInOutLine.m_inoutline_id

WHERE (COALESCE(dline.QtyOrdered_Override, dline.QtyOrdered) > dline.qtydeliveredinstockinguom
    OR dline.edi_desadvline_id = shipmentLine.edi_desadvline_id)
  AND desadvInOutLine.EDI_DesadvLine_InOutLine_id IS NULL
;

SELECT db_alter_view(
    'M_InOut_DesadvLine2_V',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('M_InOut_DesadvLine2_V$new'))
);

DROP VIEW IF EXISTS M_InOut_DesadvLine2_V$new;
