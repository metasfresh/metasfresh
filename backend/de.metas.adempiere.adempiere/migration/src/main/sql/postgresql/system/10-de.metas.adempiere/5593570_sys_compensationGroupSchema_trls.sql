-- 2021-06-18T06:13:19.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Schema-Zeilen werden in der Auftragsschnellerfassung automatisch eingefügt, wenn zu dem betreffenden Produkt ein Kompensationsschema konfiguriert ist', IsTranslated='Y', Name='Schema-Zeilen', PrintName='Schema-Zeilen',Updated=TO_TIMESTAMP('2021-06-18 09:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579311 AND AD_Language='de_CH'
;

-- 2021-06-18T06:13:19.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579311,'de_CH') 
;

-- 2021-06-18T06:13:24.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Schema-Zeilen',Updated=TO_TIMESTAMP('2021-06-18 09:13:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579311 AND AD_Language='de_DE'
;

-- 2021-06-18T06:13:24.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579311,'de_DE') 
;

-- 2021-06-18T06:13:24.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579311,'de_DE') 
;

-- 2021-06-18T06:13:24.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_CompensationGroup_Schema_TemplateLine_ID', Name='Schema-Zeilen', Description=NULL, Help=NULL WHERE AD_Element_ID=579311
;

-- 2021-06-18T06:13:24.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CompensationGroup_Schema_TemplateLine_ID', Name='Schema-Zeilen', Description=NULL, Help=NULL, AD_Element_ID=579311 WHERE UPPER(ColumnName)='C_COMPENSATIONGROUP_SCHEMA_TEMPLATELINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-18T06:13:24.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CompensationGroup_Schema_TemplateLine_ID', Name='Schema-Zeilen', Description=NULL, Help=NULL WHERE AD_Element_ID=579311 AND IsCentrallyMaintained='Y'
;

-- 2021-06-18T06:13:24.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Schema-Zeilen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579311) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579311)
;

-- 2021-06-18T06:13:24.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Compensation Group Template Line', Name='Schema-Zeilen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579311)
;

-- 2021-06-18T06:13:24.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Schema-Zeilen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579311
;

-- 2021-06-18T06:13:24.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Schema-Zeilen', Description=NULL, Help=NULL WHERE AD_Element_ID = 579311
;

-- 2021-06-18T06:13:24.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Schema-Zeilen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579311
;

-- 2021-06-18T06:13:51.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Template lines are added automatically when using order batch entry with a product which is defined to trigger a compensation group creation', Name='Template Lines', PrintName='Template Lines',Updated=TO_TIMESTAMP('2021-06-18 09:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579311 AND AD_Language='en_US'
;

-- 2021-06-18T06:13:51.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579311,'en_US') 
;

-- 2021-06-18T06:18:52.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kompensationszeilen', PrintName='Kompensationszeilen',Updated=TO_TIMESTAMP('2021-06-18 09:18:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543890 AND AD_Language='de_CH'
;

-- 2021-06-18T06:18:52.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543890,'de_CH') 
;

-- 2021-06-18T06:19:02.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Compensations', PrintName='Compensations',Updated=TO_TIMESTAMP('2021-06-18 09:19:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543890 AND AD_Language='en_US'
;

-- 2021-06-18T06:19:02.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543890,'en_US') 
;

-- 2021-06-18T06:19:03.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-18 09:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543890 AND AD_Language='de_CH'
;

-- 2021-06-18T06:19:03.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543890,'de_CH') 
;

-- 2021-06-18T06:19:19.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', IsTranslated='Y', Name='Kompensationszeilen', PrintName='Kompensationszeilen',Updated=TO_TIMESTAMP('2021-06-18 09:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543890 AND AD_Language='de_DE'
;

-- 2021-06-18T06:19:19.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543890,'de_DE') 
;

-- 2021-06-18T06:19:19.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543890,'de_DE') 
;

-- 2021-06-18T06:19:19.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_CompensationGroup_SchemaLine_ID', Name='Kompensationszeilen', Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', Help=NULL WHERE AD_Element_ID=543890
;

-- 2021-06-18T06:19:19.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CompensationGroup_SchemaLine_ID', Name='Kompensationszeilen', Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', Help=NULL, AD_Element_ID=543890 WHERE UPPER(ColumnName)='C_COMPENSATIONGROUP_SCHEMALINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-18T06:19:19.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CompensationGroup_SchemaLine_ID', Name='Kompensationszeilen', Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', Help=NULL WHERE AD_Element_ID=543890 AND IsCentrallyMaintained='Y'
;

-- 2021-06-18T06:19:19.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kompensationszeilen', Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543890) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543890)
;

-- 2021-06-18T06:19:19.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kompensationszeilen', Name='Kompensationszeilen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543890)
;

-- 2021-06-18T06:19:19.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kompensationszeilen', Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543890
;

-- 2021-06-18T06:19:19.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kompensationszeilen', Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', Help=NULL WHERE AD_Element_ID = 543890
;

-- 2021-06-18T06:19:19.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kompensationszeilen', Description = 'Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543890
;

-- 2021-06-18T06:19:22.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.',Updated=TO_TIMESTAMP('2021-06-18 09:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543890 AND AD_Language='de_CH'
;

