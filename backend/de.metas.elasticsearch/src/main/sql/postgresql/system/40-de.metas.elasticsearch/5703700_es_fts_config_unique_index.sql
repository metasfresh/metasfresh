DROP INDEX IF EXISTS es_fts_config_es_index_uq
;

CREATE UNIQUE INDEX es_fts_config_es_index_uq ON es_fts_config (es_index) WHERE isactive = 'Y'
;

