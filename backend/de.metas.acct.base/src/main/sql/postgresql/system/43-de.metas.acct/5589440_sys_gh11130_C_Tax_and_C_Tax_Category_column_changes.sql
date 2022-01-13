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

UPDATE C_Tax
SET TypeOfDestCountry='DOMESTIC'
WHERE C_Country_ID = To_Country_ID
;

UPDATE C_Tax
SET TypeOfDestCountry='OUTSIDE_COUNTRY_AREA'
WHERE C_Country_ID <> To_Country_ID
  AND isToEuLocation <> 'Y'
;

-- 2021-05-25T10:05:44.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 573974, 646823, 0, 174, 0, TO_TIMESTAMP('2021-05-25 13:05:44', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Matcht nur, wenn die betreffende Organisation im Bestimmungsland eine Fiskalvertretung hat.', 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Fiskalvertretung', 280, 210, 0, 1, 1, TO_TIMESTAMP('2021-05-25 13:05:44', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T10:05:44.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Field_ID,
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
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Field_ID = 646823
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-05-25T10:05:44.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(579209)
;

-- 2021-05-25T10:05:44.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 646823
;

-- 2021-05-25T10:05:44.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(646823)
;

-- 2021-05-25T10:05:57.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 573975, 646824, 0, 174, 0, TO_TIMESTAMP('2021-05-25 13:05:56', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Matcht nur, wenn der betreffende Geschäftspartner einer Kleinunternehmer-Steuerbefreiung unterliegt', 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Kleinunernehmen', 290, 220, 0, 1, 1, TO_TIMESTAMP('2021-05-25 13:05:56', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T10:05:57.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Field_ID,
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
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Field_ID = 646824
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-05-25T10:05:57.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(579210)
;

-- 2021-05-25T10:05:57.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 646824
;

-- 2021-05-25T10:05:57.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(646824)
;

-- 2021-05-25T10:07:28.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 646823, 0, 174, 540524, 585272, 'F', TO_TIMESTAMP('2021-05-25 13:07:27', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Matcht nur, wenn die betreffende Organisation im Bestimmungsland eine Fiskalvertretung hat.', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Fiskalvertretung', 10, 0, 0, TO_TIMESTAMP('2021-05-25 13:07:27', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T10:07:54.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 646824, 0, 174, 540524, 585273, 'F', TO_TIMESTAMP('2021-05-25 13:07:54', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Matcht nur, wenn der betreffende Geschäftspartner einer Kleinunternehmer-Steuerbefreiung unterliegt', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Kleinunernehmen', 20, 0, 0, TO_TIMESTAMP('2021-05-25 13:07:54', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T10:08:00.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=30, Updated=TO_TIMESTAMP('2021-05-25 13:08:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 544934
;

-- 2021-05-25T10:08:21.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=50, Updated=TO_TIMESTAMP('2021-05-25 13:08:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 544936
;

-- 2021-05-25T10:08:47.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=30, Updated=TO_TIMESTAMP('2021-05-25 13:08:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 544935
;

-- 2021-05-25T10:09:08.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=30, Updated=TO_TIMESTAMP('2021-05-25 13:09:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 544936
;

-- 2021-05-25T10:09:13.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 585273
;

-- 2021-05-25T10:09:17.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 585272
;

-- 2021-05-25T10:09:21.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=10, Updated=TO_TIMESTAMP('2021-05-25 13:09:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 544934
;

-- 2021-05-25T10:09:26.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=20, Updated=TO_TIMESTAMP('2021-05-25 13:09:26', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 544935
;

-- 2021-05-25T10:10:00.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 646823, 0, 174, 540521, 585274, 'F', TO_TIMESTAMP('2021-05-25 13:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Matcht nur, wenn die betreffende Organisation im Bestimmungsland eine Fiskalvertretung hat.', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Fiskalvertretung', 20, 0, 0, TO_TIMESTAMP('2021-05-25 13:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T10:10:13.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 646824, 0, 174, 540521, 585275, 'F', TO_TIMESTAMP('2021-05-25 13:10:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Matcht nur, wenn der betreffende Geschäftspartner einer Kleinunternehmer-Steuerbefreiung unterliegt', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Kleinunernehmen', 30, 0, 0, TO_TIMESTAMP('2021-05-25 13:10:13', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T10:10:56.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=120, Updated=TO_TIMESTAMP('2021-05-25 13:10:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 585274
;

-- 2021-05-25T10:10:56.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=130, Updated=TO_TIMESTAMP('2021-05-25 13:10:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 585275
;

-- 2021-05-25T10:10:56.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=140, Updated=TO_TIMESTAMP('2021-05-25 13:10:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 544924
;

-- 2021-05-25T10:10:56.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=150, Updated=TO_TIMESTAMP('2021-05-25 13:10:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 544930
;

-- 2021-05-25T10:13:22.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_UI_Element
WHERE AD_Field_ID = 56447
;

-- 2021-05-25T10:13:22.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 56447
;

-- 2021-05-25T10:13:22.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 56447
;

-- 2021-05-25T10:13:22.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Field
WHERE AD_Field_ID = 56447
;

-- 2021-05-25T10:13:45.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 573976, 646825, 0, 174, 0, TO_TIMESTAMP('2021-05-25 13:13:45', 'YYYY-MM-DD HH24:MI:SS'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Typ Bestimmungsland', 300, 230, 0, 1, 1, TO_TIMESTAMP('2021-05-25 13:13:45', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T10:13:45.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Field_ID,
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
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Field_ID = 646825
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-05-25T10:13:45.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(579211)
;

-- 2021-05-25T10:13:45.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 646825
;

-- 2021-05-25T10:13:45.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(646825)
;

-- 2021-05-25T10:14:56.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 646825, 0, 174, 540520, 585276, 'F', TO_TIMESTAMP('2021-05-25 13:14:55', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Typ Bestimmungsland', 50, 0, 0, TO_TIMESTAMP('2021-05-25 13:14:55', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T13:01:15.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 585276
;

-- 2021-05-25T13:01:40.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 646825, 0, 174, 540519, 585278, 'F', TO_TIMESTAMP('2021-05-25 16:01:40', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Typ Bestimmungsland', 100, 0, 0, TO_TIMESTAMP('2021-05-25 16:01:40', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T13:11:26.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch,
                       Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 574112, 1899, 0, 17, 270, 252, 'ProductType', TO_TIMESTAMP('2021-05-25 16:11:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'I', 'Art von Produkt', 'D', 0, 1, 'Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Produktart', 0, 0,
        TO_TIMESTAMP('2021-05-25 16:11:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-05-25T13:11:26.785Z
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
  AND t.AD_Column_ID = 574112
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-05-25T13:11:26.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(1899)
;

-- 2021-05-25T13:11:32.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('C_TaxCategory', 'ALTER TABLE public.C_TaxCategory ADD COLUMN ProductType CHAR(1) DEFAULT ''I'' NULL')
;

-- 2021-05-25T13:15:04.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID, Created, CreatedBy, EntityType, IsActive, Name, Type, Updated, UpdatedBy)
VALUES (0, 0, 540542, TO_TIMESTAMP('2021-05-25 16:15:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'C_TaxCategory ProductType', 'S', TO_TIMESTAMP('2021-05-25 16:15:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-05-25T13:19:52.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule
SET Code='EXISTS (SELECT * from C_TaxCategory c where @ProductType@=c.ProductType AND @C_TaxCategory_Id@ = c.c_taxcategory_id and c.isactive=''Y'')', Updated=TO_TIMESTAMP('2021-05-25 16:19:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Val_Rule_ID = 540542
;

-- 2021-06-02T11:29:57.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List
SET ValueName='Domestic', Updated=TO_TIMESTAMP('2021-06-02 14:29:57', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 542610
;

-- 2021-06-02T11:30:15.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List
SET ValueName='Non-EU country', Updated=TO_TIMESTAMP('2021-06-02 14:30:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 542612
;

-- 2021-06-02T11:30:25.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List
SET ValueName='EU-foreign', Updated=TO_TIMESTAMP('2021-06-02 14:30:25', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 542611
;

-- 2021-06-08T08:00:10.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-06-08 11:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585274
;

-- 2021-06-08T08:00:22.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-06-08 11:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585275
;

-- 2021-06-08T08:13:19.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579306,0,TO_TIMESTAMP('2021-06-08 11:13:19','YYYY-MM-DD HH24:MI:SS'),100,'Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.','D','Y','Typ Bestimmungsland','Typ Bestimmungsland',TO_TIMESTAMP('2021-06-08 11:13:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T08:13:19.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579306 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-08T08:13:46.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The business partner''s country. Usually this is the country in which the service is provided or to which the goods are delivered.', IsTranslated='Y', Name='Type dest. country', PrintName='Type dest. country',Updated=TO_TIMESTAMP('2021-06-08 11:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579306 AND AD_Language='en_US'
;

-- 2021-06-08T08:13:46.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579306,'en_US')
;

-- 2021-06-08T08:14:43.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=579306
;

-- 2021-06-08T08:14:43.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=579306
;

-- 2021-06-08T08:15:07.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579307,0,TO_TIMESTAMP('2021-06-08 11:15:07','YYYY-MM-DD HH24:MI:SS'),100,'Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.','U','Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.','Y','Bestimmungsland','Bestimmungsland',TO_TIMESTAMP('2021-06-08 11:15:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T08:15:07.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579307 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-08T08:15:14.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2021-06-08 11:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579307
;

-- 2021-06-08T08:15:56.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The business partner''s country. Usually this is the country in which the service is provided or to which the goods are delivered.', Help='The business partner''s country. Usually this is the country in which the service is provided or to which the goods are delivered.', IsTranslated='Y', Name='Destination country', PrintName='Destination country',Updated=TO_TIMESTAMP('2021-06-08 11:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579307 AND AD_Language='en_US'
;

-- 2021-06-08T08:15:56.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579307,'en_US')
;

-- 2021-06-08T08:17:01.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579307, Description='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Help='Land des Geschäftspartners. In der Regel ist dies das Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, oder in das die Ware geliefert wird.', Name='Bestimmungsland',Updated=TO_TIMESTAMP('2021-06-08 11:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=976
;

-- 2021-06-08T08:17:01.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579307)
;

-- 2021-06-08T08:17:01.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=976
;

-- 2021-06-08T08:17:01.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(976)
;

-- 2021-06-08T08:22:43.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574112,647451,0,176,0,TO_TIMESTAMP('2021-06-08 11:22:43','YYYY-MM-DD HH24:MI:SS'),100,'Art von Produkt',0,'D','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.',0,'Y','Y','Y','N','N','N','N','N','Produktart',100,90,0,1,1,TO_TIMESTAMP('2021-06-08 11:22:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T08:22:43.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647451 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-08T08:22:43.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1899)
;

-- 2021-06-08T08:22:43.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647451
;

-- 2021-06-08T08:22:43.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647451)
;

-- 2021-06-08T08:24:02.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579308,0,TO_TIMESTAMP('2021-06-08 11:24:02','YYYY-MM-DD HH24:MI:SS'),100,'Wenn gesetzt, dann kann diese Kategorie nur in Produktpreisen benutzt werden, deren Produkt die selbe Artikelart hat.','D','Wenn gesetzt, dann kann diese Kategorie nur in Produktpreisen benutzt werden, deren Produkt die selbe Artikelart hat.','Y','Produktart','Produktart',TO_TIMESTAMP('2021-06-08 11:24:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T08:24:02.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579308 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-08T08:24:19.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If set, then this tax category may only be used of product price records whose product has a matching type.', Help='If set, then this tax category may only be used of product price records whose product has a matching type.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-08 11:24:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579308 AND AD_Language='en_US'
;

-- 2021-06-08T08:24:19.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579308,'en_US')
;

-- 2021-06-08T08:25:09.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540312,545972,TO_TIMESTAMP('2021-06-08 11:25:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','others',15,TO_TIMESTAMP('2021-06-08 11:25:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T08:25:23.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647451,0,176,545972,585675,'F',TO_TIMESTAMP('2021-06-08 11:25:23','YYYY-MM-DD HH24:MI:SS'),100,'Art von Produkt','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.','Y','N','N','Y','N','N','N',0,'Produktart',10,0,0,TO_TIMESTAMP('2021-06-08 11:25:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T10:10:36.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540543,'c_taxcategory_id in (select cat.c_taxcategory_id from c_taxcategory cat join m_product p on cat.producttype = p.producttype WHERE p.m_product_id=@M_Product_ID@ AND)',TO_TIMESTAMP('2021-06-08 13:10:36','YYYY-MM-DD HH24:MI:SS'),100,'Product type in TaxCategory matches product category','D','Y','Product Type in TaxCategory','S',TO_TIMESTAMP('2021-06-08 13:10:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-08T10:17:06.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_TaxCategory.C_TaxCategory_ID IN (SELECT cat.C_TaxCategory_ID
                           FROM C_TaxCategory cat
                                    JOIN M_Product p ON cat.ProductType = p.ProductType OR cat.ProductType is null
                           WHERE p.M_Product_ID =@M_Product_ID@)',Updated=TO_TIMESTAMP('2021-06-08 13:15:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540543
;

-- 2021-06-08T10:17:22.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540543,Updated=TO_TIMESTAMP('2021-06-08 13:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=505149
;

-- 2021-06-15T13:01:37.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,574444,618,0,15,261,'ValidTo',TO_TIMESTAMP('2021-06-15 16:01:37','YYYY-MM-DD HH24:MI:SS'),100,'N','9999-12-31 23:59:59.0','Gültig bis inklusiv (letzter Tag)','D',7,'"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Y','N',0,'Gültig bis','NP',0,TO_TIMESTAMP('2021-06-15 16:01:37','YYYY-MM-DD HH24:MI:SS'),100,0.0)
;

-- 2021-06-15T13:01:37.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574444 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-15T13:01:37.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(618) 
;

COMMIT;
-- 2021-06-15T13:03:35.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Tax','ALTER TABLE public.C_Tax ADD COLUMN ValidTo TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_TIMESTAMP(''9999-12-31 23:59:59'',''YYYY-MM-DD HH24:MI:SS'') NOT NULL')
;

-- 2021-06-15T13:04:57.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574445,566,0,22,261,'SeqNo',TO_TIMESTAMP('2021-06-15 16:04:57','YYYY-MM-DD HH24:MI:SS'),100,'N','1000','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,14,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2021-06-15 16:04:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-15T13:04:57.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574445 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-15T13:04:57.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

COMMIT;

-- 2021-06-15T13:05:20.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Tax','ALTER TABLE public.C_Tax ADD COLUMN SeqNo NUMERIC DEFAULT 1000 NOT NULL')
;

-- 2021-06-17T08:10:57.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574444,649715,0,174,0,TO_TIMESTAMP('2021-06-17 11:10:56','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)',0,'D','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.',0,'Y','Y','Y','N','N','N','N','N','Gültig bis',310,240,0,1,1,TO_TIMESTAMP('2021-06-17 11:10:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-17T08:10:57.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649715 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-17T08:10:57.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(618) 
;

-- 2021-06-17T08:10:57.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649715
;

-- 2021-06-17T08:10:57.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(649715)
;

-- 2021-06-17T08:11:54.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649715,0,174,540519,586782,'F',TO_TIMESTAMP('2021-06-17 11:11:54','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','N','N',0,'Gültig bis',25,0,0,TO_TIMESTAMP('2021-06-17 11:11:54','YYYY-MM-DD HH24:MI:SS'),100)
;
