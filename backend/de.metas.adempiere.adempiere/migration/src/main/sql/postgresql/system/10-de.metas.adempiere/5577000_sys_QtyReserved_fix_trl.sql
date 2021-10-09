-- 2021-01-21T12:39:34.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Reservierte Menge', Help='', IsTranslated='Y', Name='Reservierte Menge', PO_Description='', PO_Help='', PO_Name='', PO_PrintName='', PrintName='Reservierte Menge',Updated=TO_TIMESTAMP('2021-01-21 14:39:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=532 AND AD_Language='de_DE'
;

-- 2021-01-21T12:39:34.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(532,'de_DE') 
;

-- 2021-01-21T12:39:34.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(532,'de_DE') 
;

-- 2021-01-21T12:39:34.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyReserved', Name='Reservierte Menge', Description='Reservierte Menge', Help='' WHERE AD_Element_ID=532
;

-- 2021-01-21T12:39:34.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyReserved', Name='Reservierte Menge', Description='Reservierte Menge', Help='', AD_Element_ID=532 WHERE UPPER(ColumnName)='QTYRESERVED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-01-21T12:39:34.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyReserved', Name='Reservierte Menge', Description='Reservierte Menge', Help='' WHERE AD_Element_ID=532 AND IsCentrallyMaintained='Y'
;

-- 2021-01-21T12:39:34.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reservierte Menge', Description='Reservierte Menge', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=532) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 532)
;

-- 2021-01-21T12:39:34.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Reservierte Menge', Name='Reservierte Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=532)
;

-- 2021-01-21T12:39:34.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reservierte Menge', Description='Reservierte Menge', Help='', CommitWarning = NULL WHERE AD_Element_ID = 532
;

-- 2021-01-21T12:39:34.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reservierte Menge', Description='Reservierte Menge', Help='' WHERE AD_Element_ID = 532
;

-- 2021-01-21T12:39:34.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reservierte Menge', Description = 'Reservierte Menge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 532
;

-- 2021-01-21T12:39:57.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Qty Reserved', PO_Description='', PO_Help='', PO_PrintName='', PrintName='Qty Reserved',Updated=TO_TIMESTAMP('2021-01-21 14:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=532 AND AD_Language='en_US'
;

-- 2021-01-21T12:39:57.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(532,'en_US') 
;





-- 2021-01-21T14:49:20.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.', PO_Description='Menge auf Bestellungen', PO_Help='Der "Bestellbestand" zeigt die Menge and, die zur Zeit bestellt ist.', PO_Name='Bestellbestand', PO_PrintName='Bestellbestand',Updated=TO_TIMESTAMP('2021-01-21 16:49:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=532 AND AD_Language='de_DE'
;

-- 2021-01-21T14:49:20.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(532,'de_DE') 
;

-- 2021-01-21T14:49:20.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(532,'de_DE') 
;

-- 2021-01-21T14:49:20.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyReserved', Name='Reservierte Menge', Description='Reservierte Menge', Help='Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.' WHERE AD_Element_ID=532
;

-- 2021-01-21T14:49:20.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyReserved', Name='Reservierte Menge', Description='Reservierte Menge', Help='Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.', AD_Element_ID=532 WHERE UPPER(ColumnName)='QTYRESERVED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-01-21T14:49:20.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyReserved', Name='Reservierte Menge', Description='Reservierte Menge', Help='Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.' WHERE AD_Element_ID=532 AND IsCentrallyMaintained='Y'
;

-- 2021-01-21T14:49:20.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reservierte Menge', Description='Reservierte Menge', Help='Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=532) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 532)
;

-- 2021-01-21T14:49:20.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reservierte Menge', Description='Reservierte Menge', Help='Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.', CommitWarning = NULL WHERE AD_Element_ID = 532
;

-- 2021-01-21T14:49:20.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reservierte Menge', Description='Reservierte Menge', Help='Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.' WHERE AD_Element_ID = 532
;

-- 2021-01-21T14:49:20.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reservierte Menge', Description = 'Reservierte Menge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 532
;

