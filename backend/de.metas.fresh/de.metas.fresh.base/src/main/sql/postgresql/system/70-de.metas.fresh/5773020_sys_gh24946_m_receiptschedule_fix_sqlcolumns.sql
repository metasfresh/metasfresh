/*
 * #%L
 * de.metas.fresh.base
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


UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'ATA', columnsql = '(SELECT max(st.ATA) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where ad_column_id = 591260;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'ATD', columnsql = '(SELECT max(st.ATD) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where ad_column_id = 591261;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'BLDate', columnsql = '(SELECT max(st.BLDate) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where ad_column_id = 591262;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'CalendarWeek', columnsql = '(SELECT EXTRACT(WEEK from COALESCE(M_ReceiptSchedule.DatePromised_Override, M_ReceiptSchedule.MovementDate)))' where ad_column_id = 590640;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'ContainerNo', columnsql = '(SELECT max(st.ContainerNo) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where ad_column_id = 591263;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'CRD', columnsql = '(SELECT max(st.CRD) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591264;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'DatePromised_Effective', columnsql = 'COALESCE(M_ReceiptSchedule.DatePromised_Override, M_ReceiptSchedule.MovementDate)' where  ad_column_id = 579327;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'ETA', columnsql = '(SELECT max(st.ETA) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591265;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'ETD', columnsql = '(SELECT max(st.ETD) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591259;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'FilteredItemsWithSameC_Order_ID', columnsql = '(select count(1) from M_ReceiptSchedule z where z.C_Order_ID=M_ReceiptSchedule.C_Order_ID)' where  ad_column_id = 571516;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'IsBLReceived', columnsql = '(SELECT max(st.IsBLReceived) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591266;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'IsBookingConfirmed', columnsql = '(SELECT max(st.IsBookingConfirmed) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591267;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'IsWENotice', columnsql = '(SELECT max(st.IsWENotice) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591268;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'MaterialReceiptInfo', columnsql = '(SELECT MaterialReceiptInfo from C_BPartner where C_BPartner_ID=M_ReceiptSchedule.C_BPartner_ID)' where  ad_column_id = 589629;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'M_Warehouse_Effective_ID', columnsql = 'COALESCE(M_Warehouse_Override_ID, M_Warehouse_ID)' where  ad_column_id = 570612;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'POD_ID', columnsql = '(SELECT max(st.POD_ID) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591269;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'POL_ID', columnsql = '(SELECT max(st.POL_ID) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591270;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'QtyMovedTU', columnsql = '(select COALESCE(SUM(iol.QtyEnteredTU),0) from M_ReceiptSchedule_Alloc rsa inner join M_InOutLine iol on (iol.M_InOutLine_ID=rsa.M_InOutLine_ID) inner join M_InOut io on (io.M_InOut_ID=iol.M_InOut_ID) where rsa.M_ReceiptSchedule_ID=M_ReceiptSchedule.M_ReceiptSchedule_ID and rsa.IsActive=''Y'' and iol.Processed=''Y'' and io.DocStatus IN (''CO'', ''CL''))' where  ad_column_id = 551296;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'QtyOrderedTU', columnsql = e'(
select COALESCE(SUM(ol.QtyEnteredTU),0) 
from C_OrderLine ol
inner join C_Order o on (o.C_Order_ID=ol.C_Order_ID) 
where ol.C_OrderLine_id = M_ReceiptSchedule.C_OrderLine_ID
and ol.Processed=\'Y\' 
and o.DocStatus IN (\'CO\', \'CL\')
)'  where  ad_column_id = 556335;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'Shipper_BPartner_ID', columnsql = '(SELECT max(st.Shipper_BPartner_ID) from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591271;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'TrackingID', columnsql = '(SELECT max(st.TrackingID)  from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591272;
UPDATE ad_column SET UpdatedBy=100, Updated='2025-10-09 13:48', columnname = 'VesselName', columnsql = '(SELECT max(st.VesselName)  from m_shippertransportation st INNER JOIN M_ShippingPackage sp on st.m_shippertransportation_id = sp.m_shippertransportation_id INNER JOIN M_ReceiptSchedule r on r.c_order_id = sp.c_order_id where r.m_receiptschedule_id = M_ReceiptSchedule.M_ReceiptSchedule_ID)' where  ad_column_id = 591273;
