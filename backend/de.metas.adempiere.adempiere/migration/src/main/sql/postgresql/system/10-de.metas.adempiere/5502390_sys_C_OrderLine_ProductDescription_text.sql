
--
-- it was set to text, but the physoical column was still restricted to 255 chars
-- 2018-10-02T07:07:28.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=999999999,Updated=TO_TIMESTAMP('2018-10-02 07:07:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=504491
;

-- 2018-10-02T07:07:32.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_orderline','ProductDescription','TEXT',null,null)
;

