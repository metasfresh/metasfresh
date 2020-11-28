-- 2019-06-05T14:20:26.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=4000,Updated=TO_TIMESTAMP('2019-06-05 14:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568157
;

-- 2019-06-05T14:20:28.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_rejection_detail','Explanation','TEXT',null,null)
;

-- 2019-06-05T14:24:03.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=14,Updated=TO_TIMESTAMP('2019-06-05 14:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568156
;

-- 2019-06-05T14:24:09.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=4000,Updated=TO_TIMESTAMP('2019-06-05 14:24:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568156
;

alter table C_Invoice_Rejection_Detail alter column reason TYPE varchar(4000);

alter table C_Invoice_Rejection_Detail alter column explanation TYPE varchar(4000);
