
DROP VIEW IF EXISTS "de.metas.monitoring".async_workpackage_old_records_v;
CREATE OR REPLACE VIEW "de.metas.monitoring".async_workpackage_old_records_v AS 
 SELECT date_part('days'::text, now() - min(c_queue_workpackage.updated)) AS workpackage_age_days
   FROM c_queue_workpackage
  WHERE c_queue_workpackage.processed = 'Y'::bpchar;

ALTER TABLE "de.metas.monitoring".async_workpackage_old_records_v
  OWNER TO metasfresh;
COMMENT ON VIEW "de.metas.monitoring".async_workpackage_old_records_v
  IS 'Returns the number of days since the oldest processed workpackage was updated.

If we run select * from dlm.archive_c_queue_data(3,30000) via cron job each night, then this number shall be less than or equal to four.';
