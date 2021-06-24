-- 2021-03-26T09:50:13.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Ausgelieferte HUs aus Altanwendung',Updated=TO_TIMESTAMP('2021-03-26 11:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578863
;

-- 2021-03-26T09:50:13.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Ausgelieferte HUs aus Altanwendung', Name='Old Shipped HU', Description=NULL, Help=NULL WHERE AD_Element_ID=578863
;

-- 2021-03-26T09:50:13.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ausgelieferte HUs aus Altanwendung', Name='Old Shipped HU', Description=NULL, Help=NULL, AD_Element_ID=578863 WHERE UPPER(ColumnName)='AUSGELIEFERTE HUS AUS ALTANWENDUNG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-26T09:50:13.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Ausgelieferte HUs aus Altanwendung', Name='Old Shipped HU', Description=NULL, Help=NULL WHERE AD_Element_ID=578863 AND IsCentrallyMaintained='Y'
;

-- 2021-03-26T09:50:31.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ServiceRepair_Old_Shipped_HU_ID',Updated=TO_TIMESTAMP('2021-03-26 11:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578863
;

-- 2021-03-26T09:50:31.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceRepair_Old_Shipped_HU_ID', Name='Old Shipped HU', Description=NULL, Help=NULL WHERE AD_Element_ID=578863
;

-- 2021-03-26T09:50:31.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceRepair_Old_Shipped_HU_ID', Name='Old Shipped HU', Description=NULL, Help=NULL, AD_Element_ID=578863 WHERE UPPER(ColumnName)='SERVICEREPAIR_OLD_SHIPPED_HU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-26T09:50:31.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceRepair_Old_Shipped_HU_ID', Name='Old Shipped HU', Description=NULL, Help=NULL WHERE AD_Element_ID=578863 AND IsCentrallyMaintained='Y'
;

-- 2021-03-26T09:50:59.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ausgelieferte HUs aus Altanwendung', PrintName='Ausgelieferte HUs aus Altanwendung',Updated=TO_TIMESTAMP('2021-03-26 11:50:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578863 AND AD_Language='de_CH'
;

-- 2021-03-26T09:50:59.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578863,'de_CH') 
;

-- 2021-03-26T09:51:10.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ausgelieferte HUs aus Altanwendung', PrintName='Ausgelieferte HUs aus Altanwendung',Updated=TO_TIMESTAMP('2021-03-26 11:51:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578863 AND AD_Language='de_DE'
;

-- 2021-03-26T09:51:10.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578863,'de_DE') 
;

-- 2021-03-26T09:51:10.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578863,'de_DE') 
;

-- 2021-03-26T09:51:10.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ServiceRepair_Old_Shipped_HU_ID', Name='Ausgelieferte HUs aus Altanwendung', Description=NULL, Help=NULL WHERE AD_Element_ID=578863
;

-- 2021-03-26T09:51:10.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceRepair_Old_Shipped_HU_ID', Name='Ausgelieferte HUs aus Altanwendung', Description=NULL, Help=NULL, AD_Element_ID=578863 WHERE UPPER(ColumnName)='SERVICEREPAIR_OLD_SHIPPED_HU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-26T09:51:10.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ServiceRepair_Old_Shipped_HU_ID', Name='Ausgelieferte HUs aus Altanwendung', Description=NULL, Help=NULL WHERE AD_Element_ID=578863 AND IsCentrallyMaintained='Y'
;

-- 2021-03-26T09:51:10.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ausgelieferte HUs aus Altanwendung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578863) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578863)
;

-- 2021-03-26T09:51:10.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ausgelieferte HUs aus Altanwendung', Name='Ausgelieferte HUs aus Altanwendung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578863)
;

-- 2021-03-26T09:51:10.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ausgelieferte HUs aus Altanwendung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578863
;

-- 2021-03-26T09:51:10.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ausgelieferte HUs aus Altanwendung', Description=NULL, Help=NULL WHERE AD_Element_ID = 578863
;

-- 2021-03-26T09:51:10.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ausgelieferte HUs aus Altanwendung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578863
;

-- 2021-03-26T09:51:23.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipped HUs from legacy application', PrintName='Shipped HUs from legacy application',Updated=TO_TIMESTAMP('2021-03-26 11:51:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578863 AND AD_Language='en_US'
;

-- 2021-03-26T09:51:23.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578863,'en_US') 
;

-- 2021-03-26T09:51:39.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Shipped HUs from legacy application',Updated=TO_TIMESTAMP('2021-03-26 11:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541604
;

-- 2021-03-26T09:51:49.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Ausgelieferte HUs aus Altanwendung',Updated=TO_TIMESTAMP('2021-03-26 11:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541604
;

-- 2021-03-26T09:51:53.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Ausgelieferte HUs aus Altanwendung',Updated=TO_TIMESTAMP('2021-03-26 11:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=541604
;

-- 2021-03-26T09:51:57.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Ausgelieferte HUs aus Altanwendung',Updated=TO_TIMESTAMP('2021-03-26 11:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541604
;

-- 2021-03-26T09:53:23.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Bereits ausgelieferte HU erstellen',Updated=TO_TIMESTAMP('2021-03-26 11:53:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584813
;

-- 2021-03-26T09:53:29.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Bereits ausgelieferte HU erstellen',Updated=TO_TIMESTAMP('2021-03-26 11:53:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584813
;

-- 2021-03-26T09:53:34.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Bereits ausgelieferte HU erstellen',Updated=TO_TIMESTAMP('2021-03-26 11:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584813
;

/*
 * #%L
 * de.metas.servicerepair.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2021-03-26T09:53:38.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-26 11:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584813
;

