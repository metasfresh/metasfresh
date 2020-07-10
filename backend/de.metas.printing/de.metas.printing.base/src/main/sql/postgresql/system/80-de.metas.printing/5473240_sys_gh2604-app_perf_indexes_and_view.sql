

CREATE INDEX IF NOT EXISTS c_print_job_line_c_printing_queue_id
  ON public.c_print_job_line
  USING btree
  (c_printing_queue_id);

CREATE INDEX IF NOT EXISTS c_print_packageinfo_c_print_package_id
  ON public.c_print_packageinfo
  USING btree
  (c_print_package_id);

CREATE INDEX IF NOT EXISTS c_print_package_c_print_job_instructions_id
  ON public.c_print_package
  USING btree
  (c_print_job_instructions_id);
  
CREATE INDEX IF NOT EXISTS c_print_job_instructions_c_print_job_id
  ON public.c_print_job_instructions
  USING btree
  (c_print_job_id);

CREATE INDEX IF NOT EXISTS c_printing_queue_ad_archive_id
  ON public.c_printing_queue
  USING btree
  (ad_archive_id);

CREATE INDEX IF NOT EXISTS c_printing_queue_recipient_c_printing_queue_id
  ON public.c_printing_queue_recipient
  USING btree
  (c_printing_queue_id);

CREATE INDEX IF NOT EXISTS c_print_job_line_c_printing_queue_id
  ON public.c_print_job_line
  USING btree
  (c_printing_queue_id);

CREATE INDEX IF NOT EXISTS c_print_job_line_c_print_package_id
  ON public.c_print_job_line
  USING btree
  (c_print_package_id);

CREATE INDEX IF NOT EXISTS c_print_job_detail_c_print_job_line_id
  ON public.c_print_job_detail
  USING btree
  (c_print_job_line_id);
  
CREATE INDEX IF NOT EXISTS c_printpackagedata_c_print_package_id
  ON public.c_printpackagedata
  USING btree
  (c_print_package_id);

DROP VIEW IF EXISTS "de.metas.printing".archived_to_printed_performance_v;
CREATE VIEW "de.metas.printing".archived_to_printed_performance_v AS
SELECT 
	a.Name AS ad_archive_name,
	(select TableName from AD_Table t where t.AD_Table_ID=a.AD_Table_ID) as ad_archive_TableName,
	ji.hostkey AS c_print_job_instructions_hostkey,
	ji.copies AS c_print_job_instructions_copies,
	CASE ji.status
            WHEN 'P' THEN 'pending'
            WHEN 'S' THEN 'send to printin client'
            WHEN 'D' THEN 'done'
            WHEN 'E' THEN 'error'
            ELSE NULL
        END AS c_print_job_instructions_current_status,
        hw.Name AS printer_name,
        log_done.created - a.created AS duration_archived_to_printed,
        ji.created - a.created AS duration_archived_to_pending,
        log_send.created - ji.created AS duration_pending_to_send,
        log_done.created - log_send.created AS duration_send_to_done,
        extract('EPOCH' from (log_send.created - ji.created)) / extract('EPOCH' from (log_done.created - a.created)) * 100 AS percent_pending_to_send,
	a.created AS ad_archive_created, 
	q.created AS c_printing_queue_created,
	jl.created AS c_print_job_line_created,
	j.created AS c_print_job_clreated,
	ji.created AS c_print_job_instructions_created,
        log_send.created AS send_timestamp,
        log_done.created AS done_timestamp
FROM AD_Archive a
	LEFT JOIN c_printing_queue q ON q.ad_archive_id = a.ad_archive_id
	LEFT JOIN c_print_job_line jl ON jl.c_printing_queue_id = q.c_printing_queue_id
	LEFT JOIN c_print_job j ON j.c_print_job_id = jl.c_print_job_id
	LEFT JOIN c_print_job_instructions ji ON ji.c_print_job_id = j.c_print_job_id
	LEFT JOIN c_print_package pp ON pp.c_print_job_instructions_id = ji.c_print_job_instructions_id
	LEFT JOIN c_print_packageinfo pi ON pi.c_print_package_id = pp.c_print_package_id
	LEFT JOIN ad_printerhw hw ON hw.ad_printerhw_id=pi.ad_printerhw_id
	LEFT JOIN AD_ChangeLog log_send 
		ON log_send.AD_Table_ID=get_table_id('c_print_job_instructions')
		AND log_send.AD_Column_ID=(select c. AD_Column_ID from AD_Column c where c.AD_Table_ID=get_table_id('c_print_job_instructions') and c.ColumnName='Status')
		AND log_send.Record_ID=ji.c_print_job_instructions_id
		AND log_send.NewValue='S'
	LEFT JOIN AD_ChangeLog log_done 
		ON log_done.AD_Table_ID=get_table_id('c_print_job_instructions')
		AND log_done.AD_Column_ID=(select c.AD_Column_ID from AD_Column c where c.AD_Table_ID=get_table_id('c_print_job_instructions') and c.ColumnName='Status')
		AND log_done.Record_ID=ji.c_print_job_instructions_id
		AND log_done.NewValue='D'
;

COMMENT ON VIEW "de.metas.printing".archived_to_printed_performance_v
IS 'This view shows creation time stamps and durations for different steps between the creation of an AD_Archive and it''s printout
See issue Printing via standalone client takes too long https://github.com/metasfresh/metasfresh/issues/2604

Usage example:
SELECT * FROM "de.metas.printing".archived_to_printed_performance_v
WHERE true
	AND c_print_job_line_created IS NOT NULL -- right now we care for the performance of things that were printed
--	AND ad_archive_created between ''2017-09-29 10:29'' and ''2017-09-29 10:46''
--	AND ad_archive_created between ''2017-09-29 11:29'' and ''2017-09-29 11:46''
	AND ad_archive_created between ''2017-09-29 10:29'' and ''2017-09-29 12:46''
--	AND printer_name=''my-printer''
ORDER BY 
	--duration_archived_to_printed
	--percent_pending_to_send
	ad_archive_created
';
