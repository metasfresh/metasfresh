DROP TABLE IF EXISTS t_es_fts_search_result
;

CREATE TABLE t_es_fts_search_result
(
    search_uuid varchar(60)                            NOT NULL,
    line        numeric(10)                            NOT NULL,
    created     timestamp WITH TIME ZONE DEFAULT NOW() NOT NULL,
    intkey1     numeric(10),
    intkey2     numeric(10),
    intkey3     numeric(10),
    json        text
)
;

