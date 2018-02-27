
CREATE OR REPLACE VIEW "de.metas.monitoring".Async_Unprocessed_Workpackage_Sum_v AS
SELECT COALESCE(SUM(Count),0)
FROM "de.metas.async".C_Queue_Unprocessed_WorkPackage_Counts_v
WHERE ClassName != 'de.metas.document.archive.async.spi.impl.ProcessPrintingQueueWorkpackageProcessor';

COMMENT ON VIEW "de.metas.monitoring".Async_Unprocessed_Workpackage_Sum_v IS
'Please make sure to remove the "className != .." condition as soon as https://github.com/metasfresh/metasfresh/issues/3170 is done';
