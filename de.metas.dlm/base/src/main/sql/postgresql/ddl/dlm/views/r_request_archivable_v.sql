CREATE OR REPLACE VIEW dlm.R_Request_Archivable_v
AS
select 
	r.R_Request_ID, 
	r.Updated AS ArchivableSince,
	rt.name, 
	rt.isuseforpartnerrequestwindow, 
	rs.IsClosed,  
	r.created, 
	r.updated,
	o.C_Order_ID
from r_request r
	join r_requesttype rt on rt.r_requesttype_id=r.r_requesttype_id
	join r_status rs on rs.r_status_id = r.r_status_id 
	join C_Order o on o.C_Order_ID=r.C_Order_ID
where rt.name='eMail'
	AND o.QtyOrdered = o.QtyInvoiced AND o.QtyOrdered = o.QtyInvoiced
;
COMMENT ON VIEW dlm.R_Request_Archivable_v IS
'This view is used by the DB-function dlm.archive_r_request_data, to identify the archivable R_Request records.
See issue https://github.com/metasfresh/metasfresh/issues/5189';
