-- 2022-01-19T07:19:38.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Neu produzierte HUs senden', PrintName='Neu produzierte HUs senden',Updated=TO_TIMESTAMP('2022-01-19 09:19:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='de_CH'
;

-- 2022-01-19T07:19:39.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'de_CH') 
;

-- 2022-01-19T07:19:44.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Neu produzierte HUs senden', PrintName='Neu produzierte HUs senden',Updated=TO_TIMESTAMP('2022-01-19 09:19:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='de_DE'
;

-- 2022-01-19T07:19:44.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'de_DE') 
;

-- 2022-01-19T07:19:44.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580401,'de_DE') 
;

-- 2022-01-19T07:19:44.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSyncHUsOnProductionReceipt', Name='Neu produzierte HUs senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580401
;

-- 2022-01-19T07:19:44.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnProductionReceipt', Name='Neu produzierte HUs senden', Description=NULL, Help=NULL, AD_Element_ID=580401 WHERE UPPER(ColumnName)='ISSYNCHUSONPRODUCTIONRECEIPT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-19T07:19:44.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncHUsOnProductionReceipt', Name='Neu produzierte HUs senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580401 AND IsCentrallyMaintained='Y'
;

-- 2022-01-19T07:19:44.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Neu produzierte HUs senden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580401) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580401)
;

-- 2022-01-19T07:19:44.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Neu produzierte HUs senden', Name='Neu produzierte HUs senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580401)
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

-- 2022-01-19T07:19:44.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Neu produzierte HUs senden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580401
;

-- 2022-01-19T07:19:44.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Neu produzierte HUs senden', Description=NULL, Help=NULL WHERE AD_Element_ID = 580401
;

-- 2022-01-19T07:19:44.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Neu produzierte HUs senden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580401
;

-- 2022-01-19T07:19:51.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Neu produzierte HUs senden', PrintName='Neu produzierte HUs senden',Updated=TO_TIMESTAMP('2022-01-19 09:19:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580401 AND AD_Language='nl_NL'
;

-- 2022-01-19T07:19:51.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580401,'nl_NL') 
;

