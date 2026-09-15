DROP INDEX IF EXISTS c_bpartner_stats_c_bpartner_id_unique
;

CREATE UNIQUE INDEX c_bpartner_stats_c_bpartner_id_unique ON c_bpartner_stats (c_bpartner_id)
;
