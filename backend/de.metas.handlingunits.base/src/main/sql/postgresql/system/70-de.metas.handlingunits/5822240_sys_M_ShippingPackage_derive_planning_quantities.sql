-- Task Q14 (delivery planning quantities): all four quantity figures on M_ShippingPackage (planned load,
-- planned discharge, actual load, actual discharge) become derived from the planning they are allocated
-- to, read-only on the instruction. The planning is reached through M_Delivery_Planning_Alloc - there is
-- no direct FK from M_ShippingPackage to M_Delivery_Planning - and there is exactly one active allocation
-- per package (M_Delivery_Planning_Alloc_Package_UQ, 5820410, a UNIQUE partial index on M_ShippingPackage_ID
-- WHERE IsActive='Y'), so each correlated subselect returns at most one row by construction.
--
-- Runs AFTER 5822230, which drops the two physical columns this converts to ColumnSQL.
--
-- Wording: the planned pair reuses the planning's own AD_Elements (581794 "Geplante Verlademenge",
-- 581795 "Geplante Entlademenge" as of 5822330, later on this branch) rather than minting new ones or
-- using an AD_Name_ID override - the same element gives identical wording on both tables by
-- construction.
--
-- IsLazyLoading='Y' on all four: display-only on the instruction line, no production Java reads them
-- (DeliveryPlanningRepository#createShippingPackage no longer writes them either - see the code change
-- in this task).

-- ActualLoadQty (585497): was physical, becomes virtual - mirrors the planning's own ActualLoadQty.
UPDATE AD_Column
SET ColumnSQL     = '(select dp.ActualLoadQty
     from M_Delivery_Planning_Alloc dpa
     join M_Delivery_Planning dp on dp.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID
     where dpa.M_ShippingPackage_ID = M_ShippingPackage.M_ShippingPackage_ID
       and dpa.IsActive = ''Y'')',
    IsLazyLoading = 'Y',
    Updated       = TO_TIMESTAMP('2026-09-03 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy     = 100
WHERE AD_Column_ID = 585497;

-- ActualDischargeQuantity (585498): was physical, becomes virtual - mirrors the planning's own
-- ActualDischargeQuantity.
UPDATE AD_Column
SET ColumnSQL     = '(select dp.ActualDischargeQuantity
     from M_Delivery_Planning_Alloc dpa
     join M_Delivery_Planning dp on dp.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID
     where dpa.M_ShippingPackage_ID = M_ShippingPackage.M_ShippingPackage_ID
       and dpa.IsActive = ''Y'')',
    IsLazyLoading = 'Y',
    Updated       = TO_TIMESTAMP('2026-09-03 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy     = 100
WHERE AD_Column_ID = 585498;

-- PlannedLoadedQuantity (new, 593470): mirrors the planning's own PlannedLoadedQuantity, element 581794.
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Version, EntityType, ColumnName, AD_Table_ID, AD_Reference_ID, FieldLength,
    IsKey, IsParent, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, IsTranslated, IsEncrypted,
    IsSelectionColumn, AD_Element_ID, IsSyncDatabase, IsAlwaysUpdateable,
    ColumnSQL, IsAutocomplete, IsAllowLogging, IsAdvancedText, IsLazyLoading, IsCalculated,
    IsGenericZoomOrigin, IsGenericZoomKeyColumn, IsUseDocSequence, IsStaleable, DDL_NoForeignKey,
    IsDimension, IsDLMPartitionBoundary, IsRangeFilter, IsShowFilterIncrementButtons,
    IsForceIncludeInGeneratedModel, PersonalDataCategory, AllowZoomTo, IsAutoApplyValidationRule,
    IsFacetFilter, IsShowFilterInline, IsExcludeFromZoomTargets, IsRestAPICustomColumn,
    CloningStrategy, IsShowFilterInactiveValues)
VALUES (
    593470 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-03 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
    0, 'METAS_SHIPPING', 'PlannedLoadedQuantity', 540031, 29, 10,
    'N', 'N', 'N', 'Y', 'N', 0, 'N', 'N',
    'N', 581794, 'N', 'N',
    '(select dp.PlannedLoadedQuantity
     from M_Delivery_Planning_Alloc dpa
     join M_Delivery_Planning dp on dp.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID
     where dpa.M_ShippingPackage_ID = M_ShippingPackage.M_ShippingPackage_ID
       and dpa.IsActive = ''Y'')', 'N', 'Y', 'N', 'Y', 'N',
    'N', 'N', 'N', 'N', 'N',
    'N', 'N', 'N', 'N',
    'N', 'NP', 'Y', 'N',
    'N', 'N', 'N', 'N',
    'XX', 'N');

-- PlannedDischargeQuantity (new, 593471): mirrors the planning's own PlannedDischargeQuantity, element 581795.
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Version, EntityType, ColumnName, AD_Table_ID, AD_Reference_ID, FieldLength,
    IsKey, IsParent, IsMandatory, IsUpdateable, IsIdentifier, SeqNo, IsTranslated, IsEncrypted,
    IsSelectionColumn, AD_Element_ID, IsSyncDatabase, IsAlwaysUpdateable,
    ColumnSQL, IsAutocomplete, IsAllowLogging, IsAdvancedText, IsLazyLoading, IsCalculated,
    IsGenericZoomOrigin, IsGenericZoomKeyColumn, IsUseDocSequence, IsStaleable, DDL_NoForeignKey,
    IsDimension, IsDLMPartitionBoundary, IsRangeFilter, IsShowFilterIncrementButtons,
    IsForceIncludeInGeneratedModel, PersonalDataCategory, AllowZoomTo, IsAutoApplyValidationRule,
    IsFacetFilter, IsShowFilterInline, IsExcludeFromZoomTargets, IsRestAPICustomColumn,
    CloningStrategy, IsShowFilterInactiveValues)
