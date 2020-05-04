
create TEMP table TEMP_M_ShipmentSchedule_Ids_User0 as select m_shipmentschedule_ID from m_shipmentschedule where processed = 'N' and ad_user_id = 0;

update m_shipmentschedule set ad_user_id = null where m_shipmentschedule_ID in (select m_shipmentschedule_id from TEMP_M_ShipmentSchedule_Ids_User0);


insert into m_shipmentschedule_recompute (M_ShipmentSchedule_ID) select * from TEMP_M_ShipmentSchedule_Ids_User0;












with wps as
         (
             select wp.c_queue_workpackage_id, qp.classname
             from c_queue_workpackage wp
                      join c_queue_block qb on wp.c_queue_block_id = qb.c_queue_block_id
                      join c_queue_packageprocessor qp
                           on qb.c_queue_packageprocessor_id = qp.c_queue_packageprocessor_id and qp.classname =
                                                                                                  'de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor'
             order by wp.created desc
             limit 1
         )
update c_queue_workpackage set processed = 'N'
from wps where wps.c_queue_workpackage_id = c_queue_workpackage.c_queue_workpackage_id;
























create TEMP table TEMP_C_Invoice_Candidate_Ids_User0 as select C_Invoice_Candidate_ID from C_Invoice_Candidate where processed = 'N' and bill_user_id = 0;

update C_Invoice_Candidate set bill_user_id = null where C_Invoice_Candidate_ID in (select C_Invoice_Candidate_ID from TEMP_C_Invoice_Candidate_Ids_User0);


insert into c_invoice_candidate_recompute (C_Invoice_Candidate_ID) select * from TEMP_C_Invoice_Candidate_Ids_User0;









with wps as
         (
             select wp.c_queue_workpackage_id, qp.classname
             from c_queue_workpackage wp
                      join c_queue_block qb on wp.c_queue_block_id = qb.c_queue_block_id
                      join c_queue_packageprocessor qp
                           on qb.c_queue_packageprocessor_id = qp.c_queue_packageprocessor_id and qp.classname ='de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor'

 order by wp.created desc
             limit 1
         )
update c_queue_workpackage set processed = 'N'
from wps where wps.c_queue_workpackage_id = c_queue_workpackage.c_queue_workpackage_id;










