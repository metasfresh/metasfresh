

-- 2021-11-25T15:12:29.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=1874919423, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-11-25 16:12:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549003
;

-- 2021-11-25T15:12:30.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_queue_workpackage','Skipped_Last_Reason','TEXT',null,null)
;

