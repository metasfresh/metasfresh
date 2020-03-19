-- 2020-03-13T09:04:39.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Table_ID, AD_Table_Process_ID, Created, CreatedBy, EntityType, IsActive, Updated, UpdatedBy, WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (0, 0, 540780, 319, 540796, TO_TIMESTAMP('2020-03-13 11:04:39', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', TO_TIMESTAMP('2020-03-13 11:04:39', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N')
;

-- 2020-03-13T09:13:10.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process
SET EntityType='de.metas.ui.web',
    Updated=TO_TIMESTAMP('2020-03-13 11:13:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Table_Process_ID = 540796
;

-- 2020-03-13T13:20:05.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y',
    SeqNoGrid=140,
    Updated=TO_TIMESTAMP('2020-03-13 15:20:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:20:57.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='N',
    SeqNoGrid=0,
    Updated=TO_TIMESTAMP('2020-03-13 15:20:57', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:22:35.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='N',
    Updated=TO_TIMESTAMP('2020-03-13 15:22:35', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:22:55.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y',
    SeqNoGrid=140,
    Updated=TO_TIMESTAMP('2020-03-13 15:22:55', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545387
;

-- 2020-03-13T13:23:49.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y',
    SeqNoGrid=150,
    Updated=TO_TIMESTAMP('2020-03-13 15:23:49', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:24:49.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='N',
    SeqNoGrid=0,
    Updated=TO_TIMESTAMP('2020-03-13 15:24:49', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545387
;

-- 2020-03-13T13:25:19.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID=540047,
    SeqNo=30,
    Updated=TO_TIMESTAMP('2020-03-13 15:25:19', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:25:32.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID=540047,
    SeqNo=40,
    Updated=TO_TIMESTAMP('2020-03-13 15:25:32', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545387
;

-- 2020-03-13T13:25:53.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='N',
    Updated=TO_TIMESTAMP('2020-03-13 15:25:53', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545387
;

-- 2020-03-13T13:26:49.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_Column_ID, AD_UI_ElementGroup_ID, Created, CreatedBy, IsActive, Name, SeqNo, Updated, UpdatedBy)
VALUES (0, 0, 540035, 543527, TO_TIMESTAMP('2020-03-13 15:26:49', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'erg', 40, TO_TIMESTAMP('2020-03-13 15:26:49', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-03-13T13:27:04.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID=543527,
    SeqNo=10,
    Updated=TO_TIMESTAMP('2020-03-13 15:27:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:27:20.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID=543527,
    SeqNo=20,
    Updated=TO_TIMESTAMP('2020-03-13 15:27:20', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545387
;

-- 2020-03-13T13:27:34.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='N',
    SeqNoGrid=0,
    Updated=TO_TIMESTAMP('2020-03-13 15:27:34', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:28:29.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID=543527,
    SeqNo=30,
    Updated=TO_TIMESTAMP('2020-03-13 15:28:29', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545388
;

-- 2020-03-13T13:28:45.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='Y',
    Updated=TO_TIMESTAMP('2020-03-13 15:28:45', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:28:46.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='Y',
    Updated=TO_TIMESTAMP('2020-03-13 15:28:46', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545387
;

-- 2020-03-13T13:29:15.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='N',
    Updated=TO_TIMESTAMP('2020-03-13 15:29:15', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:29:16.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='N',
    Updated=TO_TIMESTAMP('2020-03-13 15:29:16', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545388
;

-- 2020-03-13T13:29:16.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='N',
    Updated=TO_TIMESTAMP('2020-03-13 15:29:16', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545387
;

-- 2020-03-13T13:29:18.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='Y',
    Updated=TO_TIMESTAMP('2020-03-13 15:29:18', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;

-- 2020-03-13T13:29:36.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='N',
    Updated=TO_TIMESTAMP('2020-03-13 15:29:36', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_UI_Element_ID = 545386
;
-- 2020-03-16T10:22:22.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Result',
    PrintName='Result',
    Updated=TO_TIMESTAMP('2020-03-16 12:22:22', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 431
  AND AD_Language = 'fr_CH'
;

-- 2020-03-16T10:22:22.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(431, 'fr_CH')
;

-- 2020-03-16T10:22:27.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Result',
    PrintName='Result',
    Updated=TO_TIMESTAMP('2020-03-16 12:22:27', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 431
  AND AD_Language = 'it_CH'
;

-- 2020-03-16T10:22:27.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(431, 'it_CH')
;

-- 2020-03-16T10:22:35.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Result',
    PrintName='Result',
    Updated=TO_TIMESTAMP('2020-03-16 12:22:35', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 431
  AND AD_Language = 'en_GB'
;

-- 2020-03-16T10:22:35.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(431, 'en_GB')
;

-- 2020-03-16T10:22:47.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Ergebnis',
    PrintName='Ergebnis',
    Updated=TO_TIMESTAMP('2020-03-16 12:22:47', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 431
  AND AD_Language = 'de_CH'
;

-- 2020-03-16T10:22:47.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(431, 'de_CH')
;

-- 2020-03-16T10:22:59.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Result',
    PrintName='Result',
    Updated=TO_TIMESTAMP('2020-03-16 12:22:59', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 431
  AND AD_Language = 'en_US'
;

-- 2020-03-16T10:22:59.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(431, 'en_US')
;

-- 2020-03-16T10:23:09.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Ergebnis',
    PrintName='Ergebnis',
    Updated=TO_TIMESTAMP('2020-03-16 12:23:09', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 431
  AND AD_Language = 'nl_NL'
;

-- 2020-03-16T10:23:09.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(431, 'nl_NL')
;
-- 2020-03-16T10:33:30.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Ergebnis',
    PrintName='Ergebnis',
    Updated=TO_TIMESTAMP('2020-03-16 12:33:30', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 431
  AND AD_Language = 'de_DE'
;

-- 2020-03-16T10:33:30.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(431, 'de_DE')
;

-- 2020-03-16T10:33:30.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_ad_element_on_ad_element_trl_update(431, 'de_DE')
;

-- 2020-03-16T10:33:30.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnName='LastResult',
    Name='Ergebnis',
    Description='Result of last contact',
    Help='The Last Result identifies the result of the last contact made.'
WHERE AD_Element_ID = 431
;

-- 2020-03-16T10:33:30.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='LastResult',
    Name='Ergebnis',
    Description='Result of last contact',
    Help='The Last Result identifies the result of the last contact made.',
    AD_Element_ID=431
WHERE UPPER(ColumnName) = 'LASTRESULT'
  AND IsCentrallyMaintained = 'Y'
  AND AD_Element_ID IS NULL
;

-- 2020-03-16T10:33:30.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='LastResult',
    Name='Ergebnis',
    Description='Result of last contact',
    Help='The Last Result identifies the result of the last contact made.'
WHERE AD_Element_ID = 431
  AND IsCentrallyMaintained = 'Y'
;

-- 2020-03-16T10:33:30.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET Name='Ergebnis',
    Description='Result of last contact',
    Help='The Last Result identifies the result of the last contact made.'
WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID = 431) AND AD_Name_ID IS NULL)
   OR (AD_Name_ID = 431)
;

-- 2020-03-16T10:33:30.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi
SET PrintName='Ergebnis',
    Name='Ergebnis'
WHERE IsCentrallyMaintained = 'Y'
  AND EXISTS(SELECT * FROM AD_Column c WHERE c.AD_Column_ID = pi.AD_Column_ID AND c.AD_Element_ID = 431)
;

-- 2020-03-16T10:33:30.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab
SET Name='Ergebnis',
    Description='Result of last contact',
    Help='The Last Result identifies the result of the last contact made.',
    CommitWarning = NULL
WHERE AD_Element_ID = 431
;

-- 2020-03-16T10:33:30.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW
SET Name='Ergebnis',
    Description='Result of last contact',
    Help='The Last Result identifies the result of the last contact made.'
WHERE AD_Element_ID = 431
;

-- 2020-03-16T10:33:30.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu
SET Name                    = 'Ergebnis',
    Description             = 'Result of last contact',
    WEBUI_NameBrowse        = NULL,
    WEBUI_NameNew           = NULL,
    WEBUI_NameNewBreadcrumb = NULL
WHERE AD_Element_ID = 431
;
