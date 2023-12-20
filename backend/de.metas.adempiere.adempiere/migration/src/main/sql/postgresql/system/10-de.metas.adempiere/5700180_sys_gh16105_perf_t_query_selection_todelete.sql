CREATE INDEX IF NOT EXISTS T_Query_Selection_ToDelete_UUID_wa_ExecutorUUID ON T_Query_Selection_ToDelete (UUID) WHERE Executor_UUID IS NULL
;
COMMENT ON INDEX T_Query_Selection_ToDelete_UUID_wa_ExecutorUUID is 'Without this index the handling of T_Query_Selection_ToDelete can get out of hand when this table grows too big; issue https://github.com/metasfresh/metasfresh/issues/16105';
