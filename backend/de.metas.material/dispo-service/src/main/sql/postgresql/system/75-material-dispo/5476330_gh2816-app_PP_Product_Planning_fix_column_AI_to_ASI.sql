-- 2017-11-04T15:23:29.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=2019, AD_Reference_ID=35, ColumnName='M_AttributeSetInstance_ID', Description='Merkmals Ausprägungen zum Produkt', Help='The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.', Name='Merkmale',Updated=TO_TIMESTAMP('2017-11-04 15:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557797
;

-- 2017-11-04T15:23:29.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Merkmale', Description='Merkmals Ausprägungen zum Produkt', Help='The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.' WHERE AD_Column_ID=557797 AND IsCentrallyMaintained='Y'
;

-- 2017-11-04T15:23:37.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN M_AttributeSetInstance_ID NUMERIC(10)')
;

-- 2017-11-04T15:23:37.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Product_Planning ADD CONSTRAINT MAttributeSetInstance_PPProduc FOREIGN KEY (M_AttributeSetInstance_ID) REFERENCES public.M_AttributeSetInstance DEFERRABLE INITIALLY DEFERRED
;

/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning DROP COLUMN IF EXISTS M_AttributeInstance_ID')
;
