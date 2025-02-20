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

DROP VIEW IF EXISTS M_InOut_DesadvLine_V
;

CREATE OR REPLACE VIEW M_InOut_DesadvLine_V AS
SELECT shipment.m_inout_id                                                                                                                  AS M_InOut_Desadv_ID,

       CASE WHEN desadvInOutLine.edi_desadvline_id > 0 THEN 'Y' ELSE 'N' END                                                                AS IsDesadvLineInCurrentShipment,

       /*
         If there is no m_inoutline_id, it means that we want the row in Exp_Format EDI_Exp_DesadvLineWithNoPack_1PerInOut_ActualDesadvLine (EXP_Format_ID=540433),
         and in that case, the lookup is taking place via EDI_DesadvLine_ID
        */
       COALESCE(shipmentLine.m_inoutline_id, dline.edi_desadvline_id)                                                                       AS M_InOut_DesadvLine_V_ID,
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
         LEFT JOIN m_inout shipment ON desadv.edi_desadv_id = shipment.edi_desadv_id
         LEFT JOIN m_inoutline shipmentLine ON shipmentLine.m_inout_id = shipment.m_inout_id
    AND shipmentLine.edi_desadvline_id = dline.edi_desadvline_id
         LEFT JOIN EDI_DesadvLine_InOutLine desadvInOutLine ON shipmentLine.m_inoutline_id = desadvInOutLine.m_inoutline_id

WHERE COALESCE(dline.QtyOrdered_Override, dline.QtyOrdered) > dline.qtydeliveredinstockinguom
   OR dline.edi_desadvline_id = shipmentLine.edi_desadvline_id
;
