-- 2020-01-28T16:24:30.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 577490, 0, TO_TIMESTAMP('2020-01-28 18:24:29', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 'Validation Message', 'Validation Message', TO_TIMESTAMP('2020-01-28 18:24:29', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-01-28T16:24:30.015Z
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
  AND t.AD_Element_ID = 577490
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2020-01-28T16:25:11.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element
SET ColumnName='ValidationMessage',
    Updated=TO_TIMESTAMP('2020-01-28 18:25:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577490
;

-- 2020-01-28T16:25:11.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnName='ValidationMessage',
    Name='Validation Message',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577490
;

-- 2020-01-28T16:25:11.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='ValidationMessage',
    Name='Validation Message',
    Description=NULL,
    Help=NULL,
    AD_Element_ID=577490
WHERE UPPER(ColumnName) = 'VALIDATIONMESSAGE'
  AND IsCentrallyMaintained = 'Y'
  AND AD_Element_ID IS NULL
;

-- 2020-01-28T16:25:11.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='ValidationMessage',
    Name='Validation Message',
    Description=NULL,
    Help=NULL
WHERE AD_Element_ID = 577490
  AND IsCentrallyMaintained = 'Y'
;

-- 2020-01-28T16:25:32.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Description='A message to show to the user before performing a docaction',
    Updated=TO_TIMESTAMP('2020-01-28 18:25:32', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID = 577490
  AND AD_Language = 'en_US'
;

-- 2020-01-28T16:25:32.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_TRL_Tables_On_AD_Element_TRL_Update(577490, 'en_US')
;

-- 2020-01-28T16:25:55.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsForceIncludeInGeneratedModel,
                       IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRangeFilter, IsSelectionColumn, IsShowFilterIncrementButtons, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, SelectionColumnSeqNo, Updated, UpdatedBy, Version)
VALUES (0, 569889, 577490, 0, 10, 136, 'ValidationMessage', TO_TIMESTAMP('2020-01-28 18:25:55', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'U', 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'Validation Message', 0, TO_TIMESTAMP('2020-01-28 18:25:55', 'YYYY-MM-DD HH24:MI:SS'), 100, 1)
;

-- 2020-01-28T16:25:55.798Z
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
  AND t.AD_Column_ID = 569889
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2020-01-28T16:25:55.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_Column_Translation_From_AD_Element(577490)
;

-- 2020-01-28T16:27:01.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
SELECT public.db_alter_table('AD_Ref_List_Trl', 'ALTER TABLE public.AD_Ref_List_Trl ADD COLUMN ValidationMessage VARCHAR(255)')
;

-- 2020-01-28T16:28:03.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsForceIncludeInGeneratedModel,
                       IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRangeFilter, IsSelectionColumn, IsShowFilterIncrementButtons, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, SelectionColumnSeqNo, Updated, UpdatedBy, Version)
VALUES (0, 569890, 577490, 0, 10, 104, 'ValidationMessage', TO_TIMESTAMP('2020-01-28 18:28:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'U', 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', 'N', 'Validation Message', 0, TO_TIMESTAMP('2020-01-28 18:28:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 1)
;

-- 2020-01-28T16:28:03.929Z
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
  AND t.AD_Column_ID = 569890
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2020-01-28T16:28:03.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
select update_Column_Translation_From_AD_Element(577490)
;

-- 2020-01-28T16:28:14.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */
SELECT public.db_alter_table('AD_Ref_List', 'ALTER TABLE public.AD_Ref_List ADD COLUMN ValidationMessage VARCHAR(255)')
;

-- 2020-01-28T16:28:34.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column
values ('ad_ref_list_trl', 'ValidationMessage', 'VARCHAR(255)', null, null)
;

-- 2020-01-28T16:28:42.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column
values ('ad_ref_list', 'ValidationMessage', 'VARCHAR(255)', null, null)
;

-- 2020-01-28T16:30:17.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsMandatory='Y',
    Updated=TO_TIMESTAMP('2020-01-28 18:30:17', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID = 569890
;

-- 2020-01-28T16:30:25.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsMandatory='N',
    Updated=TO_TIMESTAMP('2020-01-28 18:30:25', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Column_ID = 569890
;

-- 2020-01-28T16:30:27.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column
values ('ad_ref_list', 'ValidationMessage', 'VARCHAR(255)', null, null)
;

update ad_ref_list_trl
set validationmessage = 'Möchten Sie diesen Beleg wirklich löschen?||Ja||Nein'
where ad_ref_list_id = 182
  and ad_language != 'en_US';

update ad_ref_list_trl
set validationmessage = 'Are you sure you want to void this document?||Yes||No'
where ad_ref_list_id = 182
  and ad_language = 'en_US';

update ad_ref_list
set validationmessage = 'Möchten Sie diesen Beleg wirklich löschen?||Ja||Nein'
where ad_ref_list_id = 182;
