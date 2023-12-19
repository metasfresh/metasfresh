-- Table: purchase_prices_in_stock_uom_plv_v
-- 2023-12-11T09:36:59.788Z
INSERT INTO AD_Table (AccessLevel, ACTriggerLength, AD_Client_ID, AD_Org_ID, AD_Table_ID, CopyColumnsFromTable, Created, CreatedBy, EntityType, ImportTable, IsActive, IsAutocomplete, IsChangeLog, IsDeleteable, IsDLM, IsEnableRemoteCacheInvalidation, IsHighVolume, IsSecurityEnabled, IsView, LoadSeq, Name, PersonalDataCategory, ReplicationType, TableName, TooltipType, Updated, UpdatedBy)
VALUES ('3', 0, 0, 0, 542382, 'N', TO_TIMESTAMP('2023-12-11 09:36:59.499000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'D', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 0, 'purchase_prices_in_stock_uom_plv_v', 'NP', 'L', 'purchase_prices_in_stock_uom_plv_v', 'DTI',
        TO_TIMESTAMP('2023-12-11 09:36:59.499000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100)
;

-- 2023-12-11T09:36:59.829Z
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Table_ID,
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
     AD_Table t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Table_ID = 542382
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID)
;

-- Column: purchase_prices_in_stock_uom_plv_v.M_PriceList_ID
-- 2023-12-11T09:46:26.902Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587718, 449, 0, 19, 542382, 'M_PriceList_ID', TO_TIMESTAMP('2023-12-11 09:46:26.725000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Bezeichnung der Preisliste', 'D', 0, 10, 'Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Preisliste', 0, 0, TO_TIMESTAMP('2023-12-11 09:46:26.725000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T09:46:26.914Z
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
  AND t.AD_Column_ID = 587718
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T09:46:26.939Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(449)
;

-- Column: purchase_prices_in_stock_uom_plv_v.C_Currency_ID
-- 2023-12-11T09:46:44.526Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587719, 193, 0, 19, 542382, 'C_Currency_ID', TO_TIMESTAMP('2023-12-11 09:46:44.410000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Die Währung für diesen Eintrag', 'D', 0, 10, 'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Währung', 0, 0, TO_TIMESTAMP('2023-12-11 09:46:44.410000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T09:46:44.529Z
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
  AND t.AD_Column_ID = 587719
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T09:46:44.532Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(193)
;

-- Column: purchase_prices_in_stock_uom_plv_v.M_PriceList_Version_ID
-- 2023-12-11T09:47:09.081Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587720, 450, 0, 19, 542382, 'M_PriceList_Version_ID', TO_TIMESTAMP('2023-12-11 09:47:08.958000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Bezeichnet eine einzelne Version der Preisliste', 'D', 0, 10,
        'Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Version Preisliste', 0, 0,
        TO_TIMESTAMP('2023-12-11 09:47:08.958000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T09:47:09.084Z
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
  AND t.AD_Column_ID = 587720
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T09:47:09.087Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(450)
;

-- Column: purchase_prices_in_stock_uom_plv_v.ValidFrom
-- 2023-12-11T09:48:43.998Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587721, 617, 0, 15, 542382, 'ValidFrom', TO_TIMESTAMP('2023-12-11 09:48:43.880000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Gültig ab inklusiv (erster Tag)', 'D', 0, 60, '"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Gültig ab', 0, 0, TO_TIMESTAMP('2023-12-11 09:48:43.880000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T09:48:44.001Z
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
  AND t.AD_Column_ID = 587721
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T09:48:44.004Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(617)
;

-- Column: purchase_prices_in_stock_uom_plv_v.ValidTo
-- 2023-12-11T09:50:34.587Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587722, 618, 0, 15, 542382, 'ValidTo', TO_TIMESTAMP('2023-12-11 09:50:34.465000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Gültig bis inklusiv (letzter Tag)', 'D', 0, 60, '"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Gültig bis', 0, 0, TO_TIMESTAMP('2023-12-11 09:50:34.465000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T09:50:34.588Z
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
  AND t.AD_Column_ID = 587722
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T09:50:34.591Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(618)
;

-- Column: purchase_prices_in_stock_uom_plv_v.M_Product_ID
-- 2023-12-11T09:51:19.717Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo,
                       Updated, UpdatedBy, Version)
VALUES (0, 587723, 454, 0, 19, 542382, 'M_Product_ID', TO_TIMESTAMP('2023-12-11 09:51:19.571000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'Produkt, Leistung, Artikel', 'D', 0, 10, 'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Produkt', 0, 0, TO_TIMESTAMP('2023-12-11 09:51:19.571000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T09:51:19.720Z
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
  AND t.AD_Column_ID = 587723
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T09:51:19.724Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(454)
;

-- 2023-12-11T09:52:30.684Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582845, 0, 'ProductPriceInStockUOM', TO_TIMESTAMP('2023-12-11 09:52:30.571000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'D', 'Y', 'ProductPriceInStockUOM', 'ProductPriceInStockUOM', TO_TIMESTAMP('2023-12-11 09:52:30.571000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100)
;

-- 2023-12-11T09:52:30.697Z
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
  AND t.AD_Element_ID = 582845
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Column: purchase_prices_in_stock_uom_plv_v.ProductPriceInStockUOM
-- 2023-12-11T09:54:56.826Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
                       IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 587724, 582845, 0, 37, 542382, 'ProductPriceInStockUOM', TO_TIMESTAMP('2023-12-11 09:54:56.715000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N', 'D', 0, 60, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'ProductPriceInStockUOM', 0, 0,
        TO_TIMESTAMP('2023-12-11 09:54:56.715000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 0)
;

-- 2023-12-11T09:54:56.828Z
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
  AND t.AD_Column_ID = 587724
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-12-11T09:54:56.832Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582845)
;

-- Column: purchase_prices_in_stock_uom_plv_v.C_Currency_ID
-- 2023-12-11T11:17:14.603Z
UPDATE AD_Column
SET IsMandatory='Y', Updated=TO_TIMESTAMP('2023-12-11 11:17:14.602000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 587719
;

-- Column: purchase_prices_in_stock_uom_plv_v.C_UOM_ID
-- 2023-12-11T11:17:16.511Z
UPDATE AD_Column
SET IsMandatory='Y', Updated=TO_TIMESTAMP('2023-12-11 11:17:16.511000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 587725
;

-- Column: purchase_prices_in_stock_uom_plv_v.M_PriceList_ID
-- 2023-12-11T11:17:18.691Z
UPDATE AD_Column
SET IsMandatory='Y', Updated=TO_TIMESTAMP('2023-12-11 11:17:18.691000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 587718
;

-- Column: purchase_prices_in_stock_uom_plv_v.M_PriceList_Version_ID
-- 2023-12-11T11:17:20.059Z
UPDATE AD_Column
SET IsMandatory='Y', Updated=TO_TIMESTAMP('2023-12-11 11:17:20.058000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 587720
;

-- Column: purchase_prices_in_stock_uom_plv_v.M_Product_ID
-- 2023-12-11T11:17:21.530Z
UPDATE AD_Column
SET IsMandatory='Y', Updated=TO_TIMESTAMP('2023-12-11 11:17:21.530000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 587723
;

-- Column: purchase_prices_in_stock_uom_plv_v.ProductPriceInStockUOM
-- 2023-12-11T11:17:22.968Z
UPDATE AD_Column
SET IsMandatory='Y', Updated=TO_TIMESTAMP('2023-12-11 11:17:22.968000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 587724
;

-- Column: purchase_prices_in_stock_uom_plv_v.ValidFrom
-- 2023-12-11T11:17:24.321Z
UPDATE AD_Column
SET IsMandatory='Y', Updated=TO_TIMESTAMP('2023-12-11 11:17:24.321000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 587721
;

-- Column: purchase_prices_in_stock_uom_plv_v.ValidTo
-- 2023-12-11T11:17:26.778Z
UPDATE AD_Column
SET IsMandatory='Y', Updated=TO_TIMESTAMP('2023-12-11 11:17:26.778000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Column_ID = 587722
;

