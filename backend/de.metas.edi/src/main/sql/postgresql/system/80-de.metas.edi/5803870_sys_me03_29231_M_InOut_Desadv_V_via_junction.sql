-- me03 #29231: rewrite M_InOut_Desadv_V (Replication-Interface RPL export path) to enumerate rows
-- via the EDI_Desadv_M_InOut junction instead of joining on the single-FK M_InOut.EDI_Desadv_ID.
-- Fixes Broken-Path #5: for consolidated multi-source-order shipments the legacy single-FK join
-- emitted exactly one row, causing the RPL path to drop N-1 EDIFACT XMLs.
--
-- PK M_InOut_Desadv_ID is now a synthetic composite (m_inout_id * 10000000 + ordinal) so it stays
-- unique even when one shipment has N>=2 DESADVs. The PK is opaque (Record_ID for EXP_Format-driven
-- exports); no Java consumer assumes PK == M_InOut_ID.

DROP VIEW IF EXISTS M_InOut_Desadv_V$new;

CREATE OR REPLACE VIEW M_InOut_Desadv_V$new AS
select (shipment.m_inout_id::bigint * 10000000
            + (row_number() OVER (PARTITION BY shipment.m_inout_id ORDER BY desadv.edi_desadv_id) - 1)
       )::numeric                                                                                                          as M_InOut_Desadv_ID,
       shipment.m_inout_id,
       shipment.documentno as documentNo,
       desadv.EDI_Desadv_ID,
       desadv.AD_Client_ID,
       desadv.ad_org_id,
       desadv.c_bpartner_id,
       desadv.C_BPartner_Location_ID,
       desadv.Created,
       desadv.CreatedBy,
       desadv.DateOrdered,
       desadv.EDIErrorMsg,
       desadv.EDI_ExportStatus,
       desadv.IsActive,
       desadv.MovementDate,
       desadv.POReference,
       desadv.Processed,
       desadv.Updated,
       desadv.UpdatedBy,
       desadv.Bill_Location_ID,
       desadv.C_Currency_ID,
       desadv.HandOver_Location_ID,
       desadv.Processing,
       desadv.DropShip_BPartner_ID,
       desadv.DropShip_Location_ID,
       desadv.FulfillmentPercent,
       desadv.FulfillmentPercentMin,
       desadv.HandOver_Partner_ID,
       desadv.SumDeliveredInStockingUOM,
       desadv.SumOrderedInStockingUOM,
       desadv.UserFlag,
       desadv.DeliveryViaRule,
       (select CASE WHEN array_length(array_agg(DISTINCT l.invoicableqtybasedon), 1) = 1 THEN (array_agg(DISTINCT l.invoicableqtybasedon))[1] ELSE NULL END from edi_desadvline l where l.edi_desadv_id = desadv.edi_desadv_id) as InvoicableQtyBasedOn
from m_inout shipment
         inner join edi_desadv_m_inout link
                    on link.m_inout_id = shipment.m_inout_id
                        and link.isactive = 'Y'
         inner join edi_desadv desadv
                    on desadv.edi_desadv_id = link.edi_desadv_id
;

SELECT db_alter_view(
    'M_InOut_Desadv_V',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('M_InOut_Desadv_V$new'))
);

DROP VIEW IF EXISTS M_InOut_Desadv_V$new;
