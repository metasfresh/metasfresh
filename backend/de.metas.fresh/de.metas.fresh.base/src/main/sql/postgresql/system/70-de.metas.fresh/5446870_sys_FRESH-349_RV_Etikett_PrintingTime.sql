
DROP VIEW IF EXISTS RV_Etikett_PrintingTime;

CREATE OR REPLACE VIEW RV_Etikett_PrintingTime

AS

SELECT 	
	api.created	AS starttime,
	
	a.created 	AS ArchiveCreated,
	
	extract(epoch FROM (a.created::timestamp
	with time zone - api.created::timestamp with time
	zone)) 			AS FromStartToArchive,

	pq.created 	AS PrintingQueueCreated,
	
	extract(epoch FROM (pq.created::timestamp
	with time zone - a.created::timestamp with time
	zone)) 		AS FromArchiveToPrintingQueue,

	
	pjl.created 	AS PrintJobLineCreated,
	
	extract(epoch FROM (pjl.created::timestamp
	with time zone - pq.created::timestamp with time
	zone)) 		AS FromPrintingQueueToPrintJobLine,
		
	
	pji.created 	AS PrintJobInstructionCreated,
	
	extract(epoch FROM (pji.Created::timestamp
	with time zone - pjl.created::timestamp with time
	zone)) 			AS FromPrintJobLineToPrintJobInstruction,
	
	pji.updated AS PrintJobInstructionUpdated,
	
	extract(epoch FROM (pji.Updated::timestamp
	with time zone - pji.created::timestamp with time
	zone)) 			AS FromPrintJobInstructionToEnd
	


FROM c_print_job_instructions pji
   JOIN c_print_job_line pjl ON pjl.c_print_job_id = pji.c_print_job_id
   JOIN c_printing_queue pq ON pq.c_printing_queue_id = pjl.c_printing_queue_id
   JOIN ad_archive a ON a.ad_archive_id = pq.ad_archive_id
		AND a.ad_table_id = get_table_id('M_HU')
		AND a.record_id = 0
   JOIN AD_PInstance api on a.AD_PInstance_ID = api.AD_PInstance_ID

;
