

-- 2018-03-09T13:45:52.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Kennzeichen MwST-Satz',Updated=TO_TIMESTAMP('2018-03-09 13:45:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543703
;

-- 2018-03-09T13:45:52.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsReduced', Name='Ermäßigt', Description='Kennzeichen MwST-Satz', Help='' WHERE AD_Element_ID=543703
;

-- 2018-03-09T13:45:52.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReduced', Name='Ermäßigt', Description='Kennzeichen MwST-Satz', Help='', AD_Element_ID=543703 WHERE UPPER(ColumnName)='ISREDUCED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-09T13:45:52.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReduced', Name='Ermäßigt', Description='Kennzeichen MwST-Satz', Help='' WHERE AD_Element_ID=543703 AND IsCentrallyMaintained='Y'
;

-- 2018-03-09T13:45:52.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ermäßigt', Description='Kennzeichen MwST-Satz', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543703) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543703)
;

-- 2018-03-09T13:46:30.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Ohne',Updated=TO_TIMESTAMP('2018-03-09 13:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543704
;

-- 2018-03-09T13:46:30.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ohne', Name='Ohne' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543704)
;

-- 2018-03-09T13:50:12.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Means no tax', Help='Means no tax',Updated=TO_TIMESTAMP('2018-03-09 13:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543704
;

-- 2018-03-09T13:50:12.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsWithout', Name='Ohne', Description='Means no tax', Help='Means no tax' WHERE AD_Element_ID=543704
;

-- 2018-03-09T13:50:12.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWithout', Name='Ohne', Description='Means no tax', Help='Means no tax', AD_Element_ID=543704 WHERE UPPER(ColumnName)='ISWITHOUT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-09T13:50:12.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsWithout', Name='Ohne', Description='Means no tax', Help='Means no tax' WHERE AD_Element_ID=543704 AND IsCentrallyMaintained='Y'
;

-- 2018-03-09T13:50:12.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ohne', Description='Means no tax', Help='Means no tax' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543704) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543704)
;

-- 2018-03-09T13:51:01.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='There is a discount tax', Help='There is a discount tax',Updated=TO_TIMESTAMP('2018-03-09 13:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543703
;

-- 2018-03-09T13:51:01.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsReduced', Name='Ermäßigt', Description='There is a discount tax', Help='There is a discount tax' WHERE AD_Element_ID=543703
;

-- 2018-03-09T13:51:01.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReduced', Name='Ermäßigt', Description='There is a discount tax', Help='There is a discount tax', AD_Element_ID=543703 WHERE UPPER(ColumnName)='ISREDUCED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-09T13:51:01.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReduced', Name='Ermäßigt', Description='There is a discount tax', Help='There is a discount tax' WHERE AD_Element_ID=543703 AND IsCentrallyMaintained='Y'
;

-- 2018-03-09T13:51:01.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ermäßigt', Description='There is a discount tax', Help='There is a discount tax' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543703) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543703)
;

