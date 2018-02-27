-- 2017-11-27T15:01:00.201
-- de.metas.inoutcandidate.spi.impl.M_ReceiptSchedule_GeneratePlanningHUs_WorkpackageProcessor
-- assume 5478540_sys_gh3086_C_Queue_Block_C_Queue_WorkpackageProcessor_ID_drop_FK.sql was already executed
DELETE FROM C_Queue_PackageProcessor WHERE C_Queue_PackageProcessor_ID=540016
;

-- not running because might take way to long on systems where this workspace was executed for a long time.
-- SELECT "de.metas.async".DROP_C_Queue_PackageProcessor (540016);

