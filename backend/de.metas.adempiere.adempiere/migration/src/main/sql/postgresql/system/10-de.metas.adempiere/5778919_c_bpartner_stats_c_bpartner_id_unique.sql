--cleanup if neccesary
select backup_table('c_bpartner_stats','_me03_26627');

CREATE TABLE fix.c_bpartner_stats_delete_duplicates_20260227 AS
SELECT s_older.c_bpartner_id,
       s_latest.c_bpartner_stats_id AS s_latest_c_bpartner_stats_id,
       s_older.c_bpartner_stats_id  AS s_older_c_bpartner_stats_id
FROM c_bpartner_stats s_latest
         JOIN c_bpartner_stats s_older ON s_older.c_bpartner_id = s_latest.c_bpartner_id AND s_latest.c_bpartner_stats_id > s_older.c_bpartner_stats_id
;

delete from c_bpartner_stats where c_bpartner_stats_id in (select s_older_c_bpartner_stats_id from fix.c_bpartner_stats_delete_duplicates_20260227);
