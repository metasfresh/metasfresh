-- 2021-04-01T11:17:37.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column
VALUES ('c_olcand', 'IsGroupCompensationLine', 'CHAR(1)', NULL, 'N')
;

-- 2021-04-01T11:17:37.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCand
SET IsGroupCompensationLine='N'
WHERE IsGroupCompensationLine IS NULL
;

-- 2021-04-01T11:24:47.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET EntityType='de.metas.ordercandidate', Updated=TO_TIMESTAMP('2021-04-01 14:24:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573256
;

-- 2021-04-06T07:22:32.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='Y', IsDisplayed='N', Updated=TO_TIMESTAMP('2021-04-06 10:22:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 581590
;

-- 2021-04-06T07:22:37.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='Y', IsDisplayed='N', Updated=TO_TIMESTAMP('2021-04-06 10:22:37', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 581589
;

-- 2021-04-06T07:26:06.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsAdvancedField='Y', Updated=TO_TIMESTAMP('2021-04-06 10:26:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 576297
;

-- 2021-04-06T07:26:10.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayed='Y', Updated=TO_TIMESTAMP('2021-04-06 10:26:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 581590
;

-- 2021-04-06T07:26:55.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayed='Y', Updated=TO_TIMESTAMP('2021-04-06 10:26:55', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 581589
;

-- 2021-04-06T07:29:37.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ReadOnlyLogic='', Updated=TO_TIMESTAMP('2021-04-06 10:29:37', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 546225
;

-- 2021-04-06T07:32:30.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ReadOnlyLogic='@IsError@=''N'' | @Processed@=''Y''', Updated=TO_TIMESTAMP('2021-04-06 10:32:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573256
;

-- 2021-04-06T07:33:00.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ReadOnlyLogic='@IsError@=''N'' | @Processed@=''Y''', Updated=TO_TIMESTAMP('2021-04-06 10:33:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573257
;

-- 2021-04-06T07:41:21.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ReadOnlyLogic='', Updated=TO_TIMESTAMP('2021-04-06 10:41:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573257
;

-- 2021-04-06T07:41:35.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ReadOnlyLogic='', Updated=TO_TIMESTAMP('2021-04-06 10:41:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573256
;

-- 2021-04-06T12:42:23.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 579002, 0, 'IsGroupingError', TO_TIMESTAMP('2021-04-06 15:42:23', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Gruppierungsfehler', 'Gruppierungsfehler', TO_TIMESTAMP('2021-04-06 15:42:23', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-06T12:42:23.879Z
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
  AND t.AD_Element_ID = 579002
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2021-04-06T12:42:42.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET IsTranslated='Y', PrintName='Has grouping errors', Updated=TO_TIMESTAMP('2021-04-06 15:42:42', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579002
  AND AD_Language = 'en_US'
;

-- 2021-04-06T12:42:42.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579002, 'en_US')
;

-- 2021-04-06T12:43:06.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 579003, 0, 'GroupingErrorMessage', TO_TIMESTAMP('2021-04-06 15:43:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Gruppierungsfehlermeldung', 'Gruppierungsfehlermeldung', TO_TIMESTAMP('2021-04-06 15:43:06', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-06T12:43:06.783Z
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
  AND t.AD_Element_ID = 579003
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2021-04-06T12:43:17.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET IsTranslated='Y', PrintName='Grouping error message', Updated=TO_TIMESTAMP('2021-04-06 15:43:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579003
  AND AD_Language = 'en_US'
;

-- 2021-04-06T12:43:17.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579003, 'en_US')
;

-- 2021-04-06T12:44:02.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 573342, 579002, 0, 20, 540244, 'IsGroupingError', TO_TIMESTAMP('2021-04-06 15:44:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'N', 'de.metas.ordercandidate', 0, 1, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Gruppierungsfehler', 0, 0, TO_TIMESTAMP('2021-04-06 15:44:02', 'YYYY-MM-DD HH24:MI:SS'),
        100, 0)
;

-- 2021-04-06T12:44:02.979Z
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
  AND t.AD_Column_ID = 573342
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-04-06T12:44:02.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(579002)
;

-- 2021-04-06T12:44:03.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('C_OLCand', 'ALTER TABLE public.C_OLCand ADD COLUMN IsGroupingError CHAR(1) DEFAULT ''N'' CHECK (IsGroupingError IN (''Y'',''N'')) NOT NULL')
;

-- 2021-04-06T12:44:57.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 573343, 579003, 0, 10, 540244, 'GroupingErrorMessage', TO_TIMESTAMP('2021-04-06 15:44:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'de.metas.ordercandidate', 0, 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Gruppierungsfehlermeldung', 0, 0,
        TO_TIMESTAMP('2021-04-06 15:44:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-04-06T12:44:57.569Z
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
  AND t.AD_Column_ID = 573343
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-04-06T12:44:57.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(579003)
;

-- 2021-04-06T12:46:46.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET AD_Reference_ID=36, FieldLength=268435456, Updated=TO_TIMESTAMP('2021-04-06 15:46:46', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573343
;

-- 2021-04-06T12:46:53.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('C_OLCand', 'ALTER TABLE public.C_OLCand ADD COLUMN GroupingErrorMessage TEXT')
;

-- 2021-04-06T12:48:38.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 573342, 642616, 0, 540282, 0, TO_TIMESTAMP('2021-04-06 15:48:38', 'YYYY-MM-DD HH24:MI:SS'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'Y', 'N', 'Gruppierungsfehler', 440, 440, 0, 1, 1, TO_TIMESTAMP('2021-04-06 15:48:38', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-06T12:48:38.798Z
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
  AND t.AD_Field_ID = 642616
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-04-06T12:48:38.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(579002)
;

-- 2021-04-06T12:48:38.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 642616
;

-- 2021-04-06T12:48:38.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(642616)
;

-- 2021-04-06T12:48:55.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 573343, 642617, 0, 540282, 0, TO_TIMESTAMP('2021-04-06 15:48:55', 'YYYY-MM-DD HH24:MI:SS'), 100, 0, 'D', 0, 'Y', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'Gruppierungsfehlermeldung', 450, 450, 0, 1, 1, TO_TIMESTAMP('2021-04-06 15:48:55', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-06T12:48:55.867Z
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
  AND t.AD_Field_ID = 642617
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-04-06T12:48:55.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(579003)
;

-- 2021-04-06T12:48:55.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 642617
;

-- 2021-04-06T12:48:55.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(642617)
;

-- 2021-04-06T12:50:36.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 642616, 0, 540282, 540965, 583076, 'F', TO_TIMESTAMP('2021-04-06 15:50:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Gruppierungsfehler', 80, 0, 0, TO_TIMESTAMP('2021-04-06 15:50:35', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-06T12:50:46.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 642617, 0, 540282, 540965, 583077, 'F', TO_TIMESTAMP('2021-04-06 15:50:46', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Gruppierungsfehlermeldung', 90, 0, 0, TO_TIMESTAMP('2021-04-06 15:50:46', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-06T13:49:56.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET FilterOperator='E', IsSelectionColumn='Y', Updated=TO_TIMESTAMP('2021-04-06 16:49:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573342
;

-- 2021-04-06T13:50:28.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=110, Updated=TO_TIMESTAMP('2021-04-06 16:50:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 576297
;

-- 2021-04-06T13:50:28.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=120, Updated=TO_TIMESTAMP('2021-04-06 16:50:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 547261
;

-- 2021-04-06T13:50:28.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=130, Updated=TO_TIMESTAMP('2021-04-06 16:50:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 547260
;

-- 2021-04-06T13:50:28.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=140, Updated=TO_TIMESTAMP('2021-04-06 16:50:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 583076
;

-- 2021-04-06T13:50:28.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=150, Updated=TO_TIMESTAMP('2021-04-06 16:50:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 547259
;

-- 2021-04-06T13:50:28.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=160, Updated=TO_TIMESTAMP('2021-04-06 16:50:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 548695
;

-- 2021-04-06T13:53:53.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Has grouping errors', Updated=TO_TIMESTAMP('2021-04-06 16:53:53', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579002
  AND AD_Language = 'en_US'
;

-- 2021-04-06T13:53:53.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579002, 'en_US')
;

-- 2021-04-06T13:54:23.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET Name='Grouping error message', Updated=TO_TIMESTAMP('2021-04-06 16:54:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579003
  AND AD_Language = 'en_US'
;

-- 2021-04-06T13:54:23.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579003, 'en_US')
;

-- 2021-04-06T13:59:34.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='N', SeqNo=0, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547060
;

-- 2021-04-06T13:59:34.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='N', SeqNo=0, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547054
;

-- 2021-04-06T13:59:34.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='N', SeqNo=0, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550553
;

-- 2021-04-06T13:59:34.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='N', SeqNo=0, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 551902
;

-- 2021-04-06T13:59:34.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='N', SeqNo=0, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 551901
;

-- 2021-04-06T13:59:34.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='N', SeqNo=0, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550285
;

-- 2021-04-06T13:59:34.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='N', SeqNo=0, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550284
;

-- 2021-04-06T13:59:34.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=80, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555174
;

-- 2021-04-06T13:59:34.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=90, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555175
;

-- 2021-04-06T13:59:34.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=100, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553756
;

-- 2021-04-06T13:59:34.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=110, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555178
;

-- 2021-04-06T13:59:34.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=120, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555179
;

-- 2021-04-06T13:59:34.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=130, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547057
;

-- 2021-04-06T13:59:34.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=140, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553757
;

-- 2021-04-06T13:59:34.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=150, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547056
;

-- 2021-04-06T13:59:34.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=160, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555183
;

-- 2021-04-06T13:59:34.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=170, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555177
;

-- 2021-04-06T13:59:34.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=180, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553994
;

-- 2021-04-06T13:59:34.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=190, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 554066
;

-- 2021-04-06T13:59:34.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=200, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 554080
;

-- 2021-04-06T13:59:34.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=210, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547048
;

-- 2021-04-06T13:59:34.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=220, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555762
;

-- 2021-04-06T13:59:34.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=230, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547052
;

-- 2021-04-06T13:59:34.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=240, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547062
;

-- 2021-04-06T13:59:34.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=250, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547063
;

-- 2021-04-06T13:59:34.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=260, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 554064
;

-- 2021-04-06T13:59:34.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=270, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 554065
;

-- 2021-04-06T13:59:34.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=280, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 554081
;

-- 2021-04-06T13:59:34.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=290, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 554082
;

-- 2021-04-06T13:59:34.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=300, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 551069
;

-- 2021-04-06T13:59:34.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=310, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 551070
;

-- 2021-04-06T13:59:34.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=320, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550289
;

-- 2021-04-06T13:59:34.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=330, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550290
;

-- 2021-04-06T13:59:34.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=340, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550288
;

-- 2021-04-06T13:59:34.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=350, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553715
;

-- 2021-04-06T13:59:34.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=360, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 556943
;

-- 2021-04-06T13:59:34.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=370, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 556944
;

-- 2021-04-06T13:59:34.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=380, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553714
;

-- 2021-04-06T13:59:34.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=390, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 556945
;

-- 2021-04-06T13:59:34.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=400, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 556946
;

-- 2021-04-06T13:59:34.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=410, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 549447
;

-- 2021-04-06T13:59:34.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=420, Updated=TO_TIMESTAMP('2021-04-06 16:59:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 551085
;

-- 2021-04-06T13:59:35.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=430, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547047
;

-- 2021-04-06T13:59:35.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=440, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550286
;

-- 2021-04-06T13:59:35.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=450, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555176
;

-- 2021-04-06T13:59:35.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=460, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555163
;

-- 2021-04-06T13:59:35.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=470, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 555186
;

-- 2021-04-06T13:59:35.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=480, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550292
;

-- 2021-04-06T13:59:35.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=490, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 550287
;

-- 2021-04-06T13:59:35.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=500, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 547094
;

-- 2021-04-06T13:59:35.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=510, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 548747
;

-- 2021-04-06T13:59:35.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=520, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 549595
;

-- 2021-04-06T13:59:35.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=530, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553469
;

-- 2021-04-06T13:59:35.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=540, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553470
;

-- 2021-04-06T13:59:35.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=550, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 554018
;

-- 2021-04-06T13:59:35.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=560, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 554017
;

-- 2021-04-06T13:59:35.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=570, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553746
;

-- 2021-04-06T13:59:35.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=580, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 556939
;

-- 2021-04-06T13:59:35.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=590, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 556941
;

-- 2021-04-06T13:59:35.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=600, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 553745
;

-- 2021-04-06T13:59:35.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=610, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 556940
;

-- 2021-04-06T13:59:35.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=620, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 556942
;

-- 2021-04-06T13:59:35.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=630, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 613713
;

-- 2021-04-06T13:59:35.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=640, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 642616
;

-- 2021-04-06T13:59:35.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=650, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 642617
;

-- 2021-04-06T13:59:35.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=660, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 570748
;

-- 2021-04-06T13:59:35.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=670, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 569300
;

-- 2021-04-06T13:59:35.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=680, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 595932
;

-- 2021-04-06T13:59:35.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsDisplayed='Y', SeqNo=690, Updated=TO_TIMESTAMP('2021-04-06 16:59:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 595931
;

-- 2021-04-06T14:00:51.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=60, Updated=TO_TIMESTAMP('2021-04-06 17:00:51', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573342
;

-- 2021-04-06T14:00:52.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=70, Updated=TO_TIMESTAMP('2021-04-06 17:00:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 550426
;

-- 2021-04-06T14:00:52.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=80, Updated=TO_TIMESTAMP('2021-04-06 17:00:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 544279
;

-- 2021-04-06T14:00:53.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=90, Updated=TO_TIMESTAMP('2021-04-06 17:00:53', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 549849
;

-- 2021-04-06T14:00:53.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=100, Updated=TO_TIMESTAMP('2021-04-06 17:00:53', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 550427
;

-- 2021-04-06T14:00:54.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=110, Updated=TO_TIMESTAMP('2021-04-06 17:00:54', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 563706
;

-- 2021-04-06T14:00:54.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=120, Updated=TO_TIMESTAMP('2021-04-06 17:00:54', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 559589
;

-- 2021-04-06T14:00:55.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=130, Updated=TO_TIMESTAMP('2021-04-06 17:00:55', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 544276
;

-- 2021-04-06T14:00:55.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=140, Updated=TO_TIMESTAMP('2021-04-06 17:00:55', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 544273
;

-- 2021-04-06T14:00:55.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=150, Updated=TO_TIMESTAMP('2021-04-06 17:00:55', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 550369
;

-- 2021-04-06T14:00:56.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsSelectionColumn='Y', SelectionColumnSeqNo=160, Updated=TO_TIMESTAMP('2021-04-06 17:00:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 546475
;

-- 2021-04-06T14:52:25.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545031, 0, TO_TIMESTAMP('2021-04-06 17:52:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Sales order candidate cannot be processed due to compensation group configuration problems.', 'I', TO_TIMESTAMP('2021-04-06 17:52:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 'OLCandGroupingError')
;

-- 2021-04-06T14:52:25.436Z
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
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Message_ID = 545031
  AND NOT EXISTS(SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

-- 2021-04-06T14:52:38.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Verkaufsauftragskandidat kann aufgrund von Problemen mit der Kompensationsgruppenkonfiguration nicht verarbeitet werden', Updated=TO_TIMESTAMP('2021-04-06 17:52:38', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545031
;

-- 2021-04-06T14:52:42.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Verkaufsauftragskandidat kann aufgrund von Problemen mit der Kompensationsgruppenkonfiguration nicht verarbeitet werden.', Updated=TO_TIMESTAMP('2021-04-06 17:52:42', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'nl_NL'
  AND AD_Message_ID = 545031
;

-- 2021-04-06T14:52:46.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Verkaufsauftragskandidat kann aufgrund von Problemen mit der Kompensationsgruppenkonfiguration nicht verarbeitet werden.', Updated=TO_TIMESTAMP('2021-04-06 17:52:46', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545031
;

-- 2021-04-06T14:52:50.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Verkaufsauftragskandidat kann aufgrund von Problemen mit der Kompensationsgruppenkonfiguration nicht verarbeitet werden.', Updated=TO_TIMESTAMP('2021-04-06 17:52:50', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545031
;

-- 2021-04-06T14:52:52.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2021-04-06 17:52:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Message_ID = 545031
;

-- 2021-04-06T14:56:02.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message
SET Value='OLCandProcessor.OLCandGroupingError', Updated=TO_TIMESTAMP('2021-04-06 17:56:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545031
;

-- 2021-04-06T15:18:27.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, Description, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsTranslateExcelHeaders, IsUseBPartnerLanguage, LockWaitTimeout, Name, PostgrestResponseFormat,
                        RefreshAllAfterExecution, ShowHelp, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 584818, 'N', 'org.adempiere.server.rpl.trx.process.C_OLCand_ClearGroupingError', 'N', TO_TIMESTAMP('2021-04-06 18:18:27', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Der Prozess selektiert zuerst die Transaktionen aller ausgewählten Positionen, sofern diese aktiviert und noch nicht verarbeitet sind.
Im zweiten Schritt werden die selektierten Transaktionen dann freigegeben, d.h. alle  - auch nicht selektierte - Positionen werden für die Auftragsgenerierung freigegeben.', 'U', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'Y', 0, 'Auswahl Transaktionen Freigeben', 'json', 'Y', 'Y', 'Java', TO_TIMESTAMP('2021-04-06 18:18:27', 'YYYY-MM-DD HH24:MI:SS'), 100, 'C_OLCand_ClearGroupingError')
;

-- 2021-04-06T15:18:27.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_ID,
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
     AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Process_ID = 584818
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- 2021-04-07T06:19:27.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message
SET MsgType='E', Updated=TO_TIMESTAMP('2021-04-07 09:19:27', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545031
;

-- 2021-04-07T06:19:43.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Auftragskandidat kann aufgrund von Konfigurationsproblemen der Kompensationsgruppe(n) nicht verarbeitet werden.', Updated=TO_TIMESTAMP('2021-04-07 09:19:43', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Message_ID = 545031
;

-- 2021-04-07T06:19:47.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Auftragskandidat kann aufgrund von Konfigurationsproblemen der Kompensationsgruppe(n) nicht verarbeitet werden.', Updated=TO_TIMESTAMP('2021-04-07 09:19:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'nl_NL'
  AND AD_Message_ID = 545031
;

-- 2021-04-07T06:19:52.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl
SET MsgText='Auftragskandidat kann aufgrund von Konfigurationsproblemen der Kompensationsgruppe(n) nicht verarbeitet werden.', Updated=TO_TIMESTAMP('2021-04-07 09:19:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Message_ID = 545031
;

-- 2021-04-07T06:20:23.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message
SET MsgText='Auftragskandidat kann aufgrund von Konfigurationsproblemen der Kompensationsgruppe(n) nicht verarbeitet werden.', Updated=TO_TIMESTAMP('2021-04-07 09:20:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545031
;

-- 2021-04-07T06:21:09.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET Name='Fehlerstatus der Kompensationsgruppe(n) löschen.', Updated=TO_TIMESTAMP('2021-04-07 09:21:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 584818
;

-- 2021-04-07T06:21:25.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET Description='Löscht den Kompensationsgruppenfehler der ausgewählten Kandidaten, sodass diese in Aufträge umgewandelt werden können.', EntityType='D', Updated=TO_TIMESTAMP('2021-04-07 09:21:25', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 584818
;

-- 2021-04-07T06:21:56.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl
SET Description='Löscht den Kompensationsgruppenfehler der ausgewählten Kandidaten, sodass diese in Aufträge umgewandelt werden können.', Updated=TO_TIMESTAMP('2021-04-07 09:21:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Process_ID = 584818
;

-- 2021-04-07T06:22:02.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET Description='Löscht den Kompensationsgruppenfehler der ausgewählten Kandidaten, sodass diese in Aufträge umgewandelt werden können.', Help=NULL, Name='Auswahl Transaktionen Freigeben', Updated=TO_TIMESTAMP('2021-04-07 09:22:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 584818
;

-- 2021-04-07T06:22:02.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl
SET Description='Löscht den Kompensationsgruppenfehler der ausgewählten Kandidaten, sodass diese in Aufträge umgewandelt werden können.', Updated=TO_TIMESTAMP('2021-04-07 09:22:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Process_ID = 584818
;

-- 2021-04-07T06:22:15.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl
SET Description='Löscht den Kompensationsgruppenfehler der ausgewählten Kandidaten, sodass diese in Aufträge umgewandelt werden können.', Name='Fehlerstatus der Kompensationsgruppe(n) löschen.', Updated=TO_TIMESTAMP('2021-04-07 09:22:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'nl_NL'
  AND AD_Process_ID = 584818
;

-- 2021-04-07T06:22:17.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process
SET Description='Löscht den Kompensationsgruppenfehler der ausgewählten Kandidaten, sodass diese in Aufträge umgewandelt werden können.', Help=NULL, Name='Fehlerstatus der Kompensationsgruppe(n) löschen.', Updated=TO_TIMESTAMP('2021-04-07 09:22:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 584818
;

-- 2021-04-07T06:22:17.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl
SET Name='Fehlerstatus der Kompensationsgruppe(n) löschen.', Updated=TO_TIMESTAMP('2021-04-07 09:22:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Process_ID = 584818
;

-- 2021-04-07T06:22:27.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl
SET Name='Fehlerstatus der Kompensationsgruppe(n) löschen.', Updated=TO_TIMESTAMP('2021-04-07 09:22:27', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Process_ID = 584818
;

-- 2021-04-07T06:23:13.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl
SET Description='lears the compensation group error of the selected candidates so that they can be transformed into sales orders.', IsTranslated='Y', Name='Clear compensation group error status.', Updated=TO_TIMESTAMP('2021-04-07 09:23:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Process_ID = 584818
;

-- 2021-04-07T07:05:51.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Table_ID, AD_Table_Process_ID, Created, CreatedBy, EntityType, IsActive, Updated, UpdatedBy, WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (0, 0, 584818, 540244, 540922, TO_TIMESTAMP('2021-04-07 10:05:51', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', TO_TIMESTAMP('2021-04-07 10:05:51', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N')
;

-- 2021-04-07T07:07:41.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-04-07 10:07:41', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 642617
;
