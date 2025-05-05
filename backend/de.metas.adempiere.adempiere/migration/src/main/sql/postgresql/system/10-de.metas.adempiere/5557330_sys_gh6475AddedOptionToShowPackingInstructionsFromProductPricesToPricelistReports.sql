-- 2020-04-14T14:18:43.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 540399, 541797, 20, 'show_only_pricelist_instructions', TO_TIMESTAMP('2020-04-14 17:18:43', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'U', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'p_show_only_pricelist_instructions', 50, TO_TIMESTAMP('2020-04-14 17:18:43', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-04-14T14:18:43.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541797
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-04-14T14:18:58.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET EntityType='de.metas.fresh',
    Updated=TO_TIMESTAMP('2020-04-14 17:18:58', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_Para_ID = 541797
;

-- 2020-04-14T14:52:17.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 577666, 0, TO_TIMESTAMP('2020-04-14 17:52:17', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 'Show Only Packing Instructions With Price', 'Show Only Packing Instructions With Price', TO_TIMESTAMP('2020-04-14 17:52:17', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-04-14T14:52:17.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 577666
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2020-04-14T14:52:23.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name=' Nur Packanweisungen mit Preis anzeigen',
    PrintName=' Nur Packanweisungen mit Preis anzeigen',
    Updated=TO_TIMESTAMP('2020-04-14 17:52:23', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'de_CH'
;

-- 2020-04-14T14:52:23.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'de_CH')
;

-- 2020-04-14T14:52:25.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name=' Nur Packanweisungen mit Preis anzeigen',
    PrintName=' Nur Packanweisungen mit Preis anzeigen',
    Updated=TO_TIMESTAMP('2020-04-14 17:52:25', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'de_DE'
;

-- 2020-04-14T14:52:25.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'de_DE')
;

-- 2020-04-14T14:52:26.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_ad_element_on_ad_element_trl_update(577666, 'de_DE')
;

-- 2020-04-14T14:52:26.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnName=NULL,
    Name=' Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:52:26.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName=NULL,
    Name=' Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
  AND IsCentrallyMaintained = 'Y'
;

-- 2020-04-14T14:52:26.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET Name=' Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID = 577666) AND AD_Name_ID IS NULL)
   OR (AD_Name_ID = 577666)
;

-- 2020-04-14T14:52:26.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi
SET PrintName=' Nur Packanweisungen mit Preis anzeigen',
    Name=' Nur Packanweisungen mit Preis anzeigen'
WHERE IsCentrallyMaintained = 'Y'
  AND EXISTS(SELECT * FROM AD_Column c WHERE c.AD_Column_ID = pi.AD_Column_ID AND c.AD_Element_ID = 577666)
;

-- 2020-04-14T14:52:26.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab
SET Name=' Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL,
    CommitWarning = NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:52:26.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW
SET Name=' Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:52:26.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu
SET Name                    = ' Nur Packanweisungen mit Preis anzeigen',
    Description             = NULL,
    WEBUI_NameBrowse        = NULL,
    WEBUI_NameNew           = NULL,
    WEBUI_NameNewBreadcrumb = NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:52:29.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2020-04-14 17:52:29', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'de_DE'
;

-- 2020-04-14T14:52:29.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'de_DE')
;

-- 2020-04-14T14:52:29.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_ad_element_on_ad_element_trl_update(577666, 'de_DE')
;

-- 2020-04-14T14:52:47.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Nur Packanweisungen mit Preis anzeigen',
    PrintName='Nur Packanweisungen mit Preis anzeigen',
    Updated=TO_TIMESTAMP('2020-04-14 17:52:47', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'de_CH'
;

-- 2020-04-14T14:52:47.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'de_CH')
;

-- 2020-04-14T14:52:52.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Nur Packanweisungen mit Preis anzeigen',
    PrintName='Nur Packanweisungen mit Preis anzeigen',
    Updated=TO_TIMESTAMP('2020-04-14 17:52:52', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'de_DE'
;

-- 2020-04-14T14:52:52.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'de_DE')
;

-- 2020-04-14T14:52:52.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_ad_element_on_ad_element_trl_update(577666, 'de_DE')
;

-- 2020-04-14T14:52:52.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnName=NULL,
    Name='Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:52:52.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName=NULL,
    Name='Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
  AND IsCentrallyMaintained = 'Y'
;

-- 2020-04-14T14:52:52.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET Name='Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID = 577666) AND AD_Name_ID IS NULL)
   OR (AD_Name_ID = 577666)
;

-- 2020-04-14T14:52:52.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi
SET PrintName='Nur Packanweisungen mit Preis anzeigen',
    Name='Nur Packanweisungen mit Preis anzeigen'
WHERE IsCentrallyMaintained = 'Y'
  AND EXISTS(SELECT * FROM AD_Column c WHERE c.AD_Column_ID = pi.AD_Column_ID AND c.AD_Element_ID = 577666)
;

-- 2020-04-14T14:52:52.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab
SET Name='Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL,
    CommitWarning = NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:52:52.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW
SET Name='Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:52:52.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu
SET Name                    = 'Nur Packanweisungen mit Preis anzeigen',
    Description             = NULL,
    WEBUI_NameBrowse        = NULL,
    WEBUI_NameNew           = NULL,
    WEBUI_NameNewBreadcrumb = NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:53:05.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element
SET ColumnName='show_only_pricelist_instructions',
    Updated=TO_TIMESTAMP('2020-04-14 17:53:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:53:05.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnName='show_only_pricelist_instructions',
    Name='Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:53:05.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='show_only_pricelist_instructions',
    Name='Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL,
    AD_Element_ID=577666
WHERE UPPER(ColumnName) = 'SHOW_ONLY_PRICELIST_INSTRUCTIONS'
  AND IsCentrallyMaintained = 'Y'
  AND AD_Element_ID IS NULL
;

-- 2020-04-14T14:53:05.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='show_only_pricelist_instructions',
    Name='Nur Packanweisungen mit Preis anzeigen',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
  AND IsCentrallyMaintained = 'Y'
;

-- 2020-04-14T14:54:21.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element
SET EntityType='de.metas.fresh',
    Updated=TO_TIMESTAMP('2020-04-14 17:54:21', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
;

-- 2020-04-14T14:55:29.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 577666, 0, 540407, 541798, 20, 'show_only_pricelist_instructions', TO_TIMESTAMP('2020-04-14 17:55:28', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'de.metas.fresh', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Nur Packanweisungen mit Preis anzeigen', 60, TO_TIMESTAMP('2020-04-14 17:55:28', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-04-14T14:55:29.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541798
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-04-14T14:55:53.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 577666, 0, 584673, 541799, 20, 'show_only_pricelist_instructions', TO_TIMESTAMP('2020-04-14 17:55:53', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'de.metas.fresh', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Nur Packanweisungen mit Preis anzeigen', 60, TO_TIMESTAMP('2020-04-14 17:55:53', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-04-14T14:55:53.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541799
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-04-14T15:32:28.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 577666, 0, 584659, 541800, 20, 'show_only_pricelist_instructions', TO_TIMESTAMP('2020-04-14 18:32:27', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'de.metas.fresh', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Nur Packanweisungen mit Preis anzeigen', 60, TO_TIMESTAMP('2020-04-14 18:32:27', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-04-14T15:32:28.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541800
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-04-14T15:34:06.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, @show_only_pricelist_instructions@) where show_only_pricelist_instructions = ''N''
UNION ALL
SELECT * FROM report.fresh_pricelist_details_template_report_2(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, @show_only_pricelist_instructions@) where show_only_pricelist_instructions = ''Y'';',
    Updated=TO_TIMESTAMP('2020-04-14 18:34:06', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-14T15:45:41.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @show_only_pricelist_instructions@) where show_only_pricelist_instructions = ''N''
UNION ALL
SELECT * FROM report.fresh_pricelist_details_template_report_2(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @show_only_pricelist_instructions@) where show_only_pricelist_instructions = ''Y'';',
    Updated=TO_TIMESTAMP('2020-04-14 18:45:41', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-14T15:46:48.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @show_only_pricelist_instructions@)',
    Updated=TO_TIMESTAMP('2020-04-14 18:46:48', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-14T15:46:52.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @show_only_pricelist_instructions@);',
    Updated=TO_TIMESTAMP('2020-04-14 18:46:52', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-14T15:47:05.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, @show_only_pricelist_instructions@);',
    Updated=TO_TIMESTAMP('2020-04-14 18:47:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-14T15:47:28.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @show_only_pricelist_instructions@)  where show_only_pricelist_instructions = ''N''
UNION ALL
SELECT * FROM report.fresh_pricelist_details_template_report_2(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @show_only_pricelist_instructions@) where show_only_pricelist_instructions = ''Y'';',
    Updated=TO_TIMESTAMP('2020-04-14 18:47:28', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-15T09:14:19.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='All packing instructions',
    PrintName='All packing instructions',
    Updated=TO_TIMESTAMP('2020-04-15 12:14:19', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'en_US'
;

-- 2020-04-15T09:14:19.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'en_US')
;

-- 2020-04-15T09:14:37.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='All Packing Instructions',
    PrintName='All Packing Instructions',
    Updated=TO_TIMESTAMP('2020-04-15 12:14:37', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'en_US'
;

-- 2020-04-15T09:14:37.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'en_US')
;

-- 2020-04-15T09:14:46.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Alle Packvorschriften',
    PrintName='Alle Packvorschriften',
    Updated=TO_TIMESTAMP('2020-04-15 12:14:46', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'nl_NL'
;

-- 2020-04-15T09:14:46.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'nl_NL')
;

-- 2020-04-15T09:14:50.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Alle Packvorschriften',
    PrintName='Alle Packvorschriften',
    Updated=TO_TIMESTAMP('2020-04-15 12:14:50', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'de_DE'
;

-- 2020-04-15T09:14:50.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'de_DE')
;

-- 2020-04-15T09:14:50.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_ad_element_on_ad_element_trl_update(577666, 'de_DE')
;

-- 2020-04-15T09:14:50.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnName='show_only_pricelist_instructions',
    Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-15T09:14:50.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='show_only_pricelist_instructions',
    Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL,
    AD_Element_ID=577666
WHERE UPPER(ColumnName) = 'SHOW_ONLY_PRICELIST_INSTRUCTIONS'
  AND IsCentrallyMaintained = 'Y'
  AND AD_Element_ID IS NULL
;

-- 2020-04-15T09:14:50.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='show_only_pricelist_instructions',
    Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
  AND IsCentrallyMaintained = 'Y'
;

-- 2020-04-15T09:14:50.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL
WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID = 577666) AND AD_Name_ID IS NULL)
   OR (AD_Name_ID = 577666)
;

-- 2020-04-15T09:14:50.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi
SET PrintName='Alle Packvorschriften',
    Name='Alle Packvorschriften'
WHERE IsCentrallyMaintained = 'Y'
  AND EXISTS(SELECT * FROM AD_Column c WHERE c.AD_Column_ID = pi.AD_Column_ID AND c.AD_Element_ID = 577666)
;

-- 2020-04-15T09:14:50.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab
SET Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL,
    CommitWarning = NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-15T09:14:50.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW
SET Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-15T09:14:50.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu
SET Name                    = 'Alle Packvorschriften',
    Description             = NULL,
    WEBUI_NameBrowse        = NULL,
    WEBUI_NameNew           = NULL,
    WEBUI_NameNewBreadcrumb = NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-15T09:14:54.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='All Packing Instructions',
    PrintName='All Packing Instructions',
    Updated=TO_TIMESTAMP('2020-04-15 12:14:54', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'nl_NL'
;

-- 2020-04-15T09:14:54.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'nl_NL')
;

-- 2020-04-15T09:15:11.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Alle Packvorschriften',
    PrintName='Alle Packvorschriften',
    Updated=TO_TIMESTAMP('2020-04-15 12:15:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
  AND AD_Language = 'de_CH'
;

-- 2020-04-15T09:15:11.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577666, 'de_CH')
;

-- 2020-04-15T09:19:27.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element
SET ColumnName='p_show_product_price_pi_flag',
    Updated=TO_TIMESTAMP('2020-04-15 12:19:27', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577666
;

-- 2020-04-15T09:19:27.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnName='p_show_product_price_pi_flag',
    Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
;

-- 2020-04-15T09:19:27.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='p_show_product_price_pi_flag',
    Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL,
    AD_Element_ID=577666
WHERE UPPER(ColumnName) = 'P_SHOW_PRODUCT_PRICE_PI_FLAG'
  AND IsCentrallyMaintained = 'Y'
  AND AD_Element_ID IS NULL
;

-- 2020-04-15T09:19:27.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='p_show_product_price_pi_flag',
    Name='Alle Packvorschriften',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577666
  AND IsCentrallyMaintained = 'Y'
;

-- 2020-04-15T09:52:10.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @p_show_product_price_pi_flag@) where show_product_price_pi_flag = ''N'' UNION ALL SELECT * FROM report.fresh_pricelist_details_template_report_2(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @p_show_product_price_pi_flag@) where show_product_price_pi_flag = ''Y'';',
    Updated=TO_TIMESTAMP('2020-04-15 12:52:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-15T10:30:02.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @p_show_product_price_pi_flag@) where show_product_price_pi_flag = ''N'' UNION ALL SELECT * FROM report.fresh_pricelist_details_template_report_With_PP_PI(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/0@, @p_show_product_price_pi_flag@) where show_product_price_pi_flag = ''Y'';',
    Updated=TO_TIMESTAMP('2020-04-15 13:30:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-15T10:53:11.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, @p_show_product_price_pi_flag/NULL@) where show_product_price_pi_flag = ''N'' UNION ALL SELECT * FROM report.fresh_pricelist_details_template_report_With_PP_PI(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, @p_show_product_price_pi_flag/NULL@) where show_product_price_pi_flag = ''Y'';',
    Updated=TO_TIMESTAMP('2020-04-15 13:53:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID = 584659
;

-- 2020-04-15T10:53:52.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, DefaultValue, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 577666, 0, 584680, 541801, 20, 'p_show_product_price_pi_flag', TO_TIMESTAMP('2020-04-15 13:53:52', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'de.metas.fresh', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Alle Packvorschriften', 40, TO_TIMESTAMP('2020-04-15 13:53:52', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-04-15T10:53:52.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541801
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-04-15T10:54:36.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy, VFormat)
VALUES (0, 577666, 0, 541230, 541802, 20, 'p_show_product_price_pi_flag', TO_TIMESTAMP('2020-04-15 13:54:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.fresh', 0, 'Y', 'N', 'Y', 'N', 'N', 'N', 'Alle Packvorschriften', 40, TO_TIMESTAMP('2020-04-15 13:54:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y')
;

-- 2020-04-15T10:54:36.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_Para_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_Para_ID = 541802
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- 2020-04-15T13:56:23.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, ''@p_show_product_price_pi_flag/NULL@'') where show_product_price_pi_flag = ''N''
UNION ALL
SELECT * FROM report.fresh_pricelist_details_template_report_With_PP_PI(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, ''@p_show_product_price_pi_flag/NULL@'') where show_product_price_pi_flag = ''Y'';',Updated=TO_TIMESTAMP('2020-04-15 16:56:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-04-15T14:34:35.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Preisliste Packvorschriften', PrintName='Preisliste Packvorschriften',Updated=TO_TIMESTAMP('2020-04-15 17:34:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577666 AND AD_Language='de_CH'
;

-- 2020-04-15T14:34:35.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577666,'de_CH')
;

-- 2020-04-15T14:34:40.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Preisliste Packvorschriften', PrintName='Preisliste Packvorschriften',Updated=TO_TIMESTAMP('2020-04-15 17:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577666 AND AD_Language='de_DE'
;

-- 2020-04-15T14:34:40.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577666,'de_DE')
;

-- 2020-04-15T14:34:40.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577666,'de_DE')
;

-- 2020-04-15T14:34:40.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='p_show_product_price_pi_flag', Name='Preisliste Packvorschriften', Description=NULL, Help=NULL WHERE AD_Element_ID=577666
;

-- 2020-04-15T14:34:40.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='p_show_product_price_pi_flag', Name='Preisliste Packvorschriften', Description=NULL, Help=NULL, AD_Element_ID=577666 WHERE UPPER(ColumnName)='P_SHOW_PRODUCT_PRICE_PI_FLAG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-15T14:34:40.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='p_show_product_price_pi_flag', Name='Preisliste Packvorschriften', Description=NULL, Help=NULL WHERE AD_Element_ID=577666 AND IsCentrallyMaintained='Y'
;

-- 2020-04-15T14:34:40.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preisliste Packvorschriften', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577666) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577666)
;

-- 2020-04-15T14:34:40.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preisliste Packvorschriften', Name='Preisliste Packvorschriften' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577666)
;

-- 2020-04-15T14:34:40.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preisliste Packvorschriften', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577666
;

-- 2020-04-15T14:34:40.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preisliste Packvorschriften', Description=NULL, Help=NULL WHERE AD_Element_ID = 577666
;

-- 2020-04-15T14:34:40.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Preisliste Packvorschriften', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577666
;

-- 2020-04-15T14:34:51.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pricelist Instructions', PrintName='Pricelist Instructions',Updated=TO_TIMESTAMP('2020-04-15 17:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577666 AND AD_Language='en_US'
;

-- 2020-04-15T14:34:51.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577666,'en_US')
;

-- 2020-04-15T14:40:53.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pricelist Packing Instructions', PrintName='Pricelist Packing Instructions',Updated=TO_TIMESTAMP('2020-04-15 17:40:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577666 AND AD_Language='en_US'
;

-- 2020-04-15T14:40:53.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577666,'en_US')
;


DROP VIEW IF EXISTS rv_fresh_pricelist;
DROP VIEW IF EXISTS RV_fresh_PriceList_Comparison;

CREATE OR REPLACE VIEW RV_fresh_PriceList_Comparison AS


SELECT pp.AD_Org_ID,
       pp.AD_Client_ID,
       pp.Created,
       pp.CreatedBy,
       pp.Updated,
       pp.UpdatedBy,
       pp.IsActive,

       -- Displayed pricelist data
       pc.Name                                                                    AS ProductCategory,
       pc.value                                                                   AS ProductCategoryValue,
       p.M_Product_ID,
       p.Value,
       p.Name                                                                     AS ProductName,
       pp.IsSeasonFixedPrice,
       hupip.Name                                                                 AS ItemProductName,
       pm.Name                                                                    AS PackingMaterialName,
       ROUND(COALESCE(ppa.PriceStd, pp.PriceStd), coalesce(pl.priceprecision, 2)) AS PriceStd,
       ROUND(pp2.PriceStd, coalesce(pl2.priceprecision, 2))                       AS AltPriceStd,
       CASE WHEN pp2.PriceStd IS NULL THEN 0 ELSE 1 END                           AS hasaltprice,
       uom.UOMSymbol,
       COALESCE(ppa.Attributes, '')                                               as attributes,
       pp.seqNo,

       -- Filter Columns
       bp.C_BPartner_ID,
       plv.M_Pricelist_Version_ID,
       plv2.M_Pricelist_Version_ID                                                AS Alt_PriceList_Version_ID,

       -- Additional internal infos to be used
       pp.M_ProductPrice_ID,
       ppa.m_attributesetinstance_ID,
       bp_ip.M_HU_PI_Item_Product_ID                                              as M_HU_PI_Item_Product_ID,
       uom.X12DE355                                                               as UOM_X12DE355,
       hupip.Qty                                                                  as QtyCUsPerTU,
       it.m_hu_pi_version_id                                                      AS m_hu_pi_version_id,
       c.iso_code                                                                 as currency,
       c2.iso_code                                                                as currency2

FROM M_ProductPrice pp

         INNER JOIN M_Product p ON pp.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

    /** Get all BPartner and Product combinations.
     * IMPORTANT: Never use the query without BPartner Filter active
     */
         INNER JOIN C_BPartner bp ON TRUE
         INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
         INNER JOIN C_UOM uom ON pp.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'


    /*
      * We know if there are packing instructions limited to the BPartner/product-combination. If so,
      * we will use only those. If not, we will use only the non limited ones
      */
         LEFT OUTER JOIN LATERAL
    (
    SELECT vip.M_HU_PI_Item_Product_ID, vip.hasPartner
    FROM Report.Valid_PI_Item_Product_V vip
        /* WHERE isInfiniteCapacity = 'N' task 09045/09788: we can also export PiiPs with infinite capacity */
    WHERE p.M_Product_ID = vip.M_Product_ID

      AND CASE
              WHEN
                  EXISTS(select 0 from Report.Valid_PI_Item_Product_V v where p.M_Product_ID = v.M_Product_ID AND v.hasPartner is true and bp.C_BPartner_ID = v.C_BPartner_ID)
                  THEN vip.C_BPartner_ID = bp.C_BPartner_ID
              else vip.C_BPartner_ID IS NULL END
    ) bp_ip ON TRUE

         LEFT OUTER JOIN LATERAL
    (
    SELECT M_ProductPrice_ID, M_Attributesetinstance_ID, PriceStd, IsActive, M_HU_PI_Item_Product_ID, Attributes, Signature
    FROM report.fresh_AttributePrice ppa
    WHERE ppa.isActive = 'Y'
      AND ppa.M_ProductPrice_ID = pp.M_ProductPrice_ID
      AND (ppa.m_hu_pi_item_product_id = bp_ip.m_hu_pi_item_product_id OR ppa.m_hu_pi_item_product_id IS NULL)
      AND ppa.m_pricelist_version_id = pp.m_pricelist_version_id
    ) ppa on true

         LEFT OUTER JOIN m_hu_pi_item_product hupip ON bp_ip.m_hu_pi_item_product_ID = hupip.m_hu_pi_item_product_id and hupip.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item it ON hupip.M_HU_PI_Item_ID = it.M_HU_PI_Item_ID AND it.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item pmit ON it.m_hu_pi_version_id = pmit.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT AND pmit.isActive = 'Y'
         LEFT OUTER JOIN m_hu_packingmaterial pm ON pmit.m_hu_packingmaterial_id = pm.m_hu_packingmaterial_id AND pm.isActive = 'Y'


         INNER JOIN M_PriceList_Version plv ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID AND plv.IsActive = 'Y'
    /*
     Get Comparison Prices
    */

    /* Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
     limited to the same PriceList because the Parameter validation rule is enforcing this */
         LEFT JOIN M_PriceList_Version plv2 ON plv.M_PriceList_ID = plv2.M_PriceList_ID AND plv2.IsActive = 'Y'
         LEFT OUTER JOIN LATERAL (
    SELECT COALESCE(ppa2.PriceStd, pp2.PriceStd) AS PriceStd, ppa2.signature
    FROM M_ProductPrice pp2
             /* Joining attribute prices */
             INNER JOIN report.fresh_AttributePrice ppa2 ON pp2.M_ProductPrice_ID = ppa2.M_ProductPrice_ID AND ppa2.m_pricelist_version_id = pp2.m_pricelist_version_id

    WHERE p.M_Product_ID = pp2.M_Product_ID
      AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID
      AND pp2.IsActive = 'Y'
      AND (pp2.m_hu_pi_item_product_ID = pp.m_hu_pi_item_product_ID OR (pp2.m_hu_pi_item_product_ID is null and pp.m_hu_pi_item_product_ID is null))
      AND pp2.isAttributeDependant = pp.isAttributeDependant
      --avoid comparing different prices in same pricelist
      AND (CASE WHEN pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID THEN pp2.M_ProductPrice_ID = pp.M_ProductPrice_ID ELSE TRUE END)
        /* we have to make sure that only prices with the same attributes and packing instructions are compared. Note:
        * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
        * - If there are no Attribute Prices the signature will be null
        * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
      AND (ppa.signature = ppa2.signature)
      AND ppa2.IsActive = 'Y'
      AND (ppa2.m_hu_pi_item_product_id = bp_ip.m_hu_pi_item_product_id OR ppa2.m_hu_pi_item_product_id IS NULL)
    ) pp2 ON true

         INNER JOIN M_Pricelist pl ON plv.M_Pricelist_ID = pl.M_PriceList_ID AND pl.isActive = 'Y'
         LEFT JOIN M_Pricelist pl2 ON plv2.M_PriceList_ID = pl2.M_Pricelist_ID AND pl2.isActive = 'Y'
         INNER JOIN C_Currency c ON pl.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
         LEFT JOIN C_Currency c2 ON pl2.C_Currency_ID = c2.C_CUrrency_ID AND c2.isActive = 'Y'

WHERE pp.isActive = 'Y'

  AND (pp.M_Attributesetinstance_ID = ppa.M_Attributesetinstance_ID OR pp.M_Attributesetinstance_ID is null)
  AND (pp.M_HU_PI_Item_Product_ID = bp_ip.M_HU_PI_Item_Product_ID OR pp.M_HU_PI_Item_Product_ID is null)

  AND (case when plv2.M_PriceList_Version_ID = plv.M_PriceList_Version_ID THEN ppa.signature = pp2.signature ELSE true end)
;

COMMENT ON VIEW RV_fresh_PriceList_Comparison
    IS '06042 Preisliste Drucken Preisänderung (100373416918)
Refactored in Tasks 07833 and 07915
A view for a report that displays the same data as RV_fresh_PriceList but imroved to be able to filter by two Price list versions, to be able to compare them';

CREATE OR REPLACE VIEW RV_fresh_PriceList AS
SELECT
    -- Mandatory Columns
    plc.AD_Org_ID,
    plc.AD_Client_ID,
    plc.Created,
    plc.CreatedBy,
    plc.Updated,
    plc.UpdatedBy,
    plc.IsActive,

    -- Displayed pricelist data
    plc.ProductCategory,
    plc.ProductCategoryValue,
    plc.M_Product_ID,
    plc.Value,
    plc.ProductName,
    plc.IsSeasonFixedPrice,
    plc.ItemProductName,
    plc.PackingMaterialName,
    plc.PriceStd,
    plc.AltPriceStd,
    plc.HasAltPrice,
    plc.UOMSymbol,
    plc.Attributes,

    -- Filter Columns
    plc.C_BPartner_ID,
    plc.M_Pricelist_Version_ID,
    plc.M_ProductPrice_ID
FROM RV_fresh_PriceList_Comparison plc
WHERE plc.M_Pricelist_Version_ID = plc.Alt_PriceList_Version_ID
;

COMMENT ON VIEW RV_fresh_PriceList IS '05956 Printformat for Pricelist (109054740508) 
Refactored in Task 07833 and 07915
A view for a report that displays all product prices of a BPartner, including the attribute prices and CU:TU relation';

DROP VIEW IF EXISTS RV_fresh_PriceList_Comparison_With_PP_PI;

CREATE OR REPLACE VIEW RV_fresh_PriceList_Comparison_With_PP_PI AS


SELECT pp.AD_Org_ID,
       pp.AD_Client_ID,
       pp.Created,
       pp.CreatedBy,
       pp.Updated,
       pp.UpdatedBy,
       pp.IsActive,

       -- Displayed pricelist data
       pc.Name                                                                    AS ProductCategory,
       pc.value                                                                   AS ProductCategoryValue,
       p.M_Product_ID,
       p.Value,
       p.Name                                                                     AS ProductName,
       pp.IsSeasonFixedPrice,
       hupip.Name                                                                 AS ItemProductName,
       pm.Name                                                                    AS PackingMaterialName,
       ROUND(COALESCE(ppa.PriceStd, pp.PriceStd), coalesce(pl.priceprecision, 2)) AS PriceStd,
       ROUND(pp2.PriceStd, coalesce(pl2.priceprecision, 2))                       AS AltPriceStd,
       CASE WHEN pp2.PriceStd IS NULL THEN 0 ELSE 1 END                           AS hasaltprice,
       uom.UOMSymbol,
       COALESCE(ppa.Attributes, '')                                               as attributes,
       pp.seqNo,

       -- Filter Columns
       bp.C_BPartner_ID,
       plv.M_Pricelist_Version_ID,
       plv2.M_Pricelist_Version_ID                                                AS Alt_PriceList_Version_ID,

       -- Additional internal infos to be used
       pp.M_ProductPrice_ID,
       ppa.m_attributesetinstance_ID,
--        pp.m_hu_pi_item_product_id                                                 as PP_M_HU_PI_Item_Product_ID,
       pp.M_HU_PI_Item_Product_ID                                                 as M_HU_PI_Item_Product_ID,
       uom.X12DE355                                                               as UOM_X12DE355,
       hupip.Qty                                                                  as QtyCUsPerTU,
       it.m_hu_pi_version_id                                                      AS m_hu_pi_version_id,
       c.iso_code                                                                 as currency,
       c2.iso_code                                                                as currency2

FROM M_ProductPrice pp

         INNER JOIN M_Product p ON pp.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

    /** Get all BPartner and Product combinations.
     * IMPORTANT: Never use the query without BPartner Filter active
     */
         INNER JOIN C_BPartner bp ON TRUE
         INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
         INNER JOIN C_UOM uom ON pp.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'


    /*
      * We know if there are packing instructions limited to the BPartner/product-combination. If so,
      * we will use only those. If not, we will use only the non limited ones
      */
         LEFT OUTER JOIN LATERAL
    (
    SELECT vip.M_HU_PI_Item_Product_ID, vip.hasPartner
    FROM Report.Valid_PI_Item_Product_V vip
        /* WHERE isInfiniteCapacity = 'N' task 09045/09788: we can also export PiiPs with infinite capacity */
    WHERE p.M_Product_ID = vip.M_Product_ID

      AND CASE
              WHEN
                  EXISTS(select 0 from Report.Valid_PI_Item_Product_V v where p.M_Product_ID = v.M_Product_ID AND v.hasPartner is true and bp.C_BPartner_ID = v.C_BPartner_ID)
                  THEN vip.C_BPartner_ID = bp.C_BPartner_ID
              else vip.C_BPartner_ID IS NULL END
    ) bp_ip ON TRUE

         LEFT OUTER JOIN LATERAL
    (
    SELECT M_ProductPrice_ID, M_Attributesetinstance_ID, PriceStd, IsActive, M_HU_PI_Item_Product_ID, Attributes, Signature
    FROM report.fresh_AttributePrice ppa
    WHERE ppa.isActive = 'Y'
      AND ppa.M_ProductPrice_ID = pp.M_ProductPrice_ID
      AND (ppa.m_hu_pi_item_product_id = bp_ip.m_hu_pi_item_product_id OR ppa.m_hu_pi_item_product_id IS NULL)
      AND ppa.m_pricelist_version_id = pp.m_pricelist_version_id
    ) ppa on true

         LEFT OUTER JOIN m_hu_pi_item_product hupip ON pp.m_hu_pi_item_product_ID = hupip.m_hu_pi_item_product_id and hupip.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item it ON hupip.M_HU_PI_Item_ID = it.M_HU_PI_Item_ID AND it.isActive = 'Y'
         LEFT OUTER JOIN m_hu_pi_item pmit ON it.m_hu_pi_version_id = pmit.m_hu_pi_version_id AND pmit.itemtype::TEXT = 'PM'::TEXT AND pmit.isActive = 'Y'
         LEFT OUTER JOIN m_hu_packingmaterial pm ON pmit.m_hu_packingmaterial_id = pm.m_hu_packingmaterial_id AND pm.isActive = 'Y'

         INNER JOIN M_PriceList_Version plv ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID AND plv.IsActive = 'Y'
    /*
     Get Comparison Prices
    */

    /* Get all PriceList_Versions of the PriceList (we need all available PriceList_Version_IDs for outside filtering)
     limited to the same PriceList because the Parameter validation rule is enforcing this */
         LEFT JOIN M_PriceList_Version plv2 ON plv.M_PriceList_ID = plv2.M_PriceList_ID AND plv2.IsActive = 'Y'
         LEFT OUTER JOIN LATERAL (
    SELECT COALESCE(ppa2.PriceStd, pp2.PriceStd) AS PriceStd, ppa2.signature
    FROM M_ProductPrice pp2
             /* Joining attribute prices */
             INNER JOIN report.fresh_AttributePrice ppa2 ON pp2.M_ProductPrice_ID = ppa2.M_ProductPrice_ID AND ppa2.m_pricelist_version_id = pp2.m_pricelist_version_id

    WHERE p.M_Product_ID = pp2.M_Product_ID
      AND pp2.M_Pricelist_Version_ID = plv2.M_Pricelist_Version_ID
      AND pp2.IsActive = 'Y'
      AND (pp2.m_hu_pi_item_product_ID = pp.m_hu_pi_item_product_ID OR (pp2.m_hu_pi_item_product_ID is null and pp.m_hu_pi_item_product_ID is null))
      AND pp2.isAttributeDependant = pp.isAttributeDependant
      --avoid comparing different prices in same pricelist
      AND (CASE WHEN pp2.M_PriceList_Version_ID = pp.M_PriceList_Version_ID THEN pp2.M_ProductPrice_ID = pp.M_ProductPrice_ID ELSE TRUE END)
        /* we have to make sure that only prices with the same attributes and packing instructions are compared. Note:
        * - If there is an Existing Attribute Price but no signature related columns are filled the signature will be ''
        * - If there are no Attribute Prices the signature will be null
        * This is important, because otherwise an empty attribute price will be compared to the regular price AND the alternate attribute price */
      AND (ppa.signature = ppa2.signature)
      AND ppa2.IsActive = 'Y'
      AND (ppa2.m_hu_pi_item_product_id = bp_ip.m_hu_pi_item_product_id OR ppa2.m_hu_pi_item_product_id IS NULL)
    ) pp2 ON true

         INNER JOIN M_Pricelist pl ON plv.M_Pricelist_ID = pl.M_PriceList_ID AND pl.isActive = 'Y'
         LEFT JOIN M_Pricelist pl2 ON plv2.M_PriceList_ID = pl2.M_Pricelist_ID AND pl2.isActive = 'Y'
         INNER JOIN C_Currency c ON pl.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
         LEFT JOIN C_Currency c2 ON pl2.C_Currency_ID = c2.C_CUrrency_ID AND c2.isActive = 'Y'

WHERE pp.isActive = 'Y'

  AND (pp.M_Attributesetinstance_ID = ppa.M_Attributesetinstance_ID OR pp.M_Attributesetinstance_ID is null)
  AND (pp.M_HU_PI_Item_Product_ID = bp_ip.M_HU_PI_Item_Product_ID OR pp.M_HU_PI_Item_Product_ID is null)

  AND (case when plv2.M_PriceList_Version_ID = plv.M_PriceList_Version_ID THEN ppa.signature = pp2.signature ELSE true end)

GROUP BY pp.M_ProductPrice_ID, pp.AD_Client_ID, pp.Created, pp.CreatedBy, pp.Updated, pp.UpdatedBy, pp.IsActive, pc.Name, pc.value, p.M_Product_ID, p.Value, p.Name, pp.IsSeasonFixedPrice, hupip.Name, pm.Name, ROUND(COALESCE(ppa.PriceStd, pp.PriceStd), coalesce(pl.priceprecision, 2)), ROUND(pp2.PriceStd, coalesce(pl2.priceprecision, 2)), CASE WHEN pp2.PriceStd IS NULL THEN 0 ELSE 1 END,
         uom.UOMSymbol, COALESCE(ppa.Attributes, ''), pp.seqNo, bp.C_BPartner_ID, plv.M_Pricelist_Version_ID, plv2.M_Pricelist_Version_ID, pp.AD_Org_ID, ppa.m_attributesetinstance_ID, pp.M_HU_PI_Item_Product_ID, uom.X12DE355, hupip.Qty, it.m_hu_pi_version_id, c.iso_code, c2.iso_code
;

DROP FUNCTION IF EXISTS report.fresh_PriceList_Details_Report(numeric, numeric, numeric, character varying)
;
DROP FUNCTION IF EXISTS report.fresh_PriceList_Details_Report(numeric, numeric, numeric, character varying, text)
;

CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_report(IN p_c_bpartner_id numeric,
                                                                 IN p_m_pricelist_version_id numeric,
                                                                 IN p_alt_pricelist_version_id numeric,
                                                                 IN p_ad_language character varying,
                                                                 IN p_show_product_price_pi_flag text)


    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                productcategory            text,
                m_product_id               integer,
                value                      text,
                customerproductnumber      text,
                productname                text,
                isseasonfixedprice         character,
                itemproductname            character varying,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                altpricestd                numeric,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   character(3),
                currency2                  character(3),
                show_product_price_pi_flag text
            )
AS
$BODY$
    --
SELECT --
       bp.value                                                                                             AS BP_Value,
       bp.name                                                                                              AS BP_Name,
       plc.ProductCategory,
       plc.M_Product_ID::integer,
       plc.Value,
       bpp.ProductNo                                                                                        AS CustomerProductNumber,
       COALESCE(pt.name, plc.ProductName)                                                                   AS ProductName,
       plc.IsSeasonFixedPrice,
       CASE WHEN plc.m_hu_pi_version_id = 101 THEN NULL ELSE plc.ItemProductName END                        AS ItemProductName,
       plc.QtyCUsPerTU,
       plc.PackingMaterialName,
       plc.PriceStd,
       plc.AltPriceStd,
       plc.HasAltPrice,
       plc.UOMSymbol,
       plc.UOM_X12DE355::text,
       CASE WHEN p_ad_language = 'fr_CH' THEN Replace(plc.Attributes, 'AdR', 'DLR') ELSE plc.Attributes END AS Attributes,
       plc.M_ProductPrice_ID::integer,
       plc.M_AttributeSetInstance_ID::integer,
       plc.M_HU_PI_Item_Product_ID::integer,
       plc.currency                                                                                         AS currency,
       plc.currency2                                                                                        AS currency2,
       p_show_product_price_pi_flag                                                                         as show_product_price_pi_flag

FROM RV_fresh_PriceList_Comparison plc
         LEFT OUTER JOIN M_Product_Trl pt ON plc.M_Product_ID = pt.M_Product_ID AND AD_Language = p_ad_language AND pt.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner bp ON plc.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND plc.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
WHERE TRUE
  AND plc.C_BPartner_ID = p_c_bpartner_id
  AND plc.M_Pricelist_Version_ID = p_m_pricelist_version_id
  AND plc.Alt_Pricelist_Version_ID = coalesce(p_alt_pricelist_version_id, plc.m_pricelist_version_id)
  AND CASE
          WHEN p_alt_pricelist_version_id IS NOT NULL
              THEN PriceStd != 0
          ELSE PriceStd != 0 OR AltPriceStd != 0
    END

ORDER BY plc.ProductCategoryValue,
         plc.ProductName,
         plc.seqNo,
         plc.attributes,
         plc.ItemProductName;
--
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000
;

DROP FUNCTION IF EXISTS report.fresh_PriceList_Details_Report_With_PP_PI(numeric, numeric, numeric, character varying, text)
;

CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_report_With_PP_PI(IN p_c_bpartner_id numeric,
                                                                            IN p_m_pricelist_version_id numeric,
                                                                            IN p_alt_pricelist_version_id numeric,
                                                                            IN p_ad_language character varying,
                                                                            IN p_show_product_price_pi_flag text)
    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                productcategory            text,
                m_product_id               integer,
                value                      text,
                customerproductnumber      text,
                productname                text,
                isseasonfixedprice         character,
                itemproductname            character varying,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                altpricestd                numeric,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   character(3),
                currency2                  character(3),
                show_product_price_pi_flag text
            )
AS
$BODY$
    --
SELECT --
       bp.value                                                                                             AS BP_Value,
       bp.name                                                                                              AS BP_Name,
       plc.ProductCategory,
       plc.M_Product_ID::integer,
       plc.Value,
       bpp.ProductNo                                                                                        AS CustomerProductNumber,
       COALESCE(pt.name, plc.ProductName)                                                                   AS ProductName,
       plc.IsSeasonFixedPrice,
       CASE WHEN plc.m_hu_pi_version_id = 101 THEN NULL ELSE plc.ItemProductName END                        AS ItemProductName,
       plc.QtyCUsPerTU,
       plc.PackingMaterialName,
       plc.PriceStd,
       plc.AltPriceStd,
       plc.HasAltPrice,
       plc.UOMSymbol,
       plc.UOM_X12DE355::text,
       CASE WHEN p_ad_language = 'fr_CH' THEN Replace(plc.Attributes, 'AdR', 'DLR') ELSE plc.Attributes END AS Attributes,
       plc.M_ProductPrice_ID::integer,
       plc.M_AttributeSetInstance_ID::integer,
       plc.M_HU_PI_Item_Product_ID::integer,
       plc.currency                                                                                         AS currency,
       plc.currency2                                                                                        AS currency2,
       p_show_product_price_pi_flag                                                                         as show_product_price_pi_flag

FROM RV_fresh_PriceList_Comparison_With_PP_PI plc
         LEFT OUTER JOIN M_Product_Trl pt ON plc.M_Product_ID = pt.M_Product_ID AND AD_Language = p_ad_language AND pt.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner bp ON plc.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND plc.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
WHERE TRUE
  AND plc.C_BPartner_ID = p_c_bpartner_id
  AND plc.M_Pricelist_Version_ID = p_m_pricelist_version_id
  AND plc.Alt_Pricelist_Version_ID = coalesce(p_alt_pricelist_version_id, plc.m_pricelist_version_id)
  AND CASE
          WHEN p_alt_pricelist_version_id IS NOT NULL
              THEN PriceStd != 0
          ELSE PriceStd != 0 OR AltPriceStd != 0
    END

ORDER BY plc.ProductCategoryValue,
         plc.ProductName,
         plc.seqNo,
         plc.attributes,
         plc.ItemProductName;
--
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000
;

DROP FUNCTION IF EXISTS report.fresh_pricelist_details_template_report(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC);
DROP FUNCTION IF EXISTS report.fresh_pricelist_details_template_report(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC, text);
CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report(IN p_c_bpartner_id numeric, IN p_m_pricelist_version_id numeric, IN p_ad_language character varying,
                                                                          IN p_c_bpartner_location_id numeric, IN p_show_product_price_pi_flag text)
    RETURNS TABLE
            (
                prodvalue                  text,
                customerproductnumber      text,
                productcategory            text,
                productname                text,
                attributes                 text,
                itemproductname            text,
                qty                        numeric,
                uomsymbol                  text,
                pricestd                   text,
                m_productprice_id          integer,
                c_bpartner_id              numeric,
                m_hu_pi_item_product_id    integer,
                uom_x12de355               text,
                c_bpartner_location_id     numeric,
                qtycuspertu                numeric,
                m_product_id               integer,
                bp_value                   text,
                bp_name                    text,
                reportfilename             text,
                show_product_price_pi_flag text
            )

AS
$BODY$
--

SELECT plc.value                                                                                                          AS prodvalue,
       plc.customerproductnumber                                                                                          as customerproductnumber,
       plc.productcategory                                                                                                as productcategory,
       plc.productname                                                                                                    as productname,
       plc.attributes                                                                                                     as attributes,
       replace(hupip.name, hupiv.name, pi.Name)                                                                           as itemproductname,
       NULL::numeric                                                                                                      as qty,
       plc.uomsymbol                                                                                                      as uomsymbol,
       to_char(plc.pricestd, getPricePattern(prl.priceprecision::integer))                                                as pricestd,
       plc.M_ProductPrice_ID                                                                                              as m_productprice_id,
       p_c_bpartner_id                                                                                                    as c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID                                                                                        as m_hu_pi_item_product_id,
       case when plc.m_hu_pi_item_product_id is not null then 'COLI' else plc.uom_x12de355 end                            as uom_x12de355,
       p_c_bpartner_location_id                                                                                           as c_bpartner_location_id,
       plc.qtycuspertu                                                                                                    as qtycuspertu,
       plc.m_product_id                                                                                                   as m_product_id,
       plc.BP_Value                                                                                                       as bp_value,
       plc.BP_Name                                                                                                        as bp_name,
       CONCAT(bp_value, '_', bp_name, '_', case when prlv.isactive = 'Y' then prlv.validfrom::date else null end, '.xls') as reportfilename,
       p_show_product_price_pi_flag                                                                                       as show_product_price_pi_flag

FROM report.fresh_PriceList_Details_Report(p_c_bpartner_id, p_m_pricelist_version_id, NULL, p_ad_language, p_show_product_price_pi_flag) plc
         LEFT OUTER JOIN M_HU_PI_Item_Product hupip on hupip.M_HU_PI_Item_Product_ID = plc.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item hupii on hupii.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID
         LEFT OUTER JOIN M_HU_PI_Version hupiv on hupiv.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID
         LEFT OUTER JOIN M_HU_PI pi on pi.M_HU_PI_ID = hupiv.M_HU_PI_ID

         LEFT OUTER JOIN M_Pricelist_Version prlv on prlv.m_pricelist_version_id = p_m_pricelist_version_id
         LEFT OUTER JOIN M_Pricelist prl on prlv.m_pricelist_id = prl.m_pricelist_id
         LEFT OUTER JOIN M_HU_PackingMaterial pmt on plc.m_product_id = pmt.m_product_id
         LEFT OUTER JOIN M_ProductPrice ppr on ppr.m_product_id = pmt.m_product_id


$BODY$
    LANGUAGE sql STABLE;

DROP FUNCTION IF EXISTS report.fresh_pricelist_details_template_report_With_PP_PI(NUMERIC, NUMERIC, CHARACTER VARYING, NUMERIC, text);
CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report_With_PP_PI(IN p_c_bpartner_id numeric, IN p_m_pricelist_version_id numeric, IN p_ad_language character varying,
                                                                                     IN p_c_bpartner_location_id numeric, IN p_show_product_price_pi_flag text)
    RETURNS TABLE
            (
                prodvalue                  text,
                customerproductnumber      text,
                productcategory            text,
                productname                text,
                attributes                 text,
                itemproductname            text,
                qty                        numeric,
                uomsymbol                  text,
                pricestd                   text,
                m_productprice_id          integer,
                c_bpartner_id              numeric,
                m_hu_pi_item_product_id    integer,
                uom_x12de355               text,
                c_bpartner_location_id     numeric,
                qtycuspertu                numeric,
                m_product_id               integer,
                bp_value                   text,
                bp_name                    text,
                reportfilename             text,
                show_product_price_pi_flag text
            )

AS
$BODY$
--

SELECT plc.value                                                                                                          AS prodvalue,
       plc.customerproductnumber                                                                                          as customerproductnumber,
       plc.productcategory                                                                                                as productcategory,
       plc.productname                                                                                                    as productname,
       plc.attributes                                                                                                     as attributes,
       replace(hupip.name, hupiv.name, pi.Name)                                                                           as itemproductname,
       NULL::numeric                                                                                                      as qty,
       plc.uomsymbol                                                                                                      as uomsymbol,
       to_char(plc.pricestd, getPricePattern(prl.priceprecision::integer))                                                as pricestd,
       plc.M_ProductPrice_ID                                                                                              as m_productprice_id,
       p_c_bpartner_id                                                                                                    as c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID                                                                                        as m_hu_pi_item_product_id,
       case when plc.m_hu_pi_item_product_id is not null then 'COLI' else plc.uom_x12de355 end                            as uom_x12de355,
       p_c_bpartner_location_id                                                                                           as c_bpartner_location_id,
       plc.qtycuspertu                                                                                                    as qtycuspertu,
       plc.m_product_id                                                                                                   as m_product_id,
       plc.BP_Value                                                                                                       as bp_value,
       plc.BP_Name                                                                                                        as bp_name,
       CONCAT(bp_value, '_', bp_name, '_', case when prlv.isactive = 'Y' then prlv.validfrom::date else null end, '.xls') as reportfilename,
       p_show_product_price_pi_flag                                                                                       as show_product_price_pi_flag

FROM report.fresh_PriceList_Details_Report_With_PP_PI(p_c_bpartner_id, p_m_pricelist_version_id, NULL, p_ad_language, p_show_product_price_pi_flag) plc
         LEFT OUTER JOIN M_HU_PI_Item_Product hupip on hupip.M_HU_PI_Item_Product_ID = plc.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item hupii on hupii.M_HU_PI_Item_ID = hupip.M_HU_PI_Item_ID
         LEFT OUTER JOIN M_HU_PI_Version hupiv on hupiv.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID
         LEFT OUTER JOIN M_HU_PI pi on pi.M_HU_PI_ID = hupiv.M_HU_PI_ID

         LEFT OUTER JOIN M_Pricelist_Version prlv on prlv.m_pricelist_version_id = p_m_pricelist_version_id
         LEFT OUTER JOIN M_Pricelist prl on prlv.m_pricelist_id = prl.m_pricelist_id
         LEFT OUTER JOIN M_HU_PackingMaterial pmt on plc.m_product_id = pmt.m_product_id
         LEFT OUTER JOIN M_ProductPrice ppr on ppr.m_product_id = pmt.m_product_id


$BODY$
    LANGUAGE sql STABLE;

DROP FUNCTION IF EXISTS report.Current_Vs_Previous_Pricelist_Comparison_Report(p_C_BPartner_ID numeric, p_C_BP_Group_ID numeric, p_IsSoTrx text, p_AD_Language text)
;
DROP FUNCTION IF EXISTS report.Current_Vs_Previous_Pricelist_Comparison_Report(p_C_BPartner_ID numeric, p_C_BP_Group_ID numeric, p_IsSoTrx text, p_AD_Language text, p_show_product_price_pi_flag text)
;

CREATE OR REPLACE FUNCTION report.Current_Vs_Previous_Pricelist_Comparison_Report(p_C_BPartner_ID numeric = NULL,
                                                                                  p_C_BP_Group_ID numeric = NULL,
                                                                                  p_IsSoTrx text = 'Y',
                                                                                  p_AD_Language TEXT = 'en_US',
                                                                                  p_show_product_price_pi_flag text = 'Y')
    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                ProductCategory            text,
                M_Product_ID               integer,
                value                      text,
                CustomerProductNumber      text,
                ProductName                text,
                IsSeasonFixedPrice         text,
                ItemProductName            text,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                altpricestd                numeric,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                Attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   text,
                currency2                  text,
                validFromPLV1              timestamp,
                validFromPLV2              timestamp,
                namePLV1                   text,
                namePLV2                   text,
                c_bpartner_location_id     numeric,
                AD_Org_ID                  numeric,
                show_product_price_pi_flag text
            )
AS
$$
WITH PriceListVersionsByValidFrom AS
         (
             SELECT t.*
             FROM (SELECT --
                          plv.c_bpartner_id,
                          plv.m_pricelist_version_id,
                          plv.validfrom,
                          plv.name,
                          row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom DESC, plv.m_pricelist_version_id DESC) rank
                   FROM Report.Fresh_PriceList_Version_Val_Rule plv
                   WHERE TRUE
                     AND plv.validfrom <= now()
                     AND plv.issotrx = p_IsSoTrx
                     AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                     AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID))
                   ORDER BY plv.validfrom DESC,
                            plv.m_pricelist_version_id DESC) t
             WHERE t.rank <= 2
         ),
     currentAndPreviousPLV AS
         (
             -- implementation detail: all these sub-selects would be better implemented with a pivot. Unfortunately i cant understand how pivots work.
             SELECT DISTINCT --
                             plvv.c_bpartner_id,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV1_ID,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV2_ID,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV1,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV2,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV1,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV2
             FROM PriceListVersionsByValidFrom plvv
             ORDER BY plvv.c_bpartner_id
         ),
     result AS
         (
             SELECT t.*,
                    plv.validFromPLV1,
                    plv.validFromPLV2,
                    plv.namePLV1,
                    plv.namePLV2,
                    (SELECT bpl.c_bpartner_location_id FROM c_bpartner_location bpl WHERE bpl.c_bpartner_id = plv.c_bpartner_id ORDER BY bpl.isbilltodefault DESC LIMIT 1) c_bpartner_location_id,
                    (SELECT plv2.ad_org_id FROM m_pricelist_version plv2 WHERE plv2.m_pricelist_version_id = plv.PLV1_ID)                                                  AD_Org_ID
             FROM currentAndPreviousPLV plv
                      INNER JOIN LATERAL report.fresh_PriceList_Details_Report(
                     plv.c_bpartner_id,
                     plv.PLV1_ID,
                     plv.PLV2_ID,
                     p_AD_Language,
                     p_show_product_price_pi_flag
                 ) AS t ON TRUE
         )
SELECT --
       r.bp_value,
       r.bp_name,
       r.productcategory,
       r.m_product_id,
       r.value,
       r.customerproductnumber,
       r.productname,
       r.isseasonfixedprice::text,
       r.itemproductname,
       r.qtycuspertu,
       r.packingmaterialname,
       r.pricestd,
       r.altpricestd,
       r.hasaltprice,
       r.uomsymbol,
       r.uom_x12de355,
       r.attributes,
       r.m_productprice_id,
       r.m_attributesetinstance_id,
       r.m_hu_pi_item_product_id,
       r.currency::text,
       r.currency2::text,
       r.validFromPLV1,
       r.validFromPLV2,
       r.namePLV1,
       r.namePLV2,
       r.c_bpartner_location_id,
       r.AD_Org_ID,
       p_show_product_price_pi_flag as show_product_price_pi_flag
FROM result r
ORDER BY r.bp_value,
         r.productCategory,
         r.value
$$
    LANGUAGE sql STABLE
;


/*
How to run

- All Bpartners

SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report()
;

- Specific Bpartner

SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report(2156515)
;
 */

DROP FUNCTION IF EXISTS report.Current_Vs_Previous_Pricelist_Comparison_Report_With_PP_PI(p_C_BPartner_ID numeric, p_C_BP_Group_ID numeric, p_IsSoTrx text, p_AD_Language text, p_show_product_price_pi_flag text)
;

CREATE OR REPLACE FUNCTION report.Current_Vs_Previous_Pricelist_Comparison_Report_With_PP_PI(p_C_BPartner_ID numeric = NULL,
                                                                                             p_C_BP_Group_ID numeric = NULL,
                                                                                             p_IsSoTrx text = 'Y',
                                                                                             p_AD_Language TEXT = 'en_US',
                                                                                             p_show_product_price_pi_flag text = 'Y')


    RETURNS TABLE
            (
                bp_value                   text,
                bp_name                    text,
                ProductCategory            text,
                M_Product_ID               integer,
                value                      text,
                CustomerProductNumber      text,
                ProductName                text,
                IsSeasonFixedPrice         text,
                ItemProductName            text,
                qtycuspertu                numeric,
                packingmaterialname        text,
                pricestd                   numeric,
                altpricestd                numeric,
                hasaltprice                integer,
                uomsymbol                  text,
                uom_x12de355               text,
                Attributes                 text,
                m_productprice_id          integer,
                m_attributesetinstance_id  integer,
                m_hu_pi_item_product_id    integer,
                currency                   text,
                currency2                  text,
                validFromPLV1              timestamp,
                validFromPLV2              timestamp,
                namePLV1                   text,
                namePLV2                   text,
                c_bpartner_location_id     numeric,
                AD_Org_ID                  numeric,
                show_product_price_pi_flag text
            )
AS
$$
WITH PriceListVersionsByValidFrom AS
         (
             SELECT t.*
             FROM (SELECT --
                          plv.c_bpartner_id,
                          plv.m_pricelist_version_id,
                          plv.validfrom,
                          plv.name,
                          row_number() OVER (PARTITION BY plv.c_bpartner_id ORDER BY plv.validfrom DESC, plv.m_pricelist_version_id DESC) rank
                   FROM Report.Fresh_PriceList_Version_Val_Rule plv
                   WHERE TRUE
                     AND plv.validfrom <= now()
                     AND plv.issotrx = p_IsSoTrx
                     AND (p_C_BPartner_ID IS NULL OR plv.c_bpartner_id = p_C_BPartner_ID)
                     AND (p_C_BP_Group_ID IS NULL OR plv.c_bpartner_id IN (SELECT DISTINCT b.c_bpartner_id FROM c_bpartner b WHERE b.c_bp_group_id = p_C_BP_Group_ID))
                   ORDER BY plv.validfrom DESC,
                            plv.m_pricelist_version_id DESC) t
             WHERE t.rank <= 2
         ),
     currentAndPreviousPLV AS
         (
             -- implementation detail: all these sub-selects would be better implemented with a pivot. Unfortunately i cant understand how pivots work.
             SELECT DISTINCT --
                             plvv.c_bpartner_id,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV1_ID,
                             (SELECT plvv2.m_pricelist_version_id FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id) PLV2_ID,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV1,
                             (SELECT plvv2.validfrom FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)              validFromPLV2,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 1 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV1,
                             (SELECT plvv2.name FROM PriceListVersionsByValidFrom plvv2 WHERE plvv2.rank = 2 AND plvv2.c_bpartner_id = plvv.c_bpartner_id)                   namePLV2
             FROM PriceListVersionsByValidFrom plvv
             ORDER BY plvv.c_bpartner_id
         ),
     result AS
         (
             SELECT t.*,
                    plv.validFromPLV1,
                    plv.validFromPLV2,
                    plv.namePLV1,
                    plv.namePLV2,
                    (SELECT bpl.c_bpartner_location_id FROM c_bpartner_location bpl WHERE bpl.c_bpartner_id = plv.c_bpartner_id ORDER BY bpl.isbilltodefault DESC LIMIT 1) c_bpartner_location_id,
                    (SELECT plv2.ad_org_id FROM m_pricelist_version plv2 WHERE plv2.m_pricelist_version_id = plv.PLV1_ID)                                                  AD_Org_ID
             FROM currentAndPreviousPLV plv
                      INNER JOIN LATERAL report.fresh_PriceList_Details_Report_With_PP_PI(
                     plv.c_bpartner_id,
                     plv.PLV1_ID,
                     plv.PLV2_ID,
                     p_AD_Language,
                     p_show_product_price_pi_flag
                 ) AS t ON TRUE
         )
SELECT --
       r.bp_value,
       r.bp_name,
       r.productcategory,
       r.m_product_id,
       r.value,
       r.customerproductnumber,
       r.productname,
       r.isseasonfixedprice::text,
       r.itemproductname,
       r.qtycuspertu,
       r.packingmaterialname,
       r.pricestd,
       r.altpricestd,
       r.hasaltprice,
       r.uomsymbol,
       r.uom_x12de355,
       r.attributes,
       r.m_productprice_id,
       r.m_attributesetinstance_id,
       r.m_hu_pi_item_product_id,
       r.currency::text,
       r.currency2::text,
       r.validFromPLV1,
       r.validFromPLV2,
       r.namePLV1,
       r.namePLV2,
       r.c_bpartner_location_id,
       r.AD_Org_ID,
       p_show_product_price_pi_flag as show_product_price_pi_flag
FROM result r
ORDER BY r.bp_value,
         r.productCategory,
         r.value
$$
    LANGUAGE sql STABLE
;


/*
How to run

- All Bpartners

SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report_With_PP_PI()
;

- Specific Bpartner

SELECT *
FROM report.Current_Vs_Previous_Pricelist_Comparison_Report_With_PP_PI(2156515)
;
 */


