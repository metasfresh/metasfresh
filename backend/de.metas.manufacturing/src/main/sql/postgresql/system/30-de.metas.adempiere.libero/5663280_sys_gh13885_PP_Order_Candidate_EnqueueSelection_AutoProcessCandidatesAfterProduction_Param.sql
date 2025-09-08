-- 2022-11-04T14:40:34.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,0,584935,542360,20,'AutoProcessCandidatesAfterProduction',TO_TIMESTAMP('2022-11-04 16:40:34','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,'Y','N','Y','N','N','N','Auto Process Candidates After Production ','1=0',20,TO_TIMESTAMP('2022-11-04 16:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-04T14:40:34.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542360 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-11-04T14:43:30.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581640,0,'AutoProcessCandidatesAfterProduction',TO_TIMESTAMP('2022-11-04 16:43:30','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Auto Process Candidates After Production ','Auto Process Candidates After Production ',TO_TIMESTAMP('2022-11-04 16:43:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-04T14:43:30.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581640 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-07T10:50:16.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Autoprozess-Kandidaten nach der Produktion',Updated=TO_TIMESTAMP('2022-11-07 12:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542360
;

-- 2022-11-07T10:50:23.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Name='Autoprozess-Kandidaten nach der Produktion',Updated=TO_TIMESTAMP('2022-11-07 12:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542360
;

-- 2022-11-07T10:50:30.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.',Updated=TO_TIMESTAMP('2022-11-07 12:50:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542360
;

-- 2022-11-07T10:50:37.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.',Updated=TO_TIMESTAMP('2022-11-07 12:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542360
;

-- 2022-11-07T10:50:39.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='If true, selected candidates will be marked as processed immediately after the candidate''s quantity has been considered for generating the manufacturing order, even if it''s a partial quantity.',Updated=TO_TIMESTAMP('2022-11-07 12:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_Para_ID=542360
;

-- 2022-11-07T10:50:43.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='If true, selected candidates will be marked as processed immediately after the candidate''s quantity has been considered for generating the manufacturing order, even if it''s a partial quantity.',Updated=TO_TIMESTAMP('2022-11-07 12:50:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542360
;

-- 2022-11-07T11:06:45.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Autoprozess-Kandidaten nach der Produktion', PrintName='Autoprozess-Kandidaten nach der Produktion',Updated=TO_TIMESTAMP('2022-11-07 13:06:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_CH'
;

-- 2022-11-07T11:06:45.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_CH')
;

-- 2022-11-07T11:06:50.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Autoprozess-Kandidaten nach der Produktion', PrintName='Autoprozess-Kandidaten nach der Produktion',Updated=TO_TIMESTAMP('2022-11-07 13:06:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_DE'
;

-- 2022-11-07T11:06:50.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_DE')
;

-- 2022-11-07T11:06:50.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581640,'de_DE')
;

-- 2022-11-07T11:06:50.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Autoprozess-Kandidaten nach der Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID=581640
;

-- 2022-11-07T11:06:50.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Autoprozess-Kandidaten nach der Produktion', Description=NULL, Help=NULL, AD_Element_ID=581640 WHERE UPPER(ColumnName)='AUTOPROCESSCANDIDATESAFTERPRODUCTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-07T11:06:50.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Autoprozess-Kandidaten nach der Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID=581640 AND IsCentrallyMaintained='Y'
;

-- 2022-11-07T11:06:50.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Autoprozess-Kandidaten nach der Produktion', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581640) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581640)
;

-- 2022-11-07T11:06:50.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Autoprozess-Kandidaten nach der Produktion', Name='Autoprozess-Kandidaten nach der Produktion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581640)
;

-- 2022-11-07T11:06:50.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Autoprozess-Kandidaten nach der Produktion', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-07T11:06:50.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Autoprozess-Kandidaten nach der Produktion', Description=NULL, Help=NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-07T11:06:50.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Autoprozess-Kandidaten nach der Produktion', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-07T11:06:58.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.',Updated=TO_TIMESTAMP('2022-11-07 13:06:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_CH'
;

-- 2022-11-07T11:06:58.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_CH')
;

-- 2022-11-07T11:07:00.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.',Updated=TO_TIMESTAMP('2022-11-07 13:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_DE'
;

-- 2022-11-07T11:07:00.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_DE')
;

-- 2022-11-07T11:07:00.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581640,'de_DE')
;

-- 2022-11-07T11:07:00.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Autoprozess-Kandidaten nach der Produktion', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID=581640
;

-- 2022-11-07T11:07:00.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Autoprozess-Kandidaten nach der Produktion', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL, AD_Element_ID=581640 WHERE UPPER(ColumnName)='AUTOPROCESSCANDIDATESAFTERPRODUCTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

/*
 * #%L
 * de.metas.manufacturing
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

-- 2022-11-07T11:07:00.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Autoprozess-Kandidaten nach der Produktion', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID=581640 AND IsCentrallyMaintained='Y'
;

-- 2022-11-07T11:07:00.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Autoprozess-Kandidaten nach der Produktion', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581640) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581640)
;

-- 2022-11-07T11:07:00.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Autoprozess-Kandidaten nach der Produktion', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-07T11:07:00.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Autoprozess-Kandidaten nach der Produktion', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-07T11:07:00.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Autoprozess-Kandidaten nach der Produktion', Description = 'Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-07T11:07:06.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If true, selected candidates will be marked as processed immediately after the candidate''s quantity has been considered for generating the manufacturing order, even if it''s a partial quantity.',Updated=TO_TIMESTAMP('2022-11-07 13:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='en_US'
;

-- 2022-11-07T11:07:06.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'en_US')
;

-- 2022-11-07T11:09:44.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=581640, DisplayLogic='1=0', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2022-11-07 13:09:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542360
;

-- 2022-11-08T14:51:52.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Always mark as processed ',Updated=TO_TIMESTAMP('2022-11-08 16:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='en_US'
;

-- 2022-11-08T14:51:52.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'en_US')
;

-- 2022-11-08T14:52:05.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If ticked, selected candidates will be marked as processed immediately after the manufacturing order was created, even if it''s a partial quantity.', Name='Always mark as processed ',Updated=TO_TIMESTAMP('2022-11-08 16:52:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='nl_NL'
;

-- 2022-11-08T14:52:05.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'nl_NL')
;

-- 2022-11-08T14:52:07.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If ticked, selected candidates will be marked as processed immediately after the manufacturing order was created, even if it''s a partial quantity.',Updated=TO_TIMESTAMP('2022-11-08 16:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='en_US'
;

-- 2022-11-08T14:52:07.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'en_US')
;

-- 2022-11-08T14:52:11.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Always mark as processed ',Updated=TO_TIMESTAMP('2022-11-08 16:52:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='en_US'
;

-- 2022-11-08T14:52:11.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'en_US')
;

-- 2022-11-08T14:52:13.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Always mark as processed ',Updated=TO_TIMESTAMP('2022-11-08 16:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='nl_NL'
;

-- 2022-11-08T14:52:13.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'nl_NL')
;

-- 2022-11-08T14:52:23.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Immer als verarbeitet markieren',Updated=TO_TIMESTAMP('2022-11-08 16:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_DE'
;

-- 2022-11-08T14:52:23.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_DE')
;

-- 2022-11-08T14:52:23.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581640,'de_DE')
;

-- 2022-11-08T14:52:23.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Immer als verarbeitet markieren', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID=581640
;

-- 2022-11-08T14:52:23.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Immer als verarbeitet markieren', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL, AD_Element_ID=581640 WHERE UPPER(ColumnName)='AUTOPROCESSCANDIDATESAFTERPRODUCTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-08T14:52:23.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Immer als verarbeitet markieren', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID=581640 AND IsCentrallyMaintained='Y'
;

-- 2022-11-08T14:52:23.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Immer als verarbeitet markieren', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581640) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581640)
;

-- 2022-11-08T14:52:23.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Autoprozess-Kandidaten nach der Produktion', Name='Immer als verarbeitet markieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581640)
;

-- 2022-11-08T14:52:23.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Immer als verarbeitet markieren', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-08T14:52:23.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Immer als verarbeitet markieren', Description='Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-08T14:52:23.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Immer als verarbeitet markieren', Description = 'Wenn dies der Fall ist, werden die ausgewählten Kandidaten sofort als bearbeitet markiert, nachdem die Menge des Kandidaten bei der Erstellung des Produktionsauftrags berücksichtigt wurde, auch wenn es sich um eine Teilmenge handelt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-08T14:52:25.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Immer als verarbeitet markieren',Updated=TO_TIMESTAMP('2022-11-08 16:52:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_CH'
;

-- 2022-11-08T14:52:25.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_CH')
;

-- 2022-11-08T14:52:27.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Immer als verarbeitet markieren',Updated=TO_TIMESTAMP('2022-11-08 16:52:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_DE'
;

-- 2022-11-08T14:52:27.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_DE')
;

-- 2022-11-08T14:52:27.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581640,'de_DE')
;

-- 2022-11-08T14:52:27.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Immer als verarbeitet markieren', Name='Immer als verarbeitet markieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581640)
;

-- 2022-11-08T14:52:34.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.', PrintName='Immer als verarbeitet markieren',Updated=TO_TIMESTAMP('2022-11-08 16:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_CH'
;

-- 2022-11-08T14:52:34.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_CH')
;

-- 2022-11-08T14:52:37.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.',Updated=TO_TIMESTAMP('2022-11-08 16:52:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581640 AND AD_Language='de_DE'
;

-- 2022-11-08T14:52:37.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581640,'de_DE')
;

-- 2022-11-08T14:52:37.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581640,'de_DE')
;

-- 2022-11-08T14:52:37.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Immer als verarbeitet markieren', Description='Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID=581640
;

-- 2022-11-08T14:52:37.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Immer als verarbeitet markieren', Description='Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.', Help=NULL, AD_Element_ID=581640 WHERE UPPER(ColumnName)='AUTOPROCESSCANDIDATESAFTERPRODUCTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-08T14:52:37.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AutoProcessCandidatesAfterProduction', Name='Immer als verarbeitet markieren', Description='Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID=581640 AND IsCentrallyMaintained='Y'
;

-- 2022-11-08T14:52:37.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Immer als verarbeitet markieren', Description='Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581640) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581640)
;

-- 2022-11-08T14:52:37.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Immer als verarbeitet markieren', Description='Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-08T14:52:37.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Immer als verarbeitet markieren', Description='Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.', Help=NULL WHERE AD_Element_ID = 581640
;

-- 2022-11-08T14:52:37.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Immer als verarbeitet markieren', Description = 'Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581640
;