VALUES (
    593471 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-03 09:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
    0, 'METAS_SHIPPING', 'PlannedDischargeQuantity', 540031, 29, 10,
    'N', 'N', 'N', 'Y', 'N', 0, 'N', 'N',
    'N', 581795, 'N', 'N',
    '(select dp.PlannedDischargeQuantity
     from M_Delivery_Planning_Alloc dpa
     join M_Delivery_Planning dp on dp.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID
     where dpa.M_ShippingPackage_ID = M_ShippingPackage.M_ShippingPackage_ID
       and dpa.IsActive = ''Y'')', 'N', 'Y', 'N', 'Y', 'N',
    'N', 'N', 'N', 'N', 'N',
    'N', 'N', 'N', 'N',
    'N', 'NP', 'Y', 'N',
    'N', 'N', 'N', 'N',
    'XX', 'N');

-- Seed AD_Column_Trl for the two new columns across every active language, then sync Name/Description/Help
-- from their (reused) AD_Element - the idiomatic single-cascade call, not a hand-written Name literal.
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.ColumnName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID IN (593470, 593471)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(581794);
SELECT update_Column_Translation_From_AD_Element(581795);

-- Rule 1 (application-dictionary skill): cross-table virtual columns MUST carry
-- AD_SQLColumn_SourceTableColumn entries, or the WebUI grid never refreshes when the source data changes -
-- exactly the frozen-figure defect this task exists to remove. Two source tables feed each of the four
-- columns: M_Delivery_Planning_Alloc (an allocation created/deactivated for this package - LINK_COLUMN,
-- same-named M_ShippingPackage_ID on both sides) and M_Delivery_Planning (the planning's own quantity
-- edited - SQL method, since there is no direct link column between M_ShippingPackage and
-- M_Delivery_Planning; the join runs through the allocation).
INSERT INTO AD_SQLColumn_SourceTableColumn (
    AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Column_ID, AD_Table_ID, Source_Table_ID, FetchTargetRecordsMethod, Link_Column_ID)
VALUES
    -- ActualLoadQty (585497) <- M_Delivery_Planning_Alloc (alloc created/deactivated for this package);
    -- link column is M_Delivery_Planning_Alloc.M_ShippingPackage_ID (593399) - same name as the target's
    -- own PK, which is what the LINK_COLUMN fetch method requires. AD_Table_ID (540031) is the TARGET
    -- table (M_ShippingPackage), Source_Table_ID (542641) the SOURCE (M_Delivery_Planning_Alloc).
    (540231 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     585497, 540031, 542641, 'L', 593399),
    -- ActualDischargeQuantity (585498) <- M_Delivery_Planning_Alloc
    (540232 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     585498, 540031, 542641, 'L', 593399),
    -- PlannedLoadedQuantity (593470) <- M_Delivery_Planning_Alloc
    (540233 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
     593470, 540031, 542641, 'L', 593399),
    -- PlannedDischargeQuantity (593471) <- M_Delivery_Planning_Alloc
    (540234 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
     593471, 540031, 542641, 'L', 593399);

-- The M_Delivery_Planning side needs the SQL method: no column shares a name between M_ShippingPackage
-- and M_Delivery_Planning, so LINK_COLUMN cannot express the two-hop join through the allocation.
INSERT INTO AD_SQLColumn_SourceTableColumn (
    AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Column_ID, AD_Table_ID, Source_Table_ID, FetchTargetRecordsMethod, SQL_GetTargetRecordIdBySourceRecordId)
VALUES
    -- ActualLoadQty (585497) <- M_Delivery_Planning (the planning's own ActualLoadQty edited)
    (540235 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100,
     585497, 540031, 542259, 'S',
     'select dpa.M_ShippingPackage_ID from M_Delivery_Planning_Alloc dpa where dpa.M_Delivery_Planning_ID=@Record_ID@ and dpa.IsActive=''Y'''),
    -- ActualDischargeQuantity (585498) <- M_Delivery_Planning
    (540236 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
     585498, 540031, 542259, 'S',
     'select dpa.M_ShippingPackage_ID from M_Delivery_Planning_Alloc dpa where dpa.M_Delivery_Planning_ID=@Record_ID@ and dpa.IsActive=''Y'''),
    -- PlannedLoadedQuantity (593470) <- M_Delivery_Planning
    (540237 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     593470, 540031, 542259, 'S',
     'select dpa.M_ShippingPackage_ID from M_Delivery_Planning_Alloc dpa where dpa.M_Delivery_Planning_ID=@Record_ID@ and dpa.IsActive=''Y'''),
    -- PlannedDischargeQuantity (593471) <- M_Delivery_Planning
    (540238 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     593471, 540031, 542259, 'S',
     'select dpa.M_ShippingPackage_ID from M_Delivery_Planning_Alloc dpa where dpa.M_Delivery_Planning_ID=@Record_ID@ and dpa.IsActive=''Y''');
