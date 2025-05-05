-- Run mode: SWING_CLIENT

-- 2023-10-12T07:04:56.613Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582755, 0, 'Bill_BPartner_ExternalReference', TO_TIMESTAMP('2023-10-12 09:04:56.461', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Bill Business Partner ExternalReference', 'Bill Business Partner ExternalReference', TO_TIMESTAMP('2023-10-12 09:04:56.461', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T07:04:56.624Z
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
  AND t.AD_Element_ID = 582755
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: Bill_BPartner_ExternalReference
-- 2023-10-12T07:05:13.605Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:05:13.605', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582755
  AND AD_Language = 'en_US'
;

-- 2023-10-12T07:05:13.627Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582755, 'en_US')
;

-- 2023-10-12T07:05:37.030Z
DELETE
FROM AD_Element_Trl
WHERE AD_Element_ID = 582755
;

-- 2023-10-12T07:05:37.045Z
DELETE
FROM AD_Element
WHERE AD_Element_ID = 582755
;

-- 2023-10-12T07:15:40.749Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582756, 0, 'Bill_BPartner_ExternalReference', TO_TIMESTAMP('2023-10-12 09:15:40.58', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Rechnungspartner Externe Referenz', 'Rechnungspartner Externe Referenz', TO_TIMESTAMP('2023-10-12 09:15:40.58', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T07:15:40.751Z
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
  AND t.AD_Element_ID = 582756
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: Bill_BPartner_ExternalReference
-- 2023-10-12T07:15:48.884Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:15:48.884', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582756
  AND AD_Language = 'de_CH'
;

-- 2023-10-12T07:15:48.887Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582756, 'de_CH')
;

-- Element: Bill_BPartner_ExternalReference
-- 2023-10-12T07:15:50.044Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:15:50.044', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582756
  AND AD_Language = 'de_DE'
;

-- 2023-10-12T07:15:50.047Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582756, 'de_DE')
;

-- 2023-10-12T07:15:50.048Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582756, 'de_DE')
;

-- Element: Bill_BPartner_ExternalReference
-- 2023-10-12T07:16:44.254Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Bill Partner External Reference', PrintName='Bill Partner External Reference', Updated=TO_TIMESTAMP('2023-10-12 09:16:44.254', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582756
  AND AD_Language = 'en_US'
;

-- 2023-10-12T07:16:44.256Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582756, 'en_US')
;

-- 2023-10-12T07:24:24.583Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582757, 0, 'Bill_Location_ExternalReference', TO_TIMESTAMP('2023-10-12 09:24:24.465', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Rechnungsadresse Externe Referenz', 'Rechnungsadresse Externe Referenz', TO_TIMESTAMP('2023-10-12 09:24:24.465', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T07:24:24.584Z
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
  AND t.AD_Element_ID = 582757
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: Bill_Location_ExternalReference
-- 2023-10-12T07:24:31.646Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:24:31.646', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582757
  AND AD_Language = 'de_CH'
;

-- 2023-10-12T07:24:31.648Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582757, 'de_CH')
;

-- Element: Bill_Location_ExternalReference
-- 2023-10-12T07:24:32.650Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:24:32.65', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582757
  AND AD_Language = 'de_DE'
;

-- 2023-10-12T07:24:32.652Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582757, 'de_DE')
;

-- 2023-10-12T07:24:32.653Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582757, 'de_DE')
;

-- Element: Bill_Location_ExternalReference
-- 2023-10-12T07:25:15.103Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Bill Location External Reference', PrintName='Bill Location External Reference', Updated=TO_TIMESTAMP('2023-10-12 09:25:15.103', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582757
  AND AD_Language = 'en_US'
;

-- 2023-10-12T07:25:15.106Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582757, 'en_US')
;

-- 2023-10-12T07:32:15.367Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582758, 0, 'Bill_User_ExternalReference', TO_TIMESTAMP('2023-10-12 09:32:15.226', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Rechnungskontakt Externe Referenz', 'Rechnungskontakt Externe Referenz', TO_TIMESTAMP('2023-10-12 09:32:15.226', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T07:32:15.369Z
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
  AND t.AD_Element_ID = 582758
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: Bill_User_ExternalReference
-- 2023-10-12T07:32:22.300Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:32:22.3', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582758
  AND AD_Language = 'de_CH'
;

-- 2023-10-12T07:32:22.302Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582758, 'de_CH')
;

