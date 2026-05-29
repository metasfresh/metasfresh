DROP INDEX IF EXISTS t_query_selection_search; --t_query_selection (uuid, record_id)

CREATE INDEX IF NOT EXISTS t_query_selection_record_id_uuid ON t_query_selection (record_id, uuid)
;

CREATE INDEX IF NOT EXISTS t_query_selection_line_uuid ON t_query_selection (line, uuid)
;

