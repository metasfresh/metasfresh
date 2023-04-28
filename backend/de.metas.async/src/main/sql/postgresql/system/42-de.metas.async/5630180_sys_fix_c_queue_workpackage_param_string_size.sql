
-- 2022-03-15T15:48:01.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=-1593835520, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-03-15 16:48:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551984
;

-- 2022-03-15T15:48:04.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_queue_workpackage_param','P_String','TEXT',null,null)
;

