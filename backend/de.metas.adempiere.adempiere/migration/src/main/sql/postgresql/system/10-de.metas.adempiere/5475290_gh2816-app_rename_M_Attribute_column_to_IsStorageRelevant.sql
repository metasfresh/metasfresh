-- 2017-10-24T13:41:51.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsStorageRelevant', Description='Is used to do attibute matching between storage attributes and order line attributes (ASIs).', EntityType='D', Help='The flag indicates that an attribute is significant in the (HU) storage. This does not automatically imply that the attribute is actually used in any HU.
But if HU-Attributes are flagged with this, the HU-related quantities will be shown in the MRP produkt info (maybe elsewhere too, in future) with respect to those attributes'' values.', Name='Ist Bestandsrelevant', PrintName='Ist Bestandsrelevant',Updated=TO_TIMESTAMP('2017-10-24 13:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542475
;

-- 2017-10-24T13:41:51.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsStorageRelevant', Name='Ist Bestandsrelevant', Description='Is used to do attibute matching between storage attributes and order line attributes (ASIs).', Help='The flag indicates that an attribute is significant in the (HU) storage. This does not automatically imply that the attribute is actually used in any HU.
But if HU-Attributes are flagged with this, the HU-related quantities will be shown in the MRP produkt info (maybe elsewhere too, in future) with respect to those attributes'' values.' WHERE AD_Element_ID=542475
;

-- 2017-10-24T13:41:51.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsStorageRelevant', Name='Ist Bestandsrelevant', Description='Is used to do attibute matching between storage attributes and order line attributes (ASIs).', Help='The flag indicates that an attribute is significant in the (HU) storage. This does not automatically imply that the attribute is actually used in any HU.
But if HU-Attributes are flagged with this, the HU-related quantities will be shown in the MRP produkt info (maybe elsewhere too, in future) with respect to those attributes'' values.', AD_Element_ID=542475 WHERE UPPER(ColumnName)='ISSTORAGERELEVANT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-10-24T13:41:51.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsStorageRelevant', Name='Ist Bestandsrelevant', Description='Is used to do attibute matching between storage attributes and order line attributes (ASIs).', Help='The flag indicates that an attribute is significant in the (HU) storage. This does not automatically imply that the attribute is actually used in any HU.
But if HU-Attributes are flagged with this, the HU-related quantities will be shown in the MRP produkt info (maybe elsewhere too, in future) with respect to those attributes'' values.' WHERE AD_Element_ID=542475 AND IsCentrallyMaintained='Y'
;

-- 2017-10-24T13:41:51.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ist Bestandsrelevant', Description='Is used to do attibute matching between storage attributes and order line attributes (ASIs).', Help='The flag indicates that an attribute is significant in the (HU) storage. This does not automatically imply that the attribute is actually used in any HU.
But if HU-Attributes are flagged with this, the HU-related quantities will be shown in the MRP produkt info (maybe elsewhere too, in future) with respect to those attributes'' values.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542475) AND IsCentrallyMaintained='Y'
;

-- 2017-10-24T13:41:51.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ist Bestandsrelevant', Name='Ist Bestandsrelevant' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542475)
;

ALTER TABLE public.M_Attribute RENAME IsMatchHUStorage TO IsStorageRelevant;

-- 2017-10-24T13:45:10.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2017-10-24 13:45:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550884
;
