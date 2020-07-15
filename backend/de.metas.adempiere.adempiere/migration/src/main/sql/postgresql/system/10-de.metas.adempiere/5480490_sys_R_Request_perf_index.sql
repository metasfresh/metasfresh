
CREATE INDEX IF NOT EXISTS r_request_record_id
  ON public.r_request
  USING btree
  (record_id);

COMMENT ON INDEX public.r_request_record_id
  IS 'We added after we got a warning about a stale query. Already heavily used.';
