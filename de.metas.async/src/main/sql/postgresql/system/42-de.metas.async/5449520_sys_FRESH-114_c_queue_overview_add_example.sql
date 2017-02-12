COMMENT ON VIEW "de.metas.async".c_queue_overview_v
  IS 'Usage example(s):
Select all invoices that were enqueue for EDI-Sending, with their documentNo and current EDI-Status:

select v.*, i.DocumentNo, i.EDI_EXportStatus
from "de.metas.async".c_queue_overview_v v
	join C_Invoice i ON i.C_Invoice_ID=v.qe_record_ID
where true 
	AND v.classname ilike ''%EDIWorkpackageProcessor%''
	AND v.qe_table_name=''C_Invoice''
	AND v.qwp_Created>=''2016-08-25 18:00''
	AND v.qwp_Created<=''2016-08-26''
order by qwp_created;
';
