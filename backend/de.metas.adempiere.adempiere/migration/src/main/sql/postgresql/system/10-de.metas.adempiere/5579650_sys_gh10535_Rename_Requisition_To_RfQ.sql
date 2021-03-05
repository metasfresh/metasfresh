

-- 2021-02-18T17:44:21.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='RfQ für Auswahl erstellen',Updated=TO_TIMESTAMP('2021-02-18 19:44:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584795
;

-- 2021-02-18T17:45:27.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anfrage erstellt', PrintName='Anfrage erstellt',Updated=TO_TIMESTAMP('2021-02-18 19:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578760 AND AD_Language='de_CH'
;

-- 2021-02-18T17:45:27.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578760,'de_CH') 
;

-- 2021-02-18T17:45:30.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anfrage erstellt', PrintName='Anfrage erstellt',Updated=TO_TIMESTAMP('2021-02-18 19:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578760 AND AD_Language='de_DE'
;

-- 2021-02-18T17:45:30.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578760,'de_DE') 
;

-- 2021-02-18T17:45:30.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578760,'de_DE') 
;

-- 2021-02-18T17:45:30.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsRequisitionCreated', Name='Anfrage erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=578760
;

-- 2021-02-18T17:45:30.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRequisitionCreated', Name='Anfrage erstellt', Description=NULL, Help=NULL, AD_Element_ID=578760 WHERE UPPER(ColumnName)='ISREQUISITIONCREATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-18T17:45:30.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsRequisitionCreated', Name='Anfrage erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=578760 AND IsCentrallyMaintained='Y'
;

-- 2021-02-18T17:45:30.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anfrage erstellt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578760) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578760)
;

-- 2021-02-18T17:45:30.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anfrage erstellt', Name='Anfrage erstellt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578760)
;

-- 2021-02-18T17:45:30.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anfrage erstellt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578760
;

-- 2021-02-18T17:45:30.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anfrage erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID = 578760
;

-- 2021-02-18T17:45:30.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anfrage erstellt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578760
;

-- 2021-02-18T17:45:40.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='RfQ Created', PrintName='RfQ Created',Updated=TO_TIMESTAMP('2021-02-18 19:45:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578760 AND AD_Language='en_US'
;

-- 2021-02-18T17:45:40.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578760,'en_US') 
;

-- 2021-02-18T17:45:45.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anfrage erstellt', PrintName='Anfrage erstellt',Updated=TO_TIMESTAMP('2021-02-18 19:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578760 AND AD_Language='nl_NL'
;

-- 2021-02-18T17:45:45.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578760,'nl_NL') 
;

































-- 2021-02-18T17:47:46.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Anfrage',Updated=TO_TIMESTAMP('2021-02-18 19:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541008
;

-- 2021-02-18T17:47:46.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Anfrage', PrintName='Bedarfsmeldung'  WHERE C_DocType_ID=541008 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-02-18T17:47:48.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Anfrage',Updated=TO_TIMESTAMP('2021-02-18 19:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541008
;

-- 2021-02-18T17:47:48.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Anfrage', PrintName='Anfrage'  WHERE C_DocType_ID=541008 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;




-- 2021-02-18T17:52:54.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET Name='RfQ',Updated=TO_TIMESTAMP('2021-02-18 19:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188223 WHERE AD_Language='en_US' AND C_DocType_ID=541008
;

-- 2021-02-18T17:52:58.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET PrintName='RfQ',Updated=TO_TIMESTAMP('2021-02-18 19:52:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188223 WHERE AD_Language='en_US' AND C_DocType_ID=541008
;













-- 2021-02-18T17:55:26.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Anfrage für Auswahl erstellen',Updated=TO_TIMESTAMP('2021-02-18 19:55:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584795
;

-- 2021-02-18T17:55:36.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Anfrage für Auswahl erstellen',Updated=TO_TIMESTAMP('2021-02-18 19:55:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584795
;

-- 2021-02-18T17:55:41.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Anfrage für Auswahl erstellen',Updated=TO_TIMESTAMP('2021-02-18 19:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584795
;

-- 2021-02-18T17:55:50.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Create RfQ for selection',Updated=TO_TIMESTAMP('2021-02-18 19:55:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584795
;

-- 2021-02-18T17:55:55.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Anfrage für Auswahl erstellen',Updated=TO_TIMESTAMP('2021-02-18 19:55:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584795
;













-- 2021-02-18T18:00:17.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Erzeugt einen Bestellung aus einem bestehenden Anfrage', Name='Bestellung aus Anfrage',Updated=TO_TIMESTAMP('2021-02-18 20:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584794
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

-- 2021-02-18T18:00:24.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='D',Updated=TO_TIMESTAMP('2021-02-18 20:00:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584794
;

-- 2021-02-18T18:00:31.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erzeugt einen Bestellung aus einem bestehenden Anfrage', IsTranslated='Y', Name='Bestellung aus Anfrage',Updated=TO_TIMESTAMP('2021-02-18 20:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584794
;

-- 2021-02-18T18:00:41.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Creates a Purchase Order from this RfQ', Name='Create Purchase Order from this RfQ',Updated=TO_TIMESTAMP('2021-02-18 20:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584794
;

-- 2021-02-18T18:00:50.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erzeugt einen Bestellung aus einem bestehenden Anfrage', IsTranslated='Y', Name='Bestellung aus Anfrage',Updated=TO_TIMESTAMP('2021-02-18 20:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584794
;

-- 2021-02-18T18:00:54.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erzeugt einen Bestellung aus einem bestehenden Anfrage', IsTranslated='Y', Name='Bestellung aus Anfrage',Updated=TO_TIMESTAMP('2021-02-18 20:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584794
;













