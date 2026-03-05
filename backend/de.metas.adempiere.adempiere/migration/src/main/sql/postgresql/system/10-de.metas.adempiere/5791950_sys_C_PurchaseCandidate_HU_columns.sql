-- Run mode: SWING_CLIENT

-- Column: C_PurchaseCandidate.M_HU_PI_Item_Product_ID
-- 2026-03-05T15:17:31.440Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence,
                       MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592169, 542132, 0, 19, 540861, 'XX', 'M_HU_PI_Item_Product_ID', TO_TIMESTAMP('2026-03-05 15:17:31.169000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'de.metas.purchasecandidate', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0,
        'Packvorschrift', 0, 0, TO_TIMESTAMP('2026-03-05 15:17:31.169000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-05T15:17:31.446Z
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
  AND t.AD_Column_ID = 592169
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-05T15:17:31.470Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(542132)
;

-- 2026-03-05T15:18:28.366Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN M_HU_PI_Item_Product_ID NUMERIC(10)')
;

-- 2026-03-05T15:18:28.481Z
ALTER TABLE C_PurchaseCandidate
    ADD CONSTRAINT MHUPIItemProduct_CPurchaseCandidate FOREIGN KEY (M_HU_PI_Item_Product_ID) REFERENCES public.M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_PurchaseCandidate.QtyEnteredTU
-- 2026-03-05T15:19:09.629Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence,
                       MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592170, 542397, 0, 29, 540861, 'XX', 'QtyEnteredTU', TO_TIMESTAMP('2026-03-05 15:19:09.484000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'de.metas.purchasecandidate', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Menge TU', 0,
        0, TO_TIMESTAMP('2026-03-05 15:19:09.484000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-05T15:19:09.631Z
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
  AND t.AD_Column_ID = 592170
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-05T15:19:09.723Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(542397)
;

-- 2026-03-05T15:19:11.572Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN QtyEnteredTU NUMERIC')
;

