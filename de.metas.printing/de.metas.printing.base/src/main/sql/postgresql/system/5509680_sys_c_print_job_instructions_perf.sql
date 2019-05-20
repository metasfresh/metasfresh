															 
CREATE INDEX IF NOT EXISTS c_print_job_instructions_status_hostkey
    ON public.c_print_job_instructions USING btree
    (status, hostkey)
    TABLESPACE pg_default;
COMMENT ON INDEX public.c_print_job_instructions_status_hostkey
    IS 'this index supports the selection of pending print jobs';

