-- 2020-02-06T13:51:45.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, Description, EntityType, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRangeFilter, IsSelectionColumn, IsShowFilterIncrementButtons, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, SelectionColumnSeqNo, Updated, UpdatedBy, Version)
VALUES (0, 569954, 1752, 0, 30, 104, 'AD_Message_ID', TO_TIMESTAMP('2020-02-06 15:51:45', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', '', 'System Message', 'D', 10, 'Information and Error messages', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Meldung', 0, TO_TIMESTAMP('2020-02-06 15:51:45', 'YYYY-MM-DD HH24:MI:SS'),
        100, 1)
;

-- 2020-02-06T13:51:45.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Column_ID = 569954
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2020-02-06T13:51:45.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_Column_Translation_From_AD_Element(1752)
;

-- 2020-02-06T13:51:54.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
SELECT public.db_alter_table('AD_Ref_List', 'ALTER TABLE public.AD_Ref_List ADD COLUMN AD_Message_ID NUMERIC(10)')
;

-- 2020-02-06T13:51:55.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Ref_List
    ADD CONSTRAINT ADMessage_ADRefList FOREIGN KEY (AD_Message_ID) REFERENCES public.AD_Message DEFERRABLE INITIALLY DEFERRED
;

-- 2020-02-06T13:52:08.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='N',
    Updated=TO_TIMESTAMP('2020-02-06 15:52:08', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID = 569954
;

-- 2020-02-06T13:52:28.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column
values ('ad_ref_list', 'AD_Message_ID', 'NUMERIC(10)', null, null)
;

-- 2020-02-07T15:21:42.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 544962, 0, TO_TIMESTAMP('2020-02-07 17:21:42', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 'VOID ENGLISH TEST MESSAGE', 'I', TO_TIMESTAMP('2020-02-07 17:21:42', 'YYYY-MM-DD HH24:MI:SS'), 100, 'VO_Validation_Message')
;

-- 2020-02-07T15:21:42.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Message_ID = 544962
  AND NOT EXISTS(SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 2020-02-07T15:21:53.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='VOID CH TEST MESSAGE',
    Updated=TO_TIMESTAMP('2020-02-07 17:21:53', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 544962
;

-- 2020-02-07T15:21:58.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='VOID NL TEST MESSAGE',
    Updated=TO_TIMESTAMP('2020-02-07 17:21:58', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'nl_NL'
  AND AD_Message_ID = 544962
;

-- 2020-02-07T15:22:11.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message
SET MsgText='VOID GERMAN TEST MESSAGE',
    Updated=TO_TIMESTAMP('2020-02-07 17:22:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Message_ID = 544962
;

-- 2020-02-07T16:28:27.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 577511, 0, 'AD_Messsage_Value', TO_TIMESTAMP('2020-02-07 18:28:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 'SuchschlÃ¼ssel fÃ¼r den Eintrag im erforderlichen Format - muss eindeutig sein', 'U', 'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).', 'Y', 'SuchschlÃ¼ssel', 'SuchschlÃ¼ssel', TO_TIMESTAMP('2020-02-07 18:28:26', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-02-07T16:28:27.115Z
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
  AND t.AD_Element_ID = 577511
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2020-02-10T10:21:57.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 544963, 0, TO_TIMESTAMP('2020-02-10 12:21:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'YES', 'I', TO_TIMESTAMP('2020-02-10 12:21:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.popupinfo.yes')
;

-- 2020-02-10T10:21:57.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Message_ID = 544963
  AND NOT EXISTS(SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 2020-02-10T10:22:10.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 544964, 0, TO_TIMESTAMP('2020-02-10 12:22:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 'NO', 'I', TO_TIMESTAMP('2020-02-10 12:22:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.popupinfo.no')
;

-- 2020-02-10T10:22:10.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Message_ID = 544964
  AND NOT EXISTS(SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 2020-02-10T10:22:22.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 544965, 0, TO_TIMESTAMP('2020-02-10 12:22:22', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 'TESTUL', 'I', TO_TIMESTAMP('2020-02-10 12:22:22', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.popupinfo.test')
;

-- 2020-02-10T10:22:22.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Message_ID,
       t.MsgText,
       t.MsgTip,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Message_ID = 544965
  AND NOT EXISTS(SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 2020-02-10T13:37:47.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message
SET MsgText='Are you sure you want to void this document?',
    Value='de.metas.popupinfo.voidMessage',
    Updated=TO_TIMESTAMP('2020-02-10 15:37:47', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Message_ID = 544965
;

-- 2020-02-10T13:38:18.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET IsTranslated='Y',
    MsgText='Are you sure you want to void this document?',
    Updated=TO_TIMESTAMP('2020-02-10 15:38:18', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 544965
;

-- 2020-02-10T13:38:21.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Möchten Sie diesen Beleg wirklich löschen?',
    Updated=TO_TIMESTAMP('2020-02-10 15:38:21', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 544965
;

-- 2020-02-10T13:38:25.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Möchten Sie diesen Beleg wirklich löschen?',
    Updated=TO_TIMESTAMP('2020-02-10 15:38:25', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'nl_NL'
  AND AD_Message_ID = 544965
;

-- 2020-02-10T13:38:28.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Möchten Sie diesen Beleg wirklich löschen?',
    Updated=TO_TIMESTAMP('2020-02-10 15:38:28', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 544965
;

-- 2020-02-10T13:38:34.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message
SET MsgText='Möchten Sie diesen Beleg wirklich löschen?',
    Updated=TO_TIMESTAMP('2020-02-10 15:38:34', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Message_ID = 544965
;

update ad_ref_list
set ad_message_id = 544965
where ad_ref_list_id = 182
;
