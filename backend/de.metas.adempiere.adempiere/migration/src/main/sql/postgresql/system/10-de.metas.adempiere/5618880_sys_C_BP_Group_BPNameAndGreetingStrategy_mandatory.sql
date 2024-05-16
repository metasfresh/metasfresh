-- 2021-12-15T08:22:21.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='XX', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-12-15 10:22:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574454
;

-- 2021-12-15T08:22:22.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bp_group','BPNameAndGreetingStrategy','VARCHAR(25)',null,'XX')
;

-- 2021-12-15T08:22:22.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE C_BP_Group SET BPNameAndGreetingStrategy='XX' WHERE BPNameAndGreetingStrategy IS NULL
-- ;

-- 2021-12-15T08:22:22.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bp_group','BPNameAndGreetingStrategy',null,'NOT NULL',null)
;

