/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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
-- causes problems on rollouts where this script is missing and column (dline.GTIN is already changed to dline.GTIN_TU) already changed
-- is still created in 5733611_sys_gh_multiLevel_desadv.sql

-- drop view if exists M_InOut_DesadvLine_V
-- ;
--
-- create or replace view M_InOut_DesadvLine_V as
-- select shipment.m_inout_id                                                       as M_InOut_Desadv_ID,
--        case
--            when desadvInOutLine.edi_desadvline_id > 0 then
--                        desadvInOutLine.edi_desadvline_id || '-' || desadvInOutLine.m_inoutline_id
--                                                       else
--                    uuid_generate_v4() || '' -- using a random string for those desadv lines that are not in the current shipment, as we don't want any packs to match it
--        end                                                                        as M_InOut_DesadvLine_V_ID,
--
--        shipmentLine.m_inout_id,
--        shipmentLine.m_inoutline_id,
--
--        desadv.edi_desadv_id,
--        desadv.AD_Client_ID,
--        desadv.ad_org_id,
--        coalesce(desadvInOutLine.updated, dline.updated)                           as updated,
--        coalesce(desadvInOutLine.updatedby, dline.updatedby)                       as updatedby,
--        coalesce(desadvInOutLine.created, dline.created)                           as created,
--        coalesce(desadvInOutLine.createdby, dline.createdby)                       as createdby,
--
--        coalesce(desadvInOutLine.edi_desadvline_id, dline.edi_desadvline_id)       as edi_desadvline_id,
--        coalesce(desadvInOutLine.M_Product_ID, dline.M_Product_ID)                 as M_Product_ID,
--
--        coalesce(desadvInOutLine.qtydeliveredinstockinguom, 0)                     as qtydeliveredinstockinguom,
--        coalesce(desadvInOutLine.C_UOM_ID, dline.C_UOM_ID)                         as C_UOM_ID,
--        coalesce(desadvInOutLine.QtyDeliveredInUOM, 0)                             as QtyDeliveredInUOM,
--        coalesce(desadvInOutLine.C_UOM_Invoice_ID, dline.C_UOM_Invoice_ID)         as C_UOM_Invoice_ID,
--        coalesce(desadvInOutLine.QtyDeliveredInInvoiceUOM, 0)                      as QtyDeliveredInInvoiceUOM,
--        coalesce(desadvInOutLine.C_UOM_BPartner_ID, dline.C_UOM_BPartner_ID)       as C_UOM_BPartner_ID,
--        coalesce(desadvInOutLine.QtyEnteredInBPartnerUOM, 0)                       as QtyEnteredInBPartnerUOM,
--
--        dline.isactive,
--        dline.Line,
--        dline.ProductDescription,
--        dline.ProductNo,
--        dline.QtyEntered,
--        dline.IsSubsequentDeliveryPlanned,
--        dline.EAN_CU,
--        dline.EAN_TU,
--        dline.GTIN,
--        dline.UPC_TU,
--        dline.upc_cu,
--        dline.PriceActual,
--        dline.QtyOrdered,
--        dline.QtyOrdered_Override,
--        (select x12de355 from C_UOM where C_UOM.C_UOM_ID = dline.C_UOM_Invoice_ID) as EanCom_Invoice_UOM,
--        dline.InvoicableQtyBasedOn,
--        dline.OrderPOReference,
--        dline.QtyItemCapacity,
--        dline.OrderLine,
--        dline.ExternalSeqNo,
--        dline.BPartner_QtyItemCapacity,
--        case when desadvInOutLine.DesadvLineTotalQtyDelivered >= coalesce(dline.QtyOrdered_Override, dline.QtyOrdered) then 'Y' else 'N' end as IsDeliveryClosed
--
-- from edi_desadv desadv
--          inner join edi_desadvline dline on desadv.edi_desadv_id = dline.edi_desadv_id
--          left join m_inout shipment on desadv.edi_desadv_id = shipment.edi_desadv_id
--          left join m_inoutline shipmentLine on shipmentLine.m_inout_id = shipment.m_inout_id
--     and shipmentLine.edi_desadvline_id = dline.edi_desadvline_id
--          left join EDI_DesadvLine_InOutLine desadvInOutLine on shipmentLine.m_inoutline_id = desadvInOutLine.m_inoutline_id
-- where coalesce(dline.QtyOrdered_Override, dline.QtyOrdered) > dline.qtydeliveredinstockinguom or dline.edi_desadvline_id = shipmentLine.edi_desadvline_id
-- ;
