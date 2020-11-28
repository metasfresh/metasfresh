
DROP VIEW IF EXISTS dlm.R_Request_Archivable_v;
CREATE VIEW dlm.R_Request_Archivable_v
AS
select 
	GREATEST(r.Updated, COALESCE(r_related.Updated, r.Updated)) AS ArchivableSince,
	r.R_Request_ID,
	r.Updated AS r_Updated, 
	rt.name, 
	rt.isuseforpartnerrequestwindow, 
	rs.IsClosed,  
	r.created AS r_created, 

	r_related.R_Request_ID AS r_related_R_Request_ID,
	r_related.created AS r_related_created, 
	r_related.Updated AS r_related_Updated,
	o.C_Order_ID
from r_request r
	join r_requesttype rt on rt.r_requesttype_id=r.r_requesttype_id
	join r_status rs on rs.r_status_id = r.r_status_id 
	join C_Order o on o.C_Order_ID=r.C_Order_ID
	LEFT join r_request r_related ON r_related.r_requestrelated_id = r.r_request_id
where rt.name='eMail'
	AND o.QtyOrdered = o.QtyInvoiced AND o.QtyOrdered = o.QtyInvoiced
;
COMMENT ON VIEW dlm.R_Request_Archivable_v IS
'This view is used by the DB-function dlm.archive_r_request_data, to identify the archivable R_Request records.
See issue https://github.com/metasfresh/metasfresh/issues/5189';
