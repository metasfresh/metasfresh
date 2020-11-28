-- 2017-08-27T11:18:48.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-27 11:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546725
;

-- 2017-08-27T11:18:52.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-08-27 11:18:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546726
;

-- 2017-08-27T11:19:13.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-08-27 11:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546730
;

-- 2017-08-27T11:19:16.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-08-27 11:19:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546731
;

-- 2017-08-27T11:19:56.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-08-27 11:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546727
;

-- 2017-08-27T12:41:54.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.', Name='Vollqualifiziert', PrintName='Vollqualifiziert',Updated=TO_TIMESTAMP('2017-08-27 12:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=377
;

-- 2017-08-27T12:41:54.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsFullyQualified', Name='Vollqualifiziert', Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.' WHERE AD_Element_ID=377
;

-- 2017-08-27T12:41:54.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsFullyQualified', Name='Vollqualifiziert', Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.', AD_Element_ID=377 WHERE UPPER(ColumnName)='ISFULLYQUALIFIED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-27T12:41:54.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsFullyQualified', Name='Vollqualifiziert', Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.' WHERE AD_Element_ID=377 AND IsCentrallyMaintained='Y'
;

-- 2017-08-27T12:41:54.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vollqualifiziert', Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=377) AND IsCentrallyMaintained='Y'
;

-- 2017-08-27T12:41:54.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vollqualifiziert', Name='Vollqualifiziert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=377)
;

-- 2017-08-27T12:42:18.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Komplett', PrintName='Komplett',Updated=TO_TIMESTAMP('2017-08-27 12:42:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=377
;

-- 2017-08-27T12:42:18.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsFullyQualified', Name='Komplett', Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.' WHERE AD_Element_ID=377
;

-- 2017-08-27T12:42:18.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsFullyQualified', Name='Komplett', Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.', AD_Element_ID=377 WHERE UPPER(ColumnName)='ISFULLYQUALIFIED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-27T12:42:18.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsFullyQualified', Name='Komplett', Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.' WHERE AD_Element_ID=377 AND IsCentrallyMaintained='Y'
;

-- 2017-08-27T12:42:18.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Komplett', Description='All benötigten Elemente sind vorhanden', Help='Sagt aus ob elle benötigten Elemente zur Definition einer Kontenkombination erfasst wurden.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=377) AND IsCentrallyMaintained='Y'
;

-- 2017-08-27T12:42:18.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Komplett', Name='Komplett' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=377)
;

