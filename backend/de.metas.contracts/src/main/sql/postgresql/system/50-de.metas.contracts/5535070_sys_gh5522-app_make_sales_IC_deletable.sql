-- 2019-10-23T12:56:01.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='Can be null if this instance''s sales invoice candidate was deleted.',Updated=TO_TIMESTAMP('2019-10-23 14:56:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568754
;

-- 2019-10-23T12:56:04.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','C_Invoice_Candidate_ID','NUMERIC(10)',null,null)
;

-- 2019-10-23T12:56:04.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','C_Invoice_Candidate_ID',null,'NULL',null)
;

