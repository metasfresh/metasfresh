-- 2021-04-09T10:57:57.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 579010, 0, 'Specification', TO_TIMESTAMP('2021-04-09 13:57:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Spezifikation', 'Spezifikation', TO_TIMESTAMP('2021-04-09 13:57:57', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-09T10:57:57.723Z
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
  AND t.AD_Element_ID = 579010
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2021-04-09T10:58:14.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Specification', PrintName='Specification', Updated=TO_TIMESTAMP('2021-04-09 13:58:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 579010
  AND AD_Language = 'en_US'
;

-- 2021-04-09T10:58:14.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579010, 'en_US')
;

-- 2021-04-09T11:16:32.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 573347, 579010, 0, 36, 53016, 'Specification', TO_TIMESTAMP('2021-04-09 14:16:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'EE01', 0, 1024, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Spezifikation', 0, 0, TO_TIMESTAMP('2021-04-09 14:16:32', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-04-09T11:16:32.638Z
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
  AND t.AD_Column_ID = 573347
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-04-09T11:16:32.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(579010)
;

-- 2021-04-09T11:16:39.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('PP_WF_Node_Product', 'ALTER TABLE public.PP_WF_Node_Product ADD COLUMN Specification TEXT')
;

-- 2021-04-09T11:17:53.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 573348, 579010, 0, 36, 53030, 'Specification', TO_TIMESTAMP('2021-04-09 14:17:53', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'EE01', 0, 1024, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Spezifikation', 0, 0, TO_TIMESTAMP('2021-04-09 14:17:53', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-04-09T11:17:53.558Z
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
  AND t.AD_Column_ID = 573348
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-04-09T11:17:53.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(579010)
;

-- 2021-04-09T11:18:04.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('PP_Order_Node_Product', 'ALTER TABLE public.PP_Order_Node_Product ADD COLUMN Specification TEXT')
;

-- 2021-04-11T11:00:51.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET AD_Reference_ID=10, FieldLength=255, Updated=TO_TIMESTAMP('2021-04-11 14:00:51', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573347
;

-- 2021-04-11T11:00:54.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column
VALUES ('pp_wf_node_product', 'Specification', 'VARCHAR(255)', NULL, NULL)
;

-- 2021-04-11T11:01:31.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET AD_Reference_ID=10, FieldLength=255, Updated=TO_TIMESTAMP('2021-04-11 14:01:31', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573348
;

-- 2021-04-11T11:15:46.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 573347, 643539, 0, 543533, 0, TO_TIMESTAMP('2021-04-11 14:15:46', 'YYYY-MM-DD HH24:MI:SS'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Spezifikation', 10, 90, 0, 1, 1, TO_TIMESTAMP('2021-04-11 14:15:46', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-11T11:15:46.913Z
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
  AND t.AD_Field_ID = 643539
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-04-11T11:15:46.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(579010)
;

-- 2021-04-11T11:15:46.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 643539
;

-- 2021-04-11T11:15:46.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(643539)
;

-- 2021-04-11T11:16:37.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 643539, 0, 543533, 545100, 583483, 'F', TO_TIMESTAMP('2021-04-11 14:16:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Spezifikation', 80, 0, 0, TO_TIMESTAMP('2021-04-11 14:16:36', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-11T11:17:40.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=60, Updated=TO_TIMESTAMP('2021-04-11 14:17:40', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 583483
;

-- 2021-04-11T11:17:40.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=70, Updated=TO_TIMESTAMP('2021-04-11 14:17:40', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 579292
;

-- 2021-04-11T11:20:03.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, DisplayLength, EntityType, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 573348, 643540, 0, 53042, 0, TO_TIMESTAMP('2021-04-11 14:20:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0, 'D', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Spezifikation', 120, 120, 0, 1, 1, TO_TIMESTAMP('2021-04-11 14:20:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-11T11:20:03.043Z
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
  AND t.AD_Field_ID = 643540
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-04-11T11:20:03.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(579010)
;

-- 2021-04-11T11:20:03.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 643540
;

-- 2021-04-11T11:20:03.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(643540)
;
 