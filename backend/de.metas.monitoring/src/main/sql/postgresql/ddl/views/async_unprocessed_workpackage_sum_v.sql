
DROP VIEW IF EXISTS "de.metas.monitoring".async_unprocessed_workpackage_sum_v;
CREATE OR REPLACE VIEW "de.metas.monitoring".async_unprocessed_workpackage_sum_v AS 
 SELECT COALESCE(sum(c_queue_unprocessed_workpackage_counts_v.count), 0::numeric) AS "coalesce"
   FROM "de.metas.async".c_queue_unprocessed_workpackage_counts_v
  WHERE c_queue_unprocessed_workpackage_counts_v.classname::text <> 'de.metas.document.archive.async.spi.impl.ProcessPrintingQueueWorkpackageProcessor'::text;
