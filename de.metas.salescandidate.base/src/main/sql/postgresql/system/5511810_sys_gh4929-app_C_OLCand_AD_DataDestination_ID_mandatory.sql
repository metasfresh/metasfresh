
-- AD_DataDestination_ID=540003 is "DEST.de.metas.ordercandidate"
-- 2019-02-04T17:26:42.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCand SET AD_DataDestination_ID=540003 WHERE AD_DataDestination_ID IS NULL
;

COMMIT;

-- 2019-02-04T17:26:42.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_olcand','AD_DataDestination_ID',null,'NOT NULL',null)
;
