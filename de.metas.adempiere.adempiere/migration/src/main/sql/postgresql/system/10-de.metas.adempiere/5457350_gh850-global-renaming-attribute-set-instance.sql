-- 28.02.2017 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Merkmals Ausprägungen zum Produkt', Name='Merkmale', PrintName='Merkmale',Updated=TO_TIMESTAMP('2017-02-28 18:18:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2019
;

-- 28.02.2017 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2019
;

-- 28.02.2017 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_AttributeSetInstance_ID', Name='Merkmale', Description='Merkmals Ausprägungen zum Produkt', Help='The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.' WHERE AD_Element_ID=2019
;

-- 28.02.2017 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_AttributeSetInstance_ID', Name='Merkmale', Description='Merkmals Ausprägungen zum Produkt', Help='The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.', AD_Element_ID=2019 WHERE UPPER(ColumnName)='M_ATTRIBUTESETINSTANCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 28.02.2017 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_AttributeSetInstance_ID', Name='Merkmale', Description='Merkmals Ausprägungen zum Produkt', Help='The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.' WHERE AD_Element_ID=2019 AND IsCentrallyMaintained='Y'
;

-- 28.02.2017 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Merkmale', Description='Merkmals Ausprägungen zum Produkt', Help='The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2019) AND IsCentrallyMaintained='Y'
;

-- 28.02.2017 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Merkmale', Name='Merkmale' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2019)
;

-- 28.02.2017 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-02-28 18:18:45','YYYY-MM-DD HH24:MI:SS'),Name='Attributes',PrintName='Attributes',Description='Attribute Instances for Products' WHERE AD_Element_ID=2019 AND AD_Language='en_US'
;

