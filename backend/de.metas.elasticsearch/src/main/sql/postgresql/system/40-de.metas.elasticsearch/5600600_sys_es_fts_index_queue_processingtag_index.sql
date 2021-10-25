drop index if exists es_fts_index_queue_processingtag_idx;

create index es_fts_index_queue_processingtag_idx on es_fts_index_queue (processingtag);