-- Element: Bill_User_ExternalReference
-- 2023-10-12T07:32:25.100Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:32:25.1', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582758
  AND AD_Language = 'de_DE'
;

-- 2023-10-12T07:32:25.102Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582758, 'de_DE')
;

-- 2023-10-12T07:32:25.103Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582758, 'de_DE')
;

-- Element: Bill_User_ExternalReference
-- 2023-10-12T07:37:55.242Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Bill Contact External Reference', PrintName='Bill Contact External Reference', Updated=TO_TIMESTAMP('2023-10-12 09:37:55.242', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582758
  AND AD_Language = 'en_US'
;

-- 2023-10-12T07:37:55.245Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582758, 'en_US')
;

-- 2023-10-12T07:40:08.507Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582759, 0, 'AD_User_InCharge_ExternalReference', TO_TIMESTAMP('2023-10-12 09:40:08.405', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Betreuer Externe Referenz', 'Betreuer Externe Referenz', TO_TIMESTAMP('2023-10-12 09:40:08.405', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T07:40:08.510Z
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
  AND t.AD_Element_ID = 582759
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: AD_User_InCharge_ExternalReference
-- 2023-10-12T07:40:17.789Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:40:17.789', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582759
  AND AD_Language = 'de_CH'
;

-- 2023-10-12T07:40:17.791Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582759, 'de_CH')
;

-- Element: AD_User_InCharge_ExternalReference
-- 2023-10-12T07:40:27.610Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-10-12 09:40:27.61', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582759
  AND AD_Language = 'de_DE'
;

-- 2023-10-12T07:40:27.612Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582759, 'de_DE')
;

-- 2023-10-12T07:40:27.614Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582759, 'de_DE')
;

-- Element: AD_User_InCharge_ExternalReference
-- 2023-10-12T07:40:58.572Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Responsible External Reference', PrintName='Responsible External Reference', Updated=TO_TIMESTAMP('2023-10-12 09:40:58.572', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582759
  AND AD_Language = 'en_US'
;

-- 2023-10-12T07:40:58.573Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582759, 'en_US')
;

