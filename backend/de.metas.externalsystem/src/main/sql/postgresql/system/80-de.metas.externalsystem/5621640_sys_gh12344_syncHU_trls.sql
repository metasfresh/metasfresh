-- 2022-01-15T12:00:12.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HUs bei Materialeingang senden', PrintName='HUs bei Materialeingang senden',Updated=TO_TIMESTAMP('2022-01-15 14:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580400 AND AD_Language='de_CH'
;

-- 2022-01-15T12:00:12.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580400,'de_CH') 
;

-- 2022-01-15T12:00:17.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HUs bei Materialeingang senden', PrintName='HUs bei Materialeingang senden',Updated=TO_TIMESTAMP('2022-01-15 14:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580400 AND AD_Language='de_DE'
;

-- 2022-01-15T12:00:17.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580400,'de_DE') 
;

-- 2022-01-15T12:00:17.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580400,'de_DE') 
;

-- 2022-01-15T12:00:17.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSyncHUsOnMaterialReceipt', Name='HUs bei Materialeingang senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580400
;

-- 2022-01-15T12:00:17.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnMaterialReceipt', Name='HUs bei Materialeingang senden', Description=NULL, Help=NULL, AD_Element_ID=580400 WHERE UPPER(ColumnName)='ISSYNCHUSONMATERIALRECEIPT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-15T12:00:17.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnMaterialReceipt', Name='HUs bei Materialeingang senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580400 AND IsCentrallyMaintained='Y'
;

-- 2022-01-15T12:00:17.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HUs bei Materialeingang senden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580400) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580400)
;

-- 2022-01-15T12:00:17.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='HUs bei Materialeingang senden', Name='HUs bei Materialeingang senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580400)
;

-- 2022-01-15T12:00:17.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HUs bei Materialeingang senden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580400
;

-- 2022-01-15T12:00:17.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HUs bei Materialeingang senden', Description=NULL, Help=NULL WHERE AD_Element_ID = 580400
;

-- 2022-01-15T12:00:17.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'HUs bei Materialeingang senden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580400
;

-- 2022-01-15T12:00:26.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HUs bei Materialeingang senden', PrintName='HUs bei Materialeingang senden',Updated=TO_TIMESTAMP('2022-01-15 14:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580400 AND AD_Language='nl_NL'
;

-- 2022-01-15T12:00:26.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580400,'nl_NL') 
;

-- 2022-01-15T12:00:29.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Send HUs on material receipt', PrintName='Send HUs on material receipt',Updated=TO_TIMESTAMP('2022-01-15 14:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580400 AND AD_Language='en_US'
;

-- 2022-01-15T12:00:29.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580400,'en_US') 
;

-- 2022-01-15T12:00:59.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HUs bei Produktionseingang senden', PrintName='HUs bei Produktionseingang senden',Updated=TO_TIMESTAMP('2022-01-15 14:00:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='de_CH'
;

-- 2022-01-15T12:00:59.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'de_CH') 
;

-- 2022-01-15T12:01:03.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HUs bei Produktionseingang senden', PrintName='HUs bei Produktionseingang senden',Updated=TO_TIMESTAMP('2022-01-15 14:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='de_DE'
;

-- 2022-01-15T12:01:03.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'de_DE') 
;

-- 2022-01-15T12:01:03.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580401,'de_DE') 
;

-- 2022-01-15T12:01:03.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSyncHUsOnProductionReceipt', Name='HUs bei Produktionseingang senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580401
;

-- 2022-01-15T12:01:03.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnProductionReceipt', Name='HUs bei Produktionseingang senden', Description=NULL, Help=NULL, AD_Element_ID=580401 WHERE UPPER(ColumnName)='ISSYNCHUSONPRODUCTIONRECEIPT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-15T12:01:03.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnProductionReceipt', Name='HUs bei Produktionseingang senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580401 AND IsCentrallyMaintained='Y'
;

-- 2022-01-15T12:01:03.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='HUs bei Produktionseingang senden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580401) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580401)
;

-- 2022-01-15T12:01:03.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='HUs bei Produktionseingang senden', Name='HUs bei Produktionseingang senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580401)
;

-- 2022-01-15T12:01:03.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='HUs bei Produktionseingang senden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580401
;

-- 2022-01-15T12:01:03.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='HUs bei Produktionseingang senden', Description=NULL, Help=NULL WHERE AD_Element_ID = 580401
;

-- 2022-01-15T12:01:03.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'HUs bei Produktionseingang senden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580401
;


/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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


-- 2022-01-15T12:01:07.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='HUs bei Produktionseingang senden', PrintName='HUs bei Produktionseingang senden',Updated=TO_TIMESTAMP('2022-01-15 14:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='nl_NL'
;

-- 2022-01-15T12:01:07.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'nl_NL') 
;

-- 2022-01-15T12:01:16.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Send HUs on production receipt', PrintName='Send HUs on production receipt',Updated=TO_TIMESTAMP('2022-01-15 14:01:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='en_US'
;

-- 2022-01-15T12:01:16.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'en_US') 
;

