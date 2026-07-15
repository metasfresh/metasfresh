-- 2018-06-15T07:06:36.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=2019, ColumnName='M_AttributeSetInstance_ID', Description='Merkmals Ausprägungen zum Produkt', Help='The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.', Name='Merkmale',Updated=TO_TIMESTAMP('2018-06-15 07:06:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560257
;

-- 2018-06-15T07:06:36.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Merkmale', Description='Merkmals Ausprägungen zum Produkt', Help='The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.' WHERE AD_Column_ID=560257
;

SELECT db_alter_table('C_PurchaseCandidate','ALTER TABLE C_PurchaseCandidate RENAME COLUMN M_AttributeInstance_ID TO M_AttributeSetInstance_ID;');
