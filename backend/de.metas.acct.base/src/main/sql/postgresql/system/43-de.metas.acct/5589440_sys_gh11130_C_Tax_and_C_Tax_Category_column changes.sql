-- 2021-05-21T07:41:46.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 579209, 0, 'IsFiscalRepresentation', TO_TIMESTAMP('2021-05-21 10:41:46', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Matcht nur, wenn die betreffende Organisation im Bestimmungsland eine Fiskalvertretung hat.', 'U', 'Y', 'Fiskalvertretung', 'Fiskalvertretung', TO_TIMESTAMP('2021-05-21 10:41:46', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-21T07:41:46.591Z
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
  AND t.AD_Element_ID = 579209
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2021-05-21T07:42:13.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Description='Matches only if the respective org has a fiscal representation in the destination country.', Name='Fiscal representation', PrintName='Fiscal representation', Updated=TO_TIMESTAMP('2021-05-21 10:42:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579209
  AND AD_Language = 'en_US'
;

-- 2021-05-21T07:42:13.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579209, 'en_US')
;

-- 2021-05-21T07:42:24.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element
SET EntityType='D', Updated=TO_TIMESTAMP('2021-05-21 10:42:24', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579209
;

-- 2021-05-21T07:43:02.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy, WidgetSize)
VALUES (0, 579210, 0, 'IsSmallbusiness', TO_TIMESTAMP('2021-05-21 10:43:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Matcht nur, wenn der betreffende Geschäftspartner einer Kleinunternehmer-Steuerbefreiung unterliegt', 'D', 'Y', 'Kleinunernehmen', 'Kleinunernehmen', TO_TIMESTAMP('2021-05-21 10:43:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'S')
;

-- 2021-05-21T07:43:02.593Z
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
  AND t.AD_Element_ID = 579210
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2021-05-21T07:43:30.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Description='Matches only if the respective business partner has a small business tax exemption', IsTranslated='Y', Name='Small business', PrintName='Small business', Updated=TO_TIMESTAMP('2021-05-21 10:43:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579210
  AND AD_Language = 'en_US'
;

-- 2021-05-21T07:43:30.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579210, 'en_US')
;

-- 2021-05-21T07:56:07.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 573974, 579209, 0, 20, 261, 'IsFiscalRepresentation', TO_TIMESTAMP('2021-05-21 10:56:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'N', 'Matcht nur, wenn die betreffende Organisation im Bestimmungsland eine Fiskalvertretung hat.', 'D', 0, 1, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0,
        'Fiskalvertretung', 0, 0, TO_TIMESTAMP('2021-05-21 10:56:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-05-21T07:56:07.154Z
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
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 573974
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-05-21T07:56:07.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(579209)
;

-- 2021-05-21T07:56:07.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('C_Tax', 'ALTER TABLE public.C_Tax ADD COLUMN IsFiscalRepresentation CHAR(1) DEFAULT ''N'' CHECK (IsFiscalRepresentation IN (''Y'',''N''))')
;

-- 2021-05-21T07:56:43.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element
SET WidgetSize='S', Updated=TO_TIMESTAMP('2021-05-21 10:56:43', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579209
;

-- 2021-05-21T07:57:58.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 573975, 579210, 0, 20, 261, 'IsSmallbusiness', TO_TIMESTAMP('2021-05-21 10:57:58', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'N', 'Matcht nur, wenn der betreffende Geschäftspartner einer Kleinunternehmer-Steuerbefreiung unterliegt', 'D', 0, 1, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0,
        'Kleinunernehmen', 0, 0, TO_TIMESTAMP('2021-05-21 10:57:58', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-05-21T07:57:58.248Z
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
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 573975
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-05-21T07:57:58.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(579210)
;

-- 2021-05-21T07:58:14.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('C_Tax', 'ALTER TABLE public.C_Tax ADD COLUMN IsSmallbusiness CHAR(1) DEFAULT ''N'' CHECK (IsSmallbusiness IN (''Y'',''N''))')
;

-- 2021-05-21T07:59:48.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 579211, 0, 'TypeOfDestCountry', TO_TIMESTAMP('2021-05-21 10:59:47', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Typ Bestimmungsland', 'Typ Bestimmungsland', TO_TIMESTAMP('2021-05-21 10:59:47', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-21T07:59:48.070Z
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
  AND t.AD_Element_ID = 579211
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2021-05-21T07:59:59.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Type dest. country', PrintName='Type dest. country', Updated=TO_TIMESTAMP('2021-05-21 10:59:59', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579211
  AND AD_Language = 'en_US'
;

-- 2021-05-21T07:59:59.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579211, 'en_US')
;

-- 2021-05-21T08:00:57.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541323, TO_TIMESTAMP('2021-05-21 11:00:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'TypeDestCountry', TO_TIMESTAMP('2021-05-21 11:00:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'L')
;

-- 2021-05-21T08:00:57.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
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
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Reference_ID = 541323
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2021-05-21T08:01:21.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 541323, 542610, TO_TIMESTAMP('2021-05-21 11:01:21', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Inland', TO_TIMESTAMP('2021-05-21 11:01:21', 'YYYY-MM-DD HH24:MI:SS'), 100, 'DOMESTIC', 'Inland')
;

-- 2021-05-21T08:01:21.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Ref_List_ID,
       t.Description,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Ref_List t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Ref_List_ID = 542610
  AND NOT EXISTS(SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;

-- 2021-05-21T08:01:38.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 541323, 542611, TO_TIMESTAMP('2021-05-21 11:01:38', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'EU-Ausland', TO_TIMESTAMP('2021-05-21 11:01:38', 'YYYY-MM-DD HH24:MI:SS'), 100, 'WITHIN_COUNTRY_AREA', 'EU-Ausland')
;

-- 2021-05-21T08:01:38.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Ref_List_ID,
       t.Description,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Ref_List t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Ref_List_ID = 542611
  AND NOT EXISTS(SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;

-- 2021-05-21T08:02:00.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Ref_List_ID, Created, CreatedBy, EntityType, IsActive, Name, Updated, UpdatedBy, Value, ValueName)
VALUES (0, 0, 541323, 542612, TO_TIMESTAMP('2021-05-21 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Nicht-EU-Ausland', TO_TIMESTAMP('2021-05-21 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'OUTSIDE_COUNTRY_AREA', 'Nicht-EU-Ausland')
;

-- 2021-05-21T08:02:00.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Ref_List_ID,
       t.Description,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Ref_List t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Ref_List_ID = 542612
  AND NOT EXISTS(SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID)
;

-- 2021-05-21T08:02:22.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl
SET Name='Non-EU country', Updated=TO_TIMESTAMP('2021-05-21 11:02:22', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Ref_List_ID = 542612
;

-- 2021-05-21T08:02:41.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl
SET Name='EU-foreign', Updated=TO_TIMESTAMP('2021-05-21 11:02:41', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Ref_List_ID = 542611
;

-- 2021-05-21T08:02:52.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Domestic ', Updated=TO_TIMESTAMP('2021-05-21 11:02:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Ref_List_ID = 542610
;

-- 2021-05-21T08:02:57.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2021-05-21 11:02:57', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Ref_List_ID = 542611
;

-- 2021-05-21T08:03:02.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2021-05-21 11:03:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Ref_List_ID = 542612
;

-- 2021-05-21T08:05:08.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 573976, 579211, 0, 17, 541323, 261, 'TypeOfDestCountry', TO_TIMESTAMP('2021-05-21 11:05:08', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'D', 0, 21, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Typ Bestimmungsland', 0, 0, TO_TIMESTAMP('2021-05-21 11:05:08', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-05-21T08:05:08.941Z
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
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 573976
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-05-21T08:05:08.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(579211)
;

-- 2021-05-21T08:05:48.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsActive='N', Updated=TO_TIMESTAMP('2021-05-21 11:05:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 56377
;

-- 2021-05-21T08:07:48.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Updated=TO_TIMESTAMP('2021-05-21 11:07:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 594
  AND AD_Language = 'de_CH'
;

-- 2021-05-21T08:07:48.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(594, 'de_CH')
;

-- 2021-05-21T08:07:50.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Updated=TO_TIMESTAMP('2021-05-21 11:07:50', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 594
  AND AD_Language = 'nl_NL'
;

-- 2021-05-21T08:07:50.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(594, 'nl_NL')
;

-- 2021-05-21T08:08:01.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Updated=TO_TIMESTAMP('2021-05-21 11:08:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 594
  AND AD_Language = 'de_DE'
;

-- 2021-05-21T08:08:01.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(594, 'de_DE')
;

-- 2021-05-21T08:08:01.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(594, 'de_DE')
;

-- 2021-05-21T08:08:01.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnName='To_Country_ID', Name='An', Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Help='The To Country indicates the receiving country on a document'
WHERE AD_Element_ID = 594
;

-- 2021-05-21T08:08:01.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='To_Country_ID', Name='An', Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Help='The To Country indicates the receiving country on a document', AD_Element_ID=594
WHERE UPPER(ColumnName) = 'TO_COUNTRY_ID'
  AND IsCentrallyMaintained = 'Y'
  AND AD_Element_ID IS NULL
;

-- 2021-05-21T08:08:01.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para
SET ColumnName='To_Country_ID', Name='An', Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Help='The To Country indicates the receiving country on a document'
WHERE AD_Element_ID = 594
  AND IsCentrallyMaintained = 'Y'
;

-- 2021-05-21T08:08:01.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET Name='An', Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Help='The To Country indicates the receiving country on a document'
WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID = 594) AND AD_Name_ID IS NULL)
   OR (AD_Name_ID = 594)
;

-- 2021-05-21T08:08:01.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab
SET Name='An', Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Help='The To Country indicates the receiving country on a document', CommitWarning = NULL
WHERE AD_Element_ID = 594
;

-- 2021-05-21T08:08:01.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW
SET Name='An', Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Help='The To Country indicates the receiving country on a document'
WHERE AD_Element_ID = 594
;

-- 2021-05-21T08:08:01.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu
SET Name = 'An', Description = 'Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL
WHERE AD_Element_ID = 594
;

-- 2021-05-21T08:08:09.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Description='The business partner''s country. Usually this is the country in which the service is provided or to which the goods are delivered.', Updated=TO_TIMESTAMP('2021-05-21 11:08:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 594
  AND AD_Language = 'en_US'
;

-- 2021-05-21T08:08:09.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(594, 'en_US')
;

-- 2021-05-21T08:08:30.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('C_Tax', 'ALTER TABLE public.C_Tax ADD COLUMN TypeOfDestCountry VARCHAR(21)')
;

-- 2021-05-21T11:54:04.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET TechnicalNote='Deprecated in https://github.com/metasfresh/metasfresh/issues/11130 to be replaced with column: TypeOfDestCountry', Updated=TO_TIMESTAMP('2021-05-21 14:54:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 56377
;

UPDATE C_Tax
SET TypeOfDestCountry='WITHIN_COUNTRY_AREA'
WHERE isToEuLocation = 'Y'
;

-- 2021-05-21T15:16:16.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List
SET Name='Domestic', Updated=TO_TIMESTAMP('2021-05-21 18:16:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 542610
;

-- 2021-05-21T15:16:39.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List
SET Name='EU-foreign', Updated=TO_TIMESTAMP('2021-05-21 18:16:39', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 542611
;

-- 2021-05-21T15:22:08.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List
SET Name='Non-EU country', Updated=TO_TIMESTAMP('2021-05-21 18:22:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 542612
;
 
