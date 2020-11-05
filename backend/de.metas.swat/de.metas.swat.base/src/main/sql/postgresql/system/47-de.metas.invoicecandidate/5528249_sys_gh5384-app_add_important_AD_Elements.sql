-- 2019-07-29T09:27:43.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='InvoicableQtyBasedOn',Updated=TO_TIMESTAMP('2019-07-29 11:27:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948
;

-- 2019-07-29T09:27:43.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL WHERE AD_Element_ID=576948
;

-- 2019-07-29T09:27:43.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL, AD_Element_ID=576948 WHERE UPPER(ColumnName)='INVOICABLEQTYBASEDON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:27:43.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest auf welche Art der Mengenbertrag ermittelt wird, wenn die Preis-Maßeinheit von der Produkt-Maßeinheit abweicht.', Help=NULL WHERE AD_Element_ID=576948 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:29:52.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Name='Abr. Menge basiert auf', PrintName='Abr. Menge basiert auf',Updated=TO_TIMESTAMP('2019-07-29 11:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='de_CH'
;

-- 2019-07-29T09:29:52.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'de_CH')
;

-- 2019-07-29T09:29:57.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.',Updated=TO_TIMESTAMP('2019-07-29 11:29:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='de_DE'
;

-- 2019-07-29T09:29:57.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'de_DE')
;

-- 2019-07-29T09:29:57.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576948,'de_DE')
;

-- 2019-07-29T09:29:57.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID=576948
;

-- 2019-07-29T09:29:57.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL, AD_Element_ID=576948 WHERE UPPER(ColumnName)='INVOICABLEQTYBASEDON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:29:57.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID=576948 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:29:57.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576948) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576948)
;

-- 2019-07-29T09:29:57.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:29:57.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Menge in Preiseinheit durch', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:29:57.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Menge in Preiseinheit durch', Description = 'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:30:02.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abr. Menge basiert auf', PrintName='Abr. Menge basiert auf',Updated=TO_TIMESTAMP('2019-07-29 11:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='de_DE'
;

-- 2019-07-29T09:30:02.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'de_DE')
;

-- 2019-07-29T09:30:02.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576948,'de_DE')
;

-- 2019-07-29T09:30:02.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='InvoicableQtyBasedOn', Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID=576948
;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

-- 2019-07-29T09:30:02.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL, AD_Element_ID=576948 WHERE UPPER(ColumnName)='INVOICABLEQTYBASEDON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T09:30:02.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='InvoicableQtyBasedOn', Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID=576948 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T09:30:02.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576948) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576948)
;

-- 2019-07-29T09:30:02.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abr. Menge basiert auf', Name='Abr. Menge basiert auf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576948)
;

-- 2019-07-29T09:30:02.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:30:02.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abr. Menge basiert auf', Description='Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', Help=NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:30:02.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abr. Menge basiert auf', Description = 'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576948
;

-- 2019-07-29T09:30:16.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoicable Quantity per', PrintName='Invoicable Quantity per',Updated=TO_TIMESTAMP('2019-07-29 11:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576948 AND AD_Language='en_US'
;

-- 2019-07-29T09:30:16.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576948,'en_US')
;