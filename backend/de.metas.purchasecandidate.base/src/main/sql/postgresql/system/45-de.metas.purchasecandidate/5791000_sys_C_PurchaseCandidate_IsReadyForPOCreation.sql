-- Run mode: SWING_CLIENT

-- AD_Element: IsReadyForPOCreation
-- 2026-03-04T15:59:59.000Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584618, 0, 'IsReadyForPOCreation', TO_TIMESTAMP('2026-03-04 15:59:59', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Set to Y when PP_Product_Planning.IsCreatePlan=Y and a sales order is linked. Marks candidates for automatic PO creation by the debouncer processor. Candidates without this flag remain available for manual PO creation.', 'de.metas.purchasecandidate', 'Y', 'Ready for PO Creation', 'Ready for PO Creation', TO_TIMESTAMP('2026-03-04 15:59:59', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Element_ID = 584618
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Column: C_PurchaseCandidate.IsReadyForPOCreation
-- 2026-03-04T16:00:00.000Z
-- AD_Element_ID=584618, AD_Column_ID=592168
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated,
                       IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592168, 584618, 0, 20, 540861, 'XX', 'IsReadyForPOCreation', TO_TIMESTAMP('2026-03-04 16:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'N', 'Set to Y when PP_Product_Planning.IsCreatePlan=Y and a sales order is linked. Marks candidates for automatic PO creation by the debouncer processor. Candidates without this flag remain available for manual PO creation.', 'de.metas.purchasecandidate', 0, 1,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Ready for PO Creation', 'NP', 0, 0,
        TO_TIMESTAMP('2026-03-04 16:00:00', 'YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-04T16:00:00.100Z
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
  AND t.AD_Column_ID = 592168
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-04T16:00:00.200Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN IsReadyForPOCreation CHAR(1) DEFAULT ''N'' CHECK (IsReadyForPOCreation IN (''Y'',''N'')) NOT NULL')
;