-- Column: I_Invoice_Candidate.Bill_BPartner_Value
-- 2023-10-12T07:42:57.386Z
UPDATE AD_Column
SET IsMandatory='N', Updated=TO_TIMESTAMP('2023-10-12 09:42:57.386', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Column_ID = 584199
;

-- Column: I_Invoice_Candidate.Bill_Location_ID
-- 2023-10-12T07:43:08.944Z
UPDATE AD_Column
SET IsMandatory='N', Updated=TO_TIMESTAMP('2023-10-12 09:43:08.944', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Column_ID = 584168
;

-- Column: I_Invoice_Candidate.Bill_BPartner_ExternalReference
-- 2023-10-12T07:50:34.521Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
                       IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587546, 582756, 0, 10, 542207, 'Bill_BPartner_ExternalReference', TO_TIMESTAMP('2023-10-12 09:50:34.402', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'N', 'D', 0, 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Rechnungspartner Externe Referenz', 0, 0,
        TO_TIMESTAMP('2023-10-12 09:50:34.402', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 0)
;

-- 2023-10-12T07:50:34.524Z
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
  AND t.AD_Column_ID = 587546
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-10-12T07:50:34.529Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582756)
;

-- Column: I_Invoice_Candidate.Bill_Location_ExternalReference
-- 2023-10-12T07:50:52.139Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
                       IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587547, 582757, 0, 10, 542207, 'Bill_Location_ExternalReference', TO_TIMESTAMP('2023-10-12 09:50:52.038', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'N', 'D', 0, 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Rechnungsadresse Externe Referenz', 0, 0,
        TO_TIMESTAMP('2023-10-12 09:50:52.038', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 0)
;

-- 2023-10-12T07:50:52.141Z
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
  AND t.AD_Column_ID = 587547
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-10-12T07:50:52.144Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582757)
;

-- Column: I_Invoice_Candidate.Bill_User_ExternalReference
-- 2023-10-12T07:51:22.629Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
                       IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587548, 582758, 0, 10, 542207, 'Bill_User_ExternalReference', TO_TIMESTAMP('2023-10-12 09:51:22.448', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'N', 'D', 0, 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Rechnungskontakt Externe Referenz', 0, 0,
        TO_TIMESTAMP('2023-10-12 09:51:22.448', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 0)
;

-- 2023-10-12T07:51:22.631Z
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
  AND t.AD_Column_ID = 587548
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-10-12T07:51:22.634Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582758)
;

-- Column: I_Invoice_Candidate.AD_User_InCharge_ExternalReference
-- 2023-10-12T07:52:42.862Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
                       IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587549, 582759, 0, 10, 542207, 'AD_User_InCharge_ExternalReference', TO_TIMESTAMP('2023-10-12 09:52:42.753', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'N', 'D', 0, 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Betreuer Externe Referenz', 0, 0,
        TO_TIMESTAMP('2023-10-12 09:52:42.753', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 0)
;

-- 2023-10-12T07:52:42.864Z
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
  AND t.AD_Column_ID = 587549
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-10-12T07:52:42.866Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582759)
;

-- Column: I_Invoice_Candidate.ExternalSystem
-- 2023-10-12T07:53:28.227Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                       IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch,
                       Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 587550, 577608, 0, 17, 541117, 542207, 'ExternalSystem', TO_TIMESTAMP('2023-10-12 09:53:28.113', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'N', 'Name of an external system (e.g. Github )', 'D', 0, 255, 'Name of an external system (e.g. Github )', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0,
        'External system', 0, 0, TO_TIMESTAMP('2023-10-12 09:53:28.113', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 0)
;

-- 2023-10-12T07:53:28.229Z
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
  AND t.AD_Column_ID = 587550
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-10-12T07:53:28.231Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(577608)
;

-- 2023-10-12T07:53:41.390Z
/* DDL */

SELECT public.db_alter_table('I_Invoice_Candidate', 'ALTER TABLE public.I_Invoice_Candidate ADD COLUMN ExternalSystem VARCHAR(255)')
;

-- 2023-10-12T07:54:22.842Z
/* DDL */

SELECT public.db_alter_table('I_Invoice_Candidate', 'ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Bill_BPartner_ExternalReference VARCHAR(255)')
;

-- 2023-10-12T07:54:36.782Z
INSERT INTO t_alter_column
VALUES ('i_invoice_candidate', 'Bill_BPartner_Value', 'VARCHAR(255)', NULL, NULL)
;

-- 2023-10-12T07:54:36.789Z
INSERT INTO t_alter_column
VALUES ('i_invoice_candidate', 'Bill_BPartner_Value', NULL, 'NULL', NULL)
;

-- 2023-10-12T07:54:45.933Z
/* DDL */

SELECT public.db_alter_table('I_Invoice_Candidate', 'ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Bill_Location_ExternalReference VARCHAR(255)')
;

-- 2023-10-12T07:54:57.618Z
INSERT INTO t_alter_column
VALUES ('i_invoice_candidate', 'Bill_Location_ID', 'NUMERIC(10)', NULL, NULL)
;

-- 2023-10-12T07:54:57.621Z
INSERT INTO t_alter_column
VALUES ('i_invoice_candidate', 'Bill_Location_ID', NULL, 'NULL', NULL)
;

-- 2023-10-12T07:55:21.014Z
/* DDL */

SELECT public.db_alter_table('I_Invoice_Candidate', 'ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Bill_User_ExternalReference VARCHAR(255)')
;

-- 2023-10-12T07:55:35.358Z
/* DDL */

SELECT public.db_alter_table('I_Invoice_Candidate', 'ALTER TABLE public.I_Invoice_Candidate ADD COLUMN AD_User_InCharge_ExternalReference VARCHAR(255)')
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Rechnungspartner Externe Referenz
-- Column: I_Invoice_Candidate.Bill_BPartner_ExternalReference
-- 2023-10-12T08:25:25.108Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587546, 721030, 0, 546594, TO_TIMESTAMP('2023-10-12 10:25:24.929', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 255, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Rechnungspartner Externe Referenz', TO_TIMESTAMP('2023-10-12 10:25:24.929', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T08:25:25.111Z
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
  AND t.AD_Field_ID = 721030
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-10-12T08:25:25.114Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582756)
;

-- 2023-10-12T08:25:25.124Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 721030
;

-- 2023-10-12T08:25:25.127Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(721030)
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Rechnungsadresse Externe Referenz
-- Column: I_Invoice_Candidate.Bill_Location_ExternalReference
-- 2023-10-12T08:25:25.218Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587547, 721031, 0, 546594, TO_TIMESTAMP('2023-10-12 10:25:25.135', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 255, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Rechnungsadresse Externe Referenz', TO_TIMESTAMP('2023-10-12 10:25:25.135', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T08:25:25.220Z
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
  AND t.AD_Field_ID = 721031
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-10-12T08:25:25.221Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582757)
;

-- 2023-10-12T08:25:25.224Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 721031
;

-- 2023-10-12T08:25:25.225Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(721031)
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Rechnungskontakt Externe Referenz
-- Column: I_Invoice_Candidate.Bill_User_ExternalReference
-- 2023-10-12T08:25:25.317Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587548, 721032, 0, 546594, TO_TIMESTAMP('2023-10-12 10:25:25.227', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 255, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Rechnungskontakt Externe Referenz', TO_TIMESTAMP('2023-10-12 10:25:25.227', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T08:25:25.318Z
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
  AND t.AD_Field_ID = 721032
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-10-12T08:25:25.319Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582758)
;

-- 2023-10-12T08:25:25.321Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 721032
;

-- 2023-10-12T08:25:25.323Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(721032)
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Betreuer Externe Referenz
-- Column: I_Invoice_Candidate.AD_User_InCharge_ExternalReference
-- 2023-10-12T08:25:25.422Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587549, 721033, 0, 546594, TO_TIMESTAMP('2023-10-12 10:25:25.324', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 255, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Betreuer Externe Referenz', TO_TIMESTAMP('2023-10-12 10:25:25.324', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T08:25:25.424Z
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
  AND t.AD_Field_ID = 721033
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2023-10-12T08:25:25.425Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(582759)
;

-- 2023-10-12T08:25:25.428Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 721033
;

-- 2023-10-12T08:25:25.428Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(721033)
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> External system
-- Column: I_Invoice_Candidate.ExternalSystem
-- 2023-10-12T08:25:25.513Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 587550, 721034, 0, 546594, TO_TIMESTAMP('2023-10-12 10:25:25.431', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Name of an external system (e.g. Github )', 255, 'D', 'Name of an external system (e.g. Github )', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'External system', TO_TIMESTAMP('2023-10-12 10:25:25.431', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-10-12T08:25:25.515Z
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
  AND t.AD_Field_ID = 721034
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2023-10-12T08:25:25.516Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(577608)
;

-- 2023-10-12T08:25:25.526Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 721034
;

-- 2023-10-12T08:25:25.527Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(721034)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Rechnungspartner Externe Referenz
-- Column: I_Invoice_Candidate.Bill_BPartner_ExternalReference
-- 2023-10-12T08:31:07.648Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 721030, 0, 546594, 549832, 620895, 'F', TO_TIMESTAMP('2023-10-12 10:31:07.523', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Rechnungspartner Externe Referenz', 50, 0, 0, TO_TIMESTAMP('2023-10-12 10:31:07.523', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Rechnungsadresse Externe Referenz
-- Column: I_Invoice_Candidate.Bill_Location_ExternalReference
-- 2023-10-12T08:32:26.516Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 721031, 0, 546594, 549832, 620896, 'F', TO_TIMESTAMP('2023-10-12 10:32:26.341', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Rechnungsadresse Externe Referenz', 65, 0, 0, TO_TIMESTAMP('2023-10-12 10:32:26.341', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Rechnungskontakt Externe Referenz
-- Column: I_Invoice_Candidate.Bill_User_ExternalReference
-- 2023-10-12T08:33:01.278Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 721032, 0, 546594, 549832, 620897, 'F', TO_TIMESTAMP('2023-10-12 10:33:01.088', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Rechnungskontakt Externe Referenz', 75, 0, 0, TO_TIMESTAMP('2023-10-12 10:33:01.088', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Betreuer Externe Referenz
-- Column: I_Invoice_Candidate.AD_User_InCharge_ExternalReference
-- 2023-10-12T08:33:55.484Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 721033, 0, 546594, 549841, 620898, 'F', TO_TIMESTAMP('2023-10-12 10:33:55.37', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Betreuer Externe Referenz', 85, 0, 0, TO_TIMESTAMP('2023-10-12 10:33:55.37', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.External system
-- Column: I_Invoice_Candidate.ExternalSystem
-- 2023-10-12T08:35:31.739Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, Help, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 721034, 0, 546594, 549841, 620899, 'F', TO_TIMESTAMP('2023-10-12 10:35:31.602', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Name of an external system (e.g. Github )', 'Name of an external system (e.g. Github )', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'External system', 110, 0, 0, TO_TIMESTAMP('2023-10-12 10:35:31.602', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

