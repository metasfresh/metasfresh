drop view if exists M_InOut_Desadv_V
;

create or replace view M_InOut_Desadv_V as
select shipment.m_inout_id as M_InOut_Desadv_ID,
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
         inner join edi_desadv desadv on shipment.edi_desadv_id = desadv.edi_desadv_id;
