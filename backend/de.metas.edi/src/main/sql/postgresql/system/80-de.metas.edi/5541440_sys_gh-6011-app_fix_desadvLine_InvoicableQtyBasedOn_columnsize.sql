-- 2020-01-14T11:11:05.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=11,Updated=TO_TIMESTAMP('2020-01-14 12:11:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569824
;

-- 2020-01-14T11:11:06.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline','InvoicableQtyBasedOn','VARCHAR(11)',null,null)
;

COMMIT;
UPDATE edi_desadvline SET InvoicableQtyBasedOn='CatchWeight' WHERE InvoicableQtyBasedOn='CatchWeigh';