-- 2021-06-18T06:19:22.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543890,'de_CH') 
;

-- 2021-06-18T06:19:28.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Compensation lines are lines which are added at the bottom of the group (when created or updated) in order to apply discounts or surcharges.',Updated=TO_TIMESTAMP('2021-06-18 09:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543890 AND AD_Language='en_US'
;

-- 2021-06-18T06:19:28.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543890,'en_US') 
;

-- 2021-06-18T06:19:55.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=543890, CommitWarning=NULL, Description='Kompensationszeilen werden bei der Auftragserfassung automatisch eingefügt, um Rabatte oder Gebühren abzubilden.', Help=NULL, Name='Kompensationszeilen',Updated=TO_TIMESTAMP('2021-06-18 09:19:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541042
;

-- 2021-06-18T06:19:55.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(543890) 
;

-- 2021-06-18T06:19:55.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541042)
;

-- 2021-06-18T06:20:33.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Products listed in this tab are allowed to have no contract conditions even though the compensation group has contract conditions set.', IsTranslated='Y', Name='Products excl. from subscription', PrintName='Products excl. from subscription',Updated=TO_TIMESTAMP('2021-06-18 09:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579275 AND AD_Language='en_US'
;

-- 2021-06-18T06:20:33.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579275,'en_US') 
;

-- 2021-06-18T06:21:13.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produkte ohne Vertrag', PrintName='Produkte ohne Vertrag',Updated=TO_TIMESTAMP('2021-06-18 09:21:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579275 AND AD_Language='de_DE'
;

-- 2021-06-18T06:21:13.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579275,'de_DE') 
;

-- 2021-06-18T06:21:13.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579275,'de_DE') 
;

-- 2021-06-18T06:21:13.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Product_Exclude_FlatrateConditions_ID', Name='Produkte ohne Vertrag', Description=NULL, Help=NULL WHERE AD_Element_ID=579275
;

-- 2021-06-18T06:21:13.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_Exclude_FlatrateConditions_ID', Name='Produkte ohne Vertrag', Description=NULL, Help=NULL, AD_Element_ID=579275 WHERE UPPER(ColumnName)='M_PRODUCT_EXCLUDE_FLATRATECONDITIONS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-18T06:21:13.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_Exclude_FlatrateConditions_ID', Name='Produkte ohne Vertrag', Description=NULL, Help=NULL WHERE AD_Element_ID=579275 AND IsCentrallyMaintained='Y'
;

-- 2021-06-18T06:21:13.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkte ohne Vertrag', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579275)
;

-- 2021-06-18T06:21:13.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkte ohne Vertrag', Name='Produkte ohne Vertrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579275)
;

-- 2021-06-18T06:21:13.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkte ohne Vertrag', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579275
;

-- 2021-06-18T06:21:13.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkte ohne Vertrag', Description=NULL, Help=NULL WHERE AD_Element_ID = 579275
;

-- 2021-06-18T06:21:13.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkte ohne Vertrag', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579275
;

-- 2021-06-18T06:21:20.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produkte ohne Vertrag', PrintName='Produkte ohne Vertrag',Updated=TO_TIMESTAMP('2021-06-18 09:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579275 AND AD_Language='de_CH'
;

-- 2021-06-18T06:21:20.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579275,'de_CH') 
;

-- 2021-06-18T06:21:28.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.',Updated=TO_TIMESTAMP('2021-06-18 09:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579275 AND AD_Language='de_CH'
;

-- 2021-06-18T06:21:28.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579275,'de_CH') 
;

-- 2021-06-18T06:21:31.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.',Updated=TO_TIMESTAMP('2021-06-18 09:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579275 AND AD_Language='de_DE'
;

-- 2021-06-18T06:21:31.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579275,'de_DE') 
;

-- 2021-06-18T06:21:31.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579275,'de_DE') 
;

-- 2021-06-18T06:21:31.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Product_Exclude_FlatrateConditions_ID', Name='Produkte ohne Vertrag', Description='Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.', Help=NULL WHERE AD_Element_ID=579275
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- 2021-06-18T06:21:31.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_Exclude_FlatrateConditions_ID', Name='Produkte ohne Vertrag', Description='Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.', Help=NULL, AD_Element_ID=579275 WHERE UPPER(ColumnName)='M_PRODUCT_EXCLUDE_FLATRATECONDITIONS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-18T06:21:31.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_Exclude_FlatrateConditions_ID', Name='Produkte ohne Vertrag', Description='Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.', Help=NULL WHERE AD_Element_ID=579275 AND IsCentrallyMaintained='Y'
;

-- 2021-06-18T06:21:31.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkte ohne Vertrag', Description='Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579275) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579275)
;

-- 2021-06-18T06:21:31.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkte ohne Vertrag', Description='Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579275
;

-- 2021-06-18T06:21:31.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkte ohne Vertrag', Description='Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.', Help=NULL WHERE AD_Element_ID = 579275
;

-- 2021-06-18T06:21:31.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkte ohne Vertrag', Description = 'Hier aufgelistete Produkte können bei der Auftragserfassung ohne Vertrag erfasst werden, auch wenn bei der Kompensationsguppe Vertragsbedingungen hinterlegt sind.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579275
;

