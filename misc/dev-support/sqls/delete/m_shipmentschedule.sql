update C_Order set docstatus='CL', docaction='--' where issotrx='Y' and docstatus<>'CL';
delete from m_hu_trace where m_shipmentschedule_id is not null;
delete from m_picking_candidate_issuetoorder where 1=1;
delete from m_picking_candidate where 1=1;
delete from m_shipmentschedule_qtypicked where  1=1;
delete from m_shipmentschedule where 1=1;

