-- 2017-11-03T15:37:48.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Element_ID=543469, Help=NULL, ColumnName='C_Order_CompensationGroup_ID', Description=NULL, Name='Order Compensation Group',Updated=TO_TIMESTAMP('2017-11-03 15:37:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557794
;

-- 2017-11-03T15:37:48.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Order Compensation Group', Description=NULL, Help=NULL WHERE AD_Column_ID=557794 AND IsCentrallyMaintained='Y'
;

-- 2017-11-03T15:37:55.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN C_Order_CompensationGroup_ID NUMERIC(10)')
;

-- 2017-11-03T15:37:56.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT COrderCompensationGroup_CInvoi FOREIGN KEY (C_Order_CompensationGroup_ID) REFERENCES public.C_Order_CompensationGroup DEFERRABLE INITIALLY DEFERRED
;

