-- 2023-03-22T14:45:11.885Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582147, 0, 'IsOnDistinctICTypes', TO_TIMESTAMP('2023-03-22 16:45:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'On Distinct Invoice DocTypes', 'On Distinct Invoice DocTypes', TO_TIMESTAMP('2023-03-22 16:45:11', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2023-03-22T14:45:11.893Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
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
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582147
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Column: C_DocType_Invoicing_Pool.IsOnDistinctICTypes
-- 2023-03-22T14:45:37.570Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 586320, 582147, 0, 20, 542277, 'IsOnDistinctICTypes', TO_TIMESTAMP('2023-03-22 16:45:37', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'N', 'D', 0, 1, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'On Distinct Invoice DocTypes', 0, 0, TO_TIMESTAMP('2023-03-22 16:45:37', 'YYYY-MM-DD HH24:MI:SS'),
        100, 0)
;

-- 2023-03-22T14:45:37.572Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 586320
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-03-22T14:45:37.602Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582147)
;

-- Element: IsOnDistinctICTypes
-- 2023-03-22T14:46:51.965Z
UPDATE AD_Element_Trl
SET Name='Only On Distinct Invoice DocTypes', PrintName='Only On Distinct Invoice DocTypes', Updated=TO_TIMESTAMP('2023-03-22 16:46:51', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 582147
  AND AD_Language = 'en_US'
;

-- 2023-03-22T14:46:51.972Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582147, 'en_US')
;

-- Element: IsOnDistinctICTypes
-- 2023-03-22T14:48:21.119Z
UPDATE AD_Element_Trl
SET Description='When on ''Y'' , the doc type pool is only applied when the invoice candidates from the selection have different invoice doc types. When ''N'', the pool is applied in any circumstances.', Help='When on ''Y'' , the doc type pool is only applied when the invoice candidates from the selection have different invoice doc types. When ''N'', the pool is applied in any circumstances.',
    Updated=TO_TIMESTAMP('2023-03-22 16:48:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 582147
  AND AD_Language = 'en_US'
;

-- 2023-03-22T14:48:21.122Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582147, 'en_US')
;

-- Element: IsOnDistinctICTypes
-- 2023-03-22T14:49:34.036Z
UPDATE AD_Element_Trl
SET Description='When on ''Y'' , the invoicing pool is only applied when the invoice candidates from the selection have different invoice doc types. When ''N'', the pool is applied in any circumstances.', Help='When on ''Y'' , the invoicing pool is only applied when the invoice candidates from the selection have different invoice doc types. When ''N'', the pool is applied in any circumstances.',
    Updated=TO_TIMESTAMP('2023-03-22 16:49:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 582147
  AND AD_Language = 'en_US'
;

-- 2023-03-22T14:49:34.038Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582147, 'en_US')
;

-- Table: C_DocType_Invoicing_Pool
-- 2023-03-22T14:49:54.068Z
UPDATE AD_Table
SET AD_Window_ID=541658, Updated=TO_TIMESTAMP('2023-03-22 16:49:54', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Table_ID = 542277
;

-- Field: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> On Distinct Invoice DocTypes
-- Column: C_DocType_Invoicing_Pool.IsOnDistinctICTypes
-- 2023-03-22T14:50:17.409Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 586320, 712848, 0, 546734, TO_TIMESTAMP('2023-03-22 16:50:17', 'YYYY-MM-DD HH24:MI:SS'), 100, 1, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'On Distinct Invoice DocTypes', TO_TIMESTAMP('2023-03-22 16:50:17', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2023-03-22T14:50:17.410Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
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
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Field_ID = 712848
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-03-22T14:50:17.412Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582147)
;

-- 2023-03-22T14:50:17.430Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 712848
;

-- 2023-03-22T14:50:17.432Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(712848)
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 20 -> main.On Distinct Invoice DocTypes
-- Column: C_DocType_Invoicing_Pool.IsOnDistinctICTypes
-- 2023-03-22T14:51:37.761Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 712848, 0, 546734, 550212, 616022, 'F', TO_TIMESTAMP('2023-03-22 16:51:37', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'On Distinct Invoice DocTypes', 30, 0, 0, TO_TIMESTAMP('2023-03-22 16:51:37', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 10 -> main.Name
-- Column: C_DocType_Invoicing_Pool.Name
-- 2023-03-22T14:52:08.018Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=10, Updated=TO_TIMESTAMP('2023-03-22 16:52:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 614657
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 20 -> main.Aktiv
-- Column: C_DocType_Invoicing_Pool.IsActive
-- 2023-03-22T14:52:08.025Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=20, Updated=TO_TIMESTAMP('2023-03-22 16:52:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 614641
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 20 -> main.Verkaufstransaktion
-- Column: C_DocType_Invoicing_Pool.IsSOTrx
-- 2023-03-22T14:52:08.032Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=30, Updated=TO_TIMESTAMP('2023-03-22 16:52:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 614648
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 20 -> main.On Distinct Invoice DocTypes
-- Column: C_DocType_Invoicing_Pool.IsOnDistinctICTypes
-- 2023-03-22T14:52:08.039Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=40, Updated=TO_TIMESTAMP('2023-03-22 16:52:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 616022
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 10 -> main.Positiver Betrag Belegart
-- Column: C_DocType_Invoicing_Pool.Positive_Amt_C_DocType_ID
-- 2023-03-22T14:52:08.046Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=50, Updated=TO_TIMESTAMP('2023-03-22 16:52:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 614639
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 10 -> main.Negativer Betrag Belegart
-- Column: C_DocType_Invoicing_Pool.Negative_Amt_C_DocType_ID
-- 2023-03-22T14:52:08.052Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=60, Updated=TO_TIMESTAMP('2023-03-22 16:52:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 614640
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 20 -> org.Sektion
-- Column: C_DocType_Invoicing_Pool.AD_Org_ID
-- 2023-03-22T14:52:08.059Z
UPDATE AD_UI_Element
SET IsDisplayedGrid='Y', SeqNoGrid=70, Updated=TO_TIMESTAMP('2023-03-22 16:52:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 614642
;





-- 2023-03-22T15:56:28.890Z
/* DDL */ SELECT public.db_alter_table('C_DocType_Invoicing_Pool','ALTER TABLE public.C_DocType_Invoicing_Pool ADD COLUMN IsOnDistinctICTypes CHAR(1) DEFAULT ''N'' CHECK (IsOnDistinctICTypes IN (''Y'',''N'')) NOT NULL')
;











-- Column: C_DocType_Invoicing_Pool.IsOnDistinctICTypes
-- 2023-03-23T09:28:29.033Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2023-03-23 11:28:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586320
;

-- 2023-03-23T09:28:31.596Z
INSERT INTO t_alter_column values('c_doctype_invoicing_pool','IsOnDistinctICTypes','CHAR(1)',null,'Y')
;

-- 2023-03-23T09:28:31.607Z
UPDATE C_DocType_Invoicing_Pool SET IsOnDistinctICTypes='Y' WHERE IsOnDistinctICTypes IS NULL or IsOnDistinctICTypes = 'N'
;

