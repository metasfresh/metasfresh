-- 2023-07-27T11:51:11.735188500Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582577, 0, TO_TIMESTAMP('2023-07-27 13:51:11.591', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Delivery_Info', 'Delivery Inforamtion', TO_TIMESTAMP('2023-07-27 13:51:11.591', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-07-27T11:51:11.747867700Z
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
  AND t.AD_Element_ID = 582577
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- 2023-07-27T11:51:30.960928400Z
DELETE
FROM AD_Element_Trl
WHERE AD_Element_ID = 582577
;

-- 2023-07-27T11:51:30.971935500Z
DELETE
FROM AD_Element
WHERE AD_Element_ID = 582577
;

-- 2023-07-27T11:52:10.782087400Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582578, 0, 'Delivery_Info', TO_TIMESTAMP('2023-07-27 13:52:10.682', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Delivery Information', 'Delivery Information', TO_TIMESTAMP('2023-07-27 13:52:10.682', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-07-27T11:52:10.784660Z
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
  AND t.AD_Element_ID = 582578
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: Delivery_Info
-- 2023-07-27T11:52:15.882036500Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-07-27 13:52:15.881', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582578
  AND AD_Language = 'en_US'
;

-- 2023-07-27T11:52:15.919631600Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582578, 'en_US')
;

-- Element: Delivery_Info
-- 2023-07-27T11:52:36.308361400Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Anlieferinformationen', PrintName='Anlieferinformationen', Updated=TO_TIMESTAMP('2023-07-27 13:52:36.308', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582578
  AND AD_Language = 'de_CH'
;

-- 2023-07-27T11:52:36.310470700Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582578, 'de_CH')
;

-- Element: Delivery_Info
-- 2023-07-27T11:52:46.090275700Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Anlieferinformationen', PrintName='Anlieferinformationen', Updated=TO_TIMESTAMP('2023-07-27 13:52:46.09', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582578
  AND AD_Language = 'de_DE'
;

-- 2023-07-27T11:52:46.091324500Z
UPDATE AD_Element
SET Name='Anlieferinformationen', PrintName='Anlieferinformationen', Updated=TO_TIMESTAMP('2023-07-27 13:52:46.091', 'YYYY-MM-DD HH24:MI:SS.US')
WHERE AD_Element_ID = 582578
;

-- 2023-07-27T11:52:46.486683500Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582578, 'de_DE')
;

-- 2023-07-27T11:52:46.487731400Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582578, 'de_DE')
;

-- Column: C_BPartner_Location.Delivery_Info
-- 2023-07-27T12:33:59.870247500Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
                       IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587203, 582578, 0, 10, 293, 'Delivery_Info', TO_TIMESTAMP('2023-07-27 14:33:59.734', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'N', 'D', 0, 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Anlieferinformationen', 0, 0, TO_TIMESTAMP('2023-07-27 14:33:59.734', 'YYYY-MM-DD HH24:MI:SS.US'), 100,
        0)
;

-- 2023-07-27T12:33:59.872886200Z
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
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 587203
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-07-27T12:34:00.427806900Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582578)
;

-- 2023-07-27T12:34:03.546509800Z
/* DDL */

SELECT public.db_alter_table('C_BPartner_Location', 'ALTER TABLE public.C_BPartner_Location ADD COLUMN Delivery_Info VARCHAR(255)')
;

-- Field: Geschäftspartner(123,D) -> Adresse(222,D) -> Anlieferinformationen
-- Column: C_BPartner_Location.Delivery_Info
-- 2023-07-27T12:34:48.252963600Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587203, 717276, 0, 222, TO_TIMESTAMP('2023-07-27 14:34:48.07', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 255, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Anlieferinformationen', TO_TIMESTAMP('2023-07-27 14:34:48.07', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-07-27T12:34:48.255624700Z
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
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 717276
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-07-27T12:34:48.259298200Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582578)
;

-- 2023-07-27T12:34:48.276756200Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 717276
;

-- 2023-07-27T12:34:48.279221800Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(717276)
;

-- Field: Geschäftspartner(123,D) -> Adresse(222,D) -> Anlieferinformationen
-- Column: C_BPartner_Location.Delivery_Info
-- 2023-07-27T12:35:45.446564700Z
UPDATE AD_Field
SET SeqNo=80, Updated=TO_TIMESTAMP('2023-07-27 14:35:45.446', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Field_ID = 717276
;

-- 2023-07-27T12:37:43.743707400Z
INSERT INTO t_alter_column
VALUES ('c_bpartner_location', 'Delivery_Info', 'VARCHAR(255)', NULL, NULL)
;

-- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Anlieferinformationen
-- Column: C_BPartner_Location.Delivery_Info
-- 2023-07-27T12:40:29.168345600Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 717276, 0, 222, 1000034, 618597, 'F', TO_TIMESTAMP('2023-07-27 14:40:28.997', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Anlieferinformationen', 190, 0, 0, TO_TIMESTAMP('2023-07-27 14:40:28.997', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- Column: I_BPartner.Delivery_Info
-- 2023-07-27T13:15:44.565556300Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
                       IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587204, 582578, 0, 10, 533, 'Delivery_Info', TO_TIMESTAMP('2023-07-27 15:15:44.448', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'N', 'D', 0, 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Anlieferinformationen', 0, 0, TO_TIMESTAMP('2023-07-27 15:15:44.448', 'YYYY-MM-DD HH24:MI:SS.US'), 100,
        0)
;

-- 2023-07-27T13:15:44.568690900Z
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
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 587204
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-07-27T13:15:45.162407700Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582578)
;

-- 2023-07-27T13:15:48.367326Z
/* DDL */

SELECT public.db_alter_table('I_BPartner', 'ALTER TABLE public.I_BPartner ADD COLUMN Delivery_Info VARCHAR(255)')
;

-- Column: I_BPartner.GroupValue
-- 2023-07-31T17:47:40.235501200Z
UPDATE AD_Column
SET FieldLength=120, Updated=TO_TIMESTAMP('2023-07-31 19:47:40.234', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Column_ID = 7966
;

-- 2023-07-31T17:49:29.874419800Z
INSERT INTO t_alter_column
VALUES ('i_bpartner', 'GroupValue', 'VARCHAR(120)', NULL, NULL)
;

