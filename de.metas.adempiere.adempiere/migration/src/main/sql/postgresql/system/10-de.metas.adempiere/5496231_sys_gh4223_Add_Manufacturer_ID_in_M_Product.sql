
-- 2018-06-20T16:46:25.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544128, AD_Reference_ID=18, AD_Val_Rule_ID=540401, ColumnName='Manufacturer_ID', Description='Hersteller des Produktes', FieldLength=10, Help='', Name='Hersteller',Updated=TO_TIMESTAMP('2018-06-20 16:46:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558031
;

-- 2018-06-20T16:46:25.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Hersteller', Description='Hersteller des Produktes', Help='' WHERE AD_Column_ID=558031
;

-- 2018-06-20T16:46:30.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN Manufacturer_ID NUMERIC(10)')
;

-- 2018-06-20T16:46:31.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product ADD CONSTRAINT Manufacturer_MProduct FOREIGN KEY (Manufacturer_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

